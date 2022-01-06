package test;

import java.io.*;
import java.util.ArrayList;

import other.Person;
import util.*;


public class Base64Test02 {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {		
		Person Marcos = new Person("Marcos", 29, 1.78);		
		System.out.println(Marcos.toString());
		
		byte[] ba = Util.objectToByteArray(Marcos);
		System.out.println("El objeto ha sido serializado");
		
		String b64 = Base64.encode(ba);
		System.out.println("El objeto ha sido codificado en base64" + b64);
		
		byte[] ba2 = Base64.decode(b64);
		System.out.println("La cadena Base64 ha sido decodificada ");
		
		Person Doe = (Person) Util.byteArrayToObject(ba2);
                System.out.println(Doe);
	}
}