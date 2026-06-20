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
    public static final /* enum */ DuelRule a = new NoRangedDuelRule(2, 6725, 6698);
    public static final /* enum */ DuelRule b = new NoMeleeDuelRule(3, 6726, 6699);
    public static final /* enum */ DuelRule c = new NoMagicDuelRule(4, 6727, 6697);
    public static final /* enum */ DuelRule d = new NoSpecialAttackDuelRule(10, 7816, 7817);
    public static final /* enum */ DuelRule e = new FunWeaponsDuelRule(8, 670, 669);
    public static final /* enum */ DuelRule f = new NoForfeitDuelRule(0, 6721, 6696);
    public static final /* enum */ DuelRule g = new NoPotionsDuelRule(5, 6728, 6701);
    public static final /* enum */ DuelRule h = new NoFoodDuelRule(6, 6729, 6702);
    public static final /* enum */ DuelRule i = new NoPrayerDuelRule(7, 6730, 6703);
    public static final /* enum */ DuelRule j = new NoMovementDuelRule(1, 6722, 6704);
    public static final /* enum */ DuelRule k = new ObstaclesDuelRule(8, 6732, 6731);
    public static final /* enum */ DuelRule l = new NoHelmetDuelRule(11, 13813, -1);
    public static final /* enum */ DuelRule m = new NoCapeDuelRule(12, 13814, -1);
    public static final /* enum */ DuelRule n = new NoAmuletDuelRule(13, 13815, -1);
    public static final /* enum */ DuelRule o = new NoAmmoDuelRule(21, 13816, -1);
    public static final /* enum */ DuelRule p = new NoWeaponDuelRule(14, 13817, -1);
    public static final /* enum */ DuelRule q = new NoBodyDuelRule(15, 13818, -1);
    public static final /* enum */ DuelRule r = new NoShieldDuelRule(16, 13819, -1);
    public static final /* enum */ DuelRule s = new NoLegsDuelRule(17, 13820, -1);
    public static final /* enum */ DuelRule t = new NoGlovesDuelRule(18, 13823, -1);
    public static final /* enum */ DuelRule u = new NoBootsDuelRule(19, 13822, -1);
    public static final /* enum */ DuelRule v = new NoRingDuelRule(20, 13821, -1);
    public int w;
    private int x;
    private int y;
    private static final /* synthetic */ DuelRule[] z;

    static {
        z = new DuelRule[]{a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v};
    }

    public abstract void a(Player var1, boolean var2);

    public abstract boolean a(Player var1);

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private DuelRule(int n3) {
        void var5_3;
        void var4_2;
        void cfr_renamed_2;
        void cfr_renamed_1;
        this.w = n3;
        this.x = var4_2;
        this.y = var5_3;
    }

    public static DuelRule a(int n) {
        DuelRule[] duelRuleArray = DuelRule.values();
        int n2 = duelRuleArray.length;
        int n3 = 0;
        while (n3 < n2) {
            DuelRule duelRule = duelRuleArray[n3];
            if (duelRule.x == n || duelRule.y == n) {
                return duelRule;
            }
            ++n3;
        }
        return null;
    }

    public static DuelRule[] values() {
        DuelRule[] duelRuleArray = new DuelRule[22];
        System.arraycopy(z, 0, duelRuleArray, 0, 22);
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
        this((String)cfr_renamed_1, n2, n3, by, (int)var5_4);
        void var5_4;
        void cfr_renamed_1;
    }
}

