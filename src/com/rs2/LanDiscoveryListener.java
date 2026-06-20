/*
 * Decompiled with CFR 0.152.
 */
package com.rs2;

import com.rs2.LanDiscoveryService;
import java.net.DatagramPacket;
import java.net.InetAddress;

final class LanDiscoveryListener
extends Thread {
    private final /* synthetic */ DatagramPacket a;

    LanDiscoveryListener(String string, DatagramPacket datagramPacket) {
        this.a = datagramPacket;
        super(string);
    }

    @Override
    public final void run() {
        try {
            while (LanDiscoveryService.f) {
                LanDiscoveryService.b().receive(this.a);
                Object object = new String(this.a.getData()).trim();
                if (!((String)object).equals(LanDiscoveryService.a)) continue;
                object = LanDiscoveryService.b.getBytes();
                object = new DatagramPacket((byte[])object, ((Object)object).length, this.a.getAddress(), this.a.getPort());
                LanDiscoveryService.b().send((DatagramPacket)object);
                object = LanDiscoveryService.d.getBytes();
                byte[] byArray = LanDiscoveryService.c.getBytes();
                String string = InetAddress.getLocalHost().getHostName();
                if (((Object)object).length > byArray.length) {
                    string = "invalid";
                }
                object = string.getBytes();
                object = new DatagramPacket((byte[])object, ((Object)object).length, this.a.getAddress(), this.a.getPort());
                LanDiscoveryService.b().send((DatagramPacket)object);
            }
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }
}

