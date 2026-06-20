/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.npc.drop;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.npc.NpcDefinition;
import com.rs2.model.npc.drop.NpcDropEntry;
import com.rs2.model.player.Player;
import com.rs2.util.ByteArrayReader;
import com.rs2.util.FileUtil;
import java.util.ArrayList;

public final class NpcDropTable {
    private static NpcDropTable[] tablesByNpcId = new NpcDropTable[0];
    private NpcDropEntry[] guaranteedDrops;
    private NpcDropEntry[] weightedDrops;
    private NpcDropEntry[] independentDrops;
    private static int[] membersOnlyVirtualDropIds = new int[]{65001, 65002, 65003, 65004, 65005, 65007, 65009, 65011, 65012, 65013, 65014, 65015, 65018, 65020, 65021, 65022, 65023, 65024, 65025, 65026, 65027, 65028, 65029, 65030};

    public static void loadDropTables() {
        if (FileUtil.exists("./data/npcs/Npc drops.dat")) {
            Object object = FileUtil.readBytes("./data/npcs/Npc drops.dat");
            ByteArrayReader byteArrayReader = new ByteArrayReader((byte[])object);
            object = byteArrayReader;
            int n = byteArrayReader.readUnsignedByte();
            int n2 = ((ByteArrayReader)object).readUnsignedShort();
            tablesByNpcId = new NpcDropTable[n2];
            int n3 = 0;
            while (n3 < n2) {
                NpcDropEntry[] npcDropEntryArray;
                int n4 = ((ByteArrayReader)object).readUnsignedByte();
                if (n4 == 2) {
                    int n5 = ((ByteArrayReader)object).readUnsignedShort();
                    npcDropEntryArray = NpcDefinition.forId(n3);
                    npcDropEntryArray.c(n5);
                }
                if (n4 == 1) {
                    NpcDropEntry[] npcDropEntryArray2 = null;
                    npcDropEntryArray = null;
                    NpcDropEntry[] npcDropEntryArray3 = null;
                    NpcDropEntry[] npcDropEntryArray4 = null;
                    NpcDropEntry[] npcDropEntryArray5 = null;
                    NpcDropEntry[] npcDropEntryArray6 = null;
                    NpcDropEntry[] npcDropEntryArray7 = null;
                    int n6 = 0;
                    while (n6 < 7) {
                        int n7 = ((ByteArrayReader)object).readUnsignedByte();
                        if (n6 == 0) {
                            npcDropEntryArray2 = new NpcDropEntry[n7];
                        }
                        if (n6 == 1) {
                            npcDropEntryArray = new NpcDropEntry[n7];
                        }
                        if (n6 == 2) {
                            npcDropEntryArray3 = new NpcDropEntry[n7];
                        }
                        if (n6 == 3) {
                            npcDropEntryArray4 = new NpcDropEntry[n7];
                        }
                        if (n6 == 4) {
                            npcDropEntryArray5 = new NpcDropEntry[n7];
                        }
                        if (n6 == 5) {
                            npcDropEntryArray6 = new NpcDropEntry[n7];
                        }
                        if (n6 == 6) {
                            npcDropEntryArray7 = new NpcDropEntry[n7];
                        }
                        int n8 = 0;
                        while (n8 < n7) {
                            int n9;
                            int n10;
                            int n11 = n;
                            int n12 = n6;
                            Object object2 = object;
                            int n13 = -1;
                            int n14 = -1;
                            int n15 = -1;
                            if (n12 == 5 || n12 == 6) {
                                n13 = ((ByteArrayReader)object2).readUnsignedByte();
                                if (n11 == 1) {
                                    n14 = ((ByteArrayReader)object2).readUnsignedByte();
                                }
                                if (n11 == 2) {
                                    n14 = ((ByteArrayReader)object2).readUnsignedShort();
                                }
                                n15 = ((ByteArrayReader)object2).readInt();
                            }
                            n12 = ((ByteArrayReader)object2).readUnsignedByte();
                            n11 = -1;
                            int n16 = -1;
                            int n17 = -1;
                            int n18 = -1;
                            int[] nArray = null;
                            int[] nArray2 = null;
                            int[] nArray3 = null;
                            int[] nArray4 = null;
                            if (n12 != 3 && n12 != 4) {
                                n11 = ((ByteArrayReader)object2).readUnsignedShort();
                            }
                            if (n12 == 0) {
                                n16 = ((ByteArrayReader)object2).readUnsignedShort();
                            }
                            if (n12 == 1) {
                                n17 = ((ByteArrayReader)object2).readUnsignedShort();
                                n18 = ((ByteArrayReader)object2).readUnsignedShort();
                            }
                            if (n12 == 2) {
                                n10 = ((ByteArrayReader)object2).readUnsignedByte();
                                nArray = new int[n10];
                                n9 = 0;
                                while (n9 < n10) {
                                    nArray[n9] = ((ByteArrayReader)object2).readUnsignedShort();
                                    ++n9;
                                }
                            }
                            if (n12 == 3) {
                                n10 = ((ByteArrayReader)object2).readUnsignedByte();
                                nArray = new int[n10];
                                nArray2 = new int[n10];
                                n9 = 0;
                                while (n9 < n10) {
                                    nArray2[n9] = ((ByteArrayReader)object2).readUnsignedShort();
                                    nArray[n9] = ((ByteArrayReader)object2).readUnsignedShort();
                                    ++n9;
                                }
                            }
                            if (n12 == 4) {
                                n10 = ((ByteArrayReader)object2).readUnsignedByte();
                                nArray2 = new int[n10];
                                nArray3 = new int[n10];
                                nArray4 = new int[n10];
                                n9 = 0;
                                while (n9 < n10) {
                                    nArray2[n9] = ((ByteArrayReader)object2).readUnsignedShort();
                                    nArray3[n9] = ((ByteArrayReader)object2).readUnsignedShort();
                                    nArray4[n9] = ((ByteArrayReader)object2).readUnsignedShort();
                                    ++n9;
                                }
                            }
                            object2 = new NpcDropEntry(n11, n16, n17, n18, nArray2, nArray, n13, n14, n15, nArray3, nArray4);
                            if (n6 == 0) {
                                npcDropEntryArray2[n8] = object2;
                            }
                            if (n6 == 1) {
                                npcDropEntryArray[n8] = object2;
                            }
                            if (n6 == 2) {
                                npcDropEntryArray3[n8] = object2;
                            }
                            if (n6 == 3) {
                                npcDropEntryArray4[n8] = object2;
                            }
                            if (n6 == 4) {
                                npcDropEntryArray5[n8] = object2;
                            }
                            if (n6 == 5) {
                                npcDropEntryArray6[n8] = object2;
                            }
                            if (n6 == 6) {
                                npcDropEntryArray7[n8] = object2;
                            }
                            ++n8;
                        }
                        ++n6;
                    }
                    NpcDropTable.tablesByNpcId[n3] = new NpcDropTable(npcDropEntryArray2, npcDropEntryArray, npcDropEntryArray3, npcDropEntryArray4, npcDropEntryArray5, npcDropEntryArray6, npcDropEntryArray7);
                }
                ++n3;
            }
        }
    }

    private NpcDropTable(NpcDropEntry[] npcDropEntryArray, NpcDropEntry[] npcDropEntryArray2, NpcDropEntry[] npcDropEntryArray3, NpcDropEntry[] npcDropEntryArray4, NpcDropEntry[] npcDropEntryArray5, NpcDropEntry[] npcDropEntryArray6, NpcDropEntry[] npcDropEntryArray7) {
        this.guaranteedDrops = npcDropEntryArray;
        this.weightedDrops = npcDropEntryArray6;
        this.independentDrops = npcDropEntryArray7;
    }

    public static NpcDropTable forNpcId(int n) {
        NpcDropTable npcDropTable;
        if (n < 0) {
            n = 1;
        }
        if ((npcDropTable = tablesByNpcId[n]) == null) {
            npcDropTable = new NpcDropTable(null, null, null, null, null, null, null);
        }
        return npcDropTable;
    }

    public final NpcDropEntry[] getGuaranteedDrops(Entity object) {
        NpcDropEntry[] npcDropEntryArray;
        if (this.guaranteedDrops == null) {
            return null;
        }
        Player player = null;
        if (((Entity)object).isPlayer()) {
            player = (Player)object;
        }
        object = new ArrayList();
        NpcDropEntry[] npcDropEntryArray2 = this.guaranteedDrops;
        int n = this.guaranteedDrops.length;
        int n2 = 0;
        while (n2 < n) {
            block8: {
                block10: {
                    block9: {
                        npcDropEntryArray = npcDropEntryArray2[n2];
                        if (npcDropEntryArray == null) break block8;
                        if (npcDropEntryArray.getItemId() >= 65000) break block9;
                        if ((npcDropEntryArray.getItemId() < 7956 || npcDropEntryArray.getItemId() > 8118) && ItemDefinition.isDefined(npcDropEntryArray.getItemId()) && (!ServerSettings.freeToPlayWorld && (player == null || player.isMember()) || !ItemDefinition.forId(npcDropEntryArray.getItemId()).isMembersOnly())) break block10;
                        break block8;
                    }
                    npcDropEntryArray.getItemId();
                    boolean bl = false;
                    int n3 = 0;
                    while (n3 < membersOnlyVirtualDropIds.length) {
                        int n4 = membersOnlyVirtualDropIds[n3];
                        if (n4 == npcDropEntryArray.getItemId()) {
                            bl = true;
                        }
                        ++n3;
                    }
                    if ((ServerSettings.freeToPlayWorld || player != null && !player.isMember()) && bl) break block8;
                }
                ((ArrayList)object).add(npcDropEntryArray);
            }
            ++n2;
        }
        npcDropEntryArray = ((ArrayList)object).toArray(new NpcDropEntry[((ArrayList)object).size()]);
        return npcDropEntryArray;
    }

    public final NpcDropEntry[] getWeightedDrops(Entity object) {
        NpcDropEntry[] npcDropEntryArray;
        if (this.weightedDrops == null) {
            return null;
        }
        Player player = null;
        if (((Entity)object).isPlayer()) {
            player = (Player)object;
        }
        object = new ArrayList();
        NpcDropEntry[] npcDropEntryArray2 = this.weightedDrops;
        int n = this.weightedDrops.length;
        int n2 = 0;
        while (n2 < n) {
            block8: {
                block10: {
                    block9: {
                        npcDropEntryArray = npcDropEntryArray2[n2];
                        if (npcDropEntryArray == null) break block8;
                        if (npcDropEntryArray.getItemId() >= 65000) break block9;
                        if ((npcDropEntryArray.getItemId() < 7956 || npcDropEntryArray.getItemId() > 8118) && ItemDefinition.isDefined(npcDropEntryArray.getItemId()) && (!ServerSettings.freeToPlayWorld && (player == null || player.isMember()) || !ItemDefinition.forId(npcDropEntryArray.getItemId()).isMembersOnly())) break block10;
                        break block8;
                    }
                    int n3 = npcDropEntryArray.getItemId() - 65000;
                    boolean bl = false;
                    int n4 = 0;
                    while (n4 < membersOnlyVirtualDropIds.length) {
                        int n5 = membersOnlyVirtualDropIds[n4];
                        if (n5 == npcDropEntryArray.getItemId()) {
                            bl = true;
                        }
                        ++n4;
                    }
                    if ((ServerSettings.freeToPlayWorld || player != null && !player.isMember()) && bl || n3 == 7 && !ItemDefinition.isDefined(5300)) break block8;
                }
                ((ArrayList)object).add(npcDropEntryArray);
            }
            ++n2;
        }
        npcDropEntryArray = ((ArrayList)object).toArray(new NpcDropEntry[((ArrayList)object).size()]);
        return npcDropEntryArray;
    }

    public final NpcDropEntry[] getIndependentDrops(Entity object) {
        NpcDropEntry[] npcDropEntryArray;
        if (this.independentDrops == null) {
            return null;
        }
        Player player = null;
        if (((Entity)object).isPlayer()) {
            player = (Player)object;
        }
        object = new ArrayList();
        NpcDropEntry[] npcDropEntryArray2 = this.independentDrops;
        int n = this.independentDrops.length;
        int n2 = 0;
        while (n2 < n) {
            block8: {
                block10: {
                    block9: {
                        npcDropEntryArray = npcDropEntryArray2[n2];
                        if (npcDropEntryArray == null) break block8;
                        if (npcDropEntryArray.getItemId() >= 65000) break block9;
                        if ((npcDropEntryArray.getItemId() < 7956 || npcDropEntryArray.getItemId() > 8118) && ItemDefinition.isDefined(npcDropEntryArray.getItemId()) && (!ServerSettings.freeToPlayWorld && (player == null || player.isMember()) || !ItemDefinition.forId(npcDropEntryArray.getItemId()).isMembersOnly())) break block10;
                        break block8;
                    }
                    int n3 = npcDropEntryArray.getItemId() - 65000;
                    boolean bl = false;
                    int n4 = 0;
                    while (n4 < membersOnlyVirtualDropIds.length) {
                        int n5 = membersOnlyVirtualDropIds[n4];
                        if (n5 == npcDropEntryArray.getItemId()) {
                            bl = true;
                        }
                        ++n4;
                    }
                    if ((ServerSettings.freeToPlayWorld || player != null && !player.isMember()) && bl || n3 == 7 && !ItemDefinition.isDefined(5300)) break block8;
                }
                ((ArrayList)object).add(npcDropEntryArray);
            }
            ++n2;
        }
        npcDropEntryArray = ((ArrayList)object).toArray(new NpcDropEntry[((ArrayList)object).size()]);
        return npcDropEntryArray;
    }
}

