package com.wjy35.wij.ui.popup.run;

import com.intellij.openapi.ui.popup.*;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.psi.PsiJavaFile;
import com.wjy35.wij.ui.popup.run.item.DeleteAllPopupItem;
import com.wjy35.wij.ui.popup.run.item.JudgePopupItem;
import com.wjy35.wij.ui.popup.run.item.RunListPopupItem;
import com.wjy35.wij.ui.popup.run.item.UpdateAndJudgePopupItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class RunListPopup implements ListPopupStep<RunListPopupItem> {
    private PsiJavaFile psiJavaFile;

    @Override
    public @NotNull List<RunListPopupItem> getValues() {
        List<RunListPopupItem> list = new ArrayList<>();
        list.add(new JudgePopupItem(this.psiJavaFile));
        list.add(new UpdateAndJudgePopupItem(this.psiJavaFile));
        list.add(new DeleteAllPopupItem(this.psiJavaFile));

        return list;
    }

    @Override
    public boolean isSelectable(RunListPopupItem value) {
        return true;
    }

    @Override
    public @Nullable Icon getIconFor(RunListPopupItem value) {
        return value.getIcon();
    }

    @Override
    public @NlsContexts.ListItem @NotNull String getTextFor(RunListPopupItem value) {
        return value.getName();
    }

    @Override
    public @Nullable ListSeparator getSeparatorAbove(RunListPopupItem value) {
        if(value instanceof DeleteAllPopupItem) return new ListSeparator();

        return null;
    }

    @Override
    public int getDefaultOptionIndex() {
        return 0;
    }

    @Override
    public @NlsContexts.PopupTitle @Nullable String getTitle() {
        return "BaekJoon RunConfiguration";
    }

    @Override
    public @Nullable PopupStep<?> onChosen(RunListPopupItem selectedValue, boolean finalChoice) {
        selectedValue.onClick();

        return FINAL_CHOICE;
    }

    @Override
    public boolean hasSubstep(RunListPopupItem selectedValue) {
        return false;
    }

    @Override
    public void canceled() {}

    @Override
    public boolean isMnemonicsNavigationEnabled() {
        return false;
    }

    @Override
    public @Nullable MnemonicNavigationFilter<RunListPopupItem> getMnemonicNavigationFilter() {
        return null;
    }

    @Override
    public boolean isSpeedSearchEnabled() {
        return false;
    }

    @Override
    public @Nullable SpeedSearchFilter<RunListPopupItem> getSpeedSearchFilter() {
        return null;
    }

    @Override
    public boolean isAutoSelectionEnabled() {
        return false;
    }

    @Override
    public @Nullable Runnable getFinalRunnable() {
        return null;
    }

    public RunListPopup(PsiJavaFile psiJavaFile) {
        this.psiJavaFile = psiJavaFile;
    }
}
