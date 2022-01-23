package com.lxf.protocol;

import com.lxf.Router.RareAppImpl;

public final class RareImplAdder {
    public static void addRareImpl(RareInterface impl) {
        RareAppImpl.addRareImpl(impl);
    }
}
