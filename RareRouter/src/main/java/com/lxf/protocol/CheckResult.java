package com.lxf.protocol;

public final class CheckResult {
    public boolean isOk;

    public Object[] parameterArray;

    public CheckResult(boolean isOk, Object... parameters) {
        this.isOk = isOk;
        this.parameterArray = parameters;
    }
}
