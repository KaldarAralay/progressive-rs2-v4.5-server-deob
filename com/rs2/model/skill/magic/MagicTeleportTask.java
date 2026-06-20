/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.magic;

import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.TeleportManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class MagicTeleportTask
extends CycleEvent {
    private int a = 6;
    private /* synthetic */ TeleportManager b;
    private final /* synthetic */ boolean c;
    private final /* synthetic */ int d;
    private final /* synthetic */ int e;
    private final /* synthetic */ int f;

    MagicTeleportTask(TeleportManager teleportManager, boolean bl, int n, int n2, int n3) {
        this.b = teleportManager;
        this.c = bl;
        this.d = n;
        this.e = n2;
        this.f = n3;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        --this.a;
        if (!TeleportManager.a(this.b).isDead()) {
            if (this.a == 2) {
                if (!this.c) {
                    TeleportManager.a(this.b).getUpdateState().setAnimation(715);
                }
                if (TeleportManager.a(this.b).s()) {
                    TeleportManager.a(this.b).aK();
                }
                if (TeleportManager.a(this.b).getInventoryManager().containsItem(4033)) {
                    int n = TeleportManager.a(this.b).getInventoryManager().getItemAmount(4033);
                    ItemStack itemStack = new ItemStack(4033, n);
                    TeleportManager.a(this.b).getInventoryManager().removeItem(itemStack);
                }
                if (TeleportManager.a(this.b).getInventoryManager().containsItem(431)) {
                    int n = TeleportManager.a(this.b).getInventoryManager().getItemAmount(431);
                    Object object = new ItemStack(431, n);
                    TeleportManager.a(this.b).getInventoryManager().removeItem((ItemStack)object);
                    object = TeleportManager.a(this.b);
                    ((Player)object).packetSender.sendGameMessage("Why is the rum gone?");
                }
                TeleportManager.a(this.b).moveTo(new Position(this.d, this.e, this.f));
            }
        } else {
            this.a = 0;
        }
        if (this.a <= 0) {
            cycleEventContainer.stop();
        }
    }

    @Override
    public final void onStop() {
        TeleportManager.a(this.b).n(false);
        TeleportManager.a(this.b).getAttributes().put("canTakeDamage", Boolean.TRUE);
    }
}

