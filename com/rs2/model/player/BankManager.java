/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.player;

import com.rs2.ServerSettings;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.BotTaskPlanner;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.GameplayHelper;
import com.rs2.model.World;
import com.rs2.model.bankpin.BankPinEntryMode;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.BotBankContinuationTask;
import com.rs2.model.player.DelayedBankOpenTask;
import com.rs2.model.player.Player;
import com.rs2.model.player.PlayerUpdateTask;
import com.rs2.model.skill.smithing.SmeltingHandler;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import java.util.ArrayList;
import java.util.Iterator;

public final class BankManager {
    public static int freeBankCapacity = 64;
    public static int memberBankCapacity = 64;
    private static int mainBankContainerInterfaceId = 5382;
    public static PlayerUpdateTask[] bankTabUpdateTasks = new PlayerUpdateTask[]{new PlayerUpdateTask(mainBankContainerInterfaceId, 19509, 19508, 0)};

    public static void openBank(Player object) {
        if (((Player)object).botEnabled) {
            object = new DelayedBankOpenTask(3 + GameUtil.randomInt(3), (Player)object);
            World.getTaskScheduler().schedule((TickTask)object);
            return;
        }
        BankManager.openBank((Player)object, (Player)object);
    }

    public static void selectTab(Player player, int n) {
        if (player.getBankContainer().getTabCount() - 1 < n) {
            return;
        }
        player.currentBankTab = n;
        n = 0;
        while (n < player.getBankContainer().getTabLimit()) {
            player.getBankContainer().compactTab(n);
            ItemStack[] itemStackArray = player.getBankContainer().getTabItems(n);
            Player player2 = player;
            Object object = player2;
            object = bankTabUpdateTasks[n];
            player2.packetSender.sendItemContainer(((PlayerUpdateTask)object).itemContainerInterfaceId, itemStackArray);
            ++n;
        }
    }

    public static void openBank(Player object, Player player) {
        Object object2;
        if (player.gameMode == 2) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("You are ultimate ironman and cannot use banks.");
            return;
        }
        if (object.equals(player) && ServerSettings.cacheVersion >= 336) {
            if (((Player)object).getBankPinManager().hasPin()) {
                if (!((Player)object).getBankPinManager().isVerified()) {
                    ((Player)object).getBankPinManager().setEntryMode(BankPinEntryMode.a);
                    return;
                }
            } else if (!((Player)object).isBankPinReminderShown()) {
                object2 = object;
                ((Player)object2).packetSender.sendGameMessage("You do not have a bank pin, it is highly recommended you get one.");
                ((Player)object).setBankPinReminderShown(true);
            }
        }
        ItemStack[] itemStackArray = player.getInventoryManager().getContainer().getRawItems();
        int n = 0;
        while (n < ((Player)object).getBankContainer().getTabLimit()) {
            ItemStack[] itemStackArray2;
            try {
                ((Player)object).getBankContainer().compactTab(n);
                itemStackArray2 = ((Player)object).getBankContainer().getTabItems(n);
                Player player3 = player;
                object2 = player3;
                object2 = bankTabUpdateTasks[n];
                player3.packetSender.sendItemContainer(((PlayerUpdateTask)object2).itemContainerInterfaceId, itemStackArray2);
            }
            catch (Exception itemStackArray3) {
                itemStackArray2 = itemStackArray3;
                itemStackArray3.printStackTrace();
            }
            ++n;
        }
        object2 = player;
        ((Player)object2).packetSender.sendItemContainer(5064, itemStackArray);
        object2 = player;
        ((Player)object2).packetSender.showInterfaceWithInventory(5292, 5063);
        player.getAttributes().put("isBanking", Boolean.TRUE);
        if (player.getQuestState(0) == 51) {
            player.ea();
        }
        if (player.botEnabled) {
            if (player.currentBotTask.lootSellShopIds.size() > 0 && player.botMode != 4) {
                BotCombatHelper.sellBotLootItems(player);
            }
            if (player.botTaskState.equals("empty inventory")) {
                BankManager.depositInventory(player);
                if (player.botCombatLoadoutItemIds != null && player.botCombatLoadoutItemIds.size() > 0) {
                    BankManager.depositEquipment(player);
                }
            }
            if (!player.botTaskReturnToBankRequested) {
                GameplayHelper.shouldReturnToBankForBotTask(player);
            }
            if (player.botTaskReturnToBankRequested) {
                BotBankContinuationTask botBankContinuationTask = new BotBankContinuationTask(10 + GameUtil.randomInt(10), player);
                World.getTaskScheduler().schedule(botBankContinuationTask);
                return;
            }
            if (player.botTaskRequiredItems != null) {
                itemStackArray = player.botTaskRequiredItems;
                int n2 = player.botTaskRequiredItems.length;
                int n3 = 0;
                while (n3 < n2) {
                    ItemStack itemStack = itemStackArray[n3];
                    if (itemStack.getDefinition().getEquipmentSlot() == -1 || itemStack.getAmount() != 1 || !player.getEquipmentManager().containsItem(itemStack.getId())) {
                        BankManager.withdrawItem(player, itemStack.getId(), itemStack.getAmount());
                    }
                    ++n3;
                }
                if (player.botTaskReturnToBankRequested) {
                    if (BotTaskDefinition.leatherCraftingTasks.contains(player.currentBotTask) || BotTaskDefinition.tanningTasks.contains(player.currentBotTask) || BotTaskDefinition.spinningTasks.contains(player.currentBotTask) || BotTaskDefinition.cookingTasks.contains(player.currentBotTask) || BotTaskDefinition.smeltingTasks.contains(player.currentBotTask) || BotTaskDefinition.smithingTasks.contains(player.currentBotTask)) {
                        GameplayHelper.a(player, true);
                    } else {
                        GameplayHelper.c(player);
                    }
                    BankManager.depositInventory(player);
                    return;
                }
                n = 0;
                while (n < player.getInventoryManager().getContainer().getItems().length) {
                    if (player.getInventoryManager().getContainer().getItems()[n] != null) {
                        ItemStack itemStack = player.getInventoryManager().getContainer().getItems()[n];
                        if (player.botMode != 4) {
                            if (itemStack.getDefinition().getEquipmentSlot() == 13) {
                                player.getEquipmentManager().equipFromInventorySlot(n);
                            }
                        } else if (itemStack.getDefinition().getEquipmentSlot() != -1) {
                            player.getEquipmentManager().equipFromInventorySlot(n);
                        }
                    }
                    ++n;
                }
            }
            if (player.botCombatLoadoutItemIds != null && player.currentBotTask.combatTask) {
                player.queuePublicChatMessage("Im going to train some combat stats.");
                if (player.botCombatLoadoutItemIds.size() > 0) {
                    n = 0;
                    while (n < player.botCombatLoadoutItemIds.size()) {
                        ItemStack itemStack = new ItemStack((Integer)player.botCombatLoadoutItemIds.get(n));
                        BankManager.withdrawItem(player, itemStack.getId(), itemStack.getAmount());
                        ++n;
                    }
                    n = 0;
                    while (n < player.getInventoryManager().getContainer().getItems().length) {
                        if (player.getInventoryManager().getContainer().getItems()[n] != null) {
                            ItemStack itemStack = player.getInventoryManager().getContainer().getItems()[n];
                            if (player.botMode != 4) {
                                if (itemStack.getDefinition().getEquipmentSlot() == 13) {
                                    player.getEquipmentManager().equipFromInventorySlot(n);
                                }
                            } else if (itemStack.getDefinition().getEquipmentSlot() != -1) {
                                player.getEquipmentManager().equipFromInventorySlot(n);
                            }
                        }
                        ++n;
                    }
                    player.botCombatLoadoutItemIds.clear();
                }
                BotTaskPlanner.selectMeleeTrainingFightMode(player);
            }
            BankManager.depositInventory(player);
            if (player.botTaskRequiredItems != null) {
                itemStackArray = player.botTaskRequiredItems;
                int n4 = player.botTaskRequiredItems.length;
                int n5 = 0;
                while (n5 < n4) {
                    ItemStack itemStack = itemStackArray[n5];
                    if (itemStack.getDefinition().getEquipmentSlot() == -1 || itemStack.getAmount() != 1 || !player.getEquipmentManager().containsItem(itemStack.getId())) {
                        BankManager.withdrawItem(player, itemStack.getId(), itemStack.getAmount());
                    }
                    ++n5;
                }
            }
            if (player.botShopSellItemIds != null && player.botShopSellItemIds.size() > 0) {
                player.setBankWithdrawNoteMode(true);
                Iterator iterator = player.botShopSellItemIds.iterator();
                while (iterator.hasNext()) {
                    int n6 = (Integer)iterator.next();
                    object = player.getBankContainer().findItem(n6);
                    if (object == null) continue;
                    BankManager.withdrawItem(player, ((ItemStack)object).getId(), ((ItemStack)object).getAmount());
                }
                player.setBankWithdrawNoteMode(false);
            }
            player.currentBotTask.startWalkToTask(player);
            player.botInteractionOption = 1;
            object2 = player;
            ((Player)object2).packetSender.closeInterfaces();
        }
    }

    public static void openDepositBox(Player player) {
        if (player.gameMode == 2) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("You are ultimate ironman and cannot use banks.");
            return;
        }
        ItemStack[] itemStackArray = player.getInventoryManager().getContainer().getRawItems();
        Player player3 = player;
        player3.packetSender.sendItemContainer(7423, itemStackArray);
        player3 = player;
        player3.packetSender.showInterfaceWithInventory(4465, 197);
        player.getAttributes().put("isBanking", Boolean.TRUE);
    }

    public static boolean depositEquipment(Player player) {
        Object object;
        ItemStack[] itemStackArray = new ArrayList();
        ItemStack[] itemStackArray2 = player.getEquipmentManager().getContainer().getItems();
        int n = itemStackArray2.length;
        int n2 = 0;
        while (n2 < n) {
            object = itemStackArray2[n2];
            if (object != null && !itemStackArray.contains(object)) {
                itemStackArray.add(object);
            }
            ++n2;
        }
        object = new ItemStack[itemStackArray.size()];
        n2 = 0;
        while (n2 < ((ItemStack[])object).length) {
            object[n2] = (ItemStack)itemStackArray.get(n2);
            ++n2;
        }
        if (BankManager.canDepositItems(player, object)) {
            itemStackArray = player.getEquipmentManager().getContainer().getItems();
            int n3 = itemStackArray.length;
            n = 0;
            while (n < n3) {
                ItemStack itemStack = itemStackArray[n];
                if (itemStack != null) {
                    player.getEquipmentManager().removeItemWithoutRefresh(itemStack);
                    BankManager.depositToBank(player, itemStack);
                }
                ++n;
            }
            player.getEquipmentManager().finishBulkEquipmentRemoval();
            BankManager.refreshBankAndInventory(player);
            return true;
        }
        return false;
    }

    public static boolean depositInventory(Player player) {
        Object object;
        ItemStack[] itemStackArray = new ArrayList();
        ItemStack[] itemStackArray2 = player.getInventoryManager().getContainer().getItems();
        int n = itemStackArray2.length;
        int n2 = 0;
        while (n2 < n) {
            object = itemStackArray2[n2];
            if (object != null) {
                itemStackArray.contains(object);
                itemStackArray.add(object);
            }
            ++n2;
        }
        object = new ItemStack[itemStackArray.size()];
        n2 = 0;
        while (n2 < ((ItemStack[])object).length) {
            object[n2] = (ItemStack)itemStackArray.get(n2);
            ++n2;
        }
        if (BankManager.canDepositItems(player, object)) {
            itemStackArray = player.getInventoryManager().getContainer().getItems();
            int n3 = itemStackArray.length;
            n = 0;
            while (n < n3) {
                ItemStack itemStack = itemStackArray[n];
                if (itemStack != null) {
                    player.getInventoryManager().removeItem(itemStack);
                    BankManager.depositToBank(player, itemStack);
                }
                ++n;
            }
            BankManager.refreshBankAndInventory(player);
            return true;
        }
        return false;
    }

    public static boolean depositInventoryAndEquipment(Player player) {
        Object object;
        ItemStack[] itemStackArray = new ArrayList();
        ItemStack[] itemStackArray2 = player.getInventoryManager().getContainer().getItems();
        int n = itemStackArray2.length;
        int n2 = 0;
        while (n2 < n) {
            object = itemStackArray2[n2];
            if (object != null && !itemStackArray.contains(object)) {
                itemStackArray.add(object);
            }
            ++n2;
        }
        itemStackArray2 = player.getEquipmentManager().getContainer().getItems();
        n = itemStackArray2.length;
        n2 = 0;
        while (n2 < n) {
            object = itemStackArray2[n2];
            if (object != null && !itemStackArray.contains(object)) {
                itemStackArray.add(object);
            }
            ++n2;
        }
        object = new ItemStack[itemStackArray.size()];
        n2 = 0;
        while (n2 < ((ItemStack[])object).length) {
            object[n2] = (ItemStack)itemStackArray.get(n2);
            ++n2;
        }
        if (BankManager.canDepositItems(player, object)) {
            itemStackArray = player.getInventoryManager().getContainer().getItems();
            int n3 = itemStackArray.length;
            n = 0;
            while (n < n3) {
                ItemStack itemStack = itemStackArray[n];
                if (itemStack != null) {
                    player.getInventoryManager().removeItem(itemStack);
                    BankManager.depositToBank(player, itemStack);
                }
                ++n;
            }
            itemStackArray = player.getEquipmentManager().getContainer().getItems();
            n3 = itemStackArray.length;
            n = 0;
            while (n < n3) {
                ItemStack itemStack = itemStackArray[n];
                if (itemStack != null) {
                    player.getEquipmentManager().removeItemWithoutRefresh(itemStack);
                    BankManager.depositToBank(player, itemStack);
                }
                ++n;
            }
            player.getEquipmentManager().finishBulkEquipmentRemoval();
            BankManager.refreshBankAndInventory(player);
            return true;
        }
        return false;
    }

    private static boolean canDepositItem(Player player, ItemStack object, int n) {
        int n2;
        int n3 = ((ItemStack)object).getDefinition().isNote();
        n3 = n3 != 0 ? ((ItemStack)object).getDefinition().getUnnotedId() : ((ItemStack)object).getDefinition().getId();
        n3 = player.getBankContainer().getItemAmount(n3);
        if (((ItemStack)object).getMetadata() != -1) {
            n3 = 0;
        }
        if (n3 > 0) {
            return true;
        }
        object = player;
        int n4 = ((Player)object).isBot ? 288 : (n2 = ((Player)object).isMember() ? memberBankCapacity : freeBankCapacity);
        if (n >= n2) {
            player.packetSender.sendGameMessage("You don't have enough space in your bank account.");
            return false;
        }
        return true;
    }

    private static void depositToBank(Player player, ItemStack itemStack) {
        if (itemStack == null) {
            return;
        }
        int n = itemStack.getAmount();
        int n2 = player.currentBankTab;
        int n3 = itemStack.getDefinition().isNote();
        n3 = n3 != 0 ? itemStack.getDefinition().getUnnotedId() : itemStack.getDefinition().getId();
        int n4 = player.getBankContainer().getItemAmount(n3);
        int n5 = itemStack.getMetadata();
        if (!BankManager.canDepositItem(player, itemStack, player.getBankContainer().getUsedSlots())) {
            return;
        }
        int n6 = player.getBankContainer().findTabContainingItem(n3);
        int n7 = -1;
        if (n6 != -1) {
            n7 = player.getBankContainer().indexOfItemInTab(n3, n6);
            n2 = n6;
        }
        if (n5 != -1) {
            n7 = -1;
            n6 = player.getBankContainer().findTabContainingPlaceholder(n3, 0);
            if (n6 != -1) {
                n7 = player.getBankContainer().indexOfPlaceholderInTab(n3, 0, n6);
                n2 = n6;
            }
            n4 = 0;
            n = 1;
        }
        if (n7 == -1) {
            player.getBankContainer().addToTab(new ItemStack(n3, n, n5), n2);
            return;
        }
        player.getBankContainer().setTabItem(n7, new ItemStack(n3, n4 + n, n5), n2);
    }

    private static boolean canDepositItems(Player player, ItemStack[] object) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        int n = player.getBankContainer().getUsedSlots();
        ItemStack[] itemStackArray = object;
        int n2 = ((ItemStack[])object).length;
        int n3 = 0;
        while (n3 < n2) {
            object = itemStackArray[n3];
            int n4 = ((ItemStack)object).getDefinition().isNote();
            int n5 = n4 = n4 != 0 ? ((ItemStack)object).getDefinition().getUnnotedId() : ((ItemStack)object).getDefinition().getId();
            if (!arrayList.contains(n4)) {
                int n6 = player.getBankContainer().getItemAmount(n4);
                if (((ItemStack)object).getMetadata() != -1) {
                    n6 = 0;
                }
                if (!BankManager.canDepositItem(player, (ItemStack)object, n)) {
                    return false;
                }
                if (n6 == 0) {
                    ++n;
                    arrayList.add(n4);
                }
            }
            ++n3;
        }
        return true;
    }

    private static void refreshBankAndInventory(Player player) {
        player.getInventoryManager().sendToInterface(5064);
        if (ServerSettings.cacheVersion >= 336) {
            player.getInventoryManager().sendToInterface(7423);
        }
        int n = 0;
        while (n < player.getBankContainer().getTabLimit()) {
            ItemStack[] itemStackArray = player.getBankContainer().getTabItems(n);
            Player player2 = player;
            Object object = player2;
            object = bankTabUpdateTasks[n];
            player2.packetSender.sendItemContainer(((PlayerUpdateTask)object).itemContainerInterfaceId, itemStackArray);
            ++n;
        }
    }

    public static void depositInventoryItem(Player player, int n, int n2, int n3) {
        ItemStack itemStack = player.getInventoryManager().getContainer().getItemAt(n);
        if (itemStack == null || itemStack.getId() != n2 || !itemStack.isValid()) {
            return;
        }
        int n4 = player.getInventoryManager().getContainer().getItemAmount(n2);
        int n5 = player.currentBankTab;
        int n6 = itemStack.getMetadata();
        int n7 = itemStack.getDefinition().isNote();
        if (itemStack.getDefinition().getId() > 11883) {
            player.packetSender.sendGameMessage("This item is not supported yet.");
            return;
        }
        n7 = n7 != 0 ? itemStack.getDefinition().getUnnotedId() : itemStack.getDefinition().getId();
        int n8 = player.getBankContainer().getItemAmount(n7);
        int n9 = player.getBankContainer().findTabContainingItem(n7);
        int n10 = -1;
        if (n9 != -1) {
            n10 = player.getBankContainer().indexOfItemInTab(n7, n9);
            n5 = n9;
        }
        if (n6 != -1) {
            n10 = -1;
            n9 = player.getBankContainer().findTabContainingPlaceholder(n7, 0);
            if (n9 != -1) {
                n10 = player.getBankContainer().indexOfPlaceholderInTab(n7, 0, n9);
                n5 = n9;
            }
            n8 = 0;
            n4 = 1;
        }
        if (!BankManager.canDepositItem(player, itemStack, player.getBankContainer().getUsedSlots())) {
            return;
        }
        if (n4 > n3) {
            n4 = n3;
        }
        if (!itemStack.getDefinition().isStackable()) {
            n3 = 0;
            while (n3 < n4) {
                if (n6 != -1) {
                    player.getInventoryManager().removeItemFromSlot(new ItemStack(n2, 1, n6), n);
                } else {
                    player.getInventoryManager().removeItem(new ItemStack(n2, 1, n6));
                }
                ++n3;
            }
        } else if (n6 != -1) {
            player.getInventoryManager().removeItemFromSlot(new ItemStack(n2, n4, n6), n);
        } else {
            player.getInventoryManager().removeItem(new ItemStack(n2, n4, n6));
        }
        if (n10 == -1) {
            player.getBankContainer().addToTab(new ItemStack(n7, n4, n6), n5);
        } else {
            player.getBankContainer().setTabItem(n10, new ItemStack(n7, n8 + n4, n6), n5);
        }
        BankManager.refreshBankAndInventory(player);
    }

    private static void withdrawItem(Player player, int n, int n2) {
        ItemStack itemStack = new ItemStack(n);
        int n3 = player.getBankContainer().getItemAmount(n);
        int n4 = 0;
        if (n3 != 0) {
            n4 = player.getBankContainer().findTabContainingItem(n);
        }
        int n5 = 0;
        if (n3 != 0) {
            n5 = player.getBankContainer().indexOfItemInTab(n, n4);
        }
        boolean bl = player.isBankWithdrawNoteMode();
        boolean bl2 = itemStack.getDefinition().hasNote();
        int n6 = itemStack.getDefinition().getNotedId();
        if (player.botMode == 4 && player.botEnabled && player.currentBotTask != null && BotTaskDefinition.smeltingTasks.contains(player.currentBotTask) && n == 453 && n3 < SmeltingHandler.getCoalRequiredForBar(player.botTaskItemId)) {
            player.botTaskReturnToBankRequested = true;
            return;
        }
        if (n3 <= 0 || n < 0 || player.getBankContainer().getItemAtTabSlot(n5, n4) == null || player.getBankContainer().getItemAtTabSlot(n5, n4).getId() != itemStack.getId() || !itemStack.isValid()) {
            if (player.botEnabled && player.currentBotTask != null && player.botTaskRequiredItems != null && player.botTaskRequiredItems.length > 0) {
                int n7 = 0;
                while (n7 < player.botTaskRequiredItems.length) {
                    if (player.botTaskRequiredItems[n7].getId() == n && player.getInventoryManager().getItemAmount(n) < player.botTaskRequiredItems[n7].getAmount()) {
                        player.botTaskReturnToBankRequested = true;
                    }
                    ++n7;
                }
            }
            return;
        }
        int n8 = player.getBankContainer().getItemAtTabSlot(n5, n4).getMetadata();
        if (n8 != -1) {
            n2 = 1;
        }
        if (n > 11883) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("This item is not supported yet.");
            return;
        }
        if (n3 < n2) {
            n2 = n3;
        }
        if (bl && !bl2) {
            Player player3 = player;
            player3.packetSender.sendGameMessage("This item cannot be withdrawn as a note.");
            bl = false;
        }
        n3 = 0;
        if (!bl || !bl2) {
            n3 = player.getInventoryManager().addItemPartial(new ItemStack(n, n2, n8));
        } else if (bl) {
            n3 = player.getInventoryManager().addItemPartial(new ItemStack(n6, n2, n8));
        }
        player.getBankContainer().removeFromTab(new ItemStack(n, n3, n8), n5, n4);
        player.getInventoryManager().sendToInterface(5064);
        n = 0;
        while (n < player.getBankContainer().getTabLimit()) {
            ItemStack[] itemStackArray = player.getBankContainer().getTabItems(n);
            Player player4 = player;
            Object object = player4;
            object = bankTabUpdateTasks[n];
            player4.packetSender.sendItemContainer(((PlayerUpdateTask)object).itemContainerInterfaceId, itemStackArray);
            ++n;
        }
    }

    public static void withdrawItemFromTab(Player player, int n, int n2, int n3, int n4) {
        ItemStack itemStack = new ItemStack(n2);
        int n5 = player.currentBankTab;
        int n6 = 0;
        while (n6 < bankTabUpdateTasks.length) {
            PlayerUpdateTask playerUpdateTask;
            PlayerUpdateTask playerUpdateTask2 = playerUpdateTask = bankTabUpdateTasks[n6];
            if (playerUpdateTask.itemContainerInterfaceId == n4) {
                n5 = n6;
                break;
            }
            ++n6;
        }
        n6 = player.isBankWithdrawNoteMode() ? 1 : 0;
        boolean bl = itemStack.getDefinition().hasNote();
        n4 = itemStack.getDefinition().getNotedId();
        int n7 = player.getBankContainer().getItemAmount(n2);
        if (n3 <= 0 || player.getBankContainer().getItemAtTabSlot(n, n5) == null || player.getBankContainer().getItemAtTabSlot(n, n5).getId() != itemStack.getId()) {
            return;
        }
        int n8 = player.getBankContainer().getItemAtTabSlot(n, n5).getMetadata();
        int n9 = player.getBankContainer().getItemAtTabSlot(n, n5).getAmount();
        int n10 = 0;
        if (n9 <= 0) {
            player.getBankContainer().removeFromTab(new ItemStack(n2, 0, n8), n, n5);
            player.getBankContainer().removeEmptyTabs();
            BankManager.refreshBankTabs(player);
            return;
        }
        if (n2 < 0 || !itemStack.isValid()) {
            return;
        }
        if (n8 != -1) {
            n3 = 1;
        }
        if (n2 > 11883) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("This item is not supported yet.");
            return;
        }
        if (n7 < n3) {
            n3 = n7;
        }
        if (n6 != 0 && !bl) {
            Player player3 = player;
            player3.packetSender.sendGameMessage("This item cannot be withdrawn as a note.");
            n6 = 0;
        }
        if (n6 == 0 || !bl) {
            n10 = player.getInventoryManager().addItemPartial(new ItemStack(n2, n3, n8));
        } else if (n6 != 0) {
            n10 = player.getInventoryManager().addItemPartial(new ItemStack(n4, n3, n8));
        }
        player.getBankContainer().removeFromTab(new ItemStack(n2, n10, n8), n, n5);
        player.getBankContainer().removeEmptyTabs();
        player.getInventoryManager().sendToInterface(5064);
        BankManager.refreshBankTabs(player);
    }

    private static void refreshBankTabs(Player player) {
        int n = 0;
        while (n < player.getBankContainer().getTabLimit()) {
            ItemStack[] itemStackArray = player.getBankContainer().getTabItems(n);
            Player player2 = player;
            Object object = player2;
            object = bankTabUpdateTasks[n];
            player2.packetSender.sendItemContainer(((PlayerUpdateTask)object).itemContainerInterfaceId, itemStackArray);
            ++n;
        }
    }

    /*
     * Exception decompiling
     */
    public static void rearrangeBankItem(Player var0, int var1_1, int var2_2, int var3_5, int var4_8) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Statement already marked as first in another block
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.markFirstStatementInBlock(Op03SimpleStatement.java:461)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.markWholeBlock(Misc.java:251)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.considerAsSimpleIf(ConditionalRewriter.java:673)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.identifyNonjumpingConditionals(ConditionalRewriter.java:56)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:722)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }
}

