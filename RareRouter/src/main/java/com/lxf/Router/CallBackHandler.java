package com.lxf.Router;

import com.lxf.Annotation.RouterMethod;
import com.lxf.data.DataChecker;
import com.lxf.init.RouteBean;
import com.lxf.manager.RareAppImpl;
import com.lxf.protocol.CheckResult;
import com.lxf.protocol.Checker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CallBackHandler implements InvocationHandler {
    private Class<?> service;
    private Object proxyInstance;

    public CallBackHandler(Class<?> service, Object proxyInstance) {
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
        String annotationPath = annotation.path();
        if (annotationPath == null || annotationPath.length() == 0) {
            return null;
        }
        Method methodReply = null;
        for (Method m : proxyInstance.getClass().getMethods()) {
            RouterMethod annotationReply = m.getAnnotation(RouterMethod.class);
            if (annotationReply != null && annotationPath.equals(annotationReply.path())) {
                methodReply = m;
                break;
            }
        }
        if (methodReply == null) {
            return null;
        }

        RouteBean askBean = RareAppImpl.getRareAppImpl().methodAskRouteBean(annotation.path(), service.getName());
        if (askBean == null) {
            return null;
        }
        RouteBean replyBean = RareAppImpl.getRareAppImpl().methodAskRouteBean(annotation.path(), proxyInstance.getClass().getName());
        if (replyBean == null) {
            return null;
        }
        Checker checker = new DataChecker(askBean, method);
        CheckResult result = checker.methodCheck(replyBean, proxyInstance.getClass(), args);
        if (result.isOk) {
            return methodReply.invoke(proxyInstance, result.parameterArray);
        }
        return null;

    }
}
