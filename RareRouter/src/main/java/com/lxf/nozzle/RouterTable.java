package com.lxf.nozzle;

import com.lxf.init.RouteBean;

public interface RouterTable {
    boolean isMasterTable();

    int routerID();

    RouteBean clazzTableItem(String annotationPath);

    RouteBean methodAskTableItem(String pkgName);

    RouteBean methodImpTableItem(String annotationPath);
}
