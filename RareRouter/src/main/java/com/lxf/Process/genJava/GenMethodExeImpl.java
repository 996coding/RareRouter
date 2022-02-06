package com.lxf.Process.genJava;

import com.lxf.Process.base.BaseProcessor;
import com.lxf.Process.base.Bean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GenMethodExeImpl {
    private static String CLASS_NAME = "MethodExecuteImpl";
    private static final String Pre_Name = "MethodExecuteImpl";

    private static long method_index = 0;

    public static Map<String, Bean> gen(Set<Bean> set, FilerGen filerGen) {

        Map<String, Bean> exeClazz = new HashMap<>();
        if (set == null) {
            return exeClazz;
        }
        for (Bean bean : set) {
            genEveryOne(bean, filerGen, exeClazz);
        }

        return exeClazz;
    }

    private static void genEveryOne(Bean bean, FilerGen filerGen, Map<String, Bean> exeClazz) {
        CLASS_NAME = Pre_Name + "_" + BaseProcessor.moduleName + "_" + method_index;
        method_index++;
        exeClazz.put(CLASS_NAME, bean);

        StringBuilder sb = new StringBuilder();
        sb.append(class_import());
        sb.append(class_head());

        sb.append("    /*\n");
        sb.append("        Annotation Path = ");
        sb.append(bean.path + "\n");
        sb.append("    */\n\n");
        sb.append(clazz_field(bean));

        sb.append(method_head());
        sb.append(method_body(bean));
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
                " implements MethodExecute { \n\n";
    }

    private static String clazz_tail() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("}");
        return sb.toString();
    }

    private static String clazz_field(Bean bean) {
        StringBuilder sb = new StringBuilder();
        sb.append("    public final RouteBean bean = ");
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
        sb.append(");\n\n");
        return sb.toString();
    }

    private static String method_head() {
        StringBuilder sb = new StringBuilder();
        sb.append("    @Override\n");
        sb.append("    public Object execute(Object instance, Checker checker, Object... parameters) {\n");
        return sb.toString();
    }

    private static String method_tail() {
        StringBuilder sb = new StringBuilder();
        sb.append("    }\n\n");
        return sb.toString();
    }

    private static String method_body(Bean bean) {
        StringBuilder sb = new StringBuilder();
        sb.append("        CheckResult result = checker.methodCheck(bean, " + bean.pkgName + ".class, parameters);\n");
        sb.append("        if (!result.isOk) {\n");
        sb.append("            return MethodReturn.ERROR_PARAMETER;\n");
        sb.append("        }\n");
        sb.append(create_sentence(bean));
        return sb.toString();
    }


    private static String create_sentence(Bean bean) {
        StringBuilder sb = new StringBuilder();
        sb.append("        " + bean.pkgName + " proxyInstance = null;\n");
        sb.append("        if (checker.instanceCheck(instance, " + bean.pkgName + ".class)) {\n");
        sb.append("            proxyInstance = (" + bean.pkgName + ") instance;\n");
        sb.append("        }else {\n");
        sb.append("            proxyInstance = new " + bean.pkgName + "();\n");
        sb.append("        }\n");
        boolean voidReturn = "void".equals(bean.returnType.toLowerCase());
        if (voidReturn) {
            sb.append("        proxyInstance." + bean.method + "(");
        } else {
            sb.append("        return proxyInstance." + bean.method + "(");
        }
        for (int i = 0; i < bean.paramsList.size(); i++) {
            sb.append("(" + bean.paramsList.get(i) + ") result.parameterArray[" + i + "]");
            if (i < bean.paramsList.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(");\n");
        if (voidReturn) {
            sb.append("        return MethodReturn.NULL;\n");
        }
        return sb.toString();
    }

}
