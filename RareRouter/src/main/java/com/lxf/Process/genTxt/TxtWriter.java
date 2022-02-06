package com.lxf.Process.genTxt;

import com.lxf.Process.base.BaseProcessor;
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

    public static void writeScanAnnotation(Set<Bean> set) {
        StringBuilder sb = new StringBuilder();
        if (set == null || set.size() == 0) {
            sb.append(BaseProcessor.moduleName + " module has no Rare Annotation.");
        } else {
            for (Bean bean : set) {
                sb.append(bean.toString());
                sb.append("\n");
            }
        }

        writeTxt(TxtPath.PATH_SCAN_RES, sb.toString());
    }

}
