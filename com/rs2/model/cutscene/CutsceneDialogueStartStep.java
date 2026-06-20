/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.cutscene;

import com.rs2.model.cutscene.Cutscene;
import com.rs2.model.cutscene.CutsceneStep;
import com.rs2.model.player.Player;

final class CutsceneDialogueStartStep
extends CutsceneStep {
    private /* synthetic */ Cutscene cutscene;
    private final /* synthetic */ Player player;

    CutsceneDialogueStartStep(Cutscene cutscene, Cutscene cutscene2, int n, Player player) {
        this.cutscene = cutscene2;
        this.player = player;
        super(cutscene, 1);
    }

    @Override
    public final void executeStep() {
        Player player = this.player;
        player.packetSender.closeInterfaces();
        this.cutscene.startDialogue();
    }
}

