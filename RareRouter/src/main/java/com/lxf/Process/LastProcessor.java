package com.lxf.Process;

import com.google.auto.service.AutoService;
import com.lxf.Annotation.FlagAnnotation;
import com.lxf.Process.base.BaseProcessor;
import com.lxf.Process.configure.RareXml;
import com.lxf.Process.genJava.GenConfig;
import com.lxf.Process.genJava.GenIntentStarter;
import com.lxf.Process.genJava.GenModuleRareImpl;
import com.lxf.Process.genJava.GenRareAdder;
import com.lxf.Process.genTxt.TxtCreator;
import com.lxf.Process.genTxt.TxtLogger;
import com.lxf.Process.genTxt.TxtReader;
import com.lxf.Process.genTxt.TxtWriter;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;

@AutoService(Processor.class)
public class LastProcessor extends BaseProcessor {

    private String txt_rare_list, txt_last_add, txt_last_add_bp;
    private String lastAdder, lastAdder_bp;

    @Override
    public void init() {
        txt_rare_list = RareXml.logDir + systemDirPathSplit + "RareRouter" + systemDirPathSplit + "rareImpl.txt";
        txt_last_add = RareXml.logDir + systemDirPathSplit + "RareRouter" + systemDirPathSplit + "lastImpl.txt";
        txt_last_add_bp = RareXml.logDir + systemDirPathSplit + "RareRouter" + systemDirPathSplit + "lastImpl_bp.txt";

        TxtCreator.createFileIfNone(txt_rare_list);
        TxtCreator.createFileIfNone(txt_last_add);
        TxtCreator.createFileIfNone(txt_last_add_bp);
    }

    @Override
    public Set<String> getScanAnnotation() {
        Set<String> annotationSet = new HashSet<>();
        annotationSet.add(FlagAnnotation.class.getName());
        TxtLogger.flushLog();
        return annotationSet;
    }

    @Override
    public void process(RoundEnvironment roundEnvironment) {
        if (roundEnvironment.processingOver()) {
            if (GenModuleRareImpl.execute){
                TxtWriter.txtAppendWrite(txt_rare_list, GenConfig.PACKAGE + "." + GenModuleRareImpl.CLASS_NAME);
            }
            if (RareXml.appModule == null || RareXml.appModule.length() == 0) {
                lastAdder = TxtReader.readTxt(txt_last_add).trim();
                lastAdder_bp = TxtReader.readTxt(txt_last_add_bp).trim();

                File file = new File(lastAdder);
                File file_bp = new File(lastAdder_bp);

                if (file.exists()) {
                    file.delete();
                }
                if (file_bp.exists()) {
                    file_bp.delete();
                }

                Set<String> set = TxtReader.readTrimLine(txt_rare_list);
                GenRareAdder.gen(set, filerGen);
                GenRareAdder.genBackUp(set, filerGen);
                TxtWriter.writeTxt(txt_last_add, GenRareAdder.FILE_PATH);
                TxtWriter.writeTxt(txt_last_add_bp, GenRareAdder.FILE_PATH_BACK_UP);
            } else if (RareXml.appModule.equals(moduleName)) {
                Set<String> set = TxtReader.readTrimLine(txt_rare_list);
                GenRareAdder.gen(set, filerGen);
                GenRareAdder.genBackUp(set, filerGen);
                GenIntentStarter.gen(filerGen);
                TxtLogger.flushLog();
                print("======================== scan over ***********************");
            }
        }
    }
}
