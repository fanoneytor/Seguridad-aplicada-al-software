package network.echo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class EchoTCPServerProtocol{
    public static void protocol (BufferedReader input, PrintWriter output) throws IOException{
        System.out.println("Connection incoming ...");
        
        String clientMessage = input.readLine();
        System.out.println("From client: " + clientMessage);
        
        String answer = clientMessage;
        
        System.out.println("Sent to client: " + answer);
        output.println(answer);
    }
}
