package network.publiccripto;

import cipher.PublicKeyCipher;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;
import util.Base64;
import util.Util;

public class UserTCPClientProtocol {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String ESPACIO = " ";
    
    private static PrintWriter toNetwork;
    private static BufferedReader fromNetwork;
    
    public static void protocol(Socket socket) throws Exception {
        createStreams(socket);        
        PublicKey publicKey = null;
        PrivateKey privateKey = null;
        String algorithm = "RSA";
        
        System.out.println("Ingrese el nombre de usuario: ");
        String nombre = SCANNER.nextLine();
        
        File f1 = new File("llavesCliente" + File.separator + nombre + ".pub");
        File f2 = new File("llavesCliente" + File.separator + nombre + ".pri");

        if(f1.exists() == false || f2.exists() == false){// Si las llaves no existen
            //crea el par de llaves
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();
            System.out.println("El par de llaves ha sido creado");

            Util.guardar(publicKey, "llavesCliente" + File.separator + nombre + ".pub");
            System.out.println("La llave publica ha sido guardada");        
            Util.guardar(privateKey, "llavesCliente" + File.separator + nombre + ".pri");
            System.out.println("La llave privada ha sido guardada");
        } else {// Si las llaves existen carguelas
            publicKey = (PublicKey) Util.leer("llavesCliente" + File.separator + nombre + ".pub");
            System.out.println("La llave publica ha sido recuperada");
            privateKey = (PrivateKey) Util.leer("llavesCliente" + File.separator + nombre + ".pri");
            System.out.println("La llave privada ha sido recuperada");
        }
        
        byte[] pubKBA = Util.objectToByteArray(publicKey);
        String pubKB64 = Base64.encode(pubKBA);

        toNetwork.println(pubKB64);
        
        //Enviamos un mensaje cifrado con la llave privada y codificado en base64
        PublicKeyCipher a = new PublicKeyCipher(algorithm);
        System.out.println("Ingrese el mensaje: ");
        String mensaje = SCANNER.nextLine();
        byte[] cipherText = a.encryptMessage(mensaje, privateKey);
        String cipherText64 = Base64.encode(cipherText);        
        toNetwork.println(cipherText64);
        
        //La aplicación cliente decodifica la llave pública del servidor.
        String llaveServerB64 = fromNetwork.readLine();
        byte[] llaveServerD64 = Base64.decode(llaveServerB64);
        PublicKey llaveServerD = (PublicKey) Util.byteArrayToObject(llaveServerD64);
        
        //Recibimos el mensaje cifrado con la llave privada del servidor y lo desencriptamos con la llave publica
        String mensajeB64 = fromNetwork.readLine();
        byte[] mensajeCifrado = Base64.decode(mensajeB64);
        String mensajeD = a.decryptMessage(mensajeCifrado, llaveServerD);
        System.out.println("[Cliente] from server: " + mensajeD);
    }

    private static void createStreams(Socket socket) throws IOException {
        toNetwork = new PrintWriter(socket.getOutputStream(), true);
        fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
}
