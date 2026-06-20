/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.farming;

import com.rs2.model.Position;
import com.rs2.model.skill.farming.FarmingPatchUtils;

public enum SpecialTreePatch {
    a(0, new Position[]{new Position(2801, 3202), new Position(2803, 3204)}, 5375),
    b(1, new Position[]{new Position(2795, 3100), new Position(2797, 3102)}, 5503),
    c(2, new Position[]{new Position(3059, 3257), new Position(3061, 3259)}, 5375),
    d(3, new Position[]{new Position(2612, 3857), new Position(2614, 3859)}, 5375);

    private int index;
    private Position[] bounds;
    private int objectId;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private SpecialTreePatch(int n3) {
        void var5_3;
        void var4_2;
        this.index = n3;
        this.bounds = var4_2;
        this.objectId = var5_3;
    }

    public static SpecialTreePatch forPosition(Position position) {
        SpecialTreePatch[] specialTreePatchArray = SpecialTreePatch.values();
        int n = specialTreePatchArray.length;
        int n2 = 0;
        while (n2 < n) {
            SpecialTreePatch specialTreePatch;
            SpecialTreePatch specialTreePatch2 = specialTreePatch = specialTreePatchArray[n2];
            specialTreePatch2 = specialTreePatch;
            if (FarmingPatchUtils.a(specialTreePatch.bounds[0], specialTreePatch2.bounds[1], position)) {
                return specialTreePatch;
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

