package com.wjy35.wij.run.judge.runtime.result;

public class JudgeResult {
    private int acceptedCount;
    private final int totalCount;
    private JudgeStatus judgeStatus;

    public JudgeResult(int ioFileCount) {
        this.acceptedCount = 0;
        this.totalCount = ioFileCount;
    }

    public void increaseAcceptedCount(){
        acceptedCount++;
    }

    public JudgeStatus getJudgeStatus() {
        if(judgeStatus!=null) return judgeStatus;

        if(totalCount==0) this.judgeStatus = JudgeStatus.IO_FILE_NOT_EXIST;
        else if(acceptedCount==totalCount) judgeStatus = JudgeStatus.ALL_ACCEPTED;
        else judgeStatus = JudgeStatus.WRONG_ANSWER;

        return judgeStatus;
    }


    @Override
    public String toString() {
        return "Accepted: "+acceptedCount+", Total: "+totalCount;
    }
}
