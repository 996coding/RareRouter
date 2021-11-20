package com.lxf.ModuleA;

import com.lxf.Annotation.RouterMethod;

public interface CallBack {
    @RouterMethod(path = "call_back_success")
    void onSuccess(Person person);

    @RouterMethod(path = "call_back_fail")
    void onFailed();
}
