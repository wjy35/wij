package com.wjy35.wij.run.judge.process;

public class JudgeResult {
    private final int acceptedCount;
    private final int taskCount;

    public JudgeResult(int acceptedCount, int taskCount) {
        this.acceptedCount = acceptedCount;
        this.taskCount = taskCount;
    }

    public int getAcceptedCount() {
        return acceptedCount;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public boolean isAllAccepted() {
        return acceptedCount == taskCount;
    }

    @Override
    public String toString() {
        return "Accepted: "+acceptedCount+", Total: "+taskCount;
    }
}
