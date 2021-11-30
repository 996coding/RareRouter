package com.lxf.manager;

import com.lxf.init.RouteBean;
import com.lxf.nozzle.RouterTable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MasterRouteTable implements RouterTable {
    private static volatile MasterRouteTable masterTable = null;
    private final int TypeClass = 0;
    private final int TypeMethodAsk = 1;
    private final int TypeMethodImp = 2;

    private List<RouterTable> tableList = null;
    private Set<String> routerIdSet;

    private MasterRouteTable() {
        tableList = new ArrayList<>();
        routerIdSet = new HashSet<>();
        routerIdSet.add(routerID());
    }

    public static MasterRouteTable getInstance() {
        if (masterTable == null) {
            synchronized (MasterRouteTable.class) {
                if (masterTable == null) {
                    masterTable = new MasterRouteTable();
                }
            }
        }
        return masterTable;
    }


    @Override
    public boolean isMasterTable() {
        return true;
    }

    @Override
    public final String routerID() {
        return "master_router_table";
    }

    @Override
    public RouteBean clazzTableItem(String annotationPath) {
        return getTableItem(TypeClass, annotationPath);
    }

    @Override
    public RouteBean methodAskTableItem(String pkgName) {
        return getTableItem(TypeMethodAsk, pkgName);
    }

    @Override
    public RouteBean methodImpTableItem(String annotationPath) {
        return getTableItem(TypeMethodImp, annotationPath);
    }

    public void addSlaveTable(RouterTable slaveTable) {
        if (routerIdSet.contains(slaveTable.routerID())) {
            return;
        }
        this.tableList.add(0, slaveTable);
    }

    private RouteBean getTableItem(int type, String param) {
        if (tableList == null || tableList.size() == 0) {
            return null;
        }
        for (int i = 0; i < tableList.size(); i++) {
            RouteBean aimBean = null;
            if (type == TypeClass) {
                aimBean = tableList.get(i).clazzTableItem(param);
            } else if (type == TypeMethodAsk) {
                aimBean = tableList.get(i).methodAskTableItem(param);
            } else if (type == TypeMethodImp) {
                aimBean = tableList.get(i).methodImpTableItem(param);
            }
            if (aimBean != null) {
                return aimBean;
            }
        }
        return null;
    }

}
