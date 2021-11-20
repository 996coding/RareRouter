package com.lxf.init;

import java.util.ArrayList;
import java.util.List;

public final class RouteBean {
    public final String path;
    public final String pkgName;
    public final String method;
    public final String returnType;
    public final List<String> paramsList;
    public final boolean isInterface;
    /*
    String type = 0; 代表　类
    String type = 1; 代表　方法
    String type = 2; 代表　静态变量/类变量
     */
    public final String type;

    public RouteBean(String type, String isInterface, String path, String pkgName, String method, String returnType, List<String> paramsList) {
        this.path = path;
        this.pkgName = pkgName;
        this.method = method;
        this.returnType = returnType;
        this.paramsList = paramsList;
        this.isInterface = "1".equals(isInterface);
        this.type = type;
    }

    public static RouteBean create(String type, String isInterface, String path, String pkgName, String method, String returnType, List<String> paramsList) {
        return new RouteBean(type, isInterface, path, pkgName, method, returnType, paramsList);
    }

    public static RouteBean create(String type, String isInterface, String path, String pkgName, String method, String returnType, String... params) {
        List<String> paramsList = new ArrayList<>();
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                paramsList.add(params[i]);
            }
        }
        return new RouteBean(type, isInterface, path, pkgName, method, returnType, paramsList);
    }

    @Override
    public String toString() {
        return "RouteBean{" +
                "type='" + type + '\'' +
                "path='" + path + '\'' +
                ", pkgName='" + pkgName + '\'' +
                ", method='" + method + '\'' +
                ", returnType='" + returnType + '\'' +
                ", paramsList=" + paramsList +
                ", isInterface=" + (isInterface ? "yes" : "no") +
                '}';
    }
}
