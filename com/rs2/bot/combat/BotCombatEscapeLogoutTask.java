/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot.combat;

import com.rs2.ServerSettings;
import com.rs2.bot.ClanWarsBotManager;
import com.rs2.bot.combat.BotCombatEscapeHandler;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.bot.combat.BotCombatLoadoutManager;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class BotCombatEscapeLogoutTask
extends TickTask {
    private final /* synthetic */ Player a;

    BotCombatEscapeLogoutTask(int n, Player player) {
        this.a = player;
        super(2);
    }

    @Override
    public final void execute() {
        if (this.a.isDead() || !this.a.bW()) {
            this.stop();
        }
        if (this.a.getPosition().getY() < 3520 || !this.a.isInWilderness()) {
            if (this.a.currentBotTask == null && !this.a.clanWarsBot) {
                BotCombatHelper.sellBotLootItems(this.a);
            }
            if (this.a.de) {
                if (this.a.currentBotTask == null) {
                    if (this.a.clanWarsBot) {
                        ClanWarsBotManager.hideClanWarsBot(this.a);
                        return;
                    }
                    BotCombatLoadoutManager.a(this.a);
                    return;
                }
                if (this.a.az != 4) {
                    GameplayHelper.d(this.a);
                    return;
                }
                System.out.println("Bot needs to escape combat: " + this.a.getUsername() + ", trying to fix by reseting and relogging.");
                Object var2_1 = null;
                Player player = this.a;
                this.a.currentBotTask = var2_1;
                var2_1 = null;
                player = this.a;
                this.a.deferredBotTask = var2_1;
                this.a.f(new Position(ServerSettings.respawnX, ServerSettings.respawnY, ServerSettings.respawnPlane));
                World.a(this.a);
                return;
            }
            if (!this.a.getRecentCombatTimer().hasElapsed()) {
                Player player = this.a;
                player.packetSender.sendGameMessage("You have to wait 10 seconds after combat in order to logout.");
                return;
            }
            if (this.a.getSingleCombatTimer().hasElapsed()) {
                Player player = this.a;
                player.packetSender.sendLogout();
                this.a.disconnect();
                return;
            }
        } else {
            BotCombatEscapeHandler.b(this.a);
        }
    }
}

