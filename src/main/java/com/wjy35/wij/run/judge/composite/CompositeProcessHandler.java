package com.wjy35.wij.run.judge.composite;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.InvalidVirtualFileAccessException;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiJavaFile;
import com.wjy35.wij.run.judge.common.ConsoleWriter;
import com.wjy35.wij.run.judge.compilation.CompilationStep;
import com.wjy35.wij.run.judge.compilation.exception.CompilationFailedException;
import com.wjy35.wij.run.judge.fetch.exception.ProblemNumberInputCanceledException;
import com.wjy35.wij.run.judge.fetch.FetchStep;
import com.wjy35.wij.run.judge.fetch.exception.FetchFailedException;
import com.wjy35.wij.run.judge.fetch.exception.InternetConnectionLostException;
import com.wjy35.wij.run.judge.fetch.exception.InternetConnectionUnavailableException;
import com.wjy35.wij.run.judge.runtime.RuntimeStep;
import com.wjy35.wij.run.judge.runtime.exception.JudgeFailedException;
import com.wjy35.wij.run.judge.runtime.result.JudgeStatus;
import com.wjy35.wij.ui.dialog.judge.CompositeDialog;
import com.wjy35.wij.util.clipboard.ClipBoardUtil;
import com.wjy35.wij.util.file.IOFileCommand;
import com.wjy35.wij.util.file.IOFileQuery;
import com.wjy35.wij.util.file.WIJDirectoryProvider;
import org.jetbrains.annotations.NotNull;

public class CompositeProcessHandler extends OSProcessHandler {
    ConsoleView consoleView;
    PsiJavaFile psiJavaFile;
    boolean isFetchRequired;
    VirtualFile directory;
    Project project;
    String packageName;
    String qualifiedName;
    ConsoleWriter consoleWriter;

    public CompositeProcessHandler(@NotNull GeneralCommandLine commandLine, JudgeEnvironment judgeEnvironment) throws ExecutionException {
        super(commandLine);

        this.consoleView = judgeEnvironment.getConsoleView();
        this.psiJavaFile =  judgeEnvironment.getOptions().getPsiJavaFile();
        this.isFetchRequired = judgeEnvironment.getOptions().isFetchRequired();
        this.project = judgeEnvironment.getOptions().getProject();
        this.directory = judgeEnvironment.getOptions().getDirectory();
        this.packageName = judgeEnvironment.getOptions().getPackageName();
        this.qualifiedName = judgeEnvironment.getOptions().getQualifiedName();
        this.consoleWriter = new ConsoleWriter(consoleView);
    }

    RuntimeStep runtimeStep;

    @Override
    public void destroyProcess() {
        if(runtimeStep!=null && runtimeStep.isNotTerminate()) runtimeStep.terminate();
        super.destroyProcess();
    }

    @Override
    public void startNotify() {
        new Task.Backgroundable(this.project, "BOJ Test..",true) {
            public void tryToRun(@NotNull ProgressIndicator indicator){
                consoleWriter.writeAsciiArt();
                consoleWriter.writeSeparator();

                IOFileCommand ioFileCommand = new IOFileCommand(project,packageName);
                IOFileQuery ioFileQuery = new IOFileQuery(project,packageName);
                String compilePath = WIJDirectoryProvider.getInstance(project).getOrCreateCompile().getPath();

                if(isFetchRequired) new FetchStep(ioFileCommand).execute();

                new CompilationStep(consoleView, directory.getPath(), compilePath).execute();

                runtimeStep = new RuntimeStep(consoleView,ioFileQuery,compilePath,qualifiedName);
                JudgeStatus judgeStatus = runtimeStep.execute();

                if(judgeStatus==JudgeStatus.ALL_ACCEPTED){
                    ClipBoardUtil.copy(psiJavaFile);
                }
            }

            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                try{
                    tryToRun(indicator);
                } catch (CompilationFailedException | FetchFailedException | JudgeFailedException e){
                    consoleWriter.writeProcessTerminated();
                    CompositeDialog.showTryLater();
                } catch (InternetConnectionLostException e) {
                    consoleWriter.writeProcessTerminated();
                    CompositeDialog.showWrongProblemNumber();
                } catch (InternetConnectionUnavailableException e) {
                    consoleWriter.writeProcessTerminated();
                    CompositeDialog.showInternetConnectionError();
                } catch (ProblemNumberInputCanceledException ignored){
                    consoleWriter.writeProcessTerminated();
                } catch (InvalidVirtualFileAccessException e){
                    consoleWriter.writeProcessTerminated();
                    CompositeDialog.showFileDeleteDuringJudgeProcess();
                }finally {
                    destroyProcess();
                    CompositeProcessHandler.super.startNotify();
                }
            }

            @Override
            public void onCancel() {
                super.onCancel();
                destroyProcess();
            }
        }.queue();
    }
}
