/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.duel;

import com.rs2.model.gameplay.duel.DuelArenaLocationManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class DuelVictoryTask
extends CycleEvent {
    private final /* synthetic */ Player a;
    private final /* synthetic */ String b;
    private final /* synthetic */ String c;
    private final /* synthetic */ ItemStack[] d;

    DuelVictoryTask(Player player, String string, String string2, ItemStack[] itemStackArray) {
        this.a = player;
        this.b = string;
        this.c = string2;
        this.d = itemStackArray;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (this.a != null) {
            this.a.getDuelArenaLocationManager();
            this.a.moveTo(DuelArenaLocationManager.a());
            Object object = this.a;
            ((Player)object).packetSender.sendInterfaceText(this.b, 6840);
            object = this.a;
            ((Player)object).packetSender.sendInterfaceText(this.c, 6839);
            object = this.a;
            ((Player)object).packetSender.sendItemContainer(6822, this.d);
            object = this.a;
            ((Player)object).packetSender.sendEntityHintIcon(10, -1);
            ItemStack[] itemStackArray = this.d;
            int n = this.d.length;
            int n2 = 0;
            while (n2 < n) {
                object = itemStackArray[n2];
                this.a.getInventoryManager().b((ItemStack)object);
                ++n2;
            }
            this.a.eq();
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        if (this.a != null) {
            this.a.n(false);
            Player player = this.a;
            player.packetSender.showInterface(6733);
            player = this.a;
            player.packetSender.sendMusicJingle(221, 200);
        }
    }
}

