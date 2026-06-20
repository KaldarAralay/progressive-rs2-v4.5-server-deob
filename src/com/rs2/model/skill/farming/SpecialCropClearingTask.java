/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.SpecialCropPatch;
import com.rs2.model.skill.farming.SpecialCropPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class SpecialCropClearingTask
extends CycleEvent {
    private /* synthetic */ SpecialCropPatchManager manager;
    private final /* synthetic */ int animationId;
    private final /* synthetic */ SpecialCropPatch patch;

    SpecialCropClearingTask(SpecialCropPatchManager specialCropPatchManager, int n, SpecialCropPatch specialCropPatch) {
        this.manager = specialCropPatchManager;
        this.animationId = n;
        this.patch = specialCropPatch;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        SpecialCropPatchManager.getPlayer(this.manager).getUpdateState().setAnimation(this.animationId);
        boolean bl = false;
        if (this.manager.growthStages[this.patch.getIndex()] <= 2) {
            int n = this.patch.getIndex();
            this.manager.growthStages[n] = this.manager.growthStages[n] + 1;
            SpecialCropPatchManager.getPlayer(this.manager).getInventoryManager().b(new ItemStack(6055));
        } else {
            this.manager.growthStages[this.patch.getIndex()] = 3;
            bl = true;
            cycleEventContainer.stop();
        }
        SpecialCropPatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, 4.0);
        this.manager.lastUpdateTicks[this.patch.getIndex()] = Server.e();
        this.manager.refreshConfig();
        if (this.manager.growthStages[this.patch.getIndex()] == 3 && !bl) {
            cycleEventContainer.stop();
            return;
        }
    }

    @Override
    public final void onStop() {
        SpecialCropPatchManager.a(this.manager, this.patch.getIndex());
        Player player = SpecialCropPatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You clear the patch.");
        SpecialCropPatchManager.getPlayer(this.manager).n(false);
        SpecialCropPatchManager.getPlayer(this.manager).aN();
    }
}

