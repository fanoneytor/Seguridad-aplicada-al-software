package test;

import cipher.SymmetricCipher;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import util.Util;

public class SymmetricCipherTest04 {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, IOException, ClassNotFoundException {
        SecretKey secretKey = null;
        
        secretKey = KeyGenerator.getInstance("DES").generateKey();
        
        SymmetricCipher s = new SymmetricCipher(secretKey, "DES/ECB/PKCS5Padding");
        
        ArrayList<String> clearObject = new ArrayList<String>();
        byte[] cipherObject = null;
        
        clearObject.add("Ana");
        clearObject.add("Bety");
        clearObject.add("Carolina");
        clearObject.add("Daniela");
        clearObject.add("Elena");
        clearObject.add("Fernanda");
        clearObject.add("Gabriela");
        clearObject.add("Helena");
        clearObject.add("Ines");
        clearObject.add("Juliana");
        
        System.out.println(clearObject);
        
        long timebefore = System.currentTimeMillis();
        long timebefore1 = System.nanoTime();
  
        cipherObject = s.encrypObject(clearObject);
        
        long timeafter = System.currentTimeMillis();
        long timeafter1 = System.nanoTime();
        
        System.out.println("Tiempo transcurrido en milisegundos: " + (timeafter - timebefore));
        System.out.println("Tiempo transcurrido en nanosegundos: " + (timeafter1 - timebefore1));
        
        
        Util.printByteArrayHexadecimal(cipherObject);
        
        clearObject = (ArrayList<String>) s.decryptObject(cipherObject);
        System.out.println(clearObject);        
    }    
}
