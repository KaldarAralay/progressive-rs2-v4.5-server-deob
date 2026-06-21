/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net.packet.handler;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.World;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.interaction.InteractionDispatcher;
import com.rs2.model.interaction.InteractionType;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.skill.magic.Spellbook;
import com.rs2.net.packet.ByteOrder;
import com.rs2.net.packet.ByteTransform;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;

public final class NpcInteractionPacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player object, IncomingPacket object2) {
        if (((Player)object).isActionLocked()) {
            return;
        }
        Object object3 = object;
        ((Player)object3).packetSender.closeInterfaces();
        ((Player)object).resetInteractionState();
        switch (((IncomingPacket)object2).getOpcode()) {
            case 155: {
                Npc npc;
                int n = ((IncomingPacket)object2).getReader().readSignedShort(true, ByteOrder.LITTLE);
                if (n < 0 || n > World.getNpcs().length || (npc = World.getNpcs()[n]) == null || !npc.isInteractable()) break;
                ((Player)object).setInteractionTargetId(npc.getNpcId());
                ((Player)object).setInteractionTargetX(npc.getPosition().getX());
                ((Player)object).setInteractionTargetY(npc.getPosition().getY());
                ((Player)object).setInteractionTargetPlane(((Entity)object).getPosition().getPlane());
                ((Player)object).setInteractionTargetIndex(n);
                ((Entity)object).getUpdateState().setFaceEntity(n);
                ((Entity)object).setAttackRange(1);
                ((Entity)object).setMovementTarget(npc);
                if (ServerSettings.debugModeEnabled) {
                    Object object4 = object;
                    ((Player)object4).packetSender.sendGameMessage("First click npc: " + ((Player)object).getInteractionTargetId());
                }
                InteractionDispatcher.setCurrentInteractionType(InteractionType.FIRST_NPC);
                InteractionDispatcher.dispatchCurrentInteraction((Player)object);
                return;
            }
            case 17: {
                Npc npc;
                int n = ((IncomingPacket)object2).getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE) & 0xFFFF;
                if (n < 0 || n > World.getNpcs().length || (npc = World.getNpcs()[n]) == null || !npc.isInteractable()) break;
                ((Player)object).setInteractionTargetId(npc.getNpcId());
                ((Player)object).setInteractionTargetX(npc.getPosition().getX());
                ((Player)object).setInteractionTargetY(npc.getPosition().getY());
                ((Player)object).setInteractionTargetPlane(((Entity)object).getPosition().getPlane());
                ((Player)object).setInteractionTargetIndex(n);
                ((Entity)object).getUpdateState().setFaceEntity(n);
                ((Entity)object).setAttackRange(1);
                ((Entity)object).setMovementTarget(npc);
                if (ServerSettings.debugModeEnabled) {
                    Object object5 = object;
                    ((Player)object5).packetSender.sendGameMessage("Second click npc: " + ((Player)object).getInteractionTargetId());
                }
                InteractionDispatcher.setCurrentInteractionType(InteractionType.SECOND_NPC);
                InteractionDispatcher.dispatchCurrentInteraction((Player)object);
                return;
            }
            case 21: {
                Npc npc;
                int n = ((IncomingPacket)object2).getReader().readSignedShort(true);
                if (n < 0 || n > World.getNpcs().length || (npc = World.getNpcs()[n]) == null || !npc.isInteractable()) break;
                ((Player)object).setInteractionTargetId(npc.getNpcId());
                ((Player)object).setInteractionTargetX(npc.getPosition().getX());
                ((Player)object).setInteractionTargetY(npc.getPosition().getY());
                ((Player)object).setInteractionTargetPlane(((Entity)object).getPosition().getPlane());
                ((Player)object).setInteractionTargetIndex(n);
                ((Entity)object).getUpdateState().setFaceEntity(n);
                ((Entity)object).setAttackRange(1);
                ((Entity)object).setMovementTarget(npc);
                if (ServerSettings.debugModeEnabled) {
                    Object object6 = object;
                    ((Player)object6).packetSender.sendGameMessage("Third click npc: " + ((Player)object).getInteractionTargetId());
                }
                InteractionDispatcher.setCurrentInteractionType(InteractionType.THIRD_NPC);
                InteractionDispatcher.dispatchCurrentInteraction((Player)object);
                return;
            }
            case 230: {
                return;
            }
            case 72: {
                Npc npc;
                object3 = object2;
                object2 = object;
                object = this;
                int n = ((IncomingPacket)object3).getReader().readSignedShort(ByteTransform.ADD);
                if (n < 0 || n > World.getNpcs().length || (npc = World.getNpcs()[n]) == null || !npc.isInteractable()) break;
                ((Player)object2).setQueuedCombatSpell(null);
                if (npc.getOwnerPlayer() != null && npc.getOwnerPlayer() != object2) {
                    Object object7 = object2;
                    ((Player)object7).packetSender.sendGameMessage(String.valueOf(npc.getDefinition().getName()) + " is not interested in interacting with you right now.");
                    break;
                }
                if (npc.getDefinition().isAttackable() || npc.isDoorSupportNpc()) {
                    int n2 = npc.getDefinition().getId();
                    Object object8 = object2;
                    if (((Player)object8).dv != 2 || n2 != 643) {
                        CombatManager.startCombat((Entity)object2, npc);
                        break;
                    }
                }
                Object object9 = object2;
                ((Player)object9).packetSender.sendGameMessage("You cannot attack that npc!");
                return;
            }
            case 131: {
                ((Entity)object).getMovementQueue().clear();
                int n = ((IncomingPacket)object2).getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE);
                if (n < 0 || n > World.getNpcs().length) break;
                int n3 = ((IncomingPacket)object2).getReader().readSignedShort(ByteTransform.ADD);
                Npc npc = World.getNpcs()[n];
                if (npc == null || !npc.isInteractable()) break;
                if (npc.getOwnerPlayer() != null && npc.getOwnerPlayer() != object) {
                    Object object10 = object;
                    ((Player)object10).packetSender.sendGameMessage(String.valueOf(npc.getDefinition().getName()) + " is not interested in interacting with you right now.");
                    break;
                }
                SpellDefinition spellDefinition = Spellbook.getSpellForButtonId((Player)object, n3);
                if (spellDefinition != null) {
                    if (!((Entity)object).isInMageArena()) {
                        if (spellDefinition == SpellDefinition.SARADOMIN_STRIKE && ((Player)object).mageArenaSaradominStrikeCastsRemaining > 0) {
                            Object object11 = object;
                            ((Player)object11).packetSender.sendGameMessage("You need to cast this spell " + ((Player)object).mageArenaSaradominStrikeCastsRemaining + " times at Mage arena first.");
                            break;
                        }
                        if (spellDefinition == SpellDefinition.FLAMES_OF_ZAMORAK && ((Player)object).mageArenaFlamesOfZamorakCastsRemaining > 0) {
                            Object object12 = object;
                            ((Player)object12).packetSender.sendGameMessage("You need to cast this spell " + ((Player)object).mageArenaFlamesOfZamorakCastsRemaining + " times at Mage arena first.");
                            break;
                        }
                        if (spellDefinition == SpellDefinition.CLAWS_OF_GUTHIX && ((Player)object).mageArenaClawsOfGuthixCastsRemaining > 0) {
                            Object object13 = object;
                            ((Player)object13).packetSender.sendGameMessage("You need to cast this spell " + ((Player)object).mageArenaClawsOfGuthixCastsRemaining + " times at Mage arena first.");
                            break;
                        }
                    }
                    ((Player)object).setQueuedCombatSpell(spellDefinition);
                    if (spellDefinition == SpellDefinition.TELEOTHER_CAMELOT || spellDefinition == SpellDefinition.TELEOTHER_FALADOR || spellDefinition == SpellDefinition.TELEOTHER_LUMBRIDGE || spellDefinition == SpellDefinition.TELE_BLOCK) {
                        Object object14 = object;
                        ((Player)object14).packetSender.sendGameMessage("Nothing interesting happens.");
                        break;
                    }
                    CombatManager.startCombat((Entity)object, npc);
                    break;
                }
                if (((Player)object).getPlayerRights() > 1 && ServerSettings.debugModeEnabled) {
                    System.out.println("Magic id: " + n3);
                }
                return;
            }
            case 57: {
                int n = ((IncomingPacket)object2).getReader().readSignedShort(ByteTransform.ADD);
                int n4 = ((IncomingPacket)object2).getReader().readSignedShort(ByteTransform.ADD);
                int n5 = ((IncomingPacket)object2).getReader().readSignedShort(ByteOrder.LITTLE);
                if (n5 > 28 && n5 < 0) break;
                ItemStack itemStack = ((Player)object).getInventoryManager().getContainer().getItemAt(n5);
                if (itemStack == null || itemStack.getId() != n) {
                    return;
                }
                if (((Player)object).getPlayerRights() > 1 && ServerSettings.debugModeEnabled) {
                    System.out.println(String.valueOf(n) + " " + n4 + " " + n5);
                }
                if (n4 < 0 || n4 > World.getNpcs().length) {
                    return;
                }
                object2 = World.getNpcs()[n4];
                if (object2 == null || !((Npc)object2).isInteractable()) {
                    return;
                }
                ((Player)object).setSelectedItemSlot(n5);
                ((Player)object).setInteractionTargetIndex(n4);
                ((Entity)object).setInteractionTarget((Entity)object2);
                ((Player)object).setSelectedItemId(n);
                ((Player)object).setInteractionTargetId(((Npc)object2).getNpcId());
                ((Player)object).setInteractionTargetX(((Entity)object2).getPosition().getX());
                ((Player)object).setInteractionTargetY(((Entity)object2).getPosition().getY());
                ((Player)object).setInteractionTargetPlane(((Entity)object).getPosition().getPlane());
                ((Entity)object).getUpdateState().setFaceEntity(n4);
                ((Entity)object).setAttackRange(1);
                ((Entity)object).setMovementTarget((Entity)object2);
                InteractionDispatcher.setCurrentInteractionType(InteractionType.ITEM_ON_NPC);
                InteractionDispatcher.dispatchCurrentInteraction((Player)object);
            }
        }
    }
}

