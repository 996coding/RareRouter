package com.lxf.Router;

public interface RareInterface {
    /*
    method route
     */
    <T> T create(final Class<T> service);

    /*
    如果一个类使用了注解，可以从该方法获取它的class。该方法没有使用反射。
     */
    Class<?> annotationClass(String annotationPath);

    /*
    如果一个类使用了注解，该方法可以获取该类的实例。该方法没有使用反射。
     */
    Object annotationClsInstance(String annotationPath);

    /*
    启动android中的activity。
     */
    void startActivity(Object context, String activityAnnotation);
}
