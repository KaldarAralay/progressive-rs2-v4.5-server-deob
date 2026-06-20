/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.util;

import com.rs2.ConnectionThrottleSettings;
import com.rs2.Server;

public final class DelayedShutdownTask
implements Runnable {
    private final long delayMillis;

    public DelayedShutdownTask(int n) {
        this.delayMillis = n * 1000;
    }

    @Override
    public final void run() {
        try {
            Thread.sleep(this.delayMillis);
        }
        catch (InterruptedException interruptedException) {
            InterruptedException interruptedException2 = interruptedException;
            interruptedException.printStackTrace();
        }
        ConnectionThrottleSettings.a = false;
        System.exit(0);
        Server.c = true;
    }
}

