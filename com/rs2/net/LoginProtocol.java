/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net;

import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.model.player.Player;
import com.rs2.model.player.PlayerConnectionState;
import com.rs2.net.DedicatedReactor;
import com.rs2.net.IsaacCipher;
import com.rs2.net.packet.PacketBuffer;
import com.rs2.net.packet.PacketReader;
import com.rs2.net.packet.PacketWriter;
import com.rs2.util.TextUtil;
import java.math.BigInteger;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.ArrayList;

public final class LoginProtocol {
    public static ArrayList activeLoginUsernames = new ArrayList();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void processLoginBuffer(Player player, ByteBuffer object) {
        switch (player.getConnectionState()) {
            case HANDSHAKE: {
                if (((Buffer)object).remaining() < 2) {
                    ((ByteBuffer)object).compact();
                    return;
                }
                int n = ((ByteBuffer)object).get() & 0xFF;
                ((ByteBuffer)object).get();
                if (n != 14) {
                    System.err.println("Invalid login request: " + n);
                    player.disconnect();
                    return;
                }
                object = PacketBuffer.allocateWriter(17);
                ((PacketWriter)object).writeLong(0L);
                ((PacketWriter)object).writeByte(0);
                ((PacketWriter)object).writeLong(new SecureRandom().nextLong());
                player.writePacketBuffer(((PacketWriter)object).getBuffer());
                player.setConnectionState(PlayerConnectionState.LOGIN_PAYLOAD);
                return;
            }
            case LOGIN_PAYLOAD: {
                if (((Buffer)object).remaining() < 2) {
                    ((ByteBuffer)object).compact();
                    return;
                }
                int n = ((ByteBuffer)object).get();
                if (n != 16 && n != 18) {
                    System.err.println("Invalid login type: " + n);
                    player.disconnect();
                    return;
                }
                n = ((ByteBuffer)object).get() & 0xFF;
                int n2 = n - 40;
                if (((Buffer)object).remaining() < n) {
                    ((ByteBuffer)object).compact();
                    return;
                }
                PacketReader packetReader = PacketBuffer.wrapReader((ByteBuffer)object);
                player.setLoginMagicByte(packetReader.readSignedByte());
                player.setClientBuild(packetReader.readSignedShort());
                packetReader.readSignedByte();
                int n3 = packetReader.readInt();
                if (-94395865 != n3) {
                    player.disconnect();
                    return;
                }
                n3 = 0;
                while (n3 < 9) {
                    packetReader.readInt();
                    ++n3;
                }
                if (ServerSettings.rsaEnabled) {
                    n3 = ((ByteBuffer)object).get() & 0xFF;
                    if (n3 != --n2) {
                        System.err.println("Encrypted packet size zero or negative : " + n2);
                        player.disconnect();
                        return;
                    }
                    byte[] byArray = new byte[n2];
                    ((ByteBuffer)object).get(byArray);
                    ByteBuffer byteBuffer = ByteBuffer.wrap(new BigInteger(byArray).modPow(ServerSettings.rsaPrivateExponent, ServerSettings.rsaModulus).toByteArray());
                    int n4 = byteBuffer.get() & 0xFF;
                    if (n4 != 10) {
                        System.err.println("Unable to decode RSA block properly!");
                        player.disconnect();
                        return;
                    }
                    long l = byteBuffer.getLong();
                    long l2 = byteBuffer.getLong();
                    object = new int[]{(int)(l >> 32), (int)l, (int)(l2 >> 32), (int)l2};
                    player.setInboundCipher(new IsaacCipher((int[])object));
                    int n5 = 0;
                    while (n5 < 4) {
                        Object object2 = object;
                        int n6 = n5++;
                        object2[n6] = object2[n6] + 50;
                    }
                    player.setOutboundCipher(new IsaacCipher((int[])object));
                    byteBuffer.getInt();
                    String string = TextUtil.readLine(byteBuffer).trim();
                    object = TextUtil.readLine(byteBuffer).trim();
                    player.setSubmittedPassword((String)object);
                    player.setUsername(TextUtil.capitalizeFirst(string));
                    player.sessionStartMillis = System.currentTimeMillis();
                } else {
                    packetReader.readSignedByte();
                    n3 = packetReader.readSignedByte();
                    if (n3 != 10) {
                        System.err.println("Unable to decode RSA block properly!");
                        player.disconnect();
                        return;
                    }
                    long l = packetReader.readLong();
                    long l3 = packetReader.readLong();
                    int[] nArray = new int[]{(int)(l >> 32), (int)l, (int)(l3 >> 32), (int)l3};
                    player.setInboundCipher(new IsaacCipher(nArray));
                    int n7 = 0;
                    while (n7 < 4) {
                        int n8 = n7++;
                        nArray[n8] = nArray[n8] + 50;
                    }
                    player.setOutboundCipher(new IsaacCipher(nArray));
                    packetReader.readInt();
                    String string = packetReader.readString().trim();
                    String string2 = packetReader.readString().trim();
                    player.setSubmittedPassword(string2);
                    player.setUsername(TextUtil.capitalizeFirst(string));
                    player.sessionStartMillis = System.currentTimeMillis();
                }
                player.setNameHash(TextUtil.encodeNameHash(player.getUsername().toLowerCase()));
                player.setConnectionState(PlayerConnectionState.LOGIN_QUEUED);
                if (activeLoginUsernames.contains(player.getUsername())) {
                    System.out.println("Player was already logging in " + player.getUsername());
                    player.disconnect();
                    return;
                }
                activeLoginUsernames.add(player.getUsername());
                if (!player.loadAndValidateLogin() || player.getConnectionState() != PlayerConnectionState.LOGIN_QUEUED) break;
                DedicatedReactor dedicatedReactor = DedicatedReactor.getInstance();
                synchronized (dedicatedReactor) {
                    DedicatedReactor.getInstance().getSelector().wakeup();
                    player.getSelectionKey().interestOps(player.getSelectionKey().interestOps() & 0xFFFFFFFE);
                    player.getSocketChannel().register(Server.getInstance().getSelector(), 1, player);
                    return;
                }
            }
        }
    }
}

