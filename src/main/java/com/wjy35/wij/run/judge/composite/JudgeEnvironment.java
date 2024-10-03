package com.wjy35.wij.run.judge.composite;

import com.intellij.execution.ui.ConsoleView;

public class JudgeEnvironment {
    private final ConsoleView consoleView;
    private final JudgeRunConfigurationOptions options;

    public JudgeEnvironment(ConsoleView consoleView, JudgeRunConfigurationOptions options) {
        this.consoleView = consoleView;
        this.options = options;
    }

    public ConsoleView getConsoleView() {
        return consoleView;
    }

    public JudgeRunConfigurationOptions getOptions() {
        return options;
    }
}
