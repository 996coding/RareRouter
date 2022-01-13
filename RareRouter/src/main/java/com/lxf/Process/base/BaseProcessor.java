package com.lxf.Process.base;

import com.lxf.Process.Exception.CompileException;
import com.lxf.Process.genJava.FilerGen;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public abstract class BaseProcessor extends AbstractProcessor {

    protected Messager messager;
    protected Filer filer;
    protected FilerGen filerGen;

    public static String rootProjectPath;
    public static String moduleName;
    public static String modulePath;
    public static String systemDirPathSplit;

    @Override
    public final synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        this.messager = processingEnvironment.getMessager();
        this.filer = processingEnv.getFiler();
        this.filerGen = new FilerGen(this.filer);
        CompileException.messager = this.messager;
        init();
    }

    @Override
    public final SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public final Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationSet = getScanAnnotation();
        if (annotationSet == null) {
            return new HashSet<>();
        }
        return annotationSet;
    }

    @Override
    public final boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        process(roundEnvironment);
        return false;
    }

    public final void print(String str) {
        this.messager.printMessage(Diagnostic.Kind.NOTE, str);
    }

    public void init() {

    }

    public Set<String> getScanAnnotation() {
        return null;
    }

    public void process(RoundEnvironment roundEnvironment) {

    }
}
