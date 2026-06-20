/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.update;

import com.rs2.model.update.EntityUpdateOverrideType;

/*
 * Exception performing whole class analysis.
 */
public final class EntityUpdateOverrideDefinition
extends Enum {
    private int b;
    private int c;
    private EntityUpdateOverrideType d;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private EntityUpdateOverrideDefinition(EntityUpdateOverrideType entityUpdateOverrideType) {
        void cfr_renamed_16;
        void cfr_renamed_1;
        this.b = 1080;
        this.c = 1658;
        this.d = cfr_renamed_16;
    }

    public static EntityUpdateOverrideDefinition forOriginalIdAndType(int n, EntityUpdateOverrideType entityUpdateOverrideType) {
        EntityUpdateOverrideDefinition[] entityUpdateOverrideDefinitionArray = EntityUpdateOverrideDefinition.values();
        int n2 = entityUpdateOverrideDefinitionArray.length;
        int n3 = 0;
        while (n3 < n2) {
            EntityUpdateOverrideDefinition entityUpdateOverrideDefinition = entityUpdateOverrideDefinitionArray[n3];
            if (entityUpdateOverrideDefinition.c == n && entityUpdateOverrideType == entityUpdateOverrideDefinition.d) {
                return entityUpdateOverrideDefinition;
            }
            ++n3;
        }
        return null;
    }

    public final int getReplacementId() {
        return this.b;
    }
}

