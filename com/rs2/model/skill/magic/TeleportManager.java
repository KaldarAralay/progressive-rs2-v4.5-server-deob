/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.magic;

import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.AbyssTeleportTask;
import com.rs2.model.skill.magic.DelayedTeleportTask;
import com.rs2.model.skill.magic.MagicTeleportTask;
import com.rs2.model.skill.magic.ScriptedTeleportTask;
import com.rs2.model.skill.magic.Spellbook;
import com.rs2.model.skill.magic.StandardTeleportTask;
import com.rs2.model.task.CycleEventHandler;

public final class TeleportManager {
    private Player i;
    public static final Position a = new Position(ServerSettings.respawnX, ServerSettings.respawnY, ServerSettings.respawnPlane);
    public static final Position b = new Position(3087, 3495);
    public static final Position c = new Position(2912, 3170);
    public static final Position d = new Position(3104, 3249);
    public static final Position e = new Position(3293, 3162);
    public static final Position f = new Position(2441, 3090);
    public static final Position g = new Position(3317, 3233);
    public static final Position h = new Position(2207, 4941);

    public TeleportManager(Player player) {
        this.i = player;
    }

    public final boolean castSpellbookTeleport(Position object) {
        if (this.i.getEnchantmentChamberController().isInsideChamber() || this.i.getAlchemistPlaygroundController().isInsidePlayground() || this.i.getCreatureGraveyardController().isInsideGraveyard() || this.i.getTelekineticTheatreController().isInsideTheatre()) {
            object = this.i;
            ((Player)object).packetSender.sendGameMessage("You can't teleport out of here.");
            return false;
        }
        if (this.i.isInWilderness() && this.i.getWildernessLevel() > 20) {
            object = this.i;
            ((Player)object).packetSender.sendGameMessage("You can't teleport above level 20 in the wilderness.");
            return false;
        }
        if (this.i.isTeleblocked()) {
            object = this.i;
            ((Player)object).packetSender.sendGameMessage("A magical force prevents you from teleporting.");
            return false;
        }
        if (this.i.isInTeleportRestrictedArea()) {
            object = this.i;
            ((Player)object).packetSender.sendGameMessage("You can't teleport from here.");
            return false;
        }
        this.a(((Position)object).getX(), ((Position)object).getY(), ((Position)object).getPlane(), this.i.getSpellbook() == Spellbook.ANCIENT);
        return true;
    }

    public final boolean b(Position position) {
        if (this.i.getEnchantmentChamberController().isInsideChamber() || this.i.getAlchemistPlaygroundController().isInsidePlayground() || this.i.getCreatureGraveyardController().isInsideGraveyard() || this.i.getTelekineticTheatreController().isInsideTheatre()) {
            Player player = this.i;
            player.packetSender.sendGameMessage("You can't teleport out of here.");
            return false;
        }
        if (this.i.isInWilderness() && this.i.getWildernessLevel() > 30) {
            Player player = this.i;
            player.packetSender.sendGameMessage("You can't teleport above level 30 in the wilderness.");
            return false;
        }
        if (this.i.isTeleblocked()) {
            Player player = this.i;
            player.packetSender.sendGameMessage("A magical force prevents you from teleporting.");
            return false;
        }
        if (this.i.isInTeleportRestrictedArea()) {
            Player player = this.i;
            player.packetSender.sendGameMessage("You can't teleport from here.");
            return false;
        }
        this.i.getUpdateState().setAnimation(714);
        this.i.getUpdateState().setGraphicHeight100(301);
        Player player = this.i;
        player.packetSender.sendSoundEffect(202, 1, 0);
        this.a(position.getX(), position.getY(), position.getPlane(), false);
        return true;
    }

    public final void a(int n, int n2, int n3, String string, int n4, int n5, int n6, int n7) {
        this.i.setActionLocked(true);
        this.i.getAttributes().put("canTakeDamage", Boolean.FALSE);
        CycleEventHandler.getInstance().schedule(this.i, new ScriptedTeleportTask(this, 5, 804, 68, -1, n, n2, 0, null), 1);
    }

    public final void a(int n, int n2, int n3, String string) {
        this.i.setActionLocked(true);
        this.i.getAttributes().put("canTakeDamage", Boolean.FALSE);
        CycleEventHandler.getInstance().schedule(this.i, new StandardTeleportTask(this, n, n2, n3, string), 1);
    }

    private void a(int n, int n2, int n3, boolean bl) {
        if (this.i.isTeleblocked() || this.i.isInTeleportRestrictedArea()) {
            Player player = this.i;
            player.packetSender.sendGameMessage("You can't teleport out of here!");
            return;
        }
        this.i.setActionLocked(true);
        this.i.getAttributes().put("canTakeDamage", Boolean.FALSE);
        CycleEventHandler.getInstance().schedule(this.i, new MagicTeleportTask(this, bl, n, n2, n3), 1);
    }

    public final void a(int n, int n2, int n3) {
        this.a(n, n2, n3, true, null);
    }

    public final void a(int n, int n2, int n3, boolean bl, String string) {
        if (this.i.isTeleblocked()) {
            Player player = this.i;
            player.packetSender.sendGameMessage("A magical force prevents you from teleporting.");
            return;
        }
        if (this.i.isInTeleportRestrictedArea()) {
            Player player = this.i;
            player.packetSender.sendGameMessage("You can't teleport from here.");
            return;
        }
        if (bl) {
            this.i.getUpdateState().setGraphicHeight100(342);
        }
        this.i.getUpdateState().setAnimation(1816);
        this.i.setActionLocked(true);
        this.i.getAttributes().put("canTakeDamage", Boolean.FALSE);
        CycleEventHandler.getInstance().schedule(this.i, new DelayedTeleportTask(this, n, n2, n3, string), 1);
    }

    public final void b(int n, int n2, int n3) {
        if (this.i.isTeleblocked() || this.i.isInTeleportRestrictedArea()) {
            Player player = this.i;
            player.packetSender.sendGameMessage("You can't teleport out of here!");
            return;
        }
        this.i.getUpdateState().setGraphicHeight100(110);
        CycleEventHandler.getInstance().schedule(this.i, new AbyssTeleportTask(this, n, n2, n3), 1);
    }

    static /* synthetic */ Player a(TeleportManager teleportManager) {
        return teleportManager.i;
    }
}

