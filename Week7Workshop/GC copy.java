import java.net.*;
import java.io.*;

public class GC {

public static void main(String[] args){
    try{
    Socket clientSocket = new Socket("127.0.0.1", 59791);
    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    
    out.println("HELO");
    String resp = in.readLine();
    System.out.println(resp);
    
        if("G'DAY".equals(resp)){
            out.println("BYE");
        }
        else {
            out.println("unrec greeting");
        }

        resp = in.readLine();
        System.out.println(resp);
        if("BYE".equals(resp)){
            out.println("BYE");
        }
        else {
            out.println("unrec greeting");
        }

    in.close();
    out.close();
    clientSocket.close();
    } catch(Exception e){
        System.out.println("client try catch error");
    }
}
}