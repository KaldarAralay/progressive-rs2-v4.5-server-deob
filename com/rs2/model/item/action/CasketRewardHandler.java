/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.item.action;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;

public final class CasketRewardHandler {
    private static int[] a = new int[]{995, 1623, 1454};
    private static int[] b = new int[]{1621, 1619, 1452};
    private static int[] c = new int[]{1617, 985, 987, 1462};

    public static void a(Player player) {
        int n = GameUtil.g(999) == 0 ? c[GameUtil.f(4)] : (GameUtil.g(99) == 0 ? b[GameUtil.f(3)] : a[GameUtil.f(3)]);
        player.getInventoryManager().addItem(new ItemStack(n, n == 995 ? GameUtil.g(2980) + 20 : 1));
        player.packetSender.sendGameMessage("You open the casket and find some treasure inside.");
    }
}

