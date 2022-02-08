package com.lxf.ModuleA.downJar;

public interface StatusListener {
    void onStart();
    void onProgress(int progress, long currentLength, long totalLength);//进度
    void onFinish(String info);
    void onFail(String errorInfo);//下载或上传失败
}
