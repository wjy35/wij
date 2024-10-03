package com.wjy35.wij.run.judge.fetch;

import com.intellij.execution.ui.ConsoleView;
import com.wjy35.wij.run.judge.fetch.console.FetchConsoleWriter;
import com.wjy35.wij.run.judge.fetch.exception.ProblemNumberInputCanceledException;
import com.wjy35.wij.ui.dialog.judge.FetchDialog;
import com.wjy35.wij.util.file.IOFileCommand;

public class FetchStep {
    private final FetchConsoleWriter consoleWriter;
    private final Fetcher fetcher;

    public FetchStep(ConsoleView consoleView, IOFileCommand ioFileCommand) {
        this.consoleWriter = new FetchConsoleWriter(consoleView);
        this.fetcher = new FetcherImpl(ioFileCommand);
    }

    public void execute() {
        consoleWriter.writeStart();

        String problemNumber = FetchDialog.showAndGet();
        if (problemNumber == null) throw new ProblemNumberInputCanceledException();
        consoleWriter.writeProblemNumber(problemNumber);

        fetcher.crawlAndFetch(problemNumber);
        consoleWriter.writeSeparator();
    }
}
