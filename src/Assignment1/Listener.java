/*
 * (c) University of Zurich 2014
 */

package Assignment1;

import java.net.*;
import java.io.*;

public class Listener
{
    public static void main(String [] args)
   {
      String serverName = args[0];
      int port = Integer.parseInt(args[1]);
      DataInputStream streamIn = null;
      DataOutputStream streamOut = null;
      Socket clientSocket = null;
      BufferedReader input = null;
      String received = null;
      
	  // create connection to server
	  // send the string  "LISTENER" to server first!!
	  // continuously receive messages from server
      // using stdout to print out messages Received
      // do not close the connection- keep listening to further messages from the server.
      
      // Try to open connection and streams
      try {
    	  clientSocket = new Socket(serverName, port);
    	  input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    	  streamOut = new DataOutputStream(clientSocket.getOutputStream());
      }
      catch(IOException e) {
    	  System.err.println(e);
      }
      
      // Try to send announcement message
      if(streamOut != null) {
    	  try {
    		  streamOut.writeBytes("LISTENER\n");
    		  while((received = input.readLine()) != null) {
    			  System.out.println(received);
    		  }
    	  }
    	  catch(IOException e) {
    		  System.err.println(e);
    	  }
      }
   	  
   	  // Close resources
   	  
   }
}
