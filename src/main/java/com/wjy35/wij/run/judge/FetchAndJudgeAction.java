package com.wjy35.wij.run.judge;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaFile;
import org.jetbrains.annotations.NotNull;

public class FetchAndJudgeAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PsiElement element = e.getData(CommonDataKeys.PSI_FILE);
        if(element==null) return;
        if(!(element instanceof PsiJavaFile psiJavaFile)) return;

        new JudgeExecutor(psiJavaFile).executeWithFetch();
    }
}
