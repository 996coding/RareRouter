package com.lxf.protocol;

import com.lxf.init.RouteBean;

public interface MethodBeans {
    RouteBean methodAskRouteBean(String annotationPath, String pkgName);

    RouteBean methodReplyRouteBean(String annotationPath);
}
