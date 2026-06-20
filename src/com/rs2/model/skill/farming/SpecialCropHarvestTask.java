/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.SpecialCropDefinition;
import com.rs2.model.skill.farming.SpecialCropPatch;
import com.rs2.model.skill.farming.SpecialCropPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class SpecialCropHarvestTask
extends CycleEvent {
    private /* synthetic */ SpecialCropPatchManager manager;
    private final /* synthetic */ int actionSequence;
    private final /* synthetic */ SpecialCropPatch patch;
    private final /* synthetic */ SpecialCropDefinition definition;

    SpecialCropHarvestTask(SpecialCropPatchManager specialCropPatchManager, int n, SpecialCropPatch specialCropPatch, SpecialCropDefinition specialCropDefinition) {
        this.manager = specialCropPatchManager;
        this.actionSequence = n;
        this.patch = specialCropPatch;
        this.definition = specialCropDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!SpecialCropPatchManager.getPlayer(this.manager).isCurrentActionSequence(this.actionSequence) || SpecialCropPatchManager.getPlayer(this.manager).getInventoryManager().getContainer().getFreeSlots() <= 0) {
            cycleEventContainer.stop();
            return;
        }
        if (this.manager.patchStates[this.patch.getIndex()] == 3) {
            Player player = SpecialCropPatchManager.getPlayer(this.manager);
            player.packetSender.sendGameMessage("You examine the plant for signs of disease and find that it's in perfect health.");
            SpecialCropPatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, this.definition.getHealthCheckExperience());
            this.manager.patchStates[this.patch.getIndex()] = 0;
            this.manager.f[this.patch.getIndex()] = false;
            this.manager.lastUpdateTicks[this.patch.getIndex()] = Server.e() - (long)this.definition.getTotalGrowthTicks();
            this.manager.recalculateRegrowthStage(this.patch.getIndex());
            cycleEventContainer.stop();
            return;
        }
        Player player = SpecialCropPatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You harvest the crop, and pick some " + ItemDefinition.forId(this.definition.getProduceItemId()).getName().toLowerCase() + ".");
        SpecialCropPatchManager.getPlayer(this.manager).getInventoryManager().addItem(new ItemStack(this.definition.getProduceItemId()));
        SpecialCropPatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, this.definition.getHarvestExperience());
        switch (this.definition) {
            case BELLADONNA: {
                SpecialCropPatchManager.a(this.manager, this.patch.getIndex());
                this.manager.growthStages[this.patch.getIndex()] = 3;
                this.manager.lastUpdateTicks[this.patch.getIndex()] = Server.e();
                break;
            }
            case CACTUS: {
                int n = this.patch.getIndex();
                this.manager.growthStages[n] = this.manager.growthStages[n] - 1;
                break;
            }
            case MUSHROOM: {
                int n = this.patch.getIndex();
                this.manager.growthStages[n] = this.manager.growthStages[n] + 1;
                if (this.manager.growthStages[this.patch.getIndex()] != 16) break;
                SpecialCropPatchManager.a(this.manager, this.patch.getIndex());
                this.manager.growthStages[this.patch.getIndex()] = 3;
                this.manager.lastUpdateTicks[this.patch.getIndex()] = Server.e();
            }
        }
        this.manager.refreshConfig();
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        SpecialCropPatchManager.getPlayer(this.manager).n(false);
        SpecialCropPatchManager.getPlayer(this.manager).aN();
    }
}

