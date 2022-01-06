package cipher;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import util.Util;

public class SymmetricCipher {    
    private SecretKey secretKey;
    private Cipher cipher;    
    
    public SymmetricCipher(SecretKey secretKey, String transformation)            
            throws NoSuchAlgorithmException, NoSuchPaddingException{        
        this.secretKey = secretKey;        
        cipher = Cipher.getInstance(transformation);                
    }
    
    public byte[] encryptMessage(String input)
            throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        byte[] cipherText = null;
        byte[] clearText = input.getBytes();
        
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        cipherText = cipher.doFinal(clearText);
        
        return cipherText;
    }
    
    public String decryptMessage(byte[] input)
            throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        String output = "";
        
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] clearText = cipher.doFinal(input);
        output = new String(clearText);
        
        return output;
    }
    
    public SecretKey getKey(){
        return secretKey;
    }

    public byte[] encrypObject(Object input)
            throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] cipherObject = null;
        byte[] clearObject = null;
        
        clearObject = Util.objectToByteArray(input);
        
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        cipherObject = cipher.doFinal(clearObject);
        
        return cipherObject;
    }
    
    public Object decryptObject(byte[] input) throws InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, ClassNotFoundException, IOException {
        Object output = null;
        
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] clearObject = cipher.doFinal(input);
        
        output = Util.byteArrayToObject(clearObject);
        
        return output;
    }
}
