/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.fightcave;

import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.gameplay.fightcave.FightCaveCompletionTask;
import com.rs2.model.gameplay.fightcave.FightCaveWaveSpawner;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;

public final class FightCaveController {
    private Player a;
    private Position b = new Position(2438, 5168, 0);

    public FightCaveController(Player player) {
        this.a = player;
    }

    public final void a() {
        this.a.eq();
        this.c();
    }

    public final void b() {
        if (this.a.isInFightCave()) {
            this.c();
        }
    }

    public final void c() {
        this.a.moveTo(this.b);
        Object object2 = this;
        boolean bl = ((FightCaveController)object2).a.dO == 63;
        if (bl) {
            --((FightCaveController)object2).a.dO;
        }
        double d = ((FightCaveController)object2).a.dO + 1;
        int n = (int)(2.0 * (d / 2.0 * (d + 1.0)));
        if (bl) {
            ((FightCaveController)object2).a.getInventoryManager().b(new ItemStack(6570, 1));
            n += 4000;
        }
        if (n > 0) {
            ((FightCaveController)object2).a.getInventoryManager().b(new ItemStack(6529, n));
        }
        for (Object object2 : this.a.aT()) {
            ((Npc)object2).setActive(false);
            World.b((Npc)object2);
        }
    }

    public final void d() {
        ++this.a.dO;
        FightCaveWaveSpawner.a(this.a, this.a.dO);
    }

    public final void e() {
        this.a.getUpdateState().setAnimation(862);
        FightCaveCompletionTask fightCaveCompletionTask = new FightCaveCompletionTask(this, 3);
        World.getTaskScheduler().schedule(fightCaveCompletionTask);
    }

    public final void f() {
        this.a.dO = ServerSettings.fightCaveStartWaveZeroBased;
        this.a.ez = GameUtil.h(15);
        this.a.b(new Position(2411, 5114, 0), true);
        this.a.getDialogueManager().setDialogueNpcId(2617);
        this.a.getDialogueManager().showNpcTwoLineDialogue("You're on your own now JalYt, prepare to fight for", "your life!", 591);
        this.a.getDialogueManager().finishDialogue();
        FightCaveWaveSpawner.a(this.a, this.a.dO);
    }

    static /* synthetic */ Player a(FightCaveController fightCaveController) {
        return fightCaveController.a;
    }
}

