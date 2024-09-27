package com.wjy35.wij.run.judge.compilation;


import com.intellij.execution.ui.ConsoleView;
import com.wjy35.wij.run.judge.compilation.compiler.Compiler;
import com.wjy35.wij.run.judge.compilation.compiler.LoggingOSProcessCompiler;
import com.wjy35.wij.run.judge.compilation.compiler.OSProcessCompiler;

public class CompilationStep {
    private final ConsoleView consoleView;
    private final String workPath;
    private final String targetPath;

    public CompilationStep(ConsoleView consoleView, String workPath, String targetPath) {
        this.consoleView = consoleView;
        this.workPath = workPath;
        this.targetPath = targetPath;
    }

    public void execute(){
        Compiler compiler = new LoggingOSProcessCompiler(new OSProcessCompiler(consoleView, workPath, targetPath));
        compiler.compile();
    }
}
