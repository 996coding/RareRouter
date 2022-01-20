package com.lxf.data;

import com.lxf.init.RouteBean;
import com.lxf.protocol.CheckResult;
import com.lxf.protocol.Checker;

import java.lang.reflect.Method;

public class DataChecker implements Checker {

    private RouteBean askBean, replyBean;
    private Method askMethod;

    public DataChecker(RouteBean askBean, Method askMethod) {
        this.askBean = askBean;
        this.askMethod = askMethod;
    }

    @Override
    public CheckResult methodCheck(RouteBean bean, Object... parameterArray) {
        CheckResult result = new CheckResult();
        result.isOk = false;
        this.replyBean = bean;
        if (!returnCheck()) {
            return result;
        }
        Class<?>[] parameters = askMethod.getParameterTypes();
        if (parameters.length != parameterArray.length) {
            return result;
        }
        result.parameterArray = new Object[parameterArray.length];
        for (int i = 0; i < parameters.length; i++) {
            if (!parameterCheck(bean.paramsList.get(i), parameters[i], i, result.parameterArray, parameterArray)) {
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
