package com.wjy35.wij.ui.popup.run.item;

import com.intellij.psi.PsiJavaFile;
import com.wjy35.wij.run.delete.DeleteExecutor;
import com.wjy35.wij.ui.icon.Icons;

public class DeleteAllPopupItem extends RunListPopupItem {
    public DeleteAllPopupItem(PsiJavaFile psiJavaFile) {
        super("Delete All Input & Output", Icons.DELETE,psiJavaFile);
    }

    @Override
    public void onClick() {
        new DeleteExecutor(getPsiJavaFile()).start();
    }
}
