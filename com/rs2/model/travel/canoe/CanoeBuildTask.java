/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.travel.canoe;

import com.rs2.model.player.Player;
import com.rs2.model.skill.GatheringToolDefinition;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.model.travel.canoe.CanoeTravelManager;
import com.rs2.model.travel.canoe.CanoeTypeDefinition;

final class CanoeBuildTask
extends CycleEvent {
    private final /* synthetic */ Player player;
    private final /* synthetic */ int actionSequence;
    private final /* synthetic */ CanoeTypeDefinition canoeType;
    private final /* synthetic */ GatheringToolDefinition gatheringTool;

    CanoeBuildTask(Player player, int n, CanoeTypeDefinition canoeTypeDefinition, GatheringToolDefinition gatheringToolDefinition) {
        this.player = player;
        this.actionSequence = n;
        this.canoeType = canoeTypeDefinition;
        this.gatheringTool = gatheringToolDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.player.isCurrentActionSequence(this.actionSequence)) {
            cycleEventContainer.stop();
            return;
        }
        Object object = this.canoeType;
        this.player.getSkillManager().addExperience(8, object.experience);
        object = this.canoeType;
        this.player.ep[CanoeTravelManager.CANOE_CONFIG_ID] = object.configValue << (this.player.canoeStationIndex << 3);
        object = this.player;
        ((Player)object).packetSender.sendConfig(CanoeTravelManager.CANOE_CONFIG_ID, this.player.ep[CanoeTravelManager.CANOE_CONFIG_ID]);
        object = this.canoeType;
        this.player.builtCanoeTypeConfigValue = object.configValue;
        object = this.player;
        ((Player)object).packetSender.sendSoundEffect(473, 1, 0);
        this.player.getUpdateState().setAnimation(this.gatheringTool.getCanoeAnimationId(), 0);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.player.getMovementQueue().clear();
        this.player.getUpdateState().setAnimation(-1, 0);
    }
}

