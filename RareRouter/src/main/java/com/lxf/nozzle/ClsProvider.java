package com.lxf.nozzle;

public interface ClsProvider {
    Class<?> getClazz(String pkgName);

    Object getInstance(String pkgName);
}
