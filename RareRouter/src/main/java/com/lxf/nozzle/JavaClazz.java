package com.lxf.nozzle;

public interface JavaClazz {
    RouteTable getRouteTable();

    IntentStart getIntentStarter();

    ClsProvider getClazzProvider();

    FunImpClazz getFunImpClazz();

    ParamInstance getParamObjCreator();
}
