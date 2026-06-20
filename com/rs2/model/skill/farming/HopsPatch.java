/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.farming;

import com.rs2.model.Position;
import com.rs2.model.skill.farming.FarmingPatchUtils;
import java.util.HashMap;
import java.util.Map;

public enum HopsPatch {
    LUMBRIDGE(0, new Position[]{new Position(3227, 3313, 0), new Position(3231, 3317, 0)}, 2333),
    SEERS_VILLAGE(1, new Position[]{new Position(2664, 3523, 0), new Position(2669, 3528, 0)}, 2334),
    YANILLE(2, new Position[]{new Position(2574, 3103, 0), new Position(2577, 3106, 0)}, 2332),
    ENTRANA(3, new Position[]{new Position(2809, 3335, 0), new Position(2812, 3338, 0)}, 2327);

    private int index;
    private Position[] bounds;
    private int objectId;
    private static Map patchByObjectId;

    static {
        patchByObjectId = new HashMap();
        HopsPatch[] hopsPatchArray = HopsPatch.values();
        int n = hopsPatchArray.length;
        int n2 = 0;
        while (n2 < n) {
            HopsPatch hopsPatch = hopsPatchArray[n2];
            patchByObjectId.put(hopsPatch.objectId, hopsPatch);
            ++n2;
        }
    }

    public static HopsPatch forObjectId(int n) {
        return (HopsPatch)((Object)patchByObjectId.get(n));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private HopsPatch(int n3) {
        void var5_3;
        void var4_2;
        this.index = n3;
        this.bounds = var4_2;
        this.objectId = var5_3;
    }

    public static HopsPatch forPosition(Position position) {
        HopsPatch[] hopsPatchArray = HopsPatch.values();
        int n = hopsPatchArray.length;
        int n2 = 0;
        while (n2 < n) {
            HopsPatch hopsPatch;
            HopsPatch hopsPatch2 = hopsPatch = hopsPatchArray[n2];
            hopsPatch2 = hopsPatch;
            if (FarmingPatchUtils.containsPosition(hopsPatch.bounds[0], hopsPatch2.bounds[1], position)) {
                return hopsPatch;
            }
            ++n2;
        }
        return null;
    }

    public final int getIndex() {
        return this.index;
    }
}

