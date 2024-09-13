package com.wjy35.wij.run.judge.configuration;

import com.intellij.execution.DefaultExecutionResult;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.wjy35.wij.run.judge.environment.JudgeEnvironment;
import com.wjy35.wij.run.judge.process.CompositeJudgeProcessHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JudgeRunConfiguration extends LocatableConfigurationBase<JudgeRunConfiguration> {
    JudgeRunConfigurationOptions options;


    protected JudgeRunConfiguration(@NotNull Project project, @NotNull ConfigurationFactory factory, @Nullable String name, JudgeRunConfigurationOptions options) {
        super(project, factory, name);
        this.options = options;
    }

    @Override
    public @NotNull SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return null;
    }

    @Override
    public @Nullable RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment) throws ExecutionException {
        return new RunProfileState() {
            @Override
            public @Nullable ExecutionResult execute(Executor executor, @NotNull ProgramRunner<?> runner) throws ExecutionException {
                ConsoleView consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(environment.getProject()).getConsole();
                JudgeEnvironment judgeEnvironment = new JudgeEnvironment(consoleView,options);

                GeneralCommandLine commandLine = new GeneralCommandLine("java","--version");
                ProcessHandler processHandler = new CompositeJudgeProcessHandler(commandLine,judgeEnvironment);
                ProcessTerminatedListener.attach(processHandler);
                return new DefaultExecutionResult(consoleView,processHandler);
            }
        };
    }

}

