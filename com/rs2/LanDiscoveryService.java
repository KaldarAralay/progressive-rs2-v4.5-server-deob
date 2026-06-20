/*
 * Decompiled with CFR 0.152.
 */
package com.rs2;

import com.rs2.LanDiscoveryListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public final class LanDiscoveryService {
    public static String a = "rs2006sp req";
    public static String b = "rs2006sp resp";
    public static String c = "this is a reserved text for server name";
    public static String d = "tset";
    public static boolean e = false;
    private static DatagramSocket g;
    static boolean f;

    static {
        f = true;
    }

    public static void a() {
        try {
            Object object = a.getBytes();
            object = new DatagramPacket((byte[])object, ((byte[])object).length);
            new LanDiscoveryListener("Connection Listener", (DatagramPacket)object).start();
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    static /* synthetic */ DatagramSocket b() {
        if (g == null) {
            g = new DatagramSocket(8002, InetAddress.getByName("0.0.0.0"));
            g.setBroadcast(true);
        }
        return g;
    }
}

