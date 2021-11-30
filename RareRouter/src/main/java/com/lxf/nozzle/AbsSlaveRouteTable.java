package com.lxf.nozzle;

import com.lxf.init.RouteBean;
import com.lxf.manager.MasterRouteTable;

public abstract class AbsSlaveRouteTable implements RouterTable {
    @Override
    public final boolean isMasterTable() {
        return false;
    }

    @Override
    public abstract int routerID();

    @Override
    public abstract RouteBean clazzTableItem(String annotationPath);

    @Override
    public abstract RouteBean methodAskTableItem(String pkgName);

    @Override
    public abstract RouteBean methodImpTableItem(String annotationPath);

    public final void addSalveToMaster() {
        MasterRouteTable.getInstance().addSlaveTable(this);
    }

    public final RouteBean create(String type, String isInterface, String path, String pkgName, String method, String returnType, String... params) {
        return RouteBean.create(type, isInterface, path, pkgName, method, returnType, params);
    }
}
