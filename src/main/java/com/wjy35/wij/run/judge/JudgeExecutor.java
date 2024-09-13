package com.wjy35.wij.run.judge;

import com.intellij.execution.*;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
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
        JudgeRunConfigurationType type = new JudgeRunConfigurationType();
        JudgeRunConfigurationOptions options = new JudgeRunConfigurationOptions(psiJavaFile,updateFile);
        JudgeRunConfigurationFactory factory = new JudgeRunConfigurationFactory(type, options);

        RunManager runManager = RunManager.getInstance(options.getProject());
        RunnerAndConfigurationSettings settings = runManager.createConfiguration(options.getQualifiedName(), factory);

        Executor executor = ExecutorRegistry.getInstance().getExecutorById("Run");
        if(executor==null) return;

        new Task.Backgroundable(options.getProject(), "Start Test",true){
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                ProgramRunnerUtil.executeConfiguration(settings, executor);
            }
        }.queue();

        runManager.addConfiguration(settings);
        runManager.setSelectedConfiguration(settings);
    }
}
