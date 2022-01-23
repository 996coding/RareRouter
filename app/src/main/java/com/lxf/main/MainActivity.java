package com.lxf.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.lxf.Annotation.RouterClass;
import com.lxf.Annotation.RouterBean;
import com.lxf.RareApplication;
import com.lxf.log.RareLog;

@RouterBean(path = "annotate.com.lxf.main.MainActivity")
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
//                Class<?> clazz = RareApplication.annotateClazz("this_is_TestActivity");
//                Intent intent = new Intent(MainActivity.this, clazz);
//                startActivity(intent);
                RareApplication.startIntent(MainActivity.this, "this_is_TestActivity");
            }
        });

    }


}