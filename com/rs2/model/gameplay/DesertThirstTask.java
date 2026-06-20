/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay;

import com.rs2.model.combat.hit.HitType;
import com.rs2.model.gameplay.DesertHeatManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

final class DesertThirstTask
extends TickTask {
    private final /* synthetic */ Player a;

    DesertThirstTask(int n, Player player) {
        this.a = player;
        super(150);
    }

    @Override
    public final void execute() {
        if (!this.a.bW()) {
            this.stop();
            return;
        }
        if (DesertHeatManager.a(this.a)) {
            int n = 0;
            if (this.a.getInventoryManager().containsItem(1829)) {
                n = 1829;
            } else if (this.a.getInventoryManager().containsItem(1827)) {
                n = 1827;
            } else if (this.a.getInventoryManager().containsItem(1825)) {
                n = 1825;
            } else if (this.a.getInventoryManager().containsItem(1823)) {
                n = 1823;
            }
            if (n != 0) {
                Player player = this.a;
                player.packetSender.sendGameMessage("You take a drink of water.");
                this.a.getUpdateState().setAnimation(829);
                this.a.getInventoryManager().removeItem(new ItemStack(n, 1));
                this.a.getInventoryManager().b(new ItemStack(n + 2, 1));
            } else {
                this.a.applyDirectHit(1 + GameUtil.g(9), HitType.NORMAL);
                Player player = this.a;
                player.packetSender.sendGameMessage("You should get a waterskin for any travelling in the desert.");
                player = this.a;
                player.packetSender.sendGameMessage("You start dying of thirst while you're in the desert.");
            }
            this.setIntervalTicks(150);
            if (this.a.getEquipmentManager().getItemIdAtSlot(4) == DesertHeatManager.a) {
                this.setIntervalTicks(this.getIntervalTicks() + 20);
            }
            if (this.a.getEquipmentManager().getItemIdAtSlot(7) == DesertHeatManager.b) {
                this.setIntervalTicks(this.getIntervalTicks() + 20);
            }
            if (this.a.getEquipmentManager().getItemIdAtSlot(10) == DesertHeatManager.c) {
                this.setIntervalTicks(this.getIntervalTicks() + 10);
            }
            if (this.a.getEquipmentManager().getItemIdAtSlot(0) == DesertHeatManager.d || this.a.getEquipmentManager().getItemIdAtSlot(0) == DesertHeatManager.h) {
                this.setIntervalTicks(this.getIntervalTicks() + 20);
            }
            if (this.a.getEquipmentManager().getItemIdAtSlot(4) == DesertHeatManager.e || this.a.getEquipmentManager().getItemIdAtSlot(4) == DesertHeatManager.i) {
                this.setIntervalTicks(this.getIntervalTicks() + 20);
            }
            if (this.a.getEquipmentManager().getItemIdAtSlot(7) == DesertHeatManager.f || this.a.getEquipmentManager().getItemIdAtSlot(7) == DesertHeatManager.j) {
                this.setIntervalTicks(this.getIntervalTicks() + 20);
            }
            if (this.a.getEquipmentManager().getItemIdAtSlot(7) == DesertHeatManager.g || this.a.getEquipmentManager().getItemIdAtSlot(7) == DesertHeatManager.k) {
                this.setIntervalTicks(this.getIntervalTicks() + 20);
                return;
            }
        } else {
            this.a.er = -1;
            this.stop();
        }
    }
}

