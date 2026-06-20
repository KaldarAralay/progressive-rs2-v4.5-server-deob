/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.model.skill.farming.HopsDefinition;
import com.rs2.model.skill.farming.HopsPatch;
import com.rs2.model.skill.farming.HopsPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class HopsPlantingTask
extends CycleEvent {
    private /* synthetic */ HopsPatchManager manager;
    private final /* synthetic */ HopsPatch patch;
    private final /* synthetic */ int seedId;
    private final /* synthetic */ HopsDefinition definition;

    HopsPlantingTask(HopsPatchManager hopsPatchManager, HopsPatch hopsPatch, int n, HopsDefinition hopsDefinition) {
        this.manager = hopsPatchManager;
        this.patch = hopsPatch;
        this.seedId = n;
        this.definition = hopsDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.manager.patchStates[this.patch.getIndex()] = 0;
        this.manager.cropIds[this.patch.getIndex()] = this.seedId;
        this.manager.lastUpdateTicks[this.patch.getIndex()] = Server.e();
        HopsPatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, this.definition.getPlantingExperience());
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        HopsPatchManager.getPlayer(this.manager).n(false);
    }
}

