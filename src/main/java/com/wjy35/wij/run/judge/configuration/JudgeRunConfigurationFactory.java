package com.wjy35.wij.run.judge.configuration;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class JudgeRunConfigurationFactory extends ConfigurationFactory {
    JudgeRunConfigurationOptions options;

    @Override
    public @NotNull @NonNls String getId() {
        return JudgeRunConfigurationType.ID
                + options.getPsiJavaFile().getPackageName()
                + options.getPsiJavaFile().getName()
                + options.isUpdateFile();
    }

    public JudgeRunConfigurationFactory(@NotNull ConfigurationType type, @NotNull JudgeRunConfigurationOptions options) {
        super(type);
        this.options = options;
    }

    @Override
    public @NotNull RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new JudgeRunConfiguration(project,this,null,options);
    }
}
