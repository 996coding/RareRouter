package com.lxf.Process.configure;

import com.lxf.Application;
import com.lxf.Process.Exception.CompileException;
import com.lxf.Process.genTxt.TxtLogger;
import com.lxf.Process.genTxt.TxtReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModuleInfo {

    public static class Module {
        public String name;
        public String gradlePath;
        public String path;
        public boolean isMainModule = false;//是否为主module
        public boolean isRelyRouter = false;//是否依赖本sdk
        public boolean isValid = true;//gradle路径是否有效

        @Override
        public String toString() {
            return "Module{" +
                    "name=" + name +
                    ", gradlePath=" + gradlePath +
                    ", path=" + path +
                    ", isMainModule=" + isMainModule +
                    ", isRelyRouter=" + isRelyRouter +
                    ", isValid=" + isValid +
                    '}';
        }

        public static Module strToModule(String str) {
            if (str == null && str.length() == 0) {
                return null;
            }
            String regex = "(?<==).*?(?=,)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            StringBuilder sb = new StringBuilder();
            int index = 0;
            Module m = new Module();
            while (matcher.find()) {
                index++;
                if (index == 1) {
                    m.name = matcher.group();
                } else if (index == 2) {
                    m.gradlePath = matcher.group();
                } else if (index == 3) {
                    m.path = matcher.group();
                } else if (index == 4) {
                    m.isMainModule = "true".equals(matcher.group().toLowerCase());
                } else if (index == 5) {
                    m.isRelyRouter = "true".equals(matcher.group().toLowerCase());
                } else if (index == 6) {
                    m.isValid = "true".equals(matcher.group().toLowerCase());
                }
            }
            return m;
        }
    }


    private String rootDir;


    private List<Module> list;

    public ModuleInfo() {
        this.rootDir = System.getProperty("user.dir");
        this.list = new ArrayList<>();
    }

    /*
    1、获取settings.gradle的路径;
    2、解析settings.gradle中所有的module，以及module的路劲(可能不在同一目录下);
    3、遍历所有module中的build.gradle文件;
    4、判断module是否依赖该插件，判断module是否为主module;
    5、所有扫描到的module将封装放入list;
     */
    public void startScan() {
        String settingsGradle = this.rootDir + SystemConfig.pathSign + "settings.gradle";
        if (!TxtReader.isFileExist(settingsGradle)) {
            CompileException.crash_100(this.rootDir);
            return;
        }
        String settingContent = TxtReader.readTxt(settingsGradle);
        if (settingContent == null || settingContent.length() < 10) {
            CompileException.crash_101();
            return;
        }

        String regex = "(?<=':).*?(?=')";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(settingContent);
        //将所有匹配的结果打印输出
        while (matcher.find()) {
            Module module = new Module();
            module.name = matcher.group();
            module.gradlePath = this.rootDir + SystemConfig.pathSign + matcher.group() + SystemConfig.pathSign + "build.gradle";
            this.list.add(module);
        }
        for (Module everyModule : this.list) {
            readModuleGradle(everyModule);
        }
    }

    private void readModuleGradle(Module module) {
        File buildGradle = new File(module.gradlePath);
        if (buildGradle.exists()) {
            module.isValid = true;
        } else {
            module.isValid = false;
            return;
        }

        module.path = buildGradle.getParent();
        module.isMainModule = false;
        module.isRelyRouter = false;

        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(module.gradlePath));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine();
            while (line != null) {
                String tmp = line.trim();
                if (tmp.startsWith("applicationId")) {
                    module.isMainModule = true;
                }
//                if (tmp.startsWith("annotationProcessor")) {
//                    /*
//                    分工程依赖、maven依赖
//                     */
//                    if (tmp.contains("':LxfRouter'")) {
//                        module.isRelyRouter = true;
//                        break;
//                    }
//                }
                if (Application.isRely(tmp)) {
                    module.isRelyRouter = true;
                    break;
                }
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {

        }
    }

    public String getGenTxtDir() {
        String dir = null;
        String tmp = null;
        StringBuilder sb = new StringBuilder();
        sb.append("一共扫描到的module个数:" + list.size());
        sb.append("\n");
        String placeholder = "!@#$%^&*()-=";
        sb.append("编译产生的Java文件位置:" + placeholder);
        sb.append("\n");
        for (Module module : this.list) {
            if (module.isValid) {
                tmp = module.path;
                if (module.isMainModule) {
                    dir = module.path + SystemConfig.pathSign + "build";
                }
            }
            sb.append(module.toString());
            sb.append("\n");
        }
        if (dir == null) {
            if (tmp != null) {
                dir = tmp;
            } else {
                dir = this.rootDir;
            }
        }
        TxtLogger.output_new_section(sb.toString().replace(placeholder, dir));
        return dir;
    }

    public int getValidRelyModuleSize() {
        int num = 0;
        for (Module module : this.list) {
            if (module.isValid && module.isRelyRouter) {
                num = num + 1;
            }
        }
        return num;
    }

    public List<Module> getModuleList() {
        if (list == null || list.size() == 0) {
            if (TxtPath.PATH_MODULE_INFO != null) {
                Set<String> content = TxtReader.readTrimLine(TxtPath.PATH_MODULE_INFO);
                for (String line : content) {
                    Module m = Module.strToModule(line);
                    if (m != null) {
                        list.add(m);
                    }
                }
            }
        }
        return list;
    }
}
