package com.lxf.protocol;

public interface MethodProxy {
    Object proxy(Object instance, Checker checker, String annotationPath, Object... parameters);
}
