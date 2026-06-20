/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.GrandTreeQuest;
import com.rs2.model.task.TickTask;

final class GrandTreeGloughCaveEncounterTask
extends TickTask {
    private final /* synthetic */ Player a;

    GrandTreeGloughCaveEncounterTask(GrandTreeQuest grandTreeQuest, int n, Player player) {
        this.a = player;
        super(2);
    }

    @Override
    public final void execute() {
        Entity entity = this.a;
        ((Player)entity).packetSender.sendGameMessage("You can hear footsteps approaching.");
        DialogueManager.continueDialogue(this.a, 671, 100, 0);
        entity = new Npc(671);
        GameplayHelper.b(this.a, (Npc)entity, 2484, 9864, 0, -1, false, false);
        ((Npc)entity).queueScriptedPath(new Position[]{new Position(2490, 9864, 0)});
        this.a.setInteractionTarget(entity);
        this.a.n(false);
        this.stop();
    }
}

