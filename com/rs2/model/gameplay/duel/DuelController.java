/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.duel;

import com.rs2.model.GameplayHelper;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.util.TextUtil;

public final class DuelController {
    private Player a;
    private boolean b = false;

    public DuelController(Player player) {
        this.a = player;
    }

    public final void a(Player player) {
        if (this.a.gameMode != 0) {
            Player player2 = this.a;
            player2.packetSender.sendGameMessage("You are not playing on normal gamemode on cannot duel.");
            return;
        }
        if (player.gameMode != 0) {
            Player player3 = this.a;
            player3.packetSender.sendGameMessage(String.valueOf(player.getUsername()) + " is not playing on normal gamemode on cannot duel.");
            return;
        }
        if (player.fm() != this.a) {
            Player player4 = this.a;
            player4.packetSender.sendGameMessage("Sending duel request...");
            player4 = player;
            player4.packetSender.sendGameMessage(String.valueOf(TextUtil.formatDisplayName(this.a.getUsername())) + ":duelreq:");
            this.a.c(player);
            return;
        }
        this.a.getDuelSession().a(player);
        player.getDuelSession().a(this.a);
        player.getDuelInterfaceManager().a();
        this.a.getDuelInterfaceManager().a();
        this.a.c((Player)null);
        player.c((Player)null);
    }

    public final void a(boolean n) {
        if (n != 0) {
            n = 0;
            while (n < this.a.getDuelSession().h().size()) {
                this.a.getInventoryManager().addItem((ItemStack)this.a.getDuelSession().h().get(n));
                ++n;
            }
        }
        boolean bl = false;
        Object object = this;
        this.b = bl;
        this.a.getDuelSession().a();
        this.a.getDuelSession().b(false);
        this.a.getDuelSession().a((Player)null);
        this.a.c((Player)null);
        object = this.a;
        ((Player)object).packetSender.closeInterfaces();
        this.a.getDuelInterfaceManager().f();
        GameplayHelper.g(this.a);
    }

    public final void a() {
        if (this.a.getDuelSession().i() == null || !this.a.getDuelSession().i().bW() || this.a.getDuelSession().i().getDuelController() == null) {
            this.a(true);
            return;
        }
        boolean bl = true;
        Object object = this;
        this.b = bl;
        object = this.a;
        if (((Player)object).interfaceAction == "duel") {
            if (!this.a.getDuelInterfaceManager().d()) {
                bl = false;
                object = this.a.getDuelSession().i().getDuelController();
                this.a.getDuelSession().i().getDuelController().b = bl;
                bl = false;
                object = this.a.getDuelController();
                this.a.getDuelController().b = bl;
                return;
            }
            object = this.a.getDuelSession().i().getDuelController();
            if (((DuelController)object).b) {
                this.a.getDuelInterfaceManager().b();
                this.a.getDuelSession().i().getDuelInterfaceManager().b();
            }
        } else {
            object = this.a;
            if (((Player)object).interfaceAction == "duel2") {
                object = this.a.getDuelSession().i().getDuelController();
                if (((DuelController)object).b) {
                    this.a.getDuelSession().d();
                }
            }
        }
        this.a.getDuelSession().i().getDuelInterfaceManager().c();
        this.a.getDuelInterfaceManager().c();
    }

    public final boolean b() {
        return this.b;
    }

    public final void b(boolean bl) {
        this.b = bl;
    }
}

