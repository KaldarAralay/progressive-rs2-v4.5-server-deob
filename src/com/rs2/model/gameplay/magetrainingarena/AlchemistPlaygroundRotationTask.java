/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.magetrainingarena;

import com.rs2.model.World;
import com.rs2.model.gameplay.magetrainingarena.AlchemistPlaygroundController;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class AlchemistPlaygroundRotationTask
extends TickTask {
    AlchemistPlaygroundRotationTask(int n) {
        super(70);
    }

    @Override
    public final void execute() {
        AlchemistPlaygroundController.e();
        AlchemistPlaygroundController.f();
        int n = AlchemistPlaygroundController.g()[AlchemistPlaygroundController.b.nextInt(AlchemistPlaygroundController.g().length)];
        while (n == AlchemistPlaygroundController.c) {
            n = AlchemistPlaygroundController.g()[AlchemistPlaygroundController.b.nextInt(AlchemistPlaygroundController.g().length)];
        }
        AlchemistPlaygroundController.c = n;
        Player[] playerArray = World.f();
        int n2 = playerArray.length;
        int n3 = 0;
        while (n3 < n2) {
            Player player = playerArray[n3];
            if (player != null && player.getAlchemistPlaygroundController().b()) {
                player.getAlchemistPlaygroundController().d();
            }
            ++n3;
        }
    }
}

