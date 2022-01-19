package com.lxf.protocol;

import com.lxf.init.RouteBean;

import java.lang.reflect.Method;

public interface Checker {
    CheckResult methodChecker(RouteBean bean, Method method, Object... parameterArray);
}
