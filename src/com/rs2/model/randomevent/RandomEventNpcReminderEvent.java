/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.randomevent;

import com.rs2.model.GameplayHelper;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;

final class RandomEventNpcReminderEvent
extends CycleEvent {
    private int a = 120;
    private int b = -1;
    private String c;
    private final /* synthetic */ Npc d;
    private final /* synthetic */ String[] e;
    private final /* synthetic */ int f;
    private final /* synthetic */ Player g;

    RandomEventNpcReminderEvent(Player player, Npc npc, String[] stringArray, int n) {
        this.g = player;
        this.d = npc;
        this.e = stringArray;
        this.f = n;
        this.c = GameUtil.c(player.getUsername());
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (this.d.getInteractionTarget() != null && this.d.getInteractionTarget() == this.d.getOwnerPlayer() && this.b < 0) {
            this.b = 240;
        }
        if (this.b > 0) {
            --this.b;
            return;
        }
        if (this.a > 0 && this.b < 0) {
            switch (this.a) {
                case 120: {
                    this.d.getUpdateState().setForcedTextAndMarkUpdated(this.e[0].replaceAll("1", this.c));
                    break;
                }
                case 90: {
                    this.d.getUpdateState().setForcedTextAndMarkUpdated(this.e[1].replaceAll("1", this.c));
                    break;
                }
                case 60: {
                    this.d.getUpdateState().setForcedTextAndMarkUpdated(this.e[2].replaceAll("1", this.c));
                    break;
                }
                case 30: {
                    this.d.getUpdateState().setForcedTextAndMarkUpdated(this.e[3].replaceAll("1", this.c));
                    break;
                }
                case 2: {
                    if (this.d.getNpcId() != 409) break;
                    this.d.getUpdateState().setAnimation(863);
                }
            }
            --this.a;
            return;
        }
        GameplayHelper.a(this.d);
        if (this.d.getOwnerPlayer() != null && this.b != 0) {
            if (this.f == 2541) {
                this.g.getSingleCombatTimer().setDelayTicks(0);
                this.g.getSingleCombatTimer().reset();
                GameplayHelper.a(this.g, new Npc(this.f + GameplayHelper.d(this.g.getCombatLevel())), true, false);
            } else if (this.f > 0) {
                this.g.getSingleCombatTimer().setDelayTicks(0);
                this.g.getSingleCombatTimer().reset();
                GameplayHelper.a(this.g, new Npc(this.f), true, false);
            } else {
                Player player = this.g;
                player.packetSender.sendStillGraphic(86, this.d.getPosition(), 0x640000);
                player = this.g;
                player.packetSender.sendSoundEffect(300, 1, 0);
            }
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

