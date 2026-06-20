/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.cache;

import com.rs2.cache.CacheArchiveEntry;
import com.rs2.cache.CacheFile;
import com.rs2.model.clue.ChallengeQuestion;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.fletching.logs.AcheyLogFletchingAction;
import com.rs2.model.skill.fletching.logs.MagicLogFletchingAction;
import com.rs2.model.skill.fletching.logs.MapleLogFletchingAction;
import com.rs2.model.skill.fletching.logs.NormalLogFletchingAction;
import com.rs2.model.skill.fletching.logs.OakLogFletchingAction;
import com.rs2.model.skill.fletching.logs.WillowLogFletchingAction;
import com.rs2.model.skill.fletching.logs.YewLogFletchingAction;
import com.rs2.net.packet.PacketSender;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

public class CacheArchive {
    private boolean wholeArchiveDecompressed = false;
    private ByteBuffer archiveBuffer;
    private Map entriesByNameHash = new HashMap();

    public CacheArchive(CacheFile object) {
        object = ((CacheFile)object).getBuffer();
        ((ByteBuffer)object).position(0);
        int n = (((ByteBuffer)object).get() & 0xFF) << 16 | (((ByteBuffer)object).get() & 0xFF) << 8 | ((ByteBuffer)object).get() & 0xFF;
        int n2 = (((ByteBuffer)object).get() & 0xFF) << 16 | (((ByteBuffer)object).get() & 0xFF) << 8 | ((ByteBuffer)object).get() & 0xFF;
        if (n != n2) {
            byte[] byArray = new byte[n2];
            ((ByteBuffer)object).get(byArray);
            byte[] byArray2 = CacheArchive.decompressBzip2Payload(byArray);
            object = ByteBuffer.allocate(byArray2.length);
            ((ByteBuffer)object).put(byArray2);
            ((ByteBuffer)object).flip();
            this.wholeArchiveDecompressed = true;
        }
        n = ((ByteBuffer)object).getShort() & 0xFFFF;
        n2 = ((Buffer)object).position() + n * 10;
        int n3 = 0;
        while (n3 < n) {
            int n4 = ((ByteBuffer)object).getInt();
            int n5 = (((ByteBuffer)object).get() & 0xFF) << 16 | (((ByteBuffer)object).get() & 0xFF) << 8 | ((ByteBuffer)object).get() & 0xFF;
            int n6 = (((ByteBuffer)object).get() & 0xFF) << 16 | (((ByteBuffer)object).get() & 0xFF) << 8 | ((ByteBuffer)object).get() & 0xFF;
            CacheArchiveEntry cacheArchiveEntry = new CacheArchiveEntry(n4, n5, n6, n2);
            this.entriesByNameHash.put(cacheArchiveEntry.getNameHash(), cacheArchiveEntry);
            n2 += cacheArchiveEntry.getCompressedSize();
            ++n3;
        }
        this.archiveBuffer = object;
    }

    public byte[] getFileBytes(String string) {
        int n = 0;
        string = string.toUpperCase();
        int n2 = 0;
        while (n2 < string.length()) {
            n = n * 61 + string.charAt(n2) - 32;
            ++n2;
        }
        int n3 = n;
        CacheArchiveEntry cacheArchiveEntry = (CacheArchiveEntry)this.entriesByNameHash.get(n3);
        if (cacheArchiveEntry == null) {
            return null;
        }
        byte[] byArray = new byte[cacheArchiveEntry.getCompressedSize()];
        this.archiveBuffer.position(cacheArchiveEntry.getDataOffset());
        this.archiveBuffer.get(byArray);
        if (this.wholeArchiveDecompressed) {
            return byArray;
        }
        return CacheArchive.decompressBzip2Payload(byArray);
    }

    public ByteBuffer getFileBuffer(String object) {
        byte[] byArray = this.getFileBytes((String)object);
        object = byArray;
        if (byArray == null) {
            return null;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(((Object)object).length);
        byteBuffer.put((byte[])object);
        byteBuffer.flip();
        return byteBuffer;
    }

    private static byte[] decompressBzip2Payload(byte[] object) {
        byte[] byArray = new byte[((byte[])object).length + 4];
        System.arraycopy(object, 0, byArray, 4, ((byte[])object).length);
        byArray[0] = 66;
        byArray[1] = 90;
        byArray[2] = 104;
        byArray[3] = 49;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();){
            object = new BZip2CompressorInputStream(new ByteArrayInputStream(byArray));
            try {
                int n;
                while ((n = object.read(byArray = new byte[512], 0, 512)) != -1) {
                    byteArrayOutputStream.write(byArray, 0, n);
                }
            }
            finally {
                object.close();
            }
            byteArrayOutputStream.flush();
            object = byteArrayOutputStream.toByteArray();
        }
        return object;
    }

    public static boolean handleLogCuttingButton(Player player, int n, int n2) {
        Player player2 = player;
        if (player2.interfaceAction == "normalCutting" && NormalLogFletchingAction.create(player, n, n2) != null) {
            NormalLogFletchingAction.create(player, n, n2).start();
            return true;
        }
        player2 = player;
        if (player2.interfaceAction == "oakCutting" && OakLogFletchingAction.create(player, n, n2) != null) {
            OakLogFletchingAction.create(player, n, n2).start();
            return true;
        }
        player2 = player;
        if (player2.interfaceAction == "acheyCutting" && AcheyLogFletchingAction.create(player, n, n2) != null) {
            AcheyLogFletchingAction.create(player, n, n2).start();
            return true;
        }
        player2 = player;
        if (player2.interfaceAction == "willowCutting" && WillowLogFletchingAction.create(player, n, n2) != null) {
            WillowLogFletchingAction.create(player, n, n2).start();
            return true;
        }
        player2 = player;
        if (player2.interfaceAction == "mapleCutting" && MapleLogFletchingAction.create(player, n, n2) != null) {
            MapleLogFletchingAction.create(player, n, n2).start();
            return true;
        }
        player2 = player;
        if (player2.interfaceAction == "yewCutting" && YewLogFletchingAction.create(player, n, n2) != null) {
            YewLogFletchingAction.create(player, n, n2).start();
            return true;
        }
        player2 = player;
        if (player2.interfaceAction == "magicCutting" && MagicLogFletchingAction.create(player, n, n2) != null) {
            MagicLogFletchingAction.create(player, n, n2).start();
            return true;
        }
        return false;
    }

    public static void giveChallengeQuestionAnswerItem(Player player, int n) {
        ChallengeQuestion challengeQuestion = ChallengeQuestion.forClueItemId(n);
        if (challengeQuestion == null) {
            return;
        }
        player.getInventoryManager().addOrDropItem(new ItemStack(challengeQuestion.getAnswerItemId(), 1));
    }

    public static boolean hasChallengeQuestionAnswerItem(Player player, int n) {
        ChallengeQuestion challengeQuestion = ChallengeQuestion.forClueItemId(n);
        if (challengeQuestion == null) {
            return false;
        }
        return player.getInventoryManager().containsItem(challengeQuestion.getAnswerItemId());
    }

    public static String[] getChallengeQuestionLines(int n) {
        ChallengeQuestion challengeQuestion = ChallengeQuestion.forClueItemId(n);
        if (challengeQuestion == null) {
            return null;
        }
        return challengeQuestion.getQuestionLines();
    }

    public static boolean showChallengeQuestionForAnswerItem(Player stringArray, int n) {
        ChallengeQuestion challengeQuestion = ChallengeQuestion.forAnswerItemId(n);
        if (challengeQuestion == null) {
            return false;
        }
        String[] stringArray2 = stringArray;
        stringArray.packetSender.showInterface(6965);
        int n2 = 0;
        while (n2 < challengeQuestion.getQuestionLines().length) {
            int[] nArray;
            stringArray2 = stringArray;
            PacketSender packetSender = stringArray.packetSender;
            String string = challengeQuestion.getQuestionLines()[n2];
            stringArray2 = challengeQuestion.getQuestionLines();
            switch (stringArray2.length) {
                case 1: {
                    int[] nArray2 = new int[1];
                    nArray = nArray2;
                    nArray2[0] = 6971;
                    break;
                }
                case 2: {
                    int[] nArray3 = new int[2];
                    nArray3[0] = 6971;
                    nArray = nArray3;
                    nArray3[1] = 6972;
                    break;
                }
                case 3: {
                    int[] nArray4 = new int[3];
                    nArray4[0] = 6971;
                    nArray4[1] = 6972;
                    nArray = nArray4;
                    nArray4[2] = 6973;
                    break;
                }
                default: {
                    nArray = null;
                }
            }
            packetSender.sendInterfaceText(string, nArray[n2]);
            ++n2;
        }
        return true;
    }
}

