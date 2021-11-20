package com.lxf.LxfRouter;

import com.lxf.Annotation.RouterMethod;
import com.lxf.data.LxfDataFactory;
import com.lxf.data.RouterParcelable;
import com.lxf.init.RouteBean;
import com.lxf.nozzle.JavaClassGetter;
import com.lxf.utils.ReflectTools;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class DataHandler implements InvocationHandler {

    private Object implObj;
    private List<RouteBean> routeBeans;
    private List<RouteBean> requestBeans;

    public DataHandler(Object implObj, List<RouteBean> beans, List<RouteBean> requestBeans) {
        this.implObj = implObj;
        this.routeBeans = beans;
        this.requestBeans = requestBeans;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }
        if (routeBeans == null || routeBeans.size() == 0 || requestBeans == null || requestBeans.size() == 0) {
            return null;
        }

        Annotation[] annotations = method.getAnnotations();
        RouterMethod annotation = null;
        for (Annotation tmp : annotations) {
            if (tmp.annotationType().equals(RouterMethod.class)) {
                annotation = (RouterMethod) tmp;
                break;
            }
        }
        if (annotation == null) {
            return null;
        }
        String path = annotation.path();
        RouteBean reqBean = null;
        for (RouteBean bean : requestBeans) {
            if (bean.path.equals(path)) {
                reqBean = bean;
                break;
            }
        }
        if (reqBean == null) {
            return null;
        }

        RouteBean currentBean = null;
        for (RouteBean bean : routeBeans) {
            if (bean.path.equals(path)) {
                currentBean = bean;
                break;
            }
        }

        if (currentBean == null) {
            return null;
        }

        Class<?> clazzInterface = null;
        try {
            clazzInterface = Class.forName(currentBean.pkgName);
        } catch (ClassNotFoundException e) {
        }
        if (clazzInterface == null) {
            return null;
        }

        Method[] implMethodArr = clazzInterface.getMethods();
        if (implMethodArr == null || implMethodArr.length == 0) {
            return null;
        }

        Method methodImpl = null;
        for (Method implM : implMethodArr) {
            if (implM.getName().equals(currentBean.method)) {
                methodImpl = implM;
            }
        }
        if (methodImpl == null) {
            return null;
        }
        if (args == null || args.length == 0) {
            return methodImpl.invoke(implObj, args);
        }
        Object[] argsNew = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            Object obj = args[i];
            if (obj == null) {
                argsNew[i] = null;
                continue;
            }
            if (currentBean.paramsList.get(i).equals(reqBean.paramsList.get(i))) {
                argsNew[i] = obj;
                continue;
            }
            if (DataType.isBasicType(obj.getClass().getName())) {
                argsNew[i] = obj;
            } else if (obj instanceof RouterParcelable) {
                //转换类型
                Object aimObj = JavaClassGetter.getParamObjCreator().create(currentBean.paramsList.get(i));
                if (aimObj == null) {
                    aimObj = ReflectTools.newInstance(currentBean.paramsList.get(i));
                }

                if (aimObj == null || !(aimObj instanceof RouterParcelable)) {
                    return null;
                }
                LxfDataFactory.convert((RouterParcelable) obj, (RouterParcelable) aimObj);
                argsNew[i] = aimObj;
            } else if (DataType.isListType(currentBean.paramsList.get(i))) {
                argsNew[i] = obj;
                String clazzLastChild = DataType.getListLastChildType(currentBean.paramsList.get(i));
                if (!(DataType.isBasicType(clazzLastChild) || DataType.isList(clazzLastChild))) {
                    /*
                    1、直接赋值；
                    2、list广度遍历，在遍历中数据转换；
                    3、编译时，已经排除有其他类型的数据。
                     */
                    Object template = JavaClassGetter.getParamObjCreator().create(clazzLastChild);
                    if (template == null) {
                        template = ReflectTools.newInstance(clazzLastChild);
                    }
                    if (template == null || !(template instanceof RouterParcelable)) {
                        return null;
                    }
                    argsNew[i] = LxfDataFactory.convertListEle((List) obj, (RouterParcelable) template, clazzLastChild);
                }
            } else {
                argsNew[i] = obj;
            }
        }

        return methodImpl.invoke(implObj, argsNew);
    }
}
