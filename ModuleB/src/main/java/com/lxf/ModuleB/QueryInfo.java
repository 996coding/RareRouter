package com.lxf.ModuleB;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.lxf.Annotation.RouterMethod;

import java.util.List;

public class QueryInfo {

    @RouterMethod(path = "hello_get_info")
    public void getPersonName_Imp(Context context,int personId, List<People> list) {
        Toast.makeText(context,list.toString(),Toast.LENGTH_LONG).show();
    }

    @RouterMethod(path = "query_info")
    public String serverQueryInfo(final Integer personId, final CallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                People people = new People();
                people.id_Server = personId;
                people.name_Server = "张三儿";
                people.sex_Server = "男";
                callBack.onSuccess(people);
            }
        }).start();

        return "正在调用Module-B的方法，请稍等...";
    }

    @RouterMethod(path = "start_activity")
    public void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivityServer.class);
        context.startActivity(intent);
    }
}
