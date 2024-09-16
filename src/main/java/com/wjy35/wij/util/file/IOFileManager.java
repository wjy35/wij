package com.wjy35.wij.util.file;

import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.wjy35.wij.util.crawling.BojCrawlResult;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOFileManager {
    public static final String INPUT_FILE_NAME = "input";
    public static final String OUTPUT_FILE_NAME = "output";
    public static final String BOJ_FILE_LEGULAR_EXPRESSION = "^(" + INPUT_FILE_NAME + "|" + OUTPUT_FILE_NAME+ ")[0-9]$";
    public static final int MAX_FILE_COUNT = 10;
    public static final int START_FILE_NUMBER = 0;

    private final Project project;
    private final VirtualFile packageIoDirectory;
    public IOFileManager(Project project, String packageName) {
        this.project = project;
        this.packageIoDirectory = WijDirectoryManager.getInstance(project)
                .getOrMakePackageIoDirectory(packageName);
    }

    public void deleteDirectory(){
        WriteCommandAction.runWriteCommandAction(project, () -> {
            try {
                if(WijDirectoryManager.IO_FILE_DIRECTORY_NAME.equals(this.packageIoDirectory.getName())){
                    deleteFiles();
                    return;
                }

                this.packageIoDirectory.delete(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void deleteFiles(){
        WriteCommandAction.runWriteCommandAction(project, () -> {
            for(VirtualFile child : this.packageIoDirectory.getChildren()) deleteFile(child);
        });
    }

    private void deleteFile(VirtualFile file){
        if(file.getName().matches(BOJ_FILE_LEGULAR_EXPRESSION)) {
            try {
                file.delete(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public VirtualFile findFirstFile(){
        return ReadAction.compute(()->{
            VirtualFile firstFile = null;
            for(VirtualFile child :this.packageIoDirectory.getChildren()){
                if(child.getName().matches(BOJ_FILE_LEGULAR_EXPRESSION)) {
                    firstFile = child;
                    break;
                }
            }

            return firstFile;
        });
    }

    public void saveAll(List<BojCrawlResult> crawlResultList) {
        for(BojCrawlResult crawlResult : crawlResultList) save(crawlResult);
    }

    private void save(BojCrawlResult crawlResult) {
        WriteCommandAction.runWriteCommandAction(project, () -> {
            int fileNumber =  crawlResult.getFileNumber();
            VirtualFile inputFile = this.packageIoDirectory.findChild(INPUT_FILE_NAME + fileNumber);
            VirtualFile outputFile = this.packageIoDirectory.findChild(OUTPUT_FILE_NAME + fileNumber);
            try {
                if(inputFile!=null) inputFile.delete(null);
                if(outputFile!=null) outputFile.delete(null);

                inputFile = this.packageIoDirectory.createChildData(null, INPUT_FILE_NAME + fileNumber);
                outputFile = this.packageIoDirectory.createChildData(null,OUTPUT_FILE_NAME + fileNumber);

                VfsUtil.saveText(inputFile, crawlResult.getInput());
                VfsUtil.saveText(outputFile, crawlResult.getOutput());
            } catch (IOException e) {
                    throw new RuntimeException(e);
            }
        });
    }

    public List<String> getInputNumberList() {
        return ReadAction.compute(()->{
            List<String> inputNumberList = new ArrayList<>();

            for(int number = START_FILE_NUMBER; number < MAX_FILE_COUNT; number++) {

                if(this.packageIoDirectory.findChild(INPUT_FILE_NAME + number) == null) continue;
                if(this.packageIoDirectory.findChild(OUTPUT_FILE_NAME + number) == null) continue;

                inputNumberList.add(Integer.toString(number));
            }

            return inputNumberList;
        });
    }

    public String getExpected(String fileNumber){
        return ReadAction.compute(()->{
            VirtualFile outputFile = this.packageIoDirectory.findChild(OUTPUT_FILE_NAME+fileNumber);
            if(outputFile == null) return "";

            StringBuilder sb = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(outputFile.getInputStream()));
                String expectedLine = null;
                while((expectedLine = br.readLine()) != null){
                    sb.append(expectedLine).append("\n");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return sb.toString();
        });
    }

    public VirtualFile getPackageIoDirectory() {
        return packageIoDirectory;
    }

    public String getInputPath(String fileNumber){
        return packageIoDirectory.getPath() + File.separator + INPUT_FILE_NAME + fileNumber;
    }
}
