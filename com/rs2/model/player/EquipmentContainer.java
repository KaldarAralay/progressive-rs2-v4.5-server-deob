/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.player;

import com.rs2.model.item.ItemContainer;
import com.rs2.model.item.ItemContainerType;
import com.rs2.model.player.EquipmentManager;

final class EquipmentContainer
extends ItemContainer {
    private /* synthetic */ EquipmentManager a;

    EquipmentContainer(EquipmentManager equipmentManager, ItemContainerType itemContainerType, int n) {
        this.a = equipmentManager;
        super(itemContainerType, 14);
    }

    public final void i() {
        super.clear();
        EquipmentManager.getPlayer(this.a).getEquipmentManager().refreshCarriedValue();
    }
}

