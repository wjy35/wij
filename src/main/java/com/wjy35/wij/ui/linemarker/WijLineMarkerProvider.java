package com.wjy35.wij.ui.linemarker;

import com.intellij.codeInsight.daemon.GutterIconNavigationHandler;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProvider;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.psi.*;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.util.FunctionUtil;
import com.wjy35.wij.ui.icon.Icons;
import com.wjy35.wij.ui.popup.run.RunListPopup;
import org.jetbrains.annotations.NotNull;

import java.awt.event.MouseEvent;

public class WijLineMarkerProvider implements LineMarkerProvider {
    @Override
    public LineMarkerInfo<?> getLineMarkerInfo(@NotNull PsiElement element) {
        if(!isLeafMethod(element)) return null;
        if(!(isMainClass(element)||isMainMethod(element))) return null;

        return new LineMarkerInfo<>(
                element,
                element.getTextRange(),
                Icons.RUN,
                FunctionUtil.nullConstant() ,
                new BojLineMarkerClickHandler(),
                GutterIconRenderer.Alignment.CENTER,
                ()->null);
    }

    private boolean isLeafMethod(PsiElement element) {
        return element instanceof PsiIdentifier;
    }

    private boolean isMainClass(PsiElement element){
        return element.getParent() instanceof PsiClass && "Main".equals(((PsiClass)element.getParent()).getName());
    }

    private boolean isMainMethod(PsiElement element){
        return element.getParent() instanceof PsiMethod && "main".equals(((PsiMethod)element.getParent()).getName());
    }

    private static class BojLineMarkerClickHandler implements GutterIconNavigationHandler<PsiElement>{
        @Override
        public void navigate(MouseEvent mouseEvent, PsiElement psiElement) {
            PsiElement psiJavaFile = psiElement.getParent().getParent();
            if(!(psiJavaFile instanceof PsiJavaFile)) {
                psiJavaFile = psiJavaFile.getParent();
            }

            RunListPopup runListPopup = new RunListPopup((PsiJavaFile) psiJavaFile);
            ListPopup popup = JBPopupFactory.getInstance().createListPopup(runListPopup);
            popup.show(new RelativePoint(mouseEvent));
        }
    }
}
