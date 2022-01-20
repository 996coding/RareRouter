package com.lxf.data;

import com.lxf.data.read.Reader;
import com.lxf.data.write.Writer;

public interface RareParcelable {
    void routerParcelableRead(Reader reader);

    void routerParcelableWrite(Writer writer);

}
