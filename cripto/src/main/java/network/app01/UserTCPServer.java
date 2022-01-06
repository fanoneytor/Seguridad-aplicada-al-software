package network.app01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class UserTCPServer {
    public static final int PORT  = 3400;
    
    private ServerSocket listener;
    private Socket serverSideSocket;
        
    public UserTCPServer(){
        System.out.println("Echo TCP server is running on port: " + PORT);
    }
    
    protected void init() throws IOException{
        listener = new ServerSocket (PORT);
        
        while(true){
            serverSideSocket = listener.accept();
            
            String ip = serverSideSocket.getInetAddress().getHostAddress();
            System.out.println("Direccion del cliente" + ip);
            int puerto = serverSideSocket.getPort();
            System.out.println("Numero de puerto en el cliente: " + puerto);
            
            //if(ip.equals("127.0.0.1")){
            //    break;            
            //}
            
            UserTCPServerProtocol.protocol(serverSideSocket);
        }
    }
    
    public static void main(String[] args) throws IOException {
        UserTCPServer es = new UserTCPServer();
        es.init();
    }
}
