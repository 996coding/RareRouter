package com.lxf.protocol;

public interface MethodProxy {
    Object proxy(Object instance, String annotationPath, Object... parameters);
}
