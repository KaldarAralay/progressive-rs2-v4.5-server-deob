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
    private static Server instance;
    private final String bindHost;
    private final int port;
    private final int cycleMillis;
    private static long elapsedMinutes;
    private static ArrayList startingRareItems;
    public static ArrayList trackedRareItems;
    private Selector selector;
    private InetSocketAddress bindAddress;
    private ServerSocketChannel serverSocketChannel;
    private ElapsedTimer cycleTimer;
    private final Queue loginQueue = new ConcurrentLinkedQueue();
    private static final Queue disconnectQueue;
    private static Thread serverThread;
    private static Thread engineThread;
    public static int serverStatus;
    public static boolean shutdownRequested;
    public static int runtimeMinutes;
    public boolean backupPending = false;
    private boolean hourlyBackupArmed = false;
    public static boolean halloweenEventActive;
    public static boolean christmasEventActive;
    public static boolean easterEventActive;
    public static int messageOfTheWeekIndex;
    private static int botLoginBatchIndex;
    private static int configuredBotCount;
    public static int botLoginBatchSize;
    public static int botLoginBatchIntervalTicks;
    private static int wildernessBotLoginCount;
    private static int skillingBotLoginCount;
    private static int progressiveBotLoginCount;
    private static int tradeBotLoginCount;
    private static int clanWarsBotLoginCount;
    private static int otherBotLoginCount;
    public static int onlinePlayerCount;
    public static int adminPlayerCount;
    public static int moderatorPlayerCount;

    static {
        new TravelManager(new DefaultTravelBootstrap());
        startingRareItems = new StartingRareItemList();
        trackedRareItems = new ArrayList();
        disconnectQueue = new LinkedList();
        serverStatus = 0;
        shutdownRequested = false;
        runtimeMinutes = 0;
        halloweenEventActive = false;
        christmasEventActive = false;
        easterEventActive = false;
        messageOfTheWeekIndex = 0;
        botLoginBatchIndex = 0;
        configuredBotCount = 0;
        botLoginBatchSize = 150;
        botLoginBatchIntervalTicks = 5;
        wildernessBotLoginCount = 0;
        skillingBotLoginCount = 0;
        progressiveBotLoginCount = 0;
        tradeBotLoginCount = 0;
        clanWarsBotLoginCount = 0;
        otherBotLoginCount = 0;
        onlinePlayerCount = 0;
        adminPlayerCount = 0;
        moderatorPlayerCount = 0;
    }

    private Server(String string, int n, int n2) {
        this.bindHost = string;
        this.port = n;
        this.cycleMillis = 600;
    }

    public static void loadConfig() {
        String[] stringArray = "./Config.cfg";
        Object object = new File((String)stringArray);
        if (!((File)object).exists()) {
            ConfigFile.writeDefaultConfig();
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
                ConfigFile.applyConfigEntry((String)object, stringArray2);
            }
            object = bufferedReader.readLine();
        }
        bufferedReader.close();
    }

    public static void main(String[] object) {
        object = "0.0.0.0";
        runtimeMinutes = 0;
        int n = ServerSettings.serverPort;
        object = new Server((String)object, n, 600);
        if (instance != null) {
            throw new IllegalStateException("Singleton already set!");
        }
        instance = object;
        serverThread = new Thread(instance);
        serverThread.start();
    }

    public static Queue getDisconnectQueue() {
        return disconnectQueue;
    }

    public final void queueLogin(Player player) {
        long l = player.getNameHash();
        if (this.loginQueue.contains(l)) {
            System.out.println(String.valueOf(player.getUsername()) + " was already on login queue!");
            player.disconnect();
            return;
        }
        this.loginQueue.add(player);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        try {
            Object object;
            engineThread = Thread.currentThread();
            engineThread.setName("ServerEngine");
            System.setOut(new TimestampedPrintStream(System.out));
            System.setErr(new TimestampedPrintStream((OutputStream)System.err, "./data/err.log"));
            this.bindAddress = new InetSocketAddress(this.bindHost, this.port);
            System.out.println("Starting " + ServerSettings.serverName + " on " + this.bindAddress + "...");
            Calendar calendar = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(2, 9);
            calendar2.set(5, 31);
            long l = calendar.getTimeInMillis();
            long l2 = calendar2.getTimeInMillis();
            long l3 = l2 - l;
            int n = (int)Math.abs(l3 / 24L / 60L / 60L / 1000L);
            if (n <= 7) {
                halloweenEventActive = true;
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
                christmasEventActive = true;
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
                easterEventActive = true;
            }
            serverStatus = 1;
            Server.refreshControlPanelStats();
            MessageOfTheWeek.loadAndRotateMessage();
            Object object2 = new SaveAllPlayersShutdownHook();
            Runtime.getRuntime().addShutdownHook((Thread)object2);
            PacketDispatcher.registerHandlers();
            CacheStore.initializeCacheStore();
            ProjectileDefinition.c();
            QuestDefinition.loadDefinitions();
            ItemDefinition.loadDefinitions();
            NpcDefinition.loadDefinitions();
            FoodHandler.loadPotionDefinitions();
            ShopManager.loadShops();
            InterfaceDefinition.loadDefinitions();
            PluginManager.a();
            new WorldObjectLookup();
            WorldObjectLookup.loadWorldObjects();
            ObjectDefinition.a();
            WalkingCollisionMap.loadCollisionMaps();
            ProjectileCollisionMap.loadCollisionMaps();
            CacheCoordinateTranslator.detectDungeonCoordinateShift();
            CombatManager.initialize();
            object2 = this;
            try {
                object = new BufferedReader(new FileReader("./data/minutes.log"));
                elapsedMinutes = Integer.parseInt(((BufferedReader)object).readLine());
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
            MultiwayAreaDefinition.loadDefinitions();
            PartyRoomManager.loadPartyChest();
            NpcDropTable.loadDropTables();
            GameplayHelper.f();
            TreasureTrailManager.filterRewardItemPools();
            BotCombatLoadoutManager.initializeCombatLoadoutTypes();
            SkillGuideManager.initialize();
            GrandExchangePriceSample.loadPriceSamples();
            new NoopStartupHook();
            object2 = CacheStore.getInstance();
            ((CacheStore)object2).close();
            AlchemistPlaygroundController.startCupboardRotation();
            EnchantmentChamberController.startBonusColorCycle();
            CreatureGraveyardController.startFallingBoneHazards();
            object2 = this;
            this.selector = Selector.open();
            ((Server)object2).serverSocketChannel = ServerSocketChannel.open();
            DedicatedReactor.a(new DedicatedReactor(Selector.open()));
            DedicatedReactor.b().start();
            ((Server)object2).serverSocketChannel.configureBlocking(false);
            ((Server)object2).serverSocketChannel.socket().bind(((Server)object2).bindAddress);
            object = DedicatedReactor.b();
            synchronized (object) {
                DedicatedReactor.b().a().wakeup();
                ((Server)object2).serverSocketChannel.register(DedicatedReactor.b().a(), 16);
            }
            ((Server)object2).cycleTimer = new ElapsedTimer();
            serverStatus = 2;
            long l7 = System.currentTimeMillis();
            shutdownRequested = false;
            for (ItemStack itemStack : startingRareItems) {
                trackedRareItems.add(itemStack);
            }
            if (ServerSettings.content2007Enabled) {
                GodWarsDungeonManager.spawnGodWarsNpcs();
            }
            System.out.println("Online!");
            BotTaskDefinition.initializeProgressiveTaskPool();
            BotTaskDefinition.initializeTradeAdvertTaskPool();
            BotTaskDefinition.initializeDropPartyTaskPool();
            QuestEventRegistry.initializeEventHooks();
            BotPlayer.removeConfiguredBotNames();
            if (ServerSettings.progressiveBotsPrioritizeExisting && ServerSettings.progressiveBotsEnabled && ServerSettings.progressiveBotCount > 0) {
                BotPlayer.loadExistingProgressiveBotNames();
            }
            BotPlayer.prepareBotNamePools();
            if (ServerSettings.progressiveBotsEnabled) {
                ServerSettings.skillingBotsEnabled = false;
                ServerSettings.walkingBotsEnabled = true;
            }
            if (WildernessBotSettings.wildyBotCount <= 0 && ServerSettings.wildyBotsEnabled) {
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
            configuredBotCount = 0;
            if (ServerSettings.wildyBotsEnabled) {
                configuredBotCount += WildernessBotSettings.wildyBotCount;
            }
            if (ServerSettings.progressiveBotsEnabled || ServerSettings.skillingBotsEnabled) {
                configuredBotCount = !ServerSettings.progressiveBotsEnabled ? (configuredBotCount += ServerSettings.skillingBotCount) : (configuredBotCount += ServerSettings.progressiveBotCount);
            }
            if (ServerSettings.tradeBotsEnabled) {
                configuredBotCount += ServerSettings.tradeBotCount;
            }
            if (ServerSettings.clanWarsBotsEnabled) {
                configuredBotCount += ServerSettings.clanWarsTeamSize << 1;
            }
            if (ServerSettings.otherBotsEnabled) {
                configuredBotCount += ServerSettings.otherBotCount;
            }
            botLoginBatchIndex = 0;
            int n5 = configuredBotCount / botLoginBatchSize;
            if (n5 == 0) {
                Server.loginAllConfiguredBots();
            } else {
                object = new BotLoginBatchTask(botLoginBatchIntervalTicks, n5);
                World.getTaskScheduler().schedule((TickTask)object);
            }
            if (ServerSettings.lanConnectionsEnabled) {
                LanDiscoveryService.startListener();
            }
            if (ServerSettings.mysqlHiscoresEnabled) {
                HiscoresDatabase.connect();
            }
            while (!Thread.interrupted() && !shutdownRequested) {
                try {
                    long l8 = System.currentTimeMillis() - l7;
                    runtimeMinutes = (int)(l8 / 1000L / 60L);
                    if (ServerSettings.backupCharactersEnabled) {
                        if (runtimeMinutes % 60 == 0 && this.hourlyBackupArmed) {
                            if (!this.backupPending && World.getPlayerCount() > 0) {
                                this.backupPending = true;
                            }
                            if (this.backupPending) {
                                CharacterFileManager.saveAllPlayers();
                                CharacterFileManager.createTimestampedBackup();
                                System.out.println("Creating hourly backups of character files.");
                            } else {
                                System.out.println("No backup files created, reason: No players have been online");
                            }
                            this.backupPending = false;
                            this.hourlyBackupArmed = false;
                        }
                        if (runtimeMinutes % 60 != 0) {
                            this.hourlyBackupArmed = true;
                        }
                    }
                    if (!(ServerSettings.wildyBotsEnabled || ServerSettings.skillingBotsEnabled || ServerSettings.tradeBotsEnabled)) {
                    }
                    Server.refreshControlPanelStats();
                    this.processGameCycle();
                    this.sleepUntilNextCycle();
                }
                catch (Exception exception) {
                    CharacterFileManager.saveAllPlayers();
                    if (ServerSettings.mysqlHiscoresEnabled) {
                        HiscoresDatabase.disconnect();
                    }
                    exception.printStackTrace();
                }
            }
            CharacterFileManager.saveAllPlayers();
            if (ServerSettings.mysqlHiscoresEnabled) {
                HiscoresDatabase.disconnect();
            }
            serverStatus = 0;
            Server.refreshControlPanelStats();
            instance = null;
            serverThread.stop();
            serverThread = null;
            engineThread = null;
            ((Thread)null).stop();
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        PluginManager.d();
    }

    private static void loginAllConfiguredBots() {
        int n = 1;
        while (n <= configuredBotCount) {
            if (n > 0 && n < ServerSettings.botLoginIdLimit) {
                BotPlayer.createBotFromPool(n, "zxcvbn", Server.selectNextBotType());
            }
            ++n;
        }
    }

    public static int selectNextBotType() {
        if (ServerSettings.wildyBotsEnabled && wildernessBotLoginCount < WildernessBotSettings.wildyBotCount) {
            ++wildernessBotLoginCount;
            return 1;
        }
        if (ServerSettings.progressiveBotsEnabled || ServerSettings.skillingBotsEnabled) {
            if (!ServerSettings.progressiveBotsEnabled) {
                if (skillingBotLoginCount < ServerSettings.skillingBotCount) {
                    ++skillingBotLoginCount;
                    return 0;
                }
            } else if (progressiveBotLoginCount < ServerSettings.progressiveBotCount) {
                ++progressiveBotLoginCount;
                return 4;
            }
        }
        if (ServerSettings.tradeBotsEnabled && tradeBotLoginCount < ServerSettings.tradeBotCount) {
            ++tradeBotLoginCount;
            return 2;
        }
        if (ServerSettings.clanWarsBotsEnabled && clanWarsBotLoginCount < ServerSettings.clanWarsTeamSize << 1) {
            int n = ClanWarsBotManager.clanWarsTeamOneBots.size() == ServerSettings.clanWarsTeamSize ? 6 : 5;
            ++clanWarsBotLoginCount;
            return n;
        }
        if (ServerSettings.otherBotsEnabled && otherBotLoginCount < ServerSettings.otherBotCount) {
            ++otherBotLoginCount;
            return 3;
        }
        System.out.println("ERROR at getBotType, did not find type for bot!");
        return -1;
    }

    public static void d() {
    }

    private static void refreshControlPanelStats() {
        onlinePlayerCount = World.getPlayerCount();
        adminPlayerCount = World.getAdminCount();
        moderatorPlayerCount = World.getModeratorCount();
        ControlPanel.a();
    }

    public static void broadcastServerMessage(String string) {
        Player[] playerArray = World.getPlayers();
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

    public static void acceptConnection(SelectionKey selectionKey) {
        Object object = (ServerSocketChannel)selectionKey.channel();
        if ((object = ((ServerSocketChannel)object).accept()) == null) {
            return;
        }
        if (!ConnectionThrottle.tryAcquireConnectionSlot(((SocketChannel)object).socket().getInetAddress().getHostAddress())) {
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
    private void processGameCycle() {
        var1_1 = ProfilerRegistry.getTimer("loginQueue");
        var1_1.start();
        while ((var2_3 = (Player)this.loginQueue.poll()) != null) {
            try {
                var2_3.processPostLogin();
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
        this.selector.selectNow();
        for (Object var1_1 : this.selector.selectedKeys()) {
            if (!var1_1.isValid() || !var1_1.isReadable()) continue;
            PacketDispatcher.processIncoming((Player)var1_1.attachment());
        }
        var3_4.stop();
        PluginManager.b();
        World.processTick();
        var1_1 = ProfilerRegistry.getTimer("disconnectingPlayers");
        var1_1.start();
        var4_5 = Server.disconnectQueue;
        synchronized (var4_5) {
            var3_4 = Server.disconnectQueue.iterator();
            while (var3_4.hasNext()) {
                block27: {
                    var2_3 = (Player)var3_4.next();
                    var5_6 = var2_3;
                    if (var2_3.eC) ** GOTO lbl-1000
                    if (var5_6.cn) {
                        v1 = false;
                    } else if (var5_6.getConnectionState() == PlayerConnectionState.e && var5_6.getDisconnectGraceExpiresAtMillis() < System.currentTimeMillis()) {
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
                                GameplayHelper.declineTrade(var5_6);
                            }
                            PartyRoomManager.returnStagedChestItems(var5_6);
                            var2_3.stop();
                            var2_3 = ProfilerRegistry.getTimer("duelDecline");
                            var2_3.start();
                            if (var5_6.getDuelSession().getOpponent() != null) {
                                if (var5_6.getDuelSession().isStarted()) {
                                    DuelSession.finishDuelVictory(var5_6.getDuelSession().getOpponent(), var5_6);
                                } else {
                                    var5_6.getDuelSession().getOpponent().getDuelController().resetDuel(true);
                                    var5_6.getDuelController().resetDuel(true);
                                }
                            }
                            var5_6.clearTemporaryCutsceneNpcs();
                            var2_3.stop();
                            var2_3 = ProfilerRegistry.getTimer("petUnregister");
                            var2_3.start();
                            if (var5_6.getPetManager().b() != null) {
                                var5_6.getPetManager().a();
                            }
                            var2_3.stop();
                            var2_3 = ProfilerRegistry.getTimer("endFightCave");
                            var2_3.start();
                            var5_6.getFightCaveController().cleanupIfInFightCave();
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
                                HiscoresDatabase.savePlayer(var5_6);
                            }
                        }
                        catch (Exception v2) {
                            var2_3 = v2;
                            v2.printStackTrace();
                            var2_3 = ProfilerRegistry.getTimer("unregisterPlayer");
                            var2_3.start();
                            World.unregisterPlayer(var5_6);
                            var2_3.stop();
                            break block27;
                        }
                    }
                    catch (Throwable var1_2) {
                        var2_3 = ProfilerRegistry.getTimer("unregisterPlayer");
                        var2_3.start();
                        World.unregisterPlayer(var5_6);
                        var2_3.stop();
                        throw var1_2;
                    }
                    var2_3 = ProfilerRegistry.getTimer("unregisterPlayer");
                    var2_3.start();
                    World.unregisterPlayer(var5_6);
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
    private void sleepUntilNextCycle() {
        block9: {
            try {
                try {
                    var1_1 = this.cycleTimer.elapsedMillis();
                    var3_4 = (long)this.cycleMillis - var1_1;
                    if (var3_4 > 0L && var3_4 <= 600L) {
                        Thread.sleep(var3_4);
                    } else {
                        var5_8 = 100L + (Math.abs(var3_4) - (long)this.cycleMillis) / 6L;
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
                    this.cycleTimer.reset();
                    var2_9 = 0;
                    ** while (var2_9 < 256)
                }
            }
            catch (Throwable var1_3) {
                this.cycleTimer.reset();
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
        this.cycleTimer.reset();
        var2_11 = 0;
        while (var2_11 < 256) {
            var3_7 = PacketDispatcher.packetTimers[var2_11];
            var3_7.getAccumulatedMillis();
            var3_7.reset();
            ++var2_11;
        }
    }

    public static void scheduleShutdown(boolean bl) {
        int n = 300;
        if (World.getPlayerCount() == 0 || bl) {
            n = 1;
        }
        Player[] playerArray = World.getPlayers();
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
        serverStatus = 3;
    }

    public static void setElapsedMinutes(long l) {
        elapsedMinutes = l;
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./data/minutes.log"));
            bufferedWriter.write(Long.toString(elapsedMinutes));
            bufferedWriter.close();
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public static long getElapsedMinutes() {
        return elapsedMinutes;
    }

    public static Server getInstance() {
        return instance;
    }

    public final Selector getSelector() {
        return this.selector;
    }

    static /* synthetic */ int getBotLoginBatchIndex() {
        return botLoginBatchIndex;
    }

    static /* synthetic */ int getConfiguredBotCount() {
        return configuredBotCount;
    }

    static /* synthetic */ void setBotLoginBatchIndex(int n) {
        botLoginBatchIndex = n;
    }
}

