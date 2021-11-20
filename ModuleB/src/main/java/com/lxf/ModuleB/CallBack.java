package com.lxf.ModuleB;

import com.lxf.Annotation.RouterMethod;

public interface CallBack {
    @RouterMethod(path = "call_back_success")
    void onSuccess(People people);

    @RouterMethod(path = "call_back_fail")
    void onFailed();
}
