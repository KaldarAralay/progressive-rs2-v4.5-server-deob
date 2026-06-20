/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.item.action;

import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.item.action.GodBookRecitationEvent;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class GodBookHandler {
    public static int a = 3839;
    public static int b = 3840;
    public static int c = 3841;
    public static int d = 3842;
    public static int e = 3843;
    public static int f = 3844;
    private static int g = 3827;
    private static int h = 3828;
    private static int i = 3829;
    private static int j = 3830;
    private static int k = 3831;
    private static int l = 3832;
    private static int m = 3833;
    private static int n = 3834;
    private static int o = 3835;
    private static int p = 3836;
    private static int q = 3837;
    private static int r = 3838;
    private static int s = 1;
    private static int t = 2;
    private static int u = 3;
    private static int v = 4;
    private static int w = 5;
    private static int x = 6;
    private static int y = 7;
    private static int z = 8;
    private static int A = 9;
    private static int B = 10;
    private static int C = 11;
    private static int D = 12;
    private static int[] E = new int[]{s, t, u, v};
    private static int[] F = new int[]{w, x, y, z};
    private static int[] G = new int[]{A, B, C, D};
    private static int H = 1335;
    private static int I = 1336;
    private static int J = 1337;
    private static int K = 1;
    private static int L = 2;
    private static int M = 3;
    private static int N = 4;
    private static String[] O = new String[]{"In the name of Saradomin,", "Protector of us all,", "I now join you in the eyes of Saradomin."};
    private static String[] P = new String[]{"Thy cause was false, thy skills did lack;", "See you in Lumbridge when you get back."};
    private static String[] Q = new String[]{"Go in peace in the name of Saradomin;", "May his glory shine upon you like the sun."};
    private static String[] R = new String[]{"Walk proud, and show mercy.", "For you carry my name in your heart.", "This is Saradomin's wisdom."};
    private static String[] S = new String[]{"Two great warriors, joined by hand,", "to spread destruct, on across the land.", "In Zamorak's name, now two are one."};
    private static String[] T = new String[]{"The weak deserve to die,", "So that the strong may flourish.", "This is the creed of Zamorak."};
    private static String[] U = new String[]{"May your bloodthirst be never sated.", "and may all your battles be glorious.", "Zamorak bring you strength."};
    private static String[] V = new String[]{"There is no opinion that cannot be proven true,", "by crushing those who choose to disagree with it.", "Zamorak give me strength!"};
    private static String[] W = new String[]{"Light and dark, day and night,", "Balance arises from contrast.", "I unify thee in the name of Guthix."};
    private static String[] X = new String[]{"Thy death was not in vain,", "for it brought some balance to the world.", "May Guthix bring you rest."};
    private static String[] Y = new String[]{"May you walk the path, and never fall,", "For Guthix walks beside thee on thy journey.", "May Guthix bring you peace."};
    private static String[] Z = new String[]{"A journey of a single step,", "May take thee over a thousand miles.", "May Guthix bring you balance."};

    public static boolean a(Player player, int n) {
        if (n == a || n == c || n == e) {
            ArrayList arrayList = GodBookHandler.e(player, n);
            String string = "";
            int n2 = 0;
            while (n2 < arrayList.size()) {
                if (n2 > 0) {
                    string = String.valueOf(string) + ", ";
                }
                string = String.valueOf(string) + arrayList.get(n2);
                ++n2;
            }
            player.packetSender.sendGameMessage("The book is missing the following pages: " + string);
            return true;
        }
        return false;
    }

    private static ArrayList e(Player player, int n) {
        ArrayList<Integer> arrayList;
        block7: {
            block8: {
                block6: {
                    arrayList = new ArrayList<Integer>();
                    if (n != a) break block6;
                    n = 0;
                    while (n < E.length) {
                        if ((player.u & GameUtil.b(E[n])) == 0) {
                            arrayList.add(n + 1);
                        }
                        ++n;
                    }
                    break block7;
                }
                if (n != c) break block8;
                n = 0;
                while (n < F.length) {
                    if ((player.u & GameUtil.b(F[n])) == 0) {
                        arrayList.add(n + 1);
                    }
                    ++n;
                }
                break block7;
            }
            if (n != e) break block7;
            n = 0;
            while (n < G.length) {
                if ((player.u & GameUtil.b(G[n])) == 0) {
                    arrayList.add(n + 1);
                }
                ++n;
            }
        }
        return arrayList;
    }

    public static boolean a(Player player, int n, int n2) {
        if (n == a || n2 == a) {
            if (n == g || n2 == g) {
                int n3 = E[0];
                if ((player.u & GameUtil.b(n3)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(g));
                    Player player2 = player;
                    player2.packetSender.sendGameMessage("You add the page to the book...");
                    player.u += GameUtil.b(n3);
                    GodBookHandler.b(player, n, n2);
                } else {
                    Player player3 = player;
                    player3.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
            if (n == h || n2 == h) {
                int n4 = E[1];
                if ((player.u & GameUtil.b(n4)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(h));
                    Player player4 = player;
                    player4.packetSender.sendGameMessage("You add the page to the book...");
                    player.u += GameUtil.b(n4);
                    GodBookHandler.b(player, n, n2);
                } else {
                    Player player5 = player;
                    player5.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
            if (n == i || n2 == i) {
                int n5 = E[2];
                if ((player.u & GameUtil.b(n5)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(i));
                    Player player6 = player;
                    player6.packetSender.sendGameMessage("You add the page to the book...");
                    player.u += GameUtil.b(n5);
                    GodBookHandler.b(player, n, n2);
                } else {
                    Player player7 = player;
                    player7.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
            if (n == j || n2 == j) {
                int n6 = E[3];
                if ((player.u & GameUtil.b(n6)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(j));
                    Player player8 = player;
                    player8.packetSender.sendGameMessage("You add the page to the book...");
                    player.u += GameUtil.b(n6);
                    GodBookHandler.b(player, n, n2);
                } else {
                    Player player9 = player;
                    player9.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
        }
        if (n == c || n2 == c) {
            if (n == k || n2 == k) {
                int n7 = F[0];
                if ((player.u & GameUtil.b(n7)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(k));
                    Player player10 = player;
                    player10.packetSender.sendGameMessage("You add the page to the book...");
                    player.u += GameUtil.b(n7);
                    GodBookHandler.b(player, n, n2);
                } else {
                    Player player11 = player;
                    player11.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
            if (n == l || n2 == l) {
                int n8 = F[1];
                if ((player.u & GameUtil.b(n8)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(l));
                    Player player12 = player;
                    player12.packetSender.sendGameMessage("You add the page to the book...");
                    player.u += GameUtil.b(n8);
                    GodBookHandler.b(player, n, n2);
                } else {
                    Player player13 = player;
                    player13.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
            if (n == m || n2 == m) {
                int n9 = F[2];
                if ((player.u & GameUtil.b(n9)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(m));
                    Player player14 = player;
                    player14.packetSender.sendGameMessage("You add the page to the book...");
                    player.u += GameUtil.b(n9);
                    GodBookHandler.b(player, n, n2);
                } else {
                    Player player15 = player;
                    player15.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
            if (n == GodBookHandler.n || n2 == GodBookHandler.n) {
                int n10 = F[3];
                if ((player.u & GameUtil.b(n10)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(GodBookHandler.n));
                    Player player16 = player;
                    player16.packetSender.sendGameMessage("You add the page to the book...");
                    player.u += GameUtil.b(n10);
                    GodBookHandler.b(player, n, n2);
                } else {
                    Player player17 = player;
                    player17.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
        }
        if (n == e || n2 == e) {
            if (n == o || n2 == o) {
                int n11 = G[0];
                if ((player.u & GameUtil.b(n11)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(o));
                    Player player18 = player;
                    player18.packetSender.sendGameMessage("You add the page to the book...");
                    player.u += GameUtil.b(n11);
                    GodBookHandler.b(player, n, n2);
                } else {
                    Player player19 = player;
                    player19.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
            if (n == p || n2 == p) {
                int n12 = G[1];
                if ((player.u & GameUtil.b(n12)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(p));
                    Player player20 = player;
                    player20.packetSender.sendGameMessage("You add the page to the book...");
                    player.u += GameUtil.b(n12);
                    GodBookHandler.b(player, n, n2);
                } else {
                    Player player21 = player;
                    player21.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
            if (n == q || n2 == q) {
                int n13 = G[2];
                if ((player.u & GameUtil.b(n13)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(q));
                    Player player22 = player;
                    player22.packetSender.sendGameMessage("You add the page to the book...");
                    player.u += GameUtil.b(n13);
                    GodBookHandler.b(player, n, n2);
                } else {
                    Player player23 = player;
                    player23.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
            if (n == r || n2 == r) {
                int n14 = G[3];
                if ((player.u & GameUtil.b(n14)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(r));
                    Player player24 = player;
                    player24.packetSender.sendGameMessage("You add the page to the book...");
                    player.u += GameUtil.b(n14);
                    GodBookHandler.b(player, n, n2);
                } else {
                    Player player25 = player;
                    player25.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
        }
        return false;
    }

    private static void b(Player player, int n, int n2) {
        ArrayList arrayList;
        if (n == a || n2 == a) {
            ArrayList arrayList2 = GodBookHandler.e(player, a);
            if (arrayList2.size() == 0) {
                player.getInventoryManager().removeItem(new ItemStack(a));
                player.getInventoryManager().addItem(new ItemStack(b));
                player.packetSender.sendGameMessage("The book is now complete!");
                return;
            }
        } else if (n == c || n2 == c) {
            ArrayList arrayList3 = GodBookHandler.e(player, c);
            if (arrayList3.size() == 0) {
                player.getInventoryManager().removeItem(new ItemStack(c));
                player.getInventoryManager().addItem(new ItemStack(d));
                player.packetSender.sendGameMessage("The book is now complete!");
                return;
            }
        } else if ((n == e || n2 == e) && (arrayList = GodBookHandler.e(player, e)).size() == 0) {
            player.getInventoryManager().removeItem(new ItemStack(e));
            player.getInventoryManager().addItem(new ItemStack(f));
            player.packetSender.sendGameMessage("The book is now complete!");
        }
    }

    public static void b(Player player, int n) {
        ArrayList arrayList = GodBookHandler.e(player, n);
        if (arrayList.size() == 0) {
            if (n == a) {
                n = b;
            } else if (n == c) {
                n = d;
            } else if (n == e) {
                n = f;
            }
        }
        player.getInventoryManager().b(new ItemStack(n, 1));
    }

    public static boolean c(Player player, int n) {
        if (n == b || n == d || n == f) {
            player.N = n;
            DialogueManager.startDialogue(player, 13001);
            return true;
        }
        return false;
    }

    public static void d(Player player, int n) {
        int n2 = player.N;
        int n3 = -1;
        player.O = 0;
        if (n2 == b) {
            n3 = H;
        } else if (n2 == d) {
            n3 = I;
        } else if (n2 == f) {
            n3 = J;
        }
        String[] stringArray = null;
        if (n == K) {
            if (n2 == b) {
                stringArray = O;
            } else if (n2 == d) {
                stringArray = S;
            } else if (n2 == f) {
                stringArray = W;
            }
        } else if (n == L) {
            if (n2 == b) {
                stringArray = P;
            } else if (n2 == d) {
                stringArray = T;
            } else if (n2 == f) {
                stringArray = X;
            }
        } else if (n == M) {
            if (n2 == b) {
                stringArray = Q;
            } else if (n2 == d) {
                stringArray = U;
            } else if (n2 == f) {
                stringArray = Y;
            }
        } else if (n == N) {
            if (n2 == b) {
                stringArray = R;
            } else if (n2 == d) {
                stringArray = V;
            } else if (n2 == f) {
                stringArray = Z;
            }
        }
        String[] stringArray2 = stringArray;
        player.n(true);
        player.getUpdateState().setAnimation(n3, 0);
        CycleEventHandler.getInstance().schedule(player, new GodBookRecitationEvent(player, stringArray2), 2);
    }
}

