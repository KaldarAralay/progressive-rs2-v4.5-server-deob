/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.ground;

import com.rs2.ServerSettings;
import com.rs2.bot.BotPlayer;
import com.rs2.bot.DropPartyBotManager;
import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemVisibility;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;
import java.util.LinkedList;

public final class GroundItemManager
implements Runnable {
    private LinkedList a = new LinkedList();
    private static int b;
    private static int c;
    private static int d;
    private static int e;
    private static GroundItemManager f;

    static {
        f = new GroundItemManager();
        b = (int)GameUtil.secondsToTicks(100L);
        c = (int)GameUtil.secondsToTicks(100L);
        d = (int)GameUtil.secondsToTicks(ServerSettings.groundItemLifetimeSeconds);
        e = (int)GameUtil.secondsToTicks(ServerSettings.groundItemLifetimeSeconds);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void run() {
        try {
            GroundItem groundItem;
            int n = this.a.size();
            LinkedList<GroundItem> linkedList = new LinkedList<GroundItem>();
            int n2 = 0;
            while (n2 < n) {
                groundItem = (GroundItem)this.a.get(n2);
                if (groundItem == null) continue;
                switch (groundItem.getVisibility()) {
                    case b: {
                        linkedList.add(groundItem);
                        break;
                    }
                }
                ++n2;
            }
            n2 = 0;
            block11: while (n2 < n) {
                groundItem = (GroundItem)this.a.get(n2);
                if (groundItem == null) continue;
                switch (groundItem.getVisibility()) {
                    case c: {
                        if (groundItem.getItem().getId() == 6903 && groundItem.getTimer().elapsed() < 700 || (!groundItem.isRespawning() ? groundItem.getTimer().elapsed() < b : groundItem.getTimer().elapsed() < groundItem.getRespawnDelayTicks())) break;
                        groundItem.getTimer().reset();
                        groundItem.setVisibility(GroundItemVisibility.b);
                        if (groundItem.getItem().getDefinition().isStackable()) {
                            for (GroundItem groundItem2 : linkedList) {
                                if (groundItem2 == null || !groundItem.canStackWith(groundItem2)) continue;
                                GroundItemManager.mergeStackedItems(groundItem, groundItem2, World.getPlayers());
                                this.a.remove(n2);
                                --n;
                                continue block11;
                            }
                        }
                        GroundItemManager.showToPlayers(groundItem, World.getPlayers());
                        break;
                    }
                    case b: {
                        if (groundItem.getTimer().elapsed() < d) break;
                        groundItem.getTimer().reset();
                        if (groundItem.isRespawning()) break;
                        linkedList.remove(groundItem);
                        this.removeForPlayers(groundItem, World.getPlayers());
                        --n;
                        continue block11;
                    }
                    case a: {
                        if (groundItem.getItem().getDefinition().isUntradeable()) {
                            Entity entity;
                            if (groundItem.getItem().getId() == 6888) {
                                if (groundItem.getOwner().resolve() != null) break;
                                this.a.remove(groundItem);
                                --n;
                                continue block11;
                            }
                            if (groundItem.getTimer().elapsed() < e) break;
                            groundItem.getTimer().reset();
                            if (groundItem.getOwner() != null && (entity = groundItem.getOwner().resolve()) != null && entity.isPlayer()) {
                                this.removeForPlayers(groundItem, new Player[]{(Player)entity});
                            }
                            --n;
                            continue block11;
                        }
                        if (groundItem.getTimer().elapsed() < c) break;
                        groundItem.getTimer().reset();
                        groundItem.setVisibility(GroundItemVisibility.b);
                        if (groundItem.getItem().getDefinition().isStackable()) {
                            for (GroundItem groundItem3 : linkedList) {
                                if (groundItem3 == null || !groundItem.canStackWith(groundItem3)) continue;
                                GroundItemManager.mergeStackedItems(groundItem, groundItem3, World.getPlayers());
                                this.a.remove(n2);
                                --n;
                                continue block11;
                            }
                        }
                        linkedList.add(groundItem);
                        Player[] playerArray = World.getPlayers();
                        Object object = groundItem.getOwner() == null ? null : groundItem.getOwner().resolve();
                        if (object != null && !((Entity)object).isPlayer()) {
                            object = null;
                        }
                        int n3 = 0;
                        while (n3 < playerArray.length) {
                            Player player = playerArray[n3];
                            if (player != null && (player != object || !player.getVisibleGroundItems().contains(groundItem)) && groundItem.isVisibleTo(player)) {
                                player.getVisibleGroundItems().add(groundItem);
                                player.packetSender.sendGroundItemCreate(groundItem);
                            }
                            ++n3;
                        }
                        if (!DropPartyBotManager.pendingDropPartyGroundItems.contains(groundItem)) break;
                        DropPartyBotManager.notifyDropPartyRewardDropped(groundItem);
                        DropPartyBotManager.pendingDropPartyGroundItems.remove(groundItem);
                    }
                }
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

    private static void mergeStackedItems(GroundItem groundItem, GroundItem groundItem2, Player[] object) {
        Object object2;
        int n;
        int n2;
        Object object3;
        LinkedList<Object> linkedList = new LinkedList<Object>();
        if (groundItem == null || groundItem2 == null) {
            return;
        }
        if (object != null) {
            object3 = object;
            n2 = ((Player[])object).length;
            n = 0;
            while (n < n2) {
                object = object3[n];
                if (object != null && groundItem2.isVisibleTo((Player)object)) {
                    if (((Player)object).getVisibleGroundItems().contains(groundItem2)) {
                        object2 = object;
                        ((Player)object2).packetSender.sendGroundItemRemove(groundItem2);
                        ((Player)object).getVisibleGroundItems().remove(groundItem2);
                    }
                    if (((Player)object).getVisibleGroundItems().contains(groundItem)) {
                        object2 = object;
                        ((Player)object2).packetSender.sendGroundItemRemove(groundItem);
                        ((Player)object).getVisibleGroundItems().remove(groundItem);
                    }
                    linkedList.add(object);
                }
                ++n;
            }
        }
        object = linkedList.toArray(new Player[linkedList.size()]);
        groundItem2.getItem().setAmount(groundItem2.getItem().getAmount() + groundItem.getItem().getAmount());
        groundItem2.getTimer().reset();
        if (object != null) {
            object3 = object;
            n2 = ((Object)object3).length;
            n = 0;
            while (n < n2) {
                object = object3[n];
                if (groundItem.isVisibleTo((Player)object)) {
                    object2 = object;
                    ((Player)object2).packetSender.sendGroundItemCreate(groundItem2);
                    ((Player)object).getVisibleGroundItems().add(groundItem2);
                }
                ++n;
            }
        }
    }

    private void removeForPlayers(GroundItem groundItem, Player[] object) {
        if (groundItem == null) {
            return;
        }
        if (DropPartyBotManager.pendingDropPartyGroundItems.contains(groundItem)) {
            DropPartyBotManager.pendingDropPartyGroundItems.remove(groundItem);
        }
        Player[] playerArray = object;
        int n = ((Player[])object).length;
        int n2 = 0;
        while (n2 < n) {
            object = playerArray[n2];
            if (object != null && groundItem.isVisibleTo((Player)object)) {
                ((Player)object).getVisibleGroundItems().remove(groundItem);
                ((Player)object).packetSender.sendGroundItemRemove(groundItem);
            }
            ++n2;
        }
        this.a.remove(groundItem);
    }

    private static void showToPlayers(GroundItem groundItem, Player[] object) {
        if (groundItem == null) {
            return;
        }
        Player[] playerArray = object;
        int n = ((Player[])object).length;
        int n2 = 0;
        while (n2 < n) {
            object = playerArray[n2];
            if (object != null && groundItem.isVisibleTo((Player)object)) {
                ((Player)object).getVisibleGroundItems().add(groundItem);
                ((Player)object).packetSender.sendGroundItemCreate(groundItem);
            }
            ++n2;
        }
    }

    public final boolean removeForPickup(GroundItem groundItem, Player player) {
        Entity entity;
        if (groundItem == null) {
            return false;
        }
        if (DropPartyBotManager.pendingDropPartyGroundItems.contains(groundItem)) {
            DropPartyBotManager.pendingDropPartyGroundItems.remove(groundItem);
        }
        Player[] playerArray = null;
        if (groundItem.getVisibility() == GroundItemVisibility.a) {
            if (groundItem.getOwner() != null && (entity = groundItem.getOwner().resolve()) != null && entity.isPlayer()) {
                playerArray = new Player[]{(Player)entity};
            }
        } else if (groundItem.getVisibility() == GroundItemVisibility.b) {
            playerArray = World.getPlayers();
        }
        if (groundItem.getVisibility() == GroundItemVisibility.b && groundItem.isRespawning()) {
            groundItem.setVisibility(GroundItemVisibility.c);
            groundItem.getTimer().reset();
        } else {
            if (!this.a.contains(groundItem)) {
                return false;
            }
            this.a.remove(groundItem);
        }
        if (playerArray == null) {
            return false;
        }
        Player[] playerArray2 = playerArray;
        int n = playerArray.length;
        int n2 = 0;
        while (n2 < n) {
            entity = playerArray2[n2];
            if (entity != null && groundItem.isVisibleTo((Player)entity)) {
                ((Player)entity).getVisibleGroundItems().contains(groundItem);
                ((Player)entity).getVisibleGroundItems().remove(groundItem);
                ((Player)entity).packetSender.sendGroundItemRemove(groundItem);
            }
            ++n2;
        }
        player.getVisibleGroundItems().contains(groundItem);
        return true;
    }

    public final void spawn(GroundItem groundItem) {
        Player[] playerArray = null;
        if (groundItem.getSource() != null && BotPlayer.dropPartyBots.size() > 0 && groundItem.getSource().resolve() == BotPlayer.dropPartyBots.get(0)) {
            DropPartyBotManager.pendingDropPartyGroundItems.add(groundItem);
        }
        int n = this.a.size();
        LinkedList<GroundItem> linkedList = new LinkedList<GroundItem>();
        int n2 = 0;
        while (n2 < n) {
            GroundItem groundItem2 = (GroundItem)this.a.get(n2);
            if (groundItem2 == null) continue;
            switch (groundItem2.getVisibility()) {
                case b: {
                    linkedList.add(groundItem2);
                }
            }
            ++n2;
        }
        switch (groundItem.getVisibility()) {
            case a: {
                Entity entity;
                if (groundItem.getOwner() != null && (entity = groundItem.getOwner().resolve()) != null && entity.isPlayer()) {
                    playerArray = new Player[]{(Player)entity};
                }
                if (!ItemDefinition.forId(groundItem.getItem().getId()).isStackable()) break;
                for (GroundItem groundItem2 : this.a) {
                    if (!groundItem.canStackWith(groundItem2)) continue;
                    GroundItemManager.mergeStackedItems(groundItem, groundItem2, playerArray);
                    return;
                }
                break;
            }
            case b: {
                playerArray = World.getPlayers();
                if (!ItemDefinition.forId(groundItem.getItem().getId()).isStackable()) break;
                for (GroundItem groundItem2 : linkedList) {
                    if (!groundItem.canStackWith(groundItem2)) continue;
                    GroundItemManager.mergeStackedItems(groundItem, groundItem2, playerArray);
                    return;
                }
                break;
            }
        }
        this.a.add(groundItem);
        if (playerArray == null) {
            return;
        }
        GroundItemManager.showToPlayers(groundItem, playerArray);
    }

    public static GroundItemManager getInstance() {
        return f;
    }

    public static boolean isVisible(Player object2, GroundItem groundItem) {
        if (object2 == null || groundItem == null) {
            return false;
        }
        for (Object object2 : ((Player)object2).getVisibleGroundItems()) {
            if (!object2.equals(groundItem)) continue;
            return true;
        }
        return false;
    }

    public static GroundItem findVisibleItem(Player object2, int n, Position position) {
        if (object2 == null) {
            return null;
        }
        for (Object object2 : ((Player)object2).getVisibleGroundItems()) {
            if (((GroundItem)object2).getItem().getId() != n || !((GroundItem)object2).getPosition().equals(position)) continue;
            return object2;
        }
        return null;
    }

    public static GroundItem findVisibleItemAt(Player object2, Position position) {
        if (object2 == null) {
            return null;
        }
        for (Object object2 : ((Player)object2).getVisibleGroundItems()) {
            if (!((GroundItem)object2).getPosition().equals(position)) continue;
            return object2;
        }
        return null;
    }

    public static void clearVisibleItems(Player player) {
        if (player == null) {
            return;
        }
        for (GroundItem groundItem : player.getVisibleGroundItems()) {
            Player player2 = player;
            player2.packetSender.sendGroundItemRemove(groundItem);
        }
        player.getVisibleGroundItems().clear();
    }

    public final void refreshForPlayer(Player player) {
        if (player == null) {
            return;
        }
        for (GroundItem groundItem : this.a) {
            Player player2;
            if (groundItem == null || !groundItem.isVisibleTo(player)) continue;
            if (groundItem.getVisibility() == GroundItemVisibility.b) {
                player.getVisibleGroundItems().add(groundItem);
                player2 = player;
                player2.packetSender.sendGroundItemCreate(groundItem);
                continue;
            }
            if (groundItem.getVisibility() != GroundItemVisibility.a || groundItem.getOwner() == null || !groundItem.getOwner().equals(player)) continue;
            player.getVisibleGroundItems().add(groundItem);
            player2 = player;
            player2.packetSender.sendGroundItemCreate(groundItem);
        }
    }
}

