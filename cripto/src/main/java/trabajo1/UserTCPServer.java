package trabajo1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;


public class UserTCPServer {
    public static final int PORT  = 3400;
    
    private ServerSocket listener;
    private Socket serverSideSocket;
        
    public UserTCPServer(){
        System.out.println("Echo TCP server is running on port: " + PORT);
    }
    
    protected void init() throws IOException, NoSuchAlgorithmException, ClassNotFoundException{
        listener = new ServerSocket (PORT);
        
        while(true){
            System.out.println("[Server] esperando un cliente...");
            serverSideSocket = listener.accept();
            
            UserTCPServerProtocol.protocol(serverSideSocket);
        }
    }
    
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        UserTCPServer es = new UserTCPServer();
        es.init();
    }
}
