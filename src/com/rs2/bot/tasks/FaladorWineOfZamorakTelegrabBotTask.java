/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot.tasks;

import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.bot.tasks.WineOfZamorakTelegrabTickTask;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class FaladorWineOfZamorakTelegrabBotTask
extends BotTaskDefinition {
    private static Position ab = new Position(2946, 3368, 0);
    private static BotRoute[] ac = new BotRoute[]{new BotRoute(new Position[]{new Position(2946, 3374, 0), new Position(2954, 3379, 0), new Position(2961, 3384, 0), new Position(2964, 3391, 0), new Position(2962, 3407, 0), new Position(2955, 3422, 0), new Position(2951, 3435, 0), new Position(2947, 3450, 0), new Position(2946, 3468, 0), new Position(2942, 3483, 0), new Position(2942, 3498, 0), new Position(2941, 3508, 0), new Position(2941, 3517, 0)}), new BotRoute(new Position[]{new Position(2940, 3517, 0), new Position(2931, 3515, 0)})};
    final Position aa = new Position(2930, 3515, 0);

    public FaladorWineOfZamorakTelegrabBotTask(int n) {
        super(ab, ac, 0, false, 1);
        this.usesCustomTaskAction = true;
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
    }

    @Override
    public final boolean meetsUnlockRequirements(Player player) {
        return player.getSkillManager().getCurrentLevels()[6] >= 33;
    }

    @Override
    public final ArrayList getRequiredItems(Player object) {
        object = new ArrayList<ItemStack>();
        ((ArrayList)object).add(new ItemStack(1381, 1));
        ((ArrayList)object).add(new ItemStack(563, 500));
        return object;
    }

    @Override
    public final void startCustomTaskAction(Player object) {
        ((Player)object).bm();
        object = new WineOfZamorakTelegrabTickTask(this, 2, (Player)object);
        World.getTaskScheduler().schedule((TickTask)object);
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        player.getEquipmentManager().getContainer().setItem(3, new ItemStack(1381));
        player.getBankContainer().addToTab(new ItemStack(563, 10000), 0);
        ItemStack[] itemStackArray = new ItemStack[]{new ItemStack(563, 30)};
        player.botTaskRequiredItems = itemStackArray;
        player.getInventoryManager().addItem(itemStackArray[0]);
        player.getInventoryManager().refresh();
        player.getEquipmentManager().refresh();
    }

    @Override
    public final void prepareTaskCombatLoadout(Player player) {
        GameplayHelper.b(player);
        int n = 1 + GameUtil.h(99);
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
        BotCombatHelper.setBotSkillLevel(player, 5, n - (n / 5 << 1) + GameUtil.h(n2));
        n2 = n / 5 << 1;
        if (n2 == 0) {
            n2 = 2;
        }
        if ((n = n - n / 5 + GameUtil.h(n2)) < 33) {
            n = 33;
        }
        BotCombatHelper.setBotSkillLevel(player, 6, n);
        player.getSkillManager().getExperience()[3] = BotCombatHelper.calculateBotHitpointsExperience(player);
        int[] nArray = player.getSkillManager().getCurrentLevels();
        player.getSkillManager();
        nArray[3] = SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[3]);
        player.getSkillManager().refreshAllSkills();
    }

    @Override
    public final void startWalkToTask(Player player) {
        player.botTaskState = "walk towards task";
        player.botPathWaypointIndex = 0;
        player.botPathSegmentIndex = 0;
        player.currentBotRoute = ac[player.botPathSegmentIndex];
        player.bk();
    }

    @Override
    public final void startWalkToBank(Player player) {
        player.botTaskState = "walk towards bank";
        player.botPathWaypointIndex = 0;
        player.botPathSegmentIndex = ac.length - 1;
        player.currentBotRoute = ac[player.botPathSegmentIndex].reversed();
        player.bk();
    }

    public final void a(Player player, int n) {
        player.botPathWaypointIndex = n;
        player.currentBotRoute = ac[player.botPathSegmentIndex];
        this.c(player, true);
    }

    public final void b(Player player, int n) {
        player.botPathWaypointIndex = n;
        player.currentBotRoute = ac[player.botPathSegmentIndex].reversed();
        this.c(player, true);
    }

    @Override
    public final void c(Player player, boolean bl) {
        if (player.botTaskState.equals("walk towards task") || player.botTaskState.equals("walk to task") && bl) {
            if (!bl) {
                ++player.botPathSegmentIndex;
            }
            player.currentBotRoute = ac[player.botPathSegmentIndex];
            if (!bl) {
                player.botPathWaypointIndex = 0;
            }
            if (player.botPathSegmentIndex == 1) {
                player.botTaskState = "walk to task";
            }
            player.botTargetNpcId = 1530;
            return;
        }
        if (player.botTaskState.equals("walk towards bank") || player.botTaskState.equals("walk to bank") && bl) {
            if (!bl) {
                --player.botPathSegmentIndex;
            }
            player.currentBotRoute = ac[player.botPathSegmentIndex].reversed();
            if (!bl) {
                player.botPathWaypointIndex = 0;
            }
            if (player.botPathSegmentIndex == 0) {
                player.botTaskState = "walk to bank";
            }
            player.botTargetNpcId = 1530;
        }
    }
}

