package com.lxf.Process.genJava;

import com.lxf.Process.base.BaseProcessor;

import java.util.HashMap;
import java.util.Map;

public class GenInstanceCreatorImpl {
    public static String CLASS_NAME = "InstanceCreatorImpl";

    public static void gen(Map<String, String> map, FilerGen filerGen) {

        Map<String, String> newMapCreate = new HashMap<>();
        Map<String, String> newMapGen = new HashMap<>();
        for (String annotate : map.keySet()) {
            String className = map.get(annotate);
            String pkgName = className.replace(".", "_") + "_Impl";
            newMapCreate.put(pkgName, annotate);
            newMapGen.put(className,pkgName);
            genCreatorImpl(pkgName, className, filerGen);
        }


        CLASS_NAME = CLASS_NAME + "_" + BaseProcessor.moduleName;

        StringBuilder sb = new StringBuilder();
        sb.append(class_import());
        sb.append(class_head());

        sb.append(method_head());
        sb.append(method_body(newMapCreate));
        sb.append(method_tail());

        sb.append(method_head2());
        sb.append(method_body2(newMapGen));
        sb.append(method_tail2());

        sb.append(clazz_tail());
        filerGen.genJavaClass(sb.toString(), CLASS_NAME);
    }

    private static void genCreatorImpl(String pkgName, String className, FilerGen filerGen) {

        StringBuilder sb = new StringBuilder();
        sb.append("package " + GenConfig.PACKAGE + ";\n\n");
        sb.append("import com.lxf.protocol.*;\n\n");
        sb.append("public class " + pkgName + " implements DataBeanCreator {\n");

        sb.append("    @Override\n");
        sb.append("    public Object createInstance() {\n");
        sb.append("        return " + "new " + className + "();\n");
        sb.append("    }\n");
        sb.append("}\n");
        filerGen.genJavaClass(sb.toString(), pkgName);
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
                " implements InstanceCreator { \n\n";
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
        sb.append("    public DataBeanCreator beanCreator(String annotateBeanPath) {\n");
        return sb.toString();
    }

    private static String method_head2() {
        StringBuilder sb = new StringBuilder();
        sb.append("    @Override\n");
        sb.append("    public DataBeanCreator beanGenerate(String pkgName) {\n");
        return sb.toString();
    }

    private static String method_tail() {
        StringBuilder sb = new StringBuilder();
        sb.append("        return null;\n");
        sb.append("    }\n\n");
        return sb.toString();
    }

    private static String method_tail2() {
        StringBuilder sb = new StringBuilder();
        sb.append("        return null;\n");
        sb.append("    }\n\n");
        return sb.toString();
    }

    private static String method_body(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return "\n";
        }
        StringBuilder sb = new StringBuilder();
        for (String pkgName : map.keySet()) {
            sb.append("        if (\"" + map.get(pkgName) + "\".equals(annotateBeanPath)){\n");
            sb.append("            return new " + pkgName + "();\n");
            sb.append("        }\n");
        }
        return sb.toString();
    }

    private static String method_body2(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return "\n";
        }
        StringBuilder sb = new StringBuilder();
        for (String className : map.keySet()) {
            sb.append("        if (\"" + className + "\".equals(pkgName)){\n");
            sb.append("            return new " + map.get(className) + "();\n");
            sb.append("        }\n");
        }
        return sb.toString();
    }
}
