package com.wjy35.wij.run.delete;

import com.intellij.psi.PsiJavaFile;
import com.wjy35.wij.util.file.IOFileManager;

public class DeleteExecutor {
    IOFileManager iOFileManager;
    String packageName;

    public DeleteExecutor(PsiJavaFile psiJavaFile) {
        this.packageName = psiJavaFile.getPackageName();
        this.iOFileManager = new IOFileManager(psiJavaFile.getProject(),this.packageName);
    }

    public void start(){
        iOFileManager.deleteDirectory();
    }
}
