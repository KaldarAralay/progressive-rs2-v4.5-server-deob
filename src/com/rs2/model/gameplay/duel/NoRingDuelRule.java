/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.duel;

import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;

final class NoRingDuelRule
extends DuelRule {
    NoRingDuelRule(int n2, int n3, int n4) {
    }

    @Override
    public final void a(Player player, boolean bl) {
        NoRingDuelRule noRingDuelRule = this;
        Player player2 = player;
        if (!noRingDuelRule.a(player2)) {
            player2.getDuelSession().j().add(new ItemStack(player2.getEquipmentManager().getItemIdAtSlot(12)));
        } else {
            player2.getDuelSession().j().remove(new ItemStack(player2.getEquipmentManager().getItemIdAtSlot(12)));
        }
        player.getDuelInterfaceManager().a(this.w, null);
    }

    @Override
    public final boolean a(Player player) {
        return player.getDuelSession().k()[this.w];
    }
}

