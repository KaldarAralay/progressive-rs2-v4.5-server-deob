/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.item.consumable;

import com.rs2.model.item.consumable.PotionEffectMode;

public final class PotionDefinition {
    private boolean a;
    private String b;
    private PotionEffectMode c;
    private int[] d;
    private int[] e;
    private int[] f;
    private double[] g;

    public final boolean a() {
        return this.a;
    }

    public final String b() {
        return this.b;
    }

    public final int[] c() {
        return this.d;
    }

    public final int[] d() {
        return this.e;
    }

    public final int[] e() {
        return this.f;
    }

    public final double[] f() {
        return this.g;
    }

    public final PotionEffectMode g() {
        return this.c;
    }

    static /* synthetic */ void a(PotionDefinition potionDefinition, PotionEffectMode potionEffectMode) {
        potionDefinition.c = potionEffectMode;
    }

    static /* synthetic */ void a(PotionDefinition potionDefinition, int[] nArray) {
        potionDefinition.d = nArray;
    }

    static /* synthetic */ void a(PotionDefinition potionDefinition, boolean bl) {
        potionDefinition.a = bl;
    }

    static /* synthetic */ int[] a(PotionDefinition potionDefinition) {
        return potionDefinition.d;
    }

    static /* synthetic */ void a(PotionDefinition potionDefinition, String string) {
        potionDefinition.b = string;
    }

    static /* synthetic */ void b(PotionDefinition potionDefinition, int[] nArray) {
        potionDefinition.e = nArray;
    }

    static /* synthetic */ void c(PotionDefinition potionDefinition, int[] nArray) {
        potionDefinition.f = nArray;
    }

    static /* synthetic */ void a(PotionDefinition potionDefinition, double[] dArray) {
        potionDefinition.g = dArray;
    }

    static /* synthetic */ int[] b(PotionDefinition potionDefinition) {
        return potionDefinition.e;
    }

    static /* synthetic */ int[] c(PotionDefinition potionDefinition) {
        return potionDefinition.f;
    }

    static /* synthetic */ double[] d(PotionDefinition potionDefinition) {
        return potionDefinition.g;
    }
}

