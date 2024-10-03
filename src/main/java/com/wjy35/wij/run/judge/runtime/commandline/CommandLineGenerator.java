package com.wjy35.wij.run.judge.runtime.commandline;

import java.util.List;

public interface CommandLineGenerator {
    List<JudgeCommandLine> createJudgeCommandLineListBy(List<String> ioFileNumberList);
}
