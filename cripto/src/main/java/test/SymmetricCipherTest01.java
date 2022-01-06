package test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.KeyGenerator;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import cipher.SymmetricCipher;
import util.Util;

public class SymmetricCipherTest01 {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        SecretKey secretKey = null;
        
        secretKey = KeyGenerator.getInstance("DES").generateKey();
        
        SymmetricCipher s = new SymmetricCipher(secretKey, "DES/ECB/PKCS5Padding");
        
        String clearText = "En la criptografia de la llave simetrica se utiliza "
                + "la misma llave para cifrar y descifrar.";
        
        byte[] cipherText = s.encryptMessage(clearText);
        
        
        long timebefore = System.currentTimeMillis();
        long timebefore1 = System.nanoTime();
  
        System.out.println(clearText); 
        
        long timeafter = System.currentTimeMillis();
        long timeafter1 = System.nanoTime();
        
        System.out.println("Tiempo transcurrido en milisegundos: " + (timeafter - timebefore));
        System.out.println("Tiempo transcurrido en nanosegundos: " + (timeafter1 - timebefore1));
        
        Util.printByteArrayInt(cipherText);
        
        clearText = s.decryptMessage(cipherText);
        
        System.out.println(clearText);
    }
}
