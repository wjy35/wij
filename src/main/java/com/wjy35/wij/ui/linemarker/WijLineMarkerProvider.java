package com.wjy35.wij.ui.linemarker;

import com.intellij.codeInsight.daemon.GutterIconNavigationHandler;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProvider;
import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.psi.*;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.util.FunctionUtil;
import com.wjy35.wij.run.judge.JudgeAction;
import com.wjy35.wij.ui.icon.Icons;
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
            DataContext dataContext = DataManager.getInstance().getDataContext(mouseEvent.getComponent());
            ActionGroup actionGroup = (ActionGroup) ActionManager.getInstance().getAction("com.wjy35.wij.run");

            ListPopup popup = JBPopupFactory.getInstance().createActionGroupPopup("WIJ Run",actionGroup,dataContext,null,false,null);
            popup.show(new RelativePoint(mouseEvent));
        }
    }
}
