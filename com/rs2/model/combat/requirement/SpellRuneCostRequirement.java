/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat.requirement;

import com.rs2.model.Entity;
import com.rs2.model.combat.requirement.InventoryItemRequirement;
import com.rs2.model.gameplay.magetrainingarena.AlchemistPlaygroundController;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.SpellDefinition;
import java.util.ArrayList;

public abstract class SpellRuneCostRequirement
extends InventoryItemRequirement {
    private SpellDefinition spell;
    private ArrayList runeCosts = new ArrayList();
    private ArrayList combinationRuneCosts = new ArrayList();
    private ArrayList combinationRuneCredits = new ArrayList();

    public SpellRuneCostRequirement(SpellDefinition spellDefinition) {
        super(1, 1);
        this.spell = spellDefinition;
    }

    @Override
    public final void consume(Entity entity) {
        if (!entity.isPlayer()) {
            return;
        }
        super.consume(entity);
    }

    @Override
    final boolean isSatisfiedBy(Entity entity) {
        if (!entity.isPlayer()) {
            return true;
        }
        ArrayList<ItemStack> arrayList = (Player)entity;
        ItemStack[] itemStackArray = (ItemStack[])this.spell.getRuneCosts().clone();
        if ((this.spell == SpellDefinition.LOW_LEVEL_ALCHEMY || this.spell == SpellDefinition.HIGH_LEVEL_ALCHEMY) && ((Player)((Object)arrayList)).getAlchemistPlaygroundController().isInsidePlayground() && AlchemistPlaygroundController.currentFreeAlchemyItemId == ((Player)((Object)arrayList)).N) {
            arrayList = new ArrayList<ItemStack>();
            arrayList.add(new ItemStack(561, 0));
            this.runeCosts = arrayList;
        } else {
            this.runeCosts = this.buildRuneCosts((Player)((Object)arrayList), itemStackArray);
        }
        arrayList = this;
        super.setRequiredItems(((SpellRuneCostRequirement)((Object)arrayList)).runeCosts);
        return super.isSatisfiedBy(entity);
    }

    /*
     * Unable to fully structure code
     */
    private ArrayList buildRuneCosts(Player var1_1, ItemStack[] var2_2) {
        var3_5 = new ArrayList<ItemStack>();
        var4_6 = new ArrayList<ItemStack>();
        var5_7 = new ArrayList<Integer>();
        var6_8 = new ArrayList<Integer>();
        var9_9 = var2_2;
        var8_10 = ((ItemStack[])var2_2).length;
        var7_12 = 0;
        while (var7_12 < var8_10) {
            block33: {
                block32: {
                    var2_2 = var9_9[var7_12];
                    var10_15 = var2_2.getId();
                    var2_3 = var2_2.getAmount();
                    var12_17 = var10_15;
                    var11_16 = var1_1;
                    if (var11_16.isPlayer()) break block32;
                    v0 = true;
                    break block33;
                }
                if ((var11_16 = var11_16.getEquipmentManager().getContainer().getItemAt(3)) == null || !(var11_16 = ItemDefinition.forId(var11_16.getId()).getName().toLowerCase()).contains("staff")) ** GOTO lbl-1000
                switch (var12_17) {
                    case 554: {
                        if (var11_16.contains("fire") || var11_16.contains("lava") || var11_16.contains("steam")) {
                            v0 = true;
                            break;
                        }
                        ** GOTO lbl37
                    }
                    case 555: {
                        if (var11_16.contains("water") || var11_16.contains("mud") || var11_16.contains("steam")) {
                            v0 = true;
                            break;
                        }
                        ** GOTO lbl37
                    }
                    case 556: {
                        v0 = var11_16.contains("air");
                        break;
                    }
                    case 557: {
                        if (var11_16.contains("earth") || var11_16.contains("lava") || var11_16.contains("mud")) {
                            v0 = true;
                            break;
                        }
                    }
lbl37:
                    // 5 sources

                    default: lbl-1000:
                    // 2 sources

                    {
                        v0 = false;
                    }
                }
            }
            if (!v0) {
                var11_16 = var1_1.getInventoryManager().getContainer().findFlatItem(var10_15);
                var12_17 = 0;
                if (var11_16 != null) {
                    var12_17 = var11_16.getAmount();
                }
                if (var12_17 >= var2_3) {
                    var3_5.add(new ItemStack(var10_15, var2_3));
                } else if (this.collectCombinationRuneCosts(var1_1, var10_15, var2_3)) {
                    if (var3_5.size() != 0) {
                        var2_3 = 0;
                        var10_15 = 0;
                        while (var10_15 < this.combinationRuneCosts.size()) {
                            var11_16 = (ItemStack)this.combinationRuneCosts.get(var10_15);
                            for (ItemStack var12_18 : var3_5) {
                                if (var12_18.getId() != var11_16.getId() || var12_18.getAmount() >= var11_16.getAmount()) continue;
                                var12_18.setAmount(var11_16.getAmount());
                                var2_3 = 1;
                            }
                            if (var2_3 == 0) {
                                var6_8.add(var10_15);
                            }
                            ++var10_15;
                        }
                        var11_16 = var6_8.iterator();
                        while (var11_16.hasNext()) {
                            var10_15 = (Integer)var11_16.next();
                            var3_5.add((ItemStack)this.combinationRuneCosts.get(var10_15));
                        }
                        var6_8.clear();
                    } else {
                        var3_5.addAll(this.combinationRuneCosts);
                    }
                    if (var4_6.size() != 0) {
                        var2_3 = 0;
                        var10_15 = 0;
                        while (var10_15 < this.combinationRuneCredits.size()) {
                            var11_16 = (ItemStack)this.combinationRuneCredits.get(var10_15);
                            for (ItemStack var12_19 : var4_6) {
                                if (var12_19.getId() != var11_16.getId() || var12_19.getAmount() >= var12_19.getAmount()) continue;
                                var12_19.setAmount(var12_19.getAmount());
                                var2_3 = 1;
                            }
                            if (var2_3 == 0) {
                                var6_8.add(var10_15);
                            }
                            ++var10_15;
                        }
                        var11_16 = var6_8.iterator();
                        while (var11_16.hasNext()) {
                            var10_15 = (Integer)var11_16.next();
                            var4_6.add((ItemStack)this.combinationRuneCredits.get(var10_15));
                        }
                        var6_8.clear();
                    } else {
                        var4_6.addAll(this.combinationRuneCredits);
                    }
                } else {
                    var3_5.add(new ItemStack(var10_15, var2_3));
                }
            }
            ++var7_12;
        }
        var2_4 = 0;
        while (var2_4 < var3_5.size()) {
            var7_13 = (ItemStack)var3_5.get(var2_4);
            for (ItemStack var8_11 : var4_6) {
                if (var7_13.getId() != var8_11.getId()) continue;
                if (var7_13.getAmount() <= var8_11.getAmount()) {
                    var5_7.add(var2_4);
                    continue;
                }
                var7_13.setAmount(var7_13.getAmount() - var8_11.getAmount());
            }
            ++var2_4;
        }
        var7_14 = var5_7.iterator();
        while (var7_14.hasNext()) {
            var2_4 = (Integer)var7_14.next();
            var3_5.remove(var2_4);
        }
        return var3_5;
    }

    private static int getPairedCombinationRuneId(int n, int n2) {
        switch (n2) {
            case 554: {
                if (n == 4697) {
                    return 556;
                }
                if (n == 4694) {
                    return 555;
                }
                if (n != 4699) break;
                return 557;
            }
            case 555: {
                if (n == 4695) {
                    return 556;
                }
                if (n == 4694) {
                    return 554;
                }
                if (n != 4698) break;
                return 557;
            }
            case 556: {
                if (n == 4695) {
                    return 555;
                }
                if (n == 4696) {
                    return 557;
                }
                if (n != 4697) break;
                return 554;
            }
            case 557: {
                if (n == 4696) {
                    return 556;
                }
                if (n == 4698) {
                    return 555;
                }
                if (n != 4699) break;
                return 554;
            }
        }
        return -1;
    }

    /*
     * WARNING - void declaration
     */
    private boolean collectCombinationRuneCosts(Player object, int n, int n2) {
        void itemStack2;
        this.combinationRuneCosts.clear();
        this.combinationRuneCredits.clear();
        ArrayList<Object> arrayList = new ArrayList<Object>();
        ItemStack itemStack = ((Player)object).getInventoryManager().getContainer().findFlatItem(n);
        int n3 = 0;
        if (itemStack != null) {
            n3 = itemStack.getAmount();
        }
        if (n3 >= n2) {
            return true;
        }
        ItemStack[] itemStackArray = ((Player)object).getInventoryManager().getContainer().getItems();
        int n4 = itemStackArray.length;
        boolean n5 = false;
        while (itemStack2 < n4) {
            object = itemStackArray[itemStack2];
            if (object != null) {
                switch (n) {
                    case 554: {
                        if (((ItemStack)object).getId() != 4697 && ((ItemStack)object).getId() != 4694 && ((ItemStack)object).getId() != 4699) break;
                        arrayList.add(object);
                        break;
                    }
                    case 555: {
                        if (((ItemStack)object).getId() != 4695 && ((ItemStack)object).getId() != 4694 && ((ItemStack)object).getId() != 4698) break;
                        arrayList.add(object);
                        break;
                    }
                    case 556: {
                        if (((ItemStack)object).getId() != 4695 && ((ItemStack)object).getId() != 4696 && ((ItemStack)object).getId() != 4697) break;
                        arrayList.add(object);
                        break;
                    }
                    case 557: {
                        if (((ItemStack)object).getId() != 4696 && ((ItemStack)object).getId() != 4698 && ((ItemStack)object).getId() != 4699) break;
                        arrayList.add(object);
                    }
                }
            }
            ++itemStack2;
        }
        int n6 = 0;
        for (ItemStack n7 : arrayList) {
            n6 += n7.getAmount();
        }
        if (arrayList.size() == 0) {
            return false;
        }
        if (n3 + n6 >= n2) {
            int n7;
            int n8;
            int n9;
            int n10 = n2;
            if (itemStack != null) {
                this.combinationRuneCosts.add(itemStack);
            }
            if ((n9 = n10 - n3) >= ((ItemStack)arrayList.get(0)).getAmount()) {
                this.combinationRuneCosts.add((ItemStack)arrayList.get(0));
                this.combinationRuneCredits.add(new ItemStack(SpellRuneCostRequirement.getPairedCombinationRuneId(((ItemStack)arrayList.get(0)).getId(), n), ((ItemStack)arrayList.get(0)).getAmount()));
                n8 = n9 - ((ItemStack)arrayList.get(0)).getAmount();
            } else {
                this.combinationRuneCosts.add(new ItemStack(((ItemStack)arrayList.get(0)).getId(), n9));
                this.combinationRuneCredits.add(new ItemStack(SpellRuneCostRequirement.getPairedCombinationRuneId(((ItemStack)arrayList.get(0)).getId(), n), n9));
            }
            if (n3 + ((ItemStack)arrayList.get(0)).getAmount() >= n2) {
                return true;
            }
            if (n8 >= ((ItemStack)arrayList.get(1)).getAmount()) {
                this.combinationRuneCosts.add((ItemStack)arrayList.get(1));
                n7 = n8 - ((ItemStack)arrayList.get(1)).getAmount();
                this.combinationRuneCredits.add(new ItemStack(SpellRuneCostRequirement.getPairedCombinationRuneId(((ItemStack)arrayList.get(1)).getId(), n), ((ItemStack)arrayList.get(1)).getAmount()));
            } else {
                this.combinationRuneCosts.add(new ItemStack(((ItemStack)arrayList.get(1)).getId(), n8));
                this.combinationRuneCredits.add(new ItemStack(SpellRuneCostRequirement.getPairedCombinationRuneId(((ItemStack)arrayList.get(1)).getId(), n), n8));
            }
            if (n3 + ((ItemStack)arrayList.get(0)).getAmount() + ((ItemStack)arrayList.get(1)).getAmount() >= n2) {
                return true;
            }
            if (n7 >= ((ItemStack)arrayList.get(2)).getAmount()) {
                this.combinationRuneCosts.add((ItemStack)arrayList.get(2));
                ((ItemStack)arrayList.get(2)).getAmount();
                this.combinationRuneCredits.add(new ItemStack(SpellRuneCostRequirement.getPairedCombinationRuneId(((ItemStack)arrayList.get(2)).getId(), n), ((ItemStack)arrayList.get(2)).getAmount()));
            } else {
                this.combinationRuneCosts.add(new ItemStack(((ItemStack)arrayList.get(2)).getId(), n7));
                this.combinationRuneCredits.add(new ItemStack(SpellRuneCostRequirement.getPairedCombinationRuneId(((ItemStack)arrayList.get(2)).getId(), n), n7));
            }
            if (n3 + ((ItemStack)arrayList.get(0)).getAmount() + ((ItemStack)arrayList.get(1)).getAmount() + ((ItemStack)arrayList.get(2)).getAmount() >= n2) {
                return true;
            }
        }
        return false;
    }
}

