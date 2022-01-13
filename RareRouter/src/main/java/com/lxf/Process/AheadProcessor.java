package com.lxf.Process;

import com.google.auto.service.AutoService;
import com.lxf.Process.base.BaseProcessor;
import com.lxf.Process.configure.ScanIndex;
import com.lxf.Process.configure.SystemConfig;
import com.lxf.Process.configure.TxtPath;
import com.lxf.Process.genJava.GenConfig;
import com.lxf.Process.genTxt.TxtLogger;

import java.io.File;
import java.io.StringReader;

import javax.annotation.processing.Processor;


@AutoService(Processor.class)
public class AheadProcessor extends BaseProcessor {
    private TxtPath txtPath;
    private String FLAG_CLASS_NAME = "RouteFlagClass";

    @Override
    public void init() {
        txtPath = new TxtPath();
        txtPath.init();
        ScanIndex.initScanInfo();
        genFlagJavaClass();
    }

    private void genFlagJavaClass() {
        FLAG_CLASS_NAME = "RouteFlagClass" + System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append("package " + GenConfig.PACKAGE + ";\n");
        sb.append("import com.lxf.Annotation.FlagAnnotation;\n\n");
        sb.append("@FlagAnnotation\n");
        sb.append("public class " + FLAG_CLASS_NAME + " { }\n");
        String javaPath = this.filerGen.genJavaClass(sb.toString(), FLAG_CLASS_NAME);
        initModuleInfo(javaPath);
        logTxt(javaPath);
    }

    private void logTxt(String flagJavaPath) {
        String modulePath = null, moduleName = null;
        if (flagJavaPath != null && flagJavaPath.length() > 0) {
            String splitStr = SystemConfig.pathSign + "build" + SystemConfig.pathSign;
            if (SystemConfig.isWindowSystem()) {
                splitStr = "\\\\build\\\\";
            }
            String[] arrName = flagJavaPath.split(splitStr);
            if (arrName.length > 0) {
                modulePath = arrName[0];
                moduleName = modulePath.replace(txtPath.getProjectRootPath(), "").replace(SystemConfig.pathSign, "");
            }
        }
        StringBuilder log_sb = new StringBuilder();
        log_sb.append("第 " + ScanIndex.getScanIndex() + "/" + ScanIndex.getScanTotal() + " 次扫描,当前module是：");
        if (moduleName == null && modulePath == null) {
            log_sb.append("未知\n");
            log_sb.append("生产的Java标记类路径是：");
            log_sb.append(flagJavaPath);
        } else {
            log_sb.append(moduleName);
            log_sb.append("\n");
            log_sb.append("当前module所在目录：");
            log_sb.append(modulePath);
        }
        log_sb.append("\n");
        TxtLogger.output_new_section(log_sb.toString());
    }


    private void initModuleInfo(String genJavaPath) {
        rootProjectPath = System.getProperty("user.dir");
        String strA = genJavaPath.replace(FLAG_CLASS_NAME + ".java", "");
        int genIndex = strA.lastIndexOf(GenConfig.PACKAGE_SUFFIX);
        String dirPathSplit = strA.substring(genIndex + GenConfig.PACKAGE_SUFFIX.length());
        int buildIndex = strA.lastIndexOf(dirPathSplit + "build" + dirPathSplit);
        String strB = strA.substring(0,buildIndex);
        int lastDirSplitIndex = strB.lastIndexOf(dirPathSplit);
        moduleName = strB.substring(lastDirSplitIndex+1);

        print("--->>>  " + rootProjectPath);
        print("--->>>  " + strA);
        print("--->>>  " + dirPathSplit);
        print("--->>>  " + strB);
        print("--->>>  " + moduleName);


    }
}
