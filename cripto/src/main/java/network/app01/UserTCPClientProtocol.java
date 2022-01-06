package network.app01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class UserTCPClientProtocol {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String ESPACIO = " ";
    
    private static PrintWriter toNetwork;
    private static BufferedReader fromNetwork;
    
    public static void protocol(Socket socket) throws Exception {
        createStreams(socket);
        
        System.out.println("Ingrese un mensaje: ");
        String fromUser = SCANNER.nextLine();
        
        fromUser = "LOGIN" + ESPACIO + fromUser;
        
        toNetwork.println(fromUser);
        
        String fromServer =fromNetwork.readLine();
        System.out.println("[Client] From server: " + fromServer);
    }

    private static void createStreams(Socket socket) throws IOException {
        toNetwork = new PrintWriter(socket.getOutputStream(), true);
        fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
}
