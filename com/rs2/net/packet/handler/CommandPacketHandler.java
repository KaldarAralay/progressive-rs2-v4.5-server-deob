/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net.packet.handler;

import com.rs2.model.player.Player;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;
import java.util.Arrays;

public final class CommandPacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket object) {
        switch (((IncomingPacket)object).getOpcode()) {
            case 103: {
                try {
                    object = ((IncomingPacket)object).getReader().readString();
                    String[] stringArray = ((String)object).split(" ");
                    String string = stringArray[0].toLowerCase();
                    player.handleCommand(string, Arrays.copyOfRange(stringArray, 1, stringArray.length), ((String)object).substring(((String)object).indexOf(" ") + 1));
                    return;
                }
                catch (Exception exception) {}
            }
        }
    }
}

