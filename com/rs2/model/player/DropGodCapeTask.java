/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.player;

import com.rs2.model.Position;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class DropGodCapeTask
extends TickTask {
    private final /* synthetic */ Player a;
    private final /* synthetic */ int b;

    DropGodCapeTask(Player player, int n, Player player2, int n2) {
        this.a = player2;
        this.b = n2;
        super(4);
    }

    @Override
    public final void execute() {
        int n = this.a.getPosition().getX();
        int n2 = this.a.getPosition().getY() + 1;
        int n3 = 0;
        if (this.b == 2873) {
            n3 = 2412;
        } else if (this.b == 2874) {
            n3 = 2414;
        } else if (this.b == 2875) {
            n3 = 2413;
        }
        GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(n3, 1), this.a, new Position(n, n2, 0)));
        Player player = this.a;
        player.packetSender.sendStillGraphicToNearbyPlayers(86, n, n2, 0, 0);
        this.a.setActionLocked(false);
        DialogueManager.continueContextDialogue(1, this.a, this.b, 10, 0, this.a.getPosition().getX(), this.a.getPosition().getY());
        this.stop();
    }
}

