/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.randomevent;

public enum SkillRandomEventNpc {
    a(419),
    b(425),
    c(438),
    d(413),
    e(391),
    g(2463);

    int baseNpcId;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private SkillRandomEventNpc() {
        void var3_2;
        this.baseNpcId = var3_2;
    }

    public final int getBaseNpcId() {
        return this.baseNpcId;
    }
}

