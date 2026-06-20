/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.slayer;

import com.rs2.model.skill.slayer.SlayerAssignmentDefinition;
import com.rs2.model.skill.slayer.SlayerManager;
import java.util.HashMap;
import java.util.Map;

public enum SlayerMasterDefinition {
    BURTHORPE(70, 3, 15, 50, "Burthorpe", SlayerManager.BURTHORPE_ASSIGNMENTS),
    CANIFIS(1596, 20, 40, 70, "Canifis", SlayerManager.CANIFIS_ASSIGNMENTS),
    EDGEVILLE_DUNGEON(1597, 40, 60, 120, "Edgeville dungeon", SlayerManager.EDGEVILLE_DUNGEON_ASSIGNMENTS),
    ZANARIS(1598, 70, 110, 170, "Zanaris", SlayerManager.ZANARIS_ASSIGNMENTS),
    EDGEVILLE(3887, 60, 40, 80, "Edge village", SlayerManager.EDGEVILLE_ASSIGNMENTS),
    SHILO_VILLAGE(1599, 100, 130, 200, "Shilo village", SlayerManager.SHILO_VILLAGE_ASSIGNMENTS);

    private int npcId;
    private int requiredCombatLevel;
    private String locationName;
    private SlayerAssignmentDefinition[] assignments;
    private static Map definitionsByNpcId;

    static {
        definitionsByNpcId = new HashMap();
        SlayerMasterDefinition[] slayerMasterDefinitionArray = SlayerMasterDefinition.values();
        int n = slayerMasterDefinitionArray.length;
        int n2 = 0;
        while (n2 < n) {
            SlayerMasterDefinition slayerMasterDefinition = slayerMasterDefinitionArray[n2];
            definitionsByNpcId.put(slayerMasterDefinition.npcId, slayerMasterDefinition);
            ++n2;
        }
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private SlayerMasterDefinition(int n3, int n4, String string, SlayerAssignmentDefinition[] slayerAssignmentDefinitionArray) {
        void var8_6;
        void var7_5;
        this.npcId = n3;
        this.requiredCombatLevel = n4;
        this.locationName = var7_5;
        this.assignments = var8_6;
    }

    public static SlayerMasterDefinition forNpcId(int n) {
        return (SlayerMasterDefinition)((Object)definitionsByNpcId.get(n));
    }

    public final int getNpcId() {
        return this.npcId;
    }

    public final int getRequiredCombatLevel() {
        return this.requiredCombatLevel;
    }

    public final String getLocationName() {
        return this.locationName;
    }

    public final SlayerAssignmentDefinition[] getAssignments() {
        return this.assignments;
    }
}

