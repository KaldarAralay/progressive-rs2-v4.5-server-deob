/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.ElementalWorkshopQuest;
import com.rs2.model.task.TickTask;

final class ElementalShieldSmithingTask
extends TickTask {
    private /* synthetic */ ElementalWorkshopQuest a;
    private final /* synthetic */ Player b;
    private final /* synthetic */ int c;

    ElementalShieldSmithingTask(ElementalWorkshopQuest elementalWorkshopQuest, int n, Player player, int n2) {
        this.a = elementalWorkshopQuest;
        this.b = player;
        this.c = n2;
        super(3);
    }

    @Override
    public final void execute() {
        this.b.getInventoryManager().a(new ItemStack(2893, 1), new ItemStack(2890, 1));
        if (this.c != 1) {
            this.a.c(this.b);
        }
        this.stop();
    }
}

