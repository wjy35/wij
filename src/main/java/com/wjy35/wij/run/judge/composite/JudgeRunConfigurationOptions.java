package com.wjy35.wij.run.judge.composite;

import com.intellij.execution.configurations.LocatableRunConfigurationOptions;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiJavaFile;
import org.jetbrains.annotations.NotNull;

public class JudgeRunConfigurationOptions extends LocatableRunConfigurationOptions {
    private final PsiJavaFile psiJavaFile;
    private final boolean isFetchRequired;
    private final Project project;
    private final VirtualFile directory;
    private final String packageName;
    private final String qualifiedName;

    public JudgeRunConfigurationOptions(@NotNull PsiJavaFile psiJavaFile,@NotNull boolean isFetchRequired) {
        this.psiJavaFile = psiJavaFile;
        this.isFetchRequired = isFetchRequired;
        this.directory = psiJavaFile.getVirtualFile().getParent();
        this.project = psiJavaFile.getProject();
        this.packageName = psiJavaFile.getPackageName();
        this.qualifiedName = psiJavaFile.getPackageName()
                + (psiJavaFile.getPackageName().isEmpty() ? "Main": ".Main");
    }

    public PsiJavaFile getPsiJavaFile() {
        return psiJavaFile;
    }

    public boolean isFetchRequired() {
        return isFetchRequired;
    }

    public Project getProject() {
        return project;
    }

    public VirtualFile getDirectory() {
        return directory;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }
}
