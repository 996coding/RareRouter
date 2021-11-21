package com.lxf.Process;

import com.google.auto.service.AutoService;
import com.lxf.Process.base.BaseProcessor;
import com.lxf.Process.configure.ScanIndex;
import com.lxf.Process.configure.SystemConfig;
import com.lxf.Process.configure.TxtPath;
import com.lxf.Process.genTxt.TxtLogger;

import javax.annotation.processing.Processor;


@AutoService(Processor.class)
public class AheadProcessor extends BaseProcessor {
    private TxtPath txtPath;

    @Override
    public void init() {
        txtPath = new TxtPath();
        txtPath.init();
        ScanIndex.initScanInfo();
        genFlagJavaClass();
    }

    private void genFlagJavaClass() {
        String CLASS_NAME = "RouteFlagClass" + System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append("package com.lxf.RareFlag;\n");
        sb.append("import com.lxf.Annotation.FlagAnnotation;\n\n");
        sb.append("@FlagAnnotation\n");
        sb.append("public class " + CLASS_NAME + " { }\n");
        String javaPath = this.filerGen.genJavaClass(sb.toString(), CLASS_NAME);
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

}
