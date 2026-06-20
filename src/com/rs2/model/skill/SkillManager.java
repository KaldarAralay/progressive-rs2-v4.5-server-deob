/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill;

import com.rs2.ServerSettings;
import com.rs2.bot.BotTaskPlanner;
import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.model.skill.RunEnergyRestoreTask;
import com.rs2.model.skill.SkillLevelRestoreTask;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public final class SkillManager {
    private Player c;
    private int[] d = new int[22];
    private double[] e = new double[22];
    private TickTask[] f = new TickTask[22];
    private int[] g = new int[22];
    private TickTask h;
    private static final int[] i = new int[ServerSettings.maxLevel + 1];
    public static final String[] a;
    private long j = -10000L;
    private long k = -10000L;
    public static int b;

    static {
        int n = 0;
        int n2 = 1;
        while (n2 <= ServerSettings.maxLevel) {
            int n3;
            n = (int)((double)n + Math.floor((double)n2 + 300.0 * Math.pow(2.0, (double)n2 / 7.0)));
            SkillManager.i[n2] = n3 = (int)Math.floor(n / 4);
            ++n2;
        }
        a = new String[]{"Attack", "Defence", "Strength", "Hitpoints", "Ranged", "Prayer", "Magic", "Cooking", "Woodcutting", "Fletching", "Fishing", "Firemaking", "Crafting", "Smithing", "Mining", "Herblore", "Agility", "Thieving", "Slayer", "Farming", "Runecrafting"};
        b = 126;
    }

    private double getExperienceCap() {
        if (ServerSettings.capXpAtMaxLevel) {
            return SkillManager.getExperienceForLevel(ServerSettings.maxLevel - 1);
        }
        return 2.0E8;
    }

    public SkillManager(Player player) {
        int[][] nArrayArray = new int[21][];
        int[] nArray = new int[4];
        nArray[1] = 6247;
        nArrayArray[0] = nArray;
        int[] nArray2 = new int[4];
        nArray2[0] = 1;
        nArray2[1] = 6253;
        nArrayArray[1] = nArray2;
        int[] nArray3 = new int[4];
        nArray3[0] = 2;
        nArray3[1] = 6206;
        nArrayArray[2] = nArray3;
        int[] nArray4 = new int[4];
        nArray4[0] = 3;
        nArray4[1] = 6216;
        nArrayArray[3] = nArray4;
        nArrayArray[4] = new int[]{4, 4443, 5453, 6114};
        int[] nArray5 = new int[4];
        nArray5[0] = 5;
        nArray5[1] = 6242;
        nArrayArray[5] = nArray5;
        int[] nArray6 = new int[4];
        nArray6[0] = 6;
        nArray6[1] = 6211;
        nArrayArray[6] = nArray6;
        int[] nArray7 = new int[4];
        nArray7[0] = 7;
        nArray7[1] = 6226;
        nArrayArray[7] = nArray7;
        int[] nArray8 = new int[4];
        nArray8[0] = 8;
        nArray8[1] = 4272;
        nArrayArray[8] = nArray8;
        int[] nArray9 = new int[4];
        nArray9[0] = 9;
        nArray9[1] = 6231;
        nArrayArray[9] = nArray9;
        int[] nArray10 = new int[4];
        nArray10[0] = 10;
        nArray10[1] = 6258;
        nArrayArray[10] = nArray10;
        int[] nArray11 = new int[4];
        nArray11[0] = 11;
        nArray11[1] = 4282;
        nArrayArray[11] = nArray11;
        int[] nArray12 = new int[4];
        nArray12[0] = 12;
        nArray12[1] = 6263;
        nArrayArray[12] = nArray12;
        int[] nArray13 = new int[4];
        nArray13[0] = 13;
        nArray13[1] = 6221;
        nArrayArray[13] = nArray13;
        nArrayArray[14] = new int[]{14, 4416, 4417, 4438};
        int[] nArray14 = new int[4];
        nArray14[0] = 15;
        nArray14[1] = 6237;
        nArrayArray[15] = nArray14;
        int[] nArray15 = new int[4];
        nArray15[0] = 16;
        nArray15[1] = 4277;
        nArrayArray[16] = nArray15;
        nArrayArray[17] = new int[]{17, 4261, 4263, 4264};
        int[] nArray16 = new int[4];
        nArray16[0] = 18;
        nArray16[1] = 12122;
        nArrayArray[18] = nArray16;
        nArrayArray[19] = new int[]{19, 4887, 4889, 4890};
        int[] nArray17 = new int[4];
        nArray17[0] = 20;
        nArray17[1] = 4267;
        nArrayArray[20] = nArray17;
        this.c = player;
        int n = 0;
        while (n < this.d.length) {
            if (n == 3) {
                this.d[n] = 10;
                this.e[n] = 1154.0;
            } else {
                this.d[n] = 1;
                this.e[n] = 0.0;
            }
            this.setSkillRestoreDelay(n, 100);
            ++n;
        }
    }

    public final void startRestorationTasks() {
        Object object;
        int n = 0;
        while (n < 22) {
            if (n != 5) {
                int n2 = n;
                SkillManager skillManager = this;
                object = skillManager.f[n2];
                SkillManager skillManager2 = skillManager;
                if (skillManager.d[n2] != SkillManager.getLevelForExperience(skillManager2.e[n2])) {
                    if (skillManager.f[n2] == null || !skillManager.f[n2].isActive()) {
                        int n3 = skillManager.g[n2];
                        skillManager.f[n2] = new SkillLevelRestoreTask(skillManager, n3, n2);
                        World.getTaskScheduler().schedule(skillManager.f[n2]);
                    }
                } else if (object != null) {
                    ((TickTask)object).stop();
                    int n4 = skillManager.g[n2];
                    ((TickTask)object).setIntervalTicks(n4);
                    ((TickTask)object).setRemainingTicks(n4);
                }
            }
            ++n;
        }
        SkillManager skillManager = this;
        TickTask tickTask = skillManager.h;
        if (skillManager.c.getSpecialEnergy() < 100) {
            object = skillManager;
            if (((SkillManager)object).h == null || !((SkillManager)object).h.isActive()) {
                ((SkillManager)object).h = new RunEnergyRestoreTask((SkillManager)object, 50);
                World.getTaskScheduler().schedule(((SkillManager)object).h);
            }
            return;
        }
        if (tickTask != null) {
            tickTask.stop();
            tickTask.setIntervalTicks(50);
            tickTask.setRemainingTicks(50);
        }
    }

    public final void setRapidHealRestoreDelay(boolean bl) {
        this.setSkillRestoreDelay(3, bl ? 50 : 100);
    }

    public final void restoreNonPrayerLevels() {
        int n = 0;
        while (n < 22) {
            if (n != 3 && n != 5) {
                SkillManager skillManager = this;
                if (this.d[n] != SkillManager.getLevelForExperience(skillManager.e[n])) {
                    skillManager = this;
                    if (this.d[n] > SkillManager.getLevelForExperience(skillManager.e[n])) {
                        int n2 = n;
                        this.d[n2] = this.d[n2] - 1;
                    } else {
                        int n3 = n;
                        this.d[n3] = this.d[n3] + 1;
                    }
                    this.refreshSkill(n);
                }
            }
            ++n;
        }
    }

    private void setSkillRestoreDelay(int n, int n2) {
        this.g[n] = n2;
        TickTask tickTask = this.f[n];
        if (tickTask != null) {
            tickTask.setIntervalTicks(n2);
            tickTask.setRemainingTicks(n2);
        }
    }

    public final boolean isLevelModified(int n) {
        SkillManager skillManager = this;
        return this.d[n] != SkillManager.getLevelForExperience(skillManager.e[n]);
    }

    public final boolean isRunEnergyBelowMaximum() {
        return this.c.getSpecialEnergy() < 100;
    }

    public final void d() {
        this.refreshAllSkills();
    }

    public final void refreshAllSkills() {
        int n = 0;
        while (n < this.d.length) {
            Player player = this.c;
            player.packetSender.sendSkillUpdate(n, this.d[n], this.e[n]);
            ++n;
        }
        this.c.setCombatLevel(this.getCombatLevel());
        this.c.f(true);
    }

    public final void refreshSkill(int n) {
        Player player = this.c;
        player.packetSender.sendSkillUpdate(n, this.d[n], this.e[n]);
        this.c.setCombatLevel(this.getCombatLevel());
        this.c.f(true);
    }

    public final int getBaseLevel(int n) {
        return SkillManager.getLevelForExperience(this.e[n]);
    }

    public static int getLevelForExperience(double d) {
        int n = 1;
        while (n <= ServerSettings.maxLevel) {
            if ((double)i[n] > d) {
                return n;
            }
            ++n;
        }
        return ServerSettings.maxLevel;
    }

    public static int getExperienceForLevel(int n) {
        if (n >= i.length) {
            return Integer.MAX_VALUE;
        }
        return i[n];
    }

    /*
     * Enabled aggressive block sorting
     */
    public final int getTotalLevel() {
        int n = 0;
        int n2 = 0;
        while (n2 < 21) {
            if (!(n2 == 18 && ServerSettings.cacheVersion < 319 || n2 == 19 && ServerSettings.cacheVersion < 336)) {
                n += this.getBaseLevel(n2);
            }
            ++n2;
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final long getTotalExperience() {
        long l = 0L;
        int n = 0;
        while (n < 21) {
            SkillManager skillManager = this;
            l = (long)((double)l + skillManager.e[n]);
            ++n;
        }
        return l;
    }

    public final int calculateExperienceGain(int n, double d) {
        d = ServerSettings.progressiveXpMode == 0 ? (d *= ServerSettings.xpRate) : (d *= this.getExperienceRateForSkill(n));
        if (this.c.de && ServerSettings.botXpRateMode == 1) {
            d *= ServerSettings.botXpRateMultiplier;
        } else if ((this.c.de || this.c.botEnabled) && ServerSettings.botXpRateMode == 2) {
            d *= ServerSettings.botXpRateMultiplier;
        }
        if (this.c.getEnchantmentChamberController().c() || this.c.getAlchemistPlaygroundController().b() || this.c.getCreatureGraveyardController().a() || this.c.getTelekineticTheatreController().g()) {
            d *= 0.75;
        }
        n = (int)d;
        return n;
    }

    private double getExperienceRateForSkill(int n) {
        double d = 1.0;
        n = SkillManager.getLevelForExperience(this.e[n]);
        if (ServerSettings.progressiveXpMode == 1) {
            double d2 = Math.pow(1.02, n);
            double d3 = Math.pow(n, 2.0);
            double d4 = d3 / 3300.0;
            d = 1.0 + (d2 + d4 - 1.0) * ServerSettings.xpRate;
        } else if (ServerSettings.progressiveXpMode == 2) {
            d = 1.0 + (double)(n - 1) * (0.1 * ServerSettings.xpRate);
        }
        return d;
    }

    private double getExperienceRateForLevel(int n) {
        double d = 1.0;
        if (ServerSettings.progressiveXpMode == 1) {
            double d2 = Math.pow(1.02, n);
            double d3 = Math.pow(n, 2.0);
            double d4 = d3 / 3300.0;
            d = 1.0 + (d2 + d4 - 1.0) * ServerSettings.xpRate;
        } else if (ServerSettings.progressiveXpMode == 2) {
            d = 1.0 + (double)(n - 1) * (0.1 * ServerSettings.xpRate);
        }
        if (this.c.de && ServerSettings.botXpRateMode == 1) {
            d *= ServerSettings.botXpRateMultiplier;
        } else if ((this.c.de || this.c.botEnabled) && ServerSettings.botXpRateMode == 2) {
            d *= ServerSettings.botXpRateMultiplier;
        }
        return d;
    }

    public final boolean addExperience(int n, double d) {
        int n2;
        int n3;
        int n4 = SkillManager.getLevelForExperience(this.e[n]);
        if (n4 >= 3 && this.c.getQuestState(0) != 1) {
            return false;
        }
        if (d <= 0.0) {
            return false;
        }
        boolean bl = false;
        boolean bl2 = this.c.isMember();
        this.c.N = n4;
        d = ServerSettings.progressiveXpMode == 0 ? (d *= ServerSettings.xpRate) : (d *= this.getExperienceRateForSkill(n));
        if (this.c.de && ServerSettings.botXpRateMode == 1) {
            d *= ServerSettings.botXpRateMultiplier;
        } else if ((this.c.de || this.c.botEnabled) && ServerSettings.botXpRateMode == 2) {
            d *= ServerSettings.botXpRateMultiplier;
        }
        if (this.c.getEnchantmentChamberController().c() || this.c.getAlchemistPlaygroundController().b() || this.c.getCreatureGraveyardController().a() || this.c.getTelekineticTheatreController().g()) {
            int n5 = n;
            this.e[n5] = this.e[n5] + d * 0.75;
        } else {
            int n6 = n;
            this.e[n6] = this.e[n6] + d;
        }
        if (this.e[n] > this.getExperienceCap()) {
            this.e[n] = this.getExperienceCap();
        }
        if ((n3 = (n2 = SkillManager.getLevelForExperience(this.e[n])) - n4) > 0) {
            bl = true;
            int n7 = n;
            this.d[n7] = this.d[n7] + n3;
            if (n > 0 && n <= 6) {
                this.c.setCombatLevel(this.getCombatLevel());
            }
            this.showLevelUpInterface(n);
            if (this.c.botEnabled) {
                if (GameUtil.h(5) == 0 && (n2 % 2 == 0 || n2 % 5 == 0)) {
                    this.c.queuePublicChatMessage("Yay " + n2 + " " + a[n] + "!");
                }
                if (this.c.currentBotTask != null && this.c.currentBotTask.combatTask && n2 % 5 == 0) {
                    BotTaskPlanner.k(this.c);
                }
            }
        }
        this.refreshSkill(n);
        if (ServerSettings.membershipRequirementMode == 2 && !bl2 && this.c.isMember()) {
            this.c.packetSender.sendGameMessage("You have reached " + ServerSettings.membershipRequirementValue + "+ total lvl and gained access to members content!");
        }
        return bl;
    }

    public final void addQuestExperience(int n, double d) {
        int n2;
        if (d <= 0.0) {
            return;
        }
        this.c.isMember();
        this.c.N = n2 = SkillManager.getLevelForExperience(this.e[n]);
        int n3 = n;
        this.e[n3] = this.e[n3] + (d *= ServerSettings.questXpRate);
        if (this.e[n] > this.getExperienceCap()) {
            this.e[n] = this.getExperienceCap();
        }
        int n4 = SkillManager.getLevelForExperience(this.e[n]);
        if ((n4 -= n2) > 0) {
            int n5 = n;
            this.d[n5] = this.d[n5] + n4;
            if (n > 0 && n <= 6) {
                this.c.setCombatLevel(this.getCombatLevel());
            }
            this.showLevelUpInterface(n);
        }
        this.refreshSkill(n);
    }

    public final void showLevelUpInterface(int n) {
        Object object;
        if (this.c.j) {
            if (!this.c.k.contains(n)) {
                this.c.k.add(n);
            }
            return;
        }
        int[][] nArrayArray = new int[23][];
        int[] nArray = new int[8];
        nArray[1] = 6248;
        nArray[2] = 6249;
        nArray[3] = 6247;
        nArray[4] = 219;
        nArray[5] = 233;
        nArray[6] = 200;
        nArray[7] = 400;
        nArrayArray[0] = nArray;
        nArrayArray[1] = new int[]{1, 6254, 6255, 6253, 216, 210, 256, 256};
        nArrayArray[2] = new int[]{2, 6207, 6208, 6206, 235, 222, 220, 256};
        nArrayArray[3] = new int[]{3, 6217, 6218, 6216, 212, 208, 200, 220};
        nArrayArray[4] = new int[]{4, 5453, 6114, 4443, 202, 224, 256, 256};
        nArrayArray[5] = new int[]{5, 6243, 6244, 6242, 211, 207, 256, 256};
        nArrayArray[6] = new int[]{6, 6212, 6213, 6211, 215, 230, 256, 256};
        nArrayArray[7] = new int[]{7, 6227, 6228, 6226, 196, 217, 256, 180};
        nArrayArray[8] = new int[]{8, 4273, 4274, 4272, 220, 209, 220, 256};
        nArrayArray[9] = new int[]{9, 6232, 6233, 6231, 195, 205, 256, 256};
        nArrayArray[10] = new int[]{10, 6259, 6260, 6258, 213, 226, 256, 256};
        nArrayArray[11] = new int[]{11, 4283, 4284, 4282, 199, 126, 256, 256};
        nArrayArray[12] = new int[]{12, 6264, 6265, 6263, 214, 228, 300, 220};
        nArrayArray[13] = new int[]{13, 6222, 6223, 6221, 229, 206, 256, 256};
        nArrayArray[14] = new int[]{14, 4417, 4438, 4416, 227, 223, 256, 256};
        nArrayArray[15] = new int[]{15, 6238, 6239, 6237, 236, 218, 200, 256};
        nArrayArray[16] = new int[]{16, 4278, 4279, 4277, 231, 231, 256, 256};
        nArrayArray[17] = new int[]{17, 4263, 4264, 4261, 197, 201, 200, 256};
        nArrayArray[18] = new int[]{18, 12123, 12124, 12122, 275, 276, 380, 380};
        nArrayArray[19] = new int[]{19, 313, 312, 310, 422, 424, 320, 175};
        nArrayArray[20] = new int[]{20, 4268, 4269, 4267, 194, 200, 320, 320};
        nArrayArray[21] = new int[]{21, -1, -1, -1, -1, -1, -1, -1};
        nArrayArray[22] = new int[]{22, 19567, 19568, 19566, 656, 657, 256, 256};
        int[][] nArrayArray2 = nArrayArray;
        if (n == nArrayArray2[n][0]) {
            this.c.i = n;
            Player player = this.c;
            player.packetSender.sendInterfaceText("@dbl@Congratulations, you just advanced a " + a[n] + " level!", nArrayArray2[n][1]);
            player = this.c;
            player.packetSender.sendInterfaceText("Your " + a[n] + " level is now " + this.getBaseLevel(n) + ".", nArrayArray2[n][2]);
            player = this.c;
            player.packetSender.sendGameMessage("You've just advanced a " + a[n] + " level! You have reached level " + this.getBaseLevel(n) + ".");
            if (ServerSettings.progressiveXpMode != 0) {
                int n2 = this.getBaseLevel(n);
                int n3 = this.c.N;
                double d = this.getExperienceRateForLevel(n3);
                double d2 = this.getExperienceRateForLevel(n2);
                Object object2 = new DecimalFormat("#0.00");
                ((DecimalFormat)object2).setRoundingMode(RoundingMode.FLOOR);
                String string = ((NumberFormat)object2).format(d).replaceAll(",", ".");
                object2 = ((NumberFormat)object2).format(d2).replaceAll(",", ".");
                if (!string.equals(object2)) {
                    Player player2 = this.c;
                    player2.packetSender.sendGameMessage("XP rate increased to: " + (String)object2 + " (Previous XP rate was: " + string + ").");
                }
            }
            this.c.getUpdateState().setGraphic(199);
            this.c.packetSender.sendSoundEffect(323, 1, 0);
            int n4 = this.getBaseLevel(n) >= 50 ? 1 : 0;
            object = this.c;
            ((Player)object).packetSender.sendMusicJingle(nArrayArray2[n][n4 + 4], nArrayArray2[n][n4 + 6]);
            if (n == 19) {
                object = this.c;
                ((Player)object).packetSender.sendInterfacePosition(311, 0, 30);
                object = this.c;
                ((Player)object).packetSender.sendInterfaceModel(311, 200, 5340);
            }
            object = this;
            if (SkillManager.getLevelForExperience(((SkillManager)object).e[n]) == ServerSettings.maxLevel) {
                object = this.c;
                ((Player)object).packetSender.sendGameMessage("Well done! You've achieved the highest possible level in this skill!");
            }
            object = this.c;
            ((Player)object).packetSender.showChatboxInterface(nArrayArray2[n][3]);
            this.c.getDialogueManager().finishDialogue();
        }
        this.c.f(true);
        object = this.c;
        ((Player)object).packetSender.sendInterfaceText("Total Lvl: " + this.getTotalLevel(), 3984);
    }

    public final int getCombatLevel() {
        double d;
        int n = SkillManager.getLevelForExperience(this.e[0]);
        int n2 = SkillManager.getLevelForExperience(this.e[1]);
        int n3 = SkillManager.getLevelForExperience(this.e[2]);
        int n4 = SkillManager.getLevelForExperience(this.e[3]);
        int n5 = SkillManager.getLevelForExperience(this.e[5]);
        int n6 = SkillManager.getLevelForExperience(this.e[4]);
        int n7 = SkillManager.getLevelForExperience(this.e[6]);
        double d2 = n2 + n4 + n5 / 2;
        double d3 = (d2 + 1.3 * (1.5 * (double)n7)) / 4.0;
        double d4 = (d2 + 1.3 * (1.5 * (double)n6)) / 4.0;
        double d5 = (d2 + 1.3 * (double)(n + n3)) / 4.0;
        if (d >= d4 && d5 >= d3) {
            return (int)d5;
        }
        if (d4 >= d5 && d4 >= d3) {
            return (int)d4;
        }
        return (int)d3;
    }

    public static int getMaxCombatLevel() {
        double d;
        int n = ServerSettings.maxLevel;
        int n2 = ServerSettings.maxLevel;
        int n3 = ServerSettings.maxLevel;
        int n4 = ServerSettings.maxLevel;
        int n5 = ServerSettings.maxLevel;
        int n6 = ServerSettings.maxLevel;
        int n7 = ServerSettings.maxLevel;
        double d2 = n2 + n4 + n5 / 2;
        double d3 = (d2 + 1.3 * (1.5 * (double)n7)) / 4.0;
        double d4 = (d2 + 1.3 * (1.5 * (double)n6)) / 4.0;
        double d5 = (d2 + 1.3 * (double)(n + n3)) / 4.0;
        if (d >= d4 && d5 >= d3) {
            return (int)d5;
        }
        if (d4 >= d5 && d4 >= d3) {
            return (int)d4;
        }
        return (int)d3;
    }

    public final int[] getCurrentLevels() {
        return this.d;
    }

    public final double[] getExperience() {
        return this.e;
    }

    public final void setCurrentLevel(int n, int n2) {
        this.d[n] = n2;
    }

    public final boolean f(int n) {
        if (System.currentTimeMillis() >= this.j) {
            this.j = System.currentTimeMillis() + (long)n;
            return true;
        }
        return false;
    }

    public final boolean g(int n) {
        if (System.currentTimeMillis() >= this.k) {
            this.k = System.currentTimeMillis() + 600L;
            return true;
        }
        return false;
    }

    static /* synthetic */ Player getPlayer(SkillManager skillManager) {
        return skillManager.c;
    }

    static /* synthetic */ int[] getCurrentLevels(SkillManager skillManager) {
        return skillManager.d;
    }
}

