package com.lxf.Process.genJava;

import com.lxf.Process.base.Bean;
import com.lxf.response.RequestProxy;

import java.util.HashSet;
import java.util.Set;


public class GenResponseProxy {

    public static Set<String> genJavaResponseProxy(Set<Bean> beanSet, FilerGen filerGen) {
        Set<String> set = new HashSet<>();
        for (Bean bean : beanSet) {
            if (bean.type == null) {
                continue;
            }
            if ("1".equals(bean.isInterface)) {
                continue;
            }
            if (bean.type.startsWith("1")) {

            } else {
                continue;
            }
            String classFullName = RequestProxy.getProxyPkgName(bean.pkgName, bean.method, bean.paramsList);
            String javaClassContent = genInterfaceResponseClass(classFullName, bean);
            filerGen.genJavaClass(javaClassContent, classFullName);
            set.add(classFullName);
        }
        return set;
    }


    private static String genInterfaceResponseClass(String classFullName, Bean bean) {
        StringBuilder sb = new StringBuilder();
        sb.append(createImport(bean));
        sb.append(createClassHead(classFullName));
        sb.append(createMethod(bean));
        sb.append(createClassTail());
        return sb.toString();
    }

    private static String createImport(Bean bean) {
        String pkg = bean.pkgName.substring(0, bean.pkgName.lastIndexOf("."));
        return "package " + pkg + ";\n\n";
    }

    private static String createClassHead(String classFullName) {
        StringBuilder sb = new StringBuilder();
        sb.append("public class ");
        sb.append(classFullName.substring(classFullName.lastIndexOf(".") + 1));
        sb.append(" extends com.lxf.response.AimClass {\n\n");
        return sb.toString();
    }

    private static String createClassTail() {
        return "}";
    }

    private static String createMethod(Bean bean) {
        String classSimpleName = bean.pkgName.substring(bean.pkgName.lastIndexOf(".") + 1);
        StringBuilder sb = new StringBuilder();
        sb.append("    @Override\n");
        sb.append("    public void responseMethod(Object... objects) {\n");
        String resultObj = "        ";
        if (!"void".equals(bean.returnType.toLowerCase())) {
            resultObj = "        this.object = ";
        }
        sb.append(resultObj + "new " + classSimpleName + "()." + bean.method + "(");
        for (int i = 0; i < bean.paramsList.size(); i++) {
            sb.append("(" + bean.paramsList.get(i) + ") objects[" + i + "]");
            if (i < bean.paramsList.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(");\n");

        sb.append("    }\n\n");
        return sb.toString();
    }
}
