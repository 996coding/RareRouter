package com.lxf.data;


public final class DataType {
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
    BASIC_TYPE_ARRAY共计10种；
     */
    public static final String[] BASIC_TYPE_ARRAY =
            {"int-java.lang.Integer",
                    "long-java.lang.Long",
                    "byte-java.lang.Byte",
                    "short-java.lang.Short",
                    "char-java.lang.Character",
                    "boolean-java.lang.Boolean",
                    "double-java.lang.Double",
                    "float-java.lang.Float",
//                    "java.lang.String",
                    "void-java.lang.Void",
//                    RouterParcelable.class.toString()
            };

    public static final int getBasicTypeID(String pkgName) {
        for (int i = 0; i < BASIC_TYPE_ARRAY.length; i++) {
            if (BASIC_TYPE_ARRAY[i].contains(pkgName)) {
                return i;
            }
        }
        return -1;
    }

    /*
    集合类:
    java.util.Map
    java.util.Map<java.lang.String,java.lang.String>
    java.util.HashMap,
    java.util.HashMap<java.lang.Integer,java.lang.String>

    java.util.List
    java.util.List<java.lang.String>
    java.util.ArrayList
    java.util.ArrayList<java.lang.Integer>
    java.util.LinkedList
    java.util.LinkedList<java.lang.Boolean>

    java.util.Set
    java.util.Set<java.lang.String>
    java.util.HashSet
    java.util.HashSet<java.lang.Integer>

    */
}
