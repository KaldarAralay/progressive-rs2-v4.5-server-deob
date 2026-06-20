/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.cutscene;

import com.rs2.model.Entity;
import com.rs2.model.World;
import com.rs2.model.cutscene.CutsceneDialogueStartStep;
import com.rs2.model.cutscene.CutsceneEndTask;
import com.rs2.model.cutscene.CutsceneSceneSetupStep;
import com.rs2.model.cutscene.CutsceneStep;
import com.rs2.model.cutscene.CutsceneStepTask;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestNpcIds;
import com.rs2.model.task.TickTask;
import com.rs2.net.packet.PacketSender;
import java.util.ArrayList;

public class Cutscene {
    private ArrayList a = new ArrayList();
    private ArrayList b = new ArrayList();
    private Player c;

    public Cutscene(Player player, ArrayList object) {
        Object object2;
        this.c = player;
        if (object != null) {
            object2 = ((ArrayList)object).iterator();
            while (object2.hasNext()) {
                Object object3 = object = (Npc)object2.next();
                object = this;
                ((Cutscene)object).b.add(object3);
            }
        }
        object = new CutsceneSceneSetupStep(this, this, 4, player);
        object2 = new CutsceneDialogueStartStep(this, this, 1, player);
        this.a((CutsceneStep)object);
        this.a((CutsceneStep)object2);
        this.b();
    }

    public final void a(CutsceneStep cutsceneStep) {
        this.a.add(cutsceneStep);
    }

    public final Npc a(int n) {
        return (Npc)this.b.get(0);
    }

    public final Player a() {
        return this.c;
    }

    public void b() {
    }

    public void c() {
    }

    public void d() {
    }

    public final void e() {
        this.c.ev = false;
        this.c.getMovementLockTimer().setDelayTicks(0);
        Player player = this.c;
        player.packetSender.resetCamera();
        this.c.getDialogueManager().finishDialogue();
        player = this.c;
        player.packetSender.sendMinimapState(0);
        player = this.c;
        player.packetSender.refreshSidebarInterfaces();
    }

    public final void f() {
        Cutscene cutscene = this;
        this.c.ev = true;
        Object object = cutscene.c;
        ((Player)object).packetSender.sendMinimapState(2);
        object = cutscene.c;
        ((Player)object).packetSender.showInterface(8677);
        object = cutscene.c;
        Object object2 = new int[]{QuestNpcIds.a[0], QuestNpcIds.b[0], QuestNpcIds.c[0], QuestNpcIds.d[0], QuestNpcIds.e[0], QuestNpcIds.f[0], QuestNpcIds.g[0], QuestNpcIds.k[0], QuestNpcIds.l[0]};
        object = ((Player)object).packetSender;
        int n = 0;
        while (n < 9) {
            ((PacketSender)object).setSidebarInterface(object2[n], -1);
            ++n;
        }
        cutscene.c.getMovementLockTimer().setDelayTicks(cutscene.g());
        if (cutscene.b != null) {
            object2 = cutscene.b.iterator();
            while (object2.hasNext()) {
                object = (Npc)object2.next();
                ((Entity)object).getMovementLockTimer().setDelayTicks(cutscene.g());
            }
        }
        int n2 = 0;
        object2 = this.a.iterator();
        while (object2.hasNext()) {
            object = (CutsceneStep)object2.next();
            n2 += ((CutsceneStep)object).b();
            object = new CutsceneStepTask(this, n2, (CutsceneStep)object);
            World.getTaskScheduler().schedule((TickTask)object);
        }
        object = new CutsceneEndTask(this, this.h());
        World.getTaskScheduler().schedule((TickTask)object);
    }

    private int g() {
        int n = 0;
        for (CutsceneStep cutsceneStep : this.a) {
            n += cutsceneStep.b();
        }
        return n * 600;
    }

    private int h() {
        int n = 0;
        for (CutsceneStep cutsceneStep : this.a) {
            n += cutsceneStep.b();
        }
        return n;
    }
}

