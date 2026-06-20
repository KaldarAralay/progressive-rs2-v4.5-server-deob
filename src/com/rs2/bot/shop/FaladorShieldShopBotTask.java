/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot.shop;

import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.bot.shop.FaladorShieldShopTradeTickTask;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.NpcDefinition;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class FaladorShieldShopBotTask
extends BotTaskDefinition {
    private static Position aa = new Position(2946, 3368, 0);
    private static BotRoute[] ab = new BotRoute[]{new BotRoute(new Position[]{new Position(2946, 3374, 0), new Position(2954, 3379, 0), new Position(2971, 3383, 0)}), new BotRoute(new Position[]{new Position(2972, 3383, 0)})};

    public FaladorShieldShopBotTask(int n) {
        super(aa, ab, 1, false, 1);
        n = 2;
        FaladorShieldShopBotTask faladorShieldShopBotTask = this;
        this.interactionOption = 2;
        this.usesCustomTaskAction = true;
    }

    @Override
    public final int getShopId() {
        NpcDefinition npcDefinition = NpcDefinition.forId(577);
        return npcDefinition.getShopId();
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        player.botInteractionTargetIds.add(577);
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        player.getBankContainer().clear();
        player.botTaskItemId = 1189;
        player.botShopItemAmount = 1;
        player.botShopBuyMode = GameUtil.h(2);
        ItemStack[] itemStackArray = player.botShopBuyMode == 1 ? new ItemStack[]{new ItemStack(995, 5000)} : new ItemStack[]{new ItemStack(player.botTaskItemId, player.botShopItemAmount)};
        player.botTaskRequiredItems = itemStackArray;
        player.getInventoryManager().addItem(itemStackArray[0]);
        player.getInventoryManager().refresh();
        player.getEquipmentManager().refresh();
    }

    @Override
    public final void startCustomTaskAction(Player object) {
        ItemStack itemStack = new ItemStack(((Player)object).botTaskItemId, ((Player)object).botShopItemAmount);
        object = new FaladorShieldShopTradeTickTask(this, 2, (Player)object, itemStack);
        World.getTaskScheduler().schedule((TickTask)object);
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
        if (player.botTaskState.equals("walk towards task")) {
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
        } else if (player.botTaskState.equals("walk towards bank")) {
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

