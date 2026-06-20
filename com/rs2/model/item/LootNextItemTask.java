/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.item;

import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.item.ItemService;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class LootNextItemTask
extends TickTask {
    private final /* synthetic */ Player a;

    LootNextItemTask(ItemService itemService, int n, Player player) {
        this.a = player;
        super(1);
    }

    @Override
    public final void execute() {
        if (this.a.isDead() || !this.a.bW()) {
            this.stop();
            return;
        }
        BotCombatHelper.pickupBotCombatGroundItem(this.a, ((GroundItem)this.a.botLootPickupTargets.get(0)).getItem().getId(), ((GroundItem)this.a.botLootPickupTargets.get(0)).getPosition());
        this.stop();
    }
}

