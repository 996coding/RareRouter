package com.lxf.protocol;

import com.lxf.init.RouteBean;

public interface Checker {
    CheckResult methodCheck(RouteBean replyBean, Class<?> replyClazz, Object... parameterArray);

    boolean instanceCheck(Object instance, Class<?> clazz);
}
