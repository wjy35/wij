package com.wjy35.wij.run.judge.composite;

import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.openapi.util.NotNullLazyValue;
import com.wjy35.wij.ui.icon.Icons;

public class JudgeRunConfigurationType extends ConfigurationTypeBase {
    public static final String ID = "WIJ";

    public JudgeRunConfigurationType(){
        super(ID, "WIJ", "WIJ Run Configuration", NotNullLazyValue.createValue(() -> Icons.RUN));
    }
}
