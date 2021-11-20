package com.lxf.Process.genJava;

public class GenIntentStarter {
    public static final String CLASS_NAME = "ActivityStarter";

    public static void genIntentStarter(FilerGen filerGen) {
        StringBuilder sb = new StringBuilder();
        sb.append("package "+GenConfig.PACKAGE+";\n\n");
        sb.append("import com.lxf.nozzle.IntentStart;\n\n");

        sb.append("public class " + CLASS_NAME + " implements IntentStart {\n\n");

        sb.append("    @Override\n");
        sb.append("    public void startActivity(Object context, Class<?> clazz) {\n");
        sb.append("        if (context instanceof android.content.Context) {\n");
        sb.append("            android.content.Context ctx = (android.content.Context) context;\n");
        sb.append("            android.content.Intent intent = new android.content.Intent(ctx, clazz);\n");
        sb.append("            ctx.startActivity(intent);\n");
        sb.append("       }\n");
        sb.append("    }\n\n");

        sb.append("}\n");

        String clazzStr = sb.toString();
        filerGen.genJavaClass(clazzStr, CLASS_NAME);
    }
}
