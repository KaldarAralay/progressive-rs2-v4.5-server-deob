/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.ground;

import com.rs2.model.Entity;
import com.rs2.model.EntityReference;
import com.rs2.model.Position;
import com.rs2.model.ground.GroundItemVisibility;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.ElapsedTicks;

public final class GroundItem {
    private ItemStack a;
    private EntityReference b = null;
    private EntityReference c = null;
    private ElapsedTicks d;
    private GroundItemVisibility e = null;
    private Position f;
    private boolean g;
    private int h;
    private boolean i = false;

    public GroundItem(ItemStack object, Position position, int n, boolean bl) {
        this.a = object;
        position = position.copy();
        object = this;
        this.f = position;
        this.d = new ElapsedTicks();
        this.e = GroundItemVisibility.b;
        this.h = n;
        this.g = true;
    }

    public GroundItem(ItemStack object, Position position, boolean bl) {
        this.a = object;
        position = position.copy();
        object = this;
        this.f = position;
        this.d = new ElapsedTicks();
        this.e = GroundItemVisibility.b;
        this.g = false;
    }

    public GroundItem(ItemStack object, Position position, boolean bl, boolean bl2) {
        this.a = object;
        position = position.copy();
        object = this;
        this.f = position;
        this.d = new ElapsedTicks();
        this.e = GroundItemVisibility.b;
        this.g = false;
        this.i = true;
    }

    public GroundItem(ItemStack object, Position position, boolean bl, Entity entity) {
        this.a = object;
        position = position.copy();
        object = this;
        this.f = position;
        this.d = new ElapsedTicks();
        if (entity != null) {
            this.c = this.b = new EntityReference(entity);
            this.e = GroundItemVisibility.a;
        } else {
            this.e = GroundItemVisibility.b;
        }
        this.g = false;
    }

    public GroundItem(ItemStack itemStack, Entity entity, Position position) {
        this(itemStack, position, false);
        this.b = new EntityReference(entity);
        this.e = GroundItemVisibility.a;
        this.c = this.b;
    }

    public GroundItem(ItemStack itemStack, Entity entity) {
        this(itemStack, entity, entity.getPosition());
    }

    public GroundItem(ItemStack itemStack, Entity entity, Entity entity2, Position position) {
        this(itemStack, entity, position);
        this.c = new EntityReference(entity2);
    }

    public final EntityReference getSource() {
        if (this.b == null) {
            return null;
        }
        return this.b;
    }

    public final EntityReference getOwner() {
        if (this.c == null) {
            return null;
        }
        return this.c;
    }

    public final ItemStack getItem() {
        return this.a;
    }

    public final ElapsedTicks getTimer() {
        return this.d;
    }

    public final int getRespawnDelayTicks() {
        return this.h;
    }

    public final GroundItemVisibility getVisibility() {
        return this.e;
    }

    public final void setVisibility(GroundItemVisibility groundItemVisibility) {
        this.e = groundItemVisibility;
    }

    public final Position getPosition() {
        return this.f;
    }

    public final boolean isRespawning() {
        return this.g;
    }

    public final boolean allowsRestrictedModePickup() {
        return this.i;
    }

    public final boolean canStackWith(GroundItem groundItem) {
        if (groundItem == this) {
            return false;
        }
        if (groundItem.g || this.g) {
            return false;
        }
        if (groundItem.a.getId() != this.a.getId() || groundItem.e != this.e) {
            return false;
        }
        if (!groundItem.f.equals(this.f)) {
            return false;
        }
        if ((long)this.a.getAmount() + (long)groundItem.a.getAmount() > Integer.MAX_VALUE) {
            return false;
        }
        GroundItem groundItem2 = groundItem;
        switch (groundItem2.e) {
            case b: {
                return true;
            }
            case a: {
                boolean bl = false;
                if (this.c != null && this.c.equals(groundItem.c)) {
                    bl = true;
                }
                return bl;
            }
        }
        return false;
    }

    public final boolean isVisibleTo(Player player) {
        boolean bl;
        boolean bl2 = bl = this.f.getPlane() == player.getPosition().getPlane() && player.getLocalViewArea().containsExclusive(this.f);
        if (player.gameMode == 0) {
            return bl;
        }
        if (!bl) {
            return false;
        }
        if (!this.g && this.getOwner() == null && !this.i) {
            return false;
        }
        GroundItem groundItem = this;
        return groundItem.g || this.getOwner() == null || this.getOwner().resolve() == player || this.i;
    }
}

