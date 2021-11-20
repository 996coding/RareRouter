package com.lxf.Process.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bean {
    public final String path;
    public final String pkgName;
    public final String method;
    public final String returnType;
    public final List<String> paramsList;
    public final String isInterface;
    public final String type;

    public Bean(String path, String pkgName, String method, String returnType, List<String> pList, String isInterface) {
        this.path = path;
        this.pkgName = pkgName;
        this.method = method;
        this.returnType = returnType;
        if (pList == null) {
            this.paramsList = new ArrayList<>();
        } else {
            this.paramsList = pList;
        }
        this.isInterface = isInterface;
        this.type = "1";
    }

    public Bean(String path, String pkgName, String isInterface) {
        this.path = path;
        this.pkgName = pkgName;
        this.method = "";
        this.returnType = "";
        this.paramsList = new ArrayList<>();
        this.isInterface = isInterface;
        this.type = "0";
    }

    @Override
    public String toString() {
        String str = "path=" + path + '#' +
                "pkgName=" + pkgName + '#' +
                "method=" + method + '#' +
                "returnType=" + returnType + '#' +
                "paramsList=";
        if (this.paramsList != null && this.paramsList.size() > 0) {
            for (int i = 0; i < this.paramsList.size(); i++) {
                String param = this.paramsList.get(i);
                str = str + param + "-";
            }
        }
        str = str + "#" + "isInterface=" + isInterface;
        str = str + "#" + "type=" + type;
        return str;

    }

    public static Bean convertStrToBean(String toString) {
        String[] all = toString.split("#");
        String path_tmp = all[0];
        String pkgName_tmp = all[1];
        String method_tmp = all[2];
        String returnType_tmp = all[3];
        String paramsList_tmp = all[4];
        String isInterface_tmp = all[5];
        String type_tmp = all[6];

        String path = path_tmp.substring(path_tmp.indexOf("=") + 1);
        String pkgName = pkgName_tmp.substring(pkgName_tmp.indexOf("=") + 1);
        String method = method_tmp.substring(method_tmp.indexOf("=") + 1);
        String returnType = returnType_tmp.substring(returnType_tmp.indexOf("=") + 1);
        String paramsList = paramsList_tmp.substring(paramsList_tmp.indexOf("=") + 1);
        String isInterface = isInterface_tmp.substring(isInterface_tmp.indexOf("=") + 1);
        String type = type_tmp.substring(type_tmp.indexOf("=") + 1);

        Bean bean = null;
        if ("0".equals(type)) {
            bean = new Bean(path, pkgName, isInterface);
        } else if ("1".equals(type)) {
            String[] paramsArr = paramsList.split("-");
            List<String> list = new ArrayList<>();
            if (paramsArr != null && paramsArr.length > 0) {
                for (int i = 0; i < paramsArr.length; i++) {
                    list.add(paramsArr[i]);
                }
            }
            bean = new Bean(path, pkgName, method, returnType, list, isInterface);
        }
        return bean;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bean bean = (Bean) o;
        return path.equals(bean.path) &&
                pkgName.equals(bean.pkgName) &&
                method.equals(bean.method) &&
                returnType.equals(bean.returnType) &&
                paramsList.equals(bean.paramsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, pkgName, method, returnType, paramsList);
    }
}
