/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat;

import com.rs2.model.Position;
import com.rs2.model.combat.AttackBonusType;
import com.rs2.model.combat.AttackXpMode;
import com.rs2.model.combat.CombatType;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.LoadedWorldObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.objects.WorldObject;
import com.rs2.model.objects.WorldObjectLookup;
import com.rs2.model.objects.functions.DelayedObjectMoveEvent;
import com.rs2.model.objects.functions.ObjectToggleEvent;
import com.rs2.model.objects.functions.WebSlashEvent;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public class AttackStyleDefinition {
    private AttackXpMode xpMode;
    private AttackBonusType attackBonusType;
    private int buttonId;
    private CombatType combatType;

    public static void a(Player player, Position position) {
        player.n(true);
        player.getUpdateState().setAnimation(828);
        Player player2 = player;
        player2.packetSender.closeInterfaces();
        CycleEventHandler.getInstance().schedule(player, new DelayedObjectMoveEvent(player, position), 2);
    }

    public static void a(Player player, String string) {
        WorldObject worldObject = SkillActionHelper.findWorldObjectById(player.getInteractionTargetId(), player.getInteractionTargetX(), player.getInteractionTargetY(), player.getPosition().getPlane());
        if (worldObject == null) {
            return;
        }
        int n = player.getPosition().getPlane();
        int n2 = string.equalsIgnoreCase("up") ? n + 1 : (n - 1 <= 0 ? 0 : n - 1);
        AttackStyleDefinition.a(player, new Position(player.getPosition().getX(), player.getPosition().getY(), n2));
    }

    public static void a(Player player, String string, int n) {
        WorldObject worldObject = SkillActionHelper.findWorldObjectById(player.getInteractionTargetId(), player.getInteractionTargetX(), player.getInteractionTargetY(), player.getPosition().getPlane());
        if (worldObject == null) {
            return;
        }
        int n2 = player.getPosition().getPlane();
        int n3 = string.equalsIgnoreCase("up") ? n2 + 2 : (n2 - 2 < 2 ? 0 : n2 - 2);
        AttackStyleDefinition.a(player, new Position(player.getPosition().getX(), player.getPosition().getY(), n3));
    }

    public static void a(Player player, int n, int n2, String string) {
        WorldObject worldObject = SkillActionHelper.findWorldObjectById(player.getInteractionTargetId(), player.getInteractionTargetX(), player.getInteractionTargetY(), player.getPosition().getPlane());
        if (worldObject == null) {
            return;
        }
        int n3 = worldObject.getOrientation();
        int n4 = player.getPosition().getPlane();
        int n5 = string.equalsIgnoreCase("up") ? n4 + 1 : (n4 = n4 - 1 <= 0 ? 0 : n4 - 1);
        int n6 = n3 == 1 ? (string.equalsIgnoreCase("up") ? n : -n) : (n3 == 3 ? (string.equalsIgnoreCase("up") ? -n : n) : (n = 0));
        n2 = n3 == 0 ? (string.equalsIgnoreCase("up") ? n2 : -n2) : (n3 == 2 ? (string.equalsIgnoreCase("up") ? -n2 : n2) : 0);
        player.moveTo(new Position(player.getPosition().getX() + n, player.getPosition().getY() + n2, n4));
    }

    public static void b(Player player, int n, int n2, String string) {
        int n3;
        WorldObject worldObject = SkillActionHelper.findWorldObjectById(player.getInteractionTargetId(), player.getInteractionTargetX(), player.getInteractionTargetY(), player.getPosition().getPlane());
        if (worldObject == null) {
            return;
        }
        int n4 = worldObject.getOrientation();
        n2 = player.getPosition().getPlane();
        int n5 = string.equalsIgnoreCase("up") ? n2 + 1 : (n2 = n2 - 1 <= 0 ? 0 : n2 - 1);
        int n6 = n4 == 1 ? (string.equalsIgnoreCase("up") ? -4 : 4) : (n4 == 3 ? (string.equalsIgnoreCase("up") ? 4 : -4) : (n3 = 0));
        n4 = n4 == 0 ? (string.equalsIgnoreCase("up") ? -4 : 4) : (n4 == 2 ? (string.equalsIgnoreCase("up") ? 4 : -4) : 0);
        player.moveTo(new Position(player.getPosition().getX() + n3, player.getPosition().getY() + n4, n2));
    }

    /*
     * Unable to fully structure code
     */
    public static void b(Player var0, String var1_1) {
        var2_2 = SkillActionHelper.findWorldObjectById(var0.getInteractionTargetId(), var0.getInteractionTargetX(), var0.getInteractionTargetY(), var0.getPosition().getPlane());
        if (var2_2 == null) {
            return;
        }
        var3_3 = var2_2.getOrientation();
        var4_4 = var0.getPosition().getPlane();
        v0 = var1_1.equalsIgnoreCase("up") != false ? var4_4 + 1 : (var4_4 = var4_4 - 1 <= 0 ? 0 : var4_4 - 1);
        var5_5 = (var3_3 == 0 ? 1 : (var3_3 == 1 ? 1 : -1)) * (var1_1.equalsIgnoreCase("up") != false ? 1 : -1);
        if (var3_3 == 0) ** GOTO lbl-1000
        if (var3_3 == 1) {
            v1 = -1;
        } else if (var3_3 == 2) {
            v1 = -1;
        } else lbl-1000:
        // 2 sources

        {
            v1 = 1;
        }
        var3_3 = v1 * (var1_1.equalsIgnoreCase("up") != false ? 1 : -1);
        if (var2_2.getObjectId() == 1738 || var2_2.getObjectId() == 1739 || var2_2.getObjectId() == 1740) {
            var3_3 = 0;
            var5_5 = 0;
        }
        if (var0.getInteractionTargetId() == 1738 && var1_1.equals("up") && var0.getInteractionTargetX() == 2673 && var0.getInteractionTargetY() == 3300) {
            var0.moveTo(new Position(2675, 3300, var4_4));
            return;
        }
        var0.moveTo(new Position(var0.getPosition().getX() + var5_5, var0.getPosition().getY() + var3_3, var4_4));
    }

    public static void c(Player player, int n, int n2, String string) {
        WorldObject worldObject = SkillActionHelper.findWorldObjectById(player.getInteractionTargetId(), player.getInteractionTargetX(), player.getInteractionTargetY(), player.getPosition().getPlane());
        if (worldObject == null) {
            return;
        }
        int n3 = worldObject.getOrientation();
        int n4 = n3 == 1 ? (string.equalsIgnoreCase("up") ? 4 : -4) : (n3 == 3 ? (string.equalsIgnoreCase("up") ? -4 : 4) : (n2 = 0));
        n3 = n3 == 0 ? (string.equalsIgnoreCase("up") ? 4 : -4) : (n3 == 2 ? (string.equalsIgnoreCase("up") ? -4 : 4) : 0);
        player.moveTo(new Position(player.getPosition().getX() + n2, player.getPosition().getY() + n3 + (string.equalsIgnoreCase("up") ? -6400 : 6400), 0));
    }

    public AttackStyleDefinition(CombatType combatType, AttackXpMode attackXpMode, AttackBonusType attackBonusType) {
        this(combatType, -1, attackXpMode, attackBonusType);
    }

    public AttackStyleDefinition(CombatType combatType, int n, AttackXpMode attackXpMode, AttackBonusType attackBonusType) {
        this.combatType = combatType;
        this.buttonId = n;
        this.xpMode = attackXpMode;
        this.attackBonusType = attackBonusType;
    }

    public CombatType getCombatType() {
        return this.combatType;
    }

    public int getButtonId() {
        return this.buttonId;
    }

    public AttackXpMode getXpMode() {
        return this.xpMode;
    }

    public AttackBonusType getAttackBonusType() {
        return this.attackBonusType;
    }

    public int getAttackBonusIndex() {
        return this.getAttackBonusType().getIndex();
    }

    public int getDefenseBonusIndex() {
        return this.getAttackBonusType().getIndex() + AttackBonusType.values().length;
    }

    public static void a(Player player, int n, int n2, WorldObject worldObject) {
        int n3 = worldObject.getOrientation();
        int n4 = worldObject.getType();
        player.n(true);
        player.getUpdateState().setAnimation(827);
        CycleEventHandler.getInstance().schedule(player, new ObjectToggleEvent(n, worldObject, n4, n2, n3, player), 2);
    }

    public static void a(Player player, int n, int n2, int n3) {
        ObjectManager.getInstance();
        Object object = ObjectManager.findDynamicObjectByIdAt(733, n, n2, 0);
        if (object != null && ((DynamicObject)object).getWorldObject().getObjectId() != 733) {
            return;
        }
        object = new ItemStack(n3);
        ArrayList<Integer> arrayList = ((ItemStack)object).getDefinition().getName().toLowerCase();
        int n4 = ((ItemStack)object).getDefinition().getBonus(1);
        if (!((String)((Object)arrayList)).contains("knife") && n4 <= 0) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("You need a sharp weapon to slash through this.");
            if (player.botEnabled && player.currentBotTask != null) {
                player.botTaskReturnToBankRequested = true;
                player.moveTo(player.currentBotTask.getStartPosition());
                player.botTaskState = "empty inventory";
                if (player.currentBotTask.usesDepositBox) {
                    player.botInteractionOption = 2;
                    arrayList = new ArrayList<Integer>();
                    arrayList.add(2619);
                    player.interactWithBotNpcTargets(arrayList);
                    return;
                }
                player.botInteractionOption = 2;
                arrayList = new ArrayList<Integer>();
                arrayList.add(2213);
                arrayList.add(11758);
                player.interactWithBotObjectTargets(arrayList);
            }
            return;
        }
        player.nextActionSequence();
        player.n(true);
        player.getUpdateState().setAnimation(451);
        arrayList = WorldObjectLookup.findObjectByIdAt(733, n, n2, 0);
        int n5 = ((LoadedWorldObject)((Object)arrayList)).getOrientation();
        double d = 1.0;
        if (n3 == 946) {
            d = 0.5;
        } else if (n4 >= 100) {
            d = 1.0;
        } else if (n4 < 20) {
            d = 0.2;
        } else if (n4 >= 20) {
            WeaponProfile weaponProfile = WeaponProfile.forItem((ItemStack)object);
            double d2 = weaponProfile == WeaponProfile.LONGSWORD || weaponProfile == WeaponProfile.SCIMITAR ? 0.5 : 0.2;
            double d3 = 1.0 - d2;
            double d4 = n4 - 20;
            double d5 = d3 / 80.0 * d4;
            d = d2 + d5;
        }
        Boolean bl = GameUtil.a(d);
        CycleEventHandler.getInstance().schedule(player, new WebSlashEvent(bl, player, n, n2, n5), 2);
    }
}

