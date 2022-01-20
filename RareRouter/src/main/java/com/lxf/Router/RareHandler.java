package com.lxf.Router;

import com.lxf.Annotation.RouterMethod;
import com.lxf.data.DataChecker;
import com.lxf.init.RouteBean;
import com.lxf.manager.RareAppImpl;
import com.lxf.protocol.Checker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RareHandler implements InvocationHandler {

    private Class<?> service;
    private Object proxyInstance;

    public RareHandler(Class<?> service, Object proxyInstance) {
        this.service = service;
        this.proxyInstance = proxyInstance;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }
        RouterMethod annotation = method.getAnnotation(RouterMethod.class);
        if (annotation == null) {
            return null;
        }
        RouteBean askBean = RareAppImpl.getRareAppImpl().methodAskRouteBean(annotation.path(), service.getName());
        if (askBean == null) {
            return null;
        }
        Checker checker = new DataChecker(askBean, method);
        return RareAppImpl.getRareAppImpl().proxy(proxyInstance, checker, annotation.path(), args);
    }
}
