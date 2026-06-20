/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.player;

import com.rs2.ServerSettings;
import com.rs2.model.grandexchange.GrandExchangeOffer;
import com.rs2.model.grandexchange.GrandExchangePriceSample;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;

public final class GrandExchangeManager {
    private static int a = 0;

    public static void a(Player player) {
        if (player.gameMode != 0) {
            player.packetSender.sendGameMessage("You are not playing on normal gamemode and cannot use grand exchange.");
            return;
        }
        Player player2 = player;
        player.dG = -1;
        player2.dH = 0;
        player2.dI = 0;
        player2.dJ = 0;
        GrandExchangeManager.c(player);
        player.packetSender.showInterface(19018);
    }

    public static void b(Player player) {
        ItemStack itemStack;
        ItemStack[] itemStackArray;
        String string;
        double d = player.dB[player.dJ];
        double d2 = player.dy[player.dJ];
        double d3 = 100.0 * (d / d2);
        int n = (int)d3;
        boolean bl = n == 100;
        boolean bl2 = player.dA[player.dJ];
        Player player2 = player;
        player2.packetSender.sendInterfaceText("for a total price of " + GameUtil.j(player.dC[player.dJ]) + " coins.", 19002);
        String string2 = bl || bl2 ? "" : "have";
        String string3 = string = bl || bl2 ? "" : " so far";
        if (player.dw[player.dJ]) {
            player2 = player;
            player2.packetSender.sendInterfaceText("You " + string2 + " sold a total of " + GameUtil.j(player.dB[player.dJ]) + string, 19001);
        } else {
            player2 = player;
            player2.packetSender.sendInterfaceText("You " + string2 + " bought a total of " + GameUtil.j(player.dB[player.dJ]) + string, 19001);
        }
        if (bl2) {
            n = 250;
        }
        if (bl2 || bl) {
            player2 = player;
            player2.packetSender.setInterfaceHiddenFlag(1, 19016);
        } else {
            player2 = player;
            player2.packetSender.setInterfaceHiddenFlag(0, 19016);
        }
        player2 = player;
        player2.packetSender.sendInterfaceProgress(19011, n);
        if (player.dw[player.dJ]) {
            itemStackArray = new ItemStack(995, player.dD[player.dJ]);
            itemStack = new ItemStack(player.dx[player.dJ], player.dE[player.dJ]);
        } else {
            itemStackArray = new ItemStack(player.dx[player.dJ], player.dD[player.dJ]);
            itemStack = new ItemStack(995, player.dE[player.dJ]);
        }
        if (itemStackArray.getAmount() <= 0) {
            itemStackArray = null;
        }
        if (itemStack.getAmount() <= 0) {
            itemStack = null;
        }
        itemStackArray = new ItemStack[]{itemStackArray, itemStack};
        player2 = player;
        player2.packetSender.sendItemContainer(19006, itemStackArray);
    }

    public static void a(Player player, int n, int n2, int n3) {
        ItemStack itemStack;
        int n4;
        int n5;
        double d = player.dB[player.dJ];
        double d2 = player.dy[player.dJ];
        double d3 = 100.0 * (d / d2);
        n3 = (int)d3;
        ItemStack itemStack2 = new ItemStack(n2);
        boolean bl = itemStack2.getDefinition().hasNote();
        int n6 = itemStack2.getDefinition().getNotedId();
        n3 = n3 == 100 ? 1 : 0;
        boolean bl2 = player.dA[player.dJ];
        if (n == 0) {
            n5 = player.dw[player.dJ] ? 995 : player.dx[player.dJ];
            n4 = player.dD[player.dJ];
        } else {
            n5 = player.dw[player.dJ] ? player.dx[player.dJ] : 995;
            n4 = player.dE[player.dJ];
        }
        if (n2 < 0 || n5 != itemStack2.getId() || !itemStack2.isValid()) {
            return;
        }
        if (n2 > 11883) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("This item is not supported yet.");
            return;
        }
        n2 = bl ? n6 : n2;
        ItemStack itemStack3 = new ItemStack(n2, n4);
        if (!player.getInventoryManager().e(itemStack3)) {
            return;
        }
        if (n == 0) {
            player.dD[player.dJ] = 0;
        } else {
            player.dE[player.dJ] = 0;
        }
        if (player.dw[player.dJ]) {
            itemStack2 = new ItemStack(995, player.dD[player.dJ]);
            itemStack = new ItemStack(player.dx[player.dJ], player.dE[player.dJ]);
        } else {
            itemStack2 = new ItemStack(player.dx[player.dJ], player.dD[player.dJ]);
            itemStack = new ItemStack(995, player.dE[player.dJ]);
        }
        if (itemStack2.getAmount() <= 0) {
            itemStack2 = null;
        }
        if (itemStack.getAmount() <= 0) {
            itemStack = null;
        }
        ItemStack[] itemStackArray = new ItemStack[]{itemStack2, itemStack};
        Player player3 = player;
        player3.packetSender.sendItemContainer(19006, itemStackArray);
        player.getInventoryManager().addItem(itemStack3);
        if (itemStack2 == null && itemStack == null && (n3 != 0 || bl2)) {
            GrandExchangeManager.d(player);
        }
    }

    public static void a(Player player, int n) {
        String string = new ItemStack(player.dx[n], 1).getDefinition().getName();
        String string2 = "buying";
        if (player.dw[n]) {
            string2 = "selling";
        }
        Player player2 = player;
        player2.packetSender.sendGameMessage("Grand Exchange: Finished " + string2 + " " + GameUtil.j(player.dy[n]) + " x " + string + ".");
        player.dF[n] = false;
    }

    private static void d(Player player) {
        player.dw[player.dJ] = true;
        player.dx[player.dJ] = -1;
        player.dy[player.dJ] = 0;
        player.dz[player.dJ] = 0;
        player.dA[player.dJ] = false;
        player.dB[player.dJ] = 0;
        player.dC[player.dJ] = 0;
        player.dD[player.dJ] = 0;
        player.dE[player.dJ] = 0;
        GrandExchangeManager.a(player);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean handleButtonClick(Player player, int n) {
        switch (n) {
            case 19024: 
            case 19027: 
            case 19030: 
            case 19033: 
            case 19036: 
            case 19039: {
                player.dJ = (n - 19024) / 3;
                Player player2 = player;
                player2.packetSender.showInterface(18890);
                return true;
            }
            case 19025: 
            case 19028: 
            case 19031: 
            case 19034: 
            case 19037: 
            case 19040: {
                player.dJ = (n - 19025) / 3;
                ItemStack[] itemStackArray = player.getInventoryManager().getContainer().getRawItems();
                Player player3 = player;
                player3.packetSender.sendItemContainer(19102, itemStackArray);
                player3 = player;
                player3.packetSender.showInterfaceWithInventory(18939, 19101);
                return true;
            }
            case 18907: 
            case 18956: 
            case 18990: {
                GrandExchangeManager.a(player);
                return true;
            }
            case 19042: 
            case 19051: 
            case 19060: 
            case 19069: 
            case 19078: 
            case 19087: {
                Object object;
                player.dJ = (n - 19042) / 9;
                double d = player.dB[player.dJ];
                double d2 = player.dy[player.dJ];
                double d3 = 100.0 * (d / d2);
                n = (int)d3;
                boolean bl = n == 100;
                boolean bl2 = player.dA[player.dJ];
                player.dG = player.dx[player.dJ];
                player.dH = player.dy[player.dJ];
                player.dI = player.dz[player.dJ];
                Player player4 = player;
                player4.packetSender.sendInterfaceText(GameUtil.j(GrandExchangeManager.a(player.dx[player.dJ])), 18997);
                player4 = player;
                player4.packetSender.sendInterfaceItemModel(19008, player.dx[player.dJ]);
                player4 = player;
                player4.packetSender.sendInterfaceText(GameUtil.j(player.dH), 18998);
                player4 = player;
                player4.packetSender.sendInterfaceText(String.valueOf(GameUtil.j(player.dI)) + " coins", 18999);
                player4 = player;
                player4.packetSender.sendInterfaceText(String.valueOf(GameUtil.j(player.dI * player.dH)) + " coins", 19000);
                player4 = player;
                player4.packetSender.sendInterfaceText("for a total price of " + GameUtil.j(player.dC[player.dJ]) + " coins.", 19002);
                Object object2 = bl || bl2 ? "" : "have";
                Object object3 = object = bl || bl2 ? "" : " so far";
                if (player.dw[player.dJ]) {
                    player4 = player;
                    player4.packetSender.setInterfaceHiddenFlag(0, 19012);
                    player4 = player;
                    player4.packetSender.setInterfaceHiddenFlag(1, 19014);
                    player4 = player;
                    player4.packetSender.sendInterfaceText("Sell offer", 18992);
                    player4 = player;
                    player4.packetSender.sendInterfaceText("You " + (String)object2 + " sold a total of " + GameUtil.j(player.dB[player.dJ]) + (String)object, 19001);
                } else {
                    player4 = player;
                    player4.packetSender.setInterfaceHiddenFlag(1, 19012);
                    player4 = player;
                    player4.packetSender.setInterfaceHiddenFlag(0, 19014);
                    player4 = player;
                    player4.packetSender.sendInterfaceText("Buy offer", 18992);
                    player4 = player;
                    player4.packetSender.sendInterfaceText("You " + (String)object2 + " bought a total of " + GameUtil.j(player.dB[player.dJ]) + (String)object, 19001);
                }
                if (bl2) {
                    n = 250;
                }
                if (bl2 || bl) {
                    player4 = player;
                    player4.packetSender.setInterfaceHiddenFlag(1, 19016);
                } else {
                    player4 = player;
                    player4.packetSender.setInterfaceHiddenFlag(0, 19016);
                }
                player4 = player;
                player4.packetSender.sendInterfaceProgress(19011, n);
                if (player.dw[player.dJ]) {
                    object2 = new ItemStack(995, player.dD[player.dJ]);
                    object = new ItemStack(player.dx[player.dJ], player.dE[player.dJ]);
                } else {
                    object2 = new ItemStack(player.dx[player.dJ], player.dD[player.dJ]);
                    object = new ItemStack(995, player.dE[player.dJ]);
                }
                if (((ItemStack)object2).getAmount() <= 0) {
                    object2 = null;
                }
                if (((ItemStack)object).getAmount() <= 0) {
                    object = null;
                }
                ItemStack[] itemStackArray = new ItemStack[]{object2, object};
                player4 = player;
                player4.packetSender.sendItemContainer(19006, itemStackArray);
                player4 = player;
                player4.packetSender.showInterface(18984);
                if (object2 != null || object != null || !bl && !bl2) return true;
                GrandExchangeManager.d(player);
                return true;
            }
            case 18908: 
            case 18957: {
                GrandExchangeManager.e(player, -1);
                return true;
            }
            case 18898: 
            case 18909: 
            case 18947: 
            case 18958: {
                GrandExchangeManager.e(player, 1);
                return true;
            }
            case 18899: 
            case 18948: {
                GrandExchangeManager.e(player, 10);
                return true;
            }
            case 18900: 
            case 18949: {
                GrandExchangeManager.e(player, 100);
                return true;
            }
            case 18901: {
                GrandExchangeManager.e(player, 1000);
                return true;
            }
            case 18950: {
                String string = "all";
                if (string.equals("all")) {
                    player.dH = player.getInventoryManager().getContainer().getItemAmount(player.dG);
                    ItemStack itemStack = new ItemStack(player.dG, 1);
                    boolean bl = itemStack.getDefinition().hasNote();
                    if (bl) {
                        int n2 = itemStack.getDefinition().getNotedId();
                        player.dH += player.getInventoryManager().getContainer().getItemAmount(n2);
                    }
                }
                GrandExchangeManager.e(player);
                return true;
            }
            case 18902: 
            case 18951: {
                Player player5 = player;
                player5.packetSender.sendEnterInputPrompt(18900);
                return true;
            }
            case 18910: 
            case 18959: {
                GrandExchangeManager.f(player, -1);
                return true;
            }
            case 18911: 
            case 18960: {
                GrandExchangeManager.f(player, 1);
                return true;
            }
            case 18903: 
            case 18952: {
                GrandExchangeManager.a(player, "-5%");
                return true;
            }
            case 18906: 
            case 18955: {
                GrandExchangeManager.a(player, "+5%");
                return true;
            }
            case 18904: 
            case 18953: {
                GrandExchangeManager.a(player, "guide");
                return true;
            }
            case 18905: 
            case 18954: {
                if (ServerSettings.instantGrandExchangeEnabled) {
                    Player player6 = player;
                    player6.packetSender.sendGameMessage("Price can't be modified with instant Grand Exchange trades.");
                    return true;
                }
                Player player7 = player;
                player7.packetSender.sendEnterInputPrompt(18901);
                return true;
            }
            case 18896: 
            case 18945: {
                if (player.dG < 0 || player.dH < 0 || player.dJ < 0 || player.dJ > 5 || player.dI * player.dH < 0) return true;
                int n3 = 1;
                if (player.getOpenInterfaceId() == 18890) {
                    n3 = 0;
                }
                if (n3 == 0) {
                    int n4 = player.dI * player.dH;
                    if (!player.getInventoryManager().containsItemAmount(995, n4)) {
                        Player player8 = player;
                        player8.packetSender.sendGameMessage("Not enough coins!");
                        return true;
                    }
                    player.getInventoryManager().removeItem(new ItemStack(995, n4));
                } else {
                    int n5 = player.dH;
                    ItemStack itemStack = new ItemStack(player.dG, 1);
                    int n6 = 0;
                    int n7 = -1;
                    boolean bl = itemStack.getDefinition().hasNote();
                    if (bl) {
                        n7 = itemStack.getDefinition().getNotedId();
                        n6 = player.getInventoryManager().getContainer().getItemAmount(n7);
                    }
                    if (n6 >= n5) {
                        player.getInventoryManager().removeItem(new ItemStack(n7, n5));
                    } else {
                        if (n6 > 0) {
                            player.getInventoryManager().removeItem(new ItemStack(n7, n6));
                        }
                        player.getInventoryManager().removeItem(new ItemStack(player.dG, n5 - n6));
                    }
                }
                player.dw[player.dJ] = n3;
                player.dx[player.dJ] = player.dG;
                player.dy[player.dJ] = player.dH;
                player.dz[player.dJ] = player.dI;
                if (!ServerSettings.instantGrandExchangeEnabled) {
                    new GrandExchangeOffer(player.getUsername(), player.dJ, n3 != 0, player.dG, player.dH, player.dH, player.dI);
                } else {
                    int n8 = player.dJ;
                    Player player9 = player;
                    int n9 = player9.dy[n8];
                    n3 = player9.dz[n8] * player9.dy[n8];
                    player9.dB[n8] = n9;
                    player9.dC[n8] = n3;
                    player9.dD[n8] = !player9.dw[n8] ? player9.dy[n8] : n3;
                }
                GrandExchangeManager.a(player);
                return true;
            }
            case 19017: {
                ItemStack[] itemStackArray;
                int n10 = 100 * (player.dB[player.dJ] / player.dy[player.dJ]);
                boolean bl = n10 == 100;
                boolean bl3 = player.dA[player.dJ];
                if (bl3 || bl) return true;
                GrandExchangeOffer.a(player, player.dJ);
                player.dA[player.dJ] = true;
                Player player10 = player;
                player10.packetSender.setInterfaceHiddenFlag(1, 19016);
                if (player.dw[player.dJ]) {
                    ItemStack itemStack = new ItemStack(995, player.dD[player.dJ]);
                    ItemStack itemStack2 = new ItemStack(player.dx[player.dJ], player.dy[player.dJ] - player.dB[player.dJ]);
                    player.dE[player.dJ] = itemStack2.getAmount();
                    if (itemStack.getAmount() <= 0) {
                        itemStack = null;
                    }
                    if (itemStack2.getAmount() <= 0) {
                        itemStack2 = null;
                    }
                    itemStackArray = new ItemStack[]{itemStack, itemStack2};
                } else {
                    ItemStack itemStack = new ItemStack(player.dx[player.dJ], player.dD[player.dJ]);
                    ItemStack itemStack3 = new ItemStack(995, (player.dy[player.dJ] - player.dB[player.dJ]) * player.dz[player.dJ]);
                    int n11 = player.dJ;
                    player.dE[n11] = player.dE[n11] + itemStack3.getAmount();
                    if (itemStack.getAmount() <= 0) {
                        itemStack = null;
                    }
                    if (itemStack3.getAmount() <= 0) {
                        itemStack3 = null;
                    }
                    itemStackArray = new ItemStack[]{itemStack, itemStack3};
                }
                player10 = player;
                player10.packetSender.sendInterfaceProgress(19011, 250);
                player10 = player;
                player10.packetSender.sendItemContainer(19006, itemStackArray);
                GrandExchangeManager.b(player);
                return true;
            }
        }
        return false;
    }

    public static void c(Player player) {
        int n = 0;
        while (n < 6) {
            Player player2;
            if (player.dy[n] == 0) {
                player2 = player;
                player2.packetSender.sendInterfaceText("Empty", n + 19095);
                player2 = player;
                player2.packetSender.setInterfaceHiddenFlag(0, 19023 + n * 3);
                player2 = player;
                player2.packetSender.setInterfaceHiddenFlag(1, 19041 + n * 9);
                player2 = player;
                player2.packetSender.sendSingleItemContainer(19044 + n * 9, -1, 0);
                player2 = player;
                player2.packetSender.sendInterfaceText("Item name", 19045 + n * 9);
                player2 = player;
                player2.packetSender.sendInterfaceText("0 coins", 19046 + n * 9);
                player2 = player;
                player2.packetSender.sendInterfaceProgress(19049 + n * 9, 0);
            } else {
                player2 = player;
                player2.packetSender.sendInterfaceText(player.dw[n] ? "Sell" : "Buy", n + 19095);
                player2 = player;
                player2.packetSender.sendSingleItemContainer(19044 + n * 9, player.dx[n], player.dy[n]);
                player2 = player;
                player2.packetSender.sendInterfaceText(new ItemStack(player.dx[n], 1).getDefinition().getName(), 19045 + n * 9);
                player2 = player;
                player2.packetSender.sendInterfaceText(String.valueOf(GameUtil.j(player.dz[n])) + " coins", 19046 + n * 9);
                double d = player.dB[n];
                double d2 = player.dy[n];
                double d3 = 100.0 * (d / d2);
                int n2 = (int)d3;
                boolean bl = player.dA[n];
                if (bl) {
                    n2 = 250;
                }
                player2 = player;
                player2.packetSender.sendInterfaceProgress(19049 + n * 9, n2);
                player2 = player;
                player2.packetSender.setInterfaceHiddenFlag(1, 19023 + n * 3);
                player2 = player;
                player2.packetSender.setInterfaceHiddenFlag(0, 19041 + n * 9);
            }
            ++n;
        }
    }

    public static void a() {
        int n = GameUtil.i(11);
        if (GameUtil.h(2) == 0) {
            n = -n;
        }
        a = n;
    }

    public static int a(int n) {
        double d;
        double d2;
        if (n == 995) {
            return 1;
        }
        ItemDefinition itemDefinition = ItemDefinition.forId(n);
        int n2 = itemDefinition.isNote();
        n2 = n2 != 0 ? itemDefinition.getUnnotedId() : itemDefinition.getId();
        if ((n2 = GrandExchangePriceSample.a(n2)) == -1) {
            n2 = itemDefinition.n();
        }
        if (ServerSettings.instantGrandExchangeEnabled && ServerSettings.instantGrandExchangePriceFluctuationEnabled && (n2 = (int)(d2 = (double)n2 + (d = (double)(n2 / 100 * a)))) <= 0) {
            n2 = 1;
        }
        return n2;
    }

    public static void c(Player player, int n) {
        player.dH = n;
        if (player.dH <= 0) {
            player.dH = 1;
        }
        if (player.getOpenInterfaceId() == 18939) {
            n = player.getInventoryManager().getContainer().getItemAmount(player.dG);
            ItemStack itemStack = new ItemStack(player.dG, 1);
            boolean bl = itemStack.getDefinition().hasNote();
            if (bl) {
                int n2 = itemStack.getDefinition().getNotedId();
                n += player.getInventoryManager().getContainer().getItemAmount(n2);
            }
            if (player.dH >= n) {
                player.dH = n;
            }
        }
        GrandExchangeManager.e(player);
    }

    public static void d(Player player, int n) {
        player.dI = n;
        if (player.dI < 0) {
            player.dI = 0;
        }
        GrandExchangeManager.e(player);
    }

    private static void e(Player player, int n) {
        player.dH += n;
        if (player.dH <= 0) {
            player.dH = 1;
        }
        if (player.getOpenInterfaceId() == 18939) {
            n = player.getInventoryManager().getContainer().getItemAmount(player.dG);
            ItemStack itemStack = new ItemStack(player.dG, 1);
            boolean bl = itemStack.getDefinition().hasNote();
            if (bl) {
                int n2 = itemStack.getDefinition().getNotedId();
                n += player.getInventoryManager().getContainer().getItemAmount(n2);
            }
            if (player.dH >= n) {
                player.dH = n;
            }
        }
        GrandExchangeManager.e(player);
    }

    private static void f(Player player, int n) {
        if (ServerSettings.instantGrandExchangeEnabled) {
            player.packetSender.sendGameMessage("Price can't be modified with instant Grand Exchange trades.");
            return;
        }
        player.dI += n;
        if (player.dI < 0) {
            player.dI = 0;
        }
        GrandExchangeManager.e(player);
    }

    private static void a(Player player, String string) {
        double d;
        if (ServerSettings.instantGrandExchangeEnabled) {
            player.packetSender.sendGameMessage("Price can't be modified with instant Grand Exchange trades.");
            return;
        }
        if (string.equals("guide")) {
            player.dI = GrandExchangeManager.a(player.dG);
        }
        if (string.equals("+5%")) {
            int n;
            double d2 = player.dI;
            d = d2 * 1.05;
            player.dI = n = (int)d;
        }
        if (string.equals("-5%")) {
            int n;
            double d3 = player.dI;
            d = d3 * 0.95;
            player.dI = n = (int)d;
        }
        if (player.dI < 0) {
            player.dI = 0;
        }
        GrandExchangeManager.e(player);
    }

    private static void e(Player player) {
        int n = 18920;
        if (player.getOpenInterfaceId() == 18939) {
            n = 18969;
        }
        Player player2 = player;
        player2.packetSender.sendInterfaceText(GameUtil.j(player.dH), n);
        player2 = player;
        player2.packetSender.sendInterfaceText(String.valueOf(GameUtil.j(player.dI)) + " coins", n + 1);
        player2 = player;
        player2.packetSender.sendInterfaceText(String.valueOf(GameUtil.j(player.dI * player.dH)) + " coins", n + 2);
    }

    public static void b(Player player, int n, int n2, int n3) {
        Object object = player.getInventoryManager().getContainer().getItemAt(n);
        if (object == null || ((ItemStack)object).getId() != n2 || !((ItemStack)object).isValid()) {
            return;
        }
        if (((ItemStack)object).getDefinition().z()) {
            return;
        }
        player.getInventoryManager().getContainer().getItemAmount(n2);
        n2 = ((ItemStack)object).getDefinition().isNote() ? 1 : 0;
        if (((ItemStack)object).getDefinition().getId() > 11883) {
            object = player;
            ((Player)object).packetSender.sendGameMessage("This item is not supported yet.");
            return;
        }
        if ((n2 = n2 != 0 ? ((ItemStack)object).getDefinition().getUnnotedId() : ((ItemStack)object).getDefinition().getId()) == 995) {
            return;
        }
        player.dG = n2;
        player.dH = ((ItemStack)object).getAmount();
        player.dI = GrandExchangeManager.a(n2);
        object = player;
        ((Player)object).packetSender.sendInterfaceText(GameUtil.j(player.dI), 18968);
        object = player;
        ((Player)object).packetSender.sendInterfaceItemModel(18983, n2);
        GrandExchangeManager.e(player);
    }
}

