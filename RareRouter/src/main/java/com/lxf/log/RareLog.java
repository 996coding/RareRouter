package com.lxf.log;

public class RareLog {
    private static Printer printer;
    public static boolean isOpen = false;

    public static void setLogger(Printer logger) {
        printer = logger;
    }

    public static void output(String s) {
        if (isOpen && printer != null) {
            printer.log(s);
        }
    }
}
