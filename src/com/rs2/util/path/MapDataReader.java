/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.util.path;

public final class MapDataReader {
    private byte[] a;
    private int b;

    public MapDataReader(byte[] byArray) {
        this.a = byArray;
        this.b = 0;
    }

    public final void a(int n) {
        ++this.b;
    }

    public final int a() {
        return this.a[this.b++] & 0xFF;
    }

    public final int b() {
        int n = this.a[this.b] & 0xFF;
        if (n < 128) {
            return this.a();
        }
        MapDataReader mapDataReader = this;
        return (mapDataReader.a() << 8) + mapDataReader.a() - 32768;
    }
}

