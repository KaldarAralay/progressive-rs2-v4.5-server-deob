/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.cutscene;

import com.rs2.model.cutscene.Cutscene;
import com.rs2.model.task.TickTask;

final class CutsceneEndTask
extends TickTask {
    private /* synthetic */ Cutscene cutscene;

    CutsceneEndTask(Cutscene cutscene, int n) {
        this.cutscene = cutscene;
        super(n);
    }

    @Override
    public final void execute() {
        this.cutscene.finishCutscene();
        this.stop();
    }
}

