/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.duel;

import com.rs2.model.player.Player;
import java.util.ArrayList;

public final class DuelHistory {
    private static ArrayList a = new ArrayList();

    public static void a(Player player, Player player2) {
        if (a.size() >= 50) {
            a.remove(0);
        }
        a.add(String.valueOf(player.getUsername()) + " (" + player.getCombatLevel() + ") beat " + player2.getUsername() + " (" + player2.getCombatLevel() + ")");
    }

    public static void a(Player player) {
        Player player2;
        Player player3 = player;
        int n = 6402;
        while (n < 6412) {
            player2 = player3;
            player2.packetSender.sendInterfaceText("", n);
            ++n;
        }
        n = 8578;
        while (n < 8618) {
            player2 = player3;
            player2.packetSender.sendInterfaceText("", n);
            ++n;
        }
        if (a.size() == 0) {
            player2 = player;
            player2.packetSender.sendInterfaceText("No duel have been started yet.", 6402);
        } else {
            int n2 = 6402;
            while (n2 < 6412) {
                if (a.size() - 1 - (n2 - 6402) >= 0) {
                    player2 = player;
                    player2.packetSender.sendInterfaceText((String)a.get(a.size() - 1 - (n2 - 6402)), n2);
                }
                ++n2;
            }
            n2 = 8578;
            while (n2 < 8618) {
                if (a.size() - 10 - (n2 - 8578) >= 0) {
                    player2 = player;
                    player2.packetSender.sendInterfaceText((String)a.get(a.size() - 10 - (n2 - 8578)), n2);
                }
                ++n2;
            }
        }
        player2 = player;
        player2.packetSender.showInterface(6308);
    }
}

