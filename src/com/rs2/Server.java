/*
 * Decompiled with CFR 0.152.
 */
package com.rs2;

import com.rs2.BotLoginBatchTask;
import com.rs2.CacheCoordinateTranslator;
import com.rs2.ConfigFile;
import com.rs2.ConnectionThrottle;
import com.rs2.HiscoresDatabase;
import com.rs2.ItemDegradeTickTask;
import com.rs2.LanDiscoveryService;
import com.rs2.ServerSettings;
import com.rs2.StartingRareItemList;
import com.rs2.bot.BotPlayer;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.ClanWarsBotManager;
import com.rs2.bot.combat.BotCombatLoadoutManager;
import com.rs2.bot.combat.WildernessBotSettings;
import com.rs2.cache.CacheStore;
import com.rs2.cache.InterfaceDefinition;
import com.rs2.launcher.ControlPanel;
import com.rs2.model.EntityTargetMovement;
import com.rs2.model.GameplayHelper;
import com.rs2.model.NoopStartupHook;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.area.MultiwayAreaDefinition;
import com.rs2.model.c.ProjectileDefinition;
import com.rs2.model.clue.TreasureTrailManager;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.gameplay.duel.DuelSession;
import com.rs2.model.gameplay.godwars.GodWarsDungeonManager;
import com.rs2.model.gameplay.magetrainingarena.AlchemistPlaygroundController;
import com.rs2.model.gameplay.magetrainingarena.CreatureGraveyardController;
import com.rs2.model.gameplay.magetrainingarena.EnchantmentChamberController;
import com.rs2.model.gameplay.partyroom.PartyRoomManager;
import com.rs2.model.grandexchange.GrandExchangePriceSample;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.item.consumable.FoodHandler;
import com.rs2.model.message.MessageOfTheWeek;
import com.rs2.model.music.MusicAreaDefinition;
import com.rs2.model.music.MusicTrackDefinition;
import com.rs2.model.npc.NpcDefinition;
import com.rs2.model.npc.drop.NpcDropTable;
import com.rs2.model.objects.ObjectDefinition;
import com.rs2.model.objects.WorldObjectLookup;
import com.rs2.model.player.Player;
import com.rs2.model.player.PlayerConnectionState;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.model.quest.QuestEventRegistry;
import com.rs2.model.shop.ShopManager;
import com.rs2.model.skill.fishing.FishingSpotManager;
import com.rs2.model.skill.guide.SkillGuideManager;
import com.rs2.model.task.TickTask;
import com.rs2.model.travel.DefaultTravelBootstrap;
import com.rs2.model.travel.TravelManager;
import com.rs2.net.DedicatedReactor;
import com.rs2.net.packet.PacketDispatcher;
import com.rs2.util.CharacterFileManager;
import com.rs2.util.DelayedShutdownTask;
import com.rs2.util.ElapsedTimer;
import com.rs2.util.ProfilerRegistry;
import com.rs2.util.SaveAllPlayersShutdownHook;
import com.rs2.util.TimestampedPrintStream;
import com.rs2.util.path.ProjectileCollisionMap;
import com.rs2.util.path.WalkingCollisionMap;
import com.rs2.util.plugin.PluginManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server
implements Runnable {
    private static Server o;
    private final String p;
    private final int q;
    private final int r;
    private static long s;
    private static ArrayList t;
    public static ArrayList a;
    private Selector u;
    private InetSocketAddress v;
    private ServerSocketChannel w;
    private ElapsedTimer x;
    private final Queue y = new ConcurrentLinkedQueue();
    private static final Queue z;
    private static Thread A;
    private static Thread B;
    public static int b;
    public static boolean c;
    public static int d;
    public boolean e = false;
    private boolean C = false;
    public static boolean f;
    public static boolean g;
    public static boolean h;
    public static int i;
    private static int D;
    private static int E;
    public static int j;
    public static int k;
    private static int F;
    private static int G;
    private static int H;
    private static int I;
    private static int J;
    private static int K;
    public static int l;
    public static int m;
    public static int n;

    static {
        new TravelManager(new DefaultTravelBootstrap());
        t = new StartingRareItemList();
        a = new ArrayList();
        z = new LinkedList();
        b = 0;
        c = false;
        d = 0;
        f = false;
        g = false;
        h = false;
        i = 0;
        D = 0;
        E = 0;
        j = 150;
        k = 5;
        F = 0;
        G = 0;
        H = 0;
        I = 0;
        J = 0;
        K = 0;
        l = 0;
        m = 0;
        n = 0;
    }

    private Server(String string, int n, int n2) {
        this.p = string;
        this.q = n;
        this.r = 600;
    }

    public static void a() {
        String[] stringArray = "./Config.cfg";
        Object object = new File((String)stringArray);
        if (!((File)object).exists()) {
            ConfigFile.a();
        }
        object = "";
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("./" + (String)stringArray));
        }
        catch (FileNotFoundException fileNotFoundException) {
            System.out.println(String.valueOf(stringArray) + ": file not found.");
        }
        try {
            object = bufferedReader.readLine();
        }
        catch (IOException iOException) {
            System.out.println(String.valueOf(stringArray) + ": error loading file.");
        }
        while (object != null) {
            if (((String)object).startsWith("[")) {
                stringArray = ((String)object).split(";");
                object = stringArray[0].replace("[", "").replace("]", "");
                String[] stringArray2 = new String[stringArray.length - 1];
                int n = 0;
                while (n < stringArray.length - 1) {
                    stringArray2[n] = stringArray[n + 1];
                    ++n;
                }
                ConfigFile.a((String)object, stringArray2);
            }
            object = bufferedReader.readLine();
        }
        bufferedReader.close();
    }

    public static void main(String[] object) {
        object = "0.0.0.0";
        d = 0;
        int n = ServerSettings.serverPort;
        object = new Server((String)object, n, 600);
        if (o != null) {
            throw new IllegalStateException("Singleton already set!");
        }
        o = object;
        A = new Thread(o);
        A.start();
    }

    public static Queue b() {
        return z;
    }

    public final void a(Player player) {
        long l = player.dS();
        if (this.y.contains(l)) {
            System.out.println(String.valueOf(player.getUsername()) + " was already on login queue!");
            player.disconnect();
            return;
        }
        this.y.add(player);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        try {
            Object object;
            B = Thread.currentThread();
            B.setName("ServerEngine");
            System.setOut(new TimestampedPrintStream(System.out));
            System.setErr(new TimestampedPrintStream((OutputStream)System.err, "./data/err.log"));
            this.v = new InetSocketAddress(this.p, this.q);
            System.out.println("Starting " + ServerSettings.serverName + " on " + this.v + "...");
            Calendar calendar = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(2, 9);
            calendar2.set(5, 31);
            long l = calendar.getTimeInMillis();
            long l2 = calendar2.getTimeInMillis();
            long l3 = l2 - l;
            int n = (int)Math.abs(l3 / 24L / 60L / 60L / 1000L);
            if (n <= 7) {
                f = true;
            }
            calendar = Calendar.getInstance();
            calendar2 = Calendar.getInstance();
            calendar2.set(2, 11);
            calendar2.set(5, 24);
            l = calendar.getTimeInMillis();
            l2 = calendar2.getTimeInMillis();
            l3 = l2 - l;
            n = (int)Math.abs(l3 / 24L / 60L / 60L / 1000L);
            if (n <= 7) {
                g = true;
            }
            calendar = Calendar.getInstance();
            int n2 = calendar.get(1);
            Calendar calendar3 = Calendar.getInstance();
            boolean bl = false;
            int n3 = (int)((((double)n2 / 38.0 - (double)(n2 / 38)) * 1440.0 % 60.0 / 2.0 + 56.0) % 30.0) + 1;
            if ((n2 - 5) % 19 == 0) {
                n3 += 28;
                bl = true;
            }
            calendar3.set(n2, 4, n3);
            if (bl && calendar3.get(7) == 7) {
                calendar3.add(5, 7);
            }
            calendar3.add(5, -calendar3.get(7) - 34);
            long l4 = calendar.getTimeInMillis();
            long l5 = calendar3.getTimeInMillis();
            long l6 = l5 - l4;
            int n4 = (int)Math.abs(l6 / 24L / 60L / 60L / 1000L);
            if (n4 <= 7) {
                h = true;
            }
            b = 1;
            Server.k();
            MessageOfTheWeek.a();
            Object object2 = new SaveAllPlayersShutdownHook();
            Runtime.getRuntime().addShutdownHook((Thread)object2);
            PacketDispatcher.registerHandlers();
            CacheStore.initializeCacheStore();
            ProjectileDefinition.c();
            QuestDefinition.b();
            ItemDefinition.loadDefinitions();
            NpcDefinition.loadDefinitions();
            FoodHandler.a();
            ShopManager.a();
            InterfaceDefinition.loadDefinitions();
            PluginManager.a();
            new WorldObjectLookup();
            WorldObjectLookup.loadWorldObjects();
            ObjectDefinition.a();
            WalkingCollisionMap.loadCollisionMaps();
            ProjectileCollisionMap.loadCollisionMaps();
            CacheCoordinateTranslator.a();
            CombatManager.initialize();
            object2 = this;
            try {
                object = new BufferedReader(new FileReader("./data/minutes.log"));
                s = Integer.parseInt(((BufferedReader)object).readLine());
            }
            catch (Exception exception) {
                object = exception;
                exception.printStackTrace();
            }
            World.scheduleTickTask(new ItemDegradeTickTask((Server)object2, 100));
            GameplayHelper.g();
            FishingSpotManager.spawnFishingSpots();
            MusicTrackDefinition.loadDefinitions();
            MusicAreaDefinition.loadDefinitions();
            MultiwayAreaDefinition.a();
            PartyRoomManager.c();
            NpcDropTable.a();
            GameplayHelper.f();
            TreasureTrailManager.filterRewardItemPools();
            BotCombatLoadoutManager.a();
            SkillGuideManager.initialize();
            GrandExchangePriceSample.a();
            new NoopStartupHook();
            object2 = CacheStore.getInstance();
            ((CacheStore)object2).close();
            AlchemistPlaygroundController.a();
            EnchantmentChamberController.b();
            CreatureGraveyardController.d();
            object2 = this;
            this.u = Selector.open();
            ((Server)object2).w = ServerSocketChannel.open();
            DedicatedReactor.a(new DedicatedReactor(Selector.open()));
            DedicatedReactor.b().start();
            ((Server)object2).w.configureBlocking(false);
            ((Server)object2).w.socket().bind(((Server)object2).v);
            object = DedicatedReactor.b();
            synchronized (object) {
                DedicatedReactor.b().a().wakeup();
                ((Server)object2).w.register(DedicatedReactor.b().a(), 16);
            }
            ((Server)object2).x = new ElapsedTimer();
            b = 2;
            long l7 = System.currentTimeMillis();
            c = false;
            for (ItemStack itemStack : t) {
                a.add(itemStack);
            }
            if (ServerSettings.content2007Enabled) {
                GodWarsDungeonManager.a();
            }
            System.out.println("Online!");
            BotTaskDefinition.initializeProgressiveTaskPool();
            BotTaskDefinition.initializeTradeAdvertTaskPool();
            BotTaskDefinition.initializeDropPartyTaskPool();
            QuestEventRegistry.a();
            BotPlayer.removeConfiguredBotNames();
            if (ServerSettings.progressiveBotsPrioritizeExisting && ServerSettings.progressiveBotsEnabled && ServerSettings.progressiveBotCount > 0) {
                BotPlayer.loadExistingProgressiveBotNames();
            }
            BotPlayer.prepareBotNamePools();
            if (ServerSettings.progressiveBotsEnabled) {
                ServerSettings.skillingBotsEnabled = false;
                ServerSettings.walkingBotsEnabled = true;
            }
            if (WildernessBotSettings.a <= 0 && ServerSettings.wildyBotsEnabled) {
                ServerSettings.wildyBotsEnabled = false;
            }
            if (ServerSettings.skillingBotCount <= 0 && ServerSettings.skillingBotsEnabled) {
                ServerSettings.skillingBotsEnabled = false;
            }
            if (ServerSettings.progressiveBotCount <= 0 && ServerSettings.progressiveBotsEnabled) {
                ServerSettings.progressiveBotsEnabled = false;
            }
            if (ServerSettings.tradeBotCount <= 0 && ServerSettings.tradeBotsEnabled) {
                ServerSettings.tradeBotsEnabled = false;
            }
            if (ServerSettings.clanWarsTeamSize <= 0 && ServerSettings.clanWarsBotsEnabled) {
                ServerSettings.clanWarsBotsEnabled = false;
            }
            E = 0;
            if (ServerSettings.wildyBotsEnabled) {
                E += WildernessBotSettings.a;
            }
            if (ServerSettings.progressiveBotsEnabled || ServerSettings.skillingBotsEnabled) {
                E = !ServerSettings.progressiveBotsEnabled ? (E += ServerSettings.skillingBotCount) : (E += ServerSettings.progressiveBotCount);
            }
            if (ServerSettings.tradeBotsEnabled) {
                E += ServerSettings.tradeBotCount;
            }
            if (ServerSettings.clanWarsBotsEnabled) {
                E += ServerSettings.clanWarsTeamSize << 1;
            }
            if (ServerSettings.otherBotsEnabled) {
                E += ServerSettings.otherBotCount;
            }
            D = 0;
            int n5 = E / j;
            if (n5 == 0) {
                Server.j();
            } else {
                object = new BotLoginBatchTask(k, n5);
                World.getTaskScheduler().schedule((TickTask)object);
            }
            if (ServerSettings.lanConnectionsEnabled) {
                LanDiscoveryService.a();
            }
            if (ServerSettings.mysqlHiscoresEnabled) {
                HiscoresDatabase.a();
            }
            while (!Thread.interrupted() && !c) {
                try {
                    long l8 = System.currentTimeMillis() - l7;
                    d = (int)(l8 / 1000L / 60L);
                    if (ServerSettings.backupCharactersEnabled) {
                        if (d % 60 == 0 && this.C) {
                            if (!this.e && World.b() > 0) {
                                this.e = true;
                            }
                            if (this.e) {
                                CharacterFileManager.saveAllPlayers();
                                CharacterFileManager.createTimestampedBackup();
                                System.out.println("Creating hourly backups of character files.");
                            } else {
                                System.out.println("No backup files created, reason: No players have been online");
                            }
                            this.e = false;
                            this.C = false;
                        }
                        if (d % 60 != 0) {
                            this.C = true;
                        }
                    }
                    if (!(ServerSettings.wildyBotsEnabled || ServerSettings.skillingBotsEnabled || ServerSettings.tradeBotsEnabled)) {
                    }
                    Server.k();
                    this.l();
                    this.m();
                }
                catch (Exception exception) {
                    CharacterFileManager.saveAllPlayers();
                    if (ServerSettings.mysqlHiscoresEnabled) {
                        HiscoresDatabase.b();
                    }
                    exception.printStackTrace();
                }
            }
            CharacterFileManager.saveAllPlayers();
            if (ServerSettings.mysqlHiscoresEnabled) {
                HiscoresDatabase.b();
            }
            b = 0;
            Server.k();
            o = null;
            A.stop();
            A = null;
            B = null;
            ((Thread)null).stop();
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        PluginManager.d();
    }

    private static void j() {
        int n = 1;
        while (n <= E) {
            if (n > 0 && n < ServerSettings.botLoginIdLimit) {
                BotPlayer.createBotFromPool(n, "zxcvbn", Server.c());
            }
            ++n;
        }
    }

    public static int c() {
        if (ServerSettings.wildyBotsEnabled && F < WildernessBotSettings.a) {
            ++F;
            return 1;
        }
        if (ServerSettings.progressiveBotsEnabled || ServerSettings.skillingBotsEnabled) {
            if (!ServerSettings.progressiveBotsEnabled) {
                if (G < ServerSettings.skillingBotCount) {
                    ++G;
                    return 0;
                }
            } else if (H < ServerSettings.progressiveBotCount) {
                ++H;
                return 4;
            }
        }
        if (ServerSettings.tradeBotsEnabled && I < ServerSettings.tradeBotCount) {
            ++I;
            return 2;
        }
        if (ServerSettings.clanWarsBotsEnabled && J < ServerSettings.clanWarsTeamSize << 1) {
            int n = ClanWarsBotManager.clanWarsTeamOneBots.size() == ServerSettings.clanWarsTeamSize ? 6 : 5;
            ++J;
            return n;
        }
        if (ServerSettings.otherBotsEnabled && K < ServerSettings.otherBotCount) {
            ++K;
            return 3;
        }
        System.out.println("ERROR at getBotType, did not find type for bot!");
        return -1;
    }

    public static void d() {
    }

    private static void k() {
        l = World.b();
        m = World.d();
        n = World.e();
        ControlPanel.a();
    }

    public static void a(String string) {
        Player[] playerArray = World.f();
        int n = playerArray.length;
        int n2 = 0;
        while (n2 < n) {
            Player player = playerArray[n2];
            if (player != null && player.getIndex() != -1) {
                player.packetSender.sendGameMessage("[SERVER]: " + string);
            }
            ++n2;
        }
    }

    public static void a(SelectionKey selectionKey) {
        Object object = (ServerSocketChannel)selectionKey.channel();
        if ((object = ((ServerSocketChannel)object).accept()) == null) {
            return;
        }
        if (!ConnectionThrottle.a(((SocketChannel)object).socket().getInetAddress().getHostAddress())) {
            ((AbstractInterruptibleChannel)object).close();
            return;
        }
        ((AbstractSelectableChannel)object).configureBlocking(false);
        selectionKey = ((SelectableChannel)object).register(selectionKey.selector(), 1);
        object = new Player(selectionKey);
        selectionKey.attach(object);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    private void l() {
        var1_1 = ProfilerRegistry.getTimer("loginQueue");
        var1_1.start();
        while ((var2_3 = (Player)this.y.poll()) != null) {
            try {
                var2_3.bq();
                var2_3.setConnectionState(PlayerConnectionState.d);
            }
            catch (Exception v0) {
                var3_4 = v0;
                v0.printStackTrace();
                var2_3.disconnect();
            }
        }
        var1_1.stop();
        var3_4 = ProfilerRegistry.getTimer("handleNetworkPackets");
        var3_4.start();
        this.u.selectNow();
        for (Object var1_1 : this.u.selectedKeys()) {
            if (!var1_1.isValid() || !var1_1.isReadable()) continue;
            PacketDispatcher.processIncoming((Player)var1_1.attachment());
        }
        var3_4.stop();
        PluginManager.b();
        World.processTick();
        var1_1 = ProfilerRegistry.getTimer("disconnectingPlayers");
        var1_1.start();
        var4_5 = Server.z;
        synchronized (var4_5) {
            var3_4 = Server.z.iterator();
            while (var3_4.hasNext()) {
                block27: {
                    var2_3 = (Player)var3_4.next();
                    var5_6 = var2_3;
                    if (var2_3.eC) ** GOTO lbl-1000
                    if (var5_6.cn) {
                        v1 = false;
                    } else if (var5_6.getConnectionState() == PlayerConnectionState.e && var5_6.fk() < System.currentTimeMillis()) {
                        v1 = false;
                    } else if (var5_6.getRecentCombatTimer().hasElapsed()) {
                        v1 = false;
                    } else lbl-1000:
                    // 2 sources

                    {
                        v1 = true;
                    }
                    if (v1) continue;
                    var5_6 = var2_3;
                    try {
                        try {
                            var2_3 = ProfilerRegistry.getTimer("tradeDecline");
                            var2_3.start();
                            if (var5_6.getTradePartner() != null) {
                                GameplayHelper.o(var5_6);
                            }
                            PartyRoomManager.d(var5_6);
                            var2_3.stop();
                            var2_3 = ProfilerRegistry.getTimer("duelDecline");
                            var2_3.start();
                            if (var5_6.getDuelSession().i() != null) {
                                if (var5_6.getDuelSession().e()) {
                                    DuelSession.a(var5_6.getDuelSession().i(), var5_6);
                                } else {
                                    var5_6.getDuelSession().i().getDuelController().a(true);
                                    var5_6.getDuelController().a(true);
                                }
                            }
                            var5_6.aK();
                            var2_3.stop();
                            var2_3 = ProfilerRegistry.getTimer("petUnregister");
                            var2_3.start();
                            if (var5_6.getPetManager().b() != null) {
                                var5_6.getPetManager().a();
                            }
                            var2_3.stop();
                            var2_3 = ProfilerRegistry.getTimer("endFightCave");
                            var2_3.start();
                            var5_6.getFightCaveController().b();
                            var2_3.stop();
                            var2_3 = ProfilerRegistry.getTimer("unlockMovement");
                            var2_3.start();
                            var6_7 = var5_6.getMovementQueue();
                            var2_3.stop();
                            var2_3 = ProfilerRegistry.getTimer("resetFollowing");
                            var2_3.start();
                            if (var5_6.getMovementTarget() != null) {
                                EntityTargetMovement.clearMovementTarget(var5_6);
                            }
                            var2_3.stop();
                            var2_3 = ProfilerRegistry.getTimer("logoutPrivatemessage");
                            var2_3.start();
                            var5_6.getSocialManager().refreshFriendStatuses(true);
                            var2_3.stop();
                            var2_3 = var5_6;
                            if (var2_3.isInArea(1790, 2045, 4800, 4915)) {
                                var5_6.moveTo(new Position(3053, 3246, 0));
                            }
                            CharacterFileManager.savePlayer(var5_6);
                            CharacterFileManager.refreshLiveHiscoreRecord(var5_6);
                            if (ServerSettings.mysqlHiscoresEnabled) {
                                HiscoresDatabase.a(var5_6);
                            }
                        }
                        catch (Exception v2) {
                            var2_3 = v2;
                            v2.printStackTrace();
                            var2_3 = ProfilerRegistry.getTimer("unregisterPlayer");
                            var2_3.start();
                            World.c(var5_6);
                            var2_3.stop();
                            break block27;
                        }
                    }
                    catch (Throwable var1_2) {
                        var2_3 = ProfilerRegistry.getTimer("unregisterPlayer");
                        var2_3.start();
                        World.c(var5_6);
                        var2_3.stop();
                        throw var1_2;
                    }
                    var2_3 = ProfilerRegistry.getTimer("unregisterPlayer");
                    var2_3.start();
                    World.c(var5_6);
                    var2_3.stop();
                }
                var3_4.remove();
            }
        }
        var1_1.stop();
    }

    /*
     * Unable to fully structure code
     */
    private void m() {
        block9: {
            try {
                try {
                    var1_1 = this.x.elapsedMillis();
                    var3_4 = (long)this.r - var1_1;
                    if (var3_4 > 0L && var3_4 <= 600L) {
                        Thread.sleep(var3_4);
                    } else {
                        var5_8 = 100L + (Math.abs(var3_4) - (long)this.r) / 6L;
                        System.out.println("[WARNING] Server Load is at " + var5_8 + "%");
                        ProfilerRegistry.b();
                        ProfilerRegistry.resetAll();
                        System.out.println();
                    }
                    break block9;
                }
                catch (Exception v0) {
                    var1_2 = v0;
                    v0.printStackTrace();
                    this.x.reset();
                    var2_9 = 0;
                    ** while (var2_9 < 256)
                }
            }
            catch (Throwable var1_3) {
                this.x.reset();
                var2_10 = 0;
                ** while (var2_10 < 256)
            }
lbl-1000:
            // 1 sources

            {
                var3_5 = PacketDispatcher.packetTimers[var2_9];
                var3_5.getAccumulatedMillis();
                var3_5.reset();
                ++var2_9;
                continue;
            }
lbl26:
            // 1 sources

            return;
lbl-1000:
            // 1 sources

            {
                var3_6 = PacketDispatcher.packetTimers[var2_10];
                var3_6.getAccumulatedMillis();
                var3_6.reset();
                ++var2_10;
                continue;
            }
lbl37:
            // 1 sources

            throw var1_3;
        }
        this.x.reset();
        var2_11 = 0;
        while (var2_11 < 256) {
            var3_7 = PacketDispatcher.packetTimers[var2_11];
            var3_7.getAccumulatedMillis();
            var3_7.reset();
            ++var2_11;
        }
    }

    public static void a(boolean bl) {
        int n = 300;
        if (World.b() == 0 || bl) {
            n = 1;
        }
        Player[] playerArray = World.f();
        int n2 = playerArray.length;
        int n3 = 0;
        while (n3 < n2) {
            Player player = playerArray[n3];
            if (player != null && player.getIndex() != -1) {
                player.packetSender.sendSystemUpdateTimer(n / 30 * 50 + 1);
            }
            ++n3;
        }
        Runnable runnable = new DelayedShutdownTask(n + 3);
        runnable = new Thread(runnable);
        ((Thread)runnable).start();
        b = 3;
    }

    public static void a(long l) {
        s = l;
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./data/minutes.log"));
            bufferedWriter.write(Long.toString(s));
            bufferedWriter.close();
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public static long e() {
        return s;
    }

    public static Server f() {
        return o;
    }

    public final Selector g() {
        return this.u;
    }

    static /* synthetic */ int h() {
        return D;
    }

    static /* synthetic */ int i() {
        return E;
    }

    static /* synthetic */ void a(int n) {
        D = n;
    }
}

