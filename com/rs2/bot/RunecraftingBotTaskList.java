/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot;

import com.rs2.bot.BotTaskDefinition;
import java.util.ArrayList;

final class RunecraftingBotTaskList
extends ArrayList {
    RunecraftingBotTaskList() {
        this.add(BotTaskDefinition.getVarrockRuneEssenceMiningTask());
        this.add(BotTaskDefinition.getAirRuneRunecraftingTask());
        this.add(BotTaskDefinition.getMindRuneRunecraftingTask());
        this.add(BotTaskDefinition.getWaterRuneRunecraftingTask());
        this.add(BotTaskDefinition.getEarthRuneRunecraftingTask());
        this.add(BotTaskDefinition.getFireRuneRunecraftingTask());
        this.add(BotTaskDefinition.getBodyRuneRunecraftingTask());
    }
}

