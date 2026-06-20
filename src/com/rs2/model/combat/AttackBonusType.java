/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat;

public enum AttackBonusType {
    STAB(0),
    SLASH(1),
    CRUSH(2),
    MAGIC(3),
    RANGED(4);

    private int f;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private AttackBonusType() {
        void var3_2;
        this.f = var3_2;
    }

    public final int getIndex() {
        return this.f;
    }
}

