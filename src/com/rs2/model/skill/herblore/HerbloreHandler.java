/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.herblore;

import com.rs2.ServerSettings;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.model.skill.herblore.FinishedPotionTask;
import com.rs2.model.skill.herblore.UnfinishedPotionTask;
import com.rs2.model.task.CycleEventHandler;

public final class HerbloreHandler {
    private static final double[][] POTION_RECIPES = new double[][]{{249.0, 221.0, 91.0, 121.0, 25.0, 0.0}, {251.0, 235.0, 93.0, 175.0, 37.5, 5.0}, {253.0, 225.0, 95.0, 115.0, 50.0, 12.0}, {253.0, 592.0, 95.0, 3408.0, 50.0, 15.0}, {255.0, 223.0, 97.0, 127.0, 62.5, 22.0}, {255.0, 1581.0, 97.0, 1582.0, 80.0, 25.0}, {255.0, 1975.0, 97.0, 3010.0, 67.5, 26.0}, {257.0, 239.0, 99.0, 133.0, 75.0, 30.0}, {2998.0, 2152.0, 3002.0, 3034.0, 80.0, 34.0}, {257.0, 231.0, 99.0, 139.0, 87.5, 38.0}, {259.0, 221.0, 101.0, 145.0, 100.0, 45.0}, {259.0, 235.0, 101.0, 181.0, 106.3, 48.0}, {261.0, 231.0, 103.0, 151.0, 112.5, 50.0}, {261.0, 2970.0, 103.0, 3018.0, 117.5, 52.0}, {263.0, 225.0, 105.0, 157.0, 125.0, 55.0}, {263.0, 241.0, 105.0, 187.0, 137.5, 60.0}, {3000.0, 223.0, 3004.0, 3026.0, 142.5, 63.0}, {265.0, 239.0, 107.0, 163.0, 150.0, 66.0}, {2998.0, 6049.0, 5942.0, 5945.0, 175.0, 68.0}, {2481.0, 241.0, 2483.0, 2454.0, 157.5, 69.0}, {267.0, 245.0, 109.0, 169.0, 162.5, 72.0}, {6016.0, 223.0, 5936.0, 5937.0, 175.0, 73.0}, {2481.0, 3138.0, 2483.0, 3042.0, 172.5, 76.0}, {269.0, 247.0, 111.0, 189.0, 175.0, 78.0}, {259.0, 6051.0, 101.0, 5954.0, 175.0, 79.0}, {2998.0, 6693.0, 3002.0, 6687.0, 175.0, 81.0}, {2398.0, 6018.0, 5939.0, 5940.0, 175.0, 82.0}};

    public static boolean combinePotionDoses(Player player, int n, int n2, int n3, int n4) {
        block5: {
            int n5;
            int n6;
            String string;
            Object object;
            block7: {
                Object object2;
                block6: {
                    object = new ItemStack(n);
                    object2 = new ItemStack(n2);
                    string = ((ItemStack)object).getDefinition().getName().toLowerCase();
                    String string2 = ((ItemStack)object2).getDefinition().getName().toLowerCase();
                    if (string.contains("(4)") || string2.contains("(4)") || string.contains("watering") || string2.contains("watering")) {
                        return false;
                    }
                    try {
                        if (!string.substring(0, string.indexOf("(")).equalsIgnoreCase(string2.substring(0, string2.indexOf("(")))) break block5;
                        n6 = Integer.parseInt(string.substring(string.indexOf("(") + 1, string.indexOf("(") + 2));
                        n5 = Integer.parseInt(string2.substring(string2.indexOf("(") + 1, string2.indexOf("(") + 2));
                        n5 = n6 + n5;
                        if (player.getInventoryManager().containsItem(((ItemStack)object).getId()) && player.getInventoryManager().containsItem(((ItemStack)object2).getId())) break block6;
                        return false;
                    }
                    catch (Exception exception) {}
                }
                if (n5 <= 4) break block7;
                object = String.valueOf(string.substring(0, string.indexOf("(") + 1)) + 4 + ")";
                object2 = String.valueOf(string.substring(0, string.indexOf("(") + 1)) + (n5 -= 4) + ")";
                player.getInventoryManager().c(n3);
                player.getInventoryManager().c(n4);
                player.getInventoryManager().a(new ItemStack(ItemDefinition.findIdByName((String)object)), n4);
                player.getInventoryManager().a(new ItemStack(ItemDefinition.findIdByName((String)object2)), n3);
                return true;
            }
            n6 = n5;
            object = String.valueOf(string.substring(0, string.indexOf("(") + 1)) + n6 + ")";
            player.getInventoryManager().c(n3);
            player.getInventoryManager().c(n4);
            player.getInventoryManager().a(new ItemStack(ItemDefinition.findIdByName((String)object)), n4);
            player.getInventoryManager().a(new ItemStack(229, 1), n3);
            return true;
        }
        return false;
    }

    public static boolean handlePotionMaking(Player player, ItemStack object, ItemStack itemStack, int n, int n2) {
        n = ((ItemStack)object).getId();
        int n3 = itemStack.getId();
        double[][] dArray = POTION_RECIPES;
        int n4 = 0;
        while (n4 < 27) {
            double[] dArray2 = dArray[n4];
            object = dArray2;
            n2 = (int)dArray2[0];
            Object object2 = object[1];
            Object object3 = object[2];
            Object object4 = object[3];
            Object object5 = object[4];
            Object object6 = object[5];
            int n5 = n2 == 2398 || object2 == 6018.0 || n2 == 6016 || object2 == 6049.0 ? 5935 : 227;
            if (n == n2 && n3 == n5 || n == n5 && n3 == n2) {
                if ((double)player.getSkillManager().getCurrentLevels()[15] < object6) {
                    player.getDialogueManager().showOneLineStatement("You need a Herblore level of " + (int)object6 + " in order to make this potion.");
                    return true;
                }
                int n6 = n5;
                Object object7 = object3;
                int n7 = n2;
                ItemStack itemStack2 = new ItemStack(n7, 1);
                ItemStack itemStack3 = new ItemStack(n6, 1);
                if (!ServerSettings.herbloreEnabled) {
                    Player player2 = player;
                    player2.packetSender.sendGameMessage("This skill is currently disabled.");
                } else if (player.getQuestState(29) != 1) {
                    Object object8 = QuestDefinition.b(29);
                    object8 = ((QuestDefinition)object8).c();
                    Player player3 = player;
                    player3.packetSender.sendGameMessage("You need to complete " + (String)object8 + " to do this.");
                } else {
                    player.getUpdateState().setAnimation(363);
                    Player player4 = player;
                    player4.packetSender.sendGameMessage("You put the " + itemStack2.getDefinition().getName().replace(" herb", "").toLowerCase() + " into the " + itemStack3.getDefinition().getName() + ".");
                    n5 = player.nextActionSequence();
                    CycleEventHandler.getInstance().schedule(player, new UnfinishedPotionTask(player, n5, itemStack2, n6, (double)object7), 1);
                }
                return true;
            }
            if ((double)n == object3 && (double)n3 == object2 || (double)n == object2 && (double)n3 == object3) {
                if ((double)player.getSkillManager().getCurrentLevels()[15] < object6) {
                    player.getDialogueManager().showOneLineStatement("You need a Herblore level of " + (int)object6 + " in order to make this potion.");
                    return true;
                }
                Object object9 = object5;
                Object object10 = object4;
                Object object11 = object3;
                Object object12 = object2;
                Object object13 = new ItemStack((int)object12, 1);
                if (!ServerSettings.herbloreEnabled) {
                    Player player5 = player;
                    player5.packetSender.sendGameMessage("This skill is currently disabled.");
                } else if (player.getQuestState(29) != 1) {
                    Object object14 = QuestDefinition.b(29);
                    object13 = ((QuestDefinition)object14).c();
                    object14 = player;
                    ((Player)object14).packetSender.sendGameMessage("You need to complete " + (String)object13 + " to do this.");
                } else {
                    player.getUpdateState().setAnimation(363);
                    Player player6 = player;
                    player6.packetSender.sendGameMessage("You mix the " + ((ItemStack)object13).getDefinition().getName().toLowerCase() + " into your potion");
                    int n8 = player.nextActionSequence();
                    CycleEventHandler.getInstance().schedule(player, new FinishedPotionTask(player, n8, (ItemStack)object13, (double)object11, (double)object10, (double)object9), 1);
                }
                return true;
            }
            ++n4;
        }
        return false;
    }

    public static boolean emptyContainer(Player player, ItemStack itemStack, int n) {
        Object object = itemStack.getDefinition().getDescription().toLowerCase();
        String string = itemStack.getDefinition().getName().toLowerCase();
        if (((String)object).contains("bucket") || ((String)object).contains("potion") || ((String)object).contains("dose") || ((String)object).contains("jug") || string.contains("jug") || ((String)object).contains("bowl") || ((String)object).contains("vial") || string.contains("flour") || ((String)object).contains("bucket") || ((String)object).contains("cup")) {
            object = player;
            ((Player)object).packetSender.sendGameMessage("You empty your " + string + ".");
            if (player.getInventoryManager().removeItemFromSlot(itemStack, n)) {
                player.getInventoryManager().a(new ItemStack(HerbloreHandler.getEmptyContainerItemId(itemStack)), n);
            } else if (player.getInventoryManager().removeItem(itemStack)) {
                player.getInventoryManager().addItem(new ItemStack(HerbloreHandler.getEmptyContainerItemId(itemStack)));
            }
            return true;
        }
        return false;
    }

    private static int getEmptyContainerItemId(ItemStack object) {
        String string = ((ItemStack)object).getDefinition().getDescription().toLowerCase();
        object = ((ItemStack)object).getDefinition().getName().toLowerCase();
        if (string.contains("potion") || string.contains("vial") || string.contains("dose")) {
            return 229;
        }
        if (string.contains("bucket") || string.contains("compost")) {
            return 1925;
        }
        if (string.contains("bowl") || string.contains("curry")) {
            return 1923;
        }
        if (((String)object).contains("jug") || string.contains("jug")) {
            return 1935;
        }
        if (((String)object).contains("flour")) {
            return 1931;
        }
        if (string.contains("cup")) {
            return 1980;
        }
        return -1;
    }
}

