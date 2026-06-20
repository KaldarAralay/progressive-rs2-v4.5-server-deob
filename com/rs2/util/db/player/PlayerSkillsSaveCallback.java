/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.util.db.player;

import com.rs2.util.db.DatabaseCallback;
import java.sql.ResultSet;

final class PlayerSkillsSaveCallback
implements DatabaseCallback {
    PlayerSkillsSaveCallback() {
    }

    @Override
    public final void onResult(ResultSet resultSet) {
    }

    @Override
    public final void onException(Exception exception) {
        exception.printStackTrace();
    }
}

