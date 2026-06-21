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
    private int questPointReward;

    public QuestScript(int n) {
        super(n);
    }

    public String[] buildQuestJournal(Player stringArray, int n) {
        stringArray = new String[]{"Quest not added yet!"};
        return stringArray;
    }

    public final int getQuestPointReward() {
        return this.questPointReward;
    }

    public final void setQuestPointReward(int n) {
        this.questPointReward = n;
    }

    public final void markQuestComplete(Player player) {
        player.deferLevelUpInterfaces = true;
        boolean bl = player.isMember();
        Player player2 = player;
        Object object = player2;
        object = this;
        player2.packetSender.sendInterfaceTextColor(QuestDefinition.forId(((QuestHook)object).getQuestId()).getJournalButtonId(), Color.GREEN);
        player.setQuestState(this.getQuestId(), 1);
        object = player;
        ((Player)object).packetSender.sendGameMessage("Congratulations! Quest Complete!");
        player.getQuestManager().refreshQuestPointText();
        if (ServerSettings.membershipRequirementMode == 3 && !bl && player.isMember()) {
            player.packetSender.sendGameMessage("You have reached " + ServerSettings.membershipRequirementValue + " quest points and gained access to members content!");
        }
        if (ServerSettings.completeMissingQuestsMode == 2) {
            object = player;
            if (((Player)object).packetSender.updateMissingQuestCompletionStates()) {
                player.packetSender.sendGameMessage("You have completed all quests!");
            }
        }
    }

    public final void showQuestCompleteInterface(Player player) {
        Object object = player;
        ((Player)object).packetSender.sendInterfacePosition(InterfaceDefinition.interfaceCount <= 12140 ? 6161 : 12145, 0, InterfaceDefinition.interfaceCount <= 12140 ? -40 : 0);
        object = player;
        object = this;
        ((Player)object).packetSender.sendInterfaceText("You have completed " + QuestDefinition.forId(((QuestHook)object).getQuestId()).getName() + "!", InterfaceDefinition.interfaceCount <= 12140 ? 6160 : 12144);
        object = player;
        ((Player)object).packetSender.sendMusicJingle(238, 320);
        object = player;
        ((Player)object).packetSender.sendInterfaceText("" + player.getQuestPoints(), InterfaceDefinition.interfaceCount <= 12140 ? 1696 : 12147);
        if (InterfaceDefinition.interfaceCount <= 12140) {
            object = player;
            ((Player)object).packetSender.sendInterfaceText("" + this.questPointReward, 6164);
        }
    }

    public void awardCompletionRewards(Player player) {
    }

    public final void startQuest(Player player) {
        Player player2 = player;
        Object object = player2;
        object = this;
        player2.packetSender.sendInterfaceTextColor(QuestDefinition.forId(((QuestHook)object).getQuestId()).getJournalButtonId(), Color.YELLOW);
        player.setQuestState(this.getQuestId(), 2);
    }
}

