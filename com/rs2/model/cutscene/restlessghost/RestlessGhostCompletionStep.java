/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.cutscene.restlessghost;

import com.rs2.model.cutscene.Cutscene;
import com.rs2.model.cutscene.CutsceneStep;
import com.rs2.model.cutscene.restlessghost.RestlessGhostCutscene;
import com.rs2.model.item.ItemStack;

final class RestlessGhostCompletionStep
extends CutsceneStep {
    private /* synthetic */ RestlessGhostCutscene cutscene;

    RestlessGhostCompletionStep(RestlessGhostCutscene restlessGhostCutscene, Cutscene cutscene, int n) {
        this.cutscene = restlessGhostCutscene;
        super(cutscene, 5);
    }

    @Override
    public final void executeStep() {
        this.cutscene.player.getInventoryManager().removeItem(new ItemStack(553));
        RestlessGhostCutscene.questScript.awardCompletionRewards(this.cutscene.player);
    }
}

