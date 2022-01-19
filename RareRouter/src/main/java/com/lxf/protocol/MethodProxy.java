package com.lxf.protocol;

import java.lang.reflect.Method;

public interface MethodProxy {
    enum MethodReturn {NULL, ERROR_PARAMETER}

    Object proxy(Object instance, Method method, Checker checker, String annotationPath, Object... parameters);
}
