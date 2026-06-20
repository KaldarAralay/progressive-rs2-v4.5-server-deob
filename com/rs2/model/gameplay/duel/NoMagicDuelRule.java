/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.duel;

import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.player.Player;

final class NoMagicDuelRule
extends DuelRule {
    NoMagicDuelRule(int n2, int n3, int n4) {
    }

    @Override
    public final void a(Player player, boolean bl) {
        boolean bl2;
        boolean bl3 = bl;
        Player player2 = player;
        if (a.a(player2) && b.a(player2)) {
            if (bl3) {
                player2.packetSender.sendGameMessage("You can't have no ranged, no melee and no magic - how would you fight?");
            }
            bl2 = false;
        } else {
            bl2 = true;
        }
        if (bl2) {
            player.getDuelInterfaceManager().a(this.w, "Players cannot use magic");
        }
    }

    @Override
    public final boolean a(Player player) {
        return player.getDuelSession().k()[this.w];
    }
}

