/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay;

import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.gameplay.CaveLightManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class SwampGasExplosionTask
extends TickTask {
    private final /* synthetic */ Player a;

    SwampGasExplosionTask(int n, Player player) {
        this.a = player;
        super(7);
    }

    @Override
    public final void execute() {
        if (!this.a.bW()) {
            this.stop();
            return;
        }
        Object object = this.a.eJ();
        if (CaveLightManager.b(this.a) && object != null) {
            ItemStack itemStack = object;
            object = this.a;
            ((Entity)object).applyDirectHit(12, HitType.NORMAL);
            Object object2 = object;
            ((Player)object2).packetSender.sendGameMessage("The swamp gas explodes!");
            object2 = new GraphicEffect(157, 100);
            ((Entity)object).getUpdateState().setGraphic(((GraphicEffect)object2).getId(), ((GraphicEffect)object2).getPackedDelay());
            GameplayHelper.a((Player)object, itemStack.getId(), false);
            return;
        }
        this.stop();
    }
}

