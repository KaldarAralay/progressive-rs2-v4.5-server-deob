/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.magetrainingarena;

import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.gameplay.magetrainingarena.AlchemistPlaygroundRotationTask;
import com.rs2.model.gameplay.magetrainingarena.MageTrainingArenaLobby;
import com.rs2.model.item.ItemService;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.player.Player;
import com.rs2.net.packet.PacketSender;
import java.util.Random;

public final class AlchemistPlaygroundController {
    private Player d;
    private int e;
    public int a;
    private static final Position[] f = new Position[]{new Position(3363, 9623, 2), new Position(3366, 9623, 2)};
    private static int[] g = new int[]{30, 15, 8, 5, 1};
    private static int[] h = new int[]{6893, 6894, 6895, 6896, 6897};
    static Random b = new Random();
    private static int i;
    private static int j;
    private static int k;
    private static int l;
    private static int m;
    private static int n;
    private static int o;
    private static int p;
    private static int q;
    private static int r;
    public static int c;

    private static Npc h() {
        Npc[] npcArray = World.g();
        int n = npcArray.length;
        int n2 = 0;
        while (n2 < n) {
            Npc npc = npcArray[n2];
            if (npc != null && npc.getDefinition().getId() == 3099) {
                return npc;
            }
            ++n2;
        }
        return null;
    }

    public AlchemistPlaygroundController(Player player) {
        this.d = player;
    }

    public static void a() {
        m = 10791;
        l = 10789;
        k = 10787;
        j = 10785;
        i = 10783;
        r = 30;
        q = 15;
        p = 8;
        o = 5;
        n = 1;
        c = 6897;
        World.scheduleTickTask(new AlchemistPlaygroundRotationTask(70));
    }

    private static int c(int n) {
        n = n == 10797 ? 10783 : (n += 2);
        return n;
    }

    public final boolean b() {
        int n = this.d.getPosition().getX();
        int n2 = this.d.getPosition().getY();
        return this.d.getPosition().getPlane() == 2 && n >= 3355 && n <= 3390 && n2 >= 9600 && n2 <= 9665;
    }

    public final void c() {
        Player player = this.d;
        player.packetSender.showWalkableInterface(15892);
        player = this.d;
        player.packetSender.sendInterfaceText("" + this.a, 15896);
        player = this.d;
        player.packetSender.sendInterfaceText("" + r, 15902);
        player = this.d;
        player.packetSender.sendInterfaceText("" + q, 15903);
        player = this.d;
        player.packetSender.sendInterfaceText("" + p, 15904);
        player = this.d;
        player.packetSender.sendInterfaceText("" + o, 15905);
        player = this.d;
        player.packetSender.sendInterfaceText("" + n, 15906);
    }

    public final void d() {
        AlchemistPlaygroundController alchemistPlaygroundController = this;
        Player player = alchemistPlaygroundController.d;
        player.packetSender.setInterfaceHiddenFlag(1, 15907);
        player = alchemistPlaygroundController.d;
        player.packetSender.setInterfaceHiddenFlag(1, 15908);
        player = alchemistPlaygroundController.d;
        player.packetSender.setInterfaceHiddenFlag(1, 15909);
        player = alchemistPlaygroundController.d;
        player.packetSender.setInterfaceHiddenFlag(1, 15910);
        player = alchemistPlaygroundController.d;
        player.packetSender.setInterfaceHiddenFlag(1, 15911);
        if (c == 6893) {
            player = this.d;
            player.packetSender.setInterfaceHiddenFlag(0, 15907);
            return;
        }
        if (c == 6894) {
            player = this.d;
            player.packetSender.setInterfaceHiddenFlag(0, 15908);
            return;
        }
        if (c == 6895) {
            player = this.d;
            player.packetSender.setInterfaceHiddenFlag(0, 15909);
            return;
        }
        if (c == 6896) {
            player = this.d;
            player.packetSender.setInterfaceHiddenFlag(0, 15910);
            return;
        }
        if (c == 6897) {
            player = this.d;
            player.packetSender.setInterfaceHiddenFlag(0, 15911);
        }
    }

    public final boolean a(int n, int n2, int n3) {
        if (n == m || n == l || n == k || n == j || n == i) {
            if (this.b()) {
                if (this.d.getInventoryManager().getContainer().getFreeSlots() <= 0) {
                    Player player = this.d;
                    player.packetSender.sendGameMessage("Not enough space in your inventory.");
                    return true;
                }
                int n4 = n;
                n4 = n4 == m ? 6893 : (n4 == l ? 6894 : (n4 == k ? 6895 : (n4 == j ? 6896 : (n4 == i ? 6897 : -1))));
                this.d.getInventoryManager().addItem(new ItemStack(n4));
                this.d.getUpdateState().setAnimation(832, 0);
                Player player = this.d;
                PacketSender packetSender = player.packetSender;
                StringBuilder stringBuilder = new StringBuilder("You found : ");
                ItemService.getInstance();
                packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(n4)).append(".").toString());
                int n5 = n;
                int n6 = n2;
                n2 = n3;
                n = n6;
                n4 = n5;
                new DynamicObject(n4 + 1, n, n2, 0, n4 < 10791 ? 1 : 3, 10, n4, 5);
                return true;
            }
        } else {
            if (n == 10734) {
                if (this.b()) {
                    AlchemistPlaygroundController alchemistPlaygroundController = this;
                    if (!alchemistPlaygroundController.d.getInventoryManager().getContainer().containsItem(995)) {
                        Player player = alchemistPlaygroundController.d;
                        player.packetSender.sendGameMessage("You don't have any coins to deposit.");
                    } else {
                        n = alchemistPlaygroundController.d.getInventoryManager().getContainer().getItemAmount(995);
                        if (n > 12000) {
                            n = 12000;
                        }
                        alchemistPlaygroundController.d.getSkillManager().addExperience(6, n << 1);
                        n2 = (int)Math.floor(n / 100);
                        if (alchemistPlaygroundController.a + n2 > 8000) {
                            n2 = 8000 - alchemistPlaygroundController.a;
                        }
                        if (alchemistPlaygroundController.d.eH()) {
                            alchemistPlaygroundController.a += n2;
                        }
                        alchemistPlaygroundController.e += n2 * 10;
                        alchemistPlaygroundController.d.getInventoryManager().removeItem(new ItemStack(995, n));
                        alchemistPlaygroundController.d.getUpdateState().setAnimation(832, 0);
                        Player player = alchemistPlaygroundController.d;
                        player.packetSender.showChatboxInterface(363);
                        player = alchemistPlaygroundController.d;
                        player.packetSender.sendInterfaceText("You've just deposited " + n + " coins, earning you " + n2 + " Alchemist Pizazz", 364);
                        player = alchemistPlaygroundController.d;
                        player.packetSender.sendInterfaceText("Points and " + (n << 1) + " magic XP. So far you're taking " + alchemistPlaygroundController.e + " coins as a", 365);
                        player = alchemistPlaygroundController.d;
                        player.packetSender.sendInterfaceText("reward when you leave!", 366);
                    }
                }
                return true;
            }
            if (n == 10782 && this.b()) {
                AlchemistPlaygroundController alchemistPlaygroundController = this;
                n = alchemistPlaygroundController.d.getInventoryManager().getContainer().getItemAmount(995);
                Player player = alchemistPlaygroundController.d;
                player.packetSender.showWalkableInterface(-1);
                Player player2 = alchemistPlaygroundController.d;
                player2.getInventoryManager().removeItem(new ItemStack(6893, player2.getInventoryManager().getItemAmount(6893)));
                player2.getInventoryManager().removeItem(new ItemStack(6894, player2.getInventoryManager().getItemAmount(6894)));
                player2.getInventoryManager().removeItem(new ItemStack(6895, player2.getInventoryManager().getItemAmount(6895)));
                player2.getInventoryManager().removeItem(new ItemStack(6897, player2.getInventoryManager().getItemAmount(6897)));
                player2.getInventoryManager().removeItem(new ItemStack(6896, player2.getInventoryManager().getItemAmount(6896)));
                alchemistPlaygroundController.d.getInventoryManager().removeItem(new ItemStack(995, n));
                if (alchemistPlaygroundController.e != 0) {
                    alchemistPlaygroundController.d.getInventoryManager().addItem(new ItemStack(995, alchemistPlaygroundController.e));
                }
                alchemistPlaygroundController.d.moveTo(MageTrainingArenaLobby.a);
                player = alchemistPlaygroundController.d;
                player.packetSender.sendGameMessage("You've left the Alchemists' Playground " + (alchemistPlaygroundController.e != 0 ? "and you get some coins as reward" : "") + ".");
                alchemistPlaygroundController.e = 0;
                return true;
            }
            if (n == 10780) {
                AlchemistPlaygroundController alchemistPlaygroundController = this;
                n = b.nextInt(2);
                if (alchemistPlaygroundController.d.getSkillManager().getCurrentLevels()[6] < 21) {
                    Player player = alchemistPlaygroundController.d;
                    player.packetSender.sendGameMessage("You need a magic level of 21 to enter here.");
                } else if (alchemistPlaygroundController.d.getInventoryManager().getContainer().containsItem(995)) {
                    Player player = alchemistPlaygroundController.d;
                    player.packetSender.sendGameMessage("You can't take coins in the Alchemists' Playground.");
                } else {
                    alchemistPlaygroundController.d.moveTo(f[n]);
                    Player player = alchemistPlaygroundController.d;
                    player.packetSender.sendGameMessage("You've entered the Alchemists' Playground.");
                    alchemistPlaygroundController.d.getAlchemistPlaygroundController().d();
                }
                return true;
            }
            if (this.b()) {
                Player player = this.d;
                player.packetSender.sendGameMessage("The cupboard is empty.");
                return true;
            }
        }
        return false;
    }

    private static int d(int n) {
        if (n == 6893) {
            return r;
        }
        if (n == 6894) {
            return q;
        }
        if (n == 6895) {
            return p;
        }
        if (n == 6896) {
            return o;
        }
        if (n == 6897) {
            return AlchemistPlaygroundController.n;
        }
        return 0;
    }

    public static boolean a(int n) {
        return AlchemistPlaygroundController.d(n) != 0;
    }

    public final void b(int n) {
        if (AlchemistPlaygroundController.d(n) == 0) {
            Player player = this.d;
            player.packetSender.sendGameMessage("You can't alch this item here.");
            return;
        }
        this.d.getInventoryManager().removeItem(new ItemStack(n));
        this.d.getInventoryManager().addItem(new ItemStack(995, AlchemistPlaygroundController.d(n)));
        if (n == c) {
            Player player = this.d;
            player.packetSender.sendGameMessage("Item converted for free!");
        }
    }

    static /* synthetic */ void e() {
        i = AlchemistPlaygroundController.c(i);
        j = AlchemistPlaygroundController.c(j);
        k = AlchemistPlaygroundController.c(k);
        l = AlchemistPlaygroundController.c(l);
        m = AlchemistPlaygroundController.c(m);
        Npc npc = AlchemistPlaygroundController.h();
        if (npc != null) {
            AlchemistPlaygroundController.h().getUpdateState().setForcedTextAndMarkUpdated("The costs are changing!");
        }
    }

    static /* synthetic */ void f() {
        int[] nArray = new int[5];
        int n = b.nextInt(4);
        int n2 = 0;
        while (n2 < 5) {
            nArray[n] = g[n2];
            n = n <= 0 ? (n += 4) : --n;
            ++n2;
        }
        AlchemistPlaygroundController.n = nArray[0];
        o = nArray[1];
        p = nArray[2];
        q = nArray[3];
        r = nArray[4];
    }

    static /* synthetic */ int[] g() {
        return h;
    }
}

