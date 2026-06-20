/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay;

import com.rs2.model.World;
import com.rs2.model.gameplay.DesertThirstTask;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;

public final class DesertHeatManager {
    public static int a = 1833;
    public static int b = 1835;
    public static int c = 1837;
    public static int d = 6392;
    public static int e = 6394;
    public static int f = 6396;
    public static int g = 6398;
    public static int h = 6400;
    public static int i = 6402;
    public static int j = 6404;
    public static int k = 6406;
    private static int[] l = new int[]{12589, 12844, 12845, 12846, 12848, 12847, 13100, 13101, 13102, 13356, 13357, 13359, 13360, 13614, 13615, 13616, 13617, 13872, 13873};

    static boolean a(Player player) {
        int n = GameUtil.a(player.getPosition().getX(), player.getPosition().getY());
        int[] nArray = l;
        int n2 = l.length;
        int n3 = 0;
        while (n3 < n2) {
            int n4 = nArray[n3];
            if (n4 == n) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    public static boolean b(Player player) {
        if (player.er == 1234) {
            return true;
        }
        if (DesertHeatManager.a(player)) {
            DesertThirstTask desertThirstTask = new DesertThirstTask(150, player);
            World.getTaskScheduler().schedule(desertThirstTask);
            player.er = 1234;
            return true;
        }
        return false;
    }
}

