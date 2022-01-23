package com.lxf.protocol;

public interface Checker {
    CheckResult methodCheck(RouteBean replyBean, Class<?> replyClazz, Object... parameterArray);

    boolean instanceCheck(Object instance, Class<?> clazz);
}
