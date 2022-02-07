package com.lxf.ModuleB;

import com.lxf.Annotation.RouterMethod;

public interface CallBack2 {
    @RouterMethod(path = "rare_call_back2")
    void returnSth(String sth);

    @RouterMethod(path = "rare_call_back22")
    void returnCallBackSth(String sth, CallBack3 callBack3);
}
