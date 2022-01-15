package com.lxf.main;

import android.util.Log;

import com.lxf.log.Printer;

public class MyLog implements Printer {
    @Override
    public void log(String s) {
        Log.e("lv123", s);
    }
}
