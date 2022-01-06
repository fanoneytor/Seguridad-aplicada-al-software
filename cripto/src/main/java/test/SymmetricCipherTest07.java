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


public class SymmetricCipherTest07 {
	public static void main(String[] args)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, ClassNotFoundException, IOException {
		

		// Cargar la llave secreta que está almaceanada en disco
		SecretKey secretKey = (SecretKey) Util.leer("llaveSimetrica2.key");
		System.out.println("La llave simetrica ha sido recuperada");
		
		
		// Iniciar el cifrador
		SymmetricCipher s = new SymmetricCipher(secretKey, "DES/ECB/PKCS5Padding");
		
		
		// Leer
		File file = null;
		FileReader fr = null;
		BufferedReader br = null;
		
		file = new File("cifrado.txt");
		fr = new FileReader(file);
		br = new BufferedReader(fr);
		
		// Escribir
		
		FileWriter fw = null;
		PrintWriter pw = null;
		
		fw = new FileWriter("decifrado.txt");
		pw = new PrintWriter(fw);
		
		String line;
		while((line = br.readLine()) != null) {
			
			String clearText = line;
			// Texto claro
			System.out.println("Texto leído del archivo: " + clearText);
			// Decodificar el contenido de esa línea
			byte[] ba = Base64.decode(clearText);
			// Desencriptar el contenido de esa línea
			clearText = s.decryptMessage(ba);
			// Guardar el String con el texto claro
			pw.println(clearText);
			
			
		}
		
		fr.close();
		fw.close();
		
		
		
		
	}

}