/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.partyroom;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.gameplay.partyroom.PartyRoomBalloonReward;
import com.rs2.model.gameplay.partyroom.PartyRoomBalloonSpawnTask;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemContainer;
import com.rs2.model.item.ItemContainerType;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.util.CountingDataOutputStream;
import com.rs2.util.GameUtil;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public final class PartyRoomManager {
    public static boolean a = false;
    public static String b = null;
    private static String h = null;
    private static int i = 200;
    public static ItemContainer c = new ItemContainer(ItemContainerType.a, i);
    public static ArrayList d = new ArrayList();
    public static ArrayList e = new ArrayList();
    public static int f = 0;
    public static TickTask g;

    public static boolean a() {
        if (a) {
            return true;
        }
        return d.size() > 0;
    }

    public static boolean a(Player object) {
        ArrayList<ItemStack> arrayList;
        int n;
        if (PartyRoomManager.a()) {
            return false;
        }
        if (((Player)object).getInventoryManager().containsItemAmount(995, 1000)) {
            ((Player)object).getInventoryManager().removeItem(new ItemStack(995, 1000));
            a = true;
            e.clear();
            object = new ArrayList();
            int n2 = 2730;
            while (n2 < 2745) {
                n = 3462;
                while (n < 3476) {
                    if (n != 3468 || n2 < 2735 || n2 > 2740) {
                        ((ArrayList)object).add(new Position(n2, n));
                    }
                    ++n;
                }
                ++n2;
            }
            Collections.shuffle(object);
            arrayList = new ArrayList<ItemStack>();
            n = 0;
            while (n < c.g()) {
                ItemStack itemStack;
                if (c.getItemAt(n) != null && (itemStack = c.getItemAt(n)) != null) {
                    arrayList.add(itemStack);
                }
                ++n;
            }
            Collections.shuffle(arrayList);
            n = 20;
            if (f >= 50000 && f < 150000) {
                n = 100;
            } else if (f >= 150000 && f < 1000000) {
                n = 500;
            } else if (f >= 1000000) {
                n = 1000;
            }
        } else {
            return false;
        }
        g = new PartyRoomBalloonSpawnTask(n, (ArrayList)object, arrayList);
        World.getTaskScheduler().schedule(g);
        return true;
    }

    public static boolean b(Player player) {
        if (Npc.findByDefinitionId(660) != null) {
            return false;
        }
        if (player.getInventoryManager().containsItemAmount(995, 500)) {
            player.getInventoryManager().removeItem(new ItemStack(995, 500));
            int n = 2735;
            while (n <= 2740) {
                Npc npc = new Npc(660);
                GameplayHelper.b(npc, n, 3468, 0, 1000);
                ++n;
            }
        } else {
            return false;
        }
        return true;
    }

    public static boolean a(Player object, int n, int n2, int n3) {
        if (n >= 115 && n <= 122) {
            if (((Player)object).gameMode != 0) {
                Player player = object;
                player.packetSender.sendGameMessage("You are not playing on normal gamemode and cant burst the balloons.");
                return true;
            }
            int n4 = 0;
            int n5 = 0;
            if (n2 < ((Entity)object).getPosition().getX()) {
                n4 = -1;
            }
            if (n2 > ((Entity)object).getPosition().getX()) {
                n4 = 1;
            }
            if (n3 < ((Entity)object).getPosition().getY()) {
                n5 = -1;
            }
            if (n3 > ((Entity)object).getPosition().getY()) {
                n5 = 1;
            }
            Object object2 = object;
            ((Player)object2).packetSender.queueRelativeMovementStep(n4, n5, true);
            ((Entity)object).getUpdateState().setAnimation(794);
            object2 = object;
            ((Player)object2).packetSender.sendSoundEffect(393, 1, 10);
            ObjectManager.getInstance();
            DynamicObject dynamicObject = ObjectManager.findDynamicObjectAt(n2, n3, 0);
            int n6 = dynamicObject.orientation;
            ObjectManager.getInstance().removeDynamicObjectAt(n2, n3, 0, 10);
            new DynamicObject(n + 8, n2, n3, 0, n6, 10, ServerSettings.placeholderObjectId, 2, false);
            Iterator iterator = e.iterator();
            while (iterator.hasNext()) {
                int n7;
                PartyRoomBalloonReward partyRoomBalloonReward = (PartyRoomBalloonReward)iterator.next();
                if (partyRoomBalloonReward == null) continue;
                object2 = partyRoomBalloonReward;
                if (((PartyRoomBalloonReward)object2).a == null) continue;
                object2 = partyRoomBalloonReward;
                if (((PartyRoomBalloonReward)object2).b == null) continue;
                object2 = partyRoomBalloonReward;
                if (((PartyRoomBalloonReward)object2).b.getX() != n2) continue;
                object2 = partyRoomBalloonReward;
                if (((PartyRoomBalloonReward)object2).b.getY() != n3) continue;
                object2 = partyRoomBalloonReward;
                int n8 = ((PartyRoomBalloonReward)object2).a.getId();
                object2 = partyRoomBalloonReward;
                if (((PartyRoomBalloonReward)object2).a.getAmount() <= 0) {
                    n7 = 1;
                } else {
                    object2 = partyRoomBalloonReward;
                    n7 = ((PartyRoomBalloonReward)object2).a.getAmount();
                }
                object2 = partyRoomBalloonReward;
                object = new GroundItem(new ItemStack(n8, n7), (Entity)object, ((PartyRoomBalloonReward)object2).b);
                GroundItemManager.getInstance().spawn((GroundItem)object);
                iterator.remove();
                break;
            }
            return true;
        }
        return false;
    }

    public static void c(Player player) {
        player.getPartyRoomContainer().clear();
        PartyRoomManager.f(player);
        player.packetSender.showInterfaceWithInventory(2156, 2005);
    }

    public static void b() {
        Player[] playerArray = World.f();
        int n = playerArray.length;
        int n2 = 0;
        while (n2 < n) {
            Player player = playerArray[n2];
            if (player != null && player.getOpenInterfaceId() == 2156) {
                PartyRoomManager.f(player);
            }
            ++n2;
        }
    }

    public static void b(Player player, int n, int n2, int n3) {
        boolean bl;
        if (n2 == -1) {
            return;
        }
        ItemStack itemStack = player.getInventoryManager().getContainer().getItemAt(n);
        int n4 = player.getInventoryManager().getContainer().getItemAmount(n2);
        if (itemStack == null || itemStack.getId() != n2 || !itemStack.isValid()) {
            return;
        }
        if (itemStack.getId() <= 0 || !itemStack.isValid() || n3 <= 0) {
            return;
        }
        int n5 = player.getPartyRoomContainer().getItemAmount(n2);
        boolean bl2 = bl = n5 <= 0 || !itemStack.getDefinition().isStackable();
        if (a) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("Drop party is already starting, no more items are accepted!");
            return;
        }
        if (player.getPartyRoomContainer().getFreeSlots() <= 0 && bl) {
            Player player3 = player;
            player3.packetSender.sendGameMessage("You can't deposit more items at once!");
            return;
        }
        if (new ItemStack(n2).getDefinition().z()) {
            Player player4 = player;
            player4.packetSender.sendGameMessage("You cannot deposit that item.");
            return;
        }
        if (n4 > n3) {
            n4 = n3;
        }
        if (itemStack.getDefinition().isStackable()) {
            if (!player.getInventoryManager().removeItemFromSlot(new ItemStack(n2, n4), n)) {
                player.getInventoryManager().removeItem(new ItemStack(n2, n4));
            }
        } else {
            n = 0;
            while (n < n4) {
                player.getInventoryManager().removeItem(new ItemStack(n2, 1));
                ++n;
            }
        }
        if (!bl) {
            player.getPartyRoomContainer().setItem(player.getPartyRoomContainer().indexOfItem(itemStack.getId()), new ItemStack(n2, n5 + n4));
        } else {
            ItemStack itemStack2 = new ItemStack(itemStack.getId(), n4);
            ItemContainer itemContainer = player.getPartyRoomContainer();
            itemContainer.add(itemStack2, -1);
        }
        PartyRoomManager.f(player);
    }

    public static void c(Player player, int n, int n2, int n3) {
        if (n2 == -1) {
            return;
        }
        ItemStack itemStack = player.getPartyRoomContainer().getItemAt(n);
        int n4 = player.getPartyRoomContainer().getItemAmount(n2);
        if (itemStack == null || itemStack.getId() != n2 || n3 <= 0) {
            return;
        }
        if (n4 > n3) {
            n4 = n3;
        }
        n = player.getPartyRoomContainer().removeFromSlot(new ItemStack(n2, n4), n);
        player.getInventoryManager().addItem(new ItemStack(itemStack.getId(), n));
        PartyRoomManager.f(player);
    }

    private static void f(Player player) {
        Player player2 = player;
        player2.packetSender.sendItemContainer(2006, player.getInventoryManager().getContainer().getRawItems());
        player2 = player;
        player2.packetSender.sendItemContainer(2274, player.getPartyRoomContainer().getRawItems());
        player2 = player;
        player2.packetSender.sendItemContainer(2273, c.getRawItems());
        String string = "Party Drop Chest";
        if (f >= 1000) {
            string = String.valueOf(string) + " - Value: " + GameUtil.d(f);
        }
        player2 = player;
        player2.packetSender.sendInterfaceText(string, 2248);
    }

    public static void d(Player player) {
        int n = 0;
        while (n < 8) {
            ItemStack itemStack;
            if (player.getPartyRoomContainer().getItemAt(n) != null && (itemStack = player.getPartyRoomContainer().getItemAt(n)) != null) {
                player.getPartyRoomContainer().remove(itemStack);
                player.getInventoryManager().addItem(itemStack);
            }
            ++n;
        }
        player.getPartyRoomContainer().clear();
    }

    public static void e(Player player) {
        int n = 8 - player.getPartyRoomContainer().getFreeSlots();
        int n2 = c.getFreeSlots();
        if (a) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("Drop party is already starting, no more items are accepted!");
            return;
        }
        if (n <= 0) {
            Player player3 = player;
            player3.packetSender.sendGameMessage("Add some items first!");
            return;
        }
        if (n2 <= 0) {
            Player player4 = player;
            player4.packetSender.sendGameMessage("Chest is already full!");
            return;
        }
        if (n > n2) {
            Player player5 = player;
            player5.packetSender.sendGameMessage("You can only add: " + n2 + " items to the chest!");
            return;
        }
        n = 0;
        while (n < 8) {
            ItemStack itemStack;
            if (player.getPartyRoomContainer().getItemAt(n) != null && (itemStack = player.getPartyRoomContainer().getItemAt(n)) != null) {
                player.getPartyRoomContainer().remove(itemStack);
                ItemStack itemStack2 = itemStack;
                ItemContainer itemContainer = c;
                itemContainer.add(itemStack2, -1);
                int n3 = itemStack.getDefinition().getShopValue();
                if (n3 > 0) {
                    f += itemStack.getAmount() * n3;
                }
            }
            ++n;
        }
        player.getPartyRoomContainer().clear();
        PartyRoomManager.d();
        PartyRoomManager.b();
    }

    public static void c() {
        Object object = new File("./data/partyChest.dat");
        try {
            object = new FileInputStream((File)object);
            DataInputStream dataInputStream = new DataInputStream((InputStream)object);
            try {
                int n = 0;
                while (n < i) {
                    int n2 = dataInputStream.readInt();
                    if (n2 != 65535) {
                        int n3 = dataInputStream.readInt();
                        int n4 = dataInputStream.readInt();
                        ItemStack itemStack = new ItemStack(n2, n3, n4);
                        c.setItem(n, itemStack);
                        n3 = itemStack.getDefinition().getShopValue();
                        if (n3 > 0) {
                            f += itemStack.getAmount() * n3;
                        }
                    }
                    ++n;
                }
            }
            catch (Exception exception) {}
            dataInputStream.close();
            ((FileInputStream)object).close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public static void d() {
        Object object = new File("./data/partyChest.dat");
        ((File)object).delete();
        try {
            object = new CountingDataOutputStream(new FileOutputStream("./data/partyChest.dat"));
            int n = 0;
            while (n < i) {
                ItemStack itemStack = c.getItemAt(n);
                if (itemStack == null) {
                    ((CountingDataOutputStream)object).writeInt(65535);
                } else {
                    ((CountingDataOutputStream)object).writeInt(itemStack.getId());
                    ((CountingDataOutputStream)object).writeInt(itemStack.getAmount());
                    ((CountingDataOutputStream)object).writeInt(itemStack.getMetadata());
                }
                ++n;
            }
            ((FilterOutputStream)object).close();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }
}

