/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.crafting.armor;

import com.rs2.ServerSettings;
import java.util.HashMap;

public enum GreenDragonhideRecipe {
    VAMB(ServerSettings.cacheVersion < 274 ? 2471 : 8889, 1745, 1, 1065, 1, 57, 62.0),
    VAMB5(8888, 1745, 1, 1065, 5, 57, 62.0),
    VAMB10(8887, 1745, 1, 1065, 10, 57, 62.0),
    VAMBX(8886, 1745, 1, 1065, 0, 57, 62.0),
    CHAPS(ServerSettings.cacheVersion < 274 ? 2472 : 8893, 1745, 2, 1099, 1, 60, 124.0),
    CHAPS5(8892, 1745, 2, 1099, 5, 60, 124.0),
    CHAPS10(8891, 1745, 2, 1099, 10, 60, 124.0),
    CHAPSX(8890, 1745, 2, 1099, 0, 60, 124.0),
    BODY(ServerSettings.cacheVersion < 274 ? 2473 : 8897, 1745, 3, 1135, 1, 63, 186.0),
    BODY5(8896, 1745, 3, 1135, 5, 63, 186.0),
    BODY10(8895, 1745, 3, 1135, 10, 63, 186.0),
    BODYX(8894, 1745, 3, 1135, 0, 63, 186.0);

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
        GreenDragonhideRecipe[] greenDragonhideRecipeArray = GreenDragonhideRecipe.values();
        int n = greenDragonhideRecipeArray.length;
        int n2 = 0;
        while (n2 < n) {
            GreenDragonhideRecipe greenDragonhideRecipe = greenDragonhideRecipeArray[n2];
            definitionsByButtonId.put(greenDragonhideRecipe.buttonId, greenDragonhideRecipe);
            ++n2;
        }
    }

    public static GreenDragonhideRecipe forButtonId(int n) {
        if (definitionsByButtonId == null) {
            return null;
        }
        return (GreenDragonhideRecipe)((Object)definitionsByButtonId.get(n));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private GreenDragonhideRecipe(int n3, int n4, int n5, int n6, double d) {
        void var9_7;
        void var8_6;
        this.buttonId = n3;
        this.materialItemId = 1745;
        this.materialAmount = n5;
        this.productItemId = n6;
        this.quantity = (int)d;
        this.requiredLevel = var8_6;
        this.experience = var9_7;
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

