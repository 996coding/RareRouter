package com.lxf.protocol;

public interface MethodProxy {
    MethodExecutor proxy(String annotationPath);
}
