package com.lxf.Process.genJava;

import com.lxf.Router.DataType;
import com.lxf.Process.configure.TxtPath;
import com.lxf.Process.genTxt.TxtReader;

import java.util.HashSet;
import java.util.Set;

public class GenParamObjCreator {

    public static final String CLASS_NAME = "ParamObjCreator";

    public static void genParamObjCreator(Set<String> set, FilerGen filerGen) {
        /*
        将set中不需要的移除；
         */
        Set<String> scanRes = TxtReader.readTrimLine(TxtPath.PATH_SCAN_RES);
        Set<String> newSet = new HashSet<>();
        for (String str : set) {
            if (!isNeedRemove(str, scanRes)) {
                newSet.add(str);
            }
        }
        String clazzStr = createClassStr(newSet);
        filerGen.genJavaClass(clazzStr, CLASS_NAME);
    }


    private static boolean isNeedRemove(String str, Set<String> scanRes) {
        if (str == null || str.length() == 0) {
            return true;
        }
        if (str.startsWith("java.lang.") || str.startsWith("javax.") || str.startsWith("android.") || str.startsWith("androidx.")) {
            return true;
        }

        if (DataType.isListType(str)) {
            return true;
        }
        if (DataType.isBasicType(str)) {
            return true;
        }

        if (DataType.TYPE_RouterParcelable.equals(str)) {
            return true;
        }

        //如果是接口，应该在scanRes当中，scanRes会在扫描时候记录。
        for (String tip : scanRes) {
            if (tip.contains("#pkgName=" + str + "#") && tip.contains("#isInterface=1")) {
                return true;
            }
        }
        //如果参数是java中的类、android中的类、共同的类,也应当移除

        return false;
    }

    private static String createClassStr(Set<String> set) {

        StringBuilder sb = new StringBuilder();
        sb.append("package "+GenConfig.PACKAGE+";\n\n");
        sb.append("import com.lxf.nozzle.ParamInstance;\n");
        sb.append("import com.lxf.response.AimClassImp;\n\n");
        sb.append("public class " + CLASS_NAME + " implements ParamInstance {\n\n");
        sb.append("    @Override\n");
        sb.append("    public Object create(String pkgFullName) {\n\n");
        for (String clazz : set) {
            sb.append(ifSentence(clazz));
            sb.append("\n");
        }
        sb.append("        return null;\n");
        sb.append("    }\n");
        sb.append("}");
        return sb.toString();
    }

    private static String ifSentence(String classFullName) {
        StringBuilder sb = new StringBuilder();
        sb.append("        if (pkgFullName.equals(\"" + classFullName + "\")) {\n");
        sb.append("            return new " + classFullName + "();\n");
        sb.append("        }\n");
        return sb.toString();
    }
}
