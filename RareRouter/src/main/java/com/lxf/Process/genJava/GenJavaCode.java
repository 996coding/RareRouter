package com.lxf.Process.genJava;

public class GenJavaCode {
    public static final String CLASS_NAME = "JavaCode";

    public static void genJavaCode(FilerGen filerGen) {
        String clazzStr1 = getClsStr(CLASS_NAME);
        filerGen.genJavaClass(clazzStr1, CLASS_NAME);

    }

    private static String getClsStr(String clsName) {
        StringBuilder sb = new StringBuilder();
        sb.append("package " + GenConfig.PACKAGE_JAVA_CODE + ";\n\n");
        sb.append("import com.lxf.nozzle.*;\n\n");

        sb.append("public class " + clsName + " implements JavaClazz {\n\n");

        sb.append("    public boolean isValid = true;\n\n");

        sb.append("    public " + clsName + "(){\n");
        sb.append("        if (isValid){\n");
        sb.append("            JavaClassGetter.setJavaClazz(this);\n");
        sb.append("        }\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    public RouteTable getRouteTable() {\n");
        sb.append("        return new " + GenConfig.PACKAGE + ".RecordRouteInfo()" + ";\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    public IntentStart getIntentStarter() {\n");
        sb.append("        return new " + GenConfig.PACKAGE + ".ActivityStarter()" + ";\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    public ClsProvider getClazzProvider() {\n");
        sb.append("        return new " + GenConfig.PACKAGE + ".ClazzGetter()" + ";\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    public FunImpClazz getFunImpClazz() {\n");
        sb.append("        return new " + GenConfig.PACKAGE + ".MethodImpClazzProvider()" + ";\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    public ParamInstance getParamObjCreator() {\n");
        sb.append("        return new " + GenConfig.PACKAGE + ".ParamObjCreator()" + ";\n");
        sb.append("    }\n\n");


        sb.append("}\n");

        return sb.toString();
    }
}
