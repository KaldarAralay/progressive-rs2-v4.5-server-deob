/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

final class DaeroTrainingTimeSkipTask
extends TickTask {
    private /* synthetic */ MonkeyMadnessQuest a;
    private final /* synthetic */ Player b;

    DaeroTrainingTimeSkipTask(MonkeyMadnessQuest monkeyMadnessQuest, int n, Player player) {
        this.a = monkeyMadnessQuest;
        this.b = player;
        super(5);
    }

    @Override
    public final void execute() {
        Player player;
        Player player2 = player = this.b;
        player.packetSender.sendInterfaceText("", 3026);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 3027);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 3028);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 3029);
        player2 = player;
        player2.packetSender.sendInterfaceText("", 3030);
        player2 = player;
        player2.packetSender.sendInterfaceText("@cya@Several hours later...", 3031);
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
        this.stop();
    }
}

