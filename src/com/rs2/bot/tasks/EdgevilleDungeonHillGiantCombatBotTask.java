/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot.tasks;

import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.item.consumable.FoodDefinition;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class EdgevilleDungeonHillGiantCombatBotTask
extends BotTaskDefinition {
    private static Position aa = new Position(3185, 3436, 0);
    private static int[] ab = new int[]{1013, 1139, 1203, 1422, 1789, 1917};
    private static BotRoute[] ac = new BotRoute[]{new BotRoute(new Position[]{new Position(3182, 3432, 0), new Position(3163, 3426, 0), new Position(3149, 3426, 0), new Position(3135, 3432, 0), new Position(3127, 3441, 0), new Position(3115, 3449, 0)}), new BotRoute(new Position[]{new Position(3115, 3450, 0)}), new BotRoute(new Position[]{new Position(3116, 9843, 0), new Position(3114, 9836, 0)})};

    public EdgevilleDungeonHillGiantCombatBotTask(int n) {
        super(aa, ac, 1, false, 10);
        boolean bl = true;
        EdgevilleDungeonHillGiantCombatBotTask edgevilleDungeonHillGiantCombatBotTask = this;
        this.combatTask = true;
        int[] nArray = ab;
        edgevilleDungeonHillGiantCombatBotTask = this;
        this.ignoredLootItemIds = nArray;
        super.addLootSellShopIds(new int[]{151});
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        player.botInteractionTargetIds.add(117);
    }

    @Override
    public final boolean meetsUnlockRequirements(Player player) {
        return player.getCombatLevel() >= 23;
    }

    @Override
    public final boolean isWithinProgressionRange(Player player) {
        return player.getCombatLevel() <= 60;
    }

    @Override
    public final ArrayList getRequiredItems(Player player) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        int n = -1;
        FoodDefinition[] foodDefinitionArray = FoodDefinition.values();
        int n2 = foodDefinitionArray.length;
        int n3 = 0;
        while (n3 < n2) {
            FoodDefinition foodDefinition = foodDefinitionArray[n3];
            if (foodDefinition.a() >= 7) {
                int[] nArray = foodDefinition.c();
                int n4 = nArray.length;
                int n5 = 0;
                while (n5 < n4) {
                    int n6 = nArray[n5];
                    if (player.i(n6, 16)) {
                        n = n6;
                        break;
                    }
                    ++n5;
                }
                if (n != -1) break;
            }
            ++n3;
        }
        arrayList.add(new ItemStack(n, 16));
        player.botFoodItemId = n;
        arrayList.add(new ItemStack(983, 1));
        return arrayList;
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        player.getBankContainer().clear();
        player.botFoodItemId = 379;
        player.getBankContainer().addToTab(new ItemStack(player.botFoodItemId, 1000), 0);
        Object object = new ItemStack[]{new ItemStack(983), new ItemStack(player.botFoodItemId, 20)};
        player.botTaskRequiredItems = object;
        player.getInventoryManager().addItem(object[0]);
        player.getInventoryManager().addItem(object[1]);
        object = player;
        GameplayHelper.a((Player)object, -1);
        player.getInventoryManager().refresh();
        player.getEquipmentManager().refresh();
    }

    @Override
    public final void prepareTaskCombatLoadout(Player player) {
        GameplayHelper.b(player);
        int n = 20 + GameUtil.h(20);
        int n2 = n / 5 << 1;
        if (n2 == 0) {
            n2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 0, n - n / 5 + GameUtil.h(n2));
        n2 = n / 5 << 1;
        if (n2 == 0) {
            n2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 2, n - n / 5 + GameUtil.h(n2));
        n2 = n / 5 << 1;
        if (n2 == 0) {
            n2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 1, n - n / 5 + GameUtil.h(n2));
        n2 = n / 5 << 1;
        if (n2 == 0) {
            n2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 4, n - n / 5 + GameUtil.h(n2));
        n2 = n / 5 << 1;
        if (n2 == 0) {
            n2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 6, n - n / 5 + GameUtil.h(n2));
        n2 = n / 5 << 1;
        if (n2 == 0) {
            n2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 5, n - (n / 5 << 1) + GameUtil.h(n2));
        player.getSkillManager().getExperience()[3] = BotCombatHelper.calculateBotHitpointsExperience(player);
        int[] nArray = player.getSkillManager().getCurrentLevels();
        player.getSkillManager();
        nArray[3] = SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[3]);
        player.getSkillManager().refreshAllSkills();
    }

    @Override
    public final void startWalkToTask(Player player) {
        player.setAutoRetaliate(true);
        player.botTaskState = "walk towards task";
        player.botPathWaypointIndex = 0;
        player.botPathSegmentIndex = 0;
        player.currentBotRoute = ac[player.botPathSegmentIndex];
        player.bk();
    }

    @Override
    public final void startWalkToBank(Player player) {
        player.setAutoRetaliate(false);
        player.botTaskState = "walk towards bank";
        player.botPathWaypointIndex = 0;
        player.botPathSegmentIndex = ac.length - 1;
        player.currentBotRoute = ac[player.botPathSegmentIndex].reversed();
        player.bk();
    }

    public final void a(Player player, int n) {
        player.setAutoRetaliate(true);
        player.botPathWaypointIndex = n;
        player.currentBotRoute = ac[player.botPathSegmentIndex];
        this.c(player, true);
    }

    public final void b(Player player, int n) {
        player.setAutoRetaliate(false);
        player.botPathWaypointIndex = n;
        player.currentBotRoute = ac[player.botPathSegmentIndex].reversed();
        this.c(player, true);
    }

    @Override
    public final void c(Player player, boolean n) {
        if (player.botTaskState.equals("walk towards task") || player.botTaskState.equals("walk to task") && n != 0) {
            if (n == 0) {
                ++player.botPathSegmentIndex;
            }
            player.currentBotRoute = ac[player.botPathSegmentIndex];
            if (n == 0) {
                player.botPathWaypointIndex = 0;
            }
            n = GameUtil.a(player.getPosition().getX(), player.getPosition().getY());
            if (player.botPathSegmentIndex == ac.length - 1 && n == 12341) {
                player.botTaskState = "walk to task";
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(1754);
                player.interactWithBotObjectTargets(arrayList);
                player.dm = true;
                return;
            }
            player.botTargetNpcId = 1804;
            return;
        }
        if (player.botTaskState.equals("walk towards bank") || player.botTaskState.equals("walk to bank") && n != 0) {
            if (n == 0) {
                --player.botPathSegmentIndex;
            }
            player.currentBotRoute = ac[player.botPathSegmentIndex].reversed();
            if (n == 0) {
                player.botPathWaypointIndex = 0;
            }
            n = GameUtil.a(player.getPosition().getX(), player.getPosition().getY());
            if (player.botPathSegmentIndex == ac.length - 2 && n == 12441) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(1755);
                player.interactWithBotObjectTargets(arrayList);
                player.dm = true;
                return;
            }
            player.botTaskState = "walk to bank";
            player.botTargetNpcId = 1804;
        }
    }
}

