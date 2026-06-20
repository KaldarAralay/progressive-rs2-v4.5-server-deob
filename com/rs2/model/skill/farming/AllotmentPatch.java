/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.farming;

import com.rs2.model.Position;
import com.rs2.model.skill.farming.FarmingPatchUtils;
import java.util.ArrayList;

public enum AllotmentPatch {
    a(0, new Position[]{new Position(2805, 3466), new Position(2806, 3468), new Position(2805, 3467), new Position(2814, 3468)}, 2324),
    b(1, new Position[]{new Position(2805, 3459), new Position(2806, 3461), new Position(2805, 3459), new Position(2814, 3460)}, 2324),
    c(2, new Position[]{new Position(3050, 3307), new Position(3051, 3312), new Position(3050, 3311), new Position(3054, 3312)}, 2323),
    d(3, new Position[]{new Position(3055, 3303), new Position(3059, 3304), new Position(3058, 3303), new Position(3059, 3308)}, 2323),
    e(4, new Position[]{new Position(3597, 3525), new Position(3598, 3530), new Position(3597, 3529), new Position(3601, 3530)}, 2326),
    f(5, new Position[]{new Position(3602, 3521), new Position(3606, 3522), new Position(3605, 3521), new Position(3606, 3526)}, 2326),
    g(6, new Position[]{new Position(2662, 3377), new Position(2663, 3379), new Position(2662, 3378), new Position(2671, 3379)}, 2325),
    h(7, new Position[]{new Position(2662, 3370), new Position(2663, 3372), new Position(2662, 3370), new Position(2671, 3371)}, 2325);

    private int index;
    private Position[] bounds;
    private int objectId;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private AllotmentPatch(int n3) {
        void var5_3;
        void var4_2;
        this.index = n3;
        this.bounds = var4_2;
        this.objectId = var5_3;
    }

    public static AllotmentPatch forPosition(Position position) {
        AllotmentPatch[] allotmentPatchArray = AllotmentPatch.values();
        int n = allotmentPatchArray.length;
        int n2 = 0;
        while (n2 < n) {
            block4: {
                AllotmentPatch allotmentPatch;
                block3: {
                    AllotmentPatch allotmentPatch2;
                    AllotmentPatch allotmentPatch3 = allotmentPatch = allotmentPatchArray[n2];
                    allotmentPatch3 = allotmentPatch;
                    if (FarmingPatchUtils.a(allotmentPatch.bounds[0], allotmentPatch3.bounds[1], position)) break block3;
                    allotmentPatch3 = allotmentPatch;
                    allotmentPatch3 = allotmentPatch;
                    if (!FarmingPatchUtils.a(allotmentPatch2.bounds[2], allotmentPatch3.bounds[3], position)) break block4;
                }
                return allotmentPatch;
            }
            ++n2;
        }
        return null;
    }

    public static ArrayList getIndexesForObjectId(int n) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        AllotmentPatch[] allotmentPatchArray = AllotmentPatch.values();
        int n2 = allotmentPatchArray.length;
        int n3 = 0;
        while (n3 < n2) {
            AllotmentPatch allotmentPatch;
            AllotmentPatch allotmentPatch2 = allotmentPatch = allotmentPatchArray[n3];
            if (allotmentPatch.objectId == n) {
                arrayList.add(allotmentPatch.index);
            }
            ++n3;
        }
        return arrayList;
    }

    public final int getIndex() {
        return this.index;
    }
}

