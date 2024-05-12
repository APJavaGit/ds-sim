import java.net.*;
import java.io.*;

public class GS {

public static void main(String[] args){

    try{
        ServerSocket serverSocket = new ServerSocket(59791);
        Socket clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String greeting = in.readLine();
        System.out.println(greeting);
        if("HELO".equals(greeting)){
            out.println("G'DAY");
        }
        else {
            out.println("unrec greeting");
        }

        greeting = in.readLine();
        System.out.println(greeting);
        if("BYE".equals(greeting)){
            out.println("BYE");
        }
        else {
            out.println("unrec greeting");
        }
    

        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
        }
        catch(Exception e){
            System.out.println("server try catch error");
        }
    }

}

