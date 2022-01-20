package com.lxf.protocol;

public interface MethodProxy {
    enum MethodReturn {NULL, ERROR_PARAMETER}

    Object proxy(Object instance, Checker checker, String annotationPath, Object... parameters);
}
