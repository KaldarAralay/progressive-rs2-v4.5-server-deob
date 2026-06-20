/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.partyroom;

import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.gameplay.partyroom.PartyRoomBalloonReward;
import com.rs2.model.gameplay.partyroom.PartyRoomManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

final class PartyRoomBalloonSpawnTask
extends TickTask {
    private final /* synthetic */ ArrayList a;
    private final /* synthetic */ ArrayList b;

    PartyRoomBalloonSpawnTask(int n, ArrayList arrayList, ArrayList arrayList2) {
        this.a = arrayList;
        this.b = arrayList2;
        super(n);
    }

    @Override
    public final void execute() {
        int n = 0;
        for (Position position : this.a) {
            ItemStack itemStack;
            PartyRoomManager.d.add(new DynamicObject(115 + GameUtil.h(7), position.getX(), position.getY(), 0, 0 + GameUtil.h(3), 10, ServerSettings.placeholderObjectId, 1000, false));
            if (n < this.b.size() && (itemStack = (ItemStack)this.b.get(n)) != null) {
                new PartyRoomBalloonReward(itemStack, position, 0);
            }
            ++n;
        }
        this.stop();
        PartyRoomManager.c.clear();
        PartyRoomManager.a = false;
        PartyRoomManager.f = 0;
        PartyRoomManager.d();
        PartyRoomManager.b();
    }
}

