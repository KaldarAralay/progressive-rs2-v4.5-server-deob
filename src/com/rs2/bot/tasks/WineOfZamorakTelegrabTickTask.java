/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot.tasks;

import com.rs2.bot.tasks.FaladorWineOfZamorakTelegrabBotTask;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.task.TickTask;

final class WineOfZamorakTelegrabTickTask
extends TickTask {
    private /* synthetic */ FaladorWineOfZamorakTelegrabBotTask a;
    private final /* synthetic */ Player b;

    WineOfZamorakTelegrabTickTask(FaladorWineOfZamorakTelegrabBotTask faladorWineOfZamorakTelegrabBotTask, int n, Player player) {
        this.a = faladorWineOfZamorakTelegrabBotTask;
        this.b = player;
        super(2);
    }

    @Override
    public final void execute() {
        if (this.b.isDead() || !this.b.bW() || !this.b.currentBotTask.usesCustomTaskAction || !this.b.botTaskState.equals("do task")) {
            this.stop();
            return;
        }
        if (this.b.getInventoryManager().getContainer().getFreeSlots() <= 0) {
            this.b.currentBotTask.startWalkToBank(this.b);
            this.stop();
            return;
        }
        GroundItemManager.getInstance();
        GroundItem groundItem = GroundItemManager.findVisibleItemAt(this.b, this.a.aa);
        if (groundItem != null && groundItem.getItem().getId() == 245) {
            MagicSpellAction.scheduleTelekineticGrab(this.b, SpellDefinition.TELEKINETIC_GRAB, 245, this.a.aa);
        }
    }
}

