package com.lxf.ModuleB;

import com.lxf.Annotation.RouterBean;
import com.lxf.data.read.Reader;
import com.lxf.data.RouterParcelable;
import com.lxf.data.write.Writer;

@RouterBean()
public class People implements RouterParcelable {
    public String name_Server = "";
    public String sex_Server = "";
    public int id_Server = 0;

    @Override
    public void routerParcelableRead(Reader reader) {
        reader.read(name_Server);
        reader.read(sex_Server);
        reader.read(id_Server);
    }

    @Override
    public void routerParcelableWrite(Writer writer) {
        name_Server = writer.writeStr();
        sex_Server = writer.writeStr();
        id_Server = writer.writeInt();
    }

    @Override
    public String toString() {
        return "People{" +
                "name_Server='" + name_Server + '\'' +
                ", sex_Server='" + sex_Server + '\'' +
                ", id_Server=" + id_Server +
                '}';
    }
}
