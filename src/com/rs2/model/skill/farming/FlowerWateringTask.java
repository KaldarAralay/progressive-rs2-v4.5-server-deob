/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.FlowerPatch;
import com.rs2.model.skill.farming.FlowerPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class FlowerWateringTask
extends CycleEvent {
    private /* synthetic */ FlowerPatchManager manager;
    private final /* synthetic */ FlowerPatch patch;

    FlowerWateringTask(FlowerPatchManager flowerPatchManager, FlowerPatch flowerPatch) {
        this.manager = flowerPatchManager;
        this.patch = flowerPatch;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.manager.patchStates[this.patch.getIndex()] = 1;
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        FlowerPatchManager.getPlayer(this.manager).n(false);
        FlowerPatchManager.getPlayer(this.manager).aN();
    }
}

