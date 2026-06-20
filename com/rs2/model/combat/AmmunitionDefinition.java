/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat;

import com.rs2.bot.combat.BotCombatEscapeHandler;
import com.rs2.model.combat.AmmunitionProfile;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;

public enum AmmunitionDefinition {
    BRONZE_ARROW(10, 19, 1104, 7),
    IRON_ARROW(9, 18, 1105, 10),
    STEEL_ARROW(11, 20, 1106, 16),
    MITHRIL_ARROW(12, 21, 1107, 22),
    ADAMANT_ARROW(13, 22, 1108, 31),
    RUNE_ARROW(15, 24, 1109, 49),
    DRAGON_ARROW(1120, 1116, 1111, 60),
    CRYSTAL_BOW_ARROW(249, 250, 49),
    ICE_ARROWS(16, 25, 1110, 16),
    BRONZE_BRUTAL(15, 24, 11),
    IRON_BRUTAL(9, 18, 13),
    STEEL_BRUTAL(11, 20, 19),
    MITHRIL_BRUTAL(12, 21, 34),
    ADAMANT_BRUTAL(13, 22, 45),
    RUNE_BRUTAL(15, 24, 60),
    BRONZE_FIRE_ARROW(17, 26, 7),
    IRON_FIRE_ARROW(17, 26, 10),
    STEEL_FIRE_ARROW(17, 26, 16),
    MITHRIL_FIRE_ARROW(17, 26, 22),
    ADAMANT_FIRE_ARROW(17, 26, 31),
    RUNE_FIRE_ARROW(17, 26, 49),
    OGRE_ARROW(242, 243, 22),
    BROAD_ARROW(326, 325, 1112, 28),
    BRONZE_KNIFE(212, 219, 3),
    IRON_KNIFE(213, 220, 4),
    STEEL_KNIFE(214, 221, 7),
    BLACK_KNIFE(215, 222, 8),
    MITHRIL_KNIFE(216, 223, 10),
    ADAMANT_KNIFE(217, 224, 14),
    RUNE_KNIFE(218, 225, 24),
    BRONZE_THROWNAXE(35, 43, 5),
    IRON_THROWNAXE(36, 42, 7),
    STEEL_THROWNAXE(37, 44, 11),
    MITHRIL_THROWNAXE(38, 45, 16),
    ADAMANT_THROWNAXE(39, 46, 23),
    RUNE_THROWNAXE(41, 48, 26),
    BRONZE_DART(226, 232, 31),
    IRON_DART(227, 233, 3),
    STEEL_DART(228, 234, 4),
    BLACK_DART(34, 273, 6),
    MITHRIL_DART(229, 235, 7),
    ADAMANT_DART(230, 236, 10),
    RUNE_DART(231, 237, 14),
    BRONZE_JAVELIN(200, 206, 6),
    IRON_JAVELIN(201, 207, 10),
    STEEL_JAVELIN(202, 208, 12),
    MITHRIL_JAVELIN(203, 209, 18),
    ADAMANT_JAVELIN(204, 210, 28),
    RUNE_JAVELIN(205, 211, 42),
    BOLT_RACK(27, 28, 55),
    BOLT(27, 28, 10),
    BARBED_BOLTS(27, 28, 12),
    OPAL_BOLTS(27, 28, 14),
    PEARL_BOLTS(27, 28, 48),
    IRON_BOLTS(27, 28, 46),
    STEEL_BOLTS(27, 28, 64),
    MITHRIL_BOLTS(27, 28, 82),
    ADAMANT_BOLTS(27, 28, 100),
    RUNITE_BOLTS(27, 28, 115),
    DRAGON_BOLTS_E(27, 28, 117),
    TOKTZ(442, -1, 49);

    static AmmunitionDefinition[] ARROWS;
    static AmmunitionDefinition[] KNIVES;
    static AmmunitionDefinition[] DARTS;
    static AmmunitionDefinition[] BRUTAL_ARROWS;
    static AmmunitionDefinition[] THROWNAXES;
    static AmmunitionDefinition[] JAVELINS;
    private final int projectileId;
    private final int graphicId;
    private final int rangedStrength;
    private int alternateGraphicId = -1;

    static {
        ARROWS = new AmmunitionDefinition[]{BRONZE_ARROW, IRON_ARROW, STEEL_ARROW, MITHRIL_ARROW, ADAMANT_ARROW, RUNE_ARROW, DRAGON_ARROW, ICE_ARROWS, BROAD_ARROW};
        KNIVES = new AmmunitionDefinition[]{BRONZE_KNIFE, IRON_KNIFE, STEEL_KNIFE, BLACK_KNIFE, MITHRIL_KNIFE, ADAMANT_KNIFE, RUNE_KNIFE};
        DARTS = new AmmunitionDefinition[]{BRONZE_DART, IRON_DART, STEEL_DART, BLACK_DART, MITHRIL_DART, ADAMANT_DART, RUNE_DART};
        BRUTAL_ARROWS = new AmmunitionDefinition[]{BRONZE_BRUTAL, IRON_BRUTAL, STEEL_BRUTAL, MITHRIL_BRUTAL, ADAMANT_BRUTAL, RUNE_BRUTAL};
        THROWNAXES = new AmmunitionDefinition[]{BRONZE_THROWNAXE, IRON_THROWNAXE, STEEL_THROWNAXE, MITHRIL_THROWNAXE, ADAMANT_THROWNAXE, RUNE_THROWNAXE};
        JAVELINS = new AmmunitionDefinition[]{BRONZE_JAVELIN, IRON_JAVELIN, STEEL_JAVELIN, MITHRIL_JAVELIN, ADAMANT_JAVELIN, RUNE_JAVELIN};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private AmmunitionDefinition(int n3) {
        void var5_3;
        void var4_2;
        this.graphicId = var4_2;
        this.projectileId = n3;
        this.rangedStrength = var5_3;
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private AmmunitionDefinition(int n3, int n4) {
        void var6_4;
        void var5_3;
        this.graphicId = n4;
        this.alternateGraphicId = var5_3;
        this.projectileId = n3;
        this.rangedStrength = var6_4;
    }

    public final int getRangedStrength() {
        return this.rangedStrength;
    }

    public final int getProjectileId() {
        return this.projectileId;
    }

    public final int getGraphicId() {
        return this.graphicId;
    }

    public final int getAlternateGraphicId() {
        return this.alternateGraphicId;
    }

    public static boolean isCompatible(Player object, ItemStack itemStack, ItemStack itemStack2) {
        WeaponProfile weaponProfile = WeaponProfile.forItem(itemStack);
        Object object2 = weaponProfile.getAmmunitionProfile();
        if (object2 == null) {
            return false;
        }
        if (itemStack2 == null) {
            return false;
        }
        if (itemStack == null) {
            return false;
        }
        int n = ((AmmunitionProfile)((Object)object2)).getEquipmentSlot();
        String string = ItemDefinition.forId(itemStack2.getId()).getName().toLowerCase().replaceAll(" ", "_").replaceAll("\\(", "").replaceAll("\\)", "");
        object2 = ((AmmunitionProfile)((Object)object2)).getAllowedAmmunition();
        AmmunitionDefinition[] ammunitionDefinitionArray = object2;
        int n2 = ((AmmunitionDefinition[])object2).length;
        int n3 = 0;
        while (n3 < n2) {
            object2 = ammunitionDefinitionArray[n3];
            boolean bl = false;
            if (weaponProfile == WeaponProfile.METAL_CROSSBOW) {
                bl = true;
            }
            if (string.contains(((Enum)object2).name().toLowerCase()) && !bl || bl && string.startsWith(((Enum)object2).name().toLowerCase())) {
                if (n != 3) {
                    int n4;
                    if (string.contains("ogre")) {
                        n4 = ((String)(object = ((Player)object).getWeaponProfile().name().toLowerCase())).contains("ogre") ? 0 : 1;
                    } else {
                        n4 = new ItemStack(itemStack.getId()).getDefinition().getRequiredLevel(4);
                        int n5 = new ItemStack(itemStack2.getId()).getDefinition().getRequiredLevel(4);
                        int n6 = n4 = n5 > n4 ? 1 : 0;
                    }
                    if (n4 != 0) {
                        return false;
                    }
                }
                return true;
            }
            ++n3;
        }
        return false;
    }

    public static AmmunitionDefinition findEquippedAmmunition(Player player, WeaponProfile object, boolean bl) {
        Object object2 = ((WeaponProfile)((Object)object)).getAmmunitionProfile();
        if (object2 == null) {
            object = player;
            ((Player)object).packetSender.sendGameMessage("That weapon is not configured properly, please report to staff!");
            return null;
        }
        int n = ((AmmunitionProfile)((Object)object2)).getEquipmentSlot();
        ItemStack itemStack = player.getEquipmentManager().getContainer().getItemAt(n);
        int n2 = 0;
        if (itemStack != null) {
            n2 = itemStack.getAmount();
        }
        if (itemStack == null || object == WeaponProfile.DARK_BOW && n2 < 2) {
            if (object == WeaponProfile.DARK_BOW && n2 < 2) {
                object = player;
                ((Player)object).packetSender.sendGameMessage("You don't have enough ammo left.");
            } else {
                object = player;
                ((Player)object).packetSender.sendGameMessage("You have no ammo left!");
            }
            if (player.botEnabled) {
                if (player.isInWilderness()) {
                    BotCombatEscapeHandler.a(player);
                } else if (player.currentBotTask != null) {
                    player.botCombatState = "escape";
                }
            }
            return null;
        }
        String string = ItemDefinition.forId(itemStack.getId()).getName().toLowerCase().replaceAll(" ", "_").replaceAll("\\(", "").replaceAll("\\)", "");
        object2 = ((AmmunitionProfile)((Object)object2)).getAllowedAmmunition();
        AmmunitionDefinition[] ammunitionDefinitionArray = object2;
        int n3 = ((AmmunitionDefinition[])object2).length;
        int n4 = 0;
        while (n4 < n3) {
            object2 = ammunitionDefinitionArray[n4];
            boolean bl2 = false;
            if (object == WeaponProfile.METAL_CROSSBOW) {
                bl2 = true;
            }
            if (string.contains(((Enum)object2).name().toLowerCase()) && !bl2 || bl2 && string.startsWith(((Enum)object2).name().toLowerCase())) {
                if (n != 3) {
                    int n5;
                    object = player.getEquipmentManager().getContainer().getItemAt(3);
                    if (object == null) {
                        return null;
                    }
                    if (string.contains("ogre")) {
                        object = player.getWeaponProfile().name().toLowerCase();
                        n5 = ((String)object).contains("ogre") ? 0 : 1;
                    } else {
                        n5 = new ItemStack(((ItemStack)object).getId()).getDefinition().getRequiredLevel(4);
                        n = new ItemStack(itemStack.getId()).getDefinition().getRequiredLevel(4);
                        int n6 = n5 = n > n5 ? 1 : 0;
                    }
                    if (n5 != 0) {
                        Player player2 = player;
                        player2.packetSender.sendGameMessage("You cannot use that ammo with that weapon!");
                        return null;
                    }
                }
                return object2;
            }
            ++n4;
        }
        object = player;
        ((Player)object).packetSender.sendGameMessage("You can not use that kind of ammo!");
        return null;
    }
}

