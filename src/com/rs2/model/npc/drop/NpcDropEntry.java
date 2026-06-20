/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.npc.drop;

public final class NpcDropEntry {
    private int a;
    private int b;
    private int c;
    private int d;
    private int[] e;
    private int[] f;
    private int g = -1;
    private int h = -1;
    private int i = -1;
    private int[] j;
    private int[] k;

    public NpcDropEntry(int n, int n2, int n3, int n4, int[] nArray, int[] nArray2, int n5, int n6, int n7, int[] nArray3, int[] nArray4) {
        this.a = n;
        this.b = n2;
        this.c = n3;
        this.d = n4;
        this.e = nArray;
        this.f = nArray2;
        this.g = n5;
        this.h = n6;
        this.i = n7;
        this.j = nArray3;
        this.k = nArray4;
    }

    public final void a(int n) {
        this.h = n;
    }

    public final void b(int n) {
        this.i = n;
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
        return this.a;
    }

    public final int e() {
        return this.b;
    }

    public final int f() {
        return this.c;
    }

    public final int g() {
        return this.d;
    }

    public final int[] h() {
        if (this.e == null) {
            return new int[0];
        }
        return this.e;
    }

    public final int[] i() {
        if (this.f == null) {
            return new int[0];
        }
        return this.f;
    }

    public final int[] j() {
        if (this.j == null) {
            return new int[0];
        }
        return this.j;
    }

    public final int[] k() {
        if (this.k == null) {
            return new int[0];
        }
        return this.k;
    }
}

