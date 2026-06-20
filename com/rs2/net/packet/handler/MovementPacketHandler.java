/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net.packet.handler;

import com.rs2.model.Position;
import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.player.Player;
import com.rs2.net.packet.ByteOrder;
import com.rs2.net.packet.ByteTransform;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;
import com.rs2.util.GameUtil;
import com.rs2.util.path.PathFinder;
import com.rs2.util.plugin.PluginManager;

public final class MovementPacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket incomingPacket) {
        Object object;
        int n = incomingPacket.getLength();
        if (incomingPacket.getOpcode() == 248) {
            n -= 14;
        }
        if (player.isDead() || player.isActionLocked()) {
            return;
        }
        if (player.isMovementLocked()) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("A magical force stops you from moving.");
            return;
        }
        if (player.isStunned()) {
            Player player3 = player;
            player3.packetSender.sendGameMessage("You are stunned.");
            return;
        }
        if (DuelRule.NO_MOVEMENT.isEnabledFor(player)) {
            Player player4 = player;
            player4.packetSender.sendGameMessage("Movements have been disabled during this fight!");
            return;
        }
        if (incomingPacket.getOpcode() != 98) {
            player.er();
            if (player.getQuestState(0) != 1) {
                object = player;
                ((Player)object).packetSender.closeInterfaces();
            }
            object = player;
            if (((Player)object).interfaceAction == "duel") {
                if (player.getDuelSession().getOpponent() != null && !player.isInDuelArena()) {
                    player.getDuelSession().getOpponent().getDuelController().resetDuel(true);
                    object = player.getDuelSession().getOpponent();
                    ((Player)object).packetSender.sendGameMessage("Other played declined the duel.");
                }
                player.getDuelController().resetDuel(true);
            }
            PluginManager.c();
        }
        if (player.getEquipmentManager().getContainer().containsItem(6583) || player.getEquipmentManager().getContainer().containsItem(7927)) {
            player.getEquipmentManager().unequipSlot(12);
        }
        player.getMovementQueue().clearMovementActions();
        if (player.getQuestState(0) == 0) {
            player.setQuestState(0, 2);
        }
        if (player.getQuestState(0) != 1) {
            player.getQuestManager().refreshQuestJournal();
        }
        if (player.getQuestState(0) == 1) {
            object = player;
            ((Player)object).packetSender.setSidebarInterface(3, 3213);
        }
        if ((n = (n - 5) / 2) < 0) {
            return;
        }
        object = new int[n][2];
        int n2 = incomingPacket.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE);
        int n3 = 0;
        while (n3 < n) {
            object[n3][0] = incomingPacket.getReader().readSignedByte();
            object[n3][1] = incomingPacket.getReader().readSignedByte();
            ++n3;
        }
        n3 = incomingPacket.getReader().readSignedShort(ByteOrder.LITTLE);
        player.getMovementQueue().clear();
        player.getMovementQueue().setRunPath(incomingPacket.getReader().readSignedByte(ByteTransform.NEGATE) == 1);
        int n4 = GameUtil.getRegionId(player.getPosition().getX(), player.getPosition().getY());
        if (player.f == 0 || n4 == 9886 || n4 == 10142) {
            player.getMovementQueue().addStep(new Position(n2, n3));
            n4 = 0;
            while (n4 < n) {
                Object object2 = object[n4];
                object2[0] = object2[0] + n2;
                Object object3 = object[n4];
                object3[1] = object3[1] + n3;
                player.getMovementQueue().addStep(new Position((int)object[n4][0], (int)object[n4][1]));
                ++n4;
            }
            player.getMovementQueue().removeFirstStep();
            return;
        }
        n4 = 0;
        while (n4 < n) {
            Object object4 = object[n4];
            object4[0] = object4[0] + n2;
            Object object5 = object[n4];
            object5[1] = object5[1] + n3;
            ++n4;
        }
        if (n > 0) {
            if (Math.abs((int)(object[n - 1][0] - player.getPosition().getX())) > 21 || Math.abs((int)(object[n - 1][1] - player.getPosition().getY())) > 21) {
                player.getMovementQueue().clear();
            }
        } else if (Math.abs(n2 - player.getPosition().getX()) > 21 || Math.abs(n3 - player.getPosition().getY()) > 21) {
            player.getMovementQueue().clear();
            return;
        }
        if (n > 0) {
            PathFinder.getInstance();
            PathFinder.findPath(player, (int)object[n - 1][0], (int)object[n - 1][1], true, 16, 16);
            return;
        }
        PathFinder.getInstance();
        PathFinder.findPath(player, n2, n3, true, 16, 16);
    }
}

