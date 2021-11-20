package com.lxf.ModuleA;

import android.content.Context;

import com.lxf.Annotation.*;

import java.util.List;

public interface GetInfo {
    @RouterMethod(path = "hello_get_info")
    void getPersonName(Context context,Integer personId, List<Person> list);

    @RouterMethod(path = "query_info")
    String queryInfo(Integer personId, CallBack callBack);

    @RouterMethod(path = "start_activity")
    void startActivity(Context context);
}
