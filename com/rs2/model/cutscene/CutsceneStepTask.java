/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.cutscene;

import com.rs2.model.cutscene.Cutscene;
import com.rs2.model.cutscene.CutsceneStep;
import com.rs2.model.task.TickTask;

final class CutsceneStepTask
extends TickTask {
    private final /* synthetic */ CutsceneStep step;

    CutsceneStepTask(Cutscene cutscene, int n, CutsceneStep cutsceneStep) {
        this.step = cutsceneStep;
        super(n);
    }

    @Override
    public final void execute() {
        this.step.executeStep();
        this.stop();
    }
}

