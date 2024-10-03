package com.wjy35.wij.run.judge.runtime.commandline;

import com.wjy35.wij.util.file.IOFileQuery;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommandLineGeneratorImpl implements CommandLineGenerator {
    private final IOFileQuery ioFileQuery;
    private final String compilePath;
    private final String qualifiedName;

    public CommandLineGeneratorImpl(IOFileQuery ioFileQuery, String compilePath, String qualifiedName) {
        this.ioFileQuery = ioFileQuery;
        this.compilePath = compilePath;
        this.qualifiedName = qualifiedName;
    }

    @Override
    public List<JudgeCommandLine> createJudgeCommandLineListBy(List<String> ioFileNumberList) {
        List<JudgeCommandLine> judgeCommandLineList = new ArrayList<>();


        for(String ioFileNumber : ioFileNumberList){
            JudgeCommandLine commandLine = new JudgeCommandLine(ioFileNumber,"java","-Dfile.encoding=UTF-8","-cp",compilePath,qualifiedName);
            commandLine.withInput(new File(ioFileQuery.fileNumberToInputFilePath(ioFileNumber)));

            judgeCommandLineList.add(commandLine);
        }

        return judgeCommandLineList;
    }
}
