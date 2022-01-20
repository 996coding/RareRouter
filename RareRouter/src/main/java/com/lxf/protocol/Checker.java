package com.lxf.protocol;

import com.lxf.init.RouteBean;

public interface Checker {
    CheckResult methodCheck(RouteBean bean, Object... parameterArray);

    boolean instanceCheck(Object instance, Class<?> clazz);
}
