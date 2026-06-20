/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot;

import com.rs2.ServerSettings;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.bot.combat.BotCombatLoadoutManager;
import com.rs2.model.GameplayHelper;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.GrandExchangeManager;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class BotTradeAdvertManager {
    public static ArrayList tradeAdvertOfferPool = new ArrayList();
    public static ArrayList commonTradeAdvertOfferPool = new ArrayList();
    private static ArrayList scamTradeAdvertOfferPool = new ArrayList();
    private static ArrayList combatTradeAdvertOfferPool = new ArrayList();
    public static int scammerChanceDivisor = 25;
    public static int commonItemChancePercent = 90;
    private static GameplayHelper[] configuredTradeAdvertOffers = new GameplayHelper[]{new GameplayHelper(0, new int[]{1}), new GameplayHelper(52, new int[]{100, 200, 500, 1000}), new GameplayHelper(115, new int[]{10, 20, 50, 100}), new GameplayHelper(121, new int[]{10, 20, 50, 100}), new GameplayHelper(127, new int[]{10, 20, 50, 100}), new GameplayHelper(133, new int[]{10, 20, 50, 100}), new GameplayHelper(139, new int[]{10, 20, 50, 100}), new GameplayHelper(145, new int[]{10, 20, 50, 100}), new GameplayHelper(151, new int[]{10, 20, 50, 100}), new GameplayHelper(157, new int[]{10, 20, 50, 100}), new GameplayHelper(163, new int[]{10, 20, 50, 100}), new GameplayHelper(169, new int[]{10, 20, 50, 100}), new GameplayHelper(175, new int[]{10, 20, 50, 100}), new GameplayHelper(181, new int[]{10, 20, 50, 100}), new GameplayHelper(187, new int[]{1, 2, 5, 10}), new GameplayHelper(189, new int[]{10, 20, 50, 100}), new GameplayHelper(221, new int[]{10, 20, 50, 100}), new GameplayHelper(223, new int[]{10, 20, 50, 100}), new GameplayHelper(225, new int[]{10, 20, 50, 100}), new GameplayHelper(231, new int[]{10, 20, 50, 100}), new GameplayHelper(237, new int[]{10, 20, 50, 100}), new GameplayHelper(239, new int[]{10, 20, 50, 100}), new GameplayHelper(243, new int[]{10, 20, 50, 100}), new GameplayHelper(245, new int[]{10, 20, 50, 100}), new GameplayHelper(247, new int[]{10, 20, 50, 100}), new GameplayHelper(249, new int[]{10, 20, 50, 100}), new GameplayHelper(251, new int[]{10, 20, 50, 100}), new GameplayHelper(253, new int[]{10, 20, 50, 100}), new GameplayHelper(255, new int[]{10, 20, 50, 100}), new GameplayHelper(257, new int[]{10, 20, 50, 100}), new GameplayHelper(259, new int[]{10, 20, 50, 100}), new GameplayHelper(261, new int[]{10, 20, 50, 100}), new GameplayHelper(263, new int[]{10, 20, 50, 100}), new GameplayHelper(265, new int[]{10, 20, 50, 100}), new GameplayHelper(267, new int[]{10, 20, 50, 100}), new GameplayHelper(269, new int[]{10, 20, 50, 100}), new GameplayHelper(314, new int[]{100, 200, 500, 1000}), new GameplayHelper(315, new int[]{100, 200, 500, 1000}), new GameplayHelper(317, new int[]{100, 200, 500, 1000}), new GameplayHelper(319, new int[]{100, 200, 500, 1000}), new GameplayHelper(321, new int[]{100, 200, 500, 1000}), new GameplayHelper(325, new int[]{100, 200, 500, 1000}), new GameplayHelper(327, new int[]{100, 200, 500, 1000}), new GameplayHelper(329, new int[]{100, 200, 500, 1000}), new GameplayHelper(331, new int[]{100, 200, 500, 1000}), new GameplayHelper(333, new int[]{100, 200, 500, 1000}), new GameplayHelper(335, new int[]{100, 200, 500, 1000}), new GameplayHelper(339, new int[]{100, 200, 500, 1000}), new GameplayHelper(341, new int[]{100, 200, 500, 1000}), new GameplayHelper(345, new int[]{100, 200, 500, 1000}), new GameplayHelper(347, new int[]{100, 200, 500, 1000}), new GameplayHelper(349, new int[]{100, 200, 500, 1000}), new GameplayHelper(351, new int[]{100, 200, 500, 1000}), new GameplayHelper(353, new int[]{100, 200, 500, 1000}), new GameplayHelper(355, new int[]{100, 200, 500, 1000}), new GameplayHelper(359, new int[]{100, 200, 500, 1000}), new GameplayHelper(361, new int[]{100, 200, 500, 1000}), new GameplayHelper(363, new int[]{100, 200, 500, 1000}), new GameplayHelper(365, new int[]{100, 200, 500, 1000}), new GameplayHelper(371, new int[]{100, 200, 500, 1000}), new GameplayHelper(373, new int[]{100, 200, 500, 1000}), new GameplayHelper(377, new int[]{100, 200, 500, 1000}), new GameplayHelper(379, new int[]{100, 200, 500, 1000}), new GameplayHelper(381, new int[]{1}), new GameplayHelper(383, new int[]{100, 200, 500, 1000}), new GameplayHelper(385, new int[]{100, 200, 500, 1000}), new GameplayHelper(389, new int[]{100, 200, 500, 1000}), new GameplayHelper(391, new int[]{100, 200, 500, 1000}), new GameplayHelper(395, new int[]{100, 200, 500, 1000}), new GameplayHelper(397, new int[]{100, 200, 500, 1000}), new GameplayHelper(405, new int[]{1}), new GameplayHelper(407, new int[]{1}), new GameplayHelper(434, new int[]{100, 200, 500, 1000}), new GameplayHelper(436, new int[]{100, 200, 500, 1000}), new GameplayHelper(438, new int[]{100, 200, 500, 1000}), new GameplayHelper(440, new int[]{100, 200, 500, 1000}), new GameplayHelper(442, new int[]{100, 200, 500, 1000}), new GameplayHelper(444, new int[]{100, 200, 500, 1000}), new GameplayHelper(447, new int[]{100, 200, 500, 1000}), new GameplayHelper(449, new int[]{100, 200, 500, 1000}), new GameplayHelper(451, new int[]{100, 200, 500, 1000}), new GameplayHelper(453, new int[]{100, 200, 500, 1000}), new GameplayHelper(464, new int[]{1}), new GameplayHelper(526, new int[]{100, 200, 500, 1000}), new GameplayHelper(528, new int[]{100, 200, 500, 1000}), new GameplayHelper(530, new int[]{100, 200, 500, 1000}), new GameplayHelper(532, new int[]{100, 200, 500, 1000}), new GameplayHelper(534, new int[]{100, 200, 500, 1000}), new GameplayHelper(536, new int[]{100, 200, 500, 1000}), new GameplayHelper(554, new int[]{500, 1000, 2000, 5000, 10000}), new GameplayHelper(555, new int[]{500, 1000, 2000, 5000, 10000}), new GameplayHelper(556, new int[]{500, 1000, 2000, 5000, 10000}), new GameplayHelper(557, new int[]{500, 1000, 2000, 5000, 10000}), new GameplayHelper(558, new int[]{500, 1000, 2000, 5000, 10000}), new GameplayHelper(559, new int[]{500, 1000, 2000, 5000, 10000}), new GameplayHelper(560, new int[]{100, 200, 500, 1000}), new GameplayHelper(561, new int[]{100, 200, 500, 1000}), new GameplayHelper(562, new int[]{100, 200, 500, 1000}), new GameplayHelper(563, new int[]{100, 200, 500, 1000}), new GameplayHelper(564, new int[]{100, 200, 500, 1000}), new GameplayHelper(565, new int[]{100, 200, 500, 1000}), new GameplayHelper(566, new int[]{100, 200, 500, 1000}), new GameplayHelper(800, new int[]{100, 200, 500, 1000}), new GameplayHelper(801, new int[]{100, 200, 500, 1000}), new GameplayHelper(802, new int[]{100, 200, 500, 1000}), new GameplayHelper(803, new int[]{100, 200, 500, 1000}), new GameplayHelper(804, new int[]{100, 200, 500, 1000}), new GameplayHelper(805, new int[]{100, 200, 500, 1000}), new GameplayHelper(806, new int[]{100, 200, 500, 1000}), new GameplayHelper(807, new int[]{100, 200, 500, 1000}), new GameplayHelper(808, new int[]{100, 200, 500, 1000}), new GameplayHelper(809, new int[]{100, 200, 500, 1000}), new GameplayHelper(810, new int[]{100, 200, 500, 1000}), new GameplayHelper(811, new int[]{100, 200, 500, 1000}), new GameplayHelper(851, new int[]{1}), new GameplayHelper(853, new int[]{1}), new GameplayHelper(855, new int[]{1}), new GameplayHelper(857, new int[]{1}), new GameplayHelper(859, new int[]{1}), new GameplayHelper(861, new int[]{1}), new GameplayHelper(863, new int[]{100, 200, 500, 1000}), new GameplayHelper(864, new int[]{100, 200, 500, 1000}), new GameplayHelper(865, new int[]{100, 200, 500, 1000}), new GameplayHelper(866, new int[]{100, 200, 500, 1000}), new GameplayHelper(867, new int[]{100, 200, 500, 1000}), new GameplayHelper(868, new int[]{100, 200, 500, 1000}), new GameplayHelper(869, new int[]{100, 200, 500, 1000}), new GameplayHelper(877, new int[]{100, 200, 500, 1000}), new GameplayHelper(879, new int[]{100, 200, 500, 1000}), new GameplayHelper(880, new int[]{100, 200, 500, 1000}), new GameplayHelper(881, new int[]{100, 200, 500, 1000}), new GameplayHelper(882, new int[]{500, 1000, 2000, 5000, 10000}), new GameplayHelper(884, new int[]{500, 1000, 2000, 5000, 10000}), new GameplayHelper(886, new int[]{500, 1000, 2000, 5000, 10000}), new GameplayHelper(888, new int[]{100, 200, 500, 1000}), new GameplayHelper(890, new int[]{100, 200, 500, 1000}), new GameplayHelper(892, new int[]{100, 200, 500, 1000}), new GameplayHelper(952, new int[]{1}), new GameplayHelper(962, new int[]{1}), new GameplayHelper(985, new int[]{1}), new GameplayHelper(987, new int[]{1}), new GameplayHelper(989, new int[]{1}), new GameplayHelper(991, new int[]{1}), new GameplayHelper(1033, new int[]{1}), new GameplayHelper(1035, new int[]{1}), new GameplayHelper(1038, new int[]{1}), new GameplayHelper(1040, new int[]{1}), new GameplayHelper(1042, new int[]{1}), new GameplayHelper(1044, new int[]{1}), new GameplayHelper(1046, new int[]{1}), new GameplayHelper(1048, new int[]{1}), new GameplayHelper(1050, new int[]{1}), new GameplayHelper(1053, new int[]{1}), new GameplayHelper(1055, new int[]{1}), new GameplayHelper(1057, new int[]{1}), new GameplayHelper(1059, new int[]{1}), new GameplayHelper(1061, new int[]{1}), new GameplayHelper(1063, new int[]{1}), new GameplayHelper(1065, new int[]{1}), new GameplayHelper(1069, new int[]{1}), new GameplayHelper(1071, new int[]{1}), new GameplayHelper(1073, new int[]{1}), new GameplayHelper(1077, new int[]{1}), new GameplayHelper(1079, new int[]{1}), new GameplayHelper(1083, new int[]{1}), new GameplayHelper(1085, new int[]{1}), new GameplayHelper(1089, new int[]{1}), new GameplayHelper(1091, new int[]{1}), new GameplayHelper(1093, new int[]{1}), new GameplayHelper(1095, new int[]{1}), new GameplayHelper(1097, new int[]{1}), new GameplayHelper(1099, new int[]{1}), new GameplayHelper(1105, new int[]{1}), new GameplayHelper(1107, new int[]{1}), new GameplayHelper(1109, new int[]{1}), new GameplayHelper(1111, new int[]{1}), new GameplayHelper(1113, new int[]{1}), new GameplayHelper(1119, new int[]{1}), new GameplayHelper(1121, new int[]{1}), new GameplayHelper(1123, new int[]{1}), new GameplayHelper(1125, new int[]{1}), new GameplayHelper(1127, new int[]{1}), new GameplayHelper(1129, new int[]{1}), new GameplayHelper(1131, new int[]{1}), new GameplayHelper(1133, new int[]{1}), new GameplayHelper(1135, new int[]{1}), new GameplayHelper(1141, new int[]{1}), new GameplayHelper(1143, new int[]{1}), new GameplayHelper(1145, new int[]{1}), new GameplayHelper(1147, new int[]{1}), new GameplayHelper(1149, new int[]{1}), new GameplayHelper(1151, new int[]{1}), new GameplayHelper(1157, new int[]{1}), new GameplayHelper(1159, new int[]{1}), new GameplayHelper(1161, new int[]{1}), new GameplayHelper(1163, new int[]{1}), new GameplayHelper(1165, new int[]{1}), new GameplayHelper(1167, new int[]{1}), new GameplayHelper(1169, new int[]{1}), new GameplayHelper(1177, new int[]{1}), new GameplayHelper(1179, new int[]{1}), new GameplayHelper(1181, new int[]{1}), new GameplayHelper(1183, new int[]{1}), new GameplayHelper(1185, new int[]{1}), new GameplayHelper(1187, new int[]{1}), new GameplayHelper(1193, new int[]{1}), new GameplayHelper(1195, new int[]{1}), new GameplayHelper(1197, new int[]{1}), new GameplayHelper(1199, new int[]{1}), new GameplayHelper(1201, new int[]{1}), new GameplayHelper(1207, new int[]{1}), new GameplayHelper(1209, new int[]{1}), new GameplayHelper(1211, new int[]{1}), new GameplayHelper(1213, new int[]{1}), new GameplayHelper(1215, new int[]{1}), new GameplayHelper(1217, new int[]{1}), new GameplayHelper(1231, new int[]{1}), new GameplayHelper(1265, new int[]{1}), new GameplayHelper(1267, new int[]{1}), new GameplayHelper(1269, new int[]{1}), new GameplayHelper(1271, new int[]{1}), new GameplayHelper(1273, new int[]{1}), new GameplayHelper(1275, new int[]{1}), new GameplayHelper(1281, new int[]{1}), new GameplayHelper(1283, new int[]{1}), new GameplayHelper(1285, new int[]{1}), new GameplayHelper(1287, new int[]{1}), new GameplayHelper(1289, new int[]{1}), new GameplayHelper(1295, new int[]{1}), new GameplayHelper(1297, new int[]{1}), new GameplayHelper(1299, new int[]{1}), new GameplayHelper(1301, new int[]{1}), new GameplayHelper(1303, new int[]{1}), new GameplayHelper(1305, new int[]{1}), new GameplayHelper(1311, new int[]{1}), new GameplayHelper(1313, new int[]{1}), new GameplayHelper(1315, new int[]{1}), new GameplayHelper(1317, new int[]{1}), new GameplayHelper(1319, new int[]{1}), new GameplayHelper(1325, new int[]{1}), new GameplayHelper(1327, new int[]{1}), new GameplayHelper(1329, new int[]{1}), new GameplayHelper(1331, new int[]{1}), new GameplayHelper(1333, new int[]{1}), new GameplayHelper(1339, new int[]{1}), new GameplayHelper(1341, new int[]{1}), new GameplayHelper(1343, new int[]{1}), new GameplayHelper(1345, new int[]{1}), new GameplayHelper(1347, new int[]{1}), new GameplayHelper(1349, new int[]{1}), new GameplayHelper(1351, new int[]{1}), new GameplayHelper(1353, new int[]{1}), new GameplayHelper(1355, new int[]{1}), new GameplayHelper(1357, new int[]{1}), new GameplayHelper(1359, new int[]{1}), new GameplayHelper(1361, new int[]{1}), new GameplayHelper(1365, new int[]{1}), new GameplayHelper(1367, new int[]{1}), new GameplayHelper(1369, new int[]{1}), new GameplayHelper(1371, new int[]{1}), new GameplayHelper(1373, new int[]{1}), new GameplayHelper(1377, new int[]{1}), new GameplayHelper(1379, new int[]{1}), new GameplayHelper(1381, new int[]{1}), new GameplayHelper(1383, new int[]{1}), new GameplayHelper(1385, new int[]{1}), new GameplayHelper(1387, new int[]{1}), new GameplayHelper(1391, new int[]{10, 20, 50, 100}), new GameplayHelper(1393, new int[]{1}), new GameplayHelper(1395, new int[]{1}), new GameplayHelper(1397, new int[]{1}), new GameplayHelper(1399, new int[]{1}), new GameplayHelper(1401, new int[]{1}), new GameplayHelper(1403, new int[]{1}), new GameplayHelper(1405, new int[]{1}), new GameplayHelper(1407, new int[]{1}), new GameplayHelper(1424, new int[]{1}), new GameplayHelper(1426, new int[]{1}), new GameplayHelper(1428, new int[]{1}), new GameplayHelper(1430, new int[]{1}), new GameplayHelper(1432, new int[]{1}), new GameplayHelper(1434, new int[]{1}), new GameplayHelper(1436, new int[]{100, 200, 500, 1000, 2000}), new GameplayHelper(1438, new int[]{1}), new GameplayHelper(1440, new int[]{1}), new GameplayHelper(1442, new int[]{1}), new GameplayHelper(1444, new int[]{1}), new GameplayHelper(1446, new int[]{1}), new GameplayHelper(1448, new int[]{1}), new GameplayHelper(1452, new int[]{1}), new GameplayHelper(1454, new int[]{1}), new GameplayHelper(1456, new int[]{1}), new GameplayHelper(1462, new int[]{1}), new GameplayHelper(1470, new int[]{1}), new GameplayHelper(1472, new int[]{1}), new GameplayHelper(1474, new int[]{1}), new GameplayHelper(1476, new int[]{1}), new GameplayHelper(1511, new int[]{100, 200, 500, 1000}), new GameplayHelper(1513, new int[]{100, 200, 500, 1000}), new GameplayHelper(1515, new int[]{100, 200, 500, 1000}), new GameplayHelper(1517, new int[]{100, 200, 500, 1000}), new GameplayHelper(1519, new int[]{100, 200, 500, 1000}), new GameplayHelper(1521, new int[]{100, 200, 500, 1000}), new GameplayHelper(1539, new int[]{100, 200, 500, 1000}), new GameplayHelper(1601, new int[]{1, 2, 5, 10}), new GameplayHelper(1603, new int[]{1, 2, 5, 10}), new GameplayHelper(1605, new int[]{1, 2, 5, 10}), new GameplayHelper(1607, new int[]{1, 2, 5, 10}), new GameplayHelper(1609, new int[]{10, 20, 50, 100}), new GameplayHelper(1611, new int[]{10, 20, 50, 100}), new GameplayHelper(1613, new int[]{10, 20, 50, 100}), new GameplayHelper(1615, new int[]{1, 2, 5, 10}), new GameplayHelper(1617, new int[]{1, 2, 5, 10}), new GameplayHelper(1619, new int[]{1, 2, 5, 10}), new GameplayHelper(1621, new int[]{1, 2, 5, 10}), new GameplayHelper(1623, new int[]{1, 2, 5, 10}), new GameplayHelper(1625, new int[]{10, 20, 50, 100}), new GameplayHelper(1627, new int[]{10, 20, 50, 100}), new GameplayHelper(1629, new int[]{10, 20, 50, 100}), new GameplayHelper(1631, new int[]{1, 2, 5, 10}), new GameplayHelper(1635, new int[]{1}), new GameplayHelper(1637, new int[]{1}), new GameplayHelper(1639, new int[]{1}), new GameplayHelper(1641, new int[]{1}), new GameplayHelper(1643, new int[]{1}), new GameplayHelper(1645, new int[]{1}), new GameplayHelper(1654, new int[]{1}), new GameplayHelper(1656, new int[]{1}), new GameplayHelper(1658, new int[]{1}), new GameplayHelper(1660, new int[]{1}), new GameplayHelper(1662, new int[]{1}), new GameplayHelper(1664, new int[]{1}), new GameplayHelper(1712, new int[]{1}), new GameplayHelper(1725, new int[]{1}), new GameplayHelper(1727, new int[]{1}), new GameplayHelper(1729, new int[]{1}), new GameplayHelper(1731, new int[]{1}), new GameplayHelper(1716, new int[]{1}), new GameplayHelper(1718, new int[]{1}), new GameplayHelper(1722, new int[]{1}), new GameplayHelper(1724, new int[]{1}), new GameplayHelper(1739, new int[]{100, 200, 500, 1000}), new GameplayHelper(1747, new int[]{100, 200, 500, 1000}), new GameplayHelper(1749, new int[]{100, 200, 500, 1000}), new GameplayHelper(1751, new int[]{100, 200, 500, 1000}), new GameplayHelper(1753, new int[]{100, 200, 500, 1000}), new GameplayHelper(1777, new int[]{100, 200, 500, 1000}), new GameplayHelper(1779, new int[]{100, 200, 500, 1000}), new GameplayHelper(1891, new int[]{10, 20, 50, 100}), new GameplayHelper(1897, new int[]{10, 20, 50, 100}), new GameplayHelper(1949, new int[]{1}), new GameplayHelper(1959, new int[]{1}), new GameplayHelper(1961, new int[]{1}), new GameplayHelper(1969, new int[]{10, 20, 50, 100}), new GameplayHelper(1971, new int[]{10, 20, 50, 100}), new GameplayHelper(1973, new int[]{10, 20, 50, 100}), new GameplayHelper(1975, new int[]{10, 20, 50, 100}), new GameplayHelper(1993, new int[]{10, 20, 50, 100}), new GameplayHelper(2003, new int[]{10, 20, 50, 100}), new GameplayHelper(2142, new int[]{10, 20, 50, 100}), new GameplayHelper(2152, new int[]{10, 20, 50, 100}), new GameplayHelper(2289, new int[]{10, 20, 50, 100}), new GameplayHelper(2293, new int[]{10, 20, 50, 100}), new GameplayHelper(2297, new int[]{10, 20, 50, 100}), new GameplayHelper(2301, new int[]{10, 20, 50, 100}), new GameplayHelper(2309, new int[]{10, 20, 50, 100}), new GameplayHelper(2323, new int[]{10, 20, 50, 100}), new GameplayHelper(2325, new int[]{10, 20, 50, 100}), new GameplayHelper(2327, new int[]{10, 20, 50, 100}), new GameplayHelper(2349, new int[]{100, 200, 500, 1000}), new GameplayHelper(2351, new int[]{100, 200, 500, 1000}), new GameplayHelper(2353, new int[]{100, 200, 500, 1000}), new GameplayHelper(2355, new int[]{100, 200, 500, 1000}), new GameplayHelper(2357, new int[]{100, 200, 500, 1000}), new GameplayHelper(2359, new int[]{100, 200, 500, 1000}), new GameplayHelper(2361, new int[]{100, 200, 500, 1000}), new GameplayHelper(2363, new int[]{100, 200, 500, 1000}), new GameplayHelper(2366, new int[]{1}), new GameplayHelper(2368, new int[]{1}), new GameplayHelper(2454, new int[]{10, 20, 50, 100}), new GameplayHelper(2481, new int[]{10, 20, 50, 100}), new GameplayHelper(2487, new int[]{1}), new GameplayHelper(2489, new int[]{1}), new GameplayHelper(2491, new int[]{1}), new GameplayHelper(2493, new int[]{1}), new GameplayHelper(2495, new int[]{1}), new GameplayHelper(2497, new int[]{1}), new GameplayHelper(2499, new int[]{1}), new GameplayHelper(2501, new int[]{1}), new GameplayHelper(2503, new int[]{1}), new GameplayHelper(2577, new int[]{1}), new GameplayHelper(2579, new int[]{1}), new GameplayHelper(2581, new int[]{1}), new GameplayHelper(2583, new int[]{1}), new GameplayHelper(2585, new int[]{1}), new GameplayHelper(2587, new int[]{1}), new GameplayHelper(2589, new int[]{1}), new GameplayHelper(2591, new int[]{1}), new GameplayHelper(2593, new int[]{1}), new GameplayHelper(2595, new int[]{1}), new GameplayHelper(2597, new int[]{1}), new GameplayHelper(2599, new int[]{1}), new GameplayHelper(2601, new int[]{1}), new GameplayHelper(2603, new int[]{1}), new GameplayHelper(2605, new int[]{1}), new GameplayHelper(2607, new int[]{1}), new GameplayHelper(2609, new int[]{1}), new GameplayHelper(2611, new int[]{1}), new GameplayHelper(2613, new int[]{1}), new GameplayHelper(2615, new int[]{1}), new GameplayHelper(2617, new int[]{1}), new GameplayHelper(2619, new int[]{1}), new GameplayHelper(2621, new int[]{1}), new GameplayHelper(2623, new int[]{1}), new GameplayHelper(2625, new int[]{1}), new GameplayHelper(2627, new int[]{1}), new GameplayHelper(2629, new int[]{1}), new GameplayHelper(2631, new int[]{1}), new GameplayHelper(2633, new int[]{1}), new GameplayHelper(2635, new int[]{1}), new GameplayHelper(2637, new int[]{1}), new GameplayHelper(2639, new int[]{1}), new GameplayHelper(2641, new int[]{1}), new GameplayHelper(2643, new int[]{1}), new GameplayHelper(2645, new int[]{1}), new GameplayHelper(2647, new int[]{1}), new GameplayHelper(2649, new int[]{1}), new GameplayHelper(2651, new int[]{1}), new GameplayHelper(2653, new int[]{1}), new GameplayHelper(2655, new int[]{1}), new GameplayHelper(2657, new int[]{1}), new GameplayHelper(2659, new int[]{1}), new GameplayHelper(2661, new int[]{1}), new GameplayHelper(2663, new int[]{1}), new GameplayHelper(2665, new int[]{1}), new GameplayHelper(2667, new int[]{1}), new GameplayHelper(2669, new int[]{1}), new GameplayHelper(2671, new int[]{1}), new GameplayHelper(2673, new int[]{1}), new GameplayHelper(2675, new int[]{1}), new GameplayHelper(2859, new int[]{100, 200, 500, 1000}), new GameplayHelper(2862, new int[]{100, 200, 500, 1000}), new GameplayHelper(2970, new int[]{10, 20, 50, 100}), new GameplayHelper(3018, new int[]{10, 20, 50, 100}), new GameplayHelper(3026, new int[]{10, 20, 50, 100}), new GameplayHelper(3042, new int[]{10, 20, 50, 100}), new GameplayHelper(3053, new int[]{1}), new GameplayHelper(3054, new int[]{1}), new GameplayHelper(3093, new int[]{100, 200, 500, 1000}), new GameplayHelper(3122, new int[]{1}), new GameplayHelper(3125, new int[]{100, 200, 500, 1000}), new GameplayHelper(3142, new int[]{100, 200, 500, 1000}), new GameplayHelper(3144, new int[]{100, 200, 500, 1000}), new GameplayHelper(3379, new int[]{100, 200, 500, 1000}), new GameplayHelper(3381, new int[]{100, 200, 500, 1000}), new GameplayHelper(3385, new int[]{1}), new GameplayHelper(3387, new int[]{1}), new GameplayHelper(3389, new int[]{1}), new GameplayHelper(3391, new int[]{1}), new GameplayHelper(3393, new int[]{1}), new GameplayHelper(3470, new int[]{1, 2, 5, 10}), new GameplayHelper(3472, new int[]{1}), new GameplayHelper(3473, new int[]{1}), new GameplayHelper(3474, new int[]{1}), new GameplayHelper(3475, new int[]{1}), new GameplayHelper(3476, new int[]{1}), new GameplayHelper(3477, new int[]{1}), new GameplayHelper(3478, new int[]{1}), new GameplayHelper(3479, new int[]{1}), new GameplayHelper(3480, new int[]{1}), new GameplayHelper(3481, new int[]{1}), new GameplayHelper(3483, new int[]{1}), new GameplayHelper(3485, new int[]{1}), new GameplayHelper(3486, new int[]{1}), new GameplayHelper(3488, new int[]{1}), new GameplayHelper(3827, new int[]{1}), new GameplayHelper(3828, new int[]{1}), new GameplayHelper(3829, new int[]{1}), new GameplayHelper(3830, new int[]{1}), new GameplayHelper(3831, new int[]{1}), new GameplayHelper(3832, new int[]{1}), new GameplayHelper(3833, new int[]{1}), new GameplayHelper(3834, new int[]{1}), new GameplayHelper(3835, new int[]{1}), new GameplayHelper(3836, new int[]{1}), new GameplayHelper(3837, new int[]{1}), new GameplayHelper(3838, new int[]{1}), new GameplayHelper(4089, new int[]{1}), new GameplayHelper(4091, new int[]{1}), new GameplayHelper(4093, new int[]{1}), new GameplayHelper(4095, new int[]{1}), new GameplayHelper(4097, new int[]{1}), new GameplayHelper(4099, new int[]{1}), new GameplayHelper(4101, new int[]{1}), new GameplayHelper(4103, new int[]{1}), new GameplayHelper(4105, new int[]{1}), new GameplayHelper(4107, new int[]{1}), new GameplayHelper(4109, new int[]{1}), new GameplayHelper(4111, new int[]{1}), new GameplayHelper(4113, new int[]{1}), new GameplayHelper(4115, new int[]{1}), new GameplayHelper(4117, new int[]{1}), new GameplayHelper(4119, new int[]{1}), new GameplayHelper(4121, new int[]{1}), new GameplayHelper(4123, new int[]{1}), new GameplayHelper(4125, new int[]{1}), new GameplayHelper(4127, new int[]{1}), new GameplayHelper(4129, new int[]{1}), new GameplayHelper(4131, new int[]{1}), new GameplayHelper(4151, new int[]{1}), new GameplayHelper(4153, new int[]{1}), new GameplayHelper(4587, new int[]{1}), new GameplayHelper(4708, new int[]{1}), new GameplayHelper(4710, new int[]{1}), new GameplayHelper(4712, new int[]{1}), new GameplayHelper(4714, new int[]{1}), new GameplayHelper(4716, new int[]{1}), new GameplayHelper(4718, new int[]{1}), new GameplayHelper(4720, new int[]{1}), new GameplayHelper(4722, new int[]{1}), new GameplayHelper(4724, new int[]{1}), new GameplayHelper(4726, new int[]{1}), new GameplayHelper(4728, new int[]{1}), new GameplayHelper(4730, new int[]{1}), new GameplayHelper(4732, new int[]{1}), new GameplayHelper(4734, new int[]{1}), new GameplayHelper(4736, new int[]{1}), new GameplayHelper(4738, new int[]{1}), new GameplayHelper(4740, new int[]{100, 200, 500, 1000}), new GameplayHelper(4745, new int[]{1}), new GameplayHelper(4747, new int[]{1}), new GameplayHelper(4749, new int[]{1}), new GameplayHelper(4751, new int[]{1}), new GameplayHelper(4753, new int[]{1}), new GameplayHelper(4755, new int[]{1}), new GameplayHelper(4757, new int[]{1}), new GameplayHelper(4759, new int[]{1}), new GameplayHelper(5001, new int[]{100, 200, 500, 1000}), new GameplayHelper(5003, new int[]{100, 200, 500, 1000}), new GameplayHelper(5014, new int[]{1}), new GameplayHelper(5016, new int[]{1}), new GameplayHelper(5018, new int[]{1}), new GameplayHelper(5096, new int[]{10, 20, 50, 100}), new GameplayHelper(5097, new int[]{10, 20, 50, 100}), new GameplayHelper(5098, new int[]{10, 20, 50, 100}), new GameplayHelper(5099, new int[]{10, 20, 50, 100}), new GameplayHelper(5100, new int[]{10, 20, 50, 100}), new GameplayHelper(5101, new int[]{10, 20, 50, 100}), new GameplayHelper(5102, new int[]{10, 20, 50, 100}), new GameplayHelper(5103, new int[]{10, 20, 50, 100}), new GameplayHelper(5104, new int[]{10, 20, 50, 100}), new GameplayHelper(5105, new int[]{10, 20, 50, 100}), new GameplayHelper(5106, new int[]{10, 20, 50, 100}), new GameplayHelper(5280, new int[]{1, 2, 5, 10}), new GameplayHelper(5281, new int[]{1, 2, 5, 10}), new GameplayHelper(5282, new int[]{1, 2, 5, 10}), new GameplayHelper(5283, new int[]{1, 2, 5, 10}), new GameplayHelper(5284, new int[]{1, 2, 5, 10}), new GameplayHelper(5285, new int[]{1, 2, 5, 10}), new GameplayHelper(5286, new int[]{1, 2, 5, 10}), new GameplayHelper(5287, new int[]{1, 2, 5, 10}), new GameplayHelper(5288, new int[]{1, 2, 5, 10}), new GameplayHelper(5289, new int[]{1, 2, 5, 10}), new GameplayHelper(5290, new int[]{1, 2, 5, 10}), new GameplayHelper(5291, new int[]{10, 20, 50, 100}), new GameplayHelper(5292, new int[]{10, 20, 50, 100}), new GameplayHelper(5293, new int[]{10, 20, 50, 100}), new GameplayHelper(5294, new int[]{10, 20, 50, 100}), new GameplayHelper(5295, new int[]{10, 20, 50, 100}), new GameplayHelper(5296, new int[]{10, 20, 50, 100}), new GameplayHelper(5297, new int[]{10, 20, 50, 100}), new GameplayHelper(5298, new int[]{10, 20, 50, 100}), new GameplayHelper(5299, new int[]{10, 20, 50, 100}), new GameplayHelper(5300, new int[]{10, 20, 50, 100}), new GameplayHelper(5301, new int[]{10, 20, 50, 100}), new GameplayHelper(5302, new int[]{10, 20, 50, 100}), new GameplayHelper(5303, new int[]{10, 20, 50, 100}), new GameplayHelper(5304, new int[]{10, 20, 50, 100}), new GameplayHelper(5305, new int[]{10, 20, 50, 100}), new GameplayHelper(5306, new int[]{10, 20, 50, 100}), new GameplayHelper(5307, new int[]{10, 20, 50, 100}), new GameplayHelper(5308, new int[]{10, 20, 50, 100}), new GameplayHelper(5309, new int[]{10, 20, 50, 100}), new GameplayHelper(5310, new int[]{10, 20, 50, 100}), new GameplayHelper(5311, new int[]{10, 20, 50, 100}), new GameplayHelper(5312, new int[]{1, 2, 5, 10}), new GameplayHelper(5313, new int[]{1, 2, 5, 10}), new GameplayHelper(5314, new int[]{1, 2, 5, 10}), new GameplayHelper(5315, new int[]{1, 2, 5, 10}), new GameplayHelper(5316, new int[]{1, 2, 5, 10}), new GameplayHelper(5318, new int[]{10, 20, 50, 100}), new GameplayHelper(5319, new int[]{10, 20, 50, 100}), new GameplayHelper(5320, new int[]{10, 20, 50, 100}), new GameplayHelper(5321, new int[]{10, 20, 50, 100}), new GameplayHelper(5322, new int[]{10, 20, 50, 100}), new GameplayHelper(5323, new int[]{10, 20, 50, 100}), new GameplayHelper(5324, new int[]{10, 20, 50, 100}), new GameplayHelper(5325, new int[]{1}), new GameplayHelper(5329, new int[]{1}), new GameplayHelper(5331, new int[]{1}), new GameplayHelper(5341, new int[]{1}), new GameplayHelper(5343, new int[]{1}), new GameplayHelper(5350, new int[]{1, 2, 5, 10}), new GameplayHelper(5376, new int[]{10, 20, 50, 100}), new GameplayHelper(5418, new int[]{10, 20, 50, 100}), new GameplayHelper(5525, new int[]{1}), new GameplayHelper(5527, new int[]{1}), new GameplayHelper(5529, new int[]{1}), new GameplayHelper(5531, new int[]{1}), new GameplayHelper(5533, new int[]{1}), new GameplayHelper(5535, new int[]{1}), new GameplayHelper(5537, new int[]{1}), new GameplayHelper(5539, new int[]{1}), new GameplayHelper(5541, new int[]{1}), new GameplayHelper(5543, new int[]{1}), new GameplayHelper(5547, new int[]{1}), new GameplayHelper(5680, new int[]{1}), new GameplayHelper(5698, new int[]{1}), new GameplayHelper(6032, new int[]{10, 20, 50, 100}), new GameplayHelper(6036, new int[]{10, 20, 50, 100}), new GameplayHelper(6333, new int[]{100, 200, 500, 1000}), new GameplayHelper(6522, new int[]{1}), new GameplayHelper(6523, new int[]{1}), new GameplayHelper(6524, new int[]{1}), new GameplayHelper(6525, new int[]{1}), new GameplayHelper(6526, new int[]{1}), new GameplayHelper(6527, new int[]{1}), new GameplayHelper(6528, new int[]{1}), new GameplayHelper(6568, new int[]{1}), new GameplayHelper(6585, new int[]{1}), new GameplayHelper(6581, new int[]{1}), new GameplayHelper(6809, new int[]{1}), new GameplayHelper(6889, new int[]{1}), new GameplayHelper(6908, new int[]{1}), new GameplayHelper(6910, new int[]{1}), new GameplayHelper(6912, new int[]{1}), new GameplayHelper(6914, new int[]{1}), new GameplayHelper(6916, new int[]{1}), new GameplayHelper(6918, new int[]{1}), new GameplayHelper(6920, new int[]{1}), new GameplayHelper(6922, new int[]{1}), new GameplayHelper(6924, new int[]{1}), new GameplayHelper(7386, new int[]{1}), new GameplayHelper(7390, new int[]{1}), new GameplayHelper(7394, new int[]{1}), new GameplayHelper(7936, new int[]{100, 200, 500, 1000, 2000}), new GameplayHelper(10330, new int[]{1}), new GameplayHelper(10332, new int[]{1}), new GameplayHelper(10334, new int[]{1}), new GameplayHelper(10336, new int[]{1}), new GameplayHelper(10338, new int[]{1}), new GameplayHelper(10340, new int[]{1}), new GameplayHelper(10342, new int[]{1}), new GameplayHelper(10344, new int[]{1}), new GameplayHelper(10346, new int[]{1}), new GameplayHelper(10348, new int[]{1}), new GameplayHelper(10350, new int[]{1}), new GameplayHelper(10352, new int[]{1}), new GameplayHelper(10368, new int[]{1}), new GameplayHelper(10370, new int[]{1}), new GameplayHelper(10372, new int[]{1}), new GameplayHelper(10374, new int[]{1}), new GameplayHelper(10376, new int[]{1}), new GameplayHelper(10378, new int[]{1}), new GameplayHelper(10380, new int[]{1}), new GameplayHelper(10382, new int[]{1}), new GameplayHelper(10384, new int[]{1}), new GameplayHelper(10386, new int[]{1}), new GameplayHelper(10388, new int[]{1}), new GameplayHelper(10390, new int[]{1}), new GameplayHelper(10440, new int[]{1}), new GameplayHelper(10442, new int[]{1}), new GameplayHelper(10444, new int[]{1}), new GameplayHelper(10446, new int[]{1}), new GameplayHelper(10448, new int[]{1}), new GameplayHelper(10450, new int[]{1}), new GameplayHelper(10452, new int[]{1}), new GameplayHelper(10454, new int[]{1}), new GameplayHelper(10456, new int[]{1}), new GameplayHelper(10458, new int[]{1}), new GameplayHelper(10460, new int[]{1}), new GameplayHelper(10462, new int[]{1}), new GameplayHelper(10464, new int[]{1}), new GameplayHelper(10466, new int[]{1}), new GameplayHelper(10468, new int[]{1}), new GameplayHelper(10470, new int[]{1}), new GameplayHelper(10472, new int[]{1}), new GameplayHelper(10474, new int[]{1}), new GameplayHelper(11128, new int[]{1}), new GameplayHelper(11235, new int[]{1}), new GameplayHelper(11284, new int[]{1}), new GameplayHelper(11286, new int[]{1}), new GameplayHelper(11335, new int[]{1}), new GameplayHelper(11690, new int[]{1}), new GameplayHelper(11694, new int[]{1}), new GameplayHelper(11696, new int[]{1}), new GameplayHelper(11698, new int[]{1}), new GameplayHelper(11700, new int[]{1}), new GameplayHelper(11702, new int[]{1}), new GameplayHelper(11704, new int[]{1}), new GameplayHelper(11706, new int[]{1}), new GameplayHelper(11708, new int[]{1}), new GameplayHelper(11710, new int[]{1}), new GameplayHelper(11712, new int[]{1}), new GameplayHelper(11714, new int[]{1}), new GameplayHelper(11716, new int[]{1}), new GameplayHelper(11718, new int[]{1}), new GameplayHelper(11720, new int[]{1}), new GameplayHelper(11722, new int[]{1}), new GameplayHelper(11724, new int[]{1}), new GameplayHelper(11726, new int[]{1}), new GameplayHelper(11728, new int[]{1}), new GameplayHelper(11730, new int[]{1}), new GameplayHelper(11732, new int[]{1}), new GameplayHelper(11736, new int[]{1}), new GameplayHelper(11738, new int[]{1})};

    public static void initializeTradeAdvertOfferPools() {
        GameplayHelper[] gameplayHelperArray = configuredTradeAdvertOffers;
        int n = configuredTradeAdvertOffers.length;
        int n2 = 0;
        while (n2 < n) {
            GameplayHelper gameplayHelper = gameplayHelperArray[n2];
            ItemDefinition itemDefinition = ItemDefinition.forId(gameplayHelper.c());
            if (!(!ItemDefinition.isDefined(gameplayHelper.c()) || ServerSettings.freeToPlayWorld && itemDefinition.isMembersOnly() && gameplayHelper.c() != 0 || !ServerSettings.content2007Enabled && gameplayHelper.c() >= 7955 || !ServerSettings.otherBotsEnabled && (gameplayHelper.c() == 381 || gameplayHelper.c() == 0))) {
                tradeAdvertOfferPool.add(gameplayHelper);
                if (gameplayHelper.c() == 115 || gameplayHelper.c() == 127 || gameplayHelper.c() == 139 || gameplayHelper.c() == 175 || gameplayHelper.c() == 333 || gameplayHelper.c() == 379 || gameplayHelper.c() == 385 || gameplayHelper.c() == 560 || gameplayHelper.c() == 562 || gameplayHelper.c() == 565 || gameplayHelper.c() == 811 || gameplayHelper.c() == 861 || gameplayHelper.c() == 868 || gameplayHelper.c() == 886 || gameplayHelper.c() == 888 || gameplayHelper.c() == 890 || gameplayHelper.c() == 892 || gameplayHelper.c() == 1163 || gameplayHelper.c() == 1201 || gameplayHelper.c() == 1215 || gameplayHelper.c() == 1231 || gameplayHelper.c() == 1303 || gameplayHelper.c() == 1319 || gameplayHelper.c() == 1333 || gameplayHelper.c() == 1373 || gameplayHelper.c() == 2487 || gameplayHelper.c() == 2489 || gameplayHelper.c() == 2491 || gameplayHelper.c() == 2493 || gameplayHelper.c() == 2495 || gameplayHelper.c() == 2497 || gameplayHelper.c() == 2499 || gameplayHelper.c() == 2501 || gameplayHelper.c() == 2503 || gameplayHelper.c() == 4131 || gameplayHelper.c() == 4151 || gameplayHelper.c() == 4153 || gameplayHelper.c() == 4740 || gameplayHelper.c() == 5680 || gameplayHelper.c() == 5698 || gameplayHelper.c() == 6524 || gameplayHelper.c() == 6528 || gameplayHelper.c() == 6568 || gameplayHelper.c() == 6585) {
                    combatTradeAdvertOfferPool.add(gameplayHelper);
                }
                if (gameplayHelper.c() == 127 || gameplayHelper.c() == 139 || gameplayHelper.c() == 175 || gameplayHelper.c() == 223 || gameplayHelper.c() == 225 || gameplayHelper.c() == 231 || gameplayHelper.c() == 237 || gameplayHelper.c() == 249 || gameplayHelper.c() == 251 || gameplayHelper.c() == 253 || gameplayHelper.c() == 255 || gameplayHelper.c() == 257 || gameplayHelper.c() == 314 || gameplayHelper.c() == 333 || gameplayHelper.c() == 335 || gameplayHelper.c() == 377 || gameplayHelper.c() == 379 || gameplayHelper.c() == 383 || gameplayHelper.c() == 385 || gameplayHelper.c() == 453 || gameplayHelper.c() == 532 || gameplayHelper.c() == 536 || gameplayHelper.c() == 556 || gameplayHelper.c() == 561 || gameplayHelper.c() == 563 || gameplayHelper.c() == 564 || gameplayHelper.c() == 1355 || gameplayHelper.c() == 1357 || gameplayHelper.c() == 1359 || gameplayHelper.c() == 1391 || gameplayHelper.c() == 1436 || gameplayHelper.c() == 1438 || gameplayHelper.c() == 1440 || gameplayHelper.c() == 1442 || gameplayHelper.c() == 1444 || gameplayHelper.c() == 1446 || gameplayHelper.c() == 1448 || gameplayHelper.c() == 1452 || gameplayHelper.c() == 1454 || gameplayHelper.c() == 1462 || gameplayHelper.c() == 1470 || gameplayHelper.c() == 1472 || gameplayHelper.c() == 1474 || gameplayHelper.c() == 1476 || gameplayHelper.c() == 1511 || gameplayHelper.c() == 1513 || gameplayHelper.c() == 1515 || gameplayHelper.c() == 1517 || gameplayHelper.c() == 1519 || gameplayHelper.c() == 1521 || gameplayHelper.c() == 1601 || gameplayHelper.c() == 1603 || gameplayHelper.c() == 1605 || gameplayHelper.c() == 1607 || gameplayHelper.c() == 1615 || gameplayHelper.c() == 1617 || gameplayHelper.c() == 1619 || gameplayHelper.c() == 1621 || gameplayHelper.c() == 1623 || gameplayHelper.c() == 1631 || gameplayHelper.c() == 1712 || gameplayHelper.c() == 1725 || gameplayHelper.c() == 1727 || gameplayHelper.c() == 1729 || gameplayHelper.c() == 1731 || gameplayHelper.c() == 1739 || gameplayHelper.c() == 1747 || gameplayHelper.c() == 1749 || gameplayHelper.c() == 1751 || gameplayHelper.c() == 1753 || gameplayHelper.c() == 1777 || gameplayHelper.c() == 1779 || gameplayHelper.c() == 7936) {
                    commonTradeAdvertOfferPool.add(gameplayHelper);
                }
                if (gameplayHelper.c() == 0 || gameplayHelper.c() == 381 || gameplayHelper.c() == 1319) {
                    scamTradeAdvertOfferPool.add(gameplayHelper);
                }
            }
            ++n2;
        }
    }

    public static int roundAdvertUnitPrice(boolean bl, int n, int n2) {
        int n3 = 1;
        if (n < 1000) {
            n3 = n;
            n3 = bl ? (int)(Math.ceil((double)n3 / 10.0) * 10.0) : (int)(Math.floor((double)n3 / 10.0) * 10.0);
            n3 /= n2;
        }
        if (n >= 1000 && n < 10000) {
            n3 = n;
            n3 = bl ? (int)(Math.ceil((double)n3 / 100.0) * 100.0) : (int)(Math.floor((double)n3 / 100.0) * 100.0);
            n3 /= n2;
        }
        if (n >= 10000 && n < 100000) {
            n3 = n;
            n3 = bl ? (int)(Math.ceil((double)n3 / 1000.0) * 1000.0) : (int)(Math.floor((double)n3 / 1000.0) * 1000.0);
            n3 /= n2;
        }
        if (n >= 100000 && n < 1000000) {
            n3 = n;
            n3 = bl ? (int)(Math.ceil((double)n3 / 10000.0) * 10000.0) : (int)(Math.floor((double)n3 / 10000.0) * 10000.0);
            n3 /= n2;
        }
        if (n >= 1000000 && n < 10000000) {
            n3 = n;
            n3 = bl ? (int)(Math.ceil((double)n3 / 100000.0) * 100000.0) : (int)(Math.floor((double)n3 / 100000.0) * 100000.0);
            n3 /= n2;
        }
        if (n >= 10000000) {
            n3 = n;
            n3 = bl ? (int)(Math.ceil((double)n3 / 1000000.0) * 1000000.0) : (int)(Math.floor((double)n3 / 1000000.0) * 1000000.0);
            n3 /= n2;
        }
        return n3;
    }

    public static void prepareTradeAdvertOffer(Player player) {
        int n;
        double d;
        int n2;
        player.tradeAdvertMode = GameUtil.h(2);
        player.tradeAdvertVariableQuantity = false;
        player.tradeAdvertScam = false;
        if (ServerSettings.otherBotsEnabled && GameUtil.h(scammerChanceDivisor) == 0) {
            player.tradeAdvertScam = true;
        }
        Object object = player.currentBotTask.usesCombatTradeAdvertItems ? combatTradeAdvertOfferPool : ((n2 = GameUtil.h(100)) >= commonItemChancePercent ? tradeAdvertOfferPool : commonTradeAdvertOfferPool);
        if (player.tradeAdvertMode == 0 && player.tradeAdvertScam && GameUtil.h(2) == 0) {
            object = scamTradeAdvertOfferPool;
        }
        n2 = GameUtil.h(((ArrayList)object).size());
        object = (GameplayHelper)((ArrayList)object).get(n2);
        player.tradeAdvertOfferPoolIndex = n2 = tradeAdvertOfferPool.indexOf(object);
        player.botAdvertItemId = ((GameplayHelper)tradeAdvertOfferPool.get(player.tradeAdvertOfferPoolIndex)).c();
        object = ItemDefinition.forId(player.botAdvertItemId);
        double d2 = GrandExchangeManager.a(player.botAdvertItemId);
        if (d < 1.0) {
            d2 = 1.0;
        }
        if (player.botAdvertItemId == 381) {
            n2 = GameUtil.h(4);
            if (n2 == 0) {
                d2 = 1000.0;
            } else if (n2 == 1) {
                d2 = 5000.0;
            } else if (n2 == 2) {
                d2 = 10000.0;
            } else if (n2 >= 3) {
                d2 = 20000.0;
            }
        }
        n2 = GameUtil.i(11);
        double d3 = d2 / 100.0 * (double)n2;
        double d4 = player.tradeAdvertMode == 0 ? d2 + d3 : d2 - d3;
        n2 = (int)d4;
        if (player.botAdvertItemId == 0) {
            n = GameUtil.h(4);
            if (n == 0) {
                n2 = 1000;
            } else if (n == 1) {
                n2 = 5000;
            } else if (n == 2) {
                n2 = 10000;
            } else if (n >= 3) {
                n2 = 20000;
            }
            if (GameUtil.h(2) == 0) {
                n2 = 1;
            }
        }
        player.tradeAdvertQuantityOptionIndex = n = GameUtil.h(((GameplayHelper)tradeAdvertOfferPool.get(player.tradeAdvertOfferPoolIndex)).d().length);
        n = ((GameplayHelper)tradeAdvertOfferPool.get(player.tradeAdvertOfferPoolIndex)).d()[player.tradeAdvertQuantityOptionIndex];
        int n3 = n * n2;
        if (player.botAdvertItemId != 0) {
            int n4 = n;
            Player player2 = player;
            int n5 = 1;
            if (n3 < 1000) {
                n5 = n3;
                n5 = player2.tradeAdvertMode == 0 ? (int)(Math.ceil((double)n5 / 10.0) * 10.0) : (int)(Math.floor((double)n5 / 10.0) * 10.0);
                n5 /= n4;
            }
            if (n3 >= 1000 && n3 < 10000) {
                n5 = n3;
                n5 = player2.tradeAdvertMode == 0 ? (int)(Math.ceil((double)n5 / 100.0) * 100.0) : (int)(Math.floor((double)n5 / 100.0) * 100.0);
                n5 /= n4;
            }
            if (n3 >= 10000 && n3 < 100000) {
                n5 = n3;
                n5 = player2.tradeAdvertMode == 0 ? (int)(Math.ceil((double)n5 / 1000.0) * 1000.0) : (int)(Math.floor((double)n5 / 1000.0) * 1000.0);
                n5 /= n4;
            }
            if (n3 >= 100000 && n3 < 1000000) {
                n5 = n3;
                n5 = player2.tradeAdvertMode == 0 ? (int)(Math.ceil((double)n5 / 10000.0) * 10000.0) : (int)(Math.floor((double)n5 / 10000.0) * 10000.0);
                n5 /= n4;
            }
            if (n3 >= 1000000 && n3 < 10000000) {
                n5 = n3;
                n5 = player2.tradeAdvertMode == 0 ? (int)(Math.ceil((double)n5 / 100000.0) * 100000.0) : (int)(Math.floor((double)n5 / 100000.0) * 100000.0);
                n5 /= n4;
            }
            if (n3 >= 10000000) {
                n5 = n3;
                n5 = player2.tradeAdvertMode == 0 ? (int)(Math.ceil((double)n5 / 1000000.0) * 1000000.0) : (int)(Math.floor((double)n5 / 1000000.0) * 1000000.0);
                n5 /= n4;
            }
            n2 = n5;
        }
        n3 = player.botAdvertItemId;
        if (!((ItemDefinition)object).isStackable() && n >= 10) {
            n3 = ((ItemDefinition)object).getNotedId();
        }
        if (player.tradeAdvertScam && n3 == 1319) {
            n3 = ((ItemDefinition)object).getNotedId();
        }
        player.botAdvertItemId = n3;
        player.tradeAdvertQuantityRemaining = n;
        if (n2 <= 0) {
            n2 = 1 + GameUtil.h(5);
        }
        player.tradeAdvertUnitPrice = n2;
        if (player.tradeAdvertMode == 1 && n >= 1000) {
            player.tradeAdvertVariableQuantity = GameUtil.h(5) == 0;
        }
        BotTradeAdvertManager.updateTradeAdvertMessage(player);
        player.botPublicChatColor = GameUtil.h(12);
        int[] nArray = new int[3];
        nArray[1] = 1;
        nArray[2] = 3;
        object = nArray;
        n2 = GameUtil.h(3);
        player.botPublicChatEffect = (int)object[n2];
    }

    public static void updateTradeAdvertMessage(Player player) {
        Object object = ItemDefinition.forId(player.botAdvertItemId);
        if (((ItemDefinition)object).isNote()) {
            object = ItemDefinition.forId(((ItemDefinition)object).getUnnotedId());
        }
        int n = player.tradeAdvertQuantityRemaining;
        String string = GameUtil.d(player.tradeAdvertQuantityRemaining);
        int n2 = player.tradeAdvertUnitPrice * player.tradeAdvertQuantityRemaining;
        String string2 = GameUtil.e(n2);
        String string3 = GameUtil.e(player.tradeAdvertUnitPrice);
        String string4 = n <= 10 && n > 1 || player.tradeAdvertVariableQuantity ? String.valueOf(string3) + " ea" : (n2 < 1000 ? String.valueOf(string2) + "gp" : string2);
        string2 = player.tradeAdvertMode == 0 ? "Selling" : "Buying";
        object = ((ItemDefinition)object).getDisplayName();
        object = n > 1 && !((String)object).endsWith("s") ? String.valueOf(object) + "s" : object;
        string = String.valueOf(string2) + " " + string + " " + (String)object + " " + string4 + " - " + player.getUsername();
        if (n == 1) {
            string = String.valueOf(string2) + " " + (String)object + " " + string4 + " - " + player.getUsername();
        } else if (player.tradeAdvertVariableQuantity) {
            string = String.valueOf(string2) + " all " + (String)object + " " + string4 + " - " + player.getUsername();
        }
        if (player.botAdvertItemId == 0 && player.tradeAdvertUnitPrice > 1) {
            string = "Doubling money min " + string4 + " - " + player.getUsername();
        }
        if (player.botAdvertItemId == 0 && player.tradeAdvertUnitPrice == 1) {
            string = "I need free stuff - " + player.getUsername();
        }
        player.botPublicChatMessage = string;
    }

    public static void prepareTradeAdvertCombatLoadout(Player player) {
        GameplayHelper.b(player);
        int n = 1 + GameUtil.h(99);
        int n2 = n / 5 << 1;
        if (n2 == 0) {
            n2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 0, n - n / 5 + GameUtil.h(n2));
        n2 = n / 5 << 1;
        if (n2 == 0) {
            n2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 2, n - n / 5 + GameUtil.h(n2));
        n2 = n / 5 << 1;
        if (n2 == 0) {
            n2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 1, n - n / 5 + GameUtil.h(n2));
        n2 = n / 5 << 1;
        if (n2 == 0) {
            n2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 4, n - n / 5 + GameUtil.h(n2));
        n2 = n / 5 << 1;
        if (n2 == 0) {
            n2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 6, n - n / 5 + GameUtil.h(n2));
        n2 = n / 5 << 1;
        if (n2 == 0) {
            n2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 5, n - (n / 5 << 1) + GameUtil.h(n2));
        player.getSkillManager().getExperience()[3] = BotCombatHelper.calculateBotHitpointsExperience(player);
        int[] nArray = player.getSkillManager().getCurrentLevels();
        player.getSkillManager();
        nArray[3] = SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[3]);
        player.getSkillManager().refreshAllSkills();
        BotCombatLoadoutManager.a(player, true);
    }

    public static void prepareTradeAdvertInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        if (player.tradeAdvertMode == 0) {
            if (player.tradeAdvertScam && player.botAdvertItemId == 1320) {
                player.getInventoryManager().addItem(new ItemStack(player.botAdvertItemId, player.tradeAdvertQuantityRemaining));
                player.getInventoryManager().addItem(new ItemStack(1310, player.tradeAdvertQuantityRemaining));
            } else if (player.botAdvertItemId != 0) {
                player.getInventoryManager().addItem(new ItemStack(player.botAdvertItemId, player.tradeAdvertQuantityRemaining));
            }
        } else if (player.tradeAdvertMode == 1) {
            player.getInventoryManager().addItem(new ItemStack(995, player.tradeAdvertUnitPrice * player.tradeAdvertQuantityRemaining));
        }
        Player player2 = player;
        if (player2.botCombatStyle == 0) {
            BotCombatLoadoutManager.e(player2);
            BotCombatLoadoutManager.d(player2);
        } else if (player2.botCombatStyle == 2) {
            BotCombatLoadoutManager.g(player2);
        } else if (player2.botCombatStyle == 1) {
            int n;
            BotCombatLoadoutManager.f(player2);
            int n2 = player2.getSkillManager().getCurrentLevels()[4] >= 40 ? 1731 : (n = GameUtil.h(3) == 0 ? 1478 : 1729);
            if (!BotCombatHelper.isFreeToPlayWorld() && player2.getCombatLevel() >= 60 && GameUtil.h(2) == 0) {
                n = 1712;
            }
            player2.getEquipmentManager().getContainer().setItem(2, new ItemStack(n));
        }
        BotCombatLoadoutManager.c(player2);
        player.getInventoryManager().refresh();
        player.getEquipmentManager().refresh();
    }

    public static void advanceTradeAdvertTradeFlow(Player player) {
        if (player.getOpenInterfaceId() == 3323) {
            GameplayHelper.q(player);
            return;
        }
        if (player.getOpenInterfaceId() == 3443) {
            GameplayHelper.r(player);
        }
    }
}

