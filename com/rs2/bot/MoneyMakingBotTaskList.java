/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot;

import com.rs2.bot.BotTaskDefinition;
import java.util.ArrayList;

final class MoneyMakingBotTaskList
extends ArrayList {
    MoneyMakingBotTaskList() {
        this.add(BotTaskDefinition.getFaladorWineOfZamorakTelegrabTask());
        this.add(BotTaskDefinition.wizardsTowerLesserDemonMagicTask);
        this.add(BotTaskDefinition.seersFlaxPickingTask);
    }
}

