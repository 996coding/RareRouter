package com.lxf.ModuleA;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.lxf.Annotation.RouterClass;
import com.lxf.ModuleA.downJar.HttpParameter;
import com.lxf.ModuleA.downJar.OkHttpDownload;
import com.lxf.ModuleA.downJar.StatusListener;
import com.lxf.RareApplication;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.DexClassLoader;

@RouterClass(path = "http_jar_activity")
public class HttpJarActivity extends AppCompatActivity {

    private String downLoadDir;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_jar);

        downLoadDir = this.getFilesDir().getAbsolutePath();

        editText = findViewById(R.id.edit_text);

        findViewById(R.id.http_down_jar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoadJar();
            }
        });

        findViewById(R.id.local_load_jar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLocalJar();
            }
        });
        findViewById(R.id.clear_local_jar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearJar();
            }
        });
        findViewById(R.id.test_method).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelloWorld_Visitor visitor = RareApplication.createImpl(HelloWorld_Visitor.class);
                if (visitor != null) {
                    String str = visitor.say("??????hotfix??????");
                    Toast.makeText(HttpJarActivity.this, str, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void downLoadJar() {

        HttpParameter parameter = new HttpParameter(editText.getText().toString().trim(),downLoadDir);
        OkHttpDownload.downLoadFile(parameter, new StatusListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onProgress(int progress, long currentLength, long totalLength) {

            }

            @Override
            public void onFinish(final String info) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HttpJarActivity.this, "????????????!" + info, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFail(String errorInfo) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HttpJarActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void loadLocalJar() {
        File dir = new File(downLoadDir);
        if (dir.listFiles() == null || dir.listFiles().length == 0) {
            Toast.makeText(HttpJarActivity.this, "?????????????????????jar???", Toast.LENGTH_SHORT).show();
            return;
        }
        List<String> childFiles = new ArrayList<>();
        for (File file : dir.listFiles()) {
            childFiles.add(file.getName());
        }
        showDialog(childFiles);
    }

    //?????????????????????????????????
    private void showDialog(final List<String> list) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null, false);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();
        ListView listView = view.findViewById(R.id.list_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(HttpJarActivity.this, downLoadDir+"/"+list.get(position), Toast.LENGTH_SHORT).show();
                realLoadJar(list.get(position));
                dialog.dismiss();
            }
        });

        dialog.show();
        //???????????????????????????????????????????????????????????????????????????3/4  ??????????????????show????????????????????????????????????????????????????????????????????????
        dialog.getWindow().setLayout((getScreenWidth() / 4 * 3), LinearLayout.LayoutParams.WRAP_CONTENT);
    }


    public void realLoadJar(String jarName) {

        String jarPath = downLoadDir + "/" + jarName;
        File jarFile = new File(jarPath);
        if (!jarFile.exists()) {
            Toast.makeText(HttpJarActivity.this, jarName + "????????????", Toast.LENGTH_SHORT).show();
            return;
        }
        String clazzName = "com.lxf.genCode.ModuleRareImpl_" + jarName.substring(0,jarName.indexOf("."));
        // "com.lxf.genCode.ModuleRareImpl_?????????"

        String tmpPath = getApplicationContext().getDir("Jar", 0).getAbsolutePath();
        DexClassLoader cl = new DexClassLoader(jarPath, tmpPath, null, this.getClass().getClassLoader());
        Class<?> libProviderCls = null;
        try {
            libProviderCls = cl.loadClass(clazzName);

            Constructor<?> localConstructor = libProviderCls.getConstructor(new Class[]{});

            Object obj = localConstructor.newInstance(new Object[]{});

            Toast.makeText(HttpJarActivity.this, "--????????????-->??????????????????"+clazzName, Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            Toast.makeText(HttpJarActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void clearJar() {
        File dir = new File(downLoadDir);
        for (File file : dir.listFiles()) {
            file.delete();
        }
        Toast.makeText(HttpJarActivity.this, "--????????????--", Toast.LENGTH_SHORT).show();
    }

    /**
     * ??????????????????(px)
     */
    public int getScreenHeight() {
        return this.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * ??????????????????(px)
     */
    public int getScreenWidth() {
        return this.getResources().getDisplayMetrics().widthPixels;
    }

}