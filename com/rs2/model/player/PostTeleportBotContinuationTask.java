/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.player;

import com.rs2.bot.ClanWarsBotManager;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.bot.combat.BotCombatLoadoutManager;
import com.rs2.model.GameplayHelper;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class PostTeleportBotContinuationTask
extends CycleEvent {
    private /* synthetic */ Player a;
    private final /* synthetic */ boolean b;
    private final /* synthetic */ Player c;

    PostTeleportBotContinuationTask(Player player, boolean bl, Player player2) {
        this.a = player;
        this.b = bl;
        this.c = player2;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.b) {
            this.a.setActionLocked(false);
        }
        this.a.getMovementQueue().clear();
        if (!this.c.bB) {
            if (this.c.isBot && this.c.botCombatState != null && (this.c.botCombatState.startsWith("escape") || this.c.botCombatState.equals("tele") || this.c.botCombatState.equals("died"))) {
                if (!this.c.botCombatState.equals("died") && !this.c.clanWarsBot) {
                    BotCombatHelper.sellBotLootItems(this.c);
                }
                if (this.c.clanWarsBot) {
                    ClanWarsBotManager.hideClanWarsBot(this.c);
                } else if (this.c.botMode == 4) {
                    GameplayHelper.c(this.c);
                } else {
                    BotCombatLoadoutManager.startCombatLoadoutBot(this.c);
                }
            }
            if (this.c.currentBotTask != null && this.c.botCombatState != null && (this.c.botCombatState.equals("died") || this.c.botCombatState.startsWith("escape") || this.c.botCombatState.equals("tele"))) {
                if (this.c.botMode != 4) {
                    GameplayHelper.startNextBotTask(this.c);
                } else {
                    GameplayHelper.c(this.c);
                }
            }
            if (this.c.currentBotRoute != null) {
                this.c.dm = false;
                if (!this.c.dn) {
                    this.c.continueBotRoute();
                }
            }
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

