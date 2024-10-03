package com.wjy35.wij.ui.dialog.judge;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.Messages;

public class CompositeDialog {
    private static final String title = "WangJun Intellij Judge";

    private static void showMessage(String message) {
        ApplicationManager.getApplication().invokeAndWait(() -> {
            Messages.showErrorDialog(message, title);
        });
    }

    public static void showWrongProblemNumber() {
        showMessage("올바른 문제 번호를 입력해주세요!");
    }

    public static void showInternetConnectionError() {
        showMessage("인터넷 연결을 확인해주세요!");
    }

    public static void showFileDeleteDuringJudgeProcess(){ showMessage("실행 중 파일이 삭제 되었습니다.\n 다시 실행해주세요"); }
    public static void showTryLater(){
        showMessage("잠시후 다시 시도해주세요!");
    }
}
