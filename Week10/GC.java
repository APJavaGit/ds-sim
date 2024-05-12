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


for(int i = 0; i < 5; i++){
    //Trim leading spaces before the first character of text
    responses[i] = responses[i].trim();

    //Print Server names as per UserGuide
    System.out.println(responses[i]);

    //Split the response into an array of strings
    String[] splitResponse = responses[i].split(" ");
}


//Code to sort the string array "responses", so that each index within the array is sorted from largest to smallest based on the size 5th value as an integer, which is seperated by spaces within each index of the string array. 
for (int i = 0; i < responses.length; i++) {
            for (int j = i + 1; j < responses.length; j++) {
                int valueI = Integer.parseInt(responses[i].split(" ")[4]);
                int valueJ = Integer.parseInt(responses[j].split(" ")[4]);

                if (valueI < valueJ) {
                    String temp = responses[i];
                    responses[i] = responses[j];
                    responses[j] = temp;
                }
            }
        }

//Print out sorted string array of servers
// System.out.println("\n SORTED ---\n");
// for(int i = 0; i < largestServer.length; i++){
//     System.out.println(responses[i]);
// }


//Sending OK message
    System.out.println("Sending: OK");
    out.println("OK");

//read . message
    resp = in.readLine();
    System.out.println("Received "+ resp);



//Code to get the largest server name and ID
String[] largestServer = new String[5];
largestServer = responses[0].split(" ");
//System.out.println("S1 = "+largestServer[0]+" "+largestServer[1]);


//-----------------------

//Sending sch for 1 job
//    System.out.println("Sending: SCHD 0 super-silk 0");
//     out.println("SCHD 0 "+largestServer[0]+" "+largestServer[1]);



//Send SCHD for first job then check server resp to determine if there are more jobs, JCPL, or NONE
    int ctr = 0;
    System.out.println("Sending: SCHD for JOBN "+ctr+" super-silk 0");
    out.println("SCHD "+ctr+" "+largestServer[0]+" "+largestServer[1]);
    ctr++;

//read next server message
    resp = in.readLine();
    System.out.println("Received "+ resp);

    //Asking Server if there are further JOBN's and/or Job updates
    System.out.println("Sending: REDY");
    out.println("REDY");

    //read next server message
    resp = in.readLine();
    System.out.println("Received "+ resp);


    while(resp!="NONE"){

    // Asking Server if there are further JOBN's and/or Job updates
    System.out.println("Sending: REDY");
    out.println("REDY");

    // //read next server message
    resp = in.readLine();
    System.out.println("Received "+ resp);

    if(resp=="NONE"){
        break;
    }

    String arr[] = resp.split(" ", 2);
    //If resp after REDY is JOBN, schedule further job
    if(arr[0]=="JOBN"){
    System.out.println("Sending: SCHD for JOBN "+ctr+" super-silk 0");
    out.println("SCHD "+ctr+" "+largestServer[0]+" "+largestServer[1]);
    ctr++;
    }

    if(arr[0]=="JCPL"){
        continue;
    }
//
    }


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