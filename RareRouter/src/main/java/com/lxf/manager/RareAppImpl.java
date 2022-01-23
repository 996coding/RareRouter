package com.lxf.manager;

import com.lxf.Process.genJava.GenConfig;
import com.lxf.Process.genJava.GenRareAdder;
import com.lxf.protocol.RouteBean;
import com.lxf.protocol.*;
import com.lxf.template.RareAdder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RareAppImpl implements ClassBeans, MethodBeans, RouterClazz, MethodProxy, InstanceCreator {
    private static final RareAppImpl instance = new RareAppImpl();
    private List<RareInterface> rareImplList;
    private Set<String> implPkgSet;

    private RareAppImpl() {
        rareImplList = new ArrayList<>();
        implPkgSet = new HashSet<>();
//        autoAddRareImpl();
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

    public void autoAddRareImpl() {
        if (!RareAdder.enable) {
            try {
                Class.forName(GenConfig.PACKAGE_JAVA_CODE + "." + GenRareAdder.CLASS_NAME_BACK_UP);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void putRareImpl(String rareId, RareInterface rareModuleImpl) {
        if (implPkgSet.contains(rareId)) {
            return;
        }
        implPkgSet.add(rareId);
        rareImplList.add(rareModuleImpl);
    }

    public List<RareInterface> getRareImplList() {
        return rareImplList;
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
    public DataBeanCreator beanCreator(String annotateBeanPath) {
        for (RareInterface impl : rareImplList) {
            DataBeanCreator bean = impl.instanceCreator().beanCreator(annotateBeanPath);
            if (bean != null) {
                return bean;
            }
        }
        return null;
    }

    @Override
    public DataBeanCreator beanGenerate(String pkgName) {
        for (RareInterface impl : rareImplList) {
            DataBeanCreator bean = impl.instanceCreator().beanGenerate(pkgName);
            if (bean != null) {
                return bean;
            }
        }
        return null;
    }

    @Override
    public RouteBean methodAskRouteBean(String annotationPath, String pkgName) {
        for (RareInterface impl : rareImplList) {
            RouteBean bean = impl.methodBeans().methodAskRouteBean(annotationPath, pkgName);
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
    public Object proxy(Object instance, Checker checker, String annotationPath, Object... parameters) {
        for (RareInterface impl : rareImplList) {
            Object methodReturn = impl.methodProxy().proxy(instance, checker, annotationPath, parameters);
            if (methodReturn == null) {
                continue;
            }
            if (methodReturn instanceof MethodReturn) {
                return null;
            }
            return methodReturn;
        }
        return null;
    }

    @Override
    public Class<?> getClazz(String annotationPath) {
        for (RareInterface impl : rareImplList) {
            Class<?> clazz = impl.routerClazz().getClazz(annotationPath);
            if (clazz != null) {
                return clazz;
            }
        }
        return null;
    }
}
