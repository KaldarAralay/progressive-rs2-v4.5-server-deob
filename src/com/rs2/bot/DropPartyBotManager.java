/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot;

import com.rs2.ServerSettings;
import com.rs2.bot.BotTradeAdvertManager;
import com.rs2.bot.DropPartyCompletionTask;
import com.rs2.bot.DropPartyFollowerTickTask;
import com.rs2.bot.DropPartyGroundItemPickupTask;
import com.rs2.bot.DropPartyLeaderCleanupTask;
import com.rs2.bot.DropPartyLeaderTickTask;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.bot.combat.BotCombatLoadoutManager;
import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.World;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.GrandExchangeManager;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public final class DropPartyBotManager {
    private static int leaderDropItemCount = 10;
    public static boolean dropPartyActive = false;
    public static int dropPartyChanceDivisor = 48;
    private static int valuableDropMinValue = 500;
    public static ArrayList pendingDropPartyGroundItems = new ArrayList();
    public static ArrayList dropPartyParticipants = new ArrayList();
    public static int targetDropPartySize = 0;
    public static int baseDropPartySize = 4;
    public static int dropPartyPretaskLoopLimit = 2;
    private static ArrayList dropPartyRewardPool = new ArrayList();
    private static ArrayList valuableDropPartyRewardPool = new ArrayList();

    public static void notifyDropPartyRewardDropped(GroundItem groundItem) {
        int n = 1;
        while (n < dropPartyParticipants.size()) {
            Object object = (Player)dropPartyParticipants.get(n);
            int n2 = 2 + GameUtil.h(3);
            int n3 = GameUtil.b(((Entity)object).getPosition(), groundItem.getPosition());
            int n4 = 1 + GameUtil.h(5);
            int n5 = GrandExchangeManager.a(groundItem.getItem().getId());
            if (n5 >= 500) {
                n4 = 20;
            }
            if (n3 == 0 && GameUtil.h(3) == 0) {
                BotCombatHelper.pickupVisibleGroundItem((Player)object, groundItem.getItem().getId(), groundItem.getPosition());
            } else if (n3 < n4) {
                object = new DropPartyGroundItemPickupTask(n2, (Player)object, groundItem);
                World.getTaskScheduler().schedule((TickTask)object);
            }
            ++n;
        }
    }

    public static void finishLeaderDrops(Player object) {
        ((Player)object).queuePublicChatMessage("All dropped, good luck to all.");
        ((Player)object).botTaskState = "wait for new task";
        object = new DropPartyLeaderCleanupTask(10, (Player)object);
        World.getTaskScheduler().schedule((TickTask)object);
    }

    public static void finishDropPartyParticipant(Player object) {
        ((Player)object).botTaskState = "wait for new task";
        object = new DropPartyCompletionTask(10, (Player)object);
        World.getTaskScheduler().schedule((TickTask)object);
    }

    public static void startDropPartyTick(Player object) {
        ((Player)object).botPublicChatMessage = "Follow for Drop party!";
        ((Player)object).botPublicChatColor = GameUtil.h(12);
        int[] nArray = new int[3];
        nArray[1] = 1;
        nArray[2] = 3;
        Object object2 = nArray;
        int n = GameUtil.h(3);
        ((Player)object).botPublicChatEffect = object2[n];
        if (((Player)object).dropPartyLeader) {
            object2 = new DropPartyLeaderTickTask(3, (Player)object);
            World.getTaskScheduler().schedule((TickTask)object2);
            return;
        }
        object2 = (Player)dropPartyParticipants.get(0);
        object = new DropPartyFollowerTickTask(3, (Player)object, (Player)object2);
        World.getTaskScheduler().schedule((TickTask)object);
    }

    public static void prepareDropPartyCombatLoadout(Player player) {
        int n;
        GameplayHelper.b(player);
        int n2 = 80 + GameUtil.h(15);
        if (player.dropPartyFollower) {
            n2 = 1 + GameUtil.h(60);
        }
        if ((n = n2 / 5 << 1) == 0) {
            n = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 0, n2 - n2 / 5 + GameUtil.h(n));
        n = n2 / 5 << 1;
        if (n == 0) {
            n = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 2, n2 - n2 / 5 + GameUtil.h(n));
        n = n2 / 5 << 1;
        if (n == 0) {
            n = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 1, n2 - n2 / 5 + GameUtil.h(n));
        n = n2 / 5 << 1;
        if (n == 0) {
            n = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 4, n2 - n2 / 5 + GameUtil.h(n));
        n = n2 / 5 << 1;
        if (n == 0) {
            n = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 6, n2 - n2 / 5 + GameUtil.h(n));
        n = n2 / 5 << 1;
        if (n == 0) {
            n = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 5, n2 - (n2 / 5 << 1) + GameUtil.h(n));
        player.getSkillManager().getExperience()[3] = BotCombatHelper.calculateBotHitpointsExperience(player);
        int[] nArray = player.getSkillManager().getCurrentLevels();
        player.getSkillManager();
        nArray[3] = SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[3]);
        player.getSkillManager().refreshAllSkills();
        BotCombatLoadoutManager.a(player, true);
    }

    public static void initializeDropPartyRewardPools() {
        Object object = BotTradeAdvertManager.tradeAdvertOfferPool;
        Iterator iterator = ((ArrayList)object).iterator();
        while (iterator.hasNext()) {
            object = (GameplayHelper)iterator.next();
            if (object == null || ((GameplayHelper)object).c() >= 7955) continue;
            int n = GrandExchangeManager.a(((GameplayHelper)object).c());
            if (n >= valuableDropMinValue) {
                valuableDropPartyRewardPool.add(object);
            }
            dropPartyRewardPool.add(object);
        }
    }

    public static void prepareDropPartyInventory(Player player) {
        int n;
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        if (player.dropPartyLeader) {
            int n2;
            Object object;
            Object object22;
            Object object32;
            n = 0;
            ArrayList<Object> arrayList = new ArrayList<Object>();
            for (Object object32 : dropPartyRewardPool) {
                arrayList.add(object32);
            }
            object32 = new ArrayList();
            for (Object object22 : valuableDropPartyRewardPool) {
                ((ArrayList)object32).add(object22);
            }
            object22 = new ArrayList();
            Object object4 = arrayList;
            int n3 = 0;
            while (n3 < leaderDropItemCount) {
                int n4;
                if (n3 == leaderDropItemCount - 1 && n < valuableDropMinValue) {
                    object4 = object32;
                }
                int n5 = GameUtil.h(((ArrayList)object4).size());
                object = (GameplayHelper)((ArrayList)object4).get(n5);
                ((ArrayList)object22).add(object);
                arrayList.remove(object);
                if (((ArrayList)object32).contains(object)) {
                    ((ArrayList)object32).remove(object);
                }
                if ((n4 = GrandExchangeManager.a(((GameplayHelper)object).c())) > n) {
                    n = n4;
                    player.botAdvertItemId = ((GameplayHelper)object).c();
                }
                ++n3;
            }
            n = n2 = n;
            n2 = baseDropPartySize;
            if (n >= 10000 && n < 250000) {
                n2 = 6;
            }
            if (n >= 25000 && n < 100000) {
                n2 = 8;
            }
            if (n >= 100000 && n < 200000) {
                n2 = 10;
            }
            if (n >= 200000 && n < 400000) {
                n2 = 12;
            }
            if (n >= 400000 && n < 600000) {
                n2 = 14;
            }
            if (n >= 600000 && n < 800000) {
                n2 = 16;
            }
            if (n >= 800000 && n < 1000000) {
                n2 = 18;
            }
            if (n >= 1000000) {
                n2 = 20;
            }
            if (n2 > ServerSettings.otherBotCount) {
                n2 = ServerSettings.otherBotCount;
            }
            targetDropPartySize = n2;
            Collections.shuffle(object22);
            object = ((ArrayList)object22).iterator();
            while (object.hasNext()) {
                GameplayHelper gameplayHelper = (GameplayHelper)object.next();
                player.getInventoryManager().addItem(new ItemStack(gameplayHelper.c(), 1));
            }
            ItemDefinition itemDefinition = ItemDefinition.forId(player.botAdvertItemId);
            player.botPublicChatMessage = "Follow for Drop party! Best drop: " + itemDefinition.getDisplayName();
        }
        Player player2 = player;
        if (player2.botCombatStyle == 0) {
            BotCombatLoadoutManager.e(player2);
            BotCombatLoadoutManager.d(player2);
        } else if (player2.botCombatStyle == 2) {
            BotCombatLoadoutManager.g(player2);
        } else if (player2.botCombatStyle == 1) {
            BotCombatLoadoutManager.f(player2);
            int n6 = player2.getSkillManager().getCurrentLevels()[4] >= 40 ? 1731 : (n = GameUtil.h(3) == 0 ? 1478 : 1729);
            if (!BotCombatHelper.isFreeToPlayWorld() && player2.getCombatLevel() >= 60 && GameUtil.h(2) == 0) {
                n = 1712;
            }
            player2.getEquipmentManager().getContainer().setItem(2, new ItemStack(n));
        }
        BotCombatLoadoutManager.c(player2);
        player.getInventoryManager().refresh();
        player.getEquipmentManager().refresh();
    }
}

