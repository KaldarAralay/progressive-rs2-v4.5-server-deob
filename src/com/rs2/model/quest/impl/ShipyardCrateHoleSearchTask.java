/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

final class ShipyardCrateHoleSearchTask
extends TickTask {
    private final /* synthetic */ Player a;

    ShipyardCrateHoleSearchTask(MonkeyMadnessQuest monkeyMadnessQuest, int n, Player player) {
        this.a = player;
        super(2);
    }

    @Override
    public final void execute() {
        this.a.n(false);
        this.a.getDialogueManager().showTwoLineStatement("You find a hole in the floor under the crate! All you can see is the", "faint glimmer of light from extremely far below.");
        this.stop();
    }
}

