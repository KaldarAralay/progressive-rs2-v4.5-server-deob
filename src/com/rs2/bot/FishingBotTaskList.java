/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot;

import com.rs2.bot.BotTaskDefinition;
import java.util.ArrayList;

final class FishingBotTaskList
extends ArrayList {
    FishingBotTaskList() {
        this.add(BotTaskDefinition.getDraynorNetFishingTask());
        this.add(BotTaskDefinition.getBarbarianVillageFlyFishingTask());
        this.add(BotTaskDefinition.getKaramjaFishingTask());
        this.add(BotTaskDefinition.getAlKharidFlyFishingTask());
        this.add(BotTaskDefinition.getAlKharidNetBaitFishingTask());
        this.add(BotTaskDefinition.getCatherbyFishingTask());
    }
}

