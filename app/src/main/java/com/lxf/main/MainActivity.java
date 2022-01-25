package com.lxf.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lxf.Annotation.RouterClass;
import com.lxf.Annotation.RouterBean;
import com.lxf.RareApplication;
import com.lxf.log.RareLog;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

@RouterBean(path = "annotate.com.lxf.main.MainActivity")
@RouterClass(path = "this_is_activity")
public class MainActivity extends AppCompatActivity {

    public static String downLoadDir;
    public String jarPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Main Module");

        setContentView(R.layout.activity_main);

        RareLog.setLogger(new MyLog());

        downLoadDir = this.getFilesDir().getAbsolutePath();
        jarPath = downLoadDir+"/a.jar";
        findViewById(R.id.main_module).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Class<?> clazz = RareApplication.annotateClazz("this_is_TestActivity");
//                Intent intent = new Intent(MainActivity.this, clazz);
//                startActivity(intent);
                RareApplication.startIntent(MainActivity.this, "this_is_TestActivity");
            }
        });

        findViewById(R.id.http_load_jar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                downloadJar();
            }
        });

        findViewById(R.id.local_load_jar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                loadJar(jarPath);
                a(jarPath);
            }
        });
    }


    private void downloadJar() {
//        String url = "http://192.168.124.8:8080/hotfix_war_exploded/down/a.jar";
        String url = "http://192.168.50.243/a.jar";
        DownloadUtil.get().download(url, "", new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(String path) {
//                Toast.makeText(MainActivity.this, "下载成功->" + path, Toast.LENGTH_SHORT).show();
                jarPath = path + "/a.jar";
                Log.e("lv123", path);
            }

            @Override
            public void onDownloading(int progress) {

            }

            @Override
            public void onDownloadFailed(String error) {
                Log.e("lv123", error);

            }
        });
    }

    public void a(String jarPath){

        File path = new File(jarPath);
        if (!path.exists()){
            Log.e("lv123", "jar不存在  "+jarPath);
            return;
        }


        String tmpPath = getApplicationContext().getDir("Jar", 0).getAbsolutePath();

        DexClassLoader cl = new DexClassLoader(jarPath, tmpPath

                , null, this.getClass().getClassLoader());

        Class<?> libProviderCls = null;

        try {
             libProviderCls = cl.loadClass("com.lxf.genCode.ModuleRareImpl_ModuleOnline");

            Constructor<?> localConstructor = libProviderCls.getConstructor(new Class[] {});

            Object obj = localConstructor.newInstance(new Object[] {});

            Log.e("lv123","------加载完成");


        } catch (Exception e) {

            Log.e("lv123",e.getMessage());
            e.printStackTrace();

        }
    }
}