package com.lxf.Process;

import com.google.auto.service.AutoService;
import com.lxf.Annotation.FlagAnnotation;
import com.lxf.Process.base.BaseProcessor;
import com.lxf.Process.base.Bean;
import com.lxf.Process.configure.ScanIndex;
import com.lxf.Process.configure.TxtPath;
import com.lxf.Process.genJava.GenClazzGetter;
import com.lxf.Process.genJava.GenIntentStarter;
import com.lxf.Process.genJava.GenJavaCode;
import com.lxf.Process.genJava.GenMethodImpClazzProvider;
import com.lxf.Process.genJava.GenParamObjCreator;
import com.lxf.Process.genJava.GenRouteTable;
import com.lxf.Process.genTxt.TxtLogger;
import com.lxf.Process.genTxt.TxtReader;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;

@AutoService(Processor.class)
public class LastProcessor extends BaseProcessor {

    @Override
    public Set<String> getScanAnnotation() {
        Set<String> annotationSet = new HashSet<>();
        annotationSet.add(FlagAnnotation.class.getName());
        TxtLogger.flushLog();
        return annotationSet;
    }

    @Override
    public void process(RoundEnvironment roundEnvironment) {
        if (ScanIndex.isLastScan() && roundEnvironment.processingOver()) {
            genJavaClass();
            TxtLogger.flushLog();
            print("======================== scan over ***********************");
            return;
        }
    }

    public void genJavaClass() {
        //这是最后一次扫描，需要生成java文件
        Set<Bean> setRes = TxtReader.readBeans();
        GenRouteTable.genRouteInfo(setRes, filerGen);
        GenMethodImpClazzProvider.genAimClassCreator(TxtReader.readTrimLine(TxtPath.PATH_SCAN_PROXY), filerGen);
        GenParamObjCreator.genParamObjCreator(TxtReader.readTrimLine(TxtPath.PATH_SCAN_DATA_OBJ), filerGen);
        GenClazzGetter.genLeftCreator(setRes, filerGen);
        GenIntentStarter.genIntentStarter(filerGen);
        GenJavaCode.genJavaCode(filerGen);
    }


}
