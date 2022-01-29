package com.lxf.ModuleA;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lxf.Annotation.RouterClass;
import com.lxf.RareApplication;

import java.util.ArrayList;
import java.util.List;

@RouterClass(path = "this_is_TestActivity")
public class TestActivity extends AppCompatActivity {

    private TextView textView;
    HelloWorld helloWorld;
    final List<Person> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        textView = findViewById(R.id.test_text_view);
        helloWorld = RareApplication.createImpl(HelloWorld.class);
        findViewById(R.id.test_btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (helloWorld != null) {
                    Toast.makeText(TestActivity.this, helloWorld.say("你好！Hello World!"), Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.test_btn_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (helloWorld != null) {
                    helloWorld.say(TestActivity.this, "你好！Hello World!", new CallBack2() {
                        @Override
                        public void returnSth(String sth) {
                            Toast.makeText(TestActivity.this, sth, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void returnCallBackSth(String sth, CallBack3 callBack3) {
                            String str = sth + "->TestActivity";
                            callBack3.returnSth(str);
                        }
                    });
                }
            }
        });

        list.add(new Person("张三", "男"));
        list.add(new Person("Tom", "boy"));

        findViewById(R.id.test_btn_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (helloWorld != null) {
                    helloWorld.say(TestActivity.this, list);
                }
            }
        });
    }
}