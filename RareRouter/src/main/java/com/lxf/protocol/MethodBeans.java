package com.lxf.protocol;

import com.lxf.init.RouteBean;

public interface MethodBeans {
    RouteBean methodAskRouteBean(String pkgName);

    RouteBean methodReplyRouteBean(String annotationPath);
}
