package com.lxf.Process.genJava;

import com.lxf.Process.base.BaseProcessor;

import java.io.File;
import java.net.URI;
import java.util.Set;

public class GenRareAdder {
    public static String CLASS_NAME = "RareAdder";
    public static String FILE_PATH;

    public static void gen(Set<String> set, FilerGen filerGen) {
//        CLASS_NAME = CLASS_NAME + "_" + BaseProcessor.moduleName;
        URI uri = filerGen.genJavaClass(code(set).toString(), CLASS_NAME);
        File file = new File(uri);
        FILE_PATH = file.getAbsolutePath();
    }

    private static String code(Set<String> set) {
        StringBuilder sb = new StringBuilder();
        sb.append("package " + GenConfig.PACKAGE_JAVA_CODE + ";\n\n");
        sb.append("public class " + CLASS_NAME + " {\n");
        sb.append("    public static boolean enable = true;\n");
        sb.append(class_field(set));
        sb.append("}\n");
        return sb.toString();
    }

    private static String class_field(Set<String> set) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String pkgName : set) {
            i++;
            sb.append("    public Class<?> cls" + i + " = " + pkgName + ".class;\n");
        }
        return sb.toString();
    }
}
