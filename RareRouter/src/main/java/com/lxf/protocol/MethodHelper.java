package com.lxf.protocol;

public interface MethodHelper {
    MethodProxy methodProxy(String pkgFullName);

    Object methodParameter(String pkgFullName);
}
