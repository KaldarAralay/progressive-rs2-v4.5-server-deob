/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.cutscene;

import com.rs2.model.cutscene.Cutscene;
import com.rs2.model.cutscene.CutsceneStep;
import com.rs2.model.player.Player;

final class CutsceneSceneSetupStep
extends CutsceneStep {
    private /* synthetic */ Cutscene a;
    private final /* synthetic */ Player b;

    CutsceneSceneSetupStep(Cutscene cutscene, Cutscene cutscene2, int n, Player player) {
        this.a = cutscene2;
        this.b = player;
        super(cutscene, 4);
    }

    @Override
    public final void a() {
        Player player = this.b;
        player.packetSender.showInterface(18679);
        this.a.d();
    }
}

