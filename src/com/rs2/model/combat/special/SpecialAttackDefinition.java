/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat.special;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.EntityTargetMovement;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.AttackValidationResult;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.combat.CombatCycleEvent;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.combat.special.AbyssalWhipSpecialDefinition;
import com.rs2.model.combat.special.ArmadylGodswordSpecialDefinition;
import com.rs2.model.combat.special.BandosGodswordSpecialDefinition;
import com.rs2.model.combat.special.DarkBowSpecialDefinition;
import com.rs2.model.combat.special.DarklightSpecialDefinition;
import com.rs2.model.combat.special.Dragon2hSwordSpecialDefinition;
import com.rs2.model.combat.special.DragonAxeSpecialDefinition;
import com.rs2.model.combat.special.DragonBattleaxeSpecialDefinition;
import com.rs2.model.combat.special.DragonDaggerSpecialDefinition;
import com.rs2.model.combat.special.DragonHalberdSpecialDefinition;
import com.rs2.model.combat.special.DragonLongswordSpecialDefinition;
import com.rs2.model.combat.special.DragonMaceSpecialDefinition;
import com.rs2.model.combat.special.DragonScimitarSpecialDefinition;
import com.rs2.model.combat.special.DragonSpearSpecialDefinition;
import com.rs2.model.combat.special.ExcaliburSpecialDefinition;
import com.rs2.model.combat.special.GraniteMaulSpecialDefinition;
import com.rs2.model.combat.special.MagicLongbowSpecialDefinition;
import com.rs2.model.combat.special.MagicShortbowSpecialDefinition;
import com.rs2.model.combat.special.RuneClawsSpecialDefinition;
import com.rs2.model.combat.special.RuneThrownaxeSpecialDefinition;
import com.rs2.model.combat.special.SaradominGodswordSpecialDefinition;
import com.rs2.model.combat.special.SaradominSwordSpecialDefinition;
import com.rs2.model.combat.special.SeercullSpecialDefinition;
import com.rs2.model.combat.special.ZamorakGodswordSpecialDefinition;
import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;

public abstract class SpecialAttackDefinition
extends Enum {
    private static /* enum */ SpecialAttackDefinition DRAGON_DAGGER = new DragonDaggerSpecialDefinition(25, "drag dagger", "dragon dagger");
    private static /* enum */ SpecialAttackDefinition ABYSSAL_WHIP = new AbyssalWhipSpecialDefinition(50, new String[0]);
    private static /* enum */ SpecialAttackDefinition RUNE_THROWNAXE = new RuneThrownaxeSpecialDefinition(10, new String[0]);
    private static /* enum */ SpecialAttackDefinition DRAGON_LONGSWORD = new DragonLongswordSpecialDefinition(25, new String[0]);
    private static /* enum */ SpecialAttackDefinition DRAGON_SCIMITAR = new DragonScimitarSpecialDefinition(55, new String[0]);
    private static /* enum */ SpecialAttackDefinition RUNE_CLAWS = new RuneClawsSpecialDefinition(25, new String[0]);
    private static /* enum */ SpecialAttackDefinition DRAGON_MACE = new DragonMaceSpecialDefinition(25, new String[0]);
    private static /* enum */ SpecialAttackDefinition DRAGON_AXE = new DragonAxeSpecialDefinition(100, new String[0]);
    private static /* enum */ SpecialAttackDefinition DARKLIGHT = new DarklightSpecialDefinition(50, new String[0]);
    private static /* enum */ SpecialAttackDefinition DRAGON_SPEAR = new DragonSpearSpecialDefinition(25, "dragon spear", "zamorakian spear");
    private static /* enum */ SpecialAttackDefinition DRAGON_HALBERD = new DragonHalberdSpecialDefinition(30, new String[0]);
    private static /* enum */ SpecialAttackDefinition DRAGON_2H_SWORD = new Dragon2hSwordSpecialDefinition(60, new String[0]);
    private static /* enum */ SpecialAttackDefinition SEERCULL = new SeercullSpecialDefinition(100, new String[0]);
    private static /* enum */ SpecialAttackDefinition DARK_BOW = new DarkBowSpecialDefinition(55, new String[0]);
    private static /* enum */ SpecialAttackDefinition MAGIC_SHORTBOW = new MagicShortbowSpecialDefinition(ServerSettings.cacheVersion < 296 ? 35 : 55, new String[0]);
    private static /* enum */ SpecialAttackDefinition MAGIC_LONGBOW = new MagicLongbowSpecialDefinition(35, new String[0]);
    private static /* enum */ SpecialAttackDefinition DRAGON_BATTLEAXE = new DragonBattleaxeSpecialDefinition(100, new String[0]);
    private static /* enum */ SpecialAttackDefinition EXCALIBUR = new ExcaliburSpecialDefinition(100, new String[0]);
    private static /* enum */ SpecialAttackDefinition BANDOS_GODSWORD = new BandosGodswordSpecialDefinition(50, new String[0]);
    private static /* enum */ SpecialAttackDefinition ARMADYL_GODSWORD = new ArmadylGodswordSpecialDefinition(50, new String[0]);
    private static /* enum */ SpecialAttackDefinition ZAMORAK_GODSWORD = new ZamorakGodswordSpecialDefinition(50, new String[0]);
    private static /* enum */ SpecialAttackDefinition SARADOMIN_GODSWORD = new SaradominGodswordSpecialDefinition(50, new String[0]);
    private static /* enum */ SpecialAttackDefinition SARADOMIN_SWORD = new SaradominSwordSpecialDefinition(100, new String[0]);
    private static /* enum */ SpecialAttackDefinition GRANITE_MAUL = new GraniteMaulSpecialDefinition(50, new String[0]);
    private byte energyCost;
    private String[] weaponNamePatterns;
    private static final /* synthetic */ SpecialAttackDefinition[] VALUES;

    static {
        VALUES = new SpecialAttackDefinition[]{DRAGON_DAGGER, ABYSSAL_WHIP, RUNE_THROWNAXE, DRAGON_LONGSWORD, DRAGON_SCIMITAR, RUNE_CLAWS, DRAGON_MACE, DRAGON_AXE, DARKLIGHT, DRAGON_SPEAR, DRAGON_HALBERD, DRAGON_2H_SWORD, SEERCULL, DARK_BOW, MAGIC_SHORTBOW, MAGIC_LONGBOW, DRAGON_BATTLEAXE, EXCALIBUR, BANDOS_GODSWORD, ARMADYL_GODSWORD, ZAMORAK_GODSWORD, SARADOMIN_GODSWORD, SARADOMIN_SWORD, GRANITE_MAUL};
    }

    /*
     * WARNING - void declaration
     */
    private SpecialAttackDefinition() {
        void var4_1;
        void var3_2;
        void cfr_renamed_2;
        void cfr_renamed_1;
        this.energyCost = (byte)var3_2;
        this.weaponNamePatterns = var4_1;
        if (this.weaponNamePatterns == null || this.weaponNamePatterns.length == 0) {
            this.weaponNamePatterns = new String[]{this.name().toLowerCase().replaceAll("_", " ")};
        }
    }

    public abstract WeaponCombatAttack createAttack(Player var1, Entity var2, WeaponProfile var3);

    public final byte getEnergyCost() {
        return this.energyCost;
    }

    public static SpecialAttackDefinition forItem(ItemStack object) {
        if (object == null) {
            return null;
        }
        object = ItemDefinition.forId(((ItemStack)object).getId()).getName().toLowerCase();
        SpecialAttackDefinition[] specialAttackDefinitionArray = SpecialAttackDefinition.values();
        int n = specialAttackDefinitionArray.length;
        int n2 = 0;
        while (n2 < n) {
            SpecialAttackDefinition specialAttackDefinition = specialAttackDefinitionArray[n2];
            String[] stringArray = specialAttackDefinition.weaponNamePatterns;
            int n3 = specialAttackDefinition.weaponNamePatterns.length;
            int n4 = 0;
            while (n4 < n3) {
                String string = stringArray[n4];
                if (((String)object).contains(string.replace("_", " "))) {
                    return specialAttackDefinition;
                }
                ++n4;
            }
            ++n2;
        }
        return null;
    }

    public static void performDragonBattleaxeSpecial(Player player) {
        if (DuelRule.d.a(player)) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("Special attacks have been disabled during this fight!");
            return;
        }
        if (player.getSpecialEnergy() < 100) {
            Player player3 = player;
            player3.packetSender.sendGameMessage("You don't have enough special attack to do that.");
            return;
        }
        player.setSpecialAttackEnabled(!player.isSpecialAttackEnabled());
        Player player4 = player;
        player4.packetSender.refreshSpecialAttackConfig();
        player.getUpdateState().setGraphic(246);
        player.getUpdateState().setAnimation(1056);
        player4 = player;
        player4.packetSender.sendSoundEffect(389, 1, 0);
        player.getUpdateState().setForcedText("Raarrrrrgggggghhhhhhh!");
        player4 = player;
        player4.packetSender.modifySkillLevel(2, (int)((double)player.getSkillManager().getBaseLevel(2) * 0.2), true);
        player4 = player;
        player4.packetSender.modifySkillLevel(0, -((int)((double)player.getSkillManager().getBaseLevel(0) * 0.1)), true);
        player4 = player;
        player4.packetSender.modifySkillLevel(1, -((int)((double)player.getSkillManager().getBaseLevel(1) * 0.1)), true);
        player4 = player;
        player4.packetSender.modifySkillLevel(4, -((int)((double)player.getSkillManager().getBaseLevel(4) * 0.1)), true);
        player4 = player;
        player4.packetSender.modifySkillLevel(6, -((int)((double)player.getSkillManager().getBaseLevel(6) * 0.1)), true);
        player.setSpecialAttackEnabled(false);
        player.setSpecialEnergy(0);
        player4 = player;
        player4.packetSender.refreshSpecialEnergyBar(7511);
        player4 = player;
        player4.packetSender.refreshSpecialAttackConfig();
    }

    public static void performExcaliburSpecial(Player player) {
        if (DuelRule.d.a(player)) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("Special attacks have been disabled during this fight!");
            return;
        }
        if (player.getSpecialEnergy() < 100) {
            Player player3 = player;
            player3.packetSender.sendGameMessage("You don't have enough special attack to do that.");
            return;
        }
        player.setSpecialAttackEnabled(!player.isSpecialAttackEnabled());
        Player player4 = player;
        player4.packetSender.refreshSpecialAttackConfig();
        player.getUpdateState().setGraphic(247);
        player.getUpdateState().setAnimation(1057);
        player4 = player;
        player4.packetSender.sendSoundEffect(384, 1, 0);
        player.getUpdateState().setForcedText("For Camelot!");
        player4 = player;
        player4.packetSender.modifySkillLevel(1, 8, true);
        player.setSpecialAttackEnabled(false);
        player.setSpecialEnergy(0);
        player4 = player;
        player4.packetSender.refreshSpecialEnergyBar(7611);
        player4 = player;
        player4.packetSender.refreshSpecialAttackConfig();
    }

    public static void performGraniteMaulSpecial(Player player) {
        if (player.getCombatTarget().isDead() || !EntityTargetMovement.canReachTarget(player, player.getCombatTarget())) {
            return;
        }
        if (player.getCombatTarget().isDoorSupportNpc()) {
            return;
        }
        Object object = CombatCycleEvent.validateAttack(player, player.getCombatTarget());
        if (object != AttackValidationResult.VALID) {
            return;
        }
        if (DuelRule.d.a(player)) {
            object = player;
            ((Player)object).packetSender.sendGameMessage("Special attacks have been disabled during this fight!");
            return;
        }
        if (player.getSpecialEnergy() < 50) {
            object = player;
            ((Player)object).packetSender.sendGameMessage("You don't have enough special attack to do that.");
            return;
        }
        player.setSpecialAttackEnabled(!player.isSpecialAttackEnabled());
        object = player;
        ((Player)object).packetSender.refreshSpecialAttackConfig();
        player.getUpdateState().setAnimation(1667);
        player.getUpdateState().setGraphic(new GraphicEffect(337, 100));
        object = new WeaponCombatAttack(player, player.getCombatTarget(), player.getWeaponProfile());
        object = new HitDefinition(((WeaponCombatAttack)object).getAttackStyle(), HitType.NORMAL, CombatManager.calculateMeleeMaxHit(player, (WeaponCombatAttack)object)).enableRandomDamage().enableAccuracyCheck(true);
        new CombatAction(player, player.getCombatTarget(), (HitDefinition)object).queue();
        player.setSpecialAttackEnabled(false);
        player.setSpecialEnergy(player.getSpecialEnergy() - 50);
        object = player;
        ((Player)object).packetSender.refreshSpecialEnergyBar(7486);
        object = player;
        ((Player)object).packetSender.refreshSpecialAttackConfig();
    }

    public static void applyHitSpecialEffect(Entity entity, Entity entity2, HitDefinition object, int n) {
        if (entity != null && entity.isPlayer()) {
            Player[] playerArray;
            Object object2 = playerArray = (Player[])entity;
            playerArray.packetSender.closeInterfaces();
            block0 : switch (((HitDefinition)object).getSpecialEffectId()) {
                case 1: {
                    entity2.getUpdateState().setGraphic(474);
                    if (n <= 0 || !entity2.isPlayer()) break;
                    entity = (Player)entity2;
                    object2 = entity;
                    ((Player)entity).packetSender.modifySkillLevel(6, -n, true);
                    return;
                }
                case 2: {
                    if (!entity2.isPlayer()) break;
                    entity = (Player)entity2;
                    ((Player)entity).getPrayerManager().deactivatePrayer(12);
                    ((Player)entity).getPrayerManager().deactivatePrayer(13);
                    ((Player)entity).getPrayerManager().deactivatePrayer(14);
                    ((Player)entity).a(System.currentTimeMillis() + 5000L);
                    object2 = entity;
                    object2.packetSender.sendGameMessage("You have been injured!");
                    return;
                }
                case 3: {
                    if (n <= 0 || !entity2.isPlayer()) break;
                    entity = (Player)entity2;
                    object2 = entity;
                    ((Player)entity).packetSender.modifySkillLevel(1, -n, true);
                    object2 = entity;
                    object2.packetSender.modifySkillLevel(6, -n, true);
                    return;
                }
                case 4: {
                    if (n <= 0 || !entity2.isPlayer()) break;
                    entity = (Player)entity2;
                    object2 = entity;
                    ((Player)entity).packetSender.modifySkillLevel(0, -((int)((double)((Player)entity).getSkillManager().getBaseLevel(0) * 0.05)), true);
                    object2 = entity;
                    object2.packetSender.modifySkillLevel(2, -((int)((double)((Player)entity).getSkillManager().getBaseLevel(2) * 0.05)), true);
                    object2 = entity;
                    object2.packetSender.modifySkillLevel(1, -((int)((double)((Player)entity).getSkillManager().getBaseLevel(1) * 0.05)), true);
                    return;
                }
                case 5: {
                    int n2 = playerArray.getPosition().getX();
                    int n3 = playerArray.getPosition().getY();
                    n = 0;
                    int n4 = 0;
                    int n5 = entity2.getPosition().getX();
                    int n6 = entity2.getPosition().getY();
                    if (n2 > n5) {
                        n = -1;
                    } else if (n2 < n5) {
                        n = 1;
                    }
                    if (n3 > n6) {
                        n4 = -1;
                    } else if (n3 < n6) {
                        n4 = 1;
                    }
                    n2 = entity2.getSize() > 1 ? 1 : 0;
                    entity2.getUpdateState().setGraphicHeight100(254);
                    if (n2 != 0) {
                        if (entity2.isPlayer()) {
                            Player player;
                            Player player2 = player = (Player)entity2;
                            player.packetSender.queueRelativeMovementStep(n, n4, false);
                        } else {
                            Npc npc = (Npc)entity2;
                            npc.queuePathTo(new Position(npc.getPosition().getX() + n, npc.getPosition().getY() + n4), false);
                        }
                    }
                    entity2.getStunTimer().setDelayTicks(10);
                    entity2.getStunTimer().reset();
                    return;
                }
                case 6: {
                    Object object3;
                    boolean bl = entity.isInMultiCombatArea();
                    int n7 = 0;
                    if (!bl) break;
                    object2 = World.f();
                    n = ((Player[])object2).length;
                    int n8 = 0;
                    while (n8 < n) {
                        Player player = object2[n8];
                        if (player != null && player != entity && player != entity.getCombatTarget()) {
                            object3 = CombatCycleEvent.validateAttack(entity, player);
                            if (GameUtil.b(entity.getPosition(), player.getPosition()) <= 1 && object3 == AttackValidationResult.VALID) {
                                object3 = new WeaponCombatAttack((Player)playerArray, player, playerArray.getWeaponProfile());
                                object3 = new HitDefinition(((WeaponCombatAttack)object3).getAttackStyle(), HitType.NORMAL, CombatManager.calculateMeleeMaxHit((Player)playerArray, (WeaponCombatAttack)object3)).enableRandomDamage().enableAccuracyCheck(true);
                                new CombatAction(entity, player, (HitDefinition)object3).queue();
                                if (++n7 > 13) break;
                            }
                        }
                        ++n8;
                    }
                    object2 = World.g();
                    n = ((Npc[])object2).length;
                    n8 = 0;
                    while (n8 < n) {
                        Player player = object2[n8];
                        if (player != null && player != entity.getCombatTarget()) {
                            object3 = CombatCycleEvent.validateAttack(entity, player);
                            if (entity.isWithinReach(player, 1) && object3 == AttackValidationResult.VALID) {
                                object3 = new WeaponCombatAttack((Player)playerArray, player, playerArray.getWeaponProfile());
                                object3 = new HitDefinition(((WeaponCombatAttack)object3).getAttackStyle(), HitType.NORMAL, CombatManager.calculateMeleeMaxHit((Player)playerArray, (WeaponCombatAttack)object3)).enableRandomDamage().enableAccuracyCheck(true);
                                new CombatAction(entity, player, (HitDefinition)object3).queue();
                                if (++n7 > 13) break block0;
                            }
                        }
                        ++n8;
                    }
                    return;
                }
                case 7: {
                    entity2.getUpdateState().setGraphic(398);
                    entity.heal(n);
                    return;
                }
                case 8: {
                    entity2.getUpdateState().setGraphic(399);
                    if (!entity2.isPlayer()) break;
                    ((Player)entity2).addRunEnergyPercent(-4);
                    object2 = (Player)entity2;
                    object2.packetSender.sendRunEnergy();
                    return;
                }
                case 9: {
                    entity2.getUpdateState().setGraphicHeight100(400);
                    if (!entity2.isPlayer()) break;
                    object2 = (Player)entity2;
                    object2.packetSender.modifySkillLevel(2, -5, false);
                    return;
                }
                case 10: {
                    entity2.getUpdateState().setGraphicHeight100(401);
                    if (!entity2.isPlayer()) break;
                    object2 = (Player)entity2;
                    object2.packetSender.modifySkillLevel(16, -((int)((double)((Player)entity2).getSkillManager().getCurrentLevels()[16] * 0.2)), true);
                    return;
                }
                case 11: {
                    if (n <= 0 || !entity2.isPlayer()) break;
                    object = (Player)entity2;
                    object2 = object;
                    ((Player)object).packetSender.sendGameMessage("You have been drained.");
                    object2 = object;
                    int n9 = object2.packetSender.modifySkillLevelReturningRemainder(1, -n, true);
                    if (n9 <= 0) break;
                    object2 = object;
                    if ((n9 = object2.packetSender.modifySkillLevelReturningRemainder(2, -n9, true)) <= 0) break;
                    object2 = object;
                    if ((n9 = object2.packetSender.modifySkillLevelReturningRemainder(0, -n9, true)) <= 0) break;
                    object2 = object;
                    if ((n9 = object2.packetSender.modifySkillLevelReturningRemainder(5, -n9, true)) <= 0) break;
                    object2 = object;
                    if ((n9 = object2.packetSender.modifySkillLevelReturningRemainder(6, -n9, true)) <= 0) break;
                    object2 = object;
                    object2.packetSender.modifySkillLevelReturningRemainder(4, -n9, true);
                    return;
                }
                case 12: {
                    if (n <= 0) break;
                    int n10 = 10;
                    int n11 = 5;
                    if (n > 21) {
                        n10 = n / 2;
                        n11 = n / 4;
                    }
                    object2 = playerArray;
                    playerArray.packetSender.modifySkillLevelReturningRemainder(3, n10, true);
                    object2 = playerArray;
                    playerArray.packetSender.modifySkillLevelReturningRemainder(5, n11, true);
                    return;
                }
                case 13: {
                    object = new HitDefinition(ServerSettings.SARADOMIN_SWORD_ATTACK_STYLE, HitType.NORMAL, 16).enableRandomDamage().setAccuracyMultiplier(1.0);
                    new CombatAction(entity, entity2, (HitDefinition)object).queue();
                    return;
                }
                case 14: {
                    if (GameUtil.h(100) >= 6) break;
                    n = playerArray.getSkillManager().getCurrentLevels()[4] / 5;
                    object2 = new HitDefinition(ServerSettings.DRAGONFIRE_ATTACK_STYLE, HitType.NORMAL, n).setAccuracyMultiplier(1.0);
                    new CombatAction(entity, entity2, (HitDefinition)object2).queue();
                    entity2.getUpdateState().setGraphic(756);
                }
            }
        }
    }

    public static SpecialAttackDefinition[] values() {
        SpecialAttackDefinition[] specialAttackDefinitionArray = new SpecialAttackDefinition[24];
        System.arraycopy(VALUES, 0, specialAttackDefinitionArray, 0, 24);
        return specialAttackDefinitionArray;
    }

    public static SpecialAttackDefinition valueOf(String string) {
        return Enum.valueOf(SpecialAttackDefinition.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    /* synthetic */ SpecialAttackDefinition(int n, String[] stringArray, byte by) {
        this((String)cfr_renamed_1, (int)stringArray, by, (String[])var4_3);
        void var4_3;
        void cfr_renamed_1;
    }
}

