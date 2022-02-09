package com.lxf.protocol;

public interface MethodExecutor {
    Object execute(Object instance, Checker checker, Object... parameters);
}
