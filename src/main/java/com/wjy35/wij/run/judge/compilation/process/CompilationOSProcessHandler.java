package com.wjy35.wij.run.judge.compilation.process;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessOutputTypes;
import com.intellij.openapi.util.Key;
import org.jetbrains.annotations.NotNull;

public class CompilationOSProcessHandler extends OSProcessHandler {
    public CompilationOSProcessHandler(@NotNull GeneralCommandLine commandLine) throws ExecutionException {
        super(commandLine);
    }

    @Override
    public void notifyTextAvailable(@NotNull String text, @NotNull Key outputType) {
        if(outputType==ProcessOutputTypes.STDERR) return;

        super.notifyTextAvailable(text, outputType);
    }
}
