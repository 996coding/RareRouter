package com.lxf.nozzle;

import com.lxf.init.RouteBean;

public interface RouterTable {
    boolean isMasterTable();

    String routerID();

    RouteBean clazzTableItem(String annotationPath);

    RouteBean methodAskTableItem(String pkgName);

    RouteBean methodImpTableItem(String annotationPath);
}
