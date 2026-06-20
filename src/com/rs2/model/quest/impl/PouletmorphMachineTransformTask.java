/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.c.ProjectileDefinition;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.ErnestHumanDialogueTask;
import com.rs2.model.quest.impl.ErnestTheChickenQuest;
import com.rs2.model.quest.impl.PouletmorphMachineStartTask;
import com.rs2.model.skill.woodcutting.WoodcuttingHandler;
import com.rs2.model.task.TickTask;

final class PouletmorphMachineTransformTask
extends TickTask {
    private /* synthetic */ PouletmorphMachineStartTask a;
    private final /* synthetic */ Player b;

    PouletmorphMachineTransformTask(PouletmorphMachineStartTask pouletmorphMachineStartTask, int n, Player player) {
        this.a = pouletmorphMachineStartTask;
        this.b = player;
        super(1);
    }

    @Override
    public final void execute() {
        Entity entity = this.b;
        ((Player)entity).packetSender.sendGameMessage("The machine hums and shakes.");
        entity = Npc.findByDefinitionId(288);
        Object object = this.a;
        int n = 605;
        Position position = entity.getPosition();
        Position position2 = new Position(3111, 3366, 2);
        Object object2 = this.b;
        object = ((PouletmorphMachineStartTask)object).a;
        int n2 = ErnestTheChickenQuest.a(position2, position);
        new WoodcuttingHandler(position2, 0, position, 0, new ProjectileDefinition(605, ProjectileTiming.d)).a();
        object2 = new ErnestHumanDialogueTask((ErnestTheChickenQuest)object, n2, (Player)object2);
        World.getTaskScheduler().schedule((TickTask)object2);
        this.stop();
    }
}

