package com.lxf.RareRouter;

import com.lxf.Annotation.RouterMethod;
import com.lxf.data.LxfDataFactory;
import com.lxf.data.RouterParcelable;
import com.lxf.init.RouteBean;
import com.lxf.init.RouteMap;
import com.lxf.log.Log;
import com.lxf.nozzle.JavaClassGetter;
import com.lxf.response.AimClass;
import com.lxf.response.RequestProxy;
import com.lxf.utils.ReflectTools;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;


public class RouterHandler implements InvocationHandler {

    private Class<?> service;

    public RouterHandler(Class<?> service) {
        this.service = service;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
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
        RouteBean bean = RouteMap.getInstance().getClassRouteBean(path);
        if (bean == null) {
            return null;
        }
        Log.e(bean.toString());


        //旧方式
//        Class<?> cls = null;
//        try {
//            cls = Class.forName(bean.pkgName);
//        } catch (ClassNotFoundException e) {
//        }
//        if (cls == null) {
//            return null;
//        }
//
//        Method[] methods = cls.getMethods();
//        Method methodImp = null;
//        for (Method methodTmp : methods) {
//            if (methodTmp.getName().equals(bean.method)) {
//                methodImp = methodTmp;
//                break;
//            }
//        }
//        if (methodImp == null) {
//            return null;
//        }

        AimClass aimClass = JavaClassGetter.getFunImpClazz().create(RequestProxy.getProxyPkgName(bean.pkgName, bean.method, bean.paramsList));
        if (aimClass == null) {
            return null;
        }


        /*
        传递参数有3种情况：
        1、基本数据类型；
        2、复杂数据类型；
        3、List结构；
        4、回调接口；
        5、数组；(暂时不考虑)
         */
        /*
        方法检查：
        1、检查方法参数个数；
        2、检查每个参数类型；
            2.1、基本数据类型拆装箱检查；
            2.2、复杂数据类型是否都继承RouterParcelable；
            2.3、List层级和泛型类型；
            2.4、回调接口检查；
        3、检查返回值类型；
         */

        RouteBean requestBean = RouteMap.getInstance().getInterfaceRouteBean(path, service.getName());
        if (!checkParams(requestBean, bean)) {
            return null;
        }

        if (args == null || args.length == 0) {
            aimClass.responseMethod(args);
            return aimClass.object;
        }

        Object[] argsNew = new Object[args.length];
        List<String> params = bean.paramsList;
        for (int i = 0; i < args.length; i++) {
            Object obj = args[i];
            if (obj == null) {
                argsNew[i] = null;
                continue;
            }
            if (requestBean.paramsList.get(i).equals(bean.paramsList.get(i))) {
                argsNew[i] = obj;
                continue;
            }
            if (DataType.isBasicType(params.get(i))) {
                argsNew[i] = obj;
            } else if (obj instanceof RouterParcelable) {
                //转换类型
                Object aimObj = JavaClassGetter.getParamObjCreator().create(bean.paramsList.get(i));
                if (aimObj == null) {
                    aimObj = ReflectTools.newInstance(bean.paramsList.get(i));
                }

                if (aimObj == null || !(aimObj instanceof RouterParcelable)) {
                    return null;
                }
                LxfDataFactory.convert((RouterParcelable) obj, (RouterParcelable) aimObj);
                argsNew[i] = aimObj;
            } else if (DataType.isListType(params.get(i))) {
                argsNew[i] = obj;
                String clazzLastChild = DataType.getListLastChildType(params.get(i));
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
            } else if (DataType.isInstanceOfInterface(requestBean.paramsList.get(i))) {
                DataHandler dataHandler = new DataHandler(obj,
                        RouteMap.getInstance().getInterfaceRouteBeans(requestBean.paramsList.get(i)),
                        RouteMap.getInstance().getInterfaceRouteBeans(bean.paramsList.get(i)));
                argsNew[i] = create(params.get(i), dataHandler);
            } else {
                argsNew[i] = null;
            }
        }
//        return methodImp.invoke(cls.newInstance(), argsNew);
        aimClass.responseMethod(argsNew);
        return aimClass.object;
    }

    private boolean checkParams(RouteBean request, RouteBean response) {
        Log.e("request   " + request);
        Log.e("response   " + response);
        return true;
    }

    private static Object create(String clazzName, DataHandler handler) {
        Class<?> clazzInterface = null;
        try {
            clazzInterface = Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
        }
        if (clazzInterface == null) {
            return null;
        }
        return Proxy.newProxyInstance(clazzInterface.getClassLoader(),
                new Class<?>[]{clazzInterface},
                handler);
    }
}
