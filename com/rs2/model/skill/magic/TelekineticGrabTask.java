/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.magic;

import com.rs2.model.Position;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;

final class TelekineticGrabTask
extends CycleEvent {
    private final /* synthetic */ Player a;
    private final /* synthetic */ int b;
    private final /* synthetic */ int c;
    private final /* synthetic */ Position d;
    private final /* synthetic */ SpellDefinition e;

    TelekineticGrabTask(Player player, int n, int n2, Position position, SpellDefinition spellDefinition) {
        this.a = player;
        this.b = n;
        this.c = n2;
        this.d = position;
        this.e = spellDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.a.isCurrentActionSequence(this.b)) {
            cycleEventContainer.stop();
            return;
        }
        GroundItemManager.getInstance();
        Object object = GroundItemManager.findVisibleItem(this.a, this.c, this.d);
        if (object == null) {
            return;
        }
        if (this.a.gameMode != 0) {
            if (!((GroundItem)object).isRespawning() && ((GroundItem)object).getOwner() == null) {
                object = this.a;
                ((Player)object).packetSender.sendGameMessage("You are not playing on normal gamemode and cant pick that up.");
                cycleEventContainer.stop();
                return;
            }
            if (!((GroundItem)object).isRespawning() && ((GroundItem)object).getOwner().resolve() != this.a) {
                object = this.a;
                ((Player)object).packetSender.sendGameMessage("You are not playing on normal gamemode and cant pick that up.");
                cycleEventContainer.stop();
                return;
            }
        }
        switch (this.e) {
            case TELEKINETIC_GRAB: {
                if (this.c == 1583) {
                    object = this.a;
                    ((Player)object).packetSender.sendGameMessage("I can't use Telekinetic Grab on this object.");
                    cycleEventContainer.stop();
                    return;
                }
                if (this.c == 1419) {
                    if (this.a.aq(this.c)) {
                        object = this.a;
                        ((Player)object).packetSender.sendGameMessage("You already have a scythe, you don't need another one.");
                        cycleEventContainer.stop();
                        return;
                    }
                    this.a.bJ[3] = 1;
                }
                if (this.c >= 5509 && this.c <= 5515 && this.a.aq(this.c)) {
                    object = this.a;
                    ((Player)object).packetSender.sendGameMessage("I already have that pouch!");
                    cycleEventContainer.stop();
                    return;
                }
                if (!(this.a.getTelekineticTheatreController().g() || GameUtil.a(this.a.getPosition(), this.d, false) && GameUtil.a(this.a.getPosition(), this.d, 10))) {
                    return;
                }
                if (!this.a.getPosition().equals(this.d)) break;
                this.a.getTargetMovement().moveAwayFromOverlap();
                return;
            }
            default: {
                return;
            }
        }
        MagicSpellAction.castTelekineticGrab(this.a, this.e, this.c, this.d);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

