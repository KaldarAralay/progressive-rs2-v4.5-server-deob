/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.farming;

import com.rs2.model.Position;
import com.rs2.model.skill.farming.FarmingPatchUtils;

public enum HerbPatch {
    a(0, new Position[]{new Position(2670, 3374), new Position(2671, 3375)}),
    b(1, new Position[]{new Position(3605, 3529), new Position(3606, 3530)}),
    c(2, new Position[]{new Position(3058, 3311), new Position(3059, 3312)}),
    d(3, new Position[]{new Position(2813, 3463), new Position(2814, 3464)});

    private int index;
    private Position[] bounds;

    /*
     * WARNING - void declaration
     */
    private HerbPatch() {
        void var4_1;
        void var3_2;
        this.index = var3_2;
        this.bounds = var4_1;
    }

    public static HerbPatch forPosition(Position position) {
        HerbPatch[] herbPatchArray = HerbPatch.values();
        int n = herbPatchArray.length;
        int n2 = 0;
        while (n2 < n) {
            HerbPatch herbPatch;
            HerbPatch herbPatch2 = herbPatch = herbPatchArray[n2];
            herbPatch2 = herbPatch;
            if (FarmingPatchUtils.a(herbPatch.bounds[0], herbPatch2.bounds[1], position)) {
                return herbPatch;
            }
            ++n2;
        }
        return null;
    }

    public final int getIndex() {
        return this.index;
    }
}

