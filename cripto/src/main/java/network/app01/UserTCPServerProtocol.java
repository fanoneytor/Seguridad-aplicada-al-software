package network.app01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class UserTCPServerProtocol{
    private static PrintWriter toNetwork;
    private static BufferedReader fromNetwork;
    
    private static HashMap <String, Integer> tabla = new HashMap <String, Integer>();    
    public static void protocol (Socket socket) throws IOException{
        createStreams(socket);
        
        String message = fromNetwork.readLine();
        System.out.println("[Server] From client: " + message);
        
        
        String nombre = "";
        String respuesta = "";
        //verificar que el mensaje recibido del cliente inicia con LOGIN
        if(message.startsWith("LOGIN") && message.contains(".INFORME") == false &&message.contains(".INFORME_DETALLADO") == false){
            nombre = message.split(" ")[1];
            
            /*
            String[] sa = message.split(" ");
            String nombre2 = sa[1];
            String nombre3 = message.substring(6);
            */
            
            //Si el nombre no esta registrado, "Bienvenido, Usted es el usuario #n"
            //Si el nombre esta registrado, "Acceso #k "
            if(tabla.containsKey(nombre)){
                int accesos = tabla.get(nombre);
                accesos++;
                tabla.replace(nombre, accesos);
                respuesta = "Acceso #" + accesos;
                toNetwork.println(respuesta);
            }else {
                tabla.put(nombre, 0);
                respuesta = "Bienvenido. Usted es el usuario #" + tabla.size();
                toNetwork.println(respuesta);
            }
        }else if(message.contains("tabla.INFORME_DETALLADO")){
            toNetwork.println(tabla);
        }else if(message.contains("tabla.INFORME")){
            toNetwork.println(tabla.keySet());
        }
    }
    
    private static void createStreams(Socket socket) throws IOException {
        toNetwork = new PrintWriter(socket.getOutputStream(), true);
        fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
}
