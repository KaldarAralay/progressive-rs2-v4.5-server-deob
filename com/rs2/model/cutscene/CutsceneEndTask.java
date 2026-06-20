/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.cutscene;

import com.rs2.model.cutscene.Cutscene;
import com.rs2.model.task.TickTask;

final class CutsceneEndTask
extends TickTask {
    private /* synthetic */ Cutscene a;

    CutsceneEndTask(Cutscene cutscene, int n) {
        this.a = cutscene;
        super(n);
    }

    @Override
    public final void execute() {
        this.a.e();
        this.stop();
    }
}

