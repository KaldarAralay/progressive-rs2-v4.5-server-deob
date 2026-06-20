/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.util;

import com.rs2.CacheCoordinateTranslator;
import com.rs2.HiscoresDatabase;
import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.bot.route.BotWorldRouteWalker;
import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.World;
import com.rs2.model.clue.PuzzleBoxHandler;
import com.rs2.model.gameplay.godwars.GodWarsDungeonManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.functions.FlourMillHandler;
import com.rs2.model.player.CharacterFileBankTab;
import com.rs2.model.player.CharacterFileRecord;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.model.skill.farming.AllotmentPatchManager;
import com.rs2.model.skill.farming.BushPatchManager;
import com.rs2.model.skill.farming.CompostBinManager;
import com.rs2.model.skill.farming.FarmingToolStore;
import com.rs2.model.skill.farming.FlowerPatchManager;
import com.rs2.model.skill.farming.FruitTreePatchManager;
import com.rs2.model.skill.farming.HerbPatchManager;
import com.rs2.model.skill.farming.HopsPatchManager;
import com.rs2.model.skill.farming.SpecialCropPatchManager;
import com.rs2.model.skill.farming.SpecialTreePatchManager;
import com.rs2.model.skill.farming.TreePatchManager;
import com.rs2.model.skill.magic.Spellbook;
import com.rs2.net.LoginProtocol;
import com.rs2.net.packet.handler.AppearancePacketHandler;
import com.rs2.util.BackupRepairTimestampComparator;
import com.rs2.util.BackupRestoreTimestampComparator;
import com.rs2.util.ElapsedTimer;
import com.rs2.util.FileUtil;
import com.rs2.util.LoginIpReservation;
import com.rs2.util.PlayerLoginLoadCallback;
import com.rs2.util.PlayerUidLookupQuery;
import com.rs2.util.db.DatabaseQuery;
import com.rs2.util.db.DatabaseService;
import com.rs2.util.db.player.PlayerBankSaveCallback;
import com.rs2.util.db.player.PlayerContactsSaveCallback;
import com.rs2.util.db.player.PlayerEquipmentSaveCallback;
import com.rs2.util.db.player.PlayerInventorySaveCallback;
import com.rs2.util.db.player.PlayerProfileSaveCallback;
import com.rs2.util.db.player.PlayerSaveQueryFactory;
import com.rs2.util.db.player.PlayerSkillsSaveCallback;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import org.joda.time.DateTime;
import org.joda.time.base.AbstractDateTime;

public final class CharacterFileManager {
    public static int[] musicUnlockConfigIds = new int[]{20, 21, 22, 23, 24, 25, 298, 311, 346, 414, 464, 598, 662, 721};
    private static ConcurrentHashMap loginIpReservations = new ConcurrentHashMap();
    public static ArrayList deadHardcoreIronmanRecords = new ArrayList();
    public static ArrayList liveHiscoreRecords = new ArrayList();

    public static boolean savePlayer(Player player) {
        if (player.isBot) {
            if (player.botMode != 4) {
                return true;
            }
            if (ServerSettings.mysqlHiscoresEnabled) {
                HiscoresDatabase.savePlayer(player);
            }
        }
        if (System.currentTimeMillis() - player.ck < 50L) {
            return true;
        }
        if (!LoginProtocol.a.contains(player.getUsername())) {
            LoginProtocol.a.add(player.getUsername());
        }
        File file = new File("./data/characters/");
        CharacterFileManager.writePlayerFile(player);
        boolean bl = CharacterFileManager.validateCharacterFile(String.valueOf(file.getPath()) + "/", player.getUsername());
        while (!bl) {
            System.out.println("Something went wrong while saving: " + player.getUsername() + " trying again.");
            CharacterFileManager.writePlayerFile(player);
            bl = CharacterFileManager.validateCharacterFile(String.valueOf(file.getPath()) + "/", player.getUsername());
        }
        if (LoginProtocol.a.contains(player.getUsername())) {
            LoginProtocol.a.remove(player.getUsername());
        }
        return true;
    }

    /*
     * Handled impossible loop by duplicating code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void writePlayerFile(Player object) {
        if (((Player)object).isBot && ((Player)object).botMode != 4) {
            return;
        }
        try {
            int n;
            int n2;
            Object object2;
            int n3;
            Object object3;
            block287: {
                block286: {
                    block285: {
                        block284: {
                            block283: {
                                block282: {
                                    block281: {
                                        block280: {
                                            block279: {
                                                block278: {
                                                    block277: {
                                                        block276: {
                                                            block275: {
                                                                block274: {
                                                                    block273: {
                                                                        block272: {
                                                                            block271: {
                                                                                block270: {
                                                                                    block269: {
                                                                                        block268: {
                                                                                            block267: {
                                                                                                block266: {
                                                                                                    block265: {
                                                                                                        block264: {
                                                                                                            block263: {
                                                                                                                block262: {
                                                                                                                    block261: {
                                                                                                                        block260: {
                                                                                                                            block259: {
                                                                                                                                block258: {
                                                                                                                                    block257: {
                                                                                                                                        block256: {
                                                                                                                                            block255: {
                                                                                                                                                block254: {
                                                                                                                                                    block253: {
                                                                                                                                                        block252: {
                                                                                                                                                            block251: {
                                                                                                                                                                block250: {
                                                                                                                                                                    block249: {
                                                                                                                                                                        block248: {
                                                                                                                                                                            block247: {
                                                                                                                                                                                block246: {
                                                                                                                                                                                    block245: {
                                                                                                                                                                                        block244: {
                                                                                                                                                                                            block243: {
                                                                                                                                                                                                block242: {
                                                                                                                                                                                                    block241: {
                                                                                                                                                                                                        block240: {
                                                                                                                                                                                                            block239: {
                                                                                                                                                                                                                block238: {
                                                                                                                                                                                                                    block237: {
                                                                                                                                                                                                                        block236: {
                                                                                                                                                                                                                            block235: {
                                                                                                                                                                                                                                block234: {
                                                                                                                                                                                                                                    block233: {
                                                                                                                                                                                                                                        block232: {
                                                                                                                                                                                                                                            block231: {
                                                                                                                                                                                                                                                block230: {
                                                                                                                                                                                                                                                    block229: {
                                                                                                                                                                                                                                                        block228: {
                                                                                                                                                                                                                                                            block227: {
                                                                                                                                                                                                                                                                block226: {
                                                                                                                                                                                                                                                                    block225: {
                                                                                                                                                                                                                                                                        block224: {
                                                                                                                                                                                                                                                                            block223: {
                                                                                                                                                                                                                                                                                block222: {
                                                                                                                                                                                                                                                                                    block221: {
                                                                                                                                                                                                                                                                                        block220: {
                                                                                                                                                                                                                                                                                            block219: {
                                                                                                                                                                                                                                                                                                block218: {
                                                                                                                                                                                                                                                                                                    block217: {
                                                                                                                                                                                                                                                                                                        block216: {
                                                                                                                                                                                                                                                                                                            block215: {
                                                                                                                                                                                                                                                                                                                block214: {
                                                                                                                                                                                                                                                                                                                    block213: {
                                                                                                                                                                                                                                                                                                                        block212: {
                                                                                                                                                                                                                                                                                                                            block211: {
                                                                                                                                                                                                                                                                                                                                block210: {
                                                                                                                                                                                                                                                                                                                                    block209: {
                                                                                                                                                                                                                                                                                                                                        block208: {
                                                                                                                                                                                                                                                                                                                                            block207: {
                                                                                                                                                                                                                                                                                                                                                block206: {
                                                                                                                                                                                                                                                                                                                                                    block205: {
                                                                                                                                                                                                                                                                                                                                                        block204: {
                                                                                                                                                                                                                                                                                                                                                            block203: {
                                                                                                                                                                                                                                                                                                                                                                block202: {
                                                                                                                                                                                                                                                                                                                                                                    block201: {
                                                                                                                                                                                                                                                                                                                                                                        block200: {
                                                                                                                                                                                                                                                                                                                                                                            block199: {
                                                                                                                                                                                                                                                                                                                                                                                block198: {
                                                                                                                                                                                                                                                                                                                                                                                    block197: {
                                                                                                                                                                                                                                                                                                                                                                                        block196: {
                                                                                                                                                                                                                                                                                                                                                                                            block195: {
                                                                                                                                                                                                                                                                                                                                                                                                block194: {
                                                                                                                                                                                                                                                                                                                                                                                                    block193: {
                                                                                                                                                                                                                                                                                                                                                                                                        block192: {
                                                                                                                                                                                                                                                                                                                                                                                                            block191: {
                                                                                                                                                                                                                                                                                                                                                                                                                block190: {
                                                                                                                                                                                                                                                                                                                                                                                                                    block189: {
                                                                                                                                                                                                                                                                                                                                                                                                                        block188: {
                                                                                                                                                                                                                                                                                                                                                                                                                            block187: {
                                                                                                                                                                                                                                                                                                                                                                                                                                block186: {
                                                                                                                                                                                                                                                                                                                                                                                                                                    block185: {
                                                                                                                                                                                                                                                                                                                                                                                                                                        block184: {
                                                                                                                                                                                                                                                                                                                                                                                                                                            block183: {
                                                                                                                                                                                                                                                                                                                                                                                                                                                block182: {
                                                                                                                                                                                                                                                                                                                                                                                                                                                    block181: {
                                                                                                                                                                                                                                                                                                                                                                                                                                                        block180: {
                                                                                                                                                                                                                                                                                                                                                                                                                                                            block179: {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                block178: {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                    block177: {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                        block176: {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            block175: {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                block174: {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    block173: {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        block172: {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            if (ServerSettings.mysqlPlayerSaveEnabled) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                object = new PlayerSaveQueryFactory((Player)object);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                DatabaseService.getInstance().submit(((PlayerSaveQueryFactory)object).createProfileSaveQuery(), new PlayerProfileSaveCallback());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                DatabaseService.getInstance().submit(((PlayerSaveQueryFactory)object).createBankSaveQuery(), new PlayerInventorySaveCallback());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                DatabaseService.getInstance().submit(((PlayerSaveQueryFactory)object).createInventorySaveQuery(), new PlayerBankSaveCallback());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                DatabaseService.getInstance().submit(((PlayerSaveQueryFactory)object).createEquipmentSaveQuery(), new PlayerEquipmentSaveCallback());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                DatabaseService.getInstance().submit(((PlayerSaveQueryFactory)object).createContactsSaveQuery(), new PlayerContactsSaveCallback());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                DatabaseService.getInstance().submit(((PlayerSaveQueryFactory)object).createSkillsSaveQuery(), new PlayerSkillsSaveCallback());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                return;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            new ElapsedTimer();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            object3 = new File("./data/characters/" + ((Player)object).getUsername() + ".dat");
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            if (!((File)object3).exists()) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((File)object3).createNewFile();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            object3 = new FileOutputStream((File)object3);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            object3 = new DataOutputStream((OutputStream)object3);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeShort(30);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeUTF(((Player)object).getUsername());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeUTF(((Player)object).getPassword());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeUTF(((Player)object).getHostAddress());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).getPlayerRights());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeUTF("");
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeUTF(((Player)object).getProfileString1());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeUTF(((Player)object).getProfileString2());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeLong(System.currentTimeMillis());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            long l = ((Player)object).getTotalPlaytimeMillis();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            n3 = (int)(l / 1000L / 60L / 60L);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            if (n3 >= 100000) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((DataOutputStream)object3).writeLong(((Player)object).ej);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((Player)object).i("Couldn't save playing time, prev: " + ((Player)object).ej + " login: " + ((Player)object).ei + " current: " + System.currentTimeMillis());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            } else {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((DataOutputStream)object3).writeLong(((Player)object).getTotalPlaytimeMillis());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeLong(((Player)object).eg);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeBoolean(((Player)object).loginRestrictionExempt);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeBoolean(((Player)object).hasMemberFlag());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).getDonatorPoints());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            if (((Player)object).z == null) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((DataOutputStream)object3).writeInt(((Entity)object).getPosition().getX());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((DataOutputStream)object3).writeInt(((Entity)object).getPosition().getY());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((DataOutputStream)object3).writeInt(((Entity)object).getPosition().getPlane());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            } else {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((DataOutputStream)object3).writeInt(((Player)object).z.getX());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((DataOutputStream)object3).writeInt(((Player)object).z.getY());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((DataOutputStream)object3).writeInt(((Player)object).z.getPlane());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).getGender());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).dT);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).dU);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).dV);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).dW);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).dX);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).dY);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).dZ);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).ea);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).eb);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).ec);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).legacyQuestPoints);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeBoolean(((Player)object).isAutoRetaliate());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).getFightMode());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).getBrightness());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).getMouseButtons());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).getPublicChatEffects());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).getSplitPrivateChat());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).getAcceptAid());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).getMusicVolume());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).getEffectVolume());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeDouble(((Player)object).getSpecialEnergy());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeBoolean(((Player)object).getBankPinManager().isChangingPin());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeBoolean(((Player)object).getBankPinManager().isDeletingPin());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).getBankPinManager().getPinAppendYear());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).getBankPinManager().getPinAppendDate());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).getBindingNecklaceCharge());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).getRingOfForgingLife());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).getRingOfRecoilLife());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).getSkullTimer());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeShort(((Player)object).getRunEnergyRaw());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeBoolean(((Entity)object).getMovementQueue().isRunning());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            while (n3 < ((Player)object).getBankPinManager().getCurrentPin().length) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((DataOutputStream)object3).writeInt(((Player)object).getBankPinManager().getCurrentPin()[n3]);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            while (n3 < ((Player)object).getBankPinManager().getPendingPin().length) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((DataOutputStream)object3).writeInt(((Player)object).getBankPinManager().getPendingPin()[n3]);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            while (n3 < 4) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((DataOutputStream)object3).writeInt(((Player)object).am(n3));
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            while (n3 < ((Player)object).getAppearanceParts().length) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((DataOutputStream)object3).writeInt(((Player)object).getAppearanceParts()[n3]);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            while (n3 < ((Player)object).getAppearanceColors().length) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((DataOutputStream)object3).writeInt(((Player)object).getAppearanceColors()[n3]);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            while (n3 < ((Player)object).getSkillManager().getCurrentLevels().length) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((DataOutputStream)object3).writeInt(((Player)object).getSkillManager().getCurrentLevels()[n3]);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            while (n3 < ((Player)object).getSkillManager().getExperience().length) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((DataOutputStream)object3).writeInt((int)((Player)object).getSkillManager().getExperience()[n3]);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            while (n3 < 28) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                object2 = ((Player)object).getInventoryManager().getContainer().getItemAt(n3);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                if (object2 == null) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeInt(65535);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                } else {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeInt(((ItemStack)object2).getId());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeInt(((ItemStack)object2).getAmount());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeInt(((ItemStack)object2).getMetadata());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            while (n3 < 14) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                object2 = ((Player)object).getEquipmentManager().getContainer().getItemAt(n3);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                if (object2 == null) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeInt(65535);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                } else {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeInt(((ItemStack)object2).getId());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeInt(((ItemStack)object2).getAmount());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeInt(((ItemStack)object2).getMetadata());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((Player)object).getBankContainer().removeEmptyTabs();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeByte(((Player)object).getBankContainer().getTabCount());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            while (n3 < ((Player)object).getBankContainer().getTabCount()) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((Player)object).getBankContainer().compactTab(n3);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                object2 = ((Player)object).getBankContainer().getTab(n3).getItems();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                n2 = ((ArrayList)object2).size();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((DataOutputStream)object3).writeShort(n2);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                n = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                while (n < n2) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ItemStack itemStack = (ItemStack)((ArrayList)object2).get(n);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    if (itemStack == null) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        ((DataOutputStream)object3).writeInt(65535);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    } else {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        ((DataOutputStream)object3).writeInt(itemStack.getId());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        ((DataOutputStream)object3).writeInt(itemStack.getAmount());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        ((DataOutputStream)object3).writeInt(itemStack.getMetadata());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ++n;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            while (n3 < ((Player)object).getFriendsList().length) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((DataOutputStream)object3).writeLong(((Player)object).getFriendsList()[n3]);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            while (n3 < ((Player)object).getIgnoreList().length) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((DataOutputStream)object3).writeLong(((Player)object).getIgnoreList()[n3]);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            while (n3 < ((Player)object).getQueuedLoginItemIds().length) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((DataOutputStream)object3).writeInt(((Player)object).getQueuedLoginItemIds()[n3]);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((DataOutputStream)object3).writeInt(((Player)object).getQueuedLoginItemAmounts()[n3]);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).eA());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeLong(((Player)object).getMuteExpires());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeLong(((Player)object).getBanExpires());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            while (n3 < 6) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ((DataOutputStream)object3).writeBoolean(((Player)object).isBarrowsBrotherKilled(n3));
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).getBarrowsKillCount());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Player)object).getBarrowsTargetBrotherIndex());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Entity)object).getPoisonImmunityTimer().getRemainingTicks());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Entity)object).getAntifireTimer().getRemainingTicks());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((Entity)object).getTeleblockTimer().getRemainingTicks());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeDouble(((Entity)object).getPoisonDamage());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            if (!true) break block172;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getAllotmentPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            if (n3 >= ((AllotmentPatchManager)object2).growthStages.length) break block173;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        do {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getAllotmentPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((AllotmentPatchManager)object2).growthStages[n3]);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getAllotmentPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        } while (n3 < ((AllotmentPatchManager)object2).growthStages.length);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    if (!true) break block174;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getAllotmentPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    if (n3 >= ((AllotmentPatchManager)object2).cropIds.length) break block175;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                do {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getAllotmentPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeInt(((AllotmentPatchManager)object2).cropIds[n3]);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getAllotmentPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                } while (n3 < ((AllotmentPatchManager)object2).cropIds.length);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            if (!true) break block176;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getAllotmentPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            if (n3 >= ((AllotmentPatchManager)object2).harvestAmounts.length) break block177;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                        do {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getAllotmentPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((AllotmentPatchManager)object2).harvestAmounts[n3]);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getAllotmentPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                        } while (n3 < ((AllotmentPatchManager)object2).harvestAmounts.length);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                    n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                    if (!true) break block178;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getAllotmentPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                    if (n3 >= ((AllotmentPatchManager)object2).patchStates.length) break block179;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                do {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getAllotmentPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeInt(((AllotmentPatchManager)object2).patchStates[n3]);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getAllotmentPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                } while (n3 < ((AllotmentPatchManager)object2).patchStates.length);
                                                                                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                                                            if (!true) break block180;
                                                                                                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getAllotmentPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                                            if (n3 >= ((AllotmentPatchManager)object2).lastUpdateTicks.length) break block181;
                                                                                                                                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                                                                                                                                        do {
                                                                                                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getAllotmentPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeLong(((AllotmentPatchManager)object2).lastUpdateTicks[n3]);
                                                                                                                                                                                                                                                                                                                                                                                                                                                            ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getAllotmentPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                                        } while (n3 < ((AllotmentPatchManager)object2).lastUpdateTicks.length);
                                                                                                                                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                    n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                                                    if (!true) break block182;
                                                                                                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getAllotmentPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                                    if (n3 >= ((AllotmentPatchManager)object2).diseaseChanceMultipliers.length) break block183;
                                                                                                                                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                                                                                                                                do {
                                                                                                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getAllotmentPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeDouble(((AllotmentPatchManager)object2).diseaseChanceMultipliers[n3]);
                                                                                                                                                                                                                                                                                                                                                                                                                                                    ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getAllotmentPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                                } while (n3 < ((AllotmentPatchManager)object2).diseaseChanceMultipliers.length);
                                                                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                                            if (!true) break block184;
                                                                                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getAllotmentPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                            if (n3 >= ((AllotmentPatchManager)object2).protectionFlags.length) break block185;
                                                                                                                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                                                                                                                        do {
                                                                                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getAllotmentPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeBoolean(((AllotmentPatchManager)object2).protectionFlags[n3]);
                                                                                                                                                                                                                                                                                                                                                                                                                                            ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getAllotmentPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                        } while (n3 < ((AllotmentPatchManager)object2).protectionFlags.length);
                                                                                                                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                                                                                                                    n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                                    if (!true) break block186;
                                                                                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getBushPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                    if (n3 >= ((BushPatchManager)object2).growthStages.length) break block187;
                                                                                                                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                                                                                                                do {
                                                                                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getBushPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeInt(((BushPatchManager)object2).growthStages[n3]);
                                                                                                                                                                                                                                                                                                                                                                                                                                    ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getBushPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                                } while (n3 < ((BushPatchManager)object2).growthStages.length);
                                                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                            if (!true) break block188;
                                                                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getBushPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                            if (n3 >= ((BushPatchManager)object2).cropIds.length) break block189;
                                                                                                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                                                                                                        do {
                                                                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getBushPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((BushPatchManager)object2).cropIds[n3]);
                                                                                                                                                                                                                                                                                                                                                                                                                            ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getBushPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                        } while (n3 < ((BushPatchManager)object2).cropIds.length);
                                                                                                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                                                                                                    n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                                    if (!true) break block190;
                                                                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getBushPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                    if (n3 >= ((BushPatchManager)object2).patchStates.length) break block191;
                                                                                                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                                                                                                do {
                                                                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getBushPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeInt(((BushPatchManager)object2).patchStates[n3]);
                                                                                                                                                                                                                                                                                                                                                                                                                    ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getBushPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                                } while (n3 < ((BushPatchManager)object2).patchStates.length);
                                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                            if (!true) break block192;
                                                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getBushPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                            if (n3 >= ((BushPatchManager)object2).lastUpdateTicks.length) break block193;
                                                                                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                                                                                        do {
                                                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getBushPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeLong(((BushPatchManager)object2).lastUpdateTicks[n3]);
                                                                                                                                                                                                                                                                                                                                                                                                            ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getBushPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                        } while (n3 < ((BushPatchManager)object2).lastUpdateTicks.length);
                                                                                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                                                                                    n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                                    if (!true) break block194;
                                                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getBushPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                    if (n3 >= ((BushPatchManager)object2).diseaseChanceMultipliers.length) break block195;
                                                                                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                                                                                do {
                                                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getBushPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeDouble(((BushPatchManager)object2).diseaseChanceMultipliers[n3]);
                                                                                                                                                                                                                                                                                                                                                                                                    ++n3;
                                                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getBushPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                                } while (n3 < ((BushPatchManager)object2).diseaseChanceMultipliers.length);
                                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                            if (!true) break block196;
                                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getBushPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                            if (n3 >= ((BushPatchManager)object2).protectionFlags.length) break block197;
                                                                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                                                                        do {
                                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getBushPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeBoolean(((BushPatchManager)object2).protectionFlags[n3]);
                                                                                                                                                                                                                                                                                                                                                                                            ++n3;
                                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getBushPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                        } while (n3 < ((BushPatchManager)object2).protectionFlags.length);
                                                                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                                                                    n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                                    if (!true) break block198;
                                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getFlowerPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                    if (n3 >= ((FlowerPatchManager)object2).growthStages.length) break block199;
                                                                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                                                                do {
                                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getFlowerPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeInt(((FlowerPatchManager)object2).growthStages[n3]);
                                                                                                                                                                                                                                                                                                                                                                                    ++n3;
                                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getFlowerPatchManager();
                                                                                                                                                                                                                                                                                                                                                                                } while (n3 < ((FlowerPatchManager)object2).growthStages.length);
                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                            if (!true) break block200;
                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getFlowerPatchManager();
                                                                                                                                                                                                                                                                                                                                                                            if (n3 >= ((FlowerPatchManager)object2).cropIds.length) break block201;
                                                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                                                        do {
                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getFlowerPatchManager();
                                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((FlowerPatchManager)object2).cropIds[n3]);
                                                                                                                                                                                                                                                                                                                                                                            ++n3;
                                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getFlowerPatchManager();
                                                                                                                                                                                                                                                                                                                                                                        } while (n3 < ((FlowerPatchManager)object2).cropIds.length);
                                                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                                                    n3 = 0;
                                                                                                                                                                                                                                                                                                                                                                    if (!true) break block202;
                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getFlowerPatchManager();
                                                                                                                                                                                                                                                                                                                                                                    if (n3 >= ((FlowerPatchManager)object2).patchStates.length) break block203;
                                                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                                                do {
                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getFlowerPatchManager();
                                                                                                                                                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeInt(((FlowerPatchManager)object2).patchStates[n3]);
                                                                                                                                                                                                                                                                                                                                                                    ++n3;
                                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getFlowerPatchManager();
                                                                                                                                                                                                                                                                                                                                                                } while (n3 < ((FlowerPatchManager)object2).patchStates.length);
                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                                            if (!true) break block204;
                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getFlowerPatchManager();
                                                                                                                                                                                                                                                                                                                                                            if (n3 >= ((FlowerPatchManager)object2).lastUpdateTicks.length) break block205;
                                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                                        do {
                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getFlowerPatchManager();
                                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeLong(((FlowerPatchManager)object2).lastUpdateTicks[n3]);
                                                                                                                                                                                                                                                                                                                                                            ++n3;
                                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getFlowerPatchManager();
                                                                                                                                                                                                                                                                                                                                                        } while (n3 < ((FlowerPatchManager)object2).lastUpdateTicks.length);
                                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                                    n3 = 0;
                                                                                                                                                                                                                                                                                                                                                    if (!true) break block206;
                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getFlowerPatchManager();
                                                                                                                                                                                                                                                                                                                                                    if (n3 >= ((FlowerPatchManager)object2).diseaseChanceMultipliers.length) break block207;
                                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                                do {
                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getFlowerPatchManager();
                                                                                                                                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeDouble(((FlowerPatchManager)object2).diseaseChanceMultipliers[n3]);
                                                                                                                                                                                                                                                                                                                                                    ++n3;
                                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getFlowerPatchManager();
                                                                                                                                                                                                                                                                                                                                                } while (n3 < ((FlowerPatchManager)object2).diseaseChanceMultipliers.length);
                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                                            if (!true) break block208;
                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getFruitTreePatchManager();
                                                                                                                                                                                                                                                                                                                                            if (n3 >= ((FruitTreePatchManager)object2).growthStages.length) break block209;
                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                        do {
                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getFruitTreePatchManager();
                                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((FruitTreePatchManager)object2).growthStages[n3]);
                                                                                                                                                                                                                                                                                                                                            ++n3;
                                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getFruitTreePatchManager();
                                                                                                                                                                                                                                                                                                                                        } while (n3 < ((FruitTreePatchManager)object2).growthStages.length);
                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                    n3 = 0;
                                                                                                                                                                                                                                                                                                                                    if (!true) break block210;
                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getFruitTreePatchManager();
                                                                                                                                                                                                                                                                                                                                    if (n3 >= ((FruitTreePatchManager)object2).treeIds.length) break block211;
                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                do {
                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getFruitTreePatchManager();
                                                                                                                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeInt(((FruitTreePatchManager)object2).treeIds[n3]);
                                                                                                                                                                                                                                                                                                                                    ++n3;
                                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getFruitTreePatchManager();
                                                                                                                                                                                                                                                                                                                                } while (n3 < ((FruitTreePatchManager)object2).treeIds.length);
                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                                            if (!true) break block212;
                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getFruitTreePatchManager();
                                                                                                                                                                                                                                                                                                                            if (n3 >= ((FruitTreePatchManager)object2).patchStates.length) break block213;
                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                        do {
                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getFruitTreePatchManager();
                                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((FruitTreePatchManager)object2).patchStates[n3]);
                                                                                                                                                                                                                                                                                                                            ++n3;
                                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getFruitTreePatchManager();
                                                                                                                                                                                                                                                                                                                        } while (n3 < ((FruitTreePatchManager)object2).patchStates.length);
                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                    n3 = 0;
                                                                                                                                                                                                                                                                                                                    if (!true) break block214;
                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getFruitTreePatchManager();
                                                                                                                                                                                                                                                                                                                    if (n3 >= ((FruitTreePatchManager)object2).lastUpdateTicks.length) break block215;
                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                do {
                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getFruitTreePatchManager();
                                                                                                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeLong(((FruitTreePatchManager)object2).lastUpdateTicks[n3]);
                                                                                                                                                                                                                                                                                                                    ++n3;
                                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getFruitTreePatchManager();
                                                                                                                                                                                                                                                                                                                } while (n3 < ((FruitTreePatchManager)object2).lastUpdateTicks.length);
                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                                            if (!true) break block216;
                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getFruitTreePatchManager();
                                                                                                                                                                                                                                                                                                            if (n3 >= ((FruitTreePatchManager)object2).diseaseChanceMultipliers.length) break block217;
                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                        do {
                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getFruitTreePatchManager();
                                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeDouble(((FruitTreePatchManager)object2).diseaseChanceMultipliers[n3]);
                                                                                                                                                                                                                                                                                                            ++n3;
                                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getFruitTreePatchManager();
                                                                                                                                                                                                                                                                                                        } while (n3 < ((FruitTreePatchManager)object2).diseaseChanceMultipliers.length);
                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                    n3 = 0;
                                                                                                                                                                                                                                                                                                    if (!true) break block218;
                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getFruitTreePatchManager();
                                                                                                                                                                                                                                                                                                    if (n3 >= ((FruitTreePatchManager)object2).protectionFlags.length) break block219;
                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                do {
                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getFruitTreePatchManager();
                                                                                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeBoolean(((FruitTreePatchManager)object2).protectionFlags[n3]);
                                                                                                                                                                                                                                                                                                    ++n3;
                                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getFruitTreePatchManager();
                                                                                                                                                                                                                                                                                                } while (n3 < ((FruitTreePatchManager)object2).protectionFlags.length);
                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                                            if (!true) break block220;
                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getHerbPatchManager();
                                                                                                                                                                                                                                                                                            if (n3 >= ((HerbPatchManager)object2).growthStages.length) break block221;
                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                        do {
                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getHerbPatchManager();
                                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((HerbPatchManager)object2).growthStages[n3]);
                                                                                                                                                                                                                                                                                            ++n3;
                                                                                                                                                                                                                                                                                            object2 = ((Player)object).getHerbPatchManager();
                                                                                                                                                                                                                                                                                        } while (n3 < ((HerbPatchManager)object2).growthStages.length);
                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                    n3 = 0;
                                                                                                                                                                                                                                                                                    if (!true) break block222;
                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getHerbPatchManager();
                                                                                                                                                                                                                                                                                    if (n3 >= ((HerbPatchManager)object2).cropIds.length) break block223;
                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                do {
                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getHerbPatchManager();
                                                                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeInt(((HerbPatchManager)object2).cropIds[n3]);
                                                                                                                                                                                                                                                                                    ++n3;
                                                                                                                                                                                                                                                                                    object2 = ((Player)object).getHerbPatchManager();
                                                                                                                                                                                                                                                                                } while (n3 < ((HerbPatchManager)object2).cropIds.length);
                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                                            if (!true) break block224;
                                                                                                                                                                                                                                                                            object2 = ((Player)object).getHerbPatchManager();
                                                                                                                                                                                                                                                                            if (n3 >= ((HerbPatchManager)object2).harvestAmounts.length) break block225;
                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                        do {
                                                                                                                                                                                                                                                                            object2 = ((Player)object).getHerbPatchManager();
                                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((HerbPatchManager)object2).harvestAmounts[n3]);
                                                                                                                                                                                                                                                                            ++n3;
                                                                                                                                                                                                                                                                            object2 = ((Player)object).getHerbPatchManager();
                                                                                                                                                                                                                                                                        } while (n3 < ((HerbPatchManager)object2).harvestAmounts.length);
                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                    n3 = 0;
                                                                                                                                                                                                                                                                    if (!true) break block226;
                                                                                                                                                                                                                                                                    object2 = ((Player)object).getHerbPatchManager();
                                                                                                                                                                                                                                                                    if (n3 >= ((HerbPatchManager)object2).patchStates.length) break block227;
                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                do {
                                                                                                                                                                                                                                                                    object2 = ((Player)object).getHerbPatchManager();
                                                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeInt(((HerbPatchManager)object2).patchStates[n3]);
                                                                                                                                                                                                                                                                    ++n3;
                                                                                                                                                                                                                                                                    object2 = ((Player)object).getHerbPatchManager();
                                                                                                                                                                                                                                                                } while (n3 < ((HerbPatchManager)object2).patchStates.length);
                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                                            if (!true) break block228;
                                                                                                                                                                                                                                                            object2 = ((Player)object).getHerbPatchManager();
                                                                                                                                                                                                                                                            if (n3 >= ((HerbPatchManager)object2).lastUpdateTicks.length) break block229;
                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                        do {
                                                                                                                                                                                                                                                            object2 = ((Player)object).getHerbPatchManager();
                                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeLong(((HerbPatchManager)object2).lastUpdateTicks[n3]);
                                                                                                                                                                                                                                                            ++n3;
                                                                                                                                                                                                                                                            object2 = ((Player)object).getHerbPatchManager();
                                                                                                                                                                                                                                                        } while (n3 < ((HerbPatchManager)object2).lastUpdateTicks.length);
                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                    n3 = 0;
                                                                                                                                                                                                                                                    if (!true) break block230;
                                                                                                                                                                                                                                                    object2 = ((Player)object).getHerbPatchManager();
                                                                                                                                                                                                                                                    if (n3 >= ((HerbPatchManager)object2).diseaseChanceMultipliers.length) break block231;
                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                do {
                                                                                                                                                                                                                                                    object2 = ((Player)object).getHerbPatchManager();
                                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeDouble(((HerbPatchManager)object2).diseaseChanceMultipliers[n3]);
                                                                                                                                                                                                                                                    ++n3;
                                                                                                                                                                                                                                                    object2 = ((Player)object).getHerbPatchManager();
                                                                                                                                                                                                                                                } while (n3 < ((HerbPatchManager)object2).diseaseChanceMultipliers.length);
                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                                            if (!true) break block232;
                                                                                                                                                                                                                                            object2 = ((Player)object).getHopsPatchManager();
                                                                                                                                                                                                                                            if (n3 >= ((HopsPatchManager)object2).growthStages.length) break block233;
                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                        do {
                                                                                                                                                                                                                                            object2 = ((Player)object).getHopsPatchManager();
                                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((HopsPatchManager)object2).growthStages[n3]);
                                                                                                                                                                                                                                            ++n3;
                                                                                                                                                                                                                                            object2 = ((Player)object).getHopsPatchManager();
                                                                                                                                                                                                                                        } while (n3 < ((HopsPatchManager)object2).growthStages.length);
                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                    n3 = 0;
                                                                                                                                                                                                                                    if (!true) break block234;
                                                                                                                                                                                                                                    object2 = ((Player)object).getHopsPatchManager();
                                                                                                                                                                                                                                    if (n3 >= ((HopsPatchManager)object2).cropIds.length) break block235;
                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                do {
                                                                                                                                                                                                                                    object2 = ((Player)object).getHopsPatchManager();
                                                                                                                                                                                                                                    ((DataOutputStream)object3).writeInt(((HopsPatchManager)object2).cropIds[n3]);
                                                                                                                                                                                                                                    ++n3;
                                                                                                                                                                                                                                    object2 = ((Player)object).getHopsPatchManager();
                                                                                                                                                                                                                                } while (n3 < ((HopsPatchManager)object2).cropIds.length);
                                                                                                                                                                                                                            }
                                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                                            if (!true) break block236;
                                                                                                                                                                                                                            object2 = ((Player)object).getHopsPatchManager();
                                                                                                                                                                                                                            if (n3 >= ((HopsPatchManager)object2).harvestAmounts.length) break block237;
                                                                                                                                                                                                                        }
                                                                                                                                                                                                                        do {
                                                                                                                                                                                                                            object2 = ((Player)object).getHopsPatchManager();
                                                                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((HopsPatchManager)object2).harvestAmounts[n3]);
                                                                                                                                                                                                                            ++n3;
                                                                                                                                                                                                                            object2 = ((Player)object).getHopsPatchManager();
                                                                                                                                                                                                                        } while (n3 < ((HopsPatchManager)object2).harvestAmounts.length);
                                                                                                                                                                                                                    }
                                                                                                                                                                                                                    n3 = 0;
                                                                                                                                                                                                                    if (!true) break block238;
                                                                                                                                                                                                                    object2 = ((Player)object).getHopsPatchManager();
                                                                                                                                                                                                                    if (n3 >= ((HopsPatchManager)object2).patchStates.length) break block239;
                                                                                                                                                                                                                }
                                                                                                                                                                                                                do {
                                                                                                                                                                                                                    object2 = ((Player)object).getHopsPatchManager();
                                                                                                                                                                                                                    ((DataOutputStream)object3).writeInt(((HopsPatchManager)object2).patchStates[n3]);
                                                                                                                                                                                                                    ++n3;
                                                                                                                                                                                                                    object2 = ((Player)object).getHopsPatchManager();
                                                                                                                                                                                                                } while (n3 < ((HopsPatchManager)object2).patchStates.length);
                                                                                                                                                                                                            }
                                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                                            if (!true) break block240;
                                                                                                                                                                                                            object2 = ((Player)object).getHopsPatchManager();
                                                                                                                                                                                                            if (n3 >= ((HopsPatchManager)object2).lastUpdateTicks.length) break block241;
                                                                                                                                                                                                        }
                                                                                                                                                                                                        do {
                                                                                                                                                                                                            object2 = ((Player)object).getHopsPatchManager();
                                                                                                                                                                                                            ((DataOutputStream)object3).writeLong(((HopsPatchManager)object2).lastUpdateTicks[n3]);
                                                                                                                                                                                                            ++n3;
                                                                                                                                                                                                            object2 = ((Player)object).getHopsPatchManager();
                                                                                                                                                                                                        } while (n3 < ((HopsPatchManager)object2).lastUpdateTicks.length);
                                                                                                                                                                                                    }
                                                                                                                                                                                                    n3 = 0;
                                                                                                                                                                                                    if (!true) break block242;
                                                                                                                                                                                                    object2 = ((Player)object).getHopsPatchManager();
                                                                                                                                                                                                    if (n3 >= ((HopsPatchManager)object2).diseaseChanceMultipliers.length) break block243;
                                                                                                                                                                                                }
                                                                                                                                                                                                do {
                                                                                                                                                                                                    object2 = ((Player)object).getHopsPatchManager();
                                                                                                                                                                                                    ((DataOutputStream)object3).writeDouble(((HopsPatchManager)object2).diseaseChanceMultipliers[n3]);
                                                                                                                                                                                                    ++n3;
                                                                                                                                                                                                    object2 = ((Player)object).getHopsPatchManager();
                                                                                                                                                                                                } while (n3 < ((HopsPatchManager)object2).diseaseChanceMultipliers.length);
                                                                                                                                                                                            }
                                                                                                                                                                                            n3 = 0;
                                                                                                                                                                                            if (!true) break block244;
                                                                                                                                                                                            object2 = ((Player)object).getHopsPatchManager();
                                                                                                                                                                                            if (n3 >= ((HopsPatchManager)object2).protectionFlags.length) break block245;
                                                                                                                                                                                        }
                                                                                                                                                                                        do {
                                                                                                                                                                                            object2 = ((Player)object).getHopsPatchManager();
                                                                                                                                                                                            ((DataOutputStream)object3).writeBoolean(((HopsPatchManager)object2).protectionFlags[n3]);
                                                                                                                                                                                            ++n3;
                                                                                                                                                                                            object2 = ((Player)object).getHopsPatchManager();
                                                                                                                                                                                        } while (n3 < ((HopsPatchManager)object2).protectionFlags.length);
                                                                                                                                                                                    }
                                                                                                                                                                                    n3 = 0;
                                                                                                                                                                                    if (!true) break block246;
                                                                                                                                                                                    object2 = ((Player)object).getSpecialTreePatchManager();
                                                                                                                                                                                    if (n3 >= ((SpecialTreePatchManager)object2).growthStages.length) break block247;
                                                                                                                                                                                }
                                                                                                                                                                                do {
                                                                                                                                                                                    object2 = ((Player)object).getSpecialTreePatchManager();
                                                                                                                                                                                    ((DataOutputStream)object3).writeInt(((SpecialTreePatchManager)object2).growthStages[n3]);
                                                                                                                                                                                    ++n3;
                                                                                                                                                                                    object2 = ((Player)object).getSpecialTreePatchManager();
                                                                                                                                                                                } while (n3 < ((SpecialTreePatchManager)object2).growthStages.length);
                                                                                                                                                                            }
                                                                                                                                                                            n3 = 0;
                                                                                                                                                                            if (!true) break block248;
                                                                                                                                                                            object2 = ((Player)object).getSpecialTreePatchManager();
                                                                                                                                                                            if (n3 >= ((SpecialTreePatchManager)object2).treeIds.length) break block249;
                                                                                                                                                                        }
                                                                                                                                                                        do {
                                                                                                                                                                            object2 = ((Player)object).getSpecialTreePatchManager();
                                                                                                                                                                            ((DataOutputStream)object3).writeInt(((SpecialTreePatchManager)object2).treeIds[n3]);
                                                                                                                                                                            ++n3;
                                                                                                                                                                            object2 = ((Player)object).getSpecialTreePatchManager();
                                                                                                                                                                        } while (n3 < ((SpecialTreePatchManager)object2).treeIds.length);
                                                                                                                                                                    }
                                                                                                                                                                    n3 = 0;
                                                                                                                                                                    if (!true) break block250;
                                                                                                                                                                    object2 = ((Player)object).getSpecialTreePatchManager();
                                                                                                                                                                    if (n3 >= ((SpecialTreePatchManager)object2).patchStates.length) break block251;
                                                                                                                                                                }
                                                                                                                                                                do {
                                                                                                                                                                    object2 = ((Player)object).getSpecialTreePatchManager();
                                                                                                                                                                    ((DataOutputStream)object3).writeInt(((SpecialTreePatchManager)object2).patchStates[n3]);
                                                                                                                                                                    ++n3;
                                                                                                                                                                    object2 = ((Player)object).getSpecialTreePatchManager();
                                                                                                                                                                } while (n3 < ((SpecialTreePatchManager)object2).patchStates.length);
                                                                                                                                                            }
                                                                                                                                                            n3 = 0;
                                                                                                                                                            if (!true) break block252;
                                                                                                                                                            object2 = ((Player)object).getSpecialTreePatchManager();
                                                                                                                                                            if (n3 >= ((SpecialTreePatchManager)object2).lastUpdateTicks.length) break block253;
                                                                                                                                                        }
                                                                                                                                                        do {
                                                                                                                                                            object2 = ((Player)object).getSpecialTreePatchManager();
                                                                                                                                                            ((DataOutputStream)object3).writeLong(((SpecialTreePatchManager)object2).lastUpdateTicks[n3]);
                                                                                                                                                            ++n3;
                                                                                                                                                            object2 = ((Player)object).getSpecialTreePatchManager();
                                                                                                                                                        } while (n3 < ((SpecialTreePatchManager)object2).lastUpdateTicks.length);
                                                                                                                                                    }
                                                                                                                                                    n3 = 0;
                                                                                                                                                    if (!true) break block254;
                                                                                                                                                    object2 = ((Player)object).getSpecialTreePatchManager();
                                                                                                                                                    if (n3 >= ((SpecialTreePatchManager)object2).diseaseChanceMultipliers.length) break block255;
                                                                                                                                                }
                                                                                                                                                do {
                                                                                                                                                    object2 = ((Player)object).getSpecialTreePatchManager();
                                                                                                                                                    ((DataOutputStream)object3).writeDouble(((SpecialTreePatchManager)object2).diseaseChanceMultipliers[n3]);
                                                                                                                                                    ++n3;
                                                                                                                                                    object2 = ((Player)object).getSpecialTreePatchManager();
                                                                                                                                                } while (n3 < ((SpecialTreePatchManager)object2).diseaseChanceMultipliers.length);
                                                                                                                                            }
                                                                                                                                            n3 = 0;
                                                                                                                                            if (!true) break block256;
                                                                                                                                            object2 = ((Player)object).getSpecialCropPatchManager();
                                                                                                                                            if (n3 >= ((SpecialCropPatchManager)object2).growthStages.length) break block257;
                                                                                                                                        }
                                                                                                                                        do {
                                                                                                                                            object2 = ((Player)object).getSpecialCropPatchManager();
                                                                                                                                            ((DataOutputStream)object3).writeInt(((SpecialCropPatchManager)object2).growthStages[n3]);
                                                                                                                                            ++n3;
                                                                                                                                            object2 = ((Player)object).getSpecialCropPatchManager();
                                                                                                                                        } while (n3 < ((SpecialCropPatchManager)object2).growthStages.length);
                                                                                                                                    }
                                                                                                                                    n3 = 0;
                                                                                                                                    if (!true) break block258;
                                                                                                                                    object2 = ((Player)object).getSpecialCropPatchManager();
                                                                                                                                    if (n3 >= ((SpecialCropPatchManager)object2).cropIds.length) break block259;
                                                                                                                                }
                                                                                                                                do {
                                                                                                                                    object2 = ((Player)object).getSpecialCropPatchManager();
                                                                                                                                    ((DataOutputStream)object3).writeInt(((SpecialCropPatchManager)object2).cropIds[n3]);
                                                                                                                                    ++n3;
                                                                                                                                    object2 = ((Player)object).getSpecialCropPatchManager();
                                                                                                                                } while (n3 < ((SpecialCropPatchManager)object2).cropIds.length);
                                                                                                                            }
                                                                                                                            n3 = 0;
                                                                                                                            if (!true) break block260;
                                                                                                                            object2 = ((Player)object).getSpecialCropPatchManager();
                                                                                                                            if (n3 >= ((SpecialCropPatchManager)object2).patchStates.length) break block261;
                                                                                                                        }
                                                                                                                        do {
                                                                                                                            object2 = ((Player)object).getSpecialCropPatchManager();
                                                                                                                            ((DataOutputStream)object3).writeInt(((SpecialCropPatchManager)object2).patchStates[n3]);
                                                                                                                            ++n3;
                                                                                                                            object2 = ((Player)object).getSpecialCropPatchManager();
                                                                                                                        } while (n3 < ((SpecialCropPatchManager)object2).patchStates.length);
                                                                                                                    }
                                                                                                                    n3 = 0;
                                                                                                                    if (!true) break block262;
                                                                                                                    object2 = ((Player)object).getSpecialCropPatchManager();
                                                                                                                    if (n3 >= ((SpecialCropPatchManager)object2).lastUpdateTicks.length) break block263;
                                                                                                                }
                                                                                                                do {
                                                                                                                    object2 = ((Player)object).getSpecialCropPatchManager();
                                                                                                                    ((DataOutputStream)object3).writeLong(((SpecialCropPatchManager)object2).lastUpdateTicks[n3]);
                                                                                                                    ++n3;
                                                                                                                    object2 = ((Player)object).getSpecialCropPatchManager();
                                                                                                                } while (n3 < ((SpecialCropPatchManager)object2).lastUpdateTicks.length);
                                                                                                            }
                                                                                                            n3 = 0;
                                                                                                            if (!true) break block264;
                                                                                                            object2 = ((Player)object).getSpecialCropPatchManager();
                                                                                                            if (n3 >= ((SpecialCropPatchManager)object2).diseaseChanceMultipliers.length) break block265;
                                                                                                        }
                                                                                                        do {
                                                                                                            object2 = ((Player)object).getSpecialCropPatchManager();
                                                                                                            ((DataOutputStream)object3).writeDouble(((SpecialCropPatchManager)object2).diseaseChanceMultipliers[n3]);
                                                                                                            ++n3;
                                                                                                            object2 = ((Player)object).getSpecialCropPatchManager();
                                                                                                        } while (n3 < ((SpecialCropPatchManager)object2).diseaseChanceMultipliers.length);
                                                                                                    }
                                                                                                    n3 = 0;
                                                                                                    if (!true) break block266;
                                                                                                    object2 = ((Player)object).getTreePatchManager();
                                                                                                    if (n3 >= ((TreePatchManager)object2).growthStages.length) break block267;
                                                                                                }
                                                                                                do {
                                                                                                    object2 = ((Player)object).getTreePatchManager();
                                                                                                    ((DataOutputStream)object3).writeInt(((TreePatchManager)object2).growthStages[n3]);
                                                                                                    ++n3;
                                                                                                    object2 = ((Player)object).getTreePatchManager();
                                                                                                } while (n3 < ((TreePatchManager)object2).growthStages.length);
                                                                                            }
                                                                                            n3 = 0;
                                                                                            if (!true) break block268;
                                                                                            object2 = ((Player)object).getTreePatchManager();
                                                                                            if (n3 >= ((TreePatchManager)object2).treeIds.length) break block269;
                                                                                        }
                                                                                        do {
                                                                                            object2 = ((Player)object).getTreePatchManager();
                                                                                            ((DataOutputStream)object3).writeInt(((TreePatchManager)object2).treeIds[n3]);
                                                                                            ++n3;
                                                                                            object2 = ((Player)object).getTreePatchManager();
                                                                                        } while (n3 < ((TreePatchManager)object2).treeIds.length);
                                                                                    }
                                                                                    n3 = 0;
                                                                                    if (!true) break block270;
                                                                                    object2 = ((Player)object).getTreePatchManager();
                                                                                    if (n3 >= ((TreePatchManager)object2).patchData.length) break block271;
                                                                                }
                                                                                do {
                                                                                    object2 = ((Player)object).getTreePatchManager();
                                                                                    ((DataOutputStream)object3).writeInt(((TreePatchManager)object2).patchData[n3]);
                                                                                    ++n3;
                                                                                    object2 = ((Player)object).getTreePatchManager();
                                                                                } while (n3 < ((TreePatchManager)object2).patchData.length);
                                                                            }
                                                                            n3 = 0;
                                                                            if (!true) break block272;
                                                                            object2 = ((Player)object).getTreePatchManager();
                                                                            if (n3 >= ((TreePatchManager)object2).patchStates.length) break block273;
                                                                        }
                                                                        do {
                                                                            object2 = ((Player)object).getTreePatchManager();
                                                                            ((DataOutputStream)object3).writeInt(((TreePatchManager)object2).patchStates[n3]);
                                                                            ++n3;
                                                                            object2 = ((Player)object).getTreePatchManager();
                                                                        } while (n3 < ((TreePatchManager)object2).patchStates.length);
                                                                    }
                                                                    n3 = 0;
                                                                    if (!true) break block274;
                                                                    object2 = ((Player)object).getTreePatchManager();
                                                                    if (n3 >= ((TreePatchManager)object2).lastUpdateTicks.length) break block275;
                                                                }
                                                                do {
                                                                    object2 = ((Player)object).getTreePatchManager();
                                                                    ((DataOutputStream)object3).writeLong(((TreePatchManager)object2).lastUpdateTicks[n3]);
                                                                    ++n3;
                                                                    object2 = ((Player)object).getTreePatchManager();
                                                                } while (n3 < ((TreePatchManager)object2).lastUpdateTicks.length);
                                                            }
                                                            n3 = 0;
                                                            if (!true) break block276;
                                                            object2 = ((Player)object).getTreePatchManager();
                                                            if (n3 >= ((TreePatchManager)object2).diseaseChanceMultipliers.length) break block277;
                                                        }
                                                        do {
                                                            object2 = ((Player)object).getTreePatchManager();
                                                            ((DataOutputStream)object3).writeDouble(((TreePatchManager)object2).diseaseChanceMultipliers[n3]);
                                                            ++n3;
                                                            object2 = ((Player)object).getTreePatchManager();
                                                        } while (n3 < ((TreePatchManager)object2).diseaseChanceMultipliers.length);
                                                    }
                                                    n3 = 0;
                                                    if (!true) break block278;
                                                    object2 = ((Player)object).getTreePatchManager();
                                                    if (n3 >= ((TreePatchManager)object2).protectionFlags.length) break block279;
                                                }
                                                do {
                                                    object2 = ((Player)object).getTreePatchManager();
                                                    ((DataOutputStream)object3).writeBoolean(((TreePatchManager)object2).protectionFlags[n3]);
                                                    ++n3;
                                                    object2 = ((Player)object).getTreePatchManager();
                                                } while (n3 < ((TreePatchManager)object2).protectionFlags.length);
                                            }
                                            n3 = 0;
                                            if (!true) break block280;
                                            object2 = ((Player)object).getCompostBinManager();
                                            if (n3 >= ((CompostBinManager)object2).states.length) break block281;
                                        }
                                        do {
                                            object2 = ((Player)object).getCompostBinManager();
                                            ((DataOutputStream)object3).writeInt(((CompostBinManager)object2).states[n3]);
                                            ++n3;
                                            object2 = ((Player)object).getCompostBinManager();
                                        } while (n3 < ((CompostBinManager)object2).states.length);
                                    }
                                    n3 = 0;
                                    if (!true) break block282;
                                    object2 = ((Player)object).getCompostBinManager();
                                    if (n3 >= ((CompostBinManager)object2).lastUpdateTicks.length) break block283;
                                }
                                do {
                                    object2 = ((Player)object).getCompostBinManager();
                                    ((DataOutputStream)object3).writeLong(((CompostBinManager)object2).lastUpdateTicks[n3]);
                                    ++n3;
                                    object2 = ((Player)object).getCompostBinManager();
                                } while (n3 < ((CompostBinManager)object2).lastUpdateTicks.length);
                            }
                            n3 = 0;
                            if (!true) break block284;
                            object2 = ((Player)object).getCompostBinManager();
                            if (n3 >= ((CompostBinManager)object2).itemIds.length) break block285;
                        }
                        do {
                            object2 = ((Player)object).getCompostBinManager();
                            ((DataOutputStream)object3).writeInt(((CompostBinManager)object2).itemIds[n3]);
                            ++n3;
                            object2 = ((Player)object).getCompostBinManager();
                        } while (n3 < ((CompostBinManager)object2).itemIds.length);
                    }
                    n3 = 0;
                    if (!true) break block286;
                    object2 = ((Player)object).getFarmingToolStore();
                    if (n3 >= ((FarmingToolStore)object2).storedAmounts.length) break block287;
                }
                do {
                    object2 = ((Player)object).getFarmingToolStore();
                    ((DataOutputStream)object3).writeInt(((FarmingToolStore)object2).storedAmounts[n3]);
                    ++n3;
                    object2 = ((Player)object).getFarmingToolStore();
                } while (n3 < ((FarmingToolStore)object2).storedAmounts.length);
            }
            ((DataOutputStream)object3).writeInt(((Player)object).getSlayerManager().slayerMasterId);
            ((DataOutputStream)object3).writeUTF(((Player)object).getSlayerManager().slayerTaskName);
            ((DataOutputStream)object3).writeInt(((Player)object).getSlayerManager().taskAmount);
            int n4 = n3 = ((Player)object).getSpellbook() == Spellbook.ANCIENT ? 1 : 0;
            if (((Player)object).getSpellbook() == Spellbook.NECROMANCY) {
                n3 = ((Player)object).E == Spellbook.ANCIENT ? 1 : 0;
            }
            ((DataOutputStream)object3).writeBoolean(n3 != 0);
            ((DataOutputStream)object3).writeBoolean(((Player)object).isBrimhavenOpen());
            object2 = object;
            ((DataOutputStream)object3).writeBoolean(((Player)object2).killedClueAttacker);
            int n5 = 0;
            while (n5 < musicUnlockConfigIds.length) {
                ((DataOutputStream)object3).writeInt(((Player)object).ep[musicUnlockConfigIds[n5]]);
                n5 += 1;
            }
            n5 = 0;
            while (n5 < QuestDefinition.questStateCapacity) {
                ((DataOutputStream)object3).writeInt(((Player)object).getQuestState(n5, true));
                n5 += 1;
            }
            ((DataOutputStream)object3).writeByte(((Player)object).dv);
            ((DataOutputStream)object3).writeByte(((Player)object).bO);
            ((DataOutputStream)object3).writeBoolean(((Player)object).D);
            ((DataOutputStream)object3).writeByte(((Player)object).getCoalTruckCoalCount());
            if (!((Player)object).ownsClueScroll()) {
                ((Player)object).ar = 0;
            }
            ((DataOutputStream)object3).writeByte(((Player)object).ar);
            n5 = PuzzleBoxHandler.isCluePuzzleSolved((Player)object) ? 1 : 0;
            if (!((Player)object).ownsCluePuzzleBox()) {
                n5 = 0;
            }
            ((DataOutputStream)object3).writeBoolean(n5 != 0);
            ((DataOutputStream)object3).writeByte(((Player)object).ee);
            ((DataOutputStream)object3).writeInt(((Player)object).getBossPetUnlockFlags());
            ((DataOutputStream)object3).writeBoolean(((Player)object).barrowsDoorPuzzleSolved);
            ((DataOutputStream)object3).writeBoolean(((Player)object).barrowsChestOpened);
            ((DataOutputStream)object3).writeInt(((Player)object).ep[452]);
            Object object4 = object;
            ((DataOutputStream)object3).writeByte(((Player)object4).flourMillHopperGrainCount);
            ((DataOutputStream)object3).writeInt(((Player)object).ep[FlourMillHandler.flourBinConfigId]);
            ((DataOutputStream)object3).writeInt(((Player)object).bK);
            n2 = 0;
            while (n2 < QuestDefinition.questStateCapacity) {
                ((DataOutputStream)object3).writeInt(((Player)object).questProgressFlags[n2]);
                ++n2;
            }
            n2 = 0;
            while (n2 < 100) {
                ((DataOutputStream)object3).writeInt(((Player)object).questHookStates[n2]);
                ++n2;
            }
            ((DataOutputStream)object3).writeByte(((Player)object).getPublicChatMode());
            ((DataOutputStream)object3).writeByte(((Player)object).getPrivateChatMode());
            ((DataOutputStream)object3).writeByte(((Player)object).getTradeMode());
            ((DataOutputStream)object3).writeInt(((Player)object).em);
            ((DataOutputStream)object3).writeLong(((Player)object).bY);
            ((DataOutputStream)object3).writeInt(((Player)object).ca);
            ((DataOutputStream)object3).writeInt(((Player)object).bZ);
            ((Player)object).ck = System.currentTimeMillis();
            object4 = object;
            ((DataOutputStream)object3).writeUTF("");
            ((DataOutputStream)object3).writeInt(((Player)object).familyCrestGauntletItemId);
            ((DataOutputStream)object3).writeInt(((Player)object).mageArenaFlamesOfZamorakCastsRemaining);
            ((DataOutputStream)object3).writeInt(((Player)object).mageArenaSaradominStrikeCastsRemaining);
            ((DataOutputStream)object3).writeInt(((Player)object).mageArenaClawsOfGuthixCastsRemaining);
            ((DataOutputStream)object3).writeByte(((Player)object).mageArenaProgressStage);
            n2 = 0;
            while (n2 < 6) {
                ((DataOutputStream)object3).writeBoolean(((Player)object).grandExchangeSellOfferFlags[n2]);
                ++n2;
            }
            n2 = 0;
            while (n2 < 6) {
                ((DataOutputStream)object3).writeInt(((Player)object).grandExchangeItemIds[n2]);
                ++n2;
            }
            n2 = 0;
            while (n2 < 6) {
                ((DataOutputStream)object3).writeInt(((Player)object).grandExchangeQuantities[n2]);
                ++n2;
            }
            n2 = 0;
            while (n2 < 6) {
                ((DataOutputStream)object3).writeInt(((Player)object).grandExchangeUnitPrices[n2]);
                ++n2;
            }
            n2 = 0;
            while (n2 < 6) {
                ((DataOutputStream)object3).writeBoolean(((Player)object).grandExchangeCancelledFlags[n2]);
                ++n2;
            }
            n2 = 0;
            while (n2 < 6) {
                ((DataOutputStream)object3).writeInt(((Player)object).grandExchangeCompletedQuantities[n2]);
                ++n2;
            }
            n2 = 0;
            while (n2 < 6) {
                ((DataOutputStream)object3).writeInt(((Player)object).grandExchangeTotalPrices[n2]);
                ++n2;
            }
            n2 = 0;
            while (n2 < 6) {
                ((DataOutputStream)object3).writeInt(((Player)object).grandExchangePrimaryCollectAmounts[n2]);
                ++n2;
            }
            n2 = 0;
            while (n2 < 6) {
                ((DataOutputStream)object3).writeInt(((Player)object).grandExchangeSecondaryCollectAmounts[n2]);
                ++n2;
            }
            n2 = 0;
            while (n2 < 6) {
                ((DataOutputStream)object3).writeBoolean(((Player)object).grandExchangeFinishMessagePending[n2]);
                ++n2;
            }
            ((DataOutputStream)object3).writeInt(((Player)object).getTelekineticTheatreController().pizazzPoints);
            ((DataOutputStream)object3).writeInt(((Player)object).getEnchantmentChamberController().pizazzPoints);
            ((DataOutputStream)object3).writeInt(((Player)object).getAlchemistPlaygroundController().pizazzPoints);
            ((DataOutputStream)object3).writeInt(((Player)object).getCreatureGraveyardController().pizazzPoints);
            ((DataOutputStream)object3).writeBoolean(((Player)object).bonesToPeachesUnlocked);
            ((DataOutputStream)object3).writeByte(((Player)object).getTelekineticTheatreController().mazeIndex);
            ((DataOutputStream)object3).writeBoolean(((Player)object).getTelekineticTheatreController().mazeSolved);
            ((DataOutputStream)object3).writeByte(((Player)object).getTelekineticTheatreController().consecutiveMazesSolved);
            ((DataOutputStream)object3).writeInt(((Player)object).barrowsRewardPotential);
            ((DataOutputStream)object3).writeByte(((Player)object).gameMode);
            ((DataOutputStream)object3).writeInt(((Player)object).barrowsRunsCompleted);
            ((DataOutputStream)object3).writeLong(((Player)object).godWarsLastAltarBlessingMillis);
            ((DataOutputStream)object3).writeInt(((Player)object).ep[GodWarsDungeonManager.ropeShortcutConfigId]);
            n2 = 0;
            while (n2 < ((Player)object).godWarsKillCounts.length) {
                ((DataOutputStream)object3).writeInt(((Player)object).godWarsKillCounts[n2]);
                ++n2;
            }
            ((DataOutputStream)object3).writeByte(((Player)object).craftingThreadUseCount);
            ((DataOutputStream)object3).writeLong(((Player)object).membershipExpiresMillis);
            ((DataOutputStream)object3).writeByte(((Player)object).l);
            ((DataOutputStream)object3).writeInt(((Player)object).ep[33]);
            ((DataOutputStream)object3).writeShort(ServerSettings.cacheVersion);
            ((DataOutputStream)object3).writeInt(((Player)object).godBookPageFlags);
            ((DataOutputStream)object3).writeBoolean(((Player)object).swampCaveRopeAttached);
            ((DataOutputStream)object3).writeBoolean(((Player)object).lampOilStillFilled);
            ((DataOutputStream)object3).writeInt(((Player)object).enterTheAbyssMiniquestState);
            ((DataOutputStream)object3).writeBoolean(((Player)object).botEnabled);
            if (((Player)object).botEnabled) {
                ((DataOutputStream)object3).writeByte(((Player)object).botMode);
                n2 = -1;
                n = -1;
                if (((Player)object).currentBotTask != null) {
                    n2 = ((Player)object).currentBotTask.getTaskTypeId();
                    n = ((Player)object).currentBotTask.getTaskIndexForType(n2);
                }
                int n6 = -1;
                n3 = -1;
                if (((Player)object).deferredBotTask != null) {
                    n6 = ((Player)object).deferredBotTask.getTaskTypeId();
                    n3 = ((Player)object).deferredBotTask.getTaskIndexForType(n6);
                }
                ((DataOutputStream)object3).writeByte(n2);
                ((DataOutputStream)object3).writeInt(n);
                ((DataOutputStream)object3).writeByte(n6);
                ((DataOutputStream)object3).writeInt(n3);
                ((DataOutputStream)object3).writeUTF(((Player)object).botTaskState);
                n3 = 0;
                if (((Player)object).botTaskRequiredItems != null) {
                    n3 = ((Player)object).botTaskRequiredItems.length;
                }
                ((DataOutputStream)object3).writeByte(n3);
                int n7 = 0;
                while (n7 < n3) {
                    ((DataOutputStream)object3).writeInt(((Player)object).botTaskRequiredItems[n7].getId());
                    ((DataOutputStream)object3).writeInt(((Player)object).botTaskRequiredItems[n7].getAmount());
                    ++n7;
                }
                ((DataOutputStream)object3).writeInt(((Player)object).botFoodItemId);
                ((DataOutputStream)object3).writeByte(((Player)object).botPathSegmentIndex);
                ((DataOutputStream)object3).writeByte(((Player)object).botPathWaypointIndex);
                n7 = 0;
                n3 = -1;
                if (((Player)object).currentWorldRouteChoice != null) {
                    n7 = ((Player)object).currentWorldRouteChoice.isReversed();
                    n3 = BotWorldRouteWalker.getRouteIndex(((Player)object).currentWorldRouteChoice);
                }
                ((DataOutputStream)object3).writeBoolean(n7 != 0);
                ((DataOutputStream)object3).writeLong(((Player)object).getBotTaskRuntimeMillis());
                ((DataOutputStream)object3).writeByte(((Player)object).botTaskDurationMinutes);
                ((DataOutputStream)object3).writeByte(n3);
                ((DataOutputStream)object3).writeByte(((Player)object).tradeAdvertMode);
                ((DataOutputStream)object3).writeInt(((Player)object).botAdvertItemId);
                ((DataOutputStream)object3).writeInt(((Player)object).tradeAdvertQuantityRemaining);
                ((DataOutputStream)object3).writeInt(((Player)object).tradeAdvertUnitPrice);
                ((DataOutputStream)object3).writeBoolean(((Player)object).tradeAdvertScam);
                ((DataOutputStream)object3).writeBoolean(((Player)object).tradeAdvertVariableQuantity);
                ((DataOutputStream)object3).writeInt(((Player)object).tradeAdvertLastOfferAmount);
                ((DataOutputStream)object3).writeByte(((Player)object).botShopBuyMode);
                ((DataOutputStream)object3).writeInt(((Player)object).botTaskItemId);
                ((DataOutputStream)object3).writeInt(((Player)object).botShopItemAmount);
                ((DataOutputStream)object3).writeByte(((Player)object).botShopSellItemIds.size());
                n3 = 0;
                while (n3 < ((Player)object).botShopSellItemIds.size()) {
                    ((DataOutputStream)object3).writeInt((Integer)((Player)object).botShopSellItemIds.get(n3));
                    ++n3;
                }
                ((DataOutputStream)object3).writeByte(((Player)object).botCombatLoadoutItemIds.size());
                n3 = 0;
                while (n3 < ((Player)object).botCombatLoadoutItemIds.size()) {
                    ((DataOutputStream)object3).writeInt((Integer)((Player)object).botCombatLoadoutItemIds.get(n3));
                    ++n3;
                }
                ((DataOutputStream)object3).writeByte(((Player)object).botCombatStyle);
                ((DataOutputStream)object3).writeByte(((Player)object).botSkillTargetSkillId);
                ((DataOutputStream)object3).writeByte(((Player)object).botSkillTargetLevel);
                ((DataOutputStream)object3).writeByte(((Player)object).be);
                ((DataOutputStream)object3).writeByte(((Player)object).bf);
                ((DataOutputStream)object3).writeByte(((Player)object).bg);
                ((DataOutputStream)object3).writeByte(((Player)object).bh);
                ((DataOutputStream)object3).writeInt(((Player)object).botCompletionItemId);
                ((DataOutputStream)object3).writeInt(((Player)object).botCompletionItemAmount);
                ((DataOutputStream)object3).writeInt(((Player)object).bk);
                ((DataOutputStream)object3).writeInt(((Player)object).bl);
                ((DataOutputStream)object3).writeInt(((Player)object).bm);
                ((DataOutputStream)object3).writeInt(((Player)object).bn);
                ((DataOutputStream)object3).writeBoolean(((Player)object).botTaskReturnToBankRequested);
                ((DataOutputStream)object3).writeBoolean(false);
                ((DataOutputStream)object3).writeBoolean(false);
                ((DataOutputStream)object3).writeBoolean(false);
                ((DataOutputStream)object3).writeBoolean(false);
                ((DataOutputStream)object3).writeBoolean(false);
                ((DataOutputStream)object3).writeByte(((Player)object).botElementalSpellIndex);
                ((DataOutputStream)object3).writeByte(0);
                ((DataOutputStream)object3).writeByte(0);
                ((DataOutputStream)object3).writeByte(0);
                ((DataOutputStream)object3).writeByte(0);
                ((DataOutputStream)object3).writeInt(0);
                ((DataOutputStream)object3).writeInt(0);
                ((DataOutputStream)object3).writeInt(0);
                ((DataOutputStream)object3).writeInt(0);
                ((DataOutputStream)object3).writeInt(0);
                ((DataOutputStream)object3).writeUTF("");
                ((DataOutputStream)object3).writeUTF("");
                ((DataOutputStream)object3).writeUTF("");
                ((DataOutputStream)object3).writeUTF("");
                ((DataOutputStream)object3).writeUTF("");
            }
            ((DataOutputStream)object3).flush();
            ((FilterOutputStream)object3).close();
            return;
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            return;
        }
    }

    /*
     * Handled impossible loop by duplicating code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void saveCharacterFileRecord(CharacterFileRecord object) {
        try {
            int n;
            Object object2;
            Object object3;
            block107: {
                block106: {
                    block105: {
                        block104: {
                            new ElapsedTimer();
                            object3 = object;
                            object2 = new File("./data/characters/" + ((CharacterFileRecord)object3).username + ".dat");
                            if (!((File)object2).exists()) {
                                ((File)object2).createNewFile();
                            }
                            object2 = new FileOutputStream((File)object2);
                            object2 = new DataOutputStream((OutputStream)object2);
                            ((DataOutputStream)object2).writeShort(30);
                            object3 = object;
                            ((DataOutputStream)object2).writeUTF(((CharacterFileRecord)object3).username);
                            Object object4 = object;
                            ((DataOutputStream)object2).writeUTF(((CharacterFileRecord)object4).password);
                            object4 = object;
                            ((DataOutputStream)object2).writeUTF(((CharacterFileRecord)object4).hostAddress);
                            object3 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object3).playerRights);
                            object4 = object;
                            ((DataOutputStream)object2).writeUTF(((CharacterFileRecord)object4).legacyProfileString);
                            object4 = object;
                            ((DataOutputStream)object2).writeUTF(((CharacterFileRecord)object4).profileString1);
                            object4 = object;
                            ((DataOutputStream)object2).writeUTF(((CharacterFileRecord)object4).profileString2);
                            object4 = object;
                            ((DataOutputStream)object2).writeLong(((CharacterFileRecord)object4).lastSavedMillis);
                            object4 = object;
                            ((DataOutputStream)object2).writeLong(((CharacterFileRecord)object4).totalPlayTimeMillis);
                            object4 = object;
                            ((DataOutputStream)object2).writeLong(((CharacterFileRecord)object4).createdAtMillis);
                            object4 = object;
                            ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object4).loginRestrictionExempt);
                            object3 = object;
                            ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object3).memberFlag);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).donatorPoints);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).x);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).y);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).plane);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).gender);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).npcKillCount);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).playerKillCount);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).deathCount);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).easyCluesCompleted);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).mediumCluesCompleted);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).hardCluesCompleted);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).soldItemsValue);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).boughtItemsValue);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).duelWins);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).duelLosses);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).legacyQuestPoints);
                            object4 = object;
                            ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object4).autoRetaliate);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).fightMode);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).brightness);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).mouseButtons);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).publicChatEffects);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).splitPrivateChat);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).acceptAid);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).musicVolume);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).effectVolume);
                            object4 = object;
                            ((DataOutputStream)object2).writeDouble(((CharacterFileRecord)object4).specialEnergy);
                            object4 = object;
                            ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object4).changingBankPin);
                            object4 = object;
                            ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object4).deletingBankPin);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).pinAppendYear);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).pinAppendDate);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).bindingNecklaceCharge);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).ringOfForgingLife);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).ringOfRecoilLife);
                            object4 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object4).skullTimer);
                            object4 = object;
                            ((DataOutputStream)object2).writeShort(((CharacterFileRecord)object4).runEnergyRaw);
                            object4 = object;
                            ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object4).running);
                            n = 0;
                            while (n < ((CharacterFileRecord)object).currentPin.length) {
                                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).currentPin[n]);
                                ++n;
                            }
                            n = 0;
                            while (n < ((CharacterFileRecord)object).pendingPin.length) {
                                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).pendingPin[n]);
                                ++n;
                            }
                            n = 0;
                            while (n < 4) {
                                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).essencePouchAmounts[n]);
                                ++n;
                            }
                            n = 0;
                            while (n < ((CharacterFileRecord)object).appearanceParts.length) {
                                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).appearanceParts[n]);
                                ++n;
                            }
                            n = 0;
                            while (n < ((CharacterFileRecord)object).appearanceColors.length) {
                                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).appearanceColors[n]);
                                ++n;
                            }
                            n = 0;
                            if (!true) break block104;
                            object3 = object;
                            if (n >= ((CharacterFileRecord)object3).currentLevels.length) break block105;
                        }
                        do {
                            object3 = object;
                            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object3).currentLevels[n]);
                            ++n;
                            object3 = object;
                        } while (n < ((CharacterFileRecord)object3).currentLevels.length);
                    }
                    n = 0;
                    if (!true) break block106;
                    object3 = object;
                    if (n >= ((CharacterFileRecord)object3).skillExperience.length) break block107;
                }
                do {
                    object3 = object;
                    ((DataOutputStream)object2).writeInt((int)((CharacterFileRecord)object3).skillExperience[n]);
                    ++n;
                    object3 = object;
                } while (n < ((CharacterFileRecord)object3).skillExperience.length);
            }
            n = 0;
            while (n < 28) {
                object3 = ((CharacterFileRecord)object).inventoryItems[n];
                if (object3 == null) {
                    ((DataOutputStream)object2).writeInt(65535);
                } else {
                    ((DataOutputStream)object2).writeInt(((ItemStack)object3).getId());
                    ((DataOutputStream)object2).writeInt(((ItemStack)object3).getAmount());
                    ((DataOutputStream)object2).writeInt(((ItemStack)object3).getMetadata());
                }
                ++n;
            }
            n = 0;
            while (n < 14) {
                object3 = ((CharacterFileRecord)object).equipmentItems[n];
                if (object3 == null) {
                    ((DataOutputStream)object2).writeInt(65535);
                } else {
                    ((DataOutputStream)object2).writeInt(((ItemStack)object3).getId());
                    ((DataOutputStream)object2).writeInt(((ItemStack)object3).getAmount());
                    ((DataOutputStream)object2).writeInt(((ItemStack)object3).getMetadata());
                }
                ++n;
            }
            ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).bankTabs.size());
            n = 0;
            while (n < ((CharacterFileRecord)object).bankTabs.size()) {
                object3 = ((CharacterFileBankTab)((CharacterFileRecord)object).bankTabs.get(n)).getItems();
                int n2 = ((ArrayList)object3).size();
                ((DataOutputStream)object2).writeShort(n2);
                int n3 = 0;
                while (n3 < n2) {
                    ItemStack itemStack = (ItemStack)((ArrayList)object3).get(n3);
                    if (itemStack == null) {
                        ((DataOutputStream)object2).writeInt(65535);
                    } else {
                        ((DataOutputStream)object2).writeInt(itemStack.getId());
                        ((DataOutputStream)object2).writeInt(itemStack.getAmount());
                        ((DataOutputStream)object2).writeInt(itemStack.getMetadata());
                    }
                    ++n3;
                }
                ++n;
            }
            n = 0;
            while (n < ((CharacterFileRecord)object).friendsList.length) {
                ((DataOutputStream)object2).writeLong(((CharacterFileRecord)object).friendsList[n]);
                ++n;
            }
            n = 0;
            while (n < ((CharacterFileRecord)object).ignoreList.length) {
                ((DataOutputStream)object2).writeLong(((CharacterFileRecord)object).ignoreList[n]);
                ++n;
            }
            n = 0;
            while (n < ((CharacterFileRecord)object).queuedLoginItemIds.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).queuedLoginItemIds[n]);
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).queuedLoginItemAmounts[n]);
                ++n;
            }
            Object object5 = object;
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object5).abyssMageNpcId);
            object5 = object;
            ((DataOutputStream)object2).writeLong(((CharacterFileRecord)object5).muteExpires);
            object5 = object;
            ((DataOutputStream)object2).writeLong(((CharacterFileRecord)object5).banExpires);
            int n4 = 0;
            while (n4 < 6) {
                ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object).barrowsKilledBrothers[n4]);
                ++n4;
            }
            Object object6 = object;
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object6).barrowsKillCount);
            object6 = object;
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object6).barrowsTargetBrotherIndex);
            object6 = object;
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object6).poisonImmunityTicks);
            object6 = object;
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object6).antifireTicks);
            object6 = object;
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object6).teleblockTicks);
            object6 = object;
            ((DataOutputStream)object2).writeDouble(((CharacterFileRecord)object6).poisonDamage);
            int n5 = 0;
            while (n5 < ((CharacterFileRecord)object).allotmentGrowthStages.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).allotmentGrowthStages[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).allotmentCropIds.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).allotmentCropIds[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).allotmentHarvestAmounts.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).allotmentHarvestAmounts[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).allotmentPatchStates.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).allotmentPatchStates[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).allotmentLastUpdateTicks.length) {
                ((DataOutputStream)object2).writeLong(((CharacterFileRecord)object).allotmentLastUpdateTicks[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).allotmentDiseaseChanceMultipliers.length) {
                ((DataOutputStream)object2).writeDouble(((CharacterFileRecord)object).allotmentDiseaseChanceMultipliers[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).allotmentProtectionFlags.length) {
                ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object).allotmentProtectionFlags[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).bushGrowthStages.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).bushGrowthStages[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).bushCropIds.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).bushCropIds[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).bushPatchStates.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).bushPatchStates[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).bushLastUpdateTicks.length) {
                ((DataOutputStream)object2).writeLong(((CharacterFileRecord)object).bushLastUpdateTicks[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).bushDiseaseChanceMultipliers.length) {
                ((DataOutputStream)object2).writeDouble(((CharacterFileRecord)object).bushDiseaseChanceMultipliers[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).bushSavedFlags.length) {
                ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object).bushSavedFlags[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).flowerGrowthStages.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).flowerGrowthStages[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).flowerCropIds.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).flowerCropIds[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).flowerPatchStates.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).flowerPatchStates[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).flowerLastUpdateTicks.length) {
                ((DataOutputStream)object2).writeLong(((CharacterFileRecord)object).flowerLastUpdateTicks[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).flowerDiseaseChanceMultipliers.length) {
                ((DataOutputStream)object2).writeDouble(((CharacterFileRecord)object).flowerDiseaseChanceMultipliers[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).fruitTreeGrowthStages.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).fruitTreeGrowthStages[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).fruitTreeIds.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).fruitTreeIds[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).fruitTreePatchStates.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).fruitTreePatchStates[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).fruitTreeLastUpdateTicks.length) {
                ((DataOutputStream)object2).writeLong(((CharacterFileRecord)object).fruitTreeLastUpdateTicks[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).fruitTreeDiseaseChanceMultipliers.length) {
                ((DataOutputStream)object2).writeDouble(((CharacterFileRecord)object).fruitTreeDiseaseChanceMultipliers[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).fruitTreeSavedFlags.length) {
                ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object).fruitTreeSavedFlags[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).herbGrowthStages.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).herbGrowthStages[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).herbCropIds.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).herbCropIds[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).herbHarvestAmounts.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).herbHarvestAmounts[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).herbPatchStates.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).herbPatchStates[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).herbLastUpdateTicks.length) {
                ((DataOutputStream)object2).writeLong(((CharacterFileRecord)object).herbLastUpdateTicks[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).herbDiseaseChanceMultipliers.length) {
                ((DataOutputStream)object2).writeDouble(((CharacterFileRecord)object).herbDiseaseChanceMultipliers[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).hopsGrowthStages.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).hopsGrowthStages[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).hopsCropIds.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).hopsCropIds[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).hopsHarvestAmounts.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).hopsHarvestAmounts[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).hopsPatchStates.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).hopsPatchStates[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).hopsLastUpdateTicks.length) {
                ((DataOutputStream)object2).writeLong(((CharacterFileRecord)object).hopsLastUpdateTicks[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).hopsDiseaseChanceMultipliers.length) {
                ((DataOutputStream)object2).writeDouble(((CharacterFileRecord)object).hopsDiseaseChanceMultipliers[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).hopsProtectionFlags.length) {
                ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object).hopsProtectionFlags[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).specialTreeGrowthStages.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).specialTreeGrowthStages[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).specialTreeIds.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).specialTreeIds[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).specialTreePatchStates.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).specialTreePatchStates[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).specialTreeLastUpdateTicks.length) {
                ((DataOutputStream)object2).writeLong(((CharacterFileRecord)object).specialTreeLastUpdateTicks[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).specialTreeDiseaseChanceMultipliers.length) {
                ((DataOutputStream)object2).writeDouble(((CharacterFileRecord)object).specialTreeDiseaseChanceMultipliers[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).specialCropGrowthStages.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).specialCropGrowthStages[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).specialCropIds.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).specialCropIds[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).specialCropPatchStates.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).specialCropPatchStates[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).specialCropLastUpdateTicks.length) {
                ((DataOutputStream)object2).writeLong(((CharacterFileRecord)object).specialCropLastUpdateTicks[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).specialCropDiseaseChanceMultipliers.length) {
                ((DataOutputStream)object2).writeDouble(((CharacterFileRecord)object).specialCropDiseaseChanceMultipliers[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).treeGrowthStages.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).treeGrowthStages[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).treeIds.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).treeIds[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).treePatchData.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).treePatchData[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).treePatchStates.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).treePatchStates[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).treeLastUpdateTicks.length) {
                ((DataOutputStream)object2).writeLong(((CharacterFileRecord)object).treeLastUpdateTicks[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).treeDiseaseChanceMultipliers.length) {
                ((DataOutputStream)object2).writeDouble(((CharacterFileRecord)object).treeDiseaseChanceMultipliers[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).treeSavedFlags.length) {
                ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object).treeSavedFlags[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).compostBinStates.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).compostBinStates[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).compostBinLastUpdateTicks.length) {
                ((DataOutputStream)object2).writeLong(((CharacterFileRecord)object).compostBinLastUpdateTicks[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).compostBinItemIds.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).compostBinItemIds[n5]);
                ++n5;
            }
            n5 = 0;
            while (n5 < ((CharacterFileRecord)object).farmingToolStoreAmounts.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).farmingToolStoreAmounts[n5]);
                ++n5;
            }
            Object object7 = object;
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object7).slayerMasterId);
            object7 = object;
            ((DataOutputStream)object2).writeUTF(((CharacterFileRecord)object7).slayerTaskName);
            object7 = object;
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object7).slayerTaskAmount);
            object7 = object;
            ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object7).usingAncients);
            object7 = object;
            ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object7).brimhavenOpen);
            object7 = object;
            ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object7).killedClueAttacker);
            int n6 = 0;
            while (n6 < musicUnlockConfigIds.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).configStates[musicUnlockConfigIds[n6]]);
                ++n6;
            }
            n6 = 0;
            while (n6 < QuestDefinition.questStateCapacity) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).questProgress[n6]);
                ++n6;
            }
            Object object8 = object;
            ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object8).gangAffiliation);
            object8 = object;
            ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object8).piratesTreasureBananaCrateCount);
            object8 = object;
            ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object8).treasureTrailNavigationTaught);
            object8 = object;
            ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object8).coalTruckAmount);
            object8 = object;
            ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object8).treasureTrailStepCount);
            object8 = object;
            ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object8).cluePuzzleSolved);
            ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).skeletonSkinUnlocked);
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).petUnlockFlags);
            ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object).barrowsDoorPuzzleSolved);
            ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object).barrowsChestOpened);
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).configStates[452]);
            object8 = object;
            ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object8).flourMillHopperGrainCount);
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).configStates[FlourMillHandler.flourBinConfigId]);
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).questRandomSeed);
            int n7 = 0;
            while (n7 < QuestDefinition.questStateCapacity) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).questBitFlags[n7]);
                ++n7;
            }
            n7 = 0;
            while (n7 < 100) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).questHookStates[n7]);
                ++n7;
            }
            ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).publicChatMode);
            ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).privateChatMode);
            ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).tradeMode);
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).ck);
            ((DataOutputStream)object2).writeLong(((CharacterFileRecord)object).cl);
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).cm);
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).cn);
            Object object9 = object;
            ((DataOutputStream)object2).writeUTF(((CharacterFileRecord)object9).reservedVersion11String);
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).familyCrestGauntletItemId);
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).mageArenaFlamesOfZamorakCastsRemaining);
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).mageArenaSaradominStrikeCastsRemaining);
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).mageArenaClawsOfGuthixCastsRemaining);
            ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).mageArenaProgressStage);
            int n8 = 0;
            while (n8 < 6) {
                ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object).grandExchangeSellOfferFlags[n8]);
                ++n8;
            }
            n8 = 0;
            while (n8 < 6) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).grandExchangeItemIds[n8]);
                ++n8;
            }
            n8 = 0;
            while (n8 < 6) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).grandExchangeQuantities[n8]);
                ++n8;
            }
            n8 = 0;
            while (n8 < 6) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).grandExchangeUnitPrices[n8]);
                ++n8;
            }
            n8 = 0;
            while (n8 < 6) {
                ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object).grandExchangeCancelledFlags[n8]);
                ++n8;
            }
            n8 = 0;
            while (n8 < 6) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).grandExchangeCompletedQuantities[n8]);
                ++n8;
            }
            n8 = 0;
            while (n8 < 6) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).grandExchangeTotalPrices[n8]);
                ++n8;
            }
            n8 = 0;
            while (n8 < 6) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).grandExchangePrimaryCollectAmounts[n8]);
                ++n8;
            }
            n8 = 0;
            while (n8 < 6) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).grandExchangeSecondaryCollectAmounts[n8]);
                ++n8;
            }
            n8 = 0;
            while (n8 < 6) {
                ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object).grandExchangeFinishMessagePending[n8]);
                ++n8;
            }
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).telekineticPizazzPoints);
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).enchantmentPizazzPoints);
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).alchemistPizazzPoints);
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).graveyardPizazzPoints);
            ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object).bonesToPeachesUnlocked);
            ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).telekineticMazeIndex);
            ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object).telekineticMazeSolved);
            ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).telekineticConsecutiveMazesSolved);
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).barrowsRewardPotential);
            ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).gameMode);
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).barrowsRunsCompleted);
            ((DataOutputStream)object2).writeLong(((CharacterFileRecord)object).godWarsLastAltarBlessingMillis);
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).configStates[GodWarsDungeonManager.ropeShortcutConfigId]);
            n8 = 0;
            while (n8 < ((CharacterFileRecord)object).godWarsKillCounts.length) {
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).godWarsKillCounts[n8]);
                ++n8;
            }
            ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).craftingThreadUseCount);
            ((DataOutputStream)object2).writeLong(((CharacterFileRecord)object).membershipExpiresMillis);
            ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).dx);
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).configStates[33]);
            ((DataOutputStream)object2).writeShort(((CharacterFileRecord)object).savedCacheVersion);
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).godBookPageFlags);
            ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object).swampCaveRopeAttached);
            ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object).lampOilStillFilled);
            ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).enterTheAbyssMiniquestState);
            ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object).botEnabled);
            if (((CharacterFileRecord)object).botEnabled) {
                ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).botMode);
                ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).currentBotTaskTypeId);
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).currentBotTaskIndex);
                ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).deferredBotTaskTypeId);
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).deferredBotTaskIndex);
                ((DataOutputStream)object2).writeUTF(((CharacterFileRecord)object).botTaskState);
                n8 = 0;
                if (((CharacterFileRecord)object).botTaskRequiredItems != null) {
                    n8 = ((CharacterFileRecord)object).botTaskRequiredItems.length;
                }
                ((DataOutputStream)object2).writeByte(n8);
                int n9 = 0;
                while (n9 < n8) {
                    ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).botTaskRequiredItems[n9].getId());
                    ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).botTaskRequiredItems[n9].getAmount());
                    ++n9;
                }
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).botFoodItemId);
                ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).botPathSegmentIndex);
                ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).botPathWaypointIndex);
                ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object).savedWorldRouteReversed);
                ((DataOutputStream)object2).writeLong(((CharacterFileRecord)object).botTaskSavedElapsedMillis);
                ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).botTaskDurationMinutes);
                ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).savedWorldRouteIndex);
                ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).tradeAdvertMode);
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).botAdvertItemId);
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).tradeAdvertQuantityRemaining);
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).tradeAdvertUnitPrice);
                ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object).tradeAdvertScam);
                ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object).tradeAdvertVariableQuantity);
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).tradeAdvertLastOfferAmount);
                ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).botShopBuyMode);
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).botTaskItemId);
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).botShopItemAmount);
                ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).botShopSellItemIds.size());
                n9 = 0;
                while (n9 < ((CharacterFileRecord)object).botShopSellItemIds.size()) {
                    ((DataOutputStream)object2).writeInt((Integer)((CharacterFileRecord)object).botShopSellItemIds.get(n9));
                    ++n9;
                }
                ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).botCombatLoadoutItemIds.size());
                n9 = 0;
                while (n9 < ((CharacterFileRecord)object).botCombatLoadoutItemIds.size()) {
                    ((DataOutputStream)object2).writeInt((Integer)((CharacterFileRecord)object).botCombatLoadoutItemIds.get(n9));
                    ++n9;
                }
                ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).botCombatStyle);
                ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).botSkillTargetSkillId);
                ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).botSkillTargetLevel);
                ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).cN);
                ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).cM);
                ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).cL);
                ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).cK);
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).botCompletionItemId);
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).botCompletionItemAmount);
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).cH);
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).cG);
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).cF);
                ((DataOutputStream)object2).writeInt(((CharacterFileRecord)object).cE);
                ((DataOutputStream)object2).writeBoolean(((CharacterFileRecord)object).botTaskReturnToBankRequested);
                ((DataOutputStream)object2).writeBoolean(false);
                ((DataOutputStream)object2).writeBoolean(false);
                ((DataOutputStream)object2).writeBoolean(false);
                ((DataOutputStream)object2).writeBoolean(false);
                ((DataOutputStream)object2).writeBoolean(false);
                ((DataOutputStream)object2).writeByte(((CharacterFileRecord)object).botElementalSpellIndex);
                ((DataOutputStream)object2).writeByte(0);
                ((DataOutputStream)object2).writeByte(0);
                ((DataOutputStream)object2).writeByte(0);
                ((DataOutputStream)object2).writeByte(0);
                ((DataOutputStream)object2).writeInt(0);
                ((DataOutputStream)object2).writeInt(0);
                ((DataOutputStream)object2).writeInt(0);
                ((DataOutputStream)object2).writeInt(0);
                ((DataOutputStream)object2).writeInt(0);
                ((DataOutputStream)object2).writeUTF("");
                ((DataOutputStream)object2).writeUTF("");
                ((DataOutputStream)object2).writeUTF("");
                ((DataOutputStream)object2).writeUTF("");
                ((DataOutputStream)object2).writeUTF("");
            }
            ((DataOutputStream)object2).flush();
            ((FilterOutputStream)object2).close();
            return;
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            return;
        }
    }

    public static void loadPlayer(Player player) {
        if (ServerSettings.mysqlPlayerSaveEnabled) {
            Object object = player.getSocketChannel().socket().getInetAddress().getHostAddress();
            if ((object = loginIpReservations.putIfAbsent(object, new LoginIpReservation())) != null) {
                // empty if block
            }
            object = new PlayerUidLookupQuery("SELECT uid FROM `prs06_users` WHERE username = ?", player);
            DatabaseService.getInstance().submit((DatabaseQuery)object, new PlayerLoginLoadCallback(player));
            return;
        }
        CharacterFileManager.loadPlayerFromFile("./data/characters/", player);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void saveAllPlayers() {
        Player[] playerArray = World.getPlayers();
        synchronized (playerArray) {
            Object object = World.getPlayers();
            Player[] playerArray2 = object;
            int n = ((Player[])object).length;
            int n2 = 0;
            while (n2 < n) {
                object = playerArray2[n2];
                if (object != null && ((Entity)object).getIndex() != -1) {
                    try {
                        CharacterFileManager.savePlayer((Player)object);
                    }
                    catch (Exception exception) {
                        object = exception;
                        exception.printStackTrace();
                    }
                }
                ++n2;
            }
            return;
        }
    }

    private static void restorePlayerFromBackup(Player player) {
        Object object = new File("./data/backups/");
        object = ((File)object).listFiles();
        Arrays.sort(object, new BackupRestoreTimestampComparator());
        File[] fileArray = object;
        int n = ((File[])object).length;
        int n2 = 0;
        while (n2 < n) {
            object = fileArray[n2];
            File file = new File(String.valueOf(((File)object).getPath()) + "/characters/" + player.getUsername() + ".dat");
            if (file.exists() && CharacterFileManager.validateCharacterFile(String.valueOf(((File)object).getPath()) + "/characters/", player.getUsername())) {
                try {
                    CharacterFileManager.loadPlayerFromFile(String.valueOf(((File)object).getPath()) + "/characters/", player);
                    System.out.println("Restoring from backup: " + player);
                    return;
                }
                catch (Exception exception) {
                    object = exception;
                    exception.printStackTrace();
                }
            }
            ++n2;
        }
    }

    public static String stripFileExtension(String string) {
        int n = string.lastIndexOf(".");
        if (n > 0) {
            string = string.substring(0, n);
        }
        return string;
    }

    private static void repairBackupCharacterFiles() {
        Object object = new File("./data/backups/");
        object = ((File)object).listFiles();
        ArrayList<Object> arrayList = new ArrayList<Object>();
        Arrays.sort(object, new BackupRepairTimestampComparator());
        File[] fileArray = object;
        int n = ((File[])object).length;
        int n2 = 0;
        while (n2 < n) {
            object = fileArray[n2];
            object = new File(String.valueOf(((File)object).getPath()) + "/characters");
            Object object2 = ((File)object).listFiles();
            File[] fileArray2 = object2;
            int n3 = ((File[])object2).length;
            int n4 = 0;
            while (n4 < n3) {
                object2 = fileArray2[n4];
                String string = CharacterFileManager.stripFileExtension(((File)object2).getName());
                if (!CharacterFileManager.validateCharacterFile(String.valueOf(((File)object).getPath()) + "/", string)) {
                    System.out.println("corrupted file found: " + ((File)object2).getPath());
                    arrayList.add(object2);
                } else if (!arrayList.isEmpty()) {
                    File file = null;
                    for (File file2 : arrayList) {
                        String string2 = CharacterFileManager.stripFileExtension(file2.getName());
                        if (!string2.equals(string)) continue;
                        System.out.println("valid file found: " + ((File)object2).getPath());
                        file = file2;
                        try {
                            file2.delete();
                            CharacterFileManager.copyFile((File)object2, file2);
                        }
                        catch (IOException iOException) {
                            IOException iOException2 = iOException;
                            iOException.printStackTrace();
                        }
                    }
                    if (file != null) {
                        arrayList.remove(file);
                    }
                }
                ++n4;
            }
            if (arrayList.isEmpty()) {
                return;
            }
            ++n2;
        }
    }

    public static void createTimestampedBackup() {
        File file;
        File file2;
        long l;
        long l2 = l = System.currentTimeMillis();
        Object object = new DateTime(l2);
        object = String.valueOf(((AbstractDateTime)object).getHourOfDay()) + "-" + ((AbstractDateTime)object).getMinuteOfHour();
        object = String.valueOf(GameplayHelper.a(l)) + "-" + (String)object;
        Object object2 = new File("./data/characters/");
        object2 = ((File)object2).listFiles();
        File[] fileArray = object2;
        int n = ((File[])object2).length;
        int n2 = 0;
        while (n2 < n) {
            object2 = fileArray[n2];
            if (((File)object2).isFile()) {
                file2 = new File("./data/backups/" + (String)object + "/characters");
                file = new File("./data/backups/" + (String)object + "/characters/" + ((File)object2).getName());
                try {
                    file2.mkdirs();
                    CharacterFileManager.copyFile((File)object2, file);
                }
                catch (IOException iOException) {
                    object2 = iOException;
                    iOException.printStackTrace();
                }
            }
            ++n2;
        }
        CharacterFileManager.repairBackupCharacterFiles();
        object2 = new File("./data/logs/");
        object2 = ((File)object2).listFiles();
        fileArray = object2;
        n = ((File[])object2).length;
        n2 = 0;
        while (n2 < n) {
            object2 = fileArray[n2];
            if (((File)object2).isFile()) {
                file2 = new File("./data/backups/" + (String)object + "/logs");
                file = new File("./data/backups/" + (String)object + "/logs/" + ((File)object2).getName());
                try {
                    file2.mkdirs();
                    CharacterFileManager.copyFile((File)object2, file);
                    ((File)object2).delete();
                }
                catch (IOException iOException) {
                    object2 = iOException;
                    iOException.printStackTrace();
                }
            }
            ++n2;
        }
    }

    private static void copyFile(File file, File file2) {
        Files.copy(file.toPath(), file2.toPath(), new CopyOption[0]);
    }

    /*
     * Enabled aggressive exception aggregation
     */
    private static boolean validateCharacterFile(String object, String object2) {
        if (!((File)(object = new File(String.valueOf(object) + (String)object2 + ".dat"))).exists()) {
            return false;
        }
        try {
            block150: {
                object = new FileInputStream((File)object);
                object2 = new DataInputStream((InputStream)object);
                try {
                    int n;
                    int n2;
                    int n3;
                    int n4;
                    short s = ((DataInputStream)object2).readShort();
                    if (s < 24) {
                        ((FilterInputStream)object2).close();
                        return false;
                    }
                    ((DataInputStream)object2).readUTF();
                    ((DataInputStream)object2).readUTF();
                    ((DataInputStream)object2).readUTF();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readUTF();
                    ((DataInputStream)object2).readUTF();
                    ((DataInputStream)object2).readUTF();
                    ((DataInputStream)object2).readLong();
                    ((DataInputStream)object2).readLong();
                    ((DataInputStream)object2).readLong();
                    ((DataInputStream)object2).readBoolean();
                    ((DataInputStream)object2).readBoolean();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readBoolean();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readDouble();
                    ((DataInputStream)object2).readBoolean();
                    ((DataInputStream)object2).readBoolean();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    ((DataInputStream)object2).readInt();
                    if (s <= 26) {
                        ((DataInputStream)object2).readDouble();
                    } else {
                        ((DataInputStream)object2).readUnsignedShort();
                    }
                    ((DataInputStream)object2).readBoolean();
                    int n5 = 0;
                    while (n5 < 4) {
                        ((DataInputStream)object2).readInt();
                        ++n5;
                    }
                    n5 = 0;
                    while (n5 < 4) {
                        ((DataInputStream)object2).readInt();
                        ++n5;
                    }
                    n5 = 0;
                    while (n5 < 4) {
                        ((DataInputStream)object2).readInt();
                        ++n5;
                    }
                    n5 = 0;
                    while (n5 < 7) {
                        ((DataInputStream)object2).readInt();
                        ++n5;
                    }
                    n5 = 0;
                    while (n5 < 5) {
                        ((DataInputStream)object2).readInt();
                        ++n5;
                    }
                    n5 = 0;
                    while (n5 < 22) {
                        ((DataInputStream)object2).readInt();
                        ++n5;
                    }
                    n5 = 0;
                    while (n5 < 22) {
                        ((DataInputStream)object2).readInt();
                        ++n5;
                    }
                    n5 = 0;
                    while (n5 < 28) {
                        n4 = ((DataInputStream)object2).readInt();
                        if (n4 != 65535) {
                            ((DataInputStream)object2).readInt();
                            ((DataInputStream)object2).readInt();
                        }
                        ++n5;
                    }
                    n5 = 0;
                    while (n5 < 14) {
                        n4 = ((DataInputStream)object2).readInt();
                        if (n4 != 65535) {
                            ((DataInputStream)object2).readInt();
                            if (s >= 23) {
                                ((DataInputStream)object2).readInt();
                            }
                        }
                        ++n5;
                    }
                    try {
                        if (s < 17) {
                            n5 = 0;
                            while (n5 < 352) {
                                n4 = ((DataInputStream)object2).readInt();
                                if (n4 != 65535) {
                                    ((DataInputStream)object2).readInt();
                                    ((DataInputStream)object2).readInt();
                                }
                                ++n5;
                            }
                        } else {
                            n5 = ((DataInputStream)object2).readByte();
                            n4 = 0;
                            while (n4 < n5) {
                                n3 = ((DataInputStream)object2).readUnsignedShort();
                                n2 = 0;
                                while (n2 < n3) {
                                    n = ((DataInputStream)object2).readInt();
                                    if (n != 65535) {
                                        ((DataInputStream)object2).readInt();
                                        ((DataInputStream)object2).readInt();
                                    }
                                    ++n2;
                                }
                                ++n4;
                            }
                        }
                        n5 = 0;
                        while (n5 < 200) {
                            ((DataInputStream)object2).readLong();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 100) {
                            ((DataInputStream)object2).readLong();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 28) {
                            ((DataInputStream)object2).readInt();
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        ((DataInputStream)object2).readInt();
                        ((DataInputStream)object2).readLong();
                        ((DataInputStream)object2).readLong();
                        n5 = 0;
                        while (n5 < 6) {
                            ((DataInputStream)object2).readBoolean();
                            ++n5;
                        }
                        ((DataInputStream)object2).readInt();
                        ((DataInputStream)object2).readInt();
                        ((DataInputStream)object2).readInt();
                        ((DataInputStream)object2).readInt();
                        ((DataInputStream)object2).readInt();
                        ((DataInputStream)object2).readDouble();
                        n5 = 0;
                        while (n5 < 8) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 8) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 8) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 8) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 8) {
                            ((DataInputStream)object2).readLong();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 8) {
                            ((DataInputStream)object2).readDouble();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 8) {
                            ((DataInputStream)object2).readBoolean();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readLong();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readDouble();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readBoolean();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readLong();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readDouble();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readLong();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readDouble();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readBoolean();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readLong();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readDouble();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readLong();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readDouble();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readBoolean();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readLong();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readDouble();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readLong();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readDouble();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readLong();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readDouble();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readBoolean();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readLong();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 18) {
                            ((DataInputStream)object2).readInt();
                            ++n5;
                        }
                    }
                    catch (IOException iOException) {
                        IOException iOException2 = iOException;
                        iOException.printStackTrace();
                    }
                    try {
                        ((DataInputStream)object2).readInt();
                        ((DataInputStream)object2).readUTF();
                        ((DataInputStream)object2).readInt();
                    }
                    catch (IOException iOException) {
                        IOException iOException3 = iOException;
                        iOException.printStackTrace();
                    }
                    try {
                        ((DataInputStream)object2).readBoolean();
                    }
                    catch (IOException iOException) {
                        IOException iOException4 = iOException;
                        iOException.printStackTrace();
                    }
                    try {
                        ((DataInputStream)object2).readBoolean();
                    }
                    catch (IOException iOException) {
                        IOException iOException5 = iOException;
                        iOException.printStackTrace();
                    }
                    try {
                        ((DataInputStream)object2).readBoolean();
                    }
                    catch (IOException iOException) {
                        IOException iOException6 = iOException;
                        iOException.printStackTrace();
                    }
                    int n6 = 0;
                    while (n6 < musicUnlockConfigIds.length) {
                        try {
                            ((DataInputStream)object2).readInt();
                        }
                        catch (IOException iOException) {
                            IOException iOException7 = iOException;
                            iOException.printStackTrace();
                        }
                        ++n6;
                    }
                    n6 = 0;
                    while (n6 < QuestDefinition.questStateCapacity) {
                        try {
                            ((DataInputStream)object2).readInt();
                        }
                        catch (IOException iOException) {
                            IOException iOException8 = iOException;
                            iOException.printStackTrace();
                        }
                        ++n6;
                    }
                    ((DataInputStream)object2).readByte();
                    ((DataInputStream)object2).readByte();
                    ((DataInputStream)object2).readBoolean();
                    ((DataInputStream)object2).readByte();
                    ((DataInputStream)object2).readByte();
                    ((DataInputStream)object2).readBoolean();
                    if (s < 2) break block150;
                    ((DataInputStream)object2).readByte();
                    if (s >= 3) {
                        ((DataInputStream)object2).readInt();
                    }
                    if (s >= 4) {
                        ((DataInputStream)object2).readBoolean();
                        ((DataInputStream)object2).readBoolean();
                        ((DataInputStream)object2).readInt();
                    }
                    if (s >= 5) {
                        ((DataInputStream)object2).readByte();
                        ((DataInputStream)object2).readInt();
                    }
                    if (s >= 6) {
                        ((DataInputStream)object2).readInt();
                    }
                    if (s >= 7) {
                        n6 = 0;
                        while (n6 < QuestDefinition.questStateCapacity) {
                            try {
                                ((DataInputStream)object2).readInt();
                            }
                            catch (IOException iOException) {
                                IOException iOException9 = iOException;
                                iOException.printStackTrace();
                            }
                            ++n6;
                        }
                        n6 = 0;
                        while (n6 < 100) {
                            try {
                                ((DataInputStream)object2).readInt();
                            }
                            catch (IOException iOException) {
                                IOException iOException10 = iOException;
                                iOException.printStackTrace();
                            }
                            ++n6;
                        }
                    }
                    if (s >= 8) {
                        ((DataInputStream)object2).readByte();
                        ((DataInputStream)object2).readByte();
                        ((DataInputStream)object2).readByte();
                    }
                    if (s >= 9) {
                        ((DataInputStream)object2).readInt();
                    }
                    if (s >= 10) {
                        ((DataInputStream)object2).readLong();
                        ((DataInputStream)object2).readInt();
                        ((DataInputStream)object2).readInt();
                    }
                    if (s >= 11) {
                        ((DataInputStream)object2).readUTF();
                    }
                    if (s >= 12) {
                        ((DataInputStream)object2).readInt();
                    }
                    if (s >= 13) {
                        ((DataInputStream)object2).readInt();
                        ((DataInputStream)object2).readInt();
                        ((DataInputStream)object2).readInt();
                        ((DataInputStream)object2).readByte();
                    }
                    if (s >= 14) {
                        n6 = 0;
                        while (n6 < 6) {
                            ((DataInputStream)object2).readBoolean();
                            ++n6;
                        }
                        n6 = 0;
                        while (n6 < 6) {
                            ((DataInputStream)object2).readInt();
                            ++n6;
                        }
                        n6 = 0;
                        while (n6 < 6) {
                            ((DataInputStream)object2).readInt();
                            ++n6;
                        }
                        n6 = 0;
                        while (n6 < 6) {
                            ((DataInputStream)object2).readInt();
                            ++n6;
                        }
                        n6 = 0;
                        while (n6 < 6) {
                            ((DataInputStream)object2).readBoolean();
                            ++n6;
                        }
                        n6 = 0;
                        while (n6 < 6) {
                            ((DataInputStream)object2).readInt();
                            ++n6;
                        }
                        n6 = 0;
                        while (n6 < 6) {
                            ((DataInputStream)object2).readInt();
                            ++n6;
                        }
                        n6 = 0;
                        while (n6 < 6) {
                            ((DataInputStream)object2).readInt();
                            ++n6;
                        }
                        n6 = 0;
                        while (n6 < 6) {
                            ((DataInputStream)object2).readInt();
                            ++n6;
                        }
                        n6 = 0;
                        while (n6 < 6) {
                            ((DataInputStream)object2).readBoolean();
                            ++n6;
                        }
                    }
                    if (s >= 15) {
                        ((DataInputStream)object2).readInt();
                        ((DataInputStream)object2).readInt();
                        ((DataInputStream)object2).readInt();
                        ((DataInputStream)object2).readInt();
                        ((DataInputStream)object2).readBoolean();
                        ((DataInputStream)object2).readByte();
                        ((DataInputStream)object2).readBoolean();
                        ((DataInputStream)object2).readByte();
                    }
                    if (s >= 16) {
                        ((DataInputStream)object2).readInt();
                    }
                    if (s >= 18) {
                        ((DataInputStream)object2).readByte();
                    }
                    if (s >= 19) {
                        ((DataInputStream)object2).readInt();
                    }
                    if (s >= 21) {
                        ((DataInputStream)object2).readLong();
                        ((DataInputStream)object2).readInt();
                        n6 = 0;
                        while (n6 < 4) {
                            ((DataInputStream)object2).readInt();
                            ++n6;
                        }
                    }
                    if (s >= 22) {
                        ((DataInputStream)object2).readByte();
                    }
                    if (s >= 24) {
                        ((DataInputStream)object2).readLong();
                        ((DataInputStream)object2).readByte();
                    }
                    if (s >= 25) {
                        ((DataInputStream)object2).readInt();
                    }
                    if (s >= 26) {
                        ((DataInputStream)object2).readUnsignedShort();
                    }
                    if (s >= 29) {
                        ((DataInputStream)object2).readInt();
                    }
                    if (s >= 30) {
                        ((DataInputStream)object2).readBoolean();
                        ((DataInputStream)object2).readBoolean();
                        ((DataInputStream)object2).readInt();
                    }
                    if (s >= 20) {
                        boolean bl = ((DataInputStream)object2).readBoolean();
                        n6 = bl ? 1 : 0;
                        if (bl) {
                            ((DataInputStream)object2).readByte();
                            ((DataInputStream)object2).readByte();
                            ((DataInputStream)object2).readInt();
                            ((DataInputStream)object2).readByte();
                            ((DataInputStream)object2).readInt();
                            ((DataInputStream)object2).readUTF();
                            byte by = ((DataInputStream)object2).readByte();
                            n3 = 0;
                            while (n3 < by) {
                                ((DataInputStream)object2).readInt();
                                ((DataInputStream)object2).readInt();
                                ++n3;
                            }
                            ((DataInputStream)object2).readInt();
                            ((DataInputStream)object2).readByte();
                            ((DataInputStream)object2).readByte();
                            ((DataInputStream)object2).readBoolean();
                            ((DataInputStream)object2).readLong();
                            ((DataInputStream)object2).readByte();
                            ((DataInputStream)object2).readByte();
                            ((DataInputStream)object2).readByte();
                            ((DataInputStream)object2).readInt();
                            ((DataInputStream)object2).readInt();
                            ((DataInputStream)object2).readInt();
                            ((DataInputStream)object2).readBoolean();
                            ((DataInputStream)object2).readBoolean();
                            ((DataInputStream)object2).readInt();
                            ((DataInputStream)object2).readByte();
                            ((DataInputStream)object2).readInt();
                            ((DataInputStream)object2).readInt();
                            n3 = ((DataInputStream)object2).readByte();
                            n2 = 0;
                            while (n2 < n3) {
                                ((DataInputStream)object2).readInt();
                                ++n2;
                            }
                            n2 = ((DataInputStream)object2).readByte();
                            n = 0;
                            while (n < n2) {
                                ((DataInputStream)object2).readInt();
                                ++n;
                            }
                            ((DataInputStream)object2).readByte();
                            ((DataInputStream)object2).readByte();
                            ((DataInputStream)object2).readByte();
                            ((DataInputStream)object2).readByte();
                            ((DataInputStream)object2).readByte();
                            ((DataInputStream)object2).readByte();
                            ((DataInputStream)object2).readByte();
                            ((DataInputStream)object2).readInt();
                            ((DataInputStream)object2).readInt();
                            ((DataInputStream)object2).readInt();
                            ((DataInputStream)object2).readInt();
                            ((DataInputStream)object2).readInt();
                            ((DataInputStream)object2).readInt();
                            ((DataInputStream)object2).readBoolean();
                            ((DataInputStream)object2).readBoolean();
                            ((DataInputStream)object2).readBoolean();
                            ((DataInputStream)object2).readBoolean();
                            ((DataInputStream)object2).readBoolean();
                            ((DataInputStream)object2).readBoolean();
                            ((DataInputStream)object2).readByte();
                            ((DataInputStream)object2).readByte();
                            ((DataInputStream)object2).readByte();
                            ((DataInputStream)object2).readByte();
                            ((DataInputStream)object2).readByte();
                            ((DataInputStream)object2).readInt();
                            ((DataInputStream)object2).readInt();
                            ((DataInputStream)object2).readInt();
                            ((DataInputStream)object2).readInt();
                            ((DataInputStream)object2).readInt();
                            ((DataInputStream)object2).readUTF();
                            ((DataInputStream)object2).readUTF();
                            ((DataInputStream)object2).readUTF();
                            ((DataInputStream)object2).readUTF();
                            ((DataInputStream)object2).readUTF();
                        }
                    }
                }
                catch (Exception exception) {
                    Exception exception2 = exception;
                    exception.printStackTrace();
                    ((FilterInputStream)object2).close();
                    ((FileInputStream)object).close();
                    return false;
                }
            }
            ((FilterInputStream)object2).close();
            ((FileInputStream)object).close();
            return true;
        }
        catch (IOException iOException) {
            object = iOException;
            iOException.printStackTrace();
            return false;
        }
    }

    public static void archiveDeadHardcoreIronman(Player player) {
        Object object2;
        int n = 0;
        for (Object object2 : liveHiscoreRecords) {
            CharacterFileRecord characterFileRecord;
            object2 = object2;
            if (characterFileRecord.username.toLowerCase().equals(player.getUsername().toLowerCase())) {
                object2 = CharacterFileManager.readCharacterFileRecord("./data/characters/", player.getUsername(), true);
                if (object2 == null) continue;
                liveHiscoreRecords.set(n, object2);
                continue;
            }
            ++n;
        }
        object2 = FileUtil.readBytes("./data/characters/" + player.getUsername() + ".dat");
        FileUtil.writeBytes("./data/dead hcim characters/" + player.getUsername() + ".dat", (byte[])object2);
        CharacterFileRecord characterFileRecord = CharacterFileManager.readCharacterFileRecord("./data/dead hcim characters/", player.getUsername(), true);
        if (characterFileRecord != null) {
            deadHardcoreIronmanRecords.add(characterFileRecord);
        }
    }

    public static void refreshLiveHiscoreRecord(Player object) {
        CharacterFileRecord characterFileRecord2;
        if (((Player)object).isBot && ((Player)object).botMode != 4) {
            return;
        }
        int n = 0;
        for (CharacterFileRecord characterFileRecord2 : liveHiscoreRecords) {
            CharacterFileRecord characterFileRecord3;
            characterFileRecord2 = characterFileRecord2;
            if (characterFileRecord3.username.toLowerCase().equals(((Player)object).getUsername().toLowerCase())) {
                if ((object = CharacterFileManager.readCharacterFileRecord("./data/characters/", ((Player)object).getUsername(), true)) != null) {
                    liveHiscoreRecords.set(n, object);
                }
                return;
            }
            ++n;
        }
        characterFileRecord2 = CharacterFileManager.readCharacterFileRecord("./data/characters/", ((Player)object).getUsername(), true);
        if (characterFileRecord2 != null) {
            liveHiscoreRecords.add(characterFileRecord2);
            Server.d();
        }
    }

    /*
     * Enabled aggressive exception aggregation
     */
    private static CharacterFileRecord readCharacterFileRecord(String object, String object2, boolean bl) {
        if (!((File)(object = new File(String.valueOf(object) + (String)object2 + ".dat"))).exists()) {
            return null;
        }
        object2 = new CharacterFileRecord();
        try {
            DataInputStream dataInputStream;
            block157: {
                object = new FileInputStream((File)object);
                dataInputStream = new DataInputStream((InputStream)object);
                try {
                    int n;
                    int n2;
                    int n3;
                    short s = dataInputStream.readShort();
                    if (s < 24) {
                        dataInputStream.close();
                        return null;
                    }
                    String string = dataInputStream.readUTF();
                    Object object3 = object2;
                    ((CharacterFileRecord)object2).username = string;
                    string = dataInputStream.readUTF();
                    object3 = object2;
                    ((CharacterFileRecord)object2).password = string;
                    string = dataInputStream.readUTF();
                    object3 = object2;
                    ((CharacterFileRecord)object2).hostAddress = string;
                    dataInputStream.readInt();
                    boolean bl2 = false;
                    object3 = object2;
                    ((CharacterFileRecord)object2).playerRights = 0;
                    String string2 = dataInputStream.readUTF();
                    object3 = object2;
                    ((CharacterFileRecord)object2).legacyProfileString = string2;
                    string2 = dataInputStream.readUTF();
                    object3 = object2;
                    ((CharacterFileRecord)object2).profileString1 = string2;
                    string2 = dataInputStream.readUTF();
                    object3 = object2;
                    ((CharacterFileRecord)object2).profileString2 = string2;
                    long l = dataInputStream.readLong();
                    object3 = object2;
                    ((CharacterFileRecord)object2).lastSavedMillis = l;
                    l = dataInputStream.readLong();
                    object3 = object2;
                    ((CharacterFileRecord)object2).totalPlayTimeMillis = l;
                    l = dataInputStream.readLong();
                    object3 = object2;
                    ((CharacterFileRecord)object2).createdAtMillis = l;
                    int n4 = dataInputStream.readBoolean();
                    object3 = object2;
                    ((CharacterFileRecord)object2).loginRestrictionExempt = n4;
                    n4 = dataInputStream.readBoolean();
                    object3 = object2;
                    ((CharacterFileRecord)object2).memberFlag = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).donatorPoints = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).x = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).y = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).plane = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).gender = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).npcKillCount = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).playerKillCount = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).deathCount = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).easyCluesCompleted = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).mediumCluesCompleted = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).hardCluesCompleted = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).soldItemsValue = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).boughtItemsValue = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).duelWins = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).duelLosses = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).legacyQuestPoints = n4;
                    n4 = dataInputStream.readBoolean() ? 1 : 0;
                    object3 = object2;
                    ((CharacterFileRecord)object2).autoRetaliate = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).fightMode = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).brightness = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).mouseButtons = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).publicChatEffects = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).splitPrivateChat = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).acceptAid = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).musicVolume = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).effectVolume = n4;
                    n4 = (int)dataInputStream.readDouble();
                    object3 = object2;
                    ((CharacterFileRecord)object2).specialEnergy = n4;
                    n4 = dataInputStream.readBoolean() ? 1 : 0;
                    object3 = object2;
                    ((CharacterFileRecord)object2).changingBankPin = n4;
                    n4 = dataInputStream.readBoolean() ? 1 : 0;
                    object3 = object2;
                    ((CharacterFileRecord)object2).deletingBankPin = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).pinAppendYear = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).pinAppendDate = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).bindingNecklaceCharge = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).ringOfForgingLife = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).ringOfRecoilLife = n4;
                    n4 = dataInputStream.readInt();
                    object3 = object2;
                    ((CharacterFileRecord)object2).skullTimer = n4;
                    if (s <= 26) {
                        double d = dataInputStream.readDouble();
                        n4 = (int)d;
                        object3 = object2;
                        double d2 = n4;
                        double d3 = (d2 /= 100.0) * 10000.0;
                        ((CharacterFileRecord)v0).runEnergyRaw = n3 = (int)d3;
                    } else {
                        n3 = dataInputStream.readUnsignedShort();
                        object3 = object2;
                        ((CharacterFileRecord)object2).runEnergyRaw = n3;
                    }
                    n4 = dataInputStream.readBoolean() ? 1 : 0;
                    object3 = object2;
                    ((CharacterFileRecord)object2).running = n4;
                    int n5 = 0;
                    while (n5 < 4) {
                        ((CharacterFileRecord)object2).currentPin[n5] = dataInputStream.readInt();
                        ++n5;
                    }
                    n5 = 0;
                    while (n5 < 4) {
                        ((CharacterFileRecord)object2).pendingPin[n5] = dataInputStream.readInt();
                        ++n5;
                    }
                    n5 = 0;
                    while (n5 < 4) {
                        ((CharacterFileRecord)object2).essencePouchAmounts[n5] = dataInputStream.readInt();
                        ++n5;
                    }
                    n5 = 0;
                    while (n5 < 7) {
                        ((CharacterFileRecord)object2).appearanceParts[n5] = dataInputStream.readInt();
                        ++n5;
                    }
                    n5 = 0;
                    while (n5 < 5) {
                        ((CharacterFileRecord)object2).appearanceColors[n5] = dataInputStream.readInt();
                        ++n5;
                    }
                    n5 = 0;
                    while (n5 < 22) {
                        int n6 = dataInputStream.readInt();
                        n4 = n5++;
                        object3 = object2;
                        ((CharacterFileRecord)object3).currentLevels[n4] = n6;
                    }
                    n5 = 0;
                    while (n5 < 22) {
                        int n7 = dataInputStream.readInt();
                        n4 = n5++;
                        object3 = object2;
                        ((CharacterFileRecord)object3).skillExperience[n4] = n7;
                    }
                    n5 = 0;
                    while (n5 < 28) {
                        n2 = dataInputStream.readInt();
                        if (n2 != 65535) {
                            int n8 = dataInputStream.readInt();
                            n3 = dataInputStream.readInt();
                            ((CharacterFileRecord)object2).inventoryItems[n5] = new ItemStack(n2, n8, n3);
                        }
                        ++n5;
                    }
                    n5 = 0;
                    while (n5 < 14) {
                        n2 = dataInputStream.readInt();
                        if (n2 != 65535) {
                            int n9 = dataInputStream.readInt();
                            n3 = -1;
                            if (s >= 23) {
                                n3 = dataInputStream.readInt();
                            }
                            ((CharacterFileRecord)object2).equipmentItems[n5] = new ItemStack(n2, n9, n3);
                        }
                        ++n5;
                    }
                    try {
                        if (s < 17) {
                            n5 = 0;
                            while (n5 < 352) {
                                int n10 = dataInputStream.readInt();
                                if (n10 != 65535) {
                                    n3 = dataInputStream.readInt();
                                    n = dataInputStream.readInt();
                                    ItemStack itemStack = new ItemStack(n10, n3, n);
                                    ((CharacterFileRecord)object2).setBankTabItem(n5, itemStack, 0);
                                }
                                ++n5;
                            }
                        } else {
                            n5 = dataInputStream.readByte();
                            n2 = 0;
                            while (n2 < n5) {
                                int n11 = dataInputStream.readUnsignedShort();
                                n3 = 0;
                                while (n3 < n11) {
                                    int n12 = dataInputStream.readInt();
                                    if (n12 != 65535) {
                                        int n13 = dataInputStream.readInt();
                                        int n14 = dataInputStream.readInt();
                                        ItemStack itemStack = new ItemStack(n12, n13, n14);
                                        ((CharacterFileRecord)object2).setBankTabItem(n3, itemStack, n2);
                                    }
                                    ++n3;
                                }
                                ++n2;
                            }
                        }
                        n5 = 0;
                        while (n5 < 200) {
                            ((CharacterFileRecord)object2).friendsList[n5] = dataInputStream.readLong();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 100) {
                            ((CharacterFileRecord)object2).ignoreList[n5] = dataInputStream.readLong();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 28) {
                            ((CharacterFileRecord)object2).queuedLoginItemIds[n5] = dataInputStream.readInt();
                            ((CharacterFileRecord)object2).queuedLoginItemAmounts[n5] = dataInputStream.readInt();
                            ++n5;
                        }
                        n4 = dataInputStream.readInt();
                        Object object4 = object2;
                        ((CharacterFileRecord)object2).abyssMageNpcId = n4;
                        long l2 = dataInputStream.readLong();
                        object4 = object2;
                        ((CharacterFileRecord)object2).muteExpires = l2;
                        l2 = dataInputStream.readLong();
                        object4 = object2;
                        ((CharacterFileRecord)object2).banExpires = l2;
                        n5 = 0;
                        while (n5 < 6) {
                            ((CharacterFileRecord)object2).barrowsKilledBrothers[n5] = dataInputStream.readBoolean();
                            ++n5;
                        }
                        int n15 = dataInputStream.readInt();
                        object4 = object2;
                        ((CharacterFileRecord)object2).barrowsKillCount = n15;
                        n15 = dataInputStream.readInt();
                        object4 = object2;
                        ((CharacterFileRecord)object2).barrowsTargetBrotherIndex = n15;
                        n15 = n5 = dataInputStream.readInt();
                        object4 = object2;
                        ((CharacterFileRecord)object2).poisonImmunityTicks = n15;
                        n15 = n2 = dataInputStream.readInt();
                        object4 = object2;
                        ((CharacterFileRecord)object2).antifireTicks = n15;
                        n15 = dataInputStream.readInt();
                        object4 = object2;
                        ((CharacterFileRecord)object2).teleblockTicks = n15;
                        double d = dataInputStream.readDouble();
                        object4 = object2;
                        ((CharacterFileRecord)object2).poisonDamage = d;
                        int n16 = 0;
                        while (n16 < 8) {
                            ((CharacterFileRecord)object2).allotmentGrowthStages[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 8) {
                            ((CharacterFileRecord)object2).allotmentCropIds[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 8) {
                            if (s <= 27) {
                                dataInputStream.readInt();
                                ((CharacterFileRecord)object2).allotmentHarvestAmounts[n16] = 4;
                            } else {
                                ((CharacterFileRecord)object2).allotmentHarvestAmounts[n16] = dataInputStream.readInt();
                            }
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 8) {
                            ((CharacterFileRecord)object2).allotmentPatchStates[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 8) {
                            ((CharacterFileRecord)object2).allotmentLastUpdateTicks[n16] = dataInputStream.readLong();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 8) {
                            ((CharacterFileRecord)object2).allotmentDiseaseChanceMultipliers[n16] = dataInputStream.readDouble();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 8) {
                            ((CharacterFileRecord)object2).allotmentProtectionFlags[n16] = dataInputStream.readBoolean();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).bushGrowthStages[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).bushCropIds[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).bushPatchStates[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).bushLastUpdateTicks[n16] = dataInputStream.readLong();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).bushDiseaseChanceMultipliers[n16] = dataInputStream.readDouble();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).bushSavedFlags[n16] = dataInputStream.readBoolean();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).flowerGrowthStages[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).flowerCropIds[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).flowerPatchStates[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).flowerLastUpdateTicks[n16] = dataInputStream.readLong();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).flowerDiseaseChanceMultipliers[n16] = dataInputStream.readDouble();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).fruitTreeGrowthStages[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).fruitTreeIds[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).fruitTreePatchStates[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).fruitTreeLastUpdateTicks[n16] = dataInputStream.readLong();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).fruitTreeDiseaseChanceMultipliers[n16] = dataInputStream.readDouble();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).fruitTreeSavedFlags[n16] = dataInputStream.readBoolean();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).herbGrowthStages[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).herbCropIds[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            if (s <= 27) {
                                dataInputStream.readInt();
                                ((CharacterFileRecord)object2).herbHarvestAmounts[n16] = 4;
                            } else {
                                ((CharacterFileRecord)object2).herbHarvestAmounts[n16] = dataInputStream.readInt();
                            }
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).herbPatchStates[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).herbLastUpdateTicks[n16] = dataInputStream.readLong();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).herbDiseaseChanceMultipliers[n16] = dataInputStream.readDouble();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).hopsGrowthStages[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).hopsCropIds[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            if (s <= 27) {
                                dataInputStream.readInt();
                                ((CharacterFileRecord)object2).hopsHarvestAmounts[n16] = 4;
                            } else {
                                ((CharacterFileRecord)object2).hopsHarvestAmounts[n16] = dataInputStream.readInt();
                            }
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).hopsPatchStates[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).hopsLastUpdateTicks[n16] = dataInputStream.readLong();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).hopsDiseaseChanceMultipliers[n16] = dataInputStream.readDouble();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).hopsProtectionFlags[n16] = dataInputStream.readBoolean();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).specialTreeGrowthStages[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).specialTreeIds[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).specialTreePatchStates[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).specialTreeLastUpdateTicks[n16] = dataInputStream.readLong();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).specialTreeDiseaseChanceMultipliers[n16] = dataInputStream.readDouble();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).specialCropGrowthStages[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).specialCropIds[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).specialCropPatchStates[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).specialCropLastUpdateTicks[n16] = dataInputStream.readLong();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).specialCropDiseaseChanceMultipliers[n16] = dataInputStream.readDouble();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).treeGrowthStages[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).treeIds[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).treePatchData[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).treePatchStates[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).treeLastUpdateTicks[n16] = dataInputStream.readLong();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).treeDiseaseChanceMultipliers[n16] = dataInputStream.readDouble();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).treeSavedFlags[n16] = dataInputStream.readBoolean();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).compostBinStates[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).compostBinLastUpdateTicks[n16] = dataInputStream.readLong();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 4) {
                            ((CharacterFileRecord)object2).compostBinItemIds[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                        n16 = 0;
                        while (n16 < 18) {
                            ((CharacterFileRecord)object2).farmingToolStoreAmounts[n16] = dataInputStream.readInt();
                            ++n16;
                        }
                    }
                    catch (IOException iOException) {}
                    try {
                        int n17 = dataInputStream.readInt();
                        Object object5 = object2;
                        ((CharacterFileRecord)object2).slayerMasterId = n17;
                        String string3 = dataInputStream.readUTF();
                        object5 = object2;
                        ((CharacterFileRecord)object2).slayerTaskName = string3;
                        int n18 = dataInputStream.readInt();
                        object5 = object2;
                        ((CharacterFileRecord)object2).slayerTaskAmount = n18;
                    }
                    catch (IOException iOException) {}
                    try {
                        boolean bl3 = dataInputStream.readBoolean();
                        Object object6 = object2;
                        ((CharacterFileRecord)object2).usingAncients = bl3;
                    }
                    catch (IOException iOException) {}
                    try {
                        boolean bl4 = dataInputStream.readBoolean();
                        Object object7 = object2;
                        ((CharacterFileRecord)object2).brimhavenOpen = bl4;
                    }
                    catch (IOException iOException) {}
                    try {
                        boolean bl5 = dataInputStream.readBoolean();
                        Object object8 = object2;
                        ((CharacterFileRecord)object2).killedClueAttacker = bl5;
                    }
                    catch (IOException iOException) {}
                    n5 = 0;
                    while (n5 < musicUnlockConfigIds.length) {
                        try {
                            ((CharacterFileRecord)object2).configStates[CharacterFileManager.musicUnlockConfigIds[n5]] = dataInputStream.readInt();
                        }
                        catch (IOException iOException) {}
                        ++n5;
                    }
                    n5 = 0;
                    while (n5 < QuestDefinition.questStateCapacity) {
                        try {
                            ((CharacterFileRecord)object2).questProgress[n5] = dataInputStream.readInt();
                        }
                        catch (IOException iOException) {}
                        ++n5;
                    }
                    byte by = dataInputStream.readByte();
                    Object object9 = object2;
                    ((CharacterFileRecord)object2).gangAffiliation = by;
                    by = dataInputStream.readByte();
                    object9 = object2;
                    ((CharacterFileRecord)object2).piratesTreasureBananaCrateCount = by;
                    by = (byte)(dataInputStream.readBoolean() ? 1 : 0);
                    object9 = object2;
                    ((CharacterFileRecord)object2).treasureTrailNavigationTaught = by;
                    by = dataInputStream.readByte();
                    object9 = object2;
                    ((CharacterFileRecord)object2).coalTruckAmount = by;
                    by = dataInputStream.readByte();
                    object9 = object2;
                    ((CharacterFileRecord)object2).treasureTrailStepCount = by;
                    by = (byte)(dataInputStream.readBoolean() ? 1 : 0);
                    object9 = object2;
                    ((CharacterFileRecord)object2).cluePuzzleSolved = by;
                    if (s < 2) break block157;
                    ((CharacterFileRecord)object2).skeletonSkinUnlocked = dataInputStream.readByte();
                    if (s >= 3) {
                        ((CharacterFileRecord)object2).petUnlockFlags = dataInputStream.readInt();
                    }
                    if (s >= 4) {
                        ((CharacterFileRecord)object2).barrowsDoorPuzzleSolved = dataInputStream.readBoolean();
                        ((CharacterFileRecord)object2).barrowsChestOpened = dataInputStream.readBoolean();
                        ((CharacterFileRecord)object2).configStates[452] = dataInputStream.readInt();
                    }
                    if (s >= 5) {
                        by = dataInputStream.readByte();
                        object9 = object2;
                        ((CharacterFileRecord)object2).flourMillHopperGrainCount = by;
                        ((CharacterFileRecord)object2).configStates[FlourMillHandler.flourBinConfigId] = dataInputStream.readInt();
                    }
                    if (s >= 6) {
                        ((CharacterFileRecord)object2).questRandomSeed = dataInputStream.readInt();
                    }
                    if (s >= 7) {
                        n5 = 0;
                        while (n5 < QuestDefinition.questStateCapacity) {
                            try {
                                ((CharacterFileRecord)object2).questBitFlags[n5] = dataInputStream.readInt();
                            }
                            catch (IOException iOException) {}
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 100) {
                            try {
                                ((CharacterFileRecord)object2).questHookStates[n5] = dataInputStream.readInt();
                            }
                            catch (IOException iOException) {}
                            ++n5;
                        }
                    }
                    if (s >= 8) {
                        ((CharacterFileRecord)object2).publicChatMode = dataInputStream.readByte();
                        ((CharacterFileRecord)object2).privateChatMode = dataInputStream.readByte();
                        ((CharacterFileRecord)object2).tradeMode = dataInputStream.readByte();
                    }
                    if (s >= 9) {
                        ((CharacterFileRecord)object2).ck = dataInputStream.readInt();
                    }
                    if (s >= 10) {
                        ((CharacterFileRecord)object2).cl = dataInputStream.readLong();
                        ((CharacterFileRecord)object2).cm = dataInputStream.readInt();
                        ((CharacterFileRecord)object2).cn = dataInputStream.readInt();
                    }
                    if (s >= 11) {
                        String string4 = dataInputStream.readUTF();
                        object9 = object2;
                        ((CharacterFileRecord)object2).reservedVersion11String = string4;
                    }
                    if (s >= 12) {
                        ((CharacterFileRecord)object2).familyCrestGauntletItemId = dataInputStream.readInt();
                    }
                    if (s >= 13) {
                        ((CharacterFileRecord)object2).mageArenaFlamesOfZamorakCastsRemaining = dataInputStream.readInt();
                        ((CharacterFileRecord)object2).mageArenaSaradominStrikeCastsRemaining = dataInputStream.readInt();
                        ((CharacterFileRecord)object2).mageArenaClawsOfGuthixCastsRemaining = dataInputStream.readInt();
                        ((CharacterFileRecord)object2).mageArenaProgressStage = dataInputStream.readByte();
                    }
                    if (s >= 14) {
                        n5 = 0;
                        while (n5 < 6) {
                            ((CharacterFileRecord)object2).grandExchangeSellOfferFlags[n5] = dataInputStream.readBoolean();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 6) {
                            ((CharacterFileRecord)object2).grandExchangeItemIds[n5] = dataInputStream.readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 6) {
                            ((CharacterFileRecord)object2).grandExchangeQuantities[n5] = dataInputStream.readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 6) {
                            ((CharacterFileRecord)object2).grandExchangeUnitPrices[n5] = dataInputStream.readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 6) {
                            ((CharacterFileRecord)object2).grandExchangeCancelledFlags[n5] = dataInputStream.readBoolean();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 6) {
                            ((CharacterFileRecord)object2).grandExchangeCompletedQuantities[n5] = dataInputStream.readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 6) {
                            ((CharacterFileRecord)object2).grandExchangeTotalPrices[n5] = dataInputStream.readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 6) {
                            ((CharacterFileRecord)object2).grandExchangePrimaryCollectAmounts[n5] = dataInputStream.readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 6) {
                            ((CharacterFileRecord)object2).grandExchangeSecondaryCollectAmounts[n5] = dataInputStream.readInt();
                            ++n5;
                        }
                        n5 = 0;
                        while (n5 < 6) {
                            ((CharacterFileRecord)object2).grandExchangeFinishMessagePending[n5] = dataInputStream.readBoolean();
                            ++n5;
                        }
                    }
                    if (s >= 15) {
                        ((CharacterFileRecord)object2).telekineticPizazzPoints = dataInputStream.readInt();
                        ((CharacterFileRecord)object2).enchantmentPizazzPoints = dataInputStream.readInt();
                        ((CharacterFileRecord)object2).alchemistPizazzPoints = dataInputStream.readInt();
                        ((CharacterFileRecord)object2).graveyardPizazzPoints = dataInputStream.readInt();
                        ((CharacterFileRecord)object2).bonesToPeachesUnlocked = dataInputStream.readBoolean();
                        ((CharacterFileRecord)object2).telekineticMazeIndex = dataInputStream.readByte();
                        ((CharacterFileRecord)object2).telekineticMazeSolved = dataInputStream.readBoolean();
                        ((CharacterFileRecord)object2).telekineticConsecutiveMazesSolved = dataInputStream.readByte();
                    }
                    if (s >= 16) {
                        ((CharacterFileRecord)object2).barrowsRewardPotential = dataInputStream.readInt();
                    }
                    if (s >= 18) {
                        ((CharacterFileRecord)object2).gameMode = dataInputStream.readByte();
                    }
                    if (s >= 19) {
                        ((CharacterFileRecord)object2).barrowsRunsCompleted = dataInputStream.readInt();
                    }
                    if (s >= 21) {
                        ((CharacterFileRecord)object2).godWarsLastAltarBlessingMillis = dataInputStream.readLong();
                        ((CharacterFileRecord)object2).configStates[GodWarsDungeonManager.ropeShortcutConfigId] = dataInputStream.readInt();
                        n5 = 0;
                        while (n5 < ((CharacterFileRecord)object2).godWarsKillCounts.length) {
                            ((CharacterFileRecord)object2).godWarsKillCounts[n5] = dataInputStream.readInt();
                            ++n5;
                        }
                    }
                    if (s >= 22) {
                        ((CharacterFileRecord)object2).craftingThreadUseCount = dataInputStream.readByte();
                    }
                    if (s >= 24) {
                        ((CharacterFileRecord)object2).membershipExpiresMillis = dataInputStream.readLong();
                        ((CharacterFileRecord)object2).dx = dataInputStream.readByte();
                    }
                    if (s >= 25) {
                        ((CharacterFileRecord)object2).configStates[33] = dataInputStream.readInt();
                    }
                    if (s >= 26) {
                        ((CharacterFileRecord)object2).savedCacheVersion = dataInputStream.readUnsignedShort();
                    }
                    if (s >= 29) {
                        ((CharacterFileRecord)object2).godBookPageFlags = dataInputStream.readInt();
                    }
                    if (s >= 30) {
                        ((CharacterFileRecord)object2).swampCaveRopeAttached = dataInputStream.readBoolean();
                        ((CharacterFileRecord)object2).lampOilStillFilled = dataInputStream.readBoolean();
                        ((CharacterFileRecord)object2).enterTheAbyssMiniquestState = dataInputStream.readInt();
                    }
                    if (s >= 20) {
                        n5 = dataInputStream.readBoolean() ? 1 : 0;
                        ((CharacterFileRecord)object2).botEnabled = n5;
                        if (n5 != 0) {
                            ((CharacterFileRecord)object2).botMode = dataInputStream.readByte();
                            ((CharacterFileRecord)object2).currentBotTaskTypeId = dataInputStream.readByte();
                            ((CharacterFileRecord)object2).currentBotTaskIndex = dataInputStream.readInt();
                            ((CharacterFileRecord)object2).deferredBotTaskTypeId = dataInputStream.readByte();
                            ((CharacterFileRecord)object2).deferredBotTaskIndex = dataInputStream.readInt();
                            ((CharacterFileRecord)object2).botTaskState = dataInputStream.readUTF();
                            byte by2 = dataInputStream.readByte();
                            n2 = by2;
                            if (by2 > 0) {
                                ((CharacterFileRecord)object2).botTaskRequiredItems = new ItemStack[n2];
                                int n19 = 0;
                                while (n19 < n2) {
                                    n3 = dataInputStream.readInt();
                                    n = dataInputStream.readInt();
                                    ((CharacterFileRecord)object2).botTaskRequiredItems[n19] = new ItemStack(n3, n);
                                    ++n19;
                                }
                            }
                            ((CharacterFileRecord)object2).botFoodItemId = dataInputStream.readInt();
                            ((CharacterFileRecord)object2).botPathSegmentIndex = dataInputStream.readByte();
                            ((CharacterFileRecord)object2).botPathWaypointIndex = dataInputStream.readByte();
                            ((CharacterFileRecord)object2).savedWorldRouteReversed = dataInputStream.readBoolean();
                            ((CharacterFileRecord)object2).botTaskSavedElapsedMillis = dataInputStream.readLong();
                            ((CharacterFileRecord)object2).botTaskDurationMinutes = dataInputStream.readByte();
                            ((CharacterFileRecord)object2).savedWorldRouteIndex = dataInputStream.readByte();
                            ((CharacterFileRecord)object2).tradeAdvertMode = dataInputStream.readByte();
                            ((CharacterFileRecord)object2).botAdvertItemId = dataInputStream.readInt();
                            ((CharacterFileRecord)object2).tradeAdvertQuantityRemaining = dataInputStream.readInt();
                            ((CharacterFileRecord)object2).tradeAdvertUnitPrice = dataInputStream.readInt();
                            ((CharacterFileRecord)object2).tradeAdvertScam = dataInputStream.readBoolean();
                            ((CharacterFileRecord)object2).tradeAdvertVariableQuantity = dataInputStream.readBoolean();
                            ((CharacterFileRecord)object2).tradeAdvertLastOfferAmount = dataInputStream.readInt();
                            ((CharacterFileRecord)object2).botShopBuyMode = dataInputStream.readByte();
                            ((CharacterFileRecord)object2).botTaskItemId = dataInputStream.readInt();
                            ((CharacterFileRecord)object2).botShopItemAmount = dataInputStream.readInt();
                            byte by3 = dataInputStream.readByte();
                            n3 = 0;
                            while (n3 < by3) {
                                n = dataInputStream.readInt();
                                ((CharacterFileRecord)object2).botShopSellItemIds.add(n);
                                ++n3;
                            }
                            n3 = dataInputStream.readByte();
                            n = 0;
                            while (n < n3) {
                                int n20 = dataInputStream.readInt();
                                ((CharacterFileRecord)object2).botCombatLoadoutItemIds.add(n20);
                                ++n;
                            }
                            ((CharacterFileRecord)object2).botCombatStyle = dataInputStream.readByte();
                            ((CharacterFileRecord)object2).botSkillTargetSkillId = dataInputStream.readByte();
                            ((CharacterFileRecord)object2).botSkillTargetLevel = dataInputStream.readByte();
                            ((CharacterFileRecord)object2).cN = dataInputStream.readByte();
                            ((CharacterFileRecord)object2).cM = dataInputStream.readByte();
                            ((CharacterFileRecord)object2).cL = dataInputStream.readByte();
                            ((CharacterFileRecord)object2).cK = dataInputStream.readByte();
                            ((CharacterFileRecord)object2).botCompletionItemId = dataInputStream.readInt();
                            ((CharacterFileRecord)object2).botCompletionItemAmount = dataInputStream.readInt();
                            ((CharacterFileRecord)object2).cH = dataInputStream.readInt();
                            ((CharacterFileRecord)object2).cG = dataInputStream.readInt();
                            ((CharacterFileRecord)object2).cF = dataInputStream.readInt();
                            ((CharacterFileRecord)object2).cE = dataInputStream.readInt();
                            ((CharacterFileRecord)object2).botTaskReturnToBankRequested = dataInputStream.readBoolean();
                            dataInputStream.readBoolean();
                            dataInputStream.readBoolean();
                            dataInputStream.readBoolean();
                            dataInputStream.readBoolean();
                            dataInputStream.readBoolean();
                            ((CharacterFileRecord)object2).botElementalSpellIndex = dataInputStream.readByte();
                            dataInputStream.readByte();
                            dataInputStream.readByte();
                            dataInputStream.readByte();
                            dataInputStream.readByte();
                            dataInputStream.readInt();
                            dataInputStream.readInt();
                            dataInputStream.readInt();
                            dataInputStream.readInt();
                            dataInputStream.readInt();
                            dataInputStream.readUTF();
                            dataInputStream.readUTF();
                            dataInputStream.readUTF();
                            dataInputStream.readUTF();
                            dataInputStream.readUTF();
                        }
                    }
                }
                catch (Exception exception) {
                    dataInputStream.close();
                    ((FileInputStream)object).close();
                    return null;
                }
            }
            dataInputStream.close();
            ((FileInputStream)object).close();
            ((CharacterFileRecord)object2).getStoredItemValue();
            return object2;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    /*
     * Unable to fully structure code
     */
    public static void loadPlayerFromFile(String var0, Player var1_1) {
        var2_2 = new File(String.valueOf(var0) + var1_1.getUsername() + ".dat");
        if (!var2_2.exists()) {
            if (Server.getInstance() != null) {
                Server.getInstance().queueLogin(var1_1);
            }
            return;
        }
        if (!CharacterFileManager.validateCharacterFile(var0, var1_1.getUsername()) && var0.equals("./data/characters/")) {
            CharacterFileManager.restorePlayerFromBackup(var1_1);
            return;
        }
        try {
            var2_2 = new FileInputStream((File)var2_2);
            var2_2 = new DataInputStream((InputStream)var2_2);
            var3_3 = var2_2.readShort();
            if (var3_3 < 24) {
                var2_2.close();
                return;
            }
            var1_1.setUsername(var2_2.readUTF());
            var4_4 = var2_2.readUTF();
            var1_1.setPassword(var4_4);
            var1_1.ek = var2_2.readUTF();
            var2_2.readInt();
            var1_1.setPlayerRights(0);
            var2_2.readUTF();
            var1_1.setProfileString1(var2_2.readUTF());
            var1_1.setProfileString2(var2_2.readUTF());
            var1_1.eh = var2_2.readLong();
            var1_1.ej = var2_2.readLong();
            var1_1.eg = var2_2.readLong();
            var1_1.loginRestrictionExempt = var2_2.readBoolean();
            var1_1.setMemberFlag(var2_2.readBoolean());
            var1_1.setDonatorPoints(var2_2.readInt());
            var1_1.getPosition().setX(var2_2.readInt());
            var1_1.getPosition().setPreviousX(var1_1.getPosition().getX());
            var1_1.getPosition().setY(var2_2.readInt());
            var1_1.getPosition().setPreviousY(var1_1.getPosition().getY() + 1);
            var1_1.getPosition().setPlane(var2_2.readInt());
            var1_1.setGender(var2_2.readInt());
            var1_1.dT = var2_2.readInt();
            var1_1.dU = var2_2.readInt();
            var1_1.dV = var2_2.readInt();
            var1_1.dW = var2_2.readInt();
            var1_1.dX = var2_2.readInt();
            var1_1.dY = var2_2.readInt();
            var1_1.dZ = var2_2.readInt();
            var1_1.ea = var2_2.readInt();
            var1_1.eb = var2_2.readInt();
            var1_1.ec = var2_2.readInt();
            var1_1.legacyQuestPoints = var2_2.readInt();
            var1_1.setAutoRetaliate(var2_2.readBoolean());
            var1_1.setFightMode(var2_2.readInt());
            var1_1.setBrightness(var2_2.readInt());
            var1_1.setMouseButtons(var2_2.readInt());
            var1_1.setPublicChatEffects(var2_2.readInt());
            var1_1.setSplitPrivateChat(var2_2.readInt());
            var1_1.setAcceptAid(var2_2.readInt());
            var1_1.setMusicVolume(var2_2.readInt());
            var1_1.setEffectVolume(var2_2.readInt());
            var1_1.setSpecialEnergy((int)var2_2.readDouble());
            var1_1.getBankPinManager().setChangingPin(var2_2.readBoolean());
            var1_1.getBankPinManager().setDeletingPin(var2_2.readBoolean());
            var1_1.getBankPinManager().setPinAppendYear(var2_2.readInt());
            var1_1.getBankPinManager().setPinAppendDate(var2_2.readInt());
            var1_1.setBindingNecklaceCharge(var2_2.readInt());
            var1_1.setRingOfForgingLife(var2_2.readInt());
            var1_1.setRingOfRecoilLife(var2_2.readInt());
            var4_5 = var2_2.readInt();
            if (var4_5 > 0) {
                var1_1.addPvpCombatReference(var1_1, var4_5);
            }
            if (var3_3 <= 26) {
                var8_6 = var2_2.readDouble();
                var1_1.setRunEnergyPercent((int)var8_6);
            } else {
                var1_1.setRunEnergyRaw(var2_2.readUnsignedShort());
            }
            var1_1.getMovementQueue().setRunning(var2_2.readBoolean());
            var8_7 = 0;
            while (var8_7 < var1_1.getBankPinManager().getCurrentPin().length) {
                var1_1.getBankPinManager().getCurrentPin()[var8_7] = var2_2.readInt();
                ++var8_7;
            }
            var8_7 = 0;
            while (var8_7 < var1_1.getBankPinManager().getPendingPin().length) {
                var1_1.getBankPinManager().getPendingPin()[var8_7] = var2_2.readInt();
                ++var8_7;
            }
            var8_7 = 0;
            while (var8_7 < 4) {
                var1_1.h(var8_7, var2_2.readInt());
                ++var8_7;
            }
            var8_7 = 0;
            while (var8_7 < var1_1.getAppearanceParts().length) {
                var1_1.getAppearanceParts()[var8_7] = var2_2.readInt();
                ++var8_7;
            }
            var8_7 = 0;
            while (var8_7 < var1_1.getAppearanceColors().length) {
                var1_1.getAppearanceColors()[var8_7] = var2_2.readInt();
                ++var8_7;
            }
            var8_7 = 0;
            while (var8_7 < var1_1.getSkillManager().getCurrentLevels().length) {
                var1_1.getSkillManager().getCurrentLevels()[var8_7] = var2_2.readInt();
                ++var8_7;
            }
            var8_7 = 0;
            while (var8_7 < var1_1.getSkillManager().getExperience().length) {
                var1_1.getSkillManager().getExperience()[var8_7] = var2_2.readInt();
                ++var8_7;
            }
            var8_7 = 0;
            while (var8_7 < 28) {
                var9_8 = var2_2.readInt();
                if (var9_8 != 65535) {
                    var10_9 = var2_2.readInt();
                    var11_13 = var2_2.readInt();
                    if (var9_8 < 11883 && var10_9 > 0) {
                        var12_14 = new ItemStack(var9_8, var10_9, var11_13);
                        if (var12_14.getId() == 2696 || var12_14.getId() == 2699 || var12_14.getId() == 3510) {
                            var12_14 = new ItemStack(var9_8 - 1, var10_9, var11_13);
                        }
                        var1_1.getInventoryManager().getContainer().setItem(var8_7, var12_14);
                    }
                }
                ++var8_7;
            }
            var8_7 = 0;
            while (var8_7 < 14) {
                var9_8 = var2_2.readInt();
                if (var9_8 != 65535) {
                    var10_9 = var2_2.readInt();
                    var11_13 = -1;
                    if (var3_3 >= 23) {
                        var11_13 = var2_2.readInt();
                    }
                    if (var9_8 < 11883 && var10_9 > 0) {
                        var12_14 = new ItemStack(var9_8, var10_9, var11_13);
                        var1_1.getEquipmentManager().getContainer().setItem(var8_7, var12_14);
                    }
                }
                ++var8_7;
            }
            try {
                block170: {
                    block169: {
                        block168: {
                            block167: {
                                block166: {
                                    block165: {
                                        if (var3_3 < 17) {
                                            var8_7 = 0;
                                            while (var8_7 < 352) {
                                                var9_8 = var2_2.readInt();
                                                if (var9_8 != 65535) {
                                                    var10_9 = var2_2.readInt();
                                                    var11_13 = var2_2.readInt();
                                                    if (var9_8 < 11883 && var10_9 > 0) {
                                                        var12_14 = new ItemStack(var9_8, var10_9, var11_13);
                                                        if (var12_14.getId() == 2696 || var12_14.getId() == 2699 || var12_14.getId() == 3510) {
                                                            var12_14 = new ItemStack(var9_8 - 1, var10_9, var11_13);
                                                        }
                                                        var1_1.getBankContainer().setTabItem(var8_7, var12_14, 0);
                                                    }
                                                }
                                                ++var8_7;
                                            }
                                        } else {
                                            var8_7 = var2_2.readByte();
                                            var9_8 = 0;
                                            while (var9_8 < var8_7) {
                                                var10_9 = var2_2.readUnsignedShort();
                                                var11_13 = 0;
                                                while (var11_13 < var10_9) {
                                                    var12_16 = var2_2.readInt();
                                                    if (var12_16 != 65535) {
                                                        var13_22 = var2_2.readInt();
                                                        var4_5 = var2_2.readInt();
                                                        if (var12_16 < 11883 && var13_22 > 0) {
                                                            var5_21 = new ItemStack(var12_16, var13_22, var4_5);
                                                            if (var5_21.getId() == 2696 || var5_21.getId() == 2699 || var5_21.getId() == 3510) {
                                                                var5_21 = new ItemStack(var12_16 - 1, var13_22, var4_5);
                                                            }
                                                            var1_1.getBankContainer().setTabItem(var11_13, (ItemStack)var5_21, var9_8);
                                                        }
                                                    }
                                                    ++var11_13;
                                                }
                                                ++var9_8;
                                            }
                                        }
                                        var8_7 = 0;
                                        while (var8_7 < var1_1.getFriendsList().length) {
                                            var1_1.getFriendsList()[var8_7] = var2_2.readLong();
                                            ++var8_7;
                                        }
                                        var8_7 = 0;
                                        while (var8_7 < var1_1.getIgnoreList().length) {
                                            var1_1.getIgnoreList()[var8_7] = var2_2.readLong();
                                            ++var8_7;
                                        }
                                        var8_7 = 0;
                                        while (var8_7 < var1_1.getQueuedLoginItemIds().length) {
                                            var1_1.getQueuedLoginItemIds()[var8_7] = var2_2.readInt();
                                            var1_1.getQueuedLoginItemAmounts()[var8_7] = var2_2.readInt();
                                            ++var8_7;
                                        }
                                        var1_1.ap(var2_2.readInt());
                                        var1_1.setMuteExpires(var2_2.readLong());
                                        var1_1.setBanExpires(var2_2.readLong());
                                        var8_7 = 0;
                                        while (var8_7 < 6) {
                                            var1_1.setBarrowsBrotherKilled(var8_7, var2_2.readBoolean());
                                            ++var8_7;
                                        }
                                        var1_1.setBarrowsKillCount(var2_2.readInt());
                                        var1_1.setBarrowsTargetBrotherIndex(var2_2.readInt());
                                        var8_7 = var2_2.readInt();
                                        var1_1.getPoisonImmunityTimer().setDelayTicks(var8_7);
                                        var1_1.getPoisonImmunityTimer().reset();
                                        var9_8 = var2_2.readInt();
                                        var1_1.getAntifireTimer().setDelayTicks(var9_8);
                                        var1_1.getAntifireTimer().reset();
                                        var1_1.getTeleblockTimer().setDelayTicks(var2_2.readInt());
                                        var1_1.getTeleblockTimer().reset();
                                        var12_17 = var10_10 = var2_2.readDouble();
                                        var1_1.setPoisonDamage(var12_17);
                                        var4_5 = 0;
                                        if (true) ** GOTO lbl215
                                        do {
                                            var18_24 = var2_2.readInt();
                                            var6_23 = var4_5++;
                                            var5_21 = var1_1.getAllotmentPatchManager();
                                            var5_21.growthStages[var6_23] = var18_24;
lbl215:
                                            // 2 sources

                                            var5_21 = var1_1.getAllotmentPatchManager();
                                        } while (var4_5 < var5_21.growthStages.length);
                                        var4_5 = 0;
                                        if (true) ** GOTO lbl224
                                        do {
                                            var18_24 = var2_2.readInt();
                                            var6_23 = var4_5++;
                                            var5_21 = var1_1.getAllotmentPatchManager();
                                            var5_21.cropIds[var6_23] = var18_24;
lbl224:
                                            // 2 sources

                                            var5_21 = var1_1.getAllotmentPatchManager();
                                        } while (var4_5 < var5_21.cropIds.length);
                                        if (var3_3 > 27) break block165;
                                        var4_5 = 0;
                                        if (true) ** GOTO lbl236
                                        do {
                                            var2_2.readInt();
                                            var18_24 = 4;
                                            var6_23 = var4_5++;
                                            var5_21 = var1_1.getAllotmentPatchManager();
                                            var5_21.harvestAmounts[var6_23] = var18_24;
lbl236:
                                            // 2 sources

                                            var5_21 = var1_1.getAllotmentPatchManager();
                                        } while (var4_5 < var5_21.d.length);
                                        break block166;
                                    }
                                    var4_5 = 0;
                                    if (true) ** GOTO lbl247
                                    do {
                                        var18_24 = var2_2.readInt();
                                        var6_23 = var4_5++;
                                        var5_21 = var1_1.getAllotmentPatchManager();
                                        var5_21.harvestAmounts[var6_23] = var18_24;
lbl247:
                                        // 2 sources

                                        var5_21 = var1_1.getAllotmentPatchManager();
                                    } while (var4_5 < var5_21.harvestAmounts.length);
                                }
                                var4_5 = 0;
                                if (true) ** GOTO lbl257
                                do {
                                    var18_24 = var2_2.readInt();
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getAllotmentPatchManager();
                                    var5_21.patchStates[var6_23] = var18_24;
lbl257:
                                    // 2 sources

                                    var5_21 = var1_1.getAllotmentPatchManager();
                                } while (var4_5 < var5_21.patchStates.length);
                                var4_5 = 0;
                                if (true) ** GOTO lbl266
                                do {
                                    var18_25 = var2_2.readLong();
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getAllotmentPatchManager();
                                    var5_21.lastUpdateTicks[var6_23] = var18_25;
lbl266:
                                    // 2 sources

                                    var5_21 = var1_1.getAllotmentPatchManager();
                                } while (var4_5 < var5_21.lastUpdateTicks.length);
                                var4_5 = 0;
                                if (true) ** GOTO lbl275
                                do {
                                    var18_26 = var2_2.readDouble();
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getAllotmentPatchManager();
                                    var5_21.diseaseChanceMultipliers[var6_23] = var18_26;
lbl275:
                                    // 2 sources

                                    var5_21 = var1_1.getAllotmentPatchManager();
                                } while (var4_5 < var5_21.diseaseChanceMultipliers.length);
                                var4_5 = 0;
                                if (true) ** GOTO lbl284
                                do {
                                    var18_27 = var2_2.readBoolean();
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getAllotmentPatchManager();
                                    var5_21.protectionFlags[var6_23] = var18_27;
lbl284:
                                    // 2 sources

                                    var5_21 = var1_1.getAllotmentPatchManager();
                                } while (var4_5 < var5_21.protectionFlags.length);
                                var4_5 = 0;
                                if (true) ** GOTO lbl293
                                do {
                                    var18_28 = var2_2.readInt();
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getBushPatchManager();
                                    var5_21.growthStages[var6_23] = var18_28;
lbl293:
                                    // 2 sources

                                    var5_21 = var1_1.getBushPatchManager();
                                } while (var4_5 < var5_21.growthStages.length);
                                var4_5 = 0;
                                if (true) ** GOTO lbl302
                                do {
                                    var18_29 = var2_2.readInt();
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getBushPatchManager();
                                    var5_21.cropIds[var6_23] = var18_29;
lbl302:
                                    // 2 sources

                                    var5_21 = var1_1.getBushPatchManager();
                                } while (var4_5 < var5_21.cropIds.length);
                                var4_5 = 0;
                                if (true) ** GOTO lbl311
                                do {
                                    var18_30 = var2_2.readInt();
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getBushPatchManager();
                                    var5_21.patchStates[var6_23] = var18_30;
lbl311:
                                    // 2 sources

                                    var5_21 = var1_1.getBushPatchManager();
                                } while (var4_5 < var5_21.patchStates.length);
                                var4_5 = 0;
                                if (true) ** GOTO lbl320
                                do {
                                    var18_31 = var2_2.readLong();
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getBushPatchManager();
                                    var5_21.lastUpdateTicks[var6_23] = var18_31;
lbl320:
                                    // 2 sources

                                    var5_21 = var1_1.getBushPatchManager();
                                } while (var4_5 < var5_21.lastUpdateTicks.length);
                                var4_5 = 0;
                                if (true) ** GOTO lbl329
                                do {
                                    var18_32 = var2_2.readDouble();
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getBushPatchManager();
                                    var5_21.diseaseChanceMultipliers[var6_23] = var18_32;
lbl329:
                                    // 2 sources

                                    var5_21 = var1_1.getBushPatchManager();
                                } while (var4_5 < var5_21.diseaseChanceMultipliers.length);
                                var4_5 = 0;
                                if (true) ** GOTO lbl338
                                do {
                                    var18_33 = var2_2.readBoolean();
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getBushPatchManager();
                                    var5_21.protectionFlags[var6_23] = var18_33;
lbl338:
                                    // 2 sources

                                    var5_21 = var1_1.getBushPatchManager();
                                } while (var4_5 < var5_21.protectionFlags.length);
                                var4_5 = 0;
                                if (true) ** GOTO lbl347
                                do {
                                    var18_34 = var2_2.readInt();
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getFlowerPatchManager();
                                    var5_21.growthStages[var6_23] = var18_34;
lbl347:
                                    // 2 sources

                                    var5_21 = var1_1.getFlowerPatchManager();
                                } while (var4_5 < var5_21.growthStages.length);
                                var4_5 = 0;
                                if (true) ** GOTO lbl356
                                do {
                                    var18_35 = var2_2.readInt();
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getFlowerPatchManager();
                                    var5_21.cropIds[var6_23] = var18_35;
lbl356:
                                    // 2 sources

                                    var5_21 = var1_1.getFlowerPatchManager();
                                } while (var4_5 < var5_21.cropIds.length);
                                var4_5 = 0;
                                if (true) ** GOTO lbl365
                                do {
                                    var18_36 = var2_2.readInt();
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getFlowerPatchManager();
                                    var5_21.patchStates[var6_23] = var18_36;
lbl365:
                                    // 2 sources

                                    var5_21 = var1_1.getFlowerPatchManager();
                                } while (var4_5 < var5_21.patchStates.length);
                                var4_5 = 0;
                                if (true) ** GOTO lbl374
                                do {
                                    var18_37 = var2_2.readLong();
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getFlowerPatchManager();
                                    var5_21.lastUpdateTicks[var6_23] = var18_37;
lbl374:
                                    // 2 sources

                                    var5_21 = var1_1.getFlowerPatchManager();
                                } while (var4_5 < var5_21.lastUpdateTicks.length);
                                var4_5 = 0;
                                if (true) ** GOTO lbl383
                                do {
                                    var18_38 = var2_2.readDouble();
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getFlowerPatchManager();
                                    var5_21.diseaseChanceMultipliers[var6_23] = var18_38;
lbl383:
                                    // 2 sources

                                    var5_21 = var1_1.getFlowerPatchManager();
                                } while (var4_5 < var5_21.diseaseChanceMultipliers.length);
                                var4_5 = 0;
                                if (true) ** GOTO lbl392
                                do {
                                    var18_39 = var2_2.readInt();
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getFruitTreePatchManager();
                                    var5_21.growthStages[var6_23] = var18_39;
lbl392:
                                    // 2 sources

                                    var5_21 = var1_1.getFruitTreePatchManager();
                                } while (var4_5 < var5_21.growthStages.length);
                                var4_5 = 0;
                                if (true) ** GOTO lbl401
                                do {
                                    var18_40 = var2_2.readInt();
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getFruitTreePatchManager();
                                    var5_21.treeIds[var6_23] = var18_40;
lbl401:
                                    // 2 sources

                                    var5_21 = var1_1.getFruitTreePatchManager();
                                } while (var4_5 < var5_21.treeIds.length);
                                var4_5 = 0;
                                if (true) ** GOTO lbl410
                                do {
                                    var18_41 = var2_2.readInt();
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getFruitTreePatchManager();
                                    var5_21.patchStates[var6_23] = var18_41;
lbl410:
                                    // 2 sources

                                    var5_21 = var1_1.getFruitTreePatchManager();
                                } while (var4_5 < var5_21.patchStates.length);
                                var4_5 = 0;
                                if (true) ** GOTO lbl419
                                do {
                                    var18_42 = var2_2.readLong();
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getFruitTreePatchManager();
                                    var5_21.lastUpdateTicks[var6_23] = var18_42;
lbl419:
                                    // 2 sources

                                    var5_21 = var1_1.getFruitTreePatchManager();
                                } while (var4_5 < var5_21.lastUpdateTicks.length);
                                var4_5 = 0;
                                if (true) ** GOTO lbl428
                                do {
                                    var18_43 = var2_2.readDouble();
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getFruitTreePatchManager();
                                    var5_21.diseaseChanceMultipliers[var6_23] = var18_43;
lbl428:
                                    // 2 sources

                                    var5_21 = var1_1.getFruitTreePatchManager();
                                } while (var4_5 < var5_21.diseaseChanceMultipliers.length);
                                var4_5 = 0;
                                if (true) ** GOTO lbl437
                                do {
                                    var18_44 = var2_2.readBoolean();
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getFruitTreePatchManager();
                                    var5_21.protectionFlags[var6_23] = var18_44;
lbl437:
                                    // 2 sources

                                    var5_21 = var1_1.getFruitTreePatchManager();
                                } while (var4_5 < var5_21.protectionFlags.length);
                                var4_5 = 0;
                                if (true) ** GOTO lbl446
                                do {
                                    var18_45 = var2_2.readInt();
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getHerbPatchManager();
                                    var5_21.growthStages[var6_23] = var18_45;
lbl446:
                                    // 2 sources

                                    var5_21 = var1_1.getHerbPatchManager();
                                } while (var4_5 < var5_21.growthStages.length);
                                var4_5 = 0;
                                if (true) ** GOTO lbl455
                                do {
                                    var18_46 = var2_2.readInt();
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getHerbPatchManager();
                                    var5_21.cropIds[var6_23] = var18_46;
lbl455:
                                    // 2 sources

                                    var5_21 = var1_1.getHerbPatchManager();
                                } while (var4_5 < var5_21.cropIds.length);
                                if (var3_3 > 27) break block167;
                                var4_5 = 0;
                                if (true) ** GOTO lbl467
                                do {
                                    var2_2.readInt();
                                    var18_47 = 4;
                                    var6_23 = var4_5++;
                                    var5_21 = var1_1.getHerbPatchManager();
                                    var5_21.harvestAmounts[var6_23] = var18_47;
lbl467:
                                    // 2 sources

                                    var5_21 = var1_1.getHerbPatchManager();
                                } while (var4_5 < var5_21.d.length);
                                break block168;
                            }
                            var4_5 = 0;
                            if (true) ** GOTO lbl478
                            do {
                                var18_48 = var2_2.readInt();
                                var6_23 = var4_5++;
                                var5_21 = var1_1.getHerbPatchManager();
                                var5_21.harvestAmounts[var6_23] = var18_48;
lbl478:
                                // 2 sources

                                var5_21 = var1_1.getHerbPatchManager();
                            } while (var4_5 < var5_21.harvestAmounts.length);
                        }
                        var4_5 = 0;
                        if (true) ** GOTO lbl488
                        do {
                            var18_49 = var2_2.readInt();
                            var6_23 = var4_5++;
                            var5_21 = var1_1.getHerbPatchManager();
                            var5_21.patchStates[var6_23] = var18_49;
lbl488:
                            // 2 sources

                            var5_21 = var1_1.getHerbPatchManager();
                        } while (var4_5 < var5_21.patchStates.length);
                        var4_5 = 0;
                        if (true) ** GOTO lbl497
                        do {
                            var18_50 = var2_2.readLong();
                            var6_23 = var4_5++;
                            var5_21 = var1_1.getHerbPatchManager();
                            var5_21.lastUpdateTicks[var6_23] = var18_50;
lbl497:
                            // 2 sources

                            var5_21 = var1_1.getHerbPatchManager();
                        } while (var4_5 < var5_21.lastUpdateTicks.length);
                        var4_5 = 0;
                        if (true) ** GOTO lbl506
                        do {
                            var18_51 = var2_2.readDouble();
                            var6_23 = var4_5++;
                            var5_21 = var1_1.getHerbPatchManager();
                            var5_21.diseaseChanceMultipliers[var6_23] = var18_51;
lbl506:
                            // 2 sources

                            var5_21 = var1_1.getHerbPatchManager();
                        } while (var4_5 < var5_21.diseaseChanceMultipliers.length);
                        var4_5 = 0;
                        if (true) ** GOTO lbl515
                        do {
                            var18_52 = var2_2.readInt();
                            var6_23 = var4_5++;
                            var5_21 = var1_1.getHopsPatchManager();
                            var5_21.growthStages[var6_23] = var18_52;
lbl515:
                            // 2 sources

                            var5_21 = var1_1.getHopsPatchManager();
                        } while (var4_5 < var5_21.growthStages.length);
                        var4_5 = 0;
                        if (true) ** GOTO lbl524
                        do {
                            var18_53 = var2_2.readInt();
                            var6_23 = var4_5++;
                            var5_21 = var1_1.getHopsPatchManager();
                            var5_21.cropIds[var6_23] = var18_53;
lbl524:
                            // 2 sources

                            var5_21 = var1_1.getHopsPatchManager();
                        } while (var4_5 < var5_21.cropIds.length);
                        if (var3_3 > 27) break block169;
                        var4_5 = 0;
                        if (true) ** GOTO lbl536
                        do {
                            var2_2.readInt();
                            var18_54 = 4;
                            var6_23 = var4_5++;
                            var5_21 = var1_1.getHopsPatchManager();
                            var5_21.harvestAmounts[var6_23] = var18_54;
lbl536:
                            // 2 sources

                            var5_21 = var1_1.getHopsPatchManager();
                        } while (var4_5 < var5_21.d.length);
                        break block170;
                    }
                    var4_5 = 0;
                    if (true) ** GOTO lbl547
                    do {
                        var18_55 = var2_2.readInt();
                        var6_23 = var4_5++;
                        var5_21 = var1_1.getHopsPatchManager();
                        var5_21.harvestAmounts[var6_23] = var18_55;
lbl547:
                        // 2 sources

                        var5_21 = var1_1.getHopsPatchManager();
                    } while (var4_5 < var5_21.harvestAmounts.length);
                }
                var4_5 = 0;
                if (true) ** GOTO lbl557
                do {
                    var18_56 = var2_2.readInt();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getHopsPatchManager();
                    var5_21.patchStates[var6_23] = var18_56;
lbl557:
                    // 2 sources

                    var5_21 = var1_1.getHopsPatchManager();
                } while (var4_5 < var5_21.patchStates.length);
                var4_5 = 0;
                if (true) ** GOTO lbl566
                do {
                    var18_57 = var2_2.readLong();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getHopsPatchManager();
                    var5_21.lastUpdateTicks[var6_23] = var18_57;
lbl566:
                    // 2 sources

                    var5_21 = var1_1.getHopsPatchManager();
                } while (var4_5 < var5_21.lastUpdateTicks.length);
                var4_5 = 0;
                if (true) ** GOTO lbl575
                do {
                    var18_58 = var2_2.readDouble();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getHopsPatchManager();
                    var5_21.diseaseChanceMultipliers[var6_23] = var18_58;
lbl575:
                    // 2 sources

                    var5_21 = var1_1.getHopsPatchManager();
                } while (var4_5 < var5_21.diseaseChanceMultipliers.length);
                var4_5 = 0;
                if (true) ** GOTO lbl584
                do {
                    var18_59 = var2_2.readBoolean();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getHopsPatchManager();
                    var5_21.protectionFlags[var6_23] = var18_59;
lbl584:
                    // 2 sources

                    var5_21 = var1_1.getHopsPatchManager();
                } while (var4_5 < var5_21.protectionFlags.length);
                var4_5 = 0;
                if (true) ** GOTO lbl593
                do {
                    var18_60 = var2_2.readInt();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getSpecialTreePatchManager();
                    var5_21.growthStages[var6_23] = var18_60;
lbl593:
                    // 2 sources

                    var5_21 = var1_1.getSpecialTreePatchManager();
                } while (var4_5 < var5_21.growthStages.length);
                var4_5 = 0;
                if (true) ** GOTO lbl602
                do {
                    var18_61 = var2_2.readInt();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getSpecialTreePatchManager();
                    var5_21.treeIds[var6_23] = var18_61;
lbl602:
                    // 2 sources

                    var5_21 = var1_1.getSpecialTreePatchManager();
                } while (var4_5 < var5_21.treeIds.length);
                var4_5 = 0;
                if (true) ** GOTO lbl611
                do {
                    var18_62 = var2_2.readInt();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getSpecialTreePatchManager();
                    var5_21.patchStates[var6_23] = var18_62;
lbl611:
                    // 2 sources

                    var5_21 = var1_1.getSpecialTreePatchManager();
                } while (var4_5 < var5_21.patchStates.length);
                var4_5 = 0;
                if (true) ** GOTO lbl620
                do {
                    var18_63 = var2_2.readLong();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getSpecialTreePatchManager();
                    var5_21.lastUpdateTicks[var6_23] = var18_63;
lbl620:
                    // 2 sources

                    var5_21 = var1_1.getSpecialTreePatchManager();
                } while (var4_5 < var5_21.lastUpdateTicks.length);
                var4_5 = 0;
                if (true) ** GOTO lbl629
                do {
                    var18_64 = var2_2.readDouble();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getSpecialTreePatchManager();
                    var5_21.diseaseChanceMultipliers[var6_23] = var18_64;
lbl629:
                    // 2 sources

                    var5_21 = var1_1.getSpecialTreePatchManager();
                } while (var4_5 < var5_21.diseaseChanceMultipliers.length);
                var4_5 = 0;
                if (true) ** GOTO lbl638
                do {
                    var18_65 = var2_2.readInt();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getSpecialCropPatchManager();
                    var5_21.growthStages[var6_23] = var18_65;
lbl638:
                    // 2 sources

                    var5_21 = var1_1.getSpecialCropPatchManager();
                } while (var4_5 < var5_21.growthStages.length);
                var4_5 = 0;
                if (true) ** GOTO lbl647
                do {
                    var18_66 = var2_2.readInt();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getSpecialCropPatchManager();
                    var5_21.cropIds[var6_23] = var18_66;
lbl647:
                    // 2 sources

                    var5_21 = var1_1.getSpecialCropPatchManager();
                } while (var4_5 < var5_21.cropIds.length);
                var4_5 = 0;
                if (true) ** GOTO lbl656
                do {
                    var18_67 = var2_2.readInt();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getSpecialCropPatchManager();
                    var5_21.patchStates[var6_23] = var18_67;
lbl656:
                    // 2 sources

                    var5_21 = var1_1.getSpecialCropPatchManager();
                } while (var4_5 < var5_21.patchStates.length);
                var4_5 = 0;
                if (true) ** GOTO lbl665
                do {
                    var18_68 = var2_2.readLong();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getSpecialCropPatchManager();
                    var5_21.lastUpdateTicks[var6_23] = var18_68;
lbl665:
                    // 2 sources

                    var5_21 = var1_1.getSpecialCropPatchManager();
                } while (var4_5 < var5_21.lastUpdateTicks.length);
                var4_5 = 0;
                if (true) ** GOTO lbl674
                do {
                    var18_69 = var2_2.readDouble();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getSpecialCropPatchManager();
                    var5_21.diseaseChanceMultipliers[var6_23] = var18_69;
lbl674:
                    // 2 sources

                    var5_21 = var1_1.getSpecialCropPatchManager();
                } while (var4_5 < var5_21.diseaseChanceMultipliers.length);
                var4_5 = 0;
                if (true) ** GOTO lbl683
                do {
                    var18_70 = var2_2.readInt();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getTreePatchManager();
                    var5_21.growthStages[var6_23] = var18_70;
lbl683:
                    // 2 sources

                    var5_21 = var1_1.getTreePatchManager();
                } while (var4_5 < var5_21.growthStages.length);
                var4_5 = 0;
                if (true) ** GOTO lbl692
                do {
                    var18_71 = var2_2.readInt();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getTreePatchManager();
                    var5_21.treeIds[var6_23] = var18_71;
lbl692:
                    // 2 sources

                    var5_21 = var1_1.getTreePatchManager();
                } while (var4_5 < var5_21.treeIds.length);
                var4_5 = 0;
                if (true) ** GOTO lbl701
                do {
                    var18_72 = var2_2.readInt();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getTreePatchManager();
                    var5_21.patchData[var6_23] = var18_72;
lbl701:
                    // 2 sources

                    var5_21 = var1_1.getTreePatchManager();
                } while (var4_5 < var5_21.patchData.length);
                var4_5 = 0;
                if (true) ** GOTO lbl710
                do {
                    var18_73 = var2_2.readInt();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getTreePatchManager();
                    var5_21.patchStates[var6_23] = var18_73;
lbl710:
                    // 2 sources

                    var5_21 = var1_1.getTreePatchManager();
                } while (var4_5 < var5_21.patchStates.length);
                var4_5 = 0;
                if (true) ** GOTO lbl719
                do {
                    var18_74 = var2_2.readLong();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getTreePatchManager();
                    var5_21.lastUpdateTicks[var6_23] = var18_74;
lbl719:
                    // 2 sources

                    var5_21 = var1_1.getTreePatchManager();
                } while (var4_5 < var5_21.lastUpdateTicks.length);
                var4_5 = 0;
                if (true) ** GOTO lbl728
                do {
                    var18_75 = var2_2.readDouble();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getTreePatchManager();
                    var5_21.diseaseChanceMultipliers[var6_23] = var18_75;
lbl728:
                    // 2 sources

                    var5_21 = var1_1.getTreePatchManager();
                } while (var4_5 < var5_21.diseaseChanceMultipliers.length);
                var4_5 = 0;
                if (true) ** GOTO lbl737
                do {
                    var18_76 = var2_2.readBoolean();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getTreePatchManager();
                    var5_21.protectionFlags[var6_23] = var18_76;
lbl737:
                    // 2 sources

                    var5_21 = var1_1.getTreePatchManager();
                } while (var4_5 < var5_21.protectionFlags.length);
                var4_5 = 0;
                if (true) ** GOTO lbl746
                do {
                    var18_77 = var2_2.readInt();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getCompostBinManager();
                    var5_21.states[var6_23] = var18_77;
lbl746:
                    // 2 sources

                    var5_21 = var1_1.getCompostBinManager();
                } while (var4_5 < var5_21.states.length);
                var4_5 = 0;
                if (true) ** GOTO lbl755
                do {
                    var18_78 = var2_2.readLong();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getCompostBinManager();
                    var5_21.lastUpdateTicks[var6_23] = var18_78;
lbl755:
                    // 2 sources

                    var5_21 = var1_1.getCompostBinManager();
                } while (var4_5 < var5_21.lastUpdateTicks.length);
                var4_5 = 0;
                if (true) ** GOTO lbl764
                do {
                    var18_79 = var2_2.readInt();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getCompostBinManager();
                    var5_21.itemIds[var6_23] = var18_79;
lbl764:
                    // 2 sources

                    var5_21 = var1_1.getCompostBinManager();
                } while (var4_5 < var5_21.itemIds.length);
                var4_5 = 0;
                if (true) ** GOTO lbl773
                do {
                    var18_80 = var2_2.readInt();
                    var6_23 = var4_5++;
                    var5_21 = var1_1.getFarmingToolStore();
                    var5_21.storedAmounts[var6_23] = var18_80;
lbl773:
                    // 2 sources

                    var5_21 = var1_1.getFarmingToolStore();
                } while (var4_5 < var5_21.storedAmounts.length);
            }
            catch (IOException v0) {}
            try {
                var1_1.getSlayerManager().slayerMasterId = var2_2.readInt();
                var1_1.getSlayerManager().slayerTaskName = var2_2.readUTF();
                var1_1.getSlayerManager().taskAmount = var2_2.readInt();
            }
            catch (IOException v1) {}
            try {
                var8_7 = (int)var2_2.readBoolean();
                var1_1.setSpellbook(var8_7 != 0 ? Spellbook.ANCIENT : Spellbook.MODERN);
            }
            catch (IOException v2) {}
            try {
                var1_1.setBrimhavenOpen(var2_2.readBoolean());
            }
            catch (IOException v3) {}
            try {
                var6_23 = var2_2.readBoolean();
                var5_21 = var1_1;
                var1_1.killedClueAttacker = var6_23;
            }
            catch (IOException v4) {}
            var8_7 = 0;
            while (var8_7 < CharacterFileManager.musicUnlockConfigIds.length) {
                try {
                    var1_1.ep[CharacterFileManager.musicUnlockConfigIds[var8_7]] = var2_2.readInt();
                }
                catch (IOException v5) {
                    var1_1.ep[CharacterFileManager.musicUnlockConfigIds[var8_7]] = 0;
                }
                ++var8_7;
            }
            var8_7 = 0;
            while (var8_7 < QuestDefinition.questStateCapacity) {
                try {
                    var1_1.setQuestState(var8_7, var2_2.readInt());
                }
                catch (IOException v6) {
                    var1_1.setQuestState(var8_7, 0);
                }
                ++var8_7;
            }
            var1_1.dv = var2_2.readByte();
            var1_1.bO = var2_2.readByte();
            var1_1.D = var2_2.readBoolean();
            var1_1.setCoalTruckCoalCount(var2_2.readByte());
            var1_1.ar = var2_2.readByte();
            var1_1.en = var2_2.readBoolean();
            if (var3_3 >= 2) {
                var1_1.ee = var2_2.readByte();
                if (var3_3 >= 3) {
                    var1_1.setBossPetUnlockFlags(var2_2.readInt());
                }
                if (var3_3 >= 4) {
                    var1_1.barrowsDoorPuzzleSolved = var2_2.readBoolean();
                    var1_1.barrowsChestOpened = var2_2.readBoolean();
                    var1_1.ep[452] = var2_2.readInt();
                }
                if (var3_3 >= 5) {
                    var6_23 = var2_2.readByte();
                    var5_21 = var1_1;
                    var1_1.flourMillHopperGrainCount = var6_23;
                    var1_1.ep[FlourMillHandler.flourBinConfigId] = var2_2.readInt();
                }
                if (var3_3 >= 6) {
                    var1_1.bK = var2_2.readInt();
                }
                if (var3_3 >= 7) {
                    var8_7 = 0;
                    while (var8_7 < QuestDefinition.questStateCapacity) {
                        try {
                            var1_1.questProgressFlags[var8_7] = var2_2.readInt();
                        }
                        catch (IOException v7) {
                            var1_1.questProgressFlags[var8_7] = 0;
                        }
                        ++var8_7;
                    }
                    var8_7 = 0;
                    while (var8_7 < 100) {
                        try {
                            var1_1.questHookStates[var8_7] = var2_2.readInt();
                        }
                        catch (IOException v8) {
                            var1_1.questHookStates[var8_7] = 0;
                        }
                        ++var8_7;
                    }
                }
                if (var3_3 >= 8) {
                    var1_1.setPublicChatMode(var2_2.readByte());
                    var1_1.setPrivateChatMode(var2_2.readByte());
                    var1_1.setTradeMode(var2_2.readByte());
                }
                if (var3_3 >= 9) {
                    var1_1.em = var2_2.readInt();
                }
                if (var3_3 >= 10) {
                    var1_1.bY = var2_2.readLong();
                    var1_1.ca = var2_2.readInt();
                    var1_1.bZ = var2_2.readInt();
                }
                if (var3_3 >= 11) {
                    var1_1.reservedVersion11String = var2_2.readUTF();
                }
                if (var3_3 >= 12) {
                    var1_1.familyCrestGauntletItemId = var2_2.readInt();
                }
                if (var3_3 >= 13) {
                    var1_1.mageArenaFlamesOfZamorakCastsRemaining = var2_2.readInt();
                    var1_1.mageArenaSaradominStrikeCastsRemaining = var2_2.readInt();
                    var1_1.mageArenaClawsOfGuthixCastsRemaining = var2_2.readInt();
                    var1_1.mageArenaProgressStage = var2_2.readByte();
                }
                if (var3_3 >= 14) {
                    var8_7 = 0;
                    while (var8_7 < 6) {
                        var1_1.grandExchangeSellOfferFlags[var8_7] = var2_2.readBoolean();
                        ++var8_7;
                    }
                    var8_7 = 0;
                    while (var8_7 < 6) {
                        var1_1.grandExchangeItemIds[var8_7] = var2_2.readInt();
                        ++var8_7;
                    }
                    var8_7 = 0;
                    while (var8_7 < 6) {
                        var1_1.grandExchangeQuantities[var8_7] = var2_2.readInt();
                        ++var8_7;
                    }
                    var8_7 = 0;
                    while (var8_7 < 6) {
                        var1_1.grandExchangeUnitPrices[var8_7] = var2_2.readInt();
                        ++var8_7;
                    }
                    var8_7 = 0;
                    while (var8_7 < 6) {
                        var1_1.grandExchangeCancelledFlags[var8_7] = var2_2.readBoolean();
                        ++var8_7;
                    }
                    var8_7 = 0;
                    while (var8_7 < 6) {
                        var1_1.grandExchangeCompletedQuantities[var8_7] = var2_2.readInt();
                        ++var8_7;
                    }
                    var8_7 = 0;
                    while (var8_7 < 6) {
                        var1_1.grandExchangeTotalPrices[var8_7] = var2_2.readInt();
                        ++var8_7;
                    }
                    var8_7 = 0;
                    while (var8_7 < 6) {
                        var1_1.grandExchangePrimaryCollectAmounts[var8_7] = var2_2.readInt();
                        ++var8_7;
                    }
                    var8_7 = 0;
                    while (var8_7 < 6) {
                        var1_1.grandExchangeSecondaryCollectAmounts[var8_7] = var2_2.readInt();
                        ++var8_7;
                    }
                    var8_7 = 0;
                    while (var8_7 < 6) {
                        var1_1.grandExchangeFinishMessagePending[var8_7] = var2_2.readBoolean();
                        ++var8_7;
                    }
                }
                if (var3_3 >= 15) {
                    var1_1.getTelekineticTheatreController().pizazzPoints = var2_2.readInt();
                    var1_1.getEnchantmentChamberController().pizazzPoints = var2_2.readInt();
                    var1_1.getAlchemistPlaygroundController().pizazzPoints = var2_2.readInt();
                    var1_1.getCreatureGraveyardController().pizazzPoints = var2_2.readInt();
                    var1_1.bonesToPeachesUnlocked = var2_2.readBoolean();
                    var1_1.getTelekineticTheatreController().mazeIndex = var2_2.readByte();
                    var1_1.getTelekineticTheatreController().mazeSolved = var2_2.readBoolean();
                    var1_1.getTelekineticTheatreController().consecutiveMazesSolved = var2_2.readByte();
                }
                if (var3_3 >= 16) {
                    var1_1.barrowsRewardPotential = var2_2.readInt();
                }
                if (var3_3 >= 18) {
                    var1_1.gameMode = var2_2.readByte();
                }
                if (var3_3 >= 19) {
                    var1_1.barrowsRunsCompleted = var2_2.readInt();
                }
                if (var3_3 >= 21) {
                    var1_1.godWarsLastAltarBlessingMillis = var2_2.readLong();
                    var1_1.ep[GodWarsDungeonManager.ropeShortcutConfigId] = var2_2.readInt();
                    var8_7 = 0;
                    while (var8_7 < var1_1.godWarsKillCounts.length) {
                        var1_1.godWarsKillCounts[var8_7] = var2_2.readInt();
                        ++var8_7;
                    }
                }
                if (var3_3 >= 22) {
                    var1_1.craftingThreadUseCount = var2_2.readByte();
                }
                if (var3_3 >= 24) {
                    var1_1.membershipExpiresMillis = var2_2.readLong();
                    var1_1.l = var2_2.readByte();
                }
                if (var3_3 >= 25) {
                    var1_1.ep[33] = var2_2.readInt();
                }
                if (var3_3 >= 26) {
                    var1_1.savedCacheVersion = var2_2.readUnsignedShort();
                    CacheCoordinateTranslator.translateSavedDungeonPosition(var1_1);
                }
                if (var3_3 >= 29) {
                    var1_1.godBookPageFlags = var2_2.readInt();
                }
                if (var3_3 >= 30) {
                    var1_1.swampCaveRopeAttached = var2_2.readBoolean();
                    var1_1.lampOilStillFilled = var2_2.readBoolean();
                    var1_1.enterTheAbyssMiniquestState = var2_2.readInt();
                }
                if (var3_3 >= 20) {
                    v9 = var2_2.readBoolean();
                    var8_7 = (int)v9;
                    if (v9) {
                        var2_2.readByte();
                        var1_1.currentBotTaskTypeId = var2_2.readByte();
                        var1_1.currentBotTaskIndex = var2_2.readInt();
                        var1_1.deferredBotTaskTypeId = var2_2.readByte();
                        var1_1.deferredBotTaskIndex = var2_2.readInt();
                        var1_1.botTaskState = var2_2.readUTF();
                        v10 = var2_2.readByte();
                        var9_8 = v10;
                        if (v10 > 0) {
                            var1_1.botTaskRequiredItems = new ItemStack[var9_8];
                            var10_11 = 0;
                            while (var10_11 < var9_8) {
                                var11_13 = var2_2.readInt();
                                var12_18 = var2_2.readInt();
                                var1_1.botTaskRequiredItems[var10_11] = new ItemStack(var11_13, var12_18);
                                ++var10_11;
                            }
                        }
                        var1_1.botFoodItemId = var2_2.readInt();
                        var1_1.botPathSegmentIndex = var2_2.readByte();
                        var1_1.botPathWaypointIndex = var2_2.readByte();
                        var1_1.savedWorldRouteReversed = var2_2.readBoolean();
                        var1_1.botTaskSavedElapsedMillis = var2_2.readLong();
                        var1_1.botTaskDurationMinutes = var2_2.readByte();
                        var1_1.savedWorldRouteIndex = var2_2.readByte();
                        var1_1.tradeAdvertMode = var2_2.readByte();
                        var1_1.botAdvertItemId = var2_2.readInt();
                        var1_1.tradeAdvertQuantityRemaining = var2_2.readInt();
                        var1_1.tradeAdvertUnitPrice = var2_2.readInt();
                        var1_1.tradeAdvertScam = var2_2.readBoolean();
                        var1_1.tradeAdvertVariableQuantity = var2_2.readBoolean();
                        var1_1.tradeAdvertLastOfferAmount = var2_2.readInt();
                        var1_1.botShopBuyMode = var2_2.readByte();
                        var1_1.botTaskItemId = var2_2.readInt();
                        var1_1.botShopItemAmount = var2_2.readInt();
                        var10_12 = var2_2.readByte();
                        var11_13 = 0;
                        while (var11_13 < var10_12) {
                            var12_19 = var2_2.readInt();
                            var1_1.botShopSellItemIds.add(var12_19);
                            ++var11_13;
                        }
                        var11_13 = var2_2.readByte();
                        var12_20 = 0;
                        while (var12_20 < var11_13) {
                            var13_22 = var2_2.readInt();
                            var1_1.botCombatLoadoutItemIds.add(var13_22);
                            ++var12_20;
                        }
                        var1_1.botCombatStyle = var2_2.readByte();
                        var1_1.botSkillTargetSkillId = var2_2.readByte();
                        var1_1.botSkillTargetLevel = var2_2.readByte();
                        var1_1.be = var2_2.readByte();
                        var1_1.bf = var2_2.readByte();
                        var1_1.bg = var2_2.readByte();
                        var1_1.bh = var2_2.readByte();
                        var1_1.botCompletionItemId = var2_2.readInt();
                        var1_1.botCompletionItemAmount = var2_2.readInt();
                        var1_1.bk = var2_2.readInt();
                        var1_1.bl = var2_2.readInt();
                        var1_1.bm = var2_2.readInt();
                        var1_1.bn = var2_2.readInt();
                        var1_1.botTaskReturnToBankRequested = var2_2.readBoolean();
                        var2_2.readBoolean();
                        var2_2.readBoolean();
                        var2_2.readBoolean();
                        var2_2.readBoolean();
                        var2_2.readBoolean();
                        var1_1.botElementalSpellIndex = var2_2.readByte();
                        var2_2.readByte();
                        var2_2.readByte();
                        var2_2.readByte();
                        var2_2.readByte();
                        var2_2.readInt();
                        var2_2.readInt();
                        var2_2.readInt();
                        var2_2.readInt();
                        var2_2.readInt();
                        var2_2.readUTF();
                        var2_2.readUTF();
                        var2_2.readUTF();
                        var2_2.readUTF();
                        var2_2.readUTF();
                    }
                }
            }
            AppearancePacketHandler.validateAppearance(var1_1);
            if (!var0.equals("./data/characters/")) {
                var1_1.dQ = true;
                GameplayHelper.a(String.valueOf(System.currentTimeMillis()) + "\u00a7" + var1_1.getUsername() + "\u00a7" + var1_1.eh, "restored");
            }
            var2_2.close();
            if (Server.getInstance() != null) {
                Server.getInstance().queueLogin(var1_1);
            }
            return;
        }
        catch (IOException v11) {
            var2_2 = v11;
            v11.printStackTrace();
            System.out.println("Account not loading: " + var1_1);
            return;
        }
    }
}

