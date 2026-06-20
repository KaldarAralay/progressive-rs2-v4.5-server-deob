/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net;

import com.rs2.Server;
import com.rs2.model.player.Player;
import com.rs2.net.packet.PacketDispatcher;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public final class DedicatedReactor
extends Thread {
    private static DedicatedReactor a;
    private final Selector b;

    public DedicatedReactor(Selector selector) {
        this.b = selector;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public final void run() {
        Thread.currentThread().setName("DedicatedReactor");
        block3: while (!Thread.interrupted()) {
            DedicatedReactor dedicatedReactor = a;
            // MONITORENTER : dedicatedReactor
            // MONITOREXIT : dedicatedReactor
            try {
                this.b.select();
                Iterator<SelectionKey> iterator = this.b.selectedKeys().iterator();
                while (true) {
                    if (!iterator.hasNext()) {
                        this.b.selectedKeys().clear();
                        continue block3;
                    }
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isValid() && selectionKey.isAcceptable()) {
                        Server.a(selectionKey);
                        continue;
                    }
                    Player player = (Player)selectionKey.attachment();
                    if (selectionKey.isValid() && selectionKey.isReadable()) {
                        PacketDispatcher.processIncoming(player);
                    }
                    if (!selectionKey.isValid() || !selectionKey.isWritable()) continue;
                    PacketDispatcher.flushOutgoing(player);
                }
            }
            catch (IOException iOException) {
                IOException iOException2 = iOException;
                iOException.printStackTrace();
            }
        }
    }

    public final Selector a() {
        return this.b;
    }

    public static DedicatedReactor b() {
        return a;
    }

    public static void a(DedicatedReactor dedicatedReactor) {
        if (a != null) {
            throw new IllegalStateException("Instance already set");
        }
        a = dedicatedReactor;
    }
}

