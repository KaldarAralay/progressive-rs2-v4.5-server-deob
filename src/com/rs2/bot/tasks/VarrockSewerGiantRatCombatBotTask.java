/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot.tasks;

import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.bot.tasks.VarrockSewerGiantRatDungeonEntryTickTask;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.item.ItemStack;
import com.rs2.model.item.consumable.FoodDefinition;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class VarrockSewerGiantRatCombatBotTask
extends BotTaskDefinition {
    private static Position aa = new Position(3253, 3420, 0);
    private static BotRoute[] ab = new BotRoute[]{new BotRoute(new Position[]{new Position(3253, 3428, 0), new Position(3247, 3432, 0), new Position(3245, 3444, 0), new Position(3243, 3455, 0), new Position(3238, 3458, 0)}), new BotRoute(new Position[]{new Position(3238, 9866, 0)})};

    public VarrockSewerGiantRatCombatBotTask(int n) {
        super(aa, ab, 1, false, 1);
        n = 1;
        VarrockSewerGiantRatCombatBotTask varrockSewerGiantRatCombatBotTask = this;
        this.combatTask = true;
    }

    @Override
    public final boolean isWithinProgressionRange(Player player) {
        return player.getCombatLevel() < 12;
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        player.botInteractionTargetIds.add(87);
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
            int[] nArray = foodDefinition.c();
            int n4 = nArray.length;
            int n5 = 0;
            while (n5 < n4) {
                int n6 = nArray[n5];
                if (player.i(n6, 10)) {
                    n = n6;
                    break;
                }
                ++n5;
            }
            if (n != -1) break;
            ++n3;
        }
        arrayList.add(new ItemStack(n, 10));
        player.botFoodItemId = n;
        return arrayList;
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        player.getBankContainer().clear();
        player.botFoodItemId = 315;
        player.getBankContainer().addToTab(new ItemStack(player.botFoodItemId, 1000), 0);
        Object object = new ItemStack[]{new ItemStack(player.botFoodItemId, 10)};
        player.botTaskRequiredItems = object;
        player.getInventoryManager().addItem(object[0]);
        object = player;
        GameplayHelper.a((Player)object, -1);
        player.getInventoryManager().refresh();
        player.getEquipmentManager().refresh();
    }

    @Override
    public final void prepareTaskCombatLoadout(Player player) {
        GameplayHelper.b(player);
        int n = 1 + GameUtil.h(6);
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
        player.currentBotRoute = ab[player.botPathSegmentIndex];
        player.bk();
    }

    @Override
    public final void startWalkToBank(Player player) {
        player.setAutoRetaliate(false);
        player.botTaskState = "walk towards bank";
        player.botPathWaypointIndex = 0;
        player.botPathSegmentIndex = ab.length - 1;
        player.currentBotRoute = ab[player.botPathSegmentIndex].reversed();
        player.bk();
    }

    public final void a(Player player, int n) {
        player.setAutoRetaliate(true);
        player.botPathWaypointIndex = n;
        player.currentBotRoute = ab[player.botPathSegmentIndex];
        this.c(player, true);
    }

    public final void b(Player player, int n) {
        player.setAutoRetaliate(false);
        player.botPathWaypointIndex = n;
        player.currentBotRoute = ab[player.botPathSegmentIndex].reversed();
        this.c(player, true);
    }

    @Override
    public final void c(Player player, boolean bl) {
        if (player.botTaskState.equals("walk towards task") || player.botTaskState.equals("walk to task") && bl) {
            if (!bl) {
                ++player.botPathSegmentIndex;
            }
            player.currentBotRoute = ab[player.botPathSegmentIndex];
            if (!bl) {
                player.botPathWaypointIndex = 0;
            }
            if (player.botPathSegmentIndex == 1) {
                Object object = new ArrayList<Integer>();
                ((ArrayList)object).add(881);
                player.dm = true;
                if (player.interactWithBotObjectTargetsNoRetry((ArrayList)object, false)) {
                    object = new VarrockSewerGiantRatDungeonEntryTickTask(this, 3, player);
                    World.getTaskScheduler().schedule((TickTask)object);
                } else {
                    ((ArrayList)object).clear();
                    ((ArrayList)object).add(882);
                    player.interactWithBotObjectTargetsNoRetry((ArrayList)object, false);
                }
            }
            if (player.botPathSegmentIndex == ab.length - 1) {
                player.botTaskState = "walk to task";
                return;
            }
        } else if (player.botTaskState.equals("walk towards bank") || player.botTaskState.equals("walk to bank") && bl) {
            if (!bl) {
                --player.botPathSegmentIndex;
            }
            player.currentBotRoute = ab[player.botPathSegmentIndex].reversed();
            if (!bl) {
                player.botPathWaypointIndex = 0;
            }
            if (player.botPathSegmentIndex == 0) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(1755);
                player.interactWithBotObjectTargets(arrayList);
                player.dm = true;
                player.botTaskState = "walk to bank";
            }
        }
    }
}

