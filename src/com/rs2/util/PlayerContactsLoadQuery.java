/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.util;

import com.rs2.model.player.Player;
import com.rs2.util.PlayerLoginLoadCallback;
import com.rs2.util.db.DatabaseQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

final class PlayerContactsLoadQuery
extends DatabaseQuery {
    private final /* synthetic */ Player player;

    PlayerContactsLoadQuery(PlayerLoginLoadCallback playerLoginLoadCallback, String string, Player player) {
        this.player = player;
        super(string);
    }

    @Override
    public final ResultSet executeStatement(PreparedStatement preparedStatement) {
        preparedStatement.setInt(1, this.player.getReferenceId());
        return preparedStatement.executeQuery();
    }
}

