/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net.packet.handler;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.World;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.skill.magic.Spellbook;
import com.rs2.net.packet.ByteOrder;
import com.rs2.net.packet.ByteTransform;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;
import com.rs2.net.packet.handler.DeferredTradeRequestTask;
import com.rs2.net.packet.handler.DuelRequestTask;
import com.rs2.net.packet.handler.FollowPlayerTask;
import com.rs2.net.packet.handler.ItemOnPlayerTask;
import com.rs2.net.packet.handler.TradeRequestTask;
import com.rs2.util.GameUtil;

public final class PlayerInteractionPacketHandler
implements PacketHandler {
    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void handle(Player object, IncomingPacket object2) {
        if (((Player)object).isActionLocked()) {
            return;
        }
        Object object3 = object;
        ((Player)object3).packetSender.closeInterfaces();
        ((Player)object).er();
        switch (((IncomingPacket)object2).getOpcode()) {
            case 73: 
            case 136: 
            case 139: {
                object3 = object2;
                object2 = object;
                object = this;
                int n = ((IncomingPacket)object3).getReader().readSignedShort(true, ByteOrder.LITTLE);
                if (n < 0) return;
                if (n > World.getPlayers().length) {
                    return;
                }
                Player player = World.getPlayers()[n];
                if (player == null) return;
                if (!GameUtil.isWithinDistance(((Entity)object2).getPosition(), player.getPosition(), 15)) {
                    return;
                }
                int n2 = ((Entity)object2).nextActionSequence();
                if (player.getTradePartner() == object2) {
                    GameplayHelper.declineTrade((Player)object2);
                } else if (player.getOpenInterfaceId() > 0) {
                    Object object4 = object2;
                    ((Player)object4).packetSender.sendGameMessage("This player is busy.");
                    return;
                }
                ((Entity)object2).setInteractionTarget(player);
                ((Entity)object2).setAttackRange(1);
                ((Entity)object2).setMovementTarget(player);
                World.scheduleTickTask(new TradeRequestTask((PlayerInteractionPacketHandler)object, 1, player, (Player)object2, n2));
                return;
            }
            case 153: {
                int n = ((IncomingPacket)object2).getReader().readSignedShort(true, ByteOrder.LITTLE);
                if (n < 0) return;
                if (n > World.getPlayers().length) {
                    return;
                }
                Player player = World.getPlayers()[n];
                if (player == null) return;
                if (!GameUtil.isWithinDistance(((Entity)object).getPosition(), player.getPosition(), 15)) {
                    return;
                }
                ((Entity)object).getUpdateState().setFaceEntity(player.getEncodedIndex());
                ((Entity)object).setAttackRange(1);
                ((Entity)object).setMovementTarget(player);
                return;
            }
            case 128: {
                object3 = object2;
                object2 = object;
                object = this;
                int n = ((IncomingPacket)object3).getReader().readSignedShort();
                if (n < 0) return;
                if (n > World.getPlayers().length) {
                    return;
                }
                Player player = World.getPlayers()[n];
                if (player == null) return;
                if (!GameUtil.isWithinDistance(((Entity)object2).getPosition(), player.getPosition(), 15)) {
                    return;
                }
                int n3 = ((Entity)object2).nextActionSequence();
                ((Player)object2).setQueuedCombatSpell(null);
                ((Entity)object2).getUpdateState().setFaceEntity(player.getEncodedIndex());
                if (!((Entity)object2).isInDuelArena() && !((Entity)object2).isInWilderness()) {
                    Object object5 = object;
                    int n4 = n3;
                    object = player;
                    Object object6 = object2;
                    Object object7 = object5;
                    if (ServerSettings.duelingDisabled) {
                        Object object8 = object6;
                        ((Player)object8).packetSender.sendGameMessage("This feature is currently disabled.");
                        return;
                    }
                    if (((Player)object).getOpenInterfaceId() > 0) {
                        Object object9 = object6;
                        ((Player)object9).packetSender.sendGameMessage("This player is busy.");
                        return;
                    }
                    ((Entity)object6).setInteractionTarget((Entity)object);
                    ((Entity)object6).setAttackRange(1);
                    ((Entity)object6).setMovementTarget((Entity)object);
                    World.scheduleTickTask(new DuelRequestTask((PlayerInteractionPacketHandler)object7, 1, (Player)object, (Player)object6, n4));
                    return;
                }
                CombatManager.startCombat((Entity)object2, player);
                return;
            }
            case 249: {
                int n = ((IncomingPacket)object2).getReader().readSignedShort(true, ByteTransform.ADD);
                if (n < 0) return;
                if (n > World.getPlayers().length) {
                    return;
                }
                Player player = World.getPlayers()[n];
                if (player == null) return;
                if (!GameUtil.isWithinDistance(((Entity)object).getPosition(), player.getPosition(), 15)) {
                    return;
                }
                int n5 = ((IncomingPacket)object2).getReader().readSignedShort(true, ByteOrder.LITTLE);
                SpellDefinition spellDefinition = Spellbook.getSpellForButtonId((Player)object, n5);
                if (spellDefinition == null) {
                    if (((Player)object).getPlayerRights() <= 1) return;
                    if (!ServerSettings.debugModeEnabled) return;
                    System.out.println("Magic ID: " + n5);
                    return;
                }
                if (!((Entity)object).isInMageArena()) {
                    if (spellDefinition == SpellDefinition.SARADOMIN_STRIKE && ((Player)object).mageArenaSaradominStrikeCastsRemaining > 0) {
                        Object object10 = object;
                        ((Player)object10).packetSender.sendGameMessage("You need to cast this spell " + ((Player)object).mageArenaSaradominStrikeCastsRemaining + " times at Mage arena first.");
                        return;
                    }
                    if (spellDefinition == SpellDefinition.FLAMES_OF_ZAMORAK && ((Player)object).mageArenaFlamesOfZamorakCastsRemaining > 0) {
                        Object object11 = object;
                        ((Player)object11).packetSender.sendGameMessage("You need to cast this spell " + ((Player)object).mageArenaFlamesOfZamorakCastsRemaining + " times at Mage arena first.");
                        return;
                    }
                    if (spellDefinition == SpellDefinition.CLAWS_OF_GUTHIX && ((Player)object).mageArenaClawsOfGuthixCastsRemaining > 0) {
                        Object object12 = object;
                        ((Player)object12).packetSender.sendGameMessage("You need to cast this spell " + ((Player)object).mageArenaClawsOfGuthixCastsRemaining + " times at Mage arena first.");
                        return;
                    }
                }
                ((Player)object).setQueuedCombatSpell(spellDefinition);
                if (spellDefinition != SpellDefinition.TELEOTHER_CAMELOT && spellDefinition != SpellDefinition.TELEOTHER_FALADOR && spellDefinition != SpellDefinition.TELEOTHER_LUMBRIDGE) {
                    CombatManager.startCombat((Entity)object, player);
                    return;
                }
                MagicSpellAction.castTeleotherSpell((Player)object, player, spellDefinition);
                return;
            }
            case 14: {
                object3 = object2;
                object2 = object;
                object = this;
                int n = ((IncomingPacket)object3).getReader().readSignedShort();
                if (n < 0) return;
                if (n > World.getPlayers().length) {
                    return;
                }
                Player player = World.getPlayers()[n];
                if (player == null) return;
                if (!GameUtil.isWithinDistance(((Entity)object2).getPosition(), player.getPosition(), 15)) {
                    return;
                }
                int n6 = ((IncomingPacket)object3).getReader().readSignedShort(ByteOrder.LITTLE);
                ItemStack itemStack = ((Player)object2).getInventoryManager().getContainer().getItemAt(n6);
                if (itemStack == null) return;
                int n7 = ((Entity)object2).nextActionSequence();
                if (player.getOpenInterfaceId() > 0) {
                    Object object13 = object2;
                    ((Player)object13).packetSender.sendGameMessage("This player is busy.");
                    return;
                }
                ((Entity)object2).setInteractionTarget(player);
                ((Entity)object2).setAttackRange(1);
                ((Entity)object2).setMovementTarget(player);
                World.scheduleTickTask(new ItemOnPlayerTask((PlayerInteractionPacketHandler)object, 1, player, (Player)object2, n7, itemStack, n6));
                return;
            }
            case 39: {
                object3 = object2;
                object2 = object;
                object = this;
                int n = ((IncomingPacket)object3).getReader().readSignedShort(true, ByteOrder.LITTLE);
                if (n < 0) return;
                if (n > World.getPlayers().length) {
                    return;
                }
                Player player = World.getPlayers()[n];
                if (player == null) return;
                if (!GameUtil.isWithinDistance(((Entity)object2).getPosition(), player.getPosition(), 15)) {
                    return;
                }
                ((Entity)object2).setInteractionTarget(player);
                ((Entity)object2).setAttackRange(1);
                ((Entity)object2).setMovementTarget(player);
                int n8 = ((Entity)object2).nextActionSequence();
                World.scheduleTickTask(new FollowPlayerTask((PlayerInteractionPacketHandler)object, 1, player, (Player)object2, n8));
                return;
            }
        }
    }

    public static void dispatchDeferredTradeRequest(Player player, Player player2) {
        if (player2 == null || !GameUtil.isWithinDistance(player.getPosition(), player2.getPosition(), 15)) {
            player.pendingTradeTarget = null;
            return;
        }
        int n = player.nextActionSequence();
        if (player2.getTradePartner() == player) {
            GameplayHelper.declineTrade(player);
            player.pendingTradeTarget = null;
        } else if (player2.getOpenInterfaceId() > 0) {
            player2 = player;
            player2.packetSender.sendGameMessage("This player is busy.");
            player.pendingTradeTarget = null;
            return;
        }
        player.setInteractionTarget(player2);
        player.setAttackRange(1);
        player.setMovementTarget(player2);
        World.scheduleTickTask(new DeferredTradeRequestTask(1, player2, player, n));
    }
}

