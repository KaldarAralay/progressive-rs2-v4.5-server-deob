/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.interaction;

import com.rs2.ServerSettings;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.skill.GatheringToolDefinition;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class MiningShortcutTask
extends CycleEvent {
    private final /* synthetic */ Player a;
    private final /* synthetic */ int b;
    private final /* synthetic */ int c;
    private final /* synthetic */ int d;
    private final /* synthetic */ int e;
    private final /* synthetic */ GatheringToolDefinition f;

    MiningShortcutTask(Player player, int n, int n2, int n3, int n4, GatheringToolDefinition gatheringToolDefinition) {
        this.a = player;
        this.b = n;
        this.c = n2;
        this.d = n3;
        this.e = n4;
        this.f = gatheringToolDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.a.isCurrentActionSequence(this.b)) {
            cycleEventContainer.stop();
            return;
        }
        ObjectManager.getInstance();
        Object object = ObjectManager.findDynamicObjectAt(this.c, this.d, this.a.getPosition().getPlane());
        if (object != null && ((DynamicObject)object).getWorldObject().getObjectId() != this.e) {
            cycleEventContainer.stop();
            return;
        }
        cycleEventContainer.setTickDelay(3);
        this.a.getUpdateState().setAnimation(this.f.getGatherAnimationId());
        object = this.a;
        ((Player)object).packetSender.sendSoundEffect(432, 1, 0);
        if (this.a.temporaryActionValue == 1) {
            int n = SkillActionHelper.getObjectOrientation(this.e, this.c, this.d, this.a.getPosition().getPlane());
            int n2 = SkillActionHelper.getObjectType(this.e, this.c, this.d, this.a.getPosition().getPlane());
            new DynamicObject(ServerSettings.placeholderObjectId, this.c, this.d, this.a.getPosition().getPlane(), n, n2 == 11 ? 11 : 10, this.e, 1);
            Player player = this.a;
            player.packetSender.queueRelativeMovementStep(this.a.getPosition().getX() < 2840 ? 3 : -3, 0, true);
            cycleEventContainer.stop();
        }
        ++this.a.temporaryActionValue;
    }

    @Override
    public final void onStop() {
        this.a.getUpdateState().setAnimation(-1);
    }
}

