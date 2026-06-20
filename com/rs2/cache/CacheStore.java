/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.cache;

import com.rs2.ServerSettings;
import com.rs2.cache.CacheDefinitionIndex;
import com.rs2.cache.CacheFile;
import com.rs2.cache.CacheStoreException;
import com.rs2.util.ByteArrayReader;
import com.rs2.util.FileUtil;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.zip.CRC32;

public final class CacheStore
implements Closeable {
    private static CacheStore instance;
    private final RandomAccessFile dataFile;
    private final RandomAccessFile[] indexFiles;
    private final CacheDefinitionIndex definitionIndex;
    public static boolean cacheVerificationFailed;

    static {
        cacheVerificationFailed = false;
    }

    public static void initializeCacheStore() {
        try {
            instance = new CacheStore(new File("./data/caches/cache_" + ServerSettings.cacheVersion + "/"));
        }
        catch (CacheStoreException cacheStoreException) {
            CacheStoreException cacheStoreException2 = cacheStoreException;
            cacheStoreException.printStackTrace();
        }
        try {
            instance.verifyLauncherJarIntegrity();
            return;
        }
        catch (Exception exception) {
            cacheVerificationFailed = true;
            return;
        }
    }

    public static CacheStore getInstance() {
        return instance;
    }

    private CacheStore(File file) {
        try {
            int n = 0;
            int n2 = 0;
            while (n2 < 255) {
                File file2 = new File(String.valueOf(file.getAbsolutePath()) + "/main_file_cache.idx" + n2);
                if (!file2.exists()) break;
                ++n;
                ++n2;
            }
            if (n == 0) {
                throw new CacheStoreException("No index files present.");
            }
            this.indexFiles = new RandomAccessFile[n];
            this.dataFile = new RandomAccessFile(String.valueOf(file.getAbsolutePath()) + "/main_file_cache.dat", "r");
            n2 = 0;
            while (n2 < this.indexFiles.length) {
                this.indexFiles[n2] = new RandomAccessFile(String.valueOf(file.getAbsolutePath()) + "/main_file_cache.idx" + n2, "r");
                ++n2;
            }
            this.definitionIndex = new CacheDefinitionIndex(this);
            return;
        }
        catch (FileNotFoundException fileNotFoundException) {
            throw new CacheStoreException(fileNotFoundException);
        }
        catch (IOException iOException) {
            throw new CacheStoreException(iOException);
        }
    }

    public final CacheDefinitionIndex getDefinitionIndex() {
        return this.definitionIndex;
    }

    public final CacheFile readFile(int n, int n2) {
        if (n < 0 || n >= this.indexFiles.length) {
            throw new IOException("Cache does not exist.");
        }
        Object object = this.indexFiles[n];
        ++n;
        if (n2 < 0 || (long)n2 >= ((RandomAccessFile)object).length() * 6L) {
            throw new IOException("File does not exist.");
        }
        object = ((RandomAccessFile)object).getChannel().map(FileChannel.MapMode.READ_ONLY, n2 * 6, 6L);
        int n3 = (((ByteBuffer)object).get() & 0xFF) << 16 | (((ByteBuffer)object).get() & 0xFF) << 8 | ((ByteBuffer)object).get() & 0xFF;
        int n4 = (((ByteBuffer)object).get() & 0xFF) << 16 | (((ByteBuffer)object).get() & 0xFF) << 8 | ((ByteBuffer)object).get() & 0xFF;
        int n5 = n3;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n3);
        int n6 = 0;
        while (n5 > 0) {
            int n7 = 520;
            int n8 = (int)(this.dataFile.length() - (long)(n4 * 520));
            if (n8 < 520) {
                n7 = n8;
            }
            MappedByteBuffer mappedByteBuffer = this.dataFile.getChannel().map(FileChannel.MapMode.READ_ONLY, n4 * 520, n7);
            n7 = mappedByteBuffer.getShort() & 0xFFFF;
            n8 = mappedByteBuffer.getShort() & 0xFFFF;
            int n9 = (mappedByteBuffer.get() & 0xFF) << 16 | (mappedByteBuffer.get() & 0xFF) << 8 | mappedByteBuffer.get() & 0xFF;
            int n10 = mappedByteBuffer.get() & 0xFF;
            int n11 = n5;
            if (n11 > 512) {
                n11 = 512;
            }
            byte[] byArray = new byte[n11];
            mappedByteBuffer.get(byArray);
            byteBuffer.put(byArray, 0, n11);
            n5 -= n11;
            if (n6 != n8) {
                throw new IOException("Cycle does not match part id.");
            }
            if (n5 > 0) {
                if (n10 != n) {
                    throw new IOException("Unexpected next cache id.");
                }
                if (n7 != n2) {
                    throw new IOException("Unexpected next file id.");
                }
            }
            ++n6;
            n4 = n9;
        }
        return new CacheFile(n, n2, (ByteBuffer)byteBuffer.flip());
    }

    @Override
    public final void close() {
        this.dataFile.close();
        RandomAccessFile[] randomAccessFileArray = this.indexFiles;
        int n = this.indexFiles.length;
        int n2 = 0;
        while (n2 < n) {
            RandomAccessFile randomAccessFile = randomAccessFileArray[n2];
            randomAccessFile.close();
            ++n2;
        }
    }

    private void verifyLauncherJarIntegrity() {
        Object object = new char[]{'.', '/', 'd', 'a', 't', 'S', 'e', 'r', 'v', 'j'};
        int[] nArray = new int[15];
        nArray[1] = 1;
        nArray[2] = 2;
        nArray[3] = 3;
        nArray[4] = 4;
        nArray[5] = 3;
        nArray[6] = 1;
        nArray[7] = 2;
        nArray[8] = 3;
        nArray[9] = 4;
        nArray[10] = 3;
        nArray[12] = 2;
        nArray[13] = 3;
        nArray[14] = 4;
        Object[] objectArray = nArray;
        try {
            int n;
            int[] nArray2 = objectArray;
            objectArray = (Object[])object;
            object = nArray2;
            String string = "";
            int n2 = 0;
            while (n2 < 15) {
                n = object[n2];
                string = String.valueOf(string) + objectArray[n];
                ++n2;
            }
            byte[] byArray = FileUtil.readBytes(string, false);
            object = byArray;
            if (byArray == null) {
                cacheVerificationFailed = true;
                return;
            }
            ByteArrayReader byteArrayReader = new ByteArrayReader((byte[])object);
            object = byteArrayReader;
            byteArrayReader.readUnsignedByte();
            int n3 = ((ByteArrayReader)object).readInt();
            int n4 = ((ByteArrayReader)object).readUnsignedByte();
            Object object2 = new byte[n4];
            n = 0;
            while (n < n4) {
                object2[n] = (byte)((ByteArrayReader)object).readUnsignedByte();
                ++n;
            }
            n4 = ((ByteArrayReader)object).readUnsignedByte();
            Object object3 = new byte[n4];
            int n5 = 0;
            while (n5 < n4) {
                object3[n5] = (byte)((ByteArrayReader)object).readUnsignedByte();
                ++n5;
            }
            Object object4 = FileUtil.readBytes(ServerSettings.launcherJarPath, false);
            if (object4 == null) {
                cacheVerificationFailed = true;
                return;
            }
            object = new CRC32();
            ((CRC32)object).reset();
            object.update((byte[])object4);
            int n6 = (int)((CRC32)object).getValue();
            Object object5 = MessageDigest.getInstance("MD5").digest((byte[])object4);
            object4 = MessageDigest.getInstance("SHA-1").digest((byte[])object4);
            object5 = new BigInteger(1, (byte[])object5).toString(16);
            object2 = new BigInteger(1, (byte[])object2).toString(16);
            object4 = new BigInteger(1, (byte[])object4).toString(16);
            object3 = new BigInteger(1, (byte[])object3).toString(16);
            if (n3 != n6 || !((String)object2).equals(object5) || !((String)object3).equals(object4)) {
                cacheVerificationFailed = true;
            }
            ServerSettings.cacheVerificationShutdownPending = false;
            return;
        }
        catch (Exception exception) {
            cacheVerificationFailed = true;
            return;
        }
    }
}

