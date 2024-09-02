package com.wjy35.wij.run.judge.environment;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.psi.PsiJavaFile;

public class JudgeEnvironment {
    private final ConsoleView consoleView;
    private final PsiJavaFile psiJavaFile;
    private boolean updateWijDirectory;


    public JudgeEnvironment(ConsoleView consoleView, PsiJavaFile psiJavaFile, boolean updateWijDirectory) {
        this.consoleView = consoleView;
        this.psiJavaFile = psiJavaFile;
        this.updateWijDirectory = updateWijDirectory;
    }

    public ConsoleView getConsoleView() {
        return consoleView;
    }

    public PsiJavaFile getPsiJavaFile() {
        return psiJavaFile;
    }

    public boolean isUpdateWijDirectory() {
        return updateWijDirectory;
    }
}
