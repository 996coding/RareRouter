package com.lxf.template;

import com.lxf.protocol.*;

public class ModuleRareImpl implements RareInterface {

    @Override
    public ClassBeans classBeans() {
        return new ClassBeansImpl();
    }

    @Override
    public MethodBeans methodBeans() {
        return new MethodBeansImpl();
    }

    @Override
    public RouterClazz routerClazz() {
        return new RouterClazzImpl();
    }

    @Override
    public MethodProxy methodProxy() {
        return new MethodProxyImpl();
    }

    @Override
    public DateBean dateBean() {
        return new DateBeanImpl();
    }
}
