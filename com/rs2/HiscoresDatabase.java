/*
 * Decompiled with CFR 0.152.
 */
package com.rs2;

import com.rs2.model.player.Player;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public final class HiscoresDatabase {
    private static Statement a;
    private static boolean b;
    private static String c;
    private static String d;
    private static String e;
    private static String f;

    static {
        c = "jdbc:mysql://localhost:3306/rs2psp";
        d = "root";
        e = "root";
        f = "com.mysql.cj.jdbc.Driver";
    }

    public static void a() {
        try {
            Class.forName(f).newInstance();
            Connection connection = DriverManager.getConnection(c, d, e);
            a = connection.createStatement();
            b = true;
            System.out.println("Succesfully connected to MySQL Database.");
            return;
        }
        catch (Exception exception) {
            b = false;
            exception.printStackTrace();
            return;
        }
    }

    private static ResultSet a(String object) {
        if (((String)object).toLowerCase().startsWith("select")) {
            object = a.executeQuery((String)object);
            return object;
        }
        try {
            a.executeUpdate((String)object);
            return null;
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            HiscoresDatabase.b();
            return null;
        }
    }

    public static void b() {
        try {
            if (a != null) {
                a.close();
            }
            b = false;
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public static boolean a(Player object) {
        if (!b) {
            return false;
        }
        if (((Player)object).de && ((Player)object).az != 4) {
            return false;
        }
        try {
            HiscoresDatabase.a("DELETE FROM `skills` WHERE playerName = '" + ((Player)object).getUsername() + "';");
            HiscoresDatabase.a("DELETE FROM `skillsoverall` WHERE playerName = '" + ((Player)object).getUsername() + "';");
            HiscoresDatabase.a("INSERT INTO `skills` (`playerName`,`Attacklvl`,`Attackxp`,`Defencelvl`,`Defencexp`,`Strengthlvl`,`Strengthxp`,`Hitpointslvl`,`Hitpointsxp`,`Rangelvl`,`Rangexp`,`Prayerlvl`,`Prayerxp`,`Magiclvl`,`Magicxp`,`Cookinglvl`,`Cookingxp`,`Woodcuttinglvl`,`Woodcuttingxp`,`Fletchinglvl`,`Fletchingxp`,`Fishinglvl`,`Fishingxp`,`Firemakinglvl`,`Firemakingxp`,`Craftinglvl`,`Craftingxp`,`Smithinglvl`,`Smithingxp`,`Mininglvl`,`Miningxp`,`Herblorelvl`,`Herblorexp`,`Agilitylvl`,`Agilityxp`,`Thievinglvl`,`Thievingxp`,`Slayerlvl`,`Slayerxp`,`Farminglvl`,`Farmingxp`,`Runecraftlvl`,`Runecraftxp`) VALUES ('" + ((Player)object).getUsername() + "'," + ((Player)object).getSkillManager().getBaseLevel(0) + "," + ((Player)object).getSkillManager().getExperience()[0] + "," + ((Player)object).getSkillManager().getBaseLevel(1) + "," + ((Player)object).getSkillManager().getExperience()[1] + "," + ((Player)object).getSkillManager().getBaseLevel(2) + "," + ((Player)object).getSkillManager().getExperience()[2] + "," + ((Player)object).getSkillManager().getBaseLevel(3) + "," + ((Player)object).getSkillManager().getExperience()[3] + "," + ((Player)object).getSkillManager().getBaseLevel(4) + "," + ((Player)object).getSkillManager().getExperience()[4] + "," + ((Player)object).getSkillManager().getBaseLevel(5) + "," + ((Player)object).getSkillManager().getExperience()[5] + "," + ((Player)object).getSkillManager().getBaseLevel(6) + "," + ((Player)object).getSkillManager().getExperience()[6] + "," + ((Player)object).getSkillManager().getBaseLevel(7) + "," + ((Player)object).getSkillManager().getExperience()[7] + "," + ((Player)object).getSkillManager().getBaseLevel(8) + "," + ((Player)object).getSkillManager().getExperience()[8] + "," + ((Player)object).getSkillManager().getBaseLevel(9) + "," + ((Player)object).getSkillManager().getExperience()[9] + "," + ((Player)object).getSkillManager().getBaseLevel(10) + "," + ((Player)object).getSkillManager().getExperience()[10] + "," + ((Player)object).getSkillManager().getBaseLevel(11) + "," + ((Player)object).getSkillManager().getExperience()[11] + "," + ((Player)object).getSkillManager().getBaseLevel(12) + "," + ((Player)object).getSkillManager().getExperience()[12] + "," + ((Player)object).getSkillManager().getBaseLevel(13) + "," + ((Player)object).getSkillManager().getExperience()[13] + "," + ((Player)object).getSkillManager().getBaseLevel(14) + "," + ((Player)object).getSkillManager().getExperience()[14] + "," + ((Player)object).getSkillManager().getBaseLevel(15) + "," + ((Player)object).getSkillManager().getExperience()[15] + "," + ((Player)object).getSkillManager().getBaseLevel(16) + "," + ((Player)object).getSkillManager().getExperience()[16] + "," + ((Player)object).getSkillManager().getBaseLevel(17) + "," + ((Player)object).getSkillManager().getExperience()[17] + "," + ((Player)object).getSkillManager().getBaseLevel(18) + "," + ((Player)object).getSkillManager().getExperience()[18] + "," + ((Player)object).getSkillManager().getBaseLevel(19) + "," + ((Player)object).getSkillManager().getExperience()[19] + "," + ((Player)object).getSkillManager().getBaseLevel(20) + "," + ((Player)object).getSkillManager().getExperience()[20] + ");");
            HiscoresDatabase.a("INSERT INTO `skillsoverall` (`playerName`,`level`,`xp`) VALUES ('" + ((Player)object).getUsername() + "'," + (((Player)object).getSkillManager().getBaseLevel(0) + ((Player)object).getSkillManager().getBaseLevel(1) + ((Player)object).getSkillManager().getBaseLevel(2) + ((Player)object).getSkillManager().getBaseLevel(3) + ((Player)object).getSkillManager().getBaseLevel(4) + ((Player)object).getSkillManager().getBaseLevel(5) + ((Player)object).getSkillManager().getBaseLevel(6) + ((Player)object).getSkillManager().getBaseLevel(7) + ((Player)object).getSkillManager().getBaseLevel(8) + ((Player)object).getSkillManager().getBaseLevel(9) + ((Player)object).getSkillManager().getBaseLevel(10) + ((Player)object).getSkillManager().getBaseLevel(11) + ((Player)object).getSkillManager().getBaseLevel(12) + ((Player)object).getSkillManager().getBaseLevel(13) + ((Player)object).getSkillManager().getBaseLevel(14) + ((Player)object).getSkillManager().getBaseLevel(15) + ((Player)object).getSkillManager().getBaseLevel(16) + ((Player)object).getSkillManager().getBaseLevel(17) + ((Player)object).getSkillManager().getBaseLevel(18) + ((Player)object).getSkillManager().getBaseLevel(19) + ((Player)object).getSkillManager().getBaseLevel(20)) + "," + (((Player)object).getSkillManager().getExperience()[0] + ((Player)object).getSkillManager().getExperience()[1] + ((Player)object).getSkillManager().getExperience()[2] + ((Player)object).getSkillManager().getExperience()[3] + ((Player)object).getSkillManager().getExperience()[4] + ((Player)object).getSkillManager().getExperience()[5] + ((Player)object).getSkillManager().getExperience()[6] + ((Player)object).getSkillManager().getExperience()[7] + ((Player)object).getSkillManager().getExperience()[8] + ((Player)object).getSkillManager().getExperience()[9] + ((Player)object).getSkillManager().getExperience()[10] + ((Player)object).getSkillManager().getExperience()[11] + ((Player)object).getSkillManager().getExperience()[12] + ((Player)object).getSkillManager().getExperience()[13] + ((Player)object).getSkillManager().getExperience()[14] + ((Player)object).getSkillManager().getExperience()[15] + ((Player)object).getSkillManager().getExperience()[16] + ((Player)object).getSkillManager().getExperience()[17] + ((Player)object).getSkillManager().getExperience()[18] + ((Player)object).getSkillManager().getExperience()[19] + ((Player)object).getSkillManager().getExperience()[20]) + ");");
            System.out.println("Hiscores saved for: " + ((Player)object).getUsername());
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            return false;
        }
        return true;
    }
}

