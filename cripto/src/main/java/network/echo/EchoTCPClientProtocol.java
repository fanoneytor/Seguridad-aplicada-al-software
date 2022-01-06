package network.echo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
public class EchoTCPClientProtocol {
    private static final Scanner SCANNER = new Scanner(System.in);
    
    public static void protocol(BufferedReader reader, PrintWriter writer) throws IOException {
        System.out.println("Ingrese un mensaje: ");
        String fromUser = SCANNER.nextLine();
    
        writer.println(fromUser);
        
        String fromServer = reader.readLine();
        System.out.println("FROM SERVER: " + fromServer);
    }    
}
