/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.GertrudesCatQuest;
import com.rs2.model.task.TickTask;

final class FluffsInteractionHintTask
extends TickTask {
    private final /* synthetic */ int a;
    private final /* synthetic */ Player b;

    FluffsInteractionHintTask(GertrudesCatQuest gertrudesCatQuest, int n, int n2, Player player) {
        this.a = n2;
        this.b = player;
        super(2);
    }

    @Override
    public final void execute() {
        if (this.a == 3) {
            this.b.getDialogueManager().showOneLineStatement("Maybe the cat is thirsty?");
        }
        if (this.a == 4) {
            this.b.getDialogueManager().showOneLineStatement("Maybe the cat is hungry?");
        }
        if (this.a == 5) {
            this.b.getDialogueManager().showTwoLineStatement("The cat seems afraid to leave.", "In the distance you can hear kittens mewing...");
        }
        this.b.n(false);
        this.stop();
    }
}

