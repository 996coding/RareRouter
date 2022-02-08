package com.lxf.ModuleA.downJar;


public class HttpParameter {

    public final String url;
    public final String downLoadDir;


    public HttpParameter(String url, String downLoadDir) {
        this.url = url;
        this.downLoadDir = downLoadDir;
    }

    public String getDownLoadFileName() {
        return url.substring(url.lastIndexOf("/") + 1);
    }


}
