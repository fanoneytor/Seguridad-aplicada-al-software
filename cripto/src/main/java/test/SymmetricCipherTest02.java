package test;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import cipher.SymmetricCipher;
import util.Util;

public class SymmetricCipherTest02 {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, IOException{
        SecretKey secretKey = KeyGenerator.getInstance("DES").generateKey();
        
        SymmetricCipher s = new SymmetricCipher(secretKey, "DES/ECB/PKCS5Padding");
        
        String clearText = "En la criptografia de la llave simetrica se utiliza "
                + "la misma llave para cifrar y descifrar";
        
        System.out.println(clearText);
        byte[] cipherText = s.encryptMessage(clearText);
        
        Util.printByteArrayInt(cipherText);
        
        Util.guardar(secretKey, "llavesimetrica.key");
        System.out.println("La llave simetrica ha sido guardada");
        
        Util.guardar(cipherText, "texto.cif");
        System.out.println("El texto cifrado ha sido guardado");        
    }    
}
