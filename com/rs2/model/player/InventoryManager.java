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
    private Player a;
    private ItemContainer b = new ItemContainer(ItemContainerType.a, 28);

    public InventoryManager(Player player) {
        this.a = player;
    }

    public final void refresh() {
        ItemStack[] itemStackArray = this.b.getRawItems();
        Player player = this.a;
        player.packetSender.sendItemContainer(3214, itemStackArray);
    }

    public final void sendToInterface(int n) {
        ItemStack[] itemStackArray = this.b.getRawItems();
        Player player = this.a;
        player.packetSender.sendItemContainer(n, itemStackArray);
    }

    public final int a(ItemStack object) {
        if (!this.hasSpaceFor((ItemStack)object)) {
            object = this.a;
            ((Player)object).packetSender.sendGameMessage("Not enough space in your inventory.");
            object = this.a;
            ((Player)object).packetSender.sendSoundEffect(1878, 1, 0);
            return 0;
        }
        int n = ((ItemStack)object).getAmount();
        if (!((ItemStack)object).getDefinition().isStackable() && n > this.b.getFreeSlots()) {
            n = this.b.getFreeSlots();
        }
        ItemStack itemStack = new ItemStack(((ItemStack)object).getId(), n, ((ItemStack)object).getMetadata());
        object = this.b;
        ((ItemContainer)object).add(itemStack, -1);
        this.refresh();
        this.a.getEquipmentManager().refreshCarriedValue();
        return n;
    }

    public final void b(ItemStack itemStack) {
        if (!this.addItem(itemStack)) {
            if (!itemStack.getDefinition().isStackable()) {
                int n = 0;
                while (n < itemStack.getAmount()) {
                    GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(itemStack.getId(), 1), this.a));
                    ++n;
                }
                return;
            }
            GroundItemManager.getInstance().spawn(new GroundItem(itemStack, this.a));
        }
    }

    public final boolean addItem(ItemStack object) {
        if (object == null || !((ItemStack)object).isValid()) {
            return false;
        }
        if (!this.hasSpaceFor((ItemStack)object)) {
            object = this.a;
            ((Player)object).packetSender.sendGameMessage("Not enough space in your inventory.");
            object = this.a;
            ((Player)object).packetSender.sendSoundEffect(1878, 1, 0);
            if (this.a.botEnabled) {
                if (this.a.botCombatState != null && this.a.botCombatState.equals("loot arrows")) {
                    this.a.botCombatState = null;
                    if (this.a.botLootResumeTarget != null && !this.a.botLootResumeTarget.isDead()) {
                        CombatManager.startCombat(this.a, this.a.botLootResumeTarget);
                    } else if (this.a.currentBotTask != null) {
                        this.a.interactWithBotNpcTargets(this.a.botInteractionTargetIds);
                    }
                } else if (this.a.botCombatState != null && this.a.botCombatState.equals("loot items")) {
                    this.a.botCombatState = null;
                    this.a.botLootGroundItems.clear();
                    this.a.botLootPickupTargets.clear();
                }
            }
            return false;
        }
        int n = ((ItemStack)object).getAmount();
        if (!((ItemStack)object).getDefinition().isStackable() && n > this.b.getFreeSlots()) {
            n = this.b.getFreeSlots();
            int n2 = ((ItemStack)object).getAmount() - n;
            int n3 = 0;
            while (n3 < n2) {
                GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(((ItemStack)object).getId(), 1, ((ItemStack)object).getMetadata()), this.a));
                ++n3;
            }
        }
        ItemStack itemStack = new ItemStack(((ItemStack)object).getId(), n, ((ItemStack)object).getMetadata());
        object = this.b;
        ((ItemContainer)object).add(itemStack, -1);
        this.refresh();
        this.a.getEquipmentManager().refreshCarriedValue();
        return true;
    }

    public final boolean d(ItemStack object) {
        if (!((ItemStack)object).isValid()) {
            return false;
        }
        if (!this.hasSpaceFor((ItemStack)object)) {
            object = this.a;
            ((Player)object).packetSender.sendGameMessage("Not enough space in your inventory.");
            object = this.a;
            ((Player)object).packetSender.sendSoundEffect(1878, 1, 0);
            return false;
        }
        int n = ((ItemStack)object).getAmount();
        if (n >= this.b.getFreeSlots()) {
            n = this.b.getFreeSlots();
        }
        ItemStack itemStack = new ItemStack(((ItemStack)object).getId(), n, ((ItemStack)object).getMetadata());
        object = this.b;
        ((ItemContainer)object).add(itemStack, -1);
        this.refresh();
        this.a.getEquipmentManager().refreshCarriedValue();
        return true;
    }

    public final boolean e(ItemStack object) {
        if (object == null) {
            return false;
        }
        if (!this.hasSpaceFor((ItemStack)object)) {
            object = this.a;
            ((Player)object).packetSender.sendGameMessage("Not enough space in your inventory.");
            object = this.a;
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
        if (itemStack.getDefinition().isStackable() && this.b.containsItem(itemStack.getId())) {
            bl = false;
        }
        return !(bl ? this.b.getFreeSlots() <= 0 && !this.b.canAdd(itemStack) : !this.b.canAdd(itemStack));
    }

    public final boolean b(int n) {
        ItemStack itemStack;
        ItemStack[] itemStackArray = this.b.getItems();
        int n2 = itemStackArray.length;
        int n3 = 0;
        while (n3 < n2) {
            itemStack = itemStackArray[n3];
            if (itemStack != null && itemStack.getId() == n) {
                return true;
            }
            ++n3;
        }
        itemStackArray = this.a.getBankContainer().getItems();
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

    public final void a(ItemStack object, int n) {
        if (object == null || !((ItemStack)object).isValid()) {
            return;
        }
        if (((ItemStack)object).getDefinition().isStackable() && this.b.indexOfItem(((ItemStack)object).getId()) >= 0) {
            n = this.b.indexOfItem(((ItemStack)object).getId());
            ItemStack itemStack = this.b.getItemAt(n);
            this.b.setItem(n, new ItemStack(((ItemStack)object).getId(), ((ItemStack)object).getAmount() + itemStack.getAmount(), ((ItemStack)object).getMetadata()));
            this.refresh();
            return;
        }
        if (this.b.getFirstFreeSlot() == -1) {
            object = this.a;
            ((Player)object).packetSender.sendGameMessage("Not enough space in your inventory.");
            object = this.a;
            ((Player)object).packetSender.sendSoundEffect(1878, 1, 0);
            return;
        }
        this.b.setItem(n, (ItemStack)object);
        this.refresh();
    }

    public final boolean removeItem(ItemStack itemStack) {
        if (itemStack == null || !itemStack.isValid()) {
            return false;
        }
        if (!this.containsItemStack(itemStack)) {
            return false;
        }
        this.b.remove(itemStack);
        this.refresh();
        this.a.getEquipmentManager().refreshCarriedValue();
        return true;
    }

    public final void a(ItemStack itemStack, ItemStack itemStack2) {
        this.removeItem(itemStack);
        this.addItem(itemStack2);
    }

    public final boolean c(int n) {
        if (n == -1) {
            return false;
        }
        if (this.b.getItemAt(n) == null) {
            return false;
        }
        this.b.removeFromSlot(this.b.getItemAt(n), n);
        return true;
    }

    public final boolean removeItemFromSlot(ItemStack itemStack, int n) {
        if (itemStack == null || itemStack.getId() == -1) {
            return false;
        }
        if (n == -1) {
            return false;
        }
        if (this.b.getItemAt(n) == null) {
            return false;
        }
        if (!this.b.containsItem(itemStack.getId())) {
            return false;
        }
        int n2 = this.b.removeFromSlot(itemStack, n);
        this.refresh();
        return n2 > 0;
    }

    public final void swapSlots(int n, int n2) {
        this.b.swapSlots(n, n2);
        this.refresh();
    }

    public final ItemContainer getContainer() {
        return this.b;
    }

    public final boolean containsItemStack(ItemStack itemStack) {
        return this.containsItemAmount(itemStack.getId(), itemStack.getAmount());
    }

    public final boolean containsItem(int n) {
        InventoryManager inventoryManager = this;
        return inventoryManager.b.indexOfItem(n) >= 0;
    }

    public final boolean containsItemAmount(int n, int n2) {
        if (!this.containsItem(n)) {
            return false;
        }
        InventoryManager inventoryManager = this;
        return inventoryManager.b.getItemAmount(n) >= n2;
    }

    public final int getItemAmount(int n) {
        InventoryManager inventoryManager = this.a.getInventoryManager();
        return inventoryManager.b.getItemAmount(n);
    }
}

