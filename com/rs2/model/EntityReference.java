/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model;

import com.rs2.model.Entity;
import com.rs2.model.World;
import com.rs2.model.player.Player;

public class EntityReference {
    private int referenceId;
    private boolean playerReference;
    private Entity cachedEntity;
    private String playerUsername;

    public EntityReference(Entity entity) {
        this.cachedEntity = entity;
        this.referenceId = entity.getReferenceId();
        this.playerReference = entity.isPlayer();
        if (entity.isPlayer()) {
            entity = (Player)entity;
            this.playerUsername = ((Player)entity).getUsername();
        }
    }

    public final Entity resolve() {
        if (this.cachedEntity == null || this.cachedEntity.getIndex() == -1) {
            this.cachedEntity = null;
            Object object = this.playerReference ? World.getPlayers() : World.getNpcs();
            Entity[] entityArray = object;
            int n = ((Entity[])object).length;
            int n2 = 0;
            while (n2 < n) {
                object = entityArray[n2];
                if (object != null) {
                    Player player;
                    if (((Entity)object).isPlayer() && this.playerReference && (player = (Player)object).getUsername().equals(this.playerUsername)) {
                        return object;
                    }
                    if (!this.playerReference && ((Entity)object).getReferenceId() == this.referenceId) {
                        this.cachedEntity = object;
                        break;
                    }
                }
                ++n2;
            }
        }
        return this.cachedEntity;
    }

    public boolean equals(Object object) {
        if (object == null || !(object instanceof EntityReference) && !(object instanceof Entity)) {
            return false;
        }
        if (object instanceof Entity) {
            if (((Entity)(object = (Entity)object)).isPlayer() && this.playerReference) {
                return ((Player)(object = (Player)object)).getUsername().equals(this.playerUsername);
            }
            if (this.cachedEntity == object) {
                return true;
            }
            if (((Entity)object).isPlayer() == this.playerReference && ((Entity)object).getReferenceId() == this.referenceId) {
                this.cachedEntity = object;
                return true;
            }
            return false;
        }
        object = (EntityReference)object;
        if (((EntityReference)object).playerReference && this.playerReference) {
            return ((EntityReference)object).playerUsername.equals(this.playerUsername);
        }
        return ((EntityReference)object).referenceId == this.referenceId && ((EntityReference)object).playerReference == this.playerReference;
    }
}

