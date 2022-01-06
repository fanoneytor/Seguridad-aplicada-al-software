package integrity;

import java.io.FileInputStream;
import java.security.MessageDigest;

public class Digest {
    public static byte[] getHashing(String input, String algorithm) throws Exception{
        byte[] inputBA = input.getBytes();
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(inputBA);
        return messageDigest.digest();
    }
    
    public static byte[] getHashingFile(String fileName, String algorithm) throws Exception{
        MessageDigest md = MessageDigest.getInstance(algorithm);
        
        FileInputStream in = new FileInputStream(fileName);
        byte[] buffer = new byte[1024];
        
        int length;
        while((length = in.read(buffer)) != -1){
            md.update(buffer, 0, length);
        }
        
        return md.digest();
    }
    
    public static boolean verifyDigest(byte[] digestA, byte[] digestB){
        if(digestA.length != digestB.length){
            return false;
        }
        
        for(int i = 0; i<digestA.length; i++){
            if(digestA[i] != digestB[i]){
                return false;
            }
        }
        return true;
    }
}
