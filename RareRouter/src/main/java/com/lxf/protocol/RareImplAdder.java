package com.lxf.protocol;

import com.lxf.Router.RareCore;

public final class RareImplAdder {
    public static void addRareImpl(RareInterface impl) {
        RareCore.addRareImpl(impl);
    }
}
