/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.randomevent;

import com.rs2.ServerSettings;
import com.rs2.model.GameplayHelper;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.randomevent.RandomEventManager;
import com.rs2.model.randomevent.RandomEventNpcDefinition;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;

final class RandomEventRollEvent
extends CycleEvent {
    private final /* synthetic */ Player a;

    RandomEventRollEvent(Player player) {
        this.a = player;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (this.a.fg() != null || this.a.isInTeleportRestrictedArea()) {
            return;
        }
        if (this.a.botEnabled) {
            return;
        }
        int n = GameUtil.g(70);
        boolean bl = true;
        if (ServerSettings.freeToPlayWorld || !this.a.isMember()) {
            bl = false;
        }
        if (n == 3 && !bl) {
            n = 1;
        }
        switch (n) {
            case 0: {
                this.a.getSingleCombatTimer().setDelayTicks(0);
                this.a.getSingleCombatTimer().reset();
                GameplayHelper.a(this.a, new Npc(411), true, false);
                return;
            }
            case 1: {
                RandomEventManager.a(this.a, RandomEventNpcDefinition.a);
                return;
            }
            case 2: {
                RandomEventManager.a(this.a, RandomEventNpcDefinition.b);
                return;
            }
            case 3: {
                RandomEventManager.a(this.a, RandomEventNpcDefinition.c);
            }
        }
    }

    @Override
    public final void onStop() {
    }
}

