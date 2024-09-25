package com.wjy35.wij.run.judge.compilation.compiler;


import com.wjy35.wij.run.judge.compilation.exception.CompilationFailedException;

public abstract class LoggingCompiler implements Compiler {
    protected final Compiler compiler;

    public LoggingCompiler(Compiler Compiler) {
        this.compiler = Compiler;
    }

    @Override
    public void compile() throws CompilationFailedException {
        before();
        compiler.compile();
        after();
    }

    protected abstract void before();
    protected abstract void after();
}
