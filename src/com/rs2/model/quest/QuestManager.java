/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest;

import com.rs2.ServerSettings;
import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.model.quest.QuestEventRegistry;
import com.rs2.model.quest.QuestHook;
import com.rs2.model.quest.QuestScript;

public final class QuestManager {
    private Player a;

    public QuestManager(Player player) {
        this.a = player;
    }

    private static boolean g(int n) {
        QuestDefinition questDefinition = QuestDefinition.b(n);
        int n2 = questDefinition.d();
        return n2 < InterfaceDefinition.interfaceCount;
    }

    public final boolean a(int n, int n2) {
        QuestHook questHook;
        int n3 = 0;
        while (n3 < QuestEventRegistry.a) {
            questHook = QuestEventRegistry.a(n3);
            if (questHook.b() != -1 && questHook.b(this.a, n, n2, this.a.bJ[n3])) {
                return true;
            }
            ++n3;
        }
        n3 = 1;
        while (n3 < QuestDefinition.a) {
            QuestHook questHook2;
            questHook = QuestDefinition.a(n3);
            if (!(questHook.b() == -1 || !QuestManager.g(questHook.b()) || QuestDefinition.b((questHook2 = questHook).b()).e() && !this.a.isMember() || QuestDefinition.b((questHook2 = questHook).b()).e() && ServerSettings.freeToPlayWorld || !questHook.b(this.a, n, n2, this.a.getQuestState(n3)))) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    public final boolean a(int n) {
        QuestHook questHook;
        int n2 = 0;
        while (n2 < QuestEventRegistry.a) {
            questHook = QuestEventRegistry.a(n2);
            if (questHook.b() != -1 && questHook.d(this.a, n, this.a.bJ[n2])) {
                return true;
            }
            ++n2;
        }
        n2 = 1;
        while (n2 < QuestDefinition.a) {
            QuestHook questHook2;
            questHook = QuestDefinition.a(n2);
            if (!(questHook.b() == -1 || !QuestManager.g(questHook.b()) || QuestDefinition.b((questHook2 = questHook).b()).e() && !this.a.isMember() || QuestDefinition.b((questHook2 = questHook).b()).e() && ServerSettings.freeToPlayWorld || !questHook.d(this.a, n, this.a.getQuestState(n2)))) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public final boolean b(int n) {
        QuestHook questHook;
        int n2 = 0;
        while (n2 < QuestEventRegistry.a) {
            questHook = QuestEventRegistry.a(n2);
            if (questHook.b() != -1 && questHook.f(this.a, n, this.a.bJ[n2])) {
                return true;
            }
            ++n2;
        }
        n2 = 1;
        while (n2 < QuestDefinition.a) {
            QuestHook questHook2;
            questHook = QuestDefinition.a(n2);
            if (!(questHook.b() == -1 || !QuestManager.g(questHook.b()) || QuestDefinition.b((questHook2 = questHook).b()).e() && !this.a.isMember() || QuestDefinition.b((questHook2 = questHook).b()).e() && ServerSettings.freeToPlayWorld || !questHook.f(this.a, n, this.a.getQuestState(n2)))) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public final boolean b(int n, int n2) {
        QuestHook questHook;
        int n3 = 0;
        while (n3 < QuestEventRegistry.a) {
            questHook = QuestEventRegistry.a(n3);
            if (questHook.b() != -1 && questHook.c(this.a, n, n2, this.a.bJ[n3])) {
                return true;
            }
            ++n3;
        }
        n3 = 1;
        while (n3 < QuestDefinition.a) {
            QuestHook questHook2;
            questHook = QuestDefinition.a(n3);
            if (!(questHook.b() == -1 || !QuestManager.g(questHook.b()) || QuestDefinition.b((questHook2 = questHook).b()).e() && !this.a.isMember() || QuestDefinition.b((questHook2 = questHook).b()).e() && ServerSettings.freeToPlayWorld || !questHook.c(this.a, n, n2, this.a.getQuestState(n3)))) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    public final boolean a(int n, int n2, int n3) {
        QuestHook questHook;
        int n4 = 0;
        while (n4 < QuestEventRegistry.a) {
            questHook = QuestEventRegistry.a(n4);
            if (questHook.b() != -1 && questHook.b(this.a, n, n2, n3, this.a.bJ[n4])) {
                return true;
            }
            ++n4;
        }
        n4 = 0;
        while (n4 < QuestDefinition.a) {
            QuestHook questHook2;
            questHook = QuestDefinition.a(n4);
            if (!(questHook.b() == -1 || !QuestManager.g(questHook.b()) || QuestDefinition.b((questHook2 = questHook).b()).e() && !this.a.isMember() || QuestDefinition.b((questHook2 = questHook).b()).e() && ServerSettings.freeToPlayWorld || !questHook.b(this.a, n, n2, n3, this.a.getQuestState(n4)))) {
                return true;
            }
            ++n4;
        }
        return false;
    }

    public final boolean b(int n, int n2, int n3) {
        QuestHook questHook;
        int n4 = 0;
        while (n4 < QuestEventRegistry.a) {
            questHook = QuestEventRegistry.a(n4);
            if (questHook.b() != -1 && questHook.c(this.a, n, n2, n3, this.a.bJ[n4])) {
                return true;
            }
            ++n4;
        }
        n4 = 0;
        while (n4 < QuestDefinition.a) {
            QuestHook questHook2;
            questHook = QuestDefinition.a(n4);
            if (!(questHook.b() == -1 || !QuestManager.g(questHook.b()) || QuestDefinition.b((questHook2 = questHook).b()).e() && !this.a.isMember() || QuestDefinition.b((questHook2 = questHook).b()).e() && ServerSettings.freeToPlayWorld || !questHook.c(this.a, n, n2, n3, this.a.getQuestState(n4)))) {
                return true;
            }
            ++n4;
        }
        return false;
    }

    public final boolean c(int n) {
        QuestHook questHook;
        int n2 = 0;
        while (n2 < QuestEventRegistry.a) {
            questHook = QuestEventRegistry.a(n2);
            if (questHook.b() != -1 && questHook.c(this.a, n, this.a.bJ[n2])) {
                return true;
            }
            ++n2;
        }
        n2 = 1;
        while (n2 < QuestDefinition.a) {
            QuestHook questHook2;
            questHook = QuestDefinition.a(n2);
            if (!(questHook.b() == -1 || !QuestManager.g(questHook.b()) || QuestDefinition.b((questHook2 = questHook).b()).e() && !this.a.isMember() || QuestDefinition.b((questHook2 = questHook).b()).e() && ServerSettings.freeToPlayWorld || !questHook.c(this.a, n, this.a.getQuestState(n2)))) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public final boolean c(int n, int n2) {
        QuestHook questHook;
        int n3 = 0;
        while (n3 < QuestEventRegistry.a) {
            questHook = QuestEventRegistry.a(n3);
            if (questHook.b() != -1 && questHook.a(this.a, n, n2, this.a.bJ[n3])) {
                return true;
            }
            ++n3;
        }
        n3 = 1;
        while (n3 < QuestDefinition.a) {
            QuestHook questHook2;
            questHook = QuestDefinition.a(n3);
            if (!(questHook.b() == -1 || !QuestManager.g(questHook.b()) || QuestDefinition.b((questHook2 = questHook).b()).e() && !this.a.isMember() || QuestDefinition.b((questHook2 = questHook).b()).e() && ServerSettings.freeToPlayWorld || !questHook.a(this.a, n, n2, this.a.getQuestState(n3)))) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    public final boolean d(int n, int n2) {
        QuestHook questHook;
        int n3 = 0;
        while (n3 < QuestEventRegistry.a) {
            questHook = QuestEventRegistry.a(n3);
            if (questHook.b() != -1 && questHook.d(this.a, n, n2, this.a.bJ[n3])) {
                return true;
            }
            ++n3;
        }
        n3 = 1;
        while (n3 < QuestDefinition.a) {
            QuestHook questHook2;
            questHook = QuestDefinition.a(n3);
            if (!(questHook.b() == -1 || !QuestManager.g(questHook.b()) || QuestDefinition.b((questHook2 = questHook).b()).e() && !this.a.isMember() || QuestDefinition.b((questHook2 = questHook).b()).e() && ServerSettings.freeToPlayWorld || !questHook.d(this.a, n, n2, this.a.getQuestState(n3)))) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    public final boolean d(int n) {
        QuestHook questHook;
        int n2 = 0;
        while (n2 < QuestEventRegistry.a) {
            questHook = QuestEventRegistry.a(n2);
            if (questHook.b() != -1 && !questHook.b(this.a, n, this.a.bJ[n2])) {
                return false;
            }
            ++n2;
        }
        n2 = 1;
        while (n2 < QuestDefinition.a) {
            QuestHook questHook2;
            questHook = QuestDefinition.a(n2);
            if (!(questHook.b() == -1 || !QuestManager.g(questHook.b()) || QuestDefinition.b((questHook2 = questHook).b()).e() && !this.a.isMember() || QuestDefinition.b((questHook2 = questHook).b()).e() && ServerSettings.freeToPlayWorld || questHook.b(this.a, n, this.a.getQuestState(n2)))) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    public final boolean a() {
        QuestHook questHook;
        int n = 0;
        while (n < QuestEventRegistry.a) {
            questHook = QuestEventRegistry.a(n);
            if (questHook.b() != -1 && questHook.b(this.a, this.a.bJ[n])) {
                return true;
            }
            ++n;
        }
        n = 1;
        while (n < QuestDefinition.a) {
            QuestHook questHook2;
            questHook = QuestDefinition.a(n);
            if (!(questHook.b() == -1 || !QuestManager.g(questHook.b()) || QuestDefinition.b((questHook2 = questHook).b()).e() && !this.a.isMember() || QuestDefinition.b((questHook2 = questHook).b()).e() && ServerSettings.freeToPlayWorld || !questHook.b(this.a, this.a.getQuestState(n)))) {
                return true;
            }
            ++n;
        }
        return false;
    }

    public final boolean a(Entity entity, Entity entity2) {
        QuestHook questHook;
        int n = 0;
        while (n < QuestEventRegistry.a) {
            questHook = QuestEventRegistry.a(n);
            if (questHook.b() != -1 && questHook.a(entity, entity2, this.a.bJ[n])) {
                return true;
            }
            ++n;
        }
        n = 1;
        while (n < QuestDefinition.a) {
            QuestHook questHook2;
            questHook = QuestDefinition.a(n);
            if (!(questHook.b() == -1 || !QuestManager.g(questHook.b()) || QuestDefinition.b((questHook2 = questHook).b()).e() && !this.a.isMember() || QuestDefinition.b((questHook2 = questHook).b()).e() && ServerSettings.freeToPlayWorld || !questHook.a(entity, entity2, this.a.getQuestState(n)))) {
                return true;
            }
            ++n;
        }
        return false;
    }

    public final int b(Entity entity, Entity entity2) {
        QuestHook questHook;
        int n = 0;
        while (n < QuestEventRegistry.a) {
            questHook = QuestEventRegistry.a(n);
            if (questHook.b() != -1 && questHook.b(entity, entity2, this.a.bJ[n]) != -1) {
                return questHook.b(entity, entity2, this.a.bJ[n]);
            }
            ++n;
        }
        n = 1;
        while (n < QuestDefinition.a) {
            QuestHook questHook2;
            questHook = QuestDefinition.a(n);
            if (!(questHook.b() == -1 || !QuestManager.g(questHook.b()) || QuestDefinition.b((questHook2 = questHook).b()).e() && !this.a.isMember() || QuestDefinition.b((questHook2 = questHook).b()).e() && ServerSettings.freeToPlayWorld || questHook.b(entity, entity2, this.a.getQuestState(n)) == -1)) {
                return questHook.b(entity, entity2, this.a.getQuestState(n));
            }
            ++n;
        }
        return -1;
    }

    public final boolean e(int n) {
        QuestHook questHook;
        int n2 = 0;
        while (n2 < QuestEventRegistry.a) {
            questHook = QuestEventRegistry.a(n2);
            if (questHook.b() != -1 && questHook.a(this.a, n, this.a.bJ[n2])) {
                return true;
            }
            ++n2;
        }
        n2 = 1;
        while (n2 < QuestDefinition.a) {
            QuestHook questHook2;
            questHook = QuestDefinition.a(n2);
            if (!(questHook.b() == -1 || !QuestManager.g(questHook.b()) || QuestDefinition.b((questHook2 = questHook).b()).e() && !this.a.isMember() || QuestDefinition.b((questHook2 = questHook).b()).e() && ServerSettings.freeToPlayWorld || !questHook.a(this.a, n, this.a.getQuestState(n2)))) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public final void refreshQuestJournal() {
        QuestScript questScript = QuestDefinition.a(0);
        questScript.d(this.a, this.a.getQuestState(0));
    }

    public final boolean a(int n, Player player, Position position) {
        QuestHook questHook;
        int n2 = 0;
        while (n2 < QuestEventRegistry.a) {
            questHook = QuestEventRegistry.a(n2);
            if (questHook.b() != -1 && questHook.a(player, n, position, this.a.bJ[n2])) {
                return true;
            }
            ++n2;
        }
        n2 = 1;
        while (n2 < QuestDefinition.a) {
            QuestHook questHook2;
            questHook = QuestDefinition.a(n2);
            if (!(questHook.b() == -1 || !QuestManager.g(questHook.b()) || QuestDefinition.b((questHook2 = questHook).b()).e() && !this.a.isMember() || QuestDefinition.b((questHook2 = questHook).b()).e() && ServerSettings.freeToPlayWorld || !questHook.a(player, n, position, this.a.getQuestState(n2)))) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public final boolean a(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        QuestHook questHook;
        n5 = 0;
        while (n5 < QuestEventRegistry.a) {
            questHook = QuestEventRegistry.a(n5);
            if (questHook.b() != -1 && questHook.a(n, this.a, n2, n3, n4, n6, n7, this.a.bJ[n5])) {
                return true;
            }
            ++n5;
        }
        n5 = 0;
        while (n5 < QuestDefinition.a) {
            QuestHook questHook2;
            questHook = QuestDefinition.a(n5);
            if (!(questHook.b() == -1 || !QuestManager.g(questHook.b()) || QuestDefinition.b((questHook2 = questHook).b()).e() && !this.a.isMember() || QuestDefinition.b((questHook2 = questHook).b()).e() && ServerSettings.freeToPlayWorld || !questHook.a(n, this.a, n2, n3, n4, n6, n7, this.a.getQuestState(n5)))) {
                return true;
            }
            ++n5;
        }
        return false;
    }

    public final boolean a(int n, int n2, int n3, int n4) {
        QuestHook questHook;
        n4 = 0;
        while (n4 < QuestEventRegistry.a) {
            questHook = QuestEventRegistry.a(n4);
            if (questHook.b() != -1 && questHook.a(this.a, n, n2, n3, this.a.bJ[n4])) {
                return true;
            }
            ++n4;
        }
        n4 = 0;
        while (n4 < QuestDefinition.a) {
            Object object;
            questHook = QuestDefinition.a(n4);
            if (!(questHook.b() == -1 || !QuestManager.g(questHook.b()) || QuestDefinition.b(((QuestHook)(object = questHook)).b()).e() && !this.a.isMember() || QuestDefinition.b(((QuestHook)(object = questHook)).b()).e() && ServerSettings.freeToPlayWorld || !questHook.a(this.a, n, n2, n3, this.a.getQuestState(n4)))) {
                if (this.a.getQuestState(questHook.b()) == 0 && questHook.b() >= 105 && !this.a.eg()) {
                    object = this.a;
                    ((Player)object).packetSender.closeInterfaces();
                    Player player = this.a;
                    object = player;
                    object = questHook;
                    player.packetSender.sendGameMessage("You need a donator rank to start this quest: " + QuestDefinition.b(((QuestHook)object).b()).c());
                    return false;
                }
                return true;
            }
            ++n4;
        }
        return false;
    }

    public final boolean c() {
        int n = 0;
        while (n < QuestDefinition.a) {
            QuestScript questScript;
            QuestScript questScript2 = QuestDefinition.a(n);
            if (!(questScript2.b() == -1 || !QuestManager.g(questScript2.b()) || QuestDefinition.b((questScript = questScript2).b()).e() && !this.a.isMember() || QuestDefinition.b((questScript = questScript2).b()).e() && ServerSettings.freeToPlayWorld)) {
                questScript2.c(this.a, this.a.getQuestState(n));
            }
            ++n;
        }
        return false;
    }

    public final void d() {
        Player player = this.a;
        player.packetSender.sendInterfaceText("QP: " + this.a.dA(), 3985);
    }

    public final boolean handleButtonClick(int n) {
        boolean bl;
        block10: {
            int n2;
            Object object;
            int n3;
            if (ServerSettings.cacheVersion > 245) {
                n3 = 1;
                while (n3 < QuestDefinition.a) {
                    QuestDefinition questDefinition = QuestDefinition.b(n3);
                    object = questDefinition.c();
                    n2 = questDefinition.d();
                    if (n == n2) {
                        QuestManager questManager = this;
                        Player player = questManager.a;
                        player.packetSender.sendInterfaceText("", 8145);
                        n2 = 8147;
                        while (n2 <= 8195) {
                            player = questManager.a;
                            player.packetSender.sendInterfaceText("", n2);
                            ++n2;
                        }
                        n2 = 12174;
                        while (n2 <= 12223) {
                            player = questManager.a;
                            player.packetSender.sendInterfaceText("", n2);
                            ++n2;
                        }
                        player = this.a;
                        player.packetSender.sendInterfaceText((String)object, 8144);
                        this.a.dA();
                        n2 = n3;
                        questManager = this;
                        QuestScript questScript = QuestDefinition.a(n2);
                        object = questScript.a(questManager.a, questManager.a.getQuestState(n2));
                        player = questManager.a;
                        player.packetSender.sendInterfaceText(object[0], 8145);
                        int n4 = 1;
                        while (n4 < ((String[])object).length) {
                            player = questManager.a;
                            player.packetSender.sendInterfaceText(object[n4], n4 + 8146);
                            ++n4;
                        }
                        player = this.a;
                        player.packetSender.showInterface(8134);
                        return true;
                    }
                    ++n3;
                }
            }
            n2 = n;
            QuestManager questManager = this;
            n3 = 1;
            while (n3 < QuestDefinition.a) {
                object = QuestDefinition.a(n3);
                if (object.b() != -1 && QuestManager.g(object.b())) {
                    String[] stringArray = object;
                    if (!QuestDefinition.b(object.b()).e() || questManager.a.isMember()) {
                        stringArray = object;
                        if (!(QuestDefinition.b(object.b()).e() && ServerSettings.freeToPlayWorld || !object.e(questManager.a, n2, questManager.a.getQuestState(n3)))) {
                            bl = true;
                            break block10;
                        }
                    }
                }
                ++n3;
            }
            bl = false;
        }
        return bl;
    }
}

