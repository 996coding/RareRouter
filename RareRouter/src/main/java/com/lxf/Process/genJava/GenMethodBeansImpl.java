package com.lxf.Process.genJava;

import com.lxf.Process.base.Bean;

import java.util.Set;

public class GenMethodBeansImpl {

    public static String CLASS_NAME = "MethodBeansImpl";

    public static void gen(Set<Bean> askSet, Set<Bean> implSet, FilerGen filerGen) {
        CLASS_NAME = CLASS_NAME + System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append(class_import());
        sb.append(class_head());

        sb.append(method_head());
        sb.append(method_body(askSet));
        sb.append(method_tail());

        sb.append(method2_head());
        sb.append(method2_body(implSet));
        sb.append(method2_tail());

        sb.append(clazz_tail());
        filerGen.genJavaClass(sb.toString(), CLASS_NAME);
    }

    private static String class_import() {
        StringBuilder sb = new StringBuilder();
        sb.append("package " + GenConfig.PACKAGE + ";\n\n");
        sb.append("import com.lxf.init.RouteBean;\n");
        sb.append("import com.lxf.protocol.MethodBeans;\n\n");
        return sb.toString();
    }

    private static String class_head() {
        return "public class " +
                CLASS_NAME +
                " implements MethodBeans { \n\n";
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
        sb.append("    public RouteBean methodAskRouteBean(String pkgName) {\n");
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
            sb.append("        if (\"" + bean.pkgName + "\".equals(pkgName)) {\n");
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


    private static String method2_head() {
        StringBuilder sb = new StringBuilder();
        sb.append("    @Override\n");
        sb.append("    public RouteBean methodReplyRouteBean(String annotationPath) {\n");
        return sb.toString();
    }

    private static String method2_tail() {
        StringBuilder sb = new StringBuilder();
        sb.append("        return null;\n");
        sb.append("    }\n\n");
        return sb.toString();
    }

    private static String method2_body(Set<Bean> set) {
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
}
