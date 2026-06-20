/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.shop;

import com.rs2.ServerSettings;
import com.rs2.model.World;
import com.rs2.model.item.ItemContainer;
import com.rs2.model.item.ItemContainerType;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemService;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.InventoryManager;
import com.rs2.model.player.Player;
import com.rs2.model.shop.ShopCurrency;
import com.rs2.model.shop.ShopDefinition;
import com.rs2.model.shop.ShopRestockTask;
import com.rs2.model.task.TickTask;
import com.rs2.net.packet.PacketSender;
import com.rs2.util.ByteArrayReader;
import com.rs2.util.FileUtil;
import com.rs2.util.GameUtil;
import java.util.ArrayList;
import java.util.List;

public final class ShopManager {
    private static List a = new ArrayList(40);
    private static /* synthetic */ int[] b;

    public static void a(int n) {
        ItemStack[] itemStackArray = (ItemStack[])a.get(n);
        Player[] playerArray = World.f();
        int n2 = playerArray.length;
        int n3 = 0;
        while (n3 < n2) {
            Player player = playerArray[n3];
            if (player != null && player.bS() == n) {
                ItemStack[] itemStackArray2 = itemStackArray;
                itemStackArray2 = itemStackArray2.k().getRawItems();
                player.packetSender.sendItemContainer(3900, itemStackArray2);
            }
            ++n3;
        }
    }

    public static void a(Player player, int n) {
        Player player2;
        if (n >= a.toArray().length) {
            return;
        }
        ItemStack[] itemStackArray = (ItemStack[])a.get(n);
        if (itemStackArray.e()) {
            if (player.isMember()) {
                if (ServerSettings.freeToPlayWorld) {
                    player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                    return;
                }
            } else {
                player.packetSender.sendGameMessage("You need a members account to access members content.");
                return;
            }
        }
        String string = itemStackArray.d();
        if (itemStackArray.i() == ShopCurrency.b) {
            string = "<img=2>" + string + "<img=2>";
            player2 = player;
            player2.packetSender.sendGameMessage("You have " + player.dm() + " Donator points.");
        }
        itemStackArray = itemStackArray.k().getRawItems();
        player.getInventoryManager().sendToInterface(3823);
        player2 = player;
        player2.packetSender.sendItemContainer(3900, itemStackArray);
        player2 = player;
        player2.packetSender.sendInterfaceText(string, 3901);
        player2 = player;
        player2.packetSender.showInterfaceWithInventory(3824, 3822);
        player.K(n);
        player.getAttributes().put("isShopping", Boolean.TRUE);
    }

    private static boolean b(int n) {
        return n >= 7992 && n <= 7997 || n >= 8001 && n <= 8012 || n >= 8016 && n <= 8065;
    }

    private static boolean c(int n) {
        return n == 7958 || n == 7972 || n == 7974 || n == 7976 || n == 7982 || n == 7984 || n == 7986 || n == 8068 || n == 8076 || n == 8078;
    }

    public static void a(Player player, ItemStack itemStack) {
        if (itemStack == null) {
            return;
        }
        ShopDefinition shopDefinition = (ShopDefinition)a.get(player.bS());
        if (shopDefinition == null) {
            return;
        }
        int n = shopDefinition.k().indexOfItem(itemStack.getId());
        if (n == -1) {
            return;
        }
        ShopManager.a(player, n, itemStack.getId(), itemStack.getAmount());
    }

    public static void a(Player player, int n, int n2, int n3) {
        int n4;
        int n5;
        int n6;
        Object object;
        int n7;
        ShopDefinition shopDefinition = (ShopDefinition)a.get(player.bS());
        ItemContainer itemContainer = player.getInventoryManager().getContainer();
        ItemStack itemStack = shopDefinition.k().getItemAt(n);
        if (shopDefinition.i() == ShopCurrency.a) {
            n7 = shopDefinition.h();
        } else {
            ShopDefinition shopDefinition2 = shopDefinition;
            object = player;
            switch (ShopManager.c()[shopDefinition2.i().ordinal()]) {
                case 2: {
                    n7 = ((Player)object).dm();
                    break;
                }
                default: {
                    n7 = n6 = -1;
                }
            }
        }
        if (n3 <= 0 || n2 < 0 || itemStack == null || !itemStack.isValid()) {
            if (player.botEnabled) {
                player.deferredBotTask = null;
                player.botTaskReturnToBankRequested = true;
                player.currentBotTask.startWalkToBank(player);
            }
            return;
        }
        if (n2 != itemStack.getId()) {
            return;
        }
        boolean bl = false;
        if (ShopManager.c(itemStack.getId())) {
            bl = true;
        }
        if (shopDefinition.i() == ShopCurrency.a) {
            ItemService.getInstance();
            n5 = ItemService.getPrice(n2, "buyfromshop", n6);
        } else {
            ItemService.getInstance();
            n5 = ItemService.getPrice(n2, "donator", n6);
            if (n6 < n5 * n3) {
                object = player;
                ((Player)object).packetSender.sendGameMessage("You do not have enough donator points to buy this item.");
                return;
            }
            if (bl) {
                ItemDefinition itemDefinition = ItemDefinition.forId(itemStack.getId());
                n4 = player.getInventoryManager().getItemAmount(itemDefinition.b);
                if (n4 < n3) {
                    object = player;
                    PacketSender packetSender = ((Player)object).packetSender;
                    StringBuilder stringBuilder = new StringBuilder("You do not have enough ");
                    ItemService.getInstance();
                    packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(itemDefinition.b)).append("(s) to buy this item.").toString());
                    return;
                }
            }
        }
        if (shopDefinition.k().getItemAt(n).getAmount() < n3) {
            n3 = shopDefinition.k().getItemAt(n).getAmount();
        }
        int n8 = itemContainer.getFreeSlots();
        int n9 = n5;
        int n10 = n6;
        object = player.getInventoryManager();
        if (((InventoryManager)object).containsItem(n10) && ((InventoryManager)object).getContainer().getItemAmount(n10) == n9) {
            ++n8;
        }
        int n11 = n4 = !itemStack.getDefinition().isStackable() ? n3 : 1;
        if (ShopManager.b(itemStack.getId())) {
            n4 = 2 * n3;
        }
        if (!(itemStack.getDefinition().isStackable() && player.getInventoryManager().containsItem(n2) || n8 >= n4)) {
            n3 = n8;
            object = player;
            ((Player)object).packetSender.sendGameMessage("Not enough space in your inventory.");
            if (player.botEnabled) {
                player.currentBotTask.startWalkToBank(player);
            }
            if (ShopManager.b(itemStack.getId())) {
                return;
            }
        }
        if (shopDefinition.k().getItemAt(n).getAmount() < n3) {
            n3 = shopDefinition.k().getItemAt(n).getAmount();
        }
        if (shopDefinition.g() && shopDefinition.k().getItemAt(n).getAmount() == 0) {
            object = player;
            ((Player)object).packetSender.sendGameMessage("This item is out of stock.");
            return;
        }
        n = 0;
        if (shopDefinition.j().findFlatItem(n2) != null) {
            n = shopDefinition.j().findFlatItem(n2).getAmount();
        }
        n8 = shopDefinition.k().getItemAmount(n2);
        n4 = 0;
        if (player.gameMode != 0 && n8 > n) {
            object = player;
            ((Player)object).packetSender.sendGameMessage("You are not playing on normal gamemode and cannot buy overstocked items.");
            return;
        }
        n8 = 0;
        while (n8 < n3) {
            if (shopDefinition.i() == ShopCurrency.a) {
                int n12 = itemContainer.getItemAmount(n6);
                n = ShopManager.b(shopDefinition, n2, n8);
                if (n > n12) {
                    Player player2 = player;
                    PacketSender packetSender = player2.packetSender;
                    StringBuilder stringBuilder = new StringBuilder("You do not have enough ");
                    ItemService.getInstance();
                    packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(n6)).append(" to buy this item.").toString());
                    n3 = n4;
                    if (!player.botEnabled) break;
                    player.currentBotTask.startWalkToBank(player);
                    break;
                }
                player.getInventoryManager().removeItem(new ItemStack(n6, n));
                n4 = n8 + 1;
                if (n6 == 995) {
                    player.ea += n5;
                }
            } else {
                if (bl) {
                    ItemDefinition itemDefinition = ItemDefinition.forId(itemStack.getId());
                    player.getInventoryManager().removeItem(new ItemStack(itemDefinition.b, 1));
                }
                n9 = n5;
                ShopDefinition shopDefinition3 = shopDefinition;
                Player player3 = player;
                switch (ShopManager.c()[shopDefinition3.i().ordinal()]) {
                    case 2: {
                        player3.S(n9);
                    }
                }
                if (shopDefinition.i() == ShopCurrency.b) {
                    player3 = player;
                    player3.packetSender.sendGameMessage("You have " + player.dm() + " Donator points remaining.");
                }
            }
            ++n8;
        }
        n8 = ShopManager.a(shopDefinition, itemStack.getId());
        if (shopDefinition.j().containsItem(itemStack.getId())) {
            shopDefinition.k().removeKeepingPlaceholder(new ItemStack(itemStack.getId(), n3));
        } else {
            shopDefinition.k().remove(new ItemStack(itemStack.getId(), n3));
        }
        ShopManager.d(shopDefinition, itemStack.getId(), n8);
        if (!ShopManager.b(n2)) {
            ItemStack itemStack2 = new ItemStack(n2, n3);
            player.getInventoryManager().addItem(itemStack2);
            if (player.botEnabled) {
                player.botTaskReturnToBankRequested = true;
                player.currentBotTask.startWalkToBank(player);
            }
            if (shopDefinition.i() == ShopCurrency.b) {
                GameUtil.a(itemStack2);
            }
        } else {
            player.getInventoryManager().addItem(new ItemStack(player.aZ() >= 2 && n2 != 8058 ? n2 + 1 : n2, n3));
            player.getInventoryManager().addItem(new ItemStack(n2 != 8058 ? n2 + 2 : n2 + 1, n3));
        }
        player.getInventoryManager().sendToInterface(3823);
        ShopManager.a(player.bS());
    }

    private static int b(ShopDefinition shopDefinition, int n, int n2) {
        int n3 = new ItemStack(n).getDefinition().getShopValue();
        int n4 = 0;
        if (shopDefinition.j().findFlatItem(n) != null) {
            n4 = shopDefinition.j().findFlatItem(n).getAmount();
        }
        n = shopDefinition.k().getItemAmount(n) - n2;
        n -= n4;
        if (!ServerSettings.dynamicShopPricesEnabled) {
            n = 0;
        }
        n2 = n3 * 30;
        int n5 = (int)((double)n3 * ((double)shopDefinition.a() - shopDefinition.c() * (double)n));
        n5 /= 100;
        n5 = Math.max(n5, n2 /= 100);
        return n5;
    }

    private static int c(ShopDefinition shopDefinition, int n, int n2) {
        int n3 = new ItemStack(n).getDefinition().getShopValue();
        int n4 = 0;
        if (shopDefinition.j().findFlatItem(n) != null) {
            n4 = shopDefinition.j().findFlatItem(n).getAmount();
        }
        n = shopDefinition.k().getItemAmount(n) + n2;
        n -= n4;
        if (!ServerSettings.dynamicShopPricesEnabled) {
            n = 0;
        }
        int n5 = (int)(((double)shopDefinition.b() - shopDefinition.c() * (double)Math.min(n, 10)) * (double)n3);
        n = n3 / 10;
        n5 = (n5 /= 100) < n ? n : n5;
        return n5;
    }

    public static void b(Player player, int n, int n2, int n3) {
        ShopDefinition shopDefinition = (ShopDefinition)a.get(player.bS());
        ItemContainer itemContainer = player.getInventoryManager().getContainer();
        ItemStack itemStack = itemContainer.getItemAt(n);
        int n4 = shopDefinition.h();
        if (itemStack == null) {
            return;
        }
        int n5 = n2;
        if (itemStack.getDefinition().isNote()) {
            n5 = itemStack.getDefinition().getUnnotedId();
        }
        int n6 = shopDefinition.k().getItemAmount(n5);
        if (!ServerSettings.adminInteractionsAllowed && player.getPlayerRights() >= 2) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("This action is not allowed.");
            return;
        }
        if (itemStack.getId() != n2 || !itemStack.isValid()) {
            return;
        }
        if (shopDefinition.i() != ShopCurrency.a) {
            Player player3 = player;
            player3.packetSender.sendGameMessage("This shop can't buy anything.");
            return;
        }
        if (shopDefinition.k().getFreeSlots() <= 0 && n6 <= 0) {
            Player player4 = player;
            player4.packetSender.sendGameMessage("The shop is currently full!");
            return;
        }
        if (n2 == 995) {
            Player player5 = player;
            player5.packetSender.sendGameMessage("You cannot sell coins to the shop.");
            return;
        }
        if (!shopDefinition.g() && !shopDefinition.k().containsItem(n5) || itemStack.getDefinition().z()) {
            Player player6 = player;
            player6.packetSender.sendGameMessage("You cannot sell this item in this shop.");
            return;
        }
        int n7 = itemContainer.getItemAmount(n2);
        if (n3 <= 0 || n2 < 0) {
            return;
        }
        if (n2 > 11883) {
            Player player7 = player;
            player7.packetSender.sendGameMessage("This item is not supported yet.");
            return;
        }
        if (!itemContainer.containsItem(n2)) {
            return;
        }
        if (n3 >= n7) {
            n3 = n7;
        }
        n7 = 0;
        if (shopDefinition.j().findFlatItem(n5) != null) {
            n7 = shopDefinition.j().findFlatItem(n5).getAmount();
        }
        if (player.gameMode != 0 && n6 < n7) {
            Player player8 = player;
            player8.packetSender.sendGameMessage("You are not playing on normal gamemode and cannot sell understocked items.");
            return;
        }
        player.getInventoryManager().removeItem(new ItemStack(n2, n3));
        double d = 0.0;
        n7 = 0;
        while (n7 < n3) {
            d += (double)ShopManager.c(shopDefinition, n2, n7);
            ++n7;
        }
        if (!player.botEnabled || player.currentBotTask != null) {
            player.getInventoryManager().addItem(new ItemStack(n4, (int)d));
        }
        if (n4 == 995) {
            player.dZ = (int)((double)player.dZ + d);
        }
        n7 = ShopManager.a(shopDefinition, n5);
        if (shopDefinition.g() && n6 <= 0) {
            ItemStack itemStack2 = new ItemStack(n5, n3);
            ItemContainer itemContainer2 = shopDefinition.k();
            itemContainer2.add(itemStack2, -1);
        } else {
            shopDefinition.k().setItem(shopDefinition.k().indexOfItem(n5), new ItemStack(n5, n6 + n3));
        }
        ShopManager.d(shopDefinition, n5, n7);
        if (player.botShopSellItemIds.contains(n5)) {
            n7 = player.botShopSellItemIds.indexOf(n5);
            player.botShopSellItemIds.remove(n7);
        }
        if (player.botEnabled && player.currentBotTask != null && player.botShopSellItemIds.size() == 0) {
            player.botTaskReturnToBankRequested = true;
            player.currentBotTask.startWalkToBank(player);
        }
        player.getInventoryManager().sendToInterface(3823);
        ShopManager.a(player.bS());
    }

    public static void b(Player player, ItemStack itemStack) {
        if (itemStack == null) {
            return;
        }
        ShopManager.b(player, player.getInventoryManager().getContainer().indexOfItem(itemStack.getId()), itemStack.getId(), itemStack.getAmount());
    }

    public static void b(Player player, int n) {
        ShopDefinition shopDefinition = (ShopDefinition)a.get(player.bS());
        if (shopDefinition.i() == ShopCurrency.a) {
            int n2 = n;
            ItemService.getInstance();
            String string = ItemService.getItemName(shopDefinition.h());
            int n3 = ShopManager.b(shopDefinition, n2, 0);
            PacketSender packetSender = player.packetSender;
            StringBuilder stringBuilder = new StringBuilder();
            ItemService.getInstance();
            packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(n)).append(": currently costs ").append(GameUtil.b((double)n3)).append(" ").append(string).append(".").toString());
            return;
        }
        ItemService.getInstance();
        int n4 = ItemService.getPrice(n, "donator", shopDefinition.h());
        boolean bl = false;
        if (ShopManager.c(n)) {
            bl = true;
        }
        if (!bl) {
            PacketSender packetSender = player.packetSender;
            StringBuilder stringBuilder = new StringBuilder();
            ItemService.getInstance();
            packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(n)).append(": currently costs ").append(n4).append(" ").append(ShopManager.a(shopDefinition)).append(".").toString());
            return;
        }
        ItemDefinition itemDefinition = ItemDefinition.forId(n);
        itemDefinition = ItemDefinition.forId(itemDefinition.b);
        PacketSender packetSender = player.packetSender;
        StringBuilder stringBuilder = new StringBuilder();
        ItemService.getInstance();
        packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(n)).append(": currently costs ").append(n4).append(" ").append(ShopManager.a(shopDefinition)).append(" + ").append(itemDefinition.getName()).append(".").toString());
    }

    public static void c(Player player, int n) {
        ShopDefinition shopDefinition = (ShopDefinition)a.get(player.bS());
        Object object = ItemDefinition.forId(n);
        shopDefinition.h();
        int n2 = n;
        if (((ItemDefinition)object).isNote()) {
            n2 = ((ItemDefinition)object).getUnnotedId();
        }
        if (shopDefinition.i() != ShopCurrency.a) {
            player.packetSender.sendGameMessage("This shop can't buy anything.");
            return;
        }
        if (n == 995) {
            player.packetSender.sendGameMessage("You cannot sell coins to the shop.");
            return;
        }
        if (!shopDefinition.g() && !shopDefinition.k().containsItem(n2) || ((ItemDefinition)object).z()) {
            player.packetSender.sendGameMessage("You cannot sell this item in this shop.");
            return;
        }
        if (shopDefinition.i() == ShopCurrency.a || shopDefinition.g()) {
            object = shopDefinition;
            int n3 = ShopManager.c((ShopDefinition)object, n2, 0);
            PacketSender packetSender = player.packetSender;
            StringBuilder stringBuilder = new StringBuilder();
            ItemService.getInstance();
            packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(n)).append(": shop will buy for ").append(GameUtil.b((double)n3)).append(" ").append(ShopManager.a(shopDefinition)).append(".").toString());
            return;
        }
        player.packetSender.sendGameMessage("You cannot sell this item to this shop.");
    }

    private static String a(ShopDefinition shopDefinition) {
        switch (ShopManager.c()[shopDefinition.i().ordinal()]) {
            case 2: {
                return "Donator Points";
            }
        }
        ItemService.getInstance();
        return ItemService.getItemName(shopDefinition.h());
    }

    public static void a() {
        try {
            byte[] byArray = FileUtil.readBytes("./data/content/Shops.dat");
            ByteArrayReader byteArrayReader = new ByteArrayReader(byArray);
            int n = byteArrayReader.readUnsignedShort();
            int n2 = 0;
            while (n2 < n) {
                ShopDefinition shopDefinition = new ShopDefinition();
                String string = byteArrayReader.readString();
                boolean bl = byteArrayReader.readUnsignedByte() == 1;
                int n3 = byteArrayReader.readUnsignedByte();
                int n4 = byteArrayReader.readUnsignedByte();
                int n5 = byteArrayReader.readUnsignedByte();
                int n6 = byteArrayReader.readUnsignedByte();
                int n7 = byteArrayReader.readUnsignedByte();
                ItemContainer itemContainer = new ItemContainer(ItemContainerType.b, 40);
                ItemContainer itemContainer2 = new ItemContainer(ItemContainerType.b, 40);
                ShopDefinition.a(shopDefinition, new int[40]);
                ShopDefinition.a(shopDefinition, new TickTask[40]);
                int n8 = 0;
                while (n8 < 40) {
                    ShopDefinition.a((ShopDefinition)shopDefinition)[n8] = 100;
                    ++n8;
                }
                n8 = 0;
                while (n8 < n7) {
                    int n9 = byteArrayReader.readUnsignedShort();
                    int n10 = byteArrayReader.readUnsignedShort();
                    int n11 = byteArrayReader.readUnsignedShort();
                    n10 = (int)((double)n10 * ServerSettings.shopItemMultiplier);
                    if (!(ServerSettings.freeToPlayWorld && n2 != 169 && new ItemStack(n9).getDefinition().isMembersOnly() || !ItemDefinition.isDefined(n9))) {
                        ShopDefinition.a((ShopDefinition)shopDefinition)[n8] = n11;
                        ItemStack itemStack = new ItemStack(n9, n10);
                        ItemContainer itemContainer3 = itemContainer;
                        itemContainer3.add(itemStack, -1);
                        itemStack = new ItemStack(n9, n10);
                        itemContainer3 = itemContainer2;
                        itemContainer3.add(itemStack, -1);
                    }
                    ++n8;
                }
                shopDefinition.b(n3 == 0);
                shopDefinition.a(itemContainer);
                shopDefinition.b(itemContainer2);
                n8 = byteArrayReader.readUnsignedShort();
                shopDefinition.a(string);
                shopDefinition.a(bl);
                shopDefinition.d(n8);
                shopDefinition.a(ShopCurrency.values()[n3 == 2 ? 1 : 0]);
                shopDefinition.a(n4);
                shopDefinition.b(n5);
                shopDefinition.c(n6);
                ShopDefinition.a(shopDefinition, n2);
                a.add(shopDefinition);
                ++n2;
            }
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    private static void d(ShopDefinition shopDefinition, int n, int n2) {
        int n3;
        TickTask tickTask = null;
        if (n2 != -1) {
            tickTask = ShopDefinition.b(shopDefinition)[n2];
        }
        if (shopDefinition.j().containsItem(n)) {
            n3 = shopDefinition.j().findFlatItem(n).getAmount();
            int n4 = shopDefinition.k().findFlatItem(n).getAmount();
            if (n4 < n3) {
                ShopManager.b(shopDefinition, n);
                return;
            }
            if (n4 > n3) {
                ShopManager.b(shopDefinition, n);
                return;
            }
        } else if (shopDefinition.k().containsItem(n)) {
            ShopManager.b(shopDefinition, n);
            return;
        }
        if (tickTask != null) {
            tickTask.stop();
            n3 = ShopDefinition.a(shopDefinition)[n2];
            tickTask.setIntervalTicks(n3);
            tickTask.setRemainingTicks(n3);
        }
    }

    public static int a(ShopDefinition shopDefinition, int n) {
        if (shopDefinition.k().containsItem(n)) {
            return shopDefinition.k().indexOfItem(n);
        }
        return -1;
    }

    private static void b(ShopDefinition shopDefinition, int n) {
        int n2 = ShopManager.a(shopDefinition, n);
        int n3 = ShopDefinition.a(shopDefinition)[n2];
        double d = (double)n3 * ServerSettings.shopRestockTimeMultiplier;
        n3 = (int)d;
        if (ShopDefinition.b(shopDefinition)[n2] != null && ShopDefinition.b(shopDefinition)[n2].isActive()) {
            return;
        }
        ShopDefinition.b((ShopDefinition)shopDefinition)[n2] = new ShopRestockTask(n3, shopDefinition, n, n2);
        World.getTaskScheduler().schedule(ShopDefinition.b(shopDefinition)[n2]);
    }

    public static boolean a(ShopDefinition shopDefinition, int n, int n2) {
        if (shopDefinition.j().containsItem(n)) {
            int n3 = shopDefinition.j().findFlatItem(n).getAmount();
            int n4 = shopDefinition.k().findFlatItem(n).getAmount();
            if (n4 < n3) {
                return true;
            }
            if (n4 > n3) {
                return true;
            }
        } else {
            int n5 = ShopManager.a(shopDefinition, n);
            if (n5 != n2) {
                return false;
            }
            if (shopDefinition.k().containsItem(n)) {
                return true;
            }
        }
        return false;
    }

    public static List b() {
        return a;
    }

    private static /* synthetic */ int[] c() {
        if (b != null) {
            return b;
        }
        int[] nArray = new int[ShopCurrency.values().length];
        try {
            nArray[ShopCurrency.b.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            nArray[ShopCurrency.a.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        b = nArray;
        return nArray;
    }
}

