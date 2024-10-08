package com.wjy35.wij.util.file;

import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.wjy35.wij.util.file.exception.IOFileNotFoundedException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IOFileQuery extends IOFileOperation{
    public IOFileQuery(Project project, String packageName) {
        super(project, packageName);
    }

    public VirtualFile[] findFirstIOFiles(){
        VirtualFile packageIODirectory = WIJDirectoryProvider.getInstance(project).getOrCreatePackageIO(packageName);

        return ReadAction.compute(()->{
            VirtualFile[] existInput = new VirtualFile[MAX_FILE_COUNT];
            VirtualFile[] existOutput = new VirtualFile[MAX_FILE_COUNT];

            for(VirtualFile child : packageIODirectory.getChildren()){
                if(isInputFile(child)){
                    int fileNumber = getInputFileNumber(child);
                    if(existOutput[fileNumber]!=null) return new VirtualFile[]{child,existInput[fileNumber]};

                    existInput[fileNumber] = child;
                }

                if(isOutputFile(child)){
                    int fileNumber = getOutputFileNumber(child);
                    if(existInput[fileNumber]!=null) return new VirtualFile[]{existInput[fileNumber],child};

                    existOutput[fileNumber] = child;
                }
            }

            throw new IOFileNotFoundedException();
        });
    }

    public List<String> findIOFileNumberList() {
        VirtualFile packageIODirectory = WIJDirectoryProvider.getInstance(project).getOrCreatePackageIO(packageName);

        return ReadAction.compute(()->{
            boolean[] existInputNumber = new boolean[MAX_FILE_COUNT];
            boolean[] existOutputNumber = new boolean[MAX_FILE_COUNT];

            for(VirtualFile child : packageIODirectory.getChildren()){
                if(isInputFile(child)) existInputNumber[getInputFileNumber(child)] = true;
                if(isOutputFile(child)) existOutputNumber[getOutputFileNumber(child)] = true;
            }

            List<String> ioFileNumberList = new ArrayList<>();
            for(int number=START_FILE_NUMBER; number <MAX_FILE_COUNT; number++){
                if(existInputNumber[number] && existOutputNumber[number]) ioFileNumberList.add(Integer.toString(number));
            }

            return ioFileNumberList;
        });
    }

    public VirtualFile findOutputFileBy(String ioFileNumber){
        VirtualFile packageIODirectory = WIJDirectoryProvider.getInstance(project).getOrCreatePackageIO(packageName);

        return ReadAction.compute(()-> packageIODirectory.findChild(OUTPUT_FILE_NAME+ioFileNumber));
    }

    public VirtualFile findInputFileBy(String ioFileNumber){
        VirtualFile packageIODirectory = WIJDirectoryProvider.getInstance(project).getOrCreatePackageIO(packageName);

        return ReadAction.compute(()-> packageIODirectory.findChild(INPUT_FILE_NAME+ioFileNumber));
    }


    public String fileNumberToInputFilePath(String ioFileNumber){
        String ioDirectoryPath = WIJDirectoryProvider.getInstance(project).getOrCreatePackageIO(packageName).getPath();

        return ioDirectoryPath + File.separator + INPUT_FILE_NAME + ioFileNumber;
    }
}
