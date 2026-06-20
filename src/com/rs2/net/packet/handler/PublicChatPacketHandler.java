/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net.packet.handler;

import com.rs2.model.GameplayHelper;
import com.rs2.model.player.Player;
import com.rs2.net.packet.ByteTransform;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;
import com.rs2.util.ChatTextCodec;

public final class PublicChatPacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket object) {
        int n = ((IncomingPacket)object).getReader().readByte(false, ByteTransform.SUBTRACT);
        int n2 = ((IncomingPacket)object).getReader().readByte(false, ByteTransform.SUBTRACT);
        int n3 = ((IncomingPacket)object).getLength() - 2;
        byte[] byArray = ((IncomingPacket)object).getReader().readBytesReverse(n3, ByteTransform.ADD);
        object = byArray;
        String string = ChatTextCodec.decode(byArray, n3);
        if (player.eP()) {
            object = player;
            ((Player)object).packetSender.sendGameMessage("You are muted and cannot talk. Mute expires in: " + (GameplayHelper.b(System.currentTimeMillis(), player.getMuteExpires()) + 1) + " hours.");
            return;
        }
        if (player.getQuestState(0) != 1) {
            return;
        }
        player.setPublicChatEffects(n);
        player.setPublicChatColor(n2);
        player.setPublicChatPayload((byte[])object);
        player.flagAppearanceUpdate(true);
        if (!player.de) {
            player.ey = object = string.replaceAll("\\s+$", "");
            if (player.getMovementTarget() != null) {
                if (!player.getMovementTarget().isPlayer()) {
                    return;
                }
                Player player2 = (Player)player.getMovementTarget();
                if (!player2.de) {
                    return;
                }
                player2.botPvpChatMessage = object;
                player2.botPvpChatSource = player;
                return;
            }
            if (player.botPvpTeamRequesters.size() > 0) {
                for (Player player3 : player.botPvpTeamRequesters) {
                    ((Player)var4_7.next()).botPvpChatMessage = object;
                    player3.botPvpChatSource = player;
                }
            }
        }
    }
}

