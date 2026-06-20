/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot;

import com.rs2.bot.BotTaskDefinition;
import java.util.ArrayList;

final class SpinningBotTaskList
extends ArrayList {
    SpinningBotTaskList() {
        this.add(BotTaskDefinition.getLumbridgeWoolSpinningTask());
        this.add(BotTaskDefinition.getSeersFlaxSpinningTask());
    }
}

