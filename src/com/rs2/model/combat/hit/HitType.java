/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat.hit;

public enum HitType {
    NORMAL(1),
    POISON(2),
    DISEASE(3),
    BLOCKED(0);

    private int clientId;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private HitType() {
        void var3_2;
        this.clientId = var3_2;
    }

    public final int getClientId() {
        return this.clientId;
    }
}

