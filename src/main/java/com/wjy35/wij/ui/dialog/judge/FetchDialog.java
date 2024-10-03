package com.wjy35.wij.ui.dialog.judge;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.wjy35.wij.ui.icon.Icons;
import java.util.concurrent.atomic.AtomicReference;


public class FetchDialog {
    public static String showAndGet(){
        AtomicReference<String> problemNumber = new AtomicReference<>();

        ApplicationManager.getApplication().invokeAndWait(() -> {
            problemNumber.set(Messages.showInputDialog(
                    "문제 번호를 입력하세요 (예: 1000, 12325)",
                    "WangJun Intellij Judge",
                    Icons.RUN,
                    "",
                    validator
            ));
        });

        return problemNumber.get();
    }

    private static final InputValidator validator = new InputValidator() {
        @Override
        public boolean checkInput(String inputString) {
            if(inputString==null) return false;
            if(inputString.trim().isEmpty()) return false;

            try{
                Integer.parseInt(inputString);
            }catch (NumberFormatException e){
                return false;
            }
            return true;
        }

        @Override
        public boolean canClose(String inputString) {
            return checkInput(inputString);
        }
    };
}
