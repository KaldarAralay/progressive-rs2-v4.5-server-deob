/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net.packet.handler;

import com.rs2.model.EntityTargetMovement;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.net.packet.handler.PlayerInteractionPacketHandler;
import com.rs2.util.GameUtil;

final class ItemOnPlayerTask
extends TickTask {
    private final /* synthetic */ Player a;
    private final /* synthetic */ Player b;
    private final /* synthetic */ int c;
    private final /* synthetic */ ItemStack d;
    private final /* synthetic */ int e;

    ItemOnPlayerTask(PlayerInteractionPacketHandler playerInteractionPacketHandler, int n, Player player, Player player2, int n2, ItemStack itemStack, int n3) {
        this.a = player;
        this.b = player2;
        this.c = n2;
        this.d = itemStack;
        this.e = n3;
        super(1);
    }

    @Override
    public final void execute() {
        if (this.a == null || this.a.isDead() || !this.b.isCurrentActionSequence(this.c)) {
            EntityTargetMovement.clearMovementTarget(this.b);
            this.b.setInteractionTarget(null);
            this.b.getMovementQueue().clear();
            this.stop();
            return;
        }
        if (this.b.isWithinReach(this.a, 1) && !this.b.isOverlapping(this.a) && !EntityTargetMovement.isDiagonalTo(this.b.getPosition(), this.a.getPosition())) {
            if (this.d.getDefinition().g() && (this.b.gameMode != 0 || this.a.gameMode != 0)) {
                this.b.K = this.d;
                this.b.L = this.a;
                DialogueManager.startDialogue(this.b, 12345);
                EntityTargetMovement.clearMovementTarget(this.b);
                this.b.getUpdateState().setFacePosition(this.a.getPosition());
                this.b.setInteractionTarget(null);
                this.b.getMovementQueue().clear();
                this.stop();
            }
            switch (this.d.getId()) {
                case 962: {
                    this.b.getInventoryManager().removeItem(this.d);
                    Player player = this.b;
                    player.packetSender.sendGameMessage("You pull the cracker with " + this.a.getUsername() + "...");
                    player = this.a;
                    player.packetSender.sendGameMessage(String.valueOf(this.b.getUsername()) + " pulls a Christmas cracker with you...");
                    if (GameUtil.g(1) == 1) {
                        player = this.b;
                        player.packetSender.sendGameMessage("  ... and get a partyhat! Merry Christmas!");
                        player = this.a;
                        player.packetSender.sendGameMessage("  ... and they get a partyhat! But have some coins anyways, Merry Christmas!");
                        this.a.getInventoryManager().addItem(new ItemStack(995, 5 + GameUtil.g(100)));
                        this.b.getInventoryManager().a(new ItemStack(1038 + (GameUtil.g(5) << 1)), this.e);
                        break;
                    }
                    player = this.a;
                    player.packetSender.sendGameMessage("  ... and you get a partyhat! Merry Christmas!");
                    player = this.b;
                    player.packetSender.sendGameMessage("  ... and they get a partyhat! But have some coins anyways, Merry Christmas!");
                    this.b.getInventoryManager().a(new ItemStack(995, 5 + GameUtil.g(100)), this.e);
                    this.a.getInventoryManager().addItem(new ItemStack(1038 + (GameUtil.g(5) << 1)));
                    break;
                }
                default: {
                    Player player = this.b;
                    player.packetSender.sendGameMessage("Nothing interesting happens.");
                }
            }
            EntityTargetMovement.clearMovementTarget(this.b);
            this.b.getUpdateState().setFacePosition(this.a.getPosition());
            this.b.setInteractionTarget(null);
            this.b.getMovementQueue().clear();
            this.stop();
        }
    }
}

