/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat.special;

import com.rs2.model.Entity;
import com.rs2.model.World;
import com.rs2.model.c.ProjectileDefinition;
import com.rs2.model.combat.AttackValidationResult;
import com.rs2.model.combat.CombatCycleEvent;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.combat.special.RuneThrownaxeSpecialDefinition;
import com.rs2.model.combat.special.RuneThrownaxeTargetDistanceComparator;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;
import java.util.ArrayList;
import java.util.Collections;

final class RuneThrownaxeSpecialAttack
extends WeaponCombatAttack {
    private final /* synthetic */ Player player;
    private final /* synthetic */ Entity primaryTarget;
    private final /* synthetic */ WeaponProfile sourceWeaponProfile;

    RuneThrownaxeSpecialAttack(RuneThrownaxeSpecialDefinition runeThrownaxeSpecialDefinition, Player player, Entity entity, WeaponProfile weaponProfile, Player player2, Entity entity2, WeaponProfile weaponProfile2) {
        this.player = player2;
        this.primaryTarget = entity2;
        this.sourceWeaponProfile = weaponProfile2;
        super(player, entity, weaponProfile);
    }

    @Override
    public final boolean prepareSpecialAttack() {
        Object object;
        int n;
        Object object2;
        if (!super.prepareSpecialAttack()) {
            return false;
        }
        ArrayList<Object> arrayList = new ArrayList<Object>();
        if (this.player.isInMultiCombatArea() && this.primaryTarget.isInMultiCombatArea()) {
            object2 = World.f();
            int n2 = ((Player[])object2).length;
            n = 0;
            while (n < n2) {
                object = object2[n];
                if (object != null && object != this.getAttacker() && object != this.getTarget() && ((Entity)object).isInMultiCombatArea() && GameUtil.a(this.getTarget().getPosition(), ((Entity)object).getPosition(), 3)) {
                    arrayList.add(object);
                }
                ++n;
            }
            object2 = World.g();
            n2 = ((Npc[])object2).length;
            n = 0;
            while (n < n2) {
                object = object2[n];
                if (object != null && object != this.getTarget() && ((Entity)object).isInMultiCombatArea() && GameUtil.a(this.getTarget().getPosition(), ((Entity)object).getPosition(), 3)) {
                    arrayList.add(object);
                }
                ++n;
            }
            Collections.sort(arrayList, new RuneThrownaxeTargetDistanceComparator(this));
        }
        object = new ArrayList();
        if (arrayList.size() > 5) {
            n = 0;
            while (n < arrayList.size()) {
                Entity entity = (Entity)arrayList.get(n);
                object2 = CombatCycleEvent.validateAttack(this.player, entity);
                boolean bl = false;
                if (entity.isNpc()) {
                    Npc npc = (Npc)entity;
                    bl = npc.isDoorSupportNpc();
                }
                if ((object2 == AttackValidationResult.VALID || bl) && ((ArrayList)object).size() < 5) {
                    ((ArrayList)object).add(entity);
                    if (((ArrayList)object).size() == 5) break;
                }
                ++n;
            }
        } else {
            object = arrayList;
        }
        arrayList = object;
        double d = this.calculateMaxHit();
        object2 = this.sourceWeaponProfile.getAmmunitionProfile();
        Object object3 = object2.getProjectileTiming();
        object3 = new ProjectileDefinition(258, ((ProjectileTiming)object3).copy());
        this.setHitDefinitions(new HitDefinition[]{new HitDefinition(this.getAttackStyle(), HitType.NORMAL, d).enableRandomDamage().setProjectile((ProjectileDefinition)object3).setAccuracyMultiplier(1.0).enableAccuracyCheck(true).setChainedTargets(arrayList).setChainedSource(this.player)});
        return true;
    }
}

