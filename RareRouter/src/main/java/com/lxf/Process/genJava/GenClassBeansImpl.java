package com.lxf.Process.genJava;

import com.lxf.Process.base.Bean;

import java.util.Set;

public class GenClassBeansImpl {

    public static String CLASS_NAME = "ClassBeansImpl";

    public static void gen(Set<Bean> clsSet, FilerGen filerGen) {
        CLASS_NAME = CLASS_NAME + System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append(class_import());
        sb.append(class_head());
        sb.append(method_head());
        sb.append(method_body(clsSet));
        sb.append(method_tail());
        sb.append(clazz_tail());
        filerGen.genJavaClass(sb.toString(), CLASS_NAME);
    }

    private static String class_import() {
        StringBuilder sb = new StringBuilder();
        sb.append("package " + GenConfig.PACKAGE + ";\n\n");
        sb.append("import com.lxf.init.RouteBean;\n");
        sb.append("import com.lxf.protocol.ClassBeans;\n\n");
        return sb.toString();
    }

    private static String class_head() {
        return "public class " +
                CLASS_NAME +
                " implements ClassBeans { \n\n";
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
        sb.append("    public RouteBean classRouteBean(String annotationPath) {\n");
        return sb.toString();
    }

    private static String method_tail() {
        StringBuilder sb = new StringBuilder();
        sb.append("        return null;\n");
        sb.append("    }\n");
        return sb.toString();
    }

    private static String method_body(Set<Bean> clsSet) {
        if (clsSet == null || clsSet.size() == 0) {
            return "\n";
        }
        StringBuilder sb = new StringBuilder();

        for (Bean bean : clsSet) {
            sb.append("        if (\"" + bean.path + "\".equals(annotationPath)) {\n");
            sb.append(create_sentence(bean));
            sb.append("        }\n");
        }
        return sb.toString();
    }

    private static String create_sentence(Bean bean) {
        StringBuilder sb = new StringBuilder();
        sb.append("            return ");
        sb.append("RouteBean.create(" +
                "\"" + bean.type + "\", " +
                "\"" + bean.isInterface + "\", " +
                "\"" + bean.path + "\", " +
                "\"" + bean.pkgName + "\", \"" +
                bean.method + "\", \"" +
                bean.returnType + "\"");
        for (int i = 0; i < bean.paramsList.size(); i++) {
            sb.append(", \"" + bean.paramsList.get(i) + "\"");
        }
        sb.append(");\n");
        return sb.toString();
    }
}