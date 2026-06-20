/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat.attack;

import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.c.ProjectileDefinition;
import com.rs2.model.combat.AmmunitionDefinition;
import com.rs2.model.combat.AttackStyleDefinition;
import com.rs2.model.combat.AttackXpMode;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.combat.CombatType;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.BaseCombatAttack;
import com.rs2.model.combat.attack.CombatAttackState;
import com.rs2.model.combat.effect.PoisonEffect;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.combat.requirement.AmmunitionRequirement;
import com.rs2.model.combat.requirement.CombatRequirement;
import com.rs2.model.combat.special.SpecialAttackDefinition;
import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;

public class WeaponCombatAttack
extends BaseCombatAttack {
    private WeaponProfile weaponProfile;
    private int attackStyleIndex;
    private AttackStyleDefinition attackStyle;
    private AmmunitionDefinition ammunition;
    private SpecialAttackDefinition specialAttack;
    private boolean cancelled;
    private PoisonEffect poisonEffect;
    private int droppedAmmunitionId;
    private int droppedAmmunitionAmount;
    private static PoisonEffect MELEE_POISON_EFFECT = new PoisonEffect(4.0);
    private static PoisonEffect RANGED_POISON_EFFECT = new PoisonEffect(2.0);
    private static PoisonEffect MELEE_POISON_PLUS_EFFECT = new PoisonEffect(5.0);
    private static PoisonEffect RANGED_POISON_PLUS_EFFECT = new PoisonEffect(3.0);
    private static PoisonEffect MELEE_POISON_PLUS_PLUS_EFFECT = new PoisonEffect(6.0);
    private static PoisonEffect RANGED_POISON_PLUS_PLUS_EFFECT = new PoisonEffect(4.0);
    private static PoisonEffect KARAMBWAN_POISON_EFFECT = new PoisonEffect(6.0);

    public WeaponCombatAttack(Player object, Entity object2, WeaponProfile weaponProfile) {
        super((Entity)object, (Entity)object2);
        this.weaponProfile = weaponProfile;
        this.attackStyleIndex = ((Player)this.getAttacker()).getFightMode();
        object2 = weaponProfile.getInterfaceDefinition().getAttackStyles()[this.attackStyleIndex];
        object = this;
        this.attackStyle = object2;
        this.droppedAmmunitionId = -1;
    }

    public final AmmunitionDefinition getAmmunition() {
        return this.ammunition;
    }

    public final double calculateMaxHit() {
        return CombatManager.calculateWeaponMaxHit((Player)this.getAttacker(), this);
    }

    public final AttackStyleDefinition getAttackStyle() {
        return this.attackStyle;
    }

    @Override
    public final CombatType getCombatType() {
        WeaponCombatAttack weaponCombatAttack = this;
        return weaponCombatAttack.attackStyle.getCombatType();
    }

    @Override
    public final void prepare() {
        HitDefinition[] hitDefinitionArray;
        Object object = (Player)this.getAttacker();
        Object object2 = this;
        if (((WeaponCombatAttack)object2).attackStyle.getCombatType() == CombatType.MELEE && this.getTarget().isDoorSupportNpc()) {
            this.cancelled = true;
            CombatManager.stopCombat((Entity)object);
            return;
        }
        Object object3 = ((Player)object).getEquipmentManager().getContainer().getItemAt(3);
        Object object4 = "";
        if (object3 != null) {
            object3 = ItemDefinition.forId(((ItemStack)object3).getId());
            object4 = ((ItemDefinition)object3).getName().toLowerCase();
        }
        object2 = this;
        if (((WeaponCombatAttack)object2).attackStyle.getCombatType() == CombatType.RANGED && ((String)object4).contains("crystal bow")) {
            this.ammunition = AmmunitionDefinition.CRYSTAL_BOW_ARROW;
        } else {
            object2 = this;
            if (((WeaponCombatAttack)object2).attackStyle.getCombatType() == CombatType.RANGED && !((String)object4).contains("crystal bow")) {
                this.ammunition = AmmunitionDefinition.findEquippedAmmunition((Player)object, this.weaponProfile, true);
                if (this.ammunition == null) {
                    this.cancelled = true;
                    return;
                }
                int n = this.weaponProfile.getAmmunitionProfile().getEquipmentSlot();
                this.droppedAmmunitionId = ((Player)object).getEquipmentManager().getItemIdAtSlot(n);
                this.droppedAmmunitionAmount = 1;
                this.setRequirements(new CombatRequirement[]{new AmmunitionRequirement(this, n, this.droppedAmmunitionId, this.droppedAmmunitionAmount, true)});
                this.poisonEffect = WeaponCombatAttack.a((Player)object, CombatType.RANGED, n);
            } else {
                object2 = this;
                if (((WeaponCombatAttack)object2).attackStyle.getCombatType() == CombatType.MELEE) {
                    this.poisonEffect = WeaponCombatAttack.a((Player)object, CombatType.MELEE, 3);
                }
            }
        }
        object = this;
        double d = ((WeaponCombatAttack)object).calculateMaxHit();
        if (((WeaponCombatAttack)object).weaponProfile == WeaponProfile.DARK_BOW) {
            Object object5 = ((WeaponCombatAttack)object).weaponProfile.getAmmunitionProfile().getProjectileTiming();
            object4 = new ProjectileDefinition(((WeaponCombatAttack)object).ammunition.getProjectileId(), ((ProjectileTiming)object5).copy().setStartDelay(40).setSpeed(3));
            object5 = new ProjectileDefinition(((WeaponCombatAttack)object).ammunition.getProjectileId(), ((ProjectileTiming)object5).copy().setStartDelay(41).setSpeed(2));
            object2 = object;
            object4 = new HitDefinition(((WeaponCombatAttack)object2).attackStyle, HitType.NORMAL, d).enableRandomDamage().enableAccuracyCheck().setProjectile((ProjectileDefinition)object4);
            object2 = object;
            object = new HitDefinition(((WeaponCombatAttack)object2).attackStyle, HitType.NORMAL, d).enableRandomDamage().enableAccuracyCheck().setProjectile((ProjectileDefinition)object5);
            HitDefinition[] hitDefinitionArray2 = new HitDefinition[2];
            hitDefinitionArray2[0] = object4;
            hitDefinitionArray = hitDefinitionArray2;
            hitDefinitionArray2[1] = object;
        } else {
            ProjectileDefinition projectileDefinition = null;
            if (((WeaponCombatAttack)object).ammunition != null) {
                projectileDefinition = new ProjectileDefinition(((WeaponCombatAttack)object).ammunition.getProjectileId(), ((WeaponCombatAttack)object).weaponProfile.getAmmunitionProfile().getProjectileTiming());
            }
            object2 = object;
            object4 = new HitDefinition(((WeaponCombatAttack)object2).attackStyle, HitType.NORMAL, d).enableRandomDamage().enableAccuracyCheck().setProjectile(projectileDefinition);
            if (((WeaponCombatAttack)object).ammunition != null) {
                ((HitDefinition)object4).setAmmunition(((WeaponCombatAttack)object).ammunition);
            }
            HitDefinition[] hitDefinitionArray3 = new HitDefinition[1];
            hitDefinitionArray = hitDefinitionArray3;
            hitDefinitionArray3[0] = object4;
        }
        this.setHitDefinitions(hitDefinitionArray);
        object = this;
        int n = 0;
        object2 = object;
        AttackStyleDefinition attackStyleDefinition = ((WeaponCombatAttack)object2).attackStyle;
        if (attackStyleDefinition.getXpMode() == AttackXpMode.RAPID) {
            n = -1;
        }
        this.setAttackDelay(((WeaponCombatAttack)object).weaponProfile.getAttackDelay() + n);
        object = this;
        this.setAttackerGraphic(((WeaponCombatAttack)object).ammunition != null && ((WeaponCombatAttack)object).weaponProfile.getAmmunitionProfile() != null ? (((WeaponCombatAttack)object).weaponProfile == WeaponProfile.DARK_BOW ? new GraphicEffect(((WeaponCombatAttack)object).ammunition.getAlternateGraphicId(), ((WeaponCombatAttack)object).weaponProfile.getAmmunitionProfile().getGraphicDelay()) : new GraphicEffect(((WeaponCombatAttack)object).ammunition.getGraphicId(), ((WeaponCombatAttack)object).weaponProfile.getAmmunitionProfile().getGraphicDelay())) : null);
        object = this;
        this.setAnimationId(((WeaponCombatAttack)object).weaponProfile.getAttackAnimations()[((WeaponCombatAttack)object).attackStyleIndex]);
        this.setAttackSoundId(this.weaponProfile.getInterfaceDefinition().attackSoundId);
        this.cancelled = !this.prepareSpecialAttack();
    }

    @Override
    public final int getAttackRange() {
        switch (this.attackStyle.getCombatType()) {
            case RANGED: {
                if (this.weaponProfile == WeaponProfile.LONG_BOW || this.weaponProfile == WeaponProfile.SPECIAL_BOW || this.weaponProfile == WeaponProfile.CRYSTAL_BOW) {
                    if (this.attackStyle.getXpMode() == AttackXpMode.LONGRANGE) {
                        return 11;
                    }
                    return 10;
                }
                if (this.attackStyle.getXpMode() == AttackXpMode.LONGRANGE) {
                    return 9;
                }
                return 7;
            }
            case MAGIC: {
                if (this.attackStyle.getXpMode() == AttackXpMode.DRAGONFIRE) {
                    return 1;
                }
                return 10;
            }
            case MELEE: {
                if (this.weaponProfile == WeaponProfile.HALBERD) {
                    return 2;
                }
                return 1;
            }
        }
        return 1;
    }

    public boolean prepareSpecialAttack() {
        return true;
    }

    @Override
    public final CombatAttackState getState() {
        if (this.cancelled) {
            return CombatAttackState.a;
        }
        Object object = ((Player)this.getAttacker()).getSpecialAttackDefinition();
        boolean bl = ((Player)this.getAttacker()).isSpecialAttackEnabled();
        if (object != null && bl) {
            Player player = (Player)this.getAttacker();
            this.specialAttack = object;
            if (player.getSpecialEnergy() < object.getEnergyCost()) {
                object = player;
                ((Player)object).packetSender.sendGameMessage("You have no special energy left.");
                return CombatAttackState.a;
            }
            if (DuelRule.NO_SPECIAL_ATTACK.isEnabledFor(player)) {
                object = player;
                ((Player)object).packetSender.sendGameMessage("Special attacks have been disabled during this fight!");
                return CombatAttackState.a;
            }
        }
        return super.getState();
    }

    @Override
    public int execute(CycleEventContainer cycleEventContainer) {
        Object object = (Player)this.getAttacker();
        if (((Player)object).isSpecialAttackEnabled() && this.specialAttack != null) {
            ((Player)object).setSpecialEnergy(((Player)object).getSpecialEnergy() - this.specialAttack.getEnergyCost());
            ((Player)object).refreshSpecialAttackWidgets();
            ((Player)object).setSpecialAttackEnabled(false);
            ((Player)object).refreshSpecialAttackWidgets();
        }
        if (this.getHitDefinitions() != null) {
            HitDefinition[] hitDefinitionArray = this.getHitDefinitions();
            int n = hitDefinitionArray.length;
            int n2 = 0;
            while (n2 < n) {
                object = hitDefinitionArray[n2];
                if (this.droppedAmmunitionId != -1) {
                    ((HitDefinition)object).setDroppedAmmunition(new ItemStack(this.droppedAmmunitionId, this.droppedAmmunitionAmount));
                }
                if (this.poisonEffect != null) {
                    if (this.attackStyle.getCombatType() == CombatType.MELEE) {
                        if (GameUtil.rollChance(0.25)) {
                            ((HitDefinition)object).addEffect(this.poisonEffect);
                        }
                    } else if (this.attackStyle.getCombatType() == CombatType.RANGED && GameUtil.rollChance(0.125)) {
                        ((HitDefinition)object).addEffect(this.poisonEffect);
                    }
                }
                ++n2;
            }
        }
        return super.execute(cycleEventContainer);
    }

    private static PoisonEffect a(Player object, CombatType combatType, int n) {
        if (combatType == CombatType.MAGIC) {
            return null;
        }
        if ((object = ((Player)object).getEquipmentManager().getContainer().getItemAt(n)) == null) {
            return null;
        }
        object = ItemDefinition.forId(((ItemStack)object).getId());
        if (((String)(object = ((ItemDefinition)object).getName().toLowerCase())).contains("(kp)")) {
            return KARAMBWAN_POISON_EFFECT;
        }
        if (((String)object).contains("(s)") || ((String)object).contains("(p++)") || ((String)object).contains("++")) {
            if (combatType == CombatType.MELEE) {
                return MELEE_POISON_PLUS_PLUS_EFFECT;
            }
            return RANGED_POISON_PLUS_PLUS_EFFECT;
        }
        if (((String)object).contains("(+)") || ((String)object).contains("(p+)")) {
            if (combatType == CombatType.MELEE) {
                return MELEE_POISON_PLUS_EFFECT;
            }
            return RANGED_POISON_PLUS_EFFECT;
        }
        if (((String)object).contains("(p)")) {
            if (combatType == CombatType.MELEE) {
                return MELEE_POISON_EFFECT;
            }
            return RANGED_POISON_EFFECT;
        }
        return null;
    }
}

