package com.lxf.ModuleA;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lxf.Annotation.RouterClass;
import com.lxf.RareApplication;

import java.util.ArrayList;
import java.util.List;

@RouterClass(path = "this_is_TestActivity")
public class TestActivity extends AppCompatActivity {

    private TextView textView;
    HelloWorld_Visitor visitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Module A");
        setContentView(R.layout.activity_test);

        textView = findViewById(R.id.test_text_view);
        visitor = RareApplication.createImpl(HelloWorld_Visitor.class);
        findViewById(R.id.test_btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick1();
            }
        });

        findViewById(R.id.test_btn_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick2();
            }
        });


        findViewById(R.id.test_btn_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick3();
            }
        });
        findViewById(R.id.test_btn_static_method).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick_static();
            }
        });

        findViewById(R.id.test_btn_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick4();
            }
        });
        findViewById(R.id.test_btn_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick5();
            }
        });
        findViewById(R.id.test_btn_6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick6();
            }
        });
    }

    private void onClick1() {
        if (visitor != null) {
            Toast.makeText(TestActivity.this, visitor.say("你好！Hello World!"), Toast.LENGTH_SHORT).show();
        }
    }

    private void onClick2() {
        if (visitor != null) {
            visitor.say(TestActivity.this, "你好！Hello World!", new CallBack2() {
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

    private void onClick3() {
        if (visitor != null) {
            List<Person> list = new ArrayList<>();
            list.add(new Person("张三", "男"));
            list.add(new Person("Tom", "boy"));
            visitor.say(TestActivity.this, list);
        }
    }

    private void onClick4() {
        RareApplication.startIntent(TestActivity.this, "demo_activity_module_B");
    }

    private void onClick5() {
        Class<?> clazz = RareApplication.annotateClazz("demo_activity_module_B");
        Intent intent = new Intent(TestActivity.this, clazz);
        startActivity(intent);
    }

    private void onClick6() {
        if (visitor != null) {
            visitor.startNewPage(TestActivity.this);
        }
    }

    private void onClick_static() {
        if (visitor != null) {
            Toast.makeText(TestActivity.this, visitor.getWords(), Toast.LENGTH_LONG).show();

        }
    }
}