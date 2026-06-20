/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.duel;

import com.rs2.model.gameplay.duel.DuelSession;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;
import com.rs2.util.TextUtil;

public final class DuelInterfaceManager {
    private Player a;
    private int b = 0;
    private static int[] c = new int[]{8242, 8243, 8244, 8245, 8246, 8247, 8248, 8249, 8251, 8252, 8253};
    private static final int[] d = new int[]{1, 2, 16, 32, 64, 128, 256, 512, 1024, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 0x200000, 0x800000, 0x1000000, 0x4000000, 0x8000000, 0x10000000};

    public DuelInterfaceManager(Player player) {
        this.a = player;
    }

    public final void a() {
        this.f();
        Player player = this.a;
        player.packetSender.showInterface(6575);
        String string = "duel";
        player = this.a;
        this.a.interfaceAction = string;
        player = this.a;
        player.packetSender.sendInterfaceText("Dueling with:  " + this.a.getDuelSession().i().getUsername() + "  Opponent's combat level: " + TextUtil.formatCombatLevel(this.a.getCombatLevel(), this.a.getDuelSession().i().getCombatLevel()), 6671);
        player = this.a;
        player.packetSender.sendItemContainer(13824, this.a.getEquipmentManager().getContainer().getRawItems());
        this.e();
        this.c();
    }

    public final void b() {
        Object object;
        block10: {
            Player player = this.a;
            player.packetSender.showInterface(6412);
            object = "duel2";
            player = this.a;
            this.a.interfaceAction = object;
            this.a.getDuelController().b(false);
            object = this;
            player = ((DuelInterfaceManager)object).a;
            player.packetSender.sendInterfaceText("Hitpoints will be restored.", 8250);
            player = ((DuelInterfaceManager)object).a;
            player.packetSender.sendInterfaceText("Boosted stats will be restored.", 8238);
            int n = 11;
            while (n < ((DuelInterfaceManager)object).a.getDuelSession().k().length) {
                if (((DuelInterfaceManager)object).a.getDuelSession().k()[n]) {
                    player = ((DuelInterfaceManager)object).a;
                    player.packetSender.sendInterfaceText("Some worn items will be taken off", 8239);
                    break block10;
                }
                ++n;
            }
            player = ((DuelInterfaceManager)object).a;
            player.packetSender.sendInterfaceText("", 8240);
            player = ((DuelInterfaceManager)object).a;
            player.packetSender.sendInterfaceText("", 8241);
            int[] nArray = c;
            int n2 = 0;
            while (n2 < 11) {
                n = nArray[n2];
                player = ((DuelInterfaceManager)object).a;
                player.packetSender.sendInterfaceText("", n);
                ++n2;
            }
        }
        object = this;
        String string = ((DuelInterfaceManager)object).a.getDuelSession().h().size() <= 0 ? "Absolutely nothing!" : "";
        ItemStack[] itemStackArray = DuelSession.a(((DuelInterfaceManager)object).a.getDuelSession().h());
        int n = itemStackArray.length;
        int n3 = 0;
        while (n3 < n) {
            ItemStack itemStack = itemStackArray[n3];
            if (itemStack != null) {
                string = itemStack.getDefinition().isStackable() || itemStack.getDefinition().isNote() ? String.valueOf(string) + itemStack.getDefinition().getName() + " x @cya@" + GameUtil.j(itemStack.getAmount()) + "\\n" : String.valueOf(string) + itemStack.getDefinition().getName() + "\\n";
            }
            ++n3;
        }
        Player player = ((DuelInterfaceManager)object).a;
        player.packetSender.sendInterfaceText(string, 6516);
        string = ((DuelInterfaceManager)object).a.getDuelSession().i().getDuelSession().h().size() <= 0 ? "Absolutely nothing!" : "";
        itemStackArray = DuelSession.a(((DuelInterfaceManager)object).a.getDuelSession().i().getDuelSession().h());
        n = itemStackArray.length;
        int n4 = 0;
        while (n4 < n) {
            ItemStack itemStack = itemStackArray[n4];
            if (itemStack != null) {
                string = itemStack.getDefinition().isStackable() || itemStack.getDefinition().isNote() ? String.valueOf(string) + itemStack.getDefinition().getName() + " x @cya@" + GameUtil.j(itemStack.getAmount()) + "\\n" : String.valueOf(string) + itemStack.getDefinition().getName() + "\\n";
            }
            ++n4;
        }
        Player player2 = ((DuelInterfaceManager)object).a;
        player2.packetSender.sendInterfaceText(string, 6517);
        if (this.a.getDuelSession().l().size() == 0) {
            player2 = this.a;
            player2.packetSender.sendInterfaceText("Everything will be allowed!", c[0]);
        } else {
            int n5 = 0;
            while (n5 < this.a.getDuelSession().l().size()) {
                player2 = this.a;
                player2.packetSender.sendInterfaceText((String)this.a.getDuelSession().l().get(n5), c[n5]);
                ++n5;
            }
        }
        this.c();
    }

    public final void c() {
        if (this.a.getDuelSession().i() == null || !this.a.getDuelSession().i().bW() || this.a.getDuelSession().i().getDuelController() == null) {
            this.a.getDuelController().a(true);
            return;
        }
        Player player = this.a;
        if (player.interfaceAction == "duel") {
            if (this.a.getDuelSession().i().getDuelController().b()) {
                player = this.a;
                player.packetSender.sendInterfaceText("Other player accepted.", 6684);
                return;
            }
            if (this.a.getDuelController().b()) {
                player = this.a;
                player.packetSender.sendInterfaceText("Waiting for other player...", 6684);
                return;
            }
            player = this.a;
            player.packetSender.sendInterfaceText("", 6684);
            return;
        }
        player = this.a;
        if (player.interfaceAction == "duel2") {
            if (this.a.getDuelSession().i().getDuelController().b()) {
                player = this.a;
                player.packetSender.sendInterfaceText("Other player accepted.", 6571);
                return;
            }
            if (this.a.getDuelController().b()) {
                player = this.a;
                player.packetSender.sendInterfaceText("Waiting for other player...", 6571);
                return;
            }
            player = this.a;
            player.packetSender.sendInterfaceText("", 6571);
        }
    }

    private int g() {
        int n = 0;
        for (ItemStack itemStack : this.a.getDuelSession().j()) {
            if (itemStack.getId() <= 0) continue;
            ++n;
        }
        return n;
    }

    public final boolean d() {
        Player player = this.a.getDuelSession().i();
        int n = this.a.getDuelSession().h().size() + player.getDuelSession().h().size() + this.a.getDuelInterfaceManager().g();
        if (n > this.a.getInventoryManager().getContainer().getFreeSlots()) {
            player = this.a;
            player.packetSender.sendGameMessage("You or your opponent doesn't have enough spaces for that.");
            return false;
        }
        n = player.getDuelSession().h().size() + this.a.getDuelSession().h().size() + player.getDuelInterfaceManager().g();
        if (n > player.getInventoryManager().getContainer().getFreeSlots()) {
            player = this.a;
            player.packetSender.sendGameMessage("You or your opponent doesn't have enough spaces for that.");
            return false;
        }
        return true;
    }

    public final void e() {
        if (this.a.getDuelSession().i() == null || !this.a.getDuelSession().i().bW()) {
            this.a.getDuelController().a(true);
            return;
        }
        Player player = this.a;
        player.packetSender.showInterfaceWithInventory(6575, 3321);
        player = this.a;
        player.packetSender.sendItemContainer(3322, this.a.getInventoryManager().getContainer().getRawItems());
        player = this.a;
        player.packetSender.sendItemContainer(6669, DuelSession.a(this.a.getDuelSession().h()));
        player = this.a;
        player.packetSender.sendItemContainer(6670, DuelSession.a(this.a.getDuelSession().i().getDuelSession().h()));
    }

    public final void a(int n, String string) {
        this.a.getDuelController().b(false);
        if (this.a.getDuelSession().k()[n]) {
            this.b -= d[n];
            this.a.getDuelSession().k()[n] = false;
            if (string != null) {
                this.a.getDuelSession().l().remove(string);
            }
        } else {
            this.b += d[n];
            this.a.getDuelSession().k()[n] = true;
            if (string != null) {
                this.a.getDuelSession().l().add(string);
            }
        }
        Player player = this.a;
        player.packetSender.sendConfig(286, this.b);
        this.c();
    }

    public final void f() {
        int n = 0;
        while (n < this.a.getDuelSession().k().length) {
            if (this.a.getDuelSession().k()[n]) {
                this.b -= d[n];
                this.a.getDuelSession().k()[n] = false;
            }
            ++n;
        }
        Player player = this.a;
        player.packetSender.sendConfig(286, this.b);
        int n2 = 0;
        while (n2 < this.a.getDuelSession().k().length) {
            this.a.getDuelSession().k()[n2] = false;
            ++n2;
        }
    }
}

