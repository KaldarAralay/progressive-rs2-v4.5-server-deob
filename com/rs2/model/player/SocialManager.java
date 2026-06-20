/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.player;

import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.util.ChatTextCodec;
import com.rs2.util.TextUtil;

public final class SocialManager {
    private final Player player;
    private int privateMessageCounter = 1;

    public SocialManager(Player player) {
        this.player = player;
    }

    public final void initializePrivateMessaging() {
        Player player = this.player;
        player.packetSender.sendPrivateMessagingStatus(2);
        this.refreshFriendStatuses(false);
    }

    /*
     * Unable to fully structure code
     */
    public final void refreshFriendStatuses(boolean var1_1) {
        var2_2 = 0;
        while (var2_2 < this.player.getFriendsList().length) {
            block10: {
                block9: {
                    if (this.player.getFriendsList()[var2_2] == 0L) break block10;
                    var5_8 = this.player;
                    v0 = var5_8.packetSender;
                    v1 = var10_13 = this.player.getFriendsList()[var2_2];
                    var3_4 = this;
                    var7_11 = World.getPlayers();
                    var6_10 = var7_11.length;
                    var5_7 = 0;
                    while (var5_7 < var6_10) {
                        block11: {
                            var4_5 = var7_11[var5_7];
                            if (var4_5 == null || var4_5.getNameHash() == var3_4.player.getNameHash() || var4_5.getNameHash() != var10_13) break block11;
                            if (var4_5.getPrivateChatMode() == 0) ** GOTO lbl-1000
                            if (var4_5.getPrivateChatMode() == 1) {
                                var4_5.getSocialManager();
                                ** if (!SocialManager.containsNameHash((long[])var4_5.getFriendsList(), (long)var3_4.player.getNameHash())) goto lbl-1000
                            }
                            ** GOTO lbl-1000
lbl-1000:
                            // 2 sources

                            {
                                v2 = 1;
                                ** GOTO lbl28
                            }
lbl-1000:
                            // 2 sources

                            {
                                v2 = 0;
                            }
                            break block9;
                        }
                        ++var5_7;
                    }
                    v2 = 0;
                }
                v0.sendFriendStatus(v1, v2);
            }
            ++var2_2;
        }
        var2_3 = this.player.getNameHash();
        var8_14 = World.getPlayers();
        var7_12 = var8_14.length;
        var6_10 = 0;
        while (var6_10 < var7_12) {
            block12: {
                var5_9 = var8_14[var6_10];
                if (var5_9 == null) break block12;
                var5_9.getSocialManager();
                if (!SocialManager.containsNameHash(var5_9.getFriendsList(), var2_3)) break block12;
                if (var1_1 || this.player.getPrivateChatMode() == 2) ** GOTO lbl-1000
                if (this.player.getPrivateChatMode() == 1) {
                    this.player.getSocialManager();
                    ** if (SocialManager.containsNameHash((long[])this.player.getFriendsList(), (long)var5_9.getNameHash())) goto lbl-1000
                }
                ** GOTO lbl-1000
lbl-1000:
                // 2 sources

                {
                    var4_6 = 0;
                    ** GOTO lbl53
                }
lbl-1000:
                // 2 sources

                {
                    var4_6 = 1;
                }
lbl53:
                // 2 sources

                var5_9.packetSender.sendFriendStatus(var2_3, var4_6);
            }
            ++var6_10;
        }
    }

    public final void addFriend(long l) {
        if (SocialManager.countEntries(this.player.getFriendsList()) >= 200) {
            Player player = this.player;
            player.packetSender.sendGameMessage("Your friends list is full.");
            return;
        }
        if (SocialManager.containsNameHash(this.player.getFriendsList(), l)) {
            Player player = this.player;
            player.packetSender.sendGameMessage(TextUtil.decodeNameHash(l) + " is already on your friends list.");
            return;
        }
        int n = SocialManager.findFreeSlot(this.player.getFriendsList());
        this.player.getFriendsList()[n] = l;
        this.refreshFriendStatuses(false);
    }

    public final void addIgnore(long l) {
        if (SocialManager.countEntries(this.player.getIgnoreList()) >= 100) {
            Player player = this.player;
            player.packetSender.sendGameMessage("Your ignores list is full.");
            return;
        }
        if (SocialManager.containsNameHash(this.player.getIgnoreList(), l)) {
            Player player = this.player;
            player.packetSender.sendGameMessage(TextUtil.decodeNameHash(l) + " is already on your ignores list.");
            return;
        }
        int n = SocialManager.findFreeSlot(this.player.getIgnoreList());
        this.player.getIgnoreList()[n] = l;
    }

    public final void sendPrivateMessage(Player player, long l, byte[] byArray, int n) {
        Player[] playerArray = World.getPlayers();
        int n2 = playerArray.length;
        int n3 = 0;
        while (n3 < n2) {
            Player player2 = playerArray[n3];
            if (player2 != null && player2.getNameHash() == l) {
                boolean bl;
                block5: {
                    long l2 = this.player.getNameHash();
                    SocialManager socialManager = player2.getSocialManager();
                    long[] lArray = socialManager.player.getIgnoreList();
                    int n4 = lArray.length;
                    int n5 = 0;
                    while (n5 < n4) {
                        long l3 = lArray[n5];
                        if (l3 == l2) {
                            bl = true;
                            break block5;
                        }
                        ++n5;
                    }
                    bl = false;
                }
                if (!bl) {
                    int n6 = player.getPlayerRights();
                    int n7 = player.gameMode;
                    player2.packetSender.sendPrivateMessage(player.getNameHash(), n6, 0, n7, byArray, n);
                    ChatTextCodec.decode(byArray, n);
                    return;
                }
            }
            ++n3;
        }
    }

    public final void removeFromList(long[] lArray, long l) {
        int n = 0;
        while (n < lArray.length) {
            if (lArray[n] == l) {
                lArray[n] = 0L;
                break;
            }
            ++n;
        }
        this.refreshFriendStatuses(false);
    }

    private static boolean containsNameHash(long[] lArray, long l) {
        int n = 0;
        while (n < lArray.length) {
            if (lArray[n] == l) {
                return true;
            }
            ++n;
        }
        return false;
    }

    private static int countEntries(long[] lArray) {
        int n = 0;
        long[] lArray2 = lArray;
        int n2 = lArray.length;
        int n3 = 0;
        while (n3 < n2) {
            long l = lArray2[n3];
            if (l > 0L) {
                ++n;
            }
            ++n3;
        }
        return n;
    }

    private static int findFreeSlot(long[] lArray) {
        int n = 0;
        while (n < lArray.length) {
            if (lArray[n] == 0L) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    public final int nextPrivateMessageId() {
        return this.privateMessageCounter++;
    }
}

