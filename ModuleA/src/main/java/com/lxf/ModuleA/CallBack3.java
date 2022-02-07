package com.lxf.ModuleA;

import com.lxf.Annotation.RouterMethod;

public interface CallBack3 {
    @RouterMethod(path = "rare_call_back3")
    void returnSth(String sth);
}
