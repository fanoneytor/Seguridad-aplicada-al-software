package trabajo1;

import network.app01.*;
import java.net.Socket;

public class UserTCPClient {
    public static final int PORT = 3400;
    public static final String SERVER = "localhost";
    //public static final int PORT = 16795;
   //public static final String SERVER = "0.tcp.ngrok.io";
    
    private Socket clientSideSocket;
    
    public UserTCPClient() {
        System.out.println("echo TCP client is running...");
    }
    
    public void init() throws Exception {
        while(true){
            clientSideSocket = new Socket(SERVER, PORT);

            UserTCPClientProtocol.protocol(clientSideSocket);

            clientSideSocket.close();
        }
    }
    
    public static void main(String[] args) throws Exception{
        UserTCPClient ec = new UserTCPClient();
        ec.init();
    }
}
