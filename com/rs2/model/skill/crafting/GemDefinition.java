/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.crafting;

import java.util.HashMap;

public enum GemDefinition {
    SAPPHIRE(1623, 1607, 20, 888, 50.0),
    EMERALD(1621, 1605, 27, 889, 67.0),
    RUBY(1619, 1603, 34, 887, 85.0),
    DIAMOND(1617, 1601, 43, 886, 107.5),
    DRAGONSTONE(1631, 1615, 55, 885, 137.5),
    ONYX(6571, 6573, 67, 885, 168.0),
    OPAL(1625, 1609, 1, 891, 15.0),
    JADE(1627, 1611, 13, 890, 20.0),
    RED_TOPAZ(1629, 1613, 16, 887, 25.0);

    private short uncutItemId;
    private short cutItemId;
    private byte requiredLevel;
    private short animationId;
    private double experience;
    private static HashMap definitionsByUncutItemId;

    static {
        definitionsByUncutItemId = new HashMap();
        GemDefinition[] gemDefinitionArray = GemDefinition.values();
        int n = gemDefinitionArray.length;
        int n2 = 0;
        while (n2 < n) {
            GemDefinition gemDefinition;
            GemDefinition gemDefinition2 = gemDefinition = gemDefinitionArray[n2];
            definitionsByUncutItemId.put(Integer.valueOf(gemDefinition2.uncutItemId), gemDefinition);
            ++n2;
        }
    }

    public static GemDefinition forUncutItemId(int n) {
        return (GemDefinition)((Object)definitionsByUncutItemId.get(n));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private GemDefinition(int n3, int n4, double d) {
        void var7_5;
        void var6_4;
        this.uncutItemId = (short)n3;
        this.cutItemId = (short)n4;
        this.requiredLevel = (byte)d;
        this.animationId = (short)var6_4;
        this.experience = var7_5;
    }

    public final int getCutItemId() {
        return this.cutItemId;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final int getAnimationId() {
        return this.animationId;
    }

    public final double getExperience() {
        return this.experience;
    }
}

