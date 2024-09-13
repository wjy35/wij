package com.wjy35.wij.run.judge;

import com.intellij.execution.*;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiJavaFile;
import com.wjy35.wij.run.judge.configuration.JudgeRunConfigurationFactory;
import com.wjy35.wij.run.judge.configuration.JudgeRunConfigurationOptions;
import com.wjy35.wij.run.judge.configuration.JudgeRunConfigurationType;
import org.jetbrains.annotations.NotNull;

public class JudgeExecutor {
    private final PsiJavaFile psiJavaFile;

    public JudgeExecutor(@NotNull PsiJavaFile psiJavaFile) {
        this.psiJavaFile = psiJavaFile;
    }

    public void executeWithoutUpdate(){
        execute(false);
    }

    public void executeWithUpdate(){
        execute(true);
    }

    private void execute(boolean updateFile){
        Project project = this.psiJavaFile.getProject();

        JudgeRunConfigurationType type = new JudgeRunConfigurationType();
        JudgeRunConfigurationOptions options = new JudgeRunConfigurationOptions(this.psiJavaFile,updateFile);
        JudgeRunConfigurationFactory factory = new JudgeRunConfigurationFactory(type, options);

        String qualifiedName = this.psiJavaFile.getPackageName()
                + (this.psiJavaFile.getPackageName().isEmpty() ? "Main": ".Main");

        RunManager runManager = RunManager.getInstance(project);
        RunnerAndConfigurationSettings settings = runManager.createConfiguration(qualifiedName, factory);

        Executor executor = ExecutorRegistry.getInstance().getExecutorById("Run");
        ProgramRunnerUtil.executeConfiguration(settings, executor);

        runManager.addConfiguration(settings);
        runManager.setSelectedConfiguration(settings);
    }
}
