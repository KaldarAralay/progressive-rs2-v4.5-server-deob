/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.cooking;

public enum FoodPreparationRecipe {
    a(1953, 0, 2313, 2315, 1, 0.0, true, 0),
    b(1951, 0, 2315, 2321, 10, 78.0, true, 0),
    c(1955, 0, 2315, 2317, 30, 130.0, true, 0),
    d(2142, 0, 2315, 2319, 20, 110.0, true, 0),
    e(2007, 0, 1921, 2009, 60, 280.0, true, 7496),
    f(5970, 3, 1921, 2009, 60, 280.0, true, 0),
    g(2142, 0, 2289, 2293, 45, 169.0, true, 0),
    h(319, 0, 2289, 2297, 45, 169.0, true, 0),
    i(2116, 0, 2289, 2301, 65, 195.0, true, 0),
    j(2118, 0, 2289, 2301, 65, 195.0, true, 0),
    k(1973, 0, 1891, 1897, 50, 210.0, true, 0),
    l(1975, 0, 1891, 1897, 50, 210.0, true, 0),
    m(1975, 0, 1927, 1977, 4, 0.0, true, 0),
    n(1933, 0, 1887, 1889, 40, 0.0, true, 0),
    o(1927, 0, 1887, 1889, 40, 0.0, true, 0),
    p(1944, 0, 1887, 1889, 40, 0.0, true, 0),
    q(1550, 0, 1923, 7074, 9, 25.0, true, 0),
    r(2169, 0, 7074, 7072, 9, 25.0, true, 0),
    s(2142, 0, 7072, 7062, 11, 55.0, true, 0),
    t(1944, 0, 1923, 7076, 13, 50.0, true, 0),
    u(1982, 0, 7078, 7064, 23, 50.0, true, 0),
    v(1957, 0, 1923, 1871, 42, 60.0, true, 0),
    w(6004, 0, 1923, 7080, 46, 60.0, true, 0),
    x(7082, 0, 7084, 7066, 57, 120.0, false, 1923),
    y(6697, 0, 6705, 6703, 39, 95.5, false, 1923),
    z(6703, 0, 7062, 7054, 41, 165.5, false, 1923),
    A(6703, 0, 1985, 6705, 47, 199.5, false, 1923),
    B(6703, 0, 7064, 7056, 51, 195.5, false, 1923),
    C(7082, 0, 7066, 7058, 64, 270.5, false, 1923),
    D(6703, 0, 7068, 7060, 68, 309.5, false, 1923),
    E(1987, 0, 1937, 1993, 35, 309.5, false, 0),
    F(4241, 0, 1921, 4237, 20, 52.0, false, 0),
    G(4239, 0, 1980, 4242, 20, 52.0, false, 1923);

    private int H;
    private int I;
    private int J;
    private int K;
    private int L;
    private double M;
    private boolean N;
    private int O;

    public static FoodPreparationRecipe a(int n, int n2) {
        FoodPreparationRecipe[] foodPreparationRecipeArray = FoodPreparationRecipe.values();
        int n3 = foodPreparationRecipeArray.length;
        int n4 = 0;
        while (n4 < n3) {
            FoodPreparationRecipe foodPreparationRecipe = foodPreparationRecipeArray[n4];
            if (foodPreparationRecipe.H == n && foodPreparationRecipe.J == n2 || foodPreparationRecipe.H == n2 && foodPreparationRecipe.J == n) {
                return foodPreparationRecipe;
            }
            ++n4;
        }
        return null;
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private FoodPreparationRecipe(int n3, int n4, int n5, double d, boolean bl, int n6) {
        void var11_8;
        void cfr_renamed_5;
        void var7_5;
        this.H = n3;
        this.I = n4;
        this.J = n5;
        this.K = (int)d;
        this.L = var7_5;
        this.M = (double)bl;
        this.N = cfr_renamed_5;
        this.O = var11_8;
    }

    public final int a() {
        return this.H;
    }

    public final int b() {
        return this.I;
    }

    public final int c() {
        return this.J;
    }

    public final int d() {
        return this.K;
    }

    public final int e() {
        return this.L;
    }

    public final double f() {
        return this.M;
    }

    public final boolean g() {
        return this.N;
    }

    public final int h() {
        return this.O;
    }
}

