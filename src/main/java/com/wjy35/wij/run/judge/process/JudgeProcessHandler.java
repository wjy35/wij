package com.wjy35.wij.run.judge.process;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessOutputTypes;
import com.intellij.openapi.util.Key;
import org.jetbrains.annotations.NotNull;

public class JudgeProcessHandler extends OSProcessHandler {
    private StringBuilder outputBuilder = new StringBuilder();
    private StringBuilder errorBuilder = new StringBuilder();
    private static final int TIME_LIMIT_SECOND = 10*1000;

    public JudgeProcessHandler(@NotNull GeneralCommandLine commandLine) throws ExecutionException {
        super(commandLine);
    }

    // ToDo Separate Error & Normal
    @Override
    public void notifyTextAvailable(@NotNull String text, @NotNull Key outputType) {
        if(outputType==ProcessOutputTypes.SYSTEM) return;

        synchronized (this) {
            super.notifyTextAvailable(text, outputType);
            outputBuilder.append(text);
        }
    }

    @Override
    public void startNotify() {
        super.startNotify();
    }

    public boolean isTimeLimitExceeded(){ return !super.waitFor(TIME_LIMIT_SECOND); }

    public String getOutput(){ return outputBuilder.toString();}
}
