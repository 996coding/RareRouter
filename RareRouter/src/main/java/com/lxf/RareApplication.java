package com.lxf;

import com.lxf.Router.RareHandler;
import com.lxf.Router.RareAppImpl;
import com.lxf.protocol.DataBeanCreator;

import java.lang.reflect.Proxy;

public final class RareApplication {
    static {
        RareAppImpl.getRareAppImpl().autoAddRareImpl();
    }

    public static <T> T createImpl(Class<T> service, Object proxyInstance) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(),
                new Class<?>[]{service},
                new RareHandler(service, proxyInstance));
    }

    public static <T> T createImpl(Class<T> service) {
        return createImpl(service, null);
    }

    public static Class<?> annotateClazz(String annotateClazzPath) {
        return RareAppImpl.getRareAppImpl().getClazz(annotateClazzPath);
    }

    public static Object annotateBean(String annotateBeanPath) {
        DataBeanCreator creator = dataBeanCreator(annotateBeanPath);
        return creator.createInstance();
    }

    public static DataBeanCreator dataBeanCreator(String annotateBeanPath) {
        return RareAppImpl.getRareAppImpl().beanCreator(annotateBeanPath);
    }

    public static void startIntent(Object context, String activityAnnotate) {
        Class<?> cls = annotateClazz(activityAnnotate);
        if (cls != null && context != null) {
            RareAppImpl.getRareAppImpl().startIntent(context, cls);
        }
    }

}
