/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.cooking;

import java.util.HashMap;

public enum PieRecipe {
    a(6032, 2953, 434, 2315, 7164, 7166, 7168, 29, 128.0, true, 3727, 3727, 0),
    b(1982, 1957, 1965, 2315, 7172, 7174, 7176, 34, 138.0, true, 0, 0, 0),
    c(339, 333, 1942, 2315, 7182, 7184, 7186, 47, 164.0, true, 0, 0, 0),
    d(329, 361, 1942, 2315, 7192, 7194, 7196, 70, 210.0, true, 0, 0, 0),
    e(2136, 2876, 3226, 2315, 7202, 7204, 7206, 85, 240.0, true, 0, 0, 0),
    f(5504, 5982, 1955, 2315, 7212, 7214, 7216, 95, 260.0, true, 0, 0, 0);

    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private double o;
    private boolean p;
    private int q;
    private int r;
    private int s;
    private static HashMap t;
    private static HashMap u;
    private static HashMap v;

    static {
        t = new HashMap();
        u = new HashMap();
        v = new HashMap();
        PieRecipe[] pieRecipeArray = PieRecipe.values();
        int n = pieRecipeArray.length;
        int n2 = 0;
        while (n2 < n) {
            PieRecipe pieRecipe = pieRecipeArray[n2];
            t.put(pieRecipe.g, pieRecipe);
            u.put(pieRecipe.k, pieRecipe);
            v.put(pieRecipe.l, pieRecipe);
            ++n2;
        }
    }

    public static PieRecipe a(int n) {
        return (PieRecipe)((Object)t.get(n));
    }

    public static PieRecipe b(int n) {
        return (PieRecipe)((Object)u.get(n));
    }

    public static PieRecipe c(int n) {
        return (PieRecipe)((Object)v.get(n));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private PieRecipe(int n3, int n4, int n5, int n6, int n7, int n8, double d, boolean bl, int n22, int n9, int n10) {
        void var15_12;
        void var10_8;
        this.g = n3;
        this.h = n4;
        this.i = n5;
        this.j = 2315;
        this.k = n7;
        this.l = n8;
        this.m = (int)d;
        this.n = var10_8;
        this.o = (double)bl;
        this.p = true;
        this.q = n10;
        this.r = var15_12;
        this.s = 0;
    }

    public final int a() {
        return this.g;
    }

    public final int b() {
        return this.h;
    }

    public final int c() {
        return this.i;
    }

    public final int d() {
        return this.k;
    }

    public final int e() {
        return this.l;
    }

    public final int f() {
        return this.j;
    }

    public final int g() {
        return this.m;
    }

    public final int h() {
        return this.n;
    }

    public final double i() {
        return this.o;
    }

    public final boolean j() {
        return this.p;
    }

    public final int k() {
        return this.q;
    }

    public final int l() {
        return this.r;
    }

    public final int m() {
        return this.s;
    }
}

