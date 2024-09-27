package com.wjy35.wij.util.crawling;

public class BOJCrawlResult {
    private final int number;
    private final String input;
    private final String output;

    public BOJCrawlResult(int number, String input, String output) {
        this.number = number;
        this.input = input;
        this.output = output;
    }

    public int getNumber() {
        return number;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }
}
