/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.item.consumable;

import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.item.ItemService;
import com.rs2.model.item.ItemStack;
import com.rs2.model.item.consumable.FoodDefinition;
import com.rs2.model.item.consumable.FoodHealMessageEvent;
import com.rs2.model.item.consumable.PotionDefinition;
import com.rs2.model.item.consumable.PotionEffectMode;
import com.rs2.model.item.consumable.PotionHandler;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.ByteArrayReader;
import com.rs2.util.FileUtil;
import com.rs2.util.GameUtil;

public class FoodHandler {
    Player a;

    public FoodHandler(Player player) {
        this.a = player;
    }

    public final boolean a(int n, int n2) {
        int n3;
        if (this.a.isDead()) {
            return false;
        }
        if (DuelRule.h.a(this.a)) {
            Player player = this.a;
            player.packetSender.sendGameMessage("Usage of foods have been disabled during this fight!");
            return true;
        }
        FoodDefinition foodDefinition = FoodDefinition.a(n);
        if (foodDefinition == null) {
            return false;
        }
        int n4 = n3 = foodDefinition.b() != -1 ? 600 : 1800;
        if (this.a.getSkillManager().f(n3) && this.a.getSkillManager().getCurrentLevels()[3] > 0) {
            this.a.getUpdateState().setAnimation(829);
            if (!this.a.getInventoryManager().removeItemFromSlot(new ItemStack(n, 1), n2)) {
                this.a.getInventoryManager().removeItem(new ItemStack(n, 1));
            }
            ItemService.getInstance();
            String string = ItemService.getItemName(n);
            Player player = this.a;
            player.packetSender.sendGameMessage("You eat the " + string.toLowerCase() + ".");
            player = this.a;
            player.packetSender.sendSoundEffect(317, 1, 0);
            int n5 = foodDefinition.a();
            if (n == 1971) {
                int n6;
                FoodHandler foodHandler = this;
                if (foodHandler.a.getSkillManager().getCurrentLevels()[3] >= foodHandler.a.getSkillManager().getBaseLevel(3)) {
                    n6 = 0;
                } else {
                    int n7 = GameUtil.g(9);
                    if (n7 == 0) {
                        player = foodHandler.a;
                        player.packetSender.sendGameMessage("Wow, that was an amazing kebab! You feel really invigorated.");
                        n6 = 30;
                    } else if (n7 == 1) {
                        player = foodHandler.a;
                        player.packetSender.sendGameMessage("That kebab didn't seem to do a lot.");
                        n6 = 0;
                    } else if (n7 < 5) {
                        player = foodHandler.a;
                        player.packetSender.sendGameMessage("That was a good kebab. You feel a lot better.");
                        n6 = GameUtil.g(10) + 10;
                    } else {
                        player = foodHandler.a;
                        player.packetSender.sendGameMessage("It restores some life points.");
                        n6 = n5 = foodHandler.a.getSkillManager().getBaseLevel(3) / 10;
                    }
                }
            }
            if (foodDefinition.b() != -1) {
                this.a.getInventoryManager().addItem(new ItemStack(foodDefinition.b()));
            }
            this.a.heal(n5);
            this.a.nextActionSequence();
            this.a.getAttackDelayTimer().setDelayTicks(this.a.getAttackDelayTimer().getDelayTicks() + 2);
            if (n != 10476 && n != 1971) {
                CycleEventHandler.getInstance().schedule(this.a, new FoodHealMessageEvent(this), 2);
            }
        }
        return true;
    }

    public static void a() {
        try {
            byte[] byArray = FileUtil.readBytes("./data/content/combat/potiondef.dat");
            ByteArrayReader byteArrayReader = new ByteArrayReader(byArray);
            int n = byteArrayReader.readUnsignedShort();
            int n2 = 0;
            while (n2 < n) {
                PotionDefinition potionDefinition = new PotionDefinition();
                int n3 = byteArrayReader.readUnsignedByte();
                PotionDefinition.a(potionDefinition, PotionEffectMode.values()[n3 - 1]);
                n3 = byteArrayReader.readUnsignedByte();
                PotionDefinition.a(potionDefinition, new int[n3]);
                PotionDefinition.a(potionDefinition, false);
                int n4 = 0;
                while (n4 < n3) {
                    int n5 = byteArrayReader.readUnsignedShort();
                    if (n5 == 2446 || n5 == 2448 || n5 == 5943 || n5 == 5952) {
                        PotionDefinition.a(potionDefinition, true);
                    }
                    PotionDefinition.a((PotionDefinition)potionDefinition)[n4] = n5;
                    ++n4;
                }
                n4 = PotionDefinition.a(potionDefinition)[0];
                ItemStack itemStack = new ItemStack(n4);
                Object object = itemStack.getDefinition();
                if ((object = object.getName()).contains("(")) {
                    object = object.split("\\(");
                    object = object[0];
                }
                PotionDefinition.a(potionDefinition, (String)object);
                n3 = byteArrayReader.readUnsignedByte();
                PotionDefinition.b(potionDefinition, new int[n3]);
                PotionDefinition.c(potionDefinition, new int[n3]);
                PotionDefinition.a(potionDefinition, new double[n3]);
                n4 = 0;
                while (n4 < n3) {
                    int n6 = byteArrayReader.readUnsignedByte();
                    int n7 = byteArrayReader.readUnsignedByte();
                    double d = byteArrayReader.readUnsignedShort();
                    PotionDefinition.b((PotionDefinition)potionDefinition)[n4] = n6;
                    PotionDefinition.c((PotionDefinition)potionDefinition)[n4] = n7;
                    PotionDefinition.d((PotionDefinition)potionDefinition)[n4] = d / 100.0;
                    ++n4;
                }
                PotionHandler.a()[n2] = potionDefinition;
                ++PotionHandler.b;
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
}

