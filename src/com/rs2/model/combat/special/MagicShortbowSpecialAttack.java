/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat.special;

import com.rs2.model.Entity;
import com.rs2.model.World;
import com.rs2.model.c.ProjectileDefinition;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.combat.requirement.CombatRequirement;
import com.rs2.model.combat.special.MagicShortbowArrowRequirement;
import com.rs2.model.combat.special.MagicShortbowDelayedGraphicTask;
import com.rs2.model.combat.special.MagicShortbowSpecialDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEventContainer;

final class MagicShortbowSpecialAttack
extends WeaponCombatAttack {
    private final /* synthetic */ WeaponProfile sourceWeaponProfile;
    private final /* synthetic */ Player player;

    MagicShortbowSpecialAttack(MagicShortbowSpecialDefinition magicShortbowSpecialDefinition, Player player, Entity entity, WeaponProfile weaponProfile, WeaponProfile weaponProfile2, Player player2) {
        this.sourceWeaponProfile = weaponProfile2;
        this.player = player2;
        super(player, entity, weaponProfile);
    }

    @Override
    public final boolean prepareSpecialAttack() {
        if (!super.prepareSpecialAttack()) {
            return false;
        }
        Object object = this.getAmmunition();
        Object object2 = this.sourceWeaponProfile.getAmmunitionProfile();
        if (object == null || object2 == null) {
            return false;
        }
        object = this.player.isPlayer() ? this.player.getEquipmentManager().getContainer().getItemAt(object2.getEquipmentSlot()) : null;
        if (object == null) {
            return false;
        }
        this.setRequirements(new CombatRequirement[]{new MagicShortbowArrowRequirement(this, object2.getEquipmentSlot(), ((ItemStack)object).getId(), 2, true)});
        double d = this.calculateMaxHit();
        this.setAnimationId(1074);
        this.setAttackSoundId(386);
        this.setAttackerGraphic(null);
        object = object2.getProjectileTiming();
        object2 = new ProjectileDefinition(249, ((ProjectileTiming)object).copy().setStartDelay(20).setSpeed(3));
        object = new ProjectileDefinition(249, ((ProjectileTiming)object).copy().setStartDelay(50).setSpeed(2));
        this.setHitDefinitions(new HitDefinition[]{new HitDefinition(this.getAttackStyle(), HitType.NORMAL, d).enableRandomDamage().setAccuracyMultiplier(1.1).setProjectile((ProjectileDefinition)object2).setDelay(1).enableAccuracyCheck(true), new HitDefinition(this.getAttackStyle(), HitType.NORMAL, d).enableRandomDamage().setAccuracyMultiplier(1.1).setProjectile((ProjectileDefinition)object).setDelay(0).enableAccuracyCheck(true)});
        return true;
    }

    @Override
    public final int execute(CycleEventContainer cycleEventContainer) {
        World.getTaskScheduler().schedule(new MagicShortbowDelayedGraphicTask(this, 1, this.player));
        return super.execute(cycleEventContainer) + 1;
    }
}

