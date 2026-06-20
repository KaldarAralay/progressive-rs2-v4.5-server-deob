/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.player;

import com.rs2.ServerSettings;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.Entity;
import com.rs2.model.EntityTargetMovement;
import com.rs2.model.Position;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.fletching.BowStringingDefinition;
import com.rs2.model.skill.fletching.BowStringingTask;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;
import com.rs2.util.TextUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerGroup {
    public Player a;
    public CopyOnWriteArrayList b = new CopyOnWriteArrayList();
    public CopyOnWriteArrayList c = new CopyOnWriteArrayList();
    public Boolean d = true;

    public static boolean handleBowStringing(Player player, int n, int n2) {
        BowStringingDefinition bowStringingDefinition = BowStringingDefinition.forComponents(n, n2);
        if (bowStringingDefinition == null) {
            return false;
        }
        if (!ServerSettings.fletchingEnabled) {
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return false;
        }
        if (player.getSkillManager().getCurrentLevels()[9] < bowStringingDefinition.getRequiredLevel()) {
            player.getDialogueManager().showOneLineStatement("You need a fletching level of " + bowStringingDefinition.getRequiredLevel() + " to do this");
            return true;
        }
        if (!player.getInventoryManager().getContainer().containsItem(bowStringingDefinition.getUnstrungBowItemId()) || !player.getInventoryManager().getContainer().containsItem(bowStringingDefinition.getBowStringItemId())) {
            player.getDialogueManager().showOneLineStatement("You need a " + new ItemStack(bowStringingDefinition.getUnstrungBowItemId()).getDefinition().getName().toLowerCase() + " and a " + new ItemStack(bowStringingDefinition.getBowStringItemId()).getDefinition().getName().toLowerCase() + " to make this");
            return true;
        }
        n2 = player.nextActionSequence();
        player.setActiveCycleEvent(new BowStringingTask(player, n2, bowStringingDefinition));
        CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 1);
        return true;
    }

    public PlayerGroup(Player player, Player player2) {
        if (player.q != null || player2.q != null) {
            return;
        }
        this.a = player;
        this.b.add(player);
        this.b.add(player2);
        player.q = this;
        player2.q = this;
        Player player3 = player;
        player3.packetSender.sendGameMessage(String.valueOf(player2.getUsername()) + " joined your group.");
        player3 = player2;
        player3.packetSender.sendGameMessage("You joined a group lead by: " + player.getUsername());
        this.b(player2);
    }

    public boolean a() {
        return this.b.size() >= 5;
    }

    public void b() {
        Player player = this.a;
        Iterator iterator = this.b.iterator();
        while (iterator.hasNext()) {
            Player player2 = (Player)iterator.next();
            if (this.c.contains(player2)) {
                iterator.remove();
                continue;
            }
            CombatManager.stopCombat(player2);
            if (player2 == this.a || BotCombatHelper.hasExternalCombatTarget(player2)) continue;
            player2.getUpdateState().setFaceEntity(player.getEncodedIndex());
            player2.setAttackRange(1);
            player2.setMovementTarget(player);
            player = player2;
        }
    }

    public boolean a(Player player) {
        Iterator iterator = this.b.iterator();
        while (iterator.hasNext()) {
            Player player2 = (Player)iterator.next();
            if (this.c.contains(player2)) {
                iterator.remove();
                continue;
            }
            if (player2 != player) continue;
            return true;
        }
        return false;
    }

    public void a(Entity entity) {
        Iterator iterator = this.b.iterator();
        while (iterator.hasNext()) {
            Player player = (Player)iterator.next();
            if (this.c.contains(player)) {
                iterator.remove();
                continue;
            }
            if (entity == null || entity.isDead() || player == null || player.isDead()) continue;
            CombatManager.startCombat(player, entity);
        }
    }

    public void b(Player player) {
        player.packetSender.sendGameMessage("Group members: (size:" + this.b.size() + ", roll loot:" + this.d + ")");
        Iterator iterator = this.b.iterator();
        while (iterator.hasNext()) {
            Player player2 = (Player)iterator.next();
            if (this.c.contains(player2)) {
                iterator.remove();
                continue;
            }
            String string = "";
            if (player2 == this.a) {
                string = " (leader)";
            }
            player.packetSender.sendGameMessage(String.valueOf(player2.getUsername()) + string);
        }
    }

    public int c() {
        int n = 0;
        Iterator iterator = this.b.iterator();
        while (iterator.hasNext()) {
            Player player = (Player)iterator.next();
            if (this.c.contains(player)) {
                iterator.remove();
                continue;
            }
            if (player.getCombatLevel() <= n) continue;
            n = player.getCombatLevel();
        }
        return n;
    }

    public int d() {
        int n = 0;
        Iterator iterator = this.b.iterator();
        while (iterator.hasNext()) {
            Player player = (Player)iterator.next();
            if (this.c.contains(player)) {
                iterator.remove();
                continue;
            }
            n += player.getCombatLevel();
        }
        return n;
    }

    public void c(Player player) {
        Player player2;
        if (this.b.size() >= 5) {
            return;
        }
        if (player.q != null) {
            return;
        }
        Iterator iterator = this.b.iterator();
        while (iterator.hasNext()) {
            player2 = (Player)iterator.next();
            if (this.c.contains(player2)) {
                iterator.remove();
                continue;
            }
            player2.packetSender.sendGameMessage(String.valueOf(player.getUsername()) + " joined the group.");
        }
        this.b.add(player);
        player.q = this;
        player2 = player;
        player2.packetSender.sendGameMessage("You joined a group lead by: " + this.a.getUsername());
        this.b(player);
    }

    public void d(Player player) {
        player.r = null;
        this.c.remove(player);
        int n = this.b.size() + this.c.size();
        if (n == 1) {
            if (this.b.size() == 1) {
                ((Player)this.b.get((int)0)).q = null;
                ((Player)this.b.get((int)0)).r = null;
                this.b.clear();
                return;
            }
            if (this.c.size() == 1) {
                this.c.clear();
            }
            return;
        }
    }

    public void e(Player object) {
        boolean bl = false;
        if (((Player)object).de && !this.a.de) {
            this.a.botPvpTeamRequesters.remove(object);
            EntityTargetMovement.clearMovementTarget((Entity)object);
        }
        if (!((Player)object).de) {
            ((Player)object).botPvpTeamRequesters.clear();
            bl = true;
        }
        Player player = object;
        player.packetSender.sendGameMessage("You are no longer in the group.");
        boolean bl2 = false;
        if (this.a == object) {
            bl2 = true;
        }
        this.b.remove(object);
        ((Player)object).q = null;
        boolean bl3 = true;
        Iterator iterator = this.b.iterator();
        while (iterator.hasNext()) {
            Player player2 = (Player)iterator.next();
            if (this.c.contains(player2)) {
                iterator.remove();
                continue;
            }
            player = player2;
            player.packetSender.sendGameMessage(String.valueOf(((Player)object).getUsername()) + " left the group.");
            if (!player2.botEnabled) {
                bl3 = false;
                continue;
            }
            if (!bl || player2.botPvpRejectedTeamTargets.contains(object)) continue;
            player2.botPvpRejectedTeamTargets.add(object);
        }
        int n = this.b.size() + this.c.size();
        if (n == 1) {
            if (this.b.size() == 1) {
                ((Player)this.b.get((int)0)).q = null;
                ((Player)this.b.get((int)0)).r = null;
                this.b.clear();
                return;
            }
            if (this.c.size() == 1) {
                this.c.clear();
            }
            return;
        }
        if (bl2 && this.b.size() > 0) {
            this.a = (Player)this.b.get(0);
            object = this.b.iterator();
            while (object.hasNext()) {
                Player player3 = (Player)object.next();
                if (this.c.contains(player3)) {
                    object.remove();
                    continue;
                }
                player = player3;
                player.packetSender.sendGameMessage(String.valueOf(this.a.getUsername()) + " is the new group leader.");
            }
        }
        if (bl3) {
            EntityTargetMovement.clearMovementTarget(this.a);
            this.b();
        }
    }

    public static void a(Player player, Player player2) {
        if (player.getQuestState(0) != 1) {
            return;
        }
        Player player3 = player2;
        if (player3.s != player) {
            player3 = player;
            player3.packetSender.sendGameMessage("Sending group invite...");
            player3 = player2;
            player3.packetSender.sendGameMessage(TextUtil.capitalizeFirst(player.getUsername()) + ":grpinv:");
            Player player4 = player;
            player = player2;
            player3 = player4;
            player4.s = player;
            return;
        }
        if (player2.q == null) {
            if (player2.de) {
                new PlayerGroup(player, player2);
            } else {
                new PlayerGroup(player2, player);
            }
        } else {
            player2.q.c(player);
        }
        Player player5 = player;
        player = null;
        player3 = player5;
        player5.s = player;
        player = null;
        player3 = player2;
        player2.s = player;
    }

    public Entity a(Player player, Position object5) {
        if (this.d.booleanValue()) {
            Object object;
            Object object2;
            ArrayList<Player> arrayList = new ArrayList<Player>();
            ArrayList<Player> arrayList2 = new ArrayList<Player>();
            ArrayList<Player> arrayList3 = new ArrayList<Player>();
            Iterator iterator = this.b.iterator();
            while (iterator.hasNext()) {
                Player player2 = (Player)iterator.next();
                if (this.c.contains(player2)) {
                    iterator.remove();
                    continue;
                }
                if (GameUtil.b(player2.getPosition(), (Position)object5) <= 20) {
                    arrayList.add(player2);
                    continue;
                }
                object2 = player2;
                ((Player)object2).packetSender.sendGameMessage("You were too far away from target to roll for the loot.");
                arrayList3.add(player2);
            }
            for (Player player2 : arrayList) {
                object = arrayList3.iterator();
                while (object.hasNext()) {
                    Player player3 = (Player)object.next();
                    object2 = player2;
                    ((Player)object2).packetSender.sendGameMessage(String.valueOf(player3.getUsername()) + " was too far away to roll for the loot.");
                }
            }
            while (arrayList.size() > 1) {
                arrayList2.clear();
                int n = 0;
                for (Player player4 : arrayList) {
                    int n2;
                    player4.v = n2 = GameUtil.g(100);
                    if (n2 <= n) continue;
                    n = n2;
                }
                Iterator iterator2 = this.b.iterator();
                while (iterator2.hasNext()) {
                    Player player5 = (Player)iterator2.next();
                    if (this.c.contains(player5)) {
                        iterator2.remove();
                        continue;
                    }
                    for (Player player6 : arrayList) {
                        object2 = player5;
                        ((Player)object2).packetSender.sendGameMessage(String.valueOf(player6.getUsername()) + " rolled: " + player6.v);
                    }
                }
                for (Player player7 : arrayList) {
                    if (player7.v >= n) continue;
                    arrayList2.add(player7);
                }
                for (Player player8 : arrayList2) {
                    arrayList.remove(player8);
                }
                if (arrayList.size() == 1) continue;
                Iterator iterator3 = this.b.iterator();
                while (iterator3.hasNext()) {
                    object = (Player)iterator3.next();
                    if (this.c.contains(object)) {
                        iterator3.remove();
                        continue;
                    }
                    object2 = object;
                    ((Player)object2).packetSender.sendGameMessage("---");
                }
            }
            if (arrayList.size() == 1) {
                Iterator iterator4 = this.b.iterator();
                while (iterator4.hasNext()) {
                    Player player9 = (Player)iterator4.next();
                    if (this.c.contains(player9)) {
                        iterator4.remove();
                        continue;
                    }
                    object2 = player9;
                    ((Player)object2).packetSender.sendGameMessage(String.valueOf(((Player)arrayList.get(0)).getUsername()) + " won the loot.");
                }
                return (Entity)arrayList.get(0);
            }
        }
        return player;
    }
}

