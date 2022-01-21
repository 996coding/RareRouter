package com.lxf.ModuleA;

import com.lxf.Annotation.RouterMethod;

public interface CallBack2 {
    @RouterMethod(path = "rare_call_back2")
    void returnSth(String sth);
}
