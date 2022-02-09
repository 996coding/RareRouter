package com.lxf;

import com.lxf.Router.AnnotateInterceptor;
import com.lxf.Router.RareHandler;
import com.lxf.Router.RareCore;
import com.lxf.data.SimpleChecker;
import com.lxf.protocol.DataBeanCreator;
import com.lxf.protocol.MethodExecutor;
import com.lxf.protocol.MethodReturn;

import java.lang.reflect.Proxy;

public final class RareApplication {
    static {
        RareCore.getRareCore().autoAddRareImpl();
    }

    public static <T> T createImpl(Class<T> service, Object proxyInstance, AnnotateInterceptor interceptor) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service}, new RareHandler(service, proxyInstance, interceptor));
    }

    public static <T> T createImpl(Class<T> service, RareHandler handler) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service}, handler);
    }

    public static <T> T createImpl(Class<T> service) {
        return createImpl(service, null, null);
    }

    /* 参数类型无法转换 */
    public static Object executeMethod(String annotatePath, Object... parameters) {
        MethodExecutor executor = RareCore.getRareCore().proxy(annotatePath);
        if (executor == null) {
            return MethodReturn.NO_IMPL;
        }
        return executor.execute(null, new SimpleChecker(annotatePath), parameters);
    }


    public static Class<?> annotateClazz(String annotateClazzPath) {
        return RareCore.getRareCore().getClazz(annotateClazzPath);
    }

    public static Object annotateBean(String annotateBeanPath) {
        DataBeanCreator creator = dataBeanCreator(annotateBeanPath);
        return creator.createInstance();
    }

    public static DataBeanCreator dataBeanCreator(String annotateBeanPath) {
        return RareCore.getRareCore().beanCreator(annotateBeanPath);
    }

    public static void startIntent(Object context, String activityAnnotate) {
        Class<?> cls = annotateClazz(activityAnnotate);
        if (cls != null && context != null) {
            RareCore.getRareCore().startIntent(context, cls);
        }
    }

}
