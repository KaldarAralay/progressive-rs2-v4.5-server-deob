/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.area;

import com.rs2.util.ByteArrayReader;
import com.rs2.util.FileUtil;
import com.rs2.util.RectangularArea;

public final class MultiwayAreaDefinition {
    private int b;
    private RectangularArea c;
    private int[] d;
    private static int e = 0;
    public static MultiwayAreaDefinition[] a = new MultiwayAreaDefinition[0];

    public static void a() {
        Object object = FileUtil.readBytes("./data/areas/Multiway.dat");
        ByteArrayReader byteArrayReader = new ByteArrayReader((byte[])object);
        object = byteArrayReader;
        e = byteArrayReader.readUnsignedShort();
        a = new MultiwayAreaDefinition[e];
        int n = 0;
        while (n < e) {
            int n2;
            int[] nArray = null;
            RectangularArea rectangularArea = null;
            int n3 = ((ByteArrayReader)object).readUnsignedByte();
            if (n3 == 0) {
                int n4 = ((ByteArrayReader)object).readUnsignedShort();
                n2 = ((ByteArrayReader)object).readUnsignedShort();
                int n5 = ((ByteArrayReader)object).readUnsignedShort();
                int n6 = ((ByteArrayReader)object).readUnsignedShort();
                rectangularArea = new RectangularArea(n4, n2, n5, n6, 0);
            } else {
                nArray = new int[n3];
                n2 = 0;
                while (n2 < n3) {
                    nArray[n2] = ((ByteArrayReader)object).readUnsignedShort();
                    ++n2;
                }
            }
            MultiwayAreaDefinition.a[n] = new MultiwayAreaDefinition(n, n3, rectangularArea, nArray);
            ++n;
        }
    }

    private MultiwayAreaDefinition(int n, int n2, RectangularArea rectangularArea, int[] nArray) {
        this.b = n2;
        this.c = rectangularArea;
        this.d = nArray;
    }

    public final RectangularArea b() {
        return this.c;
    }

    public final int c() {
        return this.b;
    }

    public final int[] d() {
        return this.d;
    }

    public static MultiwayAreaDefinition a(int n) {
        MultiwayAreaDefinition multiwayAreaDefinition;
        if (n < 0) {
            n = 1;
        }
        if ((multiwayAreaDefinition = a[n]) == null) {
            multiwayAreaDefinition = new MultiwayAreaDefinition(n, 0, null, null);
        }
        return multiwayAreaDefinition;
    }
}

