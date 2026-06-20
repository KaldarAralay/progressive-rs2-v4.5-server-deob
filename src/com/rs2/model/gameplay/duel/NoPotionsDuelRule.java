/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.duel;

import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.player.Player;

final class NoPotionsDuelRule
extends DuelRule {
    NoPotionsDuelRule(int n2, int n3, int n4) {
    }

    @Override
    public final void a(Player player, boolean bl) {
        player.getDuelInterfaceManager().a(this.w, "Players cannot drink potions.");
    }

    @Override
    public final boolean a(Player player) {
        return player.getDuelSession().k()[this.w];
    }
}

