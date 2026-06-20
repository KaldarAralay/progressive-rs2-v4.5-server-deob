/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net.packet.handler;

import com.rs2.ServerSettings;
import com.rs2.model.player.Player;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;

public final class IdlePacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket object) {
        player.aF(player.fn() + 1);
        if (!ServerSettings.idleLogoutEnabled || player.botEnabled) {
            return;
        }
        if (player.fn() > 20 && (player.getPlayerRights() < 2 && player.getSingleCombatTimer().hasElapsed() || player.cn)) {
            object = player;
            ((Player)object).packetSender.sendLogout();
            player.disconnect();
        }
    }
}

