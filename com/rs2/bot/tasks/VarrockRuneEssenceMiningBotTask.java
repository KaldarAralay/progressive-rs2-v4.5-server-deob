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
import com.rs2.model.skill.ItemCombinationHandler;
import com.rs2.model.skill.SkillManager;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class VarrockRuneEssenceMiningBotTask
extends BotTaskDefinition {
    private static Position aa = new Position(3254, 3420, 0);
    private static BotRoute[] ab = new BotRoute[]{new BotRoute(new Position[]{new Position(3254, 3428, 0), new Position(3260, 3425, 0), new Position(3260, 3414, 0), new Position(3257, 3400, 0), new Position(3253, 3398, 0)}), new BotRoute(new Position[]{new Position(3253, 3399, 0)}), new BotRoute(new Position[]{new Position(2911, 4832, 0)})};

    public VarrockRuneEssenceMiningBotTask(int n) {
        super(aa, ab, 0, false, 6);
        int n2 = 30;
        VarrockRuneEssenceMiningBotTask varrockRuneEssenceMiningBotTask = this;
        this.targetSearchRadius = n2;
    }

    @Override
    public final ArrayList getRequiredItems(Player player) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        arrayList.add(new ItemStack(ItemCombinationHandler.getOwnedOrFallbackGatheringTool(player, 14).getToolItemId(), 1));
        return arrayList;
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        player.botInteractionTargetIds.add(2491);
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        if (player.getSkillManager().getCurrentLevels()[14] >= 41 && player.getSkillManager().getCurrentLevels()[0] >= 40) {
            player.getEquipmentManager().getContainer().setItem(3, new ItemStack(1275));
        } else if (player.getSkillManager().getCurrentLevels()[14] >= 31 && player.getSkillManager().getCurrentLevels()[0] >= 30) {
            player.getEquipmentManager().getContainer().setItem(3, new ItemStack(1271));
        } else if (player.getSkillManager().getCurrentLevels()[14] >= 21 && player.getSkillManager().getCurrentLevels()[0] >= 20) {
            player.getEquipmentManager().getContainer().setItem(3, new ItemStack(1273));
        } else if (player.getSkillManager().getCurrentLevels()[14] >= 6 && player.getSkillManager().getCurrentLevels()[0] >= 5) {
            player.getEquipmentManager().getContainer().setItem(3, new ItemStack(1269));
        } else {
            player.getEquipmentManager().getContainer().setItem(3, new ItemStack(GameUtil.randomInt(2) == 0 ? 1267 : 1265));
        }
        player.getInventoryManager().refresh();
        player.getEquipmentManager().refresh();
    }

    @Override
    public final void prepareTaskCombatLoadout(Player player) {
        GameplayHelper.b(player);
        int n = 1 + GameUtil.randomInt(99);
        int n2 = n / 5 << 1;
        if (n2 == 0) {
            n2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 0, n - n / 5 + GameUtil.randomInt(n2));
        n2 = n / 5 << 1;
        if (n2 == 0) {
            n2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 2, n - n / 5 + GameUtil.randomInt(n2));
        n2 = n / 5 << 1;
        if (n2 == 0) {
            n2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 1, n - n / 5 + GameUtil.randomInt(n2));
        n2 = n / 5 << 1;
        if (n2 == 0) {
            n2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 4, n - n / 5 + GameUtil.randomInt(n2));
        n2 = n / 5 << 1;
        if (n2 == 0) {
            n2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 6, n - n / 5 + GameUtil.randomInt(n2));
        n2 = n / 5 << 1;
        if (n2 == 0) {
            n2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 5, n - (n / 5 << 1) + GameUtil.randomInt(n2));
        n2 = n / 5 << 1;
        if (n2 == 0) {
            n2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 14, n - n / 5 + GameUtil.randomInt(n2));
        player.getSkillManager().getExperience()[3] = BotCombatHelper.calculateBotHitpointsExperience(player);
        int[] nArray = player.getSkillManager().getCurrentLevels();
        player.getSkillManager();
        nArray[3] = SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[3]);
        player.getSkillManager().refreshAllSkills();
    }

    @Override
    public final void startWalkToTask(Player player) {
        player.botInteractionOption = 1;
        player.setAutoRetaliate(true);
        player.botTaskState = "walk towards task";
        player.botPathWaypointIndex = 0;
        player.botPathSegmentIndex = 0;
        player.currentBotRoute = ab[player.botPathSegmentIndex];
        player.botTargetNpcId = 1530;
        player.continueBotRoute();
    }

    @Override
    public final void startWalkToBank(Player player) {
        player.botInteractionOption = 1;
        player.setAutoRetaliate(false);
        player.botTaskState = "walk towards bank";
        player.botPathWaypointIndex = 0;
        player.botPathSegmentIndex = ab.length - 2;
        player.currentBotRoute = ab[player.botPathSegmentIndex].reversed();
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(2492);
        player.interactWithBotObjectTargets(arrayList);
        player.dm = true;
        player.botTargetNpcId = 1530;
        player.continueBotRoute();
    }

    @Override
    public final void continueWalkToTask(Player player, int n) {
        player.botInteractionOption = 1;
        player.setAutoRetaliate(true);
        player.botPathWaypointIndex = n;
        player.currentBotRoute = ab[player.botPathSegmentIndex];
        player.botTargetNpcId = 1530;
        this.advanceTaskRouteSegment(player, true);
    }

    @Override
    public final void continueWalkToBank(Player player, int n) {
        player.botInteractionOption = 1;
        player.setAutoRetaliate(false);
        player.botPathWaypointIndex = n;
        player.currentBotRoute = ab[player.botPathSegmentIndex].reversed();
        n = GameUtil.getRegionId(player.getPosition().getX(), player.getPosition().getY());
        if (player.botPathSegmentIndex == ab.length - 2 && n == 11595) {
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            arrayList.add(2492);
            player.interactWithBotObjectTargets(arrayList);
            player.dm = true;
        }
        player.botTargetNpcId = 1530;
        this.advanceTaskRouteSegment(player, true);
    }

    @Override
    public final void advanceTaskRouteSegment(Player player, boolean n) {
        player.botInteractionOption = 1;
        if (player.botTaskState.equals("walk towards task") || player.botTaskState.equals("walk to task") && n != 0) {
            if (n == 0) {
                ++player.botPathSegmentIndex;
            }
            player.currentBotRoute = ab[player.botPathSegmentIndex];
            if (n == 0) {
                player.botPathWaypointIndex = 0;
            }
            player.botTargetNpcId = 1530;
            n = GameUtil.getRegionId(player.getPosition().getX(), player.getPosition().getY());
            if (player.botPathSegmentIndex == ab.length - 1 && player.botTaskState.equals("walk towards task") || player.botTaskState.equals("walk to task") && n != 11595) {
                player.botTaskState = "walk to task";
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(553);
                player.botInteractionOption = 3;
                player.interactWithBotNpcTargets(arrayList);
                player.dm = true;
                player.botInteractionOption = 1;
                return;
            }
        } else if (player.botTaskState.equals("walk towards bank") || player.botTaskState.equals("walk to bank") && n != 0) {
            if (n == 0) {
                --player.botPathSegmentIndex;
            }
            player.currentBotRoute = ab[player.botPathSegmentIndex].reversed();
            if (n == 0) {
                player.botPathWaypointIndex = 0;
            }
            player.botTargetNpcId = 1530;
            if (player.botPathSegmentIndex == ab.length - 1 && player.botTaskState.equals("walk towards bank")) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(2492);
                player.interactWithBotObjectTargets(arrayList);
                player.dm = true;
            }
            if (player.botPathSegmentIndex == 0) {
                player.botTaskState = "walk to bank";
            }
        }
    }
}

