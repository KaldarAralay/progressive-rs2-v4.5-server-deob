/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.cutscene.restlessghost;

import com.rs2.model.Position;
import com.rs2.model.c.ProjectileDefinition;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.cutscene.Cutscene;
import com.rs2.model.cutscene.restlessghost.RestlessGhostAnimationStep;
import com.rs2.model.cutscene.restlessghost.RestlessGhostCompletionStep;
import com.rs2.model.cutscene.restlessghost.RestlessGhostReleaseStep;
import com.rs2.model.cutscene.restlessghost.RestlessGhostTextStep;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.model.quest.QuestScript;
import com.rs2.model.skill.woodcutting.WoodcuttingHandler;
import java.util.ArrayList;

public final class RestlessGhostCutscene
extends Cutscene {
    static QuestScript a = QuestDefinition.a(12);
    Player b = this.a();
    Npc c = this.a(0);

    public RestlessGhostCutscene(Player player, ArrayList arrayList) {
        super(player, arrayList);
    }

    @Override
    public final void b() {
        RestlessGhostTextStep restlessGhostTextStep = new RestlessGhostTextStep(this, this, 3);
        RestlessGhostAnimationStep restlessGhostAnimationStep = new RestlessGhostAnimationStep(this, this, 3);
        RestlessGhostReleaseStep restlessGhostReleaseStep = new RestlessGhostReleaseStep(this, this, 2);
        RestlessGhostCompletionStep restlessGhostCompletionStep = new RestlessGhostCompletionStep(this, this, 5);
        this.a(restlessGhostTextStep);
        this.a(restlessGhostAnimationStep);
        this.a(restlessGhostReleaseStep);
        this.a(restlessGhostCompletionStep);
    }

    @Override
    public final void d() {
        this.c.moveTo(new Position(3248, 3193));
        this.b.moveTo(new Position(3248, 3192, 0));
        Player player = this.b;
        player.packetSender.sendMapRegion();
        player = this.b;
        player.packetSender.sendCameraLookAt(3340, 3149, 330, 0, 100);
        player = this.b;
        player.packetSender.sendCameraPosition(3325, 3149, 298, 0, 100);
        this.b.getUpdateState().setFaceEntity(this.c.getEncodedIndex());
        this.c.getUpdateState().setFacePositionValue(this.b.getPosition());
    }

    @Override
    public final void c() {
        this.b.getDialogueManager().setDialogueNpcId(this.c.getNpcId());
        this.b.setQuestState(a.b(), 6);
        this.b.getDialogueManager().b("", "Release! Thank you stranger..", "", 591);
        this.c.getUpdateState().setForcedText("Release! Thank you");
    }

    public static void a(Position position, Position position2, int n) {
        new WoodcuttingHandler(position, 0, position2, 0, new ProjectileDefinition(605, ProjectileTiming.d)).a();
    }
}

