package com.wjy35.wij.util.crawling;

public class BojCrawlResult {
    private int fileNumber;
    private String input;
    private String output;

    public BojCrawlResult(int fileNumber, String input, String output) {
        this.fileNumber = fileNumber;
        this.input = input;
        this.output = output;
    }

    public int getFileNumber() {
        return fileNumber;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }
}
