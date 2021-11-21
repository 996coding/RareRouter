package com.lxf.RareRouter;

public class RareRouter {

    public static <T> T create(final Class<T> service) {
        return RareInterfaceImp.getInstance().create(service);
    }

    public static Class<?> annotationClass(String annotationPath) {
        return RareInterfaceImp.getInstance().annotationClass(annotationPath);
    }

    public static Object annotationClsInstance(String annotationPath) {
        return RareInterfaceImp.getInstance().annotationClsInstance(annotationPath);
    }

    public static void startActivity(Object context, String activityAnnotation) {
        RareInterfaceImp.getInstance().startActivity(context, activityAnnotation);
    }
}