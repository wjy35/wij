package com.wjy35.wij.focus.io;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaFile;
import com.wjy35.wij.util.file.WIJDirectoryProvider;
import org.jetbrains.annotations.NotNull;

public class DirectoryFocusAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PsiElement element = e.getData(CommonDataKeys.PSI_FILE);
        if(element==null) return;
        if(!(element instanceof PsiJavaFile psiJavaFile)) return;

        ApplicationManager.getApplication().invokeLater(()->{
            VirtualFile targetDirectory = WIJDirectoryProvider.getInstance(psiJavaFile.getProject())
                    .getOrCreatePackageIO(psiJavaFile.getPackageName());

            ProjectView.getInstance(psiJavaFile.getProject())
                    .select(null, targetDirectory, true);
        });
    }
}
