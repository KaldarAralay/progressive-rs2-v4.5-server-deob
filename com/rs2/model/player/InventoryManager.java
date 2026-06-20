/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.player;

import com.rs2.model.combat.CombatManager;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemContainer;
import com.rs2.model.item.ItemContainerType;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;

public final class InventoryManager {
    private Player player;
    private ItemContainer container = new ItemContainer(ItemContainerType.a, 28);

    public InventoryManager(Player player) {
        this.player = player;
    }

    public final void refresh() {
        ItemStack[] itemStackArray = this.container.getRawItems();
        Player player = this.player;
        player.packetSender.sendItemContainer(3214, itemStackArray);
    }

    public final void sendToInterface(int n) {
        ItemStack[] itemStackArray = this.container.getRawItems();
        Player player = this.player;
        player.packetSender.sendItemContainer(n, itemStackArray);
    }

    public final int addItemPartial(ItemStack object) {
        if (!this.hasSpaceFor((ItemStack)object)) {
            object = this.player;
            ((Player)object).packetSender.sendGameMessage("Not enough space in your inventory.");
            object = this.player;
            ((Player)object).packetSender.sendSoundEffect(1878, 1, 0);
            return 0;
        }
        int n = ((ItemStack)object).getAmount();
        if (!((ItemStack)object).getDefinition().isStackable() && n > this.container.getFreeSlots()) {
            n = this.container.getFreeSlots();
        }
        ItemStack itemStack = new ItemStack(((ItemStack)object).getId(), n, ((ItemStack)object).getMetadata());
        object = this.container;
        ((ItemContainer)object).add(itemStack, -1);
        this.refresh();
        this.player.getEquipmentManager().refreshCarriedValue();
        return n;
    }

    public final void addOrDropItem(ItemStack itemStack) {
        if (!this.addItem(itemStack)) {
            if (!itemStack.getDefinition().isStackable()) {
                int n = 0;
                while (n < itemStack.getAmount()) {
                    GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(itemStack.getId(), 1), this.player));
                    ++n;
                }
                return;
            }
            GroundItemManager.getInstance().spawn(new GroundItem(itemStack, this.player));
        }
    }

    public final boolean addItem(ItemStack object) {
        if (object == null || !((ItemStack)object).isValid()) {
            return false;
        }
        if (!this.hasSpaceFor((ItemStack)object)) {
            object = this.player;
            ((Player)object).packetSender.sendGameMessage("Not enough space in your inventory.");
            object = this.player;
            ((Player)object).packetSender.sendSoundEffect(1878, 1, 0);
            if (this.player.botEnabled) {
                if (this.player.botCombatState != null && this.player.botCombatState.equals("loot arrows")) {
                    this.player.botCombatState = null;
                    if (this.player.botLootResumeTarget != null && !this.player.botLootResumeTarget.isDead()) {
                        CombatManager.startCombat(this.player, this.player.botLootResumeTarget);
                    } else if (this.player.currentBotTask != null) {
                        this.player.interactWithBotNpcTargets(this.player.botInteractionTargetIds);
                    }
                } else if (this.player.botCombatState != null && this.player.botCombatState.equals("loot items")) {
                    this.player.botCombatState = null;
                    this.player.botLootGroundItems.clear();
                    this.player.botLootPickupTargets.clear();
                }
            }
            return false;
        }
        int n = ((ItemStack)object).getAmount();
        if (!((ItemStack)object).getDefinition().isStackable() && n > this.container.getFreeSlots()) {
            n = this.container.getFreeSlots();
            int n2 = ((ItemStack)object).getAmount() - n;
            int n3 = 0;
            while (n3 < n2) {
                GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(((ItemStack)object).getId(), 1, ((ItemStack)object).getMetadata()), this.player));
                ++n3;
            }
        }
        ItemStack itemStack = new ItemStack(((ItemStack)object).getId(), n, ((ItemStack)object).getMetadata());
        object = this.container;
        ((ItemContainer)object).add(itemStack, -1);
        this.refresh();
        this.player.getEquipmentManager().refreshCarriedValue();
        return true;
    }

    public final boolean d(ItemStack object) {
        if (!((ItemStack)object).isValid()) {
            return false;
        }
        if (!this.hasSpaceFor((ItemStack)object)) {
            object = this.player;
            ((Player)object).packetSender.sendGameMessage("Not enough space in your inventory.");
            object = this.player;
            ((Player)object).packetSender.sendSoundEffect(1878, 1, 0);
            return false;
        }
        int n = ((ItemStack)object).getAmount();
        if (n >= this.container.getFreeSlots()) {
            n = this.container.getFreeSlots();
        }
        ItemStack itemStack = new ItemStack(((ItemStack)object).getId(), n, ((ItemStack)object).getMetadata());
        object = this.container;
        ((ItemContainer)object).add(itemStack, -1);
        this.refresh();
        this.player.getEquipmentManager().refreshCarriedValue();
        return true;
    }

    public final boolean canAddItem(ItemStack object) {
        if (object == null) {
            return false;
        }
        if (!this.hasSpaceFor((ItemStack)object)) {
            object = this.player;
            ((Player)object).packetSender.sendGameMessage("Not enough space in your inventory.");
            object = this.player;
            ((Player)object).packetSender.sendSoundEffect(1878, 1, 0);
            return false;
        }
        return true;
    }

    public final boolean hasSpaceFor(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        boolean bl = true;
        if (itemStack.getDefinition().isStackable() && this.container.containsItem(itemStack.getId())) {
            bl = false;
        }
        return !(bl ? this.container.getFreeSlots() <= 0 && !this.container.canAdd(itemStack) : !this.container.canAdd(itemStack));
    }

    public final boolean b(int n) {
        ItemStack itemStack;
        ItemStack[] itemStackArray = this.container.getItems();
        int n2 = itemStackArray.length;
        int n3 = 0;
        while (n3 < n2) {
            itemStack = itemStackArray[n3];
            if (itemStack != null && itemStack.getId() == n) {
                return true;
            }
            ++n3;
        }
        itemStackArray = this.player.getBankContainer().getItems();
        n2 = itemStackArray.length;
        n3 = 0;
        while (n3 < n2) {
            itemStack = itemStackArray[n3];
            if (itemStack != null && itemStack.getId() == n) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    public final void setItemInSlot(ItemStack object, int n) {
        if (object == null || !((ItemStack)object).isValid()) {
            return;
        }
        if (((ItemStack)object).getDefinition().isStackable() && this.container.indexOfItem(((ItemStack)object).getId()) >= 0) {
            n = this.container.indexOfItem(((ItemStack)object).getId());
            ItemStack itemStack = this.container.getItemAt(n);
            this.container.setItem(n, new ItemStack(((ItemStack)object).getId(), ((ItemStack)object).getAmount() + itemStack.getAmount(), ((ItemStack)object).getMetadata()));
            this.refresh();
            return;
        }
        if (this.container.getFirstFreeSlot() == -1) {
            object = this.player;
            ((Player)object).packetSender.sendGameMessage("Not enough space in your inventory.");
            object = this.player;
            ((Player)object).packetSender.sendSoundEffect(1878, 1, 0);
            return;
        }
        this.container.setItem(n, (ItemStack)object);
        this.refresh();
    }

    public final boolean removeItem(ItemStack itemStack) {
        if (itemStack == null || !itemStack.isValid()) {
            return false;
        }
        if (!this.containsItemStack(itemStack)) {
            return false;
        }
        this.container.remove(itemStack);
        this.refresh();
        this.player.getEquipmentManager().refreshCarriedValue();
        return true;
    }

    public final void replaceItem(ItemStack itemStack, ItemStack itemStack2) {
        this.removeItem(itemStack);
        this.addItem(itemStack2);
    }

    public final boolean removeItemAtSlot(int n) {
        if (n == -1) {
            return false;
        }
        if (this.container.getItemAt(n) == null) {
            return false;
        }
        this.container.removeFromSlot(this.container.getItemAt(n), n);
        return true;
    }

    public final boolean removeItemFromSlot(ItemStack itemStack, int n) {
        if (itemStack == null || itemStack.getId() == -1) {
            return false;
        }
        if (n == -1) {
            return false;
        }
        if (this.container.getItemAt(n) == null) {
            return false;
        }
        if (!this.container.containsItem(itemStack.getId())) {
            return false;
        }
        int n2 = this.container.removeFromSlot(itemStack, n);
        this.refresh();
        return n2 > 0;
    }

    public final void swapSlots(int n, int n2) {
        this.container.swapSlots(n, n2);
        this.refresh();
    }

    public final ItemContainer getContainer() {
        return this.container;
    }

    public final boolean containsItemStack(ItemStack itemStack) {
        return this.containsItemAmount(itemStack.getId(), itemStack.getAmount());
    }

    public final boolean containsItem(int n) {
        InventoryManager inventoryManager = this;
        return inventoryManager.container.indexOfItem(n) >= 0;
    }

    public final boolean containsItemAmount(int n, int n2) {
        if (!this.containsItem(n)) {
            return false;
        }
        InventoryManager inventoryManager = this;
        return inventoryManager.container.getItemAmount(n) >= n2;
    }

    public final int getItemAmount(int n) {
        InventoryManager inventoryManager = this.player.getInventoryManager();
        return inventoryManager.container.getItemAmount(n);
    }
}

