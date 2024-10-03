package com.wjy35.wij.run.judge.fetch.console;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.wjy35.wij.run.judge.common.ConsoleWriter;

public class FetchConsoleWriter extends ConsoleWriter {
    public FetchConsoleWriter(ConsoleView consoleView) {
        super(consoleView);
    }

    public void writeStart(){
        consoleView.print("Fetch Step\n", ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
    }

    public void writeProblemNumber(String problemNumber){
        consoleView.print("Crawling : https://www.acmicpc.net/problem/"+problemNumber+"\n", ConsoleViewContentType.NORMAL_OUTPUT);
    }
}
