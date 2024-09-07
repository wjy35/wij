package com.wjy35.wij.ui.popup.run.item;

import com.intellij.psi.PsiJavaFile;
import com.wjy35.wij.run.judge.JudgeExecutor;
import com.wjy35.wij.run.judge.configuration.JudgeRunConfigurationOptions;
import com.wjy35.wij.ui.icon.Icons;

public class UpdateAndJudgePopupItem extends RunListPopupItem {
    public UpdateAndJudgePopupItem(PsiJavaFile psiJavaFile) {
        super("Update Input & Judge", Icons.UPDATE_AND_JUDGE,psiJavaFile);
    }

    @Override
    public void onClick() {
        JudgeRunConfigurationOptions options = new JudgeRunConfigurationOptions();
        options.setPsiJavaFile(getPsiJavaFile());
        options.setUpdateInputFile(true);

        new JudgeExecutor(options).start();
    }
}
