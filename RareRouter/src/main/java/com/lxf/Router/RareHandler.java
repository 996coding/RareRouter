package com.lxf.Router;

import com.lxf.Annotation.RouterMethod;
import com.lxf.manager.RareAppImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RareHandler implements InvocationHandler {

    private Class<?> service;

    public RareHandler(Class<?> service) {
        this.service = service;
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



        return null;
    }
}
