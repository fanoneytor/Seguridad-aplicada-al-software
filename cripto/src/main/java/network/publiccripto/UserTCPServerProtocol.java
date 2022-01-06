package network.publiccripto;

import cipher.PublicKeyCipher;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import util.Base64;
import util.Util;

public class UserTCPServerProtocol{
    private static PrintWriter toNetwork;
    private static BufferedReader fromNetwork;
    
    private static HashMap <String, Integer> tabla = new HashMap <String, Integer>();    
    public static void protocol (Socket socket) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        createStreams(socket);
        PublicKey publicKey = null;
        PrivateKey privateKey = null;
        String algorithm = "RSA";
        
        File f1 = new File("llavesServidor" + File.separator + "servidor" + ".pub");
        File f2 = new File("llavesServidor" + File.separator + "servidor" + ".pri");

        if(f1.exists() == false && f2.exists() == false){// Si las llaves no existen
            //crea el par de llaves
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();
            System.out.println("El par de llaves ha sido creado");

            Util.guardar(publicKey, "llavesCliente" + File.separator + "servidor" + ".pub");
            Util.guardar(publicKey, "llavesServidor" + File.separator + "servidor" + ".pub");
            System.out.println("La llave publica ha sido guardada");        
            Util.guardar(privateKey, "llavesServidor" + File.separator + "servidor" + ".pri");
            System.out.println("La llave privada ha sido guardada");
        } else {// Si las llaves existen carguelas
            publicKey = (PublicKey) Util.leer("llavesServidor" + File.separator + "servidor" + ".pub");
            System.out.println("La llave publica ha sido recuperada");
            privateKey = (PrivateKey) Util.leer("llavesServidor" + File.separator + "servidor" + ".pri");
            System.out.println("La llave privada ha sido recuperada");
        }
        
        //Se recibe la llave publica del usuario codificada en base 64
        String llaveB64 = fromNetwork.readLine();
        //System.out.println("[Server] From client: " + llaveB64);
        
        //Estamos decodificando la llave
        byte[] llaveD64 = Base64.decode(llaveB64);
        PublicKey llaveD = (PublicKey) Util.byteArrayToObject(llaveD64);
        
        //Recibimos el mensaje cifrado con la llave privada del cliente y lo desencriptamos con la llave publica
        PublicKeyCipher a = new PublicKeyCipher(algorithm);
        String mensajeB64 = fromNetwork.readLine();
        byte[] mensajeCifrado = Base64.decode(mensajeB64);
        String mensajeD = a.decryptMessage(mensajeCifrado, llaveD);
        System.out.println("[Server] from cliente: " + mensajeD);
        
        //La aplicación servidor envía al cliente su llave pública, codificada en Base64.
        byte[] pubKBA = Util.objectToByteArray(publicKey);
        String pubKB64 = Base64.encode(pubKBA);
        toNetwork.println(pubKB64);
        
        //La aplicación servidor envía al cliente el mensaje encriptado, codificado en Base64.
        byte[] mensaje = a.encryptMessage(mensajeD, privateKey);
        String cipherText64 = Base64.encode(mensaje);        
        toNetwork.println(cipherText64);
    }
    
    private static void createStreams(Socket socket) throws IOException {
        toNetwork = new PrintWriter(socket.getOutputStream(), true);
        fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
}
