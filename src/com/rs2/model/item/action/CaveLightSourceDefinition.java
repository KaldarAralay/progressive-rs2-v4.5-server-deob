/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.item.action;

import java.util.HashMap;
import java.util.Map;

public enum CaveLightSourceDefinition {
    a(1, 1, true, true, 596, 594),
    b(1, 1, true, true, 36, 33),
    c(1, 1, true, true, 38, 32),
    d(4, 1, false, true, 4529, 4531),
    e(12, 2, true, true, 4522, 4524),
    f(26, 2, false, true, 4537, 4539),
    g(49, 3, false, false, 4548, 4550),
    h(49, 2, false, false, 4701, 4702),
    i(49, 3, false, false, 9064, 9065),
    j(65, 2, false, false, 5014, 5013);

    private int k;
    private int l;
    private int m;
    private int n;
    private boolean o;
    private static Map p;

    static {
        p = new HashMap();
        CaveLightSourceDefinition[] caveLightSourceDefinitionArray = CaveLightSourceDefinition.values();
        int n = caveLightSourceDefinitionArray.length;
        int n2 = 0;
        while (n2 < n) {
            CaveLightSourceDefinition caveLightSourceDefinition = caveLightSourceDefinitionArray[n2];
            p.put(caveLightSourceDefinition.l, caveLightSourceDefinition);
            p.put(caveLightSourceDefinition.m, caveLightSourceDefinition);
            ++n2;
        }
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private CaveLightSourceDefinition(boolean bl, boolean bl2, int n3, int n4) {
        void var8_6;
        void var7_5;
        this.k = bl ? 1 : 0;
        this.l = var7_5;
        this.m = var8_6;
        this.n = bl2 ? 1 : 0;
        this.o = n3;
    }

    public final int a() {
        return this.k;
    }

    public final int b() {
        return this.l;
    }

    public final int c() {
        return this.m;
    }

    public final int d() {
        return this.n;
    }

    public final boolean e() {
        return this.o;
    }

    public static CaveLightSourceDefinition a(int n) {
        return (CaveLightSourceDefinition)((Object)p.get(n));
    }
}

