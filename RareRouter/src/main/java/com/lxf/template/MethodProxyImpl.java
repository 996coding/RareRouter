package com.lxf.template;

import com.lxf.protocol.MethodProxy;

public class MethodProxyImpl implements MethodProxy {
    @Override
    public Object proxy(Object instance, String annotationPath, Object... parameters) {
//        if ("start_activity".equals(annotationPath)) {
//            com.lxf.ModuleB.QueryInfo proxyInstance = null;
//            if (instance != null && instance instanceof com.lxf.ModuleB.QueryInfo){
//                proxyInstance = (com.lxf.ModuleB.QueryInfo) instance;
//            }else {
//                proxyInstance = new com.lxf.ModuleB.QueryInfo();
//            }
//            proxyInstance.startActivity((android.content.Context) parameters[0]);
//            return null;
//        }
//        if ("hello_get_info".equals(annotationPath)) {
//            com.lxf.ModuleB.QueryInfo proxyInstance = null;
//            if (instance != null && instance instanceof com.lxf.ModuleB.QueryInfo){
//                proxyInstance = (com.lxf.ModuleB.QueryInfo) instance;
//            }else {
//                proxyInstance = new com.lxf.ModuleB.QueryInfo();
//            }
//            proxyInstance.getPersonName_Imp((android.content.Context) parameters[0], (int) parameters[1], (java.util.List<com.lxf.ModuleB.People>) parameters[2]);
//            return null;
//        }
//        if ("query_info".equals(annotationPath)) {
//            com.lxf.ModuleB.QueryInfo proxyInstance = null;
//            if (instance != null && instance instanceof com.lxf.ModuleB.QueryInfo){
//                proxyInstance = (com.lxf.ModuleB.QueryInfo) instance;
//            }else {
//                proxyInstance = new com.lxf.ModuleB.QueryInfo();
//            }
//            return proxyInstance.serverQueryInfo((java.lang.Integer) parameters[0], (com.lxf.ModuleB.CallBack) parameters[1]);
//        }
        return null;
    }
}
