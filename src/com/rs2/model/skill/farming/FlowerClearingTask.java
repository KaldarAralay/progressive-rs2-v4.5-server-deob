/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.FlowerPatch;
import com.rs2.model.skill.farming.FlowerPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class FlowerClearingTask
extends CycleEvent {
    private /* synthetic */ FlowerPatchManager manager;
    private final /* synthetic */ int animationId;
    private final /* synthetic */ FlowerPatch patch;

    FlowerClearingTask(FlowerPatchManager flowerPatchManager, int n, FlowerPatch flowerPatch) {
        this.manager = flowerPatchManager;
        this.animationId = n;
        this.patch = flowerPatch;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        FlowerPatchManager.getPlayer(this.manager).getUpdateState().setAnimation(this.animationId);
        boolean bl = false;
        if (this.manager.growthStages[this.patch.getIndex()] <= 2) {
            int n = this.patch.getIndex();
            this.manager.growthStages[n] = this.manager.growthStages[n] + 1;
            FlowerPatchManager.getPlayer(this.manager).getInventoryManager().b(new ItemStack(6055));
        } else {
            this.manager.growthStages[this.patch.getIndex()] = 3;
            bl = true;
            cycleEventContainer.stop();
        }
        FlowerPatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, 4.0);
        this.manager.lastUpdateTicks[this.patch.getIndex()] = Server.e();
        this.manager.refreshConfig();
        if (this.manager.growthStages[this.patch.getIndex()] == 3 && !bl) {
            cycleEventContainer.stop();
            return;
        }
    }

    @Override
    public final void onStop() {
        FlowerPatchManager.a(this.manager, this.patch.getIndex());
        Player player = FlowerPatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You clear the patch.");
        FlowerPatchManager.getPlayer(this.manager).n(false);
        FlowerPatchManager.getPlayer(this.manager).aN();
    }
}

