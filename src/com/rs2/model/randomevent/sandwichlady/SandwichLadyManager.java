/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.randomevent.sandwichlady;

import com.rs2.model.Entity;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.randomevent.sandwichlady.SandwichLadyCleanupEvent;
import com.rs2.model.randomevent.sandwichlady.SandwichLadyFoodOffer;
import com.rs2.model.randomevent.sandwichlady.SandwichLadyRewardSet;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;
import java.util.HashMap;
import java.util.Map;

public final class SandwichLadyManager {
    private Player player;
    public int selectedOfferIndex;
    private int correctSelectionCount = 0;
    private static final Map rewardSetsByNpcId = new HashMap<Integer, SandwichLadyFoodOffer>();

    static {
        new SandwichLadyFoodOffer(false);
        rewardSetsByNpcId.put(3117, new SandwichLadyFoodOffer(false));
    }

    public SandwichLadyManager(Player player) {
        this.player = player;
    }

    private static SandwichLadyRewardSet getRewardSetForNpcId(int n) {
        return (SandwichLadyRewardSet)rewardSetsByNpcId.get(n);
    }

    public final void openSelectionInterface(int n) {
        SandwichLadyManager.getRewardSetForNpcId(3117);
        String string = "3117";
        Player player = this.player;
        this.player.interfaceAction = string;
        player = this.player;
        player.packetSender.showInterface(16135);
    }

    public final boolean handleButtonClick(int n) {
        Player player = this.player;
        if (player.H == null) {
            return false;
        }
        this.player.getSandwichLadyManager();
        player = this.player;
        Object object = SandwichLadyManager.getRewardSetForNpcId(player.H.getNpcId());
        if (object == null) {
            return false;
        }
        if (object.getButtonIds().contains(n)) {
            if (n == (Integer)object.getButtonIds().get(this.player.getSandwichLadyManager().selectedOfferIndex)) {
                ++this.player.getSandwichLadyManager().correctSelectionCount;
            } else {
                object.punishWrongChoice(this.player);
            }
            if (this.player.getSandwichLadyManager().correctSelectionCount == 1) {
                player = this.player;
                n = 0;
                object = player.H;
                SandwichLadyManager sandwichLadyManager = this.player.getSandwichLadyManager();
                SandwichLadyRewardSet sandwichLadyRewardSet = SandwichLadyManager.getRewardSetForNpcId(((Npc)object).getNpcId());
                GameUtil.g(6);
                player = sandwichLadyManager.player;
                player.packetSender.closeInterfaces();
                Player cfr_ignored_0 = sandwichLadyManager.player;
                ((Entity)object).getUpdateState().setForcedTextAndMarkUpdated(sandwichLadyRewardSet.getMessages()[0]);
                ((Entity)object).getUpdateState().setAnimation(863);
                sandwichLadyManager.player.getSandwichLadyManager();
                sandwichLadyManager.player.getInventoryManager().addItem(sandwichLadyRewardSet.getRewards()[sandwichLadyManager.selectedOfferIndex]);
                CycleEventHandler.getInstance().schedule(sandwichLadyManager.player, new SandwichLadyCleanupEvent(sandwichLadyManager, (Npc)object), 5);
            }
        }
        return true;
    }

    static /* synthetic */ Player getPlayer(SandwichLadyManager sandwichLadyManager) {
        return sandwichLadyManager.player;
    }
}

