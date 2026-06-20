/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.launcher;

import java.io.PrintStream;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public final class ConsolePrintStream
extends PrintStream {
    private JTextArea a;

    public ConsolePrintStream(JTextArea jTextArea, JScrollPane jScrollPane) {
        super(System.out);
        this.a = jTextArea;
    }

    @Override
    public final void println(Object object) {
        this.a(object);
    }

    private void a(Object object) {
        this.a.append(object.toString() + "\n");
    }

    @Override
    public final void println(String string) {
        this.a(string);
    }

    @Override
    public final void println() {
        this.println("println\n");
    }

    @Override
    public final void print(Object object) {
        this.b(object);
    }

    private void b(Object object) {
        this.a.append(object.toString());
    }

    @Override
    public final void print(String string) {
        this.b(string);
    }
}

