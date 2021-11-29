package com.lxf.Process;

import com.google.auto.service.AutoService;
import com.lxf.Annotation.RouterClass;
import com.lxf.Annotation.RouterMethod;
import com.lxf.Process.base.BaseProcessor;
import com.lxf.Process.base.Bean;
import com.lxf.Process.genJava.GenResponseProxy;
import com.lxf.Process.genTxt.TxtLogger;
import com.lxf.Process.genTxt.TxtWriter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

@AutoService(Processor.class)
public class CoreProcessor extends BaseProcessor {
    private Set<Bean> beanSet = new HashSet<>();
    private int processIndex = 0;

    @Override
    public Set<String> getScanAnnotation() {
        Set<String> annotationSet = new HashSet<>();
        annotationSet.add(RouterMethod.class.getName());
        annotationSet.add(RouterClass.class.getName());
        return annotationSet;
    }


    @Override
    public void process(RoundEnvironment roundEnvironment) {
        processIndex++;
        TxtLogger.output_in_section("--->>>process方法第 " + processIndex + " 次执行\n");

        if (roundEnvironment.processingOver()) {
            genRecordRouteInfo();
        } else {
            Set<? extends Element> setMethod = roundEnvironment.getElementsAnnotatedWith(RouterMethod.class);
            Set<? extends Element> setClass = roundEnvironment.getElementsAnnotatedWith(RouterClass.class);
            if (setMethod != null && setMethod.size() > 0) {
                for (Element e : setMethod) {
                    Bean bean = scanMethodAnnotation(e, RouterMethod.class);
                    if (bean != null) {
                        this.beanSet.add(bean);
                    }
                }
            }
            if (setClass != null && setClass.size() > 0) {
                for (Element e : setClass) {
                    Bean bean = scanClassAnnotation(e, RouterClass.class);
                    if (bean != null) {
                        this.beanSet.add(bean);
                    }
                }
            }
        }
    }

    public Bean scanClassAnnotation(Element e, Class<RouterClass> clazz) {

        /*
        需要获取：
        1、注解RouterClass的path值；
        2、注解所在的类的包名；
        3、继承的超类；
        4、继承的接口；
        5、当前clazz是否为接口；
         */
        String path = e.getAnnotation(clazz).path();
        String pkgName = e.asType().toString();

        /*
        合法性检查：
        1、是否修饰class ？
        2、访问权限是否 public、abstract?
        3、是否为interface ?
         */
        if (!e.getKind().name().toLowerCase().equals("class")) {
            return null;
        }
        Set<Modifier> modifiers = e.getModifiers();
        if (modifiers != null) {
            boolean isPublic = false, isAbstract = false;
            for (Modifier m : modifiers) {
                if (m.name().toLowerCase().equals("public")) {
                    isPublic = true;
                }
                if (m.name().toLowerCase().equals("abstract")) {
                    isAbstract = true;
                }
            }
            if (!isPublic || isAbstract) {
                return null;
            }
        }

        Bean bean = new Bean(path, pkgName, "0");
        return bean;
    }

    public Bean scanMethodAnnotation(Element e, Class<RouterMethod> clazz) {
        /*
        合法性检查：
        1、是否修饰method ？
        2、访问权限是否 public?
        3、所在的类必须是 class或者interface ?
         */
        if (!e.getKind().name().toLowerCase().equals("method")) {
            return null;
        }
        boolean isStatic = false, isPublic = false, isAbstract = false;
        Set<Modifier> methodModifiers = e.getModifiers();
        if (methodModifiers != null) {
            for (Modifier m : methodModifiers) {
                if (m.name().toLowerCase().equals("public")) {
                    isPublic = true;
                }
                if (m.name().toLowerCase().equals("abstract")) {
                    isAbstract = true;
                }
                if (m.name().toLowerCase().equals("static")) {
                    isStatic = true;
                }
            }
        }
        if (!isPublic) {
            return null;
        }
        boolean isClass = false, isInterface = false;
        Element classElement = e.getEnclosingElement();
        isClass = classElement.getKind().toString().toLowerCase().equals("class");
        isInterface = classElement.getKind().toString().toLowerCase().equals("interface");
        if (!isClass && !isInterface) {
            return null;
        }
        Set<Modifier> classModifiers = classElement.getModifiers();
        boolean isClsPublic = false, isClsAbstract = false;
        if (classModifiers != null) {
            for (Modifier m : classModifiers) {
                if (m.name().toLowerCase().equals("public")) {
                    isClsPublic = true;
                }
                if (m.name().toLowerCase().equals("abstract")) {
                    isClsAbstract = true;
                }
            }
        }
        if (!isClsPublic) {
            return null;
        }
        if (isClass && isClsAbstract) {
            return null;
        }

        /*
        需要获取：
        1、注解RouterMethod的path值；
        2、注解所在的类的包名；
        3、注解修饰的方法名；
        4、注解修饰方法的返回值的包名；
        5、注解修饰方法的参数的包名；
         */
        String path = e.getAnnotation(clazz).path();
        String pkgName = classElement.asType().toString();
        String method = e.getSimpleName().toString();

        ExecutableElement exe = (ExecutableElement) e;
        String returnType = exe.getReturnType().toString();
        List<? extends VariableElement> params = exe.getParameters();
        List<String> paramsList = new ArrayList<>();
        for (int i = 0; i < params.size(); i++) {
            VariableElement v = params.get(i);
            paramsList.add(v.asType().toString());
        }

        Bean bean = new Bean(path, pkgName, method, returnType, paramsList, (isInterface ? "1" : "0"));
        return bean;
    }

    public void genRecordRouteInfo() {
        logTxt();
        if (this.beanSet == null || this.beanSet.size() == 0) {
            return;
        }
        Set<String> genClasses = GenResponseProxy.genJavaResponseProxy(this.beanSet, this.filerGen);
        TxtWriter.writeMethodImpClazz(genClasses);

        Set<Bean> newSet = TxtWriter.writeBeans(this.beanSet);

        TxtWriter.recordParamClazz(newSet);
    }

    private void logTxt() {
        String placeholder1 = "!@#$%^&*()-=ABC";
        String placeholder2 = "!@#$%^&*()-=DEF\n";
        String placeholder3 = "!@#$%^&*()-=GHI";
        String placeholder4 = "!@#$%^&*()-=JKL\n";
        StringBuilder sb = new StringBuilder();
        String space = "  ";
        sb.append("当前module中，包含注解 RouterMethod 的方法，有：" + placeholder1 + " 个。\n");
        sb.append(placeholder2);
        sb.append("当前module中，包含注解 RouterClass 的类，有：" + placeholder3 + " 个。\n");
        sb.append(placeholder4);
        int methodNum = 0, clazzNum = 0;
        StringBuilder methodBody = new StringBuilder();
        StringBuilder clazzBody = new StringBuilder();
        if (this.beanSet != null && this.beanSet.size() > 0) {
            for (Bean bean : this.beanSet) {
                if (bean.method == null || bean.method.length() == 0) {
                    clazzNum++;
                    clazzBody.append(space);
                    clazzBody.append(clazzNum + "、注解值：");
                    clazzBody.append(bean.path);
                    clazzBody.append(getSpaceString(25 - bean.path.length()));
                    clazzBody.append("包名：");
                    clazzBody.append(bean.pkgName);
                    clazzBody.append("\n");
                } else {
                    methodNum++;
                    methodBody.append(space);
                    methodBody.append(methodNum + "、注解值：");
                    methodBody.append(bean.path);
                    methodBody.append(getSpaceString(25 - bean.path.length()));
                    methodBody.append("方法名：");
                    methodBody.append(bean.method);
                    methodBody.append(getSpaceString(20 - bean.method.length()));
                    methodBody.append("包名：");
                    methodBody.append(bean.pkgName);
                    methodBody.append("\n");
                }
            }
        }
        String logStr = sb.toString().
                replace(placeholder1, methodNum + "").
                replace(placeholder3, clazzNum + "").
                replace(placeholder2, methodBody.toString()).
                replace(placeholder4, clazzBody.toString());
        TxtLogger.output_in_section(logStr);
    }

    private String getSpaceString(int num) {
        if (num <= 0) {
            return "";
        }
        StringBuilder space = new StringBuilder();
        for (int i = 0; i < num; i++) {
            space.append(" ");
        }
        return space.toString();
    }

}
