package com.wjy35.wij.run.judge.process;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiJavaFile;
import com.wjy35.wij.run.judge.environment.JudgeEnvironment;
import com.wjy35.wij.run.judge.exception.ProblemNumberInputCanceledException;
import com.wjy35.wij.run.judge.task.JudgeTask;
import com.wjy35.wij.ui.dialog.ProblemNumberDialog;
import com.wjy35.wij.util.clipboard.ClipBoardUtil;
import com.wjy35.wij.util.crawling.BojCrawler;
import com.wjy35.wij.util.file.IOFileManager;
import com.wjy35.wij.util.file.WijDirectoryManager;
import org.jetbrains.annotations.NotNull;
import org.jsoup.HttpStatusException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompositeJudgeProcessHandler extends OSProcessHandler {
    ConsoleView consoleView;
    PsiJavaFile psiJavaFile;
    VirtualFile directory;
    Project project;
    String path;
    String compilePath;
    String packageName;
    boolean isUpdateWijDirectory;

    List<JudgeTask> judgeTaskList;
    JudgeProcessHandler curProcessHandler;
    boolean isCanceled;

    IOFileManager ioFileManager;
    public CompositeJudgeProcessHandler(@NotNull GeneralCommandLine commandLine, JudgeEnvironment judgeEnvironment) throws ExecutionException {
        super(commandLine);

        this.consoleView = judgeEnvironment.getConsoleView();
        this.psiJavaFile =  judgeEnvironment.getPsiJavaFile();
        this.directory = psiJavaFile.getVirtualFile().getParent();
        this.project = psiJavaFile.getProject();
        this.path = this.directory.getPath();
        this.packageName = psiJavaFile.getPackageName();
        this.isUpdateWijDirectory = judgeEnvironment.isUpdateWijDirectory();

        this.judgeTaskList = new ArrayList<>();
        this.isCanceled = false;
    }

    @Override
    public void destroyProcess() {
        isCanceled = true;
        if(curProcessHandler!=null) curProcessHandler.destroyProcess();
        super.destroyProcess();
    }

    @Override
    public void startNotify() {
        new Task.Backgroundable(this.project, "Boj Test..",true) {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                try{
                    consoleView.print("\n" +
                            " _    _                          ___               \n" +
                            "| |  | |                        |_  |              \n" +
                            "| |  | |  __ _  _ __    __ _      | | _   _  _ __  \n" +
                            "| |/\\| | / _` || '_ \\  / _` |     | || | | || '_ \\ \n" +
                            "\\  /\\  /| (_| || | | || (_| | /\\__/ /| |_| || | | |\n" +
                            " \\/  \\/  \\__,_||_| |_| \\__, | \\____/  \\__,_||_| |_|\n" +
                            "                        __/ |                      \n" +
                            "                       |___/                       \n", ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
                    consoleView.print("===================================================\n", ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
                    ioFileManager = new IOFileManager(project,packageName);
                    if(isUpdateWijDirectory) updateWijDirectory();

                    List<String> inputNumberList = ioFileManager.getInputNumberList();
                    compilePath = WijDirectoryManager.getInstance(project).getCompileDirectory().getPath();
                    compile();
                    initJudgeTaskList(inputNumberList);

                    JudgeResult result = judge();
                    if(result.isAllAccepted()){
                        consoleView.print(result+ "\n", ConsoleViewContentType.USER_INPUT);
                        consoleView.print("소스코드가 클립보드에 복사 되었습니다!\n", ConsoleViewContentType.NORMAL_OUTPUT);
                    }else{
                        consoleView.print(result + "\n", ConsoleViewContentType.ERROR_OUTPUT);
                    }
                    consoleView.print("===================================================\n", ConsoleViewContentType.LOG_VERBOSE_OUTPUT);


                    if(result.isAllAccepted()) copyToClipBoard();
                } catch (HttpStatusException e) {
                    ApplicationManager.getApplication().invokeAndWait(() -> {
                        Messages.showErrorDialog("올바른 문제 번호를 입력해주세요.","WangJun Intellij Judge");
                    });
                    consoleView.print("is Canceled", ConsoleViewContentType.USER_INPUT);
                } catch (UnknownHostException e) {
                    ApplicationManager.getApplication().invokeAndWait(() -> {
                        Messages.showErrorDialog("인터넷 연결을 확인해주세요.","WangJun Intellij Judge");
                    });
                    consoleView.print("is Canceled", ConsoleViewContentType.USER_INPUT);
                } catch (IOException e) {
                    ApplicationManager.getApplication().invokeAndWait(() -> {
                        Messages.showErrorDialog("잠시후 다시 시도해주세요.","WangJun Intellij Judge");
                    });
                    consoleView.print("is Canceled", ConsoleViewContentType.USER_INPUT);
                }catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }catch (ProblemNumberInputCanceledException e){
                    consoleView.print("is Canceled", ConsoleViewContentType.USER_INPUT);
                }finally {
                    destroyProcess();
                    CompositeJudgeProcessHandler.super.startNotify();
                }
            }
        }.queue();
    }

    public void updateWijDirectory() throws IOException {
        String problemNumber = Optional.ofNullable(ProblemNumberDialog.showAndGet())
                .orElseThrow(ProblemNumberInputCanceledException::new);

        ioFileManager.deleteFiles();
        ioFileManager.saveAll(BojCrawler.crawlAll(problemNumber));
    }

    public void compile() throws ExecutionException {
        GeneralCommandLine commandLine = new GeneralCommandLine("javac","-Xlint:none","-nowarn", "-d",compilePath,"Main.java");
        commandLine.setWorkDirectory(path);
        OSProcessHandler processHandler = new CompileProcessHandler(commandLine);
        ProcessTerminatedListener.attach(processHandler);

        consoleView.attachToProcess(processHandler);
        consoleView.print("Compile Main.java\n", ConsoleViewContentType.LOG_VERBOSE_OUTPUT);

        processHandler.startNotify();
        processHandler.waitFor();

        consoleView.print("===================================================\n", ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
    }

    public void initJudgeTaskList(List<String> inputNumberList){
        for(String inputNumber : inputNumberList){
            String qualifiedName = packageName + (packageName.isEmpty() ? "Main":".Main");
            GeneralCommandLine commandLine = new GeneralCommandLine("java","-Dfile.encoding=UTF-8","-cp",compilePath,qualifiedName);
            commandLine.withInput(new File(ioFileManager.getInputPath(inputNumber)));
            judgeTaskList.add(new JudgeTask(inputNumber,commandLine));
        }
    }

    public JudgeResult judge() throws ExecutionException {
        int acceptedCount = 0;
        int taskCount = judgeTaskList.size();

        for(JudgeTask task : judgeTaskList){
            if(isCanceled) {
                consoleView.print("is Canceled", ConsoleViewContentType.USER_INPUT);
                break;
            }

            curProcessHandler = null;
            curProcessHandler = new JudgeProcessHandler(task.getCommandLine());

            if(isCanceled) {
                consoleView.print("is Canceled", ConsoleViewContentType.USER_INPUT);
                curProcessHandler.destroyProcess();
                break;
            }

            ProcessTerminatedListener.attach(curProcessHandler);
            consoleView.attachToProcess(curProcessHandler);

            consoleView.print("#"+task.getInputNumber()+"\n", ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
            consoleView.print("Actual:\n", ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
            curProcessHandler.startNotify();
            if(curProcessHandler.isTimeLimitExceeded()){
                consoleView.print("Time Limit Exceeded\n", ConsoleViewContentType.ERROR_OUTPUT);
                curProcessHandler.destroyProcess();

                continue;
            }

            String actual = curProcessHandler.getOutput();
            String expected = ioFileManager.getExpected(task.getInputNumber());
            consoleView.print("\n\nExpected:\n", ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
            consoleView.print(expected+"\n",ConsoleViewContentType.NORMAL_OUTPUT);

            if(isAccepted(actual,expected)){
                consoleView.print("Result: Accepted\n", ConsoleViewContentType.USER_INPUT);
                acceptedCount++;
            }else{
                consoleView.print("Result: Wrong Answer\n", ConsoleViewContentType.ERROR_OUTPUT);
            }

            consoleView.print("===================================================\n", ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
        }

        return new JudgeResult(acceptedCount,taskCount);
    }

    public boolean isAccepted(String actual,String expected) {
        if(actual.isEmpty()) return false;
        try {
            BufferedReader expectedBr = new BufferedReader(new StringReader(expected));
            BufferedReader actualBr = new BufferedReader(new StringReader(actual));
            String expectedLine = null;
            String actualLine = null;

            while(((expectedLine = expectedBr.readLine()) != null) & ((actualLine = actualBr.readLine()) != null)){
                if(!expectedLine.stripTrailing().equals(actualLine.stripTrailing())) return  false;
            }

            while(expectedLine!=null && expectedLine.isEmpty()) expectedLine = expectedBr.readLine();
            while(actualLine!=null && actualLine.isEmpty()) actualLine = actualBr.readLine();

            if(expectedLine!=null) return false;
            if(actualLine!=null) return false;
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    private void copyToClipBoard(){
        String sourceCode = this.psiJavaFile.getText();
        if(!this.packageName.isEmpty()) {
            String packageCode = "package " + this.packageName + ";";
            sourceCode = sourceCode.replaceFirst(packageCode,"");
        }

        ClipBoardUtil.copy(sourceCode.trim());
    }

}
