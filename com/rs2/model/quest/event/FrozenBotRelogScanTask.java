/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.event;

import com.rs2.model.Entity;
import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.model.quest.event.FrozenBotReloginTask;
import com.rs2.model.quest.event.ServerMaintenanceEventHook;
import com.rs2.model.task.TickTask;
import java.util.ArrayList;
import java.util.Iterator;

final class FrozenBotRelogScanTask
extends TickTask {
    FrozenBotRelogScanTask(ServerMaintenanceEventHook serverMaintenanceEventHook, int n) {
        super(100);
    }

    @Override
    public final void execute() {
        Object object;
        Object object2 = new ArrayList();
        ArrayList<String> arrayList = new ArrayList<String>();
        Player[] playerArray = World.f();
        int n = playerArray.length;
        int n2 = 0;
        while (n2 < n) {
            object = playerArray[n2];
            if (object != null && ((Player)object).de && (((Player)object).az == 4 || ((Player)object).az == 0) && ((Player)object).aL()) {
                ((ArrayList)object2).add(object);
                arrayList.add(((Player)object).getUsername());
                System.out.println("Detected possibly frozen bot: " + ((Player)object).getUsername() + " at: " + ((Entity)object).getPosition() + ", trying to fix by relogging.");
            }
            ++n2;
        }
        if (((ArrayList)object2).size() > 0) {
            Iterator iterator = ((ArrayList)object2).iterator();
            while (iterator.hasNext()) {
                object2 = object = (Player)iterator.next();
                ((Player)object).packetSender.sendLogout();
                ((Player)object).disconnect();
            }
            object = new FrozenBotReloginTask(this, 30, arrayList);
            World.getTaskScheduler().schedule((TickTask)object);
        }
    }
}

