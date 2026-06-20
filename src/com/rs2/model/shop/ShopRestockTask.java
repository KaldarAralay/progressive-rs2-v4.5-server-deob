/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.shop;

import com.rs2.model.item.ItemContainer;
import com.rs2.model.item.ItemStack;
import com.rs2.model.shop.ShopDefinition;
import com.rs2.model.shop.ShopManager;
import com.rs2.model.task.TickTask;

final class ShopRestockTask
extends TickTask {
    private final /* synthetic */ ShopDefinition a;
    private final /* synthetic */ int b;
    private final /* synthetic */ int c;

    ShopRestockTask(int n, ShopDefinition shopDefinition, int n2, int n3) {
        this.a = shopDefinition;
        this.b = n2;
        this.c = n3;
        super(n);
    }

    @Override
    public final void execute() {
        if (this.a.j().containsItem(this.b)) {
            int n = this.a.j().findFlatItem(this.b).getAmount();
            int n2 = this.a.k().findFlatItem(this.b).getAmount();
            if (n2 < n) {
                ItemStack itemStack = new ItemStack(this.b);
                ItemContainer itemContainer = this.a.k();
                itemContainer.add(itemStack, -1);
            } else if (n2 > n) {
                this.a.k().removeKeepingPlaceholder(new ItemStack(this.b));
            }
        } else {
            int n = ShopManager.a(this.a, this.b);
            if (this.a.k().containsItem(this.b) && n == this.c) {
                this.a.k().remove(new ItemStack(this.b));
            }
        }
        ShopManager.a(this.a.f());
        if (!ShopManager.a(this.a, this.b, this.c)) {
            this.stop();
        }
    }
}

