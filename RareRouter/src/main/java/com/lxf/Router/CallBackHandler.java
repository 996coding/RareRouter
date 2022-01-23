package com.lxf.Router;

import com.lxf.Annotation.RouterMethod;
import com.lxf.data.DataChecker;
import com.lxf.protocol.RouteBean;
import com.lxf.protocol.CheckResult;
import com.lxf.protocol.Checker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CallBackHandler implements InvocationHandler {
    private Class<?> service;
    private Object proxyInstance;
    private Class<?> proxyClazz;


    public CallBackHandler(Class<?> service, Object proxyInstance, Class<?> proxyClazz) {
        this.service = service;
        this.proxyInstance = proxyInstance;
        this.proxyClazz = proxyClazz;
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
        String annotationPath = annotation.path();
        if (annotationPath == null || annotationPath.length() == 0) {
            return null;
        }
        Method methodReply = null;
        for (Method m : proxyClazz.getMethods()) {
            RouterMethod routerMethod = m.getAnnotation(RouterMethod.class);
            if (routerMethod != null && annotationPath.equals(routerMethod.path())) {
                methodReply = proxyInstance.getClass().getMethod(m.getName(), m.getParameterTypes());
            }
        }
        if (methodReply == null) {
            return null;
        }
        RouteBean askBean = RareAppImpl.getRareAppImpl().methodAskRouteBean(annotationPath, service.getName());
        if (askBean == null) {
            return null;
        }
        RouteBean replyBean = RareAppImpl.getRareAppImpl().methodAskRouteBean(annotationPath, proxyClazz.getName());
        if (replyBean == null) {
            return null;
        }
        Checker checker = new DataChecker(askBean, method);
        CheckResult result = checker.methodCheck(replyBean, proxyClazz, args);
        if (result.isOk) {
            return methodReply.invoke(proxyInstance, result.parameterArray);
        }
        return null;

    }
}
