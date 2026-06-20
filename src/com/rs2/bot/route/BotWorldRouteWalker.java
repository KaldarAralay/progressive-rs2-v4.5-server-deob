/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot.route;

import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.route.BotWorldRoute;
import com.rs2.bot.route.BotWorldRouteChoice;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;
import java.util.ArrayList;
import java.util.Iterator;

public final class BotWorldRouteWalker {
    private static boolean a = false;
    private static int routeDistanceThreshold = 25;

    /*
     * WARNING - void declaration
     */
    private static void findConnectingWorldRoute(Player player, ArrayList object) {
        Object object2 = player.currentBotTask;
        object2 = ((BotTaskDefinition)object2).getStartPosition();
        Object object3 = null;
        BotWorldRouteChoice object4 = null;
        double d = 0.0;
        int n = GameUtil.b(player.getPosition(), (Position)object2);
        Iterator iterator = ((ArrayList)object).iterator();
        while (iterator.hasNext()) {
            object = (BotWorldRouteChoice)iterator.next();
            ArrayList<BotWorldRouteChoice> arrayList = new ArrayList<BotWorldRouteChoice>();
            ArrayList<BotWorldRouteChoice> arrayList2 = new ArrayList<BotWorldRouteChoice>();
            Object object5 = object;
            Position position = ((BotWorldRouteChoice)object5).route.getEndPosition();
            object5 = object;
            if (((BotWorldRouteChoice)object5).reversed) {
                object5 = object;
                position = ((BotWorldRouteChoice)object5).route.getStartPosition();
            }
            BotWorldRoute[] botWorldRouteArray = BotWorldRoute.values();
            int n2 = botWorldRouteArray.length;
            int n3 = 0;
            while (n3 < n2) {
                BotWorldRoute n6 = botWorldRouteArray[n3];
                object5 = object;
                if (n6 != ((BotWorldRouteChoice)object5).route) {
                    int n4;
                    int n5 = GameUtil.b(n6.getStartPosition(), position);
                    if (n5 < (n4 = GameUtil.b(n6.getEndPosition(), position))) {
                        if (n5 <= routeDistanceThreshold) {
                            arrayList.add(new BotWorldRouteChoice(n6, false));
                        }
                    } else if (n4 <= routeDistanceThreshold) {
                        arrayList.add(new BotWorldRouteChoice(n6, true));
                    }
                }
                ++n3;
            }
            if (arrayList.size() > 1) {
                int n6;
                int n7 = -1;
                n3 = 0;
                for (BotWorldRouteChoice botWorldRouteChoice : arrayList) {
                    if (player.currentWorldRouteChoice != null) {
                        BotWorldRouteChoice botWorldRouteChoice2 = player.currentWorldRouteChoice;
                        object5 = botWorldRouteChoice2;
                        object5 = botWorldRouteChoice;
                        if (botWorldRouteChoice2.route == ((BotWorldRouteChoice)object5).route) {
                            n6 = n3;
                        }
                    }
                    ++n3;
                }
                if (n6 != -1) {
                    arrayList.remove(n6);
                }
            }
            if (arrayList.size() == 1) {
                void var12_17;
                object4 = (BotWorldRouteChoice)arrayList.get(0);
                object5 = object4;
                Position position2 = object4.route.getEndPosition();
                object5 = object4;
                if (((BotWorldRouteChoice)object5).reversed) {
                    object5 = object4;
                    Position position3 = ((BotWorldRouteChoice)object5).route.getStartPosition();
                }
                GameUtil.b((Position)var12_17, (Position)object2);
            } else {
                for (BotWorldRouteChoice botWorldRouteChoice : arrayList) {
                    int n8;
                    if (player.currentWorldRouteChoice != null) {
                        BotWorldRouteChoice botWorldRouteChoice3 = player.currentWorldRouteChoice;
                        object5 = botWorldRouteChoice3;
                        object5 = botWorldRouteChoice;
                        if (botWorldRouteChoice3.route == ((BotWorldRouteChoice)object5).route) continue;
                    }
                    object5 = botWorldRouteChoice;
                    Position position4 = ((BotWorldRouteChoice)object5).route.getEndPosition();
                    object5 = botWorldRouteChoice;
                    if (((BotWorldRouteChoice)object5).reversed) {
                        object5 = botWorldRouteChoice;
                        position4 = ((BotWorldRouteChoice)object5).route.getStartPosition();
                    }
                    if ((n8 = GameUtil.b(position4, (Position)object2)) >= n) continue;
                    arrayList2.add(botWorldRouteChoice);
                }
            }
            for (BotWorldRouteChoice botWorldRouteChoice : arrayList2) {
                double d2;
                object5 = botWorldRouteChoice;
                Position position5 = botWorldRouteChoice.route.getEndPosition();
                object5 = botWorldRouteChoice;
                if (((BotWorldRouteChoice)object5).reversed) {
                    object5 = botWorldRouteChoice;
                    position5 = ((BotWorldRouteChoice)object5).route.getStartPosition();
                }
                int n9 = GameUtil.b(position5, (Position)object2);
                double d3 = n - n9;
                object5 = object;
                double d4 = ((BotWorldRouteChoice)object5).route.getDistance();
                object5 = botWorldRouteChoice;
                double d5 = ((BotWorldRouteChoice)object5).route.getDistance();
                double d6 = d3 / (d5 + d4);
                if (!(d2 >= d)) continue;
                d = d6;
                object3 = object;
                object4 = botWorldRouteChoice;
            }
        }
        if (object3 != null && object4 != null) {
            if (!player.getMovementQueue().isRunning() && player.getRunEnergyPercent() >= 80) {
                player.getMovementQueue().setRunning(true);
            }
            BotWorldRouteWalker.startWorldRoute(player, object3);
            return;
        }
        System.out.println("Error! Could not find suitable overworld walk path for player: " + player.getUsername() + " at: " + player.getPosition() + " " + player.currentBotTask);
    }

    public static boolean findWorldRoute(Player player) {
        BotWorldRouteChoice botWorldRouteChoice;
        int n;
        Object object;
        if (player.currentBotTask == null) {
            return true;
        }
        Object object2 = player.currentBotTask;
        object2 = ((BotTaskDefinition)object2).getStartPosition();
        int n2 = GameUtil.b(player.getPosition(), (Position)object2);
        if (n2 <= routeDistanceThreshold) {
            player.currentWorldRouteChoice = null;
            GameplayHelper.f(player);
            return true;
        }
        ArrayList<BotWorldRouteChoice> arrayList = new ArrayList<BotWorldRouteChoice>();
        BotWorldRouteChoice botWorldRouteChoice2 = null;
        BotWorldRoute[] botWorldRouteArray = BotWorldRoute.values();
        int n3 = botWorldRouteArray.length;
        int n4 = 0;
        while (n4 < n3) {
            object = botWorldRouteArray[n4];
            int n5 = GameUtil.b(((BotWorldRoute)((Object)object)).getStartPosition(), player.getPosition());
            if (n5 < (n = GameUtil.b(((BotWorldRoute)((Object)object)).getEndPosition(), player.getPosition()))) {
                if (n5 <= routeDistanceThreshold) {
                    arrayList.add(new BotWorldRouteChoice((BotWorldRoute)((Object)object), false));
                }
            } else if (n <= routeDistanceThreshold) {
                arrayList.add(new BotWorldRouteChoice((BotWorldRoute)((Object)object), true));
            }
            ++n4;
        }
        object = new ArrayList();
        n4 = 0;
        if (arrayList.size() > 1) {
            n3 = -1;
            int n6 = 0;
            for (BotWorldRouteChoice botWorldRouteChoice3 : arrayList) {
                if (player.currentWorldRouteChoice != null) {
                    BotWorldRouteChoice botWorldRouteChoice4 = player.currentWorldRouteChoice;
                    botWorldRouteChoice = botWorldRouteChoice4;
                    botWorldRouteChoice = botWorldRouteChoice3;
                    if (botWorldRouteChoice4.route == botWorldRouteChoice.route) {
                        n3 = n6;
                    }
                }
                ++n6;
            }
            if (n3 != -1) {
                arrayList.remove(n3);
            }
        }
        if (arrayList.size() == 1) {
            botWorldRouteChoice = botWorldRouteChoice2 = (BotWorldRouteChoice)arrayList.get(0);
            Position position = botWorldRouteChoice2.route.getEndPosition();
            botWorldRouteChoice = botWorldRouteChoice2;
            if (botWorldRouteChoice.reversed) {
                botWorldRouteChoice = botWorldRouteChoice2;
                position = botWorldRouteChoice.route.getStartPosition();
            }
            n4 = GameUtil.b(position, (Position)object2);
        } else {
            for (BotWorldRouteChoice botWorldRouteChoice5 : arrayList) {
                if (player.currentWorldRouteChoice != null) {
                    BotWorldRouteChoice botWorldRouteChoice6 = player.currentWorldRouteChoice;
                    botWorldRouteChoice = botWorldRouteChoice6;
                    botWorldRouteChoice = botWorldRouteChoice5;
                    if (botWorldRouteChoice6.route == botWorldRouteChoice.route) continue;
                }
                botWorldRouteChoice = botWorldRouteChoice5;
                Position position = botWorldRouteChoice.route.getEndPosition();
                botWorldRouteChoice = botWorldRouteChoice5;
                if (botWorldRouteChoice.reversed) {
                    botWorldRouteChoice = botWorldRouteChoice5;
                    position = botWorldRouteChoice.route.getStartPosition();
                }
                if ((n = GameUtil.b(position, (Position)object2)) >= n2) continue;
                object.add(botWorldRouteChoice5);
            }
            double d = 0.0;
            Iterator iterator = object.iterator();
            while (iterator.hasNext()) {
                double d2;
                BotWorldRouteChoice botWorldRouteChoice7;
                botWorldRouteChoice = botWorldRouteChoice7 = (BotWorldRouteChoice)iterator.next();
                Position position = botWorldRouteChoice7.route.getEndPosition();
                botWorldRouteChoice = botWorldRouteChoice7;
                if (botWorldRouteChoice.reversed) {
                    botWorldRouteChoice = botWorldRouteChoice7;
                    position = botWorldRouteChoice.route.getStartPosition();
                }
                int n7 = GameUtil.b(position, (Position)object2);
                double d3 = n2 - n7;
                botWorldRouteChoice = botWorldRouteChoice7;
                double d4 = botWorldRouteChoice.route.getDistance();
                double d5 = d3 / d4;
                if (!(d2 >= d)) continue;
                d = d5;
                botWorldRouteChoice2 = botWorldRouteChoice7;
                n4 = n7;
            }
        }
        if (botWorldRouteChoice2 != null) {
            if (n4 <= routeDistanceThreshold) {
                player.botTaskState = "world walk finish";
                player.getMovementQueue().setRunning(false);
            }
            if (!player.getMovementQueue().isRunning() && player.getRunEnergyPercent() >= 80) {
                player.getMovementQueue().setRunning(true);
            }
            BotWorldRouteWalker.startWorldRoute(player, botWorldRouteChoice2);
        } else if (arrayList.size() > 0) {
            BotWorldRouteWalker.findConnectingWorldRoute(player, arrayList);
        } else {
            System.out.println("Error! Could not find suitable overworld walk path for player: " + player.getUsername() + " at: " + player.getPosition() + " " + player.currentBotTask);
            return false;
        }
        return true;
    }

    public static int getRouteIndex(BotWorldRouteChoice botWorldRouteChoice) {
        int n = 0;
        while (n < BotWorldRoute.values().length) {
            BotWorldRoute botWorldRoute = BotWorldRoute.values()[n];
            Object object = botWorldRoute;
            object = botWorldRouteChoice;
            if (botWorldRoute == ((BotWorldRouteChoice)object).route) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    public static void continueWorldRoute(Player player, BotWorldRouteChoice object) {
        BotWorldRouteChoice botWorldRouteChoice = object;
        BotWorldRoute botWorldRoute = botWorldRouteChoice.route;
        player.currentWorldRouteChoice = object;
        if (botWorldRoute.getRoute() != null) {
            botWorldRouteChoice = object;
            player.currentBotRoute = botWorldRouteChoice.reversed ? botWorldRoute.getRoute().reversed() : botWorldRoute.getRoute();
        } else {
            botWorldRouteChoice = object;
            BotRoute botRoute = player.currentBotRoute = botWorldRouteChoice.reversed ? botWorldRoute.getSegments()[player.botPathSegmentIndex].reversed() : botWorldRoute.getSegments()[player.botPathSegmentIndex];
        }
        if (botWorldRoute.getRouteNpcId() != -1) {
            player.botTargetNpcId = botWorldRoute.getRouteNpcId();
        }
        object = player.currentBotRoute.waypoints[player.botPathWaypointIndex];
        int n = GameUtil.b(player.getPosition(), (Position)object);
        if (n > routeDistanceThreshold) {
            System.out.println("Detected possibly frozen bot: " + player.getUsername() + " at: " + player.getPosition() + ", trying to apply fix.");
            BotWorldRouteWalker.findWorldRoute(player);
            return;
        }
        player.bk();
    }

    private static void startWorldRoute(Player player, BotWorldRouteChoice botWorldRouteChoice) {
        player.botEnabled = true;
        player.botPathWaypointIndex = 0;
        BotWorldRouteChoice botWorldRouteChoice2 = botWorldRouteChoice;
        BotWorldRoute botWorldRoute = botWorldRouteChoice2.route;
        player.currentWorldRouteChoice = botWorldRouteChoice;
        player.botPathSegmentIndex = 0;
        if (botWorldRoute.getRoute() != null) {
            botWorldRouteChoice2 = botWorldRouteChoice;
            player.currentBotRoute = botWorldRouteChoice2.reversed ? botWorldRoute.getRoute().reversed() : botWorldRoute.getRoute();
            player.botTaskState = "world walk find";
        } else {
            botWorldRouteChoice2 = botWorldRouteChoice;
            player.botPathSegmentIndex = botWorldRouteChoice2.reversed ? botWorldRoute.getSegments().length - 1 : 0;
            botWorldRouteChoice2 = botWorldRouteChoice;
            player.currentBotRoute = botWorldRouteChoice2.reversed ? botWorldRoute.getSegments()[player.botPathSegmentIndex].reversed() : botWorldRoute.getSegments()[player.botPathSegmentIndex];
            player.botTaskState = "world walk towards";
        }
        if (botWorldRoute.getRouteNpcId() != -1) {
            player.botTargetNpcId = botWorldRoute.getRouteNpcId();
        }
        player.bk();
    }

    public static void advanceRouteSegment(Player player) {
        BotWorldRouteChoice botWorldRouteChoice = player.currentWorldRouteChoice;
        BotWorldRoute botWorldRoute = botWorldRouteChoice.route;
        botWorldRouteChoice = player.currentWorldRouteChoice;
        if (!botWorldRouteChoice.reversed) {
            ++player.botPathSegmentIndex;
            player.currentBotRoute = botWorldRoute.getSegments()[player.botPathSegmentIndex];
            player.botPathWaypointIndex = 0;
            if (player.botPathSegmentIndex == botWorldRoute.getSegments().length - 1) {
                player.botTaskState = "world walk find";
                return;
            }
        } else {
            --player.botPathSegmentIndex;
            player.currentBotRoute = botWorldRoute.getSegments()[player.botPathSegmentIndex].reversed();
            player.botPathWaypointIndex = 0;
            if (player.botPathSegmentIndex == 0) {
                player.botTaskState = "world walk find";
            }
        }
    }
}

