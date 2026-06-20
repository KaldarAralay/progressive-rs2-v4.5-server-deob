/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot.combat;

import com.rs2.bot.combat.BotCombatEscapeLogoutTask;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.item.consumable.FoodDefinition;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.skill.magic.Spellbook;
import com.rs2.util.GameUtil;
import com.rs2.util.RectangularArea;
import com.rs2.util.path.PathFinder;
import com.rs2.util.path.WalkingCollisionMap;
import java.util.ArrayList;

public final class BotCombatEscapeHandler {
    private static RectangularArea[] a = new RectangularArea[]{new RectangularArea(3218, 3640, 3233, 3613), new RectangularArea(3220, 3613, 3256, 3607), new RectangularArea(3253, 3607, 3222, 3604), new RectangularArea(3251, 3603, 3225, 3600), new RectangularArea(3251, 3600, 3230, 3596), new RectangularArea(3249, 3597, 3232, 3594), new RectangularArea(3245, 3594, 3250, 3592), new RectangularArea(3233, 3611, 3257, 3647), new RectangularArea(3257, 3619, 3259, 3655), new RectangularArea(3259, 3623, 3261, 3655), new RectangularArea(3260, 3626, 3267, 3659), new RectangularArea(3267, 3627, 3269, 3665), new RectangularArea(3269, 3629, 3279, 3667), new RectangularArea(3279, 3670, 3288, 3664), new RectangularArea(3290, 3665, 3279, 3660), new RectangularArea(3278, 3660, 3288, 3657), new RectangularArea(3279, 3657, 3282, 3647), new RectangularArea(3286, 3657, 3281, 3656), new RectangularArea(3285, 3656, 3281, 3654), new RectangularArea(3282, 3654, 3283, 3650)};
    private static Position[] b = new Position[]{new Position(3236, 3635, 0), new Position(3219, 3628, 0), new Position(3213, 3617, 0)};
    private static RectangularArea[] c = new RectangularArea[]{new RectangularArea(3249, 3903, 3190, 3856), new RectangularArea(3214, 3857, 3236, 3853), new RectangularArea(3219, 3853, 3231, 3850)};
    private static Position[] d = new Position[]{new Position(3203, 3867, 0), new Position(3174, 3868, 0), new Position(3150, 3865, 0)};
    private static RectangularArea[] e = new RectangularArea[]{new RectangularArea(3159, 3867, 3189, 3864), new RectangularArea(3161, 3864, 3185, 3862), new RectangularArea(3165, 3862, 3182, 3860), new RectangularArea(3168, 3860, 3179, 3857), new RectangularArea(3176, 3855, 3172, 3857)};
    private static Position[] f = new Position[]{new Position(3173, 3867, 0), new Position(3152, 3867, 0), new Position(3152, 3861, 0)};
    private static RectangularArea[] g = new RectangularArea[]{new RectangularArea(3186, 3749, 3139, 3761)};
    private static Position[] h = new Position[]{new Position(3191, 3751, 0)};
    private static RectangularArea[] i = new RectangularArea[]{new RectangularArea(3006, 3877, 3074, 3902), new RectangularArea(3006, 3871, 3061, 3880), new RectangularArea(3006, 3867, 3051, 3873), new RectangularArea(3006, 3864, 3053, 3869), new RectangularArea(3006, 3859, 3043, 3866), new RectangularArea(3006, 3856, 3029, 3861)};
    private static Position[] j = new Position[]{new Position(3042, 3880, 0), new Position(3027, 3869, 0), new Position(3012, 3863, 0), new Position(3005, 3849, 0)};

    public static boolean a(Player player) {
        if (player.currentBotTask != null && !player.isInWilderness()) {
            player.currentBotTask.startWalkToBank(player);
            return true;
        }
        if (player.botCombatEscapeActive) {
            return false;
        }
        player.botCombatEscapeActive = true;
        if (player.q != null) {
            player.q.c.add(player);
            player.r = player.q;
            player.q.e(player);
        }
        CombatManager.stopCombat(player);
        if (player.botCombatTickTask != null && player.botCombatTickTask.isActive()) {
            player.botCombatTickTask.stop();
        }
        if (player.getEquipmentManager().getItemIdAtSlot(3) != player.botWeaponItemId) {
            player.getEquipmentManager().equipFromInventorySlot(player.getInventoryManager().getContainer().indexOfItem(player.botWeaponItemId));
            if (player.botShieldItemId != 0 && player.getEquipmentManager().getItemIdAtSlot(5) != player.botShieldItemId) {
                player.getEquipmentManager().equipFromInventorySlot(player.getInventoryManager().getContainer().indexOfItem(player.botShieldItemId));
            }
            player.botActiveCombatStyle = player.botPrimaryCombatStyle;
        }
        player.botCombatState = "escape";
        if (player.getEquipmentManager().getItemIdAtSlot(2) == 1712 && !player.isTeleblocked()) {
            BotCombatHelper.operateGloryTeleport(player);
            return true;
        }
        if (player.getSpellbook() == Spellbook.MODERN) {
            if (SpellDefinition.VARROCK_TELEPORT.getRequiredLevel() <= player.getSkillManager().getCurrentLevels()[6] && !player.isTeleblocked() && BotCombatHelper.hasRunesForSpell(player, SpellDefinition.VARROCK_TELEPORT)) {
                MagicSpellAction.castSelfSpell(player, SpellDefinition.VARROCK_TELEPORT);
                return true;
            }
            BotCombatEscapeHandler.c(player);
            return false;
        }
        if (player.getSpellbook() == Spellbook.ANCIENT) {
            if (SpellDefinition.PADDEWWA_TELEPORT.getRequiredLevel() <= player.getSkillManager().getCurrentLevels()[6] && !player.isTeleblocked() && BotCombatHelper.hasRunesForSpell(player, SpellDefinition.PADDEWWA_TELEPORT)) {
                MagicSpellAction.castSelfSpell(player, SpellDefinition.PADDEWWA_TELEPORT);
                return true;
            }
            BotCombatEscapeHandler.c(player);
            return false;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    static void b(Player arrayList) {
        boolean bl;
        boolean bl2;
        boolean bl3;
        boolean bl4;
        boolean bl5;
        boolean bl6;
        Object object;
        if (((Player)((Object)arrayList)).botFoodItemId != -1 && (object = FoodDefinition.a(((Player)((Object)arrayList)).botFoodItemId)) != null) {
            int n = ((FoodDefinition)((Object)object)).a();
            int n2 = ((Player)((Object)arrayList)).getSkillManager().getCurrentLevels()[3] + n;
            ((Player)((Object)arrayList)).getSkillManager();
            if (n2 <= SkillManager.getLevelForExperience(((Player)((Object)arrayList)).getSkillManager().getExperience()[3]) && !((Player)((Object)arrayList)).botFoodDepleted) {
                if (((Player)((Object)arrayList)).botEatDelayTicks < 3) {
                    ++((Player)((Object)arrayList)).botEatDelayTicks;
                } else {
                    BotCombatHelper.eatBotFood(arrayList);
                    ((Player)((Object)arrayList)).botEatDelayTicks = 0;
                }
            }
        }
        if (((Entity)((Object)arrayList)).getPoisonDamage() > 0.0) {
            BotCombatHelper.drinkAntipoisonPotion(arrayList);
        }
        if (!((Entity)((Object)arrayList)).getRecentCombatTimer().hasElapsed()) {
            if (((Player)((Object)arrayList)).getSkillManager().getCurrentLevels()[5] > 1 && !((Player)((Object)arrayList)).cfr_renamed_0()[8]) {
                ((Player)((Object)arrayList)).getSkillManager();
                if (SkillManager.getLevelForExperience(((Player)((Object)arrayList)).getSkillManager().getExperience()[5]) >= 25) {
                    ((Player)((Object)arrayList)).getPrayerManager().togglePrayer(8);
                }
            }
            if (BotCombatHelper.hasPrayerLevelForCombatStyle(arrayList, ((Player)((Object)arrayList)).botOpponentCombatStyle)) {
                BotCombatHelper.toggleProtectionPrayerForOpponentStyle(arrayList);
            }
        } else {
            ((Player)((Object)arrayList)).getPrayerManager().deactivateAll();
        }
        if (((Entity)((Object)arrayList)).getPosition().getY() < 3760 && ((Player)((Object)arrayList)).botCombatState != null && ((Player)((Object)arrayList)).botCombatState.equals("tele") && ((Player)((Object)arrayList)).getEquipmentManager().getItemIdAtSlot(2) == 1712 && !((Entity)((Object)arrayList)).isTeleblocked()) {
            BotCombatHelper.operateGloryTeleport(arrayList);
        }
        if (((Entity)((Object)arrayList)).getPosition().getY() < 3680 && ((Player)((Object)arrayList)).botCombatState != null && ((Player)((Object)arrayList)).botCombatState.equals("tele") && !((Entity)((Object)arrayList)).isTeleblocked()) {
            if (((Player)((Object)arrayList)).getSpellbook() == Spellbook.MODERN) {
                MagicSpellAction.castSelfSpell(arrayList, SpellDefinition.VARROCK_TELEPORT);
            } else if (((Player)((Object)arrayList)).getSpellbook() == Spellbook.ANCIENT) {
                MagicSpellAction.castSelfSpell(arrayList, SpellDefinition.PADDEWWA_TELEPORT);
            }
        }
        if (BotCombatHelper.isPlayerInAnyArea(object = arrayList, a) || ((Player)object).botEscapeRouteName.equals("chaos temple")) {
            if (!((Player)object).botEscapeRouteName.equals("chaos temple")) {
                ((Player)object).botEscapeRouteName = "chaos temple";
                ((Player)object).botPathWaypointIndex = 0;
            }
            bl6 = true;
        } else {
            bl6 = false;
        }
        if (bl6) {
            object = arrayList;
            BotCombatHelper.advanceBotEscapeWaypoints(object, b);
            return;
        }
        object = arrayList;
        if (BotCombatHelper.isPlayerInAnyArea(object, i) || ((Player)object).botEscapeRouteName.equals("rune rocks")) {
            if (!((Player)object).botEscapeRouteName.equals("rune rocks")) {
                ((Player)object).botEscapeRouteName = "rune rocks";
                ((Player)object).botPathWaypointIndex = 0;
            }
            bl5 = true;
        } else {
            bl5 = false;
        }
        if (bl5) {
            object = arrayList;
            BotCombatHelper.advanceBotEscapeWaypoints(object, j);
            return;
        }
        object = arrayList;
        if (BotCombatHelper.isPlayerInAnyArea(object, c) || ((Player)object).botEscapeRouteName.equals("escape south of p2p gates")) {
            if (!((Player)object).botEscapeRouteName.equals("escape south of p2p gates")) {
                ((Player)object).botEscapeRouteName = "escape south of p2p gates";
                ((Player)object).botPathWaypointIndex = 0;
            }
            bl4 = true;
        } else {
            bl4 = false;
        }
        if (bl4) {
            object = arrayList;
            BotCombatHelper.advanceBotEscapeWaypoints(object, d);
            return;
        }
        object = arrayList;
        if (BotCombatHelper.isPlayerInAnyArea(object, e) || ((Player)object).botEscapeRouteName.equals("escape south west of p2p gates")) {
            if (!((Player)object).botEscapeRouteName.equals("escape south west of p2p gates")) {
                ((Player)object).botEscapeRouteName = "escape south west of p2p gates";
                ((Player)object).botPathWaypointIndex = 0;
            }
            bl3 = true;
        } else {
            bl3 = false;
        }
        if (bl3) {
            object = arrayList;
            BotCombatHelper.advanceBotEscapeWaypoints(object, f);
            return;
        }
        object = arrayList;
        if (BotCombatHelper.isPlayerInAnyArea(object, g) || ((Player)object).botEscapeRouteName.equals("escape north of ruins")) {
            if (!((Player)object).botEscapeRouteName.equals("escape north of ruins")) {
                ((Player)object).botEscapeRouteName = "escape north of ruins";
                ((Player)object).botPathWaypointIndex = 0;
            }
            bl2 = true;
        } else {
            bl2 = false;
        }
        if (bl2) {
            object = arrayList;
            BotCombatHelper.advanceBotEscapeWaypoints(object, h);
            return;
        }
        object = arrayList;
        if (((Entity)object).getPosition().getY() >= 3904 || ((Player)object).botEscapeRouteName.equals("escape p2p gates")) {
            if (!((Player)object).botEscapeRouteName.equals("escape p2p gates")) {
                ((Player)object).botEscapeRouteName = "escape p2p gates";
                ((Player)object).botPathWaypointIndex = 0;
            }
            bl = true;
        } else {
            bl = false;
        }
        if (!bl) {
            if (!((Player)((Object)arrayList)).botEscapeRouteName.equals("")) return;
            BotCombatEscapeHandler.d(arrayList);
            ((Player)((Object)arrayList)).botPathWaypointIndex = -1;
            return;
        }
        object = arrayList;
        arrayList = new Position(3224, 3904);
        if (GameUtil.a(((Entity)object).getPosition(), (Position)((Object)arrayList), 1)) {
            arrayList = new ArrayList<Integer>();
            arrayList.add(1597);
            arrayList.add(1596);
            ((Player)object).interactWithBotObjectTargets(arrayList);
            return;
        }
        BotCombatHelper.walkBotTowardPosition((Player)object, new Position(3224, 3904));
    }

    private static void d(Player player) {
        int n = player.getPosition().getX();
        int n2 = player.getPosition().getY() - 20;
        if (n2 <= 3519) {
            n2 = 3519;
        }
        boolean bl = false;
        int n3 = 0;
        if (WalkingCollisionMap.getTileFlags(n, n2, 0) != 0) {
            bl = true;
        }
        while (bl && n3 < 20) {
            n = player.getPosition().getX() + GameUtil.h(20) - 10;
            bl = WalkingCollisionMap.getTileFlags(n, n2 += n3, 0) != 0;
            ++n3;
        }
        if (!player.isMovementLocked()) {
            PathFinder.getInstance();
            PathFinder.findPath(player, n, n2, true, 0, 0);
        }
    }

    public static boolean c(Player player) {
        player.getMovementQueue().setRunning(true);
        player.setAutoRetaliate(false);
        if (!player.getRecentCombatTimer().hasElapsed()) {
            if (player.getSkillManager().getCurrentLevels()[5] > 1 && !player.cfr_renamed_0()[8]) {
                player.getSkillManager();
                if (SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[5]) >= 25) {
                    player.getPrayerManager().togglePrayer(8);
                }
            }
            if (BotCombatHelper.hasPrayerLevelForCombatStyle(player, player.botOpponentCombatStyle)) {
                BotCombatHelper.toggleProtectionPrayerForOpponentStyle(player);
            }
        } else {
            player.getPrayerManager().deactivateAll();
        }
        BotCombatEscapeHandler.b(player);
        player.botEscapeLogoutTask = new BotCombatEscapeLogoutTask(2, player);
        World.getTaskScheduler().schedule(player.botEscapeLogoutTask);
        return true;
    }
}

