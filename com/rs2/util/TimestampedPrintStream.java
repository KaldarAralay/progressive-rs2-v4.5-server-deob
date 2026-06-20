/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class TimestampedPrintStream
extends PrintStream {
    private BufferedWriter logWriter;
    private DateFormat dateFormat = new SimpleDateFormat();

    public TimestampedPrintStream(OutputStream outputStream, String string) {
        super(outputStream);
        this.logWriter = new BufferedWriter(new FileWriter(string, true));
    }

    public TimestampedPrintStream(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    public final void println(String object) {
        object = "[" + this.dateFormat.format(new Date()) + "]: " + (String)object;
        super.println((String)object);
        String string = object;
        object = this;
        try {
            if (((TimestampedPrintStream)object).logWriter == null) {
                return;
            }
            ((TimestampedPrintStream)object).logWriter.write(string);
            ((TimestampedPrintStream)object).logWriter.newLine();
            ((TimestampedPrintStream)object).logWriter.flush();
            return;
        }
        catch (IOException iOException) {
            object = iOException;
            iOException.printStackTrace();
            return;
        }
    }
}

