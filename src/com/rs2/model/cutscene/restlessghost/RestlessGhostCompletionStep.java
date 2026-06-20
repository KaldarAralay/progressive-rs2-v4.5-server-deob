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
    private /* synthetic */ RestlessGhostCutscene a;

    RestlessGhostCompletionStep(RestlessGhostCutscene restlessGhostCutscene, Cutscene cutscene, int n) {
        this.a = restlessGhostCutscene;
        super(cutscene, 5);
    }

    @Override
    public final void a() {
        this.a.b.getInventoryManager().removeItem(new ItemStack(553));
        RestlessGhostCutscene.a.c(this.a.b);
    }
}

