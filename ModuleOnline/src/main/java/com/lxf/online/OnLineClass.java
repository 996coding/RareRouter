package com.lxf.online;

import com.lxf.Annotation.RouterMethod;

public class OnLineClass {
    @RouterMethod(path = "say_hello_world")
    public String say(String content) {
        return content + "->>on-line-hell-world!";
    }
}