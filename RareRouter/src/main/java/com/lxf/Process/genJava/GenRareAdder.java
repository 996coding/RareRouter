package com.lxf.Process.genJava;

import java.io.File;
import java.net.URI;
import java.util.Set;

public class GenRareAdder {
    public static String CLASS_NAME = "RareAdder";
    public static String FILE_PATH;

    public static String CLASS_NAME_BACK_UP = "RareAdder_BackUp";
    public static String FILE_PATH_BACK_UP;

    public static void gen(Set<String> set, FilerGen filerGen) {
//        CLASS_NAME = CLASS_NAME + "_" + BaseProcessor.moduleName;
        URI uri = filerGen.genJavaClass(code(set, CLASS_NAME), CLASS_NAME);
        File file = new File(uri);
        FILE_PATH = file.getAbsolutePath();
    }

    public static void genBackUp(Set<String> set, FilerGen filerGen) {
//        CLASS_NAME = CLASS_NAME + "_" + BaseProcessor.moduleName;
        URI uri = filerGen.genJavaClass(code(set, CLASS_NAME_BACK_UP), CLASS_NAME_BACK_UP);
        File file = new File(uri);
        FILE_PATH_BACK_UP = file.getAbsolutePath();
    }

    private static String code(Set<String> set, String clsName) {
        StringBuilder sb = new StringBuilder();
        sb.append("package " + GenConfig.PACKAGE_JAVA_CODE + ";\n\n");
        sb.append("public class " + clsName + " {\n");
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
            sb.append("    public static int flag" + i + " = " + pkgName + ".flag;\n");
        }
        return sb.toString();
    }
}
