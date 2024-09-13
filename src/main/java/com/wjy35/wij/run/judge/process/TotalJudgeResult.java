package com.wjy35.wij.run.judge.process;

public class TotalJudgeResult {
    private int acceptedCount;
    private final int taskCount;

    public TotalJudgeResult(int taskCount) {
        this.acceptedCount = 0;
        this.taskCount = taskCount;
    }

    public void increaseAcceptedCount(){
        acceptedCount++;
    }

    public boolean isAllAccepted() {
        return acceptedCount == taskCount;
    }

    @Override
    public String toString() {
        return "Accepted: "+acceptedCount+", Total: "+taskCount;
    }
}
