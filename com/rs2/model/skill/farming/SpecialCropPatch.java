/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.farming;

import com.rs2.model.Position;
import com.rs2.model.skill.farming.FarmingPatchUtils;

public enum SpecialCropPatch {
    a(0, new Position[]{new Position(3086, 3354), new Position(3087, 3355)}, 5281),
    b(2, new Position[]{new Position(3315, 3202), new Position(3316, 3203)}, 5280),
    c(3, new Position[]{new Position(3451, 3472), new Position(3452, 3473)}, 5282);

    private int index;
    private Position[] bounds;
    private int objectId;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private SpecialCropPatch(int n3) {
        void var5_3;
        void var4_2;
        this.index = n3;
        this.bounds = var4_2;
        this.objectId = var5_3;
    }

    public static SpecialCropPatch forPosition(Position position) {
        SpecialCropPatch[] specialCropPatchArray = SpecialCropPatch.values();
        int n = specialCropPatchArray.length;
        int n2 = 0;
        while (n2 < n) {
            SpecialCropPatch specialCropPatch;
            SpecialCropPatch specialCropPatch2 = specialCropPatch = specialCropPatchArray[n2];
            specialCropPatch2 = specialCropPatch;
            if (FarmingPatchUtils.a(specialCropPatch.bounds[0], specialCropPatch2.bounds[1], position)) {
                return specialCropPatch;
            }
            ++n2;
        }
        return null;
    }

    public final int getIndex() {
        return this.index;
    }

    public final int getObjectId() {
        return this.objectId;
    }
}

