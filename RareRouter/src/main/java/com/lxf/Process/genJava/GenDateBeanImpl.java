package com.lxf.Process.genJava;

import com.lxf.Process.base.BaseProcessor;

import java.util.Set;

public class GenDateBeanImpl {
    public static String CLASS_NAME = "DateBeanImpl";

    public static void gen(Set<String> set, FilerGen filerGen) {
        CLASS_NAME = CLASS_NAME + "_" + BaseProcessor.moduleName;

        StringBuilder sb = new StringBuilder();
        sb.append(class_import());
        sb.append(class_head());

        sb.append(method_head());
        sb.append(method_body(set));
        sb.append(method_tail());

        sb.append(clazz_tail());
        filerGen.genJavaClass(sb.toString(), CLASS_NAME);
    }


    private static String class_import() {
        StringBuilder sb = new StringBuilder();
        sb.append("package " + GenConfig.PACKAGE + ";\n\n");
        sb.append("import com.lxf.protocol.DateBean;\n\n");
        return sb.toString();
    }

    private static String class_head() {
        return "public class " +
                CLASS_NAME +
                " implements DateBean { \n\n";
    }

    private static String clazz_tail() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("}");
        return sb.toString();
    }

    private static String method_head() {
        StringBuilder sb = new StringBuilder();
        sb.append("    @Override\n");
        sb.append("    public Object getDateBean(String pkgFullName) {\n");
        return sb.toString();
    }

    private static String method_tail() {
        StringBuilder sb = new StringBuilder();
        sb.append("        return null;\n");
        sb.append("    }\n\n");
        return sb.toString();
    }

    private static String method_body(Set<String> set) {
        if (set == null || set.size() == 0) {
            return "\n";
        }
        StringBuilder sb = new StringBuilder();
        for (String pkgName : set) {
            sb.append("        if (\"" + pkgName + "\".equals(pkgFullName)){\n");
            sb.append("            return new " + pkgName + "();\n");
            sb.append("        }\n");
        }
        return sb.toString();
    }

}
