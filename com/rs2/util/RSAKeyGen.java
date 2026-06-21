/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class RSAKeyGen {
    public static void main(String[] object) {
        try {
            object = KeyFactory.getInstance("RSA");
            Object object2 = KeyPairGenerator.getInstance("RSA");
            ((KeyPairGenerator)object2).initialize(1024);
            object2 = ((KeyPairGenerator)object2).genKeyPair();
            Object object3 = ((KeyPair)object2).getPrivate();
            object2 = ((KeyPair)object2).getPublic();
            object3 = ((KeyFactory)object).getKeySpec((Key)object3, RSAPrivateKeySpec.class);
            RSAKeyGen.writeRsaConstantsFile("./data/rsapriv", ((RSAPrivateKeySpec)object3).getModulus(), ((RSAPrivateKeySpec)object3).getPrivateExponent());
            object = ((KeyFactory)object).getKeySpec((Key)object2, RSAPublicKeySpec.class);
            RSAKeyGen.writeRsaConstantsFile("./data/rsapub", ((RSAPublicKeySpec)object).getModulus(), ((RSAPublicKeySpec)object).getPublicExponent());
            return;
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            return;
        }
    }

    private static void writeRsaConstantsFile(String object, BigInteger bigInteger, BigInteger bigInteger2) {
        try {
            object = new BufferedWriter(new FileWriter((String)object));
            ((Writer)object).write("private static final BigInteger RSA_MODULUS = new BigInteger(\"" + bigInteger.toString() + "\");");
            ((BufferedWriter)object).newLine();
            ((BufferedWriter)object).newLine();
            ((Writer)object).write("private static final BigInteger RSA_EXPONENT = new BigInteger(\"" + bigInteger2.toString() + "\");");
            ((BufferedWriter)object).newLine();
            ((BufferedWriter)object).flush();
            ((BufferedWriter)object).close();
            return;
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            return;
        }
    }
}

