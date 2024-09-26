package com.wjy35.wij.util.file;

import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import java.io.IOException;

public class WIJDirectoryProvider {
    private final Project project;

    public static final String COMPILE_DIRECTORY_NAME = "class";
    public static final String IO_FILE_DIRECTORY_NAME = "io";
    public static final String WIJ_DIRECTORY_NAME = "wij";

    private WIJDirectoryProvider(Project project) {
        this.project = project;
    }

    private VirtualFile getOrMakeDirectoryWIJ() {
        VirtualFile rootDirectory = LocalFileSystem.getInstance().findFileByPath(this.project.getBasePath());

        return getOrCreateDirectory(rootDirectory, WIJ_DIRECTORY_NAME);
    }

    public VirtualFile getOrCreateCompile() {
        return getOrCreateDirectory(getOrMakeDirectoryWIJ(), COMPILE_DIRECTORY_NAME);
    }

    public VirtualFile getOrCreateIO() {
        return getOrCreateDirectory(getOrMakeDirectoryWIJ(), IO_FILE_DIRECTORY_NAME);
    }

    public VirtualFile getOrCreatePackageIO(String packageName) {
        if(packageName.isEmpty()) return getOrCreateIO();

        return getOrCreateDirectory(getOrCreateIO(),packageName);
    }

    private VirtualFile getOrCreateDirectory(VirtualFile baseDirectory, String name){
        try{
            return tryToGetOrCreateDirectory(baseDirectory, name);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private VirtualFile tryToGetOrCreateDirectory(VirtualFile baseDirectory, String name) throws IOException {
        return WriteAction.computeAndWait(()->{
            VirtualFile targetDirectory = baseDirectory.findChild(name);

            if(targetDirectory != null) return targetDirectory;
            return baseDirectory.createChildDirectory(null, name);
        });
    }

    private static WIJDirectoryProvider INSTANCE;
    public static WIJDirectoryProvider getInstance(Project project) {
        if(INSTANCE != null) return INSTANCE;

        return INSTANCE = new WIJDirectoryProvider(project);
    }
}
