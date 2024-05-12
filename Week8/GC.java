import java.net.*;
import java.io.*;

public class GC {
//Note that GC is only one used in Week8 with connecting to server with having server first ./ds-server -p 59791 -c ds-sample-config01.xml -n -v all
//and then javac GC.java on client, then java GC on client
//GC java code sends and receives responses to ds-server, then quits
public static void main(String[] args){
    try{
    Socket clientSocket = new Socket("127.0.0.1", 59791);
    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    
    //client first sending hello to server
    System.out.println("Sending: HELO");
    out.println("HELO");

    //client receiving response from server
    String resp = in.readLine();
    System.out.println("Received "+ resp);
    //client verifying if reponse from server == OK and sends AUTH to server (Sending done by out.println)
        if("OK".equals(resp)){
            System.out.println("Sending: AUTH mq46479791");
            out.println("AUTH mq46479791");
        }
        else {
            out.println("unrec greeting");
        }

//after reading "ds-system.xml"
    resp = in.readLine();
    System.out.println("Received "+ resp);
    
        if("OK".equals(resp)){
            System.out.println("Sending: REDY");
            out.println("REDY");
        }
        else {
            out.println("unrec greeting");
        }

//read JOBN message & quit
    resp = in.readLine();
    System.out.println("Received "+ resp);
    System.out.println("Sending: QUIT");
    out.println("QUIT");


    in.close();
    out.close();
    clientSocket.close();
    } catch(Exception e){
        System.out.println("client try catch error");
    }
}
}