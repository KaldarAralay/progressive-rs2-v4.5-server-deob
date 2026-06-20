/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.thieving;

import com.rs2.model.item.ItemStack;

/*
 * Exception performing whole class analysis.
 */
public final class PickpocketDefinition
extends Enum {
    private String[] npcNames;
    private int requiredLevel;
    private double experience;
    private ItemStack[] commonRewards;
    private ItemStack[] rareRewards;
    private int stunTicks;
    private int minDamage;
    private int maxDamage;
    private int successChanceLow;
    private int successChanceHigh;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private PickpocketDefinition(double d, ItemStack[] itemStackArray, int n, int stringArray22, int n2, int n3) {
        void var11_8;
        void var10_7;
        void cfr_renamed_3;
        void var4_2;
        this.npcNames = (String[])d;
        this.requiredLevel = var4_2;
        this.experience = (double)itemStackArray;
        this.commonRewards = cfr_renamed_3;
        this.stunTicks = n2;
        this.minDamage = n3;
        this.maxDamage = n3;
        this.successChanceLow = var10_7;
        this.successChanceHigh = var11_8;
    }

    private PickpocketDefinition(double d, ItemStack[] itemStackArray, ItemStack[] itemStackArray2, int n2, int n3, int n4, int n5) {
        this.npcNames = (String[])d;
        this.requiredLevel = 38;
        this.experience = 43.0;
        this.commonRewards = (ItemStack[])n2;
        this.rareRewards = (ItemStack[])n3;
        this.stunTicks = 8;
        this.minDamage = 3;
        this.maxDamage = 3;
        this.successChanceLow = 90;
        this.successChanceHigh = 240;
    }

    public final String[] getNpcNames() {
        return this.npcNames;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final double getExperience() {
        return this.experience;
    }

    public final ItemStack[] getCommonRewards() {
        return this.commonRewards;
    }

    public final ItemStack[] getRareRewards() {
        return this.rareRewards;
    }

    public final int getStunTicks() {
        return this.stunTicks;
    }

    public final int getMinDamage() {
        return this.minDamage;
    }

    public final int getMaxDamage() {
        return this.maxDamage;
    }

    public final int getSuccessChanceLow() {
        return this.successChanceLow;
    }

    public final int getSuccessChanceHigh() {
        return this.successChanceHigh;
    }
}

