/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot.combat;

import com.rs2.bot.combat.BotCombatEscapeHandler;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.bot.combat.BotCombatLoadoutManager;
import com.rs2.model.Entity;
import com.rs2.model.EntityTargetMovement;
import com.rs2.model.World;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.consumable.FoodDefinition;
import com.rs2.model.path.PathReachability;
import com.rs2.model.player.Player;
import com.rs2.model.player.PlayerGroup;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import com.rs2.util.path.PathFinder;
import java.util.ArrayList;
import java.util.Collections;

final class BotPvpTargetSearchTickTask
extends TickTask {
    private final /* synthetic */ Player a;
    private final /* synthetic */ boolean b;

    BotPvpTargetSearchTickTask(int n, Player player, boolean bl) {
        this.a = player;
        this.b = bl;
        super(10);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public final void execute() {
        block74: {
            void var3_12;
            int n;
            int n2;
            Object object;
            if (this.a.isDead() || !this.a.bW()) {
                this.stop();
                return;
            }
            if (this.a.botCombatEscapeActive) {
                if (this.a.getPosition().equals(this.a.botEscapeLastPosition) && this.a.getRecentCombatTimer().hasElapsed()) {
                    ++this.a.botEscapeStuckTicks;
                } else {
                    this.a.botEscapeLastPosition = this.a.getPosition().copy();
                    this.a.botEscapeStuckTicks = 0;
                }
                if (this.a.botEscapeStuckTicks >= 10) {
                    BotCombatLoadoutManager.a(this.a);
                }
            }
            if (BotCombatHelper.hasExternalCombatTarget(this.a) || this.a.botCombatState != null || this.a.botCombatEscapeActive) break block74;
            if (!this.a.isInWilderness()) {
                BotCombatHelper.prepareBotPvpSearchPosition(this.a);
            }
            if (this.a.getEquipmentManager().getItemIdAtSlot(3) != this.a.botWeaponItemId) {
                this.a.getEquipmentManager().equipFromInventorySlot(this.a.getInventoryManager().getContainer().indexOfItem(this.a.botWeaponItemId));
                if (this.a.botShieldItemId != 0 && this.a.getEquipmentManager().getItemIdAtSlot(5) != this.a.botShieldItemId) {
                    this.a.getEquipmentManager().equipFromInventorySlot(this.a.getInventoryManager().getContainer().indexOfItem(this.a.botShieldItemId));
                }
                this.a.botActiveCombatStyle = this.a.botPrimaryCombatStyle;
            }
            if (this.a.botMagicPenaltyGearUnequipped) {
                BotCombatHelper.reequipMagicPenaltyGear(this.a);
            }
            if (this.a.getMovementQueue().isRunning()) {
                if (this.a.q == null) {
                    this.a.getMovementQueue().setRunning(false);
                } else if (this.a.q.a == this.a) {
                    this.a.getMovementQueue().setRunning(false);
                }
            }
            BotCombatHelper.disableBotCombatPrayers(this.a);
            Object object2 = this.a;
            if (((Player)object2).botPvpChatSource != null && ((Player)object2).botPvpChatMessage != null) {
                if (((Player)object2).q == null && ((Player)object2).botPvpPendingTeamTarget != null && ((Player)object2).botPvpPendingTeamTarget == ((Player)object2).botPvpChatSource) {
                    if (((Player)object2).botPvpChatMessage.toLowerCase().equals("sure") || ((Player)object2).botPvpChatMessage.toLowerCase().equals("okay") || ((Player)object2).botPvpChatMessage.toLowerCase().equals("ok") || ((Player)object2).botPvpChatMessage.toLowerCase().equals("k")) {
                        ((Player)object2).botPvpPendingTeamTarget = null;
                        ((Player)object2).botPvpTeamInviteTicks = 0;
                        if (((Player)object2).botPvpChatSource.q == null) {
                            object = new PlayerGroup(((Player)object2).botPvpChatSource, (Player)object2);
                            ((PlayerGroup)object).b();
                        } else {
                            ((Player)object2).botPvpChatSource.q.c((Player)object2);
                            ((Player)object2).botPvpChatSource.q.b();
                        }
                    } else if (((Player)object2).botPvpChatMessage.toLowerCase().equals("nty") || ((Player)object2).botPvpChatMessage.toLowerCase().equals("nah") || ((Player)object2).botPvpChatMessage.toLowerCase().equals("no") || ((Player)object2).botPvpChatMessage.toLowerCase().equals("nope") || ((Player)object2).botPvpChatMessage.toLowerCase().equals("no thanks")) {
                        ((Player)object2).botPvpPendingTeamTarget.botPvpTeamRequesters.remove(object2);
                        EntityTargetMovement.clearMovementTarget((Entity)object2);
                        ((Player)object2).botPvpRejectedTeamTargets.add(((Player)object2).botPvpPendingTeamTarget);
                        ((Player)object2).botPvpPendingTeamTarget = null;
                        ((Player)object2).botPvpTeamInviteTicks = 0;
                    }
                }
                if (((Player)object2).botPvpChatMessage.toLowerCase().equals("team") || ((Player)object2).botPvpChatMessage.toLowerCase().equals("team?")) {
                    if (((Player)object2).q == null && BotCombatHelper.canTeamWithBotPvpPlayer((Player)object2, ((Player)object2).botPvpChatSource, false)) {
                        object = new String[]{"sure", "okay", "ok", "k"};
                        ((Player)object2).queuePublicChatMessage(object[GameUtil.h(4)]);
                        ((Player)object2).botPvpChatSource.botPvpTeamRequesters.add(object2);
                        if (((Player)object2).botPvpChatSource.q == null) {
                            PlayerGroup playerGroup = new PlayerGroup(((Player)object2).botPvpChatSource, (Player)object2);
                            playerGroup.b();
                        } else {
                            ((Player)object2).botPvpChatSource.q.c((Player)object2);
                            ((Player)object2).botPvpChatSource.q.b();
                        }
                    } else {
                        ((Player)object2).queuePublicChatMessage("nty");
                    }
                } else if (((Player)object2).q != null && ((Player)object2).botPvpChatSource.q != null && ((Player)object2).q == ((Player)object2).botPvpChatSource.q) {
                    if (((Player)object2).botPvpChatMessage.toLowerCase().equals("food") || ((Player)object2).botPvpChatMessage.toLowerCase().equals("food?") || ((Player)object2).botPvpChatMessage.toLowerCase().equals("food left") || ((Player)object2).botPvpChatMessage.toLowerCase().equals("food left?")) {
                        void n12;
                        object = ItemDefinition.forId(((Player)object2).botFoodItemId);
                        String string = ((ItemDefinition)object).getDisplayName();
                        n2 = ((Player)object2).getInventoryManager().getItemAmount(((Player)object2).botFoodItemId);
                        if (n2 > 1) {
                            String n7 = String.valueOf(string) + "s";
                        }
                        ((Player)object2).queuePublicChatMessage("I have " + n2 + " " + (String)n12 + " left");
                    } else if (((Player)object2).botPvpChatMessage.toLowerCase().equals("skills") || ((Player)object2).botPvpChatMessage.toLowerCase().equals("skills?") || ((Player)object2).botPvpChatMessage.toLowerCase().equals("lvls") || ((Player)object2).botPvpChatMessage.toLowerCase().equals("lvls?") || ((Player)object2).botPvpChatMessage.toLowerCase().equals("stats") || ((Player)object2).botPvpChatMessage.toLowerCase().equals("stats?")) {
                        int n3;
                        int n4;
                        int n5;
                        int n6;
                        object = "";
                        int player = ((Player)object2).getSkillManager().getBaseLevel(0);
                        if (player > 1) {
                            object = String.valueOf(object) + "atk: " + player + " ";
                        }
                        if ((n2 = ((Player)object2).getSkillManager().getBaseLevel(1)) > 1) {
                            object = String.valueOf(object) + "def: " + n2 + " ";
                        }
                        if ((n6 = ((Player)object2).getSkillManager().getBaseLevel(2)) > 1) {
                            object = String.valueOf(object) + "str: " + n6 + " ";
                        }
                        if ((n = ((Player)object2).getSkillManager().getBaseLevel(6)) > 1) {
                            object = String.valueOf(object) + "mage: " + n + " ";
                        }
                        if ((n5 = ((Player)object2).getSkillManager().getBaseLevel(4)) > 1) {
                            object = String.valueOf(object) + "range: " + n5 + " ";
                        }
                        if ((n4 = ((Player)object2).getSkillManager().getBaseLevel(3)) > 10) {
                            object = String.valueOf(object) + "hp: " + n4 + " ";
                        }
                        if ((n3 = ((Player)object2).getSkillManager().getBaseLevel(5)) > 1) {
                            object = String.valueOf(object) + "pray: " + n3 + " ";
                        }
                        if (player == 1 && n2 == 1 && n6 == 1 && n == 1 && n5 == 1 && n4 == 10 && n3 == 1) {
                            object = "lol, i havent trained my skills at all";
                        }
                        ((Player)object2).queuePublicChatMessage((String)object);
                    } else if (((Player)object2).botPvpChatMessage.toLowerCase().equals("leave") || ((Player)object2).botPvpChatMessage.toLowerCase().equals("g2g") || ((Player)object2).botPvpChatMessage.toLowerCase().equals("bye") || ((Player)object2).botPvpChatMessage.toLowerCase().equals("cya")) {
                        ((Player)object2).queuePublicChatMessage("cya");
                        EntityTargetMovement.clearMovementTarget((Entity)object2);
                        ((Player)object2).botPvpRejectedTeamTargets.add(((Player)object2).botPvpChatSource);
                        ((Player)object2).q.e(((Player)object2).botPvpChatSource);
                    }
                }
                ((Player)object2).botPvpChatMessage = null;
                ((Player)object2).botPvpChatSource = null;
            }
            this.a.botMagicGearSwapDelayTicks = 0;
            this.a.botThreatEscapeDelayTicks = 0;
            this.a.botPrayerSwitchDelayTicks = 0;
            this.a.botQueuedPrayerId = -1;
            this.a.botEatDelayTicks = 0;
            this.a.botWeaponSwapDelayTicks = 0;
            if (!(this.a.getCombatTarget() == null || this.a.getCombatTarget().isDead() || this.a.botCombatTickTask != null && this.a.botCombatTickTask.isActive())) {
                this.a.getMovementQueue().setRunning(true);
                CombatManager.startCombat(this.a, this.a.getCombatTarget());
            }
            object2 = FoodDefinition.a(this.a.botFoodItemId);
            int n8 = ((FoodDefinition)((Object)object2)).a();
            int n7 = this.a.getSkillManager().getCurrentLevels()[3] + n8;
            this.a.getSkillManager();
            int n9 = n8 = n7 <= SkillManager.getLevelForExperience(this.a.getSkillManager().getExperience()[3]) ? 1 : 0;
            if (!this.a.botFoodDepleted && n8 != 0) {
                int n10 = n8 = this.a.isTeleblocked() ? 6 : 4;
                if (!BotCombatHelper.eatBotFood(this.a) || this.a.getInventoryManager().getItemAmount(this.a.botFoodItemId) <= n8) {
                    BotCombatEscapeHandler.a(this.a);
                }
            }
            if (this.a.getPoisonDamage() > 0.0) {
                BotCombatHelper.drinkAntipoisonPotion(this.a);
            }
            if (this.a.botPvpTeamInviteTicks >= 3) {
                this.a.botPvpPendingTeamTarget.botPvpTeamRequesters.remove(this.a);
                EntityTargetMovement.clearMovementTarget(this.a);
                this.a.botPvpRejectedTeamTargets.add(this.a.botPvpPendingTeamTarget);
                this.a.botPvpPendingTeamTarget = null;
                this.a.botPvpTeamInviteTicks = 0;
            }
            if (this.a.botPvpTeamInviteTicks > 0) {
                ++this.a.botPvpTeamInviteTicks;
                return;
            }
            if (this.a.q != null && this.a.q.a != this.a) {
                if (!this.a.q.a.isInWilderness()) {
                    this.a.q.e(this.a.q.a);
                } else {
                    if (this.a.getMovementTarget() == null) {
                        this.a.q.b();
                    } else if (this.a.getMovementQueue().isRunning() != this.a.q.a.getMovementQueue().isRunning()) {
                        this.a.getMovementQueue().setRunning(this.a.q.a.getMovementQueue().isRunning());
                    }
                    if (this.a.q.a.getCombatTarget() != null && !this.a.q.a.getCombatTarget().isDead()) {
                        this.a.getMovementQueue().setRunning(true);
                        CombatManager.startCombat(this.a, this.a.q.a.getCombatTarget());
                    }
                }
            }
            if (this.a.q != null && this.a.q.a != this.a) {
                return;
            }
            ArrayList<Object> arrayList = new ArrayList<Object>();
            Object object3 = World.a;
            n2 = World.a.length;
            boolean n13 = false;
            while (var3_12 < n2) {
                block75: {
                    block76: {
                        block77: {
                            object = object3[var3_12];
                            if (object == null || object == this.a || ((Player)object).clanWarsBot) break block75;
                            if (this.a.q == null) break block76;
                            if (!this.a.q.a((Player)object)) break block77;
                            if (!((Entity)object).getPosition().isWithinViewport(this.a.getPosition())) {
                                this.a.q.e((Player)object);
                            }
                            break block75;
                        }
                        if (this.a.q.c.contains(object)) break block75;
                    }
                    if (((Entity)object).getPosition().isWithinViewport(this.a.getPosition())) {
                        n = BotCombatHelper.getEscapeCombatLevelMargin(this.a);
                        if (((Player)object).getCombatLevel() <= this.a.getCombatLevel() + n) {
                            arrayList.add(object);
                        }
                    }
                }
                ++var3_12;
            }
            Collections.shuffle(arrayList);
            boolean bl = false;
            for (Player player : arrayList) {
                if (player.getWildernessLevel() < Math.abs(this.a.getCombatLevel() - player.getCombatLevel()) || !player.isInMultiCombatArea() && !player.getSingleCombatTimer().hasElapsed()) continue;
                if (!PathReachability.isReachable(this.a, player.getPosition().getX(), player.getPosition().getY(), true, 0, 0) || BotCombatHelper.tryHandleBotPvpTeamGrouping(this.a, player)) continue;
                object3 = player;
                if (player.q != null) {
                    object3 = player.q.a;
                }
                if (!object3.de && BotCombatHelper.canTeamWithBotPvpPlayer(this.a, (Player)object3, true) && !this.a.botPvpRejectedTeamTargets.contains(object3)) {
                    this.a.getMovementQueue().setRunning(true);
                    this.a.getUpdateState().setFaceEntity(player.getEncodedIndex());
                    this.a.setAttackRange(1);
                    this.a.setMovementTarget(player);
                    this.a.botPvpPendingTeamTarget = object3;
                    if (GameUtil.b(this.a.getPosition(), player.getPosition()) > 5) continue;
                    this.a.queuePublicChatMessage("team?");
                    object3.botPvpTeamRequesters.add(this.a);
                    this.a.botPvpTeamInviteTicks = 1;
                    continue;
                }
                if (!this.b && !BotCombatHelper.isTargetLootWorthRisk(this.a, player)) continue;
                if (!this.a.getMovementQueue().isRunning()) {
                    this.a.getMovementQueue().setRunning(true);
                }
                if (this.a.q == null && player.q != null && (this.a.isInMultiCombatArea() || player.isInMultiCombatArea()) && ((double)player.q.c() >= (double)this.a.getCombatLevel() * 0.7 || (double)player.q.d() >= (double)this.a.getCombatLevel() * 1.2)) continue;
                if (player == null || player.isDead()) break;
                if (this.a.q == null) {
                    CombatManager.startCombat(this.a, player);
                } else {
                    this.a.q.a((Entity)player);
                }
                bl = true;
                break;
            }
            if (!bl) {
                int n11;
                int n12;
                int n14 = this.a.getPosition().getX() + GameUtil.h(20) - 10;
                int n15 = this.a.getPosition().getY() + GameUtil.h(20) - 10;
                if (n14 < 2951) {
                    n12 = 2961;
                }
                if (n12 > 3376) {
                    n11 = 3366;
                }
                if (n15 < 3520) {
                    n15 = 3530;
                }
                if (n15 > this.a.botWildernessMaxY) {
                    n15 = this.a.botWildernessMaxY - 10;
                }
                PathFinder.getInstance();
                PathFinder.findPath(this.a, n11, n15, true, 0, 0);
            }
        }
    }
}

