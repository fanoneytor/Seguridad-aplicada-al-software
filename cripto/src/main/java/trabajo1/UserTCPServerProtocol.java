package trabajo1;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import util.Base64;
import util.Util;

public class UserTCPServerProtocol{
    private static PrintWriter toNetwork;
    private static BufferedReader fromNetwork;
    
    private static HashMap <String, Usuario> tabla = new HashMap <String, Usuario>();
    
    public static void protocol (Socket socket) throws IOException, ClassNotFoundException{
        createStreams(socket);
        PublicKey publicKey = null;
        PrivateKey privateKey = null;
        
        String respuesta = "";
        String entrada = fromNetwork.readLine();
        System.out.println("[Server] From client: " + entrada);
        
        //La aplicación servidor identifica el servicio a ofrecer: inicia con la cadena Crear.
        if(entrada.startsWith("Crear")){
            //separar las partes de la entrada
            String[] sa = entrada.split(" ");
            
            //sa[0] : Crear
            //sa[1] : nombre
            //sa[2] : clave
            //sa[3] : llave publica
            
            Usuario u = new Usuario(sa[2], sa[3]);
            tabla.put(sa[1], u);
            
            respuesta = "Bienvenido #" + sa[1];
            toNetwork.println(respuesta);
        }else if(entrada.startsWith("Obtener_PubK")){
            //separar las partes de la entrada
            String[] sa = entrada.split(" ");
            //sa[0] : Obtener_Pubk
            //sa[1] : nombre
            
            //El servidor carga la llave publica del UsuarioDestino            
            publicKey = (PublicKey) Util.leer("llavesCliente" + File.separator + sa[1] + ".pub");
            System.out.println("La llave publica ha sido recuperada");
            
            //El servidor envia al usuarioOrigen la llave publica del ususarioDestino
            byte[] pubKBA = Util.objectToByteArray(publicKey);
            String pubKB64 = Base64.encode(pubKBA);
            toNetwork.println(pubKB64);            
        }else if(entrada.startsWith("Enviar")){
            //separar las partes de la entrada
            String[] sa = entrada.split(" ");
            //sa[0] : Enviar
            //sa[1] : usuarioOrigen
            //sa[2] : usuarioDestino
            //sa[3] : mensaje
            String mensaje = sa[1] + ":" + sa[3]; 
            
            Usuario usuario = tabla.get(sa[2]);
            ArrayList<String> l = usuario.getLista();
            l.add(mensaje);
            System.out.println(tabla);
            
            toNetwork.println("El mensaje ha sido guardado exitosamente");
        }
    }
    
    private static void createStreams(Socket socket) throws IOException {
        toNetwork = new PrintWriter(socket.getOutputStream(), true);
        fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
}
