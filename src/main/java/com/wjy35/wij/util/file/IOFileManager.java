package com.wjy35.wij.util.file;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.wjy35.wij.util.crawling.BojCrawlResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOFileManager {
    public static final String INPUT_FILE_NAME = "input";
    public static final String OUTPUT_FILE_NAME = "output";
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
            try {
                VirtualFile inputFile;
                VirtualFile outputFile;

                int number = START_FILE_NUMBER;
                while(number<MAX_FILE_COUNT) {
                    inputFile = this.packageIoDirectory.findChild(INPUT_FILE_NAME + number);
                    outputFile = this.packageIoDirectory.findChild(OUTPUT_FILE_NAME + number);

                    if(inputFile != null) inputFile.delete(null);
                    if(outputFile != null) outputFile.delete(null);
                    number++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        List<String> inputNumberList = new ArrayList<>();

        for(Integer number = START_FILE_NUMBER; number < MAX_FILE_COUNT; number++) {
            if(this.packageIoDirectory.findChild(INPUT_FILE_NAME + number) == null) continue;
            if(this.packageIoDirectory.findChild(OUTPUT_FILE_NAME + number) == null) continue;

            inputNumberList.add(number.toString());
        }

        return inputNumberList;
    }

    public String getExpected(String fileNumber){
        StringBuilder sb = new StringBuilder();
        FileReader fileReader = null;
        BufferedReader fileBr = null;
        try {
            String path = this.packageIoDirectory.getPath()+File.separator+OUTPUT_FILE_NAME+fileNumber;
            fileReader = new FileReader(path);
            fileBr = new BufferedReader(fileReader);

            String expectedLine = null;
            while((expectedLine = fileBr.readLine()) != null){
                sb.append(expectedLine).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return sb.toString();
    }

    public VirtualFile getPackageIoDirectory() {
        return packageIoDirectory;
    }

    public String getInputPath(String fileNumber){
        return packageIoDirectory.getPath() + File.separator + INPUT_FILE_NAME + fileNumber;
    }

    //    private PsiElement getBojElement(PsiClass psiClass) {
//        final PsiElement[] ret = new PsiElement[1];
//
//        psiClass.accept(new PsiRecursiveElementWalkingVisitor() {
//            @Override
//            public void visitElement(@NotNull PsiElement element) {
//                super.visitElement(element);
//
//                if (element instanceof PsiComment && element.getText().contains("/* boj ")) {
//                    ret[0] = element;
//                    super.stopWalking();
//                }
//            }
//        });
//
//        return ret[0];
//    }
}
