package com.wjy35.wij.run.judge.compilation;


import com.intellij.execution.ui.ConsoleView;
import com.wjy35.wij.run.judge.compilation.compiler.Compiler;
import com.wjy35.wij.run.judge.compilation.compiler.LoggingOSProcessCompiler;
import com.wjy35.wij.run.judge.compilation.compiler.OSProcessCompiler;

public class CompilationStep {
    public static void executeWith(ConsoleView consoleView, String workPath, String targetPath){
        Compiler compiler = new LoggingOSProcessCompiler(new OSProcessCompiler(consoleView, workPath, targetPath));
        compiler.compile();
    }
}
