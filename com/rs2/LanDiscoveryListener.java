/*
 * Decompiled with CFR 0.152.
 */
package com.rs2;

import com.rs2.LanDiscoveryService;
import java.net.DatagramPacket;
import java.net.InetAddress;

final class LanDiscoveryListener
extends Thread {
    private final /* synthetic */ DatagramPacket packet;

    LanDiscoveryListener(String string, DatagramPacket datagramPacket) {
        this.packet = datagramPacket;
        super(string);
    }

    @Override
    public final void run() {
        try {
            while (LanDiscoveryService.running) {
                LanDiscoveryService.getSocket().receive(this.packet);
                Object object = new String(this.packet.getData()).trim();
                if (!((String)object).equals(LanDiscoveryService.requestMessage)) continue;
                object = LanDiscoveryService.responseMessage.getBytes();
                object = new DatagramPacket((byte[])object, ((Object)object).length, this.packet.getAddress(), this.packet.getPort());
                LanDiscoveryService.getSocket().send((DatagramPacket)object);
                object = LanDiscoveryService.d.getBytes();
                byte[] byArray = LanDiscoveryService.c.getBytes();
                String string = InetAddress.getLocalHost().getHostName();
                if (((Object)object).length > byArray.length) {
                    string = "invalid";
                }
                object = string.getBytes();
                object = new DatagramPacket((byte[])object, ((Object)object).length, this.packet.getAddress(), this.packet.getPort());
                LanDiscoveryService.getSocket().send((DatagramPacket)object);
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

