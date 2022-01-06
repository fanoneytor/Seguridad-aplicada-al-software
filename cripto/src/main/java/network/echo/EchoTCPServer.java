package network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoTCPServer {
    public static final int PORT  = 3400;
    
    private ServerSocket listener;
    private Socket serverSideSocket;
    private PrintWriter writer;
    private BufferedReader reader;
    
    public EchoTCPServer() throws IOException {
        System.out.println("ECHO TCP SERVER ...");
        
        listener = new ServerSocket(PORT);
        
        while(true){
            System.out.println("The ECHO TCT SERVER is waiting for a client ...");
            serverSideSocket = listener.accept();
            
            createStreams();
            EchoTCPServerProtocol.protocol(reader, writer);
        }
    }
    
    private void createStreams() throws IOException {
        writer = new PrintWriter(serverSideSocket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(serverSideSocket.getInputStream()));
    }
    
    public static void main(String[] args) throws IOException {
        new EchoTCPServer();
    }
}
