/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.cutscene;

import com.rs2.model.cutscene.Cutscene;
import com.rs2.model.cutscene.CutsceneStep;
import com.rs2.model.task.TickTask;

final class CutsceneStepTask
extends TickTask {
    private final /* synthetic */ CutsceneStep a;

    CutsceneStepTask(Cutscene cutscene, int n, CutsceneStep cutsceneStep) {
        this.a = cutsceneStep;
        super(n);
    }

    @Override
    public final void execute() {
        this.a.a();
        this.stop();
    }
}

