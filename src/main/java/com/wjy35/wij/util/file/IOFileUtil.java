package com.wjy35.wij.util.file;

import com.intellij.openapi.vfs.VirtualFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOFileUtil {
    public static String getContent(VirtualFile ioFile){
        if(ioFile == null) return "";

        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(ioFile.getInputStream()));
            String expectedLine = null;
            while((expectedLine = br.readLine()) != null){
                sb.append(expectedLine).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
    }
}
