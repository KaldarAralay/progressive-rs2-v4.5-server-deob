/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot.combat;

import com.rs2.bot.combat.BotCombatEscapeHandler;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.item.consumable.FoodDefinition;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.task.TickTask;

final class BotGroupCombatTickTask
extends TickTask {
    private final /* synthetic */ Player a;

    BotGroupCombatTickTask(int n, Player player) {
        this.a = player;
        super(1);
    }

    @Override
    public final void execute() {
        if (this.a.isDead() || !this.a.bW() || !this.a.botEnabled) {
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
        }
        if (!BotCombatHelper.hasExternalCombatTarget(this.a) && this.a.botCombatState == null && !this.a.botCombatEscapeActive) {
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
            BotCombatHelper.disableBotCombatPrayers(this.a);
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
            FoodDefinition foodDefinition = FoodDefinition.a(this.a.botFoodItemId);
            int n = foodDefinition.a();
            int n2 = this.a.getSkillManager().getCurrentLevels()[3] + n;
            this.a.getSkillManager();
            int n3 = n = n2 <= SkillManager.getLevelForExperience(this.a.getSkillManager().getExperience()[3]) ? 1 : 0;
            if (!this.a.botFoodDepleted && n != 0) {
                int n4 = n = this.a.isTeleblocked() ? 6 : 4;
                if (!BotCombatHelper.eatBotFood(this.a) || this.a.getInventoryManager().getItemAmount(this.a.botFoodItemId) <= n) {
                    BotCombatEscapeHandler.a(this.a);
                }
            }
            if (this.a.getPoisonDamage() > 0.0) {
                BotCombatHelper.drinkAntipoisonPotion(this.a);
            }
            if (this.a.q != null && this.a.q.a != this.a) {
                if (this.a.getMovementTarget() == this.a.q.a && this.a.getMovementQueue().isRunning() != this.a.q.a.getMovementQueue().isRunning()) {
                    this.a.getMovementQueue().setRunning(this.a.q.a.getMovementQueue().isRunning());
                }
                if (this.a.getMovementTarget() == null) {
                    this.a.q.b();
                }
                if (this.a.q.a.getCombatTarget() != null && !this.a.q.a.getCombatTarget().isDead()) {
                    this.a.getMovementQueue().setRunning(true);
                    CombatManager.startCombat(this.a, this.a.q.a.getCombatTarget());
                }
            }
            if (this.a.isInWilderness() && this.a.q == null) {
                BotCombatEscapeHandler.a(this.a);
            }
        }
    }
}

