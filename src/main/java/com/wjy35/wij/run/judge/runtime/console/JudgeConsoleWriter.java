package com.wjy35.wij.run.judge.runtime.console;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.wjy35.wij.run.judge.common.ConsoleWriter;
import com.wjy35.wij.run.judge.runtime.result.JudgeResult;
import com.wjy35.wij.run.judge.runtime.result.JudgeStatus;

public class JudgeConsoleWriter extends ConsoleWriter {

    public JudgeConsoleWriter(ConsoleView consoleView) {
        super(consoleView);
    }

    public void writeJudgeResult(JudgeResult judgeResult){
        if(judgeResult.getJudgeStatus()==JudgeStatus.ALL_ACCEPTED){
            consoleView.print(judgeResult+ "\n", ConsoleViewContentType.USER_INPUT);
        } else if (judgeResult.getJudgeStatus()==JudgeStatus.IO_FILE_NOT_EXIST) {
            consoleView.print("Input & Output is not founded \n",ConsoleViewContentType.LOG_INFO_OUTPUT);
        } else{
            consoleView.print(judgeResult + "\n", ConsoleViewContentType.ERROR_OUTPUT);
        }
    }

    public void writeIOFileNumber(String number){
        consoleView.print("#"+number+"\n", ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
    }

    public void writeActual(){
        consoleView.print("Actual:\n", ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
    }

    public void writeInput(String input){
        consoleView.print("Input:\n", ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
        consoleView.print(input+"\n",ConsoleViewContentType.NORMAL_OUTPUT);
    }

    public void writeExpected(String expected){
        consoleView.print("Expected:\n", ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
        consoleView.print(expected+"\n",ConsoleViewContentType.NORMAL_OUTPUT);
    }

    public void writeTimeLimitExceeded(){
        consoleView.print("Time Limit Exceeded\n", ConsoleViewContentType.ERROR_OUTPUT);
    }

    public void writeAccepted(){
        consoleView.print("\nResult: Accepted\n", ConsoleViewContentType.USER_INPUT);
    }

    public void writeWrongAnswer(){
        consoleView.print("\nResult: Wrong Answer\n", ConsoleViewContentType.ERROR_OUTPUT);
    }
}
