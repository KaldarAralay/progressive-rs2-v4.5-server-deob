/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest;

import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.model.quest.QuestHook;
import com.rs2.model.quest.event.ChristmasDropEventHook;
import com.rs2.model.quest.event.EasterEggDropEventHook;
import com.rs2.model.quest.event.HalloweenMaskDropEventHook;
import com.rs2.model.quest.event.NoopQuestEventHook;
import com.rs2.model.quest.event.ServerMaintenanceEventHook;

public final class QuestEventRegistry {
    public static int a = 0;
    private static int b = 0;
    private static int c = 1;
    private static int d = 2;
    private static int e = 3;
    private static QuestHook[] f = new QuestHook[]{new NoopQuestEventHook(-1), new ServerMaintenanceEventHook(-1, 0), new HalloweenMaskDropEventHook(-1, c), new ChristmasDropEventHook(-1, d), new EasterEggDropEventHook(-1, e)};

    public static QuestHook a(int n) {
        int n2 = 0;
        while (n2 < f.length) {
            int n3 = f[n2].b();
            if (n == n3 && f[n2].d()) {
                return f[n2];
            }
            ++n2;
        }
        return f[0];
    }

    public static void a() {
        a = f.length;
        int n = 0;
        while (n < f.length) {
            if (f[n].c() == 0) {
                f[n].e();
                f[n].a(true);
            }
            if (f[n].c() == c && Server.f && ServerSettings.holidayItemDropsEnabled) {
                f[n].e();
                f[n].a(true);
            }
            if (f[n].c() == d && Server.g && ServerSettings.holidayItemDropsEnabled) {
                f[n].e();
                f[n].a(true);
            }
            if (f[n].c() == e && Server.h && ServerSettings.holidayItemDropsEnabled) {
                f[n].e();
                f[n].a(true);
            }
            ++n;
        }
    }
}

