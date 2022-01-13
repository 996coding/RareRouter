package com.lxf.Process.genJava;


public class GenModuleRareImpl {
    public static String CLASS_NAME = "ModuleRareImpl";

    public static void gen(FilerGen filerGen) {
        CLASS_NAME = CLASS_NAME + System.currentTimeMillis();

        StringBuilder sb = new StringBuilder();
        sb.append(class_import());
        sb.append(class_head());

        sb.append(method());

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
        sb.append("    public DateBean dateBean() {\n");
        sb.append("        return new com.lxf.genCode." + GenDateBeanImpl.CLASS_NAME + "();\n");
        sb.append("    }\n\n");

        return sb.toString();
    }
}
