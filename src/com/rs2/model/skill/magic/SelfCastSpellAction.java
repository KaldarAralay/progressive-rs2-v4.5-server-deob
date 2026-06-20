/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.magic;

import com.rs2.bot.combat.BotCombatEscapeHandler;
import com.rs2.model.Position;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.util.GameUtil;

final class SelfCastSpellAction
extends MagicSpellAction {
    private final /* synthetic */ SpellDefinition selfSpell;
    private final /* synthetic */ Player caster;

    SelfCastSpellAction(Player player, SpellDefinition spellDefinition, SpellDefinition spellDefinition2, Player player2) {
        this.selfSpell = spellDefinition2;
        this.caster = player2;
        super(player, spellDefinition, (byte)0);
    }

    @Override
    public final boolean prepareCast() {
        switch (this.selfSpell) {
            case BONES_TO_PEACHES: {
                return this.castBonesToFruit(true);
            }
            case BONES_TO_BANANAS: {
                return this.castBonesToFruit(false);
            }
            case CHARGE: {
                if (this.caster.getChargeCooldownTimer().hasElapsed()) {
                    this.caster.activateChargeSpell();
                    this.caster.getAttackDelayTimer().setDelayTicks(this.caster.getAttackDelayTimer().getDelayTicks() + 2);
                    break;
                }
                Player player = this.caster;
                player.packetSender.sendGameMessage("You cannot use this spell yet!");
                return false;
            }
            case aR: {
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(3241 + GameUtil.g(1), 3195 + GameUtil.g(1), 0));
            }
            case aS: {
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(3109 + GameUtil.g(1), 3352 + GameUtil.g(1), 0));
            }
            case aT: {
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(2978 + GameUtil.g(1), 3506 + GameUtil.g(1), 0));
            }
            case aU: {
                if (this.caster.getQuestState(72) != 1) {
                    Object object = QuestDefinition.b(72);
                    object = ((QuestDefinition)object).c();
                    Player player = this.caster;
                    player.packetSender.sendGameMessage("You need to complete " + (String)object + " to do this.");
                    return false;
                }
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(3431 + GameUtil.g(1), 3460 + GameUtil.g(1), 0));
            }
            case aV: {
                if (this.caster.getQuestState(72) != 1) {
                    Object object = QuestDefinition.b(72);
                    object = ((QuestDefinition)object).c();
                    Player player = this.caster;
                    player.packetSender.sendGameMessage("You need to complete " + (String)object + " to do this.");
                    return false;
                }
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(3545 + GameUtil.g(1), 3528 + GameUtil.g(1), 0));
            }
            case aW: {
                if (this.caster.getPlayerRights() < 2) {
                    Player player = this.caster;
                    player.packetSender.sendGameMessage("This spell has been temporarily disabled!");
                    return false;
                }
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(2501 + GameUtil.g(1), 3291 + GameUtil.g(1), 0));
            }
            case aX: {
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(2980, 3762 + GameUtil.g(1), 0));
            }
            case aY: {
                if (this.caster.getQuestState(72) != 1) {
                    Object object = QuestDefinition.b(72);
                    object = ((QuestDefinition)object).c();
                    Player player = this.caster;
                    player.packetSender.sendGameMessage("You need to complete " + (String)object + " to do this.");
                    return false;
                }
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(3565 + GameUtil.g(1), 3314 + GameUtil.g(1), 0));
            }
            case aZ: {
                if (this.caster.getQuestState(62) != 1) {
                    Object object = QuestDefinition.b(62);
                    object = ((QuestDefinition)object).c();
                    Player player = this.caster;
                    player.packetSender.sendGameMessage("You need to complete " + (String)object + " to do this.");
                    return false;
                }
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(2799 + GameUtil.g(1), 9212 + GameUtil.g(1), 0));
            }
            case VARROCK_TELEPORT: {
                boolean bl = this.caster.getTeleportManager().castSpellbookTeleport(new Position(3213 + GameUtil.g(1), 3423 + GameUtil.g(1), 0));
                if (this.caster.botEnabled && !bl && this.caster.botCombatState.startsWith("escape")) {
                    this.caster.botCombatState = "tele";
                    BotCombatEscapeHandler.c(this.caster);
                } else if (this.caster.botEnabled && !bl && this.caster.botCombatState.equals("tele")) {
                    this.caster.botCombatState = "run";
                }
                return bl;
            }
            case LUMBRIDGE_TELEPORT: {
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(3222 + GameUtil.g(1), 3218 + GameUtil.g(1), 0));
            }
            case HOME_TELEPORT: {
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(3222 + GameUtil.g(1), 3218 + GameUtil.g(1), 0));
            }
            case FALADOR_TELEPORT: {
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(2964 + GameUtil.g(1), 3378 + GameUtil.g(1), 0));
            }
            case CAMELOT_TELEPORT: {
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(2757 + GameUtil.g(1), 3479 + GameUtil.g(1), 0));
            }
            case ARDOUGNE_TELEPORT: {
                if (this.caster.getQuestState(71) != 1) {
                    Object object = QuestDefinition.b(71);
                    object = ((QuestDefinition)object).c();
                    Player player = this.caster;
                    player.packetSender.sendGameMessage("You need to complete " + (String)object + " to do this.");
                    return false;
                }
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(2662 + GameUtil.g(1), 3305 + GameUtil.g(1), 0));
            }
            case WATCHTOWER_TELEPORT: {
                if (this.caster.getQuestState(101) != 1) {
                    Object object = QuestDefinition.b(101);
                    object = ((QuestDefinition)object).c();
                    Player player = this.caster;
                    player.packetSender.sendGameMessage("You need to complete " + (String)object + " to do this.");
                    return false;
                }
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(2546 + GameUtil.g(1), 3112 + GameUtil.g(1), 2));
            }
            case TROLLHEIM_TELEPORT: {
                if (this.caster.getPlayerRights() < 2) {
                    Player player = this.caster;
                    player.packetSender.sendGameMessage("This spell has been temporarily disabled!");
                    return false;
                }
                if (this.caster.getQuestState(31) != 1) {
                    Object object = QuestDefinition.b(31);
                    object = ((QuestDefinition)object).c();
                    Player player = this.caster;
                    player.packetSender.sendGameMessage("You need to complete " + (String)object + " to do this.");
                    return false;
                }
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(2910 + GameUtil.g(1), 3612 + GameUtil.g(1), 0));
            }
            case APE_ATOLL_TELEPORT: {
                if (this.caster.getQuestState(62) != 1) {
                    Object object = QuestDefinition.b(62);
                    object = ((QuestDefinition)object).c();
                    Player player = this.caster;
                    player.packetSender.sendGameMessage("You need to complete " + (String)object + " to do this.");
                    return false;
                }
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(2798 + GameUtil.g(1), 2798 + GameUtil.g(1), 1));
            }
            case PADDEWWA_TELEPORT: {
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(3098 + GameUtil.g(1), 9884 + GameUtil.g(1), 0));
            }
            case SENNTISTEN_TELEPORT: {
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(3321 + GameUtil.g(1), 3335 + GameUtil.g(1), 0));
            }
            case CARRALLANGAR_TELEPORT: {
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(3156 + GameUtil.g(1), 3666 + GameUtil.g(1), 0));
            }
            case KHARYRLL_TELEPORT: {
                if (this.caster.getQuestState(72) != 1) {
                    Object object = QuestDefinition.b(72);
                    object = ((QuestDefinition)object).c();
                    Player player = this.caster;
                    player.packetSender.sendGameMessage("You need to complete " + (String)object + " to do this.");
                    return false;
                }
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(3492 + GameUtil.g(1), 3471 + GameUtil.g(1), 0));
            }
            case LASSAR_TELEPORT: {
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(3001 + GameUtil.g(1), 3470 + GameUtil.g(1), 0));
            }
            case DAREEYAK_TELEPORT: {
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(2970 + GameUtil.g(1), 3697 + GameUtil.g(1), 0));
            }
            case ANNAKARL_TELEPORT: {
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(3287 + GameUtil.g(1), 3886 + GameUtil.g(1), 0));
            }
            case GHORROCK_TELEPORT: {
                return this.caster.getTeleportManager().castSpellbookTeleport(new Position(2977 + GameUtil.g(1), 3873 + GameUtil.g(1), 0));
            }
        }
        return true;
    }

    @Override
    public final void applyImpact(HitDefinition hitDefinition) {
    }
}

