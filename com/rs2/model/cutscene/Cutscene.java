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
import com.rs2.model.quest.QuestConstants;
import com.rs2.model.task.TickTask;
import com.rs2.net.packet.PacketSender;
import java.util.ArrayList;

public class Cutscene {
    private ArrayList steps = new ArrayList();
    private ArrayList npcs = new ArrayList();
    private Player player;

    public Cutscene(Player player, ArrayList object) {
        Object object2;
        this.player = player;
        if (object != null) {
            object2 = ((ArrayList)object).iterator();
            while (object2.hasNext()) {
                Object object3 = object = (Npc)object2.next();
                object = this;
                ((Cutscene)object).npcs.add(object3);
            }
        }
        object = new CutsceneSceneSetupStep(this, this, 4, player);
        object2 = new CutsceneDialogueStartStep(this, this, 1, player);
        this.addStep((CutsceneStep)object);
        this.addStep((CutsceneStep)object2);
        this.addCustomSteps();
    }

    public final void addStep(CutsceneStep cutsceneStep) {
        this.steps.add(cutsceneStep);
    }

    public final Npc getNpc(int n) {
        return (Npc)this.npcs.get(0);
    }

    public final Player getPlayer() {
        return this.player;
    }

    public void addCustomSteps() {
    }

    public void startDialogue() {
    }

    public void setupScene() {
    }

    public final void finishCutscene() {
        this.player.cutsceneActive = false;
        this.player.getMovementLockTimer().setDelayTicks(0);
        Player player = this.player;
        player.packetSender.resetCamera();
        this.player.getDialogueManager().finishDialogue();
        player = this.player;
        player.packetSender.sendMinimapState(0);
        player = this.player;
        player.packetSender.refreshSidebarInterfaces();
    }

    public final void startCutscene() {
        Cutscene cutscene = this;
        this.player.cutsceneActive = true;
        Object object = cutscene.player;
        ((Player)object).packetSender.sendMinimapState(2);
        object = cutscene.player;
        ((Player)object).packetSender.showInterface(8677);
        object = cutscene.player;
        Object object2 = new int[]{QuestConstants.COMBAT_TAB_INTERFACE[0], QuestConstants.STATS_TAB_INTERFACE[0], QuestConstants.QUEST_TAB_INTERFACE[0], QuestConstants.INVENTORY_TAB_INTERFACE[0], QuestConstants.EQUIPMENT_TAB_INTERFACE[0], QuestConstants.PRAYER_TAB_INTERFACE[0], QuestConstants.MAGIC_TAB_INTERFACE[0], QuestConstants.OPTIONS_TAB_INTERFACE[0], QuestConstants.EMOTES_TAB_INTERFACE[0]};
        object = ((Player)object).packetSender;
        int n = 0;
        while (n < 9) {
            ((PacketSender)object).setSidebarInterface(object2[n], -1);
            ++n;
        }
        cutscene.player.getMovementLockTimer().setDelayTicks(cutscene.getMovementLockDurationMillis());
        if (cutscene.npcs != null) {
            object2 = cutscene.npcs.iterator();
            while (object2.hasNext()) {
                object = (Npc)object2.next();
                ((Entity)object).getMovementLockTimer().setDelayTicks(cutscene.getMovementLockDurationMillis());
            }
        }
        int n2 = 0;
        object2 = this.steps.iterator();
        while (object2.hasNext()) {
            object = (CutsceneStep)object2.next();
            n2 += ((CutsceneStep)object).getDelayTicks();
            object = new CutsceneStepTask(this, n2, (CutsceneStep)object);
            World.getTaskScheduler().schedule((TickTask)object);
        }
        object = new CutsceneEndTask(this, this.getTotalDelayTicks());
        World.getTaskScheduler().schedule((TickTask)object);
    }

    private int getMovementLockDurationMillis() {
        int n = 0;
        for (CutsceneStep cutsceneStep : this.steps) {
            n += cutsceneStep.getDelayTicks();
        }
        return n * 600;
    }

    private int getTotalDelayTicks() {
        int n = 0;
        for (CutsceneStep cutsceneStep : this.steps) {
            n += cutsceneStep.getDelayTicks();
        }
        return n;
    }
}

