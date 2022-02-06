package com.lxf.Router;

import com.lxf.Annotation.RouterMethod;
import com.lxf.data.DataChecker;
import com.lxf.protocol.MethodExecute;
import com.lxf.protocol.MethodReturn;
import com.lxf.protocol.RouteBean;
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
        String annotationPath = annotation.path();
        if (annotationPath == null || annotationPath.length() == 0) {
            return null;
        }
        RouteBean askBean = RareCore.getRareCore().methodAskRouteBean(annotationPath, service.getName());
        if (askBean == null) {
            return null;
        }
        Checker checker = new DataChecker(askBean, method);
        MethodExecute methodProxy = RareCore.getRareCore().proxy(askBean.path);
        Object result = methodProxy.execute(proxyInstance, checker, args);
        if (result == MethodReturn.ERROR_PARAMETER) {
            // 参数不匹配
            return null;
        }
        return result;
    }
}
