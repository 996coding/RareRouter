package com.lxf.data;

import com.lxf.nozzle.JavaClassGetter;

import java.util.ArrayList;
import java.util.List;

public class LxfDataFactory {
    public static boolean convert(RouterParcelable source, RouterParcelable des) {
        if (source == null || des == null) {
            return false;
        }
        DataConvert convert = new DataConvert();
        source.routerParcelableRead(convert);
        des.routerParcelableWrite(convert);
        return true;
    }

    public static List convertListEle(List list, RouterParcelable template, String clazz) {
        if (list == null || list.size() == 0) {
            return null;
        }
        List<Object> newList = new ArrayList<>();
        newList.addAll(list);
        while (newList.size() > 0) {
            Object child = newList.get(0);
            newList.remove(0);
            if (child == null) {
                continue;
            }
            if (child instanceof List) {
                if (((List) child).size() > 0) {
                    newList.addAll((List) child);
                }
            }
            if (child instanceof RouterParcelable) {
                Object rp = JavaClassGetter.getParamObjCreator().create(clazz);
                if (rp == null) {
                    try {
                        rp = template.getClass().newInstance();
                    } catch (InstantiationException e) {
                    } catch (IllegalAccessException e) {
                    }
                }

                if (rp == null) {
                    return null;
                }
                convert((RouterParcelable) child, (RouterParcelable) rp);
                child = rp;
            }
        }

        return list;
    }
}
