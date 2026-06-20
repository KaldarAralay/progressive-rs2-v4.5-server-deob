/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net.packet.handler;

import com.rs2.model.GameplayHelper;
import com.rs2.model.player.Player;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;

public final class SocialPacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket object) {
        switch (((IncomingPacket)object).getOpcode()) {
            case 188: {
                long l = ((IncomingPacket)object).getReader().readLong();
                player.getSocialManager().addFriend(l);
                return;
            }
            case 215: {
                long l = ((IncomingPacket)object).getReader().readLong();
                player.getSocialManager().removeFromList(player.getFriendsList(), l);
                return;
            }
            case 133: {
                long l = ((IncomingPacket)object).getReader().readLong();
                player.getSocialManager().addIgnore(l);
                return;
            }
            case 74: {
                long l = ((IncomingPacket)object).getReader().readLong();
                player.getSocialManager().removeFromList(player.getIgnoreList(), l);
                return;
            }
            case 126: {
                long l = ((IncomingPacket)object).getReader().readLong();
                int n = ((IncomingPacket)object).getLength() - 8;
                if (n < 0) {
                    return;
                }
                object = ((IncomingPacket)object).getReader().readBytes(n);
                if (player.isMuted()) {
                    object = player;
                    ((Player)object).packetSender.sendGameMessage("You are muted and cannot talk. Mute expires in: " + (GameplayHelper.b(System.currentTimeMillis(), player.getMuteExpires()) + 1) + " hours.");
                    return;
                }
                player.getSocialManager().sendPrivateMessage(player, l, (byte[])object, n);
            }
        }
    }
}

