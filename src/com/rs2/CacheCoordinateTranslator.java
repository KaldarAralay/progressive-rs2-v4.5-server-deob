/*
 * Decompiled with CFR 0.152.
 */
package com.rs2;

import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;
import com.rs2.util.path.WalkingCollisionMap;

public final class CacheCoordinateTranslator {
    public static boolean a = false;
    private static int[] b = new int[]{9540, 9541, 9797};
    private static int[] c = new int[]{12692, 12693, 12949};
    private static int d = 768;
    private static int e = 5120;

    public static boolean a(int n, int n2) {
        n = GameUtil.a(n, n2);
        int[] nArray = b;
        int n3 = b.length;
        int n4 = 0;
        while (n4 < n3) {
            n2 = nArray[n4];
            if (n == n2) {
                return true;
            }
            ++n4;
        }
        return false;
    }

    public static void a() {
        if (!WalkingCollisionMap.a(9797)) {
            a = true;
        }
    }

    public static void a(Player player) {
        int n = player.getPosition().getX();
        int n2 = player.getPosition().getY();
        if (player.eD < 319 && !a) {
            boolean bl;
            block5: {
                int n3 = n2;
                int n4 = n;
                n4 = GameUtil.a(n4, n3);
                int[] nArray = c;
                int n5 = c.length;
                int n6 = 0;
                while (n6 < n5) {
                    n3 = nArray[n6];
                    if (n4 == n3) {
                        bl = true;
                        break block5;
                    }
                    ++n6;
                }
                bl = false;
            }
            if (bl) {
                player.getPosition().setX(n - d);
                player.getPosition().setY(n2 - e);
                player.getPosition().setPreviousX(player.getPosition().getX());
                player.getPosition().setPreviousY(player.getPosition().getY());
            }
        }
        if (player.eD > 289 && a && CacheCoordinateTranslator.a(n, n2)) {
            player.getPosition().setX(n + d);
            player.getPosition().setY(n2 + e);
            player.getPosition().setPreviousX(player.getPosition().getX());
            player.getPosition().setPreviousY(player.getPosition().getY());
        }
    }
}

