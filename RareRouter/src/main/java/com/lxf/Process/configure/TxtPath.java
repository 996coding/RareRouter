package com.lxf.Process.configure;

import com.lxf.Process.base.BaseProcessor;
import com.lxf.Process.genTxt.TxtCreator;


public class TxtPath {


    public static String PATH_SCAN_RES;

    public static String Log_Dir;
    public static String LOG_INFO;


    public static void init() {
        String base_dir = RareXml.logDir + BaseProcessor.systemDirPathSplit + RareXml.RareSDKName +
                BaseProcessor.systemDirPathSplit + "process" + BaseProcessor.systemDirPathSplit;
        PATH_SCAN_RES = base_dir + BaseProcessor.moduleName + "_" + "scan.txt";
        LOG_INFO = RareXml.logDir + BaseProcessor.systemDirPathSplit + RareXml.RareSDKName +
                BaseProcessor.systemDirPathSplit + "log.txt";
        Log_Dir = base_dir;

         /*
          以此创建1个目录、2个文件；
        */
        TxtCreator.createDir(Log_Dir);
        TxtCreator.createFileIfNone(PATH_SCAN_RES);
        TxtCreator.createFileIfNone(LOG_INFO);
    }


}
