package com.wjy35.wij.run.judge.process;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.InvalidVirtualFileAccessException;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiJavaFile;
import com.wjy35.wij.run.judge.compilation.CompilationStep;
import com.wjy35.wij.run.judge.compilation.exception.CompilationFailedException;
import com.wjy35.wij.run.judge.console.JudgeConsolePrinter;
import com.wjy35.wij.run.judge.environment.JudgeEnvironment;
import com.wjy35.wij.run.judge.exception.ProblemNumberInputCanceledException;
import com.wjy35.wij.run.judge.fetch.FetchStep;
import com.wjy35.wij.run.judge.fetch.exception.FetchFailedException;
import com.wjy35.wij.run.judge.fetch.exception.InternetConnectionLostException;
import com.wjy35.wij.run.judge.fetch.exception.InternetConnectionUnavailableException;
import com.wjy35.wij.run.judge.task.JudgeTask;
import com.wjy35.wij.ui.dialog.FileDeletedDuringJudgeExceptionDialog;
import com.wjy35.wij.ui.dialog.JudgeErrorDialog;
import com.wjy35.wij.ui.dialog.ProblemNumberDialog;
import com.wjy35.wij.util.clipboard.ClipBoardUtil;
import com.wjy35.wij.util.crawling.BOJCrawler;
import com.wjy35.wij.util.file.IOFileCommand;
import com.wjy35.wij.util.file.IOFileQuery;
import com.wjy35.wij.util.file.IOFileUtil;
import com.wjy35.wij.util.file.WIJDirectoryProvider;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompositeJudgeProcessHandler extends OSProcessHandler {
    /* Option Data */
    ConsoleView consoleView;
    PsiJavaFile psiJavaFile;
    boolean isFetchRequired;
    VirtualFile directory;
    Project project;
    String packageName;
    String qualifiedName;

    /* Process Data */
    String compilePath;
    JudgeProcessHandler curProcessHandler;
    boolean isCanceled;

    JudgeConsolePrinter consolePrinter;
    IOFileCommand ioFileCommand;
    IOFileQuery ioFileQuery;

    public CompositeJudgeProcessHandler(@NotNull GeneralCommandLine commandLine, JudgeEnvironment judgeEnvironment) throws ExecutionException {
        super(commandLine);

        this.consoleView = judgeEnvironment.getConsoleView();
        this.psiJavaFile =  judgeEnvironment.getOptions().getPsiJavaFile();
        this.isFetchRequired = judgeEnvironment.getOptions().isFetchRequired();
        this.project = judgeEnvironment.getOptions().getProject();
        this.directory = judgeEnvironment.getOptions().getDirectory();
        this.packageName = judgeEnvironment.getOptions().getPackageName();
        this.qualifiedName = judgeEnvironment.getOptions().getQualifiedName();
        this.consolePrinter = new JudgeConsolePrinter(consoleView);

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
            public void onCancel() {
                super.onCancel();
                destroyProcess();
            }

            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                try{
                    consolePrinter.printStartMessage();
                    consolePrinter.printSeparator();

                    ioFileCommand = new IOFileCommand(project,packageName);
                    ioFileQuery = new IOFileQuery(project,packageName);

                    if(isFetchRequired) new FetchStep(ioFileCommand).execute();

                    compilePath = WIJDirectoryProvider.getInstance(project).getOrCreateCompile().getPath();
                    new CompilationStep(consoleView, directory.getPath(), compilePath).execute();

                    List<String> ioFileNumberList = ioFileQuery.findIOFileNumberList();
                    List<JudgeTask> judgeTaskList = initJudgeTaskList(ioFileNumberList);
                    TotalJudgeResult totalJudgeResult = judge(judgeTaskList);

                    consolePrinter.printTotalJudgeResult(totalJudgeResult);
                    consolePrinter.printSeparator();

                    if(totalJudgeResult.isAllAccepted()) ClipBoardUtil.copy(psiJavaFile);

                } catch (CompilationFailedException e){
                    // ToDo Handle CompilationFailedException
                } catch (FetchFailedException e){
                    // ToDo Handle FetchFailedException
                } catch (InternetConnectionLostException e) {
                    consolePrinter.printProcessCanceledMessage();
                    JudgeErrorDialog.showWrongProblemNumber();
                } catch (InternetConnectionUnavailableException e) {
                    consolePrinter.printProcessCanceledMessage();
                    JudgeErrorDialog.showInternetConnectionError();
                } catch (ExecutionException e) {
                    consolePrinter.printProcessCanceledMessage();
                    JudgeErrorDialog.showTryLater();
                } catch (ProblemNumberInputCanceledException ignored){
                    consolePrinter.printProcessCanceledMessage();
                } catch (InvalidVirtualFileAccessException e){
                    consolePrinter.printProcessCanceledMessage();
                    FileDeletedDuringJudgeExceptionDialog.show();
                }finally {
                    destroyProcess();
                    CompositeJudgeProcessHandler.super.startNotify();
                }
            }
        }.queue();
    }

    public List<JudgeTask> initJudgeTaskList(List<String> ioFileNumberList){
        List<JudgeTask> judgeTaskList = new ArrayList<>();

        for(String ioFileNumber : ioFileNumberList){
            GeneralCommandLine commandLine = new GeneralCommandLine("java","-Dfile.encoding=UTF-8","-cp",compilePath,qualifiedName);
            commandLine.withInput(new File(ioFileQuery.fileNumberToInputFilePath(ioFileNumber)));

            ioFileQuery.fileNumberToInputFilePath(ioFileNumber);
            judgeTaskList.add(new JudgeTask(ioFileNumber,commandLine));
        }

        return judgeTaskList;
    }

    public TotalJudgeResult judge(List<JudgeTask> judgeTaskList) throws ExecutionException {
        TotalJudgeResult totalJudgeResult = new TotalJudgeResult(judgeTaskList.size());

        for(JudgeTask task : judgeTaskList){
            if(isCanceled) {
                consolePrinter.printProcessCanceledMessage();
                break;
            }
            curProcessHandler = null;
            curProcessHandler = new JudgeProcessHandler(task.getCommandLine());
            if(isCanceled) {
                consolePrinter.printProcessCanceledMessage();
                curProcessHandler.destroyProcess();
                break;
            }

            ProcessTerminatedListener.attach(curProcessHandler);
            consoleView.attachToProcess(curProcessHandler);

            consolePrinter.printTestNumberMessage(task.getInputNumber());
            consolePrinter.printActual();
            curProcessHandler.startNotify();

            if(curProcessHandler.isTimeLimitExceeded()){
                consolePrinter.printTimeLimitExceededMessage();
                curProcessHandler.destroyProcess();

                continue;
            }

            String actual = curProcessHandler.getOutput();
            String expected = IOFileUtil.getContent(ioFileQuery.findOutputFileBy(task.getInputNumber()));

            consolePrinter.printExpected(expected);

            if(new JudgeResult(actual,expected).isAccepted()){
                consolePrinter.printAcceptedMessage();
                totalJudgeResult.increaseAcceptedCount();
            }else{
                consolePrinter.printWrongAnswerMessage();
            }

            consolePrinter.printSeparator();
        }

        return totalJudgeResult;
    }
}
