/*
 * Decompiled with CFR 0.152.
 */
package com.rs2;

import com.rs2.ConnectionThrottleSettings;
import java.util.concurrent.ConcurrentHashMap;

public final class ConnectionThrottle {
    private static ConcurrentHashMap a = new ConcurrentHashMap();

    public static boolean a(String string) {
        if (!ConnectionThrottleSettings.a) {
            return false;
        }
        Integer n = a.putIfAbsent(string, 1);
        if (n != null && n == 3) {
            return false;
        }
        n = n == null ? 0 : n;
        a.replace(string, n + 1);
        return true;
    }

    public static void b(String string) {
        Integer n = (Integer)a.get(string);
        if (n == null) {
            return;
        }
        if (n == 1) {
            a.remove(string);
            return;
        }
        if (n != null) {
            a.replace(string, n - 1);
        }
    }
}

