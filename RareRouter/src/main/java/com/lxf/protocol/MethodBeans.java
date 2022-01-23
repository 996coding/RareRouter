package com.lxf.protocol;

public interface MethodBeans {
    RouteBean methodAskRouteBean(String annotationPath, String pkgName);

    RouteBean methodReplyRouteBean(String annotationPath);
}
