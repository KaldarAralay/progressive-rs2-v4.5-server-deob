/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.duel;

import com.rs2.model.gameplay.duel.FunWeaponsDuelRule;
import com.rs2.model.gameplay.duel.NoAmmoDuelRule;
import com.rs2.model.gameplay.duel.NoAmuletDuelRule;
import com.rs2.model.gameplay.duel.NoBodyDuelRule;
import com.rs2.model.gameplay.duel.NoBootsDuelRule;
import com.rs2.model.gameplay.duel.NoCapeDuelRule;
import com.rs2.model.gameplay.duel.NoFoodDuelRule;
import com.rs2.model.gameplay.duel.NoForfeitDuelRule;
import com.rs2.model.gameplay.duel.NoGlovesDuelRule;
import com.rs2.model.gameplay.duel.NoHelmetDuelRule;
import com.rs2.model.gameplay.duel.NoLegsDuelRule;
import com.rs2.model.gameplay.duel.NoMagicDuelRule;
import com.rs2.model.gameplay.duel.NoMeleeDuelRule;
import com.rs2.model.gameplay.duel.NoMovementDuelRule;
import com.rs2.model.gameplay.duel.NoPotionsDuelRule;
import com.rs2.model.gameplay.duel.NoPrayerDuelRule;
import com.rs2.model.gameplay.duel.NoRangedDuelRule;
import com.rs2.model.gameplay.duel.NoRingDuelRule;
import com.rs2.model.gameplay.duel.NoShieldDuelRule;
import com.rs2.model.gameplay.duel.NoSpecialAttackDuelRule;
import com.rs2.model.gameplay.duel.NoWeaponDuelRule;
import com.rs2.model.gameplay.duel.ObstaclesDuelRule;
import com.rs2.model.player.Player;

public abstract class DuelRule
extends Enum {
    public static final /* enum */ DuelRule NO_RANGED = new NoRangedDuelRule(2, 6725, 6698);
    public static final /* enum */ DuelRule NO_MELEE = new NoMeleeDuelRule(3, 6726, 6699);
    public static final /* enum */ DuelRule NO_MAGIC = new NoMagicDuelRule(4, 6727, 6697);
    public static final /* enum */ DuelRule NO_SPECIAL_ATTACK = new NoSpecialAttackDuelRule(10, 7816, 7817);
    public static final /* enum */ DuelRule FUN_WEAPONS = new FunWeaponsDuelRule(8, 670, 669);
    public static final /* enum */ DuelRule NO_FORFEIT = new NoForfeitDuelRule(0, 6721, 6696);
    public static final /* enum */ DuelRule NO_POTIONS = new NoPotionsDuelRule(5, 6728, 6701);
    public static final /* enum */ DuelRule NO_FOOD = new NoFoodDuelRule(6, 6729, 6702);
    public static final /* enum */ DuelRule NO_PRAYER = new NoPrayerDuelRule(7, 6730, 6703);
    public static final /* enum */ DuelRule NO_MOVEMENT = new NoMovementDuelRule(1, 6722, 6704);
    public static final /* enum */ DuelRule OBSTACLES = new ObstaclesDuelRule(8, 6732, 6731);
    public static final /* enum */ DuelRule NO_HELMET = new NoHelmetDuelRule(11, 13813, -1);
    public static final /* enum */ DuelRule NO_CAPE = new NoCapeDuelRule(12, 13814, -1);
    public static final /* enum */ DuelRule NO_AMULET = new NoAmuletDuelRule(13, 13815, -1);
    public static final /* enum */ DuelRule NO_AMMO = new NoAmmoDuelRule(21, 13816, -1);
    public static final /* enum */ DuelRule NO_WEAPON = new NoWeaponDuelRule(14, 13817, -1);
    public static final /* enum */ DuelRule NO_BODY = new NoBodyDuelRule(15, 13818, -1);
    public static final /* enum */ DuelRule NO_SHIELD = new NoShieldDuelRule(16, 13819, -1);
    public static final /* enum */ DuelRule NO_LEGS = new NoLegsDuelRule(17, 13820, -1);
    public static final /* enum */ DuelRule NO_GLOVES = new NoGlovesDuelRule(18, 13823, -1);
    public static final /* enum */ DuelRule NO_BOOTS = new NoBootsDuelRule(19, 13822, -1);
    public static final /* enum */ DuelRule NO_RING = new NoRingDuelRule(20, 13821, -1);
    public int ruleIndex;
    private int primaryButtonId;
    private int secondaryButtonId;
    private static final /* synthetic */ DuelRule[] VALUES;

    static {
        VALUES = new DuelRule[]{NO_RANGED, NO_MELEE, NO_MAGIC, NO_SPECIAL_ATTACK, FUN_WEAPONS, NO_FORFEIT, NO_POTIONS, NO_FOOD, NO_PRAYER, NO_MOVEMENT, OBSTACLES, NO_HELMET, NO_CAPE, NO_AMULET, NO_AMMO, NO_WEAPON, NO_BODY, NO_SHIELD, NO_LEGS, NO_GLOVES, NO_BOOTS, NO_RING};
    }

    public abstract void toggleForPlayer(Player var1, boolean var2);

    public abstract boolean isEnabledFor(Player var1);

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private DuelRule(int n3) {
        void var5_3;
        void var4_2;
        void cfr_renamed_1;
        void cfr_renamed_0;
        this.ruleIndex = n3;
        this.primaryButtonId = var4_2;
        this.secondaryButtonId = var5_3;
    }

    public static DuelRule forButtonId(int n) {
        DuelRule[] duelRuleArray = DuelRule.values();
        int n2 = duelRuleArray.length;
        int n3 = 0;
        while (n3 < n2) {
            DuelRule duelRule = duelRuleArray[n3];
            if (duelRule.primaryButtonId == n || duelRule.secondaryButtonId == n) {
                return duelRule;
            }
            ++n3;
        }
        return null;
    }

    public static DuelRule[] values() {
        DuelRule[] duelRuleArray = new DuelRule[22];
        System.arraycopy(VALUES, 0, duelRuleArray, 0, 22);
        return duelRuleArray;
    }

    public static DuelRule valueOf(String string) {
        return Enum.valueOf(DuelRule.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    /* synthetic */ DuelRule(int n, int n2, int n3, byte by) {
        this((String)cfr_renamed_0, n2, n3, by, (int)var5_4);
        void var5_4;
        void cfr_renamed_0;
    }
}

