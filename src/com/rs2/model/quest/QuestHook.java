/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest;

import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;

public abstract class QuestHook {
    private int a;
    private int b = 0;
    private boolean c = false;

    public QuestHook(int n) {
        this.a = n;
    }

    public QuestHook(int n, int n2) {
        this.a = n;
        this.b = n2;
    }

    public final int b() {
        return this.a;
    }

    public final int c() {
        return this.b;
    }

    public final boolean d() {
        return this.c;
    }

    public final void a(boolean bl) {
        this.c = true;
    }

    public static int a(Position position, Position object) {
        int n = GameUtil.b(position, (Position)object);
        object = ProjectileTiming.d;
        double d = (double)(((ProjectileTiming)object).getStartDelay() + ((ProjectileTiming)object).getSpeed()) + (double)n * 5.0;
        d = Math.ceil(d * 12.0 / 600.0);
        if (n > 1) {
            d += 1.0;
        }
        n = 0 + (int)d;
        return n;
    }

    public boolean a(Player player, int n, int n2, int n3, int n4) {
        return false;
    }

    public boolean a(int n, Player player, int n2, int n3, int n4, int n5, int n6, int n7) {
        return false;
    }

    public boolean a(Player player, int n, Position position, int n2) {
        return false;
    }

    public boolean b(Player player, int n, int n2, int n3, int n4) {
        return false;
    }

    public boolean c(Player player, int n, int n2, int n3, int n4) {
        return false;
    }

    public boolean a(Entity entity, Entity entity2, int n) {
        return false;
    }

    public int b(Entity entity, Entity entity2, int n) {
        return -1;
    }

    public boolean a(Player player, int n, int n2) {
        return false;
    }

    public boolean b(Player player, int n) {
        return false;
    }

    public boolean b(Player player, int n, int n2) {
        return true;
    }

    public boolean c(Player player, int n) {
        return false;
    }

    public void d(Player player, int n) {
    }

    public boolean c(Player player, int n, int n2) {
        return false;
    }

    public boolean a(Player player, int n, int n2, int n3) {
        return false;
    }

    public boolean b(Player player, int n, int n2, int n3) {
        return false;
    }

    public boolean d(Player player, int n, int n2) {
        return false;
    }

    public boolean c(Player player, int n, int n2, int n3) {
        return false;
    }

    public boolean e(Player player, int n, int n2) {
        return false;
    }

    public boolean f(Player player, int n, int n2) {
        return false;
    }

    public boolean d(Player player, int n, int n2, int n3) {
        return false;
    }

    public void e() {
    }
}

