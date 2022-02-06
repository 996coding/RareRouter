package com.lxf.Router;

import com.lxf.Annotation.RouterMethod;
import com.lxf.data.DataChecker;
import com.lxf.protocol.MethodExecute;
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
    private Map<String, MethodExecute> executeMap;
    private Map<String, RouteBean> tableMap;


    public RareHandler(Class<?> service, Object proxyInstance) {
        this.service = service;
        this.proxyInstance = proxyInstance;
        this.executeMap = new HashMap<>();
        this.tableMap = new HashMap<>();
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
        RouteBean askBean = tableMap.get(annotationPath);
        if (askBean == null) {
            askBean = RareCore.getRareCore().methodAskRouteBean(annotationPath, service.getName());
            if (askBean == null) {
                return null;
            }
            tableMap.put(annotationPath, askBean);
        }

        MethodExecute methodProxy = executeMap.get(askBean.path);
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
