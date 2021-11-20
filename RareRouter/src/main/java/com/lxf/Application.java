package com.lxf;

public class Application {
    public static String RouterName = "RareRouter";
    public static String RouterVersion = "1.0.0";

    public static boolean isRely(String buildGradleSentence) {
        if (buildGradleSentence.startsWith("annotationProcessor")) {
            /*
               分工程依赖、maven依赖、jar依赖
            */
            if (buildGradleSentence.contains(RouterName)){
                return true;
            }

        }
        return false;
    }
}
