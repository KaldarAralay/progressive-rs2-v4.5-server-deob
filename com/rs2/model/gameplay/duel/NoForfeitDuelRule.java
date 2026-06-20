/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.duel;

import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.player.Player;

final class NoForfeitDuelRule
extends DuelRule {
    NoForfeitDuelRule(int n2, int n3, int n4) {
    }

    @Override
    public final void a(Player player, boolean bl) {
        boolean bl2;
        boolean bl3 = bl;
        Player player2 = player;
        if (j.a(player2)) {
            if (bl3) {
                player2.packetSender.sendGameMessage("You can't have no forfeit and no movement - you could run out of ammo");
            }
            bl2 = false;
        } else if (b.a(player2)) {
            if (bl3) {
                player2.packetSender.sendGameMessage("You can't have no forfeit and no melee - you could run out of ammo");
            }
            bl2 = false;
        } else {
            bl2 = true;
        }
        if (bl2) {
            player.getDuelInterfaceManager().a(this.w, "Players cannot forfeit!");
        }
    }

    @Override
    public final boolean a(Player player) {
        return player.getDuelSession().k()[this.w];
    }
}

