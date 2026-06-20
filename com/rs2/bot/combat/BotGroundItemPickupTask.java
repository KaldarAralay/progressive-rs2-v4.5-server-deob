/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot.combat;

import com.rs2.model.Position;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.item.ItemService;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.util.path.PathFinder;

final class BotGroundItemPickupTask
extends TickTask {
    private final /* synthetic */ Player a;
    private final /* synthetic */ Position b;
    private final /* synthetic */ int c;
    private final /* synthetic */ GroundItem d;

    BotGroundItemPickupTask(int n, Player player, Position position, int n2, GroundItem groundItem) {
        this.a = player;
        this.b = position;
        this.c = n2;
        this.d = groundItem;
        super(2);
    }

    @Override
    public final void execute() {
        if (this.a.isDead() || !this.a.bW()) {
            this.stop();
            return;
        }
        this.a.setInteractionTargetY(this.b.getY());
        this.a.setInteractionTargetId(this.c);
        this.a.setInteractionTargetX(this.b.getX());
        this.a.setInteractionTargetPlane(this.a.getPosition().getPlane());
        ItemStack itemStack = this.d.getItem();
        PathFinder.getInstance();
        PathFinder.findPath(this.a, this.b.getX(), this.b.getY(), true, 0, 0);
        ItemService.getInstance().pickupItem(this.a, itemStack.getId(), this.d.getPosition());
        this.stop();
    }
}

