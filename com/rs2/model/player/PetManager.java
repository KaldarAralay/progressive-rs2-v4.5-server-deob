/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.player;

import com.rs2.model.EntityTargetMovement;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.item.ItemContainer;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;

public final class PetManager {
    private Player b;
    private Npc c;
    private int d;
    public static final int[][] a = new int[][]{{1561, 768}, {7585, 3507}, {8070, 3854}, {8071, 3855}, {8072, 3856}, {8073, 3857}};

    public PetManager(Player player) {
        this.b = player;
    }

    public final void a(int n, int n2) {
        if (this.b.getInventoryManager().getContainer().getItemAmount(n) == 0) {
            return;
        }
        if (this.c != null) {
            Player player = this.b;
            player.packetSender.sendGameMessage("You already have a pet following you!");
            return;
        }
        int n3 = this.b.fh();
        if (n == 8070 && (n3 & 1) == 0) {
            Player player = this.b;
            player.packetSender.sendGameMessage("You need to defeat King Black Dragon first!");
            return;
        }
        if (n == 8071 && (n3 & 2) == 0) {
            Player player = this.b;
            player.packetSender.sendGameMessage("You need to defeat Kalphite Queen first!");
            return;
        }
        if (n == 8072 && (n3 & 4) == 0) {
            Player player = this.b;
            player.packetSender.sendGameMessage("You need to defeat Chaos Elemental first!");
            return;
        }
        if (n == 8073 && (n3 & 8) == 0) {
            Player player = this.b;
            player.packetSender.sendGameMessage("You need to defeat TzTok-Jad first!");
            return;
        }
        this.b.getInventoryManager().removeItem(new ItemStack(n, 1));
        this.d = n;
        this.c = new Npc(n2);
        this.c.a(new Position(this.b.getPosition().getX() - 1, this.b.getPosition().getY(), this.b.getPosition().getPlane()));
        this.c.setSpawnPosition(new Position(this.b.getPosition().getX() - 1, this.b.getPosition().getY(), this.b.getPosition().getPlane()));
        this.c.setSpawnX(this.b.getPosition().getX() - 1);
        this.c.setSpawnY(this.b.getPosition().getY() - 1);
        World.a(this.c);
        this.c.setMovementTarget(this.b);
    }

    public final void b(int n, int n2) {
        this.d = 1561;
        this.c = new Npc(768);
        this.c.a(new Position(this.b.getPosition().getX(), this.b.getPosition().getY() - 1, this.b.getPosition().getPlane()));
        this.c.setSpawnPosition(new Position(this.b.getPosition().getX(), this.b.getPosition().getY() - 1, this.b.getPosition().getPlane()));
        this.c.setSpawnX(this.b.getPosition().getX() - 1);
        this.c.setSpawnY(this.b.getPosition().getY() - 1);
        World.a(this.c);
        this.c.setMovementTarget(this.b);
        this.c.getUpdateState().setForcedText("Miaow!");
    }

    public final void a() {
        if (this.c == null) {
            return;
        }
        Player player = this.b;
        player.packetSender.sendGameMessage("You pick up your pet.");
        if (this.b.getInventoryManager().e(new ItemStack(this.d, 1))) {
            this.b.getInventoryManager().addItem(new ItemStack(this.d, 1));
        } else {
            int n = this.b.getBankContainer().getFirstFreeSlot();
            int n2 = this.b.getBankContainer().getItemAmount(this.d);
            if (n != -1) {
                if (n2 == 0) {
                    ItemStack itemStack = new ItemStack(this.d, 1);
                    ItemContainer itemContainer = this.b.getBankContainer();
                    itemContainer.add(itemStack, -1);
                } else {
                    this.b.getBankContainer().setItem(this.b.getBankContainer().indexOfItem(this.d), new ItemStack(this.d, n2 + 1));
                }
            }
        }
        this.d = -1;
        this.c.setActive(false);
        EntityTargetMovement.clearMovementTarget(this.c);
        World.b(this.c);
        this.c = null;
    }

    public final Npc b() {
        return this.c;
    }
}

