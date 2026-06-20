/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.fishing;

import com.rs2.model.item.ItemStack;

public enum FishingSpotDefinition {
    SMALL_NET(new int[]{316}, 303, -1, 621, new int[]{1, 15}, new int[]{317, 321}, new double[]{10.0, 40.0}, new int[]{48, 24}, new int[]{256, 128}),
    BAIT(new int[]{316}, 307, 313, 622, new int[]{5, 10}, new int[]{327, 345}, new double[]{20.0, 30.0}, new int[]{32, 24}, new int[]{192, 128}),
    FLY_FISHING(new int[]{309}, 309, 314, 622, new int[]{20, 30}, new int[]{335, 331}, new double[]{50.0, 70.0}, new int[]{32, 16}, new int[]{192, 96}),
    RIVER_BAIT(new int[]{309}, 307, 313, 622, new int[]{25}, new int[]{349}, new double[]{60.0}, new int[]{16}, new int[]{96}),
    LOBSTER_POT(new int[]{312}, 301, -1, 619, new int[]{40}, new int[]{377}, new double[]{90.0}, new int[]{6}, new int[]{95}),
    HARPOON(new int[]{312}, 311, -1, 618, new int[]{35, 50}, new int[]{359, 371}, new double[]{80.0, 100.0}, new int[]{8, 4}, new int[]{64, 48}),
    BIG_NET(new int[]{313}, 305, -1, 620, new int[]{16, 16, 16, 16, 16, 16, 23, 46}, new int[]{353, 405, 1059, 407, 401, 1061, 341, 363}, new double[]{20.0, 10.0, 1.0, 10.0, 1.0, 1.0, 45.0, 100.0}, new int[]{5, 1, 5, 3, 10, 10, 4, 3}, new int[]{65, 2, 5, 7, 10, 10, 55, 40}),
    SHARK_HARPOON(new int[]{313}, 311, -1, 618, new int[]{76}, new int[]{383}, new double[]{110.0}, new int[]{3}, new int[]{40}),
    LAVA_EEL(new int[]{800}, 1585, 313, 622, new int[]{53}, new int[]{2148}, new double[]{0.0}, new int[]{16}, new int[]{96}),
    MONKFISH(new int[]{1174}, 303, -1, 621, new int[]{62}, new int[]{7944}, new double[]{120.0}, new int[]{48}, new int[]{90});

    private final int[] spotNpcIds;
    private final ItemStack toolItem;
    private final int[] requiredLevels;
    private final int[] chanceLowValues;
    private final int[] chanceHighValues;
    private final ItemStack[] catchItems;
    private final double[] experienceRewards;
    private final int animationId;
    private final ItemStack baitItem;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private FishingSpotDefinition(int n, int n2, int[] nArray3, int[] nArray4, double[] dArray, int[] nArray5, int[] nArray6) {
        void var11_9;
        void var10_8;
        this.spotNpcIds = (int[])n;
        this.toolItem = new ItemStack(n2);
        this.baitItem = nArray3 == -1 ? null : new ItemStack((int)nArray3);
        this.animationId = (int)nArray4;
        this.requiredLevels = (int[])dArray;
        ItemStack[] itemStackArray = new ItemStack[nArray5.length];
        int n3 = 0;
        while (n3 < nArray5.length) {
            itemStackArray[n3] = new ItemStack(nArray5[n3]);
            ++n3;
        }
        this.catchItems = itemStackArray;
        this.experienceRewards = nArray6;
        this.chanceLowValues = var10_8;
        this.chanceHighValues = var11_9;
    }

    public final ItemStack getBaitItem() {
        return this.baitItem;
    }

    public final ItemStack[] getCatchItems() {
        return this.catchItems;
    }

    public final int getAnimationId() {
        return this.animationId;
    }

    public final int[] getRequiredLevels() {
        return this.requiredLevels;
    }

    public final int[] getChanceLowValues() {
        return this.chanceLowValues;
    }

    public final int[] getChanceHighValues() {
        return this.chanceHighValues;
    }

    public final double[] getExperienceRewards() {
        return this.experienceRewards;
    }

    public final ItemStack getToolItem() {
        return this.toolItem;
    }

    public final int[] getSpotNpcIds() {
        return this.spotNpcIds;
    }

    public static FishingSpotDefinition forNpcIdAndOption(int n, int n2) {
        if (n2 == 1) {
            switch (n) {
                case 309: {
                    return FLY_FISHING;
                }
                case 312: {
                    return LOBSTER_POT;
                }
                case 316: {
                    return SMALL_NET;
                }
                case 313: {
                    return BIG_NET;
                }
                case 800: {
                    return LAVA_EEL;
                }
                case 1174: {
                    return MONKFISH;
                }
            }
        } else {
            switch (n) {
                case 309: {
                    return RIVER_BAIT;
                }
                case 312: {
                    return HARPOON;
                }
                case 316: {
                    return BAIT;
                }
                case 313: {
                    return SHARK_HARPOON;
                }
            }
        }
        return null;
    }
}

