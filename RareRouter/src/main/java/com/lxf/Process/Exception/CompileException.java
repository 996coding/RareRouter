package com.lxf.Process.Exception;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

public class CompileException {

    public static Messager messager;
    private static boolean switchOn = true;

    private static void exitSystem(String errorInfo) {
        if (messager != null) {
            messager.printMessage(Diagnostic.Kind.ERROR, errorInfo);
        }
        if (switchOn) {
            System.exit(1);
        }
    }

    public static void crash_100(String dir) {
        StringBuilder info = new StringBuilder();
        info.append("错误代码：");
        info.append(ExceptionType.CODE_100);
        info.append("\n");
        info.append("扫描项目根目录：");
        info.append(dir);
        info.append("\n");
        info.append(ExceptionType.ERR_SETTINGS_GRADLE_NONE);
        exitSystem(info.toString());
    }

    public static void crash_101() {
        StringBuilder info = new StringBuilder();
        info.append("错误代码：");
        info.append(ExceptionType.CODE_101);
        info.append("\n");
        info.append(ExceptionType.ERR_SETTINGS_GRADLE_EMPTY);
        exitSystem(info.toString());
    }

    public static void crash_102() {
        StringBuilder info = new StringBuilder();
        info.append("错误代码：");
        info.append(ExceptionType.CODE_102);
        info.append("\n");
        info.append(ExceptionType.ERR_SETTINGS_GRADLE_ANALYSIS);
        info.append("\n能解析的settings.gradle模板是：\n");
        info.append("include ':Module的名字'\ninclude ':Module的名字'\n......\ninclude ':Module的名字'\n");
        info.append("rootProject.name = \"Project的名字\"\n");
        exitSystem(info.toString());
    }
}
