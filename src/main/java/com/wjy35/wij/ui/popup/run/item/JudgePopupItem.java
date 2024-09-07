package com.wjy35.wij.ui.popup.run.item;

import com.intellij.psi.PsiJavaFile;
import com.wjy35.wij.run.judge.JudgeExecutor;
import com.wjy35.wij.run.judge.configuration.JudgeRunConfigurationOptions;
import com.wjy35.wij.ui.icon.Icons;

public class JudgePopupItem extends RunListPopupItem {
    public JudgePopupItem(PsiJavaFile psiJavaFile) {
        super("Judge", Icons.JUDGE,psiJavaFile);
    }

    @Override
    public void onClick() {
        JudgeRunConfigurationOptions options = new JudgeRunConfigurationOptions();
        options.setPsiJavaFile(getPsiJavaFile());
        options.setUpdateInputFile(false);

        new JudgeExecutor(options).start();
    }
}
