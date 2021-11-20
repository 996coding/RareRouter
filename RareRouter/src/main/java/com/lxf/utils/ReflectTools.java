package com.lxf.utils;

public class ReflectTools {
    public static Object newInstance(String clazz) {
        Object obj = null;
        try {
            Class<?> cls = Class.forName(clazz);
            obj = cls.newInstance();
        } catch (ClassNotFoundException e) {

        } catch (IllegalAccessException e) {

        } catch (InstantiationException e) {

        }
        return obj;
    }
}
