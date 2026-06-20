/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.c;

import com.rs2.model.c.aN;

public abstract class aM
extends Enum {
    private static /* enum */ aM a = new aN();
    private static final /* synthetic */ aM[] b;

    static {
        b = new aM[]{a};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private aM() {
        void cfr_renamed_1;
        void cfr_renamed_0;
    }

    public static aM[] values() {
        aM[] aMArray = new aM[1];
        System.arraycopy(b, 0, aMArray, 0, 1);
        return aMArray;
    }

    public static aM valueOf(String string) {
        return Enum.valueOf(aM.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    /* synthetic */ aM(byte by) {
        this((String)cfr_renamed_0, (int)var2_1);
        void var2_1;
        void cfr_renamed_0;
    }
}

