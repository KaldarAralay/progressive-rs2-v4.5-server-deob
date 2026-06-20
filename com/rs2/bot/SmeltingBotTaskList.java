/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot;

import com.rs2.bot.BotTaskDefinition;
import java.util.ArrayList;

final class SmeltingBotTaskList
extends ArrayList {
    SmeltingBotTaskList() {
        this.add(BotTaskDefinition.getAlKharidSteelSmeltingTask());
        this.add(BotTaskDefinition.getFaladorSteelSmeltingTask());
    }
}

