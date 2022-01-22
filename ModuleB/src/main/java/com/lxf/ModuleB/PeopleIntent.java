package com.lxf.ModuleB;

import android.os.Parcel;
import android.os.Parcelable;

import com.lxf.Annotation.RouterClass;
import com.lxf.Annotation.RouterBean;
import com.lxf.data.RouterParcelable;
import com.lxf.data.read.Reader;
import com.lxf.data.write.Writer;

@RouterClass(path = "data_people")
@RouterBean(path = "annotate.com.lxf.ModuleB.PeopleIntent")
public class PeopleIntent implements RouterParcelable, Parcelable {
    public String name_Server = "";
    public String sex_Server = "";
    public int id_Server = 0;

    public PeopleIntent(){

    }

    public PeopleIntent(Parcel in) {
        name_Server = in.readString();
        sex_Server = in.readString();
        id_Server = in.readInt();
    }

    public static final Creator<PeopleIntent> CREATOR = new Creator<PeopleIntent>() {
        @Override
        public PeopleIntent createFromParcel(Parcel in) {
            return new PeopleIntent(in);
        }

        @Override
        public PeopleIntent[] newArray(int size) {
            return new PeopleIntent[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(name_Server);
        dest.writeString(sex_Server);
        dest.writeInt(id_Server);
    }
}

