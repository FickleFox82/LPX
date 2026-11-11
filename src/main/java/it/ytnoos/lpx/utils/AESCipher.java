/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.utils;

import it.ytnoos.lpx.utils.SecurityUtils;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AESCipher {
    private final byte[] key;
    private final SecretKeySpec secretKeySpec;
    private final Cipher cipher;

    public AESCipher(String string) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this(string.getBytes());
    }

    public AESCipher(byte[] byArray) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.key = SecurityUtils.g7(byArray);
        this.secretKeySpec = new SecretKeySpec(this.key, "AES");
        this.cipher = Cipher.getInstance("AES");
    }

    public byte[] deriveKey(String string) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return this.p3(string.getBytes());
    }

    public byte[] p3(byte[] byArray) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        this.cipher.init(1, this.secretKeySpec);
        return this.cipher.doFinal(byArray);
    }

    public byte[] p7(byte[] byArray) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        this.cipher.init(2, this.secretKeySpec);
        return this.cipher.doFinal(byArray);
    }
}

