/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.travel;

import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.model.task.TickTask;
import com.rs2.model.travel.GnomeGliderDestination;
import com.rs2.model.travel.GnomeGliderLandingTask;
import com.rs2.model.travel.GnomeGliderTravelTask;
import com.rs2.model.travel.HajedyCartRoute;
import com.rs2.model.travel.ShipRoute;
import com.rs2.model.travel.ShipTravelArrivalTask;
import com.rs2.model.travel.TravelBootstrap;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public class TravelManager {
    private int a;
    private int b;

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public static boolean handleGnomeGliderButton(Player var0, int var1_1) {
        var5_4 = GnomeGliderDestination.values();
        var4_6 = var5_4.length;
        var3_7 = 0;
        while (var3_7 < var4_6) {
            block33: {
                block34: {
                    block36: {
                        block35: {
                            var2_14 = var5_4[var3_7];
                            if (var1_1 != var2_14.buttonId) break block33;
                            if (!var0.getInteractionTarget().isNpc()) {
                                return false;
                            }
                            var1_2 = (Npc)var0.getInteractionTarget();
                            if (var0.getInteractionTarget() == null) break block34;
                            var3_8 /* !! */  = var0;
                            var3_8 /* !! */ .packetSender.showInterface(802);
                            var3_8 /* !! */  = var2_14;
                            var4_6 = 0;
                            var3_9 = var3_8 /* !! */ .routeIndex;
                            var5_5 = var1_2.getNpcId();
                            var1_3 = GameUtil.getRegionId(var1_2.getPosition().getX(), var1_2.getPosition().getY());
                            if (var5_5 == 3810 || var1_3 == 11318) {
                                var4_6 = 0;
                            } else if (var5_5 == 3811 || var1_3 == 9782) {
                                var4_6 = 1;
                            } else if (var5_5 == 3809 || var1_3 == 13106) {
                                var4_6 = 3;
                            } else if (var5_5 == 3812 || var1_3 == 11822) {
                                var4_6 = 4;
                            } else if (var5_5 == 1800 || var1_3 == 10030) {
                                var4_6 = 5;
                            }
                            if (var4_6 != var3_9) break block35;
                            v0 = 20;
                            break block36;
                        }
                        switch (var4_6) {
                            case 0: {
                                if (var3_9 == 1) {
                                    v0 = 2;
                                    break;
                                }
                                ** GOTO lbl74
                            }
                            case 1: {
                                if (var3_9 == 0) {
                                    v0 = 1;
                                    break;
                                }
                                if (var3_9 == 2) {
                                    v0 = 3;
                                    break;
                                }
                                if (var3_9 == 3) {
                                    v0 = 4;
                                    break;
                                }
                                if (var3_9 == 4) {
                                    v0 = 7;
                                    break;
                                }
                                if (var3_9 == 5) {
                                    v0 = 25;
                                    break;
                                }
                                ** GOTO lbl74
                            }
                            case 3: {
                                if (var3_9 == 1) {
                                    v0 = 5;
                                    break;
                                }
                                ** GOTO lbl74
                            }
                            case 4: {
                                if (var3_9 == 1) {
                                    v0 = 6;
                                    break;
                                }
                                ** GOTO lbl74
                            }
                            case 5: {
                                if (var3_9 == 1) {
                                    v0 = 11;
                                    break;
                                }
                            }
lbl74:
                            // 7 sources

                            default: {
                                v0 = var1_3 = 0;
                            }
                        }
                    }
                    if (v0 <= 0) {
                        var3_10 = var0;
                        var3_10.packetSender.sendGameMessage("You can't fly there from here.");
                    } else if (var1_3 == 20) {
                        var3_11 = var0;
                        var3_11.packetSender.sendGameMessage("You can't fly to the same place you are at.");
                    } else if (var1_3 == 25) {
                        var3_12 = var0;
                        var3_12.packetSender.sendGameMessage("You can't fly to that place yet.");
                    } else {
                        var3_13 = var0;
                        var3_13.packetSender.sendConfig(153, var1_3);
                        var0.setActionLocked(true);
                        CycleEventHandler.getInstance().schedule(var0, new GnomeGliderTravelTask(var0, var2_14), 3);
                        CycleEventHandler.getInstance().schedule(var0, new GnomeGliderLandingTask(var0), 4);
                    }
                }
                return true;
            }
            ++var3_7;
        }
        return false;
    }

    public TravelManager(TravelBootstrap travelBootstrap) {
        new ArrayList();
        this.a = 20;
        this.b = 5;
    }

    public static boolean handleShipRoute(Player object, ShipRoute shipRoute) {
        int n = shipRoute.fareCoins;
        if (shipRoute == ShipRoute.RELLEKKA_TO_WATERBIRTH && ((Player)object).getQuestState(40) == 1) {
            n = 0;
        }
        if (n > 0) {
            ItemStack itemStack = new ItemStack(995, n);
            if (!((Player)object).getInventoryManager().containsItemStack(itemStack)) {
                if (!((Player)object).botEnabled) {
                    ((Player)object).getDialogueManager().showPlayerOneLineDialogue("Sorry, I don't have enough coins for that.", 599);
                } else {
                    ((Player)object).currentBotTask.startWalkToBank((Player)object);
                }
                ((Player)object).getDialogueManager().finishDialogue();
                return false;
            }
            ((Player)object).getInventoryManager().removeItem(itemStack);
        }
        if (shipRoute == ShipRoute.PORT_SARIM_TO_ENTRANA && ((Player)object).hasRestrictedCombatEquipment()) {
            DialogueManager.continueDialogue((Player)object, 657, 4, 0);
            return false;
        }
        ((Player)object).setActionLocked(true);
        Object object2 = ((Player)object).botEnabled || shipRoute.interfaceDestinationPosition == null ? shipRoute.destinationPosition.copy() : shipRoute.interfaceDestinationPosition.copy();
        ((Player)object).moveTo((Position)object2);
        if (shipRoute.ordinal() < 16) {
            object2 = object;
            ((Player)object2).packetSender.sendConfig(75, shipRoute.ordinal() + 1);
            object2 = object;
            ((Player)object2).packetSender.showInterface(3281);
        } else {
            object2 = object;
            ((Player)object2).packetSender.showInterface(11092);
        }
        object2 = object;
        ((Player)object2).packetSender.sendMusicJingle(shipRoute.jingleDelayTicks > 250 ? 225 : 298, shipRoute.jingleDelayTicks);
        object2 = object;
        ((Player)object2).packetSender.sendMinimapState(2);
        object = new ShipTravelArrivalTask(shipRoute.arrivalDelayTicks, shipRoute, (Player)object);
        World.getTaskScheduler().schedule((TickTask)object);
        return true;
    }

    public static boolean handleHajedyCartRoute(Player player, HajedyCartRoute hajedyCartRoute) {
        if (hajedyCartRoute.fareCoins > 0) {
            ItemStack itemStack = new ItemStack(995, hajedyCartRoute.fareCoins);
            if (!player.getInventoryManager().containsItemStack(itemStack)) {
                player.getDialogueManager().showPlayerOneLineDialogue("Sorry, I don't have enough coins for that.", 599);
                player.getDialogueManager().finishDialogue();
                return false;
            }
            player.getInventoryManager().removeItem(itemStack);
        }
        player.scheduleDelayedMove(hajedyCartRoute.destination.copy());
        return true;
    }
}

