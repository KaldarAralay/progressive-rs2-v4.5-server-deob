/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat.attack;

import com.rs2.model.Entity;
import com.rs2.model.EntityTargetMovement;
import com.rs2.model.World;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.AttackBonusType;
import com.rs2.model.combat.AttackStyleDefinition;
import com.rs2.model.combat.AttackValidationResult;
import com.rs2.model.combat.AttackXpMode;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.combat.CombatCycleEvent;
import com.rs2.model.combat.CombatType;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.AreaAttackTargetDistanceComparator;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.combat.attack.CombatAttackState;
import com.rs2.model.combat.attack.FlaggedProjectileCombatAttack;
import com.rs2.model.combat.attack.ForcedMeleeCombatAttack;
import com.rs2.model.combat.attack.ForcedProjectileCombatAttack;
import com.rs2.model.combat.attack.MagicCombatAttack;
import com.rs2.model.combat.attack.MeleeCombatAttack;
import com.rs2.model.combat.attack.ProjectileCombatAttack;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.effect.CombatEffect;
import com.rs2.model.combat.effect.PoisonEffect;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.requirement.CombatCostRequirement;
import com.rs2.model.combat.requirement.CombatRequirement;
import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;
import java.util.ArrayList;
import java.util.Collections;

public abstract class BaseCombatAttack
extends CombatAttack {
    private CombatRequirement[] requirements;
    private HitDefinition[] hitDefinitions;
    private GraphicEffect attackerGraphic;
    private int animationId = -1;
    private int attackDelay = -1;
    private int attackSoundId = -1;

    public BaseCombatAttack(Entity entity, Entity entity2) {
        super(entity, entity2);
    }

    @Override
    public int execute(CycleEventContainer object) {
        Object object2;
        int n;
        Object[] objectArray;
        if (this.requirements != null) {
            objectArray = this.requirements;
            n = this.requirements.length;
            int n2 = 0;
            while (n2 < n) {
                object = objectArray[n2];
                if (object instanceof CombatCostRequirement) {
                    ((CombatCostRequirement)object).consume(this.getAttacker());
                }
                ++n2;
            }
        }
        if (this.getAttacker().isPlayer() && this.getTarget().isPlayer()) {
            object = (Player)this.getAttacker();
            switch (new WeaponCombatAttack((Player)object, this.getTarget(), WeaponProfile.forItem(new ItemStack(((Player)object).getEquipmentManager().getItemIdAtSlot(3)))).getAttackStyle().getCombatType()) {
                case MELEE: {
                    if (this instanceof MagicCombatAttack || !DuelRule.NO_MELEE.isEnabledFor((Player)object) || ((Player)object).getEquipmentManager().getContainer().getItemAt(3) == null) break;
                    Object object3 = object;
                    ((Player)object3).packetSender.sendGameMessage("Melee attacks have been disabled during this fight!");
                    ((Entity)object).nextActionSequence();
                    return this.attackDelay;
                }
                case RANGED: {
                    if (!DuelRule.NO_RANGED.isEnabledFor((Player)object)) break;
                    Object object4 = object;
                    ((Player)object4).packetSender.sendGameMessage("Ranged attacks have been disabled during this fight!");
                    ((Entity)object).nextActionSequence();
                    return this.attackDelay;
                }
            }
            if (this.animationId != 1818) {
                ((Player)this.getAttacker()).recordPvpAttack((Player)this.getTarget());
            }
        }
        if (this.attackerGraphic != null) {
            this.getAttacker().getUpdateState().setGraphic(this.attackerGraphic.getId(), this.attackerGraphic.getPackedDelay());
        }
        if (this.getAttacker().isPlayer()) {
            object = (Player)this.getAttacker();
            if (((Player)object).npcTransformationId > 1) {
                this.animationId = new Npc(((Player)object).npcTransformationId).getDefinition().getAttackAnimationId();
            }
        }
        if (this.animationId != -1) {
            if (this.getAttacker().isNpc()) {
                object = (Npc)this.getAttacker();
                if (((Npc)object).getNpcId() == 907 || ((Npc)object).getNpcId() == 908 || ((Npc)object).getNpcId() == 909 || ((Npc)object).getNpcId() == 910 || ((Npc)object).getNpcId() == 911 || ((Npc)object).getNpcId() == 912 || ((Npc)object).getNpcId() == 913 || ((Npc)object).getNpcId() == 914) {
                    this.animationId = ((Npc)object).getDefinition().getAttackAnimationId();
                }
                if (((Npc)object).getNpcId() == 912) {
                    ((Entity)object).getUpdateState().setForcedText("Feel the wrath of Zamorak.");
                }
                if (((Npc)object).getNpcId() == 913) {
                    ((Entity)object).getUpdateState().setForcedText("Feel the wrath of Saradomin.");
                }
                if (((Npc)object).getNpcId() == 914) {
                    ((Entity)object).getUpdateState().setForcedText("Feel the wrath of Guthix.");
                }
            }
            this.getAttacker().getUpdateState().setAnimation(this.animationId);
            if (this.getAttacker().isPlayer()) {
                object2 = object = (Player)this.getAttacker();
                ((Player)object).packetSender.sendSoundEffect(this.attackSoundId, 1, 20);
            }
            if (this.getTarget().isPlayer()) {
                object = (Player)this.getTarget();
                if (this.getAttacker().isNpc()) {
                    Npc npc = (Npc)this.getAttacker();
                    if (this.attackSoundId == -1 && npc.getAttackSoundId() != -1) {
                        this.attackSoundId = npc.getAttackSoundId();
                    }
                }
                object2 = object;
                ((Player)object2).packetSender.sendSoundEffect(this.attackSoundId, 1, 20);
            }
        }
        if (this.hitDefinitions != null) {
            object2 = this;
            objectArray = ((BaseCombatAttack)object2).hitDefinitions;
            n = ((BaseCombatAttack)object2).hitDefinitions.length;
            int n3 = 0;
            while (n3 < n) {
                Object object5;
                int n4;
                Object object6;
                object = objectArray[n3];
                if (this.getAttacker().isNpc()) {
                    object6 = (Npc)this.getAttacker();
                    if (((HitDefinition)object).getAttackStyle().getCombatType() == CombatType.MELEE && ((Npc)object6).getDefinition().getPoisonDamage() > 0 && ((Npc)object6).getDefinition().getPoisonChance() > 0.0 && GameUtil.rollChance(((Npc)object6).getDefinition().getPoisonChance())) {
                        ((HitDefinition)object).addEffect(new PoisonEffect(((Npc)object6).getDefinition().getPoisonDamage()));
                    }
                }
                object6 = new CombatAction(this.getAttacker(), this.getTarget(), (HitDefinition)object);
                if (this.getTarget().isNpc()) {
                    object2 = (Npc)this.getTarget();
                    object2 = ((Npc)object2).getCombatDefinition();
                }
                if (((HitDefinition)object).getAttackStyle().getXpMode() == AttackXpMode.KQ_RANGED) {
                    Player[] playerArray = World.getPlayers();
                    n4 = playerArray.length;
                    int n5 = 0;
                    while (n5 < n4) {
                        object2 = playerArray[n5];
                        if (object2 != null && object2 != this.getAttacker() && object2 != this.getTarget() && GameUtil.isWithinDistance(this.getAttacker().getPosition(), ((Entity)object2).getPosition(), 10) && (object5 = CombatCycleEvent.validateAttack(this.getAttacker(), (Entity)object2)) == AttackValidationResult.VALID) {
                            ((HitDefinition)object).setDelay(-1);
                            new CombatAction(this.getAttacker(), (Entity)object2, (HitDefinition)object).queue();
                        }
                        ++n5;
                    }
                }
                if (((HitDefinition)object).getAttackStyle().getXpMode() == AttackXpMode.KQ_MAGIC) {
                    object2 = new ArrayList();
                    object5 = World.getPlayers();
                    int n6 = ((Player[])object5).length;
                    n4 = 0;
                    while (n4 < n6) {
                        Player player = object5[n4];
                        if (player != null && player != this.getAttacker() && player != this.getTarget() && GameUtil.isWithinDistance(this.getAttacker().getPosition(), player.getPosition(), 6)) {
                            ((ArrayList)object2).add(player);
                        }
                        ++n4;
                    }
                    Collections.sort(object2, new AreaAttackTargetDistanceComparator(this));
                    ((HitDefinition)object).setChainedSource(this.getAttacker());
                    ((HitDefinition)object).setChainedTargets((ArrayList)object2);
                }
                ((CombatAction)object6).queue();
                ++n3;
            }
        }
        return this.attackDelay;
    }

    @Override
    public CombatAttackState getState() {
        Object object;
        Entity entity = this.getAttacker();
        Entity entity2 = this.getTarget();
        if (this.requirements != null) {
            CombatRequirement[] combatRequirementArray = this.requirements;
            int n = this.requirements.length;
            int n2 = 0;
            while (n2 < n) {
                object = combatRequirementArray[n2];
                if (!((CombatRequirement)object).validate(entity)) {
                    this.onRequirementFailed();
                    return CombatAttackState.a;
                }
                ++n2;
            }
        }
        Entity entity3 = entity2;
        object = entity;
        int n = this.d() + (!entity3.isMoving() || !((Entity)object).isMoving() ? 0 : (entity3.isRunningMovement() ? 2 : (entity3.isMoving() ? 1 : 0)));
        if (!EntityTargetMovement.canReachTarget(entity, entity2, n)) {
            return CombatAttackState.b;
        }
        return CombatAttackState.c;
    }

    public void onRequirementFailed() {
    }

    public final void setRequirements(CombatRequirement[] combatRequirementArray) {
        this.requirements = combatRequirementArray;
    }

    public final void setHitDefinitions(HitDefinition[] hitDefinitionArray) {
        this.hitDefinitions = hitDefinitionArray;
    }

    public final HitDefinition[] getHitDefinitions() {
        return this.hitDefinitions;
    }

    public final void setAttackDelay(int n) {
        this.attackDelay = n;
    }

    public final void setAttackerGraphic(GraphicEffect graphicEffect) {
        this.attackerGraphic = graphicEffect;
    }

    public final void setAnimationId(int n) {
        this.animationId = n;
    }

    public final void setAttackSoundId(int n) {
        this.attackSoundId = n;
    }

    public final BaseCombatAttack addEffect(CombatEffect combatEffect) {
        if (this.hitDefinitions == null) {
            return this;
        }
        HitDefinition[] hitDefinitionArray = this.hitDefinitions;
        int n = this.hitDefinitions.length;
        int n2 = 0;
        while (n2 < n) {
            HitDefinition hitDefinition = hitDefinitionArray[n2];
            hitDefinition.addEffect(combatEffect);
            ++n2;
        }
        return this;
    }

    public static BaseCombatAttack createForcedMeleeAttack(Entity entity, Entity entity2, AttackXpMode attackXpMode, AttackBonusType attackBonusType, int n, int n2, int n3, boolean bl) {
        return new ForcedMeleeCombatAttack(entity, entity2, attackXpMode, attackBonusType, 97, true, 2655, 8);
    }

    public static BaseCombatAttack createMeleeAttack(Entity entity, Entity entity2, AttackXpMode attackXpMode, AttackBonusType attackBonusType, int n, int n2, int n3) {
        return new MeleeCombatAttack(entity, entity2, attackXpMode, attackBonusType, n, n3, n2);
    }

    public static BaseCombatAttack createWeaponProfileMeleeAttack(Entity entity, Entity entity2, AttackXpMode attackXpMode, int n, WeaponProfile weaponProfile) {
        n = -1;
        int n2 = 0;
        while (n2 < weaponProfile.getAttackAnimations().length) {
            if (weaponProfile.getInterfaceDefinition().getAttackStyles()[n2].getXpMode() == attackXpMode) {
                n = n2;
                break;
            }
            ++n2;
        }
        if (n == -1) {
            throw new IllegalArgumentException("That weapon does not contain an attack style with the given mode!");
        }
        AttackStyleDefinition attackStyleDefinition = weaponProfile.getInterfaceDefinition().getAttackStyles()[n];
        return BaseCombatAttack.createMeleeAttack(entity, entity2, attackXpMode, attackStyleDefinition.getAttackBonusType(), 0, weaponProfile.getAttackDelay(), weaponProfile.getAttackAnimations()[n]);
    }

    private static BaseCombatAttack createProjectileAttackInternal(Entity entity, Entity entity2, CombatType combatType, AttackXpMode attackXpMode, int n, int n2, int n3, GraphicEffect graphicEffect, GraphicEffect graphicEffect2, int n4, ProjectileTiming projectileTiming, int n5, CombatEffect combatEffect) {
        return new ProjectileCombatAttack(entity, entity2, attackXpMode, combatType, projectileTiming, n4, n, 0, graphicEffect2, combatEffect, n3, n2, graphicEffect);
    }

    private static BaseCombatAttack createForcedProjectileAttackInternal(Entity entity, Entity entity2, CombatType combatType, AttackXpMode attackXpMode, int n, int n2, int n3, GraphicEffect graphicEffect, GraphicEffect graphicEffect2, int n4, ProjectileTiming projectileTiming, int n5, CombatEffect combatEffect, boolean bl) {
        return new ForcedProjectileCombatAttack(entity, entity2, attackXpMode, combatType, projectileTiming, n4, bl, n, 0, graphicEffect2, combatEffect, n3, n2, graphicEffect);
    }

    public static BaseCombatAttack createFlaggedProjectileAttack(Entity entity, Entity entity2, CombatType combatType, AttackXpMode attackXpMode, int n, int n2, int n3, GraphicEffect graphicEffect, GraphicEffect graphicEffect2, int n4, ProjectileTiming projectileTiming, boolean bl, boolean bl2) {
        Entity entity3 = entity;
        bl2 = true;
        bl = false;
        entity = null;
        boolean bl3 = false;
        n2 = 8;
        n = 97;
        Entity entity4 = entity3;
        return new FlaggedProjectileCombatAttack(entity4, entity2, attackXpMode, combatType, projectileTiming, n4, bl, n, bl2, 0, graphicEffect2, null, n3, n2, graphicEffect);
    }

    public static BaseCombatAttack createProjectileAttack(Entity entity, Entity entity2, CombatType combatType, AttackXpMode attackXpMode, int n, int n2, int n3, GraphicEffect graphicEffect, GraphicEffect graphicEffect2, int n4, ProjectileTiming projectileTiming) {
        return BaseCombatAttack.createProjectileAttackInternal(entity, entity2, combatType, attackXpMode, n, n2, n3, graphicEffect, graphicEffect2, n4, projectileTiming, 0, null);
    }

    public static BaseCombatAttack createKalphiteQueenMagicAttack(Entity entity, Entity entity2, CombatType combatType, AttackXpMode attackXpMode, int n, int n2, int n3, GraphicEffect graphicEffect, GraphicEffect graphicEffect2, int n4, ProjectileTiming projectileTiming, boolean bl) {
        return BaseCombatAttack.createForcedProjectileAttackInternal(entity, entity2, combatType, attackXpMode, 31, 4, n3, graphicEffect, graphicEffect2, 280, projectileTiming, 0, null, true);
    }

    public static BaseCombatAttack createProjectileAttackWithEffect(Entity entity, Entity entity2, CombatType combatType, AttackXpMode attackXpMode, int n, int n2, int n3, GraphicEffect graphicEffect, GraphicEffect graphicEffect2, int n4, ProjectileTiming projectileTiming, CombatEffect combatEffect) {
        return BaseCombatAttack.createProjectileAttackInternal(entity, entity2, combatType, attackXpMode, n, n2, n3, graphicEffect, graphicEffect2, n4, projectileTiming, 0, combatEffect);
    }

    public static BaseCombatAttack createKalphiteQueenRangedAttackWithEffect(Entity entity, Entity entity2, CombatType combatType, AttackXpMode attackXpMode, int n, int n2, int n3, GraphicEffect graphicEffect, GraphicEffect graphicEffect2, int n4, ProjectileTiming projectileTiming, CombatEffect combatEffect, boolean bl) {
        return BaseCombatAttack.createForcedProjectileAttackInternal(entity, entity2, combatType, attackXpMode, 31, n2, n3, graphicEffect, graphicEffect2, 289, projectileTiming, 0, combatEffect, true);
    }

    public static BaseCombatAttack createMagicAttack(Entity entity, Entity entity2, SpellDefinition spellDefinition) {
        return new MagicCombatAttack(entity, entity2, spellDefinition);
    }
}

