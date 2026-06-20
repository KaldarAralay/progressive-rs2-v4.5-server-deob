/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.crafting;

public enum JewelleryDefinition {
    GOLD(2357, -1, -1.0, 5, 15.0, 1635, 6, 20.0, 1654, 8, 30.0, 1673, 1692),
    SAPPHIRE(1607, 20, 50.0, 20, 40.0, 1637, 22, 55.0, 1656, 24, 65.0, 1675, 1694),
    EMERALD(1605, 27, 67.0, 27, 55.0, 1639, 29, 60.0, 1658, 31, 70.0, 1677, 1696),
    RUBY(1603, 34, 85.0, 34, 70.0, 1641, 40, 75.0, 1660, 50, 85.0, 1679, 1698),
    DIAMOND(1601, 43, 107.5, 43, 85.0, 1643, 56, 90.0, 1662, 70, 100.0, 1681, 1700),
    DRAGONSTONE(1615, 55, 137.5, 55, 100.0, 1645, 72, 105.0, 1664, 80, 150.0, 1683, 1702),
    ONYX(6573, 67, 167.5, 67, 115.0, 6575, 82, 120.0, 6577, 90, 165.0, 6579, 6581);

    private int[] recipeData = new int[13];

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private JewelleryDefinition(double d, int n3, double d2, int n4, int n5, double n2222, int n6, int n2222, double d3, int n7, int n2222) {
        void var19_14;
        void var18_13;
        void var15_11;
        void var11_8;
        void cfr_renamed_4;
        void var7_5;
        void var4_2;
        this.recipeData[0] = (int)d;
        this.recipeData[1] = var4_2;
        this.recipeData[2] = n3;
        this.recipeData[3] = var7_5;
        this.recipeData[4] = n4;
        this.recipeData[5] = cfr_renamed_4;
        this.recipeData[6] = var11_8;
        this.recipeData[7] = n6;
        this.recipeData[8] = (int)d3;
        this.recipeData[9] = var15_11;
        this.recipeData[10] = n7;
        this.recipeData[11] = var18_13;
        this.recipeData[12] = var19_14;
    }

    static /* synthetic */ int[] getRecipeData(JewelleryDefinition jewelleryDefinition) {
        return jewelleryDefinition.recipeData;
    }
}

