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

     /*
    结合 RouteBean 看。

   String type = 0; 代表　类
   String type = 1; 代表　方法
   String type = 2; 代表　静态变量/类变量

   对于 type=1 方法，进行位数扩展：
   第 2 位，0 代表 该方法有实现体， 1 代表 这是一个 interface;
   第 3 位，0 代表 该方法为静态 static；
    */

    public Bean(String type, String path, String pkgName, String method, String returnType, List<String> pList, String isInterface) {
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
        this.type = type;
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

    public boolean isStaticMethodImpl() {
        return "100".equals(type);
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

        if (type == null || type.length() == 0) {
            return null;
        }
        Bean bean = null;
        if (type.startsWith("0")) {
            bean = new Bean(path, pkgName, isInterface);
        } else if (type.startsWith("1")) {
            String[] paramsArr = paramsList.split("-");
            List<String> list = new ArrayList<>();
            if (paramsArr != null && paramsArr.length > 0) {
                for (int i = 0; i < paramsArr.length; i++) {
                    list.add(paramsArr[i]);
                }
            }
            bean = new Bean(type, path, pkgName, method, returnType, list, isInterface);
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
