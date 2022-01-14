package com.lxf.protocol;

public interface MethodProxy {
    enum MethodReturn {NULL}
    Object proxy(Object instance, String annotationPath, Object... parameters);
}
