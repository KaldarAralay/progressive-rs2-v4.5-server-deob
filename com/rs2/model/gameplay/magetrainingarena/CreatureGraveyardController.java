/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.magetrainingarena;

import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.gameplay.magetrainingarena.CreatureGraveyardHazardTask;
import com.rs2.model.gameplay.magetrainingarena.MageTrainingArenaLobby;
import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.LoadedWorldObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.objects.WorldObjectLookup;
import com.rs2.model.player.InventoryManager;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;
import com.rs2.util.TextUtil;
import java.util.Random;

public final class CreatureGraveyardController {
    private static Position[] b = new Position[]{new Position(3364, 9640, 1), new Position(3363, 9641, 1), new Position(3362, 9640, 1), new Position(3363, 9639, 1)};
    private Player c;
    private static Random d = new Random();
    private static int[] e = new int[]{555, 557, 560, 561, 565};
    public int a;

    public CreatureGraveyardController(Player player) {
        this.c = player;
    }

    private static int a(int n) {
        switch (n) {
            case 6904: {
                return 1;
            }
            case 6905: {
                return 2;
            }
            case 6906: {
                return 3;
            }
            case 6907: {
                return 4;
            }
        }
        return 0;
    }

    public final boolean a() {
        int n = this.c.getPosition().getX();
        int n2 = this.c.getPosition().getY();
        return this.c.getPosition().getPlane() == 1 && n >= 3340 && n <= 3390 && n2 >= 9610 && n2 <= 9670;
    }

    public final void b() {
        this.a -= 10;
        if (this.a < 0) {
            this.a = 0;
        }
        this.c.moveTo(MageTrainingArenaLobby.a);
        this.e();
    }

    public final void c() {
        Player player = this.c;
        player.packetSender.showWalkableInterface(15931);
        player = this.c;
        player.packetSender.sendInterfaceText("" + this.a, 15935);
    }

    public static void d() {
        World.scheduleTickTask(new CreatureGraveyardHazardTask(20));
    }

    public final boolean a(boolean bl) {
        int player = bl ? 6883 : 1963;
        int[] nArray = new int[]{this.c.getInventoryManager().getItemAmount(6904), this.c.getInventoryManager().getItemAmount(6905), this.c.getInventoryManager().getItemAmount(6906), this.c.getInventoryManager().getItemAmount(6907)};
        if (nArray[0] == 0 && nArray[1] == 0 && nArray[2] == 0 && nArray[3] == 0) {
            Player player2 = this.c;
            player2.packetSender.sendGameMessage("You don't have any bones to convert into fruits.");
            return false;
        }
        this.c.getInventoryManager().removeItem(new ItemStack(6904, nArray[0]));
        this.c.getInventoryManager().removeItem(new ItemStack(6905, nArray[1]));
        this.c.getInventoryManager().removeItem(new ItemStack(6906, nArray[2]));
        this.c.getInventoryManager().removeItem(new ItemStack(6907, nArray[3]));
        this.c.getInventoryManager().d(new ItemStack(player, nArray[0] * CreatureGraveyardController.a(6904)));
        this.c.getInventoryManager().d(new ItemStack(player, nArray[1] * CreatureGraveyardController.a(6905)));
        this.c.getInventoryManager().d(new ItemStack(player, nArray[2] * CreatureGraveyardController.a(6906)));
        this.c.getInventoryManager().d(new ItemStack(player, nArray[3] * CreatureGraveyardController.a(6907)));
        return true;
    }

    private void e() {
        this.c.getInventoryManager().removeItem(new ItemStack(6883, this.c.getInventoryManager().getItemAmount(6883)));
        this.c.getInventoryManager().removeItem(new ItemStack(1963, this.c.getInventoryManager().getItemAmount(1963)));
        this.c.getInventoryManager().removeItem(new ItemStack(6904, this.c.getInventoryManager().getItemAmount(6904)));
        this.c.getInventoryManager().removeItem(new ItemStack(6905, this.c.getInventoryManager().getItemAmount(6905)));
        this.c.getInventoryManager().removeItem(new ItemStack(6906, this.c.getInventoryManager().getItemAmount(6906)));
        this.c.getInventoryManager().removeItem(new ItemStack(6907, this.c.getInventoryManager().getItemAmount(6907)));
    }

    private void f() {
        int n;
        int n2 = this.c.getInventoryManager().getItemAmount(1963);
        if (n2 + (n = this.c.getInventoryManager().getItemAmount(6883)) == 0) {
            Player player = this.c;
            player.packetSender.sendGameMessage("You have no fruit to put in the fruit chute.");
            return;
        }
        int n3 = (n2 + n) / 16;
        if (this.c.eH()) {
            this.a += n3;
        }
        if (this.a > 4000) {
            this.a = 4000;
        }
        Player player = this.c;
        player.packetSender.sendGameMessage("You've put " + (n2 + n) + " in the food chute and receive " + n3 + " points");
        this.c.getUpdateState().setAnimation(832);
        this.e();
        if (n3 >= 0) {
            this.c.getSkillManager().addExperience(6, 25.0);
            n2 = GameUtil.h(2) + 1;
            n = e[d.nextInt(e.length)];
            ItemStack itemStack = new ItemStack(n, n2);
            this.c.getInventoryManager().b(itemStack);
            String string = "";
            string = n2 == 1 ? String.valueOf(string) + TextUtil.prependIndefiniteArticle(itemStack.getDefinition().getName()) : String.valueOf(string) + n2 + " " + itemStack.getDefinition().getName() + "s";
            this.c.getDialogueManager().showTwoLineStatement("Congratulations - you've been awarded " + string + " and extra", "magic XP.");
        }
    }

    public final boolean a(int n, int n2, int n3, int n4) {
        if (n >= 10725 && n <= 10728) {
            int n5;
            if (this.c.getInventoryManager().getContainer().getFreeSlots() <= 0) {
                Player player = this.c;
                player.packetSender.sendGameMessage("Not enough space in your inventory.");
                return true;
            }
            if (GameUtil.g(5) == 0) {
                int n6 = n4;
                n4 = n3;
                n3 = n2;
                n2 = n;
                LoadedWorldObject loadedWorldObject = WorldObjectLookup.findObjectByIdAt(n2, n3, n4, n6);
                if (n2 != 10725 && loadedWorldObject != null) {
                    ObjectManager.getInstance().removeDynamicObjectAt(n3, n4, n6, 10);
                    new DynamicObject(n2 - 1, n3, n4, n6, loadedWorldObject.getOrientation(), loadedWorldObject.getType(), n2, 20);
                }
            }
            if (GameUtil.g(3) == 0) {
                this.c.applyDirectHit(2, HitType.NORMAL);
            }
            InventoryManager inventoryManager = this.c.getInventoryManager();
            n2 = n;
            switch (n2) {
                case 10725: {
                    n5 = 6904;
                    break;
                }
                case 10726: {
                    n5 = 6905;
                    break;
                }
                case 10727: {
                    n5 = 6906;
                    break;
                }
                case 10728: {
                    n5 = 6907;
                    break;
                }
                default: {
                    n5 = 0;
                }
            }
            inventoryManager.addItem(new ItemStack(n5));
            this.c.getUpdateState().setAnimation(832);
            return true;
        }
        if (n == 10782 && this.a()) {
            CreatureGraveyardController creatureGraveyardController = this;
            creatureGraveyardController.c.moveTo(MageTrainingArenaLobby.a);
            Player player = creatureGraveyardController.c;
            player.packetSender.sendGameMessage("You've left the Creature Graveyard.");
            creatureGraveyardController.e();
            player = creatureGraveyardController.c;
            player.packetSender.showWalkableInterface(-1);
            return true;
        }
        if (n == 10781) {
            CreatureGraveyardController creatureGraveyardController = this;
            Random random = new Random();
            n4 = random.nextInt(4);
            if (creatureGraveyardController.c.getSkillManager().getCurrentLevels()[6] < 15) {
                Player player = creatureGraveyardController.c;
                player.packetSender.sendGameMessage("You need a magic level of 21 to enter here.");
            } else {
                creatureGraveyardController.c.moveTo(b[n4]);
                Player player = creatureGraveyardController.c;
                player.packetSender.sendGameMessage("You've entered the Creature Graveyard.");
            }
            return true;
        }
        if (n == 10735) {
            this.f();
            return true;
        }
        return false;
    }

    public static boolean a(DynamicObject dynamicObject) {
        if (dynamicObject == null) {
            return false;
        }
        DynamicObject dynamicObject2 = dynamicObject;
        switch (dynamicObject.getWorldObject().getObjectId()) {
            case 10725: {
                new DynamicObject(dynamicObject2.getWorldObject().getObjectId() + 1, dynamicObject2.getWorldObject().getPosition().getX(), dynamicObject2.getWorldObject().getPosition().getY(), dynamicObject2.getWorldObject().getPosition().getPlane(), dynamicObject2.getWorldObject().getOrientation(), dynamicObject2.getWorldObject().getType(), dynamicObject2.getWorldObject().getObjectId(), 20);
                return true;
            }
            case 10726: {
                new DynamicObject(dynamicObject2.getWorldObject().getObjectId() + 1, dynamicObject2.getWorldObject().getPosition().getX(), dynamicObject2.getWorldObject().getPosition().getY(), dynamicObject2.getWorldObject().getPosition().getPlane(), dynamicObject2.getWorldObject().getOrientation(), dynamicObject2.getWorldObject().getType(), dynamicObject2.getWorldObject().getObjectId(), 20);
                return true;
            }
            case 10727: {
                new DynamicObject(dynamicObject2.getWorldObject().getObjectId() + 1, dynamicObject2.getWorldObject().getPosition().getX(), dynamicObject2.getWorldObject().getPosition().getY(), dynamicObject2.getWorldObject().getPosition().getPlane(), dynamicObject2.getWorldObject().getOrientation(), dynamicObject2.getWorldObject().getType(), dynamicObject2.getWorldObject().getObjectId(), 500000);
                return true;
            }
        }
        return false;
    }
}

