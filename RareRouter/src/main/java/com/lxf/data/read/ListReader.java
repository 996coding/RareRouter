package com.lxf.data.read;

import com.lxf.data.RouterParcelable;

import java.util.List;

interface ListReader {
    void readListByte(List<Byte> list);

    void readListShort(List<Short> list);

    void readListInt(List<Integer> list);

    void readListLong(List<Long> list);

    void readListFloat(List<Float> list);

    void readListDouble(List<Double> list);

    void readListChar(List<Character> list);

    void readBool(List<Boolean> list);

    void readListStr(List<String> list);

    void readListObj(List<RouterParcelable> list);

    void readLists(List<List<?>> list);
}
