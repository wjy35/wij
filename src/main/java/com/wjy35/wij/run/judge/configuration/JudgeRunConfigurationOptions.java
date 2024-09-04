package com.wjy35.wij.run.judge.configuration;

import com.intellij.execution.configurations.LocatableRunConfigurationOptions;
import com.intellij.psi.PsiJavaFile;

public class JudgeRunConfigurationOptions extends LocatableRunConfigurationOptions {
    private PsiJavaFile psiJavaFile;
    private boolean selectedUpdate;

    public boolean isUpdateInputFile() {
        return selectedUpdate;
    }

    public void setUpdateInputFile(boolean updateInputFile) {
        this.selectedUpdate = updateInputFile;
    }

    public PsiJavaFile getPsiJavaFile() {
        return psiJavaFile;
    }

    public void setPsiJavaFile(PsiJavaFile psiJavaFile) {
        this.psiJavaFile = psiJavaFile;
    }


}
