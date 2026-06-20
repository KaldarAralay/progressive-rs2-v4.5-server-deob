/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.cutscene.restlessghost;

import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.cutscene.Cutscene;
import com.rs2.model.cutscene.CutsceneStep;
import com.rs2.model.cutscene.restlessghost.RestlessGhostCutscene;
import com.rs2.model.player.Player;

final class RestlessGhostReleaseStep
extends CutsceneStep {
    private /* synthetic */ RestlessGhostCutscene a;

    RestlessGhostReleaseStep(RestlessGhostCutscene restlessGhostCutscene, Cutscene cutscene, int n) {
        this.a = restlessGhostCutscene;
        super(cutscene, 2);
    }

    @Override
    public final void a() {
        this.a.c.setActive(false);
        World.b(this.a.c);
        Player player = this.a.b;
        player.packetSender.sendCameraLookAt(2659, 3195, 965, 0, 100);
        player = this.a.b;
        player.packetSender.sendCameraPosition(3660, 2500, 2000, 0, 100);
        player = this.a.b;
        player.packetSender.sendCameraLookAt(2559, 3195, 1000, 0, 15);
        player = this.a.b;
        player.packetSender.sendCameraPosition(3560, 2400, 1100, 0, 15);
        RestlessGhostCutscene.a(new Position(3244, 3193, 0), new Position(3253, 3179, 0), 605);
    }
}

