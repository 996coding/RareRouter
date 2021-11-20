package com.lxf.data;

import com.lxf.data.read.Reader;
import com.lxf.data.write.Writer;

public interface RouterParcelable {
    void routerParcelableRead(Reader reader);

    void routerParcelableWrite(Writer writer);

}
