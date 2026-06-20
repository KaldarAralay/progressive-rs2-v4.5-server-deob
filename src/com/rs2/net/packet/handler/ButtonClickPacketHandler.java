/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net.packet.handler;

import com.rs2.ServerSettings;
import com.rs2.cache.CacheArchive;
import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.EntityUpdateState;
import com.rs2.model.GameplayHelper;
import com.rs2.model.gameplay.barrows.BarrowsManager;
import com.rs2.model.gameplay.partyroom.PartyRoomManager;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.item.action.BarrowsRepairHandler;
import com.rs2.model.item.action.MysticStaffEnchantment;
import com.rs2.model.music.MusicManager;
import com.rs2.model.music.MusicTrackDefinition;
import com.rs2.model.player.BankManager;
import com.rs2.model.player.BankRearrangeMode;
import com.rs2.model.player.GrandExchangeManager;
import com.rs2.model.player.Player;
import com.rs2.model.randomevent.RandomEventManager;
import com.rs2.model.skill.cooking.CookingManager;
import com.rs2.model.skill.cooking.DairyChurnHandler;
import com.rs2.model.skill.crafting.CraftingHandler;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.skill.smithing.SmeltingHandler;
import com.rs2.model.travel.TravelManager;
import com.rs2.model.travel.canoe.CanoeTravelManager;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;

public final class ButtonClickPacketHandler
implements PacketHandler {
    /*
     * Unable to fully structure code
     */
    @Override
    public final void handle(Player var1_1, IncomingPacket var2_2) {
        block184: {
            var2_3 = var2_2.getReader().readSignedShort();
            var3_5 = InterfaceDefinition.forId(var2_3);
            if (!var1_1.isInterfaceOpen(var3_5)) {
                return;
            }
            if (ServerSettings.debugModeEnabled) {
                System.out.println("button id: " + var2_3);
            }
            if (var1_1.getDuelSession().handleButtonClick(var2_3) || var1_1.getQuestManager().handleButtonClick(var2_3)) break block184;
            if (var2_3 >= 18792 && var2_3 <= 18812) {
                var3_6 = var2_3 - 18792;
                var1_1.es = 0;
                var1_1.et = var3_6;
                var1_1.v(0);
            }
            if (var2_3 == 18791) {
                var1_1.es = 0;
                var1_1.et = 21;
                var1_1.v(0);
            }
            if (var2_3 == 18813) {
                var1_1.es = 0;
                var1_1.et = 22;
                var1_1.v(0);
            }
            if (var2_3 == 18884 && var1_1.es > 0) {
                --var1_1.es;
                var1_1.v(0);
            }
            if (var2_3 == 18883) {
                ++var1_1.es;
                var1_1.v(0);
            }
            if (var2_3 == 2246) {
                PartyRoomManager.e(var1_1);
            }
            var3_7 = 0;
            while (var3_7 < BankManager.bankTabUpdateTasks.length) {
                var4_10 = BankManager.bankTabUpdateTasks[var3_7];
                if (var4_10.getBankTabButtonId() == var2_3) {
                    BankManager.selectTab(var1_1, var3_7);
                    return;
                }
                ++var3_7;
            }
            var5_14 = var1_1;
            if (var5_14.interfaceAction != "tanning") {
                var3_7 = 0;
                while (var3_7 < MusicTrackDefinition.trackCount) {
                    var4_10 = MusicTrackDefinition.forTrackId(var3_7);
                    if (var4_10.getButtonId() == var2_3) {
                        if (MusicManager.isTrackUnlocked(var1_1, var3_7)) {
                            var5_14 = var1_1;
                            var5_14.packetSender.sendMusicTrack((MusicTrackDefinition)var4_10);
                            var1_1.cg = var3_7;
                            var1_1.eo = false;
                            break block184;
                        }
                        var5_14 = var1_1;
                        var5_14.packetSender.sendGameMessage("You haven't unlocked that song yet.");
                        return;
                    }
                    ++var3_7;
                }
            }
            var5_14 = var1_1;
            if (var5_14.interfaceAction == "tanning" && var2_3 == 7451) {
                var2_4 = "";
                var5_14 = var1_1;
                var1_1.interfaceAction = var2_4;
                var5_14 = var1_1;
                var5_14.packetSender.closeInterfaces();
                return;
            }
            if (var2_3 == 6269) {
                var1_1.eo = true;
                return;
            }
            if (var2_3 == 6270) {
                var1_1.eo = false;
                return;
            }
            switch (var2_3) {
                case 10162: {
                    var5_14 = var1_1;
                    var5_14.packetSender.closeInterfaces();
                    var1_1.W = 0;
                    var1_1.X = 0;
                    break;
                }
                case 6020: {
                    var5_14 = var1_1;
                    var5_14.packetSender.closeInterfaces();
                    var1_1.ak = -1;
                    var5_14 = var1_1;
                    var5_14.packetSender.refreshSidebarInterfaces();
                    var1_1.f(true);
                    break;
                }
                case 2422: {
                    var5_14 = var1_1;
                    var5_14.packetSender.closeInterfaces();
                    break;
                }
                case 8654: {
                    var1_1.getSkillGuideManager().showAttackGuide(1);
                    var1_1.getSkillGuideManager().selectedSkillIndex = 0;
                    return;
                }
                case 8657: {
                    var1_1.getSkillGuideManager().showStrengthGuide(1);
                    var1_1.getSkillGuideManager().selectedSkillIndex = 1;
                    return;
                }
                case 8660: {
                    var1_1.getSkillGuideManager().showDefenceGuide(1);
                    var1_1.getSkillGuideManager().selectedSkillIndex = 2;
                    return;
                }
                case 8663: {
                    var1_1.getSkillGuideManager().showRangedGuide(1);
                    var1_1.getSkillGuideManager().selectedSkillIndex = 3;
                    return;
                }
                case 8666: {
                    var1_1.getSkillGuideManager().showPrayerGuide(1);
                    var1_1.getSkillGuideManager().selectedSkillIndex = 4;
                    return;
                }
                case 8669: {
                    var1_1.getSkillGuideManager().showMagicGuide(1);
                    var1_1.getSkillGuideManager().selectedSkillIndex = 5;
                    return;
                }
                case 8672: {
                    var1_1.getSkillGuideManager().showRunecraftingGuide(1);
                    var1_1.getSkillGuideManager().selectedSkillIndex = 6;
                    return;
                }
                case 8655: {
                    var1_1.getSkillGuideManager().showHitpointsGuide(1);
                    var1_1.getSkillGuideManager().selectedSkillIndex = 7;
                    return;
                }
                case 8658: {
                    var1_1.getSkillGuideManager().showAgilityGuide(1);
                    var1_1.getSkillGuideManager().selectedSkillIndex = 8;
                    return;
                }
                case 8661: {
                    var1_1.getSkillGuideManager().showHerbloreGuide(1);
                    var1_1.getSkillGuideManager().selectedSkillIndex = 9;
                    return;
                }
                case 8664: {
                    var1_1.getSkillGuideManager().showThievingGuide(1);
                    var1_1.getSkillGuideManager().selectedSkillIndex = 10;
                    return;
                }
                case 8667: {
                    var1_1.getSkillGuideManager().showCraftingGuide(1);
                    var1_1.getSkillGuideManager().selectedSkillIndex = 11;
                    return;
                }
                case 8670: {
                    var1_1.getSkillGuideManager().showFletchingGuide(1);
                    var1_1.getSkillGuideManager().selectedSkillIndex = 12;
                    return;
                }
                case 12162: {
                    var1_1.getSkillGuideManager().showSlayerGuide(1);
                    var1_1.getSkillGuideManager().selectedSkillIndex = 13;
                    return;
                }
                case 8656: {
                    var1_1.getSkillGuideManager().showMiningGuide(1);
                    var1_1.getSkillGuideManager().selectedSkillIndex = 14;
                    return;
                }
                case 8659: {
                    var1_1.getSkillGuideManager().showSmithingGuide(1);
                    var1_1.getSkillGuideManager().selectedSkillIndex = 15;
                    return;
                }
                case 8662: {
                    var1_1.getSkillGuideManager().showFishingGuide(1);
                    var1_1.getSkillGuideManager().selectedSkillIndex = 16;
                    return;
                }
                case 8665: {
                    var1_1.getSkillGuideManager().showCookingGuide(1);
                    var1_1.getSkillGuideManager().selectedSkillIndex = 17;
                    return;
                }
                case 8668: {
                    var1_1.getSkillGuideManager().showFiremakingGuide(1);
                    var1_1.getSkillGuideManager().selectedSkillIndex = 18;
                    return;
                }
                case 8671: {
                    var1_1.getSkillGuideManager().showWoodcuttingGuide(1);
                    var1_1.getSkillGuideManager().selectedSkillIndex = 19;
                    return;
                }
                case 13928: {
                    var1_1.getSkillGuideManager().showFarmingGuide(1);
                    var1_1.getSkillGuideManager().selectedSkillIndex = 20;
                    return;
                }
                case 8846: {
                    var1_1.getSkillGuideManager().showSelectedSkillCategory(1);
                    return;
                }
                case 8823: {
                    var1_1.getSkillGuideManager().showSelectedSkillCategory(2);
                    return;
                }
                case 8824: {
                    var1_1.getSkillGuideManager().showSelectedSkillCategory(3);
                    return;
                }
                case 8827: {
                    var1_1.getSkillGuideManager().showSelectedSkillCategory(4);
                    return;
                }
                case 8837: {
                    var1_1.getSkillGuideManager().showSelectedSkillCategory(5);
                    return;
                }
                case 8840: {
                    var1_1.getSkillGuideManager().showSelectedSkillCategory(6);
                    return;
                }
                case 8843: {
                    var1_1.getSkillGuideManager().showSelectedSkillCategory(7);
                    return;
                }
                case 8859: {
                    var1_1.getSkillGuideManager().showSelectedSkillCategory(8);
                    return;
                }
                case 8862: {
                    var1_1.getSkillGuideManager().showSelectedSkillCategory(9);
                    return;
                }
                case 8865: {
                    var1_1.getSkillGuideManager().showSelectedSkillCategory(10);
                    return;
                }
                case 15303: {
                    var1_1.getSkillGuideManager().showSelectedSkillCategory(11);
                    return;
                }
                case 15306: {
                    var1_1.getSkillGuideManager().showSelectedSkillCategory(12);
                    return;
                }
                case 15309: {
                    var1_1.getSkillGuideManager().showSelectedSkillCategory(13);
                    return;
                }
                case 152: {
                    var1_1.getMovementQueue().setRunning(false);
                    return;
                }
                case 153: {
                    if (var1_1.getQuestState(0) == 23) {
                        var1_1.ea();
                    }
                    var1_1.getMovementQueue().setRunning(true);
                    return;
                }
                case 150: {
                    var1_1.setAutoRetaliate(true);
                    return;
                }
                case 151: {
                    var1_1.setAutoRetaliate(false);
                    return;
                }
                case 906: {
                    var1_1.setBrightness(1);
                    return;
                }
                case 908: {
                    var1_1.setBrightness(2);
                    return;
                }
                case 910: {
                    var1_1.setBrightness(3);
                    return;
                }
                case 912: {
                    var1_1.setBrightness(4);
                    return;
                }
                case 914: {
                    var1_1.setMouseButtons(1);
                    return;
                }
                case 913: {
                    var1_1.setMouseButtons(0);
                    return;
                }
                case 915: {
                    var1_1.setPublicChatEffects(0);
                    return;
                }
                case 916: {
                    var1_1.setPublicChatEffects(1);
                    return;
                }
                case 957: {
                    var1_1.setSplitPrivateChat(1);
                    return;
                }
                case 958: {
                    var1_1.setSplitPrivateChat(0);
                    return;
                }
                case 12464: {
                    var1_1.setAcceptAid(0);
                    return;
                }
                case 12465: {
                    var1_1.setAcceptAid(1);
                    return;
                }
                case 930: {
                    var1_1.setMusicVolume(4);
                    return;
                }
                case 931: {
                    var1_1.setMusicVolume(3);
                    return;
                }
                case 932: {
                    var1_1.setMusicVolume(2);
                    return;
                }
                case 933: {
                    var1_1.setMusicVolume(1);
                    return;
                }
                case 934: {
                    var1_1.setMusicVolume(0);
                    return;
                }
                case 941: {
                    var1_1.setEffectVolume(4);
                    return;
                }
                case 942: {
                    var1_1.setEffectVolume(3);
                    return;
                }
                case 943: {
                    var1_1.setEffectVolume(2);
                    return;
                }
                case 944: {
                    var1_1.setEffectVolume(1);
                    return;
                }
                case 945: {
                    var1_1.setEffectVolume(0);
                    return;
                }
                case 5386: {
                    var1_1.setBankWithdrawNoteMode(true);
                    return;
                }
                case 5387: {
                    var1_1.setBankWithdrawNoteMode(false);
                    return;
                }
                case 18885: {
                    BankManager.depositInventory(var1_1);
                    return;
                }
                case 18886: {
                    BankManager.depositEquipment(var1_1);
                    return;
                }
                case 8130: {
                    var1_1.setBankRearrangeMode(BankRearrangeMode.a);
                    return;
                }
                case 8131: {
                    var1_1.setBankRearrangeMode(BankRearrangeMode.b);
                    return;
                }
            }
            if (BarrowsManager.handlePuzzleButtonClick(var1_1, var2_3) || GrandExchangeManager.handleButtonClick(var1_1, var2_3) || MagicSpellAction.handleAutocastButton(var1_1, var2_3)) break block184;
            if (var1_1.getEquipmentManager().handleAttackStyleButton(var2_3)) {
                var1_1.getEquipmentManager().refreshWeaponInterface();
                return;
            }
            if (var1_1.getPrayerManager().handleButtonClick(var2_3) || var1_1.dJ()) break block184;
            switch (var2_3) {
                case 14175: {
                    if (var1_1.fc() != null) {
                        var3_8 = BarrowsRepairHandler.b(var1_1.fc());
                        if (var3_8 != null) {
                            if (var1_1.getInventoryManager().getContainer().containsItem(var1_1.fc().getId())) {
                                var5_14 = var1_1;
                                var5_14.packetSender.sendSoundEffect(376, 1, 0);
                                if (!ServerSettings.adminInteractionsAllowed && var1_1.getPlayerRights() >= 2) {
                                    var5_14 = var1_1;
                                    var5_14.packetSender.sendGameMessage("Your item disappears because you're an administrator.");
                                } else {
                                    GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(var3_8.a(), 1), var1_1));
                                }
                                if (!var1_1.getInventoryManager().removeItemFromSlot(var1_1.fc(), var1_1.getSelectedItemSlot())) {
                                    var1_1.getInventoryManager().removeItem(var1_1.fc());
                                }
                            }
                            var1_1.getEquipmentManager().refreshCarriedValue();
                        } else {
                            if (var1_1.fc().getDefinition().getName().toLowerCase().contains("progress hat")) {
                                var1_1.eF();
                            }
                            if (var1_1.fc().getDefinition().getName().toLowerCase().contains("clue scroll")) {
                                var1_1.ar = 0;
                            }
                            var1_1.getInventoryManager().removeItem(var1_1.fc());
                        }
                    }
                    var1_1.a((ItemStack)null);
                }
                case 14176: {
                    var1_1.a((ItemStack)null);
                    var5_14 = var1_1;
                    var5_14.packetSender.closeInterfaces();
                    break;
                }
                case 12566: {
                    var1_1.getTeleportManager().castSpellbookTeleport(var1_1.getTeleotherDestination());
                }
                case 12568: {
                    var5_14 = var1_1;
                    var5_14.packetSender.closeInterfaces();
                    return;
                }
                case 168: {
                    var1_1.getUpdateState().setAnimation(855);
                    return;
                }
                case 169: {
                    var1_1.getUpdateState().setAnimation(856);
                    return;
                }
                case 162: {
                    var1_1.getUpdateState().setAnimation(857);
                    return;
                }
                case 164: {
                    var1_1.getUpdateState().setAnimation(858);
                    return;
                }
                case 165: {
                    var1_1.getUpdateState().setAnimation(859);
                    return;
                }
                case 161: {
                    var1_1.getUpdateState().setAnimation(860);
                    return;
                }
                case 170: {
                    var1_1.getUpdateState().setAnimation(861);
                    return;
                }
                case 171: {
                    var1_1.getUpdateState().setAnimation(862);
                    return;
                }
                case 163: {
                    var1_1.getUpdateState().setAnimation(863);
                    return;
                }
                case 167: {
                    var1_1.getUpdateState().setAnimation(864);
                    return;
                }
                case 172: {
                    var1_1.getUpdateState().setAnimation(865);
                    return;
                }
                case 166: {
                    var1_1.getUpdateState().setAnimation(866);
                    return;
                }
                case 13362: {
                    var1_1.getUpdateState().setAnimation(2105);
                    return;
                }
                case 13363: {
                    var1_1.getUpdateState().setAnimation(2106);
                    return;
                }
                case 13364: {
                    var1_1.getUpdateState().setAnimation(2107);
                    return;
                }
                case 13365: {
                    var1_1.getUpdateState().setAnimation(2108);
                    return;
                }
                case 13366: {
                    var1_1.getUpdateState().setAnimation(2109);
                    return;
                }
                case 13367: {
                    var1_1.getUpdateState().setAnimation(2110);
                    return;
                }
                case 13368: {
                    var1_1.getUpdateState().setAnimation(2111);
                    return;
                }
                case 13369: {
                    var1_1.getUpdateState().setAnimation(2112);
                    return;
                }
                case 13370: {
                    var1_1.getUpdateState().setAnimation(2113);
                    return;
                }
                case 11100: {
                    var5_14 = var1_1;
                    var5_14.packetSender.lockPlayerForTicks(4);
                    var1_1.getUpdateState().setAnimation(1368);
                    var1_1.getUpdateState().setGraphic(574);
                    return;
                }
                case 667: {
                    var1_1.getUpdateState().setAnimation(1131);
                    return;
                }
                case 6503: {
                    var1_1.getUpdateState().setAnimation(1130);
                    return;
                }
                case 6506: {
                    var1_1.getUpdateState().setAnimation(1129);
                    return;
                }
                case 666: {
                    var1_1.getUpdateState().setAnimation(1128);
                    return;
                }
                case 13383: {
                    var1_1.getUpdateState().setAnimation(2127);
                    return;
                }
                case 13384: {
                    var1_1.getUpdateState().setAnimation(2128);
                    return;
                }
                case 18691: {
                    var1_1.getUpdateState().setAnimation(2836);
                    return;
                }
                case 18689: {
                    var1_1.getUpdateState().setAnimation(3544);
                    return;
                }
                case 18688: {
                    var1_1.getUpdateState().setAnimation(3543);
                    return;
                }
                case 18464: {
                    var1_1.getUpdateState().setAnimation(3544);
                    return;
                }
                case 18465: {
                    var1_1.getUpdateState().setAnimation(3543);
                    return;
                }
                case 15166: {
                    var1_1.getUpdateState().setAnimation(2836);
                    return;
                }
                case 6020: {
                    var5_14 = var1_1;
                    var5_14.packetSender.closeInterfaces();
                    var1_1.getEquipmentManager().unequipSlot(12);
                    return;
                }
                case 3546: {
                    GameplayHelper.r(var1_1);
                    return;
                }
                case 3420: {
                    GameplayHelper.q(var1_1);
                    return;
                }
                case 3651: {
                    var1_1.f(true);
                    var1_1.getUpdateState().setUpdateRequired(true);
                    var5_14 = var1_1;
                    var5_14.packetSender.closeInterfaces();
                    if (var1_1.getQuestState(0) == 0) {
                        var1_1.setQuestState(0, 2);
                        var1_1.getQuestManager().refreshQuestJournal();
                    }
                    return;
                }
                case 2458: {
                    if (!var1_1.getRecentCombatTimer().hasElapsed()) {
                        var5_14 = var1_1;
                        var5_14.packetSender.sendGameMessage("You have to wait 10 seconds after combat in order to logout.");
                        return;
                    }
                    if (var1_1.isInDuelArena()) {
                        var5_14 = var1_1;
                        var5_14.packetSender.sendGameMessage("You can't logout during a duel fight!");
                        return;
                    }
                    if (var1_1.getSingleCombatTimer().hasElapsed()) {
                        var5_14 = var1_1;
                        var5_14.packetSender.sendLogout();
                        var1_1.disconnect();
                    }
                    return;
                }
            }
            if (RandomEventManager.handleLampButtonClick(var1_1, var2_3) || var1_1.handleSpecialAttackButton(var2_3) || TravelManager.handleGnomeGliderButton(var1_1, var2_3) || CanoeTravelManager.handleBuildButton(var1_1, var2_3)) break block184;
            var4_11 = var2_3;
            var3_9 = var1_1;
            var4_12 = MysticStaffEnchantment.a(var4_11);
            if (var4_12 != null) {
                if (!var3_9.getInventoryManager().containsItem(var4_12.a())) {
                    var5_14 = var3_9;
                    var5_14.packetSender.sendGameMessage("You need a battlestaff to do this!");
                    v0 = true;
                } else if (!var3_9.getInventoryManager().containsItemAmount(995, 40000)) {
                    var5_14 = var3_9;
                    var5_14.packetSender.sendGameMessage("You don't have enough coins with you!");
                    v0 = true;
                } else {
                    var3_9.getInventoryManager().removeItem(new ItemStack(var4_12.a(), 1));
                    var3_9.getInventoryManager().removeItem(new ItemStack(995, 40000));
                    var3_9.getInventoryManager().addItem(new ItemStack(var4_12.b(), 1));
                    var5_14 = var3_9;
                    var5_14.packetSender.sendGameMessage("Thormac enchants your staff into a mystic staff.");
                    var5_14 = var3_9;
                    var5_14.packetSender.closeInterfaces();
                    v0 = true;
                }
            } else {
                v0 = false;
            }
            if (v0 || CanoeTravelManager.handleDestinationButton(var1_1, var2_3) || var1_1.getSkillGuideManager().handleButtonClick(var2_3) || var1_1.getSandwichLadyManager().handleButtonClick(var2_3) || GameplayHelper.handleSextantButtonClick(var1_1, var2_3)) break block184;
            var5_14 = var1_1;
            if (var5_14.interfaceAction != "tanning" && (var3_9 = (SpellDefinition)var1_1.getSpellbook().getSpellByButtonId().get(var2_3)) != null) {
                MagicSpellAction.castSelfSpell(var1_1, (SpellDefinition)var3_9);
                return;
            }
            if (var1_1.getBankPinManager().handleButtonClick(var2_3) || var1_1.getEmoteManager().handleButtonClick(var2_3)) break block184;
            var4_13 = var2_3;
            var3_9 = var1_1;
            var5_14 = var3_9;
            if (var3_9.interfaceAction == "tanning" && var4_13 == 7454) {
                var5_14 = var3_9;
                var5_14.packetSender.sendEnterInputPrompt(var4_13);
                v1 = true;
            } else {
                switch (var4_13) {
                    case 1748: 
                    case 2414: 
                    case 3988: 
                    case 3992: 
                    case 3996: 
                    case 4000: 
                    case 4158: 
                    case 6200: 
                    case 7442: 
                    case 7447: 
                    case 8687: 
                    case 8691: 
                    case 8696: 
                    case 8701: 
                    case 8706: 
                    case 8711: 
                    case 8871: 
                    case 8875: 
                    case 8886: 
                    case 8890: 
                    case 8894: 
                    case 8906: 
                    case 8910: 
                    case 8914: 
                    case 8918: 
                    case 8946: 
                    case 8950: 
                    case 8954: 
                    case 8958: 
                    case 8962: 
                    case 11471: 
                    case 11475: 
                    case 12397: 
                    case 12401: 
                    case 12405: 
                    case 12409: 
                    case 13718: 
                    case 14801: 
                    case 14802: 
                    case 14803: 
                    case 14804: 
                    case 14805: 
                    case 14806: 
                    case 14807: 
                    case 14808: {
                        var5_14 = var3_9;
                        var5_14.packetSender.sendEnterInputPrompt(var4_13);
                        v1 = true;
                        break;
                    }
                    default: {
                        v1 = false;
                    }
                }
            }
            if (v1 || DairyChurnHandler.handleButtonClick(var1_1, var2_3) || CraftingHandler.handlePotteryButton(var1_1, var2_3, 0) || CraftingHandler.handleSilverCraftingButton(var1_1, var2_3, 0) || CraftingHandler.handleSpinningButton(var1_1, var2_3, 0) || CraftingHandler.handleGlassblowingButton(var1_1, var2_3, 0) || GameplayHelper.a(var1_1, var2_3, 0)) break block184;
            var4_13 = var2_3;
            var3_9 = var1_1;
            var5_14 = var3_9;
            if (var3_9.interfaceAction != "tanning") ** GOTO lbl-1000
            switch (var4_13) {
                case 7440: 
                case 8689: 
                case 14817: {
                    GameplayHelper.b((Player)var3_9, 1, 1, 1739, 1741);
                    v2 = true;
                    break;
                }
                case 7441: 
                case 8688: 
                case 14809: {
                    GameplayHelper.b((Player)var3_9, 5, 1, 1739, 1741);
                    v2 = true;
                    break;
                }
                case 7443: 
                case 8686: 
                case 14793: {
                    GameplayHelper.b((Player)var3_9, var3_9.getInventoryManager().getContainer().getItemAmount(1739), 1, 1739, 1741);
                    v2 = true;
                    break;
                }
                case 7452: 
                case 8693: 
                case 14818: {
                    GameplayHelper.b((Player)var3_9, 1, 3, 1739, 1743);
                    v2 = true;
                    break;
                }
                case 7453: 
                case 8692: 
                case 14810: {
                    GameplayHelper.b((Player)var3_9, 5, 3, 1739, 1743);
                    v2 = true;
                    break;
                }
                case 7455: 
                case 8690: 
                case 14794: {
                    GameplayHelper.b((Player)var3_9, var3_9.getInventoryManager().getContainer().getItemAmount(1739), 3, 1739, 1743);
                    v2 = true;
                    break;
                }
                case 14819: {
                    GameplayHelper.b((Player)var3_9, 1, 20, 6287, 6289);
                    v2 = true;
                    break;
                }
                case 14811: {
                    GameplayHelper.b((Player)var3_9, 5, 20, 6287, 6289);
                    v2 = true;
                    break;
                }
                case 14795: {
                    GameplayHelper.b((Player)var3_9, var3_9.getInventoryManager().getContainer().getItemAmount(6287), 20, 6287, 6289);
                    v2 = true;
                    break;
                }
                case 8698: 
                case 14821: {
                    GameplayHelper.b((Player)var3_9, 1, 20, 1753, 1745);
                    v2 = true;
                    break;
                }
                case 8697: 
                case 14813: {
                    GameplayHelper.b((Player)var3_9, 5, 20, 1753, 1745);
                    v2 = true;
                    break;
                }
                case 8695: 
                case 14797: {
                    GameplayHelper.b((Player)var3_9, var3_9.getInventoryManager().getContainer().getItemAmount(1753), 20, 1753, 1745);
                    v2 = true;
                    break;
                }
                case 8703: 
                case 14822: {
                    GameplayHelper.b((Player)var3_9, 1, 20, 1751, 2505);
                    v2 = true;
                    break;
                }
                case 8702: 
                case 14814: {
                    GameplayHelper.b((Player)var3_9, 5, 20, 1751, 2505);
                    v2 = true;
                    break;
                }
                case 8700: 
                case 14798: {
                    GameplayHelper.b((Player)var3_9, var3_9.getInventoryManager().getContainer().getItemAmount(1751), 20, 1751, 2505);
                    v2 = true;
                    break;
                }
                case 8708: 
                case 14823: {
                    GameplayHelper.b((Player)var3_9, 1, 20, 1749, 2507);
                    v2 = true;
                    break;
                }
                case 8707: 
                case 14815: {
                    GameplayHelper.b((Player)var3_9, 5, 20, 1749, 2507);
                    v2 = true;
                    break;
                }
                case 8705: 
                case 14799: {
                    GameplayHelper.b((Player)var3_9, var3_9.getInventoryManager().getContainer().getItemAmount(1749), 20, 1749, 2507);
                    v2 = true;
                    break;
                }
                case 8713: 
                case 14824: {
                    GameplayHelper.b((Player)var3_9, 1, 20, 1747, 2509);
                    v2 = true;
                    break;
                }
                case 8712: 
                case 14816: {
                    GameplayHelper.b((Player)var3_9, 5, 20, 1747, 2509);
                    v2 = true;
                    break;
                }
                case 8710: 
                case 14800: {
                    GameplayHelper.b((Player)var3_9, var3_9.getInventoryManager().getContainer().getItemAmount(1747), 20, 1747, 2509);
                    v2 = true;
                    break;
                }
                default: lbl-1000:
                // 2 sources

                {
                    v2 = false;
                }
            }
            if (!(v2 || CraftingHandler.handleWeavingButton(var1_1, var2_3, 0) || CraftingHandler.handleDramenStaffButton(var1_1, var2_3, 0) || EntityUpdateState.a(var1_1, var2_3, 0) || CacheArchive.handleLogCuttingButton(var1_1, var2_3, 0) || CookingManager.handleCookingButton(var1_1, var2_3) || GameplayHelper.d(var1_1, var2_3) || SmeltingHandler.handleSmeltingButton(var1_1, var2_3, 0) || var1_1.getDialogueManager().handleOptionButton(var2_3) || var1_1.getPlayerRights() <= 1 || !ServerSettings.debugModeEnabled)) {
                System.out.println("button " + var2_3 + " doesn't do anything");
            }
        }
    }
}

