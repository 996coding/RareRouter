package com.lxf.Router;

import com.lxf.Process.genJava.GenConfig;
import com.lxf.Process.genJava.GenIntentStarter;
import com.lxf.Process.genJava.GenRareAdder;
import com.lxf.protocol.RouteBean;
import com.lxf.protocol.*;
import com.lxf.template.RareAdder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RareCore implements ClassBeans, MethodBeans, RouterClazz, MethodProxy, InstanceCreator, IntentStarter {
    private static final RareCore instance = new RareCore();
    private List<RareInterface> rareImplList_Local;
    private List<RareInterface> rareImplList_OnLine;
    private Set<String> implPkgSet;
    private IntentStarter intentStarter;
    private List<String> localRares;

    private RareCore() {
        rareImplList_Local = new ArrayList<>();
        rareImplList_OnLine = new ArrayList<>();
        implPkgSet = new HashSet<>();
        localRares = new ArrayList<>();
//        autoAddRareImpl();
    }

    public static RareCore getRareCore() {
        return instance;
    }

    public static void addRareImpl(RareInterface rareImpl) {
        if (rareImpl == null) {
            return;
        }
        String pkgName = rareImpl.getClass().getName();
        synchronized (RareCore.class) {
            instance.putRareImpl(pkgName, rareImpl);
        }
    }

    public void autoAddRareImpl() {
        if (RareAdder.enable) {
            localRares.addAll(RareAdder.localRares);
        } else {
            try {
                Class<?> backUp = Class.forName(GenConfig.PACKAGE_JAVA_CODE + "." + GenRareAdder.CLASS_NAME_BACK_UP);
                List<String> stringList = (List<String>) backUp.getField("localRares").get(null);
                localRares.addAll(stringList);
            } catch (Exception e) {

            }
        }
    }

    private void putRareImpl(String rareId, RareInterface rareModuleImpl) {
        if (implPkgSet.contains(rareId)) {
            return;
        }
        implPkgSet.add(rareId);
        if (localRares.contains(rareModuleImpl.getClass().getName())) {
            rareImplList_Local.add(0, rareModuleImpl);
        } else {
            rareImplList_OnLine.add(0, rareModuleImpl);
        }

    }

    public List<RareInterface> getRareImplListLocal() {
        return rareImplList_Local;
    }

    public List<RareInterface> getRareImplListOnLine() {
        return rareImplList_OnLine;
    }

    @Override
    public RouteBean classRouteBean(String annotationPath) {
        for (RareInterface impl : rareImplList_OnLine) {
            RouteBean bean = impl.classBeans().classRouteBean(annotationPath);
            if (bean != null) {
                return bean;
            }
        }
        for (RareInterface impl : rareImplList_Local) {
            RouteBean bean = impl.classBeans().classRouteBean(annotationPath);
            if (bean != null) {
                return bean;
            }
        }
        return null;
    }

    @Override
    public DataBeanCreator beanCreator(String annotateBeanPath) {
        for (RareInterface impl : rareImplList_OnLine) {
            DataBeanCreator bean = impl.instanceCreator().beanCreator(annotateBeanPath);
            if (bean != null) {
                return bean;
            }
        }
        for (RareInterface impl : rareImplList_Local) {
            DataBeanCreator bean = impl.instanceCreator().beanCreator(annotateBeanPath);
            if (bean != null) {
                return bean;
            }
        }
        return null;
    }

    @Override
    public DataBeanCreator beanGenerate(String pkgName) {
        for (RareInterface impl : rareImplList_OnLine) {
            DataBeanCreator bean = impl.instanceCreator().beanGenerate(pkgName);
            if (bean != null) {
                return bean;
            }
        }
        for (RareInterface impl : rareImplList_Local) {
            DataBeanCreator bean = impl.instanceCreator().beanGenerate(pkgName);
            if (bean != null) {
                return bean;
            }
        }
        return null;
    }

    @Override
    public RouteBean methodAskRouteBean(String annotationPath, String pkgName) {
        for (RareInterface impl : rareImplList_OnLine) {
            RouteBean bean = impl.methodBeans().methodAskRouteBean(annotationPath, pkgName);
            if (bean != null) {
                return bean;
            }
        }
        for (RareInterface impl : rareImplList_Local) {
            RouteBean bean = impl.methodBeans().methodAskRouteBean(annotationPath, pkgName);
            if (bean != null) {
                return bean;
            }
        }
        return null;
    }

    @Override
    public RouteBean methodReplyRouteBean(String annotationPath) {
        for (RareInterface impl : rareImplList_OnLine) {
            RouteBean bean = impl.methodBeans().methodReplyRouteBean(annotationPath);
            if (bean != null) {
                return bean;
            }
        }
        for (RareInterface impl : rareImplList_Local) {
            RouteBean bean = impl.methodBeans().methodReplyRouteBean(annotationPath);
            if (bean != null) {
                return bean;
            }
        }
        return null;
    }

    @Override
    public MethodExecutor proxy(String annotationPath) {
        for (RareInterface impl : rareImplList_OnLine) {
            MethodExecutor methodProxy = impl.methodProxy().proxy(annotationPath);
            if (methodProxy == null) {
                continue;
            }
            return methodProxy;
        }
        for (RareInterface impl : rareImplList_Local) {
            MethodExecutor methodProxy = impl.methodProxy().proxy(annotationPath);
            if (methodProxy == null) {
                continue;
            }
            return methodProxy;
        }
        return null;
    }

    @Override
    public Class<?> getClazz(String annotationPath) {
        for (RareInterface impl : rareImplList_OnLine) {
            Class<?> clazz = impl.routerClazz().getClazz(annotationPath);
            if (clazz != null) {
                return clazz;
            }
        }
        for (RareInterface impl : rareImplList_Local) {
            Class<?> clazz = impl.routerClazz().getClazz(annotationPath);
            if (clazz != null) {
                return clazz;
            }
        }
        return null;
    }

    @Override
    public void startIntent(Object context, Class<?> clazz) {
        if (intentStarter == null) {
            try {
                intentStarter = (IntentStarter) Class.forName(GenConfig.PACKAGE + "." + GenIntentStarter.CLASS_NAME).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (intentStarter != null) {
            intentStarter.startIntent(context, clazz);
        }
    }
}
