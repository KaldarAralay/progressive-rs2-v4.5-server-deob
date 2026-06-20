/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill;

import com.rs2.model.item.ItemStack;

public enum GatheringToolDefinition {
    a(6739, 61, 3.75, new int[]{2846, 2847}, 0, 6743, 6741, 8),
    b(1359, 41, 3.5, new int[]{867, 868}, 0, 520, 506, 8),
    c(1357, 31, 3.0, new int[]{869, 870}, 0, 518, 504, 8),
    d(1355, 21, 2.5, new int[]{871, 872}, 0, 516, 502, 8),
    e(1361, 11, 2.25, new int[]{873, 874}, 0, 514, 500, 8),
    f(1353, 6, 2.0, new int[]{875, 876}, 0, 512, 498, 8),
    g(1349, 1, 1.5, new int[]{877, 878}, 0, 510, 496, 8),
    h(1351, 1, 1.0, new int[]{879, 880}, 0, 508, 494, 8),
    i(1275, 41, 3.0, new int[]{624}, 432, 490, 478, 14),
    j(1271, 31, 4.0, new int[]{628}, 432, 488, 476, 14),
    k(1273, 21, 5.0, new int[]{629}, 432, 486, 474, 14),
    l(1269, 6, 6.0, new int[]{627}, 432, 484, 472, 14),
    m(1267, 1, 7.0, new int[]{626}, 432, 482, 470, 14),
    n(1265, 1, 8.0, new int[]{625}, 432, 480, 468, 14);

    private int o;
    private int p;
    private int[] q;
    private double r;
    private int s;
    private int t;
    private int u;
    private int v;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private GatheringToolDefinition(double d, int[] nArray, int n3, int n22, int n4, int n5) {
        void var11_8;
        void var10_7;
        void cfr_renamed_4;
        void var4_2;
        this.o = (int)d;
        this.p = var4_2;
        this.r = (double)nArray;
        this.q = cfr_renamed_4;
        this.s = n4;
        this.t = n5;
        this.u = var10_7;
        this.v = var11_8;
    }

    public final int a() {
        return this.o;
    }

    public final int b() {
        return this.p;
    }

    public final int c() {
        return this.t;
    }

    public final int d() {
        return this.q[0];
    }

    public final int e() {
        return this.q[1];
    }

    public final double f() {
        return this.r;
    }

    public final int g() {
        return this.s;
    }

    public final int h() {
        return this.u;
    }

    public final int i() {
        return this.v;
    }

    public final int j() {
        Object object = this;
        object = new ItemStack(object.u, 1);
        return ((ItemStack)object).getDefinition().n();
    }
}

