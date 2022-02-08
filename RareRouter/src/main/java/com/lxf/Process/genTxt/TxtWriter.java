package com.lxf.Process.genTxt;

import com.lxf.Process.base.Bean;
import com.lxf.Process.configure.TxtPath;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Set;

public class TxtWriter {

    public static void txtAppendWrite(String txtPath, String content) {
        TxtCreator.createFileIfNone(txtPath);
        String txtContent = TxtReader.readTxt(txtPath).trim();
        writeTxt(txtPath, txtContent + "\n" + content);
    }

    public static void writeTxt(String txtPath, String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(txtPath));
            out.write(info); // \r\n为换行
//            out.flush(); // 把缓存区内容压入文件
            out.close();
        } catch (Exception e) {
        }
    }

    public static void writeScanAnnotation(Set<Bean> clsSet, Set<Bean> bSet, Set<Bean> askSet, Set<Bean> impSet) {
        StringBuilder sb = new StringBuilder();
        for (Bean bean : clsSet) {
            sb.append(bean.toString());
            sb.append("\n");
        }
        if (clsSet.size() > 0) {
            sb.append("\n");
        }
        for (Bean bean : bSet) {
            sb.append(bean.toString());
            sb.append("\n");
        }
        if (bSet.size() > 0) {
            sb.append("\n");
        }
        for (Bean bean : askSet) {
            sb.append(bean.toString());
            sb.append("\n");
        }
        if (askSet.size() > 0) {
            sb.append("\n");
        }
        for (Bean bean : impSet) {
            sb.append(bean.toString());
            sb.append("\n");
        }
//        if (sb.length() == 0) {
//            sb.append(BaseProcessor.moduleName + " module has no Rare Annotation.");
//        }
        writeTxt(TxtPath.PATH_SCAN_RES, sb.toString());
    }

}
