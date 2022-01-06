package test;

import java.security.*;
import java.util.Scanner;

import javax.crypto.*;
import cipher.SymmetricCipher;
import util.Base64;
import util.Util;

public class SymmetricCipherTest05 {
	public static void main(String[] args)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		SecretKey secretKey = null;
		
		secretKey = KeyGenerator.getInstance("DES").generateKey();
		
		System.out.println("Ingrese un mensaje a cifrar: ");
		
		
		SymmetricCipher s = new SymmetricCipher(secretKey, "DES/ECB/PKCS5Padding");
		String clearText = new Scanner(System.in).nextLine();
		
		byte[] cipherText = null;
		
		System.out.println(clearText);
		
		cipherText = s.encryptMessage(clearText);
		System.out.println("El texto ha sido cifrado ");
		
		// Codificar en b64
		String b64 = Base64.encode(cipherText);
		System.out.println("El texto ha sido codificado ");
		
		//Imprimir la cadena codificada
		System.out.println(b64);
		
		//Decodificar
		
		byte[] ba = Base64.decode(b64);
		System.out.println("El texto ha sido decodificado ");
		
		Util.printByteArrayInt(cipherText);
		
		clearText = s.decryptMessage(ba);
		
		//Imprimir el texto descifrado
		System.out.println("Texto claro: " + clearText);
	}
	
}