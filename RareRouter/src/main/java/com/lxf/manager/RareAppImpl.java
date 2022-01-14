package com.lxf.manager;

import com.lxf.init.RouteBean;
import com.lxf.protocol.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RareAppImpl implements ClassBeans, MethodBeans, RouterClazz, MethodProxy, DateBean {
    private static final RareAppImpl instance = new RareAppImpl();
    private List<RareInterface> rareImplList;
    private Set<String> implPkgSet;

    private RareAppImpl() {
        rareImplList = new ArrayList<>();
        implPkgSet = new HashSet<>();
    }

    public static RareAppImpl getRareAppImpl() {
        return instance;
    }

    public static void addRareImpl(RareInterface rareImpl) {
        if (rareImpl == null) {
            return;
        }
        String pkgName = rareImpl.getClass().getName();
        synchronized (RareAppImpl.class) {
            instance.putRareImpl(pkgName, rareImpl);
        }
    }

    private void putRareImpl(String rareId, RareInterface rareModuleImpl) {
        if (implPkgSet.contains(rareId)) {
            return;
        }
        implPkgSet.add(rareId);
        rareImplList.add(rareModuleImpl);
    }

    @Override
    public RouteBean classRouteBean(String annotationPath) {
        for (RareInterface impl : rareImplList) {
            RouteBean bean = impl.classBeans().classRouteBean(annotationPath);
            if (bean != null) {
                return bean;
            }
        }
        return null;
    }

    @Override
    public Object getDateBean(String pkgFullName) {
        for (RareInterface impl : rareImplList) {
            Object bean = impl.dateBean().getDateBean(pkgFullName);
            if (bean != null) {
                return bean;
            }
        }
        return null;
    }

    @Override
    public RouteBean methodAskRouteBean(String pkgName) {
        for (RareInterface impl : rareImplList) {
            RouteBean bean = impl.methodBeans().methodAskRouteBean(pkgName);
            if (bean != null) {
                return bean;
            }
        }
        return null;
    }

    @Override
    public RouteBean methodReplyRouteBean(String annotationPath) {
        for (RareInterface impl : rareImplList) {
            RouteBean bean = impl.methodBeans().methodReplyRouteBean(annotationPath);
            if (bean != null) {
                return bean;
            }
        }
        return null;
    }

    @Override
    public Object proxy(Object instance, String annotationPath, Object... parameters) {
        for (RareInterface impl : rareImplList) {
            Object methodReturn = impl.methodProxy().proxy(instance, annotationPath, parameters);
            if (methodReturn != null) {
                if (methodReturn == MethodReturn.NULL) {
                    return null;
                }
                return methodReturn;
            }
        }
        return null;
    }

    @Override
    public Class<?> getClazz(String pkgName) {
        for (RareInterface impl : rareImplList) {
            Class<?> clazz = impl.routerClazz().getClazz(pkgName);
            if (clazz != null) {
                return clazz;
            }
        }
        return null;
    }
}
