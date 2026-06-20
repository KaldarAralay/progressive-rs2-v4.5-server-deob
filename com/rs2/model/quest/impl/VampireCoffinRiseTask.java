/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.npc.Npc;
import com.rs2.model.npc.NpcDefinition;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.VampireAttackStartTask;
import com.rs2.model.quest.impl.VampireSlayerQuest;
import com.rs2.model.task.TickTask;

final class VampireCoffinRiseTask
extends TickTask {
    private TickTask a;
    private final /* synthetic */ Npc b;
    private final /* synthetic */ Player c;

    VampireCoffinRiseTask(VampireSlayerQuest vampireSlayerQuest, int n, Npc npc, Player player) {
        this.b = npc;
        this.c = player;
        super(3);
        this.a = new VampireAttackStartTask(this, 5, npc, player);
    }

    @Override
    public final void execute() {
        if (NpcDefinition.isDefined(3154)) {
            this.b.transformToNpcId(3154, 5);
        }
        GameplayHelper.c(this.c, this.b, 3077, 9775, 0, -1, false, false);
        this.c.setActionLocked(false);
        this.b.getUpdateState().setFacePositionValue(new Position(3077, 9770));
        this.b.getUpdateState().setAnimation(3114);
        World.getTaskScheduler().schedule(this.a);
        this.stop();
    }
}

