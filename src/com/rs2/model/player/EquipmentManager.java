/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.player;

import com.rs2.ServerSettings;
import com.rs2.model.EntityTargetMovement;
import com.rs2.model.GameplayHelper;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.special.SpecialAttackDefinition;
import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.item.ItemContainer;
import com.rs2.model.item.ItemContainerType;
import com.rs2.model.item.ItemService;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.EquipmentContainer;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.util.GameUtil;

public final class EquipmentManager {
    private Player a;
    private ItemContainer b = new EquipmentContainer(this, ItemContainerType.a, 14);
    private int[] c = new int[]{1342, 1343, 1344, 1346};

    public EquipmentManager(Player player) {
        this.a = player;
    }

    public final void refresh() {
        Object object = this.b.getRawItems();
        Player player = this.a;
        player.packetSender.sendItemContainer(1688, (ItemStack[])object);
        object = object[3];
        if (object != null && ((ItemStack)object).getDefinition().isMembersOnly() && ServerSettings.freeToPlayWorld) {
            object = null;
        }
        this.a.setWeaponProfile(WeaponProfile.forItem((ItemStack)object));
        this.a.getEquipmentManager().j();
        if (!ServerSettings.freeToPlayWorld) {
            this.a.getEquipmentManager().i();
        }
        this.refreshEquipmentBonuses(this.a);
        this.refreshWeaponInterface();
    }

    public final boolean a(ItemStack itemStack) {
        if (itemStack == null || !itemStack.isValid()) {
            return false;
        }
        this.b.remove(itemStack);
        if (itemStack.getId() == 6583 || itemStack.getId() == 7927) {
            this.a.ak = -1;
            Player player = this.a;
            player.packetSender.refreshSidebarInterfaces();
            this.a.f(true);
        }
        if (itemStack.getId() == 4024) {
            this.a.getUpdateState().setGraphic(160, 0);
            this.a.ak = -1;
            this.a.f(true);
        }
        return true;
    }

    public final boolean b(ItemStack itemStack) {
        if (!itemStack.isValid()) {
            return false;
        }
        EquipmentManager equipmentManager = this;
        ItemStack itemStack2 = itemStack;
        EquipmentManager equipmentManager2 = equipmentManager;
        int n = itemStack2.getAmount();
        int n2 = itemStack2.getId();
        equipmentManager2 = equipmentManager;
        if (!(equipmentManager.containsItem(n2) && equipmentManager2.b.getItemAmount(n2) >= n)) {
            return false;
        }
        this.b.remove(itemStack);
        this.refresh();
        this.a.getEquipmentManager().refreshCarriedValue();
        return true;
    }

    public final boolean containsItem(int n) {
        EquipmentManager equipmentManager = this;
        return equipmentManager.b.indexOfItem(n) >= 0;
    }

    public final void b() {
        this.refresh();
        GameplayHelper.f(this.a, -1);
        this.a.nextActionSequence();
        this.a.setWeaponProfile(null);
        this.a.a((SpecialAttackDefinition)null);
        this.a.b((SpellDefinition)null);
        this.refreshCarriedValue();
        GameplayHelper.h(this.a);
        this.a.f(true);
    }

    public final void a(int n, int n2) {
        ItemStack itemStack = new ItemStack(this.a.getEquipmentManager().getItemIdAtSlot(n2));
        ItemStack itemStack2 = new ItemStack(n);
        this.b.removeFromSlot(itemStack, n2);
        this.b.setItem(n2, itemStack2);
        this.refresh();
        this.a.setSpecialAttackEnabled(false);
        this.refreshWeaponInterface();
        EntityTargetMovement.clearMovementTarget(this.a);
        this.a.getInventoryManager().refresh();
        this.a.f(true);
        this.a.getAttributes().put("usedGlory", Boolean.FALSE);
    }

    public final void b(int n, int n2) {
        ItemStack itemStack = new ItemStack(n);
        this.b.setItem(n2, itemStack);
        this.refresh();
        this.a.setSpecialAttackEnabled(false);
        this.refreshWeaponInterface();
        EntityTargetMovement.clearMovementTarget(this.a);
        this.a.getInventoryManager().refresh();
        this.a.f(true);
    }

    public final void equipFromInventorySlot(int n) {
        int n2;
        int n3;
        ItemStack itemStack;
        block61: {
            block64: {
                block63: {
                    Object object;
                    block62: {
                        boolean bl;
                        block60: {
                            itemStack = this.a.getInventoryManager().getContainer().getItemAt(n);
                            if (itemStack == null) {
                                return;
                            }
                            n3 = itemStack.getDefinition().getEquipmentSlot();
                            if (!this.a.getInventoryManager().containsItemStack(itemStack)) {
                                return;
                            }
                            int n4 = n3;
                            n2 = itemStack.getId();
                            object = this;
                            if (new ItemStack(n2).getDefinition().isMembersOnly() && !((EquipmentManager)object).a.isMember()) {
                                ((EquipmentManager)object).a.packetSender.sendGameMessage("You need a members account to access members content.");
                                bl = false;
                            } else if (new ItemStack(n2).getDefinition().isMembersOnly() && ServerSettings.freeToPlayWorld) {
                                ((EquipmentManager)object).a.packetSender.sendGameMessage("You need to be in members world to access members content.");
                                bl = false;
                            } else {
                                char c;
                                int n5;
                                int n6;
                                String string = "wear this item.";
                                if (n4 == 3) {
                                    string = "wield this weapon.";
                                } else if (n4 == 13) {
                                    string = "equip this ammo.";
                                }
                                n4 = 0;
                                while (n4 < 22) {
                                    n6 = ((EquipmentManager)object).a.getSkillManager().getBaseLevel(n4);
                                    if (n6 < (n5 = new ItemStack(n2).getDefinition().getRequiredLevel(n4))) {
                                        String string2 = "a";
                                        c = SkillManager.a[n4].charAt(0);
                                        if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
                                            string2 = "an";
                                        }
                                        object = ((EquipmentManager)object).a;
                                        ((Player)object).packetSender.sendGameMessage("You need " + string2 + " " + SkillManager.a[n4] + " level of " + n5 + " to " + string);
                                        bl = false;
                                        break block60;
                                    }
                                    ++n4;
                                }
                                n4 = ((EquipmentManager)object).a.dA();
                                if (n4 < (n6 = new ItemStack(n2).getDefinition().getRequiredQuestPoints())) {
                                    object = ((EquipmentManager)object).a;
                                    ((Player)object).packetSender.sendGameMessage("You need " + n6 + " quest points to " + string);
                                    bl = false;
                                } else {
                                    n5 = 0;
                                    while (n5 < QuestDefinition.a) {
                                        n6 = new ItemStack(n2).getDefinition().requiresQuest(n5) ? 1 : 0;
                                        char c2 = c = ((EquipmentManager)object).a.getQuestState(n5) == 1 ? (char)'\u0001' : '\u0000';
                                        if (n6 != 0 && c == '\u0000') {
                                            Object object2 = QuestDefinition.b(n5);
                                            object2 = ((QuestDefinition)object2).c();
                                            object = ((EquipmentManager)object).a;
                                            ((Player)object).packetSender.sendGameMessage("You need to complete " + (String)object2 + " to " + string);
                                            bl = false;
                                            break block60;
                                        }
                                        ++n5;
                                    }
                                    bl = true;
                                }
                            }
                        }
                        if (!bl) {
                            return;
                        }
                        if (this.a.getQuestState(0) < 42 && this.a.getQuestState(0) != 1) {
                            this.a.getDialogueManager().showOneLineStatement("You haven't learned how to wield items yet!");
                            return;
                        }
                        if (this.a.getQuestState(0) != 44) break block61;
                        if (itemStack.getId() != 1171) break block62;
                        object = this.a.getEquipmentManager();
                        if (((EquipmentManager)object).b.containsItem(1277)) break block63;
                    }
                    if (itemStack.getId() != 1277) break block64;
                    object = this.a.getEquipmentManager();
                    if (!((EquipmentManager)object).b.containsItem(1171)) break block64;
                }
                this.a.ea();
            }
            this.a.getQuestManager().refreshQuestJournal();
        }
        if (itemStack.getId() == 1205 && this.a.getQuestState(0) == 42) {
            this.a.ea();
        }
        if (this.a.isInDuelArena()) {
            int[] nArray = ServerSettings.FUN_WEAPON_IDS;
            n2 = 0;
            while (n2 < 13) {
                int n7 = nArray[n2];
                if (!DuelRule.e.a(this.a) && itemStack.getId() == n7) {
                    Player player = this.a;
                    player.packetSender.sendGameMessage("Usage of 'Fun weapons' haven't been enabled during this fight!");
                    return;
                }
                ++n2;
            }
        }
        int n8 = 0;
        switch (n3) {
            case 0: {
                n8 = DuelRule.l.a(this.a);
                break;
            }
            case 1: {
                n8 = DuelRule.m.a(this.a);
                break;
            }
            case 2: {
                n8 = DuelRule.n.a(this.a);
                break;
            }
            case 13: {
                n8 = DuelRule.o.a(this.a);
                break;
            }
            case 3: {
                n8 = !DuelRule.p.a(this.a) && (!itemStack.getDefinition().isTwoHanded() || !DuelRule.r.a(this.a)) ? 0 : 1;
                break;
            }
            case 4: {
                n8 = DuelRule.q.a(this.a);
                break;
            }
            case 5: {
                n8 = DuelRule.r.a(this.a);
                break;
            }
            case 7: {
                n8 = DuelRule.s.a(this.a);
                break;
            }
            case 9: {
                n8 = DuelRule.t.a(this.a);
                break;
            }
            case 10: {
                n8 = DuelRule.u.a(this.a);
                break;
            }
            case 12: {
                n8 = DuelRule.v.a(this.a);
            }
        }
        if (n8 != 0) {
            Player player = this.a;
            player.packetSender.sendGameMessage("You cannot wear this during this fight!");
            return;
        }
        n2 = 0;
        if (itemStack.getDefinition().isStackable()) {
            n8 = n3;
            ItemStack itemStack2 = this.b.getItemAt(n8);
            this.a.getInventoryManager().removeItemFromSlot(itemStack, n);
            if (this.b.getItemAt(n8) != null) {
                if (itemStack.getId() == itemStack2.getId()) {
                    this.b.setItem(n8, new ItemStack(itemStack.getId(), itemStack.getAmount() + itemStack2.getAmount(), itemStack.getMetadata()));
                } else {
                    this.a.getInventoryManager().a(itemStack2, n);
                    this.b.setItem(n8, itemStack);
                }
            } else {
                this.b.setItem(n8, itemStack);
            }
        } else {
            n8 = n3;
            if (n8 == 3 && itemStack.getDefinition().isTwoHanded()) {
                if (this.b.getItemAt(3) != null && this.b.getItemAt(5) != null && this.a.getInventoryManager().getContainer().getFirstFreeSlot() == -1) {
                    Player player = this.a;
                    player.packetSender.sendGameMessage("Not enough space in your inventory.");
                    return;
                }
                this.a.getInventoryManager().removeItemFromSlot(itemStack, n);
                n2 = 1;
                this.unequipSlot(5);
                if (this.b.getItemAt(5) != null) {
                    return;
                }
            }
            if (n8 == 5 && this.b.getItemAt(3) != null && this.b.getItemAt(3).getDefinition().isTwoHanded()) {
                this.a.getInventoryManager().removeItemFromSlot(itemStack, n);
                n2 = 1;
                this.unequipSlot(3);
                if (this.b.getItemAt(3) != null) {
                    return;
                }
            }
            if (this.b.getItemAt(n8) != null) {
                ItemStack itemStack3 = this.b.getItemAt(n8);
                if (itemStack3.getId() == 4024) {
                    this.a.getUpdateState().setGraphic(160, 0);
                    this.a.ak = -1;
                    this.a.f(true);
                }
                if (n2 == 0) {
                    this.a.getInventoryManager().removeItemFromSlot(itemStack, n);
                    this.a.getInventoryManager().a(itemStack3, n);
                } else {
                    this.a.getInventoryManager().addItem(itemStack3);
                }
            } else if (n2 == 0) {
                this.a.getInventoryManager().removeItemFromSlot(itemStack, n);
            }
            this.b.setItem(n8, new ItemStack(itemStack.getId(), itemStack.getAmount(), itemStack.getMetadata()));
        }
        this.a.nextActionSequence();
        this.a.setSpecialAttackEnabled(false);
        EntityTargetMovement.clearMovementTarget(this.a);
        this.a.getInventoryManager().refresh();
        if (n3 == 3) {
            this.a.setWeaponProfile(WeaponProfile.forItem(itemStack));
            this.a.a(SpecialAttackDefinition.forItem(itemStack));
            this.a.b((SpellDefinition)null);
        }
        this.refresh();
        this.refreshCarriedValue();
        this.a.getAttributes().put("usedGlory", Boolean.FALSE);
        if (itemStack.getId() == 6583 || itemStack.getId() == 7927) {
            this.a.ak = itemStack.getId() == 6583 ? 2626 : 3689 + GameUtil.g(5);
            this.a.f(true);
            Player player = this.a;
            player.packetSender.clearSidebarInterfaces();
            player = this.a;
            player.packetSender.setSidebarInterface(3, 6014);
        }
        if (itemStack.getId() == 4024) {
            this.a.getUpdateState().setGraphic(160, 0);
            this.a.ak = 1463;
            this.a.f(true);
        }
        GameplayHelper.h(this.a);
        GameplayHelper.f(this.a, itemStack.getId());
        Player player = this.a;
        player.packetSender.sendSoundEffect(this.c[GameUtil.h(this.c.length)], 1, 0);
        this.a.f(true);
    }

    public final void unequipSlot(int n) {
        Object object;
        ItemStack itemStack = this.b.getItemAt(n);
        if (itemStack == null) {
            return;
        }
        if (this.a.getInventoryManager().getContainer().getFirstFreeSlot() == -1) {
            Player player = this.a;
            player.packetSender.sendGameMessage("Not enough space in your inventory.");
            return;
        }
        if (itemStack.getId() == 6583 || itemStack.getId() == 7927) {
            this.a.ak = -1;
            object = this.a;
            ((Player)object).packetSender.refreshSidebarInterfaces();
            this.a.f(true);
        }
        if (itemStack.getId() == 4024) {
            this.a.getUpdateState().setGraphic(160, 0);
            this.a.ak = -1;
            this.a.f(true);
        }
        if (n == 0) {
            GameplayHelper.f(this.a, -1);
        }
        object = this.a.getEquipmentManager();
        if (!((EquipmentManager)object).b.containsItem(itemStack.getId())) {
            return;
        }
        this.a.nextActionSequence();
        this.b.removeFromSlot(itemStack, n);
        this.a.getInventoryManager().addItem(new ItemStack(itemStack.getId(), itemStack.getAmount(), itemStack.getMetadata()));
        if (n == 3) {
            this.a.setWeaponProfile(null);
            this.a.a((SpecialAttackDefinition)null);
            this.a.b((SpellDefinition)null);
        }
        ItemStack itemStack2 = new ItemStack(-1, 0);
        int n2 = n;
        EquipmentManager equipmentManager = this;
        object = equipmentManager.a;
        ((Player)object).packetSender.sendInterfaceSlotItem(n2, 1688, itemStack2);
        equipmentManager.a.getEquipmentManager().j();
        equipmentManager.a.getEquipmentManager().i();
        equipmentManager.refreshEquipmentBonuses(equipmentManager.a);
        equipmentManager.refreshWeaponInterface();
        this.refreshCarriedValue();
        object = this.a;
        ((Player)object).packetSender.sendSoundEffect(this.c[GameUtil.h(this.c.length)], 1, 0);
        GameplayHelper.h(this.a);
        this.a.f(true);
    }

    public final void refreshCarriedValue() {
        Object object;
        double d = 0.0;
        int n = 0;
        while (n < 11) {
            object = this.a.getEquipmentManager();
            if (((EquipmentManager)object).b.getItemAt(n) != null) {
                object = this.a.getEquipmentManager();
                object = ((EquipmentManager)object).b.getItemAt(n);
                double d2 = ((ItemStack)object).getDefinition().getWeight();
                if (((ItemStack)object).getDefinition().isMembersOnly() && ServerSettings.freeToPlayWorld && d2 < 0.0) {
                    d2 = 0.0;
                }
                d += d2;
            }
            ++n;
        }
        n = 0;
        while (n < 28) {
            if (this.a.getInventoryManager().getContainer().getItemAt(n) != null) {
                d += this.a.getInventoryManager().getContainer().getItemAt(n).getDefinition().getWeight();
                if (this.a.getInventoryManager().getContainer().getItemAt(n).getId() == 88) {
                    d += 4.8;
                }
            }
            ++n;
        }
        this.a.al = d;
        object = this.a;
        ((Player)object).packetSender.sendWeight();
    }

    public final void c(int n, int n2) {
        ItemStack itemStack = this.b.getItemAt(n);
        if (itemStack == null) {
            return;
        }
        n2 = this.b.removeFromSlot(itemStack, n) - n2;
        if (n2 > 0) {
            ItemStack itemStack2 = new ItemStack(itemStack.getId(), n2, itemStack.getMetadata());
            this.b.add(itemStack2, n);
        } else {
            this.b.add(new ItemStack(-1, 0), n);
        }
        this.refresh();
        this.a.f(true);
        this.refreshEquipmentBonuses(this.a);
    }

    private void refreshEquipmentBonuses(Player player) {
        Object object;
        int n = 0;
        int n2 = 0;
        while (n2 < 14) {
            player.g(n2, 0);
            ++n2;
        }
        Object object2 = player;
        n2 = 0;
        while (n2 < 14) {
            object = ((Player)object2).getEquipmentManager();
            if (!(((EquipmentManager)object).b.getItemAt(n2) == null || ((Player)object2).dI() && n2 == 13)) {
                object = ((Player)object2).getEquipmentManager();
                object = ((EquipmentManager)object).b.getItemAt(n2);
                if (!((ItemStack)object).getDefinition().isMembersOnly() || !ServerSettings.freeToPlayWorld) {
                    int n3 = 0;
                    while (n3 < 14) {
                        int n4 = 0;
                        if (((ItemStack)object).getId() == 11283 && ((ItemStack)object).getMetadata() >= 0 && (n3 == 5 || n3 == 6 || n3 == 7 || n3 == 9)) {
                            n4 = ((ItemStack)object).getMetadata();
                        }
                        ((Player)object2).g(n3, ((ItemStack)object).getDefinition().getBonuses()[n3] + n4 + (Integer)((Player)object2).bU().get(n3));
                        ++n3;
                    }
                }
            }
            ++n2;
        }
        n2 = 0;
        while (n2 < 12) {
            object2 = (Integer)player.bU().get(n2) >= 0 ? String.valueOf(ServerSettings.bonusTypeNames[n2]) + ": +" + player.bU().get(n2) : String.valueOf(ServerSettings.bonusTypeNames[n2]) + ": -" + Math.abs((Integer)player.bU().get(n2));
            if (n2 == 10) {
                n = 1;
            }
            object = player;
            ((Player)object).packetSender.sendInterfaceText((String)object2, n2 + 1675 + n);
            ++n2;
        }
    }

    public final void refreshWeaponInterface() {
        Object object = this;
        ItemStack itemStack = ((EquipmentManager)object).b.getItemAt(3);
        if (itemStack != null && itemStack.getDefinition().isMembersOnly() && ServerSettings.freeToPlayWorld) {
            itemStack = null;
        }
        WeaponProfile weaponProfile = WeaponProfile.forItem(itemStack);
        if (this.a.getQuestState(0) >= 45 || this.a.getQuestState(0) == 1) {
            object = this.a;
            ((Player)object).packetSender.setSidebarInterface(0, weaponProfile.getInterfaceDefinition().getSidebarInterfaceId());
        }
        object = this.a;
        ((Player)object).packetSender.sendInterfaceText(itemStack == null ? "Unarmed" : itemStack.getDefinition().getName(), weaponProfile.getInterfaceDefinition().getWeaponNameTextId());
        WeaponProfile weaponProfile2 = weaponProfile;
        object = this;
        if (weaponProfile2.getAttackAnimations().length < 4 && ((EquipmentManager)object).a.getFightMode() == 3) {
            ((EquipmentManager)object).a.setFightMode(2);
        }
        object = this.a;
        ((Player)object).packetSender.sendConfig(43, this.a.getFightMode());
        if (weaponProfile.getInterfaceDefinition().getWeaponModelWidgetId() != -1) {
            object = this.a;
            ((Player)object).packetSender.sendInterfaceModel(weaponProfile.getInterfaceDefinition().getWeaponModelWidgetId(), 200, itemStack.getId());
        }
        if (weaponProfile.getInterfaceDefinition().getSpecialBarWidgetId() != -1) {
            if (SpecialAttackDefinition.forItem(itemStack) == null) {
                object = this.a;
                ((Player)object).packetSender.setInterfaceHiddenFlag(1, weaponProfile.getInterfaceDefinition().getSpecialBarWidgetId());
                return;
            }
            object = this.a;
            ((Player)object).packetSender.setInterfaceHiddenFlag(0, weaponProfile.getInterfaceDefinition().getSpecialBarWidgetId());
            this.a.refreshSpecialAttackWidgets();
        }
    }

    public final boolean handleAttackStyleButton(int n) {
        Object object = this;
        object = ((EquipmentManager)object).b.getItemAt(3);
        if (object != null && ((ItemStack)object).getDefinition().isMembersOnly() && ServerSettings.freeToPlayWorld) {
            object = null;
        }
        object = WeaponProfile.forItem((ItemStack)object);
        int n2 = 0;
        while (n2 < ((WeaponProfile)((Object)object)).getInterfaceDefinition().getAttackStyles().length) {
            if (n == ((WeaponProfile)((Object)object)).getInterfaceDefinition().getAttackStyles()[n2].getButtonId()) {
                this.a.ef();
                this.a.setFightMode(n2);
                return true;
            }
            ++n2;
        }
        return false;
    }

    public final int getItemIdAtSlot(int n) {
        EquipmentManager equipmentManager = this;
        ItemStack itemStack = equipmentManager.b.getItemAt(n);
        if (itemStack != null) {
            return itemStack.getId();
        }
        return 0;
    }

    public final int getStandAnimation() {
        return this.a.getWeaponProfile().getMovementAnimations()[0];
    }

    public final int getWalkAnimation() {
        return this.a.getWeaponProfile().getMovementAnimations()[1];
    }

    public final int getRunAnimation() {
        return this.a.getWeaponProfile().getMovementAnimations()[2];
    }

    public final ItemContainer getContainer() {
        return this.b;
    }

    public final boolean canEquipItem(int n) {
        int n2;
        int n3;
        int n4 = 0;
        while (n4 < 22) {
            n3 = this.a.getSkillManager().getBaseLevel(n4);
            if (n3 < (n2 = new ItemStack(n).getDefinition().getRequiredLevel(n4))) {
                return false;
            }
            ++n4;
        }
        n4 = this.a.dA();
        if (n4 < (n3 = new ItemStack(n).getDefinition().getRequiredQuestPoints())) {
            return false;
        }
        n2 = 0;
        while (n2 < QuestDefinition.a) {
            n4 = new ItemStack(n).getDefinition().requiresQuest(n2) ? 1 : 0;
            int n5 = n3 = this.a.getQuestState(n2) == 1 ? 1 : 0;
            if (n4 != 0 && n3 == 0) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    /*
     * Unable to fully structure code
     */
    public final void i() {
        block39: {
            block40: {
                block42: {
                    block41: {
                        block38: {
                            block37: {
                                block34: {
                                    block36: {
                                        block35: {
                                            block33: {
                                                block32: {
                                                    block29: {
                                                        block31: {
                                                            block30: {
                                                                block28: {
                                                                    block27: {
                                                                        block24: {
                                                                            block26: {
                                                                                block25: {
                                                                                    block23: {
                                                                                        block22: {
                                                                                            block19: {
                                                                                                block21: {
                                                                                                    block20: {
                                                                                                        block18: {
                                                                                                            block17: {
                                                                                                                block14: {
                                                                                                                    block16: {
                                                                                                                        block15: {
                                                                                                                            block13: {
                                                                                                                                block12: {
                                                                                                                                    var1_1 = this;
                                                                                                                                    var2_2 = var1_1.a.getEquipmentManager();
                                                                                                                                    if (var2_2.b.getItemAt(0) == null) break block12;
                                                                                                                                    var2_2 = var1_1.a.getEquipmentManager();
                                                                                                                                    if (var2_2.b.getItemAt(7) == null) break block12;
                                                                                                                                    var2_2 = var1_1.a.getEquipmentManager();
                                                                                                                                    if (var2_2.b.getItemAt(4) == null) break block12;
                                                                                                                                    var2_2 = var1_1.a.getEquipmentManager();
                                                                                                                                    if (var2_2.b.getItemAt(3) != null) break block13;
                                                                                                                                }
                                                                                                                                v0 = false;
                                                                                                                                break block14;
                                                                                                                            }
                                                                                                                            var2_2 = var1_1.a.getEquipmentManager();
                                                                                                                            if (!var2_2.b.getItemAt(0).getDefinition().getName().toLowerCase().contains("ahrims hood")) ** GOTO lbl-1000
                                                                                                                            var2_2 = var1_1.a.getEquipmentManager();
                                                                                                                            if (var2_2.b.getItemAt(4).getDefinition().getName().toLowerCase().contains("ahrims top")) break block15;
                                                                                                                            var2_2 = var1_1.a.getEquipmentManager();
                                                                                                                            if (!var2_2.b.getItemAt(4).getDefinition().getName().toLowerCase().contains("ahrims robetop")) ** GOTO lbl-1000
                                                                                                                        }
                                                                                                                        var2_2 = var1_1.a.getEquipmentManager();
                                                                                                                        if (var2_2.b.getItemAt(7).getDefinition().getName().toLowerCase().contains("ahrims skirt")) break block16;
                                                                                                                        var2_2 = var1_1.a.getEquipmentManager();
                                                                                                                        if (!var2_2.b.getItemAt(7).getDefinition().getName().toLowerCase().contains("ahrims robeskirt")) ** GOTO lbl-1000
                                                                                                                    }
                                                                                                                    var2_2 = var1_1.a.getEquipmentManager();
                                                                                                                    if (var2_2.b.getItemAt(3).getDefinition().getName().toLowerCase().contains("ahrims staff")) {
                                                                                                                        v0 = true;
                                                                                                                    } else lbl-1000:
                                                                                                                    // 4 sources

                                                                                                                    {
                                                                                                                        v0 = false;
                                                                                                                    }
                                                                                                                }
                                                                                                                this.a.q(v0);
                                                                                                                var1_1 = this;
                                                                                                                var2_2 = var1_1.a.getEquipmentManager();
                                                                                                                if (var2_2.b.getItemAt(0) == null) break block17;
                                                                                                                var2_2 = var1_1.a.getEquipmentManager();
                                                                                                                if (var2_2.b.getItemAt(7) == null) break block17;
                                                                                                                var2_2 = var1_1.a.getEquipmentManager();
                                                                                                                if (var2_2.b.getItemAt(4) == null) break block17;
                                                                                                                var2_2 = var1_1.a.getEquipmentManager();
                                                                                                                if (var2_2.b.getItemAt(3) != null) break block18;
                                                                                                            }
                                                                                                            v1 = false;
                                                                                                            break block19;
                                                                                                        }
                                                                                                        var2_2 = var1_1.a.getEquipmentManager();
                                                                                                        if (!var2_2.b.getItemAt(0).getDefinition().getName().toLowerCase().contains("karils coif")) ** GOTO lbl-1000
                                                                                                        var2_2 = var1_1.a.getEquipmentManager();
                                                                                                        if (var2_2.b.getItemAt(4).getDefinition().getName().toLowerCase().contains("karils top")) break block20;
                                                                                                        var2_2 = var1_1.a.getEquipmentManager();
                                                                                                        if (!var2_2.b.getItemAt(4).getDefinition().getName().toLowerCase().contains("karils leathertop")) ** GOTO lbl-1000
                                                                                                    }
                                                                                                    var2_2 = var1_1.a.getEquipmentManager();
                                                                                                    if (var2_2.b.getItemAt(7).getDefinition().getName().toLowerCase().contains("karils skirt")) break block21;
                                                                                                    var2_2 = var1_1.a.getEquipmentManager();
                                                                                                    if (!var2_2.b.getItemAt(7).getDefinition().getName().toLowerCase().contains("karils leatherskirt")) ** GOTO lbl-1000
                                                                                                }
                                                                                                var2_2 = var1_1.a.getEquipmentManager();
                                                                                                if (var2_2.b.getItemAt(3).getDefinition().getName().toLowerCase().contains("karils x-bow")) ** GOTO lbl-1000
                                                                                                var2_2 = var1_1.a.getEquipmentManager();
                                                                                                if (var2_2.b.getItemAt(3).getDefinition().getName().toLowerCase().contains("karils crossbow")) lbl-1000:
                                                                                                // 2 sources

                                                                                                {
                                                                                                    v1 = true;
                                                                                                } else lbl-1000:
                                                                                                // 4 sources

                                                                                                {
                                                                                                    v1 = false;
                                                                                                }
                                                                                            }
                                                                                            this.a.r(v1);
                                                                                            var1_1 = this;
                                                                                            var2_2 = var1_1.a.getEquipmentManager();
                                                                                            if (var2_2.b.getItemAt(0) == null) break block22;
                                                                                            var2_2 = var1_1.a.getEquipmentManager();
                                                                                            if (var2_2.b.getItemAt(7) == null) break block22;
                                                                                            var2_2 = var1_1.a.getEquipmentManager();
                                                                                            if (var2_2.b.getItemAt(4) == null) break block22;
                                                                                            var2_2 = var1_1.a.getEquipmentManager();
                                                                                            if (var2_2.b.getItemAt(3) != null) break block23;
                                                                                        }
                                                                                        v2 = false;
                                                                                        break block24;
                                                                                    }
                                                                                    var2_2 = var1_1.a.getEquipmentManager();
                                                                                    if (!var2_2.b.getItemAt(0).getDefinition().getName().toLowerCase().contains("dharoks helm")) ** GOTO lbl-1000
                                                                                    var2_2 = var1_1.a.getEquipmentManager();
                                                                                    if (var2_2.b.getItemAt(4).getDefinition().getName().toLowerCase().contains("dharoks body")) break block25;
                                                                                    var2_2 = var1_1.a.getEquipmentManager();
                                                                                    if (!var2_2.b.getItemAt(4).getDefinition().getName().toLowerCase().contains("dharoks platebody")) ** GOTO lbl-1000
                                                                                }
                                                                                var2_2 = var1_1.a.getEquipmentManager();
                                                                                if (var2_2.b.getItemAt(7).getDefinition().getName().toLowerCase().contains("dharoks legs")) break block26;
                                                                                var2_2 = var1_1.a.getEquipmentManager();
                                                                                if (!var2_2.b.getItemAt(7).getDefinition().getName().toLowerCase().contains("dharoks platelegs")) ** GOTO lbl-1000
                                                                            }
                                                                            var2_2 = var1_1.a.getEquipmentManager();
                                                                            if (var2_2.b.getItemAt(3).getDefinition().getName().toLowerCase().contains("dharoks axe")) ** GOTO lbl-1000
                                                                            var2_2 = var1_1.a.getEquipmentManager();
                                                                            if (var2_2.b.getItemAt(3).getDefinition().getName().toLowerCase().contains("dharoks greataxe")) lbl-1000:
                                                                            // 2 sources

                                                                            {
                                                                                v2 = true;
                                                                            } else lbl-1000:
                                                                            // 4 sources

                                                                            {
                                                                                v2 = false;
                                                                            }
                                                                        }
                                                                        this.a.p(v2);
                                                                        var1_1 = this;
                                                                        var2_2 = var1_1.a.getEquipmentManager();
                                                                        if (var2_2.b.getItemAt(0) == null) break block27;
                                                                        var2_2 = var1_1.a.getEquipmentManager();
                                                                        if (var2_2.b.getItemAt(7) == null) break block27;
                                                                        var2_2 = var1_1.a.getEquipmentManager();
                                                                        if (var2_2.b.getItemAt(4) == null) break block27;
                                                                        var2_2 = var1_1.a.getEquipmentManager();
                                                                        if (var2_2.b.getItemAt(3) != null) break block28;
                                                                    }
                                                                    v3 = false;
                                                                    break block29;
                                                                }
                                                                var2_2 = var1_1.a.getEquipmentManager();
                                                                if (!var2_2.b.getItemAt(0).getDefinition().getName().toLowerCase().contains("veracs helm")) ** GOTO lbl-1000
                                                                var2_2 = var1_1.a.getEquipmentManager();
                                                                if (var2_2.b.getItemAt(4).getDefinition().getName().toLowerCase().contains("veracs top")) break block30;
                                                                var2_2 = var1_1.a.getEquipmentManager();
                                                                if (!var2_2.b.getItemAt(4).getDefinition().getName().toLowerCase().contains("veracs brassard")) ** GOTO lbl-1000
                                                            }
                                                            var2_2 = var1_1.a.getEquipmentManager();
                                                            if (var2_2.b.getItemAt(7).getDefinition().getName().toLowerCase().contains("veracs skirt")) break block31;
                                                            var2_2 = var1_1.a.getEquipmentManager();
                                                            if (!var2_2.b.getItemAt(7).getDefinition().getName().toLowerCase().contains("veracs plateskirt")) ** GOTO lbl-1000
                                                        }
                                                        var2_2 = var1_1.a.getEquipmentManager();
                                                        if (var2_2.b.getItemAt(3).getDefinition().getName().toLowerCase().contains("veracs flail")) {
                                                            v3 = true;
                                                        } else lbl-1000:
                                                        // 4 sources

                                                        {
                                                            v3 = false;
                                                        }
                                                    }
                                                    this.a.u(v3);
                                                    var1_1 = this;
                                                    var2_2 = var1_1.a.getEquipmentManager();
                                                    if (var2_2.b.getItemAt(0) == null) break block32;
                                                    var2_2 = var1_1.a.getEquipmentManager();
                                                    if (var2_2.b.getItemAt(7) == null) break block32;
                                                    var2_2 = var1_1.a.getEquipmentManager();
                                                    if (var2_2.b.getItemAt(4) == null) break block32;
                                                    var2_2 = var1_1.a.getEquipmentManager();
                                                    if (var2_2.b.getItemAt(3) != null) break block33;
                                                }
                                                v4 = false;
                                                break block34;
                                            }
                                            var2_2 = var1_1.a.getEquipmentManager();
                                            if (!var2_2.b.getItemAt(0).getDefinition().getName().toLowerCase().contains("torags helm")) ** GOTO lbl-1000
                                            var2_2 = var1_1.a.getEquipmentManager();
                                            if (var2_2.b.getItemAt(4).getDefinition().getName().toLowerCase().contains("torags body")) break block35;
                                            var2_2 = var1_1.a.getEquipmentManager();
                                            if (!var2_2.b.getItemAt(4).getDefinition().getName().toLowerCase().contains("torags platebody")) ** GOTO lbl-1000
                                        }
                                        var2_2 = var1_1.a.getEquipmentManager();
                                        if (var2_2.b.getItemAt(7).getDefinition().getName().toLowerCase().contains("torags legs")) break block36;
                                        var2_2 = var1_1.a.getEquipmentManager();
                                        if (!var2_2.b.getItemAt(7).getDefinition().getName().toLowerCase().contains("torags platelegs")) ** GOTO lbl-1000
                                    }
                                    var2_2 = var1_1.a.getEquipmentManager();
                                    if (var2_2.b.getItemAt(3).getDefinition().getName().toLowerCase().contains("torags hammer")) ** GOTO lbl-1000
                                    var2_2 = var1_1.a.getEquipmentManager();
                                    if (var2_2.b.getItemAt(3).getDefinition().getName().toLowerCase().contains("torags hammers")) lbl-1000:
                                    // 2 sources

                                    {
                                        v4 = true;
                                    } else lbl-1000:
                                    // 4 sources

                                    {
                                        v4 = false;
                                    }
                                }
                                this.a.s(v4);
                                var1_1 = this;
                                var2_2 = var1_1.a.getEquipmentManager();
                                if (var2_2.b.getItemAt(0) == null) break block37;
                                var2_2 = var1_1.a.getEquipmentManager();
                                if (var2_2.b.getItemAt(7) == null) break block37;
                                var2_2 = var1_1.a.getEquipmentManager();
                                if (var2_2.b.getItemAt(4) == null) break block37;
                                var2_2 = var1_1.a.getEquipmentManager();
                                if (var2_2.b.getItemAt(3) != null) break block38;
                            }
                            v5 = false;
                            break block39;
                        }
                        var2_2 = var1_1.a.getEquipmentManager();
                        if (!var2_2.b.getItemAt(0).getDefinition().getName().toLowerCase().contains("guthans helm")) break block40;
                        var2_2 = var1_1.a.getEquipmentManager();
                        if (var2_2.b.getItemAt(4).getDefinition().getName().toLowerCase().contains("guthans body")) break block41;
                        var2_2 = var1_1.a.getEquipmentManager();
                        if (!var2_2.b.getItemAt(4).getDefinition().getName().toLowerCase().contains("guthans platebody")) break block40;
                    }
                    var2_2 = var1_1.a.getEquipmentManager();
                    if (var2_2.b.getItemAt(7).getDefinition().getName().toLowerCase().contains("guthans skirt")) break block42;
                    var2_2 = var1_1.a.getEquipmentManager();
                    if (!var2_2.b.getItemAt(7).getDefinition().getName().toLowerCase().contains("guthans chainskirt")) break block40;
                }
                var2_2 = var1_1.a.getEquipmentManager();
                if (var2_2.b.getItemAt(3).getDefinition().getName().toLowerCase().contains("guthans spear")) ** GOTO lbl-1000
            }
            var2_2 = var1_1.a.getEquipmentManager();
            if (!var2_2.b.getItemAt(3).getDefinition().getName().toLowerCase().contains("guthans warspear")) {
                v5 = false;
            } else lbl-1000:
            // 2 sources

            {
                v5 = true;
            }
        }
        this.a.t(v5);
    }

    public final void j() {
        String string = this.a.getWeaponProfile().name().toLowerCase();
        boolean bl = ((string = string.replaceAll("_", " ")).contains("seercull") || string.contains("bow")) && !string.contains("crossbow");
        Player player = this.a;
        boolean bl2 = string.contains("crossbow") || string.contains("x-bow");
        Player player2 = this.a;
        boolean bl3 = string.contains("knife") || string.contains("dart") || string.contains("javelin") || string.contains("thrownaxe") || string.contains("toktz-xil-ul") || string.contains("throwing axe");
        Player player3 = this.a;
        this.a.m(string.contains("crystal bow"));
        ItemService.getInstance();
        String string2 = ItemService.getItemName(this.a.getEquipmentManager().getItemIdAtSlot(13)).toLowerCase();
        boolean bl4 = string2.contains("arrow");
        Player player4 = this.a;
        boolean bl5 = string2.contains("bolt");
        Player player5 = this.a;
        this.a.o(!string.contains("crystal") && !string.contains("karils"));
    }

    static /* synthetic */ Player getPlayer(EquipmentManager equipmentManager) {
        return equipmentManager.a;
    }
}

