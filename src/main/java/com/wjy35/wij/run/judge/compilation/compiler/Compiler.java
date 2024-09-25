package com.wjy35.wij.run.judge.compilation.compiler;


import com.wjy35.wij.run.judge.compilation.exception.CompilationFailedException;

public interface Compiler {
    void compile() throws CompilationFailedException;
}
