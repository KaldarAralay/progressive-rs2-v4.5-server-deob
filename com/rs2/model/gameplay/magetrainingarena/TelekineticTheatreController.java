/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.magetrainingarena;

import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.gameplay.magetrainingarena.MageTrainingArenaLobby;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;
import java.util.Random;

public final class TelekineticTheatreController {
    private static final Position[] e = new Position[]{new Position(3336, 9718, 0), new Position(3379, 9716, 0), new Position(3374, 9696, 0), new Position(3354, 9690, 0), new Position(3362, 9713, 1), new Position(3378, 9706, 1), new Position(3382, 9698, 1), new Position(3355, 9693, 1), new Position(3368, 9680, 2), new Position(3359, 9701, 2)};
    private static final Position[] f = new Position[]{new Position(3337, 9720, 0), new Position(3382, 9718, 0), new Position(3374, 9689, 0), new Position(3349, 9687, 0), new Position(3354, 9713, 1), new Position(3386, 9709, 1), new Position(3376, 9698, 1), new Position(3359, 9690, 1), new Position(3363, 9680, 2), new Position(3357, 9706, 2)};
    private static final Position[] g = new Position[]{new Position(3343, 9705, 0), new Position(3368, 9712, 0), new Position(3373, 9678, 0), new Position(3343, 9680, 0), new Position(3350, 9717, 1), new Position(3374, 9713, 1), new Position(3376, 9686, 1), new Position(3351, 9684, 1), new Position(3348, 9674, 2), new Position(3346, 9718, 2)};
    private static final Position[] h = new Position[]{new Position(3347, 9714, 0), new Position(3367, 9720, 0), new Position(3375, 9682, 0), new Position(3342, 9684, 0), new Position(3341, 9708, 1), new Position(3383, 9713, 1), new Position(3385, 9686, 1), new Position(3353, 9680, 1), new Position(3339, 9683, 2), new Position(3345, 9718, 2)};
    private static final Position[] i = new Position[]{new Position(3337, 9715, 0), new Position(3365, 9721, 0), new Position(3368, 9683, 0), new Position(3337, 9685, 0), new Position(3340, 9718, 1), new Position(3373, 9723, 1), new Position(3375, 9687, 1), new Position(3345, 9690, 1), new Position(3338, 9684, 2), new Position(3342, 9719, 2)};
    private static final Position[] j = new Position[]{new Position(3337, 9704, 0), new Position(3365, 9710, 0), new Position(3368, 9672, 0), new Position(3337, 9674, 0), new Position(3340, 9707, 1), new Position(3373, 9712, 1), new Position(3375, 9676, 1), new Position(3345, 9679, 1), new Position(3338, 9673, 2), new Position(3342, 9708, 2)};
    private static final Position[] k = new Position[]{new Position(3348, 9715, 0), new Position(3376, 9721, 0), new Position(3379, 9683, 0), new Position(3348, 9685, 0), new Position(3351, 9715, 1), new Position(3384, 9723, 1), new Position(3386, 9687, 1), new Position(3356, 9690, 1), new Position(3349, 9684, 2), new Position(3353, 9719, 2)};
    private static final Position[] l = new Position[]{new Position(3348, 9704, 0), new Position(3376, 9710, 0), new Position(3379, 9672, 0), new Position(3348, 9674, 0), new Position(3351, 9707, 1), new Position(3384, 9712, 1), new Position(3386, 9676, 1), new Position(3356, 9679, 1), new Position(3349, 9673, 2), new Position(3353, 9708, 2)};
    private Player m;
    public int a;
    public int b;
    public boolean c;
    public int d;
    private int n = 6888;
    private int o = 3098;
    private static Random p = new Random();

    public TelekineticTheatreController(Player player) {
        this.m = player;
    }

    public final void a() {
        Player player = this.m;
        player.packetSender.showWalkableInterface(15962);
        player = this.m;
        player.packetSender.sendInterfaceText("" + this.a, 15966);
        player = this.m;
        player.packetSender.sendInterfaceText("" + this.d, 15968);
    }

    public final void b() {
        Player player = this.m;
        player.packetSender.sendGameMessage("This feature is currently missing.");
    }

    public final void c() {
        Object object = g[this.b];
        object = new GroundItem(new ItemStack(this.n, 1), this.m, new Position(((Position)object).getX(), ((Position)object).getY(), this.m.getPosition().getPlane()));
        GroundItemManager.getInstance().spawn((GroundItem)object);
    }

    public final boolean a(Position position) {
        Position position2 = h[this.b];
        return position.getX() == position2.getX() && position.getY() == position2.getY();
    }

    public final void d() {
        if (this.m.eH()) {
            this.a += 2;
        }
        if (this.a >= 4000) {
            this.a = 4000;
        }
        ++this.d;
        this.c = true;
        if (this.d == 5) {
            if (this.m.eH()) {
                this.a += 8;
            }
            if (this.a >= 4000) {
                this.a = 4000;
            }
            this.m.getSkillManager().addExperience(6, 1000.0);
            this.m.getInventoryManager().b(new ItemStack(563, 10));
            this.d = 0;
            this.m.getDialogueManager().showTwoLineStatement("Congratulations on solving five mazes in a row, have 8 bonus points,", "10 law runes and extra magic XP!");
            return;
        }
        this.m.getDialogueManager().showOneLineStatement("Congratulations! You have received two Telekinetic Pizazz Points!");
    }

    public final void e() {
        int n = p.nextInt(10);
        while (n == this.b) {
            n = p.nextInt(10);
        }
        this.b = n;
        this.h();
    }

    public final void f() {
        Object object = e[this.b];
        this.m.b(new Position(this.m.getPosition().getX(), this.m.getPosition().getY(), ((Position)object).getPlane()), true);
        object = new Npc(this.o);
        Position position = f[this.b];
        Player player = this.m;
        if (player.H != null) {
            player = this.m;
            GameplayHelper.a(player.H);
        }
        GameplayHelper.a(this.m, new Position(position.getX(), position.getY(), this.m.getPosition().getPlane()), (Npc)object, false, false);
        if (!this.c) {
            this.c();
        }
    }

    private void h() {
        this.c = false;
        this.m.b(e[this.b], true);
        Object object = new Npc(this.o);
        Position position = f[this.b];
        Player player = this.m;
        if (player.H != null) {
            player = this.m;
            GameplayHelper.a(player.H);
        }
        GameplayHelper.a(this.m, new Position(position.getX(), position.getY(), this.m.getPosition().getPlane()), (Npc)object, false, false);
        object = g[this.b];
        object = new GroundItem(new ItemStack(this.n, 1), this.m, new Position(((Position)object).getX(), ((Position)object).getY(), this.m.getPosition().getPlane()));
        GroundItemManager.getInstance().spawn((GroundItem)object);
    }

    public final String a(int n) {
        int n2 = this.m.getPosition().getX();
        int n3 = this.m.getPosition().getY();
        if (n2 <= j[n].getX() && n3 >= j[n].getY() && n3 <= i[n].getY()) {
            return "left";
        }
        if (n2 >= l[n].getX() && n3 >= l[n].getY() && n3 <= k[n].getY()) {
            return "right";
        }
        if (n3 >= i[n].getY() && n2 >= i[n].getX() && n2 <= k[n].getX()) {
            return "upper";
        }
        if (n3 <= j[n].getY() && n2 >= j[n].getX() && n2 <= l[n].getX()) {
            return "bottom";
        }
        return "";
    }

    public final boolean b(int n) {
        if (n == 10782 && this.g()) {
            TelekineticTheatreController telekineticTheatreController = this;
            this.d = 0;
            telekineticTheatreController.c = false;
            Player player = telekineticTheatreController.m;
            if (player.H != null) {
                player = telekineticTheatreController.m;
                GameplayHelper.a(player.H);
            }
            player = telekineticTheatreController.m;
            player.packetSender.showWalkableInterface(-1);
            telekineticTheatreController.m.moveTo(MageTrainingArenaLobby.a);
            player = telekineticTheatreController.m;
            player.packetSender.sendGameMessage("You've left the Telekinetic Theatre.");
            return true;
        }
        if (n == 10778) {
            TelekineticTheatreController telekineticTheatreController = this;
            if (telekineticTheatreController.m.getSkillManager().getCurrentLevels()[6] < 33) {
                Player player = telekineticTheatreController.m;
                player.packetSender.sendGameMessage("You need a magic level of 33 to enter here.");
            } else {
                telekineticTheatreController.d = 0;
                Player player = telekineticTheatreController.m;
                player.packetSender.sendGameMessage("You've entered the Telekinetic Theatre.");
                telekineticTheatreController.b = p.nextInt(10);
                telekineticTheatreController.h();
            }
            return true;
        }
        return false;
    }

    public final boolean g() {
        int n = this.m.getPosition().getX();
        int n2 = this.m.getPosition().getY();
        return (n = GameUtil.a(n, n2)) == 13463;
    }
}

