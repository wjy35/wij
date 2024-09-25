package com.wjy35.wij.run.judge.compilation.compiler;

import com.intellij.execution.ui.ConsoleViewContentType;
import com.wjy35.wij.run.judge.common.JudgeConsoleMessages;

public class LoggingOSProcessCompiler extends LoggingCompiler{
    public LoggingOSProcessCompiler(OSProcessCompiler Compiler) {
        super(Compiler);
    }

    @Override
    protected void before() {
        ((OSProcessCompiler)super.compiler).consoleView.print("Compile Main.java\n", ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
    }

    @Override
    protected void after() {
        ((OSProcessCompiler)super.compiler).consoleView.print(JudgeConsoleMessages.SEPARATOR, ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
    }
}
