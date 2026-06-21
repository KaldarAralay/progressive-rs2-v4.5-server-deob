/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.objects.functions;

import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.functions.WildernessObelisk;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import java.awt.Point;

public class ObeliskTick
extends TickTask {
    private static final int activationDelayTicks = (int)GameUtil.secondsToTicks(5L);
    private WildernessObelisk obelisk;

    private ObeliskTick(WildernessObelisk wildernessObelisk) {
        super(activationDelayTicks, true);
        this.obelisk = wildernessObelisk;
    }

    @Override
    public final void execute() {
        Position[] positionArray;
        Object object;
        int n;
        if (!this.obelisk.active) {
            this.obelisk.active = true;
            WildernessObelisk wildernessObelisk = this.obelisk;
            Position[] positionArray2 = wildernessObelisk.cornerPositions;
            int n2 = wildernessObelisk.cornerPositions.length;
            int n3 = 0;
            while (n3 < n2) {
                Position position = positionArray2[n3];
                new DynamicObject(14825, position.getX(), position.getY(), position.getPlane(), 0, 10, wildernessObelisk.objectId, activationDelayTicks + 3);
                ++n3;
            }
            return;
        }
        this.stop();
        this.obelisk.active = false;
        do {
            n = GameUtil.getRandom().nextInt(WildernessObelisk.values().length);
        } while (WildernessObelisk.values()[n] == this.obelisk);
        Position[] positionArray3 = WildernessObelisk.values()[n];
        Player[] playerArray = World.getPlayers();
        int n4 = playerArray.length;
        int n5 = 0;
        while (n5 < n4) {
            Player player = playerArray[n5];
            if (player != null) {
                object = this.obelisk;
                if (object.teleportBounds.contains(new Point(player.getPosition().getX(), player.getPosition().getY()))) {
                    Position[] positionArray4 = positionArray3;
                    object = this;
                    int n6 = player.getPosition().getX() - ((ObeliskTick)object).obelisk.basePosition.getX();
                    int n7 = player.getPosition().getY() - ((ObeliskTick)object).obelisk.basePosition.getY();
                    player.getTeleportManager().startDelayedTeleport(positionArray4.basePosition.getX() + n6, positionArray4.basePosition.getY() + n7, positionArray4.basePosition.getPlane(), false, "Ancient magic teleports you somewhere in the wilderness");
                }
            }
            ++n5;
        }
        object = this.obelisk;
        positionArray3 = positionArray = object.effectArea.getPositions();
        int n8 = positionArray.length;
        n4 = 0;
        while (n4 < n8) {
            Position position = positionArray3[n4];
            World.sendStillGraphicToNearbyPlayers(GraphicEffect.createHeight0(342), position);
            ++n4;
        }
    }

    private static Position[] buildCornerPositions(WildernessObelisk object) {
        object = ((WildernessObelisk)((Object)object)).basePosition;
        int n = 0;
        Position[] positionArray = new Position[4];
        int n2 = 0;
        while (n2 <= 4) {
            int n3 = 0;
            while (n3 <= 4) {
                positionArray[n++] = new Position(((Position)object).getX() + n2, ((Position)object).getY() + n3, ((Position)object).getPlane());
                n3 += 4;
            }
            n2 += 4;
        }
        return positionArray;
    }

    public static void main(String[] stringArray) {
        ObeliskTick.buildCornerPositions(WildernessObelisk.LEVEL_13);
    }

    public static boolean activateForPlayer(Player player, int n) {
        WildernessObelisk[] wildernessObeliskArray = WildernessObelisk.values();
        int n2 = wildernessObeliskArray.length;
        int n3 = 0;
        while (n3 < n2) {
            WildernessObelisk wildernessObelisk = wildernessObeliskArray[n3];
            if (wildernessObelisk.objectId == n) {
                if (player.isMember()) {
                    if (ServerSettings.freeToPlayWorld) {
                        player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                    } else {
                        if (wildernessObelisk.active) {
                            return true;
                        }
                        World.scheduleTickTask(new ObeliskTick(wildernessObelisk));
                    }
                } else {
                    player.packetSender.sendGameMessage("You need a members account to access members content.");
                }
                return true;
            }
            ++n3;
        }
        return false;
    }

    static /* synthetic */ Position[] getCornerPositions(WildernessObelisk wildernessObelisk) {
        return ObeliskTick.buildCornerPositions(wildernessObelisk);
    }
}

