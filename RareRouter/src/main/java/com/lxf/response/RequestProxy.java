package com.lxf.response;

import com.lxf.LxfRouter.DataType;
import com.lxf.init.RouteBean;
import com.lxf.nozzle.JavaClassGetter;

import java.util.List;

public class RequestProxy {

    private static String getProxyPkgName(RouteBean bean) {
        return getProxyPkgName(bean.pkgName, bean.method, bean.paramsList);
    }

    public static String getProxyPkgName(String pkgName, String method, List<String> paramsList) {
        StringBuilder sb = new StringBuilder();
        sb.append(pkgName);
        sb.append("_");
        sb.append(method);
        for (String param : paramsList) {
            sb.append("_");
            if (DataType.isBasicType(param)) {
                int indexPoint = param.lastIndexOf(".");
                if (indexPoint > 0) {
                    sb.append(param.substring(indexPoint + 1));
                } else {
                    sb.append(param);
                }
            } else if (DataType.isListType(param)) {
                sb.append("List");
            } else {
                if (param.contains(".")){
                    sb.append(param.replace(".", "_"));
                }else {
                    sb.append(param);
                }
            }
        }
        return sb.toString();
    }

    public static Object methodInvoke(RouteBean bean, Object[] args) {
        AimClass aimClass = JavaClassGetter.getFunImpClazz().create(getProxyPkgName(bean));
        if (aimClass == null) {
            return null;
        }

        aimClass.responseMethod(args);
        return aimClass.object;
    }
}
