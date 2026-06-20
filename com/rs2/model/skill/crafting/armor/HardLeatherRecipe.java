/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.crafting.armor;

import java.util.HashMap;

public enum HardLeatherRecipe {
    BODY(2799, 1743, 1, 1131, 1, 28, 35.0),
    BODY5(2798, 1743, 1, 1131, 5, 28, 35.0),
    BODY10(1747, 1743, 1, 1131, 27, 28, 35.0),
    BODYX(1748, 1743, 1, 1131, 0, 28, 35.0);

    private int buttonId;
    private int materialItemId;
    private int materialAmount;
    private int productItemId;
    private int quantity;
    private int requiredLevel;
    private double experience;
    private static HashMap definitionsByButtonId;

    static {
        definitionsByButtonId = new HashMap();
        HardLeatherRecipe[] hardLeatherRecipeArray = HardLeatherRecipe.values();
        int n = hardLeatherRecipeArray.length;
        int n2 = 0;
        while (n2 < n) {
            HardLeatherRecipe hardLeatherRecipe = hardLeatherRecipeArray[n2];
            definitionsByButtonId.put(hardLeatherRecipe.buttonId, hardLeatherRecipe);
            ++n2;
        }
    }

    public static HardLeatherRecipe forButtonId(int n) {
        if (definitionsByButtonId == null) {
            return null;
        }
        return (HardLeatherRecipe)((Object)definitionsByButtonId.get(n));
    }

    private HardLeatherRecipe(int n3, int n4, int n5, int n6, double d) {
        this.buttonId = n3;
        this.materialItemId = 1743;
        this.materialAmount = 1;
        this.productItemId = 1131;
        this.quantity = (int)d;
        this.requiredLevel = 28;
        this.experience = 35.0;
    }

    public final int getButtonId() {
        return this.buttonId;
    }

    public final int getMaterialItemId() {
        return this.materialItemId;
    }

    public final int getMaterialAmount() {
        return this.materialAmount;
    }

    public final int getProductItemId() {
        return this.productItemId;
    }

    public final int getQuantity() {
        return this.quantity;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final double getExperience() {
        return this.experience;
    }
}

