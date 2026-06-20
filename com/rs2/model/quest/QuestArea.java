/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest;

import com.rs2.model.Position;
import com.rs2.util.RectangularArea;

public final class QuestArea {
    private int a;
    private Position b;
    private RectangularArea c;

    public QuestArea(int n, Position position, RectangularArea rectangularArea) {
        this.a = n;
        this.b = position;
        this.c = rectangularArea;
    }

    public final int a() {
        return this.a;
    }

    public final Position b() {
        return this.b;
    }

    public final RectangularArea c() {
        return this.c;
    }
}

