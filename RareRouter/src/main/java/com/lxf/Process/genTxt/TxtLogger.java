package com.lxf.Process.genTxt;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.lxf.Process.configure.TxtPath.Log_Dir;
import static com.lxf.Process.configure.TxtPath.LOG_INFO;

import com.lxf.Process.base.Bean;

public class TxtLogger {
    private static List<String> delayWrite = new ArrayList<>();
    private static String HEAD_SPACE = "  ";

    public static void logTxt(String moduleName, Set<Bean> rClassSet, Set<Bean> rBeanSet, Set<Bean> rMethodAskSet, Set<Bean> rMethodImplSet) {

        StringBuilder sb = new StringBuilder();
        String space = "  ";

        StringBuilder methodBody = new StringBuilder();
        StringBuilder clazzBody = new StringBuilder();
        StringBuilder beanBody = new StringBuilder();


        sb.append("【" + moduleName + "】\n");
        sb.append("注解 RouterClass 有：" + rClassSet.size() + " 个。\n");
        int clazzNum = 1;
        for (Bean bean : rClassSet) {
            clazzBody.append(space);
            clazzBody.append(clazzNum + "、注解值：");
            clazzBody.append(bean.path);
            clazzBody.append(getSpaceString(25 - bean.path.length()));
            clazzBody.append("包名：");
            clazzBody.append(bean.pkgName);
            clazzBody.append("\n");
            clazzNum++;
        }
        sb.append(clazzBody);

        sb.append("注解 RouterBean ：" + rBeanSet.size() + " 个。\n");
        int beanNum = 1;
        for (Bean bean : rClassSet) {
            beanBody.append(space);
            beanBody.append(beanNum + "、注解值：");
            beanBody.append(bean.path);
            beanBody.append(getSpaceString(25 - bean.path.length()));
            beanBody.append("包名：");
            beanBody.append(bean.pkgName);
            beanBody.append("\n");
            beanNum++;
        }
        sb.append(beanBody);

        sb.append("注解 RouterMethod ：" + (rMethodImplSet.size() + rMethodAskSet.size()) + " 个。\n");

        int methodNum = 1;
        for (Bean bean : rMethodAskSet) {
            methodBody.append(space);
            methodBody.append(methodNum + "、Ask  注解值：");
            methodBody.append(bean.path);
            methodBody.append(getSpaceString(25 - bean.path.length()));
            methodBody.append("方法名：");
            methodBody.append(bean.method);
            methodBody.append(getSpaceString(20 - bean.method.length()));
            methodBody.append("包名：");
            methodBody.append(bean.pkgName);
            methodBody.append("\n");
            methodNum++;
        }
        sb.append("\n");
        methodNum = 1;
        for (Bean bean : rMethodAskSet) {
            methodBody.append(space);
            methodBody.append(methodNum + "、Reply注解值：");
            methodBody.append(bean.path);
            methodBody.append(getSpaceString(25 - bean.path.length()));
            methodBody.append("方法名：");
            methodBody.append(bean.method);
            methodBody.append(getSpaceString(20 - bean.method.length()));
            methodBody.append("包名：");
            methodBody.append(bean.pkgName);
            methodBody.append("\n");
            methodNum++;
        }
        sb.append(methodBody);

        TxtLogger.output_in_section(sb.toString());
    }

    public static String getSpaceString(int num) {
        if (num <= 0) {
            return "";
        }
        StringBuilder space = new StringBuilder();
        for (int i = 0; i < num; i++) {
            space.append(" ");
        }
        return space.toString();
    }


    public static void output_new_section(String content) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n\n");
        sb.append("Router-Log-Time:");
        sb.append(getTime());
        sb.append("\n");
        sb.append("-------------------------------------\n" + HEAD_SPACE);
        sb.append(content.replaceAll("\n", "\n" + HEAD_SPACE));
        delayWrite.add(sb.toString());
        delayWriteLog();
    }

    public static void output_in_section(String content) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(HEAD_SPACE + "time:");
        sb.append(getTime());
        sb.append("\n" + HEAD_SPACE);
        sb.append(content.replaceAll("\n", "\n" + HEAD_SPACE));
        sb.append("\n-----------------------------------------------------------------------------");
        sb.append("-----------------------------------------------------------------------------\n");
        delayWrite.add(sb.toString());
        delayWriteLog();
    }

    public static boolean flushLog() {
        return delayWriteLog();
    }

    private static boolean delayWriteLog() {
        if (LOG_INFO == null || LOG_INFO.length() == 0) {
            return false;
        }
        if (delayWrite.size() == 0) {
            return true;
        }
        TxtCreator.createDir(Log_Dir);
        TxtCreator.createFileIfNone(LOG_INFO);
        StringBuilder allTxt = new StringBuilder();
        for (String str : delayWrite) {
            allTxt.append(str);
        }
        delayWrite.clear();
        write(allTxt.toString());
        return true;
    }

    private static void write(String txtContent) {
        FileWriter fw = null;
        try {
            // 传递true，代表不覆盖已有文件，追加
            fw = new FileWriter(LOG_INFO, true);
            fw.write(txtContent);
        } catch (IOException e) {

        } finally {
            try {
                if (fw != null)
                    fw.close();
            } catch (IOException e) {

            }
        }
    }

    private static String getTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}
