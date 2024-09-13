package com.wjy35.wij.run.judge.configuration;

import com.intellij.execution.configurations.LocatableRunConfigurationOptions;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiJavaFile;
import org.jetbrains.annotations.NotNull;

public class JudgeRunConfigurationOptions extends LocatableRunConfigurationOptions {
    private final PsiJavaFile psiJavaFile;
    private final boolean updateFile;
    private final Project project;
    private final VirtualFile directory;
    private final String packageName;
    private final String qualifiedName;

    public JudgeRunConfigurationOptions(@NotNull PsiJavaFile psiJavaFile,@NotNull boolean updateFile) {
        this.psiJavaFile = psiJavaFile;
        this.updateFile = updateFile;
        this.directory = psiJavaFile.getVirtualFile().getParent();
        this.project = psiJavaFile.getProject();
        this.packageName = psiJavaFile.getPackageName();
        this.qualifiedName = psiJavaFile.getPackageName()
                + (psiJavaFile.getPackageName().isEmpty() ? "Main": ".Main");
    }

    public PsiJavaFile getPsiJavaFile() {
        return psiJavaFile;
    }

    public boolean isUpdateFile() {
        return updateFile;
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
