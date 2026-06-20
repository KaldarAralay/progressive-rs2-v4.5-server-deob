/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net.packet.handler;

import com.rs2.ServerSettings;
import com.rs2.cache.CacheArchive;
import com.rs2.model.EntityUpdateState;
import com.rs2.model.GameplayHelper;
import com.rs2.model.World;
import com.rs2.model.bankpin.BankPinManager;
import com.rs2.model.clue.ChallengeQuestion;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.gameplay.partyroom.PartyRoomManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.BankManager;
import com.rs2.model.player.GrandExchangeManager;
import com.rs2.model.player.Player;
import com.rs2.model.shop.ShopManager;
import com.rs2.model.skill.cooking.CookingManager;
import com.rs2.model.skill.crafting.CraftingHandler;
import com.rs2.model.skill.smithing.SmeltingHandler;
import com.rs2.net.packet.ByteOrder;
import com.rs2.net.packet.ByteTransform;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketBuffer;
import com.rs2.net.packet.PacketHandler;
import com.rs2.net.packet.handler.SkillMenuPacketHandler;

public final class InterfaceInputPacketHandler
implements PacketHandler {
    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Override
    public final void handle(Player var1_1, IncomingPacket var2_2) {
        switch (var2_2.getOpcode()) {
            case 40: {
                if (ServerSettings.debugModeEnabled) {
                    var6_6 = var1_1;
                    var6_6.packetSender.sendGameMessage("dialogue: " + var1_1.getDialogueManager().getDialogueId() + " chat: " + var1_1.getDialogueManager().getDialogueStep());
                }
                if (!var1_1.getDialogueManager().isDialogueInactive()) ** GOTO lbl23
                if (var1_1.i == -1 || var1_1.getQuestState(0) != 1 || !ServerSettings.showSkillUnlocks) ** GOTO lbl12
                var2_3 = BankPinManager.a(var1_1, var1_1.i);
                var1_1.i = -1;
                if (var2_3) ** GOTO lbl32
lbl12:
                // 2 sources

                if (var1_1.k.size() > 0) {
                    var1_1.getSkillManager().showLevelUpInterface((Integer)var1_1.k.get(0));
                    var1_1.k.remove(0);
                    break;
                }
                var6_6 = var1_1;
                var6_6.packetSender.closeInterfaces();
                var1_1.getDialogueManager().resetDialogueState();
                break;
lbl23:
                // 1 sources

                if (var1_1.getDialogueManager().getDialogueType() == 1) {
                    DialogueManager.continueContextDialogue(var1_1.getDialogueManager().getDialogueContextId(), var1_1, var1_1.getDialogueManager().getDialogueId(), var1_1.getDialogueManager().getDialogueStep() + 1, 0, var1_1.getDialogueManager().getDialogueContextX(), var1_1.getDialogueManager().getDialogueContextY());
                    break;
                }
                if (var1_1.getDialogueManager().getDialogueType() == 0) {
                    DialogueManager.continueDialogue(var1_1, var1_1.getDialogueManager().getDialogueId(), var1_1.getDialogueManager().getDialogueStep() + 1, 0);
                    break;
                }
                System.out.println("Unhandled dialogue type: " + var1_1.getDialogueManager().getDialogueType());
lbl32:
                // 2 sources

                return;
            }
            case 135: {
                var1_1.setSelectedInterfaceSlot(var2_2.getReader().readSignedShort(ByteOrder.LITTLE));
                var1_1.setSelectedInterfaceId(var2_2.getReader().readSignedShort(ByteTransform.ADD));
                var1_1.setSelectedInterfaceItemId(var2_2.getReader().readSignedShort(ByteOrder.LITTLE));
                if (var1_1.getSelectedInterfaceId() == 3900) {
                    ShopManager.a(var1_1, var1_1.getSelectedInterfaceSlot(), var1_1.getSelectedInterfaceItemId(), 100);
                    break;
                }
                if (var1_1.getSelectedInterfaceId() == 3823) {
                    ShopManager.b(var1_1, var1_1.getSelectedInterfaceSlot(), var1_1.getSelectedInterfaceItemId(), 100);
                    break;
                }
                var2_2 = PacketBuffer.allocateWriter(2);
                var2_2.writeOpcode(var1_1.getOutboundCipher(), 27);
                var1_1.writePacketBuffer(var2_2.getBuffer());
                return;
            }
            case 208: {
                var2_4 = var2_2.getReader().readInt();
                if (var2_4 <= 0) break;
                if (var1_1.getSelectedInterfaceId() == 5064 && var1_1.getOpenInterfaceId() == 5292) {
                    BankManager.depositInventoryItem(var1_1, var1_1.getSelectedInterfaceSlot(), var1_1.getSelectedInterfaceItemId(), var2_4);
                    return;
                }
                if (var1_1.getSelectedInterfaceId() == 2006 && var1_1.getOpenInterfaceId() == 2156) {
                    PartyRoomManager.b(var1_1, var1_1.getSelectedInterfaceSlot(), var1_1.getSelectedInterfaceItemId(), var2_4);
                    return;
                }
                if (var1_1.getSelectedInterfaceId() == 2274 && var1_1.getOpenInterfaceId() == 2156) {
                    PartyRoomManager.c(var1_1, var1_1.getSelectedInterfaceSlot(), var1_1.getSelectedInterfaceItemId(), var2_4);
                    return;
                }
                if ((var1_1.getSelectedInterfaceId() == 5382 || var1_1.getSelectedInterfaceId() == 19532 || var1_1.getSelectedInterfaceId() == 19533 || var1_1.getSelectedInterfaceId() == 19534 || var1_1.getSelectedInterfaceId() == 19535 || var1_1.getSelectedInterfaceId() == 19536 || var1_1.getSelectedInterfaceId() == 19537 || var1_1.getSelectedInterfaceId() == 19538 || var1_1.getSelectedInterfaceId() == 19539 || var1_1.getSelectedInterfaceId() == 19540) && var1_1.getOpenInterfaceId() == 5292) {
                    BankManager.withdrawItemFromTab(var1_1, var1_1.getSelectedInterfaceSlot(), var1_1.getSelectedInterfaceItemId(), var2_4, var1_1.getSelectedInterfaceId());
                    return;
                }
                if (var1_1.getSelectedInterfaceId() == 3322) {
                    var6_7 = var1_1;
                    if (var6_7.interfaceAction == "duel") {
                        var1_1.getDuelSession().a(new ItemStack(var1_1.getSelectedInterfaceItemId(), var2_4), var1_1.getInventoryManager().getContainer().indexOfItem(var1_1.getSelectedInterfaceItemId()));
                        break;
                    }
                    GameplayHelper.b(var1_1, var1_1.getSelectedInterfaceSlot(), var1_1.getSelectedInterfaceItemId(), var2_4);
                    return;
                }
                if (var1_1.getSelectedInterfaceId() == 3415) {
                    GameplayHelper.c(var1_1, var1_1.getSelectedInterfaceSlot(), var1_1.getSelectedInterfaceItemId(), var2_4);
                    return;
                }
                if (var1_1.getSelectedInterfaceId() == 15682 || var1_1.getSelectedInterfaceId() == 15683) {
                    var1_1.getFarmingToolStore().withdrawItem(var1_1.getSelectedItemId(), var2_4);
                    return;
                }
                if (var1_1.getSelectedInterfaceId() == 15594 || var1_1.getSelectedInterfaceId() == 15595) {
                    var1_1.getFarmingToolStore().depositItem(var1_1.getSelectedItemId(), var2_4);
                    return;
                }
                if (var1_1.getSelectedInterfaceId() == 6669) {
                    var1_1.getDuelSession().a(new ItemStack(var1_1.getSelectedInterfaceItemId(), var2_4));
                    return;
                }
                if (var1_1.getSelectedInterfaceId() == 207) {
                    var5_9 = var1_1.au;
                    var4_11 = var2_4;
                    var3_13 = var1_1;
                    var1_1 = ChallengeQuestion.forClueItemId(var5_9);
                    if (var1_1 != null && var3_13.getInventoryManager().getContainer().containsItem(var1_1.getAnswerItemId())) {
                        var2_5 = World.g()[var3_13.getInteractionTargetIndex()];
                        var3_13.getDialogueManager().setDialogueNpcId(var2_5 != null ? var2_5.getNpcId() : 0);
                        if (var4_11 == var1_1.getAnswerValue()) {
                            DialogueManager.a(var3_13, 10009, 2);
                            var3_13.getDialogueManager().showNpcOneLineDialogue("Thank you, this is the right answer.", 588);
                            var3_13.V = new ItemStack[2];
                            var3_13.V[0] = new ItemStack(var1_1.getClueItemId(), 1);
                            var3_13.V[1] = new ItemStack(var1_1.getAnswerItemId(), 1);
                            break;
                        }
                        DialogueManager.a(var3_13, 10009, 3);
                        var3_13.getDialogueManager().showNpcOneLineDialogue("Sorry, this is the wrong answer, try again.", 588);
                    }
                    return;
                }
                if (var1_1.getSelectedInterfaceId() == 13718) {
                    CookingManager.startCookingTask(var1_1, var2_4);
                    return;
                }
                if (SmeltingHandler.handleSmeltingButton(var1_1, var1_1.getSelectedInterfaceId(), var2_4) || CraftingHandler.handlePotteryButton(var1_1, var1_1.getSelectedInterfaceId(), var2_4) || CraftingHandler.handleSilverCraftingButton(var1_1, var1_1.getSelectedInterfaceId(), var2_4) || CraftingHandler.handleSpinningButton(var1_1, var1_1.getSelectedInterfaceId(), var2_4) || CraftingHandler.handleGlassblowingButton(var1_1, var1_1.getSelectedInterfaceId(), var2_4) || GameplayHelper.a(var1_1, var1_1.getSelectedInterfaceId(), var2_4) || CraftingHandler.handleWeavingButton(var1_1, var1_1.getSelectedInterfaceId(), var2_4) || CraftingHandler.handleDramenStaffButton(var1_1, var1_1.getSelectedInterfaceId(), var2_4) || EntityUpdateState.a(var1_1, var1_1.getSelectedInterfaceId(), var2_4) || CacheArchive.handleLogCuttingButton(var1_1, var1_1.getSelectedInterfaceId(), var2_4)) break;
                var5_10 = var2_4;
                var4_12 = var1_1.getSelectedInterfaceId();
                var6_8 = var3_14 = var1_1;
                if (var3_14.interfaceAction != "tanning") ** GOTO lbl-1000
                switch (var4_12) {
                    case 7442: 
                    case 8687: 
                    case 14801: {
                        GameplayHelper.b((Player)var3_14, var5_10, 1, 1739, 1741);
                        v0 = true;
                        break;
                    }
                    case 7454: 
                    case 8691: 
                    case 14802: {
                        GameplayHelper.b((Player)var3_14, var5_10, 3, 1739, 1743);
                        v0 = true;
                        break;
                    }
                    case 14803: {
                        GameplayHelper.b((Player)var3_14, var5_10, 20, 6287, 6289);
                        v0 = true;
                        break;
                    }
                    case 8696: 
                    case 14805: {
                        GameplayHelper.b((Player)var3_14, var5_10, 20, 1753, 1745);
                        v0 = true;
                        break;
                    }
                    case 8701: 
                    case 14806: {
                        GameplayHelper.b((Player)var3_14, var5_10, 20, 1751, 2505);
                        v0 = true;
                        break;
                    }
                    case 8706: 
                    case 14807: {
                        GameplayHelper.b((Player)var3_14, var5_10, 20, 1749, 2507);
                        v0 = true;
                        break;
                    }
                    case 8711: 
                    case 14808: {
                        GameplayHelper.b((Player)var3_14, var5_10, 20, 1747, 2509);
                        v0 = true;
                        break;
                    }
                    default: lbl-1000:
                    // 2 sources

                    {
                        v0 = false;
                    }
                }
                if (v0 || SkillMenuPacketHandler.a(var1_1, var1_1.getSelectedInterfaceId(), var2_4)) break;
                if (var1_1.getSelectedInterfaceId() == 18902) {
                    var3_15 = var1_1.getInventoryManager().getItemAmount(var1_1.N);
                    if (var2_4 > var3_15 || var2_4 <= 0) {
                        var2_4 = var3_15;
                    }
                    if (var2_4 > var1_1.getInventoryManager().getContainer().getFreeSlots()) {
                        var2_4 = var1_1.getInventoryManager().getContainer().getFreeSlots();
                    }
                    var3_16 = new ItemStack(var1_1.N, var3_15);
                    var3_16 = var3_16.getDefinition();
                    var3_17 = var3_16.getUnnotedId();
                    var1_1.getInventoryManager().removeItem(new ItemStack(var1_1.N, var2_4));
                    var1_1.getInventoryManager().addItem(new ItemStack(var3_17, var2_4));
                    return;
                }
                if (var1_1.getSelectedInterfaceId() == 18900) {
                    GrandExchangeManager.c(var1_1, var2_4);
                    return;
                }
                if (var1_1.getSelectedInterfaceId() != 18901) break;
                GrandExchangeManager.d(var1_1, var2_4);
            }
        }
    }
}

