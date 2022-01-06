package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class Util {
    public static void printByteArrayInt(byte[] byteArray) {
        System.out.println("{" + byteArrayIntToString(byteArray) + "}");
    }
    
    public static String byteArrayIntToString(byte[] byteArray) {
        String out = "";
        int i = 0;
        System.out.println("Longitud del byte "+byteArray.length + "\n");
        for(;i< byteArray.length - 1; i++){
            if (i % 10 == 0 && i != 0)
                out += "\n";            
            out += byteArray[i] + 128 + "\t";
        }
        out += byteArray[i] + 128;
        
        return out;
    }

    public static void guardar(Object o, String fileName) throws IOException {
        FileOutputStream fileOut;
        ObjectOutputStream out;
        
        fileOut = new FileOutputStream(fileName);
        out = new ObjectOutputStream(fileOut);
        
        out.writeObject(o);
        
        out.flush();
        out.close();
    }
    
    @SuppressWarnings("resource")
    public static Object leer(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fileIn;
        ObjectInputStream in;
        
        fileIn = new FileInputStream(fileName);
        in = new ObjectInputStream(fileIn);
        
        return in.readObject();
    }
    
    public static byte[] objectToByteArray(Object o) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(o);
        out.close();
        byte[] buffer = bos.toByteArray();
        
        return buffer;
    }
    
    public static Object byteArrayToObject(byte[] byteArray)throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(byteArray));
        Object o = in.readObject();
        in.close();
        
        return o;
    }
    
    public static void printByteArrayHexadecimal (byte[] byteArray) {
        System.out.println("{" + byteArrayHexadecimalToString(byteArray) + "}");
    }
    
    public static String byteArrayHexadecimalToString(byte[] byteArray){
        String out = "";
        int i = 0;
        System.out.println("Longitud del byte "+byteArray.length + "\n");
        for(; i < byteArray.length - 1; i++){
            if((byteArray[i] & 0xff) <= 0xf){
                out += "0";                
            }
            out += Integer.toHexString(byteArray[i] & 0xff) + " ";
        }
        out += Integer.toHexString(byteArray[i] & 0xff);
        
        return out;
    }
    
    public static String baToHexString(byte[] array){
        final StringBuilder hexString = new StringBuilder();
        for(int i=0; i<array.length; i++){
            final String hex = Integer.toHexString(0xff & array[i]);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
