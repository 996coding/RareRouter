package com.lxf.protocol;

public interface MethodExecute {
    Object execute(Object instance, Checker checker, Object... parameters);
}
