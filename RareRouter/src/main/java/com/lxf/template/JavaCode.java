package com.lxf.template;

import com.lxf.nozzle.*;

public class JavaCode implements JavaClazz {

    public boolean isValid = false;

    public JavaCode(){
        if (isValid){
            JavaClassGetter.setJavaClazz(this);
        }
    }

    @Override
    public RouteTable getRouteTable() {
        return null;
    }

    @Override
    public IntentStart getIntentStarter() {
        return null;
    }

    @Override
    public ClsProvider getClazzProvider() {
        return null;
    }

    @Override
    public FunImpClazz getFunImpClazz() {
        return null;
    }

    @Override
    public ParamInstance getParamObjCreator() {
        return null;
    }
}
