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
import java.util.ArrayList;

public final class SeersFlaxSpinningBotTask
extends BotTaskDefinition {
    private static Position aa = new Position(2724, 3493, 0);
    private static BotRoute[] ab = new BotRoute[]{new BotRoute(new Position[]{new Position(2725, 3486, 0), new Position(2718, 3484, 0), new Position(2716, 3472, 0)}), new BotRoute(new Position[]{new Position(2715, 3472, 0), new Position(2715, 3471, 0)}), new BotRoute(new Position[]{new Position(2715, 3471, 1), new Position(2712, 3471, 1)})};

    public SeersFlaxSpinningBotTask(int n) {
        super(aa, ab, 0, true, 4);
        n = 2;
        SeersFlaxSpinningBotTask seersFlaxSpinningBotTask = this;
        this.interactionOption = 2;
    }

    @Override
    public final boolean meetsUnlockRequirements(Player player) {
        return player.getCombatLevel() > 20;
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        player.botInteractionTargetIds.add(2644);
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        player.getBankContainer().clear();
        player.botTaskItemId = 1779;
        player.getBankContainer().addToTab(new ItemStack(player.botTaskItemId, 5000), 0);
        ItemStack[] itemStackArray = new ItemStack[]{new ItemStack(player.botTaskItemId, 28)};
        player.botTaskRequiredItems = itemStackArray;
        player.getInventoryManager().addItem(itemStackArray[0]);
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
        if ((n = n - n / 5 + GameUtil.h(n2)) < 10) {
            n = 10;
        }
        BotCombatHelper.setBotSkillLevel(player, 12, n);
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
            if (player.botPathSegmentIndex == 0 || player.botPathSegmentIndex == 1) {
                player.botTargetNpcId = 1530;
                return;
            }
            if (player.botPathSegmentIndex == 2 && player.botPathWaypointIndex == 0 && player.getPosition().getPlane() == 0) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(1747);
                player.interactWithBotObjectTargets(arrayList);
                player.dm = true;
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
            if (player.getPosition().getPlane() != 0 && (player.botPathSegmentIndex == 1 || player.botPathSegmentIndex == 2 && player.botPathWaypointIndex == 0)) {
                player.botInteractionOption = 1;
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(1746);
                player.interactWithBotObjectTargets(arrayList);
                player.dm = true;
                return;
            }
            if (player.botPathSegmentIndex == 1) {
                player.botInteractionOption = 1;
                player.botTargetNpcId = 1530;
                return;
            }
            if (player.botPathSegmentIndex == 0) {
                player.botInteractionOption = 1;
                player.botTargetNpcId = 1530;
                player.botTaskState = "walk to bank";
            }
        }
    }
}

