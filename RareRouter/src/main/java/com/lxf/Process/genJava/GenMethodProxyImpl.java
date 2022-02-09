package com.lxf.Process.genJava;

import com.lxf.Process.base.BaseProcessor;
import com.lxf.Process.base.Bean;

import java.util.Map;
import java.util.Set;

public class GenMethodProxyImpl {
    public static String CLASS_NAME = "MethodProxyImpl";

    public static void gen(Map<String, Bean> methodsImplProxy, FilerGen filerGen) {
        CLASS_NAME = CLASS_NAME + "_" + BaseProcessor.moduleName;

        StringBuilder sb = new StringBuilder();
        sb.append(class_import());
        sb.append(class_head());

        sb.append(method_head());
        sb.append(method_body(methodsImplProxy));
        sb.append(method_tail());

        sb.append(clazz_tail());
        filerGen.genJavaClass(sb.toString(), CLASS_NAME);
    }

    private static String class_import() {
        StringBuilder sb = new StringBuilder();
        sb.append("package " + GenConfig.PACKAGE + ";\n\n");
        sb.append("import com.lxf.protocol.*;\n\n");
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
        sb.append("    public MethodExecutor proxy(String annotationPath) {\n");
        return sb.toString();
    }

    private static String method_tail() {
        StringBuilder sb = new StringBuilder();
        sb.append("        return null;\n");
        sb.append("    }\n\n");
        return sb.toString();
    }

    private static String method_body(Map<String, Bean> methodsImplProxy) {
        if (methodsImplProxy == null || methodsImplProxy.size() == 0) {
            return "\n";
        }
        StringBuilder sb = new StringBuilder();
        for (String clazz : methodsImplProxy.keySet()) {
            Bean bean = methodsImplProxy.get(clazz);
            sb.append("        if (\"" + bean.path + "\".equals(annotationPath)) {\n");
            sb.append("            return new " + clazz + "();\n");
            sb.append("        }\n");
        }
        return sb.toString();
    }
}
