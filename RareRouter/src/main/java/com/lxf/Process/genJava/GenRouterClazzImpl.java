package com.lxf.Process.genJava;

import com.lxf.Process.base.Bean;

import java.util.HashSet;
import java.util.Set;

public class GenRouterClazzImpl {

    public static String CLASS_NAME = "RouterClazzImpl";

    public static void gen(Set<Bean> set, FilerGen filerGen) {
        CLASS_NAME = CLASS_NAME + System.currentTimeMillis();
        Set<Bean> clsSet = getClassSet(set);

        StringBuilder sb = new StringBuilder();
        sb.append(class_import());
        sb.append(class_head());

        sb.append(method_head());
        sb.append(method_body(clsSet));
        sb.append(method_tail());

        sb.append(clazz_tail());
        filerGen.genJavaClass(sb.toString(), CLASS_NAME);
    }

    private static Set<Bean> getClassSet(Set<Bean> set) {
        Set<Bean> hashSet = new HashSet<>();
        if (set == null || set.size() == 0) {
            return hashSet;
        }
        //确保为class类型的bean
        for (Bean bean : set) {
            if (bean.type.equals("0")) {
                hashSet.add(bean);
            }
        }
        return hashSet;
    }

    private static String class_import() {
        StringBuilder sb = new StringBuilder();
        sb.append("package " + GenConfig.PACKAGE + ";\n\n");
        sb.append("import com.lxf.protocol.RouterClazz;\n\n");
        return sb.toString();
    }

    private static String class_head() {
        return "public class " + CLASS_NAME + " implements RouterClazz { \n\n";
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
        sb.append("    public Class<?> getClazz(String pkgName) {\n");
        return sb.toString();
    }

    private static String method_tail() {
        StringBuilder sb = new StringBuilder();
        sb.append("        return null;\n");
        sb.append("    }\n");
        return sb.toString();
    }

    private static String method_body(Set<Bean> set) {
        if (set == null || set.size() == 0) {
            return "\n";
        }
        StringBuilder sb = new StringBuilder();

        for (Bean bean : set) {
            sb.append(create_sentence(bean));
            sb.append("\n");
        }
        return sb.toString();
    }

    private static String create_sentence(Bean bean) {
        StringBuilder sb = new StringBuilder();
        sb.append("        if (pkgName.equals(\"" + bean.pkgName + "\")) {\n");
        sb.append("            return " + bean.pkgName + ".class;\n");
        sb.append("        }\n");
        return sb.toString();
    }

}
