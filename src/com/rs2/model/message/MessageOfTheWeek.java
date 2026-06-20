/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.message;

import com.rs2.Server;
import com.rs2.model.GameplayHelper;
import com.rs2.util.ByteArrayReader;
import com.rs2.util.CountingDataOutputStream;
import com.rs2.util.FileUtil;
import com.rs2.util.GameUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import org.joda.time.DateTime;

public final class MessageOfTheWeek {
    private static int a = 5993;
    private static int b = 15767;
    private static int c = 15774;
    private static int d = 15819;
    private int e;
    private String[] f;
    private String g;
    private static MessageOfTheWeek[] h = new MessageOfTheWeek[]{new MessageOfTheWeek(c, false, "Your password is only as safe as your computer.", "Install anti-virus software!"), new MessageOfTheWeek(a, false, "Out of date anti-virus software is useless.", "Update it often and run regular scans!")};
    private static MessageOfTheWeek i = new MessageOfTheWeek(b, false, "Halloween has arrived to RuneScape!", "");
    private static MessageOfTheWeek j = new MessageOfTheWeek(d, false, "JaGeX wishes you a Merry Christmas", "and a Happy New Year!");
    private static MessageOfTheWeek k = new MessageOfTheWeek(b, false, "Easter has arrived to RuneScape!", "");

    private MessageOfTheWeek(int n, boolean bl, String ... stringArray) {
        this.e = n;
        this.f = stringArray;
        this.g = "Message of the week";
    }

    public static MessageOfTheWeek a(int n) {
        if (Server.f) {
            return i;
        }
        if (Server.g) {
            return j;
        }
        if (Server.h) {
            return k;
        }
        return h[n];
    }

    private static void e() {
        Object object = new File("./data/messageOfTheWeek.dat");
        ((File)object).delete();
        try {
            object = new CountingDataOutputStream(new FileOutputStream("./data/messageOfTheWeek.dat"));
            ((CountingDataOutputStream)object).writeLong(System.currentTimeMillis());
            ((CountingDataOutputStream)object).writeUnsignedByte(Server.i);
            ((FilterOutputStream)object).close();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public static void a() {
        long l;
        DateTime dateTime;
        long l2 = 0L;
        long l3 = System.currentTimeMillis();
        if (FileUtil.exists("./data/messageOfTheWeek.dat")) {
            try {
                Object object = FileUtil.readBytes("./data/messageOfTheWeek.dat");
                ByteArrayReader byteArrayReader = new ByteArrayReader((byte[])object);
                object = byteArrayReader;
                l2 = byteArrayReader.readLong();
                Server.i = ((ByteArrayReader)object).readUnsignedByte();
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
            }
        }
        if (Server.f || Server.g || Server.h) {
            Server.i = 250;
            MessageOfTheWeek.e();
            return;
        }
        if (GameplayHelper.a(l2, l3) > 0 && (dateTime = new DateTime(l = l3)).dayOfWeek().get() == 1 || Server.i == 250 || l2 == 0L) {
            Server.i = GameUtil.h(h.length);
            MessageOfTheWeek.e();
        }
    }

    public final int b() {
        return this.e;
    }

    public final String c() {
        return this.g;
    }

    public final String[] d() {
        return this.f;
    }
}

