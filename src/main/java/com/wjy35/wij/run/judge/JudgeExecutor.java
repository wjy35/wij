package com.wjy35.wij.run.judge;

import com.intellij.execution.*;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiJavaFile;
import com.wjy35.wij.run.judge.configuration.JudgeRunConfigurationFactory;
import com.wjy35.wij.run.judge.configuration.JudgeRunConfigurationOptions;
import com.wjy35.wij.run.judge.configuration.JudgeRunConfigurationType;

public class JudgeExecutor{
    private JudgeRunConfigurationOptions options;

    public JudgeExecutor(JudgeRunConfigurationOptions options) {
        this.options = options;
    }

    public void start(){
        PsiJavaFile psiJavaFile = options.getPsiJavaFile();
        Project project = psiJavaFile.getProject();

        JudgeRunConfigurationFactory factory = new JudgeRunConfigurationFactory(new JudgeRunConfigurationType(), options);
        RunnerAndConfigurationSettings settings = RunManager.getInstance(project).createConfiguration("Boj " + psiJavaFile.getPackageName()+ "." + psiJavaFile.getName(), factory);

        Executor executor = ExecutorRegistry.getInstance().getExecutorById("Run");
        ProgramRunnerUtil.executeConfiguration(settings, executor);

        RunManager.getInstance(project).addConfiguration(settings);
    }
}
