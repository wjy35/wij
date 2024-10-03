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
//            consoleView.print("소스코드가 클립보드에 복사 되었습니다!\n", ConsoleViewContentType.NORMAL_OUTPUT);

        }else{
            consoleView.print(judgeResult + "\n", ConsoleViewContentType.ERROR_OUTPUT);
        }
    }

    public void writeIOFileNumber(String number){
        consoleView.print("#"+number+"\n", ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
    }

    public void writeActual(){
        consoleView.print("Actual:\n", ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
    }

    public void writeExpected(String expected){
        consoleView.print("\nExpected:\n", ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
        consoleView.print(expected+"\n",ConsoleViewContentType.NORMAL_OUTPUT);
    }

    public void writeTimeLimitExceeded(){
        consoleView.print("Time Limit Exceeded\n", ConsoleViewContentType.ERROR_OUTPUT);
    }

    public void writeAccepted(){
        consoleView.print("Result: Accepted\n", ConsoleViewContentType.USER_INPUT);
    }

    public void writeWrongAnswer(){
        consoleView.print("Result: Wrong Answer\n", ConsoleViewContentType.ERROR_OUTPUT);
    }
}
