/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot.shop;

import com.rs2.bot.shop.AlKharidLegsShopBotTask;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.shop.ShopManager;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

final class AlKharidLegsShopTradeTickTask
extends TickTask {
    private final /* synthetic */ Player player;
    private final /* synthetic */ ItemStack shopItem;

    AlKharidLegsShopTradeTickTask(AlKharidLegsShopBotTask alKharidLegsShopBotTask, int n, Player player, ItemStack itemStack) {
        this.player = player;
        this.shopItem = itemStack;
        super(2);
    }

    @Override
    public final void execute() {
        if (this.player.isDead() || !this.player.bW() || !this.player.currentBotTask.usesCustomTaskAction || !this.player.botTaskState.equals("do task")) {
            this.stop();
            return;
        }
        if (this.player.getOpenInterfaceId() == 3824) {
            if (this.player.N <= 0) {
                if (this.player.botShopBuyMode == 1) {
                    ShopManager.a(this.player, this.shopItem);
                    return;
                }
                ShopManager.b(this.player, this.shopItem);
                return;
            }
            --this.player.N;
            return;
        }
        this.player.N = 3 + GameUtil.h(3);
        this.player.botInteractionOption = 2;
        this.player.interactWithBotNpcTargets(this.player.botInteractionTargetIds);
    }
}

