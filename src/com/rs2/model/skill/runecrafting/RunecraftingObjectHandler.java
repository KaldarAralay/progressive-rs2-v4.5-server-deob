/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.runecrafting;

import com.rs2.ServerSettings;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemService;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.model.skill.GatheringToolDefinition;
import com.rs2.model.skill.ItemCombinationHandler;
import com.rs2.model.skill.runecrafting.MysteriousRuinsTeleportTask;
import com.rs2.model.skill.runecrafting.RuneDefinition;
import com.rs2.model.skill.runecrafting.RunecraftingAltarDefinition;
import com.rs2.model.skill.runecrafting.RunecraftingHandler;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.net.packet.PacketSender;

public class RunecraftingObjectHandler {
    private Player player;
    private int[] toolInteractionNpcIds = new int[]{519, 594};

    public static boolean handleAltarOrAbyssObject(Player player, int n) {
        switch (n) {
            case 2489: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.SOUL_RUNE);
                return true;
            }
            case 2490: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.BLOOD_RUNE);
                return true;
            }
            case 2482: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.FIRE_RUNE);
                return true;
            }
            case 2480: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.WATER_RUNE);
                return true;
            }
            case 2478: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.AIR_RUNE);
                return true;
            }
            case 2481: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.EARTH_RUNE);
                return true;
            }
            case 2479: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.MIND_RUNE);
                return true;
            }
            case 2483: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.BODY_RUNE);
                return true;
            }
            case 2488: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.DEATH_RUNE);
                return true;
            }
            case 2486: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.NATURE_RUNE);
                return true;
            }
            case 2487: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.CHAOS_RUNE);
                return true;
            }
            case 2485: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.LAW_RUNE);
                return true;
            }
            case 2484: {
                RunecraftingHandler.craftRunesAtAltar(player, RuneDefinition.COSMIC_RUNE);
                return true;
            }
            case 7141: {
                player.packetSender.sendGameMessage("This has been disabled temporarily.");
                return true;
            }
            case 7138: {
                player.packetSender.sendGameMessage("This has been disabled temporarily.");
                return true;
            }
            case 7133: {
                player.getTeleportManager().a(2400, 4835, 0);
                return true;
            }
            case 7132: {
                n = player.getQuestState(58) == 1 ? 1 : 0;
                if (n == 0) {
                    Object object = QuestDefinition.b(58);
                    object = ((QuestDefinition)object).c();
                    player.getDialogueManager().showOneLineStatement("You need to complete " + (String)object + " first.");
                    player.getDialogueManager().finishDialogue();
                } else {
                    player.getTeleportManager().a(2142, 4813, 0);
                }
                return true;
            }
            case 7129: {
                player.getTeleportManager().a(2574, 4849, 0);
                return true;
            }
            case 7130: {
                player.getTeleportManager().a(2655, 4830, 0);
                return true;
            }
            case 7131: {
                player.getTeleportManager().a(2523, 4826, 0);
                return true;
            }
            case 7140: {
                player.getTeleportManager().a(2793, 4828, 0);
                return true;
            }
            case 7139: {
                player.getTeleportManager().a(2841, 4829, 0);
                return true;
            }
            case 7137: {
                player.getTeleportManager().a(2726, 4832, 0);
                return true;
            }
            case 7136: {
                if (player.getPlayerRights() < 2) {
                    player.packetSender.sendGameMessage("This has been disabled temporarily.");
                } else {
                    player.getTeleportManager().a(2208, 4830, 0);
                }
                return true;
            }
            case 7135: {
                if (player.eB()) {
                    player.getDialogueManager().showOneLineStatement("You cannot bring combat items to Entrana!");
                    player.getDialogueManager().finishDialogue();
                } else {
                    player.getTeleportManager().a(2464, 4818, 0);
                }
                return true;
            }
            case 7134: {
                player.getTeleportManager().a(2281, 4837, 0);
                return true;
            }
        }
        return false;
    }

    public static boolean handleRuinsOrPortalObject(Player player, int n) {
        Object object;
        RunecraftingAltarDefinition runecraftingAltarDefinition;
        Object object2;
        block13: {
            RunecraftingAltarDefinition runecraftingAltarDefinition2;
            RunecraftingAltarDefinition runecraftingAltarDefinition3;
            RunecraftingAltarDefinition runecraftingAltarDefinition4;
            int n2;
            int n3;
            RunecraftingAltarDefinition[] runecraftingAltarDefinitionArray;
            int n4;
            block12: {
                n4 = n;
                runecraftingAltarDefinitionArray = RunecraftingAltarDefinition.values();
                n3 = runecraftingAltarDefinitionArray.length;
                n2 = 0;
                while (n2 < n3) {
                    object2 = runecraftingAltarDefinition4 = runecraftingAltarDefinitionArray[n2];
                    if (n4 == object2.ruinsObjectId) {
                        runecraftingAltarDefinition3 = runecraftingAltarDefinition4;
                        break block12;
                    }
                    ++n2;
                }
                runecraftingAltarDefinition3 = null;
            }
            runecraftingAltarDefinition = runecraftingAltarDefinition3;
            n4 = n;
            runecraftingAltarDefinitionArray = RunecraftingAltarDefinition.values();
            n3 = runecraftingAltarDefinitionArray.length;
            n2 = 0;
            while (n2 < n3) {
                object = runecraftingAltarDefinition4 = runecraftingAltarDefinitionArray[n2];
                if (n4 == object.portalObjectId) {
                    runecraftingAltarDefinition2 = runecraftingAltarDefinition4;
                    break block13;
                }
                ++n2;
            }
            runecraftingAltarDefinition2 = object = null;
        }
        if (runecraftingAltarDefinition == null && object == null) {
            return false;
        }
        if (!ServerSettings.runecraftingEnabled) {
            object2 = player;
            ((Player)object2).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (runecraftingAltarDefinition != null) {
            object2 = runecraftingAltarDefinition;
            if (object2.membersOnly && !player.isMember()) {
                player.packetSender.sendGameMessage("You need a members account to access members content.");
                return true;
            }
            object2 = runecraftingAltarDefinition;
            if (object2.membersOnly && ServerSettings.freeToPlayWorld) {
                player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                return true;
            }
        }
        if (player.getQuestState(14) != 1) {
            object = QuestDefinition.b(14);
            object = ((QuestDefinition)object).c();
            object2 = player;
            ((Player)object2).packetSender.sendGameMessage("You need to complete " + (String)object + " to do this.");
            return true;
        }
        if (runecraftingAltarDefinition != null) {
            object2 = runecraftingAltarDefinition;
            player.moveTo(object2.altarPosition);
            object2 = player;
            ((Player)object2).packetSender.sendGameMessage("You feel a powerful force take hold of you...");
            return true;
        }
        if (object != null) {
            object2 = object;
            player.moveTo(object2.ruinsPosition);
            object2 = player;
            ((Player)object2).packetSender.sendGameMessage("You step through the portal...");
            return true;
        }
        return false;
    }

    public static boolean handleTalismanOnMysteriousRuins(Player player, int n, int n2) {
        Object object;
        RunecraftingAltarDefinition runecraftingAltarDefinition;
        Object object2;
        block8: {
            RunecraftingAltarDefinition[] runecraftingAltarDefinitionArray = RunecraftingAltarDefinition.values();
            int n3 = runecraftingAltarDefinitionArray.length;
            int n4 = 0;
            while (n4 < n3) {
                RunecraftingAltarDefinition runecraftingAltarDefinition2;
                object2 = runecraftingAltarDefinition2 = runecraftingAltarDefinitionArray[n4];
                if (n == object2.talismanItemId) {
                    object2 = runecraftingAltarDefinition2;
                    if (n2 == object2.ruinsObjectId) {
                        runecraftingAltarDefinition = runecraftingAltarDefinition2;
                        break block8;
                    }
                }
                ++n4;
            }
            runecraftingAltarDefinition = object = null;
        }
        if (runecraftingAltarDefinition == null) {
            return false;
        }
        if (!ServerSettings.runecraftingEnabled) {
            object2 = player;
            ((Player)object2).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        object2 = object;
        if (object2.membersOnly && !player.isMember()) {
            player.packetSender.sendGameMessage("You need a members account to access members content.");
            return true;
        }
        object2 = object;
        if (object2.membersOnly && ServerSettings.freeToPlayWorld) {
            player.packetSender.sendGameMessage("You need to be in members world to access members content.");
            return true;
        }
        if (player.getQuestState(14) != 1) {
            object = QuestDefinition.b(14);
            object = ((QuestDefinition)object).c();
            object2 = player;
            ((Player)object2).packetSender.sendGameMessage("You need to complete " + (String)object + " to do this.");
            return true;
        }
        object2 = player;
        PacketSender packetSender = ((Player)object2).packetSender;
        StringBuilder stringBuilder = new StringBuilder("You hold the ");
        ItemService.getInstance();
        object2 = object;
        packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(object2.talismanItemId)).append(" towards the mysterious ruins.").toString());
        player.getUpdateState().setAnimation(827);
        player.n(true);
        CycleEventHandler.getInstance().schedule(player, new MysteriousRuinsTeleportTask(player, (RunecraftingAltarDefinition)((Object)object)), 2);
        return true;
    }

    public RunecraftingObjectHandler(Player player) {
        this.player = player;
    }

    public boolean handleToolOnNpc(int n, int n2) {
        int n3 = -1;
        int[] nArray = this.toolInteractionNpcIds;
        int n4 = this.toolInteractionNpcIds.length;
        int n5 = 0;
        while (n5 < n4) {
            int n6 = nArray[n5];
            if (n == n6) {
                n3 = n6;
                break;
            }
            ++n5;
        }
        GatheringToolDefinition gatheringToolDefinition = ItemCombinationHandler.c(n2);
        if (n3 != -1 && gatheringToolDefinition != null) {
            this.player.N = n2;
            this.player.O = n3;
            DialogueManager.startDialogue(this.player, 10088);
            return true;
        }
        return false;
    }
}

