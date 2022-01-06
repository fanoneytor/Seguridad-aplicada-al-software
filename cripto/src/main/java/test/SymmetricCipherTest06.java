package test;

import java.io.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import cipher.SymmetricCipher;
import util.Base64;
import util.Util;


public class SymmetricCipherTest06 {    
    public static void main(String[] args)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, ClassNotFoundException, IOException {
        
        

        //Generar una llave secreta
        SecretKey secretKey = KeyGenerator.getInstance("DES").generateKey() ;
        // Iniciar el cifrador
        SymmetricCipher s = new SymmetricCipher(secretKey, "DES/ECB/PKCS5Padding");


        // Guardar la llave en un archivo
        Util.guardar(secretKey, "llaveSimetrica2.key");
        System.out.println("La llave simetrica ha sido guardada");

        // Leer
        File file = null;
        FileReader fr = null;
        BufferedReader br = null;

        file = new File("texto.txt");
        fr = new FileReader(file);
        br = new BufferedReader(fr);

        // Escribir

        FileWriter fw = null;
        PrintWriter pw = null;

        fw = new FileWriter("cifrado.txt");
        pw = new PrintWriter(fw);

        String line;
        while((line = br.readLine()) != null) {
            String clearText = line;
            // Texto claro
            System.out.println("Texto leído del archivo: " + clearText);

            //Encriptar texto
            byte[] cipherText = s.encryptMessage(clearText);
            //Util.printByteArrayInt(cipherText);

            // Codificar en b64
            String b64 = Base64.encode(cipherText);
            //System.out.println("El texto ha sido codificado en b64: ");

            pw.println(b64);
            System.out.println(b64);
            System.out.println(line);

        }

        fr.close();
        fw.close();
        //System.out.println(clearText);
        //byte[] cipherText = s.encryptMessage(clearText);

        //Util.printByteArrayInt(cipherText);
    }

}