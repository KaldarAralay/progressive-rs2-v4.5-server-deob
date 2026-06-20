/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill;

import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.GatheringToolComparator;
import com.rs2.model.skill.GatheringToolDefinition;
import com.rs2.model.skill.ItemCombinationRecipe;
import com.rs2.model.skill.SkillManager;
import com.rs2.util.RectangularArea;
import java.util.ArrayList;
import java.util.Collections;

public class ItemCombinationHandler {
    private Player a;
    private RectangularArea b = new RectangularArea(2638, 9736, 2655, 9745, 0);

    public ItemCombinationHandler(Player player) {
        this.a = player;
    }

    public final boolean a(ItemStack object, ItemStack object2) {
        int n;
        int n2;
        ItemStack[] itemStackArray;
        if ((object = ItemCombinationRecipe.a(object.getId(), ((ItemStack)object2).getId())) == null) {
            return false;
        }
        if (ItemCombinationRecipe.a((ItemCombinationRecipe)((Object)object)) != null && this.a.getSkillManager().getCurrentLevels()[ItemCombinationRecipe.a((ItemCombinationRecipe)((Object)object))[0]] < ItemCombinationRecipe.a((ItemCombinationRecipe)((Object)object))[1]) {
            object2 = this.a;
            ((Player)object2).packetSender.sendGameMessage("Your " + SkillManager.a[ItemCombinationRecipe.a((ItemCombinationRecipe)((Object)object))[0]].toLowerCase() + " level is not high enough to do this.");
            return true;
        }
        if (ItemCombinationRecipe.b((ItemCombinationRecipe)((Object)object)) != null) {
            itemStackArray = ItemCombinationRecipe.b((ItemCombinationRecipe)((Object)object));
            n2 = itemStackArray.length;
            n = 0;
            while (n < n2) {
                object2 = itemStackArray[n];
                if (this.a.getInventoryManager().getItemAmount(((ItemStack)object2).getId()) < ((ItemStack)object2).getAmount()) {
                    return true;
                }
                ++n;
            }
            itemStackArray = ItemCombinationRecipe.b((ItemCombinationRecipe)((Object)object));
            n2 = itemStackArray.length;
            n = 0;
            while (n < n2) {
                object2 = itemStackArray[n];
                this.a.getInventoryManager().removeItem((ItemStack)object2);
                ++n;
            }
        }
        if (ItemCombinationRecipe.c((ItemCombinationRecipe)((Object)object)) != null) {
            object2 = this.a;
            ((Player)object2).packetSender.sendGameMessage(ItemCombinationRecipe.c((ItemCombinationRecipe)((Object)object)));
        }
        if (ItemCombinationRecipe.d((ItemCombinationRecipe)((Object)object)) != null) {
            if (ItemCombinationRecipe.d((ItemCombinationRecipe)((Object)object)).length == 2 && ItemCombinationRecipe.b((ItemCombinationRecipe)((Object)object)).length == 2) {
                this.a.getInventoryManager().addItem(ItemCombinationRecipe.d((ItemCombinationRecipe)((Object)object))[0]);
                this.a.getInventoryManager().addItem(ItemCombinationRecipe.d((ItemCombinationRecipe)((Object)object))[1]);
            } else {
                itemStackArray = ItemCombinationRecipe.d((ItemCombinationRecipe)((Object)object));
                n2 = itemStackArray.length;
                n = 0;
                while (n < n2) {
                    object2 = itemStackArray[n];
                    this.a.getInventoryManager().addItem((ItemStack)object2);
                    ++n;
                }
                if (this.a.eI() > 0 && this.b.containsExclusive(this.a.getPosition())) {
                    object2 = this.a;
                    ((Player)object2).packetSender.sendGameMessage("The light lets you see further into the room.");
                    this.a.moveTo(new Position(this.a.getPosition().getX(), this.a.getPosition().getY() + 23, 0));
                }
            }
        }
        if (ItemCombinationRecipe.e((ItemCombinationRecipe)((Object)object)) > 0) {
            this.a.getUpdateState().setAnimation(ItemCombinationRecipe.e((ItemCombinationRecipe)((Object)object)));
        }
        if (ItemCombinationRecipe.a((ItemCombinationRecipe)((Object)object)) != null) {
            this.a.getSkillManager().addExperience(ItemCombinationRecipe.a((ItemCombinationRecipe)((Object)object))[0], ItemCombinationRecipe.f((ItemCombinationRecipe)((Object)object)));
        }
        return true;
    }

    public static boolean a(int n) {
        GatheringToolDefinition[] gatheringToolDefinitionArray = GatheringToolDefinition.values();
        int n2 = gatheringToolDefinitionArray.length;
        int n3 = 0;
        while (n3 < n2) {
            GatheringToolDefinition gatheringToolDefinition = gatheringToolDefinitionArray[n3];
            if (gatheringToolDefinition.a() == n) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    public static GatheringToolDefinition a(Player player, int n) {
        GatheringToolDefinition[] gatheringToolDefinitionArray = GatheringToolDefinition.values();
        int n2 = gatheringToolDefinitionArray.length;
        int n3 = 0;
        while (n3 < n2) {
            GatheringToolDefinition gatheringToolDefinition = gatheringToolDefinitionArray[n3];
            if (gatheringToolDefinition.i() == n && player.getSkillManager().getCurrentLevels()[n] >= gatheringToolDefinition.b() && (player.getEquipmentManager().getItemIdAtSlot(3) == gatheringToolDefinition.a() || player.getInventoryManager().containsItem(gatheringToolDefinition.a()))) {
                return gatheringToolDefinition;
            }
            ++n3;
        }
        return null;
    }

    public static GatheringToolDefinition b(Player player, int n) {
        GatheringToolDefinition[] gatheringToolDefinitionArray = GatheringToolDefinition.values();
        int n2 = gatheringToolDefinitionArray.length;
        int n3 = 0;
        while (n3 < n2) {
            GatheringToolDefinition gatheringToolDefinition = gatheringToolDefinitionArray[n3];
            if (gatheringToolDefinition.i() == n && player.getSkillManager().getCurrentLevels()[n] >= gatheringToolDefinition.b() && player.aq(gatheringToolDefinition.a())) {
                return gatheringToolDefinition;
            }
            ++n3;
        }
        return null;
    }

    public static ArrayList b(int n) {
        ArrayList<GatheringToolDefinition> arrayList = new ArrayList<GatheringToolDefinition>();
        GatheringToolDefinition[] gatheringToolDefinitionArray = GatheringToolDefinition.values();
        int n2 = gatheringToolDefinitionArray.length;
        int n3 = 0;
        while (n3 < n2) {
            GatheringToolDefinition gatheringToolDefinition = gatheringToolDefinitionArray[n3];
            if (gatheringToolDefinition.i() == n) {
                arrayList.add(gatheringToolDefinition);
            }
            ++n3;
        }
        Collections.sort(arrayList, new GatheringToolComparator(n));
        return arrayList;
    }

    public static GatheringToolDefinition c(Player object, int n) {
        if ((object = ItemCombinationHandler.b(object, n)) != null) {
            return object;
        }
        object = ItemCombinationHandler.b(n);
        return (GatheringToolDefinition)((Object)((ArrayList)object).get(0));
    }

    public static GatheringToolDefinition c(int n) {
        GatheringToolDefinition[] gatheringToolDefinitionArray = GatheringToolDefinition.values();
        int n2 = gatheringToolDefinitionArray.length;
        int n3 = 0;
        while (n3 < n2) {
            GatheringToolDefinition gatheringToolDefinition = gatheringToolDefinitionArray[n3];
            if (gatheringToolDefinition.h() == n) {
                return gatheringToolDefinition;
            }
            ++n3;
        }
        return null;
    }

    public static boolean a(Player player, int n, int n2) {
        GatheringToolDefinition[] gatheringToolDefinitionArray = GatheringToolDefinition.values();
        int n3 = gatheringToolDefinitionArray.length;
        int n4 = 0;
        while (n4 < n3) {
            block3: {
                GatheringToolDefinition gatheringToolDefinition;
                block4: {
                    gatheringToolDefinition = gatheringToolDefinitionArray[n4];
                    if (gatheringToolDefinition.c() != n && gatheringToolDefinition.c() != n2) break block3;
                    GatheringToolDefinition gatheringToolDefinition2 = gatheringToolDefinition;
                    if (n == 0) break block4;
                    gatheringToolDefinition2 = gatheringToolDefinition;
                    if (n2 != 0) break block3;
                }
                player.getInventoryManager().removeItem(new ItemStack(n, 1));
                player.getInventoryManager().removeItem(new ItemStack(n2, 1));
                player.getInventoryManager().addItem(new ItemStack(gatheringToolDefinition.a(), 1));
                return true;
            }
            ++n4;
        }
        return false;
    }

    public static void d(Player player, int n) {
        GatheringToolDefinition gatheringToolDefinition = ItemCombinationHandler.a(player, n);
        if (player.getEquipmentManager().getItemIdAtSlot(3) == gatheringToolDefinition.a()) {
            player.getEquipmentManager().a(gatheringToolDefinition.h(), 3);
            return;
        }
        if (player.getInventoryManager().containsItemAmount(gatheringToolDefinition.a(), 1)) {
            player.getInventoryManager().removeItem(new ItemStack(gatheringToolDefinition.a(), 1));
            player.getInventoryManager().addItem(new ItemStack(gatheringToolDefinition.h(), 1));
        }
    }

    public static boolean e(Player player, int n) {
        GatheringToolDefinition[] gatheringToolDefinitionArray = GatheringToolDefinition.values();
        int n2 = gatheringToolDefinitionArray.length;
        int n3 = 0;
        while (n3 < n2) {
            GatheringToolDefinition gatheringToolDefinition = gatheringToolDefinitionArray[n3];
            if (gatheringToolDefinition.h() == n) {
                if (!player.getInventoryManager().containsItemStack(new ItemStack(995, gatheringToolDefinition.j()))) {
                    player.packetSender.sendGameMessage("You don't have enough coins to fix that.");
                    return false;
                }
                player.getInventoryManager().removeItem(new ItemStack(n, 1));
                player.getInventoryManager().removeItem(new ItemStack(995, gatheringToolDefinition.j()));
                player.getInventoryManager().addItem(new ItemStack(gatheringToolDefinition.a(), 1));
                return true;
            }
            ++n3;
        }
        return false;
    }
}

