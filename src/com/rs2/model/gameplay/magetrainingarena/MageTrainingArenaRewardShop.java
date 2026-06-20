/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.magetrainingarena;

import com.rs2.model.gameplay.magetrainingarena.MageTrainingArenaRewardDefinition;
import com.rs2.model.item.ItemService;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.net.packet.PacketSender;

public final class MageTrainingArenaRewardShop {
    private static MageTrainingArenaRewardDefinition[] a;

    static {
        MageTrainingArenaRewardDefinition[] mageTrainingArenaRewardDefinitionArray = new MageTrainingArenaRewardDefinition[24];
        mageTrainingArenaRewardDefinitionArray[0] = new MageTrainingArenaRewardDefinition(6908, new int[]{30, 30, 300, 30});
        mageTrainingArenaRewardDefinitionArray[1] = new MageTrainingArenaRewardDefinition(6910, new int[]{60, 60, 600, 60});
        mageTrainingArenaRewardDefinitionArray[2] = new MageTrainingArenaRewardDefinition(6912, new int[]{150, 200, 1500, 150});
        mageTrainingArenaRewardDefinitionArray[3] = new MageTrainingArenaRewardDefinition(6914, new int[]{240, 240, 2400, 240});
        mageTrainingArenaRewardDefinitionArray[4] = new MageTrainingArenaRewardDefinition(6916, new int[]{400, 450, 4000, 400});
        mageTrainingArenaRewardDefinitionArray[5] = new MageTrainingArenaRewardDefinition(6918, new int[]{350, 400, 3000, 350});
        mageTrainingArenaRewardDefinitionArray[6] = new MageTrainingArenaRewardDefinition(6920, new int[]{120, 120, 1200, 120});
        mageTrainingArenaRewardDefinitionArray[7] = new MageTrainingArenaRewardDefinition(6922, new int[]{175, 225, 1500, 175});
        mageTrainingArenaRewardDefinitionArray[8] = new MageTrainingArenaRewardDefinition(6924, new int[]{450, 500, 5000, 450});
        mageTrainingArenaRewardDefinitionArray[9] = new MageTrainingArenaRewardDefinition(6889, new int[]{500, 550, 6000, 500});
        mageTrainingArenaRewardDefinitionArray[10] = new MageTrainingArenaRewardDefinition(6926, new int[]{200, 300, 2000, 200});
        mageTrainingArenaRewardDefinitionArray[11] = new MageTrainingArenaRewardDefinition(4695, new int[]{1, 1, 15, 1});
        mageTrainingArenaRewardDefinitionArray[12] = new MageTrainingArenaRewardDefinition(4696, new int[]{1, 1, 15, 1});
        mageTrainingArenaRewardDefinitionArray[13] = new MageTrainingArenaRewardDefinition(4698, new int[]{1, 1, 15, 1});
        mageTrainingArenaRewardDefinitionArray[14] = new MageTrainingArenaRewardDefinition(4697, new int[]{1, 1, 15, 1});
        mageTrainingArenaRewardDefinitionArray[15] = new MageTrainingArenaRewardDefinition(4696, new int[]{1, 1, 15, 1});
        mageTrainingArenaRewardDefinitionArray[16] = new MageTrainingArenaRewardDefinition(4699, new int[]{1, 1, 15, 1});
        int[] nArray = new int[4];
        nArray[2] = 5;
        mageTrainingArenaRewardDefinitionArray[17] = new MageTrainingArenaRewardDefinition(564, nArray);
        int[] nArray2 = new int[4];
        nArray2[2] = 5;
        mageTrainingArenaRewardDefinitionArray[18] = new MageTrainingArenaRewardDefinition(562, nArray2);
        int[] nArray3 = new int[4];
        nArray3[1] = 1;
        nArray3[3] = 1;
        mageTrainingArenaRewardDefinitionArray[19] = new MageTrainingArenaRewardDefinition(561, nArray3);
        mageTrainingArenaRewardDefinitionArray[20] = new MageTrainingArenaRewardDefinition(560, new int[]{2, 1, 20, 1});
        int[] nArray4 = new int[4];
        nArray4[0] = 2;
        mageTrainingArenaRewardDefinitionArray[21] = new MageTrainingArenaRewardDefinition(563, nArray4);
        mageTrainingArenaRewardDefinitionArray[22] = new MageTrainingArenaRewardDefinition(566, new int[]{2, 2, 25, 2});
        mageTrainingArenaRewardDefinitionArray[23] = new MageTrainingArenaRewardDefinition(565, new int[]{2, 2, 25, 2});
        a = mageTrainingArenaRewardDefinitionArray;
    }

    public static void a(Player player) {
        Object object;
        MageTrainingArenaRewardShop.b(player);
        ItemStack[] itemStackArray = new ItemStack[a.length];
        int n = 0;
        while (n < a.length) {
            object = a[n];
            itemStackArray[n] = new ItemStack(((MageTrainingArenaRewardDefinition)object).a, 100);
            ++n;
        }
        object = player;
        ((Player)object).packetSender.sendItemContainer(15948, itemStackArray);
        object = player;
        ((Player)object).packetSender.showInterface(15944);
    }

    private static void b(Player player) {
        Player player2 = player;
        player2.packetSender.sendInterfaceText("" + player.getTelekineticTheatreController().a, 15955);
        player2 = player;
        player2.packetSender.sendInterfaceText("" + player.getEnchantmentChamberController().a, 15956);
        player2 = player;
        player2.packetSender.sendInterfaceText("" + player.getAlchemistPlaygroundController().a, 15957);
        player2 = player;
        player2.packetSender.sendInterfaceText("" + player.getCreatureGraveyardController().a, 15958);
    }

    public static void a(Player player, int n) {
        Object object = player;
        PacketSender packetSender = ((Player)object).packetSender;
        ItemService.getInstance();
        object = a[n];
        MageTrainingArenaRewardDefinition mageTrainingArenaRewardDefinition = a[n];
        object = mageTrainingArenaRewardDefinition;
        object = a[n];
        packetSender.sendGameMessage(String.valueOf(ItemService.getItemName(((MageTrainingArenaRewardDefinition)object).a)) + " costs " + mageTrainingArenaRewardDefinition.b[0] + " Telekinetic, " + ((MageTrainingArenaRewardDefinition)object).b[1] + " Alchemist,");
        Player player2 = player;
        object = player2;
        object = a[n];
        object = a[n];
        player2.packetSender.sendGameMessage(String.valueOf(((MageTrainingArenaRewardDefinition)object).b[2]) + " Enchantment and " + ((MageTrainingArenaRewardDefinition)object).b[3] + " Graveyard Pizazz Points.");
    }

    public static void b(Player player, int n) {
        Object object;
        MageTrainingArenaRewardDefinition mageTrainingArenaRewardDefinition;
        block15: {
            block14: {
                mageTrainingArenaRewardDefinition = a[n];
                object = mageTrainingArenaRewardDefinition;
                if (mageTrainingArenaRewardDefinition.a == 6926 && player.df) {
                    object = player;
                    ((Player)object).packetSender.sendGameMessage("You have already bought this item!");
                    return;
                }
                object = mageTrainingArenaRewardDefinition;
                if (((MageTrainingArenaRewardDefinition)object).a == 6910 && !player.getInventoryManager().containsItem(6908)) {
                    object = player;
                    PacketSender packetSender = ((Player)object).packetSender;
                    StringBuilder stringBuilder = new StringBuilder("You need ");
                    ItemService.getInstance();
                    packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(6908)).append(" to buy this item!").toString());
                    return;
                }
                object = mageTrainingArenaRewardDefinition;
                if (((MageTrainingArenaRewardDefinition)object).a == 6912 && !player.getInventoryManager().containsItem(6910)) {
                    object = player;
                    PacketSender packetSender = ((Player)object).packetSender;
                    StringBuilder stringBuilder = new StringBuilder("You need ");
                    ItemService.getInstance();
                    packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(6910)).append(" to buy this item!").toString());
                    return;
                }
                object = mageTrainingArenaRewardDefinition;
                if (((MageTrainingArenaRewardDefinition)object).a == 6914 && !player.getInventoryManager().containsItem(6912)) {
                    object = player;
                    PacketSender packetSender = ((Player)object).packetSender;
                    StringBuilder stringBuilder = new StringBuilder("You need ");
                    ItemService.getInstance();
                    packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(6912)).append(" to buy this item!").toString());
                    return;
                }
                object = mageTrainingArenaRewardDefinition;
                if (((MageTrainingArenaRewardDefinition)object).b[0] > player.getTelekineticTheatreController().a) break block14;
                object = mageTrainingArenaRewardDefinition;
                if (((MageTrainingArenaRewardDefinition)object).b[2] > player.getEnchantmentChamberController().a) break block14;
                object = mageTrainingArenaRewardDefinition;
                if (((MageTrainingArenaRewardDefinition)object).b[1] > player.getAlchemistPlaygroundController().a) break block14;
                object = mageTrainingArenaRewardDefinition;
                if (((MageTrainingArenaRewardDefinition)object).b[3] <= player.getCreatureGraveyardController().a) break block15;
            }
            object = player;
            ((Player)object).packetSender.sendGameMessage("You don't have enough points to buy this!");
            return;
        }
        object = mageTrainingArenaRewardDefinition;
        if (((MageTrainingArenaRewardDefinition)object).a == 6926) {
            player.df = true;
            object = player;
            ((Player)object).packetSender.sendGameMessage("You have unlocked 'bones to peaches' spell.");
            MageTrainingArenaRewardShop.a(player, mageTrainingArenaRewardDefinition);
            return;
        }
        object = mageTrainingArenaRewardDefinition;
        if (player.getInventoryManager().e(new ItemStack(((MageTrainingArenaRewardDefinition)object).a, 1))) {
            object = mageTrainingArenaRewardDefinition;
            if (((MageTrainingArenaRewardDefinition)object).a == 6910) {
                player.getInventoryManager().removeItem(new ItemStack(6908, 1));
            } else {
                object = mageTrainingArenaRewardDefinition;
                if (((MageTrainingArenaRewardDefinition)object).a == 6912) {
                    player.getInventoryManager().removeItem(new ItemStack(6910, 1));
                } else {
                    object = mageTrainingArenaRewardDefinition;
                    if (((MageTrainingArenaRewardDefinition)object).a == 6914) {
                        player.getInventoryManager().removeItem(new ItemStack(6912, 1));
                    }
                }
            }
            object = mageTrainingArenaRewardDefinition;
            player.getInventoryManager().addItem(new ItemStack(((MageTrainingArenaRewardDefinition)object).a, 1));
            MageTrainingArenaRewardShop.a(player, mageTrainingArenaRewardDefinition);
        }
    }

    private static void a(Player player, MageTrainingArenaRewardDefinition mageTrainingArenaRewardDefinition) {
        MageTrainingArenaRewardDefinition mageTrainingArenaRewardDefinition2 = mageTrainingArenaRewardDefinition;
        player.getTelekineticTheatreController().a -= mageTrainingArenaRewardDefinition2.b[0];
        mageTrainingArenaRewardDefinition2 = mageTrainingArenaRewardDefinition;
        player.getEnchantmentChamberController().a -= mageTrainingArenaRewardDefinition2.b[2];
        mageTrainingArenaRewardDefinition2 = mageTrainingArenaRewardDefinition;
        player.getAlchemistPlaygroundController().a -= mageTrainingArenaRewardDefinition2.b[1];
        mageTrainingArenaRewardDefinition2 = mageTrainingArenaRewardDefinition;
        player.getCreatureGraveyardController().a -= mageTrainingArenaRewardDefinition2.b[3];
        MageTrainingArenaRewardShop.b(player);
    }
}

