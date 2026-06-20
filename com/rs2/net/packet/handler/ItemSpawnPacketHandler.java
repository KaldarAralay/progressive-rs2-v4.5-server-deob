/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net.packet.handler;

import com.rs2.ServerSettings;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.GrandExchangeManager;
import com.rs2.model.player.Player;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;
import com.rs2.util.GameUtil;

public final class ItemSpawnPacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket incomingPacket) {
        if (player.dJ()) {
            return;
        }
        switch (incomingPacket.getOpcode()) {
            case 19: {
                Object object;
                int n = incomingPacket.getReader().readSignedShort();
                if (n < 0 || n > 11883) {
                    return;
                }
                if (n == 995 || player.getOpenInterfaceId() != 18890 || ((ItemStack)(object = new ItemStack(n, 1))).getDefinition().z()) break;
                if (((ItemStack)object).getDefinition().isMembersOnly() && n != 7999 && n != 8000) {
                    if (player.isMember()) {
                        if (ServerSettings.freeToPlayWorld) {
                            player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                            return;
                        }
                    } else {
                        player.packetSender.sendGameMessage("You need a members account to access members content.");
                        return;
                    }
                }
                player.dG = n;
                player.dH = 1;
                player.dI = GrandExchangeManager.a(n);
                object = player;
                ((Player)object).packetSender.sendInterfaceItemModel(18938, player.dG);
                object = player;
                ((Player)object).packetSender.sendInterfaceText(GameUtil.j(GrandExchangeManager.a(n)), 18919);
                object = player;
                ((Player)object).packetSender.sendInterfaceText("" + player.dH, 18920);
                object = player;
                ((Player)object).packetSender.sendInterfaceText(String.valueOf(GameUtil.j(player.dI)) + " coins", 18921);
                object = player;
                ((Player)object).packetSender.sendInterfaceText(String.valueOf(GameUtil.j(player.dI * player.dH)) + " coins", 18922);
            }
        }
    }
}

