package com.lxf.RareRouter;

import com.lxf.init.RouteBean;
import com.lxf.init.RouteMap;

import java.lang.reflect.Proxy;

public class RareRouter {

    public static <T> T create(final Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(),
                new Class<?>[]{service},
                new RouterHandler(service));
    }

    public static Class<?> annotationClass(String activityAnnotationPath) {
        RouteBean routeBean = RouteMap.getInstance().getClassRouteBean(activityAnnotationPath);
        if (routeBean == null) {
            return null;
        }
        return RouteMap.getInstance().getClazz(routeBean.pkgName);
    }

    public static Object annotationClsInstance(String annotationPath) {
        RouteBean routeBean = RouteMap.getInstance().getClassRouteBean(annotationPath);
        if (routeBean == null) {
            return null;
        }
        return RouteMap.getInstance().getObject(routeBean.pkgName);
    }

    public static void startActivity(Object context, String activityAnnotationPath) {
        Class<?> cls = annotationClass(activityAnnotationPath);
        if (cls == null) {
            return;
        }
        RouteMap.getInstance().startActivity(context, cls);
    }
}