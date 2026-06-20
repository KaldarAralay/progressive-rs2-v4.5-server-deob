/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.npc;

import com.rs2.model.npc.Npc;
import com.rs2.model.task.TickTask;

final class NpcSequenceAdvanceTask
extends TickTask {
    private /* synthetic */ Npc npc;

    NpcSequenceAdvanceTask(Npc npc, int n) {
        this.npc = npc;
        super(10);
    }

    @Override
    public final void execute() {
        ++this.npc.scriptedPathStage;
        if (this.npc.scriptedPathStage > 7) {
            this.npc.scriptedPathStage = 0;
            ++this.npc.scriptedSequenceLoopCount;
        }
        this.npc.queueSequenceAdvancePath(this.npc.scriptedPathStage);
        this.stop();
    }
}

