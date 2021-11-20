package com.lxf.ModuleA;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.lxf.Annotation.RouterClass;
import com.lxf.RareRouter.RareRouter;
import com.lxf.data.LxfDataFactory;
import com.lxf.data.RouterParcelable;

import java.util.ArrayList;
import java.util.List;

@RouterClass(path = "here_is_activity")
public class ClientActivity extends AppCompatActivity {


    private TextView textView;
    private GetInfo getInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Module A");
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.CYAN));

        setContentView(R.layout.activity_client);

        textView = findViewById(R.id.text_view);
        getInfo = RareRouter.create(GetInfo.class);

        findViewById(R.id.client_btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                funA();
            }
        });

        findViewById(R.id.client_btn_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                funB();
            }
        });

        findViewById(R.id.client_btn_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                funC();
            }
        });

        findViewById(R.id.client_btn_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                funD();
            }
        });

        findViewById(R.id.client_btn_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                funE();
            }
        });

    }

    //同步调用
    private void funA() {
        if (getInfo == null) {
            return;
        }
        List<Person> list = new ArrayList<>();
        list.add(new Person("张三", "男", 666));
        list.add(new Person("李四", "男", 777));
        getInfo.getPersonName(this,100, list);
    }

    //异步调用
    private void funB() {
        if (getInfo == null) {
            return;
        }

        String returnString = getInfo.queryInfo(10086, new CallBack() {
            @Override
            public void onSuccess(final Person person) {
                ClientActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(textView.getText() + "\n" + person.toString());
                    }
                });
            }

            @Override
            public void onFailed() {
            }
        });
        textView.setText(returnString);
    }

    //启动另一个模块的activity
    private void funC() {
        if (getInfo == null) {
            return;
        }
        getInfo.startActivity(this);
    }

    public void funD() {
        Person person = new Person("Tom", "man", 10010);
        Object aimObj = RareRouter.getModuleObj("data_people");
        LxfDataFactory.convert(person, (RouterParcelable) aimObj);

        Intent intent = new Intent(this, RareRouter.getActivityClass("that_is_activity"));
        intent.putExtra("key", (Parcelable) aimObj);
        startActivity(intent);
    }

    public void funE() {
        RareRouter.startAndroidComponent(this, "that_is_activity");
    }
}