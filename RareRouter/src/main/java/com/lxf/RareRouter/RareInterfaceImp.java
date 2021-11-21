package com.lxf.RareRouter;

import com.lxf.init.RouteBean;
import com.lxf.init.RouteMap;

import java.lang.reflect.Proxy;

public class RareInterfaceImp implements RareInterface {

    private static RareInterfaceImp interfaceImp = null;

    private RareInterfaceImp() {

    }

    public static RareInterface getInstance() {
        if (interfaceImp == null) {
            interfaceImp = new RareInterfaceImp();
        }
        return interfaceImp;
    }

    @Override
    public <T> T create(Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(),
                new Class<?>[]{service},
                new RouterHandler(service));
    }

    @Override
    public Class<?> annotationClass(String annotationPath) {
        RouteBean routeBean = RouteMap.getInstance().getClassRouteBean(annotationPath);
        if (routeBean == null) {
            return null;
        }
        return RouteMap.getInstance().getClazz(routeBean.pkgName);
    }

    @Override
    public Object annotationClsInstance(String annotationPath) {
        RouteBean routeBean = RouteMap.getInstance().getClassRouteBean(annotationPath);
        if (routeBean == null) {
            return null;
        }
        return RouteMap.getInstance().getObject(routeBean.pkgName);
    }

    @Override
    public void startActivity(Object context, String activityAnnotation) {
        Class<?> cls = annotationClass(activityAnnotation);
        if (cls == null) {
            return;
        }
        RouteMap.getInstance().startActivity(context, cls);
    }
}
