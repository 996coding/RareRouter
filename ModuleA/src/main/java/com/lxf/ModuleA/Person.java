package com.lxf.ModuleA;

import com.lxf.Annotation.RouterBean;
import com.lxf.data.read.Reader;
import com.lxf.data.RouterParcelable;
import com.lxf.data.write.Writer;

@RouterBean(path = "annotate.com.lxf.ModuleA.Person")
public class Person implements RouterParcelable {
    public String name = "";
    public String sex = "";
    public int id = 0;

    public Person() {
    }

    public Person(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public Person(String name, String sex, int id) {
        this.name = name;
        this.sex = sex;
        this.id = id;
    }

    @Override
    public void routerParcelableRead(Reader reader) {
        reader.read(name);
        reader.read(sex);
        reader.read(id);
    }

    @Override
    public void routerParcelableWrite(Writer writer) {
        name = writer.writeStr();
        sex = writer.writeStr();
        id = writer.writeInt();
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", id=" + id +
                '}';
    }
}
