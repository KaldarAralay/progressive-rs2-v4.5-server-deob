/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model;

import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.model.BotReloginTask;
import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.npc.Npc;
import com.rs2.model.npc.NpcDefinition;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.objects.WorldObjectRegionIndex;
import com.rs2.model.player.Player;
import com.rs2.model.player.PlayerConnectionState;
import com.rs2.model.player.PlayerUpdateTask;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.model.task.TaskScheduler;
import com.rs2.model.task.TickTask;
import com.rs2.net.packet.PacketSender;
import com.rs2.util.CharacterFileManager;
import com.rs2.util.GameUtil;
import com.rs2.util.ProfilerRegistry;
import com.rs2.util.ProfilerTimer;
import com.rs2.util.TextUtil;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Executors;

public final class World {
    public static final Player[] a = new Player[ServerSettings.maxPlayers];
    private static Npc[] c = new Npc[20000];
    private static TaskScheduler d = new TaskScheduler();
    private static NpcDefinition[] e = new NpcDefinition[6433];
    public static int b = 0;
    private static final World f;
    private WorldObjectRegionIndex g = new WorldObjectRegionIndex();

    static {
        int n = Runtime.getRuntime().availableProcessors();
        Executors.newFixedThreadPool(n);
        f = new World();
    }

    public static void a(Player object) {
        if (!((Player)object).de) {
            return;
        }
        String string = ((Player)object).getUsername();
        Player player = object;
        player.packetSender.sendLogout();
        ((Player)object).disconnect();
        object = new BotReloginTask(30, string);
        d.schedule((TickTask)object);
    }

    public static synchronized void processTick() {
        Object object;
        Object object2;
        Object object3;
        ++b;
        ProfilerTimer profilerTimer = ProfilerRegistry.getTimer("executeTicks");
        Object object4 = new LinkedList();
        object4.addAll(d.getTasks());
        object4 = object4.iterator();
        d.getTasks().clear();
        profilerTimer.start();
        while (object4.hasNext()) {
            object3 = (TickTask)object4.next();
            try {
                ((TickTask)object3).tick();
                if (!((TickTask)object3).isActive()) continue;
                d.getTasks().add(object3);
            }
            catch (Exception exception) {
                object2 = exception;
                exception.printStackTrace();
            }
        }
        profilerTimer.stop();
        try {
            profilerTimer = ProfilerRegistry.getTimer("groundItemsUpdate");
            profilerTimer.start();
            GroundItemManager.getInstance().run();
            profilerTimer.stop();
            profilerTimer = ProfilerRegistry.getTimer("groundObjectsUpdate");
            profilerTimer.start();
            ObjectManager.getInstance().processObjects();
            profilerTimer.stop();
        }
        catch (Exception exception) {
            object3 = exception;
            exception.printStackTrace();
        }
        profilerTimer = ProfilerRegistry.getTimer("processPlayerLogic");
        profilerTimer.start();
        object3 = new ArrayList<String>();
        Entity[] entityArray = a;
        int n = a.length;
        int n2 = 0;
        while (n2 < n) {
            object2 = entityArray[n2];
            if (object2 != null) {
                if (((ArrayList)object3).contains(((Player)object2).getUsername())) {
                    System.out.println("Disconnecting: " + ((Player)object2).getUsername() + " Reason: Multilog!");
                    object = object2;
                    ((Player)object).packetSender.sendLogout();
                    ((Player)object2).disconnect();
                } else {
                    ((ArrayList)object3).add(((Player)object2).getUsername());
                    try {
                        ((Player)object2).bc();
                    }
                    catch (Exception exception) {
                        object = exception;
                        exception.printStackTrace();
                        ((Player)object2).disconnect();
                    }
                }
            }
            ++n2;
        }
        profilerTimer.stop();
        profilerTimer = ProfilerRegistry.getTimer("processNPCLogic");
        profilerTimer.start();
        entityArray = c;
        n = c.length;
        n2 = 0;
        while (n2 < n) {
            object2 = entityArray[n2];
            if (object2 != null) {
                try {
                    ((Npc)object2).process();
                }
                catch (Exception exception) {
                    object = exception;
                    exception.printStackTrace();
                    World.b((Npc)object2);
                }
            }
            ++n2;
        }
        profilerTimer.stop();
        CycleEventHandler.getInstance().process();
        profilerTimer = ProfilerRegistry.getTimer("processMovement");
        profilerTimer.start();
        entityArray = a;
        n = a.length;
        n2 = 0;
        while (n2 < n) {
            object2 = entityArray[n2];
            if (object2 != null) {
                ((Entity)object2).getMovementQueue().process();
            }
            ++n2;
        }
        entityArray = c;
        n = c.length;
        n2 = 0;
        while (n2 < n) {
            object2 = entityArray[n2];
            if (object2 != null) {
                ((Entity)object2).getMovementQueue().process();
            }
            ++n2;
        }
        profilerTimer.stop();
        profilerTimer = ProfilerRegistry.getTimer("updatePlayers");
        profilerTimer.start();
        entityArray = a;
        n = a.length;
        n2 = 0;
        while (n2 < n) {
            object2 = entityArray[n2];
            if (object2 != null) {
                try {
                    PlayerUpdateTask.a((Player)object2);
                    GameplayHelper.l((Player)object2);
                }
                catch (Exception exception) {
                    object = exception;
                    exception.printStackTrace();
                    ((Player)object2).disconnect();
                }
            }
            ++n2;
        }
        profilerTimer.stop();
        profilerTimer = ProfilerRegistry.getTimer("resetPlayers");
        profilerTimer.start();
        entityArray = a;
        n = a.length;
        n2 = 0;
        while (n2 < n) {
            object2 = entityArray[n2];
            if (object2 != null) {
                try {
                    object3 = object2;
                    ((Entity)object3).getUpdateState().reset();
                    ((Entity)object3).setWalkDirection(-1);
                    ((Entity)object3).setRunDirection(-1);
                    ((Player)object3).f(false);
                    ((Player)object3).g(false);
                    ((Player)object3).e(false);
                    ((Player)object3).setPublicChatPayload(null);
                }
                catch (Exception exception) {
                    object = exception;
                    exception.printStackTrace();
                    ((Player)object2).disconnect();
                }
            }
            ++n2;
        }
        profilerTimer.stop();
        profilerTimer = ProfilerRegistry.getTimer("resetNPCs");
        profilerTimer.start();
        entityArray = c;
        n = c.length;
        n2 = 0;
        while (n2 < n) {
            object2 = entityArray[n2];
            if (object2 != null) {
                try {
                    ((Npc)object2).resetAfterUpdate();
                }
                catch (Exception exception) {
                    object = exception;
                    exception.printStackTrace();
                    World.b((Npc)object2);
                }
            }
            ++n2;
        }
        profilerTimer.stop();
    }

    public static synchronized void b(Player player) {
        int n = 1;
        while (n < a.length) {
            if (a[n] == null) {
                World.a[n] = player;
                player.setIndex(n);
                player.setEncodedIndex(n + 32768);
                player.setSize(1);
                return;
            }
            ++n;
        }
        throw new IllegalStateException("Server is full!");
    }

    public static synchronized void a(Npc npc) {
        int n = 1;
        while (n < c.length) {
            if (c[n] == null) {
                World.c[n] = npc;
                npc.setIndex(n);
                npc.setEncodedIndex(n);
                npc.setSize(e[npc.getNpcId()].getSize());
                int[] nArray = Npc.combatTransformNpcIds;
                int n2 = Npc.combatTransformNpcIds.length;
                int n3 = 0;
                while (n3 < n2) {
                    n = nArray[n3];
                    if (n == npc.getNpcId()) {
                        npc.setCombatTransformNpcId(npc.getNpcId() - 1);
                        break;
                    }
                    ++n3;
                }
                nArray = Npc.scriptedMovementNpcIds;
                n2 = Npc.scriptedMovementNpcIds.length;
                n3 = 0;
                while (n3 < n2) {
                    n = nArray[n3];
                    if (n == npc.getNpcId()) {
                        npc.setScriptedMovementEnabled(true);
                        break;
                    }
                    ++n3;
                }
                nArray = Npc.targetMovementDisabledNpcIds;
                n2 = Npc.targetMovementDisabledNpcIds.length;
                n3 = 0;
                while (n3 < n2) {
                    n = nArray[n3];
                    if (n == npc.getNpcId()) {
                        npc.setTargetMovementDisabled(true);
                        break;
                    }
                    ++n3;
                }
                nArray = Npc.autoRetaliateDisabledNpcIds;
                n2 = Npc.autoRetaliateDisabledNpcIds.length;
                n3 = 0;
                while (n3 < n2) {
                    n = nArray[n3];
                    if (n == npc.getNpcId()) {
                        npc.setAutoRetaliateDisabled(true);
                        break;
                    }
                    ++n3;
                }
                nArray = Npc.faceEntityUpdateDisabledNpcIds;
                n2 = Npc.faceEntityUpdateDisabledNpcIds.length;
                n3 = 0;
                while (n3 < n2) {
                    n = nArray[n3];
                    if (n == npc.getNpcId()) {
                        npc.setFaceEntityUpdateDisabled(true);
                        return;
                    }
                    ++n3;
                }
                return;
            }
            ++n;
        }
        throw new IllegalStateException("Server is full!");
    }

    public static synchronized void c(Player object) {
        try {
            CharacterFileManager.savePlayer((Player)object);
            ((Player)object).j(false);
            ((Player)object).setConnectionState(PlayerConnectionState.f);
            if (((Entity)object).getIndex() == -1) {
                return;
            }
            object.ad();
            World.a[((Entity)object).getIndex()] = null;
            ((Entity)object).setIndex(-1);
            return;
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            return;
        }
    }

    public static synchronized void b(Npc npc) {
        if (npc.getIndex() == -1) {
            return;
        }
        npc.ad();
        World.c[npc.getIndex()] = null;
        npc.setIndex(-1);
    }

    public static int b() {
        int n = 0;
        Player[] playerArray = a;
        int n2 = a.length;
        int n3 = 0;
        while (n3 < n2) {
            Player player = playerArray[n3];
            if (player != null) {
                ++n;
            }
            ++n3;
        }
        return n;
    }

    public static int c() {
        int n = 0;
        Player[] playerArray = a;
        int n2 = a.length;
        int n3 = 0;
        while (n3 < n2) {
            Player player = playerArray[n3];
            if (player != null && !player.de) {
                ++n;
            }
            ++n3;
        }
        return n;
    }

    public static boolean c(Npc npc) {
        Player[] playerArray = a;
        int n = a.length;
        int n2 = 0;
        while (n2 < n) {
            Player player = playerArray[n2];
            if (player != null && !player.de && GameUtil.b(npc.getPosition(), player.getPosition()) <= 32) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public static int d() {
        int n = 0;
        Player[] playerArray = a;
        int n2 = a.length;
        int n3 = 0;
        while (n3 < n2) {
            Player player = playerArray[n3];
            if (player != null && player.getPlayerRights() >= 2) {
                ++n;
            }
            ++n3;
        }
        return n;
    }

    public static int e() {
        int n = 0;
        Player[] playerArray = a;
        int n2 = a.length;
        int n3 = 0;
        while (n3 < n2) {
            Player player = playerArray[n3];
            if (player != null && player.getPlayerRights() == 1) {
                ++n;
            }
            ++n3;
        }
        return n;
    }

    public static Player a(String object) {
        long l = TextUtil.encodeNameHash((String)object);
        Player[] playerArray = a;
        int n = a.length;
        int n2 = 0;
        while (n2 < n) {
            object = playerArray[n2];
            if (object != null && ((Player)object).dS() == l) {
                return object;
            }
            ++n2;
        }
        return null;
    }

    public static void a(boolean bl) {
        Player[] playerArray = a;
        int n = a.length;
        int n2 = 0;
        while (n2 < n) {
            Player player = playerArray[n2];
            if (player != null && player.de) {
                Player player2 = player;
                player2.packetSender.sendLogout();
                player.disconnect();
            }
            ++n2;
        }
        Server.a(true);
    }

    public static void scheduleTickTask(TickTask tickTask) {
        d.schedule(tickTask);
    }

    public static Player[] f() {
        return a;
    }

    public static Npc[] g() {
        return c;
    }

    public static TaskScheduler getTaskScheduler() {
        return d;
    }

    public static NpcDefinition[] i() {
        return e;
    }

    public static World j() {
        return f;
    }

    public final WorldObjectRegionIndex k() {
        return this.g;
    }

    public static void a(GraphicEffect graphicEffect, Position position) {
        Player[] playerArray = a;
        int n = a.length;
        int n2 = 0;
        while (n2 < n) {
            Object object = playerArray[n2];
            if (object != null && position.isWithinViewport(((Entity)object).getPosition())) {
                Position position2 = position;
                GraphicEffect graphicEffect2 = graphicEffect;
                object = ((Player)object).packetSender;
                ((PacketSender)object).sendStillGraphic(graphicEffect2.getId(), position2, graphicEffect2.getPackedDelay());
            }
            ++n2;
        }
    }
}

