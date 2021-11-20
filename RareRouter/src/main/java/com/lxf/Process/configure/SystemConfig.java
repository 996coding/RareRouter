package com.lxf.Process.configure;

/*
不同的操作系统，路径分割符号不一样。
 */
public class SystemConfig {
    private static final String LINUX_PATH_SEPARATE_SING = "/";
    private static final String WIN_PATH_SEPARATE_SING = "\\";
    public static String pathSign = LINUX_PATH_SEPARATE_SING;

    static {
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            pathSign = WIN_PATH_SEPARATE_SING;
        } else {
            pathSign = LINUX_PATH_SEPARATE_SING;
        }
    }

    public static void changeSystemSign() {
        if (pathSign == LINUX_PATH_SEPARATE_SING) {
            pathSign = WIN_PATH_SEPARATE_SING;
        } else {
            pathSign = LINUX_PATH_SEPARATE_SING;
        }
    }

    public static boolean isWindowSystem(){
        return WIN_PATH_SEPARATE_SING.equals(pathSign);
    }

}
