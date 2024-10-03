package com.wjy35.wij.run.judge;

import com.intellij.execution.*;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.psi.PsiJavaFile;
import com.wjy35.wij.run.judge.composite.JudgeRunConfigurationFactory;
import com.wjy35.wij.run.judge.composite.JudgeRunConfigurationOptions;
import com.wjy35.wij.run.judge.composite.JudgeRunConfigurationType;
import org.jetbrains.annotations.NotNull;

public class JudgeExecutor {
    private final PsiJavaFile psiJavaFile;

    public JudgeExecutor(@NotNull PsiJavaFile psiJavaFile) {
        this.psiJavaFile = psiJavaFile;
    }

    public void executeWithoutFetch(){
        execute(false);
    }

    public void executeWithFetch(){
        execute(true);
    }

    private void execute(boolean isFetchRequired){
        JudgeRunConfigurationType type = new JudgeRunConfigurationType();
        JudgeRunConfigurationOptions options = new JudgeRunConfigurationOptions(psiJavaFile,isFetchRequired);
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
