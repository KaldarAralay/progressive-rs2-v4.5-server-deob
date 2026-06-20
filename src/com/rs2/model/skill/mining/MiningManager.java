/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.mining;

import com.rs2.ServerSettings;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.item.ItemService;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.LoadedWorldObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.objects.WorldObjectLookup;
import com.rs2.model.player.Player;
import com.rs2.model.skill.GatheringToolDefinition;
import com.rs2.model.skill.ItemCombinationHandler;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.skill.mining.MineableRockDefinition;
import com.rs2.model.skill.mining.MiningTask;
import com.rs2.model.skill.mining.ProspectingTask;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;
import com.rs2.util.RectangularArea;

public final class MiningManager {
    private Player b;
    private static int[] c = new int[]{6981, 6979, 6983};
    private static int[] d = new int[]{6977, 6971, 6975, 6973};
    private static int[] e = new int[]{1623, 1623, 1623, 1623, 1621, 1621, 1621, 1619, 1619, 1617};
    private static int[] f = new int[]{1625, 1625, 1627, 1627, 1629};
    RectangularArea a = new RectangularArea(2727, 9681, 2742, 9696, 0);

    public MiningManager(Player player) {
        this.b = player;
    }

    public final boolean a(int n) {
        if (!MiningManager.c(n)) {
            return false;
        }
        if (!ServerSettings.miningEnabled) {
            Player player = this.b;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return false;
        }
        if (this.b.getInventoryManager().getContainer().getFreeSlots() <= 0) {
            Player player = this.b;
            player.packetSender.sendSoundEffect(1878, 1, 0);
            player = this.b;
            player.packetSender.sendGameMessage("Not enough space in your inventory.");
            if (this.b.getQuestState(0) != 1) {
                this.b.getDialogueManager().showOneLineStatement("Not enough space in your inventory.");
                this.b.setInteractionTargetId(0);
            }
            if (this.b.botEnabled) {
                this.b.currentBotTask.startWalkToBank(this.b);
            }
            return false;
        }
        if (ItemCombinationHandler.a(this.b, 14) == null) {
            Player player = this.b;
            player.packetSender.sendGameMessage("You do not have a pickaxe that you can use.");
            if (this.b.getQuestState(0) != 1) {
                this.b.getDialogueManager().showOneLineStatement("You do not have a pickaxe that you can use.");
                this.b.setInteractionTargetId(0);
            }
            if (this.b.botEnabled) {
                this.b.currentBotTask.startWalkToBank(this.b);
            }
            return false;
        }
        return SkillActionHelper.checkSkillRequirement(this.b, 14, MineableRockDefinition.forObjectId(n) != null ? MineableRockDefinition.getRequiredLevel(MineableRockDefinition.forObjectId(n)) : 0, "mine here");
    }

    public final void a(int n, int n2, int n3) {
        if (!SkillActionHelper.isObjectPresent(n, n2, n3, this.b.getPosition().getPlane())) {
            if (this.b.botEnabled) {
                this.b.interactWithBotObjectTargets(this.b.botInteractionTargetIds);
            }
            return;
        }
        ObjectManager.getInstance();
        Object object = ObjectManager.findDynamicObjectAt(n2, n3, this.b.getPosition().getPlane());
        if (MiningManager.e(n) == -1 && object != null) {
            object = this.b;
            ((Player)object).packetSender.sendGameMessage("There is currently no ores remaining in this rock.");
            if (this.b.getQuestState(0) != 1) {
                this.b.getDialogueManager().showOneLineStatement("There is currently no ores remaining in this rock.");
                this.b.setInteractionTargetId(0);
            }
            object = this.b;
            ((Player)object).packetSender.sendSoundEffect(429, 1, 0);
            if (this.b.botEnabled) {
                this.b.interactWithBotObjectTargets(this.b.botInteractionTargetIds);
            }
            return;
        }
        if (this.b.getInventoryManager().getContainer().getFreeSlots() <= 0) {
            object = this.b;
            ((Player)object).packetSender.sendGameMessage("Not enough space in your inventory.");
            if (this.b.botEnabled) {
                this.b.currentBotTask.startWalkToBank(this.b);
            }
            return;
        }
        object = this.b;
        ((Player)object).packetSender.sendGameMessage("You swing your pick at the rock.");
        if (this.b.getQuestState(0) != 1) {
            this.b.getDialogueManager().a("Please wait.", "", "Your character is now attempting to mine the rock.", "This should only take a few seconds.", "", true);
        }
        int n4 = this.b.nextActionSequence();
        MineableRockDefinition mineableRockDefinition = MineableRockDefinition.forObjectId(n);
        if (mineableRockDefinition == null) {
            return;
        }
        GatheringToolDefinition gatheringToolDefinition = ItemCombinationHandler.a(this.b, 14);
        if (gatheringToolDefinition == null) {
            Player player = this.b;
            player.packetSender.sendGameMessage("You do not have a pickaxe that you can use.");
            if (this.b.botEnabled) {
                this.b.currentBotTask.startWalkToBank(this.b);
            }
            return;
        }
        int n5 = MineableRockDefinition.getOreItemId(mineableRockDefinition);
        int n6 = MineableRockDefinition.getMineChanceLow(mineableRockDefinition);
        int n7 = MineableRockDefinition.getMineChanceHigh(mineableRockDefinition);
        int n8 = (int)gatheringToolDefinition.f();
        double d = MineableRockDefinition.getDepletionChance(mineableRockDefinition);
        if (n5 == 0) {
            Player player = this.b;
            player.packetSender.sendGameMessage("There is currently no ores remaining in this rock.");
            player = this.b;
            player.packetSender.sendSoundEffect(429, 1, 0);
            if (this.b.botEnabled) {
                this.b.interactWithBotObjectTargets(this.b.botInteractionTargetIds);
            }
            return;
        }
        int n9 = MineableRockDefinition.getBaseExperience(mineableRockDefinition);
        int n10 = MineableRockDefinition.getRespawnTicks(mineableRockDefinition);
        MineableRockDefinition.getRequiredLevel(mineableRockDefinition);
        this.b.aN();
        this.b.getUpdateState().setAnimation(gatheringToolDefinition.d());
        this.b.gatheringHazardCounter = 0;
        if (ServerSettings.randomEventsMode == 0 && GameUtil.h(800) == 0 && this.b.getQuestState(0) == 1 && !this.b.botEnabled && !this.b.r()) {
            int n11 = MiningManager.a(n, new Position(n2, n3, this.b.getPosition().getPlane()));
            ObjectManager.getInstance();
            DynamicObject dynamicObject = ObjectManager.findDynamicObjectAt(n2, n3, this.b.getPosition().getPlane());
            if (dynamicObject == null && n11 != -1) {
                n11 = SkillActionHelper.getObjectOrientation(n, n2, n3, this.b.getPosition().getPlane());
                int n12 = SkillActionHelper.getObjectType(n, n2, n3, this.b.getPosition().getPlane());
                ObjectManager.getInstance().addDynamicObject(new DynamicObject(MiningManager.a(n, new Position(n2, n3, this.b.getPosition().getPlane())), n2, n3, this.b.getPosition().getPlane(), n11, n12, n, 15), true);
            }
        }
        this.b.setActiveCycleEvent(new MiningTask(this, n4, n, n2, n3, gatheringToolDefinition, n6, n7, n5, n9, d, n10));
        CycleEventHandler.getInstance().schedule(this.b, this.b.getActiveCycleEvent(), n8);
    }

    public static int b(int n) {
        switch (n) {
            case 1: {
                return 2;
            }
            case 2: {
                return 4;
            }
            case 3: {
                return 1;
            }
            case 4: {
                return 0;
            }
        }
        return n;
    }

    public static int a(int n, int n2) {
        switch (n) {
            case 0: {
                return e[GameUtil.f(10)];
            }
            case 2111: {
                if (GameUtil.g(2) == 0) {
                    return e[GameUtil.f(10)];
                }
                return f[GameUtil.f(5)];
            }
            case 10947: {
                return c[GameUtil.g(2)];
            }
            case 10946: {
                return d[GameUtil.g(3)];
            }
        }
        return n2;
    }

    public static boolean c(int n) {
        return MineableRockDefinition.forObjectId(n) != null;
    }

    public final boolean d(int n) {
        Object object = new int[]{10587, 10585, 10586, 14832, 14833, 14834, 10944, 10945, 9723, 9724, 9725, 11555, 11552, 11553, 11554, 11557, 11556, 450, 451, 452};
        int[] nArray = object;
        int n2 = 0;
        while (n2 < 20) {
            int n3 = nArray[n2];
            if (n == n3) {
                Player player = this.b;
                player.packetSender.sendGameMessage("There is currently no ores remaining in this rock.");
                player = this.b;
                player.packetSender.sendSoundEffect(429, 1, 0);
                return true;
            }
            ++n2;
        }
        MineableRockDefinition mineableRockDefinition = MineableRockDefinition.forObjectId(n);
        object = (Object)mineableRockDefinition;
        if (mineableRockDefinition == null) {
            return false;
        }
        if (this.b.getQuestState(0) != 1) {
            this.b.getDialogueManager().a("Please wait.", "", "Your character is now attempting to prospect the rock. This", "should only take a few seconds.", "", true);
        }
        Player player = this.b;
        player.packetSender.sendGameMessage("You examine the rock for ores...");
        this.b.n(true);
        int n4 = MiningManager.a(n, MineableRockDefinition.getOreItemId((MineableRockDefinition)((Object)object)));
        ItemService.getInstance();
        object = ItemService.getItemName(n4).toLowerCase().replaceAll("ore", "").trim();
        CycleEventHandler.getInstance().schedule(this.b, new ProspectingTask(this, n, (String)object), 5);
        return true;
    }

    public static int a(int n, Position position) {
        LoadedWorldObject loadedWorldObject = WorldObjectLookup.findObjectByIdAt(n, position.getX(), position.getY(), position.getPlane());
        if (loadedWorldObject.getWorldObject().getObjectId() >= 2090 && loadedWorldObject.getWorldObject().getObjectId() <= 2111) {
            return loadedWorldObject.getWorldObject().getObjectId() + 29;
        }
        if (loadedWorldObject.getWorldObject().getObjectId() >= 10946 && loadedWorldObject.getWorldObject().getObjectId() <= 10949) {
            return loadedWorldObject.getWorldObject().getObjectId() + 246;
        }
        if (loadedWorldObject.getWorldObject().getObjectId() >= 11183 && loadedWorldObject.getWorldObject().getObjectId() <= 11191) {
            return loadedWorldObject.getWorldObject().getObjectId() - 1456;
        }
        if (loadedWorldObject.getWorldObject().getObjectId() >= 10583 && loadedWorldObject.getWorldObject().getObjectId() <= 10584) {
            return loadedWorldObject.getWorldObject().getObjectId() + 583;
        }
        if (loadedWorldObject.getWorldObject().getObjectId() >= 14850 && loadedWorldObject.getWorldObject().getObjectId() <= 14864) {
            return loadedWorldObject.getWorldObject().getObjectId() - 15;
        }
        return -1;
    }

    public static int e(int n) {
        if (n >= 2119 && n <= 2140) {
            return n - 29;
        }
        if (n >= 9727 && n <= 9735) {
            return n + 1456;
        }
        if (n >= 11166 && n <= 11167) {
            return n - 583;
        }
        if (n >= 11168 && n <= 11182) {
            return n - 1460;
        }
        if (n >= 11192 && n <= 11195) {
            return n - 246;
        }
        if (n >= 11424 && n <= 11432) {
            return n + 506;
        }
        if (n >= 11433 && n <= 11444) {
            return n + 521;
        }
        if (n >= 11915 && n <= 11920) {
            return n + 33;
        }
        if (n >= 11921 && n <= 11923) {
            return n + 24;
        }
        if (n >= 11925 && n <= 11929) {
            return n + 15;
        }
        if (n >= 14835 && n <= 14849) {
            return n + 15;
        }
        return -1;
    }

    static /* synthetic */ Player a(MiningManager miningManager) {
        return miningManager.b;
    }

    static /* synthetic */ double b(int n, int n2) {
        switch (n) {
            case 6979: {
                return 50.0;
            }
            case 6981: {
                return 60.0;
            }
            case 6983: {
                return 75.0;
            }
            case 6971: {
                return 30.0;
            }
            case 6973: {
                return 40.0;
            }
            case 6975: {
                return 50.0;
            }
            case 6977: {
                return 60.0;
            }
        }
        return n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static /* synthetic */ int f(int n) {
        int[] nArray = new int[]{9708, 9711, 9714, 9717, 9720};
        int[] nArray2 = new int[]{9709, 9712, 9715, 9718, 9721};
        int[] nArray3 = new int[]{9710, 9713, 9716, 9719, 9722};
        int[] nArray4 = new int[]{11183, 11186, 11189, 11930, 11933, 11936, 11939, 11942, 11945, 11948, 11951, 11954, 11957, 11960, 11963};
        int[] nArray5 = new int[]{11184, 11187, 11190, 11931, 11934, 11937, 11940, 11943, 11946, 11949, 11952, 11955, 11958, 11961, 11964};
        int[] nArray6 = new int[]{11185, 11188, 11191, 11932, 11935, 11938, 11941, 11944, 11947, 11950, 11953, 11956, 11959, 11962, 11965};
        int[] nArray7 = new int[]{14850, 14853, 14856, 14859, 14862};
        int[] nArray8 = new int[]{14851, 14854, 14857, 14860, 14863};
        int[] nArray9 = new int[]{14852, 14855, 14858, 14861, 14864};
        if (n == 10946) return 10944;
        if (n == 10948) {
            return 10944;
        }
        if (n == 10947) return 10945;
        if (n == 10949) {
            return 10945;
        }
        if (n == 2110) {
            if (GameplayHelper.b(10587)) {
                return 10587;
            }
            if (ServerSettings.cacheVersion >= 270) return 451;
            return 452;
        }
        if (n == 10583) {
            return 10585;
        }
        if (n == 10584) {
            return 10586;
        }
        int[] nArray10 = nArray;
        int n2 = 0;
        while (n2 < 5) {
            int n3 = nArray10[n2];
            if (n == n3) {
                return 9723;
            }
            ++n2;
        }
        nArray10 = nArray2;
        n2 = 0;
        while (n2 < 5) {
            int n4 = nArray10[n2];
            if (n == n4) {
                return 9724;
            }
            ++n2;
        }
        nArray10 = nArray3;
        n2 = 0;
        while (n2 < 5) {
            int n5 = nArray10[n2];
            if (n == n5) {
                return 9725;
            }
            ++n2;
        }
        nArray10 = nArray4;
        n2 = 0;
        while (n2 < 15) {
            int n6 = nArray10[n2];
            if (n == n6) {
                if (n < 11945) return 11552;
                return 11555;
            }
            ++n2;
        }
        nArray10 = nArray5;
        n2 = 0;
        while (n2 < 15) {
            int n7 = nArray10[n2];
            if (n == n7) {
                if (n < 11945) return 11553;
                return 11556;
            }
            ++n2;
        }
        nArray10 = nArray6;
        n2 = 0;
        while (n2 < 15) {
            int n8 = nArray10[n2];
            if (n == n8) {
                if (n < 11945) return 11554;
                return 11557;
            }
            ++n2;
        }
        nArray10 = nArray7;
        n2 = 0;
        while (n2 < 5) {
            int n9 = nArray10[n2];
            if (n == n9) {
                return 14832;
            }
            ++n2;
        }
        nArray10 = nArray8;
        n2 = 0;
        while (n2 < 5) {
            int n10 = nArray10[n2];
            if (n == n10) {
                return 14833;
            }
            ++n2;
        }
        nArray10 = nArray9;
        n2 = 0;
        while (n2 < 5) {
            int n11 = nArray10[n2];
            if (n == n11) {
                return 14834;
            }
            ++n2;
        }
        if (n % 2 == 0) return 450;
        if (n == 3043) {
            return 450;
        }
        if (ServerSettings.cacheVersion >= 270) return 451;
        return 452;
    }
}

