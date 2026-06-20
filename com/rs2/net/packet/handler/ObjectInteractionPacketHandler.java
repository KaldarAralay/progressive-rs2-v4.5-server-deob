/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net.packet.handler;

import com.rs2.ServerSettings;
import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.EntityTargetMovement;
import com.rs2.model.interaction.InteractionDispatcher;
import com.rs2.model.interaction.InteractionType;
import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.net.packet.ByteOrder;
import com.rs2.net.packet.ByteTransform;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;
import com.rs2.net.packet.PacketReader;

public final class ObjectInteractionPacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket object) {
        if (player.dJ()) {
            return;
        }
        Object object2 = player;
        ((Player)object2).packetSender.closeInterfaces();
        player.er();
        switch (((IncomingPacket)object).getOpcode()) {
            case 192: {
                player.setSelectedItemInterfaceId(((IncomingPacket)object).getReader().readSignedShort());
                player.setInteractionTargetId(((IncomingPacket)object).getReader().readSignedShort(true, ByteOrder.LITTLE));
                player.setInteractionTargetY(((IncomingPacket)object).getReader().readShort(true, ByteTransform.ADD, ByteOrder.LITTLE));
                player.setSelectedItemSlot(((IncomingPacket)object).getReader().readSignedShort(ByteOrder.LITTLE));
                player.setInteractionTargetX(((IncomingPacket)object).getReader().readShort(true, ByteTransform.ADD, ByteOrder.LITTLE));
                player.setInteractionTargetPlane(player.getPosition().getPlane());
                player.setSelectedItemId(((IncomingPacket)object).getReader().readSignedShort());
                if (player.getSelectedItemSlot() <= 28) {
                    object = player.getInventoryManager().getContainer().getItemAt(player.getSelectedItemSlot());
                    if (object == null || ((ItemStack)object).getId() != player.getSelectedItemId()) break;
                    object2 = InterfaceDefinition.forId(player.getSelectedItemInterfaceId());
                    if (player.isInterfaceOpen((InterfaceDefinition)object2)) {
                        if (player.getPlayerRights() > 1 && ServerSettings.debugModeEnabled) {
                            System.out.println("item: " + player.getSelectedItemId() + " object: " + player.getInteractionTargetId());
                        }
                        EntityTargetMovement.clearMovementTarget(player);
                        InteractionDispatcher.setCurrentInteractionType(InteractionType.ITEM_ON_OBJECT);
                        InteractionDispatcher.dispatchCurrentInteraction(player);
                    }
                }
                return;
            }
            case 132: {
                player.setInteractionTargetX(((IncomingPacket)object).getReader().readShort(true, ByteTransform.ADD, ByteOrder.LITTLE));
                player.setInteractionTargetId(((IncomingPacket)object).getReader().readSignedShort());
                player.setInteractionTargetY(((IncomingPacket)object).getReader().readSignedShort(ByteTransform.ADD));
                player.setInteractionTargetPlane(player.getPosition().getPlane());
                if (player.getPlayerRights() > 1 && ServerSettings.debugModeEnabled) {
                    System.out.println("first click id = " + player.getInteractionTargetId() + " x = " + player.getInteractionTargetX() + " y = " + player.getInteractionTargetY() + " type " + SkillActionHelper.getObjectType(player.getInteractionTargetId(), player.getInteractionTargetX(), player.getInteractionTargetY(), player.getPosition().getPlane()));
                }
                EntityTargetMovement.clearMovementTarget(player);
                InteractionDispatcher.setCurrentInteractionType(InteractionType.FIRST_OBJECT);
                InteractionDispatcher.dispatchCurrentInteraction(player);
                return;
            }
            case 252: {
                player.setInteractionTargetId(((IncomingPacket)object).getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE));
                player.setInteractionTargetY(((IncomingPacket)object).getReader().readSignedShort(true, ByteOrder.LITTLE));
                player.setInteractionTargetX(((IncomingPacket)object).getReader().readSignedShort(ByteTransform.ADD));
                player.setInteractionTargetPlane(player.getPosition().getPlane());
                if (player.getPlayerRights() > 1 && ServerSettings.debugModeEnabled) {
                    System.out.println("second click id = " + player.getInteractionTargetId() + " x = " + player.getInteractionTargetX() + " y = " + player.getInteractionTargetY());
                }
                EntityTargetMovement.clearMovementTarget(player);
                ObjectManager.prepareObjectInteractionMovement(player, player.getInteractionTargetId(), player.getInteractionTargetX(), player.getInteractionTargetY());
                InteractionDispatcher.setCurrentInteractionType(InteractionType.SECOND_OBJECT);
                InteractionDispatcher.dispatchCurrentInteraction(player);
                return;
            }
            case 70: {
                player.setInteractionTargetX(((IncomingPacket)object).getReader().readSignedShort(true, ByteOrder.LITTLE));
                player.setInteractionTargetY(((IncomingPacket)object).getReader().readSignedShort());
                player.setInteractionTargetId(((IncomingPacket)object).getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE));
                player.setInteractionTargetPlane(player.getPosition().getPlane());
                if (player.getPlayerRights() > 1 && ServerSettings.debugModeEnabled) {
                    System.out.println("third click id = " + player.getInteractionTargetId() + " x = " + player.getInteractionTargetX() + " y = " + player.getInteractionTargetY());
                }
                EntityTargetMovement.clearMovementTarget(player);
                ObjectManager.prepareObjectInteractionMovement(player, player.getInteractionTargetId(), player.getInteractionTargetX(), player.getInteractionTargetY());
                InteractionDispatcher.setCurrentInteractionType(InteractionType.THIRD_OBJECT);
                InteractionDispatcher.dispatchCurrentInteraction(player);
                return;
            }
            case 234: {
                player.setInteractionTargetX(((IncomingPacket)object).getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE));
                player.setInteractionTargetId(((IncomingPacket)object).getReader().readSignedShort(ByteTransform.ADD));
                player.setInteractionTargetY(((IncomingPacket)object).getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE));
                player.setInteractionTargetPlane(player.getPosition().getPlane());
                if (player.getPlayerRights() > 1 && ServerSettings.debugModeEnabled) {
                    System.out.println("fourth click id = " + player.getInteractionTargetId() + " x = " + player.getInteractionTargetX() + " y = " + player.getInteractionTargetY());
                }
                EntityTargetMovement.clearMovementTarget(player);
                ObjectManager.prepareObjectInteractionMovement(player, player.getInteractionTargetId(), player.getInteractionTargetX(), player.getInteractionTargetY());
                InteractionDispatcher.setCurrentInteractionType(InteractionType.FOURTH_OBJECT);
                InteractionDispatcher.dispatchCurrentInteraction(player);
                return;
            }
            case 35: {
                object = ((IncomingPacket)object).getReader();
                int n = ((PacketReader)object).readSignedShort(ByteOrder.LITTLE);
                int n2 = ((PacketReader)object).readSignedShort(ByteTransform.ADD, ByteOrder.BIG);
                int n3 = ((PacketReader)object).readSignedShort(ByteTransform.ADD, ByteOrder.BIG);
                int n4 = ((PacketReader)object).readSignedShort(ByteOrder.LITTLE);
                player.setInteractionTargetX(n);
                player.setInteractionTargetId(n4);
                player.setInteractionTargetY(n3);
                player.setInteractionTargetPlane(player.getPosition().getPlane());
                player.setInteractionSpellButtonId(n2);
                if (!SkillActionHelper.isObjectPresent(n4, n, n3, player.getPosition().getPlane())) break;
                EntityTargetMovement.clearMovementTarget(player);
                ObjectManager.prepareObjectInteractionMovement(player, player.getInteractionTargetId(), player.getInteractionTargetX(), player.getInteractionTargetY());
                InteractionDispatcher.setCurrentInteractionType(InteractionType.SPELL_ON_OBJECT);
                InteractionDispatcher.dispatchCurrentInteraction(player);
            }
        }
    }
}

