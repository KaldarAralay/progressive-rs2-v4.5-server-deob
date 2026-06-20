/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.duel;

import com.rs2.model.combat.CombatType;
import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;

final class NoRangedDuelRule
extends DuelRule {
    NoRangedDuelRule(int n2, int n3, int n4) {
    }

    @Override
    public final void a(Player player, boolean bl) {
        boolean bl2;
        boolean bl3 = bl;
        Player player2 = player;
        Object object = this;
        if (b.a(player2) && c.a(player2)) {
            if (bl3) {
                object = player2;
                ((Player)object).packetSender.sendGameMessage("You can't have no ranged, no melee and no magic - how would you fight?");
            }
            bl2 = false;
        } else {
            if (!object.a(player2)) {
                if (player2.getDuelSession().b() == CombatType.RANGED) {
                    player2.getDuelSession().j().add(new ItemStack(player2.getEquipmentManager().getItemIdAtSlot(3)));
                }
            } else if (player2.getDuelSession().b() == CombatType.RANGED) {
                player2.getDuelSession().j().remove(new ItemStack(player2.getEquipmentManager().getItemIdAtSlot(3)));
            }
            bl2 = true;
        }
        if (bl2) {
            player.getDuelInterfaceManager().a(this.w, "Players cannot use range");
        }
    }

    @Override
    public final boolean a(Player player) {
        return player.getDuelSession().k()[this.w];
    }
}

