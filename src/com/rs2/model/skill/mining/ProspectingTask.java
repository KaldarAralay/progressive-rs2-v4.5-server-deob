/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.mining;

import com.rs2.model.player.Player;
import com.rs2.model.skill.mining.MiningManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class ProspectingTask
extends CycleEvent {
    private /* synthetic */ MiningManager a;
    private final /* synthetic */ int b;
    private final /* synthetic */ String c;

    ProspectingTask(MiningManager miningManager, int n, String string) {
        this.a = miningManager;
        this.b = n;
        this.c = string;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        Player player = MiningManager.a(this.a);
        player.packetSender.sendGameMessage("This rock contains " + (this.b == 2111 ? "gems" : (this.b == 2491 ? "unbound Rune Stone essence" : String.valueOf(this.c) + ".")));
        if (MiningManager.a(this.a).getQuestState(0) != 1) {
            if (MiningManager.a(this.a).getQuestState(0) == 30 && this.c.contains("tin")) {
                MiningManager.a(this.a).ea();
            } else if (MiningManager.a(this.a).getQuestState(0) == 31 && this.c.contains("copper")) {
                MiningManager.a(this.a).ea();
            }
            MiningManager.a(this.a).getDialogueManager().showOneLineStatement("This rock contains " + (this.b == 2111 ? "gems" : (this.b == 2491 ? "unbound Rune Stone essence" : String.valueOf(this.c) + ".")));
            MiningManager.a(this.a).getQuestManager().refreshQuestJournal();
            MiningManager.a(this.a).setInteractionTargetId(0);
        }
        player = MiningManager.a(this.a);
        player.packetSender.sendSoundEffect(431, 1, 0);
        MiningManager.a(this.a).n(false);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

