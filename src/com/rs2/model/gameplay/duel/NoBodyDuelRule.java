/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.duel;

import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;

final class NoBodyDuelRule
extends DuelRule {
    NoBodyDuelRule(int n2, int n3, int n4) {
    }

    @Override
    public final void a(Player player, boolean bl) {
        NoBodyDuelRule noBodyDuelRule = this;
        Player player2 = player;
        if (!noBodyDuelRule.a(player2)) {
            player2.getDuelSession().j().add(new ItemStack(player2.getEquipmentManager().getItemIdAtSlot(4)));
        } else {
            player2.getDuelSession().j().remove(new ItemStack(player2.getEquipmentManager().getItemIdAtSlot(4)));
        }
        player.getDuelInterfaceManager().a(this.w, null);
    }

    @Override
    public final boolean a(Player player) {
        return player.getDuelSession().k()[this.w];
    }
}

