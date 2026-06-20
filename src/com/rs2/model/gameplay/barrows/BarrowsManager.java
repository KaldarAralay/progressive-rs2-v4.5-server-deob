/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.barrows;

import com.rs2.ServerSettings;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.combat.AttackStyleDefinition;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.gameplay.barrows.BarrowsDoorPuzzle;
import com.rs2.model.gameplay.barrows.BarrowsPrayerDrainTask;
import com.rs2.model.gameplay.barrows.BarrowsRewardTable;
import com.rs2.model.gameplay.barrows.BarrowsTunnelRoom;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.GrandExchangeManager;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.util.CharacterFileManager;
import com.rs2.util.GameUtil;
import com.rs2.util.RectangularArea;
import java.util.ArrayList;

public final class BarrowsManager {
    private static int[][] c = new int[][]{{6823, 2030}, {6772, 2029}, {6822, 2028}, {6773, 2027}, {6771, 2026}, {6821, 2025}};
    private static Position[] d = new Position[]{new Position(3578, 9706, 3), new Position(3568, 9683, 3), new Position(3546, 9684, 3), new Position(3534, 9704, 3), new Position(3556, 9718, 3), new Position(3557, 9703, 3)};
    private static int[][] e = new int[][]{{4753, 4755, 4757, 4759}, {4745, 4747, 4749, 4751}, {4732, 4734, 4736, 4738}, {4724, 4726, 4728, 4730}, {4716, 4718, 4720, 4722}, {4708, 4710, 4712, 4714}};
    private static ArrayList f = new BarrowsRewardTable();
    private static int[] g = new int[]{1, 3, 7, 9};
    private static Position[] h = new Position[]{new Position(3535, 9710), new Position(3568, 9710), new Position(3535, 9676), new Position(3568, 9676)};
    private static int[] i = new int[]{4545, 4546, 4547};
    private static int[] j = new int[]{4550, 4551, 4552};
    public static final int[] a = new int[]{4761, 4762, 4763, 4764, 4765, 4766, 4767, 4768, 4769, 4770, 4771, 4772};
    public static final int[] b = new int[]{4537, 4538, 4539, 4540, 4541, 4542};
    private static int[] k = new int[]{2031, 2032, 2033, 2034, 2035, 2036, 2037};
    private static BarrowsTunnelRoom[] l = new BarrowsTunnelRoom[]{new BarrowsTunnelRoom(1, new int[]{2, 3, 4, 7}, new int[]{13, 10, 12, 11}, new RectangularArea(3529, 9706, 3540, 9717, 0)), new BarrowsTunnelRoom(2, new int[]{1, 3}, new int[]{13, 15}, new RectangularArea(3546, 9706, 3557, 9717, 0)), new BarrowsTunnelRoom(3, new int[]{1, 2, 6, 9}, new int[]{10, 15, 16, 17}, new RectangularArea(3563, 9706, 3574, 9717, 0)), new BarrowsTunnelRoom(4, new int[]{1, 7}, new int[]{12, 20}, new RectangularArea(3529, 9689, 3540, 9700, 0)), new BarrowsTunnelRoom(5, new int[]{2, 4, 6, 8}, new int[]{14, 18, 19, 21}, new RectangularArea(3546, 9689, 3557, 9700, 0)), new BarrowsTunnelRoom(6, new int[]{3, 9}, new int[]{16, 22}, new RectangularArea(3563, 9689, 3574, 9700, 0)), new BarrowsTunnelRoom(7, new int[]{1, 4, 8, 9}, new int[]{11, 20, 23, 25}, new RectangularArea(3529, 9672, 3540, 9683, 0)), new BarrowsTunnelRoom(8, new int[]{7, 9}, new int[]{23, 24}, new RectangularArea(3546, 9672, 3557, 9683, 0)), new BarrowsTunnelRoom(9, new int[]{3, 6, 7, 8}, new int[]{17, 22, 25, 24}, new RectangularArea(3563, 9672, 3574, 9683, 0))};
    private static BarrowsDoorPuzzle[] m = new BarrowsDoorPuzzle[]{new BarrowsDoorPuzzle(new int[]{6716, 6717, 6718}, new int[]{6713, 6714, 6715}), new BarrowsDoorPuzzle(new int[]{6722, 6723, 6724}, new int[]{6719, 6720, 6721}), new BarrowsDoorPuzzle(new int[]{6728, 6729, 6730}, new int[]{6725, 6726, 6727}), new BarrowsDoorPuzzle(new int[]{6734, 6735, 6736}, new int[]{6731, 6732, 6733})};

    public static boolean a(Player player, int n, int n2, int n3) {
        if (n == 6709 && n2 == 3534 && n3 == 9712) {
            n = (int)Math.pow(2.0, 6.0);
            if ((player.ep[452] & n) == 0 || player.ep[452] == 0) {
                return false;
            }
            AttackStyleDefinition.a(player, d[player.eU()]);
            return true;
        }
        if (n == 6717 && n2 == 3528 && n3 == 9711 || n == 6736 && n2 == 3528 && n3 == 9712) {
            n = (int)Math.pow(2.0, 11.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getX() < 3529 ? 1 : -1;
            Player player2 = player;
            player2.packetSender.queueRelativeMovementStep(n, 0, true);
            player2 = player;
            player2.packetSender.openDoubleDoorPair(6736, 6717, 3528, 9712, 3528, 9711, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX() + n, player.getPosition().getY()));
            return true;
        }
        if (n == 6719 && n2 == 3541 && n3 == 9712 || n == 6738 && n2 == 3541 && n3 == 9711) {
            n = (int)Math.pow(2.0, 13.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getX() < 3541 ? 1 : -1;
            Player player3 = player;
            player3.packetSender.queueRelativeMovementStep(n, 0, true);
            player3 = player;
            player3.packetSender.openDoubleDoorPair(6738, 6719, 3541, 9711, 3541, 9712, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX() + n, player.getPosition().getY()));
            return true;
        }
        if (n == 6716 && n2 == 3534 && n3 == 9718 || n == 6735 && n2 == 3535 && n3 == 9718) {
            n = (int)Math.pow(2.0, 10.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getY() < 9718 ? 1 : -1;
            Player player4 = player;
            player4.packetSender.queueRelativeMovementStep(0, n, true);
            player4 = player;
            player4.packetSender.openDoubleDoorPair(6735, 6716, 3535, 9718, 3534, 9718, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX(), player.getPosition().getY() + n));
            return true;
        }
        if (n == 6718 && n2 == 3535 && n3 == 9705 || n == 6737 && n2 == 3534 && n3 == 9705) {
            n = (int)Math.pow(2.0, 12.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getY() < 9706 ? 1 : -1;
            Player player5 = player;
            player5.packetSender.queueRelativeMovementStep(0, n, true);
            player5 = player;
            player5.packetSender.openDoubleDoorPair(6737, 6718, 3534, 9705, 3535, 9705, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX(), player.getPosition().getY() + n));
            return true;
        }
        if (n == 6719 && n2 == 3545 && n3 == 9711 || n == 6738 && n2 == 3545 && n3 == 9712) {
            n = (int)Math.pow(2.0, 13.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getX() < 3546 ? 1 : -1;
            Player player6 = player;
            player6.packetSender.queueRelativeMovementStep(n, 0, true);
            player6 = player;
            player6.packetSender.openDoubleDoorPair(6738, 6719, 3545, 9712, 3545, 9711, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX() + n, player.getPosition().getY()));
            return true;
        }
        if (n == 6721 && n2 == 3558 && n3 == 9712 || n == 6740 && n2 == 3558 && n3 == 9711) {
            n = (int)Math.pow(2.0, 15.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getX() < 3558 ? 1 : -1;
            Player player7 = player;
            player7.packetSender.queueRelativeMovementStep(n, 0, true);
            player7 = player;
            player7.packetSender.openDoubleDoorPair(6740, 6721, 3558, 9711, 3558, 9712, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX() + n, player.getPosition().getY()));
            return true;
        }
        if (n == 6720 && n2 == 3552 && n3 == 9705 || n == 6739 && n2 == 3551 && n3 == 9705) {
            n = (int)Math.pow(2.0, 14.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            if (player.bQ) {
                n = player.getPosition().getY() < 9706 ? 1 : -1;
                Player player8 = player;
                player8.packetSender.queueRelativeMovementStep(0, n, true);
                player8 = player;
                player8.packetSender.openDoubleDoorPair(6739, 6720, 3551, 9705, 3552, 9705, 0);
                BarrowsManager.a(player, new Position(player.getPosition().getX(), player.getPosition().getY() + n));
            } else {
                BarrowsManager.g(player);
            }
            return true;
        }
        if (n == 6710 && n2 == 3568 && n3 == 9712) {
            n = (int)Math.pow(2.0, 7.0);
            if ((player.ep[452] & n) == 0 || player.ep[452] == 0) {
                return false;
            }
            AttackStyleDefinition.a(player, d[player.eU()]);
            return true;
        }
        if (n == 6721 && n2 == 3562 && n3 == 9711 || n == 6740 && n2 == 3562 && n3 == 9712) {
            n = (int)Math.pow(2.0, 15.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getX() < 3563 ? 1 : -1;
            Player player9 = player;
            player9.packetSender.queueRelativeMovementStep(n, 0, true);
            player9 = player;
            player9.packetSender.openDoubleDoorPair(6740, 6721, 3562, 9712, 3562, 9711, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX() + n, player.getPosition().getY()));
            return true;
        }
        if (n == 6723 && n2 == 3575 && n3 == 9712 || n == 6742 && n2 == 3575 && n3 == 9711) {
            n = (int)Math.pow(2.0, 17.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getX() < 3575 ? 1 : -1;
            Player player10 = player;
            player10.packetSender.queueRelativeMovementStep(n, 0, true);
            player10 = player;
            player10.packetSender.openDoubleDoorPair(6742, 6723, 3575, 9711, 3575, 9712, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX() + n, player.getPosition().getY()));
            return true;
        }
        if (n == 6716 && n2 == 3568 && n3 == 9718 || n == 6735 && n2 == 3569 && n3 == 9718) {
            n = (int)Math.pow(2.0, 10.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getY() < 9718 ? 1 : -1;
            Player player11 = player;
            player11.packetSender.queueRelativeMovementStep(0, n, true);
            player11 = player;
            player11.packetSender.openDoubleDoorPair(6735, 6716, 3569, 9718, 3568, 9718, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX(), player.getPosition().getY() + n));
            return true;
        }
        if (n == 6722 && n2 == 3569 && n3 == 9705 || n == 6741 && n2 == 3568 && n3 == 9705) {
            n = (int)Math.pow(2.0, 16.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getY() < 9706 ? 1 : -1;
            Player player12 = player;
            player12.packetSender.queueRelativeMovementStep(0, n, true);
            player12 = player;
            player12.packetSender.openDoubleDoorPair(6741, 6722, 3568, 9705, 3569, 9705, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX(), player.getPosition().getY() + n));
            return true;
        }
        if (n == 6718 && n2 == 3534 && n3 == 9701 || n == 6737 && n2 == 3535 && n3 == 9701) {
            n = (int)Math.pow(2.0, 12.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getY() < 9701 ? 1 : -1;
            Player player13 = player;
            player13.packetSender.queueRelativeMovementStep(0, n, true);
            player13 = player;
            player13.packetSender.openDoubleDoorPair(6737, 6718, 3535, 9701, 3534, 9701, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX(), player.getPosition().getY() + n));
            return true;
        }
        if (n == 6726 && n2 == 3535 && n3 == 9688 || n == 6745 && n2 == 3534 && n3 == 9688) {
            n = (int)Math.pow(2.0, 20.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getY() < 9689 ? 1 : -1;
            Player player14 = player;
            player14.packetSender.queueRelativeMovementStep(0, n, true);
            player14 = player;
            player14.packetSender.openDoubleDoorPair(6745, 6726, 3534, 9688, 3535, 9688, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX(), player.getPosition().getY() + n));
            return true;
        }
        if (n == 6724 && n2 == 3541 && n3 == 9695 || n == 6743 && n2 == 3541 && n3 == 9694) {
            n = (int)Math.pow(2.0, 18.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            if (player.bQ) {
                n = player.getPosition().getX() < 3541 ? 1 : -1;
                Player player15 = player;
                player15.packetSender.queueRelativeMovementStep(n, 0, true);
                player15 = player;
                player15.packetSender.openDoubleDoorPair(6743, 6724, 3541, 9694, 3541, 9695, 0);
                BarrowsManager.a(player, new Position(player.getPosition().getX() + n, player.getPosition().getY()));
            } else {
                BarrowsManager.g(player);
            }
            return true;
        }
        if (n == 6724 && n2 == 3545 && n3 == 9694 || n == 6743 && n2 == 3545 && n3 == 9695) {
            n = (int)Math.pow(2.0, 18.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getX() < 3546 ? 1 : -1;
            Player player16 = player;
            player16.packetSender.queueRelativeMovementStep(n, 0, true);
            player16 = player;
            player16.packetSender.openDoubleDoorPair(6743, 6724, 3545, 9695, 3545, 9694, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX() + n, player.getPosition().getY()));
            return true;
        }
        if (n == 6720 && n2 == 3551 && n3 == 9701 || n == 6739 && n2 == 3552 && n3 == 9701) {
            n = (int)Math.pow(2.0, 14.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getY() < 9701 ? 1 : -1;
            Player player17 = player;
            player17.packetSender.queueRelativeMovementStep(0, n, true);
            player17 = player;
            player17.packetSender.openDoubleDoorPair(6739, 6720, 3552, 9701, 3551, 9701, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX(), player.getPosition().getY() + n));
            return true;
        }
        if (n == 6725 && n2 == 3558 && n3 == 9695 || n == 6744 && n2 == 3558 && n3 == 9694) {
            n = (int)Math.pow(2.0, 19.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getX() < 3558 ? 1 : -1;
            Player player18 = player;
            player18.packetSender.queueRelativeMovementStep(n, 0, true);
            player18 = player;
            player18.packetSender.openDoubleDoorPair(6744, 6725, 3558, 9694, 3558, 9695, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX() + n, player.getPosition().getY()));
            return true;
        }
        if (n == 6727 && n2 == 3552 && n3 == 9688 || n == 6746 && n2 == 3551 && n3 == 9688) {
            n = (int)Math.pow(2.0, 21.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getY() < 9689 ? 1 : -1;
            Player player19 = player;
            player19.packetSender.queueRelativeMovementStep(0, n, true);
            player19 = player;
            player19.packetSender.openDoubleDoorPair(6746, 6727, 3551, 9688, 3552, 9688, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX(), player.getPosition().getY() + n));
            return true;
        }
        if (n == 6725 && n2 == 3562 && n3 == 9694 || n == 6744 && n2 == 3562 && n3 == 9695) {
            n = (int)Math.pow(2.0, 19.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            if (player.bQ) {
                n = player.getPosition().getX() < 3563 ? 1 : -1;
                Player player20 = player;
                player20.packetSender.queueRelativeMovementStep(n, 0, true);
                player20 = player;
                player20.packetSender.openDoubleDoorPair(6744, 6725, 3562, 9695, 3562, 9694, 0);
                BarrowsManager.a(player, new Position(player.getPosition().getX() + n, player.getPosition().getY()));
            } else {
                BarrowsManager.g(player);
            }
            return true;
        }
        if (n == 6722 && n2 == 3568 && n3 == 9701 || n == 6741 && n2 == 3569 && n3 == 9701) {
            n = (int)Math.pow(2.0, 16.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getY() < 9701 ? 1 : -1;
            Player player21 = player;
            player21.packetSender.queueRelativeMovementStep(0, n, true);
            player21 = player;
            player21.packetSender.openDoubleDoorPair(6741, 6722, 3569, 9701, 3568, 9701, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX(), player.getPosition().getY() + n));
            return true;
        }
        if (n == 6728 && n2 == 3569 && n3 == 9688 || n == 6747 && n2 == 3568 && n3 == 9688) {
            n = (int)Math.pow(2.0, 22.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getY() < 9689 ? 1 : -1;
            Player player22 = player;
            player22.packetSender.queueRelativeMovementStep(0, n, true);
            player22 = player;
            player22.packetSender.openDoubleDoorPair(6747, 6728, 3568, 9688, 3569, 9688, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX(), player.getPosition().getY() + n));
            return true;
        }
        if (n == 6711 && n2 == 3534 && n3 == 9678) {
            n = (int)Math.pow(2.0, 8.0);
            if ((player.ep[452] & n) == 0 || player.ep[452] == 0) {
                return false;
            }
            AttackStyleDefinition.a(player, d[player.eU()]);
            return true;
        }
        if (n == 6717 && n2 == 3528 && n3 == 9677 || n == 6736 && n2 == 3528 && n3 == 9678) {
            n = (int)Math.pow(2.0, 11.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getX() < 3529 ? 1 : -1;
            Player player23 = player;
            player23.packetSender.queueRelativeMovementStep(n, 0, true);
            player23 = player;
            player23.packetSender.openDoubleDoorPair(6736, 6717, 3528, 9678, 3528, 9677, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX() + n, player.getPosition().getY()));
            return true;
        }
        if (n == 6729 && n2 == 3541 && n3 == 9678 || n == 6748 && n2 == 3541 && n3 == 9677) {
            n = (int)Math.pow(2.0, 23.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getX() < 3541 ? 1 : -1;
            Player player24 = player;
            player24.packetSender.queueRelativeMovementStep(n, 0, true);
            player24 = player;
            player24.packetSender.openDoubleDoorPair(6748, 6729, 3541, 9677, 3541, 9678, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX() + n, player.getPosition().getY()));
            return true;
        }
        if (n == 6726 && n2 == 3534 && n3 == 9684 || n == 6745 && n2 == 3535 && n3 == 9684) {
            n = (int)Math.pow(2.0, 20.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getY() < 9684 ? 1 : -1;
            Player player25 = player;
            player25.packetSender.queueRelativeMovementStep(0, n, true);
            player25 = player;
            player25.packetSender.openDoubleDoorPair(6745, 6726, 3535, 9684, 3534, 9684, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX(), player.getPosition().getY() + n));
            return true;
        }
        if (n == 6731 && n2 == 3535 && n3 == 9671 || n == 6750 && n2 == 3534 && n3 == 9671) {
            n = (int)Math.pow(2.0, 25.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getY() < 9672 ? 1 : -1;
            Player player26 = player;
            player26.packetSender.queueRelativeMovementStep(0, n, true);
            player26 = player;
            player26.packetSender.openDoubleDoorPair(6750, 6731, 3534, 9671, 3535, 9671, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX(), player.getPosition().getY() + n));
            return true;
        }
        if (n == 6729 && n2 == 3545 && n3 == 9677 || n == 6748 && n2 == 3545 && n3 == 9678) {
            n = (int)Math.pow(2.0, 23.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getX() < 3546 ? 1 : -1;
            Player player27 = player;
            player27.packetSender.queueRelativeMovementStep(n, 0, true);
            player27 = player;
            player27.packetSender.openDoubleDoorPair(6748, 6729, 3545, 9678, 3545, 9677, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX() + n, player.getPosition().getY()));
            return true;
        }
        if (n == 6730 && n2 == 3558 && n3 == 9678 || n == 6749 && n2 == 3558 && n3 == 9677) {
            n = (int)Math.pow(2.0, 24.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getX() < 3558 ? 1 : -1;
            Player player28 = player;
            player28.packetSender.queueRelativeMovementStep(n, 0, true);
            player28 = player;
            player28.packetSender.openDoubleDoorPair(6749, 6730, 3558, 9677, 3558, 9678, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX() + n, player.getPosition().getY()));
            return true;
        }
        if (n == 6727 && n2 == 3551 && n3 == 9684 || n == 6746 && n2 == 3552 && n3 == 9684) {
            n = (int)Math.pow(2.0, 21.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            if (player.bQ) {
                n = player.getPosition().getY() < 9684 ? 1 : -1;
                Player player29 = player;
                player29.packetSender.queueRelativeMovementStep(0, n, true);
                player29 = player;
                player29.packetSender.openDoubleDoorPair(6746, 6727, 3552, 9684, 3551, 9684, 0);
                BarrowsManager.a(player, new Position(player.getPosition().getX(), player.getPosition().getY() + n));
            } else {
                BarrowsManager.g(player);
            }
            return true;
        }
        if (n == 6712 && n2 == 3568 && n3 == 9678) {
            n = (int)Math.pow(2.0, 9.0);
            if ((player.ep[452] & n) == 0 || player.ep[452] == 0) {
                return false;
            }
            AttackStyleDefinition.a(player, d[player.eU()]);
            return true;
        }
        if (n == 6730 && n2 == 3562 && n3 == 9677 || n == 6749 && n2 == 3562 && n3 == 9678) {
            n = (int)Math.pow(2.0, 24.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getX() < 3563 ? 1 : -1;
            Player player30 = player;
            player30.packetSender.queueRelativeMovementStep(n, 0, true);
            player30 = player;
            player30.packetSender.openDoubleDoorPair(6749, 6730, 3562, 9678, 3562, 9677, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX() + n, player.getPosition().getY()));
            return true;
        }
        if (n == 6723 && n2 == 3575 && n3 == 9678 || n == 6742 && n2 == 3575 && n3 == 9677) {
            n = (int)Math.pow(2.0, 17.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getX() < 3575 ? 1 : -1;
            Player player31 = player;
            player31.packetSender.queueRelativeMovementStep(n, 0, true);
            player31 = player;
            player31.packetSender.openDoubleDoorPair(6742, 6723, 3575, 9677, 3575, 9678, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX() + n, player.getPosition().getY()));
            return true;
        }
        if (n == 6728 && n2 == 3568 && n3 == 9684 || n == 6747 && n2 == 3569 && n3 == 9684) {
            n = (int)Math.pow(2.0, 22.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getY() < 9684 ? 1 : -1;
            Player player32 = player;
            player32.packetSender.queueRelativeMovementStep(0, n, true);
            player32 = player;
            player32.packetSender.openDoubleDoorPair(6747, 6728, 3569, 9684, 3568, 9684, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX(), player.getPosition().getY() + n));
            return true;
        }
        if (n == 6731 && n2 == 3569 && n3 == 9671 || n == 6750 && n2 == 3568 && n3 == 9671) {
            n = (int)Math.pow(2.0, 25.0);
            if ((player.ep[452] & n) != 0 || player.ep[452] == 0) {
                return false;
            }
            n = player.getPosition().getY() < 9672 ? 1 : -1;
            Player player33 = player;
            player33.packetSender.queueRelativeMovementStep(0, n, true);
            player33 = player;
            player33.packetSender.openDoubleDoorPair(6750, 6731, 3568, 9671, 3569, 9671, 0);
            BarrowsManager.a(player, new Position(player.getPosition().getX(), player.getPosition().getY() + n));
            return true;
        }
        return false;
    }

    private static void a(Player player, Position position) {
        int n = k[GameUtil.h(7)];
        GameplayHelper.a(player, position, new Npc(n), true, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean a(Player player, int n) {
        switch (n) {
            case 10284: {
                if (player.bU) {
                    return false;
                }
                int n2 = (int)Math.pow(2.0, 16.0);
                if ((player.ep[453] & n2) == 0 || player.ep[453] == 0) {
                    Player player2 = player;
                    player2.packetSender.sendConfig(453, n2);
                    player.ep[453] = n2;
                    if (player.eS()[player.eU()]) return true;
                    GameplayHelper.a(player, new Npc(c[player.eU()][1]), true, true);
                    return true;
                }
                if ((player.ep[453] & n2) != 0) {
                    if (player.eT() <= 0) {
                        Player player3 = player;
                        player3.packetSender.sendGameMessage("You search the chest but don't find anything.");
                        return true;
                    }
                    BarrowsManager.e(player);
                    return true;
                }
            }
            case 6774: {
                if (player.bU) {
                    return false;
                }
                int n2 = (int)Math.pow(2.0, 16.0);
                if ((player.ep[453] & n2) == 0 || player.ep[453] == 0) {
                    Player player4 = player;
                    player4.packetSender.sendConfig(453, n2);
                    player.ep[453] = n2;
                    n2 = player.getInteractionTargetX();
                    int n3 = player.getInteractionTargetY();
                    int n4 = SkillActionHelper.getObjectOrientation(n, n2, n3, player.getPosition().getPlane());
                    int n5 = SkillActionHelper.getObjectType(n, n2, n3, player.getPosition().getPlane());
                    ObjectManager.getInstance().addDynamicObject(new DynamicObject(6775, n2, n3, player.getPosition().getPlane(), n4, n5, n, 500), true);
                    if (player.eS()[player.eU()]) return true;
                    GameplayHelper.a(player, new Npc(c[player.eU()][1]), true, true);
                    return true;
                }
            }
            case 6775: {
                if (player.bU) {
                    return false;
                }
                int n2 = (int)Math.pow(2.0, 16.0);
                if ((player.ep[453] & n2) == 0 || player.ep[453] == 0) {
                    Player player5 = player;
                    player5.packetSender.sendConfig(453, n2);
                    player.ep[453] = n2;
                    if (player.eS()[player.eU()]) return true;
                    GameplayHelper.a(player, new Npc(c[player.eU()][1]), true, true);
                    return true;
                }
                if ((player.ep[453] & n2) != 0) {
                    if (player.eT() <= 0) {
                        Player player6 = player;
                        player6.packetSender.sendGameMessage("You search the chest but don't find anything.");
                        return true;
                    }
                    BarrowsManager.e(player);
                    return true;
                }
            }
            case 6771: 
            case 6772: 
            case 6773: 
            case 6821: 
            case 6822: 
            case 6823: {
                int n2 = 0;
                while (true) {
                    if (n2 >= 6) {
                        return true;
                    }
                    if (n == c[n2][0]) {
                        if (n2 == player.eU()) {
                            DialogueManager.startDialogue(player, 10001);
                            return true;
                        }
                        if (GameplayHelper.i(player, c[n2][1])) {
                            Player player7 = player;
                            player7.packetSender.sendGameMessage("You must kill the the brother before searching this.");
                            return true;
                        }
                        if (player.eS()[n2]) {
                            Player player8 = player;
                            player8.packetSender.sendGameMessage("You have already searched this sarcophagus.");
                            return true;
                        }
                        GameplayHelper.a(player, new Npc(c[n2][1]), true, true);
                        if (n2 == player.eU()) return true;
                        Player player9 = player;
                        player9.packetSender.sendGameMessage("You don't find anything.");
                        return true;
                    }
                    ++n2;
                }
            }
            case 6707: {
                player.moveTo(new Position(3556, 3298, 0));
                return true;
            }
            case 6706: {
                player.moveTo(new Position(3553, 3283, 0));
                return false;
            }
            case 6705: {
                player.moveTo(new Position(3565, 3276, 0));
                return true;
            }
            case 6704: {
                player.moveTo(new Position(3578, 3284, 0));
                return true;
            }
            case 6703: {
                player.moveTo(new Position(3574, 3298, 0));
                return true;
            }
            case 6702: {
                player.moveTo(new Position(3565, 3290, 0));
                return true;
            }
        }
        return false;
    }

    public static boolean a(Player player) {
        if (player.isInArea(3553, 3561, 3294, 3301)) {
            player.moveTo(new Position(3578, 9706, 3));
            player.packetSender.sendGameMessage("You've broken into a crypt!");
            return true;
        }
        if (player.isInArea(3550, 3557, 3278, 3287)) {
            player.moveTo(new Position(3568, 9683, 3));
            player.packetSender.sendGameMessage("You've broken into a crypt!");
            return true;
        }
        if (player.isInArea(3561, 3568, 3285, 3292)) {
            player.moveTo(new Position(3557, 9703, 3));
            player.packetSender.sendGameMessage("You've broken into a crypt!");
            return true;
        }
        if (player.isInArea(3570, 3579, 3293, 3302)) {
            player.moveTo(new Position(3556, 9718, 3));
            player.packetSender.sendGameMessage("You've broken into a crypt!");
            return true;
        }
        if (player.isInArea(3571, 3582, 3278, 3285)) {
            player.moveTo(new Position(3534, 9704, 3));
            player.packetSender.sendGameMessage("You've broken into a crypt!");
            return true;
        }
        if (player.isInArea(3562, 3569, 3273, 3279)) {
            player.moveTo(new Position(3546, 9684, 3));
            player.packetSender.sendGameMessage("You've broken into a crypt!");
            return true;
        }
        return false;
    }

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    private static void e(Player var0) {
        var1_1 = new ArrayList<ItemStack>();
        var2_2 = BarrowsManager.f(var0);
        var3_4 = var0.bS + (var2_2 << 1);
        var4_5 = new ArrayList<Integer>();
        var5_6 = 0;
        if (true) ** GOTO lbl17
        do {
            if (var0.eS()[var5_6]) {
                var6_8 = 0;
                while (var6_8 < BarrowsManager.e[var5_6].length) {
                    var4_5.add(BarrowsManager.e[var5_6][var6_8]);
                    ++var6_8;
                }
            }
            ++var5_6;
lbl17:
            // 2 sources

        } while (var5_6 < 6);
        var5_6 = 0;
        while (var5_6 < var2_2 + 1) {
            var6_10 = 450 - var2_2 * 58;
            var8_19 = var6_10 / ServerSettings.barrowsRewardRate;
            var6_11 = (int)var8_19;
            if (var2_2 > 0 && GameUtil.h(var6_11) == 0) {
                var6_11 = GameUtil.h(var4_5.size());
                var7_15 = (Integer)var4_5.get(var6_11);
                var1_1.add(new ItemStack(var7_15, 1));
                var4_5.remove(var6_11);
            } else {
                var6_12 = null;
                var7_15 = GameUtil.c(1, var3_4);
                var8_20 = -1;
                for (Object var9_22 : BarrowsManager.f) {
                    if (var9_22.d <= var7_15) {
                        var6_12 = var9_22;
                        continue;
                    }
                    var8_20 = var9_22.d - 1;
                    break;
                }
                if (var6_12 != null) {
                    var9_23 = var6_12.a;
                    if (var9_23 == 985) {
                        var9_23 = GameUtil.h(2) == 0 ? 985 : 987;
                    }
                    var10_25 = var6_12.b;
                    var11_26 = var6_12.c;
                    var17_27 = var11_26 - var10_25;
                    var11_26 = 1;
                    if (var17_27 > 0.0) {
                        var20_29 = var8_20 - var6_12.d;
                        var22_31 = var17_27 / var20_29;
                        var24_34 = var7_15 - var6_12.d;
                        var6_13 = (int)(var22_31 * var24_34);
                        var11_26 = var10_25 + var6_13;
                    }
                    if ((var20_28 = new ItemStack(var9_23, var11_26)).getDefinition().isStackable()) {
                        var21_30 = false;
                        for (ItemStack var22_32 : var1_1) {
                            var24_35 = var22_32.getId();
                            if (var24_35 != var9_23) continue;
                            var21_30 = true;
                            var25_36 = var22_32.getAmount();
                            var22_32.setAmount(var25_36 + var11_26);
                            break;
                        }
                        if (!var21_30) {
                            var1_1.add(var20_28);
                        }
                    } else {
                        var1_1.add(var20_28);
                    }
                }
            }
            ++var5_6;
        }
        if (var1_1.size() == 0) {
            var2_3 = var0;
            var2_3.packetSender.sendGameMessage("The chest was empty!");
        } else {
            var5_7 = new ItemStack[var1_1.size()];
            var6_14 = 0;
            var8_21 = var1_1.iterator();
            while (var8_21.hasNext()) {
                var5_7[var6_14] = var7_17 = (ItemStack)var8_21.next();
                ++var6_14;
            }
            var2_3 = var0;
            var2_3.packetSender.sendItemContainer(19555, var5_7);
            var2_3 = var0;
            var2_3.packetSender.showInterface(19550);
            ++var0.ed;
            var7_18 = 0;
            for (Object var8_21 : var1_1) {
                var0.getInventoryManager().b((ItemStack)var8_21);
                var6_14 = GrandExchangeManager.a(var8_21.getId());
                var7_18 += var6_14 * var8_21.getAmount();
            }
            var2_3 = var0;
            var2_3.packetSender.sendGameMessage("Your chest is worth around " + GameUtil.j(var7_18) + " coins!");
        }
        var0.bl();
        var2_3 = var0;
        CharacterFileManager.savePlayer(var2_3);
    }

    private static int f(Player player) {
        int n = 0;
        boolean[] blArray = player.eS();
        int n2 = blArray.length;
        int n3 = 0;
        while (n3 < n2) {
            boolean bl = blArray[n3];
            if (bl) {
                ++n;
            }
            ++n3;
        }
        return n;
    }

    public static boolean b(Player player) {
        if (player.eq == 4535) {
            return true;
        }
        if (player.isInBarrows()) {
            BarrowsPrayerDrainTask barrowsPrayerDrainTask = new BarrowsPrayerDrainTask(30, player);
            player.eq = 4535;
            World.getTaskScheduler().schedule(barrowsPrayerDrainTask);
            return true;
        }
        return false;
    }

    public static boolean handlePuzzleButtonClick(Player player, int n) {
        if (player.bR < 0) {
            return false;
        }
        BarrowsDoorPuzzle barrowsDoorPuzzle = m[player.bR];
        switch (n) {
            case 4550: {
                Object object = barrowsDoorPuzzle;
                if (player.bT[0] == ((BarrowsDoorPuzzle)object).b[0]) {
                    player.bQ = true;
                    object = player;
                    ((Player)object).packetSender.sendGameMessage("You hear the doors' locking mechanism grind open.");
                } else {
                    BarrowsManager.a(player, false);
                    object = player;
                    ((Player)object).packetSender.sendGameMessage("You got the puzzle wrong! You can hear the catacombs moving around you.");
                }
                object = player;
                ((Player)object).packetSender.closeInterfaces();
                return true;
            }
            case 4551: {
                Object object = barrowsDoorPuzzle;
                if (player.bT[1] == ((BarrowsDoorPuzzle)object).b[0]) {
                    player.bQ = true;
                    object = player;
                    ((Player)object).packetSender.sendGameMessage("You hear the doors' locking mechanism grind open.");
                } else {
                    BarrowsManager.a(player, false);
                    object = player;
                    ((Player)object).packetSender.sendGameMessage("You got the puzzle wrong! You can hear the catacombs moving around you.");
                }
                object = player;
                ((Player)object).packetSender.closeInterfaces();
                return true;
            }
            case 4552: {
                Object object = barrowsDoorPuzzle;
                if (player.bT[2] == ((BarrowsDoorPuzzle)object).b[0]) {
                    player.bQ = true;
                    object = player;
                    ((Player)object).packetSender.sendGameMessage("You hear the doors' locking mechanism grind open.");
                } else {
                    BarrowsManager.a(player, false);
                    object = player;
                    ((Player)object).packetSender.sendGameMessage("You got the puzzle wrong! You can hear the catacombs moving around you.");
                }
                object = player;
                ((Player)object).packetSender.closeInterfaces();
                return true;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void g(Player player) {
        int n;
        player.bR = n = GameUtil.h(m.length);
        BarrowsDoorPuzzle barrowsDoorPuzzle = m[n];
        Object object = barrowsDoorPuzzle;
        int[] nArray = (int[])barrowsDoorPuzzle.b.clone();
        player.bT = GameUtil.a(nArray);
        int n2 = 0;
        while (n2 < 3) {
            Player player2 = player;
            object = player2;
            int n3 = n2;
            object = barrowsDoorPuzzle;
            player2.packetSender.sendInterfaceModelId(i[n2], ((BarrowsDoorPuzzle)object).a[n3]);
            object = player;
            ((Player)object).packetSender.sendInterfaceModelId(j[n2], player.bT[n2]);
            ++n2;
        }
        object = player;
        ((Player)object).packetSender.sendGameMessage("The door is locked with a strange puzzle.");
        object = player;
        ((Player)object).packetSender.showInterface(4543);
    }

    /*
     * Handled impossible loop by duplicating code
     * Enabled aggressive block sorting
     */
    public static void a(Player player, boolean n) {
        int n2 = 0;
        int n3 = 0;
        player.bQ = false;
        ArrayList<BarrowsTunnelRoom> arrayList = new ArrayList<BarrowsTunnelRoom>();
        int n4 = 10;
        while (n4 <= 25) {
            n2 += (int)Math.pow(2.0, n4);
            ++n4;
        }
        n4 = GameUtil.h(4);
        n2 += (int)Math.pow(2.0, n4 + 6);
        int n5 = g[n4];
        if (n != 0) {
            player.moveTo(h[n4]);
        }
        n = 0;
        while (n < l.length) {
            BarrowsTunnelRoom barrowsTunnelRoom;
            BarrowsTunnelRoom barrowsTunnelRoom2 = barrowsTunnelRoom = l[n];
            if (barrowsTunnelRoom.d.contains(player.getPosition())) {
                barrowsTunnelRoom2 = barrowsTunnelRoom;
                n3 = barrowsTunnelRoom2.a;
                break;
            }
            ++n;
        }
        int n6 = 5;
        arrayList.add(l[n6 - 1]);
        n = 0;
        boolean bl = false;
        while (n == 0 || !bl) {
            BarrowsTunnelRoom barrowsTunnelRoom;
            int n7;
            ArrayList<Integer> arrayList2;
            BarrowsTunnelRoom barrowsTunnelRoom3;
            block16: {
                BarrowsTunnelRoom barrowsTunnelRoom4;
                block15: {
                    barrowsTunnelRoom4 = null;
                    if (arrayList.size() - 2 >= 0) {
                        barrowsTunnelRoom4 = (BarrowsTunnelRoom)arrayList.get(arrayList.size() - 2);
                    }
                    barrowsTunnelRoom3 = (BarrowsTunnelRoom)arrayList.get(arrayList.size() - 1);
                    arrayList2 = new ArrayList<Integer>();
                    n7 = 0;
                    if (!true) break block15;
                    barrowsTunnelRoom = barrowsTunnelRoom3;
                    if (n7 >= barrowsTunnelRoom.b.length) break block16;
                }
                do {
                    if (barrowsTunnelRoom4 != null) {
                        barrowsTunnelRoom = barrowsTunnelRoom4;
                        if (n7 != barrowsTunnelRoom.a) {
                            arrayList2.add(n7);
                        }
                    } else {
                        arrayList2.add(n7);
                    }
                    ++n7;
                    barrowsTunnelRoom = barrowsTunnelRoom3;
                } while (n7 < barrowsTunnelRoom.b.length);
            }
            int n8 = n7 = GameUtil.h(arrayList2.size());
            barrowsTunnelRoom = barrowsTunnelRoom3;
            int n9 = barrowsTunnelRoom.b[n8];
            n8 = 0;
            for (BarrowsTunnelRoom barrowsTunnelRoom5 : arrayList) {
                barrowsTunnelRoom = barrowsTunnelRoom5;
                barrowsTunnelRoom = barrowsTunnelRoom5;
                if (barrowsTunnelRoom5.a != n9) continue;
                n8 = 1;
            }
            if (n8 == 0) {
                n8 = n7;
                barrowsTunnelRoom = barrowsTunnelRoom3;
                n2 -= (int)Math.pow(2.0, barrowsTunnelRoom.c[n8]);
            }
            n6 = n9;
            arrayList.add(l[n6 - 1]);
            if (n9 == n5) {
                n = 1;
            }
            if (n9 != n3) continue;
            bl = true;
        }
        Player player2 = player;
        player2.packetSender.sendConfig(452, n2);
        player.ep[452] = n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void c(Player player) {
        int n = 0;
        while (n < 6) {
            player.b(n, false);
            ++n;
        }
        player.av(0);
        player.bS = 0;
        player.aw(GameUtil.g(5));
        Player player2 = player;
        player2.packetSender.sendConfig(453, 0);
        player.ep[453] = 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void a(Player player, Npc npc) {
        Player player2;
        int n = 0;
        while (n < 6) {
            if (npc.getNpcId() == c[n][1]) {
                player.av(player.eT() + 1);
                player2 = player;
                player2.packetSender.sendInterfaceText("Kill count: " + player.eT(), 4536);
                player.b(n, true);
                player.bS += npc.getDefinition().getCombatLevel();
                if (player.bS <= 1000) break;
                player.bS = 1000;
                break;
            }
            ++n;
        }
        n = 0;
        while (n < 7) {
            if (npc.getNpcId() == k[n]) {
                player.av(player.eT() + 1);
                player2 = player;
                player2.packetSender.sendInterfaceText("Kill count: " + player.eT(), 4536);
                player.bS += npc.getDefinition().getCombatLevel();
                if (player.bS <= 1000) break;
                player.bS = 1000;
                return;
            }
            ++n;
        }
    }

    static /* synthetic */ int d(Player player) {
        return BarrowsManager.f(player);
    }
}

