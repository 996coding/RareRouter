package com.lxf.ModuleA;

import android.content.Context;

import com.lxf.Annotation.RouterMethod;

import java.util.List;

public interface HelloWorld {
    @RouterMethod(path = "say_hello_world")
    String say(String content);

    @RouterMethod(path = "say_hello_world2")
    void say(Context context, String str, CallBack2 callBack2);

    @RouterMethod(path = "say_hello_world3")
    void say(Context context, List<Person> list);
}
