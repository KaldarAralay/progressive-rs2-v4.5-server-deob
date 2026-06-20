/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.partyroom;

import com.rs2.model.Position;
import com.rs2.model.gameplay.partyroom.PartyRoomManager;
import com.rs2.model.item.ItemStack;

public final class PartyRoomBalloonReward {
    ItemStack a;
    Position b;

    private PartyRoomBalloonReward(ItemStack itemStack, Position position) {
        this.a = itemStack;
        this.b = position;
        PartyRoomManager.e.add(this);
    }

    /* synthetic */ PartyRoomBalloonReward(ItemStack itemStack, Position position, byte by) {
        this(itemStack, position);
    }
}

