/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.mining;

import com.rs2.ServerSettings;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemService;
import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.randomevent.SkillRandomEventNpc;
import com.rs2.model.skill.GatheringToolDefinition;
import com.rs2.model.skill.ItemCombinationHandler;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.skill.mining.MiningManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.net.packet.PacketSender;
import com.rs2.util.GameUtil;
import com.rs2.util.TextUtil;

final class MiningTask
extends CycleEvent {
    private /* synthetic */ MiningManager manager;
    private final /* synthetic */ int actionSequence;
    private final /* synthetic */ int rockObjectId;
    private final /* synthetic */ int x;
    private final /* synthetic */ int y;
    private final /* synthetic */ GatheringToolDefinition gatheringTool;
    private final /* synthetic */ int mineChanceLow;
    private final /* synthetic */ int mineChanceHigh;
    private final /* synthetic */ int oreItemId;
    private final /* synthetic */ int baseExperience;
    private final /* synthetic */ double depletionChance;
    private final /* synthetic */ int respawnTicks;

    MiningTask(MiningManager miningManager, int n, int n2, int n3, int n4, GatheringToolDefinition gatheringToolDefinition, int n5, int n6, int n7, int n8, double d, int n9) {
        this.manager = miningManager;
        this.actionSequence = n;
        this.rockObjectId = n2;
        this.x = n3;
        this.y = n4;
        this.gatheringTool = gatheringToolDefinition;
        this.mineChanceLow = n5;
        this.mineChanceHigh = n6;
        this.oreItemId = n7;
        this.baseExperience = n8;
        this.depletionChance = d;
        this.respawnTicks = n9;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        int n;
        boolean bl;
        block48: {
            block47: {
                if (!MiningManager.a(this.manager).isCurrentActionSequence(this.actionSequence)) {
                    if (MiningManager.a((MiningManager)this.manager).botEnabled) {
                        MiningManager.a((MiningManager)this.manager).currentBotTask.startWalkToBank(MiningManager.a(this.manager));
                    }
                    cycleEventContainer.stop();
                    return;
                }
                bl = false;
                n = 0;
                if (MiningManager.e(this.rockObjectId) != -1) break block47;
                int n2 = MiningManager.a(this.rockObjectId, new Position(this.x, this.y, MiningManager.a(this.manager).getPosition().getPlane()));
                ObjectManager.getInstance();
                DynamicObject dynamicObject = ObjectManager.findDynamicObjectAt(this.x, this.y, MiningManager.a(this.manager).getPosition().getPlane());
                if (dynamicObject == null || n2 == -1 || dynamicObject.getWorldObject().getObjectId() != n2) break block48;
            }
            n = 1;
        }
        if (n != 0) {
            if (MiningManager.a((MiningManager)this.manager).gatheringHazardCounter >= 2) {
                ItemCombinationHandler.d(MiningManager.a(this.manager), 14);
                Player player = MiningManager.a(this.manager);
                player.packetSender.sendStillGraphicToNearbyPlayers(157, this.x, this.y, 0, 1);
                player = MiningManager.a(this.manager);
                player.packetSender.sendGameMessage("Your pickaxe has been broken by the rock!");
                MiningManager.a(this.manager).applyDirectHit(GameUtil.g(MiningManager.a(this.manager).getMaxHitpoints() / 20) + MiningManager.a(this.manager).getMaxHitpoints() / 20 + 1, HitType.NORMAL);
                MiningManager.a(this.manager).getUpdateState().setAnimation(-1);
                player = MiningManager.a(this.manager);
                player.packetSender.sendSoundEffect(42, 1, 0);
                if (MiningManager.a((MiningManager)this.manager).botEnabled) {
                    MiningManager.a((MiningManager)this.manager).currentBotTask.startWalkToBank(MiningManager.a(this.manager));
                }
                cycleEventContainer.stop();
                return;
            }
            MiningManager.a(this.manager).getUpdateState().setAnimation(this.gatheringTool.d());
            ++MiningManager.a((MiningManager)this.manager).gatheringHazardCounter;
            return;
        }
        ObjectManager.getInstance();
        String[] stringArray = ObjectManager.findDynamicObjectAt(this.x, this.y, MiningManager.a(this.manager).getPosition().getPlane());
        if (stringArray != null && stringArray.getWorldObject().getObjectId() != this.rockObjectId) {
            if (MiningManager.a(this.manager).getQuestState(0) != 1) {
                MiningManager.a(this.manager).getDialogueManager().showOneLineStatement("There is no more ore in this rock.");
                MiningManager.a(this.manager).setInteractionTargetId(0);
            }
            stringArray = MiningManager.a(this.manager);
            stringArray.packetSender.sendGameMessage("There is no more ore in this rock.");
            stringArray = MiningManager.a(this.manager);
            stringArray.packetSender.sendSoundEffect(429, 1, 0);
            if (MiningManager.a((MiningManager)this.manager).botEnabled) {
                MiningManager.a(this.manager).interactWithBotObjectTargets(MiningManager.a((MiningManager)this.manager).botInteractionTargetIds);
            }
            cycleEventContainer.stop();
            return;
        }
        if (SkillActionHelper.shouldTriggerRandomEvent(MiningManager.a(this.manager)) && !MiningManager.a((MiningManager)this.manager).botEnabled && !MiningManager.a(this.manager).r()) {
            GameplayHelper.a(MiningManager.a(this.manager), SkillRandomEventNpc.d);
        }
        MiningManager.a(this.manager).getUpdateState().setAnimation(this.gatheringTool.d());
        int n3 = 256;
        if (MiningManager.a(this.manager).eL()) {
            n3 = 86;
        }
        if (this.rockObjectId != 2111 && GameUtil.h(n3) == 0 && MiningManager.a(this.manager).getQuestState(0) == 1) {
            String[] stringArray2 = new String[]{"10/4681", "1/1024", "1/2048", "1/4096", "1/16384"};
            stringArray = new String[]{"1/157", "1/344", "1/688", "1/1376", "1/5504"};
            int[] nArray = new int[]{65000, 1623, 1621, 1619, 1617};
            int n4 = GameUtil.a(MiningManager.a(this.manager).eL() ? stringArray : stringArray2);
            int n5 = nArray[n4];
            if (n5 != 65000) {
                ItemStack itemStack = new ItemStack(n5, 1);
                MiningManager.a(this.manager).getInventoryManager().addItem(itemStack);
                String string = itemStack.getDefinition().getName().replace("Uncut ", "");
                Player player = MiningManager.a(this.manager);
                player.packetSender.sendGameMessage("You just found " + TextUtil.prependIndefiniteArticle(string) + "!");
                if (!MiningManager.a(this.manager).getInventoryManager().e(itemStack)) {
                    if (MiningManager.a((MiningManager)this.manager).botEnabled) {
                        MiningManager.a((MiningManager)this.manager).currentBotTask.startWalkToBank(MiningManager.a(this.manager));
                    }
                    cycleEventContainer.stop();
                    return;
                }
            }
            return;
        }
        n = this.mineChanceLow;
        int n6 = this.mineChanceHigh;
        if (this.rockObjectId == 2111 && MiningManager.a(this.manager).eL()) {
            n = 84;
            n6 = 210;
        }
        if (GameUtil.b(n, n6, MiningManager.a(this.manager).getSkillManager().getCurrentLevels()[14])) {
            String string;
            if (this.rockObjectId == 2111) {
                String[] stringArray3 = new String[]{"1000/2133", "1000/4267", "1000/8533", "100/1422", "10/256", "10/256", "1/32"};
                int[] nArray = new int[]{1625, 1627, 1629, 1623, 1621, 1619, 1617};
                n = GameUtil.a(stringArray3);
                n3 = nArray[n];
            } else {
                n3 = MiningManager.a(this.rockObjectId, this.oreItemId);
            }
            if (n3 == 444) {
                n = n3;
                if (this.manager.a.containsExclusive(MiningManager.a(this.manager).getPosition())) {
                    n = 446;
                }
                MiningManager.a(this.manager).getInventoryManager().addItem(new ItemStack(n, 1));
            } else {
                MiningManager.a(this.manager).getInventoryManager().addItem(new ItemStack(n3, 1));
            }
            Object object = MiningManager.a(this.manager);
            PacketSender packetSender = ((Player)object).packetSender;
            StringBuilder stringBuilder = new StringBuilder("You manage to mine some ");
            if (this.rockObjectId == 2111) {
                string = "gem";
            } else if (this.rockObjectId == 10946) {
                string = "sandstone";
            } else if (this.rockObjectId == 10947) {
                string = "granite";
            } else {
                ItemService.getInstance();
                string = String.valueOf(ItemService.getItemName(this.oreItemId).toLowerCase()) + ".";
            }
            packetSender.sendGameMessage(stringBuilder.append(string).toString());
            if (MiningManager.a(this.manager).getQuestState(0) != 1) {
                String string2;
                DialogueManager dialogueManager = MiningManager.a(this.manager).getDialogueManager();
                StringBuilder stringBuilder2 = new StringBuilder("You manage to mine some ");
                if (this.rockObjectId == 2111) {
                    string2 = "gem";
                } else if (this.rockObjectId == 10946) {
                    string2 = "sandstone";
                } else if (this.rockObjectId == 10947) {
                    string2 = "granite";
                } else {
                    ItemService.getInstance();
                    string2 = String.valueOf(ItemService.getItemName(this.oreItemId).toLowerCase()) + ".";
                }
                dialogueManager.showOneLineStatement(stringBuilder2.append(string2).toString());
                MiningManager.a(this.manager).getDialogueManager().finishDialogue();
                MiningManager.a(this.manager).setInteractionTargetId(0);
                if (MiningManager.a(this.manager).getQuestState(0) == 33 && this.rockObjectId == 3043) {
                    MiningManager.a(this.manager).ea();
                } else if (MiningManager.a(this.manager).getQuestState(0) == 34 && this.rockObjectId == 3042) {
                    MiningManager.a(this.manager).ea();
                }
                MiningManager.a(this.manager).getQuestManager().refreshQuestJournal();
            }
            MiningManager.a(this.manager).getSkillManager().addExperience(14, MiningManager.b(n3, this.baseExperience));
            MiningManager.a(this.manager);
            Player.rollActionReward();
            if (!ServerSettings.wcStyleMiningEnabled || ServerSettings.wcStyleMiningEnabled && GameUtil.a(this.depletionChance)) {
                try {
                    int n7 = SkillActionHelper.getObjectOrientation(this.rockObjectId, this.x, this.y, MiningManager.a(this.manager).getPosition().getPlane());
                    n = SkillActionHelper.getObjectType(this.rockObjectId, this.x, this.y, MiningManager.a(this.manager).getPosition().getPlane());
                    new DynamicObject(MiningManager.f(this.rockObjectId), this.x, this.y, MiningManager.a(this.manager).getPosition().getPlane(), n == 22 ? MiningManager.b(n7) : n7, n == 11 ? 11 : 10, this.rockObjectId, this.respawnTicks);
                }
                catch (Exception exception) {
                    object = exception;
                    exception.printStackTrace();
                }
                if (MiningManager.a((MiningManager)this.manager).botEnabled) {
                    MiningManager.a(this.manager).interactWithBotObjectTargets(MiningManager.a((MiningManager)this.manager).botInteractionTargetIds);
                }
                cycleEventContainer.stop();
                bl = true;
            }
        }
        if (!bl && !MiningManager.a(this.manager).getInventoryManager().e(new ItemStack(MiningManager.a(this.rockObjectId, this.oreItemId), 1))) {
            if (MiningManager.a((MiningManager)this.manager).botEnabled) {
                MiningManager.a((MiningManager)this.manager).currentBotTask.startWalkToBank(MiningManager.a(this.manager));
            }
            cycleEventContainer.stop();
            return;
        }
    }

    @Override
    public final void onStop() {
        MiningManager.a(this.manager).getUpdateState().setAnimation(-1);
    }
}

