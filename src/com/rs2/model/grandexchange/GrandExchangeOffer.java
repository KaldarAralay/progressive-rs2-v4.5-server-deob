/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.grandexchange;

import com.rs2.ServerSettings;
import com.rs2.bot.BotTradeAdvertManager;
import com.rs2.model.GameplayHelper;
import com.rs2.model.World;
import com.rs2.model.grandexchange.GrandExchangePriceSample;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.player.CharacterFileRecord;
import com.rs2.model.player.GrandExchangeManager;
import com.rs2.model.player.Player;
import com.rs2.util.CharacterFileManager;
import com.rs2.util.GameUtil;
import java.util.ArrayList;
import java.util.Iterator;

public final class GrandExchangeOffer {
    private String d;
    private boolean e;
    int a;
    private int f;
    int b;
    private int g;
    int c;
    private long h;
    private int i;
    private static ArrayList j = new ArrayList();
    private static ArrayList k = new ArrayList();
    private static ArrayList l = new ArrayList();
    private static ArrayList m = new ArrayList();
    private static ArrayList n = new ArrayList();

    public GrandExchangeOffer(String string, int n, boolean bl, int n2, int n3, int n4, int n5) {
        new GrandExchangeOffer(string, n, bl, n2, n3, n4, n5, 0L, 0);
    }

    private GrandExchangeOffer(String string, int n, boolean bl, int n2, int n3, int n4, int n5, long l, int n6) {
        this.d = string;
        this.a = n2;
        this.e = bl;
        this.g = n3;
        this.c = n5;
        this.f = n;
        this.b = n4;
        this.h = l;
        this.i = n6;
        if (bl) {
            j.add(this);
        } else {
            k.add(this);
        }
        this.a(this, bl);
    }

    public static void a() {
        int n = 0;
        while (n < ServerSettings.grandExchangeServerOfferCount) {
            GrandExchangeOffer.c();
            ++n;
        }
    }

    private static void c() {
        double d;
        boolean bl = GameUtil.h(2) == 0;
        int n = GameUtil.h(100);
        Object object = n >= BotTradeAdvertManager.commonItemChancePercent ? BotTradeAdvertManager.tradeAdvertOfferPool : BotTradeAdvertManager.commonTradeAdvertOfferPool;
        int n2 = GameUtil.h(((ArrayList)object).size());
        object = (GameplayHelper)((ArrayList)object).get(n2);
        n2 = BotTradeAdvertManager.tradeAdvertOfferPool.indexOf(object);
        int n3 = ((GameplayHelper)BotTradeAdvertManager.tradeAdvertOfferPool.get(n2)).c();
        ItemDefinition.forId(n3);
        double d2 = GrandExchangeManager.a(n3);
        if (d < 1.0) {
            d2 = 1.0;
        }
        int n4 = GameUtil.i(11);
        double d3 = d2 / 100.0 * (double)n4;
        if (bl && GameUtil.h(10) == 0) {
            d3 = -d3;
        }
        if (!bl && GameUtil.h(10) == 0) {
            d3 = -d3;
        }
        double d4 = bl ? d2 + d3 : d2 - d3;
        n4 = (int)d4;
        int n5 = GameUtil.h(((GameplayHelper)BotTradeAdvertManager.tradeAdvertOfferPool.get(n2)).d().length);
        n2 = GameUtil.h(((GameplayHelper)BotTradeAdvertManager.tradeAdvertOfferPool.get(n2)).d()[n5]) + 1;
        n4 = n2 * n4;
        if ((n4 = BotTradeAdvertManager.roundAdvertUnitPrice(bl, n4, n2)) <= 0) {
            n4 = 1 + GameUtil.h(5);
        }
        n5 = 2 + GameUtil.h(4);
        GrandExchangeOffer grandExchangeOffer = new GrandExchangeOffer("[SERVER]", 0, bl, n3, n2, n2, n4, System.currentTimeMillis(), n5);
        l.add(grandExchangeOffer);
    }

    public static void b() {
        int n = 0;
        ArrayList<GrandExchangeOffer> arrayList = new ArrayList<GrandExchangeOffer>();
        ArrayList<GrandExchangeOffer> arrayList2 = new ArrayList<GrandExchangeOffer>();
        ArrayList<GrandExchangeOffer> arrayList3 = new ArrayList<GrandExchangeOffer>();
        Iterator iterator = l.iterator();
        while (iterator.hasNext()) {
            GrandExchangeOffer grandExchangeOffer;
            GrandExchangeOffer grandExchangeOffer2 = grandExchangeOffer = (GrandExchangeOffer)iterator.next();
            if (!(System.currentTimeMillis() >= grandExchangeOffer2.h + (long)GameUtil.l(grandExchangeOffer2.i))) continue;
            if (grandExchangeOffer.e) {
                arrayList.add(grandExchangeOffer);
            } else {
                arrayList2.add(grandExchangeOffer);
            }
            arrayList3.add(grandExchangeOffer);
            ++n;
        }
        for (GrandExchangeOffer grandExchangeOffer : arrayList) {
            j.remove(grandExchangeOffer);
        }
        for (GrandExchangeOffer grandExchangeOffer : arrayList2) {
            k.remove(grandExchangeOffer);
        }
        for (GrandExchangeOffer grandExchangeOffer : arrayList3) {
            l.remove(grandExchangeOffer);
        }
        int n2 = 0;
        while (n2 < n) {
            GrandExchangeOffer.c();
            ++n2;
        }
    }

    public static void a(Player player, int n) {
        GrandExchangeOffer grandExchangeOffer;
        GrandExchangeOffer grandExchangeOffer22;
        Iterator iterator;
        String string = player.getUsername();
        if (player.dw[n]) {
            iterator = j.iterator();
            while (iterator.hasNext()) {
                grandExchangeOffer = grandExchangeOffer22 = (GrandExchangeOffer)iterator.next();
                if (!grandExchangeOffer22.d.equals(string)) continue;
                grandExchangeOffer = grandExchangeOffer22;
                if (grandExchangeOffer.f != n || !GrandExchangeOffer.a(player, grandExchangeOffer22)) continue;
                GrandExchangeOffer.n.add(grandExchangeOffer22);
                break;
            }
        } else {
            iterator = k.iterator();
            while (iterator.hasNext()) {
                grandExchangeOffer = grandExchangeOffer22 = (GrandExchangeOffer)iterator.next();
                if (!grandExchangeOffer22.d.equals(string)) continue;
                grandExchangeOffer = grandExchangeOffer22;
                if (grandExchangeOffer.f != n || !GrandExchangeOffer.a(player, grandExchangeOffer22)) continue;
                m.add(grandExchangeOffer22);
                break;
            }
        }
        for (GrandExchangeOffer grandExchangeOffer22 : m) {
            k.remove(grandExchangeOffer22);
        }
        for (GrandExchangeOffer grandExchangeOffer22 : GrandExchangeOffer.n) {
            j.remove(grandExchangeOffer22);
        }
    }

    private void a(GrandExchangeOffer grandExchangeOffer, boolean bl) {
        GrandExchangeOffer grandExchangeOffer2;
        Iterator iterator;
        m.clear();
        n.clear();
        int n = 0;
        if (bl) {
            iterator = k.iterator();
            while (iterator.hasNext()) {
                GrandExchangeOffer grandExchangeOffer3;
                grandExchangeOffer2 = grandExchangeOffer3 = (GrandExchangeOffer)iterator.next();
                grandExchangeOffer2 = grandExchangeOffer;
                if (grandExchangeOffer3.d.equals(grandExchangeOffer2.d)) continue;
                GrandExchangeOffer grandExchangeOffer4 = grandExchangeOffer3;
                grandExchangeOffer2 = grandExchangeOffer4;
                grandExchangeOffer2 = grandExchangeOffer;
                if (grandExchangeOffer4.a != grandExchangeOffer2.a) continue;
                GrandExchangeOffer grandExchangeOffer5 = grandExchangeOffer3;
                grandExchangeOffer2 = grandExchangeOffer5;
                grandExchangeOffer2 = grandExchangeOffer;
                if (grandExchangeOffer5.c < grandExchangeOffer2.c) continue;
                this.a(grandExchangeOffer, grandExchangeOffer3);
                grandExchangeOffer2 = grandExchangeOffer3;
                if (grandExchangeOffer2.g == 0) {
                    m.add(grandExchangeOffer3);
                }
                grandExchangeOffer2 = grandExchangeOffer;
                if (grandExchangeOffer2.g != 0) continue;
                GrandExchangeOffer.n.add(grandExchangeOffer);
                break;
            }
        } else {
            iterator = j.iterator();
            while (iterator.hasNext()) {
                GrandExchangeOffer grandExchangeOffer6;
                grandExchangeOffer2 = grandExchangeOffer6 = (GrandExchangeOffer)iterator.next();
                grandExchangeOffer2 = grandExchangeOffer;
                if (grandExchangeOffer6.d.equals(grandExchangeOffer2.d)) continue;
                GrandExchangeOffer grandExchangeOffer7 = grandExchangeOffer6;
                grandExchangeOffer2 = grandExchangeOffer7;
                grandExchangeOffer2 = grandExchangeOffer;
                if (grandExchangeOffer7.a != grandExchangeOffer2.a) continue;
                GrandExchangeOffer grandExchangeOffer8 = grandExchangeOffer6;
                grandExchangeOffer2 = grandExchangeOffer8;
                grandExchangeOffer2 = grandExchangeOffer;
                if (grandExchangeOffer8.c > grandExchangeOffer2.c) continue;
                this.a(grandExchangeOffer6, grandExchangeOffer);
                grandExchangeOffer2 = grandExchangeOffer6;
                if (grandExchangeOffer2.g == 0) {
                    GrandExchangeOffer.n.add(grandExchangeOffer6);
                }
                grandExchangeOffer2 = grandExchangeOffer;
                if (grandExchangeOffer2.g != 0) continue;
                m.add(grandExchangeOffer);
                break;
            }
        }
        for (GrandExchangeOffer grandExchangeOffer9 : m) {
            k.remove(grandExchangeOffer9);
            if (!l.contains(grandExchangeOffer9)) continue;
            l.remove(grandExchangeOffer9);
            ++n;
        }
        for (GrandExchangeOffer grandExchangeOffer10 : GrandExchangeOffer.n) {
            j.remove(grandExchangeOffer10);
            if (!l.contains(grandExchangeOffer10)) continue;
            l.remove(grandExchangeOffer10);
            ++n;
        }
        int n2 = 0;
        while (n2 < n) {
            GrandExchangeOffer.c();
            ++n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a(GrandExchangeOffer grandExchangeOffer, GrandExchangeOffer grandExchangeOffer2) {
        boolean bl = false;
        boolean bl2 = false;
        GrandExchangeOffer grandExchangeOffer3 = grandExchangeOffer;
        String string = grandExchangeOffer3.d;
        grandExchangeOffer3 = grandExchangeOffer2;
        String string2 = grandExchangeOffer3.d;
        if (string.equals("[SERVER]")) {
            bl = true;
        }
        if (string2.equals("[SERVER]")) {
            bl2 = true;
        }
        Object object = null;
        Object object2 = null;
        Object object3 = null;
        Object object4 = null;
        Player[] playerArray = World.f();
        int n = playerArray.length;
        int n2 = 0;
        while (n2 < n) {
            Object object5 = playerArray[n2];
            if (object5 != null) {
                if (((Player)object5).getUsername().equals(string)) {
                    if (!GrandExchangeOffer.a((Player)object5, grandExchangeOffer)) {
                        GrandExchangeOffer.n.add(grandExchangeOffer);
                        return;
                    }
                    object = object5;
                    bl = true;
                } else if (((Player)object5).getUsername().equals(string2)) {
                    if (!GrandExchangeOffer.a((Player)object5, grandExchangeOffer2)) {
                        m.add(grandExchangeOffer2);
                        return;
                    }
                    object2 = object5;
                    bl2 = true;
                }
                if (bl && bl2) break;
            }
            ++n2;
        }
        if (!bl || !bl2) {
            for (Object object5 : CharacterFileManager.liveHiscoreRecords) {
                if (object5 == null) continue;
                if (!bl) {
                    Object object6 = object5;
                    if (((CharacterFileRecord)object6).username.equals(string)) {
                        if (!GrandExchangeOffer.a((CharacterFileRecord)object5, grandExchangeOffer)) {
                            GrandExchangeOffer.n.add(grandExchangeOffer);
                            return;
                        }
                        object3 = object5;
                        bl = true;
                    }
                }
                if (!bl2) {
                    Object object7 = object5;
                    if (((CharacterFileRecord)object7).username.equals(string2)) {
                        if (!GrandExchangeOffer.a((CharacterFileRecord)object5, grandExchangeOffer2)) {
                            m.add(grandExchangeOffer2);
                            return;
                        }
                        object4 = object5;
                        bl2 = true;
                    }
                }
                if (bl && bl2) break;
            }
        }
        if (object4 != null && object3 != null) {
            GrandExchangeOffer.a(object4, grandExchangeOffer2, object3, grandExchangeOffer);
            return;
        }
        if (object2 != null && object != null) {
            ((Player)object2).eC = true;
            ((Player)object).eC = true;
            GrandExchangeOffer.a(object2, grandExchangeOffer2, (Player)object, grandExchangeOffer);
            ((Player)object2).eC = false;
            ((Player)object).eC = false;
            return;
        }
        if (object4 != null && object != null) {
            ((Player)object).eC = true;
            GrandExchangeOffer.a(object4, grandExchangeOffer2, (Player)object, grandExchangeOffer);
            ((Player)object).eC = false;
            return;
        }
        if (object2 != null && object3 != null) {
            ((Player)object2).eC = true;
            GrandExchangeOffer.a(object2, grandExchangeOffer2, object3, grandExchangeOffer);
            ((Player)object2).eC = false;
            return;
        }
        if (object4 != null && string.equals("[SERVER]")) {
            GrandExchangeOffer.a(object4, grandExchangeOffer2, object3, grandExchangeOffer);
            return;
        }
        if (object2 != null && string.equals("[SERVER]")) {
            ((Player)object2).eC = true;
            GrandExchangeOffer.a((Player)object2, grandExchangeOffer2, (Player)object, grandExchangeOffer);
            ((Player)object2).eC = false;
            return;
        }
        if (string2.equals("[SERVER]") && object != null) {
            ((Player)object).eC = true;
            GrandExchangeOffer.a(object4, grandExchangeOffer2, (Player)object, grandExchangeOffer);
            ((Player)object).eC = false;
            return;
        }
        if (string2.equals("[SERVER]") && object3 != null) {
            GrandExchangeOffer.a((Player)object2, grandExchangeOffer2, (CharacterFileRecord)object3, grandExchangeOffer);
        }
    }

    private static void a(CharacterFileRecord characterFileRecord, GrandExchangeOffer grandExchangeOffer, CharacterFileRecord characterFileRecord2, GrandExchangeOffer grandExchangeOffer2) {
        int n;
        GrandExchangeOffer grandExchangeOffer3 = grandExchangeOffer2;
        int n2 = grandExchangeOffer3.g;
        grandExchangeOffer3 = grandExchangeOffer;
        if (grandExchangeOffer3.g >= n2) {
            n = n2;
        } else {
            grandExchangeOffer3 = grandExchangeOffer;
            n = grandExchangeOffer3.g;
        }
        n2 = n;
        grandExchangeOffer3 = grandExchangeOffer2;
        int n3 = grandExchangeOffer3.c;
        grandExchangeOffer3 = grandExchangeOffer2;
        int n4 = grandExchangeOffer3.c * n2;
        grandExchangeOffer3 = grandExchangeOffer;
        int n5 = grandExchangeOffer3.g - n2;
        grandExchangeOffer3 = grandExchangeOffer;
        grandExchangeOffer.g = n5;
        grandExchangeOffer3 = grandExchangeOffer2;
        n5 = grandExchangeOffer3.g - n2;
        grandExchangeOffer3 = grandExchangeOffer2;
        grandExchangeOffer2.g = n5;
        grandExchangeOffer3 = grandExchangeOffer;
        int n6 = grandExchangeOffer3.f;
        characterFileRecord.grandExchangeCompletedQuantities[n6] = characterFileRecord.grandExchangeCompletedQuantities[n6] + n2;
        if (characterFileRecord2 != null) {
            grandExchangeOffer3 = grandExchangeOffer2;
            int n7 = grandExchangeOffer3.f;
            characterFileRecord2.grandExchangeCompletedQuantities[n7] = characterFileRecord2.grandExchangeCompletedQuantities[n7] + n2;
        }
        grandExchangeOffer3 = grandExchangeOffer;
        int n8 = grandExchangeOffer3.f;
        characterFileRecord.grandExchangeTotalPrices[n8] = characterFileRecord.grandExchangeTotalPrices[n8] + n4;
        if (characterFileRecord2 != null) {
            grandExchangeOffer3 = grandExchangeOffer2;
            int n9 = grandExchangeOffer3.f;
            characterFileRecord2.grandExchangeTotalPrices[n9] = characterFileRecord2.grandExchangeTotalPrices[n9] + n4;
        }
        grandExchangeOffer3 = grandExchangeOffer;
        if (n3 < grandExchangeOffer3.c) {
            grandExchangeOffer3 = grandExchangeOffer;
            n3 = grandExchangeOffer3.c - n3;
            grandExchangeOffer3 = grandExchangeOffer;
            int n10 = grandExchangeOffer3.f;
            characterFileRecord.grandExchangeSecondaryCollectAmounts[n10] = characterFileRecord.grandExchangeSecondaryCollectAmounts[n10] + (n3 *= n2);
        }
        grandExchangeOffer3 = grandExchangeOffer;
        int n11 = grandExchangeOffer3.f;
        characterFileRecord.grandExchangePrimaryCollectAmounts[n11] = characterFileRecord.grandExchangePrimaryCollectAmounts[n11] + n2;
        if (characterFileRecord2 != null) {
            grandExchangeOffer3 = grandExchangeOffer2;
            int n12 = grandExchangeOffer3.f;
            characterFileRecord2.grandExchangePrimaryCollectAmounts[n12] = characterFileRecord2.grandExchangePrimaryCollectAmounts[n12] + n4;
        }
        grandExchangeOffer3 = grandExchangeOffer;
        if (grandExchangeOffer3.g == 0) {
            grandExchangeOffer3 = grandExchangeOffer;
            characterFileRecord.grandExchangeFinishMessagePending[grandExchangeOffer3.f] = true;
        }
        grandExchangeOffer3 = grandExchangeOffer2;
        if (grandExchangeOffer3.g == 0) {
            if (characterFileRecord2 != null) {
                grandExchangeOffer3 = grandExchangeOffer2;
                characterFileRecord2.grandExchangeFinishMessagePending[grandExchangeOffer3.f] = true;
            }
            new GrandExchangePriceSample(grandExchangeOffer2);
        }
        CharacterFileManager.saveCharacterFileRecord(characterFileRecord);
        if (characterFileRecord2 != null) {
            CharacterFileManager.saveCharacterFileRecord(characterFileRecord2);
        }
    }

    private static void a(Player player, GrandExchangeOffer grandExchangeOffer, Player player2, GrandExchangeOffer grandExchangeOffer2) {
        int n;
        Object object = grandExchangeOffer2;
        int n2 = ((GrandExchangeOffer)object).g;
        object = grandExchangeOffer;
        if (((GrandExchangeOffer)object).g >= n2) {
            n = n2;
        } else {
            object = grandExchangeOffer;
            n = ((GrandExchangeOffer)object).g;
        }
        n2 = n;
        object = grandExchangeOffer2;
        int n3 = ((GrandExchangeOffer)object).c;
        object = grandExchangeOffer2;
        int n4 = ((GrandExchangeOffer)object).c * n2;
        object = grandExchangeOffer;
        int n5 = ((GrandExchangeOffer)object).g - n2;
        object = grandExchangeOffer;
        grandExchangeOffer.g = n5;
        object = grandExchangeOffer2;
        n5 = ((GrandExchangeOffer)object).g - n2;
        object = grandExchangeOffer2;
        grandExchangeOffer2.g = n5;
        object = grandExchangeOffer;
        int n6 = ((GrandExchangeOffer)object).f;
        player.dB[n6] = player.dB[n6] + n2;
        if (player2 != null) {
            object = grandExchangeOffer2;
            int n7 = ((GrandExchangeOffer)object).f;
            player2.dB[n7] = player2.dB[n7] + n2;
        }
        object = grandExchangeOffer;
        int n8 = ((GrandExchangeOffer)object).f;
        player.dC[n8] = player.dC[n8] + n4;
        if (player2 != null) {
            object = grandExchangeOffer2;
            int n9 = ((GrandExchangeOffer)object).f;
            player2.dC[n9] = player2.dC[n9] + n4;
        }
        object = grandExchangeOffer;
        if (n3 < ((GrandExchangeOffer)object).c) {
            object = grandExchangeOffer;
            n3 = ((GrandExchangeOffer)object).c - n3;
            object = grandExchangeOffer;
            int n10 = ((GrandExchangeOffer)object).f;
            player.dE[n10] = player.dE[n10] + (n3 *= n2);
        }
        object = grandExchangeOffer;
        int n11 = ((GrandExchangeOffer)object).f;
        player.dD[n11] = player.dD[n11] + n2;
        if (player2 != null) {
            object = grandExchangeOffer2;
            int n12 = ((GrandExchangeOffer)object).f;
            player2.dD[n12] = player2.dD[n12] + n4;
        }
        object = grandExchangeOffer;
        if (((GrandExchangeOffer)object).g == 0 && player != null) {
            object = grandExchangeOffer;
            GrandExchangeManager.a(player, ((GrandExchangeOffer)object).f);
        }
        object = grandExchangeOffer2;
        if (((GrandExchangeOffer)object).g == 0) {
            if (player2 != null) {
                object = grandExchangeOffer2;
                GrandExchangeManager.a(player2, ((GrandExchangeOffer)object).f);
            }
            new GrandExchangePriceSample(grandExchangeOffer2);
        }
        if (player.getOpenInterfaceId() == 19018) {
            GrandExchangeManager.c(player);
        }
        if (player.getOpenInterfaceId() == 18984) {
            object = grandExchangeOffer;
            if (player.dJ == ((GrandExchangeOffer)object).f) {
                GrandExchangeManager.b(player);
            }
        }
        if (player2 != null) {
            if (player2.getOpenInterfaceId() == 19018) {
                GrandExchangeManager.c(player2);
            }
            if (player2.getOpenInterfaceId() == 18984) {
                object = grandExchangeOffer2;
                if (player2.dJ == ((GrandExchangeOffer)object).f) {
                    GrandExchangeManager.b(player2);
                }
            }
        }
        object = player;
        CharacterFileManager.savePlayer((Player)object);
        if (player2 != null) {
            object = player2;
            CharacterFileManager.savePlayer((Player)object);
        }
    }

    private static void a(Player player, GrandExchangeOffer grandExchangeOffer, CharacterFileRecord characterFileRecord, GrandExchangeOffer grandExchangeOffer2) {
        int n;
        Object object = grandExchangeOffer2;
        int n2 = ((GrandExchangeOffer)object).g;
        object = grandExchangeOffer;
        if (((GrandExchangeOffer)object).g >= n2) {
            n = n2;
        } else {
            object = grandExchangeOffer;
            n = ((GrandExchangeOffer)object).g;
        }
        n2 = n;
        object = grandExchangeOffer2;
        int n3 = ((GrandExchangeOffer)object).c;
        object = grandExchangeOffer2;
        int n4 = ((GrandExchangeOffer)object).c * n2;
        object = grandExchangeOffer;
        int n5 = ((GrandExchangeOffer)object).g - n2;
        object = grandExchangeOffer;
        grandExchangeOffer.g = n5;
        object = grandExchangeOffer2;
        n5 = ((GrandExchangeOffer)object).g - n2;
        object = grandExchangeOffer2;
        grandExchangeOffer2.g = n5;
        if (player != null) {
            object = grandExchangeOffer;
            int n6 = ((GrandExchangeOffer)object).f;
            player.dB[n6] = player.dB[n6] + n2;
        }
        object = grandExchangeOffer2;
        int n7 = ((GrandExchangeOffer)object).f;
        characterFileRecord.grandExchangeCompletedQuantities[n7] = characterFileRecord.grandExchangeCompletedQuantities[n7] + n2;
        if (player != null) {
            object = grandExchangeOffer;
            int n8 = ((GrandExchangeOffer)object).f;
            player.dC[n8] = player.dC[n8] + n4;
        }
        object = grandExchangeOffer2;
        int n9 = ((GrandExchangeOffer)object).f;
        characterFileRecord.grandExchangeTotalPrices[n9] = characterFileRecord.grandExchangeTotalPrices[n9] + n4;
        object = grandExchangeOffer;
        if (n3 < ((GrandExchangeOffer)object).c) {
            object = grandExchangeOffer;
            n3 = ((GrandExchangeOffer)object).c - n3;
            n3 *= n2;
            if (player != null) {
                object = grandExchangeOffer;
                int n10 = ((GrandExchangeOffer)object).f;
                player.dE[n10] = player.dE[n10] + n3;
            }
        }
        if (player != null) {
            object = grandExchangeOffer;
            int n11 = ((GrandExchangeOffer)object).f;
            player.dD[n11] = player.dD[n11] + n2;
        }
        object = grandExchangeOffer2;
        int n12 = ((GrandExchangeOffer)object).f;
        characterFileRecord.grandExchangePrimaryCollectAmounts[n12] = characterFileRecord.grandExchangePrimaryCollectAmounts[n12] + n4;
        object = grandExchangeOffer;
        if (((GrandExchangeOffer)object).g == 0 && player != null) {
            object = grandExchangeOffer;
            GrandExchangeManager.a(player, ((GrandExchangeOffer)object).f);
        }
        object = grandExchangeOffer2;
        if (((GrandExchangeOffer)object).g == 0) {
            object = grandExchangeOffer2;
            characterFileRecord.grandExchangeFinishMessagePending[((GrandExchangeOffer)object).f] = true;
            new GrandExchangePriceSample(grandExchangeOffer2);
        }
        if (player != null) {
            if (player.getOpenInterfaceId() == 19018) {
                GrandExchangeManager.c(player);
            }
            if (player.getOpenInterfaceId() == 18984) {
                object = grandExchangeOffer;
                if (player.dJ == ((GrandExchangeOffer)object).f) {
                    GrandExchangeManager.b(player);
                }
            }
        }
        CharacterFileManager.saveCharacterFileRecord(characterFileRecord);
        if (player != null) {
            object = player;
            CharacterFileManager.savePlayer((Player)object);
        }
    }

    private static void a(CharacterFileRecord characterFileRecord, GrandExchangeOffer grandExchangeOffer, Player player, GrandExchangeOffer grandExchangeOffer2) {
        int n;
        Object object = grandExchangeOffer2;
        int n2 = ((GrandExchangeOffer)object).g;
        object = grandExchangeOffer;
        if (((GrandExchangeOffer)object).g >= n2) {
            n = n2;
        } else {
            object = grandExchangeOffer;
            n = ((GrandExchangeOffer)object).g;
        }
        n2 = n;
        object = grandExchangeOffer2;
        int n3 = ((GrandExchangeOffer)object).c;
        object = grandExchangeOffer2;
        int n4 = ((GrandExchangeOffer)object).c * n2;
        object = grandExchangeOffer;
        int n5 = ((GrandExchangeOffer)object).g - n2;
        object = grandExchangeOffer;
        grandExchangeOffer.g = n5;
        object = grandExchangeOffer2;
        n5 = ((GrandExchangeOffer)object).g - n2;
        object = grandExchangeOffer2;
        grandExchangeOffer2.g = n5;
        if (characterFileRecord != null) {
            object = grandExchangeOffer;
            int n6 = ((GrandExchangeOffer)object).f;
            characterFileRecord.grandExchangeCompletedQuantities[n6] = characterFileRecord.grandExchangeCompletedQuantities[n6] + n2;
        }
        object = grandExchangeOffer2;
        int n7 = ((GrandExchangeOffer)object).f;
        player.dB[n7] = player.dB[n7] + n2;
        if (characterFileRecord != null) {
            object = grandExchangeOffer;
            int n8 = ((GrandExchangeOffer)object).f;
            characterFileRecord.grandExchangeTotalPrices[n8] = characterFileRecord.grandExchangeTotalPrices[n8] + n4;
        }
        object = grandExchangeOffer2;
        int n9 = ((GrandExchangeOffer)object).f;
        player.dC[n9] = player.dC[n9] + n4;
        object = grandExchangeOffer;
        if (n3 < ((GrandExchangeOffer)object).c) {
            object = grandExchangeOffer;
            n3 = ((GrandExchangeOffer)object).c - n3;
            n3 *= n2;
            if (characterFileRecord != null) {
                object = grandExchangeOffer;
                int n10 = ((GrandExchangeOffer)object).f;
                characterFileRecord.grandExchangeSecondaryCollectAmounts[n10] = characterFileRecord.grandExchangeSecondaryCollectAmounts[n10] + n3;
            }
        }
        if (characterFileRecord != null) {
            object = grandExchangeOffer;
            int n11 = ((GrandExchangeOffer)object).f;
            characterFileRecord.grandExchangePrimaryCollectAmounts[n11] = characterFileRecord.grandExchangePrimaryCollectAmounts[n11] + n2;
        }
        object = grandExchangeOffer2;
        int n12 = ((GrandExchangeOffer)object).f;
        player.dD[n12] = player.dD[n12] + n4;
        object = grandExchangeOffer2;
        if (((GrandExchangeOffer)object).g == 0) {
            object = grandExchangeOffer2;
            GrandExchangeManager.a(player, ((GrandExchangeOffer)object).f);
            new GrandExchangePriceSample(grandExchangeOffer2);
        }
        object = grandExchangeOffer;
        if (((GrandExchangeOffer)object).g == 0 && characterFileRecord != null) {
            object = grandExchangeOffer;
            characterFileRecord.grandExchangeFinishMessagePending[((GrandExchangeOffer)object).f] = true;
        }
        if (player.getOpenInterfaceId() == 19018) {
            GrandExchangeManager.c(player);
        }
        if (player.getOpenInterfaceId() == 18984) {
            object = grandExchangeOffer2;
            if (player.dJ == ((GrandExchangeOffer)object).f) {
                GrandExchangeManager.b(player);
            }
        }
        if (characterFileRecord != null) {
            CharacterFileManager.saveCharacterFileRecord(characterFileRecord);
        }
        object = player;
        CharacterFileManager.savePlayer((Player)object);
    }

    private static boolean a(CharacterFileRecord characterFileRecord, GrandExchangeOffer grandExchangeOffer) {
        if (characterFileRecord.grandExchangeCancelledFlags[grandExchangeOffer.f]) {
            return false;
        }
        GrandExchangeOffer grandExchangeOffer2 = grandExchangeOffer;
        if (characterFileRecord.grandExchangeSellOfferFlags[grandExchangeOffer.f] == grandExchangeOffer2.e) {
            grandExchangeOffer2 = grandExchangeOffer;
            if (characterFileRecord.grandExchangeItemIds[grandExchangeOffer.f] == grandExchangeOffer2.a) {
                grandExchangeOffer2 = grandExchangeOffer;
                if (characterFileRecord.grandExchangeQuantities[grandExchangeOffer.f] == grandExchangeOffer2.b) {
                    grandExchangeOffer2 = grandExchangeOffer;
                    if (characterFileRecord.grandExchangeUnitPrices[grandExchangeOffer.f] == grandExchangeOffer2.c) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean a(Player player, GrandExchangeOffer grandExchangeOffer) {
        if (player.dA[grandExchangeOffer.f]) {
            return false;
        }
        GrandExchangeOffer grandExchangeOffer2 = grandExchangeOffer;
        if (player.dw[grandExchangeOffer.f] == grandExchangeOffer2.e) {
            grandExchangeOffer2 = grandExchangeOffer;
            if (player.dx[grandExchangeOffer.f] == grandExchangeOffer2.a) {
                grandExchangeOffer2 = grandExchangeOffer;
                if (player.dy[grandExchangeOffer.f] == grandExchangeOffer2.b) {
                    grandExchangeOffer2 = grandExchangeOffer;
                    if (player.dz[grandExchangeOffer.f] == grandExchangeOffer2.c) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void a(int n, int n2, int n3) {
        ItemDefinition itemDefinition = ItemDefinition.forId(n);
        boolean bl = itemDefinition.isNote();
        int n4 = bl ? itemDefinition.getUnnotedId() : itemDefinition.getId();
        new GrandExchangePriceSample(n4, n2, n3);
    }
}

