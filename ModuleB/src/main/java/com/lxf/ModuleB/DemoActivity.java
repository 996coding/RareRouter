package com.lxf.ModuleB;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lxf.Annotation.RouterClass;

@RouterClass(path = "demo_activity_module_B")
public class DemoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Module B");
        setContentView(R.layout.activity_demo);
    }
}