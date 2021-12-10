package com.lxf.Process.genJava;

import java.util.Set;

public class GenMethodImpClazzProvider {

    public static final String CLASS_NAME = "MethodImpClazzProvider";
    private static final String CLASS_PKG = "com.lxf.genCode.";

    public static void genAimClassCreator(Set<String> set, FilerGen filerGen) {
        String clazzStr = createClassStr(set);
        filerGen.genJavaClass(clazzStr, CLASS_NAME);
    }

    private static String createClassStr(Set<String> set) {

        StringBuilder sb = new StringBuilder();
        sb.append("package "+GenConfig.PACKAGE+";\n\n");
        sb.append("import com.lxf.response.AimClass;\n");
        sb.append("import com.lxf.response.AimClassImp;\n");
        sb.append("import com.lxf.nozzle.FunImpClazz;\n\n");
        sb.append("public class " + CLASS_NAME + " implements FunImpClazz {\n\n");
        sb.append("    @Override\n");
        sb.append("    public AimClass create(String pkgFullName) {\n\n");
        for (String clazz : set) {
            sb.append(ifSentence(clazz));
            sb.append("\n");
        }
        sb.append("        return null;\n");
        sb.append("    }\n");
        sb.append("}");
        return sb.toString();
    }

    private static String ifSentence(String classFullName) {
        StringBuilder sb = new StringBuilder();
        sb.append("        if (pkgFullName.equals(\"" + classFullName + "\")) {\n");
        sb.append("            return new "+CLASS_PKG + classFullName + "();\n");
        sb.append("        }\n");
        return sb.toString();
    }
}
