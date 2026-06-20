/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.farming;

import java.util.HashMap;
import java.util.Map;

public enum FarmedTreeDefinition {
    OAK(5370, 6043, 15, new int[]{5968, 1}, 200, 0.2, 14.0, 467.3, 8, 12, 13, 14, 1281),
    WILLOW(5371, 6045, 30, new int[]{5386, 1}, 280, 0.2, 25.0, 1456.3, 15, 21, 22, 23, 1308),
    MAPLE(5372, 6047, 45, new int[]{5396, 1}, 320, 0.25, 45.0, 3403.4, 24, 32, 33, 34, 1307),
    YEW(5373, 6049, 60, new int[]{6016, 10}, 400, 0.25, 81.0, 7069.9, 35, 45, 46, 47, 1309),
    MAGIC(5374, 6051, 75, new int[]{5976, 25}, 480, 0.25, 145.5, 13768.3, 48, 60, 61, 62, 1306);

    private int saplingId;
    private int rootItemId;
    private int requiredLevel;
    private int[] protectionPayment;
    private int totalGrowthTicks;
    private double diseaseChance;
    private double plantingExperience;
    private double healthCheckExperience;
    private int configStartStage;
    private int configEndStage;
    private int checkedTreeConfigStage;
    private int stumpConfigStage;
    private int treeObjectId;
    private static Map definitionsBySaplingId;

    static {
        definitionsBySaplingId = new HashMap();
        FarmedTreeDefinition[] farmedTreeDefinitionArray = FarmedTreeDefinition.values();
        int n = farmedTreeDefinitionArray.length;
        int n2 = 0;
        while (n2 < n) {
            FarmedTreeDefinition farmedTreeDefinition = farmedTreeDefinitionArray[n2];
            definitionsBySaplingId.put(farmedTreeDefinition.saplingId, farmedTreeDefinition);
            ++n2;
        }
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private FarmedTreeDefinition(int n3, int[] nArray, int n4, double d, double d2, double n2222, int n2222, int n5, int n2222, int n6, int n7) {
        void var18_13;
        void var17_12;
        void cfr_renamed_10;
        void cfr_renamed_11;
        void cfr_renamed_4;
        void var7_5;
        this.saplingId = n3;
        this.rootItemId = (int)nArray;
        this.requiredLevel = n4;
        this.protectionPayment = (int[])d;
        this.totalGrowthTicks = var7_5;
        this.diseaseChance = d2;
        this.plantingExperience = cfr_renamed_4;
        this.healthCheckExperience = cfr_renamed_11;
        this.configStartStage = cfr_renamed_10;
        this.configEndStage = n6;
        this.checkedTreeConfigStage = n7;
        this.stumpConfigStage = var17_12;
        this.treeObjectId = var18_13;
    }

    public static FarmedTreeDefinition forSaplingId(int n) {
        return (FarmedTreeDefinition)((Object)definitionsBySaplingId.get(n));
    }

    public final int getRootItemId() {
        return this.rootItemId;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final int[] getProtectionPayment() {
        return this.protectionPayment;
    }

    public final double getDiseaseChance() {
        return this.diseaseChance;
    }

    public final double getPlantingExperience() {
        return this.plantingExperience;
    }

    public final double getHealthCheckExperience() {
        return this.healthCheckExperience;
    }

    public final int getConfigStartStage() {
        return this.configStartStage;
    }

    public final int getConfigEndStage() {
        return this.configEndStage;
    }

    public final int getGrowthCycleTicks() {
        FarmedTreeDefinition farmedTreeDefinition = this;
        FarmedTreeDefinition farmedTreeDefinition2 = farmedTreeDefinition;
        FarmedTreeDefinition farmedTreeDefinition3 = farmedTreeDefinition2 = this;
        farmedTreeDefinition3 = farmedTreeDefinition2;
        return farmedTreeDefinition.totalGrowthTicks / (farmedTreeDefinition2.configEndStage - farmedTreeDefinition3.configStartStage);
    }

    public final int getCheckedTreeConfigStage() {
        return this.checkedTreeConfigStage;
    }

    public final int getStumpConfigStage() {
        return this.stumpConfigStage;
    }

    public final int getTreeObjectId() {
        return this.treeObjectId;
    }
}

