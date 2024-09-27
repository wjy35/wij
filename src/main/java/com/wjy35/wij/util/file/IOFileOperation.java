package com.wjy35.wij.util.file;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public abstract class IOFileOperation {
    public static final String INPUT_FILE_NAME = "input";
    public static final String OUTPUT_FILE_NAME = "output";
    public static final String INPUT_FILE_REGULAR_EXPRESSION = "^" + INPUT_FILE_NAME +"[0-9]$";
    public static final String OUTPUT_FILE_REGULAR_EXPRESSION = "^" + OUTPUT_FILE_NAME +"[0-9]$";

    protected static final int MAX_FILE_COUNT = 10;
    protected static final int START_FILE_NUMBER = 0;

    protected final Project project;
    protected final String packageName;

    public IOFileOperation(Project project, String packageName) {
        this.project = project;
        this.packageName = packageName;
    }

    public boolean isIOFile(VirtualFile file){
        return isInputFile(file) || isOutputFile(file);
    }

    public boolean isInputFile(VirtualFile file){
        return file.getName().matches(INPUT_FILE_REGULAR_EXPRESSION);
    }

    public boolean isOutputFile(VirtualFile file){
        return file.getName().matches(OUTPUT_FILE_REGULAR_EXPRESSION);
    }

    protected int getInputFileNumber(VirtualFile file){
        return file.getName().charAt(INPUT_FILE_NAME.length())-'0';
    }

    protected int getOutputFileNumber(VirtualFile file){
        return file.getName().charAt(OUTPUT_FILE_NAME.length())-'0';
    }
}
