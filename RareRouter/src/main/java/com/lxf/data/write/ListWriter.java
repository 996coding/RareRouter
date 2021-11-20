package com.lxf.data.write;

import com.lxf.data.RouterParcelable;

import java.util.List;

interface ListWriter {
    void writeListByte(List<Byte> list);

    void writeListShort(List<Short> list);

    void writeListInt(List<Integer> list);

    void writeListLong(List<Long> list);

    void writeListFloat(List<Float> list);

    void writeListDouble(List<Double> list);

    void writeListChar(List<Character> list);

    void writeBool(List<Boolean> list);

    void writeListStr(List<String> list);

    void writeListObj(List<RouterParcelable> list);

    void writeLists(List<List<?>> list);
}
