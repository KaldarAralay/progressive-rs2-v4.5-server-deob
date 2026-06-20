/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.cache;

import com.rs2.ServerSettings;
import com.rs2.cache.CacheArchive;
import com.rs2.cache.CacheStore;
import com.rs2.cache.DefinitionIndexEntry;
import com.rs2.cache.MapIndexEntry;
import com.rs2.model.GameplayHelper;
import com.rs2.model.clue.NpcClue;
import com.rs2.model.clue.PuzzleBoxHandler;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.randomevent.RandomEventRollEvent;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.net.packet.PacketSender;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Random;

public class CacheDefinitionIndex {
    private MapIndexEntry[] mapIndexEntries;
    private DefinitionIndexEntry[] objectDefinitionEntries;
    private DefinitionIndexEntry[] itemDefinitionEntries;
    private DefinitionIndexEntry[] npcDefinitionEntries;

    public static void dismissRandomEventNpc(Player player) {
        if (player.fg() != null && !player.fg().isDead()) {
            Player player2 = player;
            player2.packetSender.sendStillGraphic(86, player.fg().getPosition(), 0x640000);
            player2 = player;
            player2.packetSender.sendSoundEffect(300, 1, 0);
            GameplayHelper.a(player.fg());
            player.c((Npc)null);
        }
    }

    public static void scheduleRandomEventRoll(Player player) {
        if (player.botEnabled) {
            return;
        }
        if (player.r()) {
            return;
        }
        if (ServerSettings.randomEventsMode == 2) {
            return;
        }
        CycleEventHandler.getInstance().schedule(player, new RandomEventRollEvent(player), 1000);
    }

    public CacheDefinitionIndex(CacheStore object) {
        CacheArchive cacheArchive = new CacheArchive(((CacheStore)object).readFile(0, 2));
        this.loadObjectDefinitionIndex(cacheArchive);
        this.loadItemDefinitionIndex(cacheArchive);
        this.loadNpcDefinitionIndex(cacheArchive);
        object = new CacheArchive(((CacheStore)object).readFile(0, 5));
        this.loadMapIndex((CacheArchive)object);
    }

    private void loadObjectDefinitionIndex(CacheArchive object) {
        object = ((CacheArchive)object).getFileBuffer("loc.idx");
        int n = ((ByteBuffer)object).getShort() & 0xFFFF;
        this.objectDefinitionEntries = new DefinitionIndexEntry[n];
        int n2 = 2;
        int n3 = 0;
        while (n3 < n) {
            this.objectDefinitionEntries[n3] = new DefinitionIndexEntry(n3, n2);
            n2 += ((ByteBuffer)object).getShort() & 0xFFFF;
            ++n3;
        }
    }

    private void loadItemDefinitionIndex(CacheArchive object) {
        object = ((CacheArchive)object).getFileBuffer("obj.idx");
        int n = ((ByteBuffer)object).getShort() & 0xFFFF;
        this.itemDefinitionEntries = new DefinitionIndexEntry[n];
        int n2 = 2;
        int n3 = 0;
        while (n3 < n) {
            this.itemDefinitionEntries[n3] = new DefinitionIndexEntry(n3, n2);
            n2 += ((ByteBuffer)object).getShort() & 0xFFFF;
            ++n3;
        }
    }

    private void loadNpcDefinitionIndex(CacheArchive object) {
        object = ((CacheArchive)object).getFileBuffer("npc.idx");
        int n = ((ByteBuffer)object).getShort() & 0xFFFF;
        this.npcDefinitionEntries = new DefinitionIndexEntry[n];
        int n2 = 2;
        int n3 = 0;
        while (n3 < n) {
            this.npcDefinitionEntries[n3] = new DefinitionIndexEntry(n3, n2);
            n2 += ((ByteBuffer)object).getShort() & 0xFFFF;
            ++n3;
        }
    }

    private void loadMapIndex(CacheArchive object) {
        object = ((CacheArchive)object).getFileBuffer("map_index");
        int n = ((Buffer)object).remaining() / 7;
        this.mapIndexEntries = new MapIndexEntry[n];
        int n2 = 0;
        while (n2 < n) {
            MapIndexEntry mapIndexEntry;
            int n3 = ((ByteBuffer)object).getShort() & 0xFFFF;
            int n4 = ((ByteBuffer)object).getShort() & 0xFFFF;
            int n5 = ((ByteBuffer)object).getShort() & 0xFFFF;
            boolean bl = (((ByteBuffer)object).get() & 0xFF) == 1;
            this.mapIndexEntries[n2] = mapIndexEntry = new MapIndexEntry(n3, n4, n5, bl);
            ++n2;
        }
    }

    public DefinitionIndexEntry[] getObjectDefinitionEntries() {
        return this.objectDefinitionEntries;
    }

    public DefinitionIndexEntry[] getItemDefinitionEntries() {
        return this.itemDefinitionEntries;
    }

    public DefinitionIndexEntry[] getNpcDefinitionEntries() {
        return this.npcDefinitionEntries;
    }

    public DefinitionIndexEntry getObjectDefinitionEntry(int n) {
        return this.objectDefinitionEntries[n];
    }

    public DefinitionIndexEntry getItemDefinitionEntry(int n) {
        return this.itemDefinitionEntries[n];
    }

    public DefinitionIndexEntry getNpcDefinitionEntry(int n) {
        return this.npcDefinitionEntries[n];
    }

    public static boolean showNpcClue(Player stringArray, int n) {
        NpcClue npcClue = NpcClue.forClueItemId(n);
        if (npcClue == null) {
            return false;
        }
        String[] stringArray2 = stringArray;
        stringArray.packetSender.showInterface(6965);
        int n2 = 0;
        while (n2 < npcClue.getClueTextLines().length) {
            int[] nArray;
            stringArray2 = stringArray;
            PacketSender packetSender = stringArray.packetSender;
            String string = npcClue.getClueTextLines()[n2];
            stringArray2 = npcClue.getClueTextLines();
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
                    nArray4[0] = 6970;
                    nArray4[1] = 6971;
                    nArray = nArray4;
                    nArray4[2] = 6972;
                    break;
                }
                case 4: {
                    int[] nArray5 = new int[4];
                    nArray5[0] = 6970;
                    nArray5[1] = 6971;
                    nArray5[2] = 6972;
                    nArray = nArray5;
                    nArray5[3] = 6973;
                    break;
                }
                case 5: {
                    int[] nArray6 = new int[5];
                    nArray6[0] = 6969;
                    nArray6[1] = 6970;
                    nArray6[2] = 6971;
                    nArray6[3] = 6972;
                    nArray = nArray6;
                    nArray6[4] = 6973;
                    break;
                }
                case 6: {
                    int[] nArray7 = new int[6];
                    nArray7[0] = 6969;
                    nArray7[1] = 6970;
                    nArray7[2] = 6971;
                    nArray7[3] = 6972;
                    nArray7[4] = 6973;
                    nArray = nArray7;
                    nArray7[5] = 6974;
                    break;
                }
                case 7: {
                    int[] nArray8 = new int[7];
                    nArray8[0] = 6968;
                    nArray8[1] = 6969;
                    nArray8[2] = 6970;
                    nArray8[3] = 6971;
                    nArray8[4] = 6972;
                    nArray8[5] = 6973;
                    nArray = nArray8;
                    nArray8[6] = 6974;
                    break;
                }
                case 8: {
                    int[] nArray9 = new int[8];
                    nArray9[0] = 6968;
                    nArray9[1] = 6969;
                    nArray9[2] = 6970;
                    nArray9[3] = 6971;
                    nArray9[4] = 6972;
                    nArray9[5] = 6973;
                    nArray9[6] = 6974;
                    nArray = nArray9;
                    nArray9[7] = 6975;
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

    public static int randomNpcClueItemForLevel(int n) {
        int n2 = new Random().nextInt(NpcClue.values().length);
        while (NpcClue.values()[n2].getLevel() != n) {
            n2 = new Random().nextInt(NpcClue.values().length);
        }
        return NpcClue.values()[n2].getClueItemId();
    }

    public static boolean handleNpcClueNpc(Player player, int n) {
        NpcClue npcClue = NpcClue.forNpcId(n);
        if (npcClue == null) {
            return false;
        }
        if (!player.getInventoryManager().containsItem(npcClue.getClueItemId())) {
            return false;
        }
        player.getDialogueManager().setDialogueNpcId(n);
        if (npcClue.getFollowupType() == "Challenge") {
            if (CacheArchive.hasChallengeQuestionAnswerItem(player, npcClue.getClueItemId())) {
                player.at = npcClue.getLevel();
                player.au = npcClue.getClueItemId();
                DialogueManager.a(player, 10009, 3);
                if (CacheArchive.getChallengeQuestionLines(npcClue.getClueItemId()).length == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue(CacheArchive.getChallengeQuestionLines(npcClue.getClueItemId())[0], 588);
                } else {
                    player.getDialogueManager().showNpcTwoLineDialogue(CacheArchive.getChallengeQuestionLines(npcClue.getClueItemId())[0], CacheArchive.getChallengeQuestionLines(npcClue.getClueItemId())[1], 588);
                }
            } else {
                player.V = new ItemStack[1];
                player.V[0] = new ItemStack(npcClue.getClueItemId(), 1);
                player.getDialogueManager().showNpcOneLineDialogue("Here's a challenge for you.", 588);
                CacheArchive.giveChallengeQuestionAnswerItem(player, npcClue.getClueItemId());
            }
        } else if (npcClue.getFollowupType() == "Puzzle") {
            if (PuzzleBoxHandler.isCluePuzzleSolved(player) && player.eM()) {
                DialogueManager.a(player, 10009, 2);
                player.getDialogueManager().showNpcOneLineDialogue("Thank you very much.", 588);
                player.V = new ItemStack[4];
                player.V[0] = new ItemStack(npcClue.getClueItemId(), 1);
                player.V[1] = new ItemStack(2800, 1);
                player.V[2] = new ItemStack(3571, 1);
                player.V[3] = new ItemStack(3565, 1);
                player.at = npcClue.getLevel();
            } else if (player.eM()) {
                player.getDialogueManager().showNpcOneLineDialogue("The puzzle doesn't seem to be complete yet.", 588);
            } else {
                player.getDialogueManager().showNpcOneLineDialogue("Hello, Solve this puzzle for me please.", 588);
                PuzzleBoxHandler.giveRandomPuzzleBox(player);
            }
        } else {
            DialogueManager.a(player, 10009, 2);
            player.getDialogueManager().showNpcOneLineDialogue("Thank you very much.", 588);
            player.V = new ItemStack[1];
            player.V[0] = new ItemStack(npcClue.getClueItemId(), 1);
            player.at = npcClue.getLevel();
        }
        return true;
    }
}

