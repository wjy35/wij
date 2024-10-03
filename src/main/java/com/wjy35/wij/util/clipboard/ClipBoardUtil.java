package com.wjy35.wij.util.clipboard;

import com.intellij.openapi.application.ReadAction;
import com.intellij.psi.PsiJavaFile;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class ClipBoardUtil {

    public static void copy(PsiJavaFile psiJavaFile){
        String packageName = ReadAction.compute(psiJavaFile::getPackageName);
        String sourceCode = ReadAction.compute(psiJavaFile::getText);

        if(!packageName.isEmpty()) sourceCode =  sourceCode.replaceFirst("package "+packageName+";","");

        StringSelection stringSelection = new StringSelection(sourceCode.trim());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}
