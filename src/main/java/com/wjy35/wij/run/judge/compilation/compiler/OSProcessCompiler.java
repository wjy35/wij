package com.wjy35.wij.run.judge.compilation.compiler;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.ui.ConsoleView;
import com.wjy35.wij.run.judge.compilation.exception.CompilationFailedException;
import com.wjy35.wij.run.judge.compilation.process.CompilationOSProcessHandler;

public class OSProcessCompiler implements Compiler {
    protected final ConsoleView consoleView;
    protected final String workPath;
    protected final String targetPath;

    public OSProcessCompiler(ConsoleView consoleView, String workPath, String targetPath) {
        this.consoleView = consoleView;
        this.workPath = workPath;
        this.targetPath = targetPath;
    }

    public void compile(){
        try{
            tryToCompile();
        } catch (ExecutionException e) {

            throw new CompilationFailedException();
        }
    }

    private void tryToCompile() throws ExecutionException {
        GeneralCommandLine commandLine = new GeneralCommandLine("javac","-encoding","UTF-8","-Xlint:none","-nowarn", "-d",targetPath,"Main.java");
        commandLine.setWorkDirectory(workPath);

        OSProcessHandler processHandler = new CompilationOSProcessHandler(commandLine);
        ProcessTerminatedListener.attach(processHandler);
        consoleView.attachToProcess(processHandler);

        processHandler.startNotify();
        processHandler.waitFor();
    }
}
