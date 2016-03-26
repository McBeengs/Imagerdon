/* **********   PasswordManager.java   **********
 *
 * This piece of garbage was brought to you by nothing less than the almighty lord
 * of programming, the Java God and ruler of all the non living things, McBeengs, 
 * A.K.A. myself. I don't mind anyone steal or using my codes at their own business,
 * but at least, and I meant VERY least, give me the proper credit for it. I really
 * don't know what the code below does at this point in time while I write this stuff, 
 * but if you took all this time to sit, rip the .java files and read all this 
 * unnecessary bullshit, you know for what you came, doesn't ?
 * 
 * Copyright(c) {YEAR!!!} Mc's brilliant mind. All Rights (kinda) Reserved.
 */

 /*
 * {Insert class description here}
 */
package com.util.crypto;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class PasswordManager {

    private Cipher cipher;

    public PasswordManager() {
        try {
            cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
            Logger.getLogger(PasswordManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public byte[] encrypt(String dataToEncrypt, byte[] initialValue, byte[] secretKey) {
        try {
            if (dataToEncrypt.length() % 8 != 0) {
                for (int i = 0; i < (dataToEncrypt.length() % 8); i++) {
                    dataToEncrypt += " ";
                }
            }
            
            SecretKeySpec key = new SecretKeySpec(secretKey, "DES");
            IvParameterSpec ivSpec = new IvParameterSpec(initialValue);
            byte[] bytes = dataToEncrypt.getBytes();

            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            byte[] encrypted = new byte[cipher.getOutputSize(bytes.length)];
            int enc_len = cipher.update(bytes, 0, bytes.length, encrypted, 0);
            enc_len += cipher.doFinal(encrypted, enc_len);

            return encrypted;

        } catch (InvalidKeyException | InvalidAlgorithmParameterException | ShortBufferException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(PasswordManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String decrypt(byte[] dataToDecrypt, byte[] initialValue, byte[] secretKey) {
        try {
            SecretKeySpec key = new SecretKeySpec(secretKey, "DES");
            IvParameterSpec ivSpec = new IvParameterSpec(initialValue);

            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
            byte[] decrypted = new byte[cipher.getOutputSize(dataToDecrypt.length)];
            int enc_len = cipher.update(dataToDecrypt, 0, dataToDecrypt.length, decrypted, 0);
            enc_len += cipher.doFinal(decrypted, enc_len);

            return new String(decrypted, StandardCharsets.UTF_8).trim();

        } catch (InvalidKeyException | InvalidAlgorithmParameterException | ShortBufferException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(PasswordManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public byte[] stringToByte(String dataToConvert) {
        byte[] array = new byte[100];
        int c = 0;

        if (dataToConvert.startsWith("[")) {
            dataToConvert = dataToConvert.substring(1);
        }

        if (dataToConvert.endsWith("]")) {
            dataToConvert = dataToConvert.substring(0, dataToConvert.length() - 1);
        }

        while (true) {
            int pos = dataToConvert.indexOf(", ");
            array[c] = Byte.parseByte(dataToConvert.substring(0, pos));
            dataToConvert = dataToConvert.substring(pos + 2);
            c++;

            if (!dataToConvert.contains(", ")) {
                array[c] = Byte.parseByte(dataToConvert);
                break;
            }
        }

        c = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                break;
            }

            c++;
        }

        byte[] send = new byte[c];
        System.arraycopy(array, 0, send, 0, c);

        return send;
    }
}
