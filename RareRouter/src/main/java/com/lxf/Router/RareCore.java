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
    private long APP_BUILD_TIME = 0l;
    private List<RareInterface> rareImplList_Local;
    private List<RareInterface> rareImplList_OnLine;
    private Set<String> implPkgSet;
    private IntentStarter intentStarter;

    private RareCore() {
        rareImplList_Local = new ArrayList<>();
        rareImplList_OnLine = new ArrayList<>();
        implPkgSet = new HashSet<>();
//        autoAddRareImpl();
    }

    public static RareCore getRareCore() {
        return instance;
    }

    public static void addRareImpl(RareInterface rareImpl, long buildTime) {
        if (rareImpl == null) {
            return;
        }
        String pkgName = rareImpl.getClass().getName();
        synchronized (RareCore.class) {
            instance.putRareImpl(pkgName, rareImpl, buildTime);
        }
    }

    public void autoAddRareImpl() {
        if (RareAdder.enable) {
            APP_BUILD_TIME = RareAdder.app_build_time;
        } else {
            try {
                Class<?> backUp = Class.forName(GenConfig.PACKAGE_JAVA_CODE + "." + GenRareAdder.CLASS_NAME_BACK_UP);
                APP_BUILD_TIME = backUp.getField("app_build_time").getLong("app_build_time");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void putRareImpl(String rareId, RareInterface rareModuleImpl, long buildTime) {
        if (implPkgSet.contains(rareId)) {
            return;
        }
        implPkgSet.add(rareId);
        if (buildTime <= APP_BUILD_TIME) {
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
    public Object proxy(Object instance, Checker checker, String annotationPath, Object... parameters) {
        for (RareInterface impl : rareImplList_OnLine) {
            Object methodReturn = impl.methodProxy().proxy(instance, checker, annotationPath, parameters);
            if (methodReturn == null) {
                continue;
            }
            if (methodReturn instanceof MethodReturn) {
                return null;
            }
            return methodReturn;
        }
        for (RareInterface impl : rareImplList_Local) {
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
