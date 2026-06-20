/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.util;

import com.rs2.ServerSettings;
import com.rs2.model.player.Player;
import com.rs2.util.ConverterUidLookupCallback;
import com.rs2.util.ConverterUidLookupQuery;
import com.rs2.util.db.DatabaseService;
import java.io.File;
import java.util.Arrays;

public class Converter {
    private static int convertedCount = 0;
    private static volatile boolean readyForNextFile = true;

    public static void main(String[] object) {
        try {
            ServerSettings.mysqlPlayerSaveEnabled = true;
            object = new File("./data/characters/");
            DatabaseService.setInstance(new DatabaseService(8, ServerSettings.mysqlDriverClass, ServerSettings.mysqlJdbcUrl, ServerSettings.mysqlUsername, ServerSettings.mysqlPassword));
            System.out.println("Preparing to convert " + ((File)object).listFiles().length + " character files...");
            Object object2 = Arrays.asList(((File)object).listFiles());
            object2 = object2.iterator();
            while (true) {
                if (!readyForNextFile) {
                    continue;
                }
                if (object2.hasNext()) {
                    Object object3 = (File)object2.next();
                    readyForNextFile = false;
                    object3 = ((File)object3).getName().substring(0, ((File)object3).getName().indexOf(46)).toLowerCase();
                    Player player = new Player(null);
                    player.setUsername((String)object3);
                    ConverterUidLookupQuery converterUidLookupQuery = new ConverterUidLookupQuery("SELECT uid FROM `prs06_users` WHERE username = ?", (String)object3);
                    DatabaseService.getInstance().submit(converterUidLookupQuery, new ConverterUidLookupCallback((File)object, player, (String)object3));
                    continue;
                }
                break;
            }
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
        }
    }

    static /* synthetic */ void markReadyForNextFile(boolean bl) {
        readyForNextFile = true;
    }

    static /* synthetic */ int getConvertedCount() {
        return convertedCount;
    }

    static /* synthetic */ void setConvertedCount(int n) {
        convertedCount = n;
    }
}

