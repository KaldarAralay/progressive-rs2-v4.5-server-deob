/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest;

import com.rs2.ServerSettings;
import com.rs2.model.quest.NullQuestScript;
import com.rs2.model.quest.QuestScript;
import com.rs2.model.quest.impl.BlackKnightsFortressQuest;
import com.rs2.model.quest.impl.CooksAssistantQuest;
import com.rs2.model.quest.impl.DemonSlayerQuest;
import com.rs2.model.quest.impl.DoricsQuest;
import com.rs2.model.quest.impl.DragonSlayerQuest;
import com.rs2.model.quest.impl.DruidicRitualQuest;
import com.rs2.model.quest.impl.ElementalWorkshopQuest;
import com.rs2.model.quest.impl.ErnestTheChickenQuest;
import com.rs2.model.quest.impl.FamilyCrestQuest;
import com.rs2.model.quest.impl.FremennikTrialsQuest;
import com.rs2.model.quest.impl.GertrudesCatQuest;
import com.rs2.model.quest.impl.GoblinDiplomacyQuest;
import com.rs2.model.quest.impl.GrandTreeQuest;
import com.rs2.model.quest.impl.HeroesQuest;
import com.rs2.model.quest.impl.HolyGrailQuest;
import com.rs2.model.quest.impl.ImpCatcherQuest;
import com.rs2.model.quest.impl.JunglePotionQuest;
import com.rs2.model.quest.impl.KnightsSwordQuest;
import com.rs2.model.quest.impl.LostCityQuest;
import com.rs2.model.quest.impl.MerlinsCrystalQuest;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.quest.impl.PiratesTreasureQuest;
import com.rs2.model.quest.impl.PriestInPerilQuest;
import com.rs2.model.quest.impl.PrinceAliRescueQuest;
import com.rs2.model.quest.impl.RestlessGhostQuest;
import com.rs2.model.quest.impl.RomeoAndJulietQuest;
import com.rs2.model.quest.impl.RuneMysteriesQuest;
import com.rs2.model.quest.impl.ScorpionCatcherQuest;
import com.rs2.model.quest.impl.SheepShearerQuest;
import com.rs2.model.quest.impl.ShieldOfArravQuest;
import com.rs2.model.quest.impl.TreeGnomeVillageQuest;
import com.rs2.model.quest.impl.TutorialQuest;
import com.rs2.model.quest.impl.VampireSlayerQuest;
import com.rs2.model.quest.impl.WitchsHouseQuest;
import com.rs2.model.quest.impl.WitchsPotionQuest;
import com.rs2.util.ByteArrayReader;
import com.rs2.util.FileUtil;

public final class QuestDefinition {
    private String c;
    private int d;
    private boolean e;
    public static int a = 0;
    public static int b = 200;
    private static QuestDefinition[] f = new QuestDefinition[0];
    private static QuestScript[] g = new QuestScript[]{new NullQuestScript(-1), new TutorialQuest(0), new BlackKnightsFortressQuest(1), new CooksAssistantQuest(2), new DemonSlayerQuest(3), new DoricsQuest(4), new DragonSlayerQuest(5), new ErnestTheChickenQuest(6), new GoblinDiplomacyQuest(7), new ImpCatcherQuest(8), new KnightsSwordQuest(9), new PiratesTreasureQuest(10), new PrinceAliRescueQuest(11), new RestlessGhostQuest(12), new RomeoAndJulietQuest(13), new RuneMysteriesQuest(14), new SheepShearerQuest(15), new ShieldOfArravQuest(16), new VampireSlayerQuest(17), new WitchsPotionQuest(18), new DruidicRitualQuest(29), new ElementalWorkshopQuest(32), new FamilyCrestQuest(35), new FremennikTrialsQuest(40), new GertrudesCatQuest(42), new GrandTreeQuest(46), new HeroesQuest(50), new HolyGrailQuest(51), new JunglePotionQuest(56), new LostCityQuest(58), new MerlinsCrystalQuest(61), new MonkeyMadnessQuest(62), new PriestInPerilQuest(72), new ScorpionCatcherQuest(80), new TreeGnomeVillageQuest(95), new WitchsHouseQuest(103)};

    public static int a() {
        int n = 0;
        int n2 = 1;
        while (n2 < a) {
            QuestScript questScript = QuestDefinition.a(n2);
            n += questScript.a();
            ++n2;
        }
        return n;
    }

    public static QuestScript a(int n) {
        int n2 = 0;
        while (n2 < g.length) {
            int n3 = g[n2].b();
            if (n == n3) {
                return g[n2];
            }
            ++n2;
        }
        return g[0];
    }

    public static QuestDefinition b(int n) {
        QuestDefinition questDefinition;
        if (n < 0) {
            n = 1;
        }
        if ((questDefinition = f[n]) == null) {
            questDefinition = new QuestDefinition(n, "UNKNOWN", -1, false);
        }
        return questDefinition;
    }

    public static void b() {
        try {
            Object object = FileUtil.readBytes("./data/content/questing/Quests.dat");
            ByteArrayReader byteArrayReader = new ByteArrayReader((byte[])object);
            object = byteArrayReader;
            a = byteArrayReader.readUnsignedByte();
            f = new QuestDefinition[a];
            int n = 0;
            while (n < a) {
                boolean bl;
                String string = ((ByteArrayReader)object).readString();
                int n2 = ((ByteArrayReader)object).readUnsignedShort() - 1;
                boolean bl2 = bl = ((ByteArrayReader)object).readUnsignedByte() != 0;
                if (ServerSettings.cacheVersion < 274 && n == 99) {
                    n2 = 7382;
                }
                QuestDefinition.f[n] = new QuestDefinition(n, string, n2, bl);
                ++n;
            }
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    private QuestDefinition(int n, String string, int n2, boolean bl) {
        this.c = string;
        this.d = n2;
        this.e = bl;
    }

    public final String c() {
        return this.c;
    }

    public final int d() {
        return this.d;
    }

    public final boolean e() {
        return this.e;
    }
}

