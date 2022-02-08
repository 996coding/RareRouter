package com.lxf.ModuleB;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.lxf.Annotation.RouterMethod;

import java.util.List;

public class HelloWorldImpl {

    @RouterMethod(path = "new_page_demo_activity")
    public void startNewPage(Context context) {
        Intent intent = new Intent(context, DemoActivity.class);
        context.startActivity(intent);
    }

    @RouterMethod(path = "say_hello_world")
    public String say(String content) {
        return content + "->HelloWorldImpl";
    }

    @RouterMethod(path = "say_hello_world2")
    public void say(final Context context, String str, CallBack2 callBack2) {
        callBack2.returnCallBackSth(str + "->HelloWorldImpl", new CallBack3() {
            @Override
            public void returnSth(String sth) {
                Toast.makeText(context, sth + "->HelloWorldImpl", Toast.LENGTH_LONG).show();
            }
        });
    }

    @RouterMethod(path = "say_hello_world3")
    public void say(Context context, List<People> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null) {
            for (People p : list) {
                sb.append(p.name_Server + ">");
            }

        } else {
            sb.append("error");
        }

        Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG).show();
    }

    @RouterMethod(path = "static_method_get_words")
    public static String getWords() {
        return "hello from Module B Static Method.";
    }

    @RouterMethod(path = "static_method_say_words")
    public static void sayWords() {
        System.out.println("hi from Module B Static Method.");
    }
}
