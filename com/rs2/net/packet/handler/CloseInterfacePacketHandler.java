/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net.packet.handler;

import com.rs2.model.GameplayHelper;
import com.rs2.model.gameplay.partyroom.PartyRoomManager;
import com.rs2.model.player.Player;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;

public final class CloseInterfacePacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket object) {
        block10: {
            block9: {
                GameplayHelper.declineTrade(player);
                PartyRoomManager.returnStagedChestItems(player);
                object = player;
                if (((Player)object).interfaceAction == "flour") {
                    GameplayHelper.d(player, 53204);
                }
                object = player;
                if (((Player)object).interfaceAction == "duel") break block9;
                object = player;
                if (((Player)object).interfaceAction != "duel2") break block10;
            }
            if (!player.isInDuelArena()) {
                if (player.getDuelSession().getOpponent() != null && player.getDuelSession().getOpponent().isRegistered()) {
                    player.getDuelSession().getOpponent().getDuelController().resetDuel(true);
                    object = player.getDuelSession().getOpponent();
                    ((Player)object).packetSender.sendGameMessage("Other played declined the duel.");
                }
                player.getDuelController().resetDuel(true);
            }
        }
        player.getAttributes().put("isBanking", Boolean.FALSE);
        player.getAttributes().put("isShopping", Boolean.FALSE);
        String string = "";
        object = player;
        player.interfaceAction = string;
        if (player.getQuestState(0) != 1) {
            object = player;
            ((Player)object).packetSender.closeInterfaces();
        }
        if (player.getQuestState(0) == 1) {
            object = player;
            ((Player)object).packetSender.setSidebarInterface(3, 3213);
        }
        player.setOpenInterfaceId(0);
        if (player.k.size() > 0) {
            player.getSkillManager().showLevelUpInterface((Integer)player.k.get(0));
            player.k.remove(0);
        }
    }
}

