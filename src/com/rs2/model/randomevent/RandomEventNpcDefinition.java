/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.randomevent;

public enum RandomEventNpcDefinition {
    a(956, 2429, new String[]{"'Ere, matey, 'ave some 'o the good stuff.", "Dun ignore your matey!", "I hates you 1!", "Aww comeon, talk to ikle me 1!"}),
    b(409, 0, new String[]{"Greetings 1!", "I need to talk to you 1.", "1 please speak with me.", "1 I'm in a hurry, please talk to me!"}),
    g(2476, 2476, new String[]{"Hello 1!", "I need to talk to you 1.", "1 please speak with me.", "1 I'm in a hurry, please talk to me!"}),
    c(2540, 2541, new String[]{"Hey 1!", "I need to talk to you 1.", "1 please speak with me.", "1 I'm in a hurry, please talk to me!"});

    int d;
    int e;
    String[] f;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private RandomEventNpcDefinition(String[] stringArray) {
        void var5_3;
        void var4_2;
        this.d = (int)stringArray;
        this.e = var4_2;
        this.f = var5_3;
    }
}

