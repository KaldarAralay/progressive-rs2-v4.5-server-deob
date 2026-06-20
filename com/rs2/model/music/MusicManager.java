/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.music;

import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.music.MusicAreaDefinition;
import com.rs2.model.music.MusicTrackDefinition;
import com.rs2.model.player.Player;
import com.rs2.util.CharacterFileManager;
import com.rs2.util.GameUtil;
import java.util.Arrays;
import java.util.List;

public final class MusicManager {
    private static List buttonlessTrackIds = Arrays.asList(648, 649, 650, 651, 652);
    private int currentAreaId = -1;
    private int currentTrackId = -1;

    public final void updateForPlayerPosition(Player player) {
        int n;
        int n2;
        int n3;
        int n4;
        Object object = player;
        MusicManager musicManager = this;
        int n5 = ((Entity)object).getPosition().getX();
        int n6 = ((Entity)object).getPosition().getY();
        Object object2 = new int[5];
        int n7 = 0;
        int n8 = 0;
        while (n8 < MusicAreaDefinition.areaCount) {
            boolean bl;
            block16: {
                block18: {
                    MusicAreaDefinition musicAreaDefinition;
                    int n9;
                    block17: {
                        if (n8 == 0) {
                            n7 = 0;
                        }
                        int n10 = n6;
                        n4 = n5;
                        n9 = n8;
                        n3 = GameUtil.getRegionId(n4, n10);
                        musicAreaDefinition = MusicAreaDefinition.forAreaId(n9);
                        if (musicAreaDefinition.getRegionCount() != 0) break block17;
                        if (!musicAreaDefinition.getAreaBounds().contains(new Position(n4, n10, 0))) break block18;
                        bl = true;
                        break block16;
                    }
                    int[] nArray = musicAreaDefinition.getRegionIds();
                    n9 = 0;
                    while (n9 < musicAreaDefinition.getRegionCount()) {
                        if (n3 == nArray[n9]) {
                            bl = true;
                            break block16;
                        }
                        ++n9;
                    }
                }
                bl = false;
            }
            if (bl) {
                object2[n7] = n8;
                ++n7;
            }
            ++n8;
        }
        n4 = n7;
        Object object3 = object2;
        n6 = 0;
        n3 = -1;
        int n11 = 0;
        while (n11 < n4) {
            MusicAreaDefinition musicAreaDefinition = MusicAreaDefinition.forAreaId(object3[n11]);
            if (n11 == 0) {
                n6 = musicAreaDefinition.getPriority();
                n3 = musicAreaDefinition.getAreaId();
            } else if (n6 < musicAreaDefinition.getPriority()) {
                n6 = musicAreaDefinition.getPriority();
                n3 = musicAreaDefinition.getAreaId();
            }
            ++n11;
        }
        musicManager.currentAreaId = n3;
        if (musicManager.currentAreaId != -1) {
            int n12;
            MusicTrackDefinition musicTrackDefinition;
            MusicAreaDefinition musicAreaDefinition = MusicAreaDefinition.forAreaId(musicManager.currentAreaId);
            n5 = musicAreaDefinition.getTrackId();
            if (((Player)object).eo) {
                musicManager.currentTrackId = n5;
            }
            if ((musicTrackDefinition = MusicTrackDefinition.forTrackId(n5)).getUnlockConfigId() != -1 && ((n12 = ((Player)object).ep[musicTrackDefinition.getUnlockConfigId()]) & (n7 = musicTrackDefinition.getUnlockBitMask())) == 0) {
                MusicTrackDefinition musicTrackDefinition2 = musicTrackDefinition;
                object3 = object;
                ((Player)object).eo = true;
                int n13 = musicTrackDefinition2.getUnlockConfigId();
                n3 = musicTrackDefinition2.getUnlockBitMask();
                int n14 = n13;
                object3.ep[n14] = object3.ep[n14] + n3;
                object2 = object3;
                object3.packetSender.sendConfig(n13, object3.ep[n13]);
                object2 = object3;
                object3.packetSender.sendGameMessage("@red@You have unlocked a new music track: " + musicTrackDefinition2.getName());
                musicManager.currentTrackId = n5;
            }
            n2 = musicManager.currentTrackId;
        } else {
            n2 = n = -1;
        }
        if (n2 != -1) {
            if (n == player.cg) {
                return;
            }
            player.cg = n;
            object = MusicTrackDefinition.forTrackId(n);
            if (((MusicTrackDefinition)object).getButtonId() != -1 || buttonlessTrackIds.contains(n)) {
                Player player2 = player;
                object2 = player2;
                player2.packetSender.sendMusicTrack((MusicTrackDefinition)object);
            }
        }
    }

    public static void unlockTrack(Player player, int n) {
        int n2;
        MusicTrackDefinition musicTrackDefinition = MusicTrackDefinition.forTrackId(n);
        int n3 = player.ep[musicTrackDefinition.getUnlockConfigId()];
        if ((n3 & (n2 = musicTrackDefinition.getUnlockBitMask())) == 0) {
            int n4;
            int n5 = n4 = musicTrackDefinition.getUnlockConfigId();
            player.ep[n5] = player.ep[n5] + n2;
            Player player2 = player;
            player2.packetSender.sendConfig(n4, player.ep[n4]);
        }
    }

    public static void unlockAllTracks(Player player) {
        int n = 0;
        while (n < MusicTrackDefinition.trackCount) {
            int n2;
            int n3;
            MusicTrackDefinition musicTrackDefinition = MusicTrackDefinition.forTrackId(n);
            if (musicTrackDefinition.getUnlockConfigId() != -1 && ((n3 = player.ep[musicTrackDefinition.getUnlockConfigId()]) & (n2 = musicTrackDefinition.getUnlockBitMask())) == 0) {
                int n4;
                int n5 = n4 = musicTrackDefinition.getUnlockConfigId();
                player.ep[n5] = player.ep[n5] + n2;
            }
            ++n;
        }
        n = 0;
        while (n < CharacterFileManager.musicUnlockConfigIds.length) {
            int n6 = CharacterFileManager.musicUnlockConfigIds[n];
            Player player2 = player;
            player2.packetSender.sendConfig(n6, player.ep[n6]);
            ++n;
        }
    }

    public static boolean isTrackUnlocked(Player player, int n) {
        int n2;
        MusicTrackDefinition musicTrackDefinition = MusicTrackDefinition.forTrackId(n);
        int n3 = player.ep[musicTrackDefinition.getUnlockConfigId()];
        return (n3 & (n2 = musicTrackDefinition.getUnlockBitMask())) != 0 && n2 != -1;
    }
}

