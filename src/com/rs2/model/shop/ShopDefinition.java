/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.shop;

import com.rs2.model.item.ItemContainer;
import com.rs2.model.shop.ShopCurrency;
import com.rs2.model.task.TickTask;

public final class ShopDefinition {
    private int a;
    private String b;
    private boolean c;
    private boolean d = false;
    private ShopCurrency e;
    private int f;
    private ItemContainer g;
    private ItemContainer h;
    private int i;
    private int j;
    private int k;
    private int[] l;
    private TickTask[] m;

    public final void a(int n) {
        this.i = n;
    }

    public final int a() {
        return this.i;
    }

    public final void b(int n) {
        this.j = n;
    }

    public final int b() {
        return this.j;
    }

    public final void c(int n) {
        this.k = n;
    }

    public final double c() {
        double d = this.k;
        return d /= 10.0;
    }

    public final void a(String string) {
        this.b = string;
    }

    public final String d() {
        return this.b;
    }

    public final boolean e() {
        return this.d;
    }

    public final void a(boolean bl) {
        this.d = bl;
    }

    public final int f() {
        return this.a;
    }

    public final void b(boolean bl) {
        this.c = bl;
    }

    public final boolean g() {
        return this.c;
    }

    public final void d(int n) {
        this.f = n;
    }

    public final int h() {
        return this.f;
    }

    public final ShopCurrency i() {
        return this.e;
    }

    public final void a(ShopCurrency shopCurrency) {
        this.e = shopCurrency;
    }

    public final void a(ItemContainer itemContainer) {
        this.g = itemContainer;
    }

    public final ItemContainer j() {
        return this.g;
    }

    public final void b(ItemContainer itemContainer) {
        this.h = itemContainer;
    }

    public final ItemContainer k() {
        return this.h;
    }

    static /* synthetic */ void a(ShopDefinition shopDefinition, int[] nArray) {
        shopDefinition.l = nArray;
    }

    static /* synthetic */ void a(ShopDefinition shopDefinition, TickTask[] tickTaskArray) {
        shopDefinition.m = tickTaskArray;
    }

    static /* synthetic */ int[] a(ShopDefinition shopDefinition) {
        return shopDefinition.l;
    }

    static /* synthetic */ void a(ShopDefinition shopDefinition, int n) {
        shopDefinition.a = n;
    }

    static /* synthetic */ TickTask[] b(ShopDefinition shopDefinition) {
        return shopDefinition.m;
    }
}

