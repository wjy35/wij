package com.wjy35.wij.run.judge.fetch;

import com.wjy35.wij.run.judge.fetch.exception.ProblemNumberInputCanceledException;
import com.wjy35.wij.ui.dialog.judge.FetchDialog;
import com.wjy35.wij.util.file.IOFileCommand;

public class FetchStep {
    private final Fetcher fetcher;

    public FetchStep(IOFileCommand ioFileCommand) {
        this.fetcher = new FetcherImpl(ioFileCommand);
    }

    public void execute() {
        String problemNumber = FetchDialog.showAndGet();
        if (problemNumber == null) throw new ProblemNumberInputCanceledException();

        fetcher.crawlAndFetch(problemNumber);
    }
}
