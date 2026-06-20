/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.cooking;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;

public final class WineFermentationHandler {
    private Player a;

    public WineFermentationHandler(Player player) {
        this.a = player;
    }

    public final void a(int n) {
        if (!this.a.getInventoryManager().removeItemFromSlot(new ItemStack(1995), n)) {
            this.a.getInventoryManager().removeItem(new ItemStack(1995));
        }
        if (GameUtil.b(48, 352, this.a.getSkillManager().getCurrentLevels()[7])) {
            this.a.getSkillManager().addExperience(7, 110.0);
            this.a.getInventoryManager().addItem(new ItemStack(1993));
            return;
        }
        this.a.getInventoryManager().addItem(new ItemStack(1991));
    }

    public final void b(int n) {
        this.a.getBankContainer().removeFromSlot(new ItemStack(1995), n);
        if (GameUtil.b(48, 352, this.a.getSkillManager().getCurrentLevels()[7])) {
            this.a.getSkillManager().addExperience(7, 110.0);
            this.a.getInventoryManager().addItem(new ItemStack(1993));
            return;
        }
        this.a.getInventoryManager().addItem(new ItemStack(1991));
    }
}

