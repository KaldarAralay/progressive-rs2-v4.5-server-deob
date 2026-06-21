/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat;

import com.rs2.bot.combat.BotPvpCombatHandler;
import com.rs2.model.Entity;
import com.rs2.model.combat.AttackValidationResult;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.combat.attack.CombatAttackState;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.skill.smithing.SmithingHandler;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class CombatCycleEvent
extends CycleEvent {
    private Entity attacker;
    private Entity target;
    private int actionSequence;

    public CombatCycleEvent(Entity entity, Entity entity2) {
        this.attacker = entity;
        this.target = entity2;
        this.actionSequence = entity.nextActionSequence();
    }

    @Override
    public final void execute(CycleEventContainer object) {
        try {
            Object object2;
            Player player;
            if (!this.attacker.isCurrentActionSequence(this.actionSequence) || this.attacker.getCombatTarget() == null) {
                Player player2;
                if (this.attacker.isPlayer() && (player2 = (Player)this.attacker).getUsername().toLowerCase().equals("mod test")) {
                    System.out.println("check combat cycle3 " + this.attacker.getCombatTarget() + " " + this.target.isDead());
                }
                ((CycleEventContainer)object).stop();
                return;
            }
            Object object3 = CombatCycleEvent.validateAttack(this.attacker, this.target);
            if (object3 != AttackValidationResult.VALID) {
                ((CycleEventContainer)object).stop();
                CombatManager.stopCombat(this.attacker);
                if (this.attacker.isPlayer()) {
                    Player player3 = (Player)this.attacker;
                    if (player3.currentBotTask != null) {
                        player3.interactWithBotNpcTargets(player3.botInteractionTargetIds);
                    }
                    switch (object3) {
                        case INVALID_TARGET_LOCATION: {
                            object = (Player)this.attacker;
                            ((Player)object).packetSender.sendGameMessage("You can't attack that npc from here.");
                            return;
                        }
                        case WILDERNESS_LEVEL_MISMATCH: {
                            object = (Player)this.attacker;
                            ((Player)object).packetSender.sendGameMessage("Your level difference is too great!");
                            object = (Player)this.attacker;
                            ((Player)object).packetSender.sendGameMessage("You need to move deeper into the Wilderness.");
                            CombatManager.stopCombat((Player)this.attacker);
                            return;
                        }
                        case ALREADY_IN_COMBAT: {
                            object = (Player)this.attacker;
                            ((Player)object).packetSender.sendGameMessage("You are already under attack!");
                            return;
                        }
                        case NOT_DUEL_OPPONENT: {
                            object = (Player)this.attacker;
                            ((Player)object).packetSender.sendGameMessage("That player is not your opponent!");
                            return;
                        }
                        case TARGET_NOT_IN_WILDERNESS: {
                            object = (Player)this.attacker;
                            ((Player)object).packetSender.sendGameMessage("That player is not in the wilderness!");
                        }
                    }
                }
                return;
            }
            object3 = CombatCycleEvent.validateAttack(this.target, this.attacker);
            if (object3 != AttackValidationResult.VALID) {
                ((CycleEventContainer)object).stop();
                CombatManager.stopCombat(this.attacker);
                if (this.attacker.isPlayer()) {
                    Player player4 = (Player)this.attacker;
                    if (player4.currentBotTask != null) {
                        player4.interactWithBotNpcTargets(player4.botInteractionTargetIds);
                    }
                    switch (object3) {
                        case WILDERNESS_LEVEL_MISMATCH: {
                            object = (Player)this.attacker;
                            ((Player)object).packetSender.sendGameMessage("Your level difference is too great!");
                            object = (Player)this.attacker;
                            ((Player)object).packetSender.sendGameMessage("You need to move deeper into the Wilderness.");
                            CombatManager.stopCombat((Player)this.attacker);
                            return;
                        }
                        case ALREADY_IN_COMBAT: {
                            object = (Player)this.attacker;
                            ((Player)object).packetSender.sendGameMessage("That " + (this.target.isPlayer() ? "player" : "monster") + " is already in combat!");
                            return;
                        }
                        case NOT_DUEL_OPPONENT: {
                            object = (Player)this.attacker;
                            ((Player)object).packetSender.sendGameMessage("That player is not your opponent!");
                        }
                    }
                }
                return;
            }
            if (this.attacker.isPlayer() && this.target.isPlayer()) {
                player = (Player)this.attacker;
                object3 = (Player)this.target;
                if (player.botEnabled || ((Player)object3).botEnabled) {
                    if (player.botCombatState != null) {
                        return;
                    }
                    BotPvpCombatHandler.startBotPvpCombatTicks(player, (Player)object3);
                }
            }
            if (this.attacker.isPlayer() && this.target.isNpc()) {
                player = (Player)this.attacker;
                object3 = (Npc)this.target;
                if (player.botEnabled) {
                    if (player.currentBotTask == null) {
                        return;
                    }
                    if (!player.botTaskState.equals("do task")) {
                        return;
                    }
                    player.currentBotTask.startNpcCombatTick(player, (Npc)object3);
                }
            }
            int n = GameUtil.getDistance(this.attacker.getPosition(), this.target.getPosition());
            object3 = new LinkedList();
            n = this.attacker.collectCombatAttackOptions((List)object3, this.target, n);
            CombatAttack combatAttack = null;
            boolean bl = this.attacker.getAttackDelayTimer().hasElapsed();
            if (bl) {
                Collections.shuffle(object3);
            }
            Iterator iterator = object3.iterator();
            while (iterator.hasNext()) {
                object2 = (SmithingHandler)iterator.next();
                CombatAttackState combatAttackState = ((SmithingHandler)object2).getState();
                if (this.attacker.isStunned()) {
                    combatAttackState = CombatAttackState.b;
                }
                this.attacker.getUpdateState().setFaceEntity(this.target.getEncodedIndex());
                if (combatAttackState == CombatAttackState.b) {
                    combatAttack = ((SmithingHandler)object2).getAttack();
                    continue;
                }
                if (combatAttackState != CombatAttackState.c) continue;
                if (!this.attacker.isPlayer()) {
                    this.attacker.setAttackRange(((SmithingHandler)object2).getAttack().getAttackRange());
                }
                if (bl) {
                    int n2 = ((SmithingHandler)object2).getAttack().execute((CycleEventContainer)object);
                    if (n2 == -1) {
                        Player player5;
                        CombatManager.stopCombat(this.attacker);
                        if (this.attacker.isPlayer() && (player5 = (Player)this.attacker).getUsername().toLowerCase().equals("mod test")) {
                            System.out.println("check combat cycle2");
                        }
                        ((CycleEventContainer)object).stop();
                        return;
                    }
                    this.target.getRecentCombatTimer().setTargetDelay(this.attacker, 17);
                    this.target.getSingleCombatTimer().setTargetDelay(this.attacker, 8);
                    this.attacker.setAttackDelayTicks(n2);
                }
                return;
            }
            if (combatAttack == null) {
                if (n <= object3.size()) {
                    CombatManager.stopCombat(this.attacker);
                    if (this.attacker.isPlayer() && ((Player)(object2 = (Player)this.attacker)).getUsername().toLowerCase().equals("mod test")) {
                        System.out.println("check combat cycle1");
                    }
                    ((CycleEventContainer)object).stop();
                }
                this.attacker.getMovementQueue().clear();
                return;
            }
            if (!this.attacker.isPlayer()) {
                this.attacker.setAttackRange(combatAttack.getAttackRange());
            }
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    @Override
    public final void onStop() {
    }

    public static AttackValidationResult validateAttack(Entity entity, Entity entity2) {
        if (entity2 == null || entity2.isDead()) {
            return AttackValidationResult.INVALID_TARGET;
        }
        if (entity2.isPlayer() && !((Player)entity2).isRegistered() || !entity2.getPosition().isWithinViewport(entity.getPosition())) {
            return AttackValidationResult.INVALID_TARGET;
        }
        if (entity2.getMaxHitpoints() <= 0) {
            return AttackValidationResult.INVALID_TARGET;
        }
        if (entity2.isPlayer() && ((Player)entity2).cn) {
            return AttackValidationResult.INVALID_TARGET_LOCATION;
        }
        if (entity2.isNpc() && (((Npc)entity2).getNpcId() == 2440 ? entity.getPosition().getY() < 10141 : (((Npc)entity2).getNpcId() == 2443 ? entity.getPosition().getX() < 2543 : ((Npc)entity2).getNpcId() == 2446 && entity.getPosition().getY() > 10145))) {
            return AttackValidationResult.INVALID_TARGET_LOCATION;
        }
        if (!(entity.isInMultiCombatArea() && entity2.isInMultiCombatArea() || entity.getSingleCombatTimer().getTarget() == null || entity.getSingleCombatTimer().hasElapsed() || entity.getSingleCombatTimer().getTarget() == entity2)) {
            return AttackValidationResult.ALREADY_IN_COMBAT;
        }
        int n = entity.isPlayer() && entity2.isPlayer() ? 1 : 0;
        if (n != 0) {
            if (entity.isInDuelArena() && ((Player)entity).getDuelSession().getOpponent() != entity2) {
                return AttackValidationResult.NOT_DUEL_OPPONENT;
            }
            if (entity.isInWilderness()) {
                int n2;
                int n3;
                n = entity.getWildernessLevel();
                if (n < Math.abs((n3 = ((Player)entity).getCombatLevel()) - (n2 = ((Player)entity2).getCombatLevel()))) {
                    return AttackValidationResult.WILDERNESS_LEVEL_MISMATCH;
                }
            } else if (entity.isPlayer() && !entity.isInDuelArena()) {
                return AttackValidationResult.TARGET_NOT_IN_WILDERNESS;
            }
        }
        return AttackValidationResult.VALID;
    }
}

