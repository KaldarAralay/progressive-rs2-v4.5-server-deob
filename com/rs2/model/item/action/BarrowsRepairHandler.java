/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.item.action;

import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.InventoryManager;
import com.rs2.model.player.Player;

public final class BarrowsRepairHandler {
    private static int[] a = new int[]{519};
    private int b;
    private int c;
    private static BarrowsRepairHandler[][] d = new BarrowsRepairHandler[][]{{new BarrowsRepairHandler(4708, 4856), new BarrowsRepairHandler(4710, 4862), new BarrowsRepairHandler(4712, 4868), new BarrowsRepairHandler(4714, 4874)}, {new BarrowsRepairHandler(4716, 4880), new BarrowsRepairHandler(4718, 4886), new BarrowsRepairHandler(4720, 4892), new BarrowsRepairHandler(4722, 4898)}, {new BarrowsRepairHandler(4724, 4904), new BarrowsRepairHandler(4726, 4910), new BarrowsRepairHandler(4728, 4916), new BarrowsRepairHandler(4730, 4922)}, {new BarrowsRepairHandler(4732, 4928), new BarrowsRepairHandler(4734, 4934), new BarrowsRepairHandler(4736, 4940), new BarrowsRepairHandler(4738, 4946)}, {new BarrowsRepairHandler(4745, 4952), new BarrowsRepairHandler(4747, 4958), new BarrowsRepairHandler(4749, 4964), new BarrowsRepairHandler(4751, 4970)}, {new BarrowsRepairHandler(4753, 4976), new BarrowsRepairHandler(4755, 4982), new BarrowsRepairHandler(4757, 4988), new BarrowsRepairHandler(4759, 4994)}};

    public static boolean a(Player player, int n, ItemStack itemStack) {
        int n2 = -1;
        int[] nArray = a;
        int n3 = a.length;
        int n4 = 0;
        while (n4 < n3) {
            int n5 = nArray[n4];
            if (n == n5) {
                n2 = n5;
                break;
            }
            ++n4;
        }
        if (n2 != -1 && BarrowsRepairHandler.a(itemStack) != -1) {
            player.K = itemStack;
            player.O = n2;
            DialogueManager.startDialogue(player, 10089);
            return true;
        }
        return false;
    }

    public static boolean a(Player object, ItemStack itemStack) {
        int n = BarrowsRepairHandler.a(itemStack);
        if (!((Player)object).getInventoryManager().containsItemStack(new ItemStack(995, n)) && n > 0) {
            ((Player)object).packetSender.sendGameMessage("You don't have enough coins to fix that.");
            return false;
        }
        ((Player)object).getInventoryManager().removeItem(new ItemStack(995, n));
        ((Player)object).getInventoryManager().removeItemFromSlot(itemStack, ((Player)object).getSelectedItemSlot());
        InventoryManager inventoryManager = ((Player)object).getInventoryManager();
        object = itemStack;
        object = BarrowsRepairHandler.b((ItemStack)object);
        inventoryManager.addItem(new ItemStack(object == null ? -1 : ((BarrowsRepairHandler)object).b, 1));
        return true;
    }

    private BarrowsRepairHandler(int n, int n2) {
        this.b = n;
        this.c = n2;
    }

    public final int a() {
        return this.c + 4;
    }

    public static int a(ItemStack itemStack) {
        int n = itemStack.getDefinition().getEquipmentSlot();
        BarrowsRepairHandler barrowsRepairHandler = BarrowsRepairHandler.b(itemStack);
        if (barrowsRepairHandler == null) {
            return -1;
        }
        int n2 = itemStack.getId() - barrowsRepairHandler.c;
        double d = itemStack.getMetadata() < 0 ? 0 : 4500 - itemStack.getMetadata();
        double d2 = 1.0;
        double d3 = d / 4500.0;
        if (n == 0) {
            d2 = 15000.0;
        } else if (n == 3) {
            d2 = 25000.0;
        } else if (n == 4) {
            d2 = 22500.0;
        } else if (n == 7) {
            d2 = 20000.0;
        }
        return (int)(d2 * (double)n2 + d2 * d3);
    }

    public static BarrowsRepairHandler b(ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }
        int n = itemStack.getId();
        int n2 = itemStack.getDefinition().getEquipmentSlot();
        if (n2 == 0) {
            return BarrowsRepairHandler.a(n, 0);
        }
        if (n2 == 3) {
            return BarrowsRepairHandler.a(n, 1);
        }
        if (n2 == 4) {
            return BarrowsRepairHandler.a(n, 2);
        }
        if (n2 == 7) {
            return BarrowsRepairHandler.a(n, 3);
        }
        return null;
    }

    private static BarrowsRepairHandler a(int n, int n2) {
        int n3 = 0;
        while (n3 < d.length) {
            BarrowsRepairHandler barrowsRepairHandler = d[n3][n2];
            if (n >= barrowsRepairHandler.c) {
                barrowsRepairHandler = d[n3][n2];
                if (n <= barrowsRepairHandler.c + 4) {
                    return d[n3][n2];
                }
            }
            ++n3;
        }
        return null;
    }
}

