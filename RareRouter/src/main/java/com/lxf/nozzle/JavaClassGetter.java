package com.lxf.nozzle;

import com.lxf.template.JavaCode;

public class JavaClassGetter {
    public static RouteTable getRouteTable() {
        return getJavaCls().getRouteTable();
    }

    public static IntentStart getIntentStarter() {
        return getJavaCls().getIntentStarter();
    }

    public static ClsProvider getClazzProvider() {
        return getJavaCls().getClazzProvider();
    }

    public static FunImpClazz getFunImpClazz() {
        return getJavaCls().getFunImpClazz();
    }

    public static ParamInstance getParamObjCreator() {
        return getJavaCls().getParamObjCreator();
    }

    private static JavaClazz javaClazz;

    private static JavaClazz getJavaCls() {
        if (javaClazz == null) {
            JavaCode javaCode = new JavaCode();
            if (javaCode.isValid) {
                javaClazz = javaCode;
                return javaClazz;
            }
            /*
                不在生成JavaCodeMajor，JavaCode必须生成。
             */
        }
        return javaClazz;
    }

    public static void setJavaClazz(JavaClazz javaClazzImp) {
        javaClazz = javaClazzImp;
    }
}
