/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net.packet;

import com.rs2.CacheRevisionInfo;
import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.item.ItemStack;
import com.rs2.model.message.MessageOfTheWeek;
import com.rs2.model.music.MusicTrackDefinition;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.LoadedWorldObject;
import com.rs2.model.objects.WorldObjectLookup;
import com.rs2.model.objects.functions.DoubleDoorHandler;
import com.rs2.model.player.BankRearrangeMode;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.model.quest.QuestHook;
import com.rs2.model.quest.QuestScript;
import com.rs2.model.skill.magic.Spellbook;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.net.packet.AgilityMovementCompletionEvent;
import com.rs2.net.packet.ByteOrder;
import com.rs2.net.packet.ByteTransform;
import com.rs2.net.packet.DelayedAnimationEvent;
import com.rs2.net.packet.DelayedUnlockEvent;
import com.rs2.net.packet.PacketBuffer;
import com.rs2.net.packet.PacketWriter;
import com.rs2.net.packet.QueuedPositionUnlockEvent;
import com.rs2.net.packet.RelativePositionUnlockEvent;
import com.rs2.net.packet.YAxisPositionUnlockEvent;
import com.rs2.util.GameUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.regex.Pattern;

public final class PacketSender {
    private Player a;

    public PacketSender(Player player) {
        int[] nArray = new int[]{17511, 15819, 15812, 15801, 15791, 15774, 15767};
        this.a = player;
    }

    public final PacketSender sendMinimapState(int n) {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(2);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 99);
        packetWriter.writeByte(n);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final void sendMusicTrack(MusicTrackDefinition object) {
        this.sendInterfaceText(((MusicTrackDefinition)object).getName(), 4439);
        int n = ((MusicTrackDefinition)object).getTrackId();
        object = this;
        if (!((PacketSender)object).a.de && ((PacketSender)object).a.ax != n) {
            ((PacketSender)object).a.ax = n;
            if (n != -1) {
                PacketWriter packetWriter = PacketBuffer.allocateWriter(3);
                packetWriter.writeOpcode(((PacketSender)object).a.getOutboundCipher(), 74);
                packetWriter.writeShort(n, ByteOrder.LITTLE);
                ((PacketSender)object).a.writePacketBuffer(packetWriter.getBuffer());
            }
        }
    }

    public final void lockPlayerForTicks(int n) {
        this.a.n(true);
        CycleEventHandler.getInstance().schedule(this.a, new DelayedUnlockEvent(this), n);
    }

    public final PacketSender sendSystemUpdateTimer(int n) {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(4);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 114);
        packetWriter.writeShort(n, ByteOrder.LITTLE);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final void refreshSidebarInterfaces() {
        int[] nArray = new int[14];
        nArray[0] = 2423;
        nArray[1] = 3917;
        nArray[2] = 638;
        nArray[3] = 3213;
        nArray[4] = 1644;
        nArray[5] = 5608;
        nArray[7] = -1;
        nArray[8] = 5065;
        nArray[9] = 5715;
        nArray[10] = 2449;
        nArray[11] = 904;
        nArray[12] = 147;
        nArray[13] = 962;
        int[] nArray2 = nArray;
        int n = 0;
        while (n < 14) {
            this.setSidebarInterface(n, nArray2[n]);
            if (n == 6) {
                if (this.a.getSpellbook() == Spellbook.MODERN) {
                    this.setSidebarInterface(n, 1151);
                }
                if (this.a.getSpellbook() == Spellbook.ANCIENT) {
                    this.setSidebarInterface(n, 12855);
                }
                if (this.a.getSpellbook() == Spellbook.NECROMANCY) {
                    this.setSidebarInterface(n, 19104);
                }
            }
            ++n;
        }
        this.a.getEquipmentManager().refreshWeaponInterface();
    }

    public final boolean b() {
        boolean bl;
        block7: {
            int n;
            ArrayList<Integer> arrayList;
            block8: {
                bl = true;
                if (ServerSettings.completeMissingQuestsMode <= 0) break block7;
                ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
                arrayList = new ArrayList<Integer>();
                n = 1;
                while (n < QuestDefinition.a) {
                    QuestScript questScript = QuestDefinition.a(n);
                    if (questScript.b() == -1) {
                        arrayList.add(n);
                    } else {
                        arrayList2.add(n);
                    }
                    ++n;
                }
                n = 0;
                while (n < arrayList2.size()) {
                    int n2 = (Integer)arrayList2.get(n);
                    if (this.a.getQuestState(n2) != 1) {
                        bl = false;
                    }
                    ++n;
                }
                if (ServerSettings.completeMissingQuestsMode != 1 && (ServerSettings.completeMissingQuestsMode != 2 || !bl)) break block8;
                n = 0;
                while (n < arrayList.size()) {
                    int n3 = (Integer)arrayList.get(n);
                    this.a.setQuestState(n3, 1);
                    ++n;
                }
                break block7;
            }
            if (ServerSettings.completeMissingQuestsMode != 2 || bl) break block7;
            n = 0;
            while (n < arrayList.size()) {
                int n4 = (Integer)arrayList.get(n);
                this.a.setQuestState(n4, 0);
                ++n;
            }
        }
        return bl;
    }

    public final PacketSender sendPostLoginState() {
        PacketSender packetSender;
        int n;
        Object object;
        Object object2;
        PacketSender packetSender2;
        if (!this.a.bs().equals("127.0.0.1")) {
            Server.f().e = true;
        }
        this.sendPlayerIndex();
        Object object3 = this;
        if (((PacketSender)object3).a.de) {
            packetSender2 = object3;
        } else {
            PacketWriter packetWriter = PacketBuffer.allocateWriter(1);
            packetWriter.writeOpcode(((PacketSender)object3).a.getOutboundCipher(), 107);
            ((PacketSender)object3).a.writePacketBuffer(packetWriter.getBuffer());
            packetSender2 = object3;
        }
        this.sendMapRegion();
        this.sendRunEnergy();
        object3 = this;
        ((PacketSender)object3).b();
        int n2 = 1;
        while (n2 < QuestDefinition.a) {
            Player player;
            object2 = QuestDefinition.b(n2);
            object = QuestDefinition.a(n2);
            n = ((QuestDefinition)object2).d();
            if (ServerSettings.recolorMissingQuests && ((QuestHook)object).b() == -1 && ((PacketSender)object3).a.getQuestState(n2, true) != 1) {
                player = ((PacketSender)object3).a;
                player.packetSender.sendInterfaceTextColor(n, new Color(102, 102, 102));
            } else if (((PacketSender)object3).a.getQuestState(n2, true) == 0) {
                player = ((PacketSender)object3).a;
                player.packetSender.sendInterfaceTextColor(n, Color.RED);
            } else if (((PacketSender)object3).a.getQuestState(n2, true) == 1) {
                player = ((PacketSender)object3).a;
                player.packetSender.sendInterfaceTextColor(n, Color.GREEN);
            } else if (((PacketSender)object3).a.getQuestState(n2, true) >= 2) {
                player = ((PacketSender)object3).a;
                player.packetSender.sendInterfaceTextColor(n, Color.YELLOW);
            }
            ++n2;
        }
        object3 = this;
        if (((PacketSender)object3).a.de) {
            packetSender = object3;
        } else {
            PacketWriter packetWriter = PacketBuffer.allocateWriter(4);
            packetWriter.writeOpcode(((PacketSender)object3).a.getOutboundCipher(), 206);
            packetWriter.writeByte(((PacketSender)object3).a.getPublicChatMode());
            packetWriter.writeByte(((PacketSender)object3).a.getPrivateChatMode());
            packetWriter.writeByte(((PacketSender)object3).a.getTradeMode());
            ((PacketSender)object3).a.writePacketBuffer(packetWriter.getBuffer());
            packetSender = object3;
        }
        this.a.getEquipmentManager().refreshCarriedValue();
        this.a.getCompostBinManager().processRotting();
        this.a.getAllotmentPatchManager().processGrowth();
        this.a.getFlowerPatchManager().processGrowth();
        this.a.getHerbPatchManager().processGrowth();
        this.a.getHopsPatchManager().processGrowth();
        this.a.getBushPatchManager().processGrowth();
        this.a.getAllotmentPatchManager().processGrowth();
        this.a.getTreePatchManager().processGrowth();
        this.a.getFruitTreePatchManager().processGrowth();
        this.a.getSpecialTreePatchManager().processGrowth();
        this.a.getSpecialCropPatchManager().processGrowth();
        int n3 = 0;
        while (n3 < this.a.cT().length) {
            this.a.getInventoryManager().addItem(new ItemStack(this.a.cT()[n3], this.a.cU()[n3]));
            ++n3;
        }
        n3 = 0;
        while (n3 < 4) {
            this.a.getTreePatchManager().scheduleStumpRegrowth(n3);
            ++n3;
        }
        this.a.getSocialManager().initializePrivateMessaging();
        if (this.a.getQuestState(0) == 1) {
            object3 = this;
            if (!((PacketSender)object3).a.de) {
                PacketSender packetSender3;
                int n4 = GameplayHelper.a(((PacketSender)object3).a.eh, System.currentTimeMillis());
                object2 = MessageOfTheWeek.a(Server.i);
                int n5 = ((MessageOfTheWeek)object2).b();
                n = ((PacketSender)object3).a.aQ();
                int n6 = n4;
                n4 = PacketSender.b(((PacketSender)object3).a.ek);
                n3 = 0;
                boolean bl = ((PacketSender)object3).a.isMember();
                n3 = 200;
                PacketSender packetSender4 = object3;
                if (packetSender4.a.de) {
                    packetSender3 = packetSender4;
                } else {
                    PacketWriter packetWriter = PacketBuffer.allocateWriter(18);
                    packetWriter.writeOpcode(packetSender4.a.getOutboundCipher(), 176);
                    packetWriter.writeShort(200);
                    packetWriter.writeShort(0, ByteTransform.ADD);
                    packetWriter.writeByte(bl ? 1 : 0);
                    packetWriter.writeByte(ServerSettings.freeToPlayWorld ? 0 : 1);
                    packetWriter.writeByte(ServerSettings.maxLevel);
                    packetWriter.writeInt(n4);
                    packetWriter.writeShort(n6);
                    packetWriter.writeShort(n);
                    packetWriter.writeShort(n5);
                    packetSender4.a.writePacketBuffer(packetWriter.getBuffer());
                    packetSender3 = packetSender4;
                }
                object = "\\nYou do not have a Bank PIN.\\nPlease visit a bank if you would like one.";
                if (((PacketSender)object3).a.getBankPinManager().hasPin()) {
                    object = "\\nYou have set a Bank PIN.";
                }
                ((PacketSender)object3).sendInterfaceText((String)object, 15270);
                n = 0;
                if (((MessageOfTheWeek)object2).b() == 5993) {
                    n = 1;
                }
                ((PacketSender)object3).sendInterfaceText(((MessageOfTheWeek)object2).c(), n != 0 ? 6002 : ((MessageOfTheWeek)object2).b() + 4);
                ((PacketSender)object3).sendInterfaceText(((MessageOfTheWeek)object2).d()[0], n != 0 ? 15491 : ((MessageOfTheWeek)object2).b() + 2);
                ((PacketSender)object3).sendInterfaceText(((MessageOfTheWeek)object2).d()[1], n != 0 ? 15492 : ((MessageOfTheWeek)object2).b() + 3);
            }
            this.sendGameMessage("Welcome to " + ServerSettings.serverName + "." + (ServerSettings.showServerEmulatorInWelcome ? " (Emulation run by: " + ServerSettings.serverEmulatorName + ")" : ""));
            String string = "";
            CacheRevisionInfo cacheRevisionInfo = CacheRevisionInfo.a(ServerSettings.cacheVersion);
            if (cacheRevisionInfo != null) {
                if (cacheRevisionInfo.a != null) {
                    string = " (From: " + cacheRevisionInfo.a + ")";
                }
                if (cacheRevisionInfo.b != null) {
                    string = String.valueOf(string) + " - Updates:";
                }
            }
            this.sendGameMessage("Running RS2 Build #" + ServerSettings.cacheVersion + string);
            if (cacheRevisionInfo != null && cacheRevisionInfo.b != null) {
                int n7 = 0;
                while (n7 < cacheRevisionInfo.b.length) {
                    boolean bl = false;
                    int n8 = n7++;
                    object3 = cacheRevisionInfo;
                    n = n8;
                    object = object3;
                    this.sendGameMessage(String.valueOf(((CacheRevisionInfo)object).b[n]) + "#url#");
                }
            }
            if (this.a.dQ) {
                this.sendGameMessage("Your account file was somehow corrupted and had to be loaded from backup.");
            }
            this.a.el = true;
        }
        return this;
    }

    private static int b(String stringArray) {
        int n = 0;
        int n2 = 0;
        String[] stringArray2 = new String[4];
        String[] stringArray3 = stringArray.split(Pattern.quote("."));
        int n3 = stringArray3.length;
        int n4 = 0;
        while (n4 < n3) {
            stringArray = stringArray3[n4];
            stringArray2[n2] = stringArray;
            ++n2;
            ++n4;
        }
        stringArray = String.valueOf(stringArray2[1]) + "." + stringArray2[0] + "." + stringArray2[3] + "." + stringArray2[2];
        stringArray = stringArray.split(Pattern.quote("."));
        int n5 = stringArray.length;
        n3 = 0;
        while (n3 < n5) {
            String string = stringArray[n3];
            n <<= 8;
            n |= Integer.parseInt(string);
            ++n3;
        }
        return n;
    }

    public final PacketSender syncPlayerConfigs() {
        this.refreshAutocastConfig();
        this.sendConfig(166, this.a.getBrightness());
        this.sendConfig(168, this.a.getMusicVolume());
        this.sendConfig(169, this.a.getEffectVolume());
        this.sendConfig(170, this.a.getMouseButtons());
        this.sendConfig(171, this.a.getPublicChatEffects());
        this.sendConfig(172, this.a.isAutoRetaliate() ? 0 : 1);
        this.sendConfig(173, this.a.getMovementQueue().isRunning() ? 1 : 0);
        this.sendConfig(287, this.a.getSplitPrivateChat());
        this.sendConfig(427, this.a.isAcceptAidEnabled() ? 1 : 0);
        this.sendConfig(115, this.a.isBankWithdrawNoteMode() ? 1 : 0);
        this.sendConfig(304, this.a.getBankRearrangeMode().equals((Object)BankRearrangeMode.a) ? 0 : 1);
        return this;
    }

    public final PacketSender sendEnterInputPrompt(int n) {
        this.a.setSelectedInterfaceId(n);
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(1);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 27);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender modifySkillLevel(int n, int n2, boolean bl) {
        int n3 = this.a.getSkillManager().getBaseLevel(n) + n2;
        if (this.a.getPlayerRights() > 1 && ServerSettings.debugModeEnabled) {
            System.out.println("current: " + this.a.getSkillManager().getBaseLevel(n) + " max: " + n3);
        }
        if (!bl) {
            int[] nArray = this.a.getSkillManager().getCurrentLevels();
            int n4 = n;
            nArray[n4] = nArray[n4] + n2;
            if (this.a.getSkillManager().getCurrentLevels()[n] < 0) {
                this.a.getSkillManager().getCurrentLevels()[n] = 0;
            }
            this.a.getSkillManager().refreshSkill(n);
            return this;
        }
        if (n2 < 0) {
            if (this.a.getSkillManager().getCurrentLevels()[n] < n3) {
                return this;
            }
            if (this.a.getSkillManager().getCurrentLevels()[n] + n2 < n3) {
                this.a.getSkillManager().getCurrentLevels()[n] = n3;
            } else {
                int[] nArray = this.a.getSkillManager().getCurrentLevels();
                int n5 = n;
                nArray[n5] = nArray[n5] + n2;
            }
        } else {
            if (this.a.getSkillManager().getCurrentLevels()[n] > n3) {
                return this;
            }
            if (this.a.getSkillManager().getCurrentLevels()[n] + n2 > n3) {
                this.a.getSkillManager().getCurrentLevels()[n] = n3;
            } else {
                int[] nArray = this.a.getSkillManager().getCurrentLevels();
                int n6 = n;
                nArray[n6] = nArray[n6] + n2;
            }
        }
        this.a.getSkillManager().refreshSkill(n);
        return this;
    }

    /*
     * WARNING - void declaration
     */
    public final int modifySkillLevelReturningRemainder(int n, int n2, boolean bl) {
        void var3_7;
        boolean bl2 = false;
        int n3 = this.a.getSkillManager().getBaseLevel(n);
        int n4 = this.a.getSkillManager().getCurrentLevels()[n];
        if (n2 < 0) {
            if (n4 + n2 < 0) {
                int n5 = n4 + n2;
                this.a.getSkillManager().getCurrentLevels()[n] = 0;
            } else {
                int[] nArray = this.a.getSkillManager().getCurrentLevels();
                int n6 = n;
                nArray[n6] = nArray[n6] + n2;
            }
        } else if (n4 + n2 > n3) {
            int n7 = n2 - (n3 - n4);
            this.a.getSkillManager().getCurrentLevels()[n] = n3;
        } else {
            int[] nArray = this.a.getSkillManager().getCurrentLevels();
            int n8 = n;
            nArray[n8] = nArray[n8] + n2;
        }
        this.a.getSkillManager().refreshSkill(n);
        return Math.abs((int)var3_7);
    }

    public final PacketSender queueAgilityMovement(int n, int n2, boolean bl, int n3, int n4, int n5, double d, boolean bl2, String string) {
        if (this.a.isStunned() || this.a.isMovementLocked()) {
            return this;
        }
        if (bl2) {
            this.a.getMovementQueue().setRunning(false);
        }
        this.a.n(true);
        this.a.aw = true;
        if (n3 > 0) {
            this.a.ak(n3);
            this.a.f(true);
        }
        this.a.getMovementQueue().clear();
        this.a.getMovementQueue().addStep(new Position(this.a.getPosition().getX() + n, this.a.getPosition().getY() + n2));
        this.a.getMovementQueue().removeFirstStep();
        CycleEventHandler.getInstance().schedule(this.a, new AgilityMovementCompletionEvent(this, string, d, bl2, true), n4 > 0 ? n5 - 3 : n5);
        if (n4 > 0) {
            CycleEventHandler.getInstance().schedule(this.a, new DelayedAnimationEvent(this, n4), n5);
        }
        return this;
    }

    public final PacketSender queueTwoStepMovement(Position[] object, int n, boolean bl) {
        if (this.a.isStunned() || this.a.isMovementLocked()) {
            return this;
        }
        this.a.n(true);
        this.a.setInteractionTarget(null);
        this.a.getUpdateState().setFaceEntity(65535);
        this.a.aw = true;
        this.a.getMovementQueue().clear();
        Position[] positionArray = object;
        n = 0;
        while (n < 2) {
            object = positionArray[n];
            this.a.getMovementQueue().addStep((Position)object);
            ++n;
        }
        this.a.getMovementQueue().removeFirstStep();
        CycleEventHandler.getInstance().schedule(this.a, new QueuedPositionUnlockEvent(this, true), 2);
        return this;
    }

    public final PacketSender queueRelativeMovementStep(int n, int n2, boolean bl) {
        if (this.a.isStunned() || this.a.isMovementLocked()) {
            return this;
        }
        this.a.n(true);
        if (bl) {
            this.a.aw = true;
        }
        this.a.getMovementQueue().clear();
        this.a.getMovementQueue().addStep(new Position(this.a.getPosition().getX() + n, this.a.getPosition().getY() + n2));
        this.a.getMovementQueue().removeFirstStep();
        CycleEventHandler.getInstance().schedule(this.a, new RelativePositionUnlockEvent(this, bl), 1);
        return this;
    }

    public final PacketSender queueYAxisMovementStep(int n, int n2, int n3, boolean bl) {
        if (this.a.isStunned() || this.a.isMovementLocked()) {
            return this;
        }
        this.a.n(true);
        this.a.aw = true;
        this.a.getMovementQueue().clear();
        this.a.getMovementQueue().addStep(new Position(this.a.getPosition().getX(), this.a.getPosition().getY() + n2));
        this.a.getMovementQueue().removeFirstStep();
        CycleEventHandler.getInstance().schedule(this.a, new YAxisPositionUnlockEvent(this, true), 2);
        return this;
    }

    public final PacketSender queueAbsoluteMovementStep(int n, int n2) {
        if (this.a.isStunned() || this.a.isMovementLocked()) {
            return this;
        }
        this.a.getMovementQueue().clear();
        this.a.getMovementQueue().addStep(new Position(n, n2));
        this.a.getMovementQueue().removeFirstStep();
        return this;
    }

    public final PacketSender sendStillGraphicToNearbyPlayers(int n, int n2, int n3, int n4, int n5) {
        Player[] playerArray = World.f();
        int n6 = playerArray.length;
        int n7 = 0;
        while (n7 < n6) {
            Player player = playerArray[n7];
            if (player != null && player.getPosition().getPlane() == n4 && GameUtil.a(n2, n3, player.getPosition().getX(), player.getPosition().getY(), 25)) {
                player.packetSender.sendStillGraphic(n, new Position(n2, n3, n4), n5);
            }
            ++n7;
        }
        return this;
    }

    public final PacketSender sendSkillUpdate(int n, int n2, double d) {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(7);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 134);
        packetWriter.writeByte(n);
        packetWriter.writeInt((int)d, ByteOrder.MIDDLE);
        packetWriter.writeByte(n2);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendInterfaceModelRotation(int n, int n2, int n3, int n4) {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(10);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 230);
        packetWriter.writeShort(n4, ByteTransform.ADD);
        packetWriter.writeShort(n);
        packetWriter.writeShort(513);
        packetWriter.writeShort(n3, ByteTransform.ADD, ByteOrder.LITTLE);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender selectMagicSidebarTab(int n) {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(2);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 106);
        packetWriter.writeByte(6, ByteTransform.NEGATE);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendInterfaceOffset(int n, int n2, int n3) {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(7);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 70);
        packetWriter.writeShort(n);
        packetWriter.writeShort(n2, ByteOrder.LITTLE);
        packetWriter.writeShort(n3, ByteOrder.LITTLE);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendEntityHintIcon(int n, int n2) {
        this.a.bP = n2;
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(7);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 254);
        packetWriter.writeByte(n);
        packetWriter.writeShort(n2);
        packetWriter.writeByte(0);
        packetWriter.writeByte(0);
        packetWriter.writeByte(0);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendPositionHintIcon(int n, int n2, int n3, int n4) {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(7);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 254);
        packetWriter.writeByte(n4);
        packetWriter.writeShort(n);
        packetWriter.writeShort(n2);
        packetWriter.writeByte(n3);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendInterfaceSlotItem(ItemStack itemStack, int n, int n2, int n3) {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(32);
        packetWriter.startVariableShortPacket(this.a.getOutboundCipher(), 34);
        packetWriter.writeShort(n2);
        packetWriter.writeByte(n);
        if (itemStack == null) {
            packetWriter.writeShort(0);
            packetWriter.writeByte(0);
        } else {
            packetWriter.writeShort(itemStack.getId() + 1);
            packetWriter.writeByte(n3);
        }
        packetWriter.finishVariableShortPacket();
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendInterfaceSlotItem(int n, int n2, ItemStack itemStack) {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(32);
        packetWriter.startVariableShortPacket(this.a.getOutboundCipher(), 34);
        packetWriter.writeShort(n2);
        packetWriter.writeByte(n);
        if (itemStack.getId() == 0) {
            packetWriter.writeShort(0);
            packetWriter.writeByte(0);
        } else {
            packetWriter.writeShort(itemStack.getId() + 1);
            if (itemStack.getAmount() > 254) {
                packetWriter.writeByte(255);
                packetWriter.writeShort(itemStack.getAmount());
            } else {
                packetWriter.writeByte(itemStack.getAmount());
            }
        }
        packetWriter.finishVariableShortPacket();
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendSingleItemContainer(int n, int n2, int n3) {
        if (this.a.de) {
            return this;
        }
        if (n >= InterfaceDefinition.interfaceCount) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(8192);
        packetWriter.startVariableShortPacket(this.a.getOutboundCipher(), 53);
        packetWriter.writeShort(n);
        packetWriter.writeShort(1);
        if (n2 > 0) {
            if (n3 > 254) {
                packetWriter.writeByte(255);
                packetWriter.writeInt(n3, ByteOrder.INVERSE_MIDDLE);
            } else {
                packetWriter.writeByte(n3);
            }
            packetWriter.writeShort(n2 + 1, ByteTransform.ADD, ByteOrder.LITTLE);
        } else {
            packetWriter.writeByte(0);
            packetWriter.writeShort(0, ByteTransform.ADD, ByteOrder.LITTLE);
        }
        packetWriter.finishVariableShortPacket();
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendItemContainer(int n, ItemStack[] itemStackArray) {
        if (this.a.de) {
            return this;
        }
        if (n >= InterfaceDefinition.interfaceCount) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(8192);
        packetWriter.startVariableShortPacket(this.a.getOutboundCipher(), 53);
        packetWriter.writeShort(n);
        packetWriter.writeShort(itemStackArray.length);
        ItemStack[] itemStackArray2 = itemStackArray;
        int n2 = itemStackArray.length;
        int n3 = 0;
        while (n3 < n2) {
            ItemStack itemStack = itemStackArray2[n3];
            if (itemStack != null) {
                if (itemStack.getAmount() > 254) {
                    packetWriter.writeByte(255);
                    packetWriter.writeInt(itemStack.getAmount(), ByteOrder.INVERSE_MIDDLE);
                } else {
                    packetWriter.writeByte(itemStack.getAmount());
                }
                packetWriter.writeShort(itemStack.getId() + 1, ByteTransform.ADD, ByteOrder.LITTLE);
            } else {
                packetWriter.writeByte(0);
                packetWriter.writeShort(0, ByteTransform.ADD, ByteOrder.LITTLE);
            }
            ++n3;
        }
        packetWriter.finishVariableShortPacket();
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendObjectCreate(int n, int n2, int n3, int n4, int n5, int n6) {
        if (this.a.de) {
            return this;
        }
        this.b(new Position(n2, n3, n4));
        PacketWriter packetWriter = PacketBuffer.allocateWriter(5);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 151);
        packetWriter.writeByte(0, ByteTransform.SUBTRACT);
        packetWriter.writeShort(n, ByteOrder.LITTLE);
        packetWriter.writeByte((n6 << 2) + (n5 & 3), ByteTransform.SUBTRACT);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender closeInterface(int n) {
        if (this.a.de) {
            return this;
        }
        if (n >= InterfaceDefinition.interfaceCount) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(4);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 218);
        packetWriter.writeShort(n, ByteTransform.ADD, ByteOrder.LITTLE);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendGameMessage(String string) {
        if (this.a.de) {
            return this;
        }
        if (string == null) {
            return this;
        }
        if (this.a.getQuestState(0) != 1) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(string.length() + 3);
        packetWriter.startVariableBytePacket(this.a.getOutboundCipher(), 253);
        packetWriter.writeString(string);
        packetWriter.finishVariableBytePacket();
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendAccountStatus() {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(4);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 250);
        packetWriter.writeByte(this.a.getPlayerRights());
        packetWriter.writeByte(0);
        packetWriter.writeByte(this.a.gameMode);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        this.a.f(true);
        return this;
    }

    public final void clearSidebarInterfaces() {
        int n = 0;
        while (n < 14) {
            this.setSidebarInterface(n, -1);
            ++n;
        }
    }

    public final PacketSender setSidebarInterface(int n, int n2) {
        this.a.setSidebarInterfaceId(n, n2);
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(4);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 71);
        packetWriter.writeShort(n2);
        packetWriter.writeByte(n, ByteTransform.ADD);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendInterfaceItemModel(int n, int n2) {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(5);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 16);
        packetWriter.writeShort(n);
        packetWriter.writeShort(n2);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendInterfaceProgress(int n, int n2) {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(4);
        if (n2 < 0) {
            n2 = 0;
        }
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 18);
        packetWriter.writeShort(n);
        packetWriter.writeByte(n2);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendProjectile(Position object, int n, int n2, byte by, byte by2, int n3, int n4, int n5, int n6, int n7, int n8) {
        if (this.a.de) {
            return this;
        }
        if (n > 1) {
            this.a(new Position(((Position)object).getX() + n / 2, ((Position)object).getY() + n / 2));
        } else {
            this.a((Position)object);
        }
        object = PacketBuffer.allocateWriter(16);
        ((PacketWriter)object).writeOpcode(this.a.getOutboundCipher(), 117);
        ((PacketWriter)object).writeByte(50);
        ((PacketWriter)object).writeByte(by);
        ((PacketWriter)object).writeByte(by2);
        ((PacketWriter)object).writeShort(n2);
        ((PacketWriter)object).writeShort(n3);
        ((PacketWriter)object).writeByte(n6);
        ((PacketWriter)object).writeByte(n7);
        ((PacketWriter)object).writeShort(n4);
        ((PacketWriter)object).writeShort(n5);
        ((PacketWriter)object).writeByte(n8);
        ((PacketWriter)object).writeByte(64);
        this.a.writePacketBuffer(((PacketWriter)object).getBuffer());
        return this;
    }

    private PacketSender a(Position position) {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(3);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 85);
        int n = position.getY() - (this.a.bt().getRegionY() << 3) - 2;
        int n2 = position.getX() - (this.a.bt().getRegionX() << 3) - 3;
        packetWriter.writeByte(n, ByteTransform.NEGATE);
        packetWriter.writeByte(n2, ByteTransform.NEGATE);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendMapRegion() {
        this.a.bt().set(this.a.getPosition());
        this.a.eE();
        int n = this.a.bt().getRegionX() << 3;
        int n2 = this.a.bt().getRegionY() << 3;
        this.a.g = this.a.getPosition().getX() - n;
        this.a.h = this.a.getPosition().getY() - n2;
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(5);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 73);
        packetWriter.writeShort(this.a.getPosition().getRegionX() + 6, ByteTransform.ADD);
        packetWriter.writeShort(this.a.getPosition().getRegionY() + 6);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendLogout() {
        this.a.cn = true;
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(1);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 109);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender showInterface(int n) {
        this.a.setOpenInterfaceId(n);
        if (this.a.de) {
            return this;
        }
        if (n >= InterfaceDefinition.interfaceCount && n == 12140) {
            n = 8680;
        }
        if (n >= InterfaceDefinition.interfaceCount) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(3);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 97);
        packetWriter.writeShort(n);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender showWalkableInterface(int n) {
        if (this.a.de) {
            return this;
        }
        if (n >= InterfaceDefinition.interfaceCount) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(3);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 208);
        packetWriter.writeShort(n, ByteOrder.LITTLE);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendInterfaceScrollPosition(int n, int n2) {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(6);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 79);
        packetWriter.writeShort(8717, ByteOrder.LITTLE);
        packetWriter.writeShort(0, ByteTransform.ADD);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendMultiwayAreaState(boolean bl) {
        if (bl == this.a.cf) {
            return this;
        }
        this.a.cf = bl;
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(5);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 61);
        packetWriter.writeByte(bl ? 1 : 0);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender showInterfaceWithInventory(int n, int n2) {
        this.a.setOpenInterfaceId(n);
        this.a.setInventoryOverlayInterfaceId(n2);
        if (this.a.de) {
            return this;
        }
        if (n2 >= InterfaceDefinition.interfaceCount) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(5);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 248);
        packetWriter.writeShort(n, ByteTransform.ADD);
        packetWriter.writeShort(n2);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender flashSidebarIcon(int n) {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(3);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 24);
        packetWriter.writeByte(-n, ByteTransform.ADD);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender closeInterfaces() {
        this.a.setOpenInterfaceId(0);
        this.a.setInventoryOverlayInterfaceId(0);
        this.a.W = 0;
        this.a.X = 0;
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(2);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 219);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    private PacketSender b(Position position) {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(3);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 85);
        int n = position.getY() - 8 * this.a.bt().getRegionY();
        int n2 = position.getX() - 8 * this.a.bt().getRegionX();
        packetWriter.writeByte(n, ByteTransform.NEGATE);
        packetWriter.writeByte(n2, ByteTransform.NEGATE);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendGroundItemCreate(GroundItem groundItem) {
        if (this.a.de) {
            return this;
        }
        this.b(groundItem.getPosition());
        PacketWriter packetWriter = PacketBuffer.allocateWriter(8);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 44);
        packetWriter.writeShort(groundItem.getItem().getId(), ByteTransform.ADD, ByteOrder.LITTLE);
        packetWriter.writeInt(groundItem.getItem().getAmount());
        packetWriter.writeByte(0);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendGroundItemRemove(GroundItem groundItem) {
        if (this.a.de) {
            return this;
        }
        this.b(groundItem.getPosition());
        PacketWriter packetWriter = PacketBuffer.allocateWriter(4);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 156);
        packetWriter.writeByte(0, ByteTransform.SUBTRACT);
        packetWriter.writeShort(groundItem.getItem().getId());
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendConfig(int n, int n2) {
        if (this.a.de) {
            return this;
        }
        if (n2 < 128 && -128 <= n2) {
            PacketWriter packetWriter = PacketBuffer.allocateWriter(4);
            packetWriter.writeOpcode(this.a.getOutboundCipher(), 36);
            packetWriter.writeShort(n, ByteOrder.LITTLE);
            packetWriter.writeByte(n2);
            this.a.writePacketBuffer(packetWriter.getBuffer());
        } else {
            PacketWriter packetWriter = PacketBuffer.allocateWriter(7);
            packetWriter.writeOpcode(this.a.getOutboundCipher(), 87);
            packetWriter.writeShort(n, ByteOrder.LITTLE);
            packetWriter.writeInt(n2, ByteOrder.MIDDLE);
            this.a.writePacketBuffer(packetWriter.getBuffer());
        }
        return this;
    }

    public final PacketSender sendInterfaceTextColor(int n, Color color) {
        if (this.a.de) {
            return this;
        }
        if (n >= InterfaceDefinition.interfaceCount) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(5);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 122);
        packetWriter.writeShort(n, ByteTransform.ADD, ByteOrder.LITTLE);
        n = color.getRed() >> 3 & 0x1F;
        int n2 = color.getGreen() >> 3 & 0x1F;
        int n3 = color.getBlue() >> 3 & 0x1F;
        packetWriter.writeShort(n << 10 | n2 << 5 | n3, ByteTransform.ADD, ByteOrder.LITTLE);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendInterfaceText(String string, int n) {
        if (this.a.de) {
            return this;
        }
        if (n >= InterfaceDefinition.interfaceCount) {
            if (n == 12144) {
                n = 8684;
            } else if (n == 12147) {
                n = 8925;
            } else if (n == 12150) {
                n = 8929;
                this.sendInterfaceText("", 8928);
            } else if (n == 12151) {
                n = 8930;
            } else if (n == 12152) {
                n = 8931;
            } else if (n == 12153) {
                n = 8932;
            } else if (n == 12154) {
                n = 8933;
            }
        }
        if (n >= InterfaceDefinition.interfaceCount) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(string.length() + 6);
        packetWriter.startVariableShortPacket(this.a.getOutboundCipher(), 126);
        packetWriter.writeString(string);
        packetWriter.writeShort(n, ByteTransform.ADD);
        packetWriter.finishVariableShortPacket();
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendFriendStatus(long l, int n) {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(10);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 50);
        if (n != 0) {
            n += 9;
        }
        packetWriter.writeLong(l);
        packetWriter.writeByte(n);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendPrivateMessagingStatus(int n) {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(2);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 221);
        packetWriter.writeByte(2);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendPrivateMessage(long l, int n, int n2, int n3, byte[] byArray, int n4) {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(2048);
        packetWriter.startVariableBytePacket(this.a.getOutboundCipher(), 196);
        packetWriter.writeLong(l);
        packetWriter.writeInt(this.a.getSocialManager().nextPrivateMessageId());
        packetWriter.writeByte(n);
        packetWriter.writeByte(0);
        packetWriter.writeByte(n3);
        packetWriter.writeBytes(byArray, n4);
        packetWriter.finishVariableBytePacket();
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendInterfaceModel(int n, int n2, int n3) {
        if (this.a.de) {
            return this;
        }
        if (n >= InterfaceDefinition.interfaceCount && n == 12145) {
            n = 8923;
        }
        if (n >= InterfaceDefinition.interfaceCount) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(7);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 246);
        packetWriter.writeShort(n == 0 ? -1 : n, ByteOrder.LITTLE);
        packetWriter.writeShort(n2);
        packetWriter.writeShort(n3);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendInterfaceModelId(int n, int n2) {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(5);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 8);
        packetWriter.writeShort(n, ByteTransform.ADD, ByteOrder.LITTLE);
        packetWriter.writeShort(n2);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendStillGraphic(int n, Position position, int n2) {
        if (this.a.de) {
            return this;
        }
        this.b(position);
        PacketWriter packetWriter = PacketBuffer.allocateWriter(7);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 4);
        packetWriter.writeByte(0);
        packetWriter.writeShort(n);
        packetWriter.writeByte(position.getPlane());
        packetWriter.writeShort(n2);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender showChatboxInterface(int n) {
        this.a.setOpenInterfaceId(n);
        if (this.a.de) {
            return this;
        }
        if (n >= InterfaceDefinition.interfaceCount) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(3);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 164);
        packetWriter.writeShort(n, ByteOrder.LITTLE);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendInterfaceAnimation(int n, int n2) {
        if (this.a.de) {
            return this;
        }
        if (n >= InterfaceDefinition.interfaceCount) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(5);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 200);
        packetWriter.writeShort(n);
        packetWriter.writeShort(n2);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendObjectAnimation(int n, int n2, int n3, int n4) {
        if (this.a.de) {
            return this;
        }
        LoadedWorldObject loadedWorldObject = WorldObjectLookup.findObjectAt(n, n2, n3);
        if (loadedWorldObject == null) {
            return this;
        }
        this.b(new Position(n, n2, n3));
        PacketWriter packetWriter = PacketBuffer.allocateWriter(5);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 160);
        packetWriter.writeByte(0, ByteTransform.SUBTRACT);
        packetWriter.writeByte((loadedWorldObject.getType() << 2) + (loadedWorldObject.getOrientation() & 3), ByteTransform.SUBTRACT);
        packetWriter.writeShort(127, ByteTransform.ADD);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendPlayerHeadOnInterface(int n) {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(3);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 185);
        packetWriter.writeShort(n, ByteTransform.ADD, ByteOrder.LITTLE);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendNpcHeadOnInterface(int n, int n2) {
        if (this.a.de) {
            return this;
        }
        if (n2 >= InterfaceDefinition.interfaceCount) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(5);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 75);
        packetWriter.writeShort(n, ByteTransform.ADD, ByteOrder.LITTLE);
        packetWriter.writeShort(n2, ByteTransform.ADD, ByteOrder.LITTLE);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendSoundEffect(int n, int n2, int n3) {
        if (this.a.de) {
            return this;
        }
        if (n < 0) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(7);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 174);
        packetWriter.writeShort(n);
        packetWriter.writeByte(1);
        packetWriter.writeShort(n3);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendMusicJingle(int n, int n2) {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(5);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 121);
        packetWriter.writeShort(n, ByteTransform.ADD, ByteOrder.LITTLE);
        packetWriter.writeShort(n2, ByteTransform.ADD, ByteOrder.BIG);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendPlayerOption(String string, int n, boolean bl) {
        if (this.a.de) {
            return this;
        }
        if (n > 0 && n <= 5) {
            if (this.a.ce[n - 1] != null && this.a.ce[n - 1].equals(string)) {
                return this;
            }
            this.a.ce[n - 1] = string;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(string.length() + 5);
        packetWriter.startVariableBytePacket(this.a.getOutboundCipher(), 104);
        packetWriter.writeByte(n, ByteTransform.NEGATE);
        packetWriter.writeByte(0, ByteTransform.ADD);
        packetWriter.writeString(string);
        packetWriter.finishVariableBytePacket();
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendPlayerIndex() {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(4);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 249);
        packetWriter.writeByte(1, ByteTransform.ADD);
        packetWriter.writeShort(this.a.getIndex(), ByteTransform.ADD, ByteOrder.LITTLE);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendRunEnergy() {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(2);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 110);
        packetWriter.writeByte(this.a.getRunEnergyPercent());
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendWeight() {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(3);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 240);
        Player player = this.a;
        packetWriter.writeShort((int)Math.floor(player.al));
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final void refreshAutocastConfig() {
        if (this.a.ed() == null) {
            this.sendConfig(108, 0);
            this.sendConfig(43, this.a.getFightMode());
            this.sendInterfaceText("", 352);
            return;
        }
        this.sendConfig(43, this.a.getFightMode());
        this.sendConfig(108, 2);
    }

    public final PacketSender setInterfaceHiddenFlag(int n, int n2) {
        if (this.a.de) {
            return this;
        }
        if (n2 >= InterfaceDefinition.interfaceCount) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(4);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 171);
        packetWriter.writeByte(n);
        packetWriter.writeShort(n2);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendCameraShake(int n, int n2, int n3, int n4) {
        if (this.a.de) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(5);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 35);
        packetWriter.writeByte(2);
        packetWriter.writeByte(3);
        packetWriter.writeByte(2);
        packetWriter.writeByte(3);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender setInterfaceVisible(int n, boolean bl) {
        if (this.a.de) {
            return this;
        }
        if (n >= InterfaceDefinition.interfaceCount) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(4);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 171);
        packetWriter.writeByte(bl ? 0 : 1);
        packetWriter.writeShort(n);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final PacketSender sendInterfacePosition(int n, int n2, int n3) {
        if (this.a.de) {
            return this;
        }
        if (n >= InterfaceDefinition.interfaceCount && n == 12145) {
            n = 8923;
        }
        if (n >= InterfaceDefinition.interfaceCount) {
            return this;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(7);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 70);
        packetWriter.writeShort(n2);
        packetWriter.writeShort(n3, ByteOrder.LITTLE);
        packetWriter.writeShort(n, ByteOrder.LITTLE);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        return this;
    }

    public final void refreshSpecialAttackConfig() {
        if (this.a.isSpecialAttackEnabled()) {
            this.sendConfig(301, 1);
            return;
        }
        this.sendConfig(301, 0);
    }

    public final void refreshSpecialEnergyBar(int n) {
        int n2 = 10;
        int n3 = this.a.getSpecialEnergy() / 10;
        int n4 = 0;
        while (n4 < 10) {
            PacketSender packetSender;
            int n5 = --n;
            int n6 = n3 >= n2 ? 500 : 0;
            PacketSender packetSender2 = this;
            if (packetSender2.a.de) {
                packetSender = packetSender2;
            } else {
                PacketWriter packetWriter = PacketBuffer.allocateWriter(7);
                packetWriter.writeOpcode(packetSender2.a.getOutboundCipher(), 70);
                packetWriter.writeShort(n6);
                packetWriter.writeShort(0, ByteOrder.LITTLE);
                packetWriter.writeShort(n5, ByteOrder.LITTLE);
                packetSender2.a.writePacketBuffer(packetWriter.getBuffer());
                packetSender = packetSender2;
            }
            --n2;
            ++n4;
        }
    }

    public final void sendCameraPosition(int n, int n2, int n3, int n4, int n5) {
        if (this.a.de) {
            return;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(7);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 177);
        packetWriter.writeByte(n / 64);
        packetWriter.writeByte(n2 / 64);
        packetWriter.writeShort(n3);
        packetWriter.writeByte(0);
        packetWriter.writeByte(n5);
        this.a.writePacketBuffer(packetWriter.getBuffer());
    }

    public final void sendCameraLookAt(int n, int n2, int n3, int n4, int n5) {
        if (this.a.de) {
            return;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(7);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 166);
        packetWriter.writeByte(n / 64);
        packetWriter.writeByte(n2 / 64);
        packetWriter.writeShort(n3);
        packetWriter.writeByte(0);
        packetWriter.writeByte(n5);
        this.a.writePacketBuffer(packetWriter.getBuffer());
    }

    public final void resetCamera() {
        if (this.a.de) {
            return;
        }
        PacketWriter packetWriter = PacketBuffer.allocateWriter(1);
        packetWriter.writeOpcode(this.a.getOutboundCipher(), 107);
        this.a.writePacketBuffer(packetWriter.getBuffer());
        this.a.getUpdateState().setUpdateRequired(true);
    }

    public final void openDoubleDoorPair(int n, int n2, int n3, int n4, int n5, int n6) {
        int bl = this.a.getPosition().getPlane();
        LoadedWorldObject loadedWorldObject = WorldObjectLookup.findObjectByIdAt(n, n2, n3, bl);
        LoadedWorldObject loadedWorldObject2 = WorldObjectLookup.findObjectByIdAt(n4, n5, n6, bl);
        boolean bl2 = DoubleDoorHandler.usesWideDoubleDoorOffset(n);
        if (loadedWorldObject.getOrientation() == 0) {
            LoadedWorldObject loadedWorldObject3;
            LoadedWorldObject loadedWorldObject4;
            if (n3 < n6) {
                loadedWorldObject4 = loadedWorldObject;
                loadedWorldObject3 = loadedWorldObject2;
            } else {
                loadedWorldObject4 = loadedWorldObject2;
                loadedWorldObject3 = loadedWorldObject;
            }
            PacketSender.a(loadedWorldObject4, true, bl2);
            PacketSender.a(loadedWorldObject3, false, bl2);
            return;
        }
        if (loadedWorldObject.getOrientation() == 1) {
            LoadedWorldObject loadedWorldObject5;
            LoadedWorldObject loadedWorldObject6;
            if (n2 < n5) {
                loadedWorldObject6 = loadedWorldObject;
                loadedWorldObject5 = loadedWorldObject2;
            } else {
                loadedWorldObject6 = loadedWorldObject2;
                loadedWorldObject5 = loadedWorldObject;
            }
            PacketSender.a(loadedWorldObject6, true, bl2);
            PacketSender.a(loadedWorldObject5, false, bl2);
            return;
        }
        if (loadedWorldObject.getOrientation() == 2) {
            LoadedWorldObject loadedWorldObject7;
            LoadedWorldObject loadedWorldObject8;
            if (n3 < n6) {
                loadedWorldObject8 = loadedWorldObject2;
                loadedWorldObject7 = loadedWorldObject;
            } else {
                loadedWorldObject8 = loadedWorldObject;
                loadedWorldObject7 = loadedWorldObject2;
            }
            PacketSender.a(loadedWorldObject8, true, bl2);
            PacketSender.a(loadedWorldObject7, false, bl2);
            return;
        }
        if (loadedWorldObject.getOrientation() == 3) {
            LoadedWorldObject loadedWorldObject9;
            LoadedWorldObject loadedWorldObject10;
            if (n2 < n5) {
                loadedWorldObject10 = loadedWorldObject;
                loadedWorldObject9 = loadedWorldObject2;
            } else {
                loadedWorldObject10 = loadedWorldObject2;
                loadedWorldObject9 = loadedWorldObject;
            }
            PacketSender.a(loadedWorldObject10, true, bl2);
            PacketSender.a(loadedWorldObject9, false, bl2);
        }
    }

    /*
     * Unable to fully structure code
     */
    private static void a(LoadedWorldObject var0, boolean var1_1, boolean var2_2) {
        block24: {
            block22: {
                block25: {
                    block23: {
                        block21: {
                            block19: {
                                block20: {
                                    var3_3 = 0;
                                    var4_4 = 0;
                                    if (var0.getOrientation() != 0) break block19;
                                    if (var1_1 != 0 || var2_2 == 0) break block20;
                                    var3_3 = -2;
                                    ** GOTO lbl-1000
                                }
                                var3_3 = -1;
                                break block21;
                            }
                            if (var0.getOrientation() == 1) {
                                if (var1_1 == 0 && var2_2 != 0) {
                                    var3_3 = -1;
                                    var4_4 = 2;
                                } else {
                                    var4_4 = 1;
                                }
                            } else if (var0.getOrientation() == 2) {
                                if (var1_1 == 0 && var2_2 != 0) {
                                    var3_3 = 2;
                                    var4_4 = 1;
                                } else {
                                    var3_3 = 1;
                                }
                            } else if (var0.getOrientation() == 3) {
                                ** if (var1_1 != 0 || var2_2 == 0) goto lbl-1000
lbl-1000:
                                // 1 sources

                                {
                                    var3_3 = -1;
                                    var4_4 = -2;
                                    ** GOTO lbl32
                                }
                            }
                            break block21;
lbl-1000:
                            // 2 sources

                            {
                                var4_4 = -1;
                            }
                        }
                        new DynamicObject(ServerSettings.placeholderObjectId, var0.getPosition().getX(), var0.getPosition().getY(), var0.getPosition().getPlane(), var0.getOrientation(), var0.getType(), var0.getWorldObject().getObjectId(), 1, false);
                        v0 = var0.getWorldObject().getObjectId();
                        v1 = var0.getPosition().getX() + var3_3;
                        v2 = var0.getPosition().getY() + var4_4;
                        v3 = var0.getPosition().getPlane();
                        var3_3 = var2_2;
                        var2_2 = var1_1;
                        var1_1 = var0.getOrientation();
                        if (var2_2 == 0) break block22;
                        if (var1_1 != 0) break block23;
                        v4 = 3;
                        break block24;
                    }
                    if (var1_1 == 1) ** GOTO lbl-1000
                    if (var1_1 != 2) break block25;
                    v4 = 1;
                    break block24;
                }
                if (var1_1 != 3) ** GOTO lbl-1000
                ** GOTO lbl-1000
            }
            if (var1_1 == 0) {
                v4 = var3_3 != 0 ? 3 : 1;
            } else if (var1_1 == 1) {
                v4 = var3_3 != 0 ? 0 : 2;
            } else if (var1_1 == 2) {
                v4 = var3_3 != 0 ? 1 : 3;
            } else if (var1_1 == 3) {
                v4 = var3_3 != 0 ? 0 : 2;
            } else lbl-1000:
            // 4 sources

            {
                v4 = 0;
            }
        }
        new DynamicObject(v0, v1, v2, v3, v4, var0.getType(), ServerSettings.placeholderObjectId, 1, false);
    }

    public final void openWestShiftedDoubleDoorPair(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        LoadedWorldObject loadedWorldObject = WorldObjectLookup.findObjectByIdAt(n, n3, n4, 0);
        LoadedWorldObject loadedWorldObject2 = WorldObjectLookup.findObjectByIdAt(n2, n5, n6, 0);
        int n8 = this.a.getPosition().getPlane();
        new DynamicObject(ServerSettings.placeholderObjectId, n3, n4, n8, loadedWorldObject.getOrientation(), 0, n, 3, false);
        new DynamicObject(ServerSettings.placeholderObjectId, n5, n6, n8, loadedWorldObject2.getOrientation(), 0, n2, 3, false);
        new DynamicObject(n, n3 - 1, n4, n8, loadedWorldObject.getOrientation() + 3, 0, ServerSettings.placeholderObjectId, 3, false);
        new DynamicObject(n2, n5 - 1, n6, n8, loadedWorldObject2.getOrientation() + 1, 0, ServerSettings.placeholderObjectId, 3, false);
        this.sendSoundEffect(318, 1, 0);
    }

    public final void openSingleDoor(int n, int n2, int n3, int n4) {
        LoadedWorldObject loadedWorldObject = WorldObjectLookup.findObjectByIdAt(n, n2, n3, n4);
        new DynamicObject(n, n2, n3, n4, loadedWorldObject.getOrientation() - 1, 0, n, 2, loadedWorldObject.getOrientation(), n2, n3, false);
        this.sendSoundEffect(318, 1, 0);
    }

    public final void openSouthShiftedSingleDoor(int n, int n2, int n3, int n4) {
        LoadedWorldObject loadedWorldObject = WorldObjectLookup.findObjectByIdAt(n, n2, n3, n4);
        new DynamicObject(ServerSettings.placeholderObjectId, n2, n3, n4, loadedWorldObject.getOrientation() - 1, loadedWorldObject.getType(), n, 1, loadedWorldObject.getOrientation(), n2, n3, false);
        new DynamicObject(n, n2, n3 - 1, n4, loadedWorldObject.getOrientation() - 1, loadedWorldObject.getType(), ServerSettings.placeholderObjectId, 1, loadedWorldObject.getOrientation(), n2, n3, false);
        this.sendSoundEffect(318, 1, 0);
    }

    public final void openDoubleDoorPair(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        LoadedWorldObject loadedWorldObject = WorldObjectLookup.findObjectByIdAt(n, n3, n4, 0);
        LoadedWorldObject loadedWorldObject2 = WorldObjectLookup.findObjectByIdAt(n2, n5, n6, 0);
        new DynamicObject(n, n3, n4, 0, loadedWorldObject.getOrientation() - 1, 0, n, 1, loadedWorldObject.getOrientation(), n3, n4, false);
        new DynamicObject(n2, n5, n6, 0, loadedWorldObject2.getOrientation() + 1, 0, n2, 1, loadedWorldObject2.getOrientation(), n5, n6, false);
        this.sendSoundEffect(318, 1, 0);
    }

    public final void openNorthShiftedDoubleDoorPair(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        LoadedWorldObject loadedWorldObject = WorldObjectLookup.findObjectByIdAt(n, n3, n4, 0);
        LoadedWorldObject loadedWorldObject2 = WorldObjectLookup.findObjectByIdAt(n2, n5, n6, 0);
        new DynamicObject(ServerSettings.placeholderObjectId, n3, n4, 0, loadedWorldObject.getOrientation() - 1, 0, n, 1, loadedWorldObject.getOrientation(), n3, n4, false);
        new DynamicObject(ServerSettings.placeholderObjectId, n5, n6, 0, loadedWorldObject2.getOrientation() + 1, 0, n2, 1, loadedWorldObject2.getOrientation(), n5, n6, false);
        new DynamicObject(n, n3, n4 + 1, 0, loadedWorldObject.getOrientation() - 1, 0, ServerSettings.placeholderObjectId, 1, loadedWorldObject.getOrientation(), n3, n4, false);
        new DynamicObject(n2, n5, n6 + 1, 0, loadedWorldObject2.getOrientation() + 1, 0, ServerSettings.placeholderObjectId, 1, loadedWorldObject2.getOrientation(), n5, n6, false);
        this.sendSoundEffect(318, 1, 0);
    }

    static /* synthetic */ Player a(PacketSender packetSender) {
        return packetSender.a;
    }
}

