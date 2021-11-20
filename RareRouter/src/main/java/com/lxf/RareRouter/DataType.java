package com.lxf.RareRouter;

import com.lxf.data.RouterParcelable;
import com.lxf.init.RouteBean;
import com.lxf.init.RouteMap;

import java.util.List;

public class DataType {
    public static final String TYPE_int = "int";
    public static final String TYPE_Integer = "java.lang.Integer";

    public static final String TYPE_long = "long";
    public static final String TYPE_Long = "java.lang.Long";

    public static final String TYPE_byte = "byte";
    public static final String TYPE_Byte = "java.lang.Byte";

    public static final String TYPE_short = "short";
    public static final String TYPE_Short = "java.lang.Short";

    public static final String TYPE_char = "char";
    public static final String TYPE_Character = "java.lang.Character";

    public static final String TYPE_boolean = "boolean";
    public static final String TYPE_Boolean = "java.lang.Boolean";

    public static final String TYPE_double = "double";
    public static final String TYPE_Double = "java.lang.Double";

    public static final String TYPE_float = "float";
    public static final String TYPE_Float = "java.lang.Float";

    public static final String TYPE_String = "java.lang.String";

    public static final String TYPE_void = "void";
    public static final String TYPE_Void = "java.lang.Void";

    public static final String TYPE_RouterParcelable = RouterParcelable.class.toString();

    /*
    list泛型检查在编译阶段进行，
    仅支持list当中传递:
    1、不带泛型；
    2、泛型为基本数据类型；
    3、泛型为自定义复杂类，该类需要实现RouterParcelable接口；
    4、泛型为List，且list当中含有list或上面1-3类型；
     */
    public static final String TYPE_List = "java.util.List";
    public static final String TYPE_List_Generic = "java.util.List<";
    public static final String TYPE_List_List_Generic = "java.util.List<java.util.List";


    public static boolean isBasicType(String clazz) {
        return TYPE_int.equals(clazz) || TYPE_Integer.equals(clazz) ||
                TYPE_String.equals(clazz) ||
                TYPE_boolean.equals(clazz) || TYPE_Boolean.equals(clazz) ||
                TYPE_void.equals(clazz) || TYPE_Void.equals(clazz) ||
                TYPE_long.equals(clazz) || TYPE_Long.equals(clazz) ||
                TYPE_char.equals(clazz) || TYPE_Character.equals(clazz) ||
                TYPE_float.equals(clazz) || TYPE_Float.equals(clazz) ||
                TYPE_double.equals(clazz) || TYPE_Double.equals(clazz) ||
                TYPE_byte.equals(clazz) || TYPE_Byte.equals(clazz) ||
                TYPE_short.equals(clazz) || TYPE_Short.equals(clazz);

    }

    public static boolean isList(String clazz) {
        return TYPE_List.equals(clazz);
    }

    public static boolean isListType(String clazz) {
        return clazz.startsWith(TYPE_List);
    }

    public static boolean isListGeneric(String clazz) {
        return clazz.startsWith(TYPE_List_Generic) && (clazz.startsWith(TYPE_List_List_Generic));
    }

    public static boolean isListBasic(String clazz) {
        if (!clazz.startsWith(TYPE_List_Generic)) {
            return false;
        }
        String childClazz = getListChildGeneric(clazz);
        return isBasicType(childClazz);
    }

    public static boolean isListListGeneric(String clazz) {
        return clazz.startsWith(TYPE_List_List_Generic);
    }

    private static String getListChildGeneric(String listStr) {
        int first = listStr.indexOf("<");
        int last = listStr.lastIndexOf(">");
        if (first < 0 || last < first) {
            return listStr;
        }
        return listStr.substring(first + 1, last);
    }

    public static String getListLastChildType(String clazz) {
        String childClazz = clazz;
        while (childClazz.startsWith(TYPE_List_Generic)) {
            childClazz = getListChildGeneric(childClazz);
        }
        return childClazz;
    }

    public static boolean isInstanceOfInterface(Object object, String interfaceName) {
        Class<?>[] interfaceArr = object.getClass().getInterfaces();
        if (interfaceArr == null || interfaceArr.length == 0) {
            return false;
        }
        for (Class<?> clazz : interfaceArr) {
            if (clazz.getName().equals(interfaceName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInstanceOfInterface(String pkgName) {
        if (pkgName == null || pkgName.length() == 0) {
            return false;
        }
        List<RouteBean> list = RouteMap.getInstance().getInterfaceRouteBeans(pkgName);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }
}
