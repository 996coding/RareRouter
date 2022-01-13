package com.lxf.template;


import com.lxf.protocol.ClassBeans;
import com.lxf.protocol.RouterClazz;
import com.lxf.protocol.MethodBeans;
import com.lxf.protocol.DateBean;
import com.lxf.protocol.MethodProxy;
import com.lxf.protocol.RareInterface;

public class DemoRare implements RareInterface {

    @Override
    public String rareID() {
        return null;
    }

    @Override
    public ClassBeans classBeans() {
        return null;
    }

    @Override
    public MethodBeans methodBeans() {
        return null;
    }

    @Override
    public RouterClazz routerClazz() {
        return null;
    }

    @Override
    public MethodProxy methodProxy() {
        return null;
    }

    @Override
    public DateBean dateBean() {
        return null;
    }
}
