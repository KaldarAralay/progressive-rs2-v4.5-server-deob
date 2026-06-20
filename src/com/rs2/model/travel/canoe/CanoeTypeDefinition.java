/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.travel.canoe;

public enum CanoeTypeDefinition {
    LOG(18204, 12, 30, 1, new int[]{-1, -1}, 1, false),
    DUGOUT(18205, 27, 60, 2, new int[]{18212, 18185}, 2, false),
    STABLE_DUGOUT(18206, 42, 90, 3, new int[]{18215, 18182}, 3, false),
    WAKA(18207, 57, 150, 4, new int[]{18209, 18193}, 4, true);

    int buttonId;
    int requiredLevel;
    int experience;
    int configValue;
    int[] levelInterfaceIds;
    int travelRange;
    boolean canEnterWilderness;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private CanoeTypeDefinition(int n3, int n4, int[] nArray, int n5, boolean bl) {
        void var9_7;
        void var8_6;
        this.buttonId = n3;
        this.configValue = n5;
        this.requiredLevel = n4;
        this.experience = (int)nArray;
        this.levelInterfaceIds = (int[])bl;
        this.travelRange = var8_6;
        this.canEnterWilderness = var9_7;
    }
}

