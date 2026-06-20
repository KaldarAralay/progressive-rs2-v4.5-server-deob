/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model;

import com.rs2.model.Entity;
import com.rs2.model.World;
import com.rs2.model.player.Player;

public class EntityReference {
    private int a;
    private boolean b;
    private Entity c;
    private String d;

    public EntityReference(Entity entity) {
        this.c = entity;
        this.a = entity.getReferenceId();
        this.b = entity.isPlayer();
        if (entity.isPlayer()) {
            entity = (Player)entity;
            this.d = ((Player)entity).getUsername();
        }
    }

    public final Entity resolve() {
        if (this.c == null || this.c.getIndex() == -1) {
            this.c = null;
            Object object = this.b ? World.getPlayers() : World.getNpcs();
            Entity[] entityArray = object;
            int n = ((Entity[])object).length;
            int n2 = 0;
            while (n2 < n) {
                object = entityArray[n2];
                if (object != null) {
                    Player player;
                    if (((Entity)object).isPlayer() && this.b && (player = (Player)object).getUsername().equals(this.d)) {
                        return object;
                    }
                    if (!this.b && ((Entity)object).getReferenceId() == this.a) {
                        this.c = object;
                        break;
                    }
                }
                ++n2;
            }
        }
        return this.c;
    }

    public boolean equals(Object object) {
        if (object == null || !(object instanceof EntityReference) && !(object instanceof Entity)) {
            return false;
        }
        if (object instanceof Entity) {
            if (((Entity)(object = (Entity)object)).isPlayer() && this.b) {
                return ((Player)(object = (Player)object)).getUsername().equals(this.d);
            }
            if (this.c == object) {
                return true;
            }
            if (((Entity)object).isPlayer() == this.b && ((Entity)object).getReferenceId() == this.a) {
                this.c = object;
                return true;
            }
            return false;
        }
        object = (EntityReference)object;
        if (((EntityReference)object).b && this.b) {
            return ((EntityReference)object).d.equals(this.d);
        }
        return ((EntityReference)object).a == this.a && ((EntityReference)object).b == this.b;
    }
}

