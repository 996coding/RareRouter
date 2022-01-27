package com.lxf.data;

import com.lxf.Router.RareCore;
import com.lxf.protocol.DataBeanCreator;

public class DataBeanGetter implements DataBeanCreator {
    private String pkgName;
    private DataBeanCreator proxy;
    private Class<?> clazz;
    private boolean clazzInit = false;

    public DataBeanGetter(String pkgName) {
        this.pkgName = pkgName;
        this.proxy = RareCore.getRareCore().beanGenerate(pkgName);
    }

    private Class<?> getClazz() {
        if (clazz == null && !clazzInit) {
            clazzInit = true;
            try {
                clazz = Class.forName(pkgName);
            } catch (ClassNotFoundException e) {

            }
        }
        return clazz;
    }

    @Override
    public Object createInstance() {
        if (proxy != null) {
            proxy.createInstance();
        }
        Class<?> cls = getClazz();
        if (cls != null) {
            try {
                cls.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
