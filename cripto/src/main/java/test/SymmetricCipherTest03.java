package test;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import cipher.SymmetricCipher;
import util.Util;

public class SymmetricCipherTest03 {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, IOException, ClassNotFoundException{
        
        SecretKey secretKey = (SecretKey) Util.leer("llaveSimetrica.key");
        System.out.println("La llave simetrica ha sido recuperada");
        
        byte[] cipherText = (byte[]) Util.leer("texto.cif");
        System.out.println("El texto cifrado ha sido recuperado");
        
        SymmetricCipher s = new SymmetricCipher(secretKey, "DES/ECB/PKCS5Padding");
        
        String clearText2 = s.decryptMessage(cipherText);
        System.out.println(clearText2);
    }   
}
