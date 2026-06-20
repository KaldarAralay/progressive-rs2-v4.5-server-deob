/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.fletching.logs;

import com.rs2.ServerSettings;
import java.util.HashMap;

public enum MagicLogFletchingRecipe {
    a(ServerSettings.cacheVersion < 274 ? 2461 : 8874, 1513, 72, 1, 80, 83.3),
    b(8873, 1513, 72, 5, 65, 68.5),
    c(8872, 1513, 72, 10, 65, 68.5),
    d(8871, 1513, 72, 0, 65, 68.5),
    e(ServerSettings.cacheVersion < 274 ? 2462 : 8878, 1513, 70, 1, 85, 91.5),
    f(8877, 1513, 70, 5, 85, 91.5),
    g(8876, 1513, 70, 10, 85, 91.5),
    h(8875, 1513, 70, 0, 85, 91.5);

    private int buttonId;
    private int logItemId;
    private int productItemId;
    private int menuQuantity;
    private int requiredLevel;
    private double experience;
    private static HashMap recipesByButtonId;

    static {
        recipesByButtonId = new HashMap();
        MagicLogFletchingRecipe[] magicLogFletchingRecipeArray = MagicLogFletchingRecipe.values();
        int n = magicLogFletchingRecipeArray.length;
        int n2 = 0;
        while (n2 < n) {
            MagicLogFletchingRecipe magicLogFletchingRecipe = magicLogFletchingRecipeArray[n2];
            recipesByButtonId.put(magicLogFletchingRecipe.buttonId, magicLogFletchingRecipe);
            ++n2;
        }
    }

    public static MagicLogFletchingRecipe forButtonId(int n) {
        return (MagicLogFletchingRecipe)((Object)recipesByButtonId.get(n));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private MagicLogFletchingRecipe(int n3, int n4, int n5, double d) {
        void var8_6;
        void var7_5;
        this.buttonId = n3;
        this.logItemId = 1513;
        this.productItemId = n5;
        this.menuQuantity = (int)d;
        this.requiredLevel = var7_5;
        this.experience = var8_6;
    }

    public final int getLogItemId() {
        return this.logItemId;
    }

    public final int getProductItemId() {
        return this.productItemId;
    }

    public final int getMenuQuantity() {
        return this.menuQuantity;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final double getExperience() {
        return this.experience;
    }
}

