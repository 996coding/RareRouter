package com.lxf.Process.configure;

import com.lxf.Application;
import com.lxf.Process.genTxt.TxtCreator;
import com.lxf.Process.genTxt.TxtWriter;

import java.io.File;
import java.util.List;

public class TxtPath {

    private final String BUILD_DIR = Application.RouterName;
    private final String BUILD_DIR_PROCESS = "process";

    private final String ROUTE_SCAN_INDEX = "RouteScanIndex.txt";
    private final String ROUTE_PROXY = "RouteProxy.txt";
    private final String ROUTE_SCAN_RES = "RouteScanResult.txt";
    private final String ROUTE_DATA_OBJ = "RouteDataObj.txt";
    private final String LOG_TXT = "log.txt";
    private final String MODULE_INFO_TXT = "module.txt";

    public static String PATH_SCAN_RES;
    public static String PATH_SCAN_INDEX;
    public static String PATH_SCAN_PROXY;
    public static String PATH_SCAN_DATA_OBJ;

    public static String PATH_MODULE_INFO;

    public static String DELETE_DIR;
    public static String LOG_INFO;

    private static String PROJECT_ROOT_PATH;


    private ModuleInfo moduleInfo;
    private String Txt_Root_Dir;
    private int moduleNumTotal = -1;

    public TxtPath() {

    }

    public void init() {
        initBasePath();
        initPath();
        createTxtIfNotExist();
        initIndexTxt();
        //记录扫描的module信息
        recordModuleTxt();
    }

    private void initBasePath() {
        Txt_Root_Dir = findConfigDir();
        if (Txt_Root_Dir != null) {
            return;
        }

        moduleInfo = new ModuleInfo();
        moduleInfo.startScan();
        moduleNumTotal = moduleInfo.getValidRelyModuleSize();
        Txt_Root_Dir = moduleInfo.getGenTxtDir();
    }

    private void initPath() {
        String base_dir = Txt_Root_Dir + SystemConfig.pathSign + BUILD_DIR +
                SystemConfig.pathSign + BUILD_DIR_PROCESS + SystemConfig.pathSign;
        PATH_SCAN_RES = base_dir + ROUTE_SCAN_RES;
        PATH_SCAN_INDEX = base_dir + ROUTE_SCAN_INDEX;
        PATH_SCAN_PROXY = base_dir + ROUTE_PROXY;
        PATH_SCAN_DATA_OBJ = base_dir + ROUTE_DATA_OBJ;
        PATH_MODULE_INFO = base_dir + MODULE_INFO_TXT;
        LOG_INFO = Txt_Root_Dir + SystemConfig.pathSign + BUILD_DIR +
                SystemConfig.pathSign + LOG_TXT;
        DELETE_DIR = base_dir;
    }

    private void initIndexTxt() {
        if (this.moduleNumTotal <= 0) {
            return;
        }
        String scanInfo = ScanIndex.genScanIndexInfo(moduleNumTotal, 0);
        TxtWriter.writeTxt(PATH_SCAN_INDEX, scanInfo);
    }

    private void createTxtIfNotExist() {
        /*
          以此创建1个目录、4个文件；
        */
        TxtCreator.createDir(DELETE_DIR);
        TxtCreator.createFileIfNone(PATH_SCAN_RES);
        TxtCreator.createFileIfNone(PATH_SCAN_INDEX);
        TxtCreator.createFileIfNone(PATH_SCAN_PROXY);
        TxtCreator.createFileIfNone(PATH_SCAN_DATA_OBJ);
        TxtCreator.createFileIfNone(PATH_MODULE_INFO);
        TxtCreator.createFileIfNone(LOG_INFO);
    }

    private String findConfigDir() {
        String projectPath = System.getProperty("user.dir");
        PROJECT_ROOT_PATH = projectPath;
        File dir = new File(projectPath);
        if (!dir.exists() || !dir.isDirectory()) {// 判断是否存在目录
            return null;
        }

        String[] files = dir.list();// 读取目录下的所有目录文件信息
        for (int i = 0; i < files.length; i++) {// 循环，添加文件名或回调自身
            String path_tail = SystemConfig.pathSign + BUILD_DIR + SystemConfig.pathSign + BUILD_DIR_PROCESS + SystemConfig.pathSign + ROUTE_SCAN_INDEX;
            String aimPath = projectPath + SystemConfig.pathSign + files[i] + SystemConfig.pathSign + "build" + path_tail;
            File tmp = new File(aimPath);
            if (tmp.exists()) {
                String dirPath = projectPath + SystemConfig.pathSign + files[i] + SystemConfig.pathSign + "build";
                return dirPath;
            }
        }
        return null;
    }

    public ModuleInfo getModuleInfo() {
        return moduleInfo;
    }

    public String getProjectRootPath() {
        if (PROJECT_ROOT_PATH == null || PROJECT_ROOT_PATH.length() == 0) {
            PROJECT_ROOT_PATH = System.getProperty("user.dir");
        }
        return PROJECT_ROOT_PATH;
    }

    private void recordModuleTxt() {
        if (this.moduleInfo == null) {
            return;
        }
        List<ModuleInfo.Module> list = this.moduleInfo.getModuleList();
        if (list == null || list.size() == 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (ModuleInfo.Module m : list) {
            sb.append(m.toString());
            sb.append("\n");
        }
        TxtWriter.writeTxt(PATH_MODULE_INFO, sb.toString());
    }
}
