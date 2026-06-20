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
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.util.GameUtil;

public final class FaladorSteelSmeltingBotTask
extends BotTaskDefinition {
    private static Position aa = new Position(2946, 3368, 0);
    private static BotRoute[] ab = new BotRoute[]{new BotRoute(new Position[]{new Position(2946, 3374, 0), new Position(2954, 3379, 0), new Position(2971, 3377, 0)}), new BotRoute(new Position[]{new Position(2971, 3376, 0), new Position(2974, 3369, 0), !GameplayHelper.b(11666) ? new Position(2974, 3369, 0) : new Position(2975, 3369, 0)})};

    public FaladorSteelSmeltingBotTask(int n) {
        super(aa, ab, 0, false, 1);
        n = 2;
        FaladorSteelSmeltingBotTask faladorSteelSmeltingBotTask = this;
        this.interactionOption = 2;
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        int n = !GameplayHelper.b(11666) ? 2781 : 11666;
        player.botInteractionTargetIds.clear();
        player.botInteractionTargetIds.add(n);
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        player.getBankContainer().clear();
        player.botTaskItemId = 2353;
        player.getBankContainer().addToTab(new ItemStack(440, 5000), 0);
        player.getBankContainer().addToTab(new ItemStack(453, 10000), 0);
        ItemStack[] itemStackArray = new ItemStack[]{new ItemStack(440, 9), new ItemStack(453, 18)};
        player.botTaskRequiredItems = itemStackArray;
        player.getInventoryManager().addItem(itemStackArray[0]);
        player.getInventoryManager().addItem(itemStackArray[1]);
        player.getInventoryManager().refresh();
        player.getEquipmentManager().refresh();
    }

    @Override
    public final void prepareTaskCombatLoadout(Player player) {
        GameplayHelper.b(player);
        int n = 1 + GameUtil.h(40);
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
        n2 = n / 5 << 1;
        if (n2 == 0) {
            n2 = 2;
        }
        if ((n = n - n / 5 + GameUtil.h(n2)) < 30) {
            n = 30;
        }
        BotCombatHelper.setBotSkillLevel(player, 13, n);
        player.getSkillManager().getExperience()[3] = BotCombatHelper.calculateBotHitpointsExperience(player);
        int[] nArray = player.getSkillManager().getCurrentLevels();
        player.getSkillManager();
        nArray[3] = SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[3]);
        player.getSkillManager().refreshAllSkills();
    }

    @Override
    public final void startWalkToTask(Player player) {
        int n = !GameplayHelper.b(11707) ? 1530 : 11707;
        player.setAutoRetaliate(true);
        player.botTaskState = "walk towards task";
        player.botPathWaypointIndex = 0;
        player.botPathSegmentIndex = 0;
        player.currentBotRoute = ab[player.botPathSegmentIndex];
        player.botTargetNpcId = n;
        player.bk();
    }

    @Override
    public final void startWalkToBank(Player player) {
        int n = !GameplayHelper.b(11707) ? 1530 : 11707;
        player.setAutoRetaliate(false);
        player.botTaskState = "walk towards bank";
        player.botPathWaypointIndex = 0;
        player.botPathSegmentIndex = ab.length - 1;
        player.currentBotRoute = ab[player.botPathSegmentIndex].reversed();
        player.botTargetNpcId = n;
        player.bk();
    }

    public final void a(Player player, int n) {
        int n2 = !GameplayHelper.b(11707) ? 1530 : 11707;
        player.setAutoRetaliate(true);
        player.botPathWaypointIndex = n;
        player.currentBotRoute = ab[player.botPathSegmentIndex];
        player.botTargetNpcId = n2;
        this.c(player, true);
    }

    public final void b(Player player, int n) {
        int n2 = !GameplayHelper.b(11707) ? 1530 : 11707;
        player.setAutoRetaliate(false);
        player.botPathWaypointIndex = n;
        player.currentBotRoute = ab[player.botPathSegmentIndex].reversed();
        player.botTargetNpcId = n2;
        this.c(player, true);
    }

    @Override
    public final void c(Player player, boolean bl) {
        int n;
        int n2 = n = !GameplayHelper.b(11707) ? 1530 : 11707;
        if (player.botTaskState.equals("walk towards task") || player.botTaskState.equals("walk to task") && bl) {
            if (!bl) {
                ++player.botPathSegmentIndex;
            }
            player.currentBotRoute = ab[player.botPathSegmentIndex];
            if (!bl) {
                player.botPathWaypointIndex = 0;
            }
            player.botTargetNpcId = n;
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
            player.botTargetNpcId = n;
            if (player.botPathSegmentIndex == 0) {
                player.botTaskState = "walk to bank";
            }
        }
    }
}

