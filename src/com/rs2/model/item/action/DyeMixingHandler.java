/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.item.action;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;

public final class DyeMixingHandler {
    private static int a = 1763;
    private static int b = 1765;
    private static int c = 1767;
    private static int d = 1769;
    private static int e = 1771;
    private static int f = 1773;

    public static boolean a(Player player, int n, int n2) {
        boolean bl;
        if (n == a && n2 == b || n == b && n2 == a) {
            player.getInventoryManager().removeItem(new ItemStack(n, 1));
            player.getInventoryManager().removeItem(new ItemStack(n2, 1));
            player.getInventoryManager().addItem(new ItemStack(d, 1));
            player.packetSender.sendGameMessage("You mix the two dyes and make an orange one.");
            bl = true;
        } else if (n == c && n2 == b || n == b && n2 == c) {
            player.getInventoryManager().removeItem(new ItemStack(n, 1));
            player.getInventoryManager().removeItem(new ItemStack(n2, 1));
            player.getInventoryManager().addItem(new ItemStack(e, 1));
            player.packetSender.sendGameMessage("You mix the two dyes and make a green one.");
            bl = true;
        } else if (n == c && n2 == a || n == a && n2 == c) {
            player.getInventoryManager().removeItem(new ItemStack(n, 1));
            player.getInventoryManager().removeItem(new ItemStack(n2, 1));
            player.getInventoryManager().addItem(new ItemStack(f, 1));
            player.packetSender.sendGameMessage("You mix the two dyes and make a purple one.");
            bl = true;
        } else {
            bl = false;
        }
        return bl;
    }
}

