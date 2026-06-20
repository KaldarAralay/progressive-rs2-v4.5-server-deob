/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.npc.drop;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.clue.TreasureTrailManager;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.NpcDefinition;
import com.rs2.model.npc.drop.NpcDropEntry;
import com.rs2.model.npc.drop.NpcDropTable;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;
import java.util.ArrayList;
import java.util.Random;

public final class NpcDropManager {
    private static Random a = new Random();
    private static int[] b = new int[]{199, 201, 203, 205, 207, 209, 211, 213, 215, 217, 219, 2485, 3049, 3051};
    private static int[] c = new int[]{5291, 5292, 5293, 5294, 5295, 5297, 5298, 5299, 5301, 5303, 5304, 5302, 5296, 5300};
    private static int[] d = new int[]{1623, 1621, 1619, 1617};
    private static int[] e = new int[]{4, 4, 4, 4, 4, 3, 3, 3, 2, 2, 2, 1, 1, 1};
    private static int[] f = new int[]{4, 4, 4, 4, 4, 3, 3, 3, 2, 2, 2, 1, 1, 1};
    private static int[] g = new int[]{4, 3, 2, 1};
    private static ArrayList h = new ArrayList();

    private static int a(Entity entity, int n) {
        Player player = null;
        if (entity.isPlayer()) {
            player = (Player)entity;
        }
        Random random = new Random();
        int n2 = 0;
        if (!entity.isPlayer() || player.eK()) {
            n2 = 1;
        }
        h.clear();
        int n3 = n - 65000;
        switch (n3) {
            case 0: {
                return -1;
            }
            case 1: {
                if (n2 != 0) {
                    return -1;
                }
                return TreasureTrailManager.randomClueItemForLevel(1);
            }
            case 2: {
                if (n2 != 0) {
                    return -1;
                }
                return TreasureTrailManager.randomClueItemForLevel(2);
            }
            case 3: {
                if (n2 != 0) {
                    return -1;
                }
                return TreasureTrailManager.randomClueItemForLevel(3);
            }
            case 4: {
                if (n2 != 0) {
                    return -1;
                }
                return TreasureTrailManager.randomClueItemForLevel(3);
            }
            case 5: {
                n3 = 0;
                while (n3 < b.length) {
                    n = e[n3];
                    n2 = 0;
                    while (n2 < n) {
                        h.add(b[n3]);
                        ++n2;
                    }
                    ++n3;
                }
                return (Integer)h.get(random.nextInt(h.size()));
            }
            case 6: {
                n3 = 0;
                while (n3 < d.length) {
                    n = g[n3];
                    n2 = 0;
                    while (n2 < n) {
                        if (!ServerSettings.freeToPlayWorld && (player == null || player.isMember()) || !ItemDefinition.forId(d[n3]).isMembersOnly()) {
                            h.add(d[n3]);
                        }
                        ++n2;
                    }
                    ++n3;
                }
                return (Integer)h.get(random.nextInt(h.size()));
            }
            case 7: {
                n3 = 0;
                while (n3 < c.length) {
                    n = f[n3];
                    n2 = 0;
                    while (n2 < n) {
                        h.add(c[n3]);
                        ++n2;
                    }
                    ++n3;
                }
                return (Integer)h.get(random.nextInt(h.size()));
            }
        }
        return -1;
    }

    private static boolean a(int n) {
        return n >= 65000;
    }

    private static int a(int n, int n2) {
        n = a.nextInt(n2 + 1 - n) + n;
        return n;
    }

    private static NpcDropTable b(int n) {
        NpcDefinition npcDefinition = NpcDefinition.forId(n);
        int n2 = npcDefinition.h();
        n = n2 == -1 ? n : n2;
        return NpcDropTable.a(n);
    }

    public static ItemStack[] a(Entity itemStackArray, int n, boolean bl) {
        int n2;
        int n3;
        int n4;
        NpcDefinition npcDefinition = NpcDefinition.forId(n);
        ItemStack[] itemStackArray2 = null;
        Object[] objectArray = null;
        ItemStack[] itemStackArray3 = null;
        objectArray = NpcDropManager.b(n);
        if ((objectArray = objectArray.a((Entity)itemStackArray)) != null && objectArray.length > 0) {
            ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
            n4 = 0;
            while (n4 < objectArray.length) {
                n3 = ((NpcDropEntry)objectArray[n4]).d();
                n2 = 1;
                if (((NpcDropEntry)objectArray[n4]).e() > 0) {
                    n2 = ((NpcDropEntry)objectArray[n4]).e();
                }
                if (((NpcDropEntry)objectArray[n4]).f() > 0) {
                    n2 = NpcDropManager.a(((NpcDropEntry)objectArray[n4]).f(), ((NpcDropEntry)objectArray[n4]).g());
                }
                if (((NpcDropEntry)objectArray[n4]).i().length > 0 && ((NpcDropEntry)objectArray[n4]).d() > 0) {
                    int n5 = a.nextInt(((NpcDropEntry)objectArray[n4]).i().length);
                    n2 = ((NpcDropEntry)objectArray[n4]).i()[n5];
                }
                if (((NpcDropEntry)objectArray[n4]).j().length > 0 && ((NpcDropEntry)objectArray[n4]).d() > 0) {
                    int n6 = a.nextInt(((NpcDropEntry)objectArray[n4]).i().length);
                    n2 = NpcDropManager.a(((NpcDropEntry)objectArray[n4]).j()[n6], ((NpcDropEntry)objectArray[n4]).k()[n6]);
                }
                if (NpcDropManager.a(n3)) {
                    if (NpcDropManager.c(n3)) {
                        ItemStack[] itemStackArray4 = NpcDropManager.a((Entity)itemStackArray, n3, n2, npcDefinition.getCombatLevel());
                        int n7 = 0;
                        while (n7 < itemStackArray4.length) {
                            arrayList.add(NpcDropManager.b(itemStackArray4[n7].getId(), itemStackArray4[n7].getAmount()));
                            ++n7;
                        }
                    } else {
                        n3 = NpcDropManager.a((Entity)itemStackArray, n3);
                    }
                }
                if (n3 == 5509) {
                    n3 = GameplayHelper.selectNextAvailableEssencePouch((Entity)itemStackArray, n3);
                }
                if (ItemDefinition.isDefined(n3)) {
                    arrayList.add(NpcDropManager.b(n3, n2));
                }
                ++n4;
            }
            itemStackArray2 = new ItemStack[arrayList.size()];
            n4 = 0;
            while (n4 < arrayList.size()) {
                itemStackArray2[n4] = (ItemStack)arrayList.get(n4);
                ++n4;
            }
        }
        objectArray = NpcDropManager.b((Entity)itemStackArray, n, true, 1, npcDefinition.getCombatLevel());
        itemStackArray3 = NpcDropManager.a((Entity)itemStackArray, n, true, 1, npcDefinition.getCombatLevel());
        int n8 = itemStackArray2 == null ? 0 : itemStackArray2.length;
        n4 = objectArray == null ? 0 : objectArray.length;
        n3 = itemStackArray3 == null ? 0 : itemStackArray3.length;
        itemStackArray = new ItemStack[n8 + n4 + n3];
        if (itemStackArray2 != null) {
            n2 = 0;
            while (n2 < n8) {
                itemStackArray[n2] = itemStackArray2[n2];
                ++n2;
            }
        }
        if (objectArray != null) {
            n2 = 0;
            while (n2 < n4) {
                itemStackArray[n2 + n8] = objectArray[n2];
                ++n2;
            }
        }
        if (itemStackArray3 != null) {
            n2 = 0;
            while (n2 < n3) {
                itemStackArray[n2 + n8 + n4] = itemStackArray3[n2];
                ++n2;
            }
        }
        return itemStackArray;
    }

    private static ItemStack[] a(Entity entity, int n, boolean bl, int n2, int n3) {
        NpcDropEntry[] npcDropEntryArray = NpcDropManager.b(n);
        if ((npcDropEntryArray = npcDropEntryArray.c(entity)) == null) {
            return null;
        }
        if (npcDropEntryArray.length == 0) {
            return null;
        }
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        int n4 = 0;
        while (n4 < npcDropEntryArray.length) {
            double d = npcDropEntryArray[n4].b();
            double d2 = npcDropEntryArray[n4].c();
            double d3 = d / d2;
            if (ServerSettings.customDropRatesEnabled) {
                d3 = NpcDropManager.a(d, d2);
            }
            if (GameUtil.a(d3)) {
                ItemStack[] itemStackArray = NpcDropManager.a(entity, n, true, 1, n3, true, n4);
                int n5 = 0;
                while (n5 < itemStackArray.length) {
                    arrayList.add(itemStackArray[n5]);
                    ++n5;
                }
            }
            ++n4;
        }
        ItemStack[] itemStackArray = new ItemStack[arrayList.size()];
        int n6 = 0;
        while (n6 < arrayList.size()) {
            itemStackArray[n6] = (ItemStack)arrayList.get(n6);
            ++n6;
        }
        return itemStackArray;
    }

    private static ItemStack[] b(Entity entity, int n, boolean bl, int n2, int n3) {
        return NpcDropManager.a(entity, n, true, n2, n3, false, -1);
    }

    private static ItemStack[] a(Entity entity, int n, boolean n2, int n3, int n4, boolean n5, int n6) {
        Object object = NpcDropManager.b(n);
        Object[] objectArray = ((NpcDropTable)object).b(entity);
        if (n5 != 0) {
            objectArray = ((NpcDropTable)object).c(entity);
        }
        if (objectArray == null) {
            return null;
        }
        if (objectArray.length == 0) {
            return null;
        }
        object = objectArray;
        int n7 = 0;
        if (n5 == 0) {
            Object object2;
            if (ServerSettings.rareDropChanceDivisor > 0 || ServerSettings.customDropRatesEnabled) {
                n7 = 1;
            }
            if (n7 != 0 && ServerSettings.rareDropChanceDivisor > 0) {
                if (n2 != 0) {
                    NpcDropManager.b((NpcDropEntry[])object);
                }
                object2 = objectArray;
                object = new ArrayList();
                ArrayList<Object> arrayList = new ArrayList<Object>();
                int n8 = 0;
                while (n8 < ((Object[])object2).length) {
                    ((NpcDropEntry)object2[n8]).d();
                    double d = ((NpcDropEntry)object2[n8]).b();
                    double d2 = ((NpcDropEntry)object2[n8]).c();
                    double d3 = d / d2;
                    double d4 = 1.0 / (double)ServerSettings.rareDropChanceDivisor;
                    if (d3 <= d4) {
                        arrayList.add(object2[n8]);
                    } else {
                        ((ArrayList)object).add(object2[n8]);
                    }
                    ++n8;
                }
                new ArrayList();
                Object object3 = arrayList.size() == 0 ? object : arrayList;
                object = new NpcDropEntry[((ArrayList)object3).size()];
                int n9 = 0;
                while (n9 < ((ArrayList)object3).size()) {
                    ((NpcDropEntry)((ArrayList)object3).get(n9)).d();
                    object[n9] = (NpcDropEntry)((ArrayList)object3).get(n9);
                    ++n9;
                }
            }
            if ((n == 6391 || n == 6392 || n == 6393) && entity.isPlayer()) {
                boolean bl;
                object2 = (Player)entity;
                if (object2.getEquipmentManager().getItemIdAtSlot(12) == 2572) {
                    object2.cd = true;
                    bl = true;
                } else {
                    bl = false;
                }
                if (bl) {
                    object = NpcDropManager.a((NpcDropEntry[])objectArray);
                    n7 = 1;
                }
            }
        }
        n = 0;
        objectArray = new ItemStack[1];
        if (n2 != 0) {
            if (n5 != 0) {
                n2 = n6;
            } else {
                NpcDropManager.b((NpcDropEntry[])object);
                n2 = NpcDropManager.a((NpcDropEntry[])object, n7 != 0);
            }
            if (n2 == -1) {
                n = 1;
                n2 = 0;
            }
        } else {
            n2 = a.nextInt(((NpcDropEntry[])object).length);
        }
        n6 = 0;
        if (object[n2].h().length > 0) {
            n7 = a.nextInt(object[n2].h().length);
            n5 = object[n2].h()[n7];
        } else {
            n5 = object[n2].d();
        }
        n7 = 0;
        if (object[n2].e() > 0) {
            n7 = object[n2].e();
        }
        if (object[n2].f() > 0) {
            n7 = NpcDropManager.a(object[n2].f(), object[n2].g());
        }
        if (object[n2].i().length > 0 && object[n2].d() > 0) {
            n7 = a.nextInt(object[n2].i().length);
            n7 = object[n2].i()[n7];
        }
        if (object[n2].h().length > 1 && object[n2].j().length <= 1) {
            n7 = object[n2].i()[0];
        }
        if (object[n2].j().length > 1) {
            n7 = NpcDropManager.a(object[n2].j()[0], ((NpcDropEntry)object[n2]).k()[0]);
        }
        if (n != 0) {
            n5 = 65000;
        }
        if (NpcDropManager.a(n5)) {
            if (NpcDropManager.c(n5)) {
                ItemStack[] itemStackArray = NpcDropManager.a(entity, n5, n7 * n3, n4);
                if (itemStackArray != null) {
                    objectArray = new ItemStack[itemStackArray.length];
                    n = 0;
                    while (n < itemStackArray.length) {
                        objectArray[n] = NpcDropManager.b(itemStackArray[n].getId(), itemStackArray[n].getAmount());
                        ++n;
                    }
                }
            } else {
                n5 = NpcDropManager.a(entity, n5);
            }
        }
        if (n5 == 5509) {
            n5 = GameplayHelper.selectNextAvailableEssencePouch(entity, n5);
        }
        if (((NpcDropEntry)object[n2]).e() > 0) {
            n6 = ((NpcDropEntry)object[n2]).e();
        }
        if (((NpcDropEntry)object[n2]).f() > 0) {
            n6 = NpcDropManager.a(((NpcDropEntry)object[n2]).f(), ((NpcDropEntry)object[n2]).g());
        }
        if (((NpcDropEntry)object[n2]).i().length > 0 && ((NpcDropEntry)object[n2]).d() > 0) {
            int n10 = a.nextInt(((NpcDropEntry)object[n2]).i().length);
            n6 = ((NpcDropEntry)object[n2]).i()[n10];
        }
        if (((NpcDropEntry)object[n2]).h().length > 1) {
            ArrayList<Object> arrayList = new ArrayList<Object>();
            n = 0;
            while (n < ((NpcDropEntry)object[n2]).h().length) {
                n5 = ((NpcDropEntry)object[n2]).h()[n];
                n6 = ((NpcDropEntry)object[n2]).j().length > 1 ? NpcDropManager.a(((NpcDropEntry)object[n2]).j()[n], ((NpcDropEntry)object[n2]).k()[n]) : ((NpcDropEntry)object[n2]).i()[n];
                if (NpcDropManager.a(n5)) {
                    if (NpcDropManager.c(n5)) {
                        objectArray = NpcDropManager.a(entity, n5, n6 * n3, n4);
                        int n11 = 0;
                        while (n11 < objectArray.length) {
                            arrayList.add(objectArray[n11]);
                            ++n11;
                        }
                    } else {
                        n5 = NpcDropManager.a(entity, n5);
                    }
                }
                if (n5 == 5509) {
                    n5 = GameplayHelper.selectNextAvailableEssencePouch(entity, n5);
                }
                if (ItemDefinition.isDefined(n5)) {
                    arrayList.add(NpcDropManager.b(n5, n6 * n3));
                }
                ++n;
            }
            objectArray = new ItemStack[arrayList.size()];
            n = 0;
            while (n < arrayList.size()) {
                objectArray[n] = (ItemStack)arrayList.get(n);
                ++n;
            }
        } else if (ItemDefinition.isDefined(n5)) {
            objectArray[0] = NpcDropManager.b(n5, n6 * n3);
        }
        return objectArray;
    }

    private static NpcDropEntry[] a(NpcDropEntry[] npcDropEntryArray) {
        int n;
        ArrayList<NpcDropEntry> arrayList = new ArrayList<NpcDropEntry>();
        int n2 = 0;
        while (n2 < npcDropEntryArray.length) {
            n = npcDropEntryArray[n2].d();
            if (n != 65000) {
                arrayList.add(npcDropEntryArray[n2]);
            }
            ++n2;
        }
        NpcDropEntry[] npcDropEntryArray2 = new NpcDropEntry[arrayList.size()];
        n = 0;
        while (n < arrayList.size()) {
            ((NpcDropEntry)arrayList.get(n)).d();
            npcDropEntryArray2[n] = (NpcDropEntry)arrayList.get(n);
            ++n;
        }
        return npcDropEntryArray2;
    }

    private static boolean c(int n) {
        return n >= 65008;
    }

    private static ItemStack[] a(Entity entity, int n, int n2, int n3) {
        ItemStack[] itemStackArray = null;
        if (n == 65008) {
            itemStackArray = NpcDropManager.b(entity, 6391, true, n2, n3);
        } else if (n == 65009) {
            itemStackArray = NpcDropManager.b(entity, 6392, true, n2, n3);
        } else if (n == 65010) {
            itemStackArray = NpcDropManager.b(entity, 6393, true, n2, n3);
        } else if (n == 65011) {
            itemStackArray = NpcDropManager.b(entity, 6394, true, n2, n3);
        } else if (n == 65012) {
            itemStackArray = NpcDropManager.b(entity, 6395, true, n2, n3);
        } else if (n == 65013) {
            itemStackArray = NpcDropManager.b(entity, 6396, true, n2, n3);
        } else if (n == 65014) {
            itemStackArray = NpcDropManager.b(entity, 6397, true, n2, n3);
        } else if (n == 65015) {
            itemStackArray = NpcDropManager.b(entity, 6398, true, n2, n3);
        } else if (n == 65016) {
            itemStackArray = NpcDropManager.b(entity, 6399, true, n2, n3);
        } else if (n == 65017) {
            itemStackArray = NpcDropManager.b(entity, 6400, true, n2, n3);
        } else if (n == 65018) {
            n = 1 + n3 * 10;
            if ((n = GameUtil.h(n)) < 485) {
                itemStackArray = NpcDropManager.b(entity, 6401, true, n2, n3);
            } else if (n >= 485 && n < 728) {
                itemStackArray = NpcDropManager.b(entity, 6414, true, n2, n3);
            } else if (n >= 728 && n < 850) {
                itemStackArray = NpcDropManager.b(entity, 6415, true, n2, n3);
            } else if (n >= 850 && n < 947) {
                itemStackArray = NpcDropManager.b(entity, 6416, true, n2, n3);
            } else if (n >= 947 && n < 995) {
                itemStackArray = NpcDropManager.b(entity, 6417, true, n2, n3);
            } else if (n >= 995) {
                itemStackArray = NpcDropManager.b(entity, 6418, true, n2, n3);
            }
        } else if (n == 65019) {
            itemStackArray = NpcDropManager.b(entity, 6402, true, n2, n3);
        } else if (n == 65020) {
            itemStackArray = NpcDropManager.b(entity, 6403, true, n2, n3);
        } else if (n == 65021) {
            itemStackArray = NpcDropManager.b(entity, 6404, true, n2, n3);
        } else if (n == 65022) {
            itemStackArray = NpcDropManager.b(entity, 6405, true, n2, n3);
        } else if (n == 65023) {
            itemStackArray = NpcDropManager.b(entity, 6406, true, n2, n3);
        } else if (n == 65024) {
            itemStackArray = NpcDropManager.b(entity, 6407, true, n2, n3);
        } else if (n == 65025) {
            itemStackArray = NpcDropManager.b(entity, 6408, true, n2, n3);
        } else if (n == 65026) {
            itemStackArray = NpcDropManager.b(entity, 6409, true, n2, n3);
        } else if (n == 65027) {
            itemStackArray = NpcDropManager.b(entity, 6410, true, n2, n3);
        } else if (n == 65028) {
            itemStackArray = NpcDropManager.b(entity, 6411, true, n2, n3);
        } else if (n == 65029) {
            itemStackArray = NpcDropManager.b(entity, 6412, true, n2, n3);
        } else if (n == 65030) {
            itemStackArray = NpcDropManager.b(entity, 6413, true, n2, n3);
        } else if (n == 65036) {
            itemStackArray = NpcDropManager.b(entity, 6419, true, n2, n3);
        } else if (n == 65040) {
            itemStackArray = NpcDropManager.b(entity, 6423, true, n2, n3);
        } else if (n == 65041) {
            itemStackArray = NpcDropManager.b(entity, 6424, true, n2, n3);
        } else if (n == 65042) {
            itemStackArray = NpcDropManager.b(entity, 6425, true, n2, n3);
        }
        return itemStackArray;
    }

    private static void b(NpcDropEntry[] npcDropEntryArray) {
        int n = npcDropEntryArray.length;
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        double d = 0.0;
        int n2 = 0;
        while (n2 < n) {
            if (npcDropEntryArray[n2].a() == 1) {
                arrayList.add(n2);
            } else {
                double d2 = npcDropEntryArray[n2].b();
                double d3 = npcDropEntryArray[n2].c();
                double d4 = d2 / d3;
                d += d4;
            }
            ++n2;
        }
        if (arrayList.size() > 0) {
            String string;
            double d5;
            double d6 = d5 = (1.0 - d) / (double)arrayList.size();
            double d7 = Math.floor(d5);
            double d8 = d6 - d7;
            long l = NpcDropManager.a(Math.round(d8 * 1.0E9), 1000000000L);
            long l2 = Math.round(d8 * 1.0E9) / l;
            long l3 = 1000000000L / l;
            String string2 = string = String.valueOf((long)(d7 * (double)l3) + l2) + "/" + l3;
            String[] stringArray = string.split("/");
            int n3 = 0;
            while (n3 < arrayList.size()) {
                int n4 = (Integer)arrayList.get(n3);
                npcDropEntryArray[n4].a(Integer.parseInt(stringArray[0]));
                npcDropEntryArray[n4].b(Integer.parseInt(stringArray[1]));
                ++n3;
            }
        }
    }

    private static long a(long l, long l2) {
        while (l != 0L) {
            if (l2 == 0L) {
                return l;
            }
            if (l < l2) {
                l2 %= l;
                continue;
            }
            long l3 = l2;
            l2 = l % l2;
            l = l3;
        }
        return l2;
    }

    private static double a(double d, double d2) {
        double d3;
        double d4;
        double d5;
        double d6 = d / d2;
        double d7 = d5 >= 0.043478260869565216 ? d * ServerSettings.commonDropRateMultiplier : (d6 >= 0.011111111111111112 ? d * ServerSettings.uncommonDropRateMultiplier : (d6 >= 0.0012300123001230013 ? d * ServerSettings.rareDropRateMultiplier : d * ServerSettings.veryRareDropRateMultiplier));
        double d8 = d7 / d2;
        if (ServerSettings.dropRateCap > 0 && d8 < (d4 = 1.0 / (d3 = (double)ServerSettings.dropRateCap))) {
            d8 = d4;
        }
        return d8;
    }

    private static int a(NpcDropEntry[] npcDropEntryArray, boolean bl) {
        double d = 1.0;
        if (bl) {
            d = 0.0;
        }
        int n = npcDropEntryArray.length;
        int n2 = 0;
        while (n2 < n) {
            double d2 = npcDropEntryArray[n2].b();
            double d3 = npcDropEntryArray[n2].c();
            double d4 = d2 / d3;
            if (ServerSettings.customDropRatesEnabled) {
                d4 = NpcDropManager.a(d2, d3);
            }
            if (bl) {
                d += d4;
            }
            ++n2;
        }
        double d5 = Math.random();
        double d6 = d5 * d;
        double d7 = 0.0;
        int n3 = 0;
        while (n3 < n) {
            double d8;
            double d9 = npcDropEntryArray[n3].b();
            double d10 = npcDropEntryArray[n3].c();
            double d11 = d9 / d10;
            if (ServerSettings.customDropRatesEnabled) {
                d11 = NpcDropManager.a(d9, d10);
            }
            d7 += d11;
            if (d8 >= d6) {
                return n3;
            }
            ++n3;
        }
        return -1;
    }

    private static ItemStack b(int n, int n2) {
        return new ItemStack(n, n2);
    }
}

