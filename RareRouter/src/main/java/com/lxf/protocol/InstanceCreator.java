package com.lxf.protocol;

public interface InstanceCreator {
    DataBeanCreator beanCreator(String annotateBeanPath);
}
