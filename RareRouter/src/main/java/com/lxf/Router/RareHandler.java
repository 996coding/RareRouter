package com.lxf.Router;

import com.lxf.Annotation.RouterMethod;
import com.lxf.data.DataChecker;
import com.lxf.protocol.MethodExecutor;
import com.lxf.protocol.MethodReturn;
import com.lxf.protocol.RouteBean;
import com.lxf.protocol.Checker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RareHandler implements InvocationHandler {

    private Class<?> service;
    private Object proxyInstance;
    private Map<String, MethodExecutor> executeMap;
    private Map<String, RouteBean> tableMap;
    private AnnotateInterceptor interceptor;

    public RareHandler(Class<?> service, Object proxyInstance, AnnotateInterceptor interceptor) {
        this.service = service;
        this.proxyInstance = proxyInstance;
        this.executeMap = new HashMap<>();
        this.tableMap = new HashMap<>();
        this.interceptor = interceptor;
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
        if (interceptor != null) {
            annotationPath = interceptor.onIntercept(annotationPath);
        }
        if (annotationPath == null || annotationPath.length() == 0) {
            return null;
        }
        RouteBean askBean = tableMap.get(annotationPath);
        if (askBean == null) {
            askBean = RareCore.getRareCore().methodAskRouteBean(annotationPath, service.getName());
            if (askBean == null) {
                return null;
            }
            tableMap.put(annotationPath, askBean);
        }

        MethodExecutor methodProxy = executeMap.get(askBean.path);
        if (methodProxy == null) {
            methodProxy = RareCore.getRareCore().proxy(askBean.path);
            if (methodProxy == null) {
                return null;
            }
            executeMap.put(askBean.path, methodProxy);
        }
        Checker checker = new DataChecker(askBean, method);
        Object result = methodProxy.execute(proxyInstance, checker, args);
        if (result == MethodReturn.ERROR_PARAMETER) {
            // 参数不匹配
            return null;
        }
        return result;
    }
}
