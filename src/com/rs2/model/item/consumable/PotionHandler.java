/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.item.consumable;

import com.rs2.model.combat.effect.PoisonEffect;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.item.ItemStack;
import com.rs2.model.item.consumable.PotionDefinition;
import com.rs2.model.item.consumable.PotionEffectMode;
import com.rs2.model.player.Player;

public final class PotionHandler {
    private Player d;
    public static PotionDefinition[] a = new PotionDefinition[50];
    public static int b = 0;
    public int c = 0;
    private int e = 0;

    public PotionHandler(Player player) {
        this.d = player;
    }

    public final boolean a(int n) {
        int n2 = 0;
        while (n2 < b) {
            int n3 = 0;
            while (n3 < a[n2].c().length) {
                if (a[n2].c()[n3] == n) {
                    this.c = n2;
                    this.e = n3;
                    return true;
                }
                ++n3;
            }
            ++n2;
        }
        return false;
    }

    public final void a(int n, int n2) {
        if (DuelRule.g.a(this.d)) {
            Player player = this.d;
            player.packetSender.sendGameMessage("Usage of drinks have been disabled during this fight!");
            return;
        }
        if (this.d.getSkillManager().g(600) && !this.d.isDead()) {
            int[] nArray = a[this.c].c();
            Object object = a[this.c].d();
            int[] nArray2 = a[this.c].e();
            double[] dArray = a[this.c].f();
            int n3 = 0;
            while (n3 < ((int[])object).length) {
                int n4;
                int n5;
                int n6;
                int n7;
                if (a[this.c].g() == PotionEffectMode.a) {
                    n7 = object[n3];
                    n6 = this.d.getSkillManager().getCurrentLevels()[n7];
                    n4 = n5 = this.d.getSkillManager().getBaseLevel(n7);
                    n4 = (int)((double)n5 + (double)n5 * dArray[n3]);
                    n4 += nArray2[n3];
                    if (n6 < n5) {
                        int[] nArray3 = this.d.getSkillManager().getCurrentLevels();
                        int n8 = n7;
                        nArray3[n8] = nArray3[n8] + (n4 - n5);
                        this.d.getSkillManager().refreshSkill(n7);
                    } else if (n6 < n4) {
                        this.d.getSkillManager().getCurrentLevels()[n7] = n4;
                        this.d.getSkillManager().refreshSkill(n7);
                    }
                } else if (a[this.c].g() == PotionEffectMode.b) {
                    n7 = object[n3];
                    n6 = this.d.getSkillManager().getCurrentLevels()[n7];
                    n5 = this.d.getSkillManager().getBaseLevel(n7);
                    n4 = (int)((double)n6 + (double)n5 * dArray[n3]);
                    n4 += nArray2[n3];
                    if (n6 <= n5) {
                        if (n4 <= n5) {
                            this.d.getSkillManager().getCurrentLevels()[n7] = n4;
                            this.d.getSkillManager().refreshSkill(n7);
                        } else {
                            this.d.getSkillManager().getCurrentLevels()[n7] = this.d.getSkillManager().getBaseLevel(n7);
                            this.d.getSkillManager().refreshSkill(n7);
                        }
                    }
                }
                ++n3;
            }
            int n9 = n;
            object = this;
            switch (n9) {
                case 175: 
                case 177: 
                case 179: 
                case 2446: {
                    object.d.a(PoisonEffect.class);
                    object.d.getPoisonImmunityTimer().setDelayTicks(150);
                    object.d.getPoisonImmunityTimer().reset();
                    object.d.setPoisonDamage(0.0);
                    break;
                }
                case 3008: 
                case 3010: 
                case 3012: 
                case 3014: {
                    object.d.addRunEnergyPercent(10);
                    break;
                }
                case 181: 
                case 183: 
                case 185: 
                case 2448: {
                    object.d.a(PoisonEffect.class);
                    object.d.getPoisonImmunityTimer().setDelayTicks(600);
                    object.d.getPoisonImmunityTimer().reset();
                    object.d.setPoisonDamage(0.0);
                    break;
                }
                case 3016: 
                case 3018: 
                case 3020: 
                case 3022: {
                    object.d.addRunEnergyPercent(20);
                    break;
                }
                case 5943: 
                case 5945: 
                case 5947: 
                case 5949: {
                    object.d.a(PoisonEffect.class);
                    object.d.getPoisonImmunityTimer().setDelayTicks(900);
                    object.d.getPoisonImmunityTimer().reset();
                    object.d.setPoisonDamage(0.0);
                    break;
                }
                case 2452: 
                case 2454: 
                case 2456: 
                case 2458: {
                    object.d.getAntifireTimer().setDelayTicks(600);
                    object.d.getAntifireTimer().reset();
                    break;
                }
                case 5952: 
                case 5954: 
                case 5956: 
                case 5958: {
                    object.d.a(PoisonEffect.class);
                    object.d.getPoisonImmunityTimer().setDelayTicks(1200);
                    object.d.getPoisonImmunityTimer().reset();
                    object.d.setPoisonDamage(0.0);
                    break;
                }
                case 6685: 
                case 6687: 
                case 6689: 
                case 6691: {
                    object.d.heal(2);
                    Player player = object.d;
                    player.packetSender.modifySkillLevel(3, (int)(2.0 + (double)object.d.getSkillManager().getBaseLevel(3) * 0.15), true);
                    player = object.d;
                    player.packetSender.modifySkillLevel(1, (int)(2.0 + (double)object.d.getSkillManager().getBaseLevel(1) * 0.2), true);
                    player = object.d;
                    player.packetSender.modifySkillLevel(0, (int)(-(2.0 + (double)object.d.getSkillManager().getBaseLevel(0) * 0.1)), false);
                    player = object.d;
                    player.packetSender.modifySkillLevel(2, (int)(-(2.0 + (double)object.d.getSkillManager().getBaseLevel(2) * 0.1)), false);
                    player = object.d;
                    player.packetSender.modifySkillLevel(6, (int)(-(2.0 + (double)object.d.getSkillManager().getBaseLevel(6) * 0.1)), false);
                    player = object.d;
                    player.packetSender.modifySkillLevel(4, (int)(-(2.0 + (double)object.d.getSkillManager().getBaseLevel(4) * 0.1)), false);
                    break;
                }
                case 189: 
                case 191: 
                case 193: 
                case 2450: {
                    Player player = object.d;
                    player.packetSender.modifySkillLevel(0, (int)((double)object.d.getSkillManager().getBaseLevel(0) * 0.2) + 2, true);
                    player = object.d;
                    player.packetSender.modifySkillLevel(2, (int)((double)object.d.getSkillManager().getBaseLevel(2) * 0.12) + 2, true);
                    player = object.d;
                    player.packetSender.modifySkillLevel(5, (int)((double)object.d.getSkillManager().getBaseLevel(5) * 0.1), true);
                    player = object.d;
                    player.packetSender.modifySkillLevel(1, -((int)((double)object.d.getSkillManager().getBaseLevel(1) * 0.1) + 2), false);
                    object.d.applyDirectHit((int)((double)object.d.getSkillManager().getCurrentLevels()[3] * 0.12), HitType.NORMAL);
                }
            }
            this.d.getUpdateState().setAnimation(n == 3801 ? 1330 : 829, 0);
            Player player = this.d;
            player.packetSender.sendSoundEffect(334, 1, 0);
            this.d.nextActionSequence();
            this.d.getAttackDelayTimer().setDelayTicks(this.d.getAttackDelayTimer().getDelayTicks() + 2);
            switch (n) {
                case 7919: {
                    this.d.heal(14);
                    player = this.d;
                    player.packetSender.sendGameMessage("You drink the " + a[this.c].b() + ".");
                    player = this.d;
                    player.packetSender.modifySkillLevel(0, -3, false);
                    if (!this.d.getInventoryManager().removeItemFromSlot(new ItemStack(n, 1), n2)) {
                        this.d.getInventoryManager().removeItem(new ItemStack(n, 1));
                    }
                    return;
                }
                case 3801: {
                    player = this.d;
                    player.packetSender.modifySkillLevel(0, -((int)(5.0 + (double)this.d.getSkillManager().getBaseLevel(0) * 0.5)), false);
                    player = this.d;
                    player.packetSender.modifySkillLevel(2, (int)(2.0 + (double)this.d.getSkillManager().getBaseLevel(2) * 0.1), true);
                    player = this.d;
                    player.packetSender.sendGameMessage("You drink the " + a[this.c].b() + "... that wasn't very smart!");
                    this.d.heal(15);
                    if (!this.d.getInventoryManager().removeItemFromSlot(new ItemStack(n, 1), n2)) {
                        this.d.getInventoryManager().removeItem(new ItemStack(n, 1));
                    }
                    return;
                }
                case 1993: {
                    player = this.d;
                    player.packetSender.modifySkillLevel(0, -2, false);
                    this.d.heal(11);
                    player = this.d;
                    player.packetSender.sendGameMessage("You drink the " + a[this.c].b() + ".");
                    if (this.d.getInventoryManager().removeItemFromSlot(new ItemStack(n, 1), n2)) {
                        this.d.getInventoryManager().a(new ItemStack(1935), n2);
                        return;
                    }
                    if (this.d.getInventoryManager().removeItem(new ItemStack(n, 1))) {
                        this.d.getInventoryManager().addItem(new ItemStack(1935));
                    }
                    return;
                }
                case 1978: {
                    player = this.d;
                    player.packetSender.modifySkillLevel(0, (int)(2.0 + (double)this.d.getSkillManager().getBaseLevel(0) * 0.02), true);
                    this.d.getUpdateState().setForcedTextAndMarkUpdated("Aaah, nothing like a nice cuppa tea!");
                    this.d.heal(3);
                    if (this.d.getInventoryManager().removeItemFromSlot(new ItemStack(n, 1), n2)) {
                        this.d.getInventoryManager().a(new ItemStack(1980), n2);
                        return;
                    }
                    if (this.d.getInventoryManager().removeItem(new ItemStack(n, 1))) {
                        this.d.getInventoryManager().addItem(new ItemStack(1980));
                    }
                    return;
                }
                case 4627: {
                    player = this.d;
                    player.packetSender.modifySkillLevel(1, -((int)(3.0 + (double)this.d.getSkillManager().getBaseLevel(1) * 0.06)), false);
                    player = this.d;
                    player.packetSender.modifySkillLevel(2, -((int)(3.0 + (double)this.d.getSkillManager().getBaseLevel(2) * 0.06)), false);
                    this.d.heal(1);
                    player = this.d;
                    player.packetSender.sendGameMessage("You drink the " + a[this.c].b() + "... it tashtes delishush!");
                    this.d.getUpdateState().setForcedTextAndMarkUpdated("... hic!");
                    if (this.d.getInventoryManager().removeItemFromSlot(new ItemStack(n, 1), n2)) {
                        this.d.getInventoryManager().a(new ItemStack(1919), n2);
                        return;
                    }
                    if (this.d.getInventoryManager().removeItem(new ItemStack(n, 1))) {
                        this.d.getInventoryManager().addItem(new ItemStack(1919));
                    }
                    return;
                }
                case 1913: {
                    player = this.d;
                    player.packetSender.modifySkillLevel(0, -((int)(2.0 + (double)this.d.getSkillManager().getBaseLevel(0) * 0.04)), false);
                    player = this.d;
                    player.packetSender.modifySkillLevel(1, -((int)(2.0 + (double)this.d.getSkillManager().getBaseLevel(1) * 0.04)), false);
                    player = this.d;
                    player.packetSender.modifySkillLevel(2, -((int)(2.0 + (double)this.d.getSkillManager().getBaseLevel(2) * 0.04)), false);
                    this.d.heal(1);
                    player = this.d;
                    player.packetSender.sendGameMessage("You drink the " + a[this.c].b() + "... it tashtes delishush!");
                    this.d.getUpdateState().setForcedTextAndMarkUpdated("... hic!");
                    if (this.d.getInventoryManager().removeItemFromSlot(new ItemStack(n, 1), n2)) {
                        this.d.getInventoryManager().a(new ItemStack(1919), n2);
                        return;
                    }
                    if (this.d.getInventoryManager().removeItem(new ItemStack(n, 1))) {
                        this.d.getInventoryManager().addItem(new ItemStack(1919));
                    }
                    return;
                }
                case 1907: {
                    player = this.d;
                    player.packetSender.modifySkillLevel(0, -((int)(1.0 + (double)this.d.getSkillManager().getBaseLevel(0) * 0.05)), false);
                    player = this.d;
                    player.packetSender.modifySkillLevel(1, -((int)(1.0 + (double)this.d.getSkillManager().getBaseLevel(1) * 0.05)), false);
                    player = this.d;
                    player.packetSender.modifySkillLevel(2, -((int)(1.0 + (double)this.d.getSkillManager().getBaseLevel(2) * 0.05)), false);
                    player = this.d;
                    player.packetSender.modifySkillLevel(6, (int)(2.0 + (double)this.d.getSkillManager().getBaseLevel(6) * 0.02), true);
                    this.d.heal(1);
                    player = this.d;
                    player.packetSender.sendGameMessage("You drink the " + a[this.c].b() + "... it tashtes delishush!");
                    this.d.getUpdateState().setForcedTextAndMarkUpdated("... hic!");
                    if (this.d.getInventoryManager().removeItemFromSlot(new ItemStack(n, 1), n2)) {
                        this.d.getInventoryManager().a(new ItemStack(1919), n2);
                        return;
                    }
                    if (this.d.getInventoryManager().removeItem(new ItemStack(n, 1))) {
                        this.d.getInventoryManager().addItem(new ItemStack(1919));
                    }
                    return;
                }
                case 1917: {
                    player = this.d;
                    player.packetSender.modifySkillLevel(2, (int)(1.0 + (double)this.d.getSkillManager().getBaseLevel(2) * 0.02), true);
                    player = this.d;
                    player.packetSender.modifySkillLevel(0, -((int)(1.0 + (double)this.d.getSkillManager().getBaseLevel(0) * 0.06)), false);
                    this.d.heal(1);
                    player = this.d;
                    player.packetSender.sendGameMessage("You drink the " + a[this.c].b() + "... it tashtes delishush!");
                    this.d.getUpdateState().setForcedTextAndMarkUpdated("... hic!");
                    if (this.d.getInventoryManager().removeItemFromSlot(new ItemStack(n, 1), n2)) {
                        this.d.getInventoryManager().a(new ItemStack(1919), n2);
                        return;
                    }
                    if (this.d.getInventoryManager().removeItem(new ItemStack(n, 1))) {
                        this.d.getInventoryManager().addItem(new ItemStack(1919));
                    }
                    return;
                }
            }
            if (this.e < 3) {
                if (this.d.getInventoryManager().removeItemFromSlot(new ItemStack(n, 1), n2)) {
                    this.d.getInventoryManager().a(new ItemStack(nArray[this.e + 1], 1), n2);
                } else if (this.d.getInventoryManager().removeItem(new ItemStack(n, 1))) {
                    this.d.getInventoryManager().addItem(new ItemStack(nArray[this.e + 1], 1));
                }
                player = this.d;
                int n10 = n;
                player.packetSender.sendGameMessage("You drink" + (n10 != 1993 && n10 != 1978 && n10 != 1917 && n10 != 1907 && n10 != 1913 && n10 != 4627 && n10 != 7919 && n10 != 3801 ? " a dose of" : "") + " your " + a[this.c].b() + ".");
                return;
            }
            if (this.d.getInventoryManager().removeItemFromSlot(new ItemStack(n, 1), n2)) {
                this.d.getInventoryManager().a(new ItemStack(229), n2);
            } else if (this.d.getInventoryManager().removeItem(new ItemStack(n, 1))) {
                this.d.getInventoryManager().addItem(new ItemStack(229));
            }
            player = this.d;
            player.packetSender.sendGameMessage("You drink the last of your " + a[this.c].b() + ".");
        }
    }

    public static PotionDefinition[] a() {
        return a;
    }
}

