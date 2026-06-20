/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.duel;

import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.combat.CombatType;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.gameplay.duel.DuelArenaLocationManager;
import com.rs2.model.gameplay.duel.DuelCountdownTask;
import com.rs2.model.gameplay.duel.DuelHistory;
import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.gameplay.duel.DuelVictoryTask;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class DuelSession {
    private Player a;
    private Player b;
    private boolean c = false;
    private ArrayList d = new ArrayList();
    private ArrayList e = new ArrayList();
    private ArrayList f = new ArrayList();
    private boolean[] g = new boolean[22];
    private ArrayList h = new ArrayList();

    public DuelSession(Player player) {
        this.a = player;
    }

    public final void a() {
        this.h.clear();
        this.f.clear();
        this.e.clear();
        this.d.clear();
    }

    public static ItemStack[] a(ArrayList arrayList) {
        ItemStack[] itemStackArray = new ItemStack[arrayList.size()];
        int n = 0;
        while (n < arrayList.size()) {
            itemStackArray[n] = (ItemStack)arrayList.get(n);
            ++n;
        }
        return itemStackArray;
    }

    public final CombatType b() {
        return new WeaponCombatAttack(this.a, this.a.getDuelSession().b, WeaponProfile.forItem(new ItemStack(this.a.getEquipmentManager().getItemIdAtSlot(3)))).getAttackStyle().getCombatType();
    }

    public final boolean handleButtonClick(int n) {
        DuelSession duelSession;
        DuelRule duelRule;
        block7: {
            block6: {
                if (n == 6674 || n == 6520) {
                    if (!this.a.getDuelSession().c) {
                        this.a.getDuelController().a();
                    }
                    return true;
                }
                duelRule = DuelRule.a(n);
                if (duelRule == null) {
                    return false;
                }
                duelSession = this.a.getDuelSession();
                if (duelSession.b == null) break block6;
                duelSession = this.a.getDuelSession();
                if (duelSession.b.bW()) break block7;
            }
            this.a.getDuelController().a(true);
            return true;
        }
        duelRule.a(this.a, true);
        duelSession = this.a.getDuelSession();
        duelRule.a(duelSession.b, false);
        return true;
    }

    public static void a(Player player, Player player2) {
        if (player == null || player2 == null) {
            return;
        }
        DuelHistory.a(player, player2);
        String string = player2.getUsername();
        String string2 = "" + player2.getCombatLevel();
        ItemStack[] itemStackArray = player2.getDuelSession();
        itemStackArray = DuelSession.a(itemStackArray.h);
        player.getDuelController().a(true);
        player.getDuelSession().a();
        player2.getDuelSession().a();
        player.n(true);
        ++player.eb;
        ++player2.ec;
        CycleEventHandler.getInstance().schedule(player, new DuelVictoryTask(player, string, string2, itemStackArray), 2);
    }

    public final void a(boolean bl) {
        Player player;
        if (this.b != null && this.b.getDuelSession() != null) {
            this.b.getAttributes().put("canTakeDamage", true);
            DuelSession.a(this.b, this.a);
        }
        if (bl) {
            player = this.a;
            player.packetSender.closeInterfaces();
            player = this.a;
            player.packetSender.sendGameMessage("You forfeited the duel.");
        } else {
            player = this.a;
            player.packetSender.sendGameMessage("You have been defeated!");
        }
        this.a.eq();
        player = this.a;
        player.packetSender.sendEntityHintIcon(10, -1);
        this.a.getDuelController().a(false);
        this.a.getDuelArenaLocationManager();
        this.a.moveTo(DuelArenaLocationManager.a());
    }

    public final void c() {
        if (this.a.isInDuelArena()) {
            this.a.getDuelArenaLocationManager();
            this.a.moveTo(DuelArenaLocationManager.a());
        }
    }

    /*
     * Handled impossible loop by duplicating code
     * Enabled aggressive block sorting
     */
    public final void d() {
        Position position;
        int n;
        block6: {
            int n2;
            Object object;
            block5: {
                n = GameUtil.g(2);
                this.a.eq();
                this.b.eq();
                object = this.a;
                ((Player)object).packetSender.closeInterfaces();
                object = this.b;
                ((Player)object).packetSender.closeInterfaces();
                object = this.a;
                ((Player)object).packetSender.sendEntityHintIcon(10, this.b.getIndex());
                object = this.b;
                ((Player)object).packetSender.sendEntityHintIcon(10, this.a.getIndex());
                n2 = 0;
                while (n2 < this.e.size()) {
                    this.a.getEquipmentManager().unequipSlot(this.a.getEquipmentManager().getContainer().indexOfItem(((ItemStack)this.e.get(n2)).getId()));
                    ++n2;
                }
                n2 = 0;
                if (!true) break block5;
                object = this.b.getDuelSession();
                if (n2 >= ((DuelSession)object).e.size()) break block6;
            }
            do {
                object = this.b.getDuelSession();
                this.b.getEquipmentManager().unequipSlot(this.b.getEquipmentManager().getContainer().indexOfItem(((ItemStack)((DuelSession)object).e.get(n2)).getId()));
                ++n2;
                object = this.b.getDuelSession();
            } while (n2 < ((DuelSession)object).e.size());
        }
        Position position2 = this.a.getDuelArenaLocationManager().a(DuelRule.k.a(this.a), n);
        this.a.moveTo(position2);
        if (DuelRule.j.a(this.b)) {
            this.a.getDuelArenaLocationManager();
            position = DuelArenaLocationManager.a(position2);
        } else {
            position = this.a.getDuelArenaLocationManager().a(DuelRule.k.a(this.b), n);
        }
        this.b.moveTo(position);
        this.m();
        this.b.getDuelSession().m();
        this.a.getDuelController().b(false);
    }

    private void m() {
        CycleEventHandler.getInstance().schedule(this.a, new DuelCountdownTask(this), 2);
    }

    public final void a(ItemStack itemStack, int n) {
        Player player = this.a;
        if (player.interfaceAction != "duel" || !this.a.getInventoryManager().getContainer().containsItem(itemStack.getId()) || this.b == null) {
            return;
        }
        if (itemStack.getDefinition().z()) {
            player = this.a;
            player.packetSender.sendGameMessage("You can't stake this item.");
            return;
        }
        if (!(this.h.size() < this.b.getInventoryManager().getContainer().getFreeSlots() || itemStack.getDefinition().isStackable() && this.b(itemStack))) {
            player = this.a;
            player.packetSender.sendGameMessage("The opponent has no free spaces left for that.");
            return;
        }
        if (!ServerSettings.adminInteractionsAllowed && this.a.getPlayerRights() >= 2) {
            player = this.a;
            player.packetSender.sendGameMessage("This action is not allowed.");
            return;
        }
        if (!this.a.getInventoryManager().containsItemStack(itemStack)) {
            return;
        }
        int n2 = this.a.getInventoryManager().getItemAmount(itemStack.getId());
        if (!this.a.getInventoryManager().removeItemFromSlot(itemStack, n)) {
            return;
        }
        if (!itemStack.getDefinition().isStackable() && !itemStack.getDefinition().isNote()) {
            n = 0;
            while (n < itemStack.getAmount()) {
                if (n2 > 0) {
                    this.h.add(new ItemStack(itemStack.getId(), 1));
                    --n2;
                }
                ++n;
            }
        } else {
            n = 0;
            int n3 = 0;
            while (n3 < this.h.size()) {
                if (((ItemStack)this.h.get(n3)).getId() == itemStack.getId()) {
                    ((ItemStack)this.h.get(n3)).setAmount(((ItemStack)this.h.get(n3)).getAmount() + itemStack.getAmount());
                    n = 1;
                }
                ++n3;
            }
            if (n == 0) {
                this.h.add(new ItemStack(itemStack.getId(), itemStack.getAmount() > n2 ? n2 : itemStack.getAmount()));
            }
        }
        this.a.getDuelController().b(false);
        this.b.getDuelController().b(false);
        this.a.getDuelInterfaceManager().c();
        this.b.getDuelInterfaceManager().c();
        this.a.getDuelInterfaceManager().e();
        this.b.getDuelInterfaceManager().e();
    }

    public final void a(ItemStack itemStack) {
        Player player = this.a;
        if (player.interfaceAction != "duel" || this.h.size() <= 0 || !this.b(itemStack) || this.b == null) {
            return;
        }
        if (!itemStack.getDefinition().isNote() && !itemStack.getDefinition().isStackable()) {
            int n = 0;
            while (n < itemStack.getAmount()) {
                boolean bl = false;
                int n2 = 0;
                while (n2 < this.h.size()) {
                    if (((ItemStack)this.h.get(n2)).getId() == itemStack.getId() && !bl) {
                        this.h.remove(n2);
                        this.a.getInventoryManager().addItem(new ItemStack(itemStack.getId()));
                        bl = true;
                    }
                    ++n2;
                }
                ++n;
            }
        } else {
            int n = 0;
            while (n < this.h.size()) {
                if (((ItemStack)this.h.get(n)).getId() == itemStack.getId()) {
                    if (itemStack.getAmount() >= ((ItemStack)this.h.get(n)).getAmount()) {
                        int n3 = ((ItemStack)this.h.get(n)).getAmount();
                        this.h.remove(n);
                        this.a.getInventoryManager().addItem(new ItemStack(itemStack.getId(), n3));
                    } else {
                        ((ItemStack)this.h.get(n)).setAmount(((ItemStack)this.h.get(n)).getAmount() - itemStack.getAmount());
                        this.a.getInventoryManager().addItem(new ItemStack(itemStack.getId(), itemStack.getAmount()));
                    }
                }
                ++n;
            }
        }
        this.a.getDuelController().b(false);
        this.b.getDuelController().b(false);
        this.a.getDuelInterfaceManager().c();
        this.b.getDuelInterfaceManager().c();
        this.a.getDuelInterfaceManager().e();
        this.b.getDuelInterfaceManager().e();
    }

    private boolean b(ItemStack itemStack) {
        int n = 0;
        int n2 = 0;
        while (n2 < this.h.size()) {
            if (((ItemStack)this.h.get(n2)).getId() == itemStack.getId()) {
                ++n;
            }
            ++n2;
        }
        return n >= itemStack.getAmount();
    }

    public final boolean e() {
        return this.c;
    }

    public final boolean f() {
        Object object;
        block6: {
            block5: {
                if (!this.a.isInDuelArena()) break block5;
                object = this;
                if (((DuelSession)object).c) break block6;
            }
            object = this.a;
            ((Player)object).packetSender.sendGameMessage("The duel hasn't started yet!");
        }
        if (this.a.isInDuelArena()) {
            object = this;
            if (((DuelSession)object).c) {
                return true;
            }
        }
        return false;
    }

    public final void g() {
        if (this.a.getSkillManager().getCurrentLevels()[3] < this.a.getSkillManager().getBaseLevel(3)) {
            this.a.getUpdateState().setGraphic(84);
            this.a.getUpdateState().setAnimation(866);
            Player player = this.a;
            player.packetSender.sendGameMessage("You have been healed.");
            this.a.getSkillManager().setCurrentLevel(3, this.a.getSkillManager().getBaseLevel(3));
            this.a.getSkillManager().refreshSkill(3);
            return;
        }
        Player player = this.a;
        player.packetSender.sendGameMessage("You are already very healthy.");
    }

    public final ArrayList h() {
        return this.h;
    }

    public final Player i() {
        return this.b;
    }

    public final void a(Player player) {
        this.b = player;
    }

    public final ArrayList j() {
        return this.e;
    }

    public final boolean[] k() {
        return this.g;
    }

    public final ArrayList l() {
        return this.f;
    }

    public final void b(boolean bl) {
        this.c = bl;
    }

    static /* synthetic */ Player a(DuelSession duelSession) {
        return duelSession.a;
    }
}

