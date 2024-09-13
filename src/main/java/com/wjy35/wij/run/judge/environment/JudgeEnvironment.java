package com.wjy35.wij.run.judge.environment;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.psi.PsiJavaFile;
import com.wjy35.wij.run.judge.configuration.JudgeRunConfigurationOptions;

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
