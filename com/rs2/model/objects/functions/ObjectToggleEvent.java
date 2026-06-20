/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.objects.functions;

import com.rs2.ServerSettings;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.objects.WorldObject;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class ObjectToggleEvent
extends CycleEvent {
    private final /* synthetic */ int restoreObjectId;
    private final /* synthetic */ WorldObject worldObject;
    private final /* synthetic */ int objectType;
    private final /* synthetic */ int toggledObjectId;
    private final /* synthetic */ int toggledOrientation;
    private final /* synthetic */ Player player;

    ObjectToggleEvent(int n, WorldObject worldObject, int n2, int n3, int n4, Player player) {
        this.restoreObjectId = n;
        this.worldObject = worldObject;
        this.objectType = n2;
        this.toggledObjectId = n3;
        this.toggledOrientation = n4;
        this.player = player;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        ObjectManager.getInstance();
        if (ObjectManager.findDynamicObjectByIdAt(this.restoreObjectId, this.worldObject.getPosition().getX(), this.worldObject.getPosition().getY(), this.worldObject.getPosition().getPlane()) != null) {
            int n = this.objectType;
            int n2 = this.worldObject.getPosition().getPlane();
            int n3 = this.worldObject.getPosition().getY();
            int n4 = this.worldObject.getPosition().getX();
            int n5 = this.restoreObjectId;
            ObjectManager objectManager = ObjectManager.getInstance();
            DynamicObject dynamicObject = ObjectManager.findDynamicObjectByIdAt(n5, n4, n3, n2);
            if (dynamicObject != null) {
                ObjectManager.removeObjectCollision(dynamicObject.getWorldObject().getObjectId(), n4, n3, n2, n, dynamicObject.getWorldObject().getOrientation());
                ObjectManager.activeDynamicObjects.remove(dynamicObject);
                objectManager.revertDynamicObject(dynamicObject);
            }
            if (this.restoreObjectId == 883) {
                ObjectManager.getInstance().removeDynamicObjectAt(this.worldObject.getPosition().getX(), this.worldObject.getPosition().getY() + 1, this.worldObject.getPosition().getPlane(), this.objectType);
            }
        } else {
            new DynamicObject(this.toggledObjectId, this.worldObject.getPosition().getX(), this.worldObject.getPosition().getY(), this.worldObject.getPosition().getPlane(), this.toggledOrientation, this.objectType, this.restoreObjectId, 999999);
            if (this.restoreObjectId == 881) {
                new DynamicObject(883, this.worldObject.getPosition().getX(), this.worldObject.getPosition().getY() - 1, this.worldObject.getPosition().getPlane(), this.toggledOrientation, this.objectType, ServerSettings.placeholderObjectId, 999999);
            }
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.player.setActionLocked(false);
    }
}

