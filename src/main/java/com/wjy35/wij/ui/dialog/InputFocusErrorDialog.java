package com.wjy35.wij.ui.dialog;

import com.intellij.openapi.ui.Messages;

public class InputFocusErrorDialog {
    public static void show(){
        Messages.showErrorDialog("input & output 파일이 없습니다!","WIJ");
    }
}
