package com.lxf.LxfRouter;

import com.lxf.init.RouteBean;
import com.lxf.init.RouteMap;

import java.lang.reflect.Proxy;

public class RestfulRouter {

    public static <T> T create(final Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(),
                new Class<?>[]{service},
                new RouterHandler(service));
    }

    public static Class<?> getActivityClass(String activityAnnotationPath) {
        RouteBean routeBean = RouteMap.getInstance().getClassRouteBean(activityAnnotationPath);
        if (routeBean == null) {
            return null;
        }
        return RouteMap.getInstance().getClazz(routeBean.pkgName);
    }

    public static Object getModuleObj(String annotationPath) {
        RouteBean routeBean = RouteMap.getInstance().getClassRouteBean(annotationPath);
        if (routeBean == null) {
            return null;
        }
        return RouteMap.getInstance().getObject(routeBean.pkgName);
    }

    public static void startAndroidComponent(Object context, String activityAnnotationPath) {
        Class<?> cls = getActivityClass(activityAnnotationPath);
        if (cls == null) {
            return;
        }
        RouteMap.getInstance().startActivity(context, cls);
    }
}