/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.launcher;

import com.rs2.launcher.ControlPanel;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public final class MapVerticalScrollListener
implements AdjustmentListener {
    private /* synthetic */ ControlPanel a;

    public MapVerticalScrollListener(ControlPanel controlPanel) {
        this.a = controlPanel;
    }

    @Override
    public final void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
        int n;
        this.a.e = n = adjustmentEvent.getValue();
        int cfr_ignored_0 = this.a.c;
        ControlPanel.b.repaint();
    }
}

