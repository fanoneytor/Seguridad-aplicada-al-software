package test;

import cipher.PublicKeyCipher;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import util.Util;
import util.Base64;


public class PublicKeyCipherTest01 {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        String algorithm = "RSA";
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        
        PublicKeyCipher a = new PublicKeyCipher(algorithm);
        String clearText = "Bienvenidos a la criptografia";
        System.out.println(clearText);
        byte[] cipherText = a.encryptMessage(clearText, publicKey);
        String cipherText64 = Base64.encode(cipherText);
        System.out.println(cipherText64 + "\n");
        
        
        clearText = a.decryptMessage(cipherText, privateKey);
        System.out.println(clearText);
        
        cipherText = a.encryptMessage(clearText, privateKey);
        cipherText64 = Base64.encode(cipherText);
        System.out.println(cipherText64 + "\n");
        
        clearText = a.decryptMessage(cipherText, publicKey);
        System.out.println(clearText + "\n");
        
        byte[] publicKey1 = Util.objectToByteArray(publicKey);
        String publicKey64 = Base64.encode(publicKey1);
        System.out.println("LLAVE PUBLICA: \n" + publicKey64 + "\n");
        
        byte[] privateKey1 = Util.objectToByteArray(privateKey);
        String privateKey64 = Base64.encode(privateKey1);
        System.out.println("LLAVE PRIVADA: \n" + privateKey64);
    }    
}
