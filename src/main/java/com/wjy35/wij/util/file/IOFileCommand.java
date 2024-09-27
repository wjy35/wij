package com.wjy35.wij.util.file;

import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.wjy35.wij.util.crawling.BOJCrawlResult;

import java.io.IOException;
import java.util.List;

public class IOFileCommand extends IOFileOperation{
    public IOFileCommand(Project project, String packageName) {
        super(project, packageName);
    }

    /* Delete */
    public void deleteDirectory(){
        try {
            tryToDeleteDirectory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void tryToDeleteDirectory() throws IOException {
        VirtualFile packageIODirectory = WIJDirectoryProvider.getInstance(project).getPackageIO(packageName);

        WriteAction.runAndWait(()->{
            if(packageName.isEmpty()){
                deleteAllIOFile();
                return;
            }

            if(packageIODirectory==null) return;
            packageIODirectory.delete(null);
        });
    }

    public void deleteAllIOFile(){
        VirtualFile packageIODirectory = WIJDirectoryProvider.getInstance(project).getPackageIO(packageName);

        if(packageIODirectory==null) return;

        for(VirtualFile child :packageIODirectory.getChildren()) deleteIOFile(child);
    }

    private void deleteIOFile(VirtualFile ioFile) {
        try {
            tryToDeleteIOFile(ioFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void tryToDeleteIOFile(VirtualFile file) throws IOException {
        WriteAction.runAndWait(()->{
            if(!isIOFile(file)) return;

            file.delete(null);
        });
    }

    /* Save */
    public void saveAllBy(List<BOJCrawlResult> crawlResultList) {
        for(BOJCrawlResult crawlResult : crawlResultList) saveBy(crawlResult);
    }

    private void saveBy(BOJCrawlResult crawlResult) {
        try{
            tryToSaveBy(crawlResult);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void tryToSaveBy(BOJCrawlResult crawlResult) throws IOException {
        VirtualFile packageIODirectory = WIJDirectoryProvider.getInstance(project).getOrCreatePackageIO(packageName);

        WriteAction.runAndWait(() -> {
            int fileNumber =  crawlResult.getNumber();

            VirtualFile inputFile = packageIODirectory.findChild(INPUT_FILE_NAME + fileNumber);
            VirtualFile outputFile = packageIODirectory.findChild(OUTPUT_FILE_NAME + fileNumber);

            if(inputFile!=null) inputFile.delete(null);
            if(outputFile!=null) outputFile.delete(null);

            inputFile = packageIODirectory.createChildData(null, INPUT_FILE_NAME + fileNumber);
            outputFile = packageIODirectory.createChildData(null,OUTPUT_FILE_NAME + fileNumber);

            VfsUtil.saveText(inputFile, crawlResult.getInput());
            VfsUtil.saveText(outputFile, crawlResult.getOutput());
        });
    }
}
