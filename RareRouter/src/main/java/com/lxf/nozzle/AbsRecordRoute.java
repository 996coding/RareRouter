package com.lxf.nozzle;

import com.lxf.init.RouteBean;

public abstract class AbsRecordRoute implements RouteTable {
    public RouteBean create(String type, String isInterface, String path, String pkgName, String method, String returnType, String... params) {
        return RouteBean.create(type, isInterface, path, pkgName, method, returnType, params);
    }
}
