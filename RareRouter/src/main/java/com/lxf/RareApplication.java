package com.lxf;

import com.lxf.Router.RareHandler;
import com.lxf.manager.RareAppImpl;
import com.lxf.nozzle.JavaClassGetter;

import java.lang.reflect.Proxy;

public final class RareApplication {
    static {
        RareAppImpl.getRareAppImpl().autoAddRareImpl();
    }

    public static <T> T methodProxy(Class<T> service, Object proxyInstance) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(),
                new Class<?>[]{service},
                new RareHandler(service, proxyInstance));
    }

    public static <T> T methodProxy(Class<T> service) {
        return methodProxy(service, null);
    }

    public static Class<?> annotateClazz(String annotateClazzPath) {
        return RareAppImpl.getRareAppImpl().getClazz(annotateClazzPath);
    }

    public static Object annotateBean(String pkgFullName) {
        return RareAppImpl.getRareAppImpl().getDateBean(pkgFullName);
    }

    public static void startActivity(Object context, String activityAnnotate) {
        Class<?> cls = annotateClazz(activityAnnotate);
        if (cls != null && context != null) {
            JavaClassGetter.getIntentStarter().startActivity(context, cls);
        }
    }

}
