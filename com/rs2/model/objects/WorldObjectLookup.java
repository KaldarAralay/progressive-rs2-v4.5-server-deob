/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.objects;

import com.rs2.cache.CacheStore;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.objects.LoadedWorldObject;
import com.rs2.model.objects.ObjectDefinition;
import com.rs2.model.objects.WorldObjectRegionIndex;
import com.rs2.model.skill.agility.AgilityObstacleHandler;
import java.util.logging.Logger;

public class WorldObjectLookup {
    static {
        Logger.getLogger(WorldObjectLookup.class.getName());
    }

    public static void loadWorldObjects() {
        Object object = CacheStore.getInstance();
        try {
            ((CacheStore)object).getDefinitionIndex().getObjectDefinitionEntries();
            GameplayHelper.e();
            return;
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            return;
        }
    }

    public static LoadedWorldObject findObjectAt(int n, int n2, int n3) {
        Position position = new Position(n, n2, n3);
        World.j().k();
        Object object2 = WorldObjectRegionIndex.getOrCreateRegionBucket(position);
        for (Object object2 : ((AgilityObstacleHandler)object2).getLoadedObjects()) {
            if (!((LoadedWorldObject)object2).getPosition().equals(position)) continue;
            ObjectDefinition objectDefinition = ObjectDefinition.forId(((LoadedWorldObject)object2).getWorldObject().getObjectId());
            if (((LoadedWorldObject)object2).getType() == 0 && !objectDefinition.interactive || ((LoadedWorldObject)object2).getType() == 3 && !objectDefinition.interactive || ((LoadedWorldObject)object2).getType() == 4 && !objectDefinition.interactive || ((LoadedWorldObject)object2).getType() == 22 && !objectDefinition.interactive) continue;
            return object2;
        }
        return null;
    }

    public static LoadedWorldObject findObjectByIdAt(int n, int n2, int n3, int n4) {
        Position position = new Position(n2, n3, n4);
        World.j().k();
        Object object2 = WorldObjectRegionIndex.getOrCreateRegionBucket(position);
        for (Object object2 : ((AgilityObstacleHandler)object2).getLoadedObjects()) {
            if (((LoadedWorldObject)object2).getWorldObject().getObjectId() != n || !((LoadedWorldObject)object2).getPosition().equals(position)) continue;
            return object2;
        }
        return null;
    }

    public static LoadedWorldObject findObjectByNameAt(String string, int n, int n2, int n3) {
        Position position = new Position(n, n2, n3);
        World.j().k();
        Object object2 = WorldObjectRegionIndex.getOrCreateRegionBucket(position);
        for (Object object2 : ((AgilityObstacleHandler)object2).getLoadedObjects()) {
            Object object3;
            Object object4;
            if (ObjectDefinition.forId(((LoadedWorldObject)object2).getWorldObject().getObjectId()) != null) {
                object4 = ObjectDefinition.forId(((LoadedWorldObject)object2).getWorldObject().getObjectId());
                object3 = ((ObjectDefinition)object4).name.toLowerCase();
            } else {
                object3 = object4 = "";
            }
            if (!((String)object3).contains(string.toLowerCase()) || !((LoadedWorldObject)object2).getPosition().equals(position)) continue;
            return object2;
        }
        return null;
    }
}

