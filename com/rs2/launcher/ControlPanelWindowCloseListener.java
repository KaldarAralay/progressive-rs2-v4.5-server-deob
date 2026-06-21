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
    private /* synthetic */ ControlPanel controlPanel;

    ControlPanelWindowCloseListener(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }

    @Override
    public final void windowClosing(WindowEvent windowEvent) {
        if (ControlPanel.shutdownServerButton.isEnabled()) {
            JOptionPane.showMessageDialog(ControlPanel.getTabbedPane(this.controlPanel), "Please use the shutdown button for closing!");
            return;
        }
        System.exit(0);
    }
}

