package com.lxf.Process.genJava;


import com.lxf.Process.base.BaseProcessor;

import java.io.File;
import java.net.URI;

public class GenModuleRareImpl {
    public static String CLASS_NAME = "ModuleRareImpl";
    public static String FILE_PATH;

    public static void gen(FilerGen filerGen) {
        CLASS_NAME = CLASS_NAME + "_" + BaseProcessor.moduleName;

        StringBuilder sb = new StringBuilder();
        sb.append(class_import());
        sb.append(class_head());

        sb.append(static_code());
        sb.append(method());

        sb.append(clazz_tail());
        URI uri = filerGen.genJavaClass(sb.toString(), CLASS_NAME);
        File file = new File(uri);
        FILE_PATH = file.getAbsolutePath();
    }

    private static String static_code() {
        StringBuilder sb = new StringBuilder();
        sb.append("    static {\n");
        sb.append("        RareImplAdder.addRareImpl(new " + CLASS_NAME + "());\n");
        sb.append("    }\n\n");
        sb.append("    public static String flag = " + CLASS_NAME + ".class.getName();\n\n");
        return sb.toString();
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
                " implements RareInterface { \n\n";
    }

    private static String clazz_tail() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("}");
        return sb.toString();
    }

    private static String method() {
        StringBuilder sb = new StringBuilder();
        sb.append("    @Override\n");
        sb.append("    public ClassBeans classBeans() {\n");
        sb.append("        return new com.lxf.genCode." + GenClassBeansImpl.CLASS_NAME + "();\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    public MethodBeans methodBeans() {\n");
        sb.append("        return new com.lxf.genCode." + GenMethodBeansImpl.CLASS_NAME + "();\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    public RouterClazz routerClazz() {\n");
        sb.append("        return new com.lxf.genCode." + GenRouterClazzImpl.CLASS_NAME + "();\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    public MethodProxy methodProxy() {\n");
        sb.append("        return new com.lxf.genCode." + GenMethodProxyImpl.CLASS_NAME + "();\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    public InstanceCreator instanceCreator() {\n");
        sb.append("        return new com.lxf.genCode." + GenInstanceCreatorImpl.CLASS_NAME + "();\n");
        sb.append("    }\n\n");

        return sb.toString();
    }
}
