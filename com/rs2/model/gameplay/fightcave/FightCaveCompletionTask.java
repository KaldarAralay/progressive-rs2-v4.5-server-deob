/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.fightcave;

import com.rs2.model.gameplay.fightcave.FightCaveController;
import com.rs2.model.task.TickTask;

final class FightCaveCompletionTask
extends TickTask {
    private /* synthetic */ FightCaveController a;

    FightCaveCompletionTask(FightCaveController fightCaveController, int n) {
        this.a = fightCaveController;
        super(3);
    }

    @Override
    public final void execute() {
        ++FightCaveController.a((FightCaveController)this.a).dO;
        this.a.c();
        FightCaveController.a(this.a).getDialogueManager().setDialogueNpcId(2617);
        FightCaveController.a(this.a).getDialogueManager().showNpcTwoLineDialogue("You even defeated TzTok-Jad, I am most impressed!", "Please accept this gift as a reward.", 591);
        FightCaveController.a(this.a).getDialogueManager().finishDialogue();
        this.stop();
    }
}

