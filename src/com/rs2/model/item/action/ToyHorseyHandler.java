/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.item.action;

import com.rs2.model.item.action.ToyHorseyUnlockEvent;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;

public final class ToyHorseyHandler {
    private static String[] a = new String[]{"Come on Dobbin, we can win the race!", "Hi-ho Silver, and away!", "Neaahhhyyy! Giddy-up horsey!"};
    private static int[][] b = new int[][]{{2520, 918}, {2522, 919}, {2524, 920}, {2526, 921}};

    public static boolean a(Player player, int n) {
        int[][] nArray = b;
        int n2 = b.length;
        int n3 = 0;
        while (n3 < n2) {
            int[] nArray2 = nArray[n3];
            if (nArray2[0] == n) {
                player.n(true);
                player.getUpdateState().setAnimation(nArray2[1], 0);
                n = GameUtil.h(a.length);
                player.getUpdateState().setForcedText(a[n]);
                CycleEventHandler.getInstance().schedule(player, new ToyHorseyUnlockEvent(player), 3);
                return true;
            }
            ++n3;
        }
        return false;
    }
}

