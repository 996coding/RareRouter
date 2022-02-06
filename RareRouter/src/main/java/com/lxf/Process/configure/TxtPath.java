package com.lxf.Process.configure;

import com.lxf.Application;
import com.lxf.Process.base.BaseProcessor;
import com.lxf.Process.genTxt.TxtCreator;


public class TxtPath {

    private final String BUILD_DIR = Application.RouterName;
    private final String BUILD_DIR_PROCESS = "process";

    private final String ROUTE_SCAN_RES = "RouteScanResult.txt";
    private final String LOG_TXT = "log.txt";

    public static String PATH_SCAN_RES;

    public static String DELETE_DIR;
    public static String LOG_INFO;


    public void init() {
        initPath();
        createTxtIfNotExist();
    }

    private void initPath() {

        String base_dir = RareXml.logDir + BaseProcessor.systemDirPathSplit + BUILD_DIR +
                BaseProcessor.systemDirPathSplit + BUILD_DIR_PROCESS + BaseProcessor.systemDirPathSplit;
        PATH_SCAN_RES = base_dir + ROUTE_SCAN_RES;
        LOG_INFO = RareXml.logDir + BaseProcessor.systemDirPathSplit + BUILD_DIR +
                BaseProcessor.systemDirPathSplit + LOG_TXT;
        DELETE_DIR = base_dir;
    }


    private void createTxtIfNotExist() {
        /*
          以此创建1个目录、4个文件；
        */
        TxtCreator.createDir(DELETE_DIR);
        TxtCreator.createFileIfNone(PATH_SCAN_RES);
        TxtCreator.createFileIfNone(LOG_INFO);
    }


}
