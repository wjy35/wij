package com.wjy35.wij.ui.dialog;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.Messages;

import java.io.IOException;
import java.net.UnknownHostException;

public class JudgeErrorDialog {
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

    public static void showTryLater(){
        showMessage("잠시후 다시 시도해주세요!");
    }
}
