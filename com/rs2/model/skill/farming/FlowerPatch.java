/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.farming;

import com.rs2.model.Position;
import com.rs2.model.skill.farming.FarmingPatchUtils;

public enum FlowerPatch {
    ARDOUGNE(0, new Position[]{new Position(2666, 3374), new Position(2667, 3375)}),
    MORYTANIA(1, new Position[]{new Position(3601, 3525), new Position(3602, 3526)}),
    FALADOR(2, new Position[]{new Position(3054, 3307), new Position(3055, 3308)}),
    CATHERBY(3, new Position[]{new Position(2809, 3463), new Position(2810, 3464)});

    private int index;
    private Position[] bounds;

    /*
     * WARNING - void declaration
     */
    private FlowerPatch() {
        void var4_1;
        void var3_2;
        this.index = var3_2;
        this.bounds = var4_1;
    }

    public static FlowerPatch forPosition(Position position) {
        FlowerPatch[] flowerPatchArray = FlowerPatch.values();
        int n = flowerPatchArray.length;
        int n2 = 0;
        while (n2 < n) {
            FlowerPatch flowerPatch;
            FlowerPatch flowerPatch2 = flowerPatch = flowerPatchArray[n2];
            flowerPatch2 = flowerPatch;
            if (FarmingPatchUtils.containsPosition(flowerPatch.bounds[0], flowerPatch2.bounds[1], position)) {
                return flowerPatch;
            }
            ++n2;
        }
        return null;
    }

    public final int getIndex() {
        return this.index;
    }
}

