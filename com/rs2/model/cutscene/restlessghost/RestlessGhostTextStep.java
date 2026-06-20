/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.cutscene.restlessghost;

import com.rs2.model.cutscene.Cutscene;
import com.rs2.model.cutscene.CutsceneStep;
import com.rs2.model.cutscene.restlessghost.RestlessGhostCutscene;

final class RestlessGhostTextStep
extends CutsceneStep {
    private /* synthetic */ RestlessGhostCutscene cutscene;

    RestlessGhostTextStep(RestlessGhostCutscene restlessGhostCutscene, Cutscene cutscene, int n) {
        this.cutscene = restlessGhostCutscene;
        super(cutscene, 3);
    }

    @Override
    public final void executeStep() {
        this.cutscene.ghost.getUpdateState().setForcedText("stranger..");
    }
}

