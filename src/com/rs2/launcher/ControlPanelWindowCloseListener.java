/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.launcher;

import com.rs2.launcher.ControlPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

final class ControlPanelWindowCloseListener
extends WindowAdapter {
    private /* synthetic */ ControlPanel a;

    ControlPanelWindowCloseListener(ControlPanel controlPanel) {
        this.a = controlPanel;
    }

    @Override
    public final void windowClosing(WindowEvent windowEvent) {
        if (ControlPanel.a.isEnabled()) {
            JOptionPane.showMessageDialog(ControlPanel.a(this.a), "Please use the shutdown button for closing!");
            return;
        }
        System.exit(0);
    }
}

