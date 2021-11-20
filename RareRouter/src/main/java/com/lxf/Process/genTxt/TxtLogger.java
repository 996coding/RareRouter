package com.lxf.Process.genTxt;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.lxf.Process.configure.TxtPath.DELETE_DIR;
import static com.lxf.Process.configure.TxtPath.LOG_INFO;

public class TxtLogger {
    private static List<String> delayWrite = new ArrayList<>();
    private static String HEAD_SPACE = "  ";

    public static void output_new_section(String content) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n\n");
        sb.append("Router-Log-Time:");
        sb.append(getTime());
        sb.append("\n");
        sb.append("-------------------------------------\n"+HEAD_SPACE);
        sb.append(content.replaceAll("\n","\n"+HEAD_SPACE));
        delayWrite.add(sb.toString());
        delayWriteLog();
    }

    public static void output_in_section(String content) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(HEAD_SPACE+"time:");
        sb.append(getTime());
        sb.append("\n"+HEAD_SPACE);
        sb.append(content.replaceAll("\n","\n"+HEAD_SPACE));
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
        TxtCreator.createDir(DELETE_DIR);
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
