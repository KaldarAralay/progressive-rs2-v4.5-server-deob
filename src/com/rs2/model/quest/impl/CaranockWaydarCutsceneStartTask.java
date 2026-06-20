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

final class CaranockWaydarCutsceneStartTask
extends TickTask {
    private final /* synthetic */ Player a;

    CaranockWaydarCutsceneStartTask(MonkeyMadnessQuest monkeyMadnessQuest, int n, Player player) {
        this.a = player;
        super(5);
    }

    @Override
    public final void execute() {
        Position position = new Position(2980, 3045, 0);
        position.getPlane();
        int n = position.getPlane() + 4 + (this.a.getIndex() << 2);
        this.a.z = new Position(2805, 9143, 0);
        Npc npc = new Npc(1427);
        Npc npc2 = new Npc(1409);
        GameplayHelper.a(npc, 2672, 4559, n, 5);
        GameplayHelper.a(npc2, 2670, 4559, n, 4);
        this.a.A.add(npc);
        this.a.A.add(npc2);
        this.a.moveTo(new Position(2670, 4573, n));
        Player player = this.a;
        player.packetSender.sendMapRegion();
        player = this.a;
        player.packetSender.sendCameraLookAt(3600, 2900, 400, 0, 100);
        player = this.a;
        player.packetSender.sendCameraPosition(3500, 1000, 0, 0, 100);
        player = this.a;
        player.packetSender.showWalkableInterface(-1);
        DialogueManager.continueDialogue(this.a, 1427, 100, 0);
        this.stop();
    }
}

