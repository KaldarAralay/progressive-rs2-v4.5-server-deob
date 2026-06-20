/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.magetrainingarena;

import com.rs2.model.World;
import com.rs2.model.gameplay.magetrainingarena.EnchantmentChamberController;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class EnchantmentChamberColorTask
extends TickTask {
    EnchantmentChamberColorTask(int n) {
        super(40);
    }

    @Override
    public final void execute() {
        EnchantmentChamberController.b(EnchantmentChamberController.e()[EnchantmentChamberController.b.nextInt(EnchantmentChamberController.e().length)]);
        Player[] playerArray = World.f();
        int n = playerArray.length;
        int n2 = 0;
        while (n2 < n) {
            Player player = playerArray[n2];
            if (player != null && player.getEnchantmentChamberController().c()) {
                player.getEnchantmentChamberController().a(EnchantmentChamberController.f());
            }
            ++n2;
        }
        if (EnchantmentChamberController.a() != null) {
            EnchantmentChamberController.a().getUpdateState().setForcedTextAndMarkUpdated("The color shape is now " + EnchantmentChamberController.f() + "!");
        }
    }
}

