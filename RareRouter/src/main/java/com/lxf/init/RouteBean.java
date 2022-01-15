package com.lxf.init;

import com.lxf.Annotation.RouterMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
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

    对于 type=1 方法，进行位数扩展：
    第 2 位，0 代表 该方法有实现体， 1 代表 这是一个 interface;
    第 3 位，0 代表 该方法为静态 static；
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

    public static RouteBean createInterfaceBean(Class<?> clazz, Method method) {
        if (method == null || clazz == null || !clazz.isInterface()) {
            return null;
        }
        RouterMethod annotation = method.getAnnotation(RouterMethod.class);
        if (annotation == null) {
            return null;
        }
        String pkgName = clazz.getName();
        String path = annotation.path();
        String methodName = method.getName();
        String returnType = method.getReturnType().getName();
        List<String> paramsList = new ArrayList<>();
        for (Class<?> param : method.getParameterTypes()) {
            paramsList.add(param.getName());
        }
        return create("0", "1", path, pkgName, methodName, returnType, paramsList);
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
