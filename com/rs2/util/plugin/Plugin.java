/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.util.plugin;

import com.rs2.util.plugin.PluginType;

public abstract class Plugin {
    private PluginType a = PluginType.a;

    public Plugin(PluginType pluginType) {
        this.a = pluginType;
    }

    public abstract String a();

    public abstract String b();

    public abstract double c();

    public final PluginType d() {
        return this.a;
    }
}

