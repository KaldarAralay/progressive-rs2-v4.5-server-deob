/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot.tasks;

import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.BotTradeAdvertManager;
import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.util.RectangularArea;

public final class SeersTradeAdvertBotTask
extends BotTaskDefinition {
    private static Position aa = new Position(2727, 3493, 0);
    private static BotRoute ab = null;
    private static RectangularArea[] ac = new RectangularArea[]{new RectangularArea(2724, 3490, 2727, 3493)};

    public SeersTradeAdvertBotTask(int n) {
        super(aa, (BotRoute)null, 0, true, 10);
        super.setTaskAreas(ac);
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        BotTradeAdvertManager.prepareTradeAdvertInventory(player);
    }

    @Override
    public final void prepareTradeAdvertState(Player player) {
        BotTradeAdvertManager.prepareTradeAdvertOffer(player);
    }

    @Override
    public final void prepareTaskCombatLoadout(Player player) {
        BotTradeAdvertManager.prepareTradeAdvertCombatLoadout(player);
    }
}

