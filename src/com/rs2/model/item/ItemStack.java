/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.item;

import com.rs2.model.item.ItemDefinition;
import com.rs2.model.skill.farming.FarmingPatchUtils;

public class ItemStack {
    private int a;
    private int b;
    private int c;

    public ItemStack(int n) {
        this(n, 1);
    }

    public final void setAmount(int n) {
        this.b = n;
    }

    public final void setMetadata(int n) {
        this.c = n;
    }

    public ItemStack(int n, int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException("Count cannot be negative.");
        }
        this.a = n;
        this.b = n2;
        this.c = -1;
        int[] nArray = FarmingPatchUtils.a;
        int n3 = 0;
        while (n3 < 14) {
            n2 = nArray[n3];
            if (n2 == n) {
                this.c = 5;
            }
            ++n3;
        }
        if (n == 1995) {
            this.c = 1;
        }
        if (n >= 5510 && n <= 5515 || n == 5519) {
            this.c = 0;
        }
    }

    public ItemStack(int n, int n2, int n3) {
        if (n2 < 0) {
            throw new IllegalArgumentException("Count cannot be negative.");
        }
        this.a = n;
        this.b = n2;
        this.c = n3;
    }

    public final int getId() {
        return this.a;
    }

    public final int getAmount() {
        return this.b;
    }

    public final int getMetadata() {
        return this.c;
    }

    public final ItemDefinition getDefinition() {
        return ItemDefinition.forId(this.a);
    }

    public String toString() {
        return String.valueOf(ItemStack.class.getName()) + " [id=" + this.a + ", count=" + this.b + "]";
    }

    public final boolean isValid() {
        ItemStack itemStack = this;
        if (itemStack.a >= 0) {
            itemStack = this;
            if (itemStack.a <= 11883) {
                itemStack = this;
                if (itemStack.b > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean isEquippable() {
        if (this.isValid()) {
            ItemStack itemStack = this;
            if (ItemDefinition.forId(itemStack.a).getEquipmentSlot() != -1) {
                return true;
            }
        }
        return false;
    }
}

