/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.magetrainingarena;

import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.gameplay.magetrainingarena.EnchantmentChamberColorTask;
import com.rs2.model.gameplay.magetrainingarena.MageTrainingArenaLobby;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import java.util.Random;

public final class EnchantmentChamberController {
    private static final Position[] entryPositions = new Position[]{new Position(3373, 9640, 0), new Position(3374, 9639, 0), new Position(3375, 9640, 0), new Position(3374, 9641, 0), new Position(3362, 9650, 0), new Position(3363, 9651, 0), new Position(3364, 9650, 0), new Position(3363, 9649, 0), new Position(3363, 9628, 0), new Position(3364, 9629, 0), new Position(3363, 9630, 0), new Position(3362, 9629, 0), new Position(3354, 9640, 0), new Position(3353, 9639, 0), new Position(3352, 9640, 0), new Position(3353, 9641, 0)};
    private Player player;
    private static int[] bonusRuneRewardItemIds = new int[]{560, 564, 565};
    public int pizazzPoints;
    private int[] enchantmentOrbCountsBySpellTier = new int[6];
    private static final String[] bonusColorNames = new String[]{"red", "yellow", "green", "blue"};
    private static String currentBonusColor;
    static Random random;

    static {
        random = new Random();
    }

    public static Npc findEnchantmentGuardianNpc() {
        Npc[] npcArray = World.getNpcs();
        int n = npcArray.length;
        int n2 = 0;
        while (n2 < n) {
            Npc npc = npcArray[n2];
            if (npc != null && npc.getDefinition().getId() == 3100) {
                return npc;
            }
            ++n2;
        }
        return null;
    }

    public EnchantmentChamberController(Player player) {
        this.player = player;
    }

    public static void startBonusColorCycle() {
        currentBonusColor = "red";
        World.scheduleTickTask(new EnchantmentChamberColorTask(40));
    }

    public final boolean isInsideChamber() {
        int n = this.player.getPosition().getX();
        int n2 = this.player.getPosition().getY();
        return this.player.getPosition().getPlane() == 0 && n >= 3334 && n <= 3388 && n2 >= 9610 && n2 <= 9664;
    }

    public final void refreshPizazzInterface() {
        Player player = this.player;
        player.packetSender.showWalkableInterface(15917);
        player = this.player;
        player.packetSender.sendInterfaceText("" + this.pizazzPoints, 15921);
    }

    public final void refreshBonusColorIndicator(String string) {
        EnchantmentChamberController enchantmentChamberController = this;
        Player player = enchantmentChamberController.player;
        player.packetSender.setInterfaceHiddenFlag(1, 15924);
        player = enchantmentChamberController.player;
        player.packetSender.setInterfaceHiddenFlag(1, 15922);
        player = enchantmentChamberController.player;
        player.packetSender.setInterfaceHiddenFlag(1, 15923);
        player = enchantmentChamberController.player;
        player.packetSender.setInterfaceHiddenFlag(1, 15925);
        if (string == "red") {
            player = this.player;
            player.packetSender.setInterfaceHiddenFlag(0, 15924);
            return;
        }
        if (string == "yellow") {
            player = this.player;
            player.packetSender.setInterfaceHiddenFlag(0, 15922);
            return;
        }
        if (string == "green") {
            player = this.player;
            player.packetSender.setInterfaceHiddenFlag(0, 15923);
            return;
        }
        if (string == "blue") {
            player = this.player;
            player.packetSender.setInterfaceHiddenFlag(0, 15925);
        }
    }

    /*
     * Unable to fully structure code
     */
    public final boolean handleObjectAction(int var1_1) {
        block17: {
            block19: {
                block20: {
                    block18: {
                        if (var1_1 == 10802 || var1_1 == 10799 || var1_1 == 10800 || var1_1 == 10801) {
                            var2_6 = var1_1;
                            var1_2 = this;
                            if (var1_2.player.getInventoryManager().getContainer().getFreeSlots() <= 0) {
                                var4_11 = var1_2.player;
                                var4_11.packetSender.sendGameMessage("Not enough space in your inventory.");
                            } else {
                                var1_2.player.getUpdateState().setAnimation(832, 0);
                                v0 = var1_2.player.getInventoryManager();
                                var3_20 = var2_6;
                                switch (var3_20) {
                                    case 10802: {
                                        v1 = 6901;
                                        break;
                                    }
                                    case 10799: {
                                        v1 = 6899;
                                        break;
                                    }
                                    case 10800: {
                                        v1 = 6898;
                                        break;
                                    }
                                    case 10801: {
                                        v1 = 6900;
                                        break;
                                    }
                                    default: {
                                        v1 = 0;
                                    }
                                }
                                v0.addItem(new ItemStack(v1));
                            }
                            return true;
                        }
                        if (var1_1 == 10782 && this.isInsideChamber()) {
                            var1_3 = this;
                            var4_12 = var1_3.player;
                            var4_12.packetSender.sendGameMessage("You've left the Enchantment Chamber");
                            var4_12 = var1_3.player;
                            var4_12.packetSender.showWalkableInterface(-1);
                            var1_3.player.moveTo(MageTrainingArenaLobby.LOBBY_POSITION);
                            var2_7 = var1_3;
                            var2_7.player.getInventoryManager().removeItem(new ItemStack(6901, var2_7.player.getInventoryManager().getItemAmount(6901)));
                            var2_7.player.getInventoryManager().removeItem(new ItemStack(6899, var2_7.player.getInventoryManager().getItemAmount(6899)));
                            var2_7.player.getInventoryManager().removeItem(new ItemStack(6898, var2_7.player.getInventoryManager().getItemAmount(6898)));
                            var2_7.player.getInventoryManager().removeItem(new ItemStack(6900, var2_7.player.getInventoryManager().getItemAmount(6900)));
                            var2_7.player.getInventoryManager().removeItem(new ItemStack(6902, var2_7.player.getInventoryManager().getItemAmount(6902)));
                            var2_7.player.getInventoryManager().removeItem(new ItemStack(6903, var2_7.player.getInventoryManager().getItemAmount(6903)));
                            var4_12 = var1_3.player;
                            var4_12.packetSender.showWalkableInterface(-1);
                            return true;
                        }
                        if (var1_1 == 10779) {
                            var1_4 = this;
                            if (var1_4.player.getSkillManager().getCurrentLevels()[6] < 7) {
                                var4_13 = var1_4.player;
                                var4_13.packetSender.sendGameMessage("You need a magic level of 7 to enter here.");
                            } else {
                                var4_14 = var1_4.player;
                                var4_14.packetSender.sendGameMessage("You've entered the Enchantment Chamber");
                                var2_8 = EnchantmentChamberController.random.nextInt(16);
                                var1_4.player.moveTo(EnchantmentChamberController.entryPositions[var2_8]);
                                var1_4.player.getEnchantmentChamberController().refreshBonusColorIndicator(EnchantmentChamberController.currentBonusColor);
                            }
                            return true;
                        }
                        if (var1_1 != 10803) break block17;
                        var1_5 = this;
                        if (var1_5.player.getInventoryManager().getContainer().containsItem(6902)) break block18;
                        var4_15 = var1_5.player;
                        var4_15.packetSender.sendGameMessage("You don't have any orbs to deposit.");
                        break block19;
                    }
                    var2_9 = var1_5.player.getInventoryManager().getContainer().getItemAmount(6902);
                    var3_21 = 0;
                    if (var1_5.pizazzPoints >= 16000 || !var1_5.player.hasActiveProgressHat()) break block20;
                    var1_5.pizazzPoints = (int)((double)var1_5.pizazzPoints + Math.floor(var2_9 / 10) * 10.0);
                    var4_16 = 0;
                    ** GOTO lbl92
                    {
                        v2 = var4_16;
                        var1_5.enchantmentOrbCountsBySpellTier[v2] = var1_5.enchantmentOrbCountsBySpellTier[v2] - 10;
                        var1_5.pizazzPoints += EnchantmentChamberController.getBonusPizazzPointsForSpellTier(var4_16);
                        var3_21 += EnchantmentChamberController.getBonusPizazzPointsForSpellTier(var4_16);
                        do {
                            if (var1_5.enchantmentOrbCountsBySpellTier[var4_16] - 10 >= 0) continue block6;
                            ++var4_16;
lbl92:
                            // 2 sources

                        } while (var4_16 < var1_5.enchantmentOrbCountsBySpellTier.length);
                    }
                    if (var1_5.pizazzPoints >= 16000) {
                        var1_5.pizazzPoints = 16000;
                    }
                }
                var4_17 = var1_5.player;
                var4_17.packetSender.showChatboxInterface(359);
                var4_17 = var1_5.player;
                var4_17.packetSender.sendInterfaceText("You've just deposited " + var2_9 + " orbs, earning you " + (int)(Math.floor(var2_9 / 10) * 10.0) + " Enchanting Pizazz", 360);
                var4_17 = var1_5.player;
                var4_17.packetSender.sendInterfaceText("Points and " + var3_21 + " extra points for the enchanting spell used.", 361);
                var1_5.resetEnchantSpellTierCounts();
                var1_5.player.getInventoryManager().removeItem(new ItemStack(6902, var1_5.player.getInventoryManager().getItemAmount(6902)));
                var1_5.player.getUpdateState().setAnimation(832, 0);
                if (var2_9 >= 20) {
                    var4_18 = EnchantmentChamberController.bonusRuneRewardItemIds[EnchantmentChamberController.random.nextInt(EnchantmentChamberController.bonusRuneRewardItemIds.length)];
                    var2_10 = new ItemStack(var4_18, 3);
                    var1_5.player.getInventoryManager().addOrDropItem(var2_10);
                    var4_19 = var1_5.player;
                    var4_19.packetSender.sendGameMessage("Congratulations! You've been rewarded with 3 " + var2_10.getDefinition().getName() + "s for your efforts.");
                }
            }
            return true;
        }
        return false;
    }

    private static int getBasePizazzPointsForSpellTier(int n) {
        switch (n) {
            case 0: {
                return 2;
            }
            case 1: {
                return 4;
            }
            case 2: {
                return 6;
            }
            case 3: {
                return 8;
            }
            case 4: {
                return 10;
            }
            case 5: {
                return 12;
            }
        }
        return 0;
    }

    private static int getBonusPizazzPointsForSpellTier(int n) {
        switch (n) {
            case 0: {
                return 1;
            }
            case 1: {
                return 2;
            }
            case 2: {
                return 3;
            }
            case 3: {
                return 4;
            }
            case 4: {
                return 5;
            }
            case 5: {
                return 6;
            }
        }
        return 0;
    }

    public static boolean isEnchantableChamberItem(int n) {
        return n == 6901 || n == 6899 || n == 6898 || n == 6900 || n == 6903;
    }

    public final void enchantChamberItem(int n, int n2) {
        if (n2 != 6901 && n2 != 6899 && n2 != 6898 && n2 != 6900 && n2 != 6903) {
            return;
        }
        if (n2 == 6903) {
            if (this.player.hasActiveProgressHat()) {
                this.pizazzPoints = this.pizazzPoints + EnchantmentChamberController.getBasePizazzPointsForSpellTier(n) >= 16000 ? 16000 : (this.pizazzPoints += EnchantmentChamberController.getBasePizazzPointsForSpellTier(n));
            }
        } else {
            int n3 = n2;
            if (currentBonusColor == (n3 == 6901 ? "red" : (n3 == 6899 ? "yellow" : (n3 == 6898 ? "green" : (n3 == 6900 ? "blue" : "")))) && this.pizazzPoints < 16000 && this.player.ownsProgressHat()) {
                Player player = this.player;
                player.packetSender.sendGameMessage("You recieve 1 bonus point!");
                ++this.pizazzPoints;
            }
            n3 = n;
            int n4 = EnchantmentChamberController.getBasePizazzPointsForSpellTier(n3) / 2 - 1;
            this.enchantmentOrbCountsBySpellTier[n4] = this.enchantmentOrbCountsBySpellTier[n4] + 1;
        }
        this.player.getInventoryManager().removeItem(new ItemStack(n2));
        this.player.getInventoryManager().addItem(new ItemStack(6902));
    }

    private void resetEnchantSpellTierCounts() {
        int n = 0;
        while (n < this.enchantmentOrbCountsBySpellTier.length) {
            this.enchantmentOrbCountsBySpellTier[n] = 0;
            ++n;
        }
    }

    static /* synthetic */ String[] getBonusColorNames() {
        return bonusColorNames;
    }

    static /* synthetic */ void setCurrentBonusColor(String string) {
        currentBonusColor = string;
    }

    static /* synthetic */ String getCurrentBonusColor() {
        return currentBonusColor;
    }
}

