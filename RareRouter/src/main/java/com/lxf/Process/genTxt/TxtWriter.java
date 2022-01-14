package com.lxf.Process.genTxt;

import com.lxf.Process.base.Bean;
import com.lxf.Process.configure.TxtPath;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Set;

import static com.lxf.Process.genTxt.TxtReader.readTxt;

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

    public static Set<Bean> writeBeans(Set<Bean> set) {
        String txtContent = readTxt(TxtPath.PATH_SCAN_RES);
        boolean isOldContent = false;
        StringBuilder sb = new StringBuilder();
        for (Bean bean : set) {
            if (txtContent.contains(bean.toString())) {
                isOldContent = true;
            }
            sb.append(bean.toString());
            sb.append("\n");
        }
        if (isOldContent) {
            /*
                1、删除文件；
                2、重新创建文件；
                ---->>>> 可以省略步骤1和2，直接覆盖写入
                3、写入文件；
                4、返回该set；
             */
            writeTxt(TxtPath.PATH_SCAN_RES, sb.toString());
            return set;
        } else {
            /*
            1、txt文本后面补加；
            2、读取txt所有字符串；
            3、生成Set<Bean>；
             */
            writeTxt(TxtPath.PATH_SCAN_RES, sb.append(txtContent).toString());
        }

        return TxtReader.readBeans();
    }

    public static void writeMethodImpClazz(Set<String> classes) {
        if (classes == null || classes.size() == 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        String readContent = readTxt(TxtPath.PATH_SCAN_PROXY);
        if (readContent != null && readContent.length() > 0) {
            sb.append(readContent);
            sb.append("\n");
        }
        for (String s : classes) {
            sb.append(s);
            sb.append("\n");
        }
        writeTxt(TxtPath.PATH_SCAN_PROXY, sb.toString());
    }

    public static void recordParamClazz(Set<Bean> set) {
        StringBuilder sb = new StringBuilder();
        for (Bean bean : set) {
            if (bean.returnType != null && bean.returnType.length() > 0) {
                sb.append(bean.returnType.trim());
                sb.append("\n");
            }
            for (String param : bean.paramsList) {
                if (param != null && param.length() > 0) {
                    sb.append(param.trim());
                    sb.append("\n");
                }
            }
        }
        writeTxt(TxtPath.PATH_SCAN_DATA_OBJ, sb.toString());

    }
}
