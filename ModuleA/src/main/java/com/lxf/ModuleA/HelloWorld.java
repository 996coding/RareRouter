package com.lxf.ModuleA;

import android.content.Context;

import com.lxf.Annotation.RouterMethod;

public interface HelloWorld {
    @RouterMethod(path = "say_hello_world")
    void say(Context context, String content);

    @RouterMethod(path = "say_hello_world2")
    void say( Context context, String str, CallBack2 callBack2);
}
