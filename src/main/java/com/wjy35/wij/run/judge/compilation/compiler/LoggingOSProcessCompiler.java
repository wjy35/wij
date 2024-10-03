package com.wjy35.wij.run.judge.compilation.compiler;

import com.wjy35.wij.run.judge.compilation.console.CompilationConsoleWriter;

public class LoggingOSProcessCompiler extends LoggingCompiler{
    private final CompilationConsoleWriter consoleWriter;

    public LoggingOSProcessCompiler(OSProcessCompiler compiler) {
        super(compiler);
        this.consoleWriter = new CompilationConsoleWriter(compiler.consoleView);
    }

    @Override
    protected void before() {
        consoleWriter.writeStart();
    }

    @Override
    protected void after() {
        consoleWriter.writeSeparator();
    }
}
