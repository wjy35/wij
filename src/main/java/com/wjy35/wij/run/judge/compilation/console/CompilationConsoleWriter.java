package com.wjy35.wij.run.judge.compilation.console;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.wjy35.wij.run.judge.common.ConsoleWriter;

public class CompilationConsoleWriter extends ConsoleWriter {
    public CompilationConsoleWriter(ConsoleView consoleView) {
        super(consoleView);
    }

    public void writeStart(){
        consoleView.print("Compilation Step\n", ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
    }
}
