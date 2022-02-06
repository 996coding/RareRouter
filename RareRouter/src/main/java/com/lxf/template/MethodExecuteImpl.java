package com.lxf.template;

import com.lxf.protocol.*;


public class MethodExecuteImpl implements MethodExecute {

    /*
        Annotation Path = say_hello_world3
    */

    public final RouteBean bean = RouteBean.create("101", "0", "say_hello_world3", "com.lxf.ModuleB.HelloWorldImpl", "say", "void", "android.content.Context", "java.util.List<com.lxf.ModuleB.People>");


    @Override
    public Object execute(Object instance, Checker checker, Object... parameters) {

        return MethodReturn.NULL;
    }
}
