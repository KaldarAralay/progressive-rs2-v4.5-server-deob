/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.runecrafting;

import com.rs2.model.player.Player;
import com.rs2.model.skill.runecrafting.RunecraftingAltarDefinition;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class MysteriousRuinsTeleportTask
extends CycleEvent {
    private final /* synthetic */ Player player;
    private final /* synthetic */ RunecraftingAltarDefinition altarDefinition;

    MysteriousRuinsTeleportTask(Player player, RunecraftingAltarDefinition runecraftingAltarDefinition) {
        this.player = player;
        this.altarDefinition = runecraftingAltarDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        Object object = this.player;
        object.packetSender.sendGameMessage("You feel a powerful force take hold of you...");
        object = this.altarDefinition;
        this.player.moveTo(((RunecraftingAltarDefinition)((Object)object)).altarPosition);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.player.setActionLocked(false);
    }
}

