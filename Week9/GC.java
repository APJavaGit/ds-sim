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

//read JOBN message
    resp = in.readLine();
    System.out.println("Received "+ resp);

//sending GETS
    System.out.println("Sending: GETS");
    out.println("GETS Capable 2 900 2500");

//read DATA message
    resp = in.readLine();
    System.out.println("Received "+ resp);

//sending OK
    System.out.println("Sending: OK");
    out.println("OK");

//read all lines from Server and print out Server state Info 
String[] responses = new String[5];
    for(int i = 0; i<5;i++){
    resp = in.readLine();
    responses[i] = resp;
    //System.out.println("Received " + resp);
    }

int[] CPUCores = new int[5];
String[] largestServer = new String[5];

for(int i = 0; i < 5; i++){
    //Trim leading spaces before the first character of text
    responses[i] = responses[i].trim();

    //Print Server names as per UserGuid
    System.out.println(responses[i]);

    //Split the response into an array of strings
    String[] splitResponse = responses[i].split(" ");

    //Parse the 5th value as an integer and store it in the CPUCores array
    CPUCores[i] = Integer.parseInt(splitResponse[4]);

    //Split name of server and cores into string array
    largestServer[i] = splitResponse[0]+" "+splitResponse[4];

    //System.out.println(responses[i]);
}


//Determine the Largest server type in cores
int maxCore = 0;
// Iterate over the CPUCores array
for(int i = 0; i < CPUCores.length; i++){
    if(CPUCores[i] > maxCore){
        maxCore = CPUCores[i];
    }
}

System.out.println("\n BELOW");

//Print the largest value
System.out.println("Largest server type in cores is: " + maxCore);

//Store Server name with most cores in string to have job scheduled to it
for(int i = 0; i < largestServer.length; i++){
    System.out.println(largestServer[i]);
}



//Sending OK message
    System.out.println("Sending: OK");
    out.println("OK");

//read . message
    resp = in.readLine();
    System.out.println("Received "+ resp);


//Sending JOB schedule message to super-silk 0
    System.out.println("Sending: SCHD 0 super-silk 0");
    out.println("SCHD 0 super-silk 0");


//Sending QUIT message
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