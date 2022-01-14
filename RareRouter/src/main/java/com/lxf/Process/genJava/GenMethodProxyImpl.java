package com.lxf.Process.genJava;

import com.lxf.Process.base.BaseProcessor;
import com.lxf.Process.base.Bean;

import java.util.Set;

public class GenMethodProxyImpl {
    public static String CLASS_NAME = "MethodProxyImpl";

    public static void gen(Set<Bean> set, FilerGen filerGen) {
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
        sb.append("import com.lxf.protocol.MethodProxy;\n\n");
        return sb.toString();
    }

    private static String class_head() {
        return "public class " +
                CLASS_NAME +
                " implements MethodProxy { \n\n";
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
        sb.append("    public Object proxy(Object instance, String annotationPath, Object... parameters) {\n");
        return sb.toString();
    }

    private static String method_tail() {
        StringBuilder sb = new StringBuilder();
        sb.append("        return null;\n");
        sb.append("    }\n\n");
        return sb.toString();
    }

    private static String method_body(Set<Bean> set) {
        if (set == null || set.size() == 0) {
            return "\n";
        }
        StringBuilder sb = new StringBuilder();
        for (Bean bean : set) {
            sb.append("        if (\"" + bean.path + "\".equals(annotationPath)) {\n");
            sb.append(create_sentence(bean));
            sb.append("        }\n");
        }
        return sb.toString();
    }


    private static String create_sentence(Bean bean) {
        StringBuilder sb = new StringBuilder();
        sb.append("            " + bean.pkgName + " proxyInstance = null;\n");
        sb.append("            if (instance != null && instance instanceof " + bean.pkgName + "){\n");
        sb.append("                proxyInstance = (" + bean.pkgName + ") instance;\n");
        sb.append("            }else {\n");
        sb.append("                proxyInstance = new " + bean.pkgName + "();\n");
        sb.append("            }\n");
        boolean voidReturn = "void".equals(bean.returnType.toLowerCase());
        if (voidReturn) {
            sb.append("            proxyInstance." + bean.method + "(");
        } else {
            sb.append("            return proxyInstance." + bean.method + "(");
        }
        for (int i = 0; i < bean.paramsList.size(); i++) {
            sb.append("(" + bean.paramsList.get(i) + ") parameters[" + i + "]");
            if (i < bean.paramsList.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(");\n");
        if (voidReturn){
            sb.append("            return null;\n");
        }
        return sb.toString();
    }
}
