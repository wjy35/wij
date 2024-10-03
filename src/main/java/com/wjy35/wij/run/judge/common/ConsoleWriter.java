package com.wjy35.wij.run.judge.common;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;

public class ConsoleWriter{
    protected final ConsoleView consoleView;
    private final String startAsciiArt =
                    " _    _                          ___               \n" +
                    "| |  | |                        |_  |              \n" +
                    "| |  | |  __ _  _ __    __ _      | | _   _  _ __  \n" +
                    "| |/\\| | / _` || '_ \\  / _` |     | || | | || '_ \\ \n" +
                    "\\  /\\  /| (_| || | | || (_| | /\\__/ /| |_| || | | |\n" +
                    " \\/  \\/  \\__,_||_| |_| \\__, | \\____/  \\__,_||_| |_|\n" +
                    "                        __/ |                      \n" +
                    "                       |___/                       \n";

    private final String SEPARATOR = "===================================================\n";


    public ConsoleWriter(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    public void writeSeparator(){
        consoleView.print(SEPARATOR, ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
    }

    public void writeAsciiArt(){
        consoleView.print(startAsciiArt, ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
    }

    public void writeProcessTerminated(){
        consoleView.print("is Terminated\n", ConsoleViewContentType.LOG_WARNING_OUTPUT);
        writeSeparator();
    }

    public void writeCopyToClipBoard(){
        consoleView.print("소스코드가 클립보드에 복사 되었습니다!\n", ConsoleViewContentType.NORMAL_OUTPUT);
    }
}
