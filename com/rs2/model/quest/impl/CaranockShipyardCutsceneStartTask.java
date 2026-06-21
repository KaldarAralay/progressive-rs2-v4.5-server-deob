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

final class CaranockShipyardCutsceneStartTask
extends TickTask {
    private final /* synthetic */ Player a;

    CaranockShipyardCutsceneStartTask(MonkeyMadnessQuest monkeyMadnessQuest, int n, Player player) {
        this.a = player;
        super(5);
    }

    @Override
    public final void execute() {
        Position position = new Position(2980, 3045, 0);
        position.getPlane();
        int n = position.getPlane() + 4 + (this.a.getIndex() << 2);
        this.a.cutsceneReturnPosition = new Position(2802, 2707, 0);
        Npc npc = new Npc(1427);
        Npc npc2 = new Npc(1470);
        GameplayHelper.a(npc, 2604, 4580, n, 2);
        GameplayHelper.a(npc2, 2604, 4582, n, 0);
        this.a.temporaryCutsceneNpcs.add(npc);
        this.a.temporaryCutsceneNpcs.add(npc2);
        this.a.moveTo(new Position(2596, 4581, n));
        Player player = this.a;
        player.packetSender.sendMapRegion();
        player = this.a;
        player.packetSender.sendCameraLookAt(3550, 3450, 400, 0, 100);
        player = this.a;
        player.packetSender.sendCameraPosition(16000, 3500, 0, 0, 100);
        player = this.a;
        player.packetSender.showWalkableInterface(-1);
        DialogueManager.continueDialogue(this.a, 1470, 100, 0);
        this.stop();
    }
}

