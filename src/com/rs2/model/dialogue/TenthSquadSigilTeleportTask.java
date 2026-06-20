/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.dialogue;

import com.rs2.model.Position;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class TenthSquadSigilTeleportTask
extends TickTask {
    private final /* synthetic */ Player a;

    TenthSquadSigilTeleportTask(int n, Player player) {
        this.a = player;
        super(5);
    }

    @Override
    public final void execute() {
        this.a.n(false);
        Position position = new Position(2980, 3045, 1);
        position.getPlane();
        int n = position.getPlane() + 4 + (this.a.getIndex() << 2);
        this.a.moveTo(new Position(2702, 9173, n));
        Player player = this.a;
        player.packetSender.closeInterfaces();
        this.a.p(n);
        DialogueManager.continueDialogue(this.a, 1412, 1, 0);
        this.stop();
    }
}

