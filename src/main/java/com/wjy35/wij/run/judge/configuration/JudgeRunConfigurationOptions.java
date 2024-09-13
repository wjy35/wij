package com.wjy35.wij.run.judge.configuration;

import com.intellij.execution.configurations.LocatableRunConfigurationOptions;
import com.intellij.psi.PsiJavaFile;
import org.jetbrains.annotations.NotNull;

public class JudgeRunConfigurationOptions extends LocatableRunConfigurationOptions {
    private PsiJavaFile psiJavaFile;
    private boolean updateFile;

    public JudgeRunConfigurationOptions(@NotNull PsiJavaFile psiJavaFile,@NotNull boolean updateFile) {
        this.psiJavaFile = psiJavaFile;
        this.updateFile = updateFile;
    }

    public PsiJavaFile getPsiJavaFile() {
        return psiJavaFile;
    }

    public boolean isUpdateFile() {
        return updateFile;
    }
}
