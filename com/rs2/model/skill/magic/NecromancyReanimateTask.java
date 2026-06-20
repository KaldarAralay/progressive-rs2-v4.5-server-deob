/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.magic;

import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.Spellbook;
import com.rs2.model.task.TickTask;

final class NecromancyReanimateTask
extends TickTask {
    private /* synthetic */ MagicSpellAction a;
    private final /* synthetic */ int b;

    NecromancyReanimateTask(MagicSpellAction magicSpellAction, int n, int n2) {
        this.a = magicSpellAction;
        this.b = n2;
        super(2);
    }

    @Override
    public final void execute() {
        MagicSpellAction.getPlayer(this.a).getInventoryManager().removeItem(new ItemStack(MagicSpellAction.getNecromancyReanimationTable()[this.b][0], 1));
        Entity entity = new Npc(MagicSpellAction.getNecromancyReanimationTable()[this.b][1]);
        GameplayHelper.a(MagicSpellAction.getPlayer(this.a), entity, true, false);
        entity.getUpdateState().setGraphicHeight100(110);
        if (MagicSpellAction.getPlayer(this.a).getEquipmentManager().getItemIdAtSlot(5) != 8118) {
            MagicSpellAction.getPlayer(this.a).setSpellbook(MagicSpellAction.getPlayer((MagicSpellAction)this.a).E);
            if (MagicSpellAction.getPlayer(this.a).getSpellbook() == Spellbook.MODERN) {
                entity = MagicSpellAction.getPlayer(this.a);
                ((Player)entity).packetSender.setSidebarInterface(6, 1151);
            }
            if (MagicSpellAction.getPlayer(this.a).getSpellbook() == Spellbook.ANCIENT) {
                entity = MagicSpellAction.getPlayer(this.a);
                ((Player)entity).packetSender.setSidebarInterface(6, 12855);
            }
        }
        MagicSpellAction.getPlayer(this.a).setActionLocked(false);
        this.stop();
    }
}

