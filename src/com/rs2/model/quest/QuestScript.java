/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest;

import com.rs2.ServerSettings;
import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.model.quest.QuestHook;
import java.awt.Color;

public abstract class QuestScript
extends QuestHook {
    private int a;

    public QuestScript(int n) {
        super(n);
    }

    public String[] a(Player stringArray, int n) {
        stringArray = new String[]{"Quest not added yet!"};
        return stringArray;
    }

    public final int a() {
        return this.a;
    }

    public final void a(int n) {
        this.a = n;
    }

    public final void a(Player player) {
        player.j = true;
        boolean bl = player.isMember();
        Player player2 = player;
        Object object = player2;
        object = this;
        player2.packetSender.sendInterfaceTextColor(QuestDefinition.b(((QuestHook)object).b()).d(), Color.GREEN);
        player.setQuestState(this.b(), 1);
        object = player;
        ((Player)object).packetSender.sendGameMessage("Congratulations! Quest Complete!");
        player.getQuestManager().d();
        if (ServerSettings.membershipRequirementMode == 3 && !bl && player.isMember()) {
            player.packetSender.sendGameMessage("You have reached " + ServerSettings.membershipRequirementValue + " quest points and gained access to members content!");
        }
        if (ServerSettings.completeMissingQuestsMode == 2) {
            object = player;
            if (((Player)object).packetSender.b()) {
                player.packetSender.sendGameMessage("You have completed all quests!");
            }
        }
    }

    public final void b(Player player) {
        Object object = player;
        ((Player)object).packetSender.sendInterfacePosition(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 0, InterfaceDefinition.interfaceCount <= 12140 ? -40 : 0);
        object = player;
        object = this;
        ((Player)object).packetSender.sendInterfaceText("You have completed " + QuestDefinition.b(((QuestHook)object).b()).c() + "!", InterfaceDefinition.interfaceCount <= 12140 ? 6160 : 12144);
        object = player;
        ((Player)object).packetSender.sendMusicJingle(238, 320);
        object = player;
        ((Player)object).packetSender.sendInterfaceText("" + player.dA(), InterfaceDefinition.interfaceCount <= 12140 ? 1696 : 12147);
        if (InterfaceDefinition.interfaceCount <= 12140) {
            object = player;
            ((Player)object).packetSender.sendInterfaceText("" + this.a, 6164);
        }
    }

    public void c(Player player) {
    }

    public final void d(Player player) {
        Player player2 = player;
        Object object = player2;
        object = this;
        player2.packetSender.sendInterfaceTextColor(QuestDefinition.b(((QuestHook)object).b()).d(), Color.YELLOW);
        player.setQuestState(this.b(), 2);
    }
}

