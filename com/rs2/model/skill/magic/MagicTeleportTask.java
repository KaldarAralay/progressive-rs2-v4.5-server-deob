/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.magic;

import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.TeleportManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class MagicTeleportTask
extends CycleEvent {
    private int ticksRemaining = 6;
    private /* synthetic */ TeleportManager teleportManager;
    private final /* synthetic */ boolean ancientSpellbookTeleport;
    private final /* synthetic */ int destinationX;
    private final /* synthetic */ int destinationY;
    private final /* synthetic */ int destinationPlane;

    public MagicTeleportTask(TeleportManager teleportManager, boolean bl, int n, int n2, int n3) {
        this.teleportManager = teleportManager;
        this.ancientSpellbookTeleport = bl;
        this.destinationX = n;
        this.destinationY = n2;
        this.destinationPlane = n3;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        --this.ticksRemaining;
        if (!TeleportManager.getPlayer(this.teleportManager).isDead()) {
            if (this.ticksRemaining == 2) {
                if (!this.ancientSpellbookTeleport) {
                    TeleportManager.getPlayer(this.teleportManager).getUpdateState().setAnimation(715);
                }
                if (TeleportManager.getPlayer(this.teleportManager).isInTenthSquadSigilInstance()) {
                    TeleportManager.getPlayer(this.teleportManager).clearTemporaryCutsceneNpcs();
                }
                if (TeleportManager.getPlayer(this.teleportManager).getInventoryManager().containsItem(4033)) {
                    int n = TeleportManager.getPlayer(this.teleportManager).getInventoryManager().getItemAmount(4033);
                    ItemStack itemStack = new ItemStack(4033, n);
                    TeleportManager.getPlayer(this.teleportManager).getInventoryManager().removeItem(itemStack);
                }
                if (TeleportManager.getPlayer(this.teleportManager).getInventoryManager().containsItem(431)) {
                    int n = TeleportManager.getPlayer(this.teleportManager).getInventoryManager().getItemAmount(431);
                    Object object = new ItemStack(431, n);
                    TeleportManager.getPlayer(this.teleportManager).getInventoryManager().removeItem((ItemStack)object);
                    object = TeleportManager.getPlayer(this.teleportManager);
                    ((Player)object).packetSender.sendGameMessage("Why is the rum gone?");
                }
                TeleportManager.getPlayer(this.teleportManager).moveTo(new Position(this.destinationX, this.destinationY, this.destinationPlane));
            }
        } else {
            this.ticksRemaining = 0;
        }
        if (this.ticksRemaining <= 0) {
            cycleEventContainer.stop();
        }
    }

    @Override
    public final void onStop() {
        TeleportManager.getPlayer(this.teleportManager).setActionLocked(false);
        TeleportManager.getPlayer(this.teleportManager).getAttributes().put("canTakeDamage", Boolean.TRUE);
    }
}

