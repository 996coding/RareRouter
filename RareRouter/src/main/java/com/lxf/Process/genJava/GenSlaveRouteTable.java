package com.lxf.Process.genJava;

import com.lxf.Process.base.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GenSlaveRouteTable {

    public static final String CLASS_NAME = "SlaveRouteTable";

    public static void genRouteInfo(Set<Bean> set, FilerGen filerGen) {
        String clazzStr = createInfoClass(set);
        filerGen.genJavaClass(clazzStr, CLASS_NAME);
    }

    private static String createInfoClass(List<Bean> beanList) {
        StringBuilder sb = new StringBuilder();
        sb.append(infoImport());
        sb.append(infoHead());


        sb.append(infoTail());
        return sb.toString();
    }

    private static String createInfoClass(Set<Bean> beanSet) {
        List<Bean> beanList = new ArrayList<>();
        for (Bean bean : beanSet) {
            beanList.add(bean);
        }
        return createInfoClass(beanList);
    }

    private static String infoImport() {
        StringBuilder sb = new StringBuilder();
        sb.append("package "+GenConfig.PACKAGE+";\n\n");
        sb.append("import com.lxf.nozzle.AbsSlaveRouteTable;\n");
        sb.append("import com.lxf.init.RouteBean;\n\n");
        return sb.toString();
    }

    private static String infoHead() {
        return "public class " + GenRouteTable.CLASS_NAME + "  extends AbsSlaveRouteTable { \n\n";
    }

//    private static String createMethod(List<Bean> beanList)


    private static String infoFiled_Arr(List<Bean> beanList) {
        StringBuilder sb = new StringBuilder();
        sb.append("    public RouteBean[] arr = {");
        sb.append("\n");
        if (beanList != null && beanList.size() > 0) {
            for (Bean bean : beanList) {
                sb.append(create_sentence(bean));
            }
        }
        sb.append("    };\n");
        return sb.toString();
    }

    private static String createImpFun() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("    @Override\n");
        sb.append("    public RouteBean[] getRouteTable() {\n");
        sb.append("        return arr;\n");
        sb.append("    }\n");
        return sb.toString();
    }

    private static String create_sentence(Bean bean) {
        if (bean == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("            ");
        sb.append("create(" +
                "\"" + bean.type + "\", " +
                "\"" + bean.isInterface + "\", " +
                "\"" + bean.path + "\", " +
                "\"" + bean.pkgName + "\", \"" +
                bean.method + "\", \"" +
                bean.returnType + "\"");
        for (int i = 0; i < bean.paramsList.size(); i++) {
            sb.append(", \"" + bean.paramsList.get(i) + "\"");
        }
        sb.append("),\n");
        return sb.toString();
    }


    private static String infoTail() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("}");
        return sb.toString();
    }
}
