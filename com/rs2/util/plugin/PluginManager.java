/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.util.plugin;

import com.rs2.model.player.Player;
import com.rs2.util.ProfilerRegistry;
import com.rs2.util.ProfilerTimer;
import com.rs2.util.plugin.GlobalPlugin;
import com.rs2.util.plugin.PlayerPlugin;
import com.rs2.util.plugin.Plugin;
import com.rs2.util.plugin.PluginType;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PluginManager {
    private static List a = new LinkedList();
    private static List b = new LinkedList();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void a() {
        try {
            Serializable serializable;
            Object object = "com.rs2.util.plugin.impl";
            Object object2 = Thread.currentThread().getContextClassLoader();
            assert (object2 != null);
            Object object3 = ((String)object).replace('.', '/');
            object3 = ((ClassLoader)object2).getResources((String)object3);
            Object object4 = new ArrayList<File>();
            while (object3.hasMoreElements()) {
                serializable = (URL)object3.nextElement();
                object4.add(new File(((URL)serializable).getFile()));
            }
            serializable = new ArrayList();
            object4 = object4.iterator();
            while (object4.hasNext()) {
                object3 = (File)object4.next();
                ((ArrayList)serializable).addAll(PluginManager.a((File)object3, (String)object));
            }
            object = ((ArrayList)serializable).toArray(new Class[((ArrayList)serializable).size()]);
            serializable = object;
            int n = ((Class[])object).length;
            int n2 = 0;
            while (n2 < n) {
                object = serializable[n2];
                object2 = (Plugin)((Class)object).newInstance();
                if (((Plugin)object2).d() == PluginType.b) {
                    object = (GlobalPlugin)object2;
                    object2 = a;
                    synchronized (object2) {
                        System.out.println("Loaded global plugin: " + ((Plugin)object).a() + " v" + ((Plugin)object).c() + " by " + ((Plugin)object).b());
                        b.add(object);
                    }
                }
                if (((Plugin)object2).d() == PluginType.c) {
                    object2 = a;
                    synchronized (object2) {
                        System.out.println("Loaded local plugin: " + ((Class)object).getSimpleName());
                        a.add(object);
                    }
                }
                ++n2;
            }
            return;
        }
        catch (InstantiationException instantiationException) {
            InstantiationException instantiationException2 = instantiationException;
            instantiationException.printStackTrace();
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            IllegalAccessException illegalAccessException2 = illegalAccessException;
            illegalAccessException.printStackTrace();
            return;
        }
        catch (ClassNotFoundException classNotFoundException) {
            ClassNotFoundException classNotFoundException2 = classNotFoundException;
            classNotFoundException.printStackTrace();
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void a(Player player) {
        List list = a;
        synchronized (list) {
            Iterator iterator = a.iterator();
            while (iterator.hasNext()) {
                Object object;
                try {
                    object = (PlayerPlugin)((Class)iterator.next()).newInstance();
                    player.addPlayerPlugin((PlayerPlugin)object);
                }
                catch (InstantiationException instantiationException) {
                    object = instantiationException;
                    instantiationException.printStackTrace();
                    System.out.println("Error instantiating local plugin for " + player.getUsername());
                }
                catch (IllegalAccessException illegalAccessException) {
                    object = illegalAccessException;
                    illegalAccessException.printStackTrace();
                    System.out.println("Error instantiating local plugin for " + player.getUsername());
                }
            }
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void b() {
        ProfilerTimer profilerTimer = ProfilerRegistry.getTimer("tickPlugins");
        profilerTimer.start();
        List list = b;
        synchronized (list) {
            Iterator iterator = b.iterator();
            while (iterator.hasNext()) {
                iterator.next();
            }
        }
        profilerTimer.stop();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void c() {
        List list = b;
        synchronized (list) {
            Iterator iterator = b.iterator();
            while (iterator.hasNext()) {
                iterator.next();
            }
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void d() {
        List list = b;
        synchronized (list) {
            Iterator iterator = b.iterator();
            while (iterator.hasNext()) {
                iterator.next();
            }
            return;
        }
    }

    private static List a(File object, String string) {
        ArrayList arrayList = new ArrayList();
        if (!((File)object).exists()) {
            return arrayList;
        }
        object = ((File)object).listFiles();
        File[] fileArray = object;
        int n = ((File[])object).length;
        int n2 = 0;
        while (n2 < n) {
            object = fileArray[n2];
            if (((File)object).isDirectory()) {
                assert (!((File)object).getName().contains("."));
                arrayList.addAll(PluginManager.a((File)object, String.valueOf(string) + "." + ((File)object).getName()));
            } else if (((File)object).getName().endsWith(".class")) {
                arrayList.add(Class.forName(String.valueOf(string) + '.' + ((File)object).getName().substring(0, ((File)object).getName().length() - 6)));
            }
            ++n2;
        }
        return arrayList;
    }
}

