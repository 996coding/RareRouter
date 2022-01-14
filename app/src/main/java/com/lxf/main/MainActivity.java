package com.lxf.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lxf.Annotation.RouterClass;
import com.lxf.ModuleA.ClientActivity;
import com.lxf.Annotation.RouterBean;
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

        findViewById(R.id.main_module).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ClientActivity.class);
                startActivity(intent);
            }
        });
        RareAppImpl.getRareAppImpl().autoAddRareImpl();
        Log.e("lv123"," rare adder   = "+ RareAppImpl.getRareAppImpl().getRareImplList().size());
    }



}