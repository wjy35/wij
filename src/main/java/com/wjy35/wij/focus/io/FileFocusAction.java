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
import com.wjy35.wij.util.file.IOFileQuery;
import com.wjy35.wij.util.file.exception.IOFileNotFoundedException;
import org.jetbrains.annotations.NotNull;

public class FileFocusAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PsiElement element = e.getData(CommonDataKeys.PSI_FILE);
        if(element==null) return;
        if(!(element instanceof PsiJavaFile psiJavaFile)) return;

        focus(psiJavaFile);
    }

    private void focus(PsiJavaFile psiJavaFile){
        ApplicationManager.getApplication().invokeLater(()->{
            IOFileQuery ioFileQuery = new IOFileQuery(psiJavaFile.getProject(),psiJavaFile.getPackageName());

            VirtualFile[] ioFiles = ioFileQuery.findFirstIOFiles();

            FileEditorManager.getInstance(psiJavaFile.getProject()).openFile(ioFiles[1], true);
            FileEditorManager.getInstance(psiJavaFile.getProject()).openFile(ioFiles[0], true);

            ProjectView.getInstance(psiJavaFile.getProject()).select(null, ioFiles[0], true);
        });
    }

    private void tryToFocus(PsiJavaFile psiJavaFile){
        try{
            focus(psiJavaFile);
        }catch(IOFileNotFoundedException e){

        }
    }
}
