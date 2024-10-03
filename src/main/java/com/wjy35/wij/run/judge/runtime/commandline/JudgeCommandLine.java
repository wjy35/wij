package com.wjy35.wij.run.judge.runtime.commandline;

import com.intellij.execution.configurations.GeneralCommandLine;
import org.jetbrains.annotations.NotNull;

public class JudgeCommandLine extends GeneralCommandLine {
    private final String ioFileNumber;

    public JudgeCommandLine(String ioFileNumber, String @NotNull ... command) {
        super(command);
        this.ioFileNumber = ioFileNumber;
    }

    public String getIoFileNumber() {
        return ioFileNumber;
    }
}
