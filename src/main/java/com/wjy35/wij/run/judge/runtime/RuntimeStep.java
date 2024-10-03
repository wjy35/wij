package com.wjy35.wij.run.judge.runtime;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.ui.ConsoleView;
import com.wjy35.wij.run.judge.runtime.process.JudgeProcessHandler;
import com.wjy35.wij.run.judge.runtime.commandline.CommandLineGenerator;
import com.wjy35.wij.run.judge.runtime.commandline.CommandLineGeneratorImpl;
import com.wjy35.wij.run.judge.runtime.commandline.JudgeCommandLine;
import com.wjy35.wij.run.judge.runtime.console.JudgeConsoleWriter;
import com.wjy35.wij.run.judge.runtime.exception.JudgeFailedException;
import com.wjy35.wij.run.judge.runtime.result.JudgeResult;
import com.wjy35.wij.run.judge.runtime.result.JudgeStatus;
import com.wjy35.wij.run.judge.runtime.validator.JudgeValidator;
import com.wjy35.wij.util.file.IOFileQuery;
import com.wjy35.wij.util.file.IOFileUtil;
import java.util.List;

public class RuntimeStep {
    private final ConsoleView consoleView;
    private final IOFileQuery ioFileQuery;
    private final String compilePath;
    private final String qualifiedName;
    private final JudgeConsoleWriter consoleWriter;

    private boolean isTerminated;
    private JudgeProcessHandler current;

    public RuntimeStep(ConsoleView consoleView, IOFileQuery ioFileQuery, String compilePath, String qualifiedName) {
        this.consoleView = consoleView;
        this.ioFileQuery = ioFileQuery;
        this.compilePath = compilePath;
        this.qualifiedName = qualifiedName;
        this.consoleWriter = new JudgeConsoleWriter(consoleView);

    }

    public JudgeStatus execute(){
        try{
            return tryToExecute();
        } catch (ExecutionException e) {
            throw new JudgeFailedException();
        }
    }

    private JudgeStatus tryToExecute() throws ExecutionException {
        CommandLineGenerator commandLineGenerator = new CommandLineGeneratorImpl(ioFileQuery,compilePath,qualifiedName);
        List<JudgeCommandLine> commandLineList = commandLineGenerator.createJudgeCommandLineListBy(ioFileQuery.findIOFileNumberList());

        JudgeResult judgeResult = new JudgeResult(commandLineList.size());
        for(JudgeCommandLine commandLine : commandLineList){
            if(isTerminated) break;

            current = new JudgeProcessHandler(commandLine);
            ProcessTerminatedListener.attach(current);
            consoleView.attachToProcess(current);
            consoleWriter.writeIOFileNumber(commandLine.getIoFileNumber());

            if(isTerminated) break;
            String input = IOFileUtil.getContent(ioFileQuery.findInputFileBy(commandLine.getIoFileNumber()));
            String expected = IOFileUtil.getContent(ioFileQuery.findOutputFileBy(commandLine.getIoFileNumber()));
            consoleWriter.writeInput(input);
            consoleWriter.writeExpected(expected);
            consoleWriter.writeActual();

            current.startNotify();
            if(current.isTimeLimitExceeded()){
                consoleWriter.writeTimeLimitExceeded();
                current.destroyProcess();
                continue;
            }

            if(isTerminated) break;

            String actual = current.getOutput();
            if(JudgeValidator.isAccepted(actual,expected)){
                consoleWriter.writeAccepted();
                judgeResult.increaseAcceptedCount();
            }else{
                consoleWriter.writeWrongAnswer();
            }

            consoleWriter.writeSeparator();
        }

        if(isTerminated){
            consoleWriter.writeProcessTerminated();
        }else {
            consoleWriter.writeJudgeResult(judgeResult);
            consoleWriter.writeSeparator();
        }

        return judgeResult.getJudgeStatus();
    }

    public void terminate() {
        isTerminated = true;
        if(current!=null) current.destroyProcess();
    }

    public boolean isNotTerminate(){
        return !isTerminated;
    }
}
