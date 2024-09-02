package com.wjy35.wij.run.judge.task;

import com.intellij.execution.configurations.GeneralCommandLine;
import org.jetbrains.annotations.NotNull;

public class JudgeTask {
    private String inputNumber;
    private GeneralCommandLine commandLine;

    public JudgeTask(@NotNull String inputNumber,@NotNull GeneralCommandLine commandLine) {
        this.inputNumber = inputNumber;
        this.commandLine = commandLine;
    }

    public String getInputNumber() {
        return inputNumber;
    }

    public GeneralCommandLine getCommandLine() {
        return commandLine;
    }
}

