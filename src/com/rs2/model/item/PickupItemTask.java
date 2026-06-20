/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.item;

import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemService;
import com.rs2.model.item.ItemStack;
import com.rs2.model.path.PathReachability;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import com.rs2.util.path.PathFinder;

final class PickupItemTask
extends TickTask {
    private /* synthetic */ ItemService a;
    private final /* synthetic */ Player b;
    private final /* synthetic */ int c;
    private final /* synthetic */ int d;
    private final /* synthetic */ Position e;

    PickupItemTask(ItemService itemService, int n, boolean bl, Player player, int n2, int n3, Position position) {
        this.a = itemService;
        this.b = player;
        this.c = n2;
        this.d = n3;
        this.e = position;
        super(1, true);
    }

    @Override
    public final void execute() {
        if (!this.b.isCurrentActionSequence(this.c)) {
            if (this.b.botLootPickupTargets.size() > 0) {
                BotCombatHelper.pickupBotCombatGroundItem(this.b, ((GroundItem)this.b.botLootPickupTargets.get(0)).getItem().getId(), ((GroundItem)this.b.botLootPickupTargets.get(0)).getPosition());
            } else if (this.b.currentBotTask != null) {
                int n;
                GameplayHelper.e(this.b);
                int n2 = n = this.b.isInWilderness() ? 8 : 0;
                if (this.b.getInventoryManager().getItemAmount(this.b.botFoodItemId) <= n && this.b.currentBotTask.getForcedCombatStyle() != 2 || this.b.botTaskReturnToBankRequested) {
                    this.b.currentBotTask.startWalkToBank(this.b);
                } else {
                    this.b.interactWithBotNpcTargets(this.b.botInteractionTargetIds);
                }
            }
            this.stop();
            return;
        }
        GroundItemManager.getInstance();
        GroundItem groundItem = GroundItemManager.findVisibleItem(this.b, this.d, this.e);
        if (groundItem == null) {
            if (this.b.dropPartyFollower) {
                if (GameUtil.h(3) == 0 && this.b.currentBotTask != null) {
                    Position position = this.b.currentBotTask.getRandomTaskAreaPosition();
                    PathFinder.getInstance();
                    PathFinder.findPath(this.b, position.getX(), position.getY(), true, 0, 0);
                }
            } else if (this.b.currentBotTask != null) {
                int n;
                GameplayHelper.e(this.b);
                int n3 = n = this.b.isInWilderness() ? 8 : 0;
                if (this.b.getInventoryManager().getItemAmount(this.b.botFoodItemId) <= n && this.b.currentBotTask.getForcedCombatStyle() != 2 || this.b.botTaskReturnToBankRequested) {
                    this.b.currentBotTask.startWalkToBank(this.b);
                } else {
                    this.b.interactWithBotNpcTargets(this.b.botInteractionTargetIds);
                }
            }
            this.stop();
            return;
        }
        boolean bl = false;
        if (this.e.getX() == 2822 && this.e.getY() == 3355) {
            bl = true;
        }
        if (!GameUtil.a(this.b.getPosition(), this.e, 1)) {
            return;
        }
        boolean bl2 = PathReachability.isReachable(this.b, this.e.getX(), this.e.getY(), true, 0, 0);
        if (!GameUtil.a(this.b.getPosition(), this.e, bl2) && !bl) {
            return;
        }
        if (GameUtil.a(this.b.getPosition(), this.e, true) && !this.b.getPosition().equals(this.e)) {
            return;
        }
        if (this.b.getQuestManager().c(this.d)) {
            this.stop();
            return;
        }
        if (this.b.gameMode != 0) {
            if (!groundItem.isRespawning() && groundItem.getOwner() == null && !groundItem.allowsRestrictedModePickup()) {
                Player player = this.b;
                player.packetSender.sendGameMessage("You are not playing on normal gamemode and cant pick that up.");
                this.stop();
                return;
            }
            if (!groundItem.isRespawning() && groundItem.getOwner() != null && groundItem.getOwner().resolve() != this.b && !groundItem.allowsRestrictedModePickup()) {
                Player player = this.b;
                player.packetSender.sendGameMessage("You are not playing on normal gamemode and cant pick that up.");
                this.stop();
                return;
            }
        }
        if (this.d == 245 && this.e.getX() == 2930 && this.e.getY() == 3515) {
            Player player = this.b;
            player.packetSender.sendGameMessage("You can only take the wine by casting tele grab (temporary).");
            this.stop();
            return;
        }
        if (this.d == 1419) {
            if (this.b.aq(this.d)) {
                Player player = this.b;
                player.packetSender.sendGameMessage("You already have a scythe, you don't need another one.");
                this.stop();
                return;
            }
            this.b.bJ[3] = 1;
        }
        if (this.d >= 5509 && this.d <= 5515 && this.b.aq(this.d)) {
            Player player = this.b;
            player.packetSender.sendGameMessage("I already have that pouch!");
            this.stop();
            return;
        }
        if (this.b.getWalkDirection() >= 0 || this.b.getRunDirection() >= 0) {
            return;
        }
        this.stop();
        if (!GroundItemManager.getInstance().removeForPickup(groundItem, this.b)) {
            Player player = this.b;
            player.packetSender.sendGameMessage("That item does not seem to exist anymore.");
            player = this.b;
            player.packetSender.sendGroundItemRemove(groundItem);
            if (this.b.botEnabled) {
                this.a.a(this.b, this.d, groundItem, false);
            }
            return;
        }
        Player player = this.b;
        player.packetSender.sendSoundEffect(356, 1, 0);
        if (groundItem.getItem().getDefinition().isStackable()) {
            if (this.b.getInventoryManager().addItem(new ItemStack(this.b.getInteractionTargetId(), groundItem.getItem().getAmount(), groundItem.getItem().getMetadata()))) {
                this.b.getEquipmentManager().refreshCarriedValue();
            }
        } else if (this.b.getInventoryManager().addItem(new ItemStack(this.b.getInteractionTargetId(), 1, groundItem.getItem().getMetadata()))) {
            this.b.getEquipmentManager().refreshCarriedValue();
        }
        if (this.b.botEnabled) {
            this.a.a(this.b, this.d, groundItem, true);
        }
    }
}

