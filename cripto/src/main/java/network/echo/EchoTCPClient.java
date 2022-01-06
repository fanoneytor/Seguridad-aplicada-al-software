package network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoTCPClient {
    public static final int PORT = 4200;
    public static final String SERVER = "localhost";
    //public static final int PORT = 16795;
   //public static final String SERVER = "0.tcp.ngrok.io";
    
    private Socket clientSideSocket;
    private PrintWriter writer;
    private BufferedReader reader;

    public EchoTCPClient() throws UnknownHostException, IOException {
        System.out.println("ECHO TCP CLIENT ...");
        
        clientSideSocket = new Socket(SERVER, PORT);
        createStreams();
        EchoTCPClientProtocol.protocol(reader, writer);
        
        if(reader != null){ reader.close();}
        if(writer != null){ writer.close();}
        if(clientSideSocket != null){ clientSideSocket.close();}
    }
    
    public void createStreams() throws IOException {
        writer = new PrintWriter(clientSideSocket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(clientSideSocket.getInputStream()));
    }
    
    public static void main(String[] args){
        try{
            new EchoTCPClient();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
