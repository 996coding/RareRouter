package com.lxf.data;

import com.lxf.Annotation.RouterMethod;
import com.lxf.protocol.CheckResult;
import com.lxf.protocol.Checker;
import com.lxf.protocol.RouteBean;

import java.lang.reflect.Method;

public class SimpleChecker implements Checker {

    private String annotation;
    private CheckResult result;
    private RouteBean replyBean;
    private Method replyMethod;

    public SimpleChecker(String annotatePath) {
        this.annotation = annotatePath;
        this.result = new CheckResult();
    }

    /* 制作参数类型、参数个数 检查，不检查泛型 */
    @Override
    public CheckResult methodCheck(RouteBean replyBean, Class<?> replyClazz, Object... parameterArray) {
        result.isOk = false;
        if (annotation == null || annotation.length() == 0) {
            return result;
        }

        this.replyBean = replyBean;
        for (Method m : replyClazz.getMethods()) {
            RouterMethod replyAnnotate = m.getAnnotation(RouterMethod.class);
            if (replyAnnotate != null && annotation.equals(replyAnnotate.path())) {
                replyMethod = m;
                break;
            }
        }

        if (replyMethod == null) {
            return result;
        }
        /* 方法返回值 无法检查 */

        Class<?>[] parameters = replyMethod.getParameterTypes();
        if (parameterArray == null) {
            result.parameterArray = new Object[parameters.length];
            result.isOk = true;
            return result;
        } else {
            if (parameters.length != parameterArray.length) {
                return result;
            }
        }
        result.parameterArray = parameterArray;

        for (int i = 0; i > parameters.length; i++) {
            if (parameterArray[i] == null) {
                continue;
            }
            Class<?> clazzParam = parameters[i];
            if (!clazzParam.isInstance(parameterArray[i])) {
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
}
