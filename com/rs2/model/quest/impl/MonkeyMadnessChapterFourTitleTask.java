/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

final class MonkeyMadnessChapterFourTitleTask
extends TickTask {
    private /* synthetic */ MonkeyMadnessQuest a;
    private final /* synthetic */ Player b;

    MonkeyMadnessChapterFourTitleTask(MonkeyMadnessQuest monkeyMadnessQuest, int n, Player player) {
        this.a = monkeyMadnessQuest;
        this.b = player;
        super(5);
    }

    @Override
    public final void execute() {
        Player player;
        this.b.setActionLocked(false);
        Player player2 = this.b;
        player2.packetSender.resetCamera();
        this.b.moveTo(this.b.z);
        this.b.clearTemporaryCutsceneNpcs();
        this.b.z = null;
        player2 = player = this.b;
        player.packetSender.sendInterfaceText("", 3026);
        player2 = player;
        player2.packetSender.sendInterfaceText("@cya@Monkey Madness: Chapter 4", 3027);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 3028);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 3029);
        player2 = player;
        player2.packetSender.sendInterfaceText("@cya@The Final Battle", 3030);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 3031);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 3032);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 3033);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 3034);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 3035);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 3036);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 3037);
        player2 = player;
        player2.packetSender.showInterface(3023);
        player.pendingGameMode = 0;
        this.stop();
    }
}

