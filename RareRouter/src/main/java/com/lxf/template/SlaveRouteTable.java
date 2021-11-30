package com.lxf.template;

import com.lxf.init.RouteBean;
import com.lxf.nozzle.AbsSlaveRouteTable;

public class SlaveRouteTable extends AbsSlaveRouteTable {
    @Override
    public int routerID() {
        return 0;
    }

    @Override
    public RouteBean clazzTableItem(String annotationPath) {
        if ("hello_get_info".equals(annotationPath)) {
            create("1", "0", "hello_get_info", "com.lxf.ModuleB.QueryInfo", "getPersonName_Imp", "void", "android.content.Context", "int", "java.util.List<com.lxf.ModuleB.People>");
        }
        return null;
    }

    @Override
    public RouteBean methodAskTableItem(String pkgName) {
        return null;
    }

    @Override
    public RouteBean methodImpTableItem(String annotationPath) {
        return null;
    }
}
