/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.item;

import com.rs2.ServerSettings;
import com.rs2.cache.CacheArchive;
import com.rs2.cache.CacheStore;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.model.skill.runecrafting.RunecraftingHandler;
import com.rs2.util.ByteArrayReader;
import com.rs2.util.FileUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ItemDefinition {
    public ArrayList a = new ArrayList();
    private static ItemDefinition[] c;
    private static int d;
    private static boolean[] e;
    private static boolean[] f;
    private int[] g;
    private int[] h;
    private boolean i;
    private int j;
    private String k;
    private String l;
    private boolean m;
    private boolean n;
    private boolean o;
    private int p;
    private int q;
    private boolean r;
    private int s;
    private int t;
    private int u;
    private int v;
    private int w;
    private final int[] x;
    private final int[] y;
    private final boolean[] z;
    private int A;
    private int B;
    private int C;
    public int b;
    private double D;
    private boolean E;
    private int F;
    private boolean G;

    static {
        Logger.getLogger(ItemDefinition.class.getName());
        c = new ItemDefinition[11884];
        d = 0;
        e = new boolean[11884];
        f = new boolean[11884];
    }

    public static ItemDefinition forId(int n) {
        ItemDefinition itemDefinition;
        if (n < 0) {
            n = 1;
        }
        if ((itemDefinition = c[n]) == null) {
            itemDefinition = new ItemDefinition(n, "# + id", "It's an item!", "NONE", false, false, false, -1, -1, true, 0, 0, 0, 0, new int[14], 0, new int[25], 0, new boolean[QuestDefinition.a], 0.0, 0, 0, false);
        }
        return itemDefinition;
    }

    public static void loadDefinitions() {
        int n;
        Object object;
        ByteArrayReader byteArrayReader;
        try {
            byte[] byArray = FileUtil.readBytes("./data/content/itemDefinitions.dat");
            byteArrayReader = new ByteArrayReader(byArray);
            int n2 = byteArrayReader.readUnsignedShort();
            int n3 = 0;
            while (n3 < n2) {
                object = ItemDefinition.forId(n3);
                ItemDefinition.c[n3] = object;
                n = byteArrayReader.readUnsignedByte();
                if (n != 200) {
                    int n4;
                    int n5;
                    if (n > 200) {
                        n5 = byteArrayReader.readShort();
                        ItemDefinition itemDefinition = ItemDefinition.forId(n5);
                        ((ItemDefinition)object).b = n5;
                        ((ItemDefinition)object).A = itemDefinition.A;
                        ((ItemDefinition)object).G = itemDefinition.G;
                        ItemDefinition.e[n3] = itemDefinition.G;
                        ((ItemDefinition)object).E = itemDefinition.E;
                        ((ItemDefinition)object).B = itemDefinition.B;
                        n4 = 0;
                        while (n4 < 25) {
                            ((ItemDefinition)object).y[n4] = itemDefinition.y[n4];
                            ++n4;
                        }
                        ((ItemDefinition)object).C = itemDefinition.C;
                        n4 = 0;
                        while (n4 < 104) {
                            ((ItemDefinition)object).z[n4] = itemDefinition.z[n4];
                            ++n4;
                        }
                        ((ItemDefinition)object).F = itemDefinition.F;
                        ((ItemDefinition)object).u = itemDefinition.u;
                        ((ItemDefinition)object).v = itemDefinition.v;
                        ((ItemDefinition)object).s = itemDefinition.s;
                        ((ItemDefinition)object).t = itemDefinition.t;
                        ((ItemDefinition)object).D = itemDefinition.D;
                        n4 = 0;
                        while (n4 < 14) {
                            ((ItemDefinition)object).x[n4] = itemDefinition.x[n4];
                            ++n4;
                        }
                        if (n == 202) {
                            ((ItemDefinition)object).w = byteArrayReader.readShort();
                            if (n3 == 7999) {
                                if (ServerSettings.membershipRequirementMode == 4) {
                                    ((ItemDefinition)object).F = ServerSettings.membershipRequirementValue;
                                } else if (ServerSettings.membershipRequirementMode == 5) {
                                    ((ItemDefinition)object).F = 1000;
                                }
                            }
                        }
                    } else {
                        ((ItemDefinition)object).A = n - 1;
                        ItemDefinition.e[n3] = ((ItemDefinition)object).G = byteArrayReader.readUnsignedByte() == 1;
                        if (n != 0) {
                            ((ItemDefinition)object).E = byteArrayReader.readUnsignedByte() == 1;
                            ((ItemDefinition)object).B = byteArrayReader.readUnsignedByte();
                            while ((n5 = byteArrayReader.readUnsignedByte()) != 0) {
                                int n6;
                                if (n5 == 1) {
                                    n6 = byteArrayReader.readUnsignedByte();
                                    ((ItemDefinition)object).y[n6] = n4 = byteArrayReader.readUnsignedByte();
                                }
                                if (n5 == 2) {
                                    n6 = byteArrayReader.readUnsignedByte();
                                    if (n6 == 250) {
                                        n6 = QuestDefinition.a();
                                    }
                                    ((ItemDefinition)object).C = n6;
                                }
                                if (n5 != 3) continue;
                                n6 = byteArrayReader.readUnsignedByte();
                                ((ItemDefinition)object).z[n6] = true;
                            }
                            n5 = byteArrayReader.readUnsignedByte();
                            if ((n5 & 1) != 0) {
                                ((ItemDefinition)object).F = byteArrayReader.readInt();
                            }
                            if ((n5 & 2) != 0) {
                                ((ItemDefinition)object).u = byteArrayReader.readInt();
                            }
                            if ((n5 & 4) != 0) {
                                ((ItemDefinition)object).v = byteArrayReader.readInt();
                            }
                            if ((n5 & 8) != 0) {
                                ((ItemDefinition)object).s = byteArrayReader.readInt();
                            }
                            if ((n5 & 0x10) != 0) {
                                ((ItemDefinition)object).t = byteArrayReader.readInt();
                            }
                            double d = byteArrayReader.readShort();
                            ((ItemDefinition)object).D = d / 1000.0;
                            n = 0;
                            while (n < 14) {
                                double d2 = byteArrayReader.readShort();
                                if (ServerSettings.mod2hsEnabled && (n3 == 1307 || n3 == 1309 || n3 == 1311 || n3 == 1313 || n3 == 1315 || n3 == 1317 || n3 == 1319 || n3 == 7158)) {
                                    if (n >= 0 && n <= 4) {
                                        d2 *= ServerSettings.mod2hsAttackBonusRate;
                                    } else if (n == 10) {
                                        d2 *= ServerSettings.mod2hsStrengthBonusRate;
                                    }
                                }
                                ((ItemDefinition)object).x[n] = (int)d2;
                                ++n;
                            }
                        } else {
                            n5 = byteArrayReader.readUnsignedByte();
                            if ((n5 & 1) != 0) {
                                ((ItemDefinition)object).F = byteArrayReader.readInt();
                            }
                            if ((n5 & 2) != 0) {
                                ((ItemDefinition)object).u = byteArrayReader.readInt();
                            }
                            if ((n5 & 4) != 0) {
                                ((ItemDefinition)object).v = byteArrayReader.readInt();
                            }
                            if ((n5 & 8) != 0) {
                                ((ItemDefinition)object).s = byteArrayReader.readInt();
                            }
                            if ((n5 & 0x10) != 0) {
                                ((ItemDefinition)object).t = byteArrayReader.readInt();
                            }
                            double d = byteArrayReader.readShort();
                            ((ItemDefinition)object).D = d / 1000.0;
                        }
                    }
                }
                ++n3;
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        CacheStore cacheStore = CacheStore.getInstance();
        byteArrayReader = null;
        try {
            byteArrayReader = new ByteArrayReader(new CacheArchive(cacheStore.readFile(0, 2)).getFileBytes("obj.dat"));
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
        }
        d = cacheStore.getDefinitionIndex().getItemDefinitionEntries().length;
        int n7 = 0;
        while (n7 < d) {
            byteArrayReader.position = cacheStore.getDefinitionIndex().getItemDefinitionEntry(n7).getDataOffset();
            n = n7;
            object = byteArrayReader;
            ItemDefinition itemDefinition = c[n];
            c[n].o = false;
            itemDefinition.n = false;
            itemDefinition.m = false;
            itemDefinition.r = false;
            itemDefinition.i = false;
            block11: while (true) {
                int n8;
                if ((n8 = ((ByteArrayReader)object).readUnsignedByte()) == 0) {
                    if (n != 4561) break;
                    itemDefinition.o = true;
                    break;
                }
                if (n8 == 1) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    continue;
                }
                if (n8 == 2) {
                    itemDefinition.k = ((ByteArrayReader)object).readString();
                    continue;
                }
                if (n8 == 3) {
                    String string;
                    itemDefinition.l = string = new String(((ByteArrayReader)object).readLineBytes());
                    continue;
                }
                if (n8 == 4) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    continue;
                }
                if (n8 == 5) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    continue;
                }
                if (n8 == 6) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    continue;
                }
                if (n8 == 7) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    continue;
                }
                if (n8 == 8) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    continue;
                }
                if (n8 == 10) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    continue;
                }
                if (n8 == 11) {
                    itemDefinition.o = true;
                    continue;
                }
                if (n8 == 12) {
                    ((ByteArrayReader)object).readInt();
                    continue;
                }
                if (n8 == 16) {
                    itemDefinition.r = true;
                    continue;
                }
                if (n8 == 23) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    ((ByteArrayReader)object).readByte();
                    continue;
                }
                if (n8 == 24) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    continue;
                }
                if (n8 == 25) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    ((ByteArrayReader)object).readByte();
                    continue;
                }
                if (n8 == 26) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    continue;
                }
                if (n8 >= 30 && n8 < 35) {
                    ((ByteArrayReader)object).readString();
                    continue;
                }
                if (n8 >= 35 && n8 < 40) {
                    String string = ((ByteArrayReader)object).readString();
                    if (!string.toLowerCase().equals("destroy")) continue;
                    itemDefinition.i = true;
                    continue;
                }
                if (n8 == 40) {
                    n8 = ((ByteArrayReader)object).readUnsignedByte();
                    int n9 = 0;
                    while (true) {
                        if (n9 >= n8) continue block11;
                        ((ByteArrayReader)object).readUnsignedShort();
                        ((ByteArrayReader)object).readUnsignedShort();
                        ++n9;
                    }
                }
                if (n8 == 78) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    continue;
                }
                if (n8 == 79) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    continue;
                }
                if (n8 == 90) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    continue;
                }
                if (n8 == 91) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    continue;
                }
                if (n8 == 92) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    continue;
                }
                if (n8 == 93) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    continue;
                }
                if (n8 == 95) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    continue;
                }
                if (n8 == 97) {
                    n8 = ((ByteArrayReader)object).readUnsignedShort();
                    itemDefinition.m = true;
                    itemDefinition.p = n8;
                    ItemDefinition itemDefinition2 = c[n8];
                    c[n8].n = true;
                    itemDefinition2.q = n;
                    continue;
                }
                if (n8 == 98) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    continue;
                }
                if (n8 >= 100 && n8 < 110) {
                    if (itemDefinition.g == null) {
                        itemDefinition.g = new int[10];
                        itemDefinition.h = new int[10];
                    }
                    itemDefinition.g[n8 - 100] = ((ByteArrayReader)object).readUnsignedShort();
                    itemDefinition.h[n8 - 100] = ((ByteArrayReader)object).readUnsignedShort();
                    continue;
                }
                if (n8 == 110) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    continue;
                }
                if (n8 == 111) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    continue;
                }
                if (n8 == 112) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    continue;
                }
                if (n8 == 113) {
                    ((ByteArrayReader)object).readByte();
                    continue;
                }
                if (n8 == 114) {
                    ((ByteArrayReader)object).readByte();
                    continue;
                }
                if (n8 == 115) {
                    ((ByteArrayReader)object).readUnsignedByte();
                    continue;
                }
                if (n8 == 121) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    continue;
                }
                if (n8 == 122) {
                    ((ByteArrayReader)object).readUnsignedShort();
                    continue;
                }
                if (n8 == 140) {
                    n8 = ((ByteArrayReader)object).readUnsignedByte();
                    int n10 = 0;
                    while (true) {
                        if (n10 >= n8) continue block11;
                        ((ByteArrayReader)object).readUnsignedShort();
                        ((ByteArrayReader)object).readUnsignedShort();
                        ++n10;
                    }
                }
                if (n8 != 177) continue;
                ItemDefinition.f[n] = true;
            }
            ++n7;
        }
        if (!ItemDefinition.isDefined(7936)) {
            RunecraftingHandler.PURE_ESSENCE_ITEM_ID = 1436;
        }
    }

    public static boolean isDefined(int n) {
        return n < d;
    }

    private ItemDefinition(int n, String string, String string2, String string3, boolean bl, boolean bl2, boolean bl3, int n2, int n3, boolean bl4, int n4, int n5, int n6, int n7, int[] nArray, int n8, int[] nArray2, int n9, boolean[] blArray, double d, int n10, int n11, boolean bl5) {
        this.j = n;
        this.k = string;
        this.l = string2;
        this.m = false;
        this.n = false;
        this.o = false;
        this.p = -1;
        this.q = -1;
        this.r = true;
        this.s = 0;
        this.t = 0;
        this.u = 0;
        this.v = 0;
        this.x = nArray;
        String string4 = string3;
        this.A = string4.equals("HAT") ? 0 : (string4.equals("CAPE") ? 1 : (string4.equals("AMULET") ? 2 : (string4.equals("WEAPON") ? 3 : (string4.equals("BODY") ? 4 : (string4.equals("SHIELD") ? 5 : (string4.equals("LEGS") ? 7 : (string4.equals("GLOVES") ? 9 : (string4.equals("BOOTS") ? 10 : (string4.equals("RING") ? 12 : (string4.equals("ARROWS") ? 13 : -1))))))))));
        this.B = 0;
        this.y = nArray2;
        this.C = 0;
        this.z = blArray;
        this.D = 0.0;
        this.w = 0;
        this.i = false;
    }

    public final int getId() {
        return this.j;
    }

    public final String getName() {
        if (this.m && this.p != -1 && this.p != this.j) {
            this.k = ItemDefinition.forId(this.p).getName();
        }
        return this.k;
    }

    public final String getShortName() {
        if (this.j == 249) {
            return "guam";
        }
        if (this.j == 377) {
            return "raw lob";
        }
        if (this.j == 379) {
            return "lob";
        }
        if (this.j == 381) {
            return "rare black lobster";
        }
        if (this.j == 536) {
            return "d bone";
        }
        if (this.j == 556) {
            return "air";
        }
        if (this.j == 558) {
            return "mind";
        }
        if (this.j == 560) {
            return "death";
        }
        if (this.j == 561) {
            return "nat";
        }
        if (this.j == 563) {
            return "law";
        }
        if (this.j == 564) {
            return "cosmic";
        }
        if (this.j == 565) {
            return "blood";
        }
        if (this.j == 1305) {
            return "dlong";
        }
        if (this.j == 1319) {
            return "r2h";
        }
        if (this.j == 1333) {
            return "rune scim";
        }
        if (this.j == 1373) {
            return "rune baxe";
        }
        if (this.j == 1377) {
            return "dbaxe";
        }
        if (this.j == 1436) {
            return "rune ess";
        }
        if (this.j == 1725) {
            return "str ammy";
        }
        if (this.j == 1727) {
            return "mage ammy";
        }
        if (this.j == 1729) {
            return "def ammy";
        }
        if (this.j == 1731) {
            return "power ammy";
        }
        if (this.j == 4099) {
            return "dark mystic hat";
        }
        if (this.j == 4101) {
            return "dark mystic top";
        }
        if (this.j == 4103) {
            return "dark mystic bottom";
        }
        if (this.j == 4105) {
            return "dark mystic gloves";
        }
        if (this.j == 4107) {
            return "dark mystic boots";
        }
        if (this.j == 4109) {
            return "light mystic hat";
        }
        if (this.j == 4111) {
            return "light mystic top";
        }
        if (this.j == 4113) {
            return "light mystic bottom";
        }
        if (this.j == 4115) {
            return "light mystic gloves";
        }
        if (this.j == 4117) {
            return "light mystic boots";
        }
        if (this.j == 4151) {
            return "whip";
        }
        if (this.j == 4153) {
            return "gmaul";
        }
        if (this.j == 6522) {
            return "obby ring";
        }
        if (this.j == 6523) {
            return "obby sword";
        }
        if (this.j == 6524) {
            return "obby shield";
        }
        if (this.j == 6525) {
            return "obby knife";
        }
        if (this.j == 6526) {
            return "obby staff";
        }
        if (this.j == 6527) {
            return "obby mace";
        }
        if (this.j == 6528) {
            return "obby maul";
        }
        if (this.j == 6568) {
            return "obby cape";
        }
        if (this.j == 7936) {
            return "pure ess";
        }
        return null;
    }

    public final String getDisplayName() {
        String string = this.getShortName();
        if (string == null) {
            return this.getName();
        }
        return string;
    }

    public final String getDescription() {
        if (this.m && this.p != -1 && this.p != this.j) {
            this.l = "Swap this note at any bank for the equivalent item.";
        }
        return this.l;
    }

    public final boolean g() {
        return this.j == 759 || this.j == 763 || this.j == 765 || this.j == 769 || this.j == 1586 || this.j == 1577 || this.j == 7999;
    }

    public final boolean isNote() {
        return this.m;
    }

    public final boolean hasNote() {
        return this.n;
    }

    public final boolean isStackable() {
        return this.o || this.m;
    }

    public final int getUnnotedId() {
        return this.p;
    }

    public final int getNotedId() {
        return this.q;
    }

    public final boolean isMembersOnly() {
        return this.r;
    }

    public final int n() {
        return this.getShopValue();
    }

    public final int getTokkulValue() {
        int n = this.s;
        ItemDefinition itemDefinition = this;
        if (itemDefinition.m && this.p != -1 && this.p != this.j && n < ItemDefinition.forId(this.p).getTokkulValue()) {
            n = ItemDefinition.forId(this.p).getTokkulValue();
        }
        if (n == 0) {
            return 1;
        }
        return n;
    }

    public final int getShopValue() {
        if (this.m && this.p != -1 && this.p != this.j && this.F < ItemDefinition.forId(this.p).getShopValue()) {
            this.F = ItemDefinition.forId(this.p).getShopValue();
        }
        return this.F;
    }

    public final int getDonatorPointValue() {
        if (this.m && this.p != -1 && this.p != this.j && this.w < ItemDefinition.forId(this.p).getDonatorPointValue()) {
            this.w = ItemDefinition.forId(this.p).getDonatorPointValue();
        }
        return this.w;
    }

    public final int getLowAlchemyValue() {
        int n = this.v;
        if (n == 0) {
            if (this.m && this.p != -1 && this.p != this.j) {
                n = ItemDefinition.forId(this.p).getLowAlchemyValue();
            }
            if (n == 0) {
                n = (this.getShopValue() << 1) / 5;
            }
        }
        return n;
    }

    public final int getHighAlchemyValue() {
        int n = this.u;
        if (n == 0) {
            if (this.m && this.p != -1 && this.p != this.j) {
                n = ItemDefinition.forId(this.p).getHighAlchemyValue();
            }
            if (n == 0) {
                n = this.getShopValue() * 3 / 5;
            }
        }
        return n;
    }

    public final int[] getBonuses() {
        return this.x;
    }

    public final int getBonus(int n) {
        return this.x[n];
    }

    public final int getRequiredLevel(int n) {
        return this.y[n];
    }

    public final boolean requiresQuest(int n) {
        return this.z[n];
    }

    public final double getWeight() {
        return this.D;
    }

    public final int getEquipmentSlot() {
        return this.A;
    }

    public final int w() {
        return this.B;
    }

    public final int getRequiredQuestPoints() {
        return this.C;
    }

    public final boolean isTwoHanded() {
        return this.E;
    }

    public final boolean z() {
        boolean bl = this.G;
        if (this.m && this.p != -1 && this.p != this.j) {
            bl = ItemDefinition.forId(this.p).z();
        }
        return bl;
    }

    public final boolean hasDestroyOption() {
        boolean bl = this.i;
        if (this.m && this.p != -1 && this.p != this.j) {
            bl = ItemDefinition.forId(this.p).hasDestroyOption();
        }
        return bl;
    }

    public static int findIdByName(String object) {
        object = ((String)object).toLowerCase();
        ItemDefinition[] itemDefinitionArray = c;
        int n = c.length;
        int n2 = 0;
        while (n2 < n) {
            ItemDefinition itemDefinition = itemDefinitionArray[n2];
            if (itemDefinition.getName().toLowerCase().equalsIgnoreCase((String)object)) {
                object = itemDefinition;
                return ((ItemDefinition)object).j;
            }
            ++n2;
        }
        return 0;
    }
}

