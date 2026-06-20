/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill;

import com.rs2.model.skill.GatheringToolDefinition;
import java.util.Comparator;

final class GatheringToolComparator
implements Comparator {
    private final /* synthetic */ int a;

    GatheringToolComparator(int n) {
        this.a = n;
    }

    public final /* synthetic */ int compare(Object object, Object object2) {
        GatheringToolDefinition gatheringToolDefinition = (GatheringToolDefinition)((Object)object2);
        object2 = (GatheringToolDefinition)((Object)object);
        object = this;
        if (((GatheringToolComparator)object).a == 14) {
            double d = ((GatheringToolDefinition)((Object)object2)).f();
            double d2 = gatheringToolDefinition.f();
            return Double.compare(d2, d);
        }
        double d = ((GatheringToolDefinition)((Object)object2)).f();
        double d3 = gatheringToolDefinition.f();
        return Double.compare(d, d3);
    }
}

