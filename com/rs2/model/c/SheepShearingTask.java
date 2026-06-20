/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.c;

import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;

final class SheepShearingTask
extends CycleEvent {
    private final /* synthetic */ Player a;
    private final /* synthetic */ Npc b;

    SheepShearingTask(Player player, Npc npc) {
        this.a = player;
        this.b = npc;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!GameUtil.a(0.75)) {
            Player player = this.a;
            player.packetSender.sendGameMessage("The sheep manages to get away!");
            int n = this.b.getSpawnMinPosition().getX();
            int n2 = this.b.getSpawnMinPosition().getY();
            int n3 = this.b.getSpawnMaxPosition().getX() - this.b.getSpawnMinPosition().getX();
            int n4 = this.b.getSpawnMaxPosition().getY() - this.b.getSpawnMinPosition().getY();
            n3 = GameUtil.a().nextInt(n3);
            n4 = GameUtil.a().nextInt(n4);
            Position position = new Position(n + n3, n2 + n4, this.b.getPosition().getPlane());
            this.b.queuePathTo(position, true);
            if (this.a.botEnabled) {
                this.a.interactWithBotNpcTargets(this.a.botInteractionTargetIds);
            }
            cycleEventContainer.stop();
            return;
        }
        this.a.getInventoryManager().addItem(new ItemStack(1737));
        this.b.getUpdateState().setForcedText("Baa!");
        Player player = this.a;
        player.packetSender.sendSoundEffect(1314, 1, 0);
        player = this.a;
        player.packetSender.sendGameMessage("You manage to shear the sheep.");
        this.b.transformToNpcId(42, 10);
        if (this.a.botEnabled) {
            this.a.interactWithBotNpcTargets(this.a.botInteractionTargetIds);
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.a.n(false);
    }
}

