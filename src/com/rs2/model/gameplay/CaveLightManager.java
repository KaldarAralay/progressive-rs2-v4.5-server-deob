/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay;

import com.rs2.model.World;
import com.rs2.model.gameplay.CaveInsectSwarmTask;
import com.rs2.model.gameplay.SwampGasExplosionTask;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import com.rs2.util.RectangularArea;

public final class CaveLightManager {
    private static RectangularArea[] a = new RectangularArea[]{new RectangularArea(3155, 9584, 3174, 9596), new RectangularArea(3203, 9551, 3213, 9560)};
    private static int[] b = new int[]{12693, 12949};

    static boolean a(Player player) {
        int n = GameUtil.a(player.getPosition().getX(), player.getPosition().getY());
        int[] nArray = b;
        int n2 = b.length;
        int n3 = 0;
        while (n3 < n2) {
            int n4 = nArray[n3];
            if (n4 == n) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    static boolean b(Player player) {
        Object object;
        RectangularArea[] rectangularAreaArray = a;
        int n = a.length;
        int n2 = 0;
        while (n2 < n) {
            object = rectangularAreaArray[n2];
            if (((RectangularArea)object).contains(player.getPosition())) {
                return true;
            }
            ++n2;
        }
        if (player.eI == 1 && (object = player.eJ()) != null) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("Your " + ((ItemStack)object).getDefinition().getName().toLowerCase() + " stops flaring.");
        }
        player.eI = 0;
        return false;
    }

    public static boolean c(Player player) {
        Object object = player.eJ();
        if (CaveLightManager.b(player) && object != null && player.eI == 0) {
            player.eI = 1;
            Player player2 = player;
            player2.packetSender.sendGameMessage("Your " + ((ItemStack)object).getDefinition().getName().toLowerCase() + " flares brightly!");
            object = new SwampGasExplosionTask(7, player);
            World.getTaskScheduler().schedule((TickTask)object);
        }
        if (player.er == 1657) {
            return true;
        }
        if (CaveLightManager.a(player)) {
            object = new CaveInsectSwarmTask(15, player);
            World.getTaskScheduler().schedule((TickTask)object);
            player.er = 1657;
            return true;
        }
        return false;
    }
}

