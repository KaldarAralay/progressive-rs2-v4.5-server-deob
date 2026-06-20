/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.JunglePotionQuest;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;

final class JunglePotionHerbSearchEvent
extends CycleEvent {
    private final /* synthetic */ Player a;
    private final /* synthetic */ int b;
    private final /* synthetic */ int c;
    private final /* synthetic */ int d;
    private final /* synthetic */ int e;
    private final /* synthetic */ int f;
    private final /* synthetic */ boolean g;

    JunglePotionHerbSearchEvent(JunglePotionQuest junglePotionQuest, Player player, int n, int n2, int n3, int n4, int n5, boolean bl) {
        this.a = player;
        this.b = n;
        this.c = n2;
        this.d = n3;
        this.e = n4;
        this.f = n5;
        this.g = bl;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.a.isCurrentActionSequence(this.b)) {
            cycleEventContainer.stop();
            return;
        }
        int n = 0;
        ObjectManager.getInstance();
        DynamicObject dynamicObject = ObjectManager.findDynamicObjectAt(this.c, this.d, this.a.getPosition().getPlane());
        if (dynamicObject != null && dynamicObject.getWorldObject().getObjectId() != this.e) {
            cycleEventContainer.stop();
            return;
        }
        cycleEventContainer.setTickDelay(3);
        this.a.getUpdateState().setAnimation(832);
        if (GameUtil.randomInt(5) == 0) {
            n = this.f;
            this.a.getInventoryManager().addItem(new ItemStack(n, 1));
            this.a.getDialogueManager().showItemMessage("You find a herb.", new ItemStack(n, 1));
            if (this.g) {
                try {
                    n = SkillActionHelper.getObjectOrientation(this.e, this.c, this.d, this.a.getPosition().getPlane());
                    int n2 = SkillActionHelper.getObjectType(this.e, this.c, this.d, this.a.getPosition().getPlane());
                    new DynamicObject(2576, this.c, this.d, this.a.getPosition().getPlane(), n, n2, this.e, 10);
                }
                catch (Exception exception) {
                    Exception exception2 = exception;
                    exception.printStackTrace();
                }
            }
            cycleEventContainer.stop();
            n = 1;
        }
        if (n == 0 && !this.a.getInventoryManager().canAddItem(new ItemStack(this.f, 1))) {
            cycleEventContainer.stop();
            return;
        }
    }

    @Override
    public final void onStop() {
        this.a.getUpdateState().setAnimation(-1);
    }
}

