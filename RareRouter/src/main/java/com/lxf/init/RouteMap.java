package com.lxf.init;

import com.lxf.nozzle.JavaClassGetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class RouteMap {
    public static volatile RouteMap mInstance = null;
    /*
        1、可以多个请求；
        2、不可以多个响应；
     */
    private List<RouteBean> interfaceList;
    private HashMap<String, RouteBean> classMap;

    private RouteMap() {
        initMaps();
    }

    public static RouteMap getInstance() {
        if (mInstance == null) {
            synchronized (RouteMap.class) {
                if (mInstance == null) {
                    mInstance = new RouteMap();
                }
            }
        }
        return mInstance;
    }

    public RouteBean getInterfaceRouteBean(String path, String pkgName) {
        if (path == null || path.length() == 0 || pkgName == null || pkgName.length() == 0) {
            return null;
        }
        for (RouteBean bean : interfaceList) {
            if (path.equals(bean.path) && pkgName.equals(bean.pkgName)) {
                return bean;
            }
        }
        return null;
    }

    public RouteBean getClassRouteBean(String path) {
        if (path == null || path.length() == 0) {
            return null;
        }
        if (classMap.containsKey(path)) {
            return classMap.get(path);
        }
        return null;
    }

    public List<RouteBean> getInterfaceRouteBeans(String pkgName) {
        if (pkgName == null || pkgName.length() == 0) {
            return null;
        }
        List<RouteBean> list = new ArrayList<>();
        for (RouteBean bean : interfaceList) {
            if (pkgName.equals(bean.pkgName)) {
                list.add(bean);
            }
        }
        return list;
    }

    private void initMaps() {
        interfaceList = new ArrayList<>();
        classMap = new HashMap<>();
        RouteBean[] arr = JavaClassGetter.getRouteTable().getRouteTable();
        for (RouteBean bean : arr) {
            if (bean.isInterface) {
                interfaceList.add(bean);
            } else {
                classMap.put(bean.path, bean);
            }
        }
    }

    public Class<?> getClazz(String pkgName) {
        if (pkgName == null || pkgName.length() == 0) {
            return null;
        }
        return JavaClassGetter.getClazzProvider().getClazz(pkgName);
    }

    public Object getObject(String pkgName) {
        if (pkgName == null || pkgName.length() == 0) {
            return null;
        }
        return JavaClassGetter.getClazzProvider().getInstance(pkgName);
    }

    public void startActivity(Object context, Class<?> cls) {
        JavaClassGetter.getIntentStarter().startActivity(context, cls);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        Set<String> keys_c = classMap.keySet();
        sb.append("\n");
        sb.append("包含对象个数：" + (interfaceList.size() + keys_c.size()));
        sb.append("\n");
        for (RouteBean bean : interfaceList) {
            sb.append(bean.toString());
            sb.append("\n");
        }
        for (String key : keys_c) {
            sb.append(classMap.get(key).toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
