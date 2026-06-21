/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net.packet.handler;

import com.rs2.ServerSettings;
import com.rs2.bot.BotRoute;
import com.rs2.bot.route.BotWorldRouteChoice;
import com.rs2.cache.CacheArchive;
import com.rs2.cache.CacheDefinitionIndex;
import com.rs2.cache.CacheFile;
import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.Entity;
import com.rs2.model.EntityTargetMovement;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.c.ProjectileDefinition;
import com.rs2.model.clue.AnagramClue;
import com.rs2.model.clue.CoordinateClueHandler;
import com.rs2.model.clue.CrypticDigClue;
import com.rs2.model.clue.MapClue;
import com.rs2.model.clue.PuzzleBoxHandler;
import com.rs2.model.clue.TreasureTrailManager;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.gameplay.barrows.BarrowsManager;
import com.rs2.model.gameplay.godwars.GodWarsDungeonManager;
import com.rs2.model.gameplay.magetrainingarena.MageTrainingArenaRewardShop;
import com.rs2.model.gameplay.partyroom.PartyRoomManager;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemService;
import com.rs2.model.item.ItemStack;
import com.rs2.model.item.action.BarrowsRepairHandler;
import com.rs2.model.item.action.BirdNestSearchHandler;
import com.rs2.model.item.action.CasketRewardHandler;
import com.rs2.model.item.action.CaveLightSourceDefinition;
import com.rs2.model.item.action.DyeMixingHandler;
import com.rs2.model.item.action.GodBookHandler;
import com.rs2.model.item.action.SpinningPlateHandler;
import com.rs2.model.item.action.ToyHorseyHandler;
import com.rs2.model.player.BankManager;
import com.rs2.model.player.GrandExchangeManager;
import com.rs2.model.player.PetManager;
import com.rs2.model.player.Player;
import com.rs2.model.player.PlayerGroup;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.model.shop.ShopManager;
import com.rs2.model.skill.ItemCombinationHandler;
import com.rs2.model.skill.cooking.FoodPreparationRecipe;
import com.rs2.model.skill.cooking.MultiIngredientFoodRecipe;
import com.rs2.model.skill.cooking.PieRecipe;
import com.rs2.model.skill.crafting.BattlestaffCraftingHandler;
import com.rs2.model.skill.crafting.CraftingHandler;
import com.rs2.model.skill.crafting.GemCuttingHandler;
import com.rs2.model.skill.crafting.JewelleryCraftingData;
import com.rs2.model.skill.crafting.JewelleryCraftingHandler;
import com.rs2.model.skill.farming.AllotmentPatchManager;
import com.rs2.model.skill.farming.CropStorageDefinition;
import com.rs2.model.skill.farming.MithrilSeedFlowerHandler;
import com.rs2.model.skill.fletching.GemBoltTipDefinition;
import com.rs2.model.skill.herblore.CleanHerbDefinition;
import com.rs2.model.skill.herblore.HerbloreHandler;
import com.rs2.model.skill.herblore.PestleAndMortarHandler;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.skill.magic.Spellbook;
import com.rs2.model.skill.runecrafting.EssencePouchDefinition;
import com.rs2.model.skill.runecrafting.RunecraftingHandler;
import com.rs2.model.skill.smithing.SmithingHandler;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.net.packet.ByteOrder;
import com.rs2.net.packet.ByteTransform;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;
import com.rs2.net.packet.PacketReader;
import com.rs2.net.packet.PacketSender;
import com.rs2.net.packet.handler.DigSearchTask;
import com.rs2.net.packet.handler.GroundItemFiremakingTask;
import com.rs2.net.packet.handler.TinderboxOnGroundItemTask;
import com.rs2.util.GameUtil;

public final class ItemActionPacketHandler
implements PacketHandler {
    /*
     * Unable to fully structure code
     */
    @Override
    public final void handle(Player var1_1, IncomingPacket var2_2) {
        if (var1_1.isActionLocked()) {
            return;
        }
        switch (var2_2.getOpcode()) {
            case 214: {
                var1_1.setSelectedItemInterfaceId(var2_2.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE));
                var2_2.getReader().readSignedByte(ByteTransform.NEGATE);
                var4_4 = var2_2.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE);
                var3_12 = var2_2.getReader().readSignedShort(ByteOrder.LITTLE);
                var2_3 = var2_2.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE);
                var5_19 = InterfaceDefinition.forId(var1_1.getSelectedItemInterfaceId());
                if (var1_1.isInterfaceOpen(var5_19)) {
                    switch (var1_1.getSelectedItemInterfaceId()) {
                        case 5382: 
                        case 19532: 
                        case 19533: 
                        case 19534: 
                        case 19535: 
                        case 19536: 
                        case 19537: 
                        case 19538: 
                        case 19539: 
                        case 19540: {
                            BankManager.rearrangeBankItem((Player)var1_1, var4_4, var3_12, var1_1.getSelectedItemInterfaceId(), var2_3);
                            return;
                        }
                        case 3214: 
                        case 5064: {
                            var6_21 = var1_1.getInventoryManager().getContainer().getItemAt(var4_4);
                            if (var6_21 == null || !var1_1.getInventoryManager().containsItemStack(var6_21)) break;
                            var1_1.getInventoryManager().swapSlots(var4_4, var3_12);
                            var1_1.getInventoryManager().refresh();
                        }
                    }
                }
                return;
            }
        }
        block7 : switch (var2_2.getOpcode()) {
            case 25: {
                var1_1.resetInteractionState();
                var3_13 = var2_2;
                var2_2 = var1_1;
                var1_1 = this;
                var3_13.getReader().readSignedShort();
                var4_5 = var3_13.getReader().readSignedShort(ByteTransform.ADD);
                var2_2.setInteractionTargetId(var3_13.getReader().readSignedShort());
                var2_2.setInteractionTargetY(var3_13.getReader().readSignedShort(ByteTransform.ADD));
                var2_2.setInteractionTargetPlane(var2_2.getPosition().getPlane());
                var3_13.getReader().readSignedShort();
                var2_2.setInteractionTargetX(var3_13.getReader().readSignedShort());
                if (var4_5 == 590) {
                    var3_14 = var2_2.nextActionSequence();
                    var2_2.setActiveCycleEvent(new TinderboxOnGroundItemTask((ItemActionPacketHandler)var1_1, (Player)var2_2, var3_14));
                    CycleEventHandler.getInstance().schedule((Entity)var2_2, var2_2.getActiveCycleEvent(), 1);
                }
                return;
            }
            case 53: {
                var1_1.resetInteractionState();
                var4_6 = var2_2.getReader().readSignedShort();
                var3_15 = var2_2.getReader().readSignedShort(ByteTransform.ADD);
                var2_2.getReader().readSignedShort();
                var2_2.getReader().readSignedShort();
                if (var3_15 > 28 || var4_6 > 28) break;
                var2_2 = var1_1.getInventoryManager().getContainer().getItemAt(var3_15);
                var5_20 = var1_1.getInventoryManager().getContainer().getItemAt(var4_6);
                if (var2_2 == null || var5_20 == null || !var2_2.isValid() || !var5_20.isValid()) break;
                if (var2_2.getDefinition().isMembersOnly() || var5_20.getDefinition().isMembersOnly()) {
                    if (!var1_1.isMember()) {
                        var1_1.packetSender.sendGameMessage("You need a members account to access members content.");
                        break;
                    }
                    if (ServerSettings.freeToPlayWorld) {
                        var1_1.packetSender.sendGameMessage("You need to be in members world to access members content.");
                        break;
                    }
                }
                var6_22 = var2_2.getId();
                var7_23 = var5_20.getId();
                if (var1_1.getDuelSession().getOpponent() != null && !var1_1.isInDuelArena()) {
                    var1_1.getDuelController().resetDuel(true);
                    break;
                }
                if (var1_1.getQuestManager().handleItemOnItem(var6_22, var7_23) || ServerSettings.content2007Enabled && GodWarsDungeonManager.handleGodWarsItemCombination((Player)var1_1, var6_22, var7_23)) ** GOTO lbl605
                var10_24 = var7_23;
                var9_27 = var6_22;
                var8_30 = var1_1;
                var13_33 = FoodPreparationRecipe.forIngredients(var9_27, var10_24);
                if (var13_33 != null) ** GOTO lbl78
                v0 = false;
                ** GOTO lbl139
lbl78:
                // 1 sources

                var8_30.getPacketSender().closeInterfaces();
                if (ServerSettings.cookingEnabled) ** GOTO lbl84
                var8_30.getPacketSender().sendGameMessage("This skill is currently disabled.");
                ** GOTO lbl138
lbl84:
                // 1 sources

                if (var8_30.getSkillManager().getCurrentLevels()[7] >= var13_33.getRequiredLevel()) ** GOTO lbl87
                var8_30.getDialogueManager().showOneLineStatement("You need a cooking level of " + var13_33.getRequiredLevel() + " to do this.");
                ** GOTO lbl138
lbl87:
                // 1 sources

                v1 = var14_35 = var13_33.getIngredientAmount() == 0 ? 1 : var13_33.getIngredientAmount();
                if (var8_30.getInventoryManager().getItemAmount(var13_33.getIngredientItemId()) >= var14_35) ** GOTO lbl91
                var8_30.getDialogueManager().showOneLineStatement("You need " + var14_35 + " " + ItemDefinition.forId(var13_33.getIngredientItemId()).getName().toLowerCase() + " to do this");
                ** GOTO lbl138
lbl91:
                // 1 sources

                if (var13_33.getProductItemId() != 1871 && var13_33.getProductItemId() != 7080 && var13_33.getProductItemId() != 7074 || var8_30.getInventoryManager().getContainer().containsItem(946)) ** GOTO lbl95
                var8_30.getPacketSender().sendGameMessage("You need a knife for that.");
                ** GOTO lbl138
lbl95:
                // 1 sources

                if (var13_33.getProductItemId() != 1889) ** GOTO lbl117
                if (var8_30.getInventoryManager().getContainer().containsItem(1933) && var8_30.getInventoryManager().getContainer().containsItem(1927) && var8_30.getInventoryManager().getContainer().containsItem(1944)) ** GOTO lbl99
                v0 = true;
                ** GOTO lbl139
lbl99:
                // 1 sources

                var15_39 = var8_30;
                var15_39.getInventoryManager().removeItem(new ItemStack(1933));
                var15_39.getInventoryManager().removeItem(new ItemStack(1927));
                var15_39.getInventoryManager().removeItem(new ItemStack(1944));
                var15_39.getInventoryManager().removeItem(new ItemStack(1887));
                var15_39.getInventoryManager().addItem(new ItemStack(1925));
                var15_39.getInventoryManager().addItem(new ItemStack(1931));
                var15_39.getInventoryManager().addItem(new ItemStack(1889));
                var15_39.getPacketSender().sendGameMessage("You mix the ingredients together and make a cake.");
                ** GOTO lbl138
lbl117:
                // 1 sources

                if (var13_33.getProductItemId() == 7066) {
                    var8_30.getInventoryManager().addItem(new ItemStack(1923));
                }
                if (var13_33.usesPutIntoMessage()) {
                    var8_30.getPacketSender().sendGameMessage("You put the " + ItemDefinition.forId(var13_33.getIngredientItemId()).getName().toLowerCase() + " into the " + ItemDefinition.forId(var13_33.getBaseItemId()).getName().toLowerCase() + " and make a " + ItemDefinition.forId(var13_33.getProductItemId()).getName().toLowerCase() + ".");
                } else {
                    var8_30.getPacketSender().sendGameMessage("You mix the " + ItemDefinition.forId(var13_33.getIngredientItemId()).getName().toLowerCase() + " with the " + ItemDefinition.forId(var13_33.getBaseItemId()).getName().toLowerCase() + " and make a " + ItemDefinition.forId(var13_33.getProductItemId()).getName().toLowerCase() + ".");
                }
                if (var13_33.getProductItemId() != 1889) {
                    var8_30.getInventoryManager().removeItem(new ItemStack(var13_33.getIngredientItemId(), var14_35));
                }
                var8_30.getInventoryManager().removeItem(new ItemStack(var13_33.getBaseItemId()));
                var8_30.getInventoryManager().addItem(new ItemStack(var13_33.getProductItemId()));
                var8_30.getSkillManager().addExperience(7, var13_33.getExperience());
                if (var13_33.getReturnedItemId() != 0) {
                    var8_30.getInventoryManager().addItem(new ItemStack(var13_33.getReturnedItemId()));
                }
lbl138:
                // 8 sources

                v0 = true;
lbl139:
                // 3 sources

                if (v0) ** GOTO lbl605
                var10_24 = var7_23;
                var9_27 = var6_22;
                var8_30 = var1_1;
                var13_33 = MultiIngredientFoodRecipe.forFirstIngredientItemId(var9_27) != null ? MultiIngredientFoodRecipe.forFirstIngredientItemId(var9_27) : MultiIngredientFoodRecipe.forFirstIngredientItemId(var10_24);
                var14_36 = MultiIngredientFoodRecipe.forFirstStageProductItemId(var9_27) != null ? MultiIngredientFoodRecipe.forFirstStageProductItemId(var9_27) : MultiIngredientFoodRecipe.forFirstStageProductItemId(var10_24);
                var15_40 = -1;
                var16_43 = -1;
                var17_44 = -1;
                var18_45 = null;
                if (var13_33 != null && (var9_27 == var13_33.getBaseItemId() || var10_24 == var13_33.getBaseItemId())) {
                    var15_40 = var13_33.getFirstIngredientItemId();
                    var16_43 = var13_33.getBaseItemId();
                    var17_44 = var13_33.getFirstStageProductItemId();
                    var18_45 = var13_33;
                }
                if (!(var14_36 == null || var9_27 != var14_36.getFirstStageProductItemId() && var10_24 != var14_36.getFirstStageProductItemId() || var9_27 != var14_36.getSecondIngredientItemId() && var10_24 != var14_36.getSecondIngredientItemId())) {
                    var15_40 = var14_36.getSecondIngredientItemId();
                    var16_43 = var14_36.getFirstStageProductItemId();
                    var17_44 = var14_36.getFinalProductItemId();
                    var18_45 = var14_36;
                }
                if (var18_45 == null) {
                    v2 = false;
                } else {
                    var8_30.getPacketSender().closeInterfaces();
                    if (!ServerSettings.cookingEnabled) {
                        var8_30.getPacketSender().sendGameMessage("This skill is currently disabled.");
                    } else if (var8_30.getSkillManager().getCurrentLevels()[7] < var18_45.getRequiredLevel()) {
                        var8_30.getPacketSender().sendGameMessage("You need a cooking level of " + var18_45.getRequiredLevel() + " to do this.");
                    } else if (var18_45.getFinalProductItemId() == 7068 && !var8_30.getInventoryManager().getContainer().containsItem(946)) {
                        var8_30.getPacketSender().sendGameMessage("You need a knife for that.");
                    } else {
                        if (var18_45.usesPutIntoMessage()) {
                            var8_30.getPacketSender().sendGameMessage("You put the " + ItemDefinition.forId(var15_40).getName().toLowerCase() + " into the " + ItemDefinition.forId(var16_43).getName().toLowerCase() + " and make a " + ItemDefinition.forId(var17_44).getName().toLowerCase() + ".");
                        } else {
                            var8_30.getPacketSender().sendGameMessage("You mix the " + ItemDefinition.forId(var15_40).getName().toLowerCase() + " with the " + ItemDefinition.forId(var16_43).getName().toLowerCase() + " and make a " + ItemDefinition.forId(var17_44).getName().toLowerCase() + ".");
                        }
                        var8_30.getInventoryManager().removeItem(new ItemStack(var9_27));
                        var8_30.getInventoryManager().removeItem(new ItemStack(var10_24));
                        var8_30.getInventoryManager().addOrDropItem(new ItemStack(var17_44));
                        var8_30.getSkillManager().addExperience(7, var18_45.getExperience());
                        if (var18_45.getFirstStageReturnedItemId() != 0 && var18_45 == var13_33) {
                            var8_30.getInventoryManager().addOrDropItem(new ItemStack(var18_45.getFirstStageReturnedItemId()));
                        }
                        if (var18_45.getFinalStageReturnedItemId() != 0 && var18_45 == var14_36) {
                            var8_30.getInventoryManager().addOrDropItem(new ItemStack(var18_45.getFinalStageReturnedItemId()));
                        }
                    }
                    v2 = true;
                }
                if (v2) ** GOTO lbl605
                var12_47 = var4_6;
                var11_53 = var3_15;
                var10_24 = var7_23;
                var9_27 = var6_22;
                var8_30 = var1_1;
                var13_33 = PieRecipe.forFirstIngredientItemId(var9_27) != null ? PieRecipe.forFirstIngredientItemId(var9_27) : PieRecipe.forFirstIngredientItemId(var10_24);
                var14_36 = PieRecipe.forFirstStagePieItemId(var9_27) != null ? PieRecipe.forFirstStagePieItemId(var9_27) : PieRecipe.forFirstStagePieItemId(var10_24);
                var15_41 = PieRecipe.forSecondStagePieItemId(var9_27) != null ? PieRecipe.forSecondStagePieItemId(var9_27) : PieRecipe.forSecondStagePieItemId(var10_24);
                var16_43 = -1;
                var17_44 = -1;
                var18_46 = -1;
                var19_58 = null;
                if (var13_33 != null && (var9_27 == var13_33.getPieShellItemId() || var10_24 == var13_33.getPieShellItemId())) {
                    var16_43 = var13_33.getFirstIngredientItemId();
                    var17_44 = var13_33.getPieShellItemId();
                    var18_46 = var13_33.getFirstStagePieItemId();
                    var19_58 = var13_33;
                }
                if (!(var14_36 == null || var9_27 != var14_36.getFirstStagePieItemId() && var10_24 != var14_36.getFirstStagePieItemId() || var9_27 != var14_36.getSecondIngredientItemId() && var10_24 != var14_36.getSecondIngredientItemId())) {
                    var16_43 = var14_36.getSecondIngredientItemId();
                    var17_44 = var14_36.getFirstStagePieItemId();
                    var18_46 = var14_36.getSecondStagePieItemId();
                    var19_58 = var14_36;
                }
                if (!(var15_41 == null || var9_27 != var15_41.getSecondStagePieItemId() && var10_24 != var15_41.getSecondStagePieItemId() || var9_27 != var15_41.getThirdIngredientItemId() && var10_24 != var15_41.getThirdIngredientItemId())) {
                    var16_43 = var15_41.getThirdIngredientItemId();
                    var17_44 = var15_41.getSecondStagePieItemId();
                    var18_46 = var15_41.getRawPieItemId();
                    var19_58 = var15_41;
                }
                if (var19_58 == null) {
                    v3 = false;
                } else {
                    var8_30.getPacketSender().closeInterfaces();
                    if (!ServerSettings.cookingEnabled) {
                        var8_30.getPacketSender().sendGameMessage("This skill is currently disabled.");
                    } else if (var8_30.getSkillManager().getCurrentLevels()[7] < var19_58.getRequiredLevel()) {
                        var8_30.getDialogueManager().showOneLineStatement("You need a cooking level of " + var19_58.getRequiredLevel() + " to do this.");
                    } else {
                        if (var19_58.usesPutIntoMessage()) {
                            var8_30.getPacketSender().sendGameMessage("You put the " + ItemDefinition.forId(var16_43).getName().toLowerCase() + " into the " + ItemDefinition.forId(var17_44).getName().toLowerCase() + " and make a " + ItemDefinition.forId(var18_46).getName().toLowerCase() + ".");
                        } else {
                            var8_30.getPacketSender().sendGameMessage("You mix the " + ItemDefinition.forId(var16_43).getName().toLowerCase() + " with the " + ItemDefinition.forId(var17_44).getName().toLowerCase() + " and make a " + ItemDefinition.forId(var18_46).getName().toLowerCase() + ".");
                        }
                        var8_30.getInventoryManager().removeItem(new ItemStack(var9_27));
                        var8_30.getInventoryManager().removeItem(new ItemStack(var10_24));
                        var8_30.getInventoryManager().setItemInSlot(new ItemStack(var18_46), var9_27 == var17_44 ? var11_53 : var12_47);
                        var8_30.getSkillManager().addExperience(7, var19_58.getExperience() / 5.0);
                        if (var19_58.getFirstStageReturnedItemId() != 0 && var19_58 == var13_33) {
                            var8_30.getInventoryManager().addItem(new ItemStack(var19_58.getFirstStageReturnedItemId()));
                        }
                        if (var19_58.getSecondStageReturnedItemId() != 0 && var19_58 == var14_36) {
                            var8_30.getInventoryManager().addItem(new ItemStack(var19_58.getSecondStageReturnedItemId()));
                        }
                        if (var19_58.getThirdStageReturnedItemId() != 0 && var19_58 == var15_41) {
                            var8_30.getInventoryManager().addItem(new ItemStack(var19_58.getThirdStageReturnedItemId()));
                        }
                    }
                    v3 = true;
                }
                if (v3 || GraphicEffect.handleAmmunitionFletching((Player)var1_1, var6_22, var7_23) || PlayerGroup.handleBowStringing((Player)var1_1, var6_22, var7_23)) ** GOTO lbl605
                var10_24 = var7_23;
                var9_27 = var6_22;
                var8_30 = var1_1;
                if (var9_27 != 946 && var10_24 != 946) ** GOTO lbl-1000
                if (var9_27 == 1511 || var10_24 == 1511) {
                    if (!var8_30.isMember()) {
                        var8_30.packetSender.sendGameMessage("You need a members account to access members content.");
                        v4 = true;
                    } else if (ServerSettings.freeToPlayWorld) {
                        var8_30.packetSender.sendGameMessage("You need to be in members world to access members content.");
                        v4 = true;
                    } else {
                        GameplayHelper.a((Player)var8_30, "normalCutting");
                        v4 = true;
                    }
                } else if (var9_27 == 1521 || var10_24 == 1521) {
                    GameplayHelper.a((Player)var8_30, "oakCutting");
                    v4 = true;
                } else if (var9_27 == 2862 || var10_24 == 2862) {
                    GameplayHelper.a((Player)var8_30, "acheyCutting");
                    v4 = true;
                } else if (var9_27 == 1519 || var10_24 == 1519) {
                    GameplayHelper.a((Player)var8_30, "willowCutting");
                    v4 = true;
                } else if (var9_27 == 1517 || var10_24 == 1517) {
                    GameplayHelper.a((Player)var8_30, "mapleCutting");
                    v4 = true;
                } else if (var9_27 == 1515 || var10_24 == 1515) {
                    GameplayHelper.a((Player)var8_30, "yewCutting");
                    v4 = true;
                } else if (var9_27 == 1513 || var10_24 == 1513) {
                    GameplayHelper.a((Player)var8_30, "magicCutting");
                    v4 = true;
                } else if (var9_27 == 771 || var10_24 == 771) {
                    var11_54 = "dramenBranch";
                    if (ServerSettings.cacheVersion < 334) {
                        var8_30.setInterfaceAction(var11_54);
                        CraftingHandler.handleDramenStaffButton((Player)var8_30, 2799, 1);
                    } else {
                        GameplayHelper.a((Player)var8_30, var11_54);
                    }
                    v4 = true;
                } else lbl-1000:
                // 2 sources

                {
                    v4 = false;
                }
                if (v4) ** GOTO lbl605
                var10_24 = var7_23;
                var9_27 = var6_22;
                var8_30 = var1_1;
                if (var9_27 != 1755 && var10_24 != 1755) ** GOTO lbl341
                var11_53 = -1;
                if (var9_27 == 411 || var10_24 == 411) {
                    var11_53 = 411;
                } else if (var9_27 == 413 || var10_24 == 413) {
                    var11_53 = 413;
                } else if (var9_27 == 1609 || var10_24 == 1609) {
                    var11_53 = 1609;
                }
                if (var11_53 == -1 || (var12_48 = GemBoltTipDefinition.forGemItemId(var11_53)) == null) ** GOTO lbl341
                if (ServerSettings.fletchingEnabled) {
                    if (var8_30.getSkillManager().getCurrentLevels()[9] < var12_48.getRequiredLevel()) {
                        var8_30.getDialogueManager().showOneLineStatement("You need a fletching level of " + var12_48.getRequiredLevel() + " to do this");
                        v5 = true;
                    } else {
                        var8_30.getInventoryManager().removeItem(new ItemStack(var12_48.getGemItemId(), 1));
                        var8_30.getInventoryManager().addItem(new ItemStack(var12_48.getBoltTipItemId(), var12_48.getBoltTipAmount()));
                        var8_30.getSkillManager().addExperience(9, var12_48.getExperience());
                        v5 = true;
                    }
                } else {
                    var8_30.getPacketSender().sendGameMessage("This skill is currently disabled.");
lbl341:
                    // 3 sources

                    v5 = false;
                }
                if (v5) ** GOTO lbl605
                if (GemCuttingHandler.handleGemCutting((Player)var1_1, var6_22, var7_23)) ** GOTO lbl605
                if (BattlestaffCraftingHandler.handleBattlestaffCrafting((Player)var1_1, var6_22, var7_23) || GameplayHelper.a((Player)var1_1, var6_22, var7_23, var3_15, var4_6)) ** GOTO lbl605
                var10_24 = var7_23;
                var9_27 = var6_22;
                var8_30 = var1_1;
                if (var9_27 != 1019 && var10_24 != 1019) ** GOTO lbl-1000
                if (!ServerSettings.craftingEnabled) {
                    var8_30.getPacketSender().sendGameMessage("This skill is currently disabled.");
                    v6 = true;
                } else {
                    var8_30.getInventoryManager().removeItem(new ItemStack(var9_27));
                    var8_30.getInventoryManager().removeItem(new ItemStack(var10_24));
                    var8_30.getSkillManager().addExperience(12, 2.0);
                    if (var9_27 == 1763 || var10_24 == 1763) {
                        var8_30.getPacketSender().sendGameMessage("You colour the cape into a red cape.");
                        var8_30.getInventoryManager().addItem(new ItemStack(1007));
                        v6 = true;
                    } else if (var9_27 == 1765 || var10_24 == 1765) {
                        var8_30.getPacketSender().sendGameMessage("You colour the cape into a yellow cape.");
                        var8_30.getInventoryManager().addItem(new ItemStack(1023));
                        v6 = true;
                    } else if (var9_27 == 1767 || var10_24 == 1767) {
                        var8_30.getPacketSender().sendGameMessage("You colour the cape into a blue cape.");
                        var8_30.getInventoryManager().addItem(new ItemStack(1021));
                        v6 = true;
                    } else if (var9_27 == 1769 || var10_24 == 1769) {
                        var8_30.getPacketSender().sendGameMessage("You colour the cape into a orange cape.");
                        var8_30.getInventoryManager().addItem(new ItemStack(1031));
                        v6 = true;
                    } else if (var9_27 == 1771 || var10_24 == 1771) {
                        var8_30.getPacketSender().sendGameMessage("You colour the cape into a green cape.");
                        var8_30.getInventoryManager().addItem(new ItemStack(1027));
                        v6 = true;
                    } else if (var9_27 == 1773 || var10_24 == 1773) {
                        var8_30.getPacketSender().sendGameMessage("You colour the cape into a purple cape.");
                        var8_30.getInventoryManager().addItem(new ItemStack(1029));
                        v6 = true;
                    } else lbl-1000:
                    // 2 sources

                    {
                        v6 = false;
                    }
                }
                if (v6) ** GOTO lbl605
                if (var6_22 == 1785 && var7_23 == 1775 || var7_23 == 1785 && var6_22 == 1775) {
                    GameplayHelper.a((Player)var1_1, "glassMaking");
                    break;
                }
                if (DyeMixingHandler.mixDyes((Player)var1_1, var6_22, var7_23)) ** GOTO lbl605
                var8_31 = 0;
                while (var8_31 < JewelleryCraftingData.amuletStringingRecipes.length) {
                    if (JewelleryCraftingData.amuletStringingRecipes[var8_31][0] == var6_22 || JewelleryCraftingData.amuletStringingRecipes[var8_31][0] == var7_23) {
                        JewelleryCraftingHandler.stringAmulet((Player)var1_1, var8_31);
                        break block7;
                    }
                    ++var8_31;
                }
                if (GodBookHandler.handlePageOnBook((Player)var1_1, var6_22, var7_23)) ** GOTO lbl605
                var10_24 = var7_23;
                var9_27 = var6_22;
                var8_32 = var1_1;
                var11_53 = -1;
                var12_49 = 0;
                if (CropStorageDefinition.forProduceItemId(var9_27) != null) {
                    var11_53 = var9_27;
                    var12_49 = var10_24;
                } else if (CropStorageDefinition.forProduceItemId(var10_24) != null) {
                    var11_53 = var10_24;
                    var12_49 = var9_27;
                }
                if (var11_53 == -1 || new ItemStack(var12_49, 1).getDefinition().isNote()) {
                    v7 = false;
                } else {
                    var13_33 = CropStorageDefinition.forProduceItemId(var11_53);
                    if (var13_33 != null) {
                        if (var12_49 != (var13_33.isSack() != false ? 5418 : 5376) && (var12_49 < var13_33.getBaseContainerItemId() || var12_49 > var13_33.getBaseContainerItemId() + (var13_33.isSack() != false ? 16 : 6))) {
                            v7 = false;
                        } else {
                            var14_37 = var13_33.isSack() != false ? 10 : 5;
                            if (var12_49 != (var13_33.isSack() != false ? 5418 : 5376)) {
                                var14_37 = (var13_33.isSack() != false ? 9 : 4) - (var12_49 - var13_33.getBaseContainerItemId()) / 2;
                            }
                            if (var14_37 >= (var15_42 = var8_32.getInventoryManager().getItemAmount(var11_53))) {
                                var17_44 = var12_49 != (var13_33.isSack() != false ? 5418 : 5376) ? var12_49 + 2 * var15_42 : var13_33.getBaseContainerItemId() + 2 * (var15_42 - 1);
                                var16_43 = var15_42;
                            } else {
                                var17_44 = var13_33.getBaseContainerItemId() + (var13_33.isSack() != false ? 18 : 8);
                                var16_43 = var14_37;
                            }
                            var8_32.getInventoryManager().removeItem(new ItemStack(var11_53, var16_43));
                            var8_32.getInventoryManager().removeItem(new ItemStack(var12_49, 1));
                            var8_32.getInventoryManager().addItem(new ItemStack(var17_44, 1));
                            v7 = true;
                        }
                    } else {
                        v7 = false;
                    }
                }
                if (v7 || var1_1.getPlantPotHandler().plantSeedInPot(var2_2.getId(), var5_20.getId(), var3_15, var4_6) || var1_1.getPlantPotHandler().waterSeedling(var2_2.getId(), var5_20.getId()) || var1_1.getItemCombinationHandler().handleItemCombination((ItemStack)var2_2, var5_20)) ** GOTO lbl605
                if (ItemCombinationHandler.handleToolHeadAttachment((Player)var1_1, var6_22, var7_23)) {
                    var1_1.getPacketSender().sendGameMessage("You put together the head and handle.");
                    break;
                }
                if (var1_1.getSlayerManager().combineFungicideSpray(var6_22, var7_23) || HerbloreHandler.handlePotionMaking((Player)var1_1, (ItemStack)var2_2, var5_20, var3_15, var4_6) || PestleAndMortarHandler.handlePestleAndMortar((Player)var1_1, (ItemStack)var2_2, var5_20, var3_15, var4_6) || BotRoute.handleWeaponPoisoning((Player)var1_1, (ItemStack)var2_2, var5_20)) ** GOTO lbl605
                var10_25 = var5_20;
                var9_28 = var2_2;
                var8_32 = var1_1;
                if ((var9_28.getId() != 2347 || var10_25.getId() != 5974) && (var9_28.getId() != 5974 || var10_25.getId() != 2347)) ** GOTO lbl485
                if (ServerSettings.herbloreEnabled) ** GOTO lbl471
                var8_32.getPacketSender().sendGameMessage("This skill is currently disabled.");
                v8 = true;
                ** GOTO lbl511
lbl471:
                // 1 sources

                if (var8_32.getQuestState(29) == 1) ** GOTO lbl478
                var11_55 = QuestDefinition.forId(29);
                var12_50 = var11_55.getName();
                var8_32.getPacketSender().sendGameMessage("You need to complete " + var12_50 + " to do this.");
                v8 = true;
                ** GOTO lbl511
lbl478:
                // 1 sources

                var8_32.getPacketSender().sendGameMessage("You crush the coconut with a hammer.");
                var8_32.getInventoryManager().removeItem(new ItemStack(5974));
                var8_32.getInventoryManager().addItem(new ItemStack(5976));
                ** GOTO lbl-1000
lbl485:
                // 1 sources

                if (var9_28.getId() == 229 && var10_25.getId() == 5976 || var9_28.getId() == 5976 && var10_25.getId() == 229) {
                    if (!ServerSettings.herbloreEnabled) {
                        var8_32.getPacketSender().sendGameMessage("This skill is currently disabled.");
                        v8 = true;
                    } else if (var8_32.getQuestState(29) != 1) {
                        var11_56 = QuestDefinition.forId(29);
                        var12_51 = var11_56.getName();
                        var8_32.getPacketSender().sendGameMessage("You need to complete " + var12_51 + " to do this.");
                        v8 = true;
                    } else {
                        var8_32.getInventoryManager().removeItem(new ItemStack(229));
                        var8_32.getInventoryManager().addItem(new ItemStack(5935));
                        var8_32.getInventoryManager().removeItem(new ItemStack(5976));
                        var8_32.getInventoryManager().addItem(new ItemStack(5978));
                        var8_32.getPacketSender().sendGameMessage("You overturn the coconut into a vial.");
                        v8 = true;
                    }
                } else lbl-1000:
                // 2 sources

                {
                    v8 = false;
                }
lbl511:
                // 6 sources

                if (v8 || HerbloreHandler.combinePotionDoses((Player)var1_1, var6_22, var7_23, var3_15, var4_6)) ** GOTO lbl605
                if (var6_22 == 272 && var7_23 == 273 || var6_22 == 273 && var7_23 == 272) {
                    var1_1.getInventoryManager().removeItem(new ItemStack(272, 1));
                    var1_1.getInventoryManager().removeItem(new ItemStack(273, 1));
                    var1_1.getInventoryManager().addItem(new ItemStack(274));
                    var1_1.getPacketSender().sendGameMessage("You poison the fish food.");
                    break;
                }
                var10_26 = var7_23;
                var9_29 = var6_22;
                var8_32 = var1_1;
                if (var9_29 != 590 && var10_26 != 590) {
                    v9 = false;
                } else {
                    var11_57 = var9_29 != 590 ? var9_29 : var10_26;
                    var12_52 = CaveLightSourceDefinition.forItemId(var11_57);
                    if (var12_52 == null) {
                        v9 = false;
                    } else if (var12_52.getUnlitItemId() != var11_57) {
                        v9 = false;
                    } else {
                        var13_34 = var12_52.getFiremakingLevelRequirement();
                        if (var8_32.getSkillManager().getCurrentLevels()[11] < var13_34) {
                            var8_32.getDialogueManager().showOneLineStatement("You need a firemaking level of " + var13_34 + " to do that.");
                            v9 = true;
                        } else {
                            var8_32.getInventoryManager().removeItem(new ItemStack(var11_57));
                            var8_32.getInventoryManager().addItem(new ItemStack(var12_52.getLitItemId()));
                            var14_38 = ItemDefinition.forId(var11_57);
                            var8_32.getPacketSender().sendGameMessage("You light the " + var14_38.getName().toLowerCase() + ".");
                            v9 = true;
                        }
                    }
                }
                if (v9) ** GOTO lbl605
                if (var6_22 == 590 || var7_23 == 590) {
                    var1_1.getFiremakingHandler().startFiremaking(var6_22, var7_23, false, var1_1.getPosition().getX(), var1_1.getPosition().getY(), var1_1.getPosition().getPlane());
                    break;
                }
                if ((var6_22 != 1929 || var7_23 != 1933) && (var6_22 != 1933 || var7_23 != 1929)) ** GOTO lbl603
                if (var1_1.getQuestState(0) != 1) {
                    var1_1.getInventoryManager().removeItem(new ItemStack(1933));
                    var1_1.getInventoryManager().removeItem(new ItemStack(1929));
                    var1_1.getInventoryManager().addItem(new ItemStack(2307));
                    var1_1.getInventoryManager().addItem(new ItemStack(1925));
                    var1_1.getInventoryManager().addItem(new ItemStack(1931));
                    if (var1_1.getQuestState(0) == 18) {
                        var1_1.ea();
                    }
                    var1_1.getQuestManager().refreshQuestJournal();
                    break;
                }
                var1_1.setInterfaceAction("flour");
                var8_32 = "Bread dough";
                var2_2 = "Pastry dough";
                var3_16 = "Pizza base";
                var4_7 = "Pitta dough";
                if (ServerSettings.cacheVersion < 274) {
                    var1_1.getPacketSender().setInterfaceHiddenFlag(1, 2488);
                    var1_1.getPacketSender().setInterfaceHiddenFlag(0, 2489);
                    var1_1.getPacketSender().sendInterfaceText("What would you like to make?", 2481);
                    var1_1.getPacketSender().sendInterfaceText((String)var8_32, 2482);
                    var1_1.getPacketSender().sendInterfaceText((String)var2_2, 2483);
                    var1_1.getPacketSender().sendInterfaceText(var3_16, 2484);
                    var1_1.getPacketSender().sendInterfaceText(var4_7, 2485);
                    var1_1.getPacketSender().showChatboxInterface(2480);
                } else {
                    var1_1.getPacketSender().sendInterfaceText((String)var8_32, 8209);
                    var1_1.getPacketSender().sendInterfaceText((String)var2_2, 8210);
                    var1_1.getPacketSender().sendInterfaceText(var3_16, 8211);
                    var1_1.getPacketSender().sendInterfaceText(var4_7, 8212);
                    var1_1.getPacketSender().showChatboxInterface(8207);
                    break;
lbl603:
                    // 1 sources

                    var1_1.getPacketSender().sendGameMessage("Nothing interesting happens.");
                }
lbl605:
                // 17 sources

                return;
            }
            case 87: {
                var1_1.resetInteractionState();
                ItemActionPacketHandler.handleDropItem((Player)var1_1, (IncomingPacket)var2_2);
                return;
            }
            case 236: {
                var1_1.resetInteractionState();
                var1_1.setInteractionTargetY(var2_2.getReader().readSignedShort(ByteOrder.LITTLE));
                var1_1.setInteractionTargetId(var2_2.getReader().readSignedShort());
                var1_1.setInteractionTargetX(var2_2.getReader().readSignedShort(ByteOrder.LITTLE));
                var1_1.setInteractionTargetPlane(var1_1.getPosition().getPlane());
                if (var1_1.getInteractionTargetId() == 6888 && var1_1.getTelekineticTheatreController().isInsideTheatre()) {
                    var1_1.getTelekineticTheatreController().handleMazeItemPickupAttempt();
                    break;
                }
                GroundItemManager.getInstance();
                var4_8 = GroundItemManager.findVisibleItem((Player)var1_1, var1_1.getInteractionTargetId(), new Position(var1_1.getInteractionTargetX(), var1_1.getInteractionTargetY(), var1_1.getPosition().getPlane()));
                if (var4_8 != null && var1_1.getInventoryManager().canAddItem(var4_8.getItem())) {
                    if (var1_1.ownsClueScroll() && new ItemStack(var1_1.getInteractionTargetId()).getDefinition().getName().toLowerCase().contains("clue scroll")) {
                        var1_1.getPacketSender().sendGameMessage("You can only have one scroll at a time.");
                        break;
                    }
                    if (((Boolean)var1_1.getAttributes().get("canPickup")).booleanValue()) {
                        ItemService.getInstance().pickupItem((Player)var1_1, var1_1.getInteractionTargetId(), new Position(var1_1.getInteractionTargetX(), var1_1.getInteractionTargetY(), var1_1.getPosition().getPlane()));
                    }
                }
                return;
            }
            case 253: {
                var1_1.resetInteractionState();
                var3_17 = var2_2;
                var2_2 = var1_1;
                var1_1 = this;
                var2_2.setInteractionTargetX(var3_17.getReader().readSignedShort(ByteOrder.LITTLE));
                var2_2.setInteractionTargetY(var3_17.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE));
                var2_2.setInteractionTargetId(var3_17.getReader().readSignedShort(ByteTransform.ADD));
                var2_2.setInteractionTargetPlane(var2_2.getPosition().getPlane());
                if (var2_2.getPlayerRights() > 1 && ServerSettings.debugModeEnabled) {
                    System.out.println(String.valueOf(var2_2.getInteractionTargetX()) + " " + var2_2.getInteractionTargetY());
                }
                if (var2_2.getInteractionTargetId() == 6888 && var2_2.getTelekineticTheatreController().isInsideTheatre()) {
                    GroundItemManager.getInstance();
                    var4_9 = GroundItemManager.findVisibleItem((Player)var2_2, var2_2.getInteractionTargetId(), new Position(var2_2.getInteractionTargetX(), var2_2.getInteractionTargetY(), var2_2.getInteractionTargetPlane()));
                    if (var4_9 != null) {
                        var2_2.getPacketSender().sendGroundItemRemove(var4_9);
                        var2_2.getTelekineticTheatreController().spawnMazeItem();
                        break;
                    }
                } else {
                    var4_10 = var2_2.nextActionSequence();
                    var2_2.setActiveCycleEvent(new GroundItemFiremakingTask((ItemActionPacketHandler)var1_1, (Player)var2_2, var4_10));
                    CycleEventHandler.getInstance().schedule((Entity)var2_2, var2_2.getActiveCycleEvent(), 1);
                }
                return;
            }
            case 145: {
                var1_1.resetInteractionState();
                var4_11 = var2_2.getReader().readSignedShort(ByteTransform.ADD);
                var1_1.setSelectedItemSlot(var2_2.getReader().readSignedShort(ByteTransform.ADD));
                var3_18 = var2_2.getReader().readSignedShort(ByteTransform.ADD);
                var2_2 = InterfaceDefinition.forId(var4_11);
                if (var1_1.isInterfaceOpen((InterfaceDefinition)var2_2)) {
                    if (var4_11 == 1119 || var4_11 == 1120 || var4_11 == 1121 || var4_11 == 1122 || var4_11 == 1123) {
                        SmithingHandler.startSmithingTask((Player)var1_1, var3_18, 1);
                    }
                    if (var4_11 == 1688) {
                        var1_1.getEquipmentManager().unequipSlot(var1_1.getSelectedItemSlot());
                    } else if (var4_11 == 5064 || var4_11 == 7423) {
                        BankManager.depositInventoryItem((Player)var1_1, var1_1.getSelectedItemSlot(), var3_18, 1);
                    } else if (var4_11 == 2006) {
                        PartyRoomManager.stageInventoryItemForChest((Player)var1_1, var1_1.getSelectedItemSlot(), var3_18, 1);
                    } else if (var4_11 == 2274) {
                        PartyRoomManager.withdrawStagedChestItem((Player)var1_1, var1_1.getSelectedItemSlot(), var3_18, 1);
                    } else if (var4_11 == 5382 || var4_11 == 19532 || var4_11 == 19533 || var4_11 == 19534 || var4_11 == 19535 || var4_11 == 19536 || var4_11 == 19537 || var4_11 == 19538 || var4_11 == 19539 || var4_11 == 19540) {
                        BankManager.withdrawItemFromTab((Player)var1_1, var1_1.getSelectedItemSlot(), var3_18, 1, var4_11);
                    } else if (var4_11 == 19102) {
                        GrandExchangeManager.selectSellOfferItem((Player)var1_1, var1_1.getSelectedItemSlot(), var3_18, 1);
                    } else if (var4_11 == 19006) {
                        GrandExchangeManager.collectOfferItem((Player)var1_1, var1_1.getSelectedItemSlot(), var3_18, 1);
                    } else if (var4_11 == 3900) {
                        ShopManager.sendBuyPrice((Player)var1_1, var3_18);
                    } else if (var4_11 == 15948) {
                        MageTrainingArenaRewardShop.sendRewardCostMessage((Player)var1_1, var1_1.getSelectedItemSlot());
                    } else if (var4_11 == 3823) {
                        ShopManager.sendSellPrice((Player)var1_1, var3_18);
                    } else if (var4_11 == 3322) {
                        if (var1_1.getInterfaceAction() == "duel") {
                            var1_1.getDuelSession().addStakeItem(new ItemStack(var3_18, 1), var1_1.getSelectedItemSlot());
                        } else {
                            GameplayHelper.addTradeOfferItem((Player)var1_1, var1_1.getSelectedItemSlot(), var3_18, 1);
                        }
                    } else if (var4_11 == 3415) {
                        GameplayHelper.removeTradeOfferItem((Player)var1_1, var1_1.getSelectedItemSlot(), var3_18, 1);
                    } else if (var4_11 == 15682 || var4_11 == 15683) {
                        var1_1.getFarmingToolStore().withdrawItem(var3_18, 1);
                    } else if (var4_11 == 15594 || var4_11 == 15595) {
                        var1_1.getFarmingToolStore().depositItem(var3_18, 1);
                    } else if (var4_11 == 6669) {
                        var1_1.getDuelSession().removeStakeItem(new ItemStack(var3_18, 1));
                    }
                    switch (var4_11) {
                        case 4233: {
                            JewelleryCraftingHandler.startJewelleryCraftingTask((Player)var1_1, JewelleryCraftingData.getMaterialItemIds()[var1_1.getSelectedItemSlot()], 1, 0);
                            break block7;
                        }
                        case 4239: {
                            JewelleryCraftingHandler.startJewelleryCraftingTask((Player)var1_1, JewelleryCraftingData.getMaterialItemIds()[var1_1.getSelectedItemSlot()], 1, 1);
                            break block7;
                        }
                        case 4245: {
                            JewelleryCraftingHandler.startJewelleryCraftingTask((Player)var1_1, JewelleryCraftingData.getMaterialItemIds()[var1_1.getSelectedItemSlot()], 1, 2);
                        }
                    }
                }
                return;
            }
            case 117: {
                var1_1.resetInteractionState();
                ItemActionPacketHandler.handleInterfaceItemAmountFive((Player)var1_1, (IncomingPacket)var2_2);
                return;
            }
            case 43: {
                ItemActionPacketHandler.handleInterfaceItemAmountTenOrOperate((Player)var1_1, (IncomingPacket)var2_2);
                return;
            }
            case 129: {
                var1_1.resetInteractionState();
                ItemActionPacketHandler.handleInterfaceItemAmountAll((Player)var1_1, (IncomingPacket)var2_2);
                return;
            }
            case 41: {
                ItemActionPacketHandler.handleEquipItem((Player)var1_1, (IncomingPacket)var2_2);
                return;
            }
            case 122: {
                var1_1.resetInteractionState();
                this.handleInventoryItemFirstOption((Player)var1_1, (IncomingPacket)var2_2);
                return;
            }
            case 16: {
                var1_1.resetInteractionState();
                ItemActionPacketHandler.handleInventoryItemSecondOption((Player)var1_1, (IncomingPacket)var2_2);
                return;
            }
            case 75: {
                var1_1.resetInteractionState();
                ItemActionPacketHandler.handleInventoryItemThirdOption((Player)var1_1, (IncomingPacket)var2_2);
                return;
            }
            case 237: {
                var1_1.resetInteractionState();
                ItemActionPacketHandler.handleMagicOnItem((Player)var1_1, (IncomingPacket)var2_2);
                return;
            }
            case 181: {
                var1_1.resetInteractionState();
                ItemActionPacketHandler.handleMagicOnGroundItem((Player)var1_1, (IncomingPacket)var2_2);
            }
        }
    }

    private static void handleDropItem(Player player, IncomingPacket incomingPacket) {
        int n = incomingPacket.getReader().readSignedShort(ByteTransform.ADD);
        incomingPacket.getReader().readSignedShort();
        int n2 = incomingPacket.getReader().readSignedShort(ByteTransform.ADD);
        if (n2 < 0 || n2 > player.getInventoryManager().getContainer().k() - 1) {
            return;
        }
        player.setSelectedItemSlot(n2);
        ItemStack itemStack = player.getInventoryManager().getContainer().getItemAt(player.getSelectedItemSlot());
        if (PuzzleBoxHandler.movePuzzlePiece(player, n)) {
            return;
        }
        if (itemStack == null || itemStack.getId() != n || !itemStack.isValid()) {
            return;
        }
        if (itemStack.getDefinition().isStackable()) {
            itemStack.setAmount(player.getInventoryManager().getContainer().getItemAmount(itemStack.getId()));
        } else {
            itemStack.setAmount(1);
        }
        if (!player.getInventoryManager().getContainer().containsItem(itemStack.getId())) {
            return;
        }
        if (player.getQuestManager().handleDropItem(itemStack.getId())) {
            return;
        }
        Object object = PetManager.petItemNpcPairs;
        int n3 = 0;
        while (n3 < 6) {
            int[] nArray = object[n3];
            if (itemStack.getDefinition().getId() == nArray[0]) {
                player.getPetManager().summonPetFromItem(nArray[0], nArray[1]);
                return;
            }
            ++n3;
        }
        String[][] stringArrayArray = BarrowsRepairHandler.forItem(itemStack);
        if (itemStack.getDefinition().hasDestroyOption() || stringArrayArray != null && itemStack.getDefinition().isUntradeable()) {
            String[][] stringArrayArray2 = "Dropping this item will make you lose it forever.";
            if (stringArrayArray != null) {
                stringArrayArray2 = "Dropping this item will make it degrade to 0.";
            }
            stringArrayArray = new String[][]{{"Are you sure you want to drop this item?", "14174"}, {"Yes.", "14175"}, {"No.", "14176"}, {"", "14177"}, {stringArrayArray2, "14182"}, {"", "14183"}, {itemStack.getDefinition().getName(), "14184"}};
            Player player2 = player;
            player2.packetSender.sendInterfaceSlotItem(itemStack, 0, 14171, 1);
            stringArrayArray2 = stringArrayArray;
            int n4 = 0;
            while (n4 < 7) {
                object = stringArrayArray2[n4];
                player2 = player;
                player2.packetSender.sendInterfaceText((String)object[0], Integer.parseInt((String)object[1]));
                ++n4;
            }
            player.setPendingDestroyItem(itemStack);
            player2 = player;
            player2.packetSender.showChatboxInterface(14170);
            return;
        }
        if (player.getInventoryManager().getContainer().containsItem(itemStack.getId())) {
            Player player3 = player;
            player3.packetSender.sendSoundEffect(376, 1, 0);
            if (!ServerSettings.adminInteractionsAllowed && player.getPlayerRights() >= 2) {
                player3 = player;
                player3.packetSender.sendGameMessage("Your item disappears because you're an administrator.");
            } else if (itemStack.getId() == 11283) {
                GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(11284, itemStack.getAmount()), player));
            } else {
                GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(itemStack.getId(), itemStack.getAmount(), itemStack.getMetadata()), player));
            }
            if (!player.getInventoryManager().removeItemFromSlot(itemStack, player.getSelectedItemSlot())) {
                player.getInventoryManager().removeItem(itemStack);
            }
        }
        player.getEquipmentManager().refreshCarriedValue();
    }

    private static void handleInterfaceItemAmountFive(Player player, IncomingPacket object) {
        int n = ((IncomingPacket)object).getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE);
        int n2 = ((IncomingPacket)object).getReader().readShort(true, ByteTransform.ADD, ByteOrder.LITTLE);
        player.setSelectedItemSlot(((IncomingPacket)object).getReader().readSignedShort(true, ByteOrder.LITTLE));
        object = InterfaceDefinition.forId(n);
        if (!player.isInterfaceOpen((InterfaceDefinition)object)) {
            return;
        }
        if (n == 5064 || n == 7423) {
            BankManager.depositInventoryItem(player, player.getSelectedItemSlot(), n2, 5);
        } else if (n == 2006) {
            PartyRoomManager.stageInventoryItemForChest(player, player.getSelectedItemSlot(), n2, 5);
        } else if (n == 2274) {
            PartyRoomManager.withdrawStagedChestItem(player, player.getSelectedItemSlot(), n2, 5);
        } else if (n == 5382 || n == 19532 || n == 19533 || n == 19534 || n == 19535 || n == 19536 || n == 19537 || n == 19538 || n == 19539 || n == 19540) {
            BankManager.withdrawItemFromTab(player, player.getSelectedItemSlot(), n2, 5, n);
        } else if (n == 15948) {
            MageTrainingArenaRewardShop.buyReward(player, player.getSelectedItemSlot());
        } else if (n == 3900) {
            ShopManager.buyItem(player, player.getSelectedItemSlot(), n2, 1);
        } else if (n == 3823) {
            ShopManager.sellItem(player, player.getSelectedItemSlot(), n2, 1);
        } else if (n == 3322) {
            object = player;
            if (((Player)object).interfaceAction == "duel") {
                player.getDuelSession().addStakeItem(new ItemStack(n2, 5), player.getSelectedItemSlot());
            } else {
                GameplayHelper.addTradeOfferItem(player, player.getSelectedItemSlot(), n2, 5);
            }
        } else if (n == 3415) {
            GameplayHelper.removeTradeOfferItem(player, player.getSelectedItemSlot(), n2, 5);
        } else if (n == 15682 || n == 15683) {
            player.getFarmingToolStore().withdrawItem(n2, 5);
        } else if (n == 15594 || n == 15595) {
            player.getFarmingToolStore().depositItem(n2, 5);
        } else if (n == 1119 || n == 1120 || n == 1121 || n == 1122 || n == 1123) {
            SmithingHandler.startSmithingTask(player, n2, 5);
        } else if (n == 6669) {
            player.getDuelSession().removeStakeItem(new ItemStack(n2, 5));
        }
        switch (n) {
            case 4233: {
                JewelleryCraftingHandler.startJewelleryCraftingTask(player, JewelleryCraftingData.getMaterialItemIds()[player.getSelectedItemSlot()], 5, 0);
                return;
            }
            case 4239: {
                JewelleryCraftingHandler.startJewelleryCraftingTask(player, JewelleryCraftingData.getMaterialItemIds()[player.getSelectedItemSlot()], 5, 1);
                return;
            }
            case 4245: {
                JewelleryCraftingHandler.startJewelleryCraftingTask(player, JewelleryCraftingData.getMaterialItemIds()[player.getSelectedItemSlot()], 5, 2);
            }
        }
    }

    private static void handleInterfaceItemAmountTenOrOperate(Player player, IncomingPacket incomingPacket) {
        int n = incomingPacket.getReader().readSignedShort(ByteOrder.LITTLE);
        int n2 = incomingPacket.getReader().readSignedShort(ByteTransform.ADD);
        int n3 = incomingPacket.getReader().readSignedShort(ByteTransform.ADD);
        Object object = InterfaceDefinition.forId(n);
        if (!player.isInterfaceOpen((InterfaceDefinition)object)) {
            return;
        }
        if (n != 1688 || n2 != 11283) {
            player.resetInteractionState();
        }
        player.setSelectedItemSlot(n3);
        if (n == 5064 || n == 7423) {
            BankManager.depositInventoryItem(player, player.getSelectedItemSlot(), n2, 10);
        } else if (n == 2006) {
            PartyRoomManager.stageInventoryItemForChest(player, player.getSelectedItemSlot(), n2, 10);
        } else if (n == 2274) {
            PartyRoomManager.withdrawStagedChestItem(player, player.getSelectedItemSlot(), n2, 10);
        } else if (n == 5382 || n == 19532 || n == 19533 || n == 19534 || n == 19535 || n == 19536 || n == 19537 || n == 19538 || n == 19539 || n == 19540) {
            BankManager.withdrawItemFromTab(player, player.getSelectedItemSlot(), n2, 10, n);
        } else if (n == 3900) {
            ShopManager.buyItem(player, player.getSelectedItemSlot(), n2, 5);
        } else if (n == 3823) {
            ShopManager.sellItem(player, player.getSelectedItemSlot(), n2, 5);
        } else if (n == 1688) {
            player.getSelectedItemSlot();
            Player player2 = player;
            Object object2 = player2.getEquipmentManager().getContainer().getItemAt(player2.getSelectedItemSlot());
            if (object2 != null) {
                player2.setSelectedItemId(((ItemStack)object2).getId());
                switch (((ItemStack)object2).getId()) {
                    case 2552: 
                    case 2554: 
                    case 2556: 
                    case 2558: 
                    case 2560: 
                    case 2562: 
                    case 2564: 
                    case 2566: {
                        object2 = "operate";
                        object = player2;
                        player2.interfaceAction = object2;
                        DialogueManager.startDialogue(player2, 10004);
                        break;
                    }
                    case 1706: 
                    case 1708: 
                    case 1710: 
                    case 1712: {
                        object2 = "operate";
                        object = player2;
                        player2.interfaceAction = object2;
                        DialogueManager.startDialogue(player2, 10003);
                        break;
                    }
                    case 3853: 
                    case 3855: 
                    case 3857: 
                    case 3859: 
                    case 3861: 
                    case 3863: 
                    case 3865: 
                    case 3867: {
                        object2 = "operate";
                        object = player2;
                        player2.interfaceAction = object2;
                        DialogueManager.startDialogue(player2, 10002);
                        break;
                    }
                    case 8118: {
                        if (player2.getSpellbook() != Spellbook.NECROMANCY) {
                            player2.E = player2.getSpellbook();
                            object = player2;
                            ((Player)object).packetSender.setSidebarInterface(6, 19104);
                            player2.setSpellbook(Spellbook.NECROMANCY);
                            object = player2;
                            ((Player)object).packetSender.selectMagicSidebarTab(6);
                            break;
                        }
                        player2.setSpellbook(player2.E);
                        if (player2.getSpellbook() == Spellbook.MODERN) {
                            object = player2;
                            ((Player)object).packetSender.setSidebarInterface(6, 1151);
                        }
                        if (player2.getSpellbook() == Spellbook.ANCIENT) {
                            object = player2;
                            ((Player)object).packetSender.setSidebarInterface(6, 12855);
                        }
                        object = player2;
                        ((Player)object).packetSender.selectMagicSidebarTab(6);
                        break;
                    }
                    case 11283: {
                        long l;
                        if (player2.getEquipmentManager().getItemIdAtSlot(5) != 11283) {
                            object = player2;
                            ((Player)object).packetSender.sendGameMessage("You have to wear the shield to operate it.");
                            break;
                        }
                        if (player2.getCombatTarget() == null || player2.getCombatTarget() != null && player2.getCombatTarget().isDead()) {
                            object = player2;
                            ((Player)object).packetSender.sendGameMessage("You need to be in combat to do that!");
                            break;
                        }
                        boolean bl = EntityTargetMovement.canReachTarget(player2, player2.getCombatTarget(), 10);
                        if (!bl) {
                            object = player2;
                            ((Player)object).packetSender.sendGameMessage("You are too far away!");
                            break;
                        }
                        boolean bl2 = true;
                        int n4 = 0;
                        if (player2.dragonfireShieldLastOperateMillis != -1L) {
                            n4 = 1;
                        }
                        if (n4 != 0 && (l = System.currentTimeMillis() - player2.dragonfireShieldLastOperateMillis) / 1000L < 120L) {
                            Player player3 = player2;
                            player3.packetSender.sendGameMessage("You have to wait before using another charge!");
                            bl2 = false;
                        }
                        if (bl && bl2) {
                            player2.nextActionSequence();
                            player2.getAttackDelayTimer().setDelayTicks(player2.getAttackDelayTimer().getDelayTicks() + 2);
                            player2.getUpdateState().setAnimation(6696);
                            player2.getUpdateState().setGraphic(new GraphicEffect(1165, 96));
                            player2.dragonfireShieldLastOperateMillis = System.currentTimeMillis();
                            Object object3 = new GraphicEffect(1167, 96);
                            Object object4 = ProjectileTiming.a;
                            object4 = new ProjectileDefinition(1166, ((ProjectileTiming)object4).copy());
                            n4 = 20 + GameUtil.randomInt(6);
                            object3 = new HitDefinition(ServerSettings.DRAGONFIRE_ATTACK_STYLE, HitType.NORMAL, n4).setAccuracyMultiplier(1.0).setProjectile((ProjectileDefinition)object4).setGraphic((GraphicEffect)object3);
                            new CombatAction(player2, player2.getCombatTarget(), (HitDefinition)object3).queue();
                            int n5 = player2.getEquipmentManager().getContainer().getItemAt(5).getMetadata();
                            if (n5 - 1 <= 0) {
                                player2.getEquipmentManager().getContainer().setItem(5, new ItemStack(11284));
                            } else {
                                player2.getEquipmentManager().getContainer().getItemAt(5).setMetadata(n5 - 1);
                            }
                            player2.getEquipmentManager().refresh();
                        }
                    }
                    default: {
                        break;
                    }
                }
            }
        } else if (n == 3322) {
            object = player;
            if (((Player)object).interfaceAction == "duel") {
                player.getDuelSession().addStakeItem(new ItemStack(n2, 10), player.getSelectedItemSlot());
            } else {
                GameplayHelper.addTradeOfferItem(player, player.getSelectedItemSlot(), n2, 10);
            }
        } else if (n == 3415) {
            GameplayHelper.removeTradeOfferItem(player, player.getSelectedItemSlot(), n2, 10);
        } else if (n == 15682 || n == 15683) {
            player.getFarmingToolStore().withdrawItem(n2, 255);
        } else if (n == 15594 || n == 15595) {
            player.getFarmingToolStore().depositItem(n2, player.getInventoryManager().getItemAmount(n2));
        } else if (n == 1119 || n == 1120 || n == 1121 || n == 1122 || n == 1123) {
            SmithingHandler.startSmithingTask(player, n2, 10);
        } else if (n == 6669) {
            player.getDuelSession().removeStakeItem(new ItemStack(n2, 10));
        }
        switch (n) {
            case 4233: {
                JewelleryCraftingHandler.startJewelleryCraftingTask(player, JewelleryCraftingData.getMaterialItemIds()[player.getSelectedItemSlot()], 10, 0);
                return;
            }
            case 4239: {
                JewelleryCraftingHandler.startJewelleryCraftingTask(player, JewelleryCraftingData.getMaterialItemIds()[player.getSelectedItemSlot()], 10, 1);
                return;
            }
            case 4245: {
                JewelleryCraftingHandler.startJewelleryCraftingTask(player, JewelleryCraftingData.getMaterialItemIds()[player.getSelectedItemSlot()], 10, 2);
            }
        }
    }

    private static void handleInterfaceItemAmountAll(Player player, IncomingPacket incomingPacket) {
        player.setSelectedItemSlot(incomingPacket.getReader().readSignedShort(ByteTransform.ADD));
        int n = incomingPacket.getReader().readSignedShort();
        int n2 = incomingPacket.getReader().readSignedShort(ByteTransform.ADD);
        Object object = InterfaceDefinition.forId(n);
        if (!player.isInterfaceOpen((InterfaceDefinition)object)) {
            return;
        }
        if (n == 5064 || n == 7423) {
            BankManager.depositInventoryItem(player, player.getSelectedItemSlot(), n2, player.getInventoryManager().getContainer().getItemAmount(n2));
            return;
        }
        if (n == 2006) {
            PartyRoomManager.stageInventoryItemForChest(player, player.getSelectedItemSlot(), n2, player.getInventoryManager().getContainer().getItemAmount(n2));
            return;
        }
        if (n == 2274) {
            PartyRoomManager.withdrawStagedChestItem(player, player.getSelectedItemSlot(), n2, player.getPartyRoomContainer().getItemAmount(n2));
            return;
        }
        if (n == 5382 || n == 19532 || n == 19533 || n == 19534 || n == 19535 || n == 19536 || n == 19537 || n == 19538 || n == 19539 || n == 19540) {
            BankManager.withdrawItemFromTab(player, player.getSelectedItemSlot(), n2, player.getBankContainer().getItemAmount(n2), n);
            return;
        }
        if (n == 3900) {
            ShopManager.buyItem(player, player.getSelectedItemSlot(), n2, 10);
            return;
        }
        if (n == 3823) {
            ShopManager.sellItem(player, player.getSelectedItemSlot(), n2, 10);
            return;
        }
        if (n == 3322) {
            object = player;
            if (((Player)object).interfaceAction == "duel") {
                player.getDuelSession().addStakeItem(new ItemStack(n2, player.getInventoryManager().getContainer().getItemAmount(n2)), player.getSelectedItemSlot());
                return;
            }
            GameplayHelper.addTradeOfferItem(player, player.getSelectedItemSlot(), n2, player.getInventoryManager().getContainer().getItemAmount(n2));
            return;
        }
        if (n == 15594 || n == 15595) {
            object = player;
            ((Player)object).packetSender.sendEnterInputPrompt(n);
            player.setSelectedItemId(n2);
            return;
        }
        if (n == 15682 || n == 15683) {
            object = player;
            ((Player)object).packetSender.sendEnterInputPrompt(n);
            player.setSelectedItemId(n2);
            return;
        }
        if (n == 3415) {
            GameplayHelper.removeTradeOfferItem(player, player.getSelectedItemSlot(), n2, Integer.MAX_VALUE);
            return;
        }
        if (n == 6669) {
            player.getDuelSession().removeStakeItem(new ItemStack(n2, Integer.MAX_VALUE));
        }
    }

    /*
     * Unable to fully structure code
     */
    private void handleInventoryItemFirstOption(Player var1_1, IncomingPacket var2_2) {
        var3_4 = var2_2.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE);
        var1_1.setSelectedItemSlot(var2_2.getReader().readSignedShort(ByteTransform.ADD));
        var2_3 = var2_2.getReader().readSignedShort(ByteOrder.LITTLE);
        var4_8 = InterfaceDefinition.forId(var3_4);
        if (!var1_1.isInterfaceOpen((InterfaceDefinition)var4_8)) {
            return;
        }
        var4_8 = var1_1.getInventoryManager().getContainer().getItemAt(var1_1.getSelectedItemSlot());
        if (var4_8 == null || var4_8.getId() != var2_3) {
            return;
        }
        if (new ItemStack(var2_3).getDefinition().isMembersOnly() && !var1_1.isMember() && var2_3 != 7999) {
            var1_1.packetSender.sendGameMessage("You need a members account to access members content.");
            return;
        }
        if (new ItemStack(var2_3).getDefinition().isMembersOnly() && ServerSettings.freeToPlayWorld && var2_3 != 7999) {
            var1_1.packetSender.sendGameMessage("You need to be in members world to access members content.");
            return;
        }
        if (var1_1.getQuestManager().handleInventoryItemFirstOption(var3_4, var2_3)) {
            return;
        }
        if (BirdNestSearchHandler.searchNest(var1_1, var2_3)) {
            return;
        }
        if (ToyHorseyHandler.play(var1_1, var2_3)) {
            return;
        }
        if (SpinningPlateHandler.spinPlate(var1_1, var2_3)) {
            return;
        }
        if (var2_3 == 4155) {
            DialogueManager.startDialogue(var1_1, 10012);
            return;
        }
        var6_9 = var1_1.getSelectedItemSlot();
        var5_11 = var2_3;
        var3_5 = var1_1;
        var7_14 = CleanHerbDefinition.forGrimyItemId(var5_11);
        if (var7_14 != null) {
            if (!ServerSettings.herbloreEnabled) {
                var9_15 = var3_5;
                var9_15.packetSender.sendGameMessage("This skill is currently disabled.");
                v0 = true;
            } else if (!var3_5.isMember()) {
                var3_5.packetSender.sendGameMessage("You need a members account to access members content.");
                v0 = true;
            } else if (ServerSettings.freeToPlayWorld) {
                var3_5.packetSender.sendGameMessage("You need to be in members world to access members content.");
                v0 = true;
            } else if (var3_5.getQuestState(29) != 1) {
                var5_12 = QuestDefinition.forId(29);
                var5_12 = var5_12.getName();
                var9_15 = var3_5;
                var9_15.packetSender.sendGameMessage("You need to complete " + (String)var5_12 + " to do this.");
                v0 = true;
            } else if (var3_5.getSkillManager().getCurrentLevels()[15] < var7_14.getRequiredLevel()) {
                var9_15 = var3_5;
                var9_15.packetSender.sendGameMessage("You cannot clean this herb.");
                var9_15 = var3_5;
                var9_15.packetSender.sendGameMessage("You need a higher Herblore level.");
                v0 = true;
            } else {
                var3_5.getSkillManager().addExperience(15, var7_14.getExperience());
                if (var3_5.getInventoryManager().removeItemFromSlot(new ItemStack(var5_11), var6_9)) {
                    var3_5.getInventoryManager().setItemInSlot(new ItemStack(var7_14.getCleanItemId()), var6_9);
                } else if (var3_5.getInventoryManager().removeItem(new ItemStack(var5_11))) {
                    var3_5.getInventoryManager().addItem(new ItemStack(var7_14.getCleanItemId()));
                }
                var9_15 = var3_5;
                var9_15.packetSender.sendGameMessage("You identify the herb, it's a " + new ItemStack(var7_14.getCleanItemId()).getDefinition().getName().toLowerCase().replace("clean", "") + ".");
                v0 = true;
            }
        } else {
            v0 = false;
        }
        if (v0) {
            return;
        }
        if (var1_1.getBoneBuryingHandler().handleBuryBone(var2_3, var1_1.getSelectedItemSlot())) {
            return;
        }
        if (var2_3 >= 5509 && var2_3 <= 5514) {
            GameplayHelper.fillEssencePouch(var1_1, var2_3);
            return;
        }
        if (var1_1.getPotionHandler().selectPotionForItemId(var2_3)) {
            var1_1.getPotionHandler().drinkPotion(var2_3, var1_1.getSelectedItemSlot());
            return;
        }
        if (var1_1.getFoodHandler().eatFood(var2_3, var1_1.getSelectedItemSlot())) {
            return;
        }
        if (TreasureTrailManager.handleRewardContainerItem(var1_1, var2_3)) {
            return;
        }
        if (new ItemStack(var2_3).getDefinition().getName().toLowerCase().contains("clue scroll") || new ItemStack(var2_3).getDefinition().getName().toLowerCase().contains("challenge scroll")) {
            TreasureTrailManager.clearClueInterfaceText(var1_1);
        }
        if (PuzzleBoxHandler.openCluePuzzleBox(var1_1, var2_3)) {
            return;
        }
        if (CoordinateClueHandler.showCoordinateClue(var1_1, var2_3)) {
            return;
        }
        if (BotWorldRouteChoice.showCrypticDigClue(var1_1, var2_3)) {
            return;
        }
        if (CacheArchive.showChallengeQuestionForAnswerItem(var1_1, var2_3)) {
            return;
        }
        if (CacheDefinitionIndex.showNpcClue(var1_1, var2_3)) {
            return;
        }
        var5_11 = var2_3;
        var3_5 = var1_1;
        var6_10 = AnagramClue.forClueItemId(var5_11);
        if (var6_10 == null) {
            v1 = false;
        } else {
            var9_15 = var3_5;
            var9_15.packetSender.showInterface(6965);
            var9_15 = var3_5;
            var9_15.packetSender.sendInterfaceText("This anagram reveals", 6970);
            var9_15 = var3_5;
            var9_15.packetSender.sendInterfaceText("who to speak to next:", 6971);
            var9_15 = var3_5;
            var9_15.packetSender.sendInterfaceText(var6_10.getAnagramText().toUpperCase(), 6973);
            v1 = true;
        }
        if (v1) {
            return;
        }
        var5_11 = var2_3;
        var3_5 = var1_1;
        var6_10 = MapClue.forClueItemId(var5_11);
        if (var6_10 == null) {
            v2 = false;
        } else if (var6_10.getInterfaceId() < 0) {
            v2 = false;
        } else {
            var9_15 = var3_5;
            var9_15.packetSender.showInterface(var6_10.getInterfaceId());
            v2 = true;
        }
        if (v2) {
            return;
        }
        if (CacheFile.showSearchClue(var1_1, var2_3)) {
            return;
        }
        var9_15 = var1_1;
        var3_6 = var2_3;
        if (GameplayHelper.extinguishCaveLightSource(var9_15, var3_6, true)) {
            return;
        }
        if (new ItemStack(var2_3).getDefinition().getName().toLowerCase().contains("progress hat")) {
            var9_15 = var1_1;
            var9_15.packetSender.sendGameMessage("You have " + var1_1.getTelekineticTheatreController().pizazzPoints + "/4000" + " Telekinetic, " + var1_1.getAlchemistPlaygroundController().pizazzPoints + "/8000" + " Alchemist,");
            var9_15 = var1_1;
            var9_15.packetSender.sendGameMessage(String.valueOf(var1_1.getEnchantmentChamberController().pizazzPoints) + "/16000" + " Enchantment and " + var1_1.getCreatureGraveyardController().pizazzPoints + "/4000" + " Graveyard Pizazz Points.");
            return;
        }
        switch (var2_3) {
            case 2329: {
                if (var1_1.getInventoryManager().removeItemFromSlot((ItemStack)var4_8, var1_1.getSelectedItemSlot())) {
                    var9_15 = var1_1;
                    var9_15.packetSender.sendGameMessage("You empty the pie dish.");
                    var1_1.getInventoryManager().setItemInSlot(new ItemStack(2313), var1_1.getSelectedItemSlot());
                }
                return;
            }
            case 2528: {
                var1_1.setSelectedLampSkill(-1);
                var9_15 = var1_1;
                var9_15.packetSender.sendConfig(261, 0);
                var9_15 = var1_1;
                var9_15.packetSender.showInterface(2808);
                return;
            }
            case 550: {
                var2_3 = var1_1.getPosition().getX() / 64 - 46 + (var1_1.getPosition().getY() / 64 - 49) * 6;
                var9_15 = var1_1;
                var9_15.packetSender.sendConfig(106, var2_3);
                var9_15 = var1_1;
                var9_15.packetSender.showInterface(5392);
                return;
            }
            case 405: {
                if (var1_1.getInventoryManager().removeItemFromSlot((ItemStack)var4_8, var1_1.getSelectedItemSlot())) {
                    CasketRewardHandler.openCasket(var1_1);
                }
                return;
            }
            case 8000: {
                return;
            }
            case 7999: {
                if (var1_1.isInWilderness()) {
                    var9_15 = var1_1;
                    var9_15.packetSender.sendGameMessage("This item can't be used in wilderness!");
                    return;
                }
                if (ServerSettings.membershipDaysPerPurchase <= 0) {
                    if (var1_1.hasMemberFlag()) {
                        var9_15 = var1_1;
                        var9_15.packetSender.sendGameMessage("You are already member!");
                        return;
                    }
                    if (var1_1.getInventoryManager().removeItemFromSlot((ItemStack)var4_8, var1_1.getSelectedItemSlot())) {
                        var9_15 = var1_1;
                        var9_15.packetSender.sendGameMessage("You are now a member!");
                        var1_1.setMemberFlag(true);
                        return;
                    }
                } else if (var1_1.getInventoryManager().removeItemFromSlot((ItemStack)var4_8, var1_1.getSelectedItemSlot())) {
                    var8_17 = System.currentTimeMillis();
                    if (var1_1.isMember()) {
                        var8_17 = var1_1.membershipExpiresMillis;
                    }
                    var1_1.membershipExpiresMillis = GameplayHelper.addDaysToTimestamp(var8_17, ServerSettings.membershipDaysPerPurchase);
                    var9_15 = var1_1;
                    var9_15.packetSender.sendGameMessage("You claimed " + ServerSettings.membershipDaysPerPurchase + " days of membership.");
                }
                return;
            }
            case 2150: {
                if (var1_1.getInventoryManager().removeItemFromSlot((ItemStack)var4_8, var1_1.getSelectedItemSlot())) {
                    var9_15 = var1_1;
                    var9_15.packetSender.sendGameMessage("You pull the legs off the toad. Poor toad. At least they'll grow back.");
                    var1_1.getInventoryManager().setItemInSlot(new ItemStack(2152), var1_1.getSelectedItemSlot());
                }
                return;
            }
            case 407: {
                if (var1_1.getInventoryManager().removeItemFromSlot((ItemStack)var4_8, var1_1.getSelectedItemSlot())) {
                    var9_15 = var1_1;
                    var9_15.packetSender.sendGameMessage("You open the oyster.");
                    var1_1.getInventoryManager().setItemInSlot(new ItemStack(411), var1_1.getSelectedItemSlot());
                }
                return;
            }
            case 952: {
                var1_1.getUpdateState().setAnimation(830);
                var9_15 = var1_1;
                var9_15.packetSender.sendSoundEffect(232, 1, 0);
                var3_7 = var1_1;
                var5_13 = MapClue.forPosition(new Position(var3_7.getPosition().getX(), var3_7.getPosition().getY(), var3_7.getPosition().getPlane()));
                if (var5_13 == null) {
                    v3 = false;
                } else if (!var3_7.getInventoryManager().containsItem(var5_13.getClueItemId())) {
                    v3 = false;
                } else {
                    var3_7.getInventoryManager().removeItem(new ItemStack(var5_13.getClueItemId(), 1));
                    switch (var5_13.getLevel()) {
                        case 1: {
                            var3_7.getInventoryManager().addOrDropItem(new ItemStack(2724, 1));
                            break;
                        }
                        case 2: {
                            var3_7.getInventoryManager().addOrDropItem(new ItemStack(2726, 1));
                            break;
                        }
                        case 3: {
                            var3_7.getInventoryManager().addOrDropItem(new ItemStack(2728, 1));
                        }
                    }
                    var3_7.getDialogueManager().a("You've found a casket!", 2724);
                    v3 = true;
                }
                if (v3) ** GOTO lbl-1000
                var3_7 = var1_1;
                var5_13 = CrypticDigClue.forPosition(new Position(var3_7.getPosition().getX(), var3_7.getPosition().getY(), var3_7.getPosition().getPlane()));
                if (var5_13 == null) {
                    v4 = false;
                } else if (!var3_7.getInventoryManager().containsItem(var5_13.getClueItemId())) {
                    v4 = false;
                } else {
                    var3_7.getInventoryManager().removeItem(new ItemStack(var5_13.getClueItemId(), 1));
                    switch (var5_13.getLevel()) {
                        case 1: {
                            var3_7.getInventoryManager().addOrDropItem(new ItemStack(2724, 1));
                            break;
                        }
                        case 2: {
                            var3_7.getInventoryManager().addOrDropItem(new ItemStack(2726, 1));
                            break;
                        }
                        case 3: {
                            var3_7.getInventoryManager().addOrDropItem(new ItemStack(2728, 1));
                        }
                    }
                    var3_7.getDialogueManager().a("You've found a casket!", 2724);
                    v4 = true;
                }
                if (!(v4 || CoordinateClueHandler.digAtCoordinateClue(var1_1) || BarrowsManager.digIntoCrypt(var1_1))) {
                    v5 = true;
                } else lbl-1000:
                // 2 sources

                {
                    v5 = var8_18 = false;
                }
                if (v5) {
                    var9_15 = var1_1;
                    var9_15.packetSender.sendGameMessage("You dig into the ground...");
                }
                var9_16 = var1_1.nextActionSequence();
                var1_1.setActiveCycleEvent(new DigSearchTask(this, var1_1, var9_16, var8_18));
                CycleEventHandler.getInstance().schedule(var1_1, var1_1.getActiveCycleEvent(), 2);
                return;
            }
            case 2574: {
                GameplayHelper.openSextantInterface(var1_1);
                return;
            }
            case 299: {
                MithrilSeedFlowerHandler.plantMithrilSeedFlower(var1_1);
                return;
            }
            case 4079: {
                var1_1.getUpdateState().setAnimation(1457, 0);
                return;
            }
        }
        var9_15 = var1_1;
        var9_15.packetSender.sendGameMessage("Nothing interesting happens.");
    }

    private static void handleInventoryItemSecondOption(Player player, IncomingPacket incomingPacket) {
        int n = incomingPacket.getReader().readSignedShort(ByteTransform.ADD);
        player.setSelectedItemSlot(incomingPacket.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE));
        int n2 = incomingPacket.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE);
        Object object = InterfaceDefinition.forId(n2);
        if (!player.isInterfaceOpen((InterfaceDefinition)object)) {
            return;
        }
        object = player.getInventoryManager().getContainer().getItemAt(player.getSelectedItemSlot());
        if (object == null || ((ItemStack)object).getId() != n) {
            return;
        }
        if (new ItemStack(n).getDefinition().isMembersOnly() && !player.isMember()) {
            player.packetSender.sendGameMessage("You need a members account to access members content.");
            return;
        }
        if (new ItemStack(n).getDefinition().isMembersOnly() && ServerSettings.freeToPlayWorld) {
            player.packetSender.sendGameMessage("You need to be in members world to access members content.");
            return;
        }
        if (ServerSettings.content2007Enabled && GodWarsDungeonManager.dismantleGodsword(player, n)) {
            return;
        }
        if (GodBookHandler.showMissingPages(player, n)) {
            return;
        }
        if (GodBookHandler.openRecitationDialogue(player, n)) {
            return;
        }
        Player player2 = player;
        int n3 = n;
        if (GameplayHelper.extinguishCaveLightSource(player2, n3, true)) {
            return;
        }
        int n4 = n;
        Player player3 = player;
        EssencePouchDefinition essencePouchDefinition = EssencePouchDefinition.forItemOrIndex(n4);
        if (essencePouchDefinition != null && (n4 == essencePouchDefinition.getItemId() || n4 == essencePouchDefinition.getDegradedItemId())) {
            if (!ServerSettings.runecraftingEnabled) {
                player2 = player3;
                player2.packetSender.sendGameMessage("This skill is currently disabled.");
            } else if (player3.getQuestState(14) != 1) {
                Object object2 = QuestDefinition.forId(14);
                object2 = ((QuestDefinition)object2).getName();
                player2 = player3;
                player2.packetSender.sendGameMessage("You need to complete " + (String)object2 + " to do this.");
            } else if (player3.getEssencePouchAmount(essencePouchDefinition.getPouchIndex()) > 0) {
                player2 = player3;
                PacketSender packetSender = player2.packetSender;
                StringBuilder stringBuilder = new StringBuilder("Your ");
                ItemService.getInstance();
                packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(n4)).append(" contains ").append(player3.getEssencePouchAmount(essencePouchDefinition.getPouchIndex())).append(" Pure essence.").toString());
            } else {
                player2 = player3;
                PacketSender packetSender = player2.packetSender;
                StringBuilder stringBuilder = new StringBuilder("Your ");
                ItemService.getInstance();
                packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(n4)).append(" is empty.").toString());
            }
        }
        switch (n) {
            case 11283: 
            case 11284: {
                int n5 = ((ItemStack)object).getMetadata();
                if (n5 < 0) {
                    n5 = 0;
                }
                player.packetSender.sendGameMessage("There is " + n5 + " charges left on the shield.");
                return;
            }
            case 8118: {
                if (player.getSpellbook() != Spellbook.NECROMANCY) {
                    player.E = player.getSpellbook();
                    player2 = player;
                    player2.packetSender.setSidebarInterface(6, 19104);
                    player.setSpellbook(Spellbook.NECROMANCY);
                    player2 = player;
                    player2.packetSender.selectMagicSidebarTab(6);
                    return;
                }
                player.setSpellbook(player.E);
                if (player.getSpellbook() == Spellbook.MODERN) {
                    player2 = player;
                    player2.packetSender.setSidebarInterface(6, 1151);
                }
                if (player.getSpellbook() == Spellbook.ANCIENT) {
                    player2 = player;
                    player2.packetSender.setSidebarInterface(6, 12855);
                }
                player2 = player;
                player2.packetSender.selectMagicSidebarTab(6);
                return;
            }
            case 4566: {
                player.getUpdateState().setAnimation(1835);
                return;
            }
            case 4079: {
                player.getUpdateState().setAnimation(1459, 0);
                return;
            }
        }
    }

    private static void handleInventoryItemThirdOption(Player player, IncomingPacket object) {
        int n = ((IncomingPacket)object).getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE);
        player.setSelectedItemSlot(((IncomingPacket)object).getReader().readSignedShort(ByteOrder.LITTLE));
        player.setSelectedItemId(((IncomingPacket)object).getReader().readSignedShort(true, ByteTransform.ADD));
        object = InterfaceDefinition.forId(n);
        if (!player.isInterfaceOpen((InterfaceDefinition)object)) {
            return;
        }
        object = player.getInventoryManager().getContainer().getItemAt(player.getSelectedItemSlot());
        if (object == null || ((ItemStack)object).getId() != player.getSelectedItemId()) {
            return;
        }
        if (((ItemStack)object).getDefinition().isMembersOnly() && !player.isMember()) {
            player.packetSender.sendGameMessage("You need a members account to access members content.");
            return;
        }
        if (((ItemStack)object).getDefinition().isMembersOnly() && ServerSettings.freeToPlayWorld) {
            player.packetSender.sendGameMessage("You need to be in members world to access members content.");
            return;
        }
        if (HerbloreHandler.emptyContainer(player, new ItemStack(player.getSelectedItemId()), player.getSelectedItemSlot())) {
            return;
        }
        if (RunecraftingHandler.locateTalismanDirection(player, player.getSelectedItemId())) {
            return;
        }
        switch (((ItemStack)object).getId()) {
            case 11283: {
                if (player.getInventoryManager().removeItemFromSlot((ItemStack)object, player.getSelectedItemSlot())) {
                    player.getInventoryManager().setItemInSlot(new ItemStack(11284), player.getSelectedItemSlot());
                }
                player.getUpdateState().setAnimation(6700);
                player.getUpdateState().setGraphic(1168, 10);
                player.packetSender.sendGameMessage("You release the charges.");
                return;
            }
            case 4079: {
                player.getUpdateState().setAnimation(1460, 0);
                return;
            }
            case 2552: 
            case 2554: 
            case 2556: 
            case 2558: 
            case 2560: 
            case 2562: 
            case 2564: 
            case 2566: {
                String string = "rub";
                object = player;
                player.interfaceAction = string;
                DialogueManager.startDialogue(player, 10004);
                return;
            }
            case 1706: 
            case 1708: 
            case 1710: 
            case 1712: {
                String string = "rub";
                object = player;
                player.interfaceAction = string;
                DialogueManager.startDialogue(player, 10003);
                return;
            }
            case 3853: 
            case 3855: 
            case 3857: 
            case 3859: 
            case 3861: 
            case 3863: 
            case 3865: 
            case 3867: {
                String string = "rub";
                object = player;
                player.interfaceAction = string;
                DialogueManager.startDialogue(player, 10002);
            }
        }
    }

    private static void handleEquipItem(Player player, IncomingPacket object) {
        boolean bl;
        int n = ((IncomingPacket)object).getReader().readSignedShort();
        player.setSelectedItemSlot(((IncomingPacket)object).getReader().readSignedShort(ByteTransform.ADD));
        player.setSelectedItemInterfaceId(((IncomingPacket)object).getReader().readSignedShort(ByteTransform.ADD));
        object = InterfaceDefinition.forId(player.getSelectedItemInterfaceId());
        if (!player.isInterfaceOpen((InterfaceDefinition)object)) {
            return;
        }
        object = player.getInventoryManager().getContainer().getItemAt(player.getSelectedItemSlot());
        if (object == null || ((ItemStack)object).getId() != n || !((ItemStack)object).isValid()) {
            return;
        }
        int n2 = n;
        Player player2 = player;
        EssencePouchDefinition essencePouchDefinition = EssencePouchDefinition.forItemOrIndex(n2);
        if (essencePouchDefinition == null) {
            bl = false;
        } else if (n2 != essencePouchDefinition.getItemId() && n2 != essencePouchDefinition.getDegradedItemId()) {
            bl = false;
        } else {
            if (!ServerSettings.runecraftingEnabled) {
                player2.packetSender.sendGameMessage("This skill is currently disabled.");
            } else if (player2.getQuestState(14) != 1) {
                Object object2 = QuestDefinition.forId(14);
                object2 = ((QuestDefinition)object2).getName();
                player2.packetSender.sendGameMessage("You need to complete " + (String)object2 + " to do this.");
            } else if (player2.getEssencePouchAmount(essencePouchDefinition.getPouchIndex()) > 0) {
                if (player2.getInventoryManager().getContainer().getFreeSlots() >= player2.getEssencePouchAmount(essencePouchDefinition.getPouchIndex())) {
                    player2.getInventoryManager().addItem(new ItemStack(7936, player2.getEssencePouchAmount(essencePouchDefinition.getPouchIndex())));
                    player2.setEssencePouchAmount(essencePouchDefinition.getPouchIndex(), 0);
                } else {
                    player2.packetSender.sendGameMessage("Not enough space in your inventory.");
                }
            } else {
                PacketSender packetSender = player2.packetSender;
                StringBuilder stringBuilder = new StringBuilder("Your ");
                ItemService.getInstance();
                packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(n2)).append(" is empty.").toString());
            }
            bl = true;
        }
        if (bl) {
            return;
        }
        if (AllotmentPatchManager.a(player, n)) {
            return;
        }
        if (((ItemStack)object).getDefinition().getEquipmentSlot() == -1 && GameplayHelper.extinguishCaveLightSource(player2 = player, n2 = n, true)) {
            return;
        }
        switch (((ItemStack)object).getId()) {
            case 4079: {
                player.getUpdateState().setAnimation(1458, 0);
                return;
            }
            case 4035: {
                if (player.getQuestState(62) != 20) {
                    player2 = player;
                    player2.packetSender.sendGameMessage("You have already defeated the demon.");
                    return;
                }
                if (player.isInWilderness() || player.isInTenthSquadSigilInstance()) {
                    player2 = player;
                    player2.packetSender.sendGameMessage("You can't use this item here.");
                    return;
                }
                DialogueManager.startDialogue(player, 9998);
                return;
            }
        }
        if (new ItemStack(n).getDefinition().getEquipmentSlot() == -1) {
            return;
        }
        if (player.getDuelSession().getOpponent() != null && !player.isInDuelArena()) {
            player.getDuelController().resetDuel(true);
            return;
        }
        player.getEquipmentManager().equipFromInventorySlot(player.getSelectedItemSlot());
    }

    private static void handleMagicOnItem(Player player, IncomingPacket object) {
        object = ((IncomingPacket)object).getReader();
        player.setSelectedItemSlot(((PacketReader)object).readSignedShort());
        int n = ((PacketReader)object).readSignedShort(ByteTransform.ADD);
        player.setSelectedItemInterfaceId(((PacketReader)object).readSignedShort());
        int n2 = ((PacketReader)object).readSignedShort(ByteTransform.ADD);
        SpellDefinition spellDefinition = (SpellDefinition)((Object)player.getSpellbook().getSpellByButtonId().get(n2));
        ItemStack itemStack = player.getInventoryManager().getContainer().getItemAt(player.getSelectedItemSlot());
        player.temporaryActionValue = n;
        if (itemStack == null || itemStack.getId() != n || !itemStack.isValid()) {
            return;
        }
        if (spellDefinition != null) {
            MagicSpellAction.castItemSpell(player, spellDefinition, n, player.getSelectedItemSlot());
            return;
        }
        if (player.getPlayerRights() > 1 && ServerSettings.debugModeEnabled) {
            System.out.println("Slot: " + player.getSelectedItemSlot() + " Item id: " + n + " Interface ID: " + player.getSelectedItemInterfaceId() + " magic id: " + n2);
        }
    }

    private static void handleMagicOnGroundItem(Player player, IncomingPacket object) {
        object = ((IncomingPacket)object).getReader();
        int n = ((PacketReader)object).readSignedShort(ByteOrder.LITTLE);
        int n2 = ((PacketReader)object).readSignedShort();
        int n3 = ((PacketReader)object).readSignedShort(ByteOrder.LITTLE);
        int n4 = ((PacketReader)object).readSignedShort(ByteTransform.ADD);
        SpellDefinition spellDefinition = (SpellDefinition)((Object)player.getSpellbook().getSpellByButtonId().get(n4));
        if (player.getQuestManager().handleGroundItemInteraction(n2)) {
            return;
        }
        if (spellDefinition != null) {
            MagicSpellAction.scheduleTelekineticGrab(player, spellDefinition, n2, new Position(n3, n, player.getPosition().getPlane()));
            return;
        }
        if (player.getPlayerRights() > 1 && ServerSettings.debugModeEnabled) {
            System.out.println("Magic ID: " + n4 + " Item ID: " + n2 + " X: " + n3 + " Y: " + n);
        }
    }
}

