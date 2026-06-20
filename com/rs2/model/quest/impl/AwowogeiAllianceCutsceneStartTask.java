/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

final class AwowogeiAllianceCutsceneStartTask
extends TickTask {
    private final /* synthetic */ Player a;

    AwowogeiAllianceCutsceneStartTask(MonkeyMadnessQuest monkeyMadnessQuest, int n, Player player) {
        this.a = player;
        super(5);
    }

    @Override
    public final void execute() {
        Position position = new Position(2980, 3045, 1);
        position.getPlane();
        int n = position.getPlane() + 4 + (this.a.getIndex() << 2);
        this.a.z = new Position(2807, 2761, 0);
        Npc npc = new Npc(1448);
        Npc npc2 = new Npc(1428);
        Npc npc3 = new Npc(1409);
        GameplayHelper.a(npc, 2678, 4579, n, 5);
        GameplayHelper.a(npc2, 2677, 4580, n, 0);
        GameplayHelper.a(npc3, 2677, 4578, n, 2);
        this.a.A.add(npc);
        this.a.A.add(npc2);
        this.a.A.add(npc3);
        this.a.moveTo(new Position(2690, 4578, n));
        Player player = this.a;
        player.packetSender.sendMapRegion();
        player = this.a;
        player.packetSender.sendCameraLookAt(2100, 3300, 500, 0, 100);
        player = this.a;
        player.packetSender.sendCameraPosition(8000, 3500, 0, 0, 100);
        player = this.a;
        player.packetSender.showWalkableInterface(-1);
        DialogueManager.continueDialogue(this.a, 1428, 100, 0);
        this.stop();
    }
}

