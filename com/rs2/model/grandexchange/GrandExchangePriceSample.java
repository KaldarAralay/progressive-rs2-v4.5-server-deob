/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.grandexchange;

import com.rs2.model.grandexchange.GrandExchangeOffer;
import com.rs2.model.grandexchange.GrandExchangePriceSampleTimestampComparator;
import com.rs2.model.item.ItemDefinition;
import com.rs2.util.ByteArrayReader;
import com.rs2.util.CountingDataOutputStream;
import com.rs2.util.FileUtil;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public final class GrandExchangePriceSample {
    long a;
    private int b;
    private int c;
    private int d;
    private static ArrayList e = new ArrayList();
    private static ArrayList f = new ArrayList();

    GrandExchangePriceSample(int n, int n2, int n3) {
        this.a = System.currentTimeMillis();
        this.b = n;
        this.c = n2;
        this.d = n3;
        ItemDefinition itemDefinition = ItemDefinition.forId(n);
        if (!f.contains(n)) {
            f.add(n);
        }
        itemDefinition.a.add(this);
        e.add(this);
        try {
            GrandExchangePriceSample.b();
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    GrandExchangePriceSample(GrandExchangeOffer object) {
        this.a = System.currentTimeMillis();
        GrandExchangeOffer grandExchangeOffer = object;
        this.b = grandExchangeOffer.a;
        grandExchangeOffer = object;
        this.c = grandExchangeOffer.b;
        grandExchangeOffer = object;
        this.d = grandExchangeOffer.c;
        object = ItemDefinition.forId(this.b);
        if (!f.contains(this.b)) {
            f.add(this.b);
        }
        ((ItemDefinition)object).a.add(this);
        e.add(this);
        try {
            GrandExchangePriceSample.b();
            return;
        }
        catch (IOException iOException) {
            object = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    private GrandExchangePriceSample(long l, int n, int n2, int n3) {
        this.a = l;
        this.b = n;
        this.c = n2;
        this.d = n3;
        ItemDefinition itemDefinition = ItemDefinition.forId(n);
        if (!f.contains(n)) {
            f.add(n);
        }
        itemDefinition.a.add(this);
        e.add(this);
    }

    public static int a(int n) {
        long l = 0L;
        long l2 = 0L;
        Object object = ItemDefinition.forId(n);
        Iterator iterator = ((ItemDefinition)object).a.iterator();
        while (iterator.hasNext()) {
            Object object2 = object = (GrandExchangePriceSample)iterator.next();
            long l3 = ((GrandExchangePriceSample)object).c;
            object2 = object;
            long l4 = ((GrandExchangePriceSample)object2).d;
            l += l3 * l4;
            l2 += l3;
        }
        if (l == 0L || l2 == 0L) {
            return -1;
        }
        return (int)(l / l2);
    }

    public static void a() {
        e.clear();
        if (!FileUtil.exists("./data/geOfferData.dat")) {
            return;
        }
        Object object = FileUtil.readBytes("./data/geOfferData.dat");
        ByteArrayReader byteArrayReader = new ByteArrayReader((byte[])object);
        object = byteArrayReader;
        int n = byteArrayReader.readInt();
        int n2 = 0;
        while (n2 < n) {
            long l = ((ByteArrayReader)object).readLong();
            int n3 = ((ByteArrayReader)object).readUnsignedShort();
            int n4 = ((ByteArrayReader)object).readInt();
            int n5 = ((ByteArrayReader)object).readInt();
            new GrandExchangePriceSample(l, n3, n4, n5);
            ++n2;
        }
        Iterator iterator = f.iterator();
        while (iterator.hasNext()) {
            int n6 = (Integer)iterator.next();
            object = ItemDefinition.forId(n6);
            Collections.sort(((ItemDefinition)object).a, new GrandExchangePriceSampleTimestampComparator());
            double d = 0.0;
            Iterator iterator2 = ((ItemDefinition)object).a.iterator();
            while (iterator2.hasNext()) {
                GrandExchangePriceSample grandExchangePriceSample;
                GrandExchangePriceSample grandExchangePriceSample2 = grandExchangePriceSample = (GrandExchangePriceSample)iterator2.next();
                d += (double)grandExchangePriceSample2.c;
            }
            double d2 = d * 0.75;
            n2 = (int)d2;
            ArrayList<GrandExchangePriceSample> arrayList = new ArrayList<GrandExchangePriceSample>();
            int n7 = 0;
            for (GrandExchangePriceSample grandExchangePriceSample : ((ItemDefinition)object).a) {
                if (n7 >= n2) {
                    arrayList.add(grandExchangePriceSample);
                    continue;
                }
                GrandExchangePriceSample grandExchangePriceSample3 = grandExchangePriceSample;
                n7 += grandExchangePriceSample3.c;
            }
            for (GrandExchangePriceSample grandExchangePriceSample : arrayList) {
                ((ItemDefinition)object).a.remove(grandExchangePriceSample);
                e.remove(grandExchangePriceSample);
            }
        }
    }

    private static void b() {
        CountingDataOutputStream countingDataOutputStream = new CountingDataOutputStream(new FileOutputStream("./data/geOfferData.dat"));
        countingDataOutputStream.writeInt(e.size());
        Iterator iterator = e.iterator();
        while (iterator.hasNext()) {
            GrandExchangePriceSample grandExchangePriceSample;
            GrandExchangePriceSample grandExchangePriceSample2 = grandExchangePriceSample = (GrandExchangePriceSample)iterator.next();
            countingDataOutputStream.writeLong(grandExchangePriceSample2.a);
            grandExchangePriceSample2 = grandExchangePriceSample;
            countingDataOutputStream.writeShort(grandExchangePriceSample2.b);
            grandExchangePriceSample2 = grandExchangePriceSample;
            countingDataOutputStream.writeInt(grandExchangePriceSample2.c);
            grandExchangePriceSample2 = grandExchangePriceSample;
            countingDataOutputStream.writeInt(grandExchangePriceSample2.d);
        }
        countingDataOutputStream.close();
    }
}

