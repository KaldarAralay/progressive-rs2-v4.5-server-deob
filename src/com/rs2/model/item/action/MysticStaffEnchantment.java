/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.item.action;

import java.util.HashMap;

public enum MysticStaffEnchantment {
    a(1734, 1397, 1405),
    b(1735, 1395, 1403),
    c(1736, 1399, 1407),
    d(1737, 1393, 1401),
    e(1738, 3053, 3054),
    f(15348, 6562, 6563);

    private int g;
    private int h;
    private int i;
    private static HashMap j;

    static {
        j = new HashMap();
        MysticStaffEnchantment[] mysticStaffEnchantmentArray = MysticStaffEnchantment.values();
        int n = mysticStaffEnchantmentArray.length;
        int n2 = 0;
        while (n2 < n) {
            MysticStaffEnchantment mysticStaffEnchantment;
            MysticStaffEnchantment mysticStaffEnchantment2 = mysticStaffEnchantment = mysticStaffEnchantmentArray[n2];
            j.put(mysticStaffEnchantment2.g, mysticStaffEnchantment);
            ++n2;
        }
    }

    public static MysticStaffEnchantment a(int n) {
        return (MysticStaffEnchantment)((Object)j.get(n));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private MysticStaffEnchantment(int n3) {
        void var5_3;
        void var4_2;
        this.g = n3;
        this.h = var4_2;
        this.i = var5_3;
    }

    public final int a() {
        return this.h;
    }

    public final int b() {
        return this.i;
    }
}

