/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat.special;

import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.special.MagicShortbowSpecialAttack;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class MagicShortbowDelayedGraphicTask
extends TickTask {
    private final /* synthetic */ Player player;

    MagicShortbowDelayedGraphicTask(MagicShortbowSpecialAttack magicShortbowSpecialAttack, int n, Player player) {
        this.player = player;
        super(1);
    }

    @Override
    public final void execute() {
        this.player.getUpdateState().setGraphic(new GraphicEffect(256, 90).withDelay15(15));
        this.stop();
    }
}

