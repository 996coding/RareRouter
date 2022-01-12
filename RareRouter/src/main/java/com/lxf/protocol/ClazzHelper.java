package com.lxf.protocol;

public interface ClazzHelper {
    Class<?> getClazz(String pkgName);

    Object getInstance(String pkgName);
}
