/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.Entity;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.WitchsHouseGardenEjectEvent;
import com.rs2.model.quest.impl.WitchsHouseQuest;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

final class WitchsHouseGardenTrespassTask
extends TickTask {
    private /* synthetic */ WitchsHouseQuest a;
    private final /* synthetic */ Player b;

    WitchsHouseGardenTrespassTask(WitchsHouseQuest witchsHouseQuest, int n, Player player) {
        this.a = witchsHouseQuest;
        this.b = player;
        super(1);
    }

    @Override
    public final void execute() {
        if (!this.b.isRegistered()) {
            this.stop();
            return;
        }
        if (this.a.gardenTrespassArea.contains(this.b.getPosition())) {
            Object object = Npc.findByDefinitionId(896);
            if (GameUtil.isNpcWaypointFacingPlayer(this.b, (Npc)object)) {
                ((Entity)object).getUpdateState().setForcedText("Get out of my property!");
                Player player = this.b;
                object = this.a;
                player.getUpdateState().setGraphicHeight100(110);
                CycleEventHandler.getInstance().schedule(player, new WitchsHouseGardenEjectEvent((WitchsHouseQuest)object, player), 1);
                return;
            }
        } else {
            this.b.eq = -1;
            this.stop();
        }
    }
}

