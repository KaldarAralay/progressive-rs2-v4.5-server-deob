/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot.tasks;

import com.rs2.ServerSettings;
import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.ItemCombinationHandler;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.skill.mining.MineableRockDefinition;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class DwarvenMineBotTask
extends BotTaskDefinition {
    private static Position aa = new Position(3013, 3355, 0);
    private static BotRoute[] ab = new BotRoute[]{new BotRoute(new Position[]{new Position(3013, 3359, 0), new Position(3026, 3365, 0), new Position(3036, 3368, 0), new Position(3052, 3370, 0), ServerSettings.cacheVersion < 362 ? new Position(3064, 3380, 0) : new Position(3061, 3374, 0)}), new BotRoute(new Position[]{ServerSettings.cacheVersion < 362 ? new Position(3063, 3380, 0) : new Position(3061, 3376, 0)}), new BotRoute(new Position[]{new Position(3051, 9772, 0)})};

    public DwarvenMineBotTask(int n) {
        super(aa, ab, 0, false, 4);
        int n2 = 9757;
        DwarvenMineBotTask dwarvenMineBotTask = this;
        this.targetMinY = n2;
    }

    @Override
    public final boolean meetsUnlockRequirements(Player player) {
        if (player.getSkillManager().getCurrentLevels()[14] < 30) {
            return false;
        }
        return player.getCombatLevel() > 28;
    }

    @Override
    public final ArrayList getRequiredItems(Player player) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        arrayList.add(new ItemStack(ItemCombinationHandler.c(player, 14).a(), 1));
        return arrayList;
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        int n;
        int n2;
        int n3;
        int[] nArray;
        player.botInteractionTargetIds.clear();
        if (player.getSkillManager().getCurrentLevels()[14] >= 70) {
            nArray = MineableRockDefinition.collectObjectIds(new MineableRockDefinition[]{MineableRockDefinition.ADAMANTITE});
            n3 = nArray.length;
            n2 = 0;
            while (n2 < n3) {
                n = nArray[n2];
                player.botInteractionTargetIds.add(n);
                ++n2;
            }
        }
        if (player.getSkillManager().getCurrentLevels()[14] >= 55) {
            nArray = MineableRockDefinition.collectObjectIds(new MineableRockDefinition[]{MineableRockDefinition.MITHRIL});
            n3 = nArray.length;
            n2 = 0;
            while (n2 < n3) {
                n = nArray[n2];
                player.botInteractionTargetIds.add(n);
                ++n2;
            }
        }
        if (player.getSkillManager().getCurrentLevels()[14] >= 40) {
            nArray = MineableRockDefinition.collectObjectIds(new MineableRockDefinition[]{MineableRockDefinition.GOLD});
            n3 = nArray.length;
            n2 = 0;
            while (n2 < n3) {
                n = nArray[n2];
                player.botInteractionTargetIds.add(n);
                ++n2;
            }
        }
        if (player.getSkillManager().getCurrentLevels()[14] >= 30) {
            nArray = MineableRockDefinition.collectObjectIds(new MineableRockDefinition[]{MineableRockDefinition.COAL});
            n3 = nArray.length;
            n2 = 0;
            while (n2 < n3) {
                n = nArray[n2];
                player.botInteractionTargetIds.add(n);
                ++n2;
            }
        }
        nArray = MineableRockDefinition.collectObjectIds(new MineableRockDefinition[]{MineableRockDefinition.IRON});
        n3 = nArray.length;
        n2 = 0;
        while (n2 < n3) {
            n = nArray[n2];
            player.botInteractionTargetIds.add(n);
            ++n2;
        }
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
            player.getEquipmentManager().getContainer().setItem(3, new ItemStack(GameUtil.h(2) == 0 ? 1267 : 1265));
        }
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
        if ((n = n - n / 5 + GameUtil.h(n2)) < 15) {
            n = 15;
        }
        BotCombatHelper.setBotSkillLevel(player, 14, n);
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
        player.currentBotRoute = ab[player.botPathSegmentIndex];
        player.bk();
    }

    @Override
    public final void startWalkToBank(Player player) {
        player.botTaskState = "walk towards bank";
        player.botPathWaypointIndex = 0;
        player.botPathSegmentIndex = ab.length - 1;
        player.currentBotRoute = ab[player.botPathSegmentIndex].reversed();
        player.bk();
    }

    public final void a(Player player, int n) {
        player.botPathWaypointIndex = n;
        player.currentBotRoute = ab[player.botPathSegmentIndex];
        this.c(player, true);
    }

    public final void b(Player player, int n) {
        player.botPathWaypointIndex = n;
        player.currentBotRoute = ab[player.botPathSegmentIndex].reversed();
        this.c(player, true);
    }

    @Override
    public final void c(Player player, boolean n) {
        int n2;
        int n3 = n2 = !GameplayHelper.b(11707) ? 1512 : 11707;
        if (player.botTaskState.equals("walk towards task") || player.botTaskState.equals("walk to task") && n != 0) {
            if (n == 0) {
                ++player.botPathSegmentIndex;
            }
            player.currentBotRoute = ab[player.botPathSegmentIndex];
            if (n == 0) {
                player.botPathWaypointIndex = 0;
            }
            n = GameUtil.a(player.getPosition().getX(), player.getPosition().getY());
            if (player.botPathSegmentIndex == ab.length - 1 && n == 12084) {
                player.botTaskState = "walk to task";
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(1733);
                player.interactWithBotObjectTargets(arrayList);
                player.dm = true;
                return;
            }
            player.botTargetNpcId = n2;
            return;
        }
        if (player.botTaskState.equals("walk towards bank") || player.botTaskState.equals("walk to bank") && n != 0) {
            if (n == 0) {
                --player.botPathSegmentIndex;
            }
            player.currentBotRoute = ab[player.botPathSegmentIndex].reversed();
            if (n == 0) {
                player.botPathWaypointIndex = 0;
            }
            if (player.botPathSegmentIndex == ab.length - 2) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(1734);
                player.interactWithBotObjectTargets(arrayList);
                player.dm = true;
                return;
            }
            player.botTaskState = "walk to bank";
            player.botTargetNpcId = n2;
        }
    }
}

