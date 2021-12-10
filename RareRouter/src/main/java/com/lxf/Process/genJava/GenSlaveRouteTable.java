package com.lxf.Process.genJava;

import com.lxf.Process.base.Bean;
import java.util.Set;

public class GenSlaveRouteTable {

    public static String CLASS_NAME = "SlaveRouteTable";

    public static void genRouteTable(Set<Bean> clsSet,Set<Bean> mAskSet,Set<Bean> mImpSet,FilerGen filerGen){
        CLASS_NAME = CLASS_NAME + System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append(infoImport());
        sb.append(infoHead());

        sb.append(method_routerID());
        sb.append(method_clazzTableItem(clsSet));
        sb.append(method_methodAskTableItem(mAskSet));
        sb.append(method_methodImpTableItem(mImpSet));

        sb.append(infoTail());
        filerGen.genJavaClass(sb.toString(), CLASS_NAME);
    }



    private static String infoImport() {
        StringBuilder sb = new StringBuilder();
        sb.append("package "+GenConfig.PACKAGE+";\n\n");
        sb.append("import com.lxf.nozzle.AbsSlaveRouteTable;\n");
        sb.append("import com.lxf.init.RouteBean;\n\n");
        return sb.toString();
    }

    private static String infoHead() {
        return "public class " + CLASS_NAME + "  extends AbsSlaveRouteTable { \n\n";
    }

    private static String method_routerID(){
        StringBuilder sb = new StringBuilder();
        sb.append("    @Override\n");
        sb.append("    public String routerID() {\n");
        sb.append("        return this.getClass().getName();\n");
        sb.append("    }\n\n");
        return sb.toString();
    }


    private static String method_clazzTableItem(Set<Bean> clsSet){
        StringBuilder sb = new StringBuilder();
        sb.append("    @Override\n");
        sb.append("    public RouteBean clazzTableItem(String annotationPath) {\n");

        for (Bean bean:clsSet){
            sb.append("        if (\"" + bean.path + "\".equals(annotationPath)) {\n");
            sb.append(create_sentence(bean));
            sb.append("        }\n");
        }

        sb.append("        return null;\n");
        sb.append("    }\n\n");
        return sb.toString();
    }

    private static String method_methodAskTableItem(Set<Bean> mAskSet){
        StringBuilder sb = new StringBuilder();
        sb.append("    @Override\n");
        sb.append("    public RouteBean methodAskTableItem(String pkgName) {\n");

        for (Bean bean:mAskSet){
            sb.append("        if (\"" + bean.pkgName + "\".equals(pkgName)) {\n");
            sb.append(create_sentence(bean));
            sb.append("        }\n");
        }

        sb.append("        return null;\n");
        sb.append("    }\n\n");
        return sb.toString();
    }

    private static String method_methodImpTableItem(Set<Bean> mImpSet){
        StringBuilder sb = new StringBuilder();
        sb.append("    @Override\n");
        sb.append("    public RouteBean methodImpTableItem(String annotationPath) {\n");

        for (Bean bean:mImpSet){
            sb.append("        if (\"" + bean.path + "\".equals(annotationPath)) {\n");
            sb.append(create_sentence(bean));
            sb.append("        }\n");
        }

        sb.append("        return null;\n");
        sb.append("    }\n\n");
        return sb.toString();
    }

    private static String create_sentence(Bean bean) {
        StringBuilder sb = new StringBuilder();
        sb.append("            return ");
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
        sb.append(");\n");
        return sb.toString();
    }


    private static String infoTail() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("}");
        return sb.toString();
    }
}
