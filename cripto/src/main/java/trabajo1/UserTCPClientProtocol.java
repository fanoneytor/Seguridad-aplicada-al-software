package trabajo1;

import cipher.PublicKeyCipher;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
        //String llaves = "";
        
        //MENU
        int opcion;
        System.out.println("\nMENU DE OPCIONES");
        System.out.println("1. CREAR CUENTA");
        System.out.println("2. ENVIAR MENSAJE");
        System.out.println("3. LEER MENSAJE\n");
        System.out.println("Ingrese una opcion: ");
        opcion = SCANNER.nextInt();
        switch (opcion){
            case 1:{
                //Crear una cuenta: Solicitar el nombre de un usuario y su contrasena
                System.out.println("Ingrese el nombre de usuario: ");
                SCANNER.nextLine();
                String nombre = SCANNER.nextLine();

                System.out.println("Ingrese su contrasena: ");
                String clave = SCANNER.nextLine();

                //verifique si hay llaves publicas y privadas para el usuario

                //si no hay llaves, crearlas y guardarlas

                File f1 = new File("llavesCliente" + File.separator + nombre + ".pub");
                File f2 = new File("llavesCliente" + File.separator + nombre + ".pri");

                if(f1.exists() == false && f2.exists() == false){// Si las llaves no existen
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

                //Enviar un mensaje con el formato:

                String mensaje = "Crear" + ESPACIO + nombre + ESPACIO + clave + ESPACIO + pubKB64;
                toNetwork.println(mensaje);
                
                String respuesta = fromNetwork.readLine();
                System.out.println("[Cliente] Respuesta: " + respuesta);
                
                break;
            }
            case 2:{
                //La aplicación cliente solicita nombre de origen, destino y mensaje
                System.out.println("Nombre de usuario origen: ");
                SCANNER.nextLine();
                String nombreOrigen = SCANNER.nextLine();
                System.out.println("Nombre de usuario destino: ");
                String nombreDestino = SCANNER.nextLine();
                System.out.println("Mensaje: ");
                String mensaje = SCANNER.nextLine();
                
                //Verificar si martin.txt contiene llaves publicas de Jorge
                String archivo = nombreOrigen + ".txt";
                // Leer
                // Leer
                File file = new File(archivo);
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                
                // Escribir
                FileWriter fw = new FileWriter(archivo);
                PrintWriter pw = new PrintWriter(fw);
                
                String line;
                while((line = br.readLine()) != null) {
                    System.out.println("ENTRA");
                    String[] palabras = line.split(",");
                    //palabras[0] = nombre
                    //palabras[1] = public key
                    //La aplicacion verifica si martin.txt tiene la llave publica de jorge
                    System.out.println(palabras[0]);
                    if(palabras[0].equals(nombreDestino)){
                        String llavePublica = palabras[1];
                        byte[] llaveD64 = Base64.decode(llavePublica);
                        publicKey = (PublicKey) Util.byteArrayToObject(llaveD64);
                        break;
                    }
                }
                if(publicKey == null){
                    String pedirClave = "Obtener_PubK " + nombreDestino;
                    toNetwork.println(pedirClave);
                    //El usuarioOrigen recibe la llave publica del usuarioDestino y la guarda en el archivo usuarioOringen.txt
                    String llavePublica = fromNetwork.readLine();
                    System.out.println(llavePublica);
                    byte[] llaveD64 = Base64.decode(llavePublica);
                    publicKey = (PublicKey) Util.byteArrayToObject(llaveD64);
                    String formato = nombreDestino + "," + llavePublica; 
                    pw.println(formato);
                }
                fr.close();
                fw.close();
                
                //Se envia el mensaje
                PublicKeyCipher a = new PublicKeyCipher(algorithm);
                byte[] cipherMensaje = a.encryptMessage(mensaje, publicKey);
                String cipherMensaje64 = Base64.encode(cipherMensaje);
                String mensajeFull = "Enviar " + nombreOrigen + nombreDestino + cipherMensaje64;
                toNetwork.println(mensajeFull);
                
                String respuesta = fromNetwork.readLine();
                System.out.println("[Cliente] from server: " + respuesta);
                
                break;
            }
            case 3:{
                System.out.println("Usted eligió la opcion 3.");
                break;
            }
            default: {
                System.out.println("Opcion incorrecta");
            }
        }
        /*
        leer el usuario origen
        leer el usuario destino
        leer el mensaje a enviar
        leer el archivo nombre.txt donde nombre es el nombre del usuario origen
        buscar una linea que inicie con el nombre dle usuario destino 
        
        Caso 1 se tiene la llave publica
            obtener la linea completa que inicia con el nombre del usuario destino
            separar donde encuentre una "," usando split
            en la pocision 0 esta el nombre de usuario
            en la pocision 1 esta la llave publica en base 64
            decodificar la llave del usuario destino
            debe cifrar el mensaje con la llave publica del usuario destino
            codificar el mensaje en base 64
            debe formar el mensaje :
            Enviar martin jorge B64(EncrPUB-kjorge(mensjae))
            envia el mensaje
            recibe la respuesta
            imprime la respuesta
        
        caso 2 no se tiene la llave publica
            debe enviar un mensaje al servidor solicitando la llave publica del usuario
            con este formato: Obtener_Pubk_jorge
            recibe la respuesta: Pubk_jorge_B64(PubK)
            separar las tres partes de la respuesta
            obtiene la llave publica del ususario destino en la pocision 2
            agregue la linea en el archivo ******** si no la agrega siempre la pide porque no la va a encontrar        
            
            decodificar la llave del usuario destino
            debe cifrar el mensaje con la llave publica del usuario destino
            codificar el mensaje en base 64
            debe formar el mensaje :
            Enviar martin jorge B64(EncrPUB-kjorge(mensjae))
            envia el mensaje
            recibe la respuesta
            imprime la respuesta
        */
    }

    private static void createStreams(Socket socket) throws IOException {
        toNetwork = new PrintWriter(socket.getOutputStream(), true);
        fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
}
