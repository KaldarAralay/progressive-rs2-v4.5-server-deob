/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.player;

import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.CharacterFileBankTab;
import com.rs2.model.quest.QuestDefinition;
import java.util.ArrayList;

public final class CharacterFileRecord {
    public String username;
    public String password;
    public String hostAddress = "0.0.0.0";
    public String d = "";
    public int playerRights;
    public String f = "";
    public String g = "";
    public String h = "";
    public long lastSavedMillis;
    public long totalPlayTimeMillis;
    public long createdAtMillis;
    public boolean l;
    public boolean memberFlag;
    public int donatorPoints;
    public int petUnlockFlags;
    public int x = 3093;
    public int y = 3104;
    public int plane = 0;
    public int skeletonSkinUnlocked = 0;
    public int gender;
    public int npcKillCount;
    public int playerKillCount;
    public int deathCount;
    public int easyCluesCompleted;
    public int mediumCluesCompleted;
    public int hardCluesCompleted;
    public int soldItemsValue;
    public int boughtItemsValue;
    public int duelWins;
    public int duelLosses;
    public int E;
    public boolean autoRetaliate;
    public int fightMode;
    public int brightness = 2;
    public int mouseButtons;
    public int publicChatEffects = 1;
    public int splitPrivateChat;
    public int acceptAid;
    public int musicVolume;
    public int effectVolume;
    public int specialEnergy = 100;
    public boolean changingBankPin;
    public boolean deletingBankPin;
    public int pinAppendYear = -1;
    public int pinAppendDate = -1;
    public int bindingNecklaceCharge = 15;
    public int ringOfForgingLife = 140;
    public int ringOfRecoilLife = 40;
    public int skullTimer;
    public int runEnergyRaw = 10000;
    public boolean running;
    public int abyssMageNpcId;
    public long muteExpires;
    public long banExpires;
    public int barrowsKillCount;
    public int barrowsTargetBrotherIndex;
    public int poisonImmunityTicks;
    public int antifireTicks;
    public int teleblockTicks;
    public double poisonDamage;
    public int slayerMasterId;
    public String slayerTaskName = "";
    public int slayerTaskAmount;
    public boolean usingAncients;
    public boolean brimhavenOpen;
    public boolean killedClueAttacker;
    public int gangAffiliation;
    public int piratesTreasureBananaCrateCount;
    public boolean treasureTrailNavigationTaught;
    public int coalTruckAmount;
    public int treasureTrailStepCount;
    public boolean cluePuzzleSolved;
    public int[] currentPin = new int[]{-1, -1, -1, -1};
    public int[] pendingPin = new int[]{-1, -1, -1, -1};
    public int[] essencePouchAmounts = new int[4];
    public int[] appearanceParts = new int[7];
    public int[] appearanceColors = new int[5];
    public ItemStack[] inventoryItems = new ItemStack[28];
    public ItemStack[] equipmentItems = new ItemStack[14];
    public long[] friendsList = new long[200];
    public long[] ignoreList = new long[100];
    public int[] queuedLoginItemIds = new int[28];
    public int[] queuedLoginItemAmounts = new int[28];
    public boolean[] barrowsKilledBrothers = new boolean[6];
    public int[] allotmentGrowthStages = new int[8];
    public int[] allotmentCropIds = new int[8];
    public int[] allotmentHarvestAmounts = new int[8];
    public int[] allotmentPatchStates = new int[8];
    public long[] allotmentLastUpdateTicks = new long[8];
    public double[] allotmentDiseaseChanceMultipliers = new double[8];
    public boolean[] allotmentProtectionFlags = new boolean[8];
    public int[] bushGrowthStages = new int[4];
    public int[] bushCropIds = new int[4];
    public int[] bushPatchStates = new int[4];
    public long[] bushLastUpdateTicks = new long[4];
    public double[] bushDiseaseChanceMultipliers = new double[4];
    public boolean[] bushSavedFlags = new boolean[4];
    public int[] flowerGrowthStages = new int[4];
    public int[] flowerCropIds = new int[4];
    public int[] flowerPatchStates = new int[4];
    public long[] flowerLastUpdateTicks = new long[4];
    public double[] flowerDiseaseChanceMultipliers = new double[4];
    public int[] fruitTreeGrowthStages = new int[4];
    public int[] fruitTreeIds = new int[4];
    public int[] fruitTreePatchStates = new int[4];
    public long[] fruitTreeLastUpdateTicks = new long[4];
    public double[] fruitTreeDiseaseChanceMultipliers = new double[4];
    public boolean[] fruitTreeSavedFlags = new boolean[4];
    public int[] herbGrowthStages = new int[4];
    public int[] herbCropIds = new int[4];
    public int[] herbHarvestAmounts = new int[4];
    public int[] herbPatchStates = new int[4];
    public long[] herbLastUpdateTicks = new long[4];
    public double[] herbDiseaseChanceMultipliers = new double[4];
    public int[] hopsGrowthStages = new int[4];
    public int[] hopsCropIds = new int[4];
    public int[] hopsHarvestAmounts = new int[4];
    public int[] hopsPatchStates = new int[4];
    public long[] hopsLastUpdateTicks = new long[4];
    public double[] hopsDiseaseChanceMultipliers = new double[4];
    public boolean[] hopsProtectionFlags = new boolean[4];
    public int[] specialTreeGrowthStages = new int[4];
    public int[] specialTreeIds = new int[4];
    public int[] specialTreePatchStates = new int[4];
    public long[] specialTreeLastUpdateTicks = new long[4];
    public double[] specialTreeDiseaseChanceMultipliers = new double[4];
    public int[] specialCropGrowthStages = new int[4];
    public int[] specialCropIds = new int[4];
    public int[] specialCropPatchStates = new int[4];
    public long[] specialCropLastUpdateTicks = new long[4];
    public double[] specialCropDiseaseChanceMultipliers = new double[4];
    public int[] treeGrowthStages = new int[4];
    public int[] treeIds = new int[4];
    public int[] treePatchData = new int[4];
    public int[] treePatchStates = new int[4];
    public long[] treeLastUpdateTicks = new long[4];
    public double[] treeDiseaseChanceMultipliers = new double[4];
    public boolean[] treeSavedFlags = new boolean[4];
    public int[] compostBinStates = new int[4];
    public long[] compostBinLastUpdateTicks = new long[4];
    public int[] compostBinItemIds = new int[4];
    public int[] farmingToolStoreAmounts = new int[18];
    public int[] configStates = new int[2000];
    public int[] questProgress = new int[QuestDefinition.b];
    public int[] questBitFlags = new int[QuestDefinition.b];
    public int[] questHookStates = new int[100];
    public int[] currentLevels = new int[22];
    public long[] skillExperience = new long[22];
    public boolean barrowsDoorPuzzleSolved;
    public boolean barrowsChestOpened;
    private int cachedItemValue = 0;
    public int bU = 0;
    public boolean[] grandExchangeSellOfferFlags = new boolean[6];
    public int[] grandExchangeItemIds = new int[6];
    public int[] grandExchangeQuantities = new int[6];
    public int[] grandExchangeUnitPrices = new int[6];
    public boolean[] grandExchangeCancelledFlags = new boolean[6];
    public int[] grandExchangeCompletedQuantities = new int[6];
    public int[] grandExchangeTotalPrices = new int[6];
    public int[] grandExchangePrimaryCollectAmounts = new int[6];
    public int[] grandExchangeSecondaryCollectAmounts = new int[6];
    public boolean[] grandExchangeFinishMessagePending = new boolean[6];
    public int flourMillHopperGrainCount = 0;
    public int questRandomSeed;
    public int publicChatMode = 0;
    public int privateChatMode = 0;
    public int tradeMode = 0;
    public int ck = 0;
    public long cl = 0L;
    public int cm = 0;
    public int cn = 0;
    public int co = 778;
    public int cp = 100;
    public int cq = 100;
    public int cr = 100;
    public int cs = 0;
    public int ct = 0;
    public int cu = 0;
    public int cv = 0;
    public int cw = 0;
    public boolean cx = false;
    public int cy = 0;
    public boolean cz = false;
    public int cA = 0;
    public ArrayList bankTabs = new ArrayList();
    private int dF = 10;
    public int cC = 0;
    public int cD = 0;
    public int cE = -1;
    public int cF = -1;
    public int cG = -1;
    public int cH = -1;
    public int cI = -1;
    public int cJ = -1;
    public byte cK = (byte)-1;
    public byte cL = (byte)-1;
    public byte cM = (byte)-1;
    public byte cN = (byte)-1;
    public byte cO = (byte)-1;
    public byte cP = (byte)-1;
    public byte cQ = (byte)-1;
    public ArrayList cR = new ArrayList();
    public ArrayList cS;
    public int cT;
    public int cU;
    public byte cV;
    public int cW;
    public boolean cX;
    public boolean cY;
    public int cZ;
    public int da;
    public int db;
    public byte dc;
    public byte dd;
    public byte de;
    public long df;
    public boolean dg;
    public byte dh;
    public byte di;
    public int dj;
    public ItemStack[] dk;
    public String dl;
    public int dm;
    public byte dn;
    public int cfr_renamed_0;
    public byte dp;
    public byte dq;
    public boolean dr;
    public boolean ds;
    public int dt;
    public int[] du;
    public long dv;
    public byte dw;
    public int dx;
    public long dy;
    public int dz;
    public int dA;
    public boolean dB;
    public boolean dC;
    public int dD;
    private static final int[] experienceForLevel;

    static {
        int[][][] nArrayArray = new int[][][]{new int[][]{new int[5], {11, 15, 15, 5, 7}}, new int[][]{new int[5], {11, 15, 15, 5, 7}}};
        int[][][] nArrayArray2 = new int[2][][];
        int[][] nArrayArray3 = new int[2][];
        int[] nArray = new int[7];
        nArray[0] = 18;
        nArray[1] = 26;
        nArray[2] = 36;
        nArray[4] = 33;
        nArray[5] = 42;
        nArray[6] = 10;
        nArrayArray3[0] = nArray;
        nArrayArray3[1] = new int[]{25, 31, 40, 8, 34, 43, 17};
        nArrayArray2[0] = nArrayArray3;
        nArrayArray2[1] = new int[][]{{56, 61, 70, 45, 67, 79, -1}, {60, 65, 77, 54, 68, 80, -1}};
        experienceForLevel = new int[100];
        int n = 0;
        int n2 = 1;
        while (n2 <= 99) {
            int n3;
            n = (int)((double)n + Math.floor((double)n2 + 300.0 * Math.pow(2.0, (double)n2 / 7.0)));
            CharacterFileRecord.experienceForLevel[n2] = n3 = (int)Math.floor(n / 4);
            ++n2;
        }
    }

    public CharacterFileRecord() {
        new ArrayList();
        this.cS = new ArrayList();
        this.cT = -1;
        this.cU = -1;
        this.cV = (byte)-1;
        this.cW = -1;
        this.cX = false;
        this.cY = false;
        this.cZ = -1;
        this.da = -1;
        this.db = -1;
        this.dc = (byte)-1;
        this.dd = (byte)-1;
        this.de = 0;
        this.df = 0L;
        this.dg = false;
        this.dh = (byte)-1;
        this.di = (byte)-1;
        this.dj = -1;
        this.dm = -1;
        this.dn = (byte)-1;
        this.cfr_renamed_0 = -1;
        this.dp = (byte)-1;
        this.dq = (byte)-1;
        this.dr = false;
        this.ds = false;
        this.dt = -1;
        this.du = new int[4];
        this.dw = 0;
        this.dx = 0;
        this.dB = false;
        this.dC = false;
        this.dD = 0;
        this.bankTabs.add(new CharacterFileBankTab(0));
    }

    public final void setBankTabItem(int n, ItemStack itemStack, int n2) {
        try {
            if (n2 > this.dF - 1) {
                n2 = this.dF - 1;
            }
            if (n2 > this.bankTabs.size() - 1) {
                this.bankTabs.add(new CharacterFileBankTab(0));
            }
            CharacterFileBankTab characterFileBankTab = (CharacterFileBankTab)this.bankTabs.get(n2);
            if (n > characterFileBankTab.items.size() - 1) {
                characterFileBankTab = (CharacterFileBankTab)this.bankTabs.get(n2);
                characterFileBankTab.items.add(itemStack);
                return;
            }
            characterFileBankTab = (CharacterFileBankTab)this.bankTabs.get(n2);
            characterFileBankTab.items.set(n, itemStack);
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public final int getStoredItemValue() {
        if (this.cachedItemValue == 0) {
            ItemDefinition itemDefinition;
            Object object;
            int n;
            int n2 = 0;
            while (n2 < this.bankTabs.size()) {
                Object object2 = (CharacterFileBankTab)this.bankTabs.get(n2);
                object2 = ((CharacterFileBankTab)object2).items;
                n = 0;
                while (n < ((ArrayList)object2).size()) {
                    object = (ItemStack)((ArrayList)object2).get(n);
                    if (object != null && ((ItemStack)object).getAmount() > 0 && !(itemDefinition = ((ItemStack)object).getDefinition()).z()) {
                        this.cachedItemValue += itemDefinition.n() * ((ItemStack)object).getAmount();
                    }
                    ++n;
                }
                ++n2;
            }
            object = this.inventoryItems;
            n = this.inventoryItems.length;
            int n3 = 0;
            while (n3 < n) {
                ItemStack itemStack = object[n3];
                if (itemStack != null && itemStack.getAmount() > 0 && !(itemDefinition = itemStack.getDefinition()).z()) {
                    this.cachedItemValue += itemDefinition.n() * itemStack.getAmount();
                }
                ++n3;
            }
            object = this.equipmentItems;
            n = this.equipmentItems.length;
            n3 = 0;
            while (n3 < n) {
                Object object3 = object[n3];
                if (object3 != null && ((ItemStack)object3).getAmount() > 0 && !(itemDefinition = ((ItemStack)object3).getDefinition()).z()) {
                    this.cachedItemValue += itemDefinition.n() * ((ItemStack)object3).getAmount();
                }
                ++n3;
            }
        }
        return this.cachedItemValue;
    }

    public final long getSkillExperience(int n) {
        if (n < 21) {
            return this.skillExperience[n];
        }
        long l = 0L;
        n = 0;
        while (n < 21) {
            l += this.skillExperience[n];
            ++n;
        }
        return l;
    }

    public final int getTotalLevel() {
        int n = 0;
        int n2 = 0;
        while (n2 < 21) {
            n += CharacterFileRecord.getLevelForExperience(this.getSkillExperience(n2));
            ++n2;
        }
        return n;
    }

    public static int getLevelForExperience(double d) {
        int n = 1;
        while (n <= 99) {
            if ((double)experienceForLevel[n] > d) {
                return n;
            }
            ++n;
        }
        return 99;
    }
}

