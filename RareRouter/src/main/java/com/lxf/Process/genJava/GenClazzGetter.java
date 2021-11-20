package com.lxf.Process.genJava;

import com.lxf.Process.base.Bean;
import java.util.HashSet;
import java.util.Set;


public class GenClazzGetter {
    public static final String CLASS_NAME = "ClazzGetter";

    public static void genLeftCreator(Set<Bean> set, FilerGen filerGen) {
        genJava(getSet(set), filerGen);
    }


    private static Set<Bean> getSet(Set<Bean> set) {
        Set<Bean> hashSet = new HashSet<>();
        if (set == null || set.size() == 0) {
            return hashSet;
        }
        for (Bean bean : set) {
            if (bean.type.equals("0")) {
                hashSet.add(bean);
            }
        }
        return hashSet;
    }

    private static void genJava(Set<Bean> set, FilerGen filerGen) {
        StringBuilder sb = new StringBuilder();
        sb.append("package "+GenConfig.PACKAGE+";\n\n");
        sb.append("import com.lxf.nozzle.ClsProvider;\n\n");
        sb.append("public class " + CLASS_NAME + " implements ClsProvider {\n\n");

        sb.append("    @Override\n");
        sb.append("    public Class<?> getClazz(String pkgName) {\n");
        for (Bean bean : set) {
            sb.append(createIfSentence(bean));
            sb.append("\n");
        }
        sb.append("        return null;\n");
        sb.append("    }\n\n");


        sb.append("    @Override\n");
        sb.append("    public Object getInstance(String pkgName) {\n");
        for (Bean bean : set) {
            sb.append(createIfSentence2(bean));
            sb.append("\n");
        }
        sb.append("        return null;\n");
        sb.append("    }\n\n");


        sb.append("}");

        String clazzStr = sb.toString();
        filerGen.genJavaClass(clazzStr, CLASS_NAME);
    }

    private static String createIfSentence(Bean bean) {
        StringBuilder sb = new StringBuilder();
        sb.append("        if (pkgName.equals(\"" + bean.pkgName + "\")) {\n");
        sb.append("            return " + bean.pkgName + ".class;\n");
        sb.append("        }\n");
        return sb.toString();
    }

    private static String createIfSentence2(Bean bean) {
        if (bean.isInterface.equals("1")) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("        if (pkgName.equals(\"" + bean.pkgName + "\")) {\n");
        sb.append("            return new " + bean.pkgName + "();\n");
        sb.append("        }\n");
        return sb.toString();
    }
}
