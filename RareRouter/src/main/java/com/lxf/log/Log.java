package com.lxf.log;

public class Log {
    private static JavaLibLog libLog;

    public static void setLogger(JavaLibLog logger) {
        libLog = logger;
    }

    public static void e(String s) {
        if (libLog != null) {
            libLog.log(s);
        }
    }
}
