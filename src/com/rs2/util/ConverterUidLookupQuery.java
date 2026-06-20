/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.util;

import com.rs2.util.db.DatabaseQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

final class ConverterUidLookupQuery
extends DatabaseQuery {
    private final /* synthetic */ String username;

    ConverterUidLookupQuery(String string, String string2) {
        this.username = string2;
        super(string);
    }

    @Override
    public final ResultSet executeStatement(PreparedStatement preparedStatement) {
        preparedStatement.setString(1, this.username);
        return preparedStatement.executeQuery();
    }
}

