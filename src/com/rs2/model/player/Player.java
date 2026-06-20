/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.player;

import com.rs2.ConnectionThrottle;
import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.bot.BotPlayer;
import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.BotTradeAdvertManager;
import com.rs2.bot.DropPartyBotManager;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.bot.combat.BotCombatLoadoutManager;
import com.rs2.bot.combat.BotCombatLoadoutTables;
import com.rs2.bot.route.BotWorldRoute;
import com.rs2.bot.route.BotWorldRouteChoice;
import com.rs2.bot.route.BotWorldRouteWalker;
import com.rs2.cache.CacheArchiveEntry;
import com.rs2.cache.CacheDefinitionIndex;
import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.Entity;
import com.rs2.model.EntityTargetMovement;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.bankpin.BankPinManager;
import com.rs2.model.combat.AttackValidationResult;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.combat.CombatCycleEvent;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.combat.CombatType;
import com.rs2.model.combat.PvpCombatReference;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.effect.PoisonEffect;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.combat.special.SpecialAttackDefinition;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.gameplay.barrows.BarrowsManager;
import com.rs2.model.gameplay.duel.DuelArenaLocationManager;
import com.rs2.model.gameplay.duel.DuelController;
import com.rs2.model.gameplay.duel.DuelInterfaceManager;
import com.rs2.model.gameplay.duel.DuelSession;
import com.rs2.model.gameplay.fightcave.FightCaveController;
import com.rs2.model.gameplay.godwars.GodWarsDungeonManager;
import com.rs2.model.gameplay.magetrainingarena.AlchemistPlaygroundController;
import com.rs2.model.gameplay.magetrainingarena.CreatureGraveyardController;
import com.rs2.model.gameplay.magetrainingarena.EnchantmentChamberController;
import com.rs2.model.gameplay.magetrainingarena.TelekineticTheatreController;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.interaction.InteractionDispatcher;
import com.rs2.model.interaction.InteractionType;
import com.rs2.model.item.ItemContainer;
import com.rs2.model.item.ItemContainerType;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.item.action.BarrowsRepairHandler;
import com.rs2.model.item.action.CaveLightSourceDefinition;
import com.rs2.model.item.consumable.FoodHandler;
import com.rs2.model.item.consumable.PotionHandler;
import com.rs2.model.music.MusicManager;
import com.rs2.model.npc.Npc;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectDefinition;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.objects.WorldObject;
import com.rs2.model.player.BankManager;
import com.rs2.model.player.BankRearrangeMode;
import com.rs2.model.player.BarrowsChestDamageTask;
import com.rs2.model.player.BotLumbridgeResetTask;
import com.rs2.model.player.CharacterFileRecord;
import com.rs2.model.player.DeathItemValueComparator;
import com.rs2.model.player.DelayedPositionMoveTask;
import com.rs2.model.player.DropGodCapeTask;
import com.rs2.model.player.EmoteManager;
import com.rs2.model.player.EquipmentManager;
import com.rs2.model.player.GrandExchangeManager;
import com.rs2.model.player.HiscoreEntryComparator;
import com.rs2.model.player.InventoryManager;
import com.rs2.model.player.PetManager;
import com.rs2.model.player.PlayerConnectionState;
import com.rs2.model.player.PlayerGroup;
import com.rs2.model.player.PostLoginSyncTask;
import com.rs2.model.player.PostTeleportBotContinuationTask;
import com.rs2.model.player.ProtectedItemValueComparator;
import com.rs2.model.player.RetryMissingNpcSearchTask;
import com.rs2.model.player.RetryMissingObjectSearchTask;
import com.rs2.model.player.RetryUnreachableObjectTask;
import com.rs2.model.player.SocialManager;
import com.rs2.model.player.StartBotCommandTask;
import com.rs2.model.player.TradeState;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.model.quest.QuestManager;
import com.rs2.model.quest.QuestScript;
import com.rs2.model.quest.impl.ErnestTheChickenQuest;
import com.rs2.model.randomevent.sandwichlady.SandwichLadyManager;
import com.rs2.model.reward.ActionRewardDefinition;
import com.rs2.model.skill.EquipmentKeywordBootstrap;
import com.rs2.model.skill.ItemCombinationHandler;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.skill.cooking.CookableFoodDefinition;
import com.rs2.model.skill.cooking.CookingManager;
import com.rs2.model.skill.cooking.DairyChurnHandler;
import com.rs2.model.skill.cooking.WineFermentationHandler;
import com.rs2.model.skill.farming.AllotmentPatchManager;
import com.rs2.model.skill.farming.BushPatchManager;
import com.rs2.model.skill.farming.CompostBinManager;
import com.rs2.model.skill.farming.FarmingToolStore;
import com.rs2.model.skill.farming.FlowerPatchManager;
import com.rs2.model.skill.farming.FruitTreePatchManager;
import com.rs2.model.skill.farming.HerbPatchManager;
import com.rs2.model.skill.farming.HopsPatchManager;
import com.rs2.model.skill.farming.PlantPotHandler;
import com.rs2.model.skill.farming.SpecialCropPatchManager;
import com.rs2.model.skill.farming.SpecialTreePatchManager;
import com.rs2.model.skill.farming.TreePatchManager;
import com.rs2.model.skill.firemaking.FiremakingHandler;
import com.rs2.model.skill.fishing.FishingHandler;
import com.rs2.model.skill.guide.SkillGuideManager;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.skill.magic.Spellbook;
import com.rs2.model.skill.magic.TeleportManager;
import com.rs2.model.skill.mining.MiningManager;
import com.rs2.model.skill.prayer.BoneBuryingHandler;
import com.rs2.model.skill.prayer.PrayerManager;
import com.rs2.model.skill.runecrafting.RunecraftingObjectHandler;
import com.rs2.model.skill.slayer.SlayerManager;
import com.rs2.model.skill.smithing.SmeltingHandler;
import com.rs2.model.skill.smithing.SmithingBarDefinition;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.model.task.TickTask;
import com.rs2.net.DedicatedReactor;
import com.rs2.net.IsaacCipher;
import com.rs2.net.LoginProtocol;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketBuffer;
import com.rs2.net.packet.PacketDispatcher;
import com.rs2.net.packet.PacketReader;
import com.rs2.net.packet.PacketSender;
import com.rs2.net.packet.PacketWriter;
import com.rs2.util.CharacterFileManager;
import com.rs2.util.ChatTextCodec;
import com.rs2.util.ElapsedTimer;
import com.rs2.util.FileUtil;
import com.rs2.util.GameUtil;
import com.rs2.util.RectangularArea;
import com.rs2.util.TextUtil;
import com.rs2.util.path.PathFinder;
import com.rs2.util.plugin.PlayerPlugin;
import com.rs2.util.plugin.PluginManager;
import java.awt.Color;
import java.awt.Polygon;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Player
extends Entity {
    public int f = ServerSettings.defaultMovementSystem;
    public int g = 0;
    public int h = 0;
    public int i = -1;
    public boolean j = false;
    public ArrayList k = new ArrayList();
    public int l = 0;
    public int[] m = new int[4];
    public int[] n = new int[4];
    public long o;
    public long p = -1L;
    public PlayerGroup q;
    public PlayerGroup r;
    public Player s;
    public boolean t = false;
    public int u;
    public int v = 0;
    public int w = 0;
    public int gameMode = 0;
    public int y = 0;
    public Position z;
    public ArrayList A = new ArrayList();
    private int combatLevel;
    private final SelectionKey selectionKey;
    private final ByteBuffer inboundBuffer;
    private final ByteBuffer outboundBuffer;
    private SocketChannel socketChannel;
    private PlayerConnectionState connectionState = PlayerConnectionState.a;
    private IsaacCipher outboundCipher;
    private IsaacCipher inboundCipher;
    private int currentPacketOpcode = -1;
    private int currentPacketLength = -1;
    private String username;
    private String password;
    private String submittedPassword;
    private int eR;
    private int eS = 0;
    private int eT;
    private int openInterfaceId = -1;
    public MagicSpellAction B;
    private final ElapsedTimer packetReadTimer = new ElapsedTimer();
    private final List eW = new LinkedList();
    private final List eX = new LinkedList();
    private InventoryManager eY = new InventoryManager(this);
    private EquipmentManager eZ = new EquipmentManager(this);
    private SocialManager fa = new SocialManager(this);
    private PrayerManager fb = new PrayerManager(this);
    private TeleportManager fc = new TeleportManager(this);
    private EmoteManager fd = new EmoteManager(this);
    private SkillManager fe = new SkillManager(this);
    private QuestManager ff = new QuestManager(this);
    private RunecraftingObjectHandler fg = new RunecraftingObjectHandler(this);
    public PacketSender packetSender = new PacketSender(this);
    private SlayerManager fh = new SlayerManager(this);
    private DuelController fi = new DuelController(this);
    private DuelSession fj = new DuelSession(this);
    private FightCaveController fk = new FightCaveController(this);
    private AlchemistPlaygroundController fl = new AlchemistPlaygroundController(this);
    private CreatureGraveyardController fm = new CreatureGraveyardController(this);
    private TelekineticTheatreController fn = new TelekineticTheatreController(this);
    private EnchantmentChamberController fo = new EnchantmentChamberController(this);
    private DuelInterfaceManager fp = new DuelInterfaceManager(this);
    private DuelArenaLocationManager fq = new DuelArenaLocationManager(this);
    private WineFermentationHandler fr = new WineFermentationHandler(this);
    private FishingHandler fs = new FishingHandler(this);
    private ItemCombinationHandler ft = new ItemCombinationHandler(this);
    private SkillGuideManager fu = new SkillGuideManager(this);
    private FoodHandler fv = new FoodHandler(this);
    private PotionHandler fw = new PotionHandler(this);
    private MiningManager fx = new MiningManager(this);
    private CookingManager fy = new CookingManager(this);
    private CompostBinManager fz = new CompostBinManager(this);
    private AllotmentPatchManager fA = new AllotmentPatchManager(this);
    private FlowerPatchManager fB = new FlowerPatchManager(this);
    private HerbPatchManager fC = new HerbPatchManager(this);
    private HopsPatchManager fD = new HopsPatchManager(this);
    private BushPatchManager fE = new BushPatchManager(this);
    private PlantPotHandler fF = new PlantPotHandler(this);
    private TreePatchManager fG = new TreePatchManager(this);
    private FruitTreePatchManager fH = new FruitTreePatchManager(this);
    private SpecialTreePatchManager fI = new SpecialTreePatchManager(this);
    private SpecialCropPatchManager fJ = new SpecialCropPatchManager(this);
    private FarmingToolStore fK = new FarmingToolStore(this);
    private FiremakingHandler fL = new FiremakingHandler(this);
    private BoneBuryingHandler fM = new BoneBuryingHandler(this);
    private PetManager fN;
    private SandwichLadyManager fO;
    private DialogueManager fP;
    private BankPinManager fQ;
    private LoginProtocol fR;
    private Position fS;
    private int playerRights;
    private boolean fU;
    public boolean D;
    private int publicChatColor;
    private int fW;
    private byte[] publicChatPayload;
    private int[] fY;
    private int gender;
    private final int[] appearanceParts;
    private final int[] appearanceColors;
    private ItemContainer bankContainer;
    private ItemContainer gd;
    private ItemContainer ge;
    private int interactionSpellButtonId;
    private int interactionTargetX;
    private int interactionTargetY;
    private int interactionTargetPlane;
    private int interactionTargetId;
    private int selectedItemId;
    private int selectedItemInterfaceId;
    private int selectedItemSlot;
    private int interactionTargetIndex;
    private boolean bankWithdrawNoteMode;
    private int selectedInterfaceItemId;
    private int selectedInterfaceSlot;
    private int selectedInterfaceId;
    private BankRearrangeMode bankRearrangeMode;
    private int gt;
    private boolean gu;
    private Map gv;
    private long[] friendsList;
    private long[] ignoreList;
    private int gy;
    private TradeState gz;
    private int[] gA;
    private int[] gB;
    private int runEnergyRaw;
    private boolean gD;
    private boolean gE;
    private boolean gF;
    private int gG;
    private int gH;
    private int gI;
    private boolean[] gJ;
    private Spellbook spellbook;
    public Spellbook E;
    private boolean autoRetaliate;
    public boolean F;
    private boolean gM;
    private int brightness;
    private int mouseButtons;
    private int publicChatEffects;
    private int splitPrivateChat;
    private int privateChatMode;
    private int publicChatMode;
    private int tradeMode;
    private int acceptAid;
    private int musicVolume;
    private int effectVolume;
    private int gX;
    private boolean specialAttackEnabled;
    private int specialEnergy;
    private int ringOfRecoilLife;
    private int ringOfForgingLife;
    private int bindingNecklaceCharge;
    private int fightMode;
    private boolean he;
    private boolean hf;
    private boolean hg;
    private boolean hh;
    private boolean hi;
    private boolean hj;
    private boolean hk;
    private boolean hl;
    private List hm;
    private long hn;
    private int ho;
    private int hp;
    private int hq;
    private SmithingBarDefinition hr;
    private int hs;
    public String interfaceAction;
    public Npc H;
    private long ht;
    private WeaponProfile hu;
    private SpecialAttackDefinition hv;
    private List hw;
    private SpellDefinition hx;
    private SpellDefinition hy;
    private boolean hz;
    public int I;
    public int J;
    public ItemStack K;
    public Player L;
    public int pendingGameMode;
    public int N;
    public int O;
    public int P;
    public int Q;
    public int R;
    public int S;
    public int T;
    public int U;
    public ItemStack[] V;
    public int W;
    public int X;
    private boolean hA;
    private RectangularArea hB;
    private List hC;
    private long muteExpires;
    private long banExpires;
    private boolean[] hF;
    private int hG;
    private int hH;
    private boolean brimhavenOpen;
    private Position hJ;
    private ItemStack hK;
    private boolean hL;
    private Npc hM;
    private ItemStack hN;
    private int selectedLampSkill;
    private int[] sidebarInterfaceIds;
    public int Y;
    public int Z;
    public int aa;
    public int ab;
    public int ac;
    public int ad;
    public int ae;
    public int af;
    public int ag;
    public Position ah;
    private boolean hQ;
    public boolean ai;
    public boolean aj;
    public int ak;
    public double al;
    public double am;
    public int an;
    public int ao;
    public int ap;
    public int aq;
    public int ar;
    public boolean killedClueAttacker;
    public int at;
    public int au;
    public ItemStack[] av;
    private int hR;
    public boolean aw;
    public int ax;
    private int hS;
    private int hT;
    private int hU;
    private String hV;
    private int inventoryOverlayInterfaceId;
    private int[] hX;
    private long hY;
    private int hZ;
    private Player ia;
    private Player ib;
    public boolean ay;
    public int az;
    public boolean dropPartyLeader;
    public boolean dropPartyPretaskComplete;
    public boolean dropPartyFollower;
    public boolean dropPartySentToAssignedDrop;
    public Position dropPartyAssignedDropPosition;
    public int dropPartyPretaskLoopCount;
    public int tradeAdvertAcceptedQuantity;
    public int tradeAdvertOfferPoolIndex;
    public int tradeAdvertQuantityOptionIndex;
    public boolean tradeAdvertInitialOfferPlaced;
    public boolean tradeAdvertScam;
    public boolean tradeAdvertVariableQuantity;
    public int tradeAdvertLastOfferAmount;
    public int tradeAdvertMode;
    public int botAdvertItemId;
    public int tradeAdvertQuantityRemaining;
    public int tradeAdvertUnitPrice;
    public String botPublicChatMessage;
    public int botPublicChatColor;
    public int botPublicChatEffect;
    public Player pendingTradeTarget;
    private int ic;
    private int id;
    private int ie;
    private long cfr_renamed_15;
    public int savedWorldRouteIndex;
    public BotWorldRouteChoice currentWorldRouteChoice;
    public int botTaskItemId;
    public int botSmithingProductItemId;
    public int botShopItemAmount;
    public boolean botUseTaskItemOnTarget;
    public int botShopBuyMode;
    public int botSkillTargetSkillId;
    public int botSkillTargetLevel;
    public int be;
    public int bf;
    public int bg;
    public int bh;
    public int botCompletionItemId;
    public int botCompletionItemAmount;
    public int bk;
    public int bl;
    public int bm;
    public int bn;
    public boolean savedWorldRouteReversed;
    public int botElementalSpellIndex;
    public int currentBotTaskTypeId;
    public int currentBotTaskIndex;
    public int deferredBotTaskTypeId;
    public int deferredBotTaskIndex;
    public BotTaskDefinition deferredBotTask;
    public ArrayList botCombatLoadoutItemIds;
    public ArrayList botMeleeLoadoutItemIds;
    public ArrayList botRangedLoadoutItemIds;
    public ArrayList botMagicLoadoutItemIds;
    public ArrayList botShopSellItemIds;
    public int botCombatLoadoutSlotCursor;
    public boolean bB;
    private int ig;
    public int botPvpTeamInviteTicks;
    public Player botPvpPendingTeamTarget;
    public ArrayList botPvpRejectedTeamTargets;
    public ArrayList botPvpTeamRequesters;
    public Player botPvpChatSource;
    public String botPvpChatMessage;
    private int[] questStates;
    public int[] bI;
    public int[] bJ;
    public int bK;
    public ArrayList visibleDynamicObjects;
    public ArrayList pendingDynamicObjectRemovals;
    public int bN;
    public int bO;
    public int bP;
    private String ii;
    private String ij;
    public boolean bQ;
    public int bR;
    public int bS;
    public int[] bT;
    public boolean bU;
    public String bV;
    public int flourMillHopperGrainCount;
    public boolean bX;
    public long bY;
    public int bZ;
    public int ca;
    public String cb;
    public long cc;
    private boolean ik;
    public boolean cd;
    public String[] ce;
    public boolean cf;
    public int cg;
    public int ch;
    public int ci;
    public int gatheringHazardCounter;
    public long ck;
    public long cl;
    public int cm;
    public boolean cn;
    public long co;
    public Entity botLootResumeTarget;
    public ArrayList botLootGroundItems;
    public ArrayList botLootPickupTargets;
    public ArrayList botLootSellGroundItems;
    public ArrayList botLootSellItems;
    public boolean clanWarsBot;
    public int clanWarsTeamId;
    public long botTaskStartTimeMillis;
    public int botTaskDurationMinutes;
    public long botTaskSavedElapsedMillis;
    public boolean botTaskReturnToBankRequested;
    public ItemStack[] botTaskRequiredItems;
    public Position botEscapeLastPosition;
    public int botEscapeStuckTicks;
    public boolean botCombatEscapeActive;
    public boolean botMagicPenaltyGearUnequipped;
    public boolean botAntipoisonAvailable;
    public int botActiveCombatStyle;
    public int botPrimaryCombatStyle;
    public int botSpecialCombatStyle;
    public int botOpponentCombatStyle;
    public int botCombatStyle;
    public int botSpecialAttackEnergyCost;
    public boolean botStrengthPotionDepleted;
    public boolean cN;
    public boolean botFoodDepleted;
    public int botWeaponItemId;
    public int botShieldItemId;
    public int botSpecialWeaponItemId;
    public SpellDefinition botCombatSpell;
    public int botFoodItemId;
    public int botWildernessMaxY;
    public String botCombatState;
    public TickTask botEscapeLogoutTask;
    public TickTask botCombatTickTask;
    public int botMagicGearSwapDelayTicks;
    public int botThreatEscapeDelayTicks;
    public int botPrayerSwitchDelayTicks;
    public int botQueuedPrayerId;
    public int botEatDelayTicks;
    public int botWeaponSwapDelayTicks;
    public boolean de;
    public boolean df;
    private boolean il;
    private ArrayList im;
    public int botPathSegmentIndex;
    public BotTaskDefinition currentBotTask;
    public int botTargetNpcId;
    public int botPathWaypointIndex;
    public String botEscapeRouteName;
    public BotRoute currentBotRoute;
    public boolean dm;
    public boolean dn;
    public ArrayList botInteractionTargetIds;
    public boolean botEnabled;
    public int botInteractionOption;
    public String botTaskState;
    public int ds;
    public String dt;
    public int currentBankTab;
    public int dv;
    public boolean[] dw;
    public int[] dx;
    public int[] dy;
    public int[] dz;
    public boolean[] dA;
    public int[] dB;
    public int[] dC;
    public int[] dD;
    public int[] dE;
    public boolean[] dF;
    public int dG;
    public int dH;
    public int dI;
    public int dJ;
    public int dK;
    public int dL;
    public int dM;
    public int dN;
    public int dO;
    public boolean dP;
    private ArrayList in;
    public boolean dQ;
    public long dR;
    public int dS;
    public int dT;
    public int dU;
    public int dV;
    public int dW;
    public int dX;
    public int dY;
    public int dZ;
    public int ea;
    public int eb;
    public int ec;
    public int ed;
    public int ee;
    public boolean ef;
    public long eg;
    public long eh;
    public long ei;
    public long ej;
    public String ek;
    public boolean el;
    public int em;
    public boolean en;
    public boolean eo;
    public int[] ep;
    public int eq;
    public int er;
    public int es;
    public int et;
    private boolean io;
    public static Player eu = null;
    private int ip;
    private int iq;
    private TickTask ir;
    public boolean ev;
    private boolean is;
    boolean ew;
    private static String[] it = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", " ", ".", "+", "-", "="};
    private static String[] iu = new String[]{"72.91.46.160", "46.116.160.130", "92.9.242.172", "173.197.132.38", "84.248.174.119"};
    public String ex;
    private String iv;
    public String ey;
    public int ez;
    public int eA;
    public int eB;
    public boolean eC;
    private static Polygon iw = null;
    public int eD;
    public int eE;
    public boolean eF;
    public boolean eG;
    public int eH;
    public int eI;

    public final void aK() {
        if (this.A.size() > 0) {
            for (Npc npc : this.A) {
                if (npc == null) continue;
                GameplayHelper.a(npc);
            }
        }
        this.A = new ArrayList();
    }

    public final Npc o(int n) {
        if (this.A.size() > 0) {
            for (Npc npc : this.A) {
                if (npc == null || npc.getNpcId() != n) continue;
                return npc;
            }
        }
        return null;
    }

    public final void p(int n) {
        Npc npc = new Npc(1412);
        Npc npc2 = new Npc(1426);
        Npc npc3 = new Npc(1414);
        Npc npc4 = new Npc(1416);
        Npc npc5 = new Npc(1418);
        GameplayHelper.a(this, npc, 2699, 9168, n, -1, false, false);
        GameplayHelper.a(this, npc2, 2697, 9175, n, -1, false, false);
        GameplayHelper.a(this, npc3, 2703, 9168, n, -1, false, false);
        GameplayHelper.a(this, npc4, 2698, 9172, n, -1, false, false);
        GameplayHelper.a(this, npc5, 2697, 9171, n, -1, false, false);
        npc.getUpdateState().setGraphic(86, 25);
        npc2.getUpdateState().setGraphic(86, 25);
        npc3.getUpdateState().setGraphic(86, 25);
        npc4.getUpdateState().setGraphic(86, 25);
        npc5.getUpdateState().setGraphic(86, 25);
        this.A.add(npc);
        this.A.add(npc2);
        this.A.add(npc3);
        this.A.add(npc4);
        this.A.add(npc5);
        if (this.questStates[62] == 21 && !this.l(62, 16)) {
            Player player = this;
            player.packetSender.sendEntityHintIcon(1, npc.getIndex());
            return;
        }
        if (this.questStates[62] == 21 && this.l(62, 16)) {
            Player player = this;
            player.packetSender.sendEntityHintIcon(1, npc2.getIndex());
        }
    }

    private boolean l(int n, int n2) {
        if (this.bI[62] == 0) {
            return false;
        }
        return (this.bI[62] & GameUtil.b(16)) != 0;
    }

    public final void d(int n, int n2) {
        ItemStack[] itemStackArray;
        Object object = new ItemStack(n, n2);
        ItemStack itemStack = object;
        object = this;
        if (((Player)object).botTaskRequiredItems != null) {
            itemStackArray = new ItemStack[((Player)object).botTaskRequiredItems.length + 1];
            int n3 = 0;
            while (n3 < ((Player)object).botTaskRequiredItems.length) {
                itemStackArray[n3] = ((Player)object).botTaskRequiredItems[n3];
                ++n3;
            }
            itemStackArray[((Player)object).botTaskRequiredItems.length] = itemStack;
        } else {
            ItemStack[] itemStackArray2 = new ItemStack[1];
            itemStackArray = itemStackArray2;
            itemStackArray2[0] = itemStack;
        }
        ((Player)object).botTaskRequiredItems = itemStackArray;
    }

    private boolean A(boolean bl) {
        if (bl) {
            Player player = this;
            System.out.println("Detected possibly frozen bot: " + player.username + " at: " + this.getPosition() + ", trying to apply fix.");
        }
        if (this.botTaskState.equals("do task")) {
            if (this.iq == 0) {
                ++this.iq;
                this.moveTo(this.currentBotTask.getTaskPosition());
                Player player = this;
                System.out.println(String.valueOf(player.username) + " teleported to task location: " + this.currentBotTask.getTaskPosition());
                this.e();
            } else {
                Player player = this;
                System.out.println(String.valueOf(player.username) + " can't seem to continue its task.");
                System.out.println("Reseting to lumbridge and picking new task.");
                this.iq = 0;
                this.a();
            }
            return true;
        }
        if (!BotWorldRouteWalker.findWorldRoute(this)) {
            this.moveTo(this.currentBotTask.getStartPosition());
            Player player = this;
            System.out.println(String.valueOf(player.username) + " teleported to task start location: " + this.currentBotTask.getStartPosition());
            return true;
        }
        return false;
    }

    private void a() {
        this.bB = true;
        Object object = this;
        this.botShopSellItemIds.clear();
        this.botTaskRequiredItems = null;
        this.moveTo(new Position(ServerSettings.respawnX, ServerSettings.respawnY, ServerSettings.respawnPlane));
        object = new BotLumbridgeResetTask(this, 2, (Player)object);
        World.getTaskScheduler().schedule((TickTask)object);
    }

    public final boolean aL() {
        int n = this.getPosition().getX();
        int n2 = this.getPosition().getY();
        int n3 = this.getPosition().getPlane();
        Player player = this;
        long l = player.fe.getTotalExperience();
        if (n == this.ic && n2 == this.id && n3 == this.ie && l == this.cfr_renamed_15) {
            ++this.ig;
            if (this.ig == 5) {
                return true;
            }
        } else {
            this.ig = 0;
        }
        this.ic = n;
        this.id = n2;
        this.ie = n3;
        this.cfr_renamed_15 = l;
        return false;
    }

    public final void aM() {
        int n;
        Object object;
        this.botEnabled = true;
        this.botTaskStartTimeMillis = System.currentTimeMillis();
        Player player = this;
        if (BotPlayer.forceResetBotNames.contains(player.username.toLowerCase())) {
            player = this;
            System.out.println(String.valueOf(player.username) + " was force reseted.");
            this.a();
            return;
        }
        Object object2 = null;
        if (this.currentBotTaskIndex != -1 && this.currentBotTaskTypeId != -1) {
            object = BotTaskDefinition.getTaskByTypeAndIndex(this.currentBotTaskTypeId, this.currentBotTaskIndex);
            player = this;
            this.currentBotTask = object;
            this.currentBotTask.configureTaskInteractionTargets(this);
        }
        if (this.deferredBotTaskIndex != -1 && this.deferredBotTaskTypeId != -1) {
            this.deferredBotTask = BotTaskDefinition.getTaskByTypeAndIndex(this.deferredBotTaskTypeId, this.deferredBotTaskIndex);
        }
        if (this.currentBotTask == null) {
            player = this;
            System.out.println("Reseting " + player.username + " to lumbridge and picking new task");
            if (!this.de) {
                player = this;
                player.packetSender.sendGameMessage("You didn't have a bot task to continue.");
                player = this;
                player.packetSender.sendGameMessage("You have been reset to Lumbridge and given new task.");
            }
            this.a();
            return;
        }
        if (this.currentBotTask.taskRouteSegments != null && this.botPathSegmentIndex > this.currentBotTask.taskRouteSegments.length - 1) {
            player = this;
            System.out.println(String.valueOf(player.username) + " has bugged task path at: " + this.getPosition());
            System.out.println("Reseting to lumbridge and picking new task");
            this.a();
            return;
        }
        if (this.savedWorldRouteIndex != -1) {
            object = BotWorldRoute.values()[this.savedWorldRouteIndex];
            object2 = new BotWorldRouteChoice((BotWorldRoute)((Object)object), this.savedWorldRouteReversed);
        }
        if (this.botAdvertItemId != -1) {
            BotTradeAdvertManager.updateTradeAdvertMessage(this);
            CacheArchiveEntry.startTradeOfferTick(this);
        }
        if (this.currentBotTask.membersOnly && ServerSettings.freeToPlayWorld) {
            this.a();
            return;
        }
        boolean bl = true;
        if (!this.currentBotTask.isAvailableFor(this, true)) {
            player = this;
            System.out.println("Detected possibly bugged bot: " + player.username + " at: " + this.getPosition() + ", trying to apply fix.");
            bl = false;
            this.botTaskReturnToBankRequested = true;
            this.currentBotTask.startWalkToBank(this);
        }
        if (this.currentBotTaskTypeId == 3 && (this.botTaskRequiredItems == null || this.botTaskRequiredItems.length == 0)) {
            this.botTaskRequiredItems = new ItemStack[]{new ItemStack(this.botTaskItemId, 28)};
        }
        if (this.currentBotTaskTypeId == 5 && (this.botTaskRequiredItems == null || this.botTaskRequiredItems.length == 0) && this.botTaskItemId > 0) {
            SmeltingHandler.prepareBotSmeltingRequirements(this, this.botTaskItemId);
        }
        if (this.botTaskState.equals("do task") && this.currentBotTaskTypeId != 14) {
            int n2 = GameUtil.b(this.getPosition(), this.currentBotTask.getTaskPosition());
            if (n2 >= 60 && !this.A(bl)) {
                return;
            }
            if (this.currentBotTaskTypeId == 3) {
                Player player2 = this;
                if (player2.eY.getItemAmount(this.botTaskItemId) == 0) {
                    object2 = CookableFoodDefinition.forRawItemId(this.botTaskItemId);
                    if (object2 != null) {
                        player2 = this;
                        if (player2.eY.getContainer().containsItem(((CookableFoodDefinition)((Object)object2)).getBurntItemId())) {
                            player2 = this;
                            ItemStack[] itemStackArray = player2.eY.getContainer().getItems();
                            int n3 = itemStackArray.length;
                            int n4 = 0;
                            while (n4 < n3) {
                                ItemStack itemStack = itemStackArray[n4];
                                if (itemStack != null && itemStack.getId() == ((CookableFoodDefinition)((Object)object2)).getBurntItemId()) {
                                    BotCombatHelper.dropInventoryItem(this, itemStack);
                                }
                                ++n4;
                            }
                        }
                    }
                    this.currentBotTask.startWalkToBank(this);
                    return;
                }
            }
            if (!(this.currentBotTaskTypeId != 3 && this.currentBotTaskTypeId != 6 && this.currentBotTaskTypeId != 11 || this.botUseTaskItemOnTarget)) {
                this.botUseTaskItemOnTarget = true;
            }
        }
        if (this.botTaskState.equals("empty inventory") && this.currentBotTaskTypeId != 14 && (n = GameUtil.b(this.getPosition(), this.currentBotTask.getStartPosition())) >= 60 && !this.A(bl)) {
            return;
        }
        if (!(this.botTaskState.equals("walk to task") || this.botTaskState.equals("walk to bank") || this.botTaskState.equals("walk towards task"))) {
        }
        if (this.botTaskState.equals("walk to bank")) {
            this.currentBotTask.continueWalkToBank(this, this.botPathWaypointIndex);
            return;
        }
        if (this.botTaskState.equals("worldwalk to bank")) {
            GameplayHelper.f(this);
            return;
        }
        if (this.botTaskState.equals("walk towards bank")) {
            this.currentBotTask.continueWalkToBank(this, this.botPathWaypointIndex);
            return;
        }
        if (this.botTaskState.equals("walk to task")) {
            this.currentBotTask.continueWalkToTask(this, this.botPathWaypointIndex);
            return;
        }
        if (this.botTaskState.equals("walk towards task")) {
            this.currentBotTask.continueWalkToTask(this, this.botPathWaypointIndex);
            return;
        }
        if (this.botTaskState.equals("world walk find")) {
            if (object2 == null) {
                Player player3 = this;
                System.out.println(String.valueOf(player3.username) + " is trying to continue path while not having one at: " + this.getPosition());
                System.out.println("Reseting to lumbridge and picking new task");
                this.a();
                return;
            }
            BotWorldRouteWalker.continueWorldRoute(this, object2);
            return;
        }
        if (this.botTaskState.equals("world walk towards")) {
            if (object2 == null) {
                Player player4 = this;
                System.out.println(String.valueOf(player4.username) + " is trying to continue path while not having one at: " + this.getPosition());
                System.out.println("Reseting to lumbridge and picking new task");
                this.a();
                return;
            }
            BotWorldRouteWalker.continueWorldRoute(this, object2);
            return;
        }
        if (this.botTaskState.equals("world walk finish")) {
            if (object2 == null) {
                Player player5 = this;
                System.out.println(String.valueOf(player5.username) + " is trying to continue path while not having one at: " + this.getPosition());
                System.out.println("Reseting to lumbridge and picking new task");
                this.a();
                return;
            }
            BotWorldRouteWalker.continueWorldRoute(this, object2);
            return;
        }
        if (this.botTaskState.equals("do task")) {
            if (this.currentBotTaskTypeId == 14) {
                this.currentBotTask.startWalkToTask(this);
                return;
            }
            this.botInteractionOption = this.currentBotTask.getInteractionOption(this);
            if (!this.dropPartyLeader && !this.currentBotTask.usesCustomTaskAction) {
                int n5;
                if (this.botInteractionTargetIds.size() == 1 && (n5 = ((Integer)this.botInteractionTargetIds.get(0)).intValue()) == -1 && !this.A(bl)) {
                    return;
                }
                if (this.currentBotTask.interactionTargetType == 0) {
                    this.interactWithBotObjectTargets(this.botInteractionTargetIds);
                } else {
                    this.interactWithBotNpcTargets(this.botInteractionTargetIds);
                }
            }
            if (this.currentBotTask.usesCustomTaskAction) {
                this.currentBotTask.startCustomTaskAction(this);
            }
            return;
        }
        if (this.botTaskState.equals("empty inventory")) {
            if (this.currentBotTask.usesDepositBox) {
                this.botInteractionOption = 2;
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(2619);
                this.interactWithBotNpcTargets(arrayList);
                return;
            }
            this.botInteractionOption = 2;
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            arrayList.add(2213);
            arrayList.add(11758);
            this.interactWithBotObjectTargets(arrayList);
            return;
        }
    }

    public final void queuePublicChatMessage(String object, int n, int n2) {
        if (object == null) {
            return;
        }
        if (((String)object).equals("")) {
            return;
        }
        byte[] byArray = new byte[100];
        int n3 = ChatTextCodec.encode((String)object, byArray);
        byte[] byArray2 = new byte[n3];
        ChatTextCodec.encode((String)object, byArray2);
        object = this;
        this.publicChatEffects = n2;
        n2 = n;
        object = this;
        this.publicChatColor = n2;
        byte[] byArray3 = byArray2;
        object = this;
        this.publicChatPayload = byArray3;
        this.ay = true;
        this.flagAppearanceUpdate(true);
        this.getUpdateState().setUpdateRequired(true);
    }

    public final void queuePublicChatMessage(String string) {
        this.queuePublicChatMessage(string, 0, 0);
    }

    public final void aN() {
        this.getUpdateState().setAnimation(-1);
    }

    public final int getQuestState(int n) {
        return this.getQuestState(n, false);
    }

    public final int getQuestState(int n, boolean bl) {
        if (!ServerSettings.skipRequirementsForMissingQuests || bl) {
            return this.questStates[n];
        }
        QuestScript questScript = QuestDefinition.a(n);
        if (questScript.b() == -1) {
            return 1;
        }
        return this.questStates[n];
    }

    public final void setQuestState(int n, int n2) {
        this.questStates[n] = n2;
    }

    public final void addQuestState(int n, int n2) {
        int n3 = n;
        this.questStates[n3] = this.questStates[n3] + n2;
    }

    public static ActionRewardDefinition rollActionReward() {
        return null;
    }

    public final boolean isMember() {
        Player player;
        block10: {
            block9: {
                if (this.de) {
                    return true;
                }
                if (ServerSettings.membershipRequirementMode == 0) {
                    return false;
                }
                if (ServerSettings.membershipRequirementMode == 1) {
                    return true;
                }
                player = this;
                if (player.playerRights >= 2) break block9;
                player = this;
                if (!player.fU || ServerSettings.membershipDaysPerPurchase > 0) break block10;
            }
            return true;
        }
        if (ServerSettings.membershipRequirementMode == 2) {
            player = this;
            if (player.fe.getTotalLevel() >= ServerSettings.membershipRequirementValue) {
                return true;
            }
        }
        if (ServerSettings.membershipRequirementMode == 3 && this.dA() >= ServerSettings.membershipRequirementValue) {
            return true;
        }
        return ServerSettings.membershipDaysPerPurchase > 0 && this.aQ() > 0;
    }

    public final int aQ() {
        block7: {
            block5: {
                block6: {
                    if (ServerSettings.membershipRequirementMode == 1) break block5;
                    Player player = this;
                    if (player.playerRights >= 2) break block5;
                    player = this;
                    if (player.fU && ServerSettings.membershipDaysPerPurchase <= 0) break block5;
                    if (ServerSettings.membershipRequirementMode != 2) break block6;
                    player = this;
                    if (player.fe.getTotalLevel() >= ServerSettings.membershipRequirementValue) break block5;
                }
                if (ServerSettings.membershipRequirementMode != 3 || this.dA() < ServerSettings.membershipRequirementValue) break block7;
            }
            return 365;
        }
        if (this.cc == 0L) {
            return 0;
        }
        int n = GameplayHelper.a(System.currentTimeMillis(), this.cc);
        if (n < 0) {
            n = 0;
            this.ik = true;
        }
        return n;
    }

    public final long getBotTaskRuntimeMillis() {
        return System.currentTimeMillis() - this.botTaskStartTimeMillis;
    }

    public final void a(String string, int n) {
        this.dt = string;
        this.ds = n;
    }

    public final void aS() {
        this.dt = "";
        this.ds = -1;
    }

    public final ArrayList aT() {
        return this.in;
    }

    public final void aU() {
        this.in.clear();
    }

    public final void a(Npc npc) {
        this.in.add(npc);
    }

    public final void b(Npc npc) {
        if (this.in.contains(npc)) {
            this.in.remove(npc);
        }
        this.packetSender.sendGameMessage("Enemies left: " + this.in.size());
    }

    public final int aV() {
        return this.N;
    }

    public final void r(int n) {
        this.N = n;
    }

    public final int getPrivateChatMode() {
        return this.privateChatMode;
    }

    public final void setPrivateChatMode(int n) {
        this.privateChatMode = n;
    }

    public final int getPublicChatMode() {
        return this.publicChatMode;
    }

    public final void setPublicChatMode(int n) {
        this.publicChatMode = n;
    }

    public final int getTradeMode() {
        return this.tradeMode;
    }

    public final void setTradeMode(int n) {
        this.tradeMode = n;
    }

    public final int aZ() {
        int n = 0;
        int n2 = 0;
        while (n2 < 22) {
            Player player = this;
            int n3 = player.fe.getBaseLevel(n2);
            if (n3 == 99) {
                ++n;
            }
            ++n2;
        }
        return n;
    }

    private void b() {
        Player player = this;
        player.packetSender.sendInterfaceText("", 8144);
        player = this;
        player.packetSender.sendInterfaceText("", 8145);
        int n = 8147;
        while (n <= 8195) {
            player = this;
            player.packetSender.sendInterfaceText("", n);
            ++n;
        }
        n = 12174;
        while (n <= 12223) {
            player = this;
            player.packetSender.sendInterfaceText("", n);
            ++n;
        }
    }

    private void a(String[] stringArray) {
        Player player = this;
        player.packetSender.sendInterfaceText(stringArray[0], 8145);
        int n = 1;
        while (n < 23) {
            player = this;
            player.packetSender.sendInterfaceText(stringArray[n], n + 8146);
            ++n;
        }
    }

    private long c() {
        return System.currentTimeMillis() - this.ei;
    }

    public final long ba() {
        return this.ej + this.c();
    }

    public final boolean bb() {
        return this.aq(2412) || this.aq(2413) || this.aq(2414);
    }

    public final void a(Player object, int n) {
        boolean bl = true;
        Player player = object;
        ((Player)object).gM = bl;
        object = new DropGodCapeTask(this, 4, (Player)object, n);
        World.getTaskScheduler().schedule((TickTask)object);
    }

    /*
     * Unable to fully structure code
     */
    public final void bc() {
        block28: {
            block29: {
                this.pruneExpiredDamageContributions();
                var1_1 = this;
                var2_4 = var1_1.hw.size();
                if (var2_4 != 0) {
                    var3_7 = var1_1.hw.iterator();
                    while (var3_7.hasNext()) {
                        if (!((PvpCombatReference)var3_7.next()).b()) continue;
                        var3_7.remove();
                    }
                    var2_4 = var1_1.hw.size();
                    if (var2_4 == 0) {
                        var1_1.B(false);
                    }
                }
                this.fb.drainPrayerPoints();
                this.fe.startRestorationTasks();
                var2_5 = this.hm.iterator();
                while (var2_5.hasNext()) {
                    var2_5.next();
                }
                var1_1 = this;
                if (var1_1.botEnabled) break block28;
                var6_9 = var1_1;
                if (var6_9.fW > 20 || !var1_1.getSingleCombatTimer().hasElapsed() && !var1_1.isInMultiCombatArea()) break block28;
                var6_9 = var1_1;
                if (var6_9.H == null || var1_1.dN >= 5) break block29;
                var6_9 = var1_1;
                if (var6_9.H.getNpcId() < 907) break block29;
                var6_9 = var1_1;
                if (var6_9.H.getNpcId() <= 911) break block28;
            }
            var6_9 = var1_1;
            if (var6_9.eZ.getItemIdAtSlot(3) == 4024 && var1_1.p()) break block28;
            var6_9 = var1_1;
            for (Object var2_5 : var6_9.eX) {
                block31: {
                    block35: {
                        block34: {
                            block33: {
                                block32: {
                                    block30: {
                                        if (var2_5.getOwnerPlayer() != null) continue;
                                        if (var2_5.getDefinition().getAggressionType() != 3 && var1_1.co != 0L && !var2_5.isInWilderness() && System.currentTimeMillis() - var1_1.co > 900000L) break;
                                        var5_13 = var2_5;
                                        var4_10 = var1_1;
                                        if (!var5_13.hasCombatTarget() && var5_13.getDefinition().isAttackable()) break block30;
                                        v0 = false;
                                        break block31;
                                    }
                                    if (var5_13.getNpcId() != 2429 && var5_13.getNpcId() != 1827 && var5_13.getNpcId() != 1266 && var5_13.getNpcId() != 1268 && var5_13.getNpcId() != 2453 && var5_13.getNpcId() != 2890) break block32;
                                    v0 = true;
                                    break block31;
                                }
                                if (var5_13.getNpcId() != 18) break block33;
                                if (var4_10.getCombatTarget() != null) ** GOTO lbl-1000
                                v0 = false;
                                break block31;
                            }
                            if (!var5_13.isInWilderness() && var5_13.getDefinition().getAggressionType() < 2) break block34;
                            v0 = true;
                            break block31;
                        }
                        if (var5_13.getDefinition().getAggressionType() != 0) break block35;
                        v0 = false;
                        break block31;
                    }
                    if (var5_13.getDefinition().getAggressionType() != 1) ** GOTO lbl-1000
                    var6_9 = var4_10;
                    if (var6_9.combatLevel > var5_13.getDefinition().getCombatLevel() << 1) {
                        v0 = false;
                    } else lbl-1000:
                    // 3 sources

                    {
                        v0 = true;
                    }
                }
                if (!v0 || !GameUtil.a(var1_1.getPosition(), var2_5.getPosition(), false)) continue;
                var4_11 = var2_5.getDefinition().getAggressionRange();
                if (ServerSettings.content2007Enabled && (var2_5.getNpcId() != 6203 && var2_5.getNpcId() != 6204 && var2_5.getNpcId() != 6206 && var2_5.getNpcId() != 6208 && GodWarsDungeonManager.e.contains(var2_5.getNpcId()) && (var1_1.m("zamorak") || var1_1.m("unholy")) || var2_5.getNpcId() != 6247 && var2_5.getNpcId() != 6248 && var2_5.getNpcId() != 6250 && var2_5.getNpcId() != 6252 && GodWarsDungeonManager.d.contains(var2_5.getNpcId()) && (var1_1.m("saradomin") || var1_1.m("holy")) || var2_5.getNpcId() != 6222 && var2_5.getNpcId() != 6223 && var2_5.getNpcId() != 6225 && var2_5.getNpcId() != 6227 && GodWarsDungeonManager.b.contains(var2_5.getNpcId()) && var1_1.m("armadyl") || var2_5.getNpcId() != 6260 && var2_5.getNpcId() != 6261 && var2_5.getNpcId() != 6263 && var2_5.getNpcId() != 6265 && GodWarsDungeonManager.c.contains(var2_5.getNpcId()) && var1_1.m("bandos"))) continue;
                if (var2_5.getNpcId() == 912) {
                    var6_9 = var1_1;
                    if (var6_9.eZ.getItemIdAtSlot(1) == 2414) {
                        if (!GameUtil.a(var2_5.getSpawnPosition(), var1_1.getPosition(), var4_11) || GameUtil.h(4) != 0) continue;
                        var2_5.getUpdateState().setForcedText("Hail Zamorak!");
                        continue;
                    }
                }
                if (var2_5.getNpcId() == 913) {
                    var6_9 = var1_1;
                    if (var6_9.eZ.getItemIdAtSlot(1) == 2412) {
                        if (!GameUtil.a(var2_5.getSpawnPosition(), var1_1.getPosition(), var4_11) || GameUtil.h(4) != 0) continue;
                        var2_5.getUpdateState().setForcedText("Hail Saradomin!");
                        continue;
                    }
                }
                if (var2_5.getNpcId() == 914) {
                    var6_9 = var1_1;
                    if (var6_9.eZ.getItemIdAtSlot(1) == 2413) {
                        if (!GameUtil.a(var2_5.getSpawnPosition(), var1_1.getPosition(), var4_11) || GameUtil.h(4) != 0) continue;
                        var2_5.getUpdateState().setForcedText("Hail Guthix!");
                        continue;
                    }
                }
                if (var2_5.getNpcId() == 1266 || var2_5.getNpcId() == 1268 || var2_5.getNpcId() == 2453 || var2_5.getNpcId() == 2890) {
                    var4_11 = 1;
                }
                if (var2_5.getNpcId() == 2894 || var2_5.getNpcId() == 2896) {
                    var4_11 = 10;
                }
                if (!GameUtil.a(var2_5.getSpawnPosition(), var1_1.getPosition(), var4_11) || (var4_12 = CombatCycleEvent.validateAttack((Entity)var2_5, var1_1)) != AttackValidationResult.VALID) continue;
                if (var2_5.getNpcId() == 180) {
                    var2_5.getUpdateState().setForcedText("Stand and deliver!");
                }
                if (var2_5.getNpcId() == 18) {
                    var2_5.getUpdateState().setForcedText("Brother, I will help thee with this infidel!");
                }
                if (var2_5.getTransformTicksRemaining() <= 0 && var2_5.getCombatTransformNpcId() > 0) {
                    var2_5.transformToNpcId(var2_5.getCombatTransformNpcId(), 999999);
                }
                CombatManager.startCombat((Entity)var2_5, var1_1);
                break;
            }
        }
        this.getTargetMovement().process();
        var1_2 = false;
        if (this.getMovementTarget() != null) {
            if (this.getMovementTarget().isPlayer()) {
                var2_5 = (Player)this.getMovementTarget();
                if (!var2_5.isRunningMovement()) {
                    var1_2 = true;
                }
            } else if (!this.isRunningMovement()) {
                var1_2 = true;
            }
        } else if (!this.isRunningMovement()) {
            var1_2 = true;
        }
        if (this.getRunEnergyPercent() < 100 && var1_2) {
            var6_9 = var1_3 = this;
            var2_6 = var1_3.fe.getCurrentLevels()[16];
            if (ServerSettings.freeToPlayWorld) {
                var2_6 = 1;
            }
            var3_8 = var2_6 / 6 + 8;
            this.addRunEnergyRaw(var3_8);
            this.packetSender.sendRunEnergy();
        }
        if (this.cl != 0L && System.currentTimeMillis() - this.cl >= 60000L && (this.getSingleCombatTimer().hasElapsed() || this.cn)) {
            var6_9 = this;
            var6_9.packetSender.sendLogout();
            this.disconnect();
        }
    }

    public Player(SelectionKey object) {
        new EquipmentKeywordBootstrap(this);
        new DairyChurnHandler(this);
        this.fN = new PetManager(this);
        this.fO = new SandwichLadyManager(this);
        this.fP = new DialogueManager(this);
        this.fQ = new BankPinManager(this);
        this.fR = new LoginProtocol();
        this.fS = new Position(0, 0, 0);
        this.playerRights = 0;
        this.D = false;
        this.fY = new int[4];
        this.gender = 0;
        this.appearanceParts = new int[7];
        this.appearanceColors = new int[5];
        this.bankContainer = new ItemContainer(ItemContainerType.b, 288, 1);
        this.gd = new ItemContainer(ItemContainerType.a, 28);
        this.ge = new ItemContainer(ItemContainerType.a, 8);
        this.interactionSpellButtonId = -1;
        this.interactionTargetX = -1;
        this.interactionTargetY = -1;
        this.interactionTargetPlane = -1;
        this.interactionTargetId = -1;
        this.selectedItemId = -1;
        this.selectedItemInterfaceId = -1;
        this.selectedItemSlot = -1;
        this.bankRearrangeMode = BankRearrangeMode.a;
        this.gv = new HashMap();
        this.friendsList = new long[200];
        this.ignoreList = new long[100];
        this.gy = 2;
        this.gz = TradeState.a;
        this.gA = new int[28];
        this.gB = new int[28];
        this.runEnergyRaw = 10000;
        this.gG = -1;
        this.gH = -1;
        this.gI = 0;
        this.gJ = new boolean[18];
        this.spellbook = Spellbook.MODERN;
        this.E = Spellbook.MODERN;
        this.autoRetaliate = false;
        this.brightness = 2;
        this.mouseButtons = 0;
        this.publicChatEffects = 1;
        this.splitPrivateChat = 0;
        this.privateChatMode = 0;
        this.publicChatMode = 0;
        this.tradeMode = 0;
        this.acceptAid = 0;
        this.musicVolume = 0;
        this.effectVolume = 0;
        this.gX = 0;
        this.specialAttackEnabled = false;
        this.specialEnergy = 100;
        this.ringOfRecoilLife = 40;
        this.ringOfForgingLife = 140;
        this.bindingNecklaceCharge = 15;
        this.hm = new ArrayList();
        this.hs = 553;
        new ArrayList();
        this.interfaceAction = "";
        this.hu = WeaponProfile.FISTS;
        this.hz = false;
        this.hC = new LinkedList();
        this.hF = new boolean[6];
        int[] nArray = new int[14];
        nArray[0] = 2423;
        nArray[1] = 3917;
        nArray[2] = 638;
        nArray[3] = 3213;
        nArray[4] = 1644;
        nArray[5] = 5608;
        nArray[7] = -1;
        nArray[8] = 5065;
        nArray[9] = 5715;
        nArray[10] = 2449;
        nArray[11] = 904;
        nArray[12] = 147;
        nArray[13] = 962;
        this.sidebarInterfaceIds = nArray;
        this.hQ = true;
        this.ai = false;
        this.aj = false;
        this.ak = -1;
        this.av = new ItemStack[25];
        this.aw = false;
        this.hS = -1;
        this.hT = -1;
        this.hU = -1;
        this.hX = new int[4];
        this.ay = false;
        this.az = -1;
        this.dropPartyLeader = false;
        this.dropPartyPretaskComplete = false;
        this.dropPartyFollower = false;
        this.dropPartySentToAssignedDrop = false;
        this.dropPartyPretaskLoopCount = 0;
        this.tradeAdvertAcceptedQuantity = -1;
        this.tradeAdvertOfferPoolIndex = -1;
        this.tradeAdvertQuantityOptionIndex = -1;
        this.tradeAdvertInitialOfferPlaced = false;
        this.tradeAdvertScam = false;
        this.tradeAdvertVariableQuantity = false;
        this.tradeAdvertLastOfferAmount = -1;
        this.tradeAdvertMode = -1;
        this.botAdvertItemId = -1;
        this.tradeAdvertQuantityRemaining = -1;
        this.tradeAdvertUnitPrice = -1;
        this.botPublicChatMessage = "";
        this.botPublicChatColor = -1;
        this.botPublicChatEffect = -1;
        this.pendingTradeTarget = null;
        this.ic = 0;
        this.id = 0;
        this.ie = 0;
        this.cfr_renamed_15 = 0L;
        this.savedWorldRouteIndex = -1;
        this.currentWorldRouteChoice = null;
        this.botTaskItemId = -1;
        this.botSmithingProductItemId = -1;
        this.botShopItemAmount = -1;
        this.botUseTaskItemOnTarget = false;
        this.botShopBuyMode = -1;
        this.botSkillTargetSkillId = -1;
        this.botSkillTargetLevel = -1;
        this.be = -1;
        this.bf = -1;
        this.bg = -1;
        this.bh = -1;
        this.botCompletionItemId = -1;
        this.botCompletionItemAmount = -1;
        this.bk = -1;
        this.bl = -1;
        this.bm = -1;
        this.bn = -1;
        this.savedWorldRouteReversed = false;
        this.botElementalSpellIndex = -1;
        this.currentBotTaskTypeId = -1;
        this.currentBotTaskIndex = -1;
        this.deferredBotTaskTypeId = -1;
        this.deferredBotTaskIndex = -1;
        this.botCombatLoadoutItemIds = new ArrayList();
        this.botMeleeLoadoutItemIds = new ArrayList();
        this.botRangedLoadoutItemIds = new ArrayList();
        this.botMagicLoadoutItemIds = new ArrayList();
        this.botShopSellItemIds = new ArrayList();
        this.botCombatLoadoutSlotCursor = -1;
        this.bB = false;
        this.ig = 0;
        this.botPvpTeamInviteTicks = 0;
        this.botPvpPendingTeamTarget = null;
        this.botPvpRejectedTeamTargets = new ArrayList();
        this.botPvpTeamRequesters = new ArrayList();
        this.botPvpChatSource = null;
        this.botPvpChatMessage = null;
        this.questStates = new int[QuestDefinition.b];
        this.bI = new int[QuestDefinition.b];
        this.bJ = new int[100];
        this.visibleDynamicObjects = new ArrayList();
        this.pendingDynamicObjectRemovals = new ArrayList();
        this.bN = 0;
        this.bO = 0;
        this.bP = -1;
        this.ii = "";
        this.ij = "";
        this.bQ = false;
        this.bR = -1;
        this.bS = 0;
        this.bT = new int[3];
        this.bU = false;
        this.bV = "";
        this.flourMillHopperGrainCount = 0;
        this.bX = false;
        this.bY = 0L;
        this.bZ = 0;
        this.ca = 0;
        this.cc = 0L;
        this.ik = false;
        this.cd = false;
        this.ce = new String[5];
        this.cf = false;
        this.cg = -1;
        this.ch = -1;
        this.ci = -1;
        this.gatheringHazardCounter = 0;
        this.cl = 0L;
        this.cm = 778;
        this.cn = false;
        this.co = 0L;
        this.botLootResumeTarget = null;
        this.botLootGroundItems = new ArrayList();
        this.botLootPickupTargets = new ArrayList();
        this.botLootSellGroundItems = new ArrayList();
        this.botLootSellItems = new ArrayList();
        this.clanWarsBot = false;
        this.clanWarsTeamId = -1;
        this.botTaskSavedElapsedMillis = 0L;
        this.botEscapeStuckTicks = 0;
        this.botCombatEscapeActive = false;
        this.botMagicPenaltyGearUnequipped = false;
        this.botAntipoisonAvailable = false;
        this.botActiveCombatStyle = 0;
        this.botPrimaryCombatStyle = 0;
        this.botSpecialCombatStyle = 0;
        this.botOpponentCombatStyle = 0;
        this.botCombatStyle = 0;
        this.botSpecialAttackEnergyCost = 0;
        this.botStrengthPotionDepleted = false;
        this.cN = false;
        this.botFoodDepleted = false;
        this.botWeaponItemId = 0;
        this.botShieldItemId = 0;
        this.botSpecialWeaponItemId = 0;
        this.botCombatSpell = null;
        this.botFoodItemId = 0;
        this.botWildernessMaxY = 0;
        this.botCombatState = null;
        this.botMagicGearSwapDelayTicks = 0;
        this.botThreatEscapeDelayTicks = 0;
        this.botPrayerSwitchDelayTicks = 0;
        this.botQueuedPrayerId = -1;
        this.botEatDelayTicks = 0;
        this.botWeaponSwapDelayTicks = 0;
        this.de = false;
        this.df = false;
        this.il = false;
        this.im = new ArrayList();
        this.botPathSegmentIndex = -1;
        this.botTargetNpcId = -1;
        this.botPathWaypointIndex = -1;
        this.botEscapeRouteName = "";
        this.currentBotRoute = null;
        this.dm = false;
        this.dn = false;
        this.botInteractionTargetIds = new ArrayList();
        this.botEnabled = false;
        this.botInteractionOption = 1;
        this.botTaskState = "";
        this.ds = -1;
        this.dt = "";
        this.currentBankTab = 0;
        this.dv = 0;
        this.dw = new boolean[6];
        this.dx = new int[6];
        this.dy = new int[6];
        this.dz = new int[6];
        this.dA = new boolean[6];
        this.dB = new int[6];
        this.dC = new int[6];
        this.dD = new int[6];
        this.dE = new int[6];
        this.dF = new boolean[6];
        this.dG = 0;
        this.dH = 0;
        this.dI = 0;
        this.dJ = 0;
        this.dK = 100;
        this.dL = 100;
        this.dM = 100;
        this.dN = 0;
        new ArrayList();
        this.dO = 0;
        this.dP = false;
        this.in = new ArrayList();
        this.dQ = false;
        this.dS = 0;
        this.dT = 0;
        this.dU = 0;
        this.dV = 0;
        this.dW = 0;
        this.dX = 0;
        this.dY = 0;
        this.dZ = 0;
        this.ea = 0;
        this.eb = 0;
        this.ec = 0;
        this.ed = 0;
        this.ee = 0;
        this.ef = false;
        this.el = false;
        this.en = false;
        this.eo = true;
        this.ep = new int[2000];
        this.eq = 0;
        this.er = 0;
        this.es = 0;
        this.et = 21;
        this.io = false;
        this.ip = 0;
        this.iq = 0;
        this.ev = false;
        this.is = false;
        this.ew = false;
        this.ex = null;
        this.iv = "Why u messing with my files";
        this.ey = "";
        this.ez = 0;
        this.eA = 0;
        this.eB = 0;
        this.eC = false;
        this.eE = 0;
        this.eF = false;
        this.eG = false;
        this.eH = 0;
        this.eI = 0;
        this.selectionKey = object;
        this.inboundBuffer = ByteBuffer.allocateDirect(512);
        this.outboundBuffer = ByteBuffer.allocateDirect(8192);
        if (object != null) {
            this.socketChannel = (SocketChannel)((SelectionKey)object).channel();
            this.hV = this.socketChannel.socket().getInetAddress().getHostAddress();
        }
        this.a(new Position(ServerSettings.startX, ServerSettings.startY, ServerSettings.startPlane));
        object = this;
        ((Entity)object).getAttributes().put("smithing", Boolean.FALSE);
        ((Entity)object).getAttributes().put("smelting", Boolean.FALSE);
        ((Entity)object).getAttributes().put("isBanking", Boolean.FALSE);
        ((Entity)object).getAttributes().put("isShopping", Boolean.FALSE);
        ((Entity)object).getAttributes().put("canPickup", Boolean.FALSE);
        ((Entity)object).getAttributes().put("canTakeDamage", Boolean.TRUE);
        this.resetAppearance();
        object = this;
        ((Player)object).appearanceColors[0] = 7;
        object = this;
        ((Player)object).appearanceColors[1] = 0;
        object = this;
        ((Player)object).appearanceColors[2] = 9;
        object = this;
        ((Player)object).appearanceColors[3] = 5;
        object = this;
        ((Player)object).appearanceColors[4] = 0;
        int n = 0;
        while (n < this.gA.length) {
            this.gA[n] = -1;
            this.gB[n] = 0;
            ++n;
        }
        this.hw = new LinkedList();
    }

    public final void resetAppearance() {
        int n = 0;
        Object object = this;
        this.gender = n;
        int[] nArray = ServerSettings.APPEARANCE_BODY_PART_RANGES[this.gender][0];
        object = nArray;
        System.arraycopy(nArray, 0, this.appearanceParts, 0, 7);
        int[] nArray2 = ServerSettings.APPEARANCE_COLOR_RANGES[this.gender][0];
        object = nArray2;
        System.arraycopy(nArray2, 0, this.appearanceColors, 0, 5);
    }

    public final void be() {
        int n = 0;
        Object object = this;
        this.gender = n;
        object = this;
        ((Player)object).appearanceParts[0] = 86;
        object = this;
        ((Player)object).appearanceParts[1] = 87;
        object = this;
        ((Player)object).appearanceParts[2] = 89;
        object = this;
        ((Player)object).appearanceParts[3] = 84;
        object = this;
        ((Player)object).appearanceParts[4] = 88;
        object = this;
        ((Player)object).appearanceParts[5] = 90;
        object = this;
        ((Player)object).appearanceParts[6] = 85;
        int[] nArray = ServerSettings.APPEARANCE_COLOR_RANGES[this.gender][0];
        object = nArray;
        System.arraycopy(nArray, 0, this.appearanceColors, 0, 5);
        this.f(true);
        this.getUpdateState().setUpdateRequired(true);
    }

    public final void bf() {
        Object object = PacketBuffer.wrapReader(this.inboundBuffer);
        object = new IncomingPacket(this.currentPacketOpcode, this.currentPacketLength, (PacketReader)object);
        PacketDispatcher.packetTimers[((IncomingPacket)object).getOpcode()].start();
        Iterator iterator = this.hm.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        PacketDispatcher.dispatchPacket(this, (IncomingPacket)object);
        PacketDispatcher.packetTimers[((IncomingPacket)object).getOpcode()].stop();
    }

    public final void bg() {
        int n = 0;
        while (n < QuestDefinition.a) {
            this.questStates[n] = 1;
            ++n;
        }
    }

    /*
     * Handled impossible loop by duplicating code
     * Enabled aggressive block sorting
     */
    public final void bh() {
        block9: {
            int n;
            int[] nArray;
            int[] nArray2;
            Player player;
            block8: {
                block7: {
                    block6: {
                        int n2 = GameUtil.h(2);
                        player = this;
                        this.gender = n2;
                        player = this;
                        nArray2 = ServerSettings.APPEARANCE_COLOR_RANGES[player.gender][0];
                        player = this;
                        nArray = ServerSettings.APPEARANCE_COLOR_RANGES[player.gender][1];
                        n = 0;
                        if (!true) break block6;
                        player = this;
                        if (n >= player.appearanceColors.length) break block7;
                    }
                    do {
                        player = this;
                        player.appearanceColors[n] = nArray2[n] + GameUtil.h(nArray[n] - nArray2[n]);
                        ++n;
                        player = this;
                    } while (n < player.appearanceColors.length);
                }
                player = this;
                nArray2 = ServerSettings.APPEARANCE_BODY_PART_RANGES[player.gender][0];
                player = this;
                nArray = ServerSettings.APPEARANCE_BODY_PART_RANGES[player.gender][1];
                n = 0;
                if (!true) break block8;
                player = this;
                if (n >= player.appearanceParts.length) break block9;
            }
            do {
                player = this;
                if (player.gender == 1 && n == 6) {
                    player = this;
                    player.appearanceParts[n] = -1;
                } else {
                    player = this;
                    player.appearanceParts[n] = nArray2[n] + GameUtil.h(nArray[n] - nArray2[n]);
                }
                ++n;
                player = this;
            } while (n < player.appearanceParts.length);
        }
        this.f(true);
        this.getUpdateState().setUpdateRequired(true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void writePacketBuffer(ByteBuffer byteBuffer) {
        block9: {
            if (!this.socketChannel.isOpen()) {
                return;
            }
            byteBuffer.flip();
            try {
                this.socketChannel.write(byteBuffer);
                if (!byteBuffer.hasRemaining()) break block9;
                Player player = this;
                Object object = player.outboundBuffer;
                synchronized (object) {
                    player = this;
                    player.outboundBuffer.put(byteBuffer);
                }
                object = DedicatedReactor.b();
                synchronized (object) {
                    DedicatedReactor.b().a().wakeup();
                    this.selectionKey.interestOps(this.selectionKey.interestOps() | 4);
                    return;
                }
            }
            catch (Exception exception) {
                this.disconnect();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void disconnect() {
        block25: {
            Object object;
            Object object2;
            if (this.el) {
                long l = System.currentTimeMillis() + 15000L;
                object2 = this;
                this.hY = l;
                object = PlayerConnectionState.e;
                object2 = this;
                this.connectionState = object;
            }
            if (!this.de) {
                this.selectionKey.attach(null);
                this.selectionKey.cancel();
            }
            if (this.q != null) {
                this.q.e(this);
            }
            try {
                try {
                    if (this != null && this.el) {
                        object2 = this;
                        if (((Player)object2).username != null) {
                            object2 = this;
                            if (((Player)object2).password != null) {
                                object2 = this;
                                ((Player)object2).fk.b();
                                object2 = this;
                                if (((Player)object2).gy == 2) {
                                    CharacterFileManager.savePlayer(this);
                                }
                            }
                        }
                    }
                    if (!this.de) {
                        this.socketChannel.close();
                        ConnectionThrottle.b(this.hV);
                    }
                }
                catch (Exception exception) {
                    object2 = exception;
                    exception.printStackTrace();
                    if (this.getIndex() != -1) {
                        object = Server.b();
                        synchronized (object) {
                            Server.b().offer(this);
                            return;
                        }
                    }
                    break block25;
                }
            }
            catch (Throwable throwable) {
                if (this.getIndex() != -1) {
                    object = Server.b();
                    synchronized (object) {
                        Server.b().offer(this);
                    }
                }
                throw throwable;
            }
            if (this.getIndex() != -1) {
                object = Server.b();
                synchronized (object) {
                    Server.b().offer(this);
                    return;
                }
            }
        }
    }

    public final void v(int n) {
        Object object;
        Object object2 = this;
        int n2 = 0;
        while (n2 < 16) {
            object = object2;
            ((Player)object).packetSender.sendInterfaceText("", 18819 + 4 * n2);
            object = object2;
            ((Player)object).packetSender.sendInterfaceText("", 18820 + 4 * n2);
            object = object2;
            ((Player)object).packetSender.sendInterfaceText("", 18821 + 4 * n2);
            object = object2;
            ((Player)object).packetSender.sendInterfaceText("", 18822 + 4 * n2);
            ++n2;
        }
        ArrayList<CharacterFileRecord> arrayList = new ArrayList<CharacterFileRecord>();
        for (CharacterFileRecord characterFileRecord : CharacterFileManager.liveHiscoreRecords) {
            object = characterFileRecord;
            if (characterFileRecord.playerRights >= 2 || characterFileRecord.cC != n) continue;
            arrayList.add(characterFileRecord);
        }
        if (n == 3) {
            for (CharacterFileRecord characterFileRecord : CharacterFileManager.deadHardcoreIronmanRecords) {
                arrayList.add(characterFileRecord);
            }
        }
        int n3 = this.et;
        int n4 = this.es;
        String string = n3 != 22 ? String.valueOf(n3 < 21 ? SkillManager.a[n3] : "Overall") + " Hiscores" : "Wealth Hiscores";
        Object object3 = "";
        if (n == 1) {
            object3 = "<img=3>";
        } else if (n == 2) {
            object3 = "<img=4>";
        }
        if (n == 3) {
            object3 = String.valueOf(object3) + "<img=5>";
        }
        object = this;
        ((Player)object).packetSender.sendInterfaceText(String.valueOf(object3) + string + (String)object3, 18814);
        Collections.sort(arrayList, new HiscoreEntryComparator(this, n3));
        int n5 = n4 << 4;
        while (n5 < arrayList.size()) {
            object3 = (CharacterFileRecord)arrayList.get(n5);
            int n6 = n5 - (n4 << 4);
            if (n5 == n4 + 1 << 4) break;
            if (!(n3 == 22 ? ((CharacterFileRecord)object3).getStoredItemValue() < 100000 : n3 < 21 && CharacterFileRecord.getLevelForExperience(((CharacterFileRecord)object3).getSkillExperience(n3)) < 30)) {
                Object object4;
                String string2 = "@bla@";
                object = object3;
                object = this;
                if (((CharacterFileRecord)object4).username.equals(((Player)object).username)) {
                    string2 = "@whi@";
                }
                object = object3;
                object = object3;
                int cfr_ignored_0 = ((CharacterFileRecord)object3).cC;
                boolean bl = ((CharacterFileRecord)object).memberFlag;
                int n7 = ((CharacterFileRecord)object).playerRights;
                object = "";
                if (n7 == 1) {
                    object = String.valueOf(object) + "<img=0>";
                }
                if (n7 == 2) {
                    object = String.valueOf(object) + "<img=1>";
                }
                if (bl && n7 < 2) {
                    object = String.valueOf(object) + "<img=2>";
                }
                object2 = object;
                if (n == 3 && ((CharacterFileRecord)object3).cC != 3) {
                    object2 = "<img=6>" + (String)object2;
                }
                object = this;
                ((Player)object).packetSender.sendInterfaceText(String.valueOf(string2) + (n5 + 1), 18819 + 4 * n6);
                Player player = this;
                object = player;
                object = object3;
                player.packetSender.sendInterfaceText(String.valueOf(string2) + (String)object2 + ((CharacterFileRecord)object).username, 18820 + 4 * n6);
                object2 = "";
                String string3 = "";
                if (n3 < 21) {
                    object2 = GameUtil.a((long)CharacterFileRecord.getLevelForExperience(((CharacterFileRecord)object3).getSkillExperience(n3)));
                    string3 = GameUtil.a(((CharacterFileRecord)object3).getSkillExperience(n3));
                }
                if (n3 == 21) {
                    object2 = GameUtil.a((long)((CharacterFileRecord)object3).getTotalLevel());
                    string3 = GameUtil.a(((CharacterFileRecord)object3).getSkillExperience(n3));
                }
                if (n3 == 22) {
                    object2 = GameUtil.c(((CharacterFileRecord)object3).getStoredItemValue());
                }
                object = this;
                ((Player)object).packetSender.sendInterfaceText(String.valueOf(string2) + (String)object2, 18821 + 4 * n6);
                object = this;
                ((Player)object).packetSender.sendInterfaceText(String.valueOf(string2) + string3, 18822 + 4 * n6);
            }
            ++n5;
        }
        object = this;
        ((Player)object).packetSender.showInterface(18788);
    }

    public final void bj() {
        Player player;
        int n = 18;
        if (!ServerSettings.freeToPlayWorld) {
            n = 104;
        }
        int n2 = 1;
        while (n2 <= n) {
            QuestDefinition questDefinition = QuestDefinition.b(n2);
            player = this;
            player.packetSender.sendInterfaceTextColor(questDefinition.d(), Color.GREEN);
            this.questStates[n2] = 1;
            this.dv = GameUtil.h(2) + 1;
            ++n2;
        }
        player = this;
        player.ff.d();
    }

    private void d() {
        Player player;
        int n = 18;
        if (!ServerSettings.freeToPlayWorld) {
            n = 104;
        }
        int n2 = 1;
        while (n2 <= n) {
            QuestDefinition questDefinition = QuestDefinition.b(n2);
            player = this;
            player.packetSender.sendInterfaceTextColor(questDefinition.d(), Color.RED);
            this.questStates[n2] = 0;
            this.bI[n2] = 0;
            this.dv = 0;
            ++n2;
        }
        player = this;
        player.ff.d();
    }

    public final void a(String string, String[] stringArray) {
        this.a(string, stringArray, true);
    }

    public final void a(String string, String[] object, boolean bl) {
        if (string.equals("runes")) {
            int n = 554;
            while (n <= 564) {
                this.eY.addItem(new ItemStack(n, 10000));
                ++n;
            }
            if (!ServerSettings.freeToPlayWorld) {
                this.eY.addItem(new ItemStack(565, 10000));
                this.eY.addItem(new ItemStack(566, 10000));
            }
        } else if (string.equals("nfood")) {
            this.eY.addItem(new ItemStack(334, 1000));
            this.eY.addItem(new ItemStack(330, 1000));
            this.eY.addItem(new ItemStack(362, 1000));
            this.eY.addItem(new ItemStack(380, 1000));
            this.eY.addItem(new ItemStack(374, 1000));
            if (!ServerSettings.freeToPlayWorld) {
                this.eY.addItem(new ItemStack(386, 1000));
                this.eY.addItem(new ItemStack(392, 1000));
            }
        } else if (string.equals("food")) {
            if (ServerSettings.freeToPlayWorld) {
                this.eY.addItem(new ItemStack(379, 20));
            } else {
                this.eY.addItem(new ItemStack(385, 20));
            }
        } else if (string.equals("money")) {
            this.eY.addItem(new ItemStack(995, 1000000));
        } else if (string.equals("arrows")) {
            this.eY.addItem(new ItemStack(884, 10000));
            this.eY.addItem(new ItemStack(886, 10000));
            this.eY.addItem(new ItemStack(888, 10000));
            this.eY.addItem(new ItemStack(890, 10000));
            if (!ServerSettings.freeToPlayWorld) {
                this.eY.addItem(new ItemStack(892, 10000));
            }
        } else if (string.equals("empty")) {
            Player player = this;
            player.eY.getContainer().clear();
            player = this;
            player.eY.refresh();
        } else if (string.equals("bank")) {
            BankManager.openBank(this);
        } else if (string.equals("god")) {
            this.dP = !this.dP;
            Player player = this;
            player.packetSender.sendGameMessage("God mode: " + (this.dP ? "enabled" : "disabled"));
        } else if (string.equals("master")) {
            int n = 0;
            while (n < this.fe.getCurrentLevels().length - 1) {
                this.fe.getCurrentLevels()[n] = ServerSettings.maxLevel;
                Player player = this;
                SkillManager cfr_ignored_0 = player.fe;
                this.fe.getExperience()[n] = SkillManager.getExperienceForLevel(ServerSettings.maxLevel - 1);
                ++n;
            }
            this.fe.refreshAllSkills();
        } else if (string.equals("setlevel")) {
            int n = Integer.parseInt(object[0]);
            int n2 = Integer.parseInt(object[1]);
            n2 = n2 > ServerSettings.maxLevel ? ServerSettings.maxLevel : n2;
            Player player = this;
            player.fe.getCurrentLevels()[n] = n2;
            Player player2 = this;
            player = player2;
            player = this;
            SkillManager cfr_ignored_1 = player.fe;
            player2.fe.getExperience()[n] = SkillManager.getExperienceForLevel(n2 - 1);
            player = this;
            player.fe.refreshSkill(n);
        } else if (string.equals("run")) {
            this.setRunEnergyPercent(100);
        } else if (string.equals("irun")) {
            this.t = !this.t;
            Player player = this;
            player.packetSender.sendGameMessage("Infinite run set to: " + (this.t ? "enabled" : "disabled"));
        } else if (string.equals("music")) {
            MusicManager.unlockAllTracks(this);
        } else if (string.equals("pray")) {
            Player player = this;
            Player player3 = player;
            player3 = this;
            player.fe.setCurrentLevel(5, player3.fe.getBaseLevel(5));
            player3 = this;
            player3.fe.refreshSkill(5);
        } else if (string.equals("nbones")) {
            this.eY.addItem(new ItemStack(527, 1000));
            this.eY.addItem(new ItemStack(533, 1000));
            if (!ServerSettings.freeToPlayWorld) {
                this.eY.addItem(new ItemStack(537, 1000));
            }
        } else if (string.equals("quests")) {
            this.bj();
        } else if (string.equals("rquests")) {
            this.d();
        } else if (string.equals("sitem")) {
            int n = Integer.parseInt(object[0]);
            if ((n < 7956 || n > 8118) && n >= 0 && n <= 11790 && ItemDefinition.isDefined(n)) {
                ItemDefinition itemDefinition;
                int n3 = 1;
                if (((String[])object).length > 1) {
                    n3 = Integer.parseInt(object[1]);
                    int n4 = n3 = n3 > Integer.MAX_VALUE ? Integer.MAX_VALUE : n3;
                }
                if (!(itemDefinition = ItemDefinition.forId(n)).isStackable() && n3 > 28) {
                    n = itemDefinition.getNotedId();
                }
                object = new ItemStack(n, n3);
                this.eY.addItem((ItemStack)object);
            }
        } else if (string.equals("char")) {
            Player player = this;
            player.packetSender.showInterface(3559);
        } else if (string.equals("rchar")) {
            this.bh();
        } else if (string.equals("skiptask")) {
            Player player = this;
            player.fh.completeTask();
        } else if (string.equals("bot")) {
            if (!this.botEnabled || this.de) {
                Player player = this;
                Player player4 = this;
                player4.packetSender.sendGameMessage("Bot started.");
                this.moveTo(this.getPosition());
                StartBotCommandTask startBotCommandTask = new StartBotCommandTask(this, 2, player);
                World.getTaskScheduler().schedule(startBotCommandTask);
            }
        } else if (string.equals("sbot")) {
            if (this.de) {
                BotPlayer botPlayer = (BotPlayer)this;
                if (this.az == 1) {
                    botPlayer.startCombatLoadoutBot();
                } else if (this.az == 0) {
                    botPlayer.startSkillingBot();
                } else if (this.az == 2) {
                    botPlayer.startTradeAdvertBot();
                } else if (this.az == 3) {
                    botPlayer.startDropPartyBot();
                } else if (this.az == 4) {
                    botPlayer.startProgressiveBot();
                } else if (this.az == 5 || this.az == 6) {
                    botPlayer.startClanWarsBot(this.az);
                }
            }
        } else if (string.equals("modern")) {
            Player player = this;
            player.packetSender.setSidebarInterface(6, 1151);
            this.spellbook = Spellbook.MODERN;
        } else if (string.equals("ancient")) {
            Player player = this;
            player.packetSender.setSidebarInterface(6, 12855);
            this.spellbook = Spellbook.ANCIENT;
        } else if (string.equals("tele")) {
            try {
                int n = Integer.parseInt(object[0]);
                int n5 = Integer.parseInt(object[1]);
                int n6 = Integer.parseInt(object[2]);
                this.moveTo(new Position(n, n5, n6));
            }
            catch (Exception exception) {
                try {
                    int n = Integer.parseInt(object[0]);
                    int n7 = Integer.parseInt(object[1]);
                    this.moveTo(new Position(n, n7, this.getPosition().getPlane()));
                }
                catch (Exception exception2) {}
            }
        } else if (string.equals("cquest")) {
            try {
                int n = Integer.parseInt(object[0]);
                QuestDefinition questDefinition = QuestDefinition.b(n);
                Player player = this;
                player.packetSender.sendInterfaceTextColor(questDefinition.d(), Color.GREEN);
                this.questStates[n] = 1;
                if (n == 16) {
                    this.dv = GameUtil.h(2) + 1;
                }
                player = this;
                player.ff.d();
            }
            catch (Exception exception) {}
        } else if (string.equals("rquest")) {
            try {
                int n = Integer.parseInt(object[0]);
                QuestDefinition questDefinition = QuestDefinition.b(n);
                Player player = this;
                player.packetSender.sendInterfaceTextColor(questDefinition.d(), Color.RED);
                this.questStates[n] = 0;
                this.bI[n] = 0;
                if (n == 16) {
                    this.dv = 0;
                }
                player = this;
                player.ff.d();
            }
            catch (Exception exception) {}
        }
        Player player = this;
        player.packetSender.sendGameMessage("Cheat activated.");
        if (ServerSettings.broadcastCheatUsageEnabled && bl) {
            String string2 = string;
            if (string2.equals("sitem")) {
                string2 = "item";
            }
            player = this;
            String string3 = String.valueOf(player.username) + " used cheat: " + string2;
            System.out.println(string3);
            Player[] playerArray = World.f();
            int n = playerArray.length;
            int n8 = 0;
            while (n8 < n) {
                player = playerArray[n8];
                if (player != null && player != this) {
                    player.packetSender.sendGameMessage(string3);
                }
                ++n8;
            }
        }
    }

    public final void a(String string, String[] stringArray, String string2) {
        Object object;
        int n;
        if ((string = string.toLowerCase()).equals("pk")) {
            if (this.q == null) {
                this.packetSender.sendGameMessage("You have to be in group in order to use this command.");
                return;
            }
            if (this.q != null && this.q.a == this) {
                this.packetSender.sendGameMessage("You can't be the group leader to use this command.");
                return;
            }
            this.botEnabled = !this.botEnabled;
            this.packetSender.sendGameMessage("Pkbot " + (this.botEnabled ? "enabled." : "disabled."));
            if (this.botEnabled) {
                BotCombatLoadoutManager.b(this);
            }
        } else if (string.equals("ms")) {
            this.f = n = this.f == 0 ? 1 : 0;
            this.packetSender.sendGameMessage("Movement system set to: " + n);
        } else if (string.equals("lquest")) {
            try {
                String string3 = stringArray[0].toLowerCase();
                int n2 = 1;
                while (n2 < QuestDefinition.a) {
                    object = QuestDefinition.b(n2);
                    ((QuestDefinition)object).c();
                    if (((QuestDefinition)object).c().toLowerCase().contains(string3)) {
                        this.packetSender.sendGameMessage("[" + n2 + "] " + ((QuestDefinition)object).c());
                    }
                    ++n2;
                }
            }
            catch (Exception exception) {}
        } else if (string.equals("home")) {
            object = this;
            ((Player)object).packetSender.setSidebarInterface(6, 1151);
            this.spellbook = Spellbook.MODERN;
            MagicSpellAction.castSelfSpell(this, SpellDefinition.HOME_TELEPORT);
        } else if (string.equals("runes")) {
            this.a(string, stringArray);
        } else if (string.equals("master")) {
            this.a(string, stringArray);
        } else if (string.equals("setlevel")) {
            this.a(string, stringArray);
        } else if (string.equals("modern")) {
            this.a(string, stringArray);
        } else if (string.equals("ancient") && ServerSettings.cacheVersion >= 308) {
            this.a(string, stringArray);
        } else if (string.equals("nfood")) {
            this.a(string, stringArray);
        } else if (string.equals("food")) {
            this.a(string, stringArray);
        } else if (string.equals("skiptask")) {
            this.a(string, stringArray);
        } else if (string.equals("bot")) {
            this.a(string, stringArray);
        } else if (string.equals("money")) {
            this.a(string, stringArray);
        } else if (string.equals("arrows")) {
            this.a(string, stringArray);
        } else if (string.equals("empty")) {
            this.a(string, stringArray);
        } else if (string.equals("bank")) {
            this.a(string, stringArray);
        } else if (string.equals("god")) {
            this.a(string, stringArray);
        } else if (string.equals("run")) {
            this.a(string, stringArray);
        } else if (string.equals("irun")) {
            this.a(string, stringArray);
        } else if (string.equals("music")) {
            this.a(string, stringArray);
        } else if (string.equals("pray")) {
            this.a(string, stringArray);
        } else if (string.equals("nbones")) {
            this.a(string, stringArray);
        } else if (string.equals("quests")) {
            this.a(string, stringArray);
        } else if (string.equals("rquests")) {
            this.a(string, stringArray);
        } else if (string.equals("rquest")) {
            this.a(string, stringArray);
        } else if (string.equals("cquest")) {
            this.a(string, stringArray);
        } else if (string.equals("sitem")) {
            int n3 = Integer.parseInt(stringArray[0]);
            if ((n3 < 7956 || n3 > 8118) && n3 >= 0 && n3 <= 11790 && ItemDefinition.isDefined(n3)) {
                this.a(string, stringArray);
            }
        } else if (string.equals("char")) {
            this.a(string, stringArray);
        } else if (string.equals("rchar")) {
            this.a(string, stringArray);
        } else if (string.equals("tele")) {
            try {
                this.a(string, stringArray);
            }
            catch (Exception exception) {
                try {
                    this.a(string, stringArray);
                }
                catch (Exception exception2) {}
            }
        }
        object = this;
        if (((Player)object).playerRights >= 2 && !string.equals("bug") && !string.equals("yell")) {
            new StringBuilder(String.valueOf(string)).append(stringArray.length > 0 ? " " + string2 : "");
        }
        object = this;
        if (((Player)object).playerRights >= 0) {
            if (string.equals("inv")) {
                if (this.gameMode != 0) {
                    this.packetSender.sendGameMessage("You are not playing on normal gamemode and cannot form a group.");
                    return;
                }
                if (this.q != null && this.q.a != this) {
                    this.packetSender.sendGameMessage("You can only invite as a group leader.");
                    return;
                }
                Player player = World.a(string2);
                if (player == null) {
                    return;
                }
                if (player.gameMode != 0) {
                    object = player;
                    this.packetSender.sendGameMessage(String.valueOf(((Player)object).username) + " is not playing on normal gamemode and cannot join a group.");
                    return;
                }
                if (player.q != null && player.s == this) {
                    PlayerGroup.a(this, player);
                    return;
                }
                if (player.q != null && this.q != null && player.q == this.q) {
                    object = player;
                    this.packetSender.sendGameMessage("You kicked " + ((Player)object).username + " from the group.");
                    this.q.e(player);
                    return;
                }
                if (player.q != null) {
                    object = player;
                    this.packetSender.sendGameMessage(String.valueOf(((Player)object).username) + " is already in a group.");
                    return;
                }
                PlayerGroup.a(this, player);
                return;
            }
            if (string.equals("pw")) {
                if (stringArray.length != 2) {
                    this.packetSender.sendGameMessage("usage: ::pw oldPassword newPassword");
                    return;
                }
                String string4 = stringArray[0];
                String string5 = stringArray[1];
                object = this;
                if (((Player)object).password.equals(string4)) {
                    string = string5;
                    object = this;
                    this.password = string;
                    this.packetSender.sendGameMessage("Password set to: " + string5);
                    return;
                }
                this.packetSender.sendGameMessage("Incorrect old password!");
                return;
            }
            if (string.equals("statsme") && ServerSettings.cacheVersion >= 254) {
                n = GameplayHelper.a(this.eg, System.currentTimeMillis());
                if (n == 0) {
                    n = 1;
                }
                this.b();
                this.a(new String[]{"@whi@Your stats", "", "@blu@Account created: " + GameplayHelper.a(this.eg) + " (d-m-y)", "Npcs killed: " + this.dT, "Players killed: " + this.dU, "Deaths: " + this.dV, "@whi@--Dueling--", "Wins: " + this.eb, "Losses: " + this.ec, "@whi@--Barrows--", "Runs done: " + this.ed, "@whi@--Clues Completed--", "Easy: " + this.dW, "Medium: " + this.dX, "Hard: " + this.dY, "@whi@--Shopping--", "Worth of sold items: " + GameUtil.c(this.dZ), "Worth of bought items: " + GameUtil.c(this.ea), "Profit: " + GameUtil.c(this.dZ - this.ea), "@whi@--Playing time--", "This session: " + GameplayHelper.b(this.c()), "Total: " + GameplayHelper.b(this.ba()), "Average per day: " + GameplayHelper.b(this.ba() / (long)n)});
                object = this;
                ((Player)object).packetSender.showInterface(8134);
                return;
            }
            if (string.equals("players")) {
                this.packetSender.sendGameMessage("There are currently " + World.b() + " players online.");
            }
        }
    }

    private void e() {
        this.botTaskState = "do task";
        this.botInteractionOption = this.currentBotTask.getInteractionOption(this);
        if (!this.dropPartyLeader && !this.currentBotTask.usesCustomTaskAction) {
            if (this.currentBotTask.interactionTargetType == 0) {
                this.interactWithBotObjectTargets(this.botInteractionTargetIds);
            } else {
                this.interactWithBotNpcTargets(this.botInteractionTargetIds);
            }
        }
        if (this.currentBotTask.usesCustomTaskAction) {
            this.currentBotTask.startCustomTaskAction(this);
        }
    }

    public final void bk() {
        if (this.currentBotRoute == null) {
            return;
        }
        if (this.isMoving()) {
            return;
        }
        this.getMovementQueue().clearMovementActions();
        if (this.botTaskState.equals("do task") && DropPartyBotManager.dropPartyParticipants.contains(this)) {
            this.currentBotRoute = null;
            this.botPathWaypointIndex = 0;
            return;
        }
        if (this.botPathWaypointIndex == -1) {
            Player player = this;
            System.out.println("Error! " + player.username + " does not know where to walk!");
            return;
        }
        Position position = this.currentBotRoute.waypoints[this.botPathWaypointIndex];
        int n = GameUtil.b(this.getPosition(), position);
        if (n > 50) {
            Player player = this;
            System.out.println("Detected possibly frozen bot: " + player.username + " at: " + this.getPosition() + ", trying to apply fix.");
            this.moveTo(position);
            player = this;
            System.out.println(String.valueOf(player.username) + " teleported to current destination: " + position);
        }
        PathFinder.getInstance();
        boolean bl = PathFinder.findPath(this, position.getX(), position.getY(), false, 0, 0);
        if (!bl) {
            boolean bl2 = false;
            if (this.botUseTaskItemOnTarget) {
                this.botUseTaskItemOnTarget = false;
                bl2 = true;
            }
            Player player = this;
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            arrayList.add(player.botTargetNpcId);
            int n2 = 3;
            n2 = 3;
            n2 = 0;
            Player player2 = player;
            player2.interactWithBotObjectTargets(arrayList, false, 3, 3);
            if (bl2) {
                this.botUseTaskItemOnTarget = true;
            }
        }
        if (!bl) {
            return;
        }
        if (this.getPosition().equals(position)) {
            ++this.botPathWaypointIndex;
            if (this.botPathWaypointIndex == this.currentBotRoute.waypoints.length) {
                this.currentBotRoute = null;
                this.botPathWaypointIndex = 0;
                if (this.botTaskState.equals("walk to task")) {
                    this.e();
                    return;
                }
                if (this.botTaskState.equals("walk pretask path1")) {
                    if (this.dropPartyPretaskLoopCount >= DropPartyBotManager.dropPartyPretaskLoopLimit) {
                        this.dropPartyPretaskComplete = true;
                    }
                    if (this.dropPartyPretaskComplete) {
                        this.currentBotTask.startWalkToTask(this);
                        return;
                    }
                    this.currentBotTask.returnPretaskPath(this);
                    ++this.dropPartyPretaskLoopCount;
                    return;
                }
                if (this.botTaskState.equals("walk pretask path2")) {
                    this.currentBotTask.startPretaskPath(this);
                    return;
                }
                if (this.botTaskState.equals("walk to bank") || this.botTaskState.equals("worldwalk to bank")) {
                    this.botTaskState = "empty inventory";
                    if (this.currentBotTask.usesDepositBox) {
                        this.botInteractionOption = 2;
                        ArrayList<Integer> arrayList = new ArrayList<Integer>();
                        arrayList.add(2619);
                        this.interactWithBotNpcTargets(arrayList);
                        return;
                    }
                    this.botInteractionOption = 2;
                    ArrayList<Integer> arrayList = new ArrayList<Integer>();
                    arrayList.add(2213);
                    arrayList.add(11758);
                    this.interactWithBotObjectTargets(arrayList);
                    return;
                }
                if (this.botTaskState.startsWith("walk towards")) {
                    this.currentBotTask.c(this, false);
                    return;
                }
                if (this.botTaskState.startsWith("world walk towards")) {
                    BotWorldRouteWalker.advanceRouteSegment(this);
                    return;
                }
                if (this.botTaskState.startsWith("world walk find")) {
                    BotWorldRouteWalker.findWorldRoute(this);
                    return;
                }
                if (this.botTaskState.startsWith("world walk finish")) {
                    this.currentWorldRouteChoice = null;
                    this.botTaskState = "empty inventory";
                    GameplayHelper.f(this);
                }
                return;
            }
            position = this.currentBotRoute.waypoints[this.botPathWaypointIndex];
        }
        if (position != null) {
            if (this.az == 4 && !this.getMovementQueue().isRunning() && this.getRunEnergyPercent() >= 80) {
                this.getMovementQueue().setRunning(true);
            }
            EntityTargetMovement.clearMovementTarget(this);
            PathFinder.getInstance();
            PathFinder.findGlobalPath(this, position.getX(), position.getY(), true, 0, 0);
            return;
        }
        Player player = this;
        player.packetSender.sendGameMessage("No destination!");
    }

    /*
     * WARNING - void declaration
     */
    public final void interactWithBotNpcTargets(ArrayList arrayList) {
        Object object;
        Object object2;
        block42: {
            block40: {
                Object object3;
                block41: {
                    int n;
                    void npc;
                    int n2;
                    int n3 = 30;
                    if (arrayList.size() == 1 && this.az == 4 && (n2 = ((Integer)arrayList.get(0)).intValue()) == -1 && !this.A(true)) {
                        return;
                    }
                    if (eu == null) {
                        Player player = this;
                        if (player.username.toLowerCase().equals("mod test")) {
                            eu = this;
                        }
                    }
                    if (this.currentBotTask != null && this.botTaskState.equals("do task")) {
                        if (this.currentBotTask.targetSearchRadius != -1) {
                            n3 = this.currentBotTask.targetSearchRadius;
                        }
                        if (this.currentBotTask.combatTask) {
                            if (this.botCombatStyle == 2 && this.az == 4 && this.botElementalSpellIndex != -1) {
                                this.b(BotCombatLoadoutTables.elementalStrikeSpells[this.botElementalSpellIndex]);
                            }
                            if (this.botCombatStyle == 1) {
                                Player player = this;
                                n2 = player.eZ.getItemIdAtSlot(3);
                                boolean bl = false;
                                if (n2 > 0 && (((String)(object2 = ItemDefinition.forId(n2).getName().toLowerCase())).contains("bow") || ((String)object2).contains("knife") || ((String)object2).contains("dart") || ((String)object2).contains("javelin") || ((String)object2).contains("thrownaxe"))) {
                                    bl = true;
                                }
                                if (!bl) {
                                    this.botTaskReturnToBankRequested = true;
                                    this.currentBotTask.startWalkToBank(this);
                                    return;
                                }
                            }
                        }
                        if (this.currentBotTask.combatTask && this.currentBotTask.getForcedCombatStyle() != 2) {
                            Player player = this;
                            if (player.eY.getItemAmount(this.botFoodItemId) == 0 || this.eY.getContainer().getFreeSlots() == 0) {
                                this.currentBotTask.startWalkToBank(this);
                                return;
                            }
                        }
                    }
                    int n4 = 100;
                    Position position2 = this.getPosition();
                    object2 = null;
                    ArrayList<Object> arrayList2 = new ArrayList<Object>();
                    Object object4 = World.g();
                    int n5 = ((Npc[])object4).length;
                    boolean n6 = false;
                    while (npc < n5) {
                        object3 = object4[npc];
                        if (object3 != null && !((Entity)object3).isDead() && ((Npc)object3).isActive() && ((Npc)object3).getOwnerPlayer() == null && (n = GameUtil.b(position2, ((Entity)object3).getPosition())) <= n3) {
                            int n7 = ((Entity)object3).getPosition().getY();
                            int n8 = ((Entity)object3).getPosition().getX();
                            Player player = this;
                            int n9 = -1;
                            int n10 = -1;
                            int n11 = -1;
                            int n12 = -1;
                            if (player.currentBotTask != null && player.botTaskState.equals("do task")) {
                                if (player.currentBotTask.targetMinX != -1) {
                                    n9 = player.currentBotTask.targetMinX;
                                }
                                if (player.currentBotTask.targetMinY != -1) {
                                    n10 = player.currentBotTask.targetMinY;
                                }
                                if (player.currentBotTask.targetMaxX != -1) {
                                    n11 = player.currentBotTask.targetMaxX;
                                }
                                if (player.currentBotTask.targetMaxY != -1) {
                                    n12 = player.currentBotTask.targetMaxY;
                                }
                            }
                            if ((n9 != -1 && n8 < n9 ? false : (n10 != -1 && n7 < n10 ? false : (n11 != -1 && n8 > n11 ? false : n12 == -1 || n7 <= n12))) && (!((Npc)object3).getDefinition().isAttackable() || ((Entity)object3).isInMultiCombatArea() || ((Entity)object3).getSingleCombatTimer().hasElapsed())) {
                                Iterator iterator = arrayList.iterator();
                                while (iterator.hasNext()) {
                                    int n13 = (Integer)iterator.next();
                                    if (n13 != ((Npc)object3).getNpcId()) continue;
                                    arrayList2.add(object3);
                                    n13 = GameUtil.b(position2, ((Entity)object3).getPosition());
                                    if (n13 >= n4) continue;
                                    n4 = n13;
                                }
                            }
                        }
                        ++npc;
                    }
                    object3 = new ArrayList();
                    for (Npc position : arrayList2) {
                        int n14 = GameUtil.b(position2, position.getPosition());
                        if (n14 > n4 + 3) continue;
                        ((ArrayList)object3).add(position);
                    }
                    Object var8_26 = null;
                    if (((ArrayList)object3).size() <= 1) break block41;
                    Collections.shuffle(object3);
                    object4 = ((ArrayList)object3).iterator();
                    while (object4.hasNext()) {
                        Position position;
                        object = (Npc)object4.next();
                        boolean bl = GameUtil.a(this.getPosition(), ((Entity)object).getPosition(), false);
                        n = bl ? 1 : 0;
                        if (!bl && (position = this.d((Npc)object)) == null) {
                            continue;
                        }
                        break block40;
                    }
                    break block42;
                }
                if (((ArrayList)object3).size() != 1) break block42;
                object = (Npc)((ArrayList)object3).get(0);
                boolean bl = GameUtil.a(this.getPosition(), ((Entity)object).getPosition(), false);
                if (!bl) {
                    Position position = this.d((Npc)object);
                }
            }
            object2 = object;
        }
        if (object2 != null) {
            void var8_30;
            int n = ((Entity)object2).getIndex();
            if (n < 0 || n > World.g().length) {
                return;
            }
            Npc npc = World.g()[n];
            if (npc == null || !npc.isInteractable()) {
                return;
            }
            Player player = this;
            player.packetSender.sendGameMessage("Nearby npc found: " + ((Npc)object2).getNpcId() + " [" + ((Entity)object2).getPosition() + "].");
            this.ip = 0;
            this.iq = 0;
            if (var8_30 != null) {
                PathFinder.getInstance();
                PathFinder.findPath(this, var8_30.getX(), var8_30.getY(), false, 0, 0);
            }
            if (npc.getDefinition().isAttackable()) {
                this.setAttackRange(1);
                this.setMovementTarget(npc);
                CombatManager.startCombat(this, npc);
                return;
            }
            int n15 = npc.getNpcId();
            player = this;
            this.interactionTargetId = n15;
            n15 = npc.getPosition().getX();
            player = this;
            this.interactionTargetX = n15;
            n15 = npc.getPosition().getY();
            player = this;
            this.interactionTargetY = n15;
            n15 = this.getPosition().getPlane();
            player = this;
            this.interactionTargetPlane = n15;
            n15 = n;
            player = this;
            this.interactionTargetIndex = n15;
            this.getUpdateState().setFaceEntity(n);
            this.setAttackRange(1);
            this.setMovementTarget(npc);
            if (!this.botUseTaskItemOnTarget) {
                if (this.botInteractionOption == 1) {
                    InteractionDispatcher.setCurrentInteractionType(InteractionType.FIRST_NPC);
                }
                if (this.botInteractionOption == 2) {
                    InteractionDispatcher.setCurrentInteractionType(InteractionType.SECOND_NPC);
                }
                if (this.botInteractionOption == 3) {
                    InteractionDispatcher.setCurrentInteractionType(InteractionType.THIRD_NPC);
                }
            } else {
                player = this;
                n15 = player.eY.getContainer().indexOfItem(this.botTaskItemId);
                player = this;
                this.selectedItemSlot = n15;
                n15 = this.botTaskItemId;
                player = this;
                this.selectedItemId = n15;
                this.setInteractionTarget(npc);
                InteractionDispatcher.setCurrentInteractionType(InteractionType.ITEM_ON_NPC);
            }
            InteractionDispatcher.dispatchCurrentInteraction(this);
            return;
        }
        Player player = this;
        player.packetSender.sendGameMessage("Nearby npc not found.");
        ++this.ip;
        if (this.ip == 10) {
            this.ip = 0;
            this.A(true);
            return;
        }
        object = this;
        RetryMissingNpcSearchTask retryMissingNpcSearchTask = new RetryMissingNpcSearchTask(this, 10, (Player)object, arrayList);
        World.getTaskScheduler().schedule(retryMissingNpcSearchTask);
    }

    private Position d(Npc npc) {
        Position position = null;
        int n = npc.getPosition().getX();
        int n2 = npc.getPosition().getY();
        int n3 = npc.getSize() - 1;
        int n4 = npc.getSize() - 1;
        int n5 = 40;
        int n6 = n - 1;
        while (n6 <= n + n3 + 1) {
            int n7 = n2 - 1;
            while (n7 <= n2 + n4 + 1) {
                if (n6 < n || n6 > n + n3 || n7 < n2 || n7 > n2 + n4) {
                    Position position2 = new Position(n6, n7);
                    Position position3 = GameUtil.a(npc.getPosition().getX(), npc.getPosition().getY(), position2.getX(), position2.getY(), npc.getSize(), npc.getSize(), this.getPosition().getPlane());
                    if (position3 != null) {
                        boolean bl = GameUtil.a(position2, npc.getPosition(), false);
                        PathFinder.getInstance();
                        boolean bl2 = PathFinder.findPath(this, n6, n7, false, 0, 0);
                        int n8 = this.getMovementQueue().getSteps().size();
                        if (bl && bl2 && n8 < n5) {
                            position = position2;
                            n5 = n8;
                        }
                        this.getMovementQueue().reset();
                    }
                }
                ++n7;
            }
            ++n6;
        }
        return position;
    }

    private Position a(WorldObject worldObject, ObjectDefinition objectDefinition) {
        Position position = null;
        int n = worldObject.getPosition().getX();
        int n2 = worldObject.getPosition().getY();
        int n3 = objectDefinition.getWidthForOrientation(worldObject.getOrientation()) - 1;
        int n4 = objectDefinition.getLengthForOrientation(worldObject.getOrientation()) - 1;
        int n5 = 40;
        int n6 = n - 1;
        while (n6 <= n + n3 + 1) {
            int n7 = n2 - 1;
            while (n7 <= n2 + n4 + 1) {
                if (n6 < n || n6 > n + n3 || n7 < n2 || n7 > n2 + n4) {
                    Position position2 = new Position(n6, n7);
                    Position position3 = GameUtil.a(worldObject.getPosition().getX(), worldObject.getPosition().getY(), position2.getX(), position2.getY(), objectDefinition.getWidthForOrientation(worldObject.getOrientation()), objectDefinition.getLengthForOrientation(worldObject.getOrientation()), this.getPosition().getPlane());
                    if (position3 != null) {
                        boolean bl = InteractionDispatcher.canReachObjectInteraction(position2, worldObject.getPosition(), worldObject);
                        PathFinder.getInstance();
                        boolean bl2 = PathFinder.findPath(this, n6, n7, false, 0, 0);
                        int n8 = this.getMovementQueue().getSteps().size();
                        if (bl && bl2 && n8 < n5) {
                            position = position2;
                            n5 = n8;
                        }
                        this.getMovementQueue().reset();
                    }
                }
                ++n7;
            }
            ++n6;
        }
        return position;
    }

    public final boolean interactWithBotObjectTargetsNoRetry(ArrayList arrayList, boolean bl) {
        return this.interactWithBotObjectTargets(arrayList, false, 20, 3);
    }

    public final boolean interactWithBotObjectTargets(ArrayList arrayList) {
        return this.interactWithBotObjectTargets(arrayList, true, 20, 3);
    }

    private WorldObject m(int n, int n2) {
        int n3 = -1;
        int n4 = -1;
        int n5 = -1;
        int n6 = -1;
        if (this.currentBotTask != null && this.botTaskState.equals("do task")) {
            if (this.currentBotTask.targetMinX != -1) {
                n3 = this.currentBotTask.targetMinX;
            }
            if (this.currentBotTask.targetMinY != -1) {
                n4 = this.currentBotTask.targetMinY;
            }
            if (this.currentBotTask.targetMaxX != -1) {
                n5 = this.currentBotTask.targetMaxX;
            }
            if (this.currentBotTask.targetMaxY != -1) {
                n6 = this.currentBotTask.targetMaxY;
            }
        }
        if (n3 != -1 && n < n3) {
            return null;
        }
        if (n4 != -1 && n2 < n4) {
            return null;
        }
        if (n5 != -1 && n > n5) {
            return null;
        }
        if (n6 != -1 && n2 > n6) {
            return null;
        }
        WorldObject worldObject = SkillActionHelper.findWorldObjectAt(n, n2, this.getPosition().getPlane());
        if (worldObject != null) {
            ObjectManager.getInstance();
            DynamicObject dynamicObject = ObjectManager.findDynamicObjectAt(n, n2, this.getPosition().getPlane());
            if (dynamicObject != null) {
                worldObject = dynamicObject.getWorldObject();
            }
            return worldObject;
        }
        return null;
    }

    private boolean interactWithBotObjectTargets(ArrayList arrayList, boolean bl, int n, int n2) {
        WorldObject worldObject7;
        int n3;
        int n4;
        if (arrayList.size() == 1 && this.az == 4 && (n4 = ((Integer)arrayList.get(0)).intValue()) == -1) {
            Player player = this;
            System.out.println("Detected bugged bot: " + player.username + ", trying to fix by reseting and relogging.");
            arrayList = null;
            player = this;
            this.currentBotTask = arrayList;
            arrayList = null;
            player = this;
            this.deferredBotTask = arrayList;
            this.f(new Position(ServerSettings.respawnX, ServerSettings.respawnY, ServerSettings.respawnPlane));
            World.a(this);
            return false;
        }
        if (this.currentBotTask != null) {
            if (this.botTaskState.equals("do task") && this.currentBotTask.targetSearchRadius != -1) {
                n = this.currentBotTask.targetSearchRadius;
            }
            if (this.botTaskState.equals("escape")) {
                return false;
            }
        }
        n4 = 100;
        WorldObject worldObject2 = null;
        ArrayList<WorldObject> arrayList2 = new ArrayList<WorldObject>();
        int n5 = this.getPosition().getX();
        int n6 = this.getPosition().getY();
        int n7 = 0;
        while (n7 <= n) {
            int n8;
            if (n7 > n4 + n2) break;
            if (n7 == 0) {
                n3 = n5;
                n8 = n6;
                worldObject7 = this.m(n3, n8);
                if (worldObject7 != null) {
                    Iterator iterator = arrayList.iterator();
                    while (iterator.hasNext()) {
                        int n9 = (Integer)iterator.next();
                        if (n9 != worldObject7.getObjectId()) continue;
                        arrayList2.add(worldObject7);
                        int n10 = n7;
                        if (n10 < n4) {
                            n4 = n10;
                        }
                        break;
                    }
                }
            } else {
                n3 = n6 - n7;
                while (n3 <= n6 + n7) {
                    n8 = n5 - n7;
                    int n11 = n3;
                    WorldObject worldObject3 = this.m(n8, n11);
                    if (worldObject3 != null) {
                        Iterator iterator = arrayList.iterator();
                        while (iterator.hasNext()) {
                            n8 = (Integer)iterator.next();
                            if (n8 != worldObject3.getObjectId()) continue;
                            arrayList2.add(worldObject3);
                            n8 = n7;
                            if (n8 >= n4) break;
                            n4 = n8;
                            break;
                        }
                    }
                    ++n3;
                }
                n3 = n6 - n7;
                while (n3 <= n6 + n7) {
                    n8 = n5 + n7;
                    int n12 = n3;
                    WorldObject worldObject4 = this.m(n8, n12);
                    if (worldObject4 != null) {
                        Iterator iterator = arrayList.iterator();
                        while (iterator.hasNext()) {
                            n8 = (Integer)iterator.next();
                            if (n8 != worldObject4.getObjectId()) continue;
                            arrayList2.add(worldObject4);
                            n8 = n7;
                            if (n8 >= n4) break;
                            n4 = n8;
                            break;
                        }
                    }
                    ++n3;
                }
                n3 = n5 - (n7 - 1);
                while (n3 <= n5 + (n7 - 1)) {
                    n8 = n3;
                    int n13 = n6 - n7;
                    WorldObject worldObject5 = this.m(n8, n13);
                    if (worldObject5 != null) {
                        Iterator iterator = arrayList.iterator();
                        while (iterator.hasNext()) {
                            n8 = (Integer)iterator.next();
                            if (n8 != worldObject5.getObjectId()) continue;
                            arrayList2.add(worldObject5);
                            n8 = n7;
                            if (n8 >= n4) break;
                            n4 = n8;
                            break;
                        }
                    }
                    ++n3;
                }
                n3 = n5 - (n7 - 1);
                while (n3 <= n5 + (n7 - 1)) {
                    n8 = n3;
                    int n14 = n6 + n7;
                    WorldObject worldObject6 = this.m(n8, n14);
                    if (worldObject6 != null) {
                        Iterator iterator = arrayList.iterator();
                        while (iterator.hasNext()) {
                            n8 = (Integer)iterator.next();
                            if (n8 != worldObject6.getObjectId()) continue;
                            arrayList2.add(worldObject6);
                            n8 = n7;
                            if (n8 >= n4) break;
                            n4 = n8;
                            break;
                        }
                    }
                    ++n3;
                }
            }
            ++n7;
        }
        n3 = 0;
        Object object = null;
        if (arrayList2.size() > 1) {
            Collections.shuffle(arrayList2);
            for (WorldObject worldObject7 : arrayList2) {
                ObjectDefinition objectDefinition = ObjectDefinition.forId(worldObject7.getObjectId());
                n3 = GameUtil.b(this.getPosition(), worldObject7.getPosition());
                object = this.a(worldObject7, objectDefinition);
                if (object == null && n3 > 2) continue;
                worldObject2 = worldObject7;
                break;
            }
        } else if (arrayList2.size() == 1) {
            worldObject7 = (WorldObject)arrayList2.get(0);
            ObjectDefinition objectDefinition = ObjectDefinition.forId(worldObject7.getObjectId());
            n3 = GameUtil.b(this.getPosition(), worldObject7.getPosition());
            object = this.a(worldObject7, objectDefinition);
            worldObject2 = worldObject7;
        }
        if (worldObject2 != null) {
            Player player = this;
            player.packetSender.sendGameMessage("Nearby object found: " + worldObject2.getObjectId() + " [" + worldObject2.getPosition() + "].");
            if (object != null || n3 <= 2) {
                if (object == null) {
                    player = this;
                    player.packetSender.sendGameMessage("No position to walk to was found! Trying to use object anyway, distance: " + n3);
                } else {
                    if (((Position)object).getX() == 2934 && ((Position)object).getY() == 3449) {
                        ((Position)object).setX(2935);
                        ((Position)object).setY(3450);
                    } else if (((Position)object).getX() == 2933 && ((Position)object).getY() == 3290) {
                        ((Position)object).setX(2933);
                        ((Position)object).setY(3289);
                    }
                    PathFinder.getInstance();
                    PathFinder.findPath(this, ((Position)object).getX(), ((Position)object).getY(), false, 0, 0);
                }
                int n15 = worldObject2.getPosition().getX();
                player = this;
                this.interactionTargetX = n15;
                n15 = worldObject2.getObjectId();
                player = this;
                this.interactionTargetId = n15;
                n15 = worldObject2.getPosition().getY();
                player = this;
                this.interactionTargetY = n15;
                n15 = worldObject2.getPosition().getPlane();
                player = this;
                this.interactionTargetPlane = n15;
                EntityTargetMovement.clearMovementTarget(this);
                if (!this.botUseTaskItemOnTarget) {
                    if (this.botInteractionOption == 1) {
                        InteractionDispatcher.setCurrentInteractionType(InteractionType.FIRST_OBJECT);
                    }
                    if (this.botInteractionOption == 2) {
                        InteractionDispatcher.setCurrentInteractionType(InteractionType.SECOND_OBJECT);
                    }
                    if (this.botInteractionOption == 3) {
                        InteractionDispatcher.setCurrentInteractionType(InteractionType.THIRD_OBJECT);
                    }
                } else {
                    player = this;
                    n15 = player.eY.getContainer().indexOfItem(this.botTaskItemId);
                    player = this;
                    this.selectedItemSlot = n15;
                    n15 = this.botTaskItemId;
                    player = this;
                    this.selectedItemId = n15;
                    InteractionDispatcher.setCurrentInteractionType(InteractionType.ITEM_ON_OBJECT);
                }
                InteractionDispatcher.dispatchCurrentInteraction(this);
                return true;
            }
            player = this;
            player.packetSender.sendGameMessage("No position to walk to was found!");
            if (bl) {
                object = this;
                this.ir = new RetryUnreachableObjectTask(this, 10, (Player)object, arrayList);
                World.getTaskScheduler().schedule(this.ir);
            }
            return false;
        }
        Player player = this;
        player.packetSender.sendGameMessage("Nearby object not found.");
        if (bl) {
            object = this;
            this.ir = new RetryMissingObjectSearchTask(this, 10, (Player)object, arrayList);
            World.getTaskScheduler().schedule(this.ir);
        }
        return false;
    }

    public static boolean a(Player player, Npc npc, int n, int n2) {
        int n3 = player.getPosition().getX();
        while (n3 < player.getPosition().getX() + player.getSize()) {
            int n4 = player.getPosition().getY();
            while (n4 < player.getPosition().getY() + player.getSize()) {
                int n5 = npc.getPosition().getX();
                while (n5 < npc.getPosition().getX() + npc.getSize()) {
                    int n6 = npc.getPosition().getY();
                    while (n6 < npc.getPosition().getY() + npc.getSize()) {
                        if (n5 == n3 + n && n6 == n4 + n2) {
                            return true;
                        }
                        ++n6;
                    }
                    ++n5;
                }
                ++n4;
            }
            ++n3;
        }
        return false;
    }

    public final void d(Position position) {
        Player player = this;
        player.packetSender.showInterface(8677);
        boolean bl = true;
        player = this;
        this.gM = bl;
        CycleEventHandler.getInstance().schedule(this, new DelayedPositionMoveTask(this, position), 4);
    }

    public final void bl() {
        Object object = this;
        if (((Player)object).ho == 4535) {
            int n = -1;
            object = this;
            this.ho = n;
            object = this;
            ((Player)object).packetSender.showWalkableInterface(-1);
        }
        object = this;
        ((Player)object).packetSender.sendCameraShake(2, 3, 2, 3);
        object = new BarrowsChestDamageTask(this, 50);
        World.getTaskScheduler().schedule((TickTask)object);
        this.bU = true;
    }

    public final void b(Position position, boolean bl) {
        position.getPlane();
        boolean bl2 = false;
        int n = position.getPlane() + 4 + (this.getIndex() << 2);
        this.moveTo(new Position(position.getX(), position.getY(), n));
    }

    public final void e(Position position) {
        this.is = true;
        this.moveTo(position);
        this.is = false;
    }

    @Override
    public final void moveTo(Position object) {
        Player player;
        Player player2 = this;
        int n = player2.getPosition().getPlane();
        this.ew = ((Position)object).getPlane() != n;
        n = this.dJ() ? 1 : 0;
        if (this.ew) {
            GroundItemManager.getInstance();
            GroundItemManager.clearVisibleItems(player2);
        }
        if (n == 0) {
            boolean bl = true;
            player = this;
            this.gM = bl;
        }
        if (!this.is) {
            this.er();
        }
        this.f((Position)object);
        if (!this.ev && !this.isInBarrows()) {
            player = this;
            player.packetSender.sendMinimapState(0);
            if (!this.is) {
                player = this;
                player.packetSender.closeInterfaces();
            }
        }
        if (this.bU && !this.isInBarrows()) {
            object = this;
            player = object;
            ((Player)object).packetSender.resetCamera();
            ((Player)object).bU = false;
            BarrowsManager.c(this);
        }
        CycleEventHandler.getInstance().schedule(this, new PostTeleportBotContinuationTask(this, n != 0, player2), 1);
    }

    public final void bm() {
        GameplayHelper.g(this);
        GameplayHelper.h(this);
        ObjectManager.getInstance().refreshDynamicObjectsForPlayer(this);
        GroundItemManager.getInstance();
        GroundItemManager.clearVisibleItems(this);
        GroundItemManager.getInstance().refreshForPlayer(this);
        Npc.refreshNearbyTransformedNpcs(this);
    }

    public final void f(Position object) {
        this.getPosition().set((Position)object);
        this.getPosition().setPreviousX(((Position)object).getX());
        this.getPosition().setPreviousY(((Position)object).getY() + 1);
        this.getMovementQueue().clear();
        boolean bl = true;
        object = this;
        this.gE = bl;
        bl = true;
        object = this;
        this.gD = bl;
        object = this;
        ((Player)object).packetSender.sendPlayerIndex();
    }

    public final void a(int n, int n2, int n3) {
        this.moveTo(new Position(n, n2, 0));
    }

    public final void bn() {
        if (this.de) {
            return;
        }
        if (this.eQ()) {
            PacketWriter packetWriter = PacketBuffer.allocateWriter(3);
            Player player = this;
            packetWriter.writeByte(player.gy);
            player = this;
            int n = GameplayHelper.b(System.currentTimeMillis(), player.banExpires) + 1;
            packetWriter.writeShort(n);
            this.writePacketBuffer(packetWriter.getBuffer());
            return;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(3);
        Player player = this;
        packetWriter.writeByte(player.gy);
        player = this;
        packetWriter.writeByte(player.playerRights);
        packetWriter.writeByte(0);
        this.writePacketBuffer(packetWriter.getBuffer());
    }

    public final String bo() {
        return this.ii;
    }

    public final void b(String string) {
        this.ii = string;
    }

    public final void bp() {
        int n = 0;
        if (this.eE == 1) {
            n = 4;
        } else if (this.eE >= 2) {
            n = 1;
        }
        this.ep[492] = n;
        Player player = this;
        player.packetSender.sendConfig(492, this.ep[492]);
    }

    public final void bq() {
        Player player;
        boolean bl = this.g();
        this.bn();
        if (!bl) {
            this.disconnect();
            return;
        }
        if (this.ik) {
            Player player2 = this;
            this.cc = 0L;
            player2.f(TeleportManager.a);
        }
        Player player3 = this;
        int n = 0;
        while (n < player3.ep.length) {
            if (player3.ep[n] != 0) {
                player = player3;
                player.packetSender.sendConfig(n, player3.ep[n]);
            }
            ++n;
        }
        ErnestTheChickenQuest.e(player3);
        player3.bp();
        int n2 = 1;
        player = this;
        this.gM = n2;
        World.b(this);
        this.packetSender.sendPostLoginState().syncPlayerConfigs();
        this.getPoisonDamage();
        this.getMovementQueue().isRunning();
        if (this.s()) {
            n2 = this.getPosition().getPlane() + 4 + (this.getIndex() << 2);
            this.moveTo(new Position(this.getPosition().getX(), this.getPosition().getY(), n2));
            this.p(n2);
        }
        n2 = 1;
        player = this;
        this.gD = n2;
        PluginManager.a(this);
        this.packetSender.sendPlayerOption("Follow", 2, false);
        this.packetSender.sendPlayerOption("Trade with", 3, false);
        this.fe.refreshAllSkills();
        this.gu = true;
        Object object = this.eZ.getContainer().getItemAt(3);
        if (object != null && object.getDefinition().isMembersOnly() && ServerSettings.freeToPlayWorld) {
            object = null;
        }
        if (object != null && object.getId() == 4024) {
            this.ak = 1463;
        }
        this.getUpdateState().setUpdateRequired(true);
        this.f(true);
        this.setWeaponProfile(WeaponProfile.forItem(object));
        object = SpecialAttackDefinition.forItem(object);
        player = this;
        this.hv = object;
        player = this;
        player.fj.c();
        player = this;
        player.fk.b();
        player = this;
        player.packetSender.clearSidebarInterfaces();
        player = this;
        int n3 = player.fe.getCombatLevel();
        player = this;
        this.combatLevel = n3;
        player = this;
        GameplayHelper.f(this, player.eZ.getItemIdAtSlot(0));
        player = this;
        player.eZ.j();
        player = this;
        player.eZ.i();
        Player player4 = this;
        Object object2 = player4.eY;
        ((InventoryManager)object2).refresh();
        object2 = player4.eZ;
        ((EquipmentManager)object2).refresh();
        player4.fQ.processPendingPinChanges();
        object2 = player4;
        player = object2;
        World.scheduleTickTask(new PostLoginSyncTask((Player)object2, 3, player));
        player4.fb.deactivateAll();
        player4.fe.d();
        player4.ff.d();
        boolean bl2 = false;
        player = this;
        this.gM = bl2;
        this.aj = this.isInWilderness();
        Object object3 = this;
        MusicManager.unlockTrack((Player)object3, 16);
        MusicManager.unlockTrack((Player)object3, 321);
        MusicManager.unlockTrack((Player)object3, 400);
        MusicManager.unlockTrack((Player)object3, 466);
        MusicManager.unlockTrack((Player)object3, 547);
        if (this.questStates[0] != 1) {
            player = this;
            player.ff.refreshQuestJournal();
            if (this.questStates[0] >= 45) {
                player = this;
                player.eZ.refreshWeaponInterface();
            }
        } else {
            player = this;
            player.packetSender.refreshSidebarInterfaces();
            player = this;
            player.eZ.refreshWeaponInterface();
        }
        if (this.getCurrentHitpoints() <= 0) {
            CombatManager.handleDeath(this);
        }
        CacheDefinitionIndex.scheduleRandomEventRoll(this);
        this.f(true);
        player = this;
        player.packetSender.sendInterfaceText("Total Lvl: " + this.fe.getTotalLevel(), 3984);
        if (this.getPoisonDamage() > 0.0) {
            object3 = new HitDefinition(null, HitType.POISON, Math.ceil(this.getPoisonDamage())).setDelay(30);
            object3 = new CombatAction(this, this, (HitDefinition)object3);
            object2 = new PoisonEffect(this.getPoisonDamage(), false);
            object2.a((CombatAction)object3);
        }
        int n4 = 0;
        while (n4 < 6) {
            if (this.dF[n4]) {
                GrandExchangeManager.a(this, n4);
            }
            this.dF[n4] = false;
            ++n4;
        }
        if (this.bU) {
            this.bl();
        }
        player = this;
        if (player.fn.g()) {
            player = this;
            player.fn.f();
        }
        if (this.de && this.az == 4 && this.ej > 0L) {
            this.a("bot", null, false);
        }
    }

    public final boolean br() {
        CharacterFileManager.loadPlayer(this);
        if (this.g()) {
            return true;
        }
        this.bn();
        Player player = this;
        if (LoginProtocol.a.contains(player.username)) {
            player = this;
            LoginProtocol.a.remove(player.username);
        }
        this.disconnect();
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean j(String string) {
        int n = 0;
        while (n < 41) {
            if (it[n].equals(string)) {
                return false;
            }
            ++n;
        }
        return true;
    }

    private static boolean k(String string) {
        string = string.toLowerCase();
        int n = 0;
        while (n < string.length()) {
            String string2 = "" + string.charAt(n);
            if (Player.j(string2)) {
                return false;
            }
            ++n;
        }
        return true;
    }

    public void f() {
    }

    private static boolean l(String string) {
        if (string.toLowerCase().startsWith("bot ")) {
            return true;
        }
        if (BotPlayer.botNamePool.contains(string.toLowerCase())) {
            return true;
        }
        return BotPlayer.defaultProgressiveBotNames.contains(string.toLowerCase());
    }

    private boolean g() {
        boolean bl = false;
        if (FileUtil.exists("./data/characters/" + this.username + ".dat")) {
            bl = true;
        } else {
            if (this.username.toLowerCase().startsWith("mod ") || this.username.toLowerCase().startsWith("admin ") || this.username.toLowerCase().startsWith("owner ") || this.username.contains("  ") || !Player.k(this.username) || Player.l(this.username) && !this.de) {
                int n = 3;
                Player player = this;
                this.gy = n;
                return false;
            }
            this.eg = System.currentTimeMillis();
            this.bK = 1234 + GameUtil.h(4321);
            if (this.de) {
                this.f();
            }
        }
        if (this.username.length() <= 0 || this.username.length() > 12 || this.submittedPassword.length() < 4 || this.submittedPassword.length() > 20 || !this.submittedPassword.equals(this.password) && bl) {
            int n = 3;
            Player player = this;
            this.gy = n;
            return false;
        }
        Object object = this;
        if (!((Player)object).hV.equals("127.0.0.1")) {
            object = this;
            if (!((Player)object).hV.startsWith("192.168.")) {
                object = this;
                if (!((Player)object).hV.startsWith("10.")) {
                    int n = 11;
                    object = this;
                    this.gy = n;
                    return false;
                }
            }
        }
        if (World.c() >= 5) {
            int n = 11;
            object = this;
            this.gy = n;
            return false;
        }
        Object object2 = this.submittedPassword;
        object = this;
        this.password = object2;
        object = this;
        if (((Player)object).playerRights > 1 || this.username.toLowerCase().startsWith("bot ")) {
            object = this;
            if (!((Player)object).hV.equals("127.0.0.1")) {
                int n = 3;
                object = this;
                this.gy = n;
                return false;
            }
        }
        if (Player.l(this.username) && !this.de) {
            int n = 3;
            object = this;
            this.gy = n;
            return false;
        }
        if (Server.b == 3) {
            int n = 14;
            object = this;
            this.gy = n;
            return false;
        }
        if (World.b() >= ServerSettings.maxPlayers) {
            int n = 7;
            object = this;
            this.gy = n;
            return false;
        }
        Player[] playerArray = World.f();
        int n = playerArray.length;
        int n2 = 0;
        while (n2 < n) {
            object2 = playerArray[n2];
            if (object2 != null) {
                Object object3 = object2;
                object = object3;
                object = this;
                if (((Player)object3).ht == ((Player)object).ht) {
                    int n3 = 5;
                    object = this;
                    this.gy = n3;
                    return false;
                }
                object = this;
                if (!((Player)object).hV.equals("127.0.0.1")) {
                    Object object4;
                    object = object2;
                    object = object2;
                    object = this;
                    if (((Player)object4).hV.equals(((Player)object).hV)) {
                        int n4 = 9;
                        object = this;
                        this.gy = n4;
                        return false;
                    }
                }
            }
            ++n2;
        }
        object2 = this;
        if (((Player)object2).eR != ServerSettings.clientBuild) {
            int n5 = 6;
            object = this;
            this.gy = n5;
            return false;
        }
        object2 = this;
        if (((Player)object2).eT != -1) {
            int n6 = 10;
            object = this;
            this.gy = n6;
            return false;
        }
        int n7 = 0;
        String[] stringArray = iu;
        int n8 = iu.length;
        n = 0;
        while (n < n8) {
            String string = stringArray[n];
            object = this;
            if (((Player)object).hV.equals(string)) {
                n7 = 1;
                break;
            }
            ++n;
        }
        if (this.eQ() || n7 != 0) {
            n7 = 4;
            object = this;
            this.gy = n7;
            return false;
        }
        if (!this.isMember() && !ServerSettings.freeToPlayWorld) {
            n7 = 12;
            object = this;
            this.gy = n7;
            return false;
        }
        object = this;
        if (!((Player)object).fU && !this.ef && ServerSettings.loginRestrictionMode == 1) {
            object = this;
            if (((Player)object).playerRights == 0) {
                if (bl) {
                    n7 = 12;
                    object = this;
                    this.gy = n7;
                } else {
                    n7 = 29;
                    object = this;
                    this.gy = n7;
                    CharacterFileManager.savePlayer(this);
                }
                return false;
            }
        }
        n7 = 2;
        object = this;
        this.gy = n7;
        return true;
    }

    @Override
    public final void heal(int n) {
        Player player = this;
        Player player2 = player;
        player2 = this;
        if (player.fe.getCurrentLevels()[3] + n <= player2.fe.getBaseLevel(3)) {
            player2 = this;
            int[] nArray = player2.fe.getCurrentLevels();
            nArray[3] = nArray[3] + n;
        } else {
            Player player3 = this;
            player2 = player3;
            player2 = this;
            player3.fe.getCurrentLevels()[3] = player2.fe.getBaseLevel(3);
        }
        player2 = this;
        player2.fe.refreshSkill(3);
    }

    public String toString() {
        Player player = this;
        if (player.username == null) {
            player = this;
            return "Client(" + player.hV + ")";
        }
        Player player2 = this;
        player = player2;
        Player player3 = this;
        player = player3;
        player = this;
        return "Player(" + player2.username + ":" + player3.password + " - " + player.hV + ")";
    }

    public final void c(String string) {
        if (this.de) {
            this.hV = string;
            return;
        }
        System.err.println("setHost method can only be used for bot accounts!");
    }

    public final String bs() {
        return this.hV;
    }

    public final Position bt() {
        return this.fS;
    }

    public final void e(boolean bl) {
        this.gD = bl;
    }

    public final boolean bu() {
        return this.gD;
    }

    public final InventoryManager getInventoryManager() {
        return this.eY;
    }

    public final void f(boolean bl) {
        if (bl) {
            this.getUpdateState().setUpdateRequired(true);
        }
        this.gF = bl;
    }

    public final boolean bw() {
        return this.gF;
    }

    public final void setPlayerRights(int n) {
        this.playerRights = n;
    }

    public final int getPlayerRights() {
        return this.playerRights;
    }

    public final void g(boolean bl) {
        this.gE = bl;
    }

    public final boolean by() {
        return this.gE;
    }

    public final void setPublicChatColor(int n) {
        this.publicChatColor = n;
    }

    public final int getPublicChatColor() {
        return this.publicChatColor;
    }

    public final void setPublicChatEffects(int n) {
        this.publicChatEffects = n;
    }

    public final int getPublicChatEffects() {
        return this.publicChatEffects;
    }

    public final void setPublicChatPayload(byte[] byArray) {
        this.publicChatPayload = byArray;
    }

    public final byte[] getPublicChatPayload() {
        return this.publicChatPayload;
    }

    public final void flagAppearanceUpdate(boolean bl) {
        this.getUpdateState().setUpdateRequired(true);
        this.getUpdateState().setAppearanceUpdateRequired(true);
    }

    public final int[] getAppearanceParts() {
        return this.appearanceParts;
    }

    public final int[] getAppearanceColors() {
        return this.appearanceColors;
    }

    public final void setGender(int n) {
        this.gender = n;
    }

    public final int getGender() {
        return this.gender;
    }

    public final List bF() {
        return this.eW;
    }

    public final void A(int n) {
        this.gy = n;
    }

    public final List bG() {
        return this.eX;
    }

    public final void setInteractionTargetX(int n) {
        this.interactionTargetX = n;
    }

    public final int getInteractionTargetX() {
        return this.interactionTargetX;
    }

    public final void setInteractionTargetY(int n) {
        this.interactionTargetY = n;
    }

    public final int getInteractionTargetY() {
        return this.interactionTargetY;
    }

    public final int getInteractionSpellButtonId() {
        return this.interactionSpellButtonId;
    }

    public final void setInteractionSpellButtonId(int n) {
        this.interactionSpellButtonId = n;
    }

    public final void setInteractionTargetId(int n) {
        this.interactionTargetId = n;
    }

    public final int getInteractionTargetId() {
        return this.interactionTargetId;
    }

    public final void setSelectedItemId(int n) {
        this.selectedItemId = n;
    }

    public final int getSelectedItemId() {
        return this.selectedItemId;
    }

    public final void setInteractionTargetIndex(int n) {
        this.interactionTargetIndex = n;
    }

    public final int getInteractionTargetIndex() {
        return this.interactionTargetIndex;
    }

    public final void setBankWithdrawNoteMode(boolean bl) {
        this.bankWithdrawNoteMode = bl;
    }

    public final boolean isBankWithdrawNoteMode() {
        return this.bankWithdrawNoteMode;
    }

    public final void setSelectedInterfaceItemId(int n) {
        this.selectedInterfaceItemId = n;
    }

    public final int getSelectedInterfaceItemId() {
        return this.selectedInterfaceItemId;
    }

    public final void setSelectedInterfaceSlot(int n) {
        this.selectedInterfaceSlot = n;
    }

    public final int getSelectedInterfaceSlot() {
        return this.selectedInterfaceSlot;
    }

    public final void setSelectedInterfaceId(int n) {
        this.selectedInterfaceId = n;
    }

    public final int getSelectedInterfaceId() {
        return this.selectedInterfaceId;
    }

    public final void setBankRearrangeMode(BankRearrangeMode bankRearrangeMode) {
        this.bankRearrangeMode = bankRearrangeMode;
    }

    public final BankRearrangeMode getBankRearrangeMode() {
        return this.bankRearrangeMode;
    }

    public final void K(int n) {
        this.gt = n;
    }

    public final int bS() {
        return this.gt;
    }

    public final ItemContainer getBankContainer() {
        return this.bankContainer;
    }

    public final void g(int n, int n2) {
        this.gv.put(n, n2);
    }

    public final Map bU() {
        return this.gv;
    }

    public final long[] getFriendsList() {
        return this.friendsList;
    }

    public final void j(boolean bl) {
        this.gu = false;
    }

    public final boolean bW() {
        return this.gu;
    }

    public final long[] getIgnoreList() {
        return this.ignoreList;
    }

    public final EquipmentManager getEquipmentManager() {
        return this.eZ;
    }

    public final SkillManager getSkillManager() {
        return this.fe;
    }

    public final QuestManager getQuestManager() {
        return this.ff;
    }

    public final RunecraftingObjectHandler getRunecraftingObjectHandler() {
        return this.fg;
    }

    public final String getInterfaceAction() {
        return this.interfaceAction;
    }

    public final PacketSender getPacketSender() {
        return this.packetSender;
    }

    public final SlayerManager getSlayerManager() {
        return this.fh;
    }

    public final AlchemistPlaygroundController getAlchemistPlaygroundController() {
        return this.fl;
    }

    public final CreatureGraveyardController getCreatureGraveyardController() {
        return this.fm;
    }

    public final TelekineticTheatreController getTelekineticTheatreController() {
        return this.fn;
    }

    public final EnchantmentChamberController getEnchantmentChamberController() {
        return this.fo;
    }

    public final DuelController getDuelController() {
        return this.fi;
    }

    public final DuelSession getDuelSession() {
        return this.fj;
    }

    public final FightCaveController getFightCaveController() {
        return this.fk;
    }

    public final DuelInterfaceManager getDuelInterfaceManager() {
        return this.fp;
    }

    public final DuelArenaLocationManager getDuelArenaLocationManager() {
        return this.fq;
    }

    public final Npc co() {
        return this.H;
    }

    public final WineFermentationHandler getWineFermentationHandler() {
        return this.fr;
    }

    public final CookingManager getCookingManager() {
        return this.fy;
    }

    public final ItemCombinationHandler getItemCombinationHandler() {
        return this.ft;
    }

    public final SkillGuideManager getSkillGuideManager() {
        return this.fu;
    }

    public final FoodHandler getFoodHandler() {
        return this.fv;
    }

    public final PotionHandler getPotionHandler() {
        return this.fw;
    }

    public final BankPinManager getBankPinManager() {
        return this.fQ;
    }

    public final DialogueManager getDialogueManager() {
        return this.fP;
    }

    public final FiremakingHandler getFiremakingHandler() {
        return this.fL;
    }

    public final MiningManager getMiningManager() {
        return this.fx;
    }

    public final CompostBinManager getCompostBinManager() {
        return this.fz;
    }

    public final AllotmentPatchManager getAllotmentPatchManager() {
        return this.fA;
    }

    public final FlowerPatchManager getFlowerPatchManager() {
        return this.fB;
    }

    public final HerbPatchManager getHerbPatchManager() {
        return this.fC;
    }

    public final HopsPatchManager getHopsPatchManager() {
        return this.fD;
    }

    public final BushPatchManager getBushPatchManager() {
        return this.fE;
    }

    public final PlantPotHandler getPlantPotHandler() {
        return this.fF;
    }

    public final TreePatchManager getTreePatchManager() {
        return this.fG;
    }

    public final FruitTreePatchManager getFruitTreePatchManager() {
        return this.fH;
    }

    public final SpecialTreePatchManager getSpecialTreePatchManager() {
        return this.fI;
    }

    public final SpecialCropPatchManager getSpecialCropPatchManager() {
        return this.fJ;
    }

    public final FarmingToolStore getFarmingToolStore() {
        return this.fK;
    }

    public final BoneBuryingHandler getBoneBuryingHandler() {
        return this.fM;
    }

    public final FishingHandler getFishingHandler() {
        return this.fs;
    }

    public final SandwichLadyManager getSandwichLadyManager() {
        return this.fO;
    }

    public final PetManager getPetManager() {
        return this.fN;
    }

    public final SocialManager getSocialManager() {
        return this.fa;
    }

    public final void a(TradeState tradeState) {
        this.gz = tradeState;
    }

    public final TradeState getTradeState() {
        return this.gz;
    }

    public final ItemContainer getTradeOfferContainer() {
        return this.gd;
    }

    public final ItemContainer getPartyRoomContainer() {
        return this.ge;
    }

    public final int[] cT() {
        return this.gA;
    }

    public final int[] cU() {
        return this.gB;
    }

    public final void setRunEnergyRaw(int n) {
        if (n < 0) {
            n = 0;
        }
        if (n > 10000) {
            n = 10000;
        }
        this.runEnergyRaw = n;
    }

    public final int getRunEnergyRaw() {
        return this.runEnergyRaw;
    }

    public final int getRunEnergyPercent() {
        double d = this.runEnergyRaw;
        double d2 = d / 10000.0 * 100.0;
        int n = (int)d2;
        return n;
    }

    public final void addRunEnergyRaw(int n) {
        Player player = this;
        this.setRunEnergyRaw(player.runEnergyRaw + n);
    }

    public final void addRunEnergyPercent(int n) {
        double d = n;
        double d2 = (d /= 100.0) * 10000.0;
        n = (int)d2;
        this.addRunEnergyRaw(n);
    }

    public final void setRunEnergyPercent(int n) {
        double d = n;
        double d2 = (d /= 100.0) * 10000.0;
        n = (int)d2;
        this.setRunEnergyRaw(n);
    }

    public final int cX() {
        int n = this.getCombatBonus(11);
        return 2 * n + 60;
    }

    public final ElapsedTimer getPacketReadTimer() {
        return this.packetReadTimer;
    }

    public final ByteBuffer getInboundBuffer() {
        return this.inboundBuffer;
    }

    public final SelectionKey getSelectionKey() {
        return this.selectionKey;
    }

    public final SocketChannel getSocketChannel() {
        return this.socketChannel;
    }

    public final void setOutboundCipher(IsaacCipher isaacCipher) {
        this.outboundCipher = isaacCipher;
    }

    public final IsaacCipher getOutboundCipher() {
        return this.outboundCipher;
    }

    public final void setInboundCipher(IsaacCipher isaacCipher) {
        this.inboundCipher = isaacCipher;
    }

    public final IsaacCipher getInboundCipher() {
        return this.inboundCipher;
    }

    public final void setConnectionState(PlayerConnectionState playerConnectionState) {
        this.connectionState = playerConnectionState;
    }

    public final PlayerConnectionState getConnectionState() {
        return this.connectionState;
    }

    public final LoginProtocol df() {
        return this.fR;
    }

    public final void setCurrentPacketOpcode(int n) {
        this.currentPacketOpcode = n;
    }

    public final int getCurrentPacketOpcode() {
        return this.currentPacketOpcode;
    }

    public final int getCurrentPacketLength() {
        return this.currentPacketLength;
    }

    public final void setCurrentPacketLength(int n) {
        this.currentPacketLength = n;
    }

    public final void setUsername(String string) {
        this.username = string;
    }

    public final String getUsername() {
        return this.username;
    }

    public final void setSubmittedPassword(String string) {
        this.submittedPassword = string;
    }

    public final void setPassword(String string) {
        this.password = string;
    }

    public final String getPassword() {
        return this.password;
    }

    public final void R(int n) {
        this.gG = n;
    }

    public final int dk() {
        return this.gG;
    }

    public final String dl() {
        return this.ij;
    }

    public final void g(String string) {
        this.ij = string;
    }

    public final void S(int n) {
        this.gI -= n;
    }

    public final void T(int n) {
        this.gI = n;
    }

    public final int dm() {
        return this.gI;
    }

    public final int dn() {
        return this.gH;
    }

    public final boolean[] cfr_renamed_0() {
        return this.gJ;
    }

    public final PrayerManager getPrayerManager() {
        return this.fb;
    }

    public final TeleportManager getTeleportManager() {
        return this.fc;
    }

    public final EmoteManager getEmoteManager() {
        return this.fd;
    }

    public final boolean isAutoRetaliate() {
        return this.autoRetaliate;
    }

    public final void setAutoRetaliate(boolean bl) {
        this.autoRetaliate = bl;
    }

    public final int getBrightness() {
        return this.brightness;
    }

    public final void setBrightness(int n) {
        this.brightness = n;
    }

    public final int getMouseButtons() {
        return this.mouseButtons;
    }

    public final void setMouseButtons(int n) {
        this.mouseButtons = n;
    }

    public final int getSplitPrivateChat() {
        return this.splitPrivateChat;
    }

    public final void setSplitPrivateChat(int n) {
        this.splitPrivateChat = n;
    }

    public final boolean isAcceptAidEnabled() {
        return this.acceptAid == 0;
    }

    public final int getAcceptAid() {
        return this.acceptAid;
    }

    public final void setAcceptAid(int n) {
        this.acceptAid = n;
    }

    public final int getMusicVolume() {
        return this.musicVolume;
    }

    public final void setMusicVolume(int n) {
        this.musicVolume = n;
    }

    public final int getEffectVolume() {
        return this.effectVolume;
    }

    public final void setEffectVolume(int n) {
        this.effectVolume = n;
    }

    public final int dA() {
        int n = 0;
        int n2 = 1;
        while (n2 < QuestDefinition.a) {
            QuestScript questScript = QuestDefinition.a(n2);
            if (this.questStates[n2] == 1) {
                n += questScript.a();
            }
            ++n2;
        }
        this.gX = n;
        return this.gX;
    }

    public final void setSpellbook(Spellbook spellbook) {
        if (ServerSettings.cacheVersion < 308) {
            spellbook = Spellbook.MODERN;
        }
        this.spellbook = spellbook;
    }

    public final Spellbook getSpellbook() {
        return this.spellbook;
    }

    public final void a(PlayerPlugin playerPlugin) {
        this.hm.add(playerPlugin);
    }

    public final void setSpecialEnergy(int n) {
        if (this.dP) {
            this.specialEnergy = 100;
            return;
        }
        this.specialEnergy = n;
        if (this.specialEnergy > 100) {
            this.specialEnergy = 100;
        }
    }

    public final int getSpecialEnergy() {
        return this.specialEnergy;
    }

    public final boolean isSpecialAttackEnabled() {
        return this.specialAttackEnabled;
    }

    public final void setSpecialAttackEnabled(boolean bl) {
        this.specialAttackEnabled = bl;
    }

    public final void setRingOfRecoilLife(int n) {
        this.ringOfRecoilLife = n;
    }

    public final int getRingOfRecoilLife() {
        return this.ringOfRecoilLife;
    }

    public final void setRingOfForgingLife(int n) {
        this.ringOfForgingLife = n;
    }

    public final int getRingOfForgingLife() {
        return this.ringOfForgingLife;
    }

    public final int getBindingNecklaceCharge() {
        return this.bindingNecklaceCharge;
    }

    public final void setBindingNecklaceCharge(int n) {
        this.bindingNecklaceCharge = n;
    }

    public final void setFightMode(int n) {
        this.fightMode = n;
    }

    public final int getFightMode() {
        return this.fightMode;
    }

    public final void m(boolean bl) {
        this.he = bl;
    }

    public final boolean dI() {
        return this.he;
    }

    public final void n(boolean bl) {
        this.gM = bl;
    }

    public final boolean dJ() {
        if (this.ak == 2626 || this.ak >= 3689 && this.ak <= 3694) {
            return true;
        }
        return this.gM;
    }

    public final void o(boolean bl) {
        this.hf = bl;
    }

    public final boolean dK() {
        return this.hf;
    }

    public final void p(boolean bl) {
        this.hg = bl;
    }

    public final boolean dL() {
        return this.hg;
    }

    public final void q(boolean bl) {
        this.hh = bl;
    }

    public final boolean dM() {
        return this.hh;
    }

    public final void r(boolean bl) {
        this.hi = bl;
    }

    public final boolean dN() {
        return this.hi;
    }

    public final void s(boolean bl) {
        this.hj = bl;
    }

    public final boolean dO() {
        return this.hj;
    }

    public final void t(boolean bl) {
        this.hk = bl;
    }

    public final boolean dP() {
        return this.hk;
    }

    public final void u(boolean bl) {
        this.hl = bl;
    }

    public final boolean dQ() {
        return this.hl;
    }

    public final void a(long l) {
        this.hn = l;
    }

    public final long dR() {
        return this.hn;
    }

    @Override
    public final int getCurrentHitpoints() {
        return this.fe.getCurrentLevels()[3];
    }

    @Override
    public final int getMaxHitpoints() {
        return this.fe.getBaseLevel(3);
    }

    @Override
    public final int getDeathAnimationId() {
        if (ServerSettings.cacheVersion < 327) {
            return 836;
        }
        return 2304;
    }

    @Override
    public final int getBlockAnimationId() {
        Object object = this.eZ.getContainer().getItemAt(5);
        if (object != null && ((String)(object = ItemDefinition.forId(((ItemStack)object).getId()).getName().toLowerCase())).contains("shield")) {
            return 1156;
        }
        return this.hu.getBlockAnimationId();
    }

    @Override
    public final int getDeathDelayTicks() {
        return 6;
    }

    @Override
    public final int getAttackLevelFor(CombatType combatType) {
        if (combatType == CombatType.RANGED) {
            return this.fe.getCurrentLevels()[4];
        }
        if (combatType == CombatType.MAGIC) {
            return this.fe.getCurrentLevels()[6];
        }
        return this.fe.getCurrentLevels()[0];
    }

    @Override
    public final int getDefenceLevelFor(CombatType combatType) {
        if (combatType == CombatType.MAGIC) {
            return this.fe.getCurrentLevels()[6];
        }
        return this.fe.getCurrentLevels()[1];
    }

    @Override
    public final boolean isProtectedFrom(CombatType combatType) {
        if (combatType == CombatType.MELEE) {
            return this.gJ[14];
        }
        if (combatType == CombatType.RANGED) {
            return this.gJ[13];
        }
        if (combatType == CombatType.MAGIC) {
            return this.gJ[12];
        }
        return false;
    }

    @Override
    public final void setCurrentHitpoints(int n) {
        this.fe.setCurrentLevel(3, n);
        this.fe.refreshSkill(3);
    }

    public final long dS() {
        return this.ht;
    }

    public final void b(long l) {
        this.ht = l;
    }

    public final void af(int n) {
        this.ho = n;
    }

    public final int dT() {
        return this.ho;
    }

    public final void a(SpecialAttackDefinition specialAttackDefinition) {
        this.hv = specialAttackDefinition;
    }

    public final SpecialAttackDefinition dU() {
        return this.hv;
    }

    public final void setWeaponProfile(WeaponProfile weaponProfile) {
        if (weaponProfile == null) {
            weaponProfile = WeaponProfile.FISTS;
        }
        this.hu = weaponProfile;
    }

    public final WeaponProfile getWeaponProfile() {
        return this.hu;
    }

    public final int dW() {
        int n = 405;
        Player player = this;
        int n2 = player.eZ.getItemIdAtSlot(4);
        String string = new ItemStack(n2).getDefinition().getName().toLowerCase();
        if (string.contains("platebody")) {
            n = 410;
        }
        if (string.contains("chainbody")) {
            n = 414;
        }
        return n;
    }

    public final boolean handleSpecialAttackButton(int n) {
        Player player;
        switch (n) {
            case 7487: {
                player = this;
                if (player.eZ.getItemIdAtSlot(3) != 1377) break;
                SpecialAttackDefinition.performDragonBattleaxeSpecial(this);
                return true;
            }
            case 7587: {
                player = this;
                if (player.eZ.getItemIdAtSlot(3) != 35) break;
                SpecialAttackDefinition.performExcaliburSpecial(this);
                return true;
            }
            case 7462: {
                player = this;
                if (player.eZ.getItemIdAtSlot(3) != 4153) break;
                if (this.getCombatTarget() == null) {
                    player = this;
                    player.packetSender.sendGameMessage("You can only use this special when attacking something.");
                    return true;
                }
                SpecialAttackDefinition.performGraniteMaulSpecial(this);
                return true;
            }
        }
        boolean bl = this.specialAttackEnabled;
        if (this.hu.getInterfaceDefinition().getSpecialAttackButtonId() != n) {
            return false;
        }
        player = this;
        SpecialAttackDefinition specialAttackDefinition = player.hv;
        if (specialAttackDefinition != null) {
            player = this;
            if (player.specialEnergy < specialAttackDefinition.getEnergyCost()) {
                player = this;
                player.packetSender.sendGameMessage("You don't have enough special energy to do that.");
                return true;
            }
        }
        boolean bl2 = !this.specialAttackEnabled;
        player = this;
        this.specialAttackEnabled = bl2;
        if (this.specialAttackEnabled && specialAttackDefinition == null) {
            bl2 = false;
            player = this;
            this.specialAttackEnabled = bl2;
        }
        if (this.specialAttackEnabled != bl) {
            this.refreshSpecialAttackWidgets();
        }
        return true;
    }

    public final void refreshSpecialAttackWidgets() {
        if (this.hu.getInterfaceDefinition().getSpecialBarWidgetId() <= 0) {
            return;
        }
        this.packetSender.refreshSpecialEnergyBar(this.hu.getInterfaceDefinition().getSpecialEnergyWidgetId());
        this.hu.getInterfaceDefinition().getSpecialEnergyWidgetId();
        this.packetSender.refreshSpecialAttackConfig();
    }

    public final void dY() {
        this.hw.clear();
        this.B(false);
    }

    public final int dZ() {
        int n = this.hw.size();
        if (n == 0) {
            return 0;
        }
        n = 0;
        Iterator iterator = this.hw.iterator();
        while (iterator.hasNext()) {
            int n2 = ((PvpCombatReference)iterator.next()).d();
            if (n2 <= n) continue;
            n = n2;
        }
        return n;
    }

    public final void b(Player object, int n) {
        object = new PvpCombatReference((Entity)object, n);
        this.hw.add(object);
        this.B(true);
    }

    public final void a(Player player) {
        boolean bl;
        block5: {
            if (!player.isPlayer()) {
                return;
            }
            Object object2 = player;
            if (this.isInDuelArena()) {
                return;
            }
            Player player2 = this;
            for (Object object2 : ((Player)object2).hw) {
                if (object2.a() != player2) continue;
                bl = true;
                break block5;
            }
            bl = false;
        }
        if (bl) {
            return;
        }
        for (Object object2 : this.hw) {
            if (object2.a() != player) continue;
            ((PvpCombatReference)object2).c();
            return;
        }
        this.b(player, 2000);
    }

    private void B(boolean bl) {
        this.F = bl;
        int n = bl ? 0 : -1;
        Player player = this;
        this.gH = n;
        this.f(true);
    }

    public final ArrayList a(ItemStack[] object) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        PriorityQueue<ItemStack> priorityQueue = new PriorityQueue<ItemStack>(1, new ProtectedItemValueComparator(this));
        ItemStack[] itemStackArray = object;
        int n = ((ItemStack[])object).length;
        int n2 = 0;
        while (n2 < n) {
            object = itemStackArray[n2];
            if (!(object == null || ((ItemStack)object).getDefinition().z() && ((ItemStack)object).getDefinition().n() == 1)) {
                priorityQueue.add(new ItemStack(((ItemStack)object).getId()));
                arrayList.add(new ItemStack(((ItemStack)object).getId()));
            }
            ++n2;
        }
        object = new ArrayList();
        int n3 = n2 = this.F ? 0 : 3;
        if (this.gJ[8]) {
            ++n2;
        }
        if (this.gameMode == 2) {
            n2 = 0;
        }
        while (((ArrayList)object).size() < n2 && priorityQueue.size() > 0) {
            ItemStack itemStack = (ItemStack)priorityQueue.poll();
            ((ArrayList)object).add(itemStack);
            arrayList.remove(itemStack);
        }
        return arrayList;
    }

    public final void ea() {
        if (this.questStates[0] == 1) {
            return;
        }
        this.questStates[0] = this.questStates[0] + 1;
        Player player = this;
        player.ff.refreshQuestJournal();
    }

    public final void eb() {
        this.questStates[0] = 68;
        Player player = this;
        player.ff.refreshQuestJournal();
    }

    @Override
    public final void dropDeathItems(Entity entity) {
        Object object = this;
        if (this.playerRights >= 2 || this.isInDuelArena() || this.fm.a() || this.isInFightCave()) {
            return;
        }
        if (entity == null || !(entity instanceof Player)) {
            entity = this;
        }
        object = new ItemStack[this.eZ.getContainer().g() + this.eY.getContainer().g()];
        System.arraycopy(this.eZ.getContainer().getItems(), 0, object, 0, this.eZ.getContainer().getItems().length);
        System.arraycopy(this.eY.getContainer().getItems(), 0, object, this.eZ.getContainer().getItems().length, this.eY.getContainer().getItems().length);
        Object object2 = object;
        Object object3 = this;
        Object object4 = new PriorityQueue<ItemStack>(1, new DeathItemValueComparator((Player)object3));
        Object object5 = object2;
        int n = ((ItemStack[])object2).length;
        int n2 = 0;
        while (n2 < n) {
            object2 = object5[n2];
            if (!(object2 == null || ((ItemStack)object2).getDefinition().z() && ((ItemStack)object2).getDefinition().n() == 1)) {
                ((PriorityQueue)object4).add(new ItemStack(((ItemStack)object2).getId(), ((ItemStack)object2).getAmount(), ((ItemStack)object2).getMetadata()));
            }
            ++n2;
        }
        object2 = new ArrayList();
        int n3 = n2 = ((Player)object3).F ? 0 : 3;
        if (((Player)object3).gJ[8]) {
            ++n2;
        }
        if (((Player)object3).gameMode == 2) {
            n2 = 0;
        }
        n = 0;
        while (n < n2 && ((PriorityQueue)object4).size() > 0) {
            object5 = (ItemStack)((PriorityQueue)object4).poll();
            int n4 = n2 - n;
            if (((ItemStack)object5).getAmount() < n4) {
                n4 = ((ItemStack)object5).getAmount();
            }
            n += n4;
            ((ItemStack)object5).setAmount(n4);
            ((ArrayList)object2).add(object5);
        }
        object3 = object2;
        object = new ArrayList<ItemStack>(Arrays.asList(object));
        object4 = ((ArrayList)object3).iterator();
        block2: while (object4.hasNext()) {
            object2 = (ItemStack)object4.next();
            if (object2 == null) continue;
            Iterator iterator = object.iterator();
            while (iterator.hasNext()) {
                ItemStack itemStack = (ItemStack)iterator.next();
                if (itemStack == null || itemStack.getId() != ((ItemStack)object2).getId()) continue;
                itemStack.setAmount(itemStack.getAmount() - ((ItemStack)object2).getAmount());
                if (itemStack.getAmount() > 0) continue block2;
                iterator.remove();
                continue block2;
            }
        }
        this.eZ.getContainer().clear();
        this.eY.getContainer().clear();
        object2 = entity;
        if (entity.isPlayer() && entity != this) {
            object4 = (Player)entity;
            if (((Player)object4).gameMode != 0) {
                ((Player)object4).packetSender.sendGameMessage("You are not playing on normal gamemode and cannot receive the loot.");
                object2 = this;
            }
        }
        Object object6 = ((ArrayList)object3).iterator();
        while (object6.hasNext()) {
            object4 = (ItemStack)object6.next();
            this.eY.addItem((ItemStack)object4);
        }
        object6 = object.iterator();
        while (object6.hasNext()) {
            object4 = (ItemStack)object6.next();
            if (object4 == null) continue;
            if (((ItemStack)object4).getDefinition().getName().toLowerCase().contains("clue scroll")) {
                this.ar = 0;
            }
            BarrowsRepairHandler barrowsRepairHandler = BarrowsRepairHandler.b((ItemStack)object4);
            if (((ItemStack)object4).getDefinition().z() && barrowsRepairHandler == null) continue;
            object = new ItemStack(((ItemStack)object4).getId(), ((ItemStack)object4).getAmount());
            if (barrowsRepairHandler != null) {
                object = new ItemStack(barrowsRepairHandler.a(), 1);
            }
            object = new GroundItem((ItemStack)object, (Entity)this, (Entity)object2, this.getDeathPosition());
            GroundItemManager.getInstance().spawn((GroundItem)object);
            if (!entity.isPlayer() || entity == this) continue;
            object3 = (Player)entity;
            if (!((Player)object3).botEnabled) continue;
            ((Player)object3).botLootGroundItems.add(object);
        }
        if (entity.isPlayer() && entity != this) {
            object4 = (Player)entity;
            if (((Player)object4).botEnabled) {
                if (((Player)object4).botLootGroundItems.size() > 0) {
                    ((Player)object4).botCombatState = "loot items";
                    BotCombatHelper.processBotLootQueue((Player)object4);
                } else {
                    ((Player)object4).botCombatState = null;
                }
            }
        }
        boolean bl = false;
        if (this.gameMode == 3) {
            this.gameMode = 1;
            this.packetSender.sendAccountStatus();
            bl = true;
            this.packetSender.sendGameMessage("You have fallen as a Hardcore Iron Man, your Hardcore status has been revoked.");
        }
        CharacterFileManager.savePlayer((Player)this);
        if (bl) {
            CharacterFileManager.archiveDeadHardcoreIronman((Player)this);
        }
        if (entity != null && this.getDeathPosition() != null) {
            object6 = new GroundItem(new ItemStack(526, 1), (Entity)this, entity, this.getDeathPosition());
            GroundItemManager.getInstance().spawn((GroundItem)object6);
        }
        this.eZ.refresh();
        this.eY.refresh();
    }

    public final SpellDefinition ec() {
        return this.hx;
    }

    public final SpellDefinition ed() {
        return this.hy;
    }

    public final void a(SpellDefinition spellDefinition) {
        this.hx = spellDefinition;
    }

    public final boolean ee() {
        return this.hz;
    }

    public final void v(boolean bl) {
        if (bl) {
            Player player = this;
            player.packetSender.sendConfig(108, 3);
            player = this;
            player.packetSender.sendConfig(43, 3);
        } else {
            Player player = this;
            player.packetSender.refreshAutocastConfig();
        }
        this.hz = bl;
    }

    public final void b(SpellDefinition spellDefinition) {
        if (spellDefinition == null) {
            Player player = this;
            player.packetSender.refreshAutocastConfig();
            this.hz = false;
        } else {
            Object object = this;
            ((Player)object).packetSender.setSidebarInterface(0, 328);
            object = this;
            Object object2 = spellDefinition;
            object = ((Player)object).packetSender;
            object2 = TextUtil.capitalizeFirst(object2.name().toLowerCase().replaceAll("_", " "));
            ((PacketSender)object).sendInterfaceText((String)object2, 352);
            ((PacketSender)object).sendConfig(108, 3);
            ((PacketSender)object).sendConfig(43, 3);
            this.hz = true;
        }
        this.hy = spellDefinition;
    }

    public final void ef() {
        Player player = this;
        player.packetSender.sendConfig(108, 2);
        this.hz = false;
    }

    public final void w(boolean bl) {
        this.fU = bl;
    }

    public final boolean eg() {
        return this.fU;
    }

    public final ByteBuffer getOutboundBuffer() {
        return this.outboundBuffer;
    }

    public final void setInterfaceAction(String string) {
        this.interfaceAction = string;
    }

    public final void ah(int n) {
        this.hR = n;
    }

    public final int ei() {
        return this.hR;
    }

    public final void ai(int n) {
        this.hp = n;
    }

    public final int ej() {
        return this.hp;
    }

    public final void a(SmithingBarDefinition smithingBarDefinition) {
        this.hr = smithingBarDefinition;
    }

    public final SmithingBarDefinition ek() {
        return this.hr;
    }

    public final int el() {
        if (this.hT == -1) {
            Player player = this;
            return player.eZ.getStandAnimation();
        }
        return this.hT;
    }

    public final int em() {
        if (this.hU == -1) {
            Player player = this;
            return player.eZ.getWalkAnimation();
        }
        return this.hU;
    }

    public final int en() {
        if (this.hS == -1) {
            Player player = this;
            return player.eZ.getRunAnimation();
        }
        return this.hS;
    }

    public final void aj(int n) {
        this.hS = n;
    }

    public final void ak(int n) {
        this.hU = n;
    }

    public final void al(int n) {
        this.hT = n;
    }

    public final void setMuteExpires(long l) {
        this.muteExpires = l;
    }

    public final void setBanExpires(long l) {
        this.banExpires = l;
    }

    public final long getMuteExpires() {
        return this.muteExpires;
    }

    public final long getBanExpires() {
        return this.banExpires;
    }

    public final void eq() {
        Object object = this;
        ((Player)object).hw.clear();
        this.B(false);
        Player player = this;
        player.fb.deactivateAll();
        this.setRunEnergyPercent(100);
        this.setSpecialEnergy(100);
        this.refreshSpecialAttackWidgets();
        this.clearNegativeStatusTimers();
        this.clearImmunityTimers();
        player = this;
        object = player.fe.getCurrentLevels();
        int n = 0;
        while (n < ((Object)object).length) {
            Player player2 = this;
            player = player2;
            player = this;
            player2.fe.setCurrentLevel(n, player.fe.getBaseLevel(n));
            ++n;
        }
    }

    public final boolean isInterfaceOpen(InterfaceDefinition interfaceDefinition) {
        if (interfaceDefinition == null) {
            return false;
        }
        Player player = this;
        if (player.openInterfaceId == interfaceDefinition.getParentInterfaceId()) {
            return true;
        }
        if (this.inventoryOverlayInterfaceId == interfaceDefinition.getParentInterfaceId()) {
            return true;
        }
        int[] nArray = this.sidebarInterfaceIds;
        int n = this.sidebarInterfaceIds.length;
        int n2 = 0;
        while (n2 < n) {
            int n3 = nArray[n2];
            if (n3 == interfaceDefinition.getParentInterfaceId()) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public final void i(String string) {
        Object object = "./data/errors.txt";
        try {
            object = new BufferedWriter(new FileWriter((String)object, true));
            try {
                Player player = this;
                ((Writer)object).write("[" + player.username + "] " + string);
                ((BufferedWriter)object).newLine();
            }
            finally {
                ((BufferedWriter)object).close();
            }
            return;
        }
        catch (IOException iOException) {
            object = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public final void er() {
        this.invalidateInterruptibleAction();
        this.nextActionSequence();
        Player player = this;
        int n = -1;
        Player player2 = player;
        player.selectedItemInterfaceId = n;
        n = -1;
        player2 = player;
        player.interactionTargetId = n;
        n = -1;
        player2 = player;
        player.interactionTargetY = n;
        n = -1;
        player2 = player;
        player.selectedItemSlot = n;
        n = -1;
        player2 = player;
        player.interactionTargetX = n;
        n = -1;
        player2 = player;
        player.interactionTargetPlane = n;
        n = -1;
        player2 = player;
        player.selectedItemId = n;
        EntityTargetMovement.clearMovementTarget(this);
        if (this.getInteractionTarget() != null && this.getInteractionTarget().isNpc()) {
            this.getInteractionTarget().setInteractionTarget(null);
        }
        this.setInteractionTarget(null);
        CombatManager.stopCombat(this);
        player2 = this;
        player2.fP.finishDialogue();
        if (this.getUpdateState().getFaceEntityId() != 65535) {
            this.getUpdateState().setFaceEntity(65535);
        }
    }

    public final boolean es() {
        if (iw == null) {
            iw = new Polygon();
            iw.addPoint(2600, 3281);
            iw.addPoint(2600, 3279);
            iw.addPoint(2598, 3277);
            iw.addPoint(2598, 3275);
            iw.addPoint(2599, 3274);
            iw.addPoint(2602, 3276);
            iw.addPoint(2604, 3276);
            iw.addPoint(2606, 3278);
            iw.addPoint(2606, 3280);
            iw.addPoint(2604, 3282);
            iw.addPoint(2601, 3282);
        }
        return iw.contains(this.getPosition().getX(), this.getPosition().getY());
    }

    public final boolean et() {
        return this.hA;
    }

    public final void x(boolean bl) {
        this.hA = bl;
    }

    public final boolean eu() {
        return this.hQ;
    }

    public final boolean ev() {
        Player player = this;
        if (player.eZ.getItemIdAtSlot(9) == 8842) {
            player = this;
            if (player.eZ.getItemIdAtSlot(7) == 8840) {
                player = this;
                if (player.eZ.getItemIdAtSlot(4) == 8839) {
                    player = this;
                    if (player.eZ.getItemIdAtSlot(0) == 11663) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final boolean ew() {
        Player player = this;
        if (player.eZ.getItemIdAtSlot(9) == 8842) {
            player = this;
            if (player.eZ.getItemIdAtSlot(7) == 8840) {
                player = this;
                if (player.eZ.getItemIdAtSlot(4) == 8839) {
                    player = this;
                    if (player.eZ.getItemIdAtSlot(0) == 11664) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final boolean ex() {
        Player player = this;
        if (player.eZ.getItemIdAtSlot(9) == 8842) {
            player = this;
            if (player.eZ.getItemIdAtSlot(7) == 8840) {
                player = this;
                if (player.eZ.getItemIdAtSlot(4) == 8839) {
                    player = this;
                    if (player.eZ.getItemIdAtSlot(0) == 11665) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final int ey() {
        boolean bl;
        boolean bl2;
        boolean bl3;
        int n;
        block13: {
            block12: {
                n = 0;
                bl3 = false;
                bl2 = false;
                bl = false;
                if (this.isAntifireActive()) {
                    bl3 = true;
                }
                Player player = this;
                if (player.gJ[12]) {
                    bl2 = true;
                }
                player = this;
                if (player.eZ.getItemIdAtSlot(5) == 1540) break block12;
                player = this;
                if (player.eZ.getItemIdAtSlot(5) == 11283) break block12;
                player = this;
                if (player.eZ.getItemIdAtSlot(5) != 11284) break block13;
            }
            bl = true;
        }
        if (bl3 && !bl2 && !bl) {
            n = 1;
        }
        if (!bl3 && bl2 && !bl) {
            n = 2;
        }
        if (!bl3 && !bl2 && bl) {
            n = 3;
        }
        if (bl3 && bl2 && !bl) {
            n = 4;
        }
        if (bl3 && !bl2 && bl) {
            n = 5;
        }
        if (!bl3 && bl2 && bl) {
            n = 6;
        }
        if (bl3 && bl2 && bl) {
            n = 7;
        }
        return n;
    }

    public final boolean ez() {
        Player player = this;
        if (player.eZ.getItemIdAtSlot(3) == 4024) {
            player = this;
            if (player.eZ.getItemIdAtSlot(2) == 4021) {
                return true;
            }
        }
        return false;
    }

    public final int am(int n) {
        return this.fY[n];
    }

    public final void h(int n, int n2) {
        this.fY[n] = n2;
    }

    public final void an(int n) {
        this.eR = n;
    }

    public final void ao(int n) {
        this.eT = n;
    }

    public final void ap(int n) {
        this.hs = n;
    }

    public final int eA() {
        return this.hs;
    }

    public final boolean eB() {
        int n;
        int n2;
        int[] nArray;
        Object object = this;
        ItemStack[] itemStackArray = ((Player)object).eY.getContainer().getItems();
        int n3 = itemStackArray.length;
        int n4 = 0;
        while (n4 < n3) {
            object = itemStackArray[n4];
            if (object != null && ((ItemStack)object).getDefinition().getEquipmentSlot() != 2 && ((ItemStack)object).getDefinition().getEquipmentSlot() != 12 && ((ItemStack)object).getDefinition().getEquipmentSlot() != 13) {
                nArray = ((ItemStack)object).getDefinition().getBonuses();
                n2 = nArray.length;
                n = 0;
                while (n < n2) {
                    int n5 = nArray[n];
                    if (n5 > 0) {
                        return true;
                    }
                    ++n;
                }
            }
            ++n4;
        }
        object = this;
        itemStackArray = ((Player)object).eZ.getContainer().getItems();
        n3 = itemStackArray.length;
        n4 = 0;
        while (n4 < n3) {
            ItemStack itemStack = itemStackArray[n4];
            if (itemStack != null && itemStack.getDefinition().getEquipmentSlot() != 2 && itemStack.getDefinition().getEquipmentSlot() != 12 && itemStack.getDefinition().getEquipmentSlot() != 13) {
                nArray = itemStack.getDefinition().getBonuses();
                n2 = nArray.length;
                n = 0;
                while (n < n2) {
                    int n6 = nArray[n];
                    if (n6 > 0) {
                        return true;
                    }
                    ++n;
                }
            }
            ++n4;
        }
        return false;
    }

    public final boolean eC() {
        return BankManager.depositInventoryAndEquipment(this);
    }

    public final RectangularArea eD() {
        return this.hB;
    }

    public final void eE() {
        Object object = this;
        ((Entity)object).getPosition();
        int n = Position.updateLocalX((Player)object);
        ((Entity)object).getPosition();
        int n2 = Position.updateLocalY((Player)object);
        n = ((Entity)object).getPosition().getX() - n;
        n2 = ((Entity)object).getPosition().getY() - n2;
        object = new Position(n, n2, ((Entity)object).getPosition().getPlane());
        this.hB = RectangularArea.fromPositionOffset((Position)object, 104, 104);
    }

    public final boolean aq(int n) {
        Object object = this;
        ItemStack[] itemStackArray = ((Player)object).eY.getContainer().getItems();
        int n2 = itemStackArray.length;
        int n3 = 0;
        while (n3 < n2) {
            object = itemStackArray[n3];
            if (object != null && ((ItemStack)object).getId() == n) {
                return true;
            }
            ++n3;
        }
        object = this;
        itemStackArray = ((Player)object).bankContainer.getItems();
        n2 = itemStackArray.length;
        n3 = 0;
        while (n3 < n2) {
            object = itemStackArray[n3];
            if (object != null && ((ItemStack)object).getId() == n) {
                return true;
            }
            ++n3;
        }
        object = this;
        itemStackArray = ((Player)object).eZ.getContainer().getItems();
        n2 = itemStackArray.length;
        n3 = 0;
        while (n3 < n2) {
            object = itemStackArray[n3];
            if (object != null && ((ItemStack)object).getId() == n) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    public final boolean i(int n, int n2) {
        int n3 = 0;
        Object object = this;
        ItemStack[] itemStackArray = ((Player)object).eY.getContainer().getItems();
        int n4 = itemStackArray.length;
        int n5 = 0;
        while (n5 < n4) {
            object = itemStackArray[n5];
            if (object != null && ((ItemStack)object).getId() == n) {
                n3 += ((ItemStack)object).getAmount();
            }
            ++n5;
        }
        object = this;
        itemStackArray = ((Player)object).bankContainer.getItems();
        n4 = itemStackArray.length;
        n5 = 0;
        while (n5 < n4) {
            object = itemStackArray[n5];
            if (object != null && ((ItemStack)object).getId() == n) {
                n3 += ((ItemStack)object).getAmount();
            }
            ++n5;
        }
        object = this;
        itemStackArray = ((Player)object).eZ.getContainer().getItems();
        n4 = itemStackArray.length;
        n5 = 0;
        while (n5 < n4) {
            object = itemStackArray[n5];
            if (object != null && ((ItemStack)object).getId() == n) {
                n3 += ((ItemStack)object).getAmount();
            }
            ++n5;
        }
        return n3 >= n2;
    }

    public final int ar(int n) {
        int n2 = 0;
        Object object = this;
        ItemStack[] itemStackArray = ((Player)object).eY.getContainer().getItems();
        int n3 = itemStackArray.length;
        int n4 = 0;
        while (n4 < n3) {
            object = itemStackArray[n4];
            if (object != null && ((ItemStack)object).getId() == n) {
                n2 += ((ItemStack)object).getAmount();
            }
            ++n4;
        }
        object = this;
        itemStackArray = ((Player)object).bankContainer.getItems();
        n3 = itemStackArray.length;
        n4 = 0;
        while (n4 < n3) {
            object = itemStackArray[n4];
            if (object != null && ((ItemStack)object).getId() == n) {
                n2 += ((ItemStack)object).getAmount();
            }
            ++n4;
        }
        object = this;
        itemStackArray = ((Player)object).eZ.getContainer().getItems();
        n3 = itemStackArray.length;
        n4 = 0;
        while (n4 < n3) {
            object = itemStackArray[n4];
            if (object != null && ((ItemStack)object).getId() == n) {
                n2 += ((ItemStack)object).getAmount();
            }
            ++n4;
        }
        return n2;
    }

    public final void eF() {
        Player player = this;
        this.fn.a = 0;
        player = this;
        this.fo.a = 0;
        player = this;
        this.fl.a = 0;
        player = this;
        this.fm.a = 0;
    }

    public final boolean eG() {
        Object object = this;
        ItemStack[] itemStackArray = ((Player)object).eY.getContainer().getItems();
        int n = itemStackArray.length;
        int n2 = 0;
        while (n2 < n) {
            object = itemStackArray[n2];
            if (object != null && ((ItemStack)object).getDefinition().getName().toLowerCase().contains("progress hat")) {
                return true;
            }
            ++n2;
        }
        object = this;
        itemStackArray = ((Player)object).bankContainer.getItems();
        n = itemStackArray.length;
        n2 = 0;
        while (n2 < n) {
            object = itemStackArray[n2];
            if (object != null && ((ItemStack)object).getDefinition().getName().toLowerCase().contains("progress hat")) {
                return true;
            }
            ++n2;
        }
        object = this;
        itemStackArray = ((Player)object).eZ.getContainer().getItems();
        n = itemStackArray.length;
        n2 = 0;
        while (n2 < n) {
            object = itemStackArray[n2];
            if (object != null && ((ItemStack)object).getDefinition().getName().toLowerCase().contains("progress hat")) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public final boolean eH() {
        Object object = this;
        ItemStack[] itemStackArray = ((Player)object).eY.getContainer().getItems();
        int n = itemStackArray.length;
        int n2 = 0;
        while (n2 < n) {
            object = itemStackArray[n2];
            if (object != null && ((ItemStack)object).getDefinition().getName().toLowerCase().contains("progress hat")) {
                return true;
            }
            ++n2;
        }
        object = this;
        itemStackArray = ((Player)object).eZ.getContainer().getItems();
        n = itemStackArray.length;
        n2 = 0;
        while (n2 < n) {
            object = itemStackArray[n2];
            if (object != null && ((ItemStack)object).getDefinition().getName().toLowerCase().contains("progress hat")) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public final int eI() {
        int n = 0;
        Object object = this;
        ItemStack[] itemStackArray = ((Player)object).eY.getContainer().getItems();
        int n2 = itemStackArray.length;
        int n3 = 0;
        while (n3 < n2) {
            object = itemStackArray[n3];
            if (object != null && ((ItemStack)object).getDefinition().getEquipmentSlot() == -1) {
                n += GameplayHelper.f(((ItemStack)object).getId());
            }
            ++n3;
        }
        object = this;
        itemStackArray = ((Player)object).eZ.getContainer().getItems();
        n2 = itemStackArray.length;
        n3 = 0;
        while (n3 < n2) {
            object = itemStackArray[n3];
            if (object != null && ((ItemStack)object).getDefinition().getEquipmentSlot() != -1) {
                n += GameplayHelper.f(((ItemStack)object).getId());
            }
            ++n3;
        }
        return n;
    }

    public final ItemStack eJ() {
        Object object = this;
        ItemStack[] itemStackArray = ((Player)object).eY.getContainer().getItems();
        int n = itemStackArray.length;
        int n2 = 0;
        while (n2 < n) {
            int n3;
            CaveLightSourceDefinition caveLightSourceDefinition;
            object = itemStackArray[n2];
            if (object != null && ((ItemStack)object).getDefinition().getEquipmentSlot() == -1 && ((caveLightSourceDefinition = CaveLightSourceDefinition.a(n3 = ((ItemStack)object).getId())) == null ? false : (caveLightSourceDefinition.b() == n3 ? false : caveLightSourceDefinition.e()))) {
                return object;
            }
            ++n2;
        }
        return null;
    }

    public final boolean eK() {
        Object object = this;
        ItemStack[] itemStackArray = ((Player)object).eY.getContainer().getItems();
        int n = itemStackArray.length;
        int n2 = 0;
        while (n2 < n) {
            object = itemStackArray[n2];
            if (object != null && ((ItemStack)object).getDefinition().getName().toLowerCase().contains("clue scroll")) {
                return true;
            }
            ++n2;
        }
        object = this;
        itemStackArray = ((Player)object).bankContainer.getItems();
        n = itemStackArray.length;
        n2 = 0;
        while (n2 < n) {
            object = itemStackArray[n2];
            if (object != null && ((ItemStack)object).getDefinition().getName().toLowerCase().contains("clue scroll")) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    private boolean m(String string) {
        Object object = this;
        ItemStack[] itemStackArray = ((Player)object).eZ.getContainer().getItems();
        int n = itemStackArray.length;
        int n2 = 0;
        while (n2 < n) {
            object = itemStackArray[n2];
            if (object != null && ((ItemStack)object).getDefinition().getName().toLowerCase().startsWith(string)) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public final boolean eL() {
        if (ServerSettings.freeToPlayWorld || !this.isMember()) {
            return false;
        }
        Player player = this;
        if (player.eZ.getContainer().getItemAt(2) == null) {
            return false;
        }
        player = this;
        int n = player.eZ.getContainer().getItemAt(2).getId();
        return n >= 1706 && n <= 1712 || n >= 10354 && n <= 10360;
    }

    public final boolean eM() {
        Object object = this;
        ItemStack[] itemStackArray = ((Player)object).eY.getContainer().getItems();
        int n = itemStackArray.length;
        int n2 = 0;
        while (n2 < n) {
            object = itemStackArray[n2];
            if (object != null && (((ItemStack)object).getId() == 2800 || ((ItemStack)object).getId() == 3565 || ((ItemStack)object).getId() == 3571)) {
                return true;
            }
            ++n2;
        }
        object = this;
        itemStackArray = ((Player)object).bankContainer.getItems();
        n = itemStackArray.length;
        n2 = 0;
        while (n2 < n) {
            object = itemStackArray[n2];
            if (object != null && (((ItemStack)object).getId() == 2800 || ((ItemStack)object).getId() == 3565 || ((ItemStack)object).getId() == 3571)) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public final List eN() {
        return this.hC;
    }

    public final void as(int n) {
        this.hq = n;
    }

    public final int eO() {
        return this.hq;
    }

    public final boolean eP() {
        return this.muteExpires != 0L && this.muteExpires > System.currentTimeMillis();
    }

    public final boolean eQ() {
        return this.banExpires != 0L && this.banExpires > System.currentTimeMillis();
    }

    public final int getOpenInterfaceId() {
        return this.openInterfaceId;
    }

    public final void setOpenInterfaceId(int n) {
        this.openInterfaceId = n;
    }

    public final void b(int n, boolean bl) {
        this.hF[n] = bl;
    }

    public final boolean[] eS() {
        return this.hF;
    }

    public final boolean au(int n) {
        return this.hF[n];
    }

    public final void av(int n) {
        this.hG = n;
    }

    public final int eT() {
        return this.hG;
    }

    public final void aw(int n) {
        this.hH = n;
    }

    public final int eU() {
        return this.hH;
    }

    public final int getCombatLevel() {
        return this.combatLevel;
    }

    public final void setCombatLevel(int n) {
        this.combatLevel = n;
    }

    public final void b(Player player) {
        this.ia = player;
    }

    public final Player eW() {
        return this.ia;
    }

    public final void setInteractionTargetPlane(int n) {
        this.interactionTargetPlane = n;
    }

    public final int getInteractionTargetPlane() {
        return this.interactionTargetPlane;
    }

    public final void setSidebarInterfaceId(int n, int n2) {
        this.sidebarInterfaceIds[n] = n2;
    }

    public final void setInventoryOverlayInterfaceId(int n) {
        this.inventoryOverlayInterfaceId = n;
    }

    public final void setSelectedItemInterfaceId(int n) {
        this.selectedItemInterfaceId = n;
    }

    public final int getSelectedItemInterfaceId() {
        return this.selectedItemInterfaceId;
    }

    public final void setSelectedItemSlot(int n) {
        this.selectedItemSlot = n;
    }

    public final int getSelectedItemSlot() {
        return this.selectedItemSlot;
    }

    public final void setBrimhavenOpen(boolean bl) {
        this.brimhavenOpen = bl;
    }

    public final boolean isBrimhavenOpen() {
        return this.brimhavenOpen;
    }

    public final void setTeleotherDestination(Position position) {
        this.hJ = position;
    }

    public final Position getTeleotherDestination() {
        return this.hJ;
    }

    public final void a(ItemStack itemStack) {
        this.hK = itemStack;
    }

    public final ItemStack fc() {
        return this.hK;
    }

    public final void z(boolean bl) {
        this.hL = true;
    }

    public final boolean fd() {
        return this.hL;
    }

    public final void k(int n, int n2) {
        this.hX[n2] = n;
    }

    public final void fe() {
        this.hX = new int[4];
    }

    public final int[] ff() {
        return this.hX;
    }

    public final void c(Npc npc) {
        this.hM = npc;
    }

    public final Npc fg() {
        return this.hM;
    }

    public final int fh() {
        return this.eS;
    }

    public final void aC(int n) {
        this.eS = n;
    }

    public final void b(ItemStack itemStack) {
        this.hN = itemStack;
    }

    public final ItemStack fi() {
        return this.hN;
    }

    public final void setSelectedLampSkill(int n) {
        this.selectedLampSkill = n;
    }

    public final int getSelectedLampSkill() {
        return this.selectedLampSkill;
    }

    public final long fk() {
        return this.hY;
    }

    public final void aE(int n) {
        this.hZ = n;
    }

    public final int fl() {
        return this.hZ;
    }

    public final void c(Player player) {
        this.ib = player;
    }

    public final Player fm() {
        return this.ib;
    }

    public final int fn() {
        return this.fW;
    }

    public final void aF(int n) {
        this.fW = n;
    }
}

