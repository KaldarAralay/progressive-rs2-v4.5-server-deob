/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.travel.canoe;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.skill.GatheringToolDefinition;
import com.rs2.model.skill.ItemCombinationHandler;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.model.travel.canoe.CanoeBuildTask;
import com.rs2.model.travel.canoe.CanoeStation;
import com.rs2.model.travel.canoe.CanoeTreeCutTask;
import com.rs2.model.travel.canoe.CanoeTreeDefinition;
import com.rs2.model.travel.canoe.CanoeTypeDefinition;

public final class CanoeTravelManager {
    static int CANOE_CONFIG_ID = 674;

    private static boolean canTravelToStation(Player player, CanoeStation enum_, int n) {
        if (enum_ == CanoeStation.WILDERNESS) {
            enum_ = CanoeTypeDefinition.values()[player.builtCanoeTypeConfigValue - 1];
            if (!((CanoeTypeDefinition)enum_).canEnterWilderness) {
                return false;
            }
        }
        return Math.abs(player.canoeStationIndex - n) <= CanoeTypeDefinition.values()[player.builtCanoeTypeConfigValue - 1].travelRange;
    }

    public static void handleCanoeTreeClick(Player player, int n, int n2, int n3) {
        ObjectManager.getInstance();
        Object object = ObjectManager.findDynamicObjectAt(n2, n3, player.getPosition().getPlane());
        if (object != null && object.getWorldObject().getObjectId() != n) {
            return;
        }
        Object object2 = CanoeTreeDefinition.forObjectId(n);
        if (object2 == null) {
            return;
        }
        if (player.builtCanoeTypeConfigValue != 0) {
            if (player.ep[CANOE_CONFIG_ID] == player.builtCanoeTypeConfigValue << object2.getConfigShift()) {
                player.ep[CanoeTravelManager.CANOE_CONFIG_ID] = player.builtCanoeTypeConfigValue + 10 << object2.getConfigShift();
                Player player2 = player;
                player2.packetSender.sendConfig(CANOE_CONFIG_ID, player.ep[CANOE_CONFIG_ID]);
                return;
            }
            if (player.ep[CANOE_CONFIG_ID] == player.builtCanoeTypeConfigValue + 10 << object2.getConfigShift()) {
                Object object3;
                object2 = player;
                int n4 = 0;
                CanoeStation[] canoeStationArray = CanoeStation.values();
                int n5 = canoeStationArray.length;
                int n6 = 0;
                while (n6 < n5) {
                    CanoeStation canoeStation = canoeStationArray[n6];
                    if (n4 == ((Player)object2).canoeStationIndex) {
                        CanoeTreeDefinition canoeTreeDefinition = object2;
                        object3 = canoeTreeDefinition;
                        object3 = canoeStation;
                        ((Player)((Object)canoeTreeDefinition)).packetSender.setInterfaceVisible(((CanoeStation)((Object)object3)).destinationInterfaceIds[0], false);
                        CanoeTreeDefinition canoeTreeDefinition2 = object2;
                        object3 = canoeTreeDefinition2;
                        object3 = canoeStation;
                        ((Player)((Object)canoeTreeDefinition2)).packetSender.setInterfaceVisible(((CanoeStation)((Object)object3)).destinationInterfaceIds[1], true);
                    } else if (CanoeTravelManager.canTravelToStation((Player)object2, canoeStation, n4)) {
                        CanoeTreeDefinition canoeTreeDefinition = object2;
                        object3 = canoeTreeDefinition;
                        object3 = canoeStation;
                        ((Player)((Object)canoeTreeDefinition)).packetSender.setInterfaceVisible(((CanoeStation)((Object)object3)).destinationInterfaceIds[0], true);
                        if (canoeStation != CanoeStation.WILDERNESS) {
                            CanoeTreeDefinition canoeTreeDefinition3 = object2;
                            object3 = canoeTreeDefinition3;
                            object3 = canoeStation;
                            ((Player)((Object)canoeTreeDefinition3)).packetSender.setInterfaceVisible(((CanoeStation)((Object)object3)).destinationInterfaceIds[1], false);
                        }
                    } else {
                        CanoeTreeDefinition canoeTreeDefinition = object2;
                        object3 = canoeTreeDefinition;
                        object3 = canoeStation;
                        ((Player)((Object)canoeTreeDefinition)).packetSender.setInterfaceVisible(((CanoeStation)((Object)object3)).destinationInterfaceIds[0], false);
                        if (canoeStation != CanoeStation.WILDERNESS) {
                            CanoeTreeDefinition canoeTreeDefinition4 = object2;
                            object3 = canoeTreeDefinition4;
                            object3 = canoeStation;
                            ((Player)((Object)canoeTreeDefinition4)).packetSender.setInterfaceVisible(((CanoeStation)((Object)object3)).destinationInterfaceIds[1], false);
                        }
                    }
                    ++n4;
                    ++n6;
                }
                object3 = player;
                ((Player)object3).packetSender.showInterface(18220);
                return;
            }
        }
        player.builtCanoeTypeConfigValue = 0;
        if (player.ep[CANOE_CONFIG_ID] != 10 << object2.getConfigShift()) {
            object = object2;
            object2 = player;
            if (!ServerSettings.woodcuttingEnabled) {
                CanoeTreeDefinition canoeTreeDefinition = object2;
                ((Player)((Object)canoeTreeDefinition)).packetSender.sendGameMessage("This skill is currently disabled.");
                return;
            }
            GatheringToolDefinition gatheringToolDefinition = ItemCombinationHandler.findUsableGatheringTool((Player)object2, 8);
            if (gatheringToolDefinition == null) {
                CanoeTreeDefinition canoeTreeDefinition = object2;
                ((Player)((Object)canoeTreeDefinition)).packetSender.sendGameMessage("You do not have an axe which you have the woodcutting level to use.");
                return;
            }
            if (((Player)object2).getSkillManager().getCurrentLevels()[8] < ((CanoeTreeDefinition)((Object)object)).getRequiredLevel()) {
                CanoeTreeDefinition canoeTreeDefinition = object2;
                ((Player)((Object)canoeTreeDefinition)).packetSender.sendGameMessage("You need a Woodcutting level of " + ((CanoeTreeDefinition)((Object)object)).getRequiredLevel() + " to cut this tree.");
                return;
            }
            ((Entity)object2).getUpdateState().setAnimation(gatheringToolDefinition.getGatherAnimationId(), 0);
            int n7 = ((Entity)object2).nextActionSequence();
            ((Entity)object2).setActiveCycleEvent(new CanoeTreeCutTask((Player)object2, n7, (CanoeTreeDefinition)((Object)object), gatheringToolDefinition));
            CycleEventHandler.getInstance().schedule((Entity)object2, ((Entity)object2).getActiveCycleEvent(), 4);
            return;
        }
        if (player.ep[CANOE_CONFIG_ID] == 10 << object2.getConfigShift()) {
            Object object4;
            CanoeTypeDefinition[] canoeTypeDefinitionArray = CanoeTypeDefinition.values();
            int n8 = canoeTypeDefinitionArray.length;
            n3 = 0;
            while (n3 < n8) {
                object = canoeTypeDefinitionArray[n3];
                if (object != CanoeTypeDefinition.LOG) {
                    object4 = object;
                    if (player.getSkillManager().getCurrentLevels()[8] >= ((CanoeTypeDefinition)((Object)object4)).requiredLevel) {
                        Player player3 = player;
                        object4 = player3;
                        object4 = object;
                        player3.packetSender.setInterfaceVisible(((CanoeTypeDefinition)((Object)object4)).levelInterfaceIds[0], false);
                        Player player4 = player;
                        object4 = player4;
                        object4 = object;
                        player4.packetSender.setInterfaceVisible(((CanoeTypeDefinition)((Object)object4)).levelInterfaceIds[1], true);
                    } else {
                        Player player5 = player;
                        object4 = player5;
                        object4 = object;
                        player5.packetSender.setInterfaceVisible(((CanoeTypeDefinition)((Object)object4)).levelInterfaceIds[0], true);
                        Player player6 = player;
                        object4 = player6;
                        object4 = object;
                        player6.packetSender.setInterfaceVisible(((CanoeTypeDefinition)((Object)object4)).levelInterfaceIds[1], false);
                    }
                }
                ++n3;
            }
            object4 = player;
            ((Player)object4).packetSender.showInterface(18178);
            player.canoeStationIndex = object2.getConfigShift() / 8;
            return;
        }
    }

    public static boolean handleBuildButton(Player player, int n) {
        CanoeTypeDefinition[] canoeTypeDefinitionArray = CanoeTypeDefinition.values();
        int n2 = canoeTypeDefinitionArray.length;
        int n3 = 0;
        while (n3 < n2) {
            CanoeTypeDefinition canoeTypeDefinition = canoeTypeDefinitionArray[n3];
            if (n == canoeTypeDefinition.buttonId) {
                Player player2 = player;
                if (!ServerSettings.woodcuttingEnabled) {
                    Player player3 = player2;
                    player3.packetSender.sendGameMessage("This skill is currently disabled.");
                } else {
                    GatheringToolDefinition gatheringToolDefinition = ItemCombinationHandler.findUsableGatheringTool(player2, 8);
                    if (gatheringToolDefinition == null) {
                        Player player4 = player2;
                        player4.packetSender.sendGameMessage("You do not have an axe which you have the woodcutting level to use.");
                    } else {
                        Object object = canoeTypeDefinition;
                        if (player2.getSkillManager().getCurrentLevels()[8] < object.requiredLevel) {
                            Player player5 = player2;
                            object = player5;
                            object = canoeTypeDefinition;
                            player5.packetSender.sendGameMessage("You need a Woodcutting level of " + object.requiredLevel + " to cut this tree.");
                        } else {
                            player2.getUpdateState().setAnimation(gatheringToolDefinition.getCanoeAnimationId(), 0);
                            object = player2;
                            ((Player)object).packetSender.sendSoundEffect(471, 1, 0);
                            int n4 = player2.nextActionSequence();
                            player2.setActiveCycleEvent(new CanoeBuildTask(player2, n4, canoeTypeDefinition, gatheringToolDefinition));
                            CycleEventHandler.getInstance().schedule(player2, player2.getActiveCycleEvent(), 4);
                        }
                    }
                }
                Player player6 = player;
                player6.packetSender.closeInterfaces();
                return true;
            }
            ++n3;
        }
        return false;
    }

    public static boolean handleDestinationButton(Player player, int n) {
        int n2 = 0;
        CanoeStation[] canoeStationArray = CanoeStation.values();
        int n3 = canoeStationArray.length;
        int n4 = 0;
        while (n4 < n3) {
            CanoeStation canoeStation = canoeStationArray[n4];
            if (n == canoeStation.buttonId) {
                if (CanoeTravelManager.canTravelToStation(player, canoeStation, n2)) {
                    CanoeStation canoeStation2;
                    Object object = canoeStation2 = canoeStation;
                    player.moveTo(object.destination);
                    object = player;
                    object = canoeStation2;
                    ((Player)object).packetSender.sendGameMessage("You arrive at the " + object.displayName + "." + (canoeStation2 == CanoeStation.WILDERNESS ? " There are no trees suitable to make a canoe." : ""));
                    object = player;
                    ((Player)object).packetSender.sendGameMessage("Your canoe sinks into the water after the hard journey.");
                    player.canoeStationIndex = 0;
                    player.builtCanoeTypeConfigValue = 0;
                    player.ep[CanoeTravelManager.CANOE_CONFIG_ID] = 0;
                    object = player;
                    ((Player)object).packetSender.sendConfig(CANOE_CONFIG_ID, player.ep[CANOE_CONFIG_ID]);
                    object = player;
                    ((Player)object).packetSender.closeInterfaces();
                } else {
                    Player player2 = player;
                    player2.packetSender.sendGameMessage("You cannot travel there!");
                }
                return true;
            }
            ++n2;
            ++n4;
        }
        return false;
    }
}

