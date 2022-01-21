package com.lxf.ModuleB;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.lxf.Annotation.RouterMethod;

public class HelloWorldImpl {
    @RouterMethod(path = "say_hello_world")
    public void say(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }

    @RouterMethod(path = "say_hello_world2")
    public void say(String str, CallBack2 callBack2) {
        callBack2.returnSth(str + "->HelloWorldImpl");
    }
}