package com.wjy35.wij.focus.io;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaFile;
import com.wjy35.wij.ui.dialog.InputFocusErrorDialog;
import com.wjy35.wij.util.file.IOFileManager;
import org.jetbrains.annotations.NotNull;

public class InputFocusAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PsiElement element = e.getData(CommonDataKeys.PSI_FILE);
        if(element==null) return;
        if(!(element instanceof PsiJavaFile psiJavaFile)) return;

        ApplicationManager.getApplication().invokeLater(()->{
            IOFileManager ioFileManager = new IOFileManager(psiJavaFile.getProject(),psiJavaFile.getPackageName());

            VirtualFile firstFile = ioFileManager.findFirstFile();
            if(firstFile==null){
                InputFocusErrorDialog.show();
                return;
            }

            FileEditorManager.getInstance(psiJavaFile.getProject()).openFile(firstFile, true);
            ProjectView.getInstance(psiJavaFile.getProject()).select(null, firstFile, true);
        });
    }
}
