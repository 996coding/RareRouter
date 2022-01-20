package com.lxf.data;

import com.lxf.Annotation.RouterMethod;
import com.lxf.init.RouteBean;
import com.lxf.protocol.CheckResult;
import com.lxf.protocol.Checker;

import java.lang.reflect.Method;

public class DataChecker implements Checker {

    private RouteBean askBean, replyBean;
    private Method askMethod, replyMethod;
    private String annotation;

    public DataChecker(RouteBean askBean, Method askMethod) {
        this.askBean = askBean;
        this.askMethod = askMethod;
        this.annotation = askMethod.getAnnotation(RouterMethod.class).path();
    }

    @Override
    public CheckResult methodCheck(RouteBean replyBean, Class<?> replyClazz, Object... parameterArray) {
        CheckResult result = new CheckResult();
        result.isOk = false;

        if (annotation == null || annotation.length() == 0) {
            return result;
        }

        this.replyBean = replyBean;
        for (Method m : replyClazz.getMethods()) {
            if (annotation.equals(m.getAnnotation(RouterMethod.class))) {
                replyMethod = m;
                break;
            }
        }

        if (replyMethod == null) {
            return result;
        }

        if (!returnCheck()) {
            return result;
        }
        Class<?>[] parameters = askMethod.getParameterTypes();
        if (parameters.length != parameterArray.length) {
            return result;
        }
        result.parameterArray = new Object[parameterArray.length];
        for (int i = 0; i < parameters.length; i++) {
            if (!parameterCheck(replyBean.paramsList.get(i), parameters[i], i, result.parameterArray, parameterArray)) {
                return result;
            }
        }
        result.isOk = true;
        return result;
    }

    @Override
    public boolean instanceCheck(Object instance, Class<?> clazz) {
        if (instance != null && clazz.isInstance(instance)) {
            return true;
        }
        return false;
    }


    private boolean returnCheck() {
        if (askBean.returnType.equals(replyBean.returnType)) {
            return true;
        }
        //暂时先检查通过
        return false;
    }

    private boolean parameterCheck(String parameter, Class<?> askParameter, int index, Object[] resultParams, Object[] askParams) {
        //暂时先检查通过

        return true;
    }
}
