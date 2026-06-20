/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.firemaking;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.skill.firemaking.FiremakingLog;
import com.rs2.model.skill.firemaking.FiremakingTask;
import com.rs2.model.task.CycleEventHandler;

public final class FiremakingHandler {
    private Player player;
    private static int[] fireObjectIds = new int[]{2732, 11404, 11405, 11406};

    public FiremakingHandler(Player player) {
        this.player = player;
    }

    /*
     * WARNING - void declaration
     */
    public final void startFiremaking(int n, int n2, boolean bl, int n3, int n4, int n5) {
        block16: {
            GroundItem groundItem;
            FiremakingLog firemakingLog;
            void var9_19;
            void var3_7;
            int n6;
            Object object;
            Object object2;
            int n7;
            block17: {
                block15: {
                    void player;
                    boolean groundItem2;
                    Object object3;
                    block14: {
                        if (!ServerSettings.firemakingEnabled) {
                            Player player2 = this.player;
                            player2.packetSender.sendGameMessage("This skill is currently disabled.");
                            return;
                        }
                        n = n == 590 ? n2 : n;
                        n2 = n;
                        FiremakingLog[] n62 = FiremakingLog.values();
                        n7 = n62.length;
                        int firemakingLog2 = 0;
                        while (firemakingLog2 < n7) {
                            FiremakingLog firemakingLog22 = n62[firemakingLog2];
                            if (firemakingLog22.getLogItemId() == n2) {
                                object3 = firemakingLog22;
                                break block14;
                            }
                            ++firemakingLog2;
                        }
                        object3 = object2 = null;
                    }
                    if (object3 == null) {
                        return;
                    }
                    if (!this.player.getInventoryManager().containsItem(590)) {
                        Player player3 = this.player;
                        player3.packetSender.sendGameMessage("You need a tinderbox to light this fire.");
                        return;
                    }
                    object = this.player;
                    if (System.currentTimeMillis() < 200L) {
                        return;
                    }
                    if (this.player.x() || this.player.o() || this.player.isInDuelArena()) {
                        Player player4 = this.player;
                        player4.packetSender.sendGameMessage("You can't light a fire here.");
                        return;
                    }
                    ObjectManager.getInstance();
                    object = ObjectManager.findDynamicObjectAt(groundItem2 ? 1 : 0, n6, (int)player);
                    if (object != null) {
                        Player object4 = this.player;
                        object4.packetSender.sendGameMessage("You can't light a fire here.");
                        return;
                    }
                    if (!SkillActionHelper.checkSkillRequirement(this.player, 11, ((FiremakingLog)((Object)object2)).getRequiredLevel(), "light these logs")) {
                        return;
                    }
                    void v1 = groundItem2;
                    groundItem2 = bl;
                    var3_7 = player;
                    var9_19 = n6;
                    n7 = v1;
                    firemakingLog = object2;
                    object2 = this.player;
                    object = this;
                    n6 = firemakingLog.getLogItemId();
                    if (groundItem2) break block15;
                    if (!((Player)object2).getInventoryManager().removeItem(new ItemStack(n6))) break block16;
                    groundItem = new GroundItem(new ItemStack(n6), (Entity)object2);
                    GroundItemManager.getInstance().spawn(groundItem);
                    break block17;
                }
                GroundItemManager.getInstance();
                groundItem = GroundItemManager.findVisibleItem((Player)object2, n6, new Position(n7, (int)var9_19, ((Entity)object2).getPosition().getPlane()));
                if (groundItem == null) break block16;
            }
            if (((Player)object2).getQuestState(0) != 1) {
                ((Player)object2).getDialogueManager().a("Please wait.", "", "Your character is now attempting to light the fire.", "This should only take a few seconds.", "", true);
            }
            Object n10 = object2;
            ((Player)n10).packetSender.sendGameMessage("You attempt to light the logs.");
            ((Entity)object2).getUpdateState().setAnimation(733);
            n10 = object2;
            ((Player)n10).packetSender.sendSoundEffect(375, 1, 0);
            int n8 = ((Entity)object2).nextActionSequence();
            ((Entity)object2).setActiveCycleEvent(new FiremakingTask((FiremakingHandler)object, (Player)object2, n8, groundItem, n6, firemakingLog, n7, (int)var9_19, (int)var3_7));
            CycleEventHandler.getInstance().schedule((Entity)object2, ((Entity)object2).getActiveCycleEvent(), 4);
        }
    }

    public static final boolean isFireObjectId(int n) {
        int[] nArray = fireObjectIds;
        int n2 = 0;
        while (n2 < 4) {
            int n3 = nArray[n2];
            if (n3 == n) {
                return true;
            }
            ++n2;
        }
        return false;
    }
}

