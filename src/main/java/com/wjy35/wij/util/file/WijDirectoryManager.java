package com.wjy35.wij.util.file;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class WijDirectoryManager {
    private final Project project;
    private final VirtualFile compileDirectory;
    private final VirtualFile ioDirectory;


    public static final String COMPILE_DIRECTORY_NAME = "class";
    public static final String IO_FILE_DIRECTORY_NAME = "io";
    public static final String WIJ_DIRECTORY_NAME = "wij";

    private WijDirectoryManager(Project project) {
        this.project = project;
        initWijDirectory();

        String basePath = project.getBasePath();
        String compileDirectoryPath = basePath + File.separator + WIJ_DIRECTORY_NAME + File.separator + COMPILE_DIRECTORY_NAME;
        String ioDirectoryPath = basePath + File.separator + WIJ_DIRECTORY_NAME + File.separator + IO_FILE_DIRECTORY_NAME;

        this.compileDirectory = LocalFileSystem.getInstance().findFileByPath(compileDirectoryPath);
        this.ioDirectory = LocalFileSystem.getInstance().findFileByPath(ioDirectoryPath);
    }

    public VirtualFile getCompileDirectory() {
        return compileDirectory;
    }

    public VirtualFile getIoDirectory() {
        return ioDirectory;
    }

    public VirtualFile getOrMakePackageIoDirectory(String packageName) {
        if(packageName.isEmpty()) return this.ioDirectory;
        return makeDirectory(ioDirectory,packageName);
    }

    private void initWijDirectory(){
        VirtualFile projectBaseDir = LocalFileSystem.getInstance()
                .findFileByPath(this.project.getBasePath());

        VirtualFile wijDirectory = makeDirectory(projectBaseDir, WIJ_DIRECTORY_NAME);
        makeDirectory(wijDirectory, COMPILE_DIRECTORY_NAME);
        makeDirectory(wijDirectory, IO_FILE_DIRECTORY_NAME);
    }

    private VirtualFile makeDirectory(VirtualFile baseDirectory, String name) {
        AtomicReference<VirtualFile> newDirectory = new AtomicReference<>();
        WriteCommandAction.runWriteCommandAction(project, ()->{
            try {
                newDirectory.set(baseDirectory.findChild(name));
                if(newDirectory.get() != null) return;

                newDirectory.set(baseDirectory.createChildDirectory(null, name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return newDirectory.get();
    }

    private static WijDirectoryManager instance;
    public static WijDirectoryManager getInstance(Project project) {
        if(instance != null) return instance;

        return new WijDirectoryManager(project);
    }
}
