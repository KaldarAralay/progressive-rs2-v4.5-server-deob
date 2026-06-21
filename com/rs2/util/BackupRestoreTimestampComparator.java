/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.util;

import com.rs2.model.GameplayHelper;
import java.io.File;
import java.util.Comparator;

final class BackupRestoreTimestampComparator
implements Comparator {
    BackupRestoreTimestampComparator() {
    }

    public final /* synthetic */ int compare(Object stringArray, Object stringArray2) {
        stringArray2 = (File)stringArray2;
        stringArray = (File)stringArray;
        stringArray = stringArray.getName().split("-");
        stringArray2 = stringArray2.getName().split("-");
        long l = GameplayHelper.buildTimestampMillis(Integer.parseInt(stringArray[0]), Integer.parseInt(stringArray[1]), Integer.parseInt(stringArray[2]), Integer.parseInt(stringArray[3]), Integer.parseInt(stringArray[4]));
        long l2 = GameplayHelper.buildTimestampMillis(Integer.parseInt(stringArray2[0]), Integer.parseInt(stringArray2[1]), Integer.parseInt(stringArray2[2]), Integer.parseInt(stringArray2[3]), Integer.parseInt(stringArray2[4]));
        return Long.compare(l2, l);
    }
}

