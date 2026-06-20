/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.abyss;

import com.rs2.model.gameplay.abyss.AbyssManager;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.LoadedWorldObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;

final class AbyssObstacleEvent
extends CycleEvent {
    private int a;
    private final /* synthetic */ Player b;
    private final /* synthetic */ String c;
    private final /* synthetic */ int d;
    private final /* synthetic */ int e;
    private final /* synthetic */ int f;
    private final /* synthetic */ LoadedWorldObject g;
    private final /* synthetic */ int h;
    private final /* synthetic */ int i;
    private final /* synthetic */ int j;

    AbyssObstacleEvent(int n, Player player, String string, int n2, int n3, int n4, LoadedWorldObject loadedWorldObject, int n5, int n6, int n7) {
        this.b = player;
        this.c = string;
        this.d = n2;
        this.e = n3;
        this.f = n4;
        this.g = loadedWorldObject;
        this.h = n5;
        this.i = n6;
        this.j = n7;
        this.a = n;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        AbyssManager.b(this.b, this.c);
        if (this.a == 2) {
            if (GameUtil.g(3) == 0) {
                --this.a;
                new DynamicObject(this.d, this.e, this.f, this.b.getPosition().getPlane(), this.g.getOrientation(), this.g.getType(), this.g.getWorldObject().getObjectId(), 1000);
                return;
            }
            AbyssManager.c(this.b, this.c);
            cycleEventContainer.stop();
            return;
        }
        if (this.a == 1) {
            --this.a;
            ObjectManager.getInstance().removeDynamicObjectAt(this.e, this.f, this.b.getPosition().getPlane(), 10);
            new DynamicObject(this.h, this.e, this.f, this.b.getPosition().getPlane(), this.g.getOrientation(), this.g.getType(), this.g.getWorldObject().getObjectId(), 20);
            return;
        }
        if (this.a == 0) {
            AbyssManager.a(this.b, this.c);
            AbyssManager.a(this.b, this.i, this.j);
            cycleEventContainer.stop();
        }
    }

    @Override
    public final void onStop() {
        this.b.n(false);
    }
}

