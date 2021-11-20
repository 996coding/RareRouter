package com.lxf.ModuleB;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.lxf.Annotation.RouterClass;

@RouterClass(path = "that_is_activity")
public class MainActivityServer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Module B");
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.YELLOW));

        setContentView(R.layout.activity_main_server);

        Intent intent = getIntent();
        if (intent!=null){
            PeopleIntent p = intent.getParcelableExtra("key");
            if (p!=null){
                Log.e("lv123",p.toString());
            }
        }
    }
}