/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.runecrafting;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.skill.runecrafting.RuneDefinition;
import com.rs2.model.skill.runecrafting.RunecraftingAltarDefinition;
import com.rs2.util.GameUtil;

public final class RunecraftingHandler {
    public static int PURE_ESSENCE_ITEM_ID = 7936;
    private static int[] SCRYING_ORB_NPC_IDS = new int[]{171, 300, 462, 553, 844};

    public static void craftRunesAtAltar(Player player, RuneDefinition object) {
        if (!ServerSettings.runecraftingEnabled) {
            object = player;
            ((Player)object).packetSender.sendGameMessage("This skill is currently disabled.");
            return;
        }
        if (player.getQuestState(14) != 1) {
            QuestDefinition questDefinition = QuestDefinition.b(14);
            String string = questDefinition.c();
            object = player;
            ((Player)object).packetSender.sendGameMessage("You need to complete " + string + " to do this.");
            return;
        }
        if (!SkillActionHelper.checkSkillRequirement(player, 20, ((RuneDefinition)((Object)object)).getRequiredLevel(), "craft this rune")) {
            return;
        }
        int n = player.getInventoryManager().getItemAmount(1436);
        int n2 = player.getInventoryManager().getItemAmount(PURE_ESSENCE_ITEM_ID);
        double d = ((RuneDefinition)((Object)object)).getMultipleRunesLevelInterval() < 0 ? 0 : player.getSkillManager().getBaseLevel(20) / ((RuneDefinition)((Object)object)).getMultipleRunesLevelInterval();
        int n3 = (int)Math.floor(d) + 1;
        if (((RuneDefinition)((Object)object)).getRuneItemId() >= 560) {
            if (n2 <= 0) {
                if (PURE_ESSENCE_ITEM_ID != 1436) {
                    object = player;
                    ((Player)object).packetSender.sendGameMessage("You need pure essence to make these kinds of runes.");
                } else {
                    object = player;
                    ((Player)object).packetSender.sendGameMessage("You need rune essence to make runes.");
                }
                if (player.botEnabled) {
                    player.currentBotTask.startWalkToBank(player);
                }
                return;
            }
            player.getInventoryManager().removeItem(new ItemStack(PURE_ESSENCE_ITEM_ID, n2));
            player.getInventoryManager().addItem(new ItemStack(((RuneDefinition)((Object)object)).getRuneItemId(), n2 * n3));
            player.getSkillManager().addExperience(20, ((RuneDefinition)((Object)object)).getExperience() * (double)n2);
        } else {
            if (n2 <= 0 && n <= 0) {
                if (PURE_ESSENCE_ITEM_ID != 1436) {
                    object = player;
                    ((Player)object).packetSender.sendGameMessage("You need rune or pure essence to make these kinds of runes.");
                } else {
                    object = player;
                    ((Player)object).packetSender.sendGameMessage("You need rune essence to make runes.");
                }
                if (player.botEnabled) {
                    player.currentBotTask.startWalkToBank(player);
                }
                return;
            }
            int n4 = 0;
            while (n4 < 28) {
                player.getInventoryManager().removeItem(new ItemStack(1436, n));
                if (PURE_ESSENCE_ITEM_ID != 1436) {
                    player.getInventoryManager().removeItem(new ItemStack(PURE_ESSENCE_ITEM_ID, n2));
                }
                ++n4;
            }
            player.getInventoryManager().addItem(new ItemStack(((RuneDefinition)((Object)object)).getRuneItemId(), n * n3));
            player.getSkillManager().addExperience(20, ((RuneDefinition)((Object)object)).getExperience() * (double)n);
            if (PURE_ESSENCE_ITEM_ID != 1436) {
                player.getInventoryManager().addItem(new ItemStack(((RuneDefinition)((Object)object)).getRuneItemId(), n2 * n3));
                player.getSkillManager().addExperience(20, ((RuneDefinition)((Object)object)).getExperience() * (double)n2);
            }
            if (player.botEnabled) {
                player.currentBotTask.startWalkToBank(player);
            }
        }
        object = player;
        ((Player)object).packetSender.sendSoundEffect(481, 1, 0);
        player.getUpdateState().setAnimation(791);
        player.getUpdateState().setGraphicHeight100(186);
    }

    public static void recordScryingOrbTeleport(Player player) {
        int n;
        int n2;
        int n3;
        block6: {
            n3 = player.eA();
            int n4 = 0;
            while (n4 < SCRYING_ORB_NPC_IDS.length) {
                n2 = SCRYING_ORB_NPC_IDS[n4];
                if (n3 == n2) {
                    n = n4;
                    break block6;
                }
                ++n4;
            }
            n = -1;
        }
        n3 = 1 + n;
        ItemStack itemStack = player.getInventoryManager().getContainer().findFlatItem(5519);
        n2 = itemStack.getMetadata();
        if ((n2 & GameUtil.b(n3)) == 0) {
            itemStack.setMetadata(n2 += GameUtil.b(n3));
        }
        Player player2 = player;
        n2 = 0;
        int n5 = itemStack.getMetadata();
        int n6 = 0;
        while (n6 < SCRYING_ORB_NPC_IDS.length) {
            int n7 = n6 + 1;
            if ((n5 & GameUtil.b(n7)) != 0) {
                ++n2;
            }
            ++n6;
        }
        if (n2 >= 3) {
            player2.getInventoryManager().removeItem(new ItemStack(5519));
            player2.getInventoryManager().addItem(new ItemStack(5518));
            Player player3 = player2;
            player3.packetSender.sendGameMessage("Your scrying orb has absorbed enough teleport information.");
        }
    }

    public static void startAbyssMageTeleport(Player player, Npc object) {
        if (player.getQuestState(14) != 1) {
            object = QuestDefinition.b(14);
            object = ((QuestDefinition)object).c();
            player.packetSender.sendGameMessage("You need to complete " + (String)object + " to do this.");
            return;
        }
        player.ap(((Npc)object).getNpcId());
        ((Npc)object).startAbyssMageTeleport(player, 2911, 4832, 0, "Senventior disthine molenko!");
    }

    public static boolean locateTalismanDirection(Player player, int n) {
        Object object;
        Object object2;
        Object object3;
        int n2;
        Object object4;
        block7: {
            object4 = RunecraftingAltarDefinition.values();
            int n3 = ((RunecraftingAltarDefinition[])object4).length;
            n2 = 0;
            while (n2 < n3) {
                RunecraftingAltarDefinition runecraftingAltarDefinition;
                object3 = runecraftingAltarDefinition = object4[n2];
                if (n == object3.talismanItemId) {
                    object2 = runecraftingAltarDefinition;
                    break block7;
                }
                ++n2;
            }
            object2 = object = null;
        }
        if (object2 == null) {
            return false;
        }
        RunecraftingAltarDefinition runecraftingAltarDefinition = object;
        object3 = runecraftingAltarDefinition;
        object3 = object;
        n2 = object3.ruinsPosition.getY();
        int n4 = runecraftingAltarDefinition.ruinsPosition.getX();
        object = player;
        String string = "";
        object4 = "";
        if (((Entity)object).getPosition().getX() >= n4) {
            string = "west";
        }
        if (((Entity)object).getPosition().getY() > n2) {
            object4 = "South";
        }
        if (((Entity)object).getPosition().getX() < n4) {
            string = "east";
        }
        if (((Entity)object).getPosition().getY() <= n2) {
            object4 = "North";
        }
        object3 = object;
        ((Player)object3).packetSender.sendGameMessage("You feel a slight pull towards " + (String)object4 + "-" + string + "...");
        return true;
    }
}

