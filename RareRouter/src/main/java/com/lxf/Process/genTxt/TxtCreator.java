package com.lxf.Process.genTxt;

import java.io.File;
import java.io.IOException;

public class TxtCreator {
    public static void createFileIfNone(String txtPath) {
        File file = new File(txtPath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
            }
        }
    }
    public static void createDir(String dirPath){
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
