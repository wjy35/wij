package com.wjy35.wij.ui.dialog;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.Messages;

public class FileDeletedDuringJudgeExceptionDialog {
    public static void show(){
        ApplicationManager.getApplication().invokeAndWait(() -> {
            Messages.showErrorDialog("실행 중 파일이 삭제 되었습니다.\n 다시 실행해주세요", "WIJ");
        });
    }
}
