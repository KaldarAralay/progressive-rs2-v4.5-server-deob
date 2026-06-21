/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot.combat;

import com.rs2.ServerSettings;
import com.rs2.bot.ClanWarsBotManager;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.bot.combat.BotCombatLoadoutTables;
import com.rs2.bot.combat.BotGroupCombatTickTask;
import com.rs2.bot.combat.BotPvpCombatHandler;
import com.rs2.bot.combat.BotPvpTargetSearchTickTask;
import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.GameplayHelper;
import com.rs2.model.World;
import com.rs2.model.combat.special.SpecialAttackDefinition;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.item.consumable.FoodDefinition;
import com.rs2.model.item.consumable.PotionHandler;
import com.rs2.model.path.PathReachability;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.skill.magic.Spellbook;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class BotCombatLoadoutManager {
    public static PathReachability a = new PathReachability();

    public static void initializeCombatLoadoutTypes() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(7);
        arrayList.add(4);
        arrayList.add(6);
        arrayList.add(5);
        if (ItemDefinition.isDefined(6528)) {
            arrayList.add(8);
        }
        if (ItemDefinition.isDefined(6111)) {
            arrayList.add(9);
        }
        if (ItemDefinition.isDefined(4411)) {
            arrayList.add(10);
        }
        int[] nArray = new int[arrayList.size()];
        int n = 0;
        while (n < arrayList.size()) {
            nArray[n] = (Integer)arrayList.get(n);
            ++n;
        }
        BotCombatLoadoutTables.enabledCombatLoadoutTypes = nArray;
    }

    public static void startCombatLoadoutBot(Player player) {
        Player player2;
        Player player3 = player;
        boolean bl = false;
        player3.botCombatStyle = BotCombatLoadoutTables.enabledCombatLoadoutTypes[GameUtil.randomInt(BotCombatLoadoutTables.enabledCombatLoadoutTypes.length)];
        if (player3.botCombatStyle == 8 || player3.botCombatStyle == 9 || player3.botCombatStyle == 10 || player3.botCombatStyle == 11) {
            bl = true;
        }
        while (BotCombatHelper.isFreeToPlayWorld() && bl) {
            player3.botCombatStyle = BotCombatLoadoutTables.enabledCombatLoadoutTypes[GameUtil.randomInt(BotCombatLoadoutTables.enabledCombatLoadoutTypes.length)];
            bl = player3.botCombatStyle == 8 || player3.botCombatStyle == 9 || player3.botCombatStyle == 10 || player3.botCombatStyle == 11;
        }
        GameplayHelper.resetBotTaskState(player3);
        if (ServerSettings.wildyBotsUseNewGeneration) {
            player2 = player3;
            GameplayHelper.resetBotSkillsToBase(player2);
            int n = ServerSettings.wildyBotsBaseCombatLevel - ServerSettings.wildyBotsCombatLevelSpread;
            if ((n += GameUtil.randomInt((ServerSettings.wildyBotsCombatLevelSpread << 1) + 1)) < 3) {
                n = 3;
            }
            if (n > SkillManager.maxCombatLevel) {
                n = SkillManager.maxCombatLevel;
            }
            while (player2.getCombatLevel() < n) {
                int[] nArray = new int[6];
                nArray[1] = 2;
                nArray[2] = 1;
                nArray[3] = 4;
                nArray[4] = 6;
                nArray[5] = 5;
                int[] nArray2 = nArray;
                int n2 = nArray[GameUtil.randomInt(6)];
                player2.getSkillManager();
                int n3 = SkillManager.getLevelForExperience(player2.getSkillManager().getExperience()[n2]);
                BotCombatHelper.setBotSkillLevel(player2, n2, n3 + 1);
                player2.getSkillManager().getExperience()[3] = BotCombatHelper.calculateBotHitpointsExperience(player2);
                int[] nArray3 = player2.getSkillManager().getCurrentLevels();
                player2.getSkillManager();
                nArray3[3] = SkillManager.getLevelForExperience(player2.getSkillManager().getExperience()[3]);
            }
            player2.getSkillManager().refreshAllSkills();
            BotCombatLoadoutManager.selectCombatStyleFromStats(player2, true);
            player2 = player3;
            player2.getInventoryManager().getContainer().clear();
            player2.getEquipmentManager().getContainer().clear();
            if (player2.botCombatStyle == 0) {
                player3 = player2;
                BotCombatLoadoutManager.prepareMeleeLoadout(player3, false);
                BotCombatLoadoutManager.equipGlovesAndBoots(player2);
            } else if (player2.botCombatStyle == 2) {
                BotCombatLoadoutManager.prepareMagicLoadout(player2);
            } else if (player2.botCombatStyle == 1) {
                BotCombatLoadoutManager.prepareRangedLoadout(player2);
                int n4 = player2.getSkillManager().getCurrentLevels()[4] >= 40 ? 1731 : (n = GameUtil.randomInt(3) == 0 ? 1478 : 1729);
                if (!BotCombatHelper.isFreeToPlayWorld() && player2.getCombatLevel() >= 60 && GameUtil.randomInt(2) == 0) {
                    n = 1712;
                }
                player2.getEquipmentManager().getContainer().setItem(2, new ItemStack(n));
            }
            BotCombatLoadoutManager.equipRandomCape(player2);
            BotCombatLoadoutManager.addCombatSupplies(player2);
            player2.getInventoryManager().refresh();
            player2.getEquipmentManager().refresh();
        } else {
            player2 = player3;
            int n = player2.botCombatStyle;
            GameplayHelper.resetBotSkillsToBase(player2);
            switch (n) {
                case 4: {
                    BotCombatHelper.setBotSkillLevel(player2, 0, 40);
                    BotCombatHelper.setBotSkillLevel(player2, 2, 20 + GameUtil.randomInt(79) + 1);
                    if (GameUtil.randomInt(3) == 0) {
                        BotCombatHelper.setBotSkillLevel(player2, 1, 10);
                    }
                    if (GameUtil.randomInt(4) == 0) break;
                    BotCombatHelper.setBotSkillLevel(player2, 5, GameUtil.randomInt(2) == 0 ? 25 : 31);
                    break;
                }
                case 9: {
                    BotCombatHelper.setBotSkillLevel(player2, 0, 60);
                    BotCombatHelper.setBotSkillLevel(player2, 2, 60 + GameUtil.randomInt(20));
                    if (GameUtil.randomInt(4) != 0) {
                        BotCombatHelper.setBotSkillLevel(player2, 5, GameUtil.randomInt(2) == 0 ? 25 : 25 + GameUtil.randomInt(30));
                    }
                    BotCombatHelper.setBotSkillLevel(player2, 6, GameUtil.randomInt(2) == 0 ? 1 : 25);
                    break;
                }
                case 10: {
                    BotCombatHelper.setBotSkillLevel(player2, 0, 70);
                    BotCombatHelper.setBotSkillLevel(player2, 2, 70 + GameUtil.randomInt(20));
                    BotCombatHelper.setBotSkillLevel(player2, 5, 52);
                    BotCombatHelper.setBotSkillLevel(player2, 6, GameUtil.randomInt(2) == 0 ? 1 : 25);
                    break;
                }
                case 6: {
                    BotCombatHelper.setBotSkillLevel(player2, 6, 1 + (BotCombatHelper.isFreeToPlayWorld() ? GameUtil.randomInt(59) : GameUtil.randomInt(99)));
                    break;
                }
                case 5: {
                    BotCombatHelper.setBotSkillLevel(player2, 4, 1 + GameUtil.randomInt(99));
                    break;
                }
                case 8: {
                    BotCombatHelper.setBotSkillLevel(player2, 0, GameUtil.randomInt(2) == 0 ? 1 : 50);
                    BotCombatHelper.setBotSkillLevel(player2, 2, 60 + GameUtil.randomInt(30));
                    BotCombatHelper.setBotSkillLevel(player2, 4, 50 + GameUtil.randomInt(20));
                    BotCombatHelper.setBotSkillLevel(player2, 5, GameUtil.randomInt(2) == 0 ? 25 : 31);
                    BotCombatHelper.setBotSkillLevel(player2, 6, GameUtil.randomInt(2) == 0 ? 1 : 25);
                    break;
                }
                case 0: 
                case 1: 
                case 2: 
                case 3: 
                case 7: {
                    int n5;
                    int n6 = 1 + GameUtil.randomInt(99);
                    int n7 = n6 / 5 << 1;
                    if (n7 == 0) {
                        n7 = 2;
                    }
                    if ((n5 = n6 - n6 / 5 + GameUtil.randomInt(n7)) < 40) {
                        n5 = 40;
                    }
                    BotCombatHelper.setBotSkillLevel(player2, 0, n5);
                    n7 = n6 / 5 << 1;
                    if (n7 == 0) {
                        n7 = 2;
                    }
                    if ((n5 = n6 - n6 / 5 + GameUtil.randomInt(n7)) < 40) {
                        n5 = 40;
                    }
                    BotCombatHelper.setBotSkillLevel(player2, 2, n5);
                    n7 = n6 / 5 << 1;
                    if (n7 == 0) {
                        n7 = 2;
                    }
                    if ((n5 = n6 - n6 / 5 + GameUtil.randomInt(n7)) < 40) {
                        n5 = 40;
                    }
                    BotCombatHelper.setBotSkillLevel(player2, 1, n5);
                    n7 = n6 / 5 << 1;
                    if (n7 == 0) {
                        n7 = 2;
                    }
                    if ((n5 = n6 - n6 / 5 + GameUtil.randomInt(n7)) < 40) {
                        n5 = 40;
                    }
                    BotCombatHelper.setBotSkillLevel(player2, 4, n5);
                    n7 = n6 / 5 << 1;
                    if (n7 == 0) {
                        n7 = 2;
                    }
                    if ((n5 = n6 - n6 / 5 + GameUtil.randomInt(n7)) < 40) {
                        n5 = 40;
                    }
                    BotCombatHelper.setBotSkillLevel(player2, 6, n5);
                    n7 = n6 / 5 << 1;
                    if (n7 == 0) {
                        n7 = 2;
                    }
                    if ((n5 = n6 - (n6 / 5 << 1) + GameUtil.randomInt(n7)) < 40) {
                        n5 = 40;
                    }
                    BotCombatHelper.setBotSkillLevel(player2, 5, n5);
                    if (!BotCombatHelper.isFreeToPlayWorld()) {
                        n7 = n6 / 5 << 1;
                        if (n7 == 0) {
                            n7 = 2;
                        }
                        if ((n5 = n6 - (n6 / 5 << 1) + GameUtil.randomInt(n7)) < 40) {
                            n5 = 40;
                        }
                        BotCombatHelper.setBotSkillLevel(player2, 16, n5);
                    }
                    BotCombatLoadoutManager.selectCombatStyleFromStats(player2, false);
                }
            }
            player2.getSkillManager().getExperience()[3] = BotCombatHelper.calculateBotHitpointsExperience(player2);
            int[] nArray = player2.getSkillManager().getCurrentLevels();
            player2.getSkillManager();
            nArray[3] = SkillManager.getLevelForExperience(player2.getSkillManager().getExperience()[3]);
            player2.getSkillManager().refreshAllSkills();
            BotCombatLoadoutManager.prepareCombatLoadout(player3, false);
        }
        player.botEnabled = true;
        BotCombatHelper.prepareBotPvpSearchPosition(player);
        player3 = player;
        boolean bl2 = player3.botCombatStyle == 4 || player3.botCombatStyle == 6 || player3.botCombatStyle == 5;
        BotPvpTargetSearchTickTask botPvpTargetSearchTickTask = new BotPvpTargetSearchTickTask(10, player3, bl2);
        World.getTaskScheduler().schedule(botPvpTargetSearchTickTask);
    }

    public static void startGroupCombatBot(Player player) {
        Object object;
        Object object2;
        Player player2 = player;
        player.botEnabled = true;
        player2.botEscapeStuckTicks = 0;
        player2.botCombatEscapeActive = false;
        player2.botAntipoisonAvailable = false;
        player2.setAutoRetaliate(true);
        player2.botMagicPenaltyGearUnequipped = false;
        player2.botCombatState = null;
        player2.botCombatSpell = null;
        player2.botFoodDepleted = false;
        player2.botStrengthPotionDepleted = false;
        player2.botSpecialAttackEnergyCost = 0;
        player2.botSpecialCombatStyle = 0;
        player2.botWeaponItemId = player2.getEquipmentManager().getItemIdAtSlot(3);
        player2.botPrimaryCombatStyle = 0;
        player2.botCombatStyle = 0;
        if (player2.botWeaponItemId > 0) {
            object2 = ItemDefinition.forId(player2.botWeaponItemId).getName().toLowerCase();
            if (((String)object2).contains("bow") || ((String)object2).contains("knife") || ((String)object2).contains("dart") || ((String)object2).contains("javelin") || ((String)object2).contains("thrownaxe")) {
                player2.botPrimaryCombatStyle = BotPvpCombatHandler.RANGED_COMBAT_STYLE;
                player2.botCombatStyle = 1;
            } else if (((String)object2).contains("staff")) {
                player2.botPrimaryCombatStyle = BotPvpCombatHandler.MAGIC_COMBAT_STYLE;
                player2.botCombatStyle = 2;
            }
        }
        player2.botActiveCombatStyle = player2.botPrimaryCombatStyle;
        player2.botShieldItemId = player2.getEquipmentManager().getItemIdAtSlot(5);
        ItemStack[] itemStackArray = player2.getInventoryManager().getContainer().getItems();
        int n = itemStackArray.length;
        int n2 = 0;
        while (n2 < n) {
            object2 = itemStackArray[n2];
            if (object2 != null && ((ItemStack)object2).getDefinition().getEquipmentSlot() == 3) {
                player2.botSpecialWeaponItemId = ((ItemStack)object2).getId();
                player2.botSpecialCombatStyle = 0;
                break;
            }
            ++n2;
        }
        itemStackArray = player2.getInventoryManager().getContainer().getItems();
        n = itemStackArray.length;
        n2 = 0;
        while (n2 < n) {
            object2 = itemStackArray[n2];
            if (object2 != null && (object = FoodDefinition.forItemId(((ItemStack)object2).getId())) != null) {
                player2.botFoodItemId = ((ItemStack)object2).getId();
                break;
            }
            ++n2;
        }
        itemStackArray = player2.getInventoryManager().getContainer().getItems();
        n = itemStackArray.length;
        n2 = 0;
        while (n2 < n) {
            object2 = itemStackArray[n2];
            if (object2 != null && player2.getPotionHandler().selectPotionForItemId(((ItemStack)object2).getId()) && PotionHandler.definitions[player2.getPotionHandler().selectedDefinitionIndex].isAntipoison()) {
                player2.botAntipoisonAvailable = true;
                break;
            }
            ++n2;
        }
        if (player2.botPrimaryCombatStyle == BotPvpCombatHandler.MAGIC_COMBAT_STYLE) {
            object2 = player2;
            ArrayList<SpellDefinition> arrayList = new ArrayList<SpellDefinition>();
            object = BotCombatLoadoutTables.standardCombatSpellProgression;
            int n3 = 0;
            while (n3 < 12) {
                SpellDefinition spellDefinition = object[n3];
                arrayList.add(spellDefinition);
                ++n3;
            }
            if (!ServerSettings.freeToPlayWorld) {
                object = BotCombatLoadoutTables.standardWaveSpellProgression;
                n3 = 0;
                while (n3 < 4) {
                    SpellDefinition spellDefinition = object[n3];
                    arrayList.add(spellDefinition);
                    ++n3;
                }
                if (((Player)object2).botWeaponItemId == 4675) {
                    arrayList = new ArrayList();
                    object = BotCombatLoadoutTables.ancientCombatSpellProgression;
                    n3 = 0;
                    while (n3 < 16) {
                        SpellDefinition spellDefinition = object[n3];
                        arrayList.add(spellDefinition);
                        ++n3;
                    }
                }
            }
            int n4 = -1;
            n3 = 0;
            while (n3 < arrayList.size()) {
                object = (SpellDefinition)((Object)arrayList.get(n3));
                if (object.getRequiredLevel() > ((Player)object2).getSkillManager().getCurrentLevels()[6]) break;
                if (((Player)object2).getSkillManager().getCurrentLevels()[6] >= object.getRequiredLevel() && BotCombatHelper.hasRunesForSpell((Player)object2, (SpellDefinition)((Object)object))) {
                    n4 = n3;
                }
                ++n3;
            }
            if (n4 != -1) {
                ((Player)object2).setAutocastSpell((SpellDefinition)((Object)arrayList.get(n4)));
            }
            if (!ServerSettings.freeToPlayWorld) {
                if (SpellDefinition.ENTANGLE.getRequiredLevel() <= player2.getSkillManager().getCurrentLevels()[6] && BotCombatHelper.hasRunesForSpell(player2, SpellDefinition.ENTANGLE)) {
                    player2.botCombatSpell = SpellDefinition.ENTANGLE;
                } else if (SpellDefinition.SNARE.getRequiredLevel() <= player2.getSkillManager().getCurrentLevels()[6] && BotCombatHelper.hasRunesForSpell(player2, SpellDefinition.SNARE)) {
                    player2.botCombatSpell = SpellDefinition.SNARE;
                } else if (SpellDefinition.BIND.getRequiredLevel() <= player2.getSkillManager().getCurrentLevels()[6] && BotCombatHelper.hasRunesForSpell(player2, SpellDefinition.BIND)) {
                    player2.botCombatSpell = SpellDefinition.BIND;
                }
            } else if (SpellDefinition.BIND.getRequiredLevel() <= player2.getSkillManager().getCurrentLevels()[6] && BotCombatHelper.hasRunesForSpell(player2, SpellDefinition.BIND)) {
                player2.botCombatSpell = SpellDefinition.BIND;
            }
            if (player2.getAutocastSpell() == null) {
                System.out.print("Warning! playerBot detected as mage, but no autospell set!");
                player2.packetSender.sendGameMessage("Warning! playerBot detected as mage, but no autospell set!");
            }
        }
        player2 = player;
        object2 = new BotGroupCombatTickTask(1, player2);
        World.getTaskScheduler().schedule((TickTask)object2);
    }

    public static void selectCombatStyleFromStats(Player player, boolean n) {
        int n2 = (player.getSkillManager().getCurrentLevels()[0] + player.getSkillManager().getCurrentLevels()[2]) / 2;
        int n3 = player.getSkillManager().getCurrentLevels()[6];
        int n4 = player.getSkillManager().getCurrentLevels()[4];
        if (n4 > n2 && n4 > n3) {
            player.botCombatStyle = 1;
            return;
        }
        if (n3 > n2 && n3 > n4) {
            player.botCombatStyle = 2;
            return;
        }
        if (n4 == n2 && n3 == n2) {
            n = GameUtil.randomInt(3);
            if (n == 0) {
                player.botCombatStyle = 1;
                return;
            }
            if (n == 1) {
                player.botCombatStyle = 2;
                return;
            }
            if (n == 2) {
                player.botCombatStyle = 0;
                return;
            }
        } else {
            player.botCombatStyle = 0;
            if (n == 0 && GameUtil.randomInt(5) == 0 && player.getSkillManager().getCurrentLevels()[0] >= 40 && player.getSkillManager().getCurrentLevels()[1] >= 40 && n4 >= 40) {
                player.botCombatStyle = 7;
            }
        }
    }

    public static void equipRandomCape(Player player) {
        int n = !BotCombatHelper.isFreeToPlayWorld() && GameUtil.randomInt(2) == 0 && ItemDefinition.isDefined(4413) ? BotCombatLoadoutTables.castleWarsCapeIds[GameUtil.randomInt(55)] : BotCombatHelper.selectBotLoadoutItemId(player, BotCombatLoadoutTables.capeIds, BotCombatLoadoutTables.teamCapeIds, true);
        player.getEquipmentManager().getContainer().setItem(1, new ItemStack(n));
    }

    public static void equipGlovesAndBoots(Player player) {
        int n = 1061;
        int n2 = 1059;
        if (!BotCombatHelper.isFreeToPlayWorld()) {
            int[] nArray = BotCombatHelper.filterEquippableMemberLoadoutItems(player, null, BotCombatLoadoutTables.coloredMageBootIds);
            int[] nArray2 = BotCombatHelper.filterEquippableMemberLoadoutItems(player, null, BotCombatLoadoutTables.coloredMageGloveIds);
            if (nArray2.length > 0) {
                n2 = GameUtil.randomInt(nArray2.length);
                n = nArray[n2];
                n2 = nArray2[n2];
            }
        }
        player.getEquipmentManager().getContainer().setItem(9, new ItemStack(n2));
        player.getEquipmentManager().getContainer().setItem(10, new ItemStack(n));
    }

    private static void addCombatSupplies(Player player) {
        int n;
        if (player.getSkillManager().getCurrentLevels()[3] < 25) {
            n = GameUtil.randomInt(2) == 0 ? 333 : 329;
        } else if (player.getSkillManager().getCurrentLevels()[3] >= 25 && player.getSkillManager().getCurrentLevels()[3] < 40) {
            n = GameUtil.randomInt(2) == 0 ? 361 : 379;
        } else {
            int n2 = n = GameUtil.randomInt(2) == 0 ? 379 : 373;
        }
        if (!BotCombatHelper.isFreeToPlayWorld()) {
            if (GameUtil.randomInt(2) == 0) {
                if (player.getSkillManager().getCurrentLevels()[3] >= 60) {
                    n = 385;
                }
            } else if (GameUtil.randomInt(3) == 0 && player.getSkillManager().getCurrentLevels()[3] >= 80) {
                n = 391;
            }
        }
        player.getInventoryManager().addItem(new ItemStack(n, 15));
        player.botFoodItemId = n;
        if (GameUtil.randomInt(100) != 0) {
            if (player.getSpellbook() == Spellbook.MODERN) {
                if (SpellDefinition.VARROCK_TELEPORT.getRequiredLevel() <= player.getSkillManager().getCurrentLevels()[6]) {
                    BotCombatHelper.grantBotSpellRunes(player, SpellDefinition.VARROCK_TELEPORT, 1);
                }
            } else if (player.getSpellbook() == Spellbook.ANCIENT && SpellDefinition.PADDEWWA_TELEPORT.getRequiredLevel() <= player.getSkillManager().getCurrentLevels()[6]) {
                BotCombatHelper.grantBotSpellRunes(player, SpellDefinition.PADDEWWA_TELEPORT, 1);
            }
        }
        if (!BotCombatHelper.isFreeToPlayWorld() && player.getCombatLevel() >= 25 && GameUtil.randomInt(2) == 0) {
            player.getInventoryManager().addItem(new ItemStack(2446, 1));
            player.botAntipoisonAvailable = true;
        }
    }

    private static int selectSpecialAttackWeapon(Player player) {
        int n;
        if (player.botCombatStyle == 9 || player.botCombatStyle == 10) {
            n = 1231;
        } else if (player.botCombatStyle == 8) {
            n = player.getEquipmentManager().canEquipItem(4153) ? 4153 : 6528;
        } else {
            int n2;
            n = BotCombatHelper.selectBestBotLoadoutItemId(player, BotCombatLoadoutTables.twoHandedSwordIds, null);
            if (!BotCombatHelper.isFreeToPlayWorld() && GameUtil.randomInt(2) == 0 && (n2 = BotCombatHelper.selectBotLoadoutItemId(player, null, BotCombatLoadoutTables.specialMeleeWeaponIds, true)) != -1) {
                n = n2;
            }
        }
        player.botSpecialWeaponItemId = n;
        player.botSpecialCombatStyle = 0;
        SpecialAttackDefinition specialAttackDefinition = SpecialAttackDefinition.forItem(new ItemStack(player.botSpecialWeaponItemId));
        if (specialAttackDefinition != null) {
            player.botSpecialAttackEnergyCost = specialAttackDefinition.getEnergyCost();
        }
        return player.botSpecialWeaponItemId;
    }

    public static void prepareMeleeLoadout(Player player) {
        BotCombatLoadoutManager.prepareMeleeLoadout(player, false);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static void prepareMeleeLoadout(Player player, boolean bl) {
        int n;
        int n2;
        int n3;
        int n4;
        boolean bl2;
        int n5;
        block40: {
            int n6 = GameUtil.randomInt(bl ? 3 : 4);
            n5 = 0;
            bl2 = false;
            if (n6 == 0) {
                n5 = BotCombatHelper.selectBestBotLoadoutItemId(player, BotCombatLoadoutTables.longswordIds, BotCombatLoadoutTables.dragonLongswordIds);
            } else if (n6 == 1) {
                n5 = BotCombatHelper.selectBestBotLoadoutItemId(player, BotCombatLoadoutTables.scimitarIds, !ItemDefinition.isDefined(4587) ? null : BotCombatLoadoutTables.dragonScimitarIds);
            } else if (n6 == 2) {
                n5 = BotCombatHelper.selectBestBotLoadoutItemId(player, BotCombatLoadoutTables.battleaxeIds, null);
            } else if (n6 == 3) {
                n5 = BotCombatHelper.selectBestBotLoadoutItemId(player, BotCombatLoadoutTables.twoHandedSwordIds, null);
                bl2 = true;
            }
            player.botWeaponItemId = n5;
            player.getEquipmentManager().getContainer().setItem(3, new ItemStack(player.botWeaponItemId));
            if (player.currentBotTask == null) {
                n6 = BotCombatLoadoutManager.selectSpecialAttackWeapon(player);
                if (GameUtil.randomInt(3) == 0 && player.botWeaponItemId != n6) {
                    player.botSpecialWeaponItemId = n6;
                    player.getInventoryManager().addItem(new ItemStack(player.botSpecialWeaponItemId, 1));
                }
            }
            n6 = 0;
            n5 = 0;
            while (n5 < 7) {
                n4 = new ItemStack(BotCombatLoadoutTables.platebodyIds[n5]).getDefinition().getRequiredLevel(1);
                if (n4 > player.getSkillManager().getCurrentLevels()[1]) {
                    n6 = n5 - 1;
                    break;
                }
                n6 = n5++;
            }
            int n7 = n5 = GameUtil.randomInt(2) == 0 ? BotCombatLoadoutTables.mediumHelmetIds[n6] : BotCombatLoadoutTables.fullHelmetIds[n6];
            if (!BotCombatHelper.isFreeToPlayWorld() && GameUtil.randomInt(2) == 0 && (n4 = BotCombatHelper.selectBotLoadoutItemId(player, null, BotCombatLoadoutTables.rareMeleeHelmetIds, true)) != -1) {
                n5 = n4;
            }
            n4 = GameUtil.randomInt(2) == 0 ? BotCombatLoadoutTables.chainbodyIds[n6] : BotCombatLoadoutTables.platebodyIds[n6];
            n3 = GameUtil.randomInt(2) == 0 ? BotCombatLoadoutTables.plateskirtIds[n6] : BotCombatLoadoutTables.platelegIds[n6];
            int n8 = n2 = GameUtil.randomInt(2) == 0 ? BotCombatLoadoutTables.squareShieldIds[n6] : BotCombatLoadoutTables.kiteshieldIds[n6];
            if (player.currentBotTask == null) {
                if (GameUtil.randomInt(4) == 0) {
                    n2 = 1540;
                }
            } else if (bl) {
                n2 = 1540;
            }
            if (n6 != 2 && n6 != 4 && n6 != 5) break block40;
            boolean bl3 = false;
            int n9 = GameUtil.randomInt(3);
            if (n6 == 4) {
                boolean bl4 = true;
            } else if (n6 == 5) {
                int n10 = 2;
            }
            int n11 = 0;
            while (n11 < 4) {
                block43: {
                    int n12;
                    block45: {
                        void var1_5;
                        block47: {
                            block46: {
                                block41: {
                                    block44: {
                                        block42: {
                                            n12 = n6 == 5 ? 1500 : 1000;
                                            if ((n12 = GameUtil.randomInt(n12)) != 0) break block41;
                                            if (n11 != 0) break block42;
                                            n5 = BotCombatLoadoutTables.trimmedFullHelmetIds[var1_5];
                                            break block43;
                                        }
                                        if (n11 != 1) break block44;
                                        n4 = BotCombatLoadoutTables.trimmedPlatebodyIds[var1_5];
                                        break block43;
                                    }
                                    if (n11 == 2) {
                                        n3 = GameUtil.randomInt(2) == 0 ? BotCombatLoadoutTables.trimmedPlateskirtIds[var1_5] : BotCombatLoadoutTables.trimmedPlatelegIds[var1_5];
                                        break block43;
                                    } else if (n11 == 3) {
                                        n2 = BotCombatLoadoutTables.trimmedKiteshieldIds[var1_5];
                                    }
                                    break block43;
                                }
                                if (n12 != 1) break block45;
                                if (n11 != 0) break block46;
                                n5 = BotCombatLoadoutTables.goldTrimmedFullHelmetIds[var1_5];
                                break block43;
                            }
                            if (n11 != 1) break block47;
                            n4 = BotCombatLoadoutTables.goldTrimmedPlatebodyIds[var1_5];
                            break block43;
                        }
                        if (n11 == 2) {
                            n3 = GameUtil.randomInt(2) == 0 ? BotCombatLoadoutTables.goldTrimmedPlateskirtIds[var1_5] : BotCombatLoadoutTables.goldTrimmedPlatelegIds[var1_5];
                            break block43;
                        } else if (n11 == 3) {
                            n2 = BotCombatLoadoutTables.goldTrimmedKiteshieldIds[var1_5];
                        }
                        break block43;
                    }
                    if (n12 == 3 && n6 == 5) {
                        if (n11 == 0) {
                            n5 = BotCombatLoadoutTables.godFullHelmetIds[n9];
                        } else if (n11 == 1) {
                            n4 = BotCombatLoadoutTables.godPlatebodyIds[n9];
                        } else if (n11 == 2) {
                            n3 = GameUtil.randomInt(2) == 0 ? BotCombatLoadoutTables.godPlateskirtIds[n9] : BotCombatLoadoutTables.godPlatelegIds[n9];
                        } else if (n11 == 3) {
                            n2 = BotCombatLoadoutTables.godKiteshieldIds[n9];
                        }
                    }
                }
                ++n11;
            }
        }
        player.getEquipmentManager().getContainer().setItem(0, new ItemStack(n5));
        player.getEquipmentManager().getContainer().setItem(4, new ItemStack(n4));
        player.getEquipmentManager().getContainer().setItem(7, new ItemStack(n3));
        if (!bl2) {
            player.botShieldItemId = n2;
            player.getEquipmentManager().getContainer().setItem(5, new ItemStack(player.botShieldItemId));
        }
        if (player.getCombatLevel() <= 15) {
            int n13 = 1478;
        } else if (player.getCombatLevel() <= 40 && player.getCombatLevel() > 15) {
            int n14 = GameUtil.randomInt(2) == 0 ? 1725 : 1729;
        } else {
            int n15 = 1731;
        }
        if (!BotCombatHelper.isFreeToPlayWorld() && player.getCombatLevel() >= 60 && GameUtil.randomInt(2) == 0) {
            n = 1712;
        }
        player.getEquipmentManager().getContainer().setItem(2, new ItemStack(n));
    }

    public static void prepareRangedLoadout(Player player) {
        int n;
        int n2;
        int n3 = BotCombatHelper.selectBestBotLoadoutItemId(player, BotCombatLoadoutTables.basicRangedHeadIds, (int[])(ItemDefinition.isDefined(3749) ? BotCombatLoadoutTables.rareRangedHeadIds : null));
        int n4 = BotCombatHelper.selectBestBotLoadoutItemId(player, BotCombatLoadoutTables.basicRangedBodyIds, BotCombatLoadoutTables.dragonhideBodyIds);
        int n5 = BotCombatHelper.selectBestBotLoadoutItemId(player, BotCombatLoadoutTables.basicRangedLegIds, BotCombatLoadoutTables.dragonhideChapsIds);
        int n6 = BotCombatHelper.selectBestBotLoadoutItemId(player, BotCombatLoadoutTables.basicRangedVambraceIds, BotCombatLoadoutTables.dragonhideVambraceIds);
        if (ServerSettings.cacheVersion >= 306) {
            n2 = BotCombatHelper.selectBestBotLoadoutItemId(player, BotCombatLoadoutTables.post306BowIds, BotCombatLoadoutTables.post306HighTierBowIds);
            n = BotCombatHelper.selectBestBotLoadoutItemId(player, BotCombatLoadoutTables.post306ArrowIds, BotCombatLoadoutTables.post306HighTierArrowIds);
        } else {
            n2 = BotCombatHelper.selectBestBotLoadoutItemId(player, BotCombatLoadoutTables.legacyBowIds, BotCombatLoadoutTables.legacyHighTierBowIds);
            n = BotCombatHelper.selectBestBotLoadoutItemId(player, BotCombatLoadoutTables.legacyArrowIds, BotCombatLoadoutTables.legacyHighTierArrowIds);
        }
        int n7 = 40 + GameUtil.randomInt(20);
        if (player.clanWarsBot) {
            n7 *= ClanWarsBotManager.clanWarsSupplyMultiplier;
        }
        if (player.currentBotTask != null) {
            n7 = 1000;
        }
        player.botWeaponItemId = n2;
        player.getEquipmentManager().getContainer().setItem(0, new ItemStack(n3));
        player.getEquipmentManager().getContainer().setItem(7, new ItemStack(n5));
        player.getEquipmentManager().getContainer().setItem(4, new ItemStack(n4));
        player.getEquipmentManager().getContainer().setItem(9, new ItemStack(n6));
        player.getEquipmentManager().getContainer().setItem(3, new ItemStack(player.botWeaponItemId));
        player.getEquipmentManager().getContainer().setItem(13, new ItemStack(n, n7));
        player.getEquipmentManager().getContainer().setItem(10, new ItemStack(1061));
        if (player.currentBotTask != null) {
            ItemStack[] itemStackArray;
            if (player.botTaskRequiredItems != null) {
                itemStackArray = new ItemStack[player.botTaskRequiredItems.length + 1];
                n4 = 0;
                while (n4 < itemStackArray.length - 1) {
                    itemStackArray[n4] = player.botTaskRequiredItems[n4];
                    ++n4;
                }
                itemStackArray[player.botTaskRequiredItems.length] = new ItemStack(n, n7);
            } else {
                ItemStack[] itemStackArray2 = new ItemStack[1];
                itemStackArray = itemStackArray2;
                itemStackArray2[0] = new ItemStack(n, n7);
            }
            player.getBankContainer().addToTab(new ItemStack(n, n7 * 10), 0);
            player.botTaskRequiredItems = itemStackArray;
        }
    }

    /*
     * Unable to fully structure code
     */
    public static void prepareMagicLoadout(Player var0) {
        var1_1 = new int[]{579, 1017};
        var2_3 = new int[]{577, 546};
        var3_5 = new int[]{1011, 548};
        var4_7 = 2;
        if (!BotCombatHelper.isFreeToPlayWorld()) {
            var4_7 = 3;
        }
        if ((var5_10 = GameUtil.randomInt(var4_7)) != 2) ** GOTO lbl22
        var6_11 = BotCombatHelper.filterEquippableMemberLoadoutItems(var0, null, BotCombatLoadoutTables.coloredMageHeadIds);
        var4_8 = BotCombatHelper.filterEquippableMemberLoadoutItems(var0, null, BotCombatLoadoutTables.coloredMageBodyIds);
        var7_13 = BotCombatHelper.filterEquippableMemberLoadoutItems(var0, null, BotCombatLoadoutTables.coloredMageLegIds);
        var8_15 = BotCombatHelper.filterEquippableMemberLoadoutItems(var0, null, BotCombatLoadoutTables.coloredMageBootIds);
        var9_17 = BotCombatHelper.filterEquippableMemberLoadoutItems(var0, null, BotCombatLoadoutTables.coloredMageGloveIds);
        if (var9_17.length > 0) {
            var5_10 = GameUtil.randomInt(var9_17.length);
            var1_2 = var6_11[var5_10];
            var2_4 = var4_8[var5_10];
            var3_6 = var7_13[var5_10];
            var4_9 = var8_15[var5_10];
            var5_10 = var9_17[var5_10];
        } else {
            var5_10 = GameUtil.randomInt(2);
lbl22:
            // 2 sources

            var1_2 = var1_1[var5_10];
            var2_4 = var2_3[var5_10];
            var3_6 = var3_5[var5_10];
            var4_9 = 1061;
            var5_10 = 1059;
        }
        var7_14 = var0.tradeAdvertMode != -1 || var0.dropPartyLeader != false || var0.dropPartyFollower != false;
        var6_11 = var0;
        var8_16 = 1381;
        var9_18 = false;
        var10_19 = new ArrayList<SpellDefinition>();
        var13_20 = BotCombatLoadoutTables.standardCombatSpellProgression;
        var12_21 = 0;
        while (var12_21 < 12) {
            var11_22 = var13_20[var12_21];
            var10_19.add(var11_22);
            ++var12_21;
        }
        if (!BotCombatHelper.isFreeToPlayWorld()) {
            var13_20 = BotCombatLoadoutTables.standardWaveSpellProgression;
            var12_21 = 0;
            while (var12_21 < 4) {
                var11_22 = var13_20[var12_21];
                var10_19.add(var11_22);
                ++var12_21;
            }
            if (var6_11.getSkillManager().getCurrentLevels()[6] >= 50 && ItemDefinition.isDefined(4675) && GameUtil.randomInt(3) == 0) {
                var9_18 = true;
                var10_19 = var6_11;
                var10_19.packetSender.setSidebarInterface(6, 12855);
                var6_11.setSpellbook(Spellbook.ANCIENT);
                var10_19 = new ArrayList<SpellDefinition>();
                var13_20 = BotCombatLoadoutTables.ancientCombatSpellProgression;
                var12_21 = 0;
                while (var12_21 < 16) {
                    var11_22 = var13_20[var12_21];
                    var10_19.add(var11_22);
                    ++var12_21;
                }
            }
        }
        var11_23 = 0;
        var12_21 = 0;
        while (var12_21 < var10_19.size()) {
            var11_24 = (SpellDefinition)var10_19.get(var12_21);
            if (var11_24.getRequiredLevel() > var6_11.getSkillManager().getCurrentLevels()[6]) {
                var11_23 = var12_21 - 1;
                break;
            }
            var11_23 = var12_21++;
        }
        if (var9_18) {
            var8_16 = 4675;
        } else if (var10_19.get(var11_23) == SpellDefinition.WIND_STRIKE || var10_19.get(var11_23) == SpellDefinition.WIND_BOLT || var10_19.get(var11_23) == SpellDefinition.WIND_BLAST || var10_19.get(var11_23) == SpellDefinition.WIND_WAVE) {
            var8_16 = 1381;
        } else if (var10_19.get(var11_23) == SpellDefinition.WATER_STRIKE || var10_19.get(var11_23) == SpellDefinition.WATER_BOLT || var10_19.get(var11_23) == SpellDefinition.WATER_BLAST || var10_19.get(var11_23) == SpellDefinition.WATER_WAVE) {
            var8_16 = 1383;
        } else if (var10_19.get(var11_23) == SpellDefinition.EARTH_STRIKE || var10_19.get(var11_23) == SpellDefinition.EARTH_BOLT || var10_19.get(var11_23) == SpellDefinition.EARTH_BLAST || var10_19.get(var11_23) == SpellDefinition.EARTH_WAVE) {
            var8_16 = 1385;
        } else if (var10_19.get(var11_23) == SpellDefinition.FIRE_STRIKE || var10_19.get(var11_23) == SpellDefinition.FIRE_BOLT || var10_19.get(var11_23) == SpellDefinition.FIRE_BLAST || var10_19.get(var11_23) == SpellDefinition.FIRE_WAVE) {
            var8_16 = 1387;
        }
        var6_11.botWeaponItemId = var8_16;
        var6_11.getEquipmentManager().getContainer().setItem(3, new ItemStack(var6_11.botWeaponItemId));
        if (!var7_14) {
            var12_21 = 30 + GameUtil.randomInt(30);
            if (var6_11.clanWarsBot) {
                var12_21 *= ClanWarsBotManager.clanWarsSupplyMultiplier;
            }
            if (var6_11.currentBotTask != null) {
                var12_21 = 1000;
            }
            BotCombatHelper.grantBotSpellRunes((Player)var6_11, (SpellDefinition)var10_19.get(var11_23), var12_21);
            if (!var9_18 && var6_11.currentBotTask == null) {
                if (!BotCombatHelper.isFreeToPlayWorld() && GameUtil.randomInt(3) == 0 && 12445 < InterfaceDefinition.interfaceCount && SpellDefinition.TELE_BLOCK.getRequiredLevel() <= var6_11.getSkillManager().getCurrentLevels()[6]) {
                    var12_21 = GameUtil.randomInt(11);
                    BotCombatHelper.grantBotSpellRunes((Player)var6_11, SpellDefinition.TELE_BLOCK, var12_21);
                }
                if (GameUtil.randomInt(3) == 0) {
                    var12_21 = GameUtil.randomInt(11);
                    if (!BotCombatHelper.isFreeToPlayWorld()) {
                        if (SpellDefinition.ENTANGLE.getRequiredLevel() <= var6_11.getSkillManager().getCurrentLevels()[6]) {
                            BotCombatHelper.grantBotSpellRunes((Player)var6_11, SpellDefinition.ENTANGLE, var12_21);
                            var6_11.botCombatSpell = SpellDefinition.ENTANGLE;
                        } else if (SpellDefinition.SNARE.getRequiredLevel() <= var6_11.getSkillManager().getCurrentLevels()[6]) {
                            BotCombatHelper.grantBotSpellRunes((Player)var6_11, SpellDefinition.SNARE, var12_21);
                            var6_11.botCombatSpell = SpellDefinition.SNARE;
                        } else if (SpellDefinition.BIND.getRequiredLevel() <= var6_11.getSkillManager().getCurrentLevels()[6]) {
                            BotCombatHelper.grantBotSpellRunes((Player)var6_11, SpellDefinition.BIND, var12_21);
                            var6_11.botCombatSpell = SpellDefinition.BIND;
                        }
                    } else if (SpellDefinition.BIND.getRequiredLevel() <= var6_11.getSkillManager().getCurrentLevels()[6]) {
                        BotCombatHelper.grantBotSpellRunes((Player)var6_11, SpellDefinition.BIND, var12_21);
                        var6_11.botCombatSpell = SpellDefinition.BIND;
                    }
                }
            }
            var6_11.setAutocastSpell((SpellDefinition)var10_19.get(var11_23));
        }
        if (!BotCombatHelper.isFreeToPlayWorld() && var0.getSkillManager().getCurrentLevels()[6] >= 20 && var0.getSkillManager().getCurrentLevels()[1] >= 20 && ItemDefinition.isDefined(3755)) {
            if (var0.getSkillManager().getCurrentLevels()[1] >= 45) {
                var1_2 = 3755;
            }
            if (ItemDefinition.isDefined(4097)) {
                var1_2 = BotCombatHelper.selectBestBotLoadoutItemId(var0, null, BotCombatLoadoutTables.mageHeadIds);
                var2_4 = BotCombatHelper.selectBestBotLoadoutItemId(var0, null, BotCombatLoadoutTables.mageBodyIds);
                var3_6 = BotCombatHelper.selectBestBotLoadoutItemId(var0, null, BotCombatLoadoutTables.mageLegIds);
                var4_9 = BotCombatHelper.selectBestBotLoadoutItemId(var0, null, BotCombatLoadoutTables.mageGloveIds);
                var5_10 = BotCombatHelper.selectBestBotLoadoutItemId(var0, null, BotCombatLoadoutTables.mageBootIds);
            }
        }
        var0.getEquipmentManager().getContainer().setItem(0, new ItemStack(var1_2));
        var0.getEquipmentManager().getContainer().setItem(4, new ItemStack(var2_4));
        var0.getEquipmentManager().getContainer().setItem(7, new ItemStack(var3_6));
        var6_12 = 1727;
        var0.botShieldItemId = 1540;
        if (var0.botCombatStyle == 2 && GameUtil.randomInt(2) == 0) {
            var6_12 = 1731;
        }
        if (!BotCombatHelper.isFreeToPlayWorld()) {
            v0 = var0.botShieldItemId = GameUtil.randomInt(3) == 0 ? 2890 : 1540;
            if (var0.getCombatLevel() >= 60 && GameUtil.randomInt(2) == 0) {
                var6_12 = 1712;
            }
        }
        var0.getEquipmentManager().getContainer().setItem(5, new ItemStack(var0.botShieldItemId));
        var0.getEquipmentManager().getContainer().setItem(2, new ItemStack(var6_12));
        var0.getEquipmentManager().getContainer().setItem(9, new ItemStack(var5_10));
        var0.getEquipmentManager().getContainer().setItem(10, new ItemStack(var4_9));
    }

    public static void prepareCombatLoadout(Player player, boolean bl) {
        int n = player.botCombatStyle;
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        boolean bl2 = true;
        if (bl) {
            bl2 = false;
        }
        switch (n) {
            case 4: {
                int player2;
                player.botActiveCombatStyle = player.botPrimaryCombatStyle = 0;
                int[] nArray = new int[]{1303, 1333, 1373};
                player.botWeaponItemId = nArray[GameUtil.randomInt(3)];
                player.getEquipmentManager().getContainer().setItem(3, new ItemStack(player.botWeaponItemId));
                if (player.getSkillManager().getCurrentLevels()[1] >= 10) {
                    player.getEquipmentManager().getContainer().setItem(0, new ItemStack(1165));
                    player.getEquipmentManager().getContainer().setItem(4, new ItemStack(1125));
                    player.getEquipmentManager().getContainer().setItem(7, GameUtil.randomInt(4) == 0 ? new ItemStack(1089) : new ItemStack(1077));
                    player.botShieldItemId = GameUtil.randomInt(2) == 0 ? 1195 : 1540;
                } else {
                    player.getEquipmentManager().getContainer().setItem(0, new ItemStack(1153));
                    player.getEquipmentManager().getContainer().setItem(4, new ItemStack(1115));
                    player.getEquipmentManager().getContainer().setItem(7, GameUtil.randomInt(4) == 0 ? new ItemStack(1081) : new ItemStack(1067));
                    player.botShieldItemId = GameUtil.randomInt(2) == 0 ? 1191 : 1540;
                }
                player.getEquipmentManager().getContainer().setItem(5, new ItemStack(player.botShieldItemId));
                int n2 = 1725;
                if (player.botCombatStyle == 2 && GameUtil.randomInt(2) == 0) {
                    int nArray2 = 1731;
                }
                if (!BotCombatHelper.isFreeToPlayWorld() && player.getCombatLevel() >= 60 && GameUtil.randomInt(2) == 0) {
                    player2 = 1712;
                }
                player.getEquipmentManager().getContainer().setItem(2, new ItemStack(player2));
                BotCombatLoadoutManager.equipGlovesAndBoots(player);
                player.getInventoryManager().addItem(new ItemStack(113, 1));
                if (GameUtil.randomInt(3) != 0) break;
                player.getInventoryManager().addItem(new ItemStack(BotCombatLoadoutManager.selectSpecialAttackWeapon(player), 1));
                break;
            }
            case 9: {
                player.botActiveCombatStyle = player.botPrimaryCombatStyle = 0;
                int[] nArray = new int[]{1231, 4587};
                player.botWeaponItemId = nArray[GameUtil.randomInt(2)];
                player.getEquipmentManager().getContainer().setItem(3, new ItemStack(player.botWeaponItemId));
                player.getEquipmentManager().getContainer().setItem(0, new ItemStack(6109));
                player.getEquipmentManager().getContainer().setItem(4, new ItemStack(6107));
                player.getEquipmentManager().getContainer().setItem(7, new ItemStack(6108));
                player.botShieldItemId = 3842;
                player.getEquipmentManager().getContainer().setItem(5, new ItemStack(player.botShieldItemId));
                player.getEquipmentManager().getContainer().setItem(2, new ItemStack(1725));
                player.getEquipmentManager().getContainer().setItem(10, new ItemStack(3105));
                player.getEquipmentManager().getContainer().setItem(1, new ItemStack(6111));
                player.getEquipmentManager().getContainer().setItem(12, new ItemStack(2550));
                player.getInventoryManager().addItem(new ItemStack(113, 1));
                if (GameUtil.randomInt(3) == 0 && player.botWeaponItemId != 1231) {
                    player.getInventoryManager().addItem(new ItemStack(BotCombatLoadoutManager.selectSpecialAttackWeapon(player), 1));
                }
                bl2 = false;
                break;
            }
            case 10: {
                player.botActiveCombatStyle = player.botPrimaryCombatStyle = 0;
                player.botWeaponItemId = 4151;
                player.getEquipmentManager().getContainer().setItem(3, new ItemStack(player.botWeaponItemId));
                player.getEquipmentManager().getContainer().setItem(4, new ItemStack(544));
                player.getEquipmentManager().getContainer().setItem(7, new ItemStack(542));
                player.getEquipmentManager().getContainer().setItem(2, new ItemStack(1731));
                player.getEquipmentManager().getContainer().setItem(10, new ItemStack(3105));
                player.getEquipmentManager().getContainer().setItem(1, new ItemStack(4411));
                player.getEquipmentManager().getContainer().setItem(12, new ItemStack(2550));
                player.getEquipmentManager().getContainer().setItem(9, new ItemStack(2912));
                player.getInventoryManager().addItem(new ItemStack(113, 1));
                if (GameUtil.randomInt(3) == 0) {
                    player.getInventoryManager().addItem(new ItemStack(BotCombatLoadoutManager.selectSpecialAttackWeapon(player), 1));
                }
                bl2 = false;
                break;
            }
            case 11: {
                player.botActiveCombatStyle = player.botPrimaryCombatStyle = 0;
                player.botWeaponItemId = 1231;
                player.getEquipmentManager().getContainer().setItem(3, new ItemStack(player.botWeaponItemId));
                player.getEquipmentManager().getContainer().setItem(4, new ItemStack(577));
                player.getEquipmentManager().getContainer().setItem(7, new ItemStack(1033));
                player.getEquipmentManager().getContainer().setItem(2, new ItemStack(1731));
                player.getEquipmentManager().getContainer().setItem(10, new ItemStack(3105));
                player.getEquipmentManager().getContainer().setItem(1, new ItemStack(4365));
                player.botShieldItemId = 3842;
                player.getEquipmentManager().getContainer().setItem(5, new ItemStack(player.botShieldItemId));
                player.getInventoryManager().addItem(new ItemStack(113, 1));
                bl2 = false;
                break;
            }
            case 0: {
                player.botActiveCombatStyle = player.botPrimaryCombatStyle = 0;
                Player n6 = player;
                BotCombatLoadoutManager.prepareMeleeLoadout(n6, false);
                BotCombatLoadoutManager.equipGlovesAndBoots(player);
                player.getInventoryManager().addItem(new ItemStack(113, 1));
                break;
            }
            case 7: {
                int n2;
                player.botActiveCombatStyle = player.botPrimaryCombatStyle = 0;
                int[] player3 = new int[]{1303, 1333, 1373};
                player.botWeaponItemId = player3[GameUtil.randomInt(3)];
                n = 1135;
                int n3 = 1099;
                player.botShieldItemId = 1540;
                player.getEquipmentManager().getContainer().setItem(5, new ItemStack(player.botShieldItemId));
                int n4 = 1065;
                int n5 = 1061;
                int n6 = 1731;
                if (!BotCombatHelper.isFreeToPlayWorld()) {
                    if (player.getCombatLevel() >= 60 && GameUtil.randomInt(2) == 0) {
                        n2 = 1712;
                    }
                    if (player.getEquipmentManager().canEquipItem(4587) && ItemDefinition.isDefined(4587)) {
                        player.botWeaponItemId = 4587;
                    }
                    if (player.getEquipmentManager().canEquipItem(2503)) {
                        n = 2503;
                        n3 = 2497;
                        n4 = 2491;
                    }
                    if (player.getEquipmentManager().canEquipItem(3751)) {
                        player.getEquipmentManager().getContainer().setItem(0, new ItemStack(3751));
                    }
                    player.getEquipmentManager().getContainer().setItem(12, new ItemStack(2550));
                    player.getEquipmentManager().getContainer().setItem(1, new ItemStack(2414));
                    n5 = 3105;
                    bl2 = false;
                }
                player.getEquipmentManager().getContainer().setItem(3, new ItemStack(player.botWeaponItemId));
                player.getEquipmentManager().getContainer().setItem(2, new ItemStack(n2));
                player.getEquipmentManager().getContainer().setItem(9, new ItemStack(n4));
                player.getEquipmentManager().getContainer().setItem(10, new ItemStack(n5));
                player.getEquipmentManager().getContainer().setItem(4, new ItemStack(n));
                player.getEquipmentManager().getContainer().setItem(7, new ItemStack(n3));
                player.getInventoryManager().addItem(new ItemStack(113, 1));
                if (GameUtil.randomInt(3) != 0) break;
                player.getInventoryManager().addItem(new ItemStack(BotCombatLoadoutManager.selectSpecialAttackWeapon(player), 1));
                break;
            }
            case 2: 
            case 6: {
                player.botActiveCombatStyle = player.botPrimaryCombatStyle = BotPvpCombatHandler.MAGIC_COMBAT_STYLE;
                BotCombatLoadoutManager.prepareMagicLoadout(player);
                break;
            }
            case 8: {
                player.botActiveCombatStyle = player.botPrimaryCombatStyle = BotPvpCombatHandler.RANGED_COMBAT_STYLE;
                Player player2 = player;
                n = GameUtil.randomInt(2) == 0 ? 811 : 868;
                int n7 = BotCombatHelper.selectBestBotLoadoutItemId(player2, BotCombatLoadoutTables.basicRangedLegIds, BotCombatLoadoutTables.dragonhideChapsIds);
                int n8 = BotCombatHelper.selectBestBotLoadoutItemId(player2, BotCombatLoadoutTables.basicRangedVambraceIds, BotCombatLoadoutTables.dragonhideVambraceIds);
                int n9 = 40 + GameUtil.randomInt(20);
                player2.botWeaponItemId = n;
                player2.getEquipmentManager().getContainer().setItem(0, new ItemStack(2910));
                player2.getEquipmentManager().getContainer().setItem(7, new ItemStack(n7));
                player2.getEquipmentManager().getContainer().setItem(4, new ItemStack(1129));
                player2.getEquipmentManager().getContainer().setItem(9, new ItemStack(n8));
                player2.getEquipmentManager().getContainer().setItem(3, new ItemStack(player2.botWeaponItemId, n9));
                player2.getEquipmentManager().getContainer().setItem(10, new ItemStack(1061));
                player.getEquipmentManager().getContainer().setItem(2, new ItemStack(1725));
                if (GameUtil.randomInt(3) != 0) break;
                player.getInventoryManager().addItem(new ItemStack(BotCombatLoadoutManager.selectSpecialAttackWeapon(player), 1));
                break;
            }
            case 1: 
            case 5: {
                int n10;
                int n11;
                player.botActiveCombatStyle = player.botPrimaryCombatStyle = BotPvpCombatHandler.RANGED_COMBAT_STYLE;
                BotCombatLoadoutManager.prepareRangedLoadout(player);
                int n12 = player.getSkillManager().getCurrentLevels()[4] >= 40 ? 1731 : (n11 = GameUtil.randomInt(3) == 0 ? 1478 : 1729);
                if (!BotCombatHelper.isFreeToPlayWorld() && player.getCombatLevel() >= 60 && GameUtil.randomInt(2) == 0) {
                    n10 = 1712;
                }
                player.getEquipmentManager().getContainer().setItem(2, new ItemStack(n10));
            }
        }
        if (bl2) {
            BotCombatLoadoutManager.equipRandomCape(player);
        }
        BotCombatLoadoutManager.addCombatSupplies(player);
        player.getInventoryManager().refresh();
        player.getEquipmentManager().refresh();
    }
}

