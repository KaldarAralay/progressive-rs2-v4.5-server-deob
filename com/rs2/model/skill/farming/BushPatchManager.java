/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.BushClearingTask;
import com.rs2.model.skill.farming.BushCompostTask;
import com.rs2.model.skill.farming.BushCureTask;
import com.rs2.model.skill.farming.BushDefinition;
import com.rs2.model.skill.farming.BushGrowthDefinition;
import com.rs2.model.skill.farming.BushHarvestTask;
import com.rs2.model.skill.farming.BushInspectTask;
import com.rs2.model.skill.farming.BushPatch;
import com.rs2.model.skill.farming.BushPlantingTask;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;

public final class BushPatchManager {
    private Player player;
    public int[] growthStages = new int[4];
    public int[] cropIds = new int[4];
    public int[] patchStates = new int[4];
    public long[] lastUpdateTicks = new long[4];
    public double[] diseaseChanceMultipliers = new double[]{1.0, 1.0, 1.0, 1.0};
    public boolean[] f = new boolean[4];
    public boolean[] protectionFlags = new boolean[4];

    public BushPatchManager(Player player) {
        this.player = player;
    }

    /*
     * Unable to fully structure code
     */
    public final void refreshConfig() {
        var1_1 = new int[this.growthStages.length];
        var2_3 = 0;
        while (var2_3 < this.growthStages.length) {
            var6_8 = this.patchStates[var2_3];
            var5_7 = this.cropIds[var2_3];
            var4_6 = this.growthStages[var2_3];
            var3_5 = this;
            var3_5 = BushDefinition.forSeedId(var5_7);
            switch (var4_6) {
                case 0: {
                    v0 = 0;
                    break;
                }
                case 1: {
                    v0 = 1;
                    break;
                }
                case 2: {
                    v0 = 2;
                    break;
                }
                case 3: {
                    v0 = 3;
                    break;
                }
                default: {
                    if (var3_5 == null) {
                        v0 = -1;
                        break;
                    }
                    if (BushPatchManager.b(var6_8) != 3) ** GOTO lbl30
                    v1 = BushPatchManager.b(var6_8) << 6;
                    v2 = var3_5.getHealthCheckConfigStage();
                    ** GOTO lbl41
lbl30:
                    // 1 sources

                    if (var5_7 != 5106) ** GOTO lbl-1000
                    if (BushPatchManager.b(var6_8) == 1) {
                        v1 = var3_5.getConfigStartStage() + var4_6 - 4;
                        v2 = 12;
                    } else if (BushPatchManager.b(var6_8) == 2) {
                        v1 = var3_5.getConfigStartStage() + var4_6 - 4;
                        v2 = 20;
                    } else lbl-1000:
                    // 2 sources

                    {
                        v1 = (BushPatchManager.b(var6_8) << 6) + var3_5.getConfigStartStage() + (var4_6 -= 4);
                        v2 = BushPatchManager.b(var6_8) == 2 ? -1 : 0;
                    }
lbl41:
                    // 4 sources

                    v0 = v1 + v2;
                }
            }
            var1_1[var2_3] = v0;
            ++var2_3;
        }
        var1_2 = (var1_1[0] << 16) + (var1_1[1] << 8 << 16) + var1_1[2] + (var1_1[3] << 8);
        var2_4 = this.player;
        var2_4.packetSender.sendConfig(509, var1_2);
    }

    private static int b(int n) {
        switch (n) {
            case 0: {
                return 0;
            }
            case 1: {
                return 1;
            }
            case 2: {
                return 2;
            }
            case 3: {
                return 3;
            }
        }
        return -1;
    }

    public final void processGrowth() {
        int n = 0;
        while (n < this.cropIds.length) {
            block11: {
                long l;
                block12: {
                    l = Server.getElapsedMinutes() - this.lastUpdateTicks[n];
                    if (l < 5L) break block11;
                    if (this.growthStages[n] <= 0 || this.growthStages[n] > 3) break block12;
                    int n2 = (int)(l / 5L);
                    int n3 = 0;
                    while (n3 < n2) {
                        if (this.growthStages[n] != 0) {
                            int n4 = n;
                            this.growthStages[n4] = this.growthStages[n4] - 1;
                            this.lastUpdateTicks[n] = Server.getElapsedMinutes();
                            ++n3;
                            continue;
                        }
                        break block11;
                    }
                    break block11;
                }
                BushDefinition bushDefinition = BushDefinition.forSeedId(this.cropIds[n]);
                if (bushDefinition == null || this.shouldStopGrowthCycle(n)) break block11;
                int n5 = (int)(l / (long)bushDefinition.getGrowthCycleTicks());
                int n6 = this.growthStages[n] - 4;
                if ((n5 -= n6) <= 0) break block11;
                n6 = 0;
                while (n6 < n5) {
                    block14: {
                        block17: {
                            BushDefinition bushDefinition2;
                            BushPatchManager bushPatchManager;
                            int n7;
                            block15: {
                                block16: {
                                    block13: {
                                        if (this.growthStages[n] != 4) break block13;
                                        int n8 = n;
                                        this.growthStages[n8] = this.growthStages[n8] + 1;
                                        break block14;
                                    }
                                    n7 = n;
                                    bushPatchManager = this;
                                    if (bushPatchManager.patchStates[n7] != 1) break block15;
                                    if (!bushPatchManager.protectionFlags[n7]) break block16;
                                    bushPatchManager.patchStates[n7] = 0;
                                    bushDefinition2 = BushDefinition.forSeedId(bushPatchManager.cropIds[n7]);
                                    if (bushDefinition2 == null) break block17;
                                    int n9 = n7;
                                    bushPatchManager.lastUpdateTicks[n9] = bushPatchManager.lastUpdateTicks[n9] + (long)bushDefinition2.getGrowthCycleTicks();
                                    break block15;
                                }
                                if (GameUtil.randomInt(2) == 0) {
                                    bushPatchManager.patchStates[n7] = 2;
                                }
                            }
                            if (bushPatchManager.patchStates[n7] != 1 && bushPatchManager.patchStates[n7] != 2) {
                                if (bushPatchManager.patchStates[n7] == 5 && bushPatchManager.growthStages[n7] != 2) {
                                    bushPatchManager.patchStates[n7] = 0;
                                }
                                if (bushPatchManager.patchStates[n7] == 0 && bushPatchManager.growthStages[n7] >= 4 && !bushPatchManager.f[n7] && (bushDefinition2 = BushDefinition.forSeedId(bushPatchManager.cropIds[n7])) != null) {
                                    double d = bushPatchManager.diseaseChanceMultipliers[n7] * bushDefinition2.getDiseaseChance();
                                    double d2 = d * 100.0;
                                    int n10 = (int)d2;
                                    if (GameUtil.randomInclusive(100) <= n10 && ServerSettings.diseasingEnabled) {
                                        bushPatchManager.patchStates[n7] = 1;
                                    }
                                }
                            }
                        }
                        if (this.patchStates[n] == 2) break block11;
                        if (this.patchStates[n] != 1) {
                            int n11 = n;
                            this.growthStages[n11] = this.growthStages[n11] + 1;
                        }
                        if (this.shouldStopGrowthCycle(n)) break block11;
                        if (this.growthStages[n] == bushDefinition.getGrowthStageCount() - 1) {
                            this.growthStages[n] = bushDefinition.getGrowthStageCount() + 4;
                            this.patchStates[n] = 3;
                            break;
                        }
                    }
                    ++n6;
                }
            }
            ++n;
        }
        this.refreshConfig();
    }

    private boolean shouldStopGrowthCycle(int n) {
        return this.lastUpdateTicks[n] == 0L || this.patchStates[n] == 2 || this.patchStates[n] == 3;
    }

    public final void recalculateRegrowthStage(int n) {
        BushDefinition bushDefinition = BushDefinition.forSeedId(this.cropIds[n]);
        if (bushDefinition == null) {
            return;
        }
        long l = Server.getElapsedMinutes() - this.lastUpdateTicks[n];
        int n2 = (int)(l / (long)bushDefinition.getGrowthCycleTicks());
        this.growthStages[n] = n2 + 4;
        this.refreshConfig();
    }

    public final boolean clearPatch(int n, int n2, int n3) {
        int n4;
        BushPatch bushPatch = BushPatch.forPosition(new Position(n, n2));
        if (bushPatch == null || n3 != 5341 && n3 != 952) {
            return false;
        }
        if (this.growthStages[bushPatch.getIndex()] == 3) {
            return true;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (!this.player.isMember()) {
            this.player.packetSender.sendGameMessage("You need a members account to access members content.");
            return true;
        }
        if (ServerSettings.freeToPlayWorld) {
            this.player.packetSender.sendGameMessage("You need to be in members world to access members content.");
            return true;
        }
        if (this.growthStages[bushPatch.getIndex()] <= 3) {
            if (!this.player.getInventoryManager().getContainer().containsItem(5341)) {
                this.player.getDialogueManager().showOneLineStatement("You need a rake to clear this path.");
                return true;
            }
            n2 = 2273;
            n3 = 1323;
            n4 = 5;
        } else {
            if (!this.player.getInventoryManager().getContainer().containsItem(952)) {
                this.player.getDialogueManager().showOneLineStatement("You need a spade to clear this path.");
                return true;
            }
            n2 = 830;
            n3 = 232;
            n4 = 3;
        }
        this.player.setActionLocked(true);
        Player player = this.player;
        player.packetSender.sendSoundEffect(n3, 1, 0);
        this.player.getUpdateState().setAnimation(n2);
        CycleEventHandler.getInstance().schedule(this.player, new BushClearingTask(this, n2, bushPatch), n4);
        return true;
    }

    public final boolean plantSeed(int n, int n2, int n3) {
        BushPatch bushPatch = BushPatch.forPosition(new Position(n, n2));
        BushDefinition bushDefinition = BushDefinition.forSeedId(n3);
        if (bushPatch == null || bushDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.growthStages[bushPatch.getIndex()] != 3) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You can't plant a seed here.");
            return false;
        }
        if (bushDefinition.getRequiredLevel() > this.player.getSkillManager().getCurrentLevels()[19]) {
            this.player.getDialogueManager().showOneLineStatement("You need a farming level of " + bushDefinition.getRequiredLevel() + " to plant this seed.");
            return true;
        }
        if (!this.player.getInventoryManager().getContainer().containsItem(5343)) {
            this.player.getDialogueManager().showOneLineStatement("You need a seed dibber to plant seed here.");
            return true;
        }
        if (this.player.getInventoryManager().getItemAmount(bushDefinition.getSeedId()) < bushDefinition.getSeedAmount()) {
            this.player.getDialogueManager().showOneLineStatement("You need atleast " + bushDefinition.getSeedAmount() + " seeds to plant here.");
            return true;
        }
        this.player.getUpdateState().setAnimation(2291);
        Player player = this.player;
        player.packetSender.sendSoundEffect(1321, 1, 0);
        this.growthStages[bushPatch.getIndex()] = 4;
        this.player.getInventoryManager().removeItem(new ItemStack(n3, bushDefinition.getSeedAmount()));
        this.player.setActionLocked(true);
        CycleEventHandler.getInstance().schedule(this.player, new BushPlantingTask(this, bushPatch, n3, bushDefinition), 3);
        return true;
    }

    public final boolean harvestPatch(int n, int n2) {
        Object object = BushPatch.forPosition(new Position(n, n2));
        if (object == null) {
            return false;
        }
        BushDefinition bushDefinition = BushDefinition.forSeedId(this.cropIds[object.getIndex()]);
        if (bushDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            object = this.player;
            ((Player)object).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.player.getInventoryManager().getContainer().getFreeSlots() <= 0) {
            object = this.player;
            ((Player)object).packetSender.sendGameMessage("Not enough space in your inventory.");
            return true;
        }
        this.player.getUpdateState().setAnimation(832);
        int n3 = this.player.nextActionSequence();
        this.player.setActiveCycleEvent(new BushHarvestTask(this, n3, (BushPatch)((Object)object), bushDefinition));
        CycleEventHandler.getInstance().schedule(this.player, this.player.getActiveCycleEvent(), 2);
        return true;
    }

    public final boolean compostPatch(int n, int n2, int n3) {
        if (n3 != 6032 && n3 != 6034) {
            return false;
        }
        BushPatch bushPatch = BushPatch.forPosition(new Position(n, n2));
        if (bushPatch == null) {
            return false;
        }
        if (this.growthStages[bushPatch.getIndex()] != 3 || this.patchStates[bushPatch.getIndex()] == 5) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This patch doesn't need compost.");
            return true;
        }
        this.player.getInventoryManager().removeItem(new ItemStack(n3));
        this.player.getInventoryManager().addItem(new ItemStack(1925));
        Player player = this.player;
        player.packetSender.sendGameMessage("You pour some " + (n3 == 6034 ? "super" : "") + "compost on the patch.");
        this.player.getUpdateState().setAnimation(2283);
        this.player.getSkillManager().addExperience(19, n3 == 6034 ? 26.0 : 18.0);
        this.player.setActionLocked(true);
        CycleEventHandler.getInstance().schedule(this.player, new BushCompostTask(this, bushPatch, n3), 7);
        return true;
    }

    public final boolean inspectPatch(int n, int n2) {
        BushPatch bushPatch = BushPatch.forPosition(new Position(n, n2));
        if (bushPatch == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        BushGrowthDefinition bushGrowthDefinition = BushGrowthDefinition.forCropId(this.cropIds[bushPatch.getIndex()]);
        Object object = BushDefinition.forSeedId(this.cropIds[bushPatch.getIndex()]);
        if (this.patchStates[bushPatch.getIndex()] == 1) {
            this.player.getDialogueManager().showTwoLineStatement("This plant is diseased. Use a plant cure on it to cure it, ", "or clear the patch with a spade.");
            return true;
        }
        if (this.patchStates[bushPatch.getIndex()] == 2) {
            this.player.getDialogueManager().showTwoLineStatement("This plant is dead. You did not cure it while it was diseased.", "Clear the patch with a spade.");
            return true;
        }
        if (this.patchStates[bushPatch.getIndex()] == 3) {
            this.player.getDialogueManager().showTwoLineStatement("This plant has fully grown. You can check it's health", "to gain some farming experiences.");
            return true;
        }
        if (this.growthStages[bushPatch.getIndex()] == 0) {
            this.player.getDialogueManager().showTwoLineStatement("This is a bush patch. The soil has not been treated.", "The patch needs weeding.");
        } else if (this.growthStages[bushPatch.getIndex()] == 3) {
            if (this.diseaseChanceMultipliers[bushPatch.getIndex()] == 0.1) {
                this.player.getDialogueManager().showTwoLineStatement("This is a bush patch. The soil has been treated with supercompost.", "The patch is empty and weeded.");
                return true;
            }
            if (this.diseaseChanceMultipliers[bushPatch.getIndex()] == 0.35) {
                this.player.getDialogueManager().showTwoLineStatement("This is a bush patch. The soil has been treated with compost.", "The patch is empty and weeded.");
                return true;
            }
            this.player.getDialogueManager().showTwoLineStatement("This is a bush patch. The soil has not been treated.", "The patch is empty and weeded.");
        } else if (bushGrowthDefinition != null && object != null) {
            object = this.player;
            ((Player)object).packetSender.sendGameMessage("You bend down and start to inspect the patch...");
            this.player.getUpdateState().setAnimation(1331);
            this.player.setActionLocked(true);
            CycleEventHandler.getInstance().schedule(this.player, new BushInspectTask(this, bushPatch, bushGrowthDefinition), 5);
        }
        return true;
    }

    public final boolean openSkillGuide(int n, int n2) {
        Object object = BushPatch.forPosition(new Position(n, n2));
        if (object == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            object = this.player;
            ((Player)object).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        this.player.getSkillGuideManager().showFarmingGuide(5);
        this.player.getSkillGuideManager().selectedSkillIndex = 20;
        return true;
    }

    public final boolean startResurrection(int n, int n2) {
        Object object = BushPatch.forPosition(new Position(n, n2));
        if (object == null) {
            return false;
        }
        BushDefinition bushDefinition = BushDefinition.forSeedId(this.cropIds[object.getIndex()]);
        if (bushDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            object = this.player;
            ((Player)object).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.patchStates[object.getIndex()] != 2) {
            object = this.player;
            ((Player)object).packetSender.sendGameMessage("This plant doesn't need to be resurrected.");
            return true;
        }
        this.player.setPendingCropResurrectionTarget("bush", object.getIndex());
        return true;
    }

    public final boolean finishResurrection(boolean bl) {
        if (bl) {
            Object object = BushDefinition.forSeedId(this.cropIds[this.player.pendingCropResurrectionPatchIndex]);
            this.patchStates[this.player.pendingCropResurrectionPatchIndex] = 0;
            int n = this.growthStages[this.player.pendingCropResurrectionPatchIndex] - 4;
            this.lastUpdateTicks[this.player.pendingCropResurrectionPatchIndex] = Server.getElapsedMinutes() - (long)(object.getGrowthCycleTicks() * n);
            object = this.player;
            ((Player)object).packetSender.sendGameMessage("You succesfully resurrected the crop.");
        } else {
            this.resetPatch(this.player.pendingCropResurrectionPatchIndex);
            this.growthStages[this.player.pendingCropResurrectionPatchIndex] = 3;
            this.lastUpdateTicks[this.player.pendingCropResurrectionPatchIndex] = Server.getElapsedMinutes();
            Player player = this.player;
            player.packetSender.sendGameMessage("You failed to resurrect the crop.");
        }
        this.refreshConfig();
        return true;
    }

    public final boolean curePatch(int n, int n2, int n3) {
        Object object = BushPatch.forPosition(new Position(n, n2));
        if (object == null || n3 != 6036) {
            return false;
        }
        BushDefinition bushDefinition = BushDefinition.forSeedId(this.cropIds[object.getIndex()]);
        if (bushDefinition == null) {
            return false;
        }
        if (!ServerSettings.farmingEnabled) {
            object = this.player;
            ((Player)object).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (this.patchStates[object.getIndex()] != 1) {
            object = this.player;
            ((Player)object).packetSender.sendGameMessage("This plant doesn't need to be cured.");
            return true;
        }
        this.player.getInventoryManager().removeItem(new ItemStack(n3));
        this.player.getInventoryManager().addItem(new ItemStack(229));
        this.player.getUpdateState().setAnimation(2288);
        this.player.setActionLocked(true);
        this.patchStates[object.getIndex()] = 0;
        CycleEventHandler.getInstance().schedule(this.player, new BushCureTask(this), 7);
        return true;
    }

    private void resetPatch(int n) {
        this.cropIds[n] = 0;
        this.patchStates[n] = 0;
        this.diseaseChanceMultipliers[n] = 1.0;
        this.f[n] = false;
        this.protectionFlags[n] = false;
    }

    static /* synthetic */ Player getPlayer(BushPatchManager bushPatchManager) {
        return bushPatchManager.player;
    }

    static /* synthetic */ void a(BushPatchManager bushPatchManager, int n) {
        bushPatchManager.resetPatch(n);
    }
}

