package test;

import integrity.Digest;
import util.Util;

public class IntegrityTest01 {
    
    public static void main(String[] args) throws Exception{
        String mensaje = "Curso de Seguridad aplicada al software";
        String mensaje1 = "Curso de Seguridad aplicada al software";
        String mensaje2 = "Curso de seguridad aplicada al software";
        
        System.out.println(Util.baToHexString(Digest.getHashing(mensaje, "MD5")));
        System.out.println(Util.baToHexString(Digest.getHashing(mensaje, "SHA-1")));
        System.out.println(Util.baToHexString(Digest.getHashing(mensaje, "SHA-256")) + "\n");
        
        System.out.println(Util.baToHexString(Digest.getHashing(mensaje1, "MD5")));
        System.out.println(Util.baToHexString(Digest.getHashing(mensaje1, "SHA-1")));
        System.out.println(Util.baToHexString(Digest.getHashing(mensaje1, "SHA-256")) + "\n");
        
        System.out.println(Util.baToHexString(Digest.getHashing(mensaje2, "MD5")));
        System.out.println(Util.baToHexString(Digest.getHashing(mensaje2, "SHA-1")));
        System.out.println(Util.baToHexString(Digest.getHashing(mensaje2, "SHA-256")) + "\n");
        
        String filename = "Scan.pdf";
        System.out.println(Util.baToHexString(Digest.getHashing(filename, "MD5")));
        System.out.println(Util.baToHexString(Digest.getHashing(filename, "SHA-1")));
        System.out.println(Util.baToHexString(Digest.getHashing(filename, "SHA-256")));
    }
    
}
