/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot;

import com.rs2.bot.BotTaskDefinition;
import java.util.ArrayList;

final class CookingBotTaskList
extends ArrayList {
    CookingBotTaskList() {
        this.add(BotTaskDefinition.getVarrockLobsterCookingTask());
        this.add(BotTaskDefinition.getAlKharidLobsterCookingTask());
        this.add(BotTaskDefinition.getCatherbyLobsterCookingTask());
    }
}

