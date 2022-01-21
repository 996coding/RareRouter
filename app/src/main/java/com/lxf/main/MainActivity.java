package com.lxf.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lxf.Annotation.RouterClass;
import com.lxf.ModuleA.ClientActivity;
import com.lxf.Annotation.RouterBean;
import com.lxf.RareApplication;
import com.lxf.log.RareLog;
import com.lxf.manager.RareAppImpl;
import com.lxf.template.RareAdder;

@RouterBean()
@RouterClass(path = "this_is_activity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Main Module");

        setContentView(R.layout.activity_main);

        RareLog.setLogger(new MyLog());

        findViewById(R.id.main_module).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Class<?> clazz = RareApplication.annotateClazz("this_is_TestActivity");
                Log.e("lv123",""+clazz.getName());
                Intent intent = new Intent(MainActivity.this, clazz);
                startActivity(intent);
            }
        });

    }


}