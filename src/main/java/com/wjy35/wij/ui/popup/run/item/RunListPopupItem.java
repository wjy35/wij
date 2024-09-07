package com.wjy35.wij.ui.popup.run.item;

import com.intellij.psi.PsiJavaFile;
import javax.swing.*;

public abstract class RunListPopupItem {
    private String name;
    private Icon icon;
    private PsiJavaFile psiJavaFile;

    public RunListPopupItem(String name, Icon icon, PsiJavaFile psiJavaFile) {
        this.name = name;
        this.icon = icon;
        this.psiJavaFile = psiJavaFile;
    }

    public String getName() {
        return name;
    }

    public Icon getIcon() {
        return icon;
    }

    public PsiJavaFile getPsiJavaFile() {
        return psiJavaFile;
    }

    public abstract void onClick();
}
