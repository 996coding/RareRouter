package com.lxf.Process.genJava;

import java.io.IOException;
import java.io.Writer;

import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;

public class FilerGen {
    private String pkgName = GenConfig.PACKAGE + ".";
    private Filer filer;

    public FilerGen(Filer filer) {
        this.filer = filer;
    }

    public java.net.URI genJavaClass(String clazzStr, String className) {
        Writer writer = null;
        java.net.URI uri = null;
        try {
            JavaFileObject sourceFile = filer.createSourceFile(pkgName + className);
            writer = sourceFile.openWriter();
            writer.write(clazzStr);
//            javaPath = sourceFile.toUri().getPath().toString();
            uri = sourceFile.toUri();
        } catch (Exception e) {

        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return uri;
    }
}
