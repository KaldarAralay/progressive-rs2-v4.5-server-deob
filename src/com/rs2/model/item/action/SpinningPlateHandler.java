/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.item.action;

import com.rs2.model.item.action.SpinningPlateResultEvent;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEventHandler;

public final class SpinningPlateHandler {
    private static int a = 4613;
    private static int b = 4614;
    private static int c = 1902;
    private static int d = 1904;
    private static int e = 1906;

    public static boolean a(Player player, int n) {
        if (n == a) {
            player.n(true);
            player.getUpdateState().setAnimation(c, 0);
            CycleEventHandler.getInstance().schedule(player, new SpinningPlateResultEvent(player), 5);
            return true;
        }
        return false;
    }

    static /* synthetic */ int a() {
        return e;
    }

    static /* synthetic */ int b() {
        return a;
    }

    static /* synthetic */ int c() {
        return b;
    }

    static /* synthetic */ int d() {
        return d;
    }
}

