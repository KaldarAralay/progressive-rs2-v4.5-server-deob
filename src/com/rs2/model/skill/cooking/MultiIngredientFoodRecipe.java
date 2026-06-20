/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.cooking;

import java.util.HashMap;

public enum MultiIngredientFoodRecipe {
    a(2142, 1942, 1921, 1999, 2001, 25, 117.0, true, 0, 0),
    b(1942, 2142, 1921, 1997, 2001, 25, 117.0, true, 0, 0),
    c(2140, 1942, 1921, 1999, 2001, 25, 117.0, true, 0, 0),
    d(1942, 2140, 1921, 1997, 2001, 25, 117.0, true, 0, 0),
    e(1982, 1985, 2283, 2285, 2287, 35, 143.0, true, 0, 0),
    f(361, 5988, 1923, 7086, 7068, 67, 204.0, true, 0, 0),
    g(5988, 361, 1923, 7088, 7068, 67, 204.0, true, 0, 0);

    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private double n;
    private boolean o;
    private int p;
    private int q;
    private static HashMap r;
    private static HashMap s;

    static {
        r = new HashMap();
        s = new HashMap();
        MultiIngredientFoodRecipe[] multiIngredientFoodRecipeArray = MultiIngredientFoodRecipe.values();
        int n = multiIngredientFoodRecipeArray.length;
        int n2 = 0;
        while (n2 < n) {
            MultiIngredientFoodRecipe multiIngredientFoodRecipe = multiIngredientFoodRecipeArray[n2];
            r.put(multiIngredientFoodRecipe.h, multiIngredientFoodRecipe);
            s.put(multiIngredientFoodRecipe.k, multiIngredientFoodRecipe);
            ++n2;
        }
    }

    public static MultiIngredientFoodRecipe a(int n) {
        return (MultiIngredientFoodRecipe)((Object)r.get(n));
    }

    public static MultiIngredientFoodRecipe b(int n) {
        return (MultiIngredientFoodRecipe)((Object)s.get(n));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private MultiIngredientFoodRecipe(int n3, int n4, int n5, int n6, double d, boolean n7, int n8, int n9) {
        void cfr_renamed_6;
        void var8_7;
        this.h = n3;
        this.i = n4;
        this.j = n5;
        this.k = n6;
        this.l = (int)d;
        this.m = var8_7;
        this.n = cfr_renamed_6;
        this.o = true;
        this.p = 0;
        this.q = 0;
    }

    public final int a() {
        return this.h;
    }

    public final int b() {
        return this.i;
    }

    public final int c() {
        return this.k;
    }

    public final int d() {
        return this.j;
    }

    public final int e() {
        return this.l;
    }

    public final int f() {
        return this.m;
    }

    public final double g() {
        return this.n;
    }

    public final boolean h() {
        return this.o;
    }

    public final int i() {
        return this.p;
    }

    public final int j() {
        return this.q;
    }
}

