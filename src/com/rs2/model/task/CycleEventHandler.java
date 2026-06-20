/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.task;

import com.rs2.model.Entity;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.ProfilerRegistry;
import com.rs2.util.ProfilerTimer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public final class CycleEventHandler {
    private static CycleEventHandler a;
    private Queue b = new LinkedList();
    private Queue c = new LinkedList();

    public static CycleEventHandler getInstance() {
        if (a == null) {
            a = new CycleEventHandler();
        }
        return a;
    }

    public final CycleEventContainer schedule(Entity entity, CycleEvent object, int n) {
        object = new CycleEventContainer(entity, (CycleEvent)object, n);
        if (entity.getIndex() == -1) {
            return object;
        }
        this.c.add(object);
        return object;
    }

    public final void process() {
        ProfilerTimer profilerTimer = ProfilerRegistry.getTimer("cycleEvents");
        profilerTimer.start();
        this.b.addAll(this.c);
        this.c.clear();
        Iterator iterator = this.b.iterator();
        while (iterator.hasNext()) {
            CycleEventContainer cycleEventContainer = (CycleEventContainer)iterator.next();
            if (cycleEventContainer == null) continue;
            if (cycleEventContainer.getEntity() == null || cycleEventContainer.getEntity().getIndex() == -1) {
                iterator.remove();
                continue;
            }
            if (cycleEventContainer.tick()) {
                cycleEventContainer.execute();
            }
            if (cycleEventContainer.isActive()) continue;
            iterator.remove();
        }
        profilerTimer.stop();
    }
}

