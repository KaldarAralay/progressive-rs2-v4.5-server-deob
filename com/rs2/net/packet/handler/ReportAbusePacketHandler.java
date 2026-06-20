/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net.packet.handler;

import com.rs2.model.GameplayHelper;
import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;
import org.joda.time.DateTime;
import org.joda.time.base.BaseDateTime;

public final class ReportAbusePacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket incomingPacket) {
        Player player2;
        Player player3;
        Object object;
        int n;
        boolean bl;
        byte by;
        long l;
        block7: {
            l = incomingPacket.getReader().readLong();
            by = (byte)incomingPacket.getReader().readUnsignedByte(false);
            bl = (byte)incomingPacket.getReader().readUnsignedByte(false) == 1;
            long l2 = l;
            Player[] playerArray = World.f();
            n = playerArray.length;
            int n2 = 0;
            while (n2 < n) {
                object = playerArray[n2];
                if (object != null && ((Player)object).dS() == l2) {
                    player3 = object;
                    break block7;
                }
                ++n2;
            }
            player3 = player2 = null;
        }
        if (player3 == null) {
            Player player4 = player;
            player4.packetSender.sendGameMessage("You cannot report offline players.");
            return;
        }
        if (player.dS() == l) {
            Player player5 = player;
            player5.packetSender.sendGameMessage("You cannot report yourself!");
            return;
        }
        Player player6 = player;
        if (System.currentTimeMillis() - player6.dR < 60000L) {
            Player player7 = player;
            player7.packetSender.sendGameMessage("You can only report a player once every 60 seconds.");
            return;
        }
        Player player8 = player;
        player8.packetSender.sendGameMessage("Thank-you, your abuse report has been received.");
        if (bl && player.getPlayerRights() > 0 && player2.getPlayerRights() == 0) {
            if (player2.eP()) {
                return;
            }
            n = 48;
            long l3 = System.currentTimeMillis();
            object = new DateTime(l3);
            object = ((DateTime)object).plusHours(48);
            player2.setMuteExpires(((BaseDateTime)object).getMillis());
        }
        long l4 = System.currentTimeMillis();
        Player player9 = player;
        player.dR = l4;
        GameplayHelper.a(String.valueOf(System.currentTimeMillis()) + "\u00a7" + player.getUsername() + "\u00a7" + player.getPosition().getX() + "\u00a7" + player.getPosition().getY() + "\u00a7" + player.getPosition().getPlane() + "\u00a7" + player2.getUsername() + "\u00a7" + player2.getPosition().getX() + "\u00a7" + player2.getPosition().getY() + "\u00a7" + player2.getPosition().getPlane() + "\u00a7" + by + "\u00a7" + (bl ? 1 : 0), "reports");
    }
}

