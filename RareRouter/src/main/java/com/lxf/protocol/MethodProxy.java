package com.lxf.protocol;

public abstract class MethodProxy {
    public Object methodReturn = null;

    public abstract void proxy(Object... objects);
}
