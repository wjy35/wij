package com.wjy35.wij.run.judge.console;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.wjy35.wij.run.judge.process.TotalJudgeResult;

public class JudgeConsolePrinter {
    private final String startAsciiArt =
            " _    _                          ___               \n" +
            "| |  | |                        |_  |              \n" +
            "| |  | |  __ _  _ __    __ _      | | _   _  _ __  \n" +
            "| |/\\| | / _` || '_ \\  / _` |     | || | | || '_ \\ \n" +
            "\\  /\\  /| (_| || | | || (_| | /\\__/ /| |_| || | | |\n" +
            " \\/  \\/  \\__,_||_| |_| \\__, | \\____/  \\__,_||_| |_|\n" +
            "                        __/ |                      \n" +
            "                       |___/                       \n";

    private final String separator = "===================================================\n";

    public void printStartMessage(){
        consoleView.print(startAsciiArt, ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
    }

    public void printSeparator(){
        consoleView.print(separator, ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
    }

    public void printProcessCanceledMessage(){
        consoleView.print("is Canceled", ConsoleViewContentType.LOG_WARNING_OUTPUT);
    }

    public void printTestNumberMessage(String number){
        consoleView.print("#"+number+"\n", ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
    }

    public void printActual(){
        consoleView.print("Actual:\n", ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
    }

    public void printExpected(String expected){
        consoleView.print("\nExpected:\n", ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
        consoleView.print(expected+"\n",ConsoleViewContentType.NORMAL_OUTPUT);
    }

    public void printTimeLimitExceededMessage(){
        consoleView.print("Time Limit Exceeded\n", ConsoleViewContentType.ERROR_OUTPUT);
    }

    public void printAcceptedMessage(){
        consoleView.print("Result: Accepted\n", ConsoleViewContentType.USER_INPUT);
    }

    public void printWrongAnswerMessage(){
        consoleView.print("Result: Wrong Answer\n", ConsoleViewContentType.ERROR_OUTPUT);
    }

    public void printTotalJudgeResult(TotalJudgeResult totalJudgeResult){
        if(totalJudgeResult.isAllAccepted()){
            consoleView.print(totalJudgeResult+ "\n", ConsoleViewContentType.USER_INPUT);
            consoleView.print("소스코드가 클립보드에 복사 되었습니다!\n", ConsoleViewContentType.NORMAL_OUTPUT);
        }else{
            consoleView.print(totalJudgeResult + "\n", ConsoleViewContentType.ERROR_OUTPUT);
        }
    }

    private final ConsoleView consoleView;

    public JudgeConsolePrinter(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }
}
