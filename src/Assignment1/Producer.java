/*
 * (c) University of Zurich 2014
 */

package Assignment1;

import java.net.*;
import java.io.*;

public class Producer {
	public static void main(String[] args) {
		String serverName = args[0];
		int port = Integer.parseInt(args[1]);
		String clientName = args[2];
		String inputFileName = args[3];
		DataOutputStream streamOut = null;
		BufferedReader streamIn = null;
		Socket clientSocket = null;

		// create connection to server
		// send the string "PRODUCER" to server first
		// read messages from input file line by line
		// put the client name and colon in front of each message
		// e.g., clientName:....
		// send message until you find ".bye" in the input file
		// close connection
		
		// Try to open connection and streams
	    try {
	    	clientSocket = new Socket(serverName, port);
	    	streamOut = new DataOutputStream(clientSocket.getOutputStream());
	    	streamIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	    }
	    catch(IOException e) {
	    	System.err.println(e);
	    }
	    
	    System.out.println("Producer connections opened");
	    
	    // Try to send announcement message
	    if(streamOut != null) {
	    	try {
	    		streamOut.writeBytes("PRODUCER\n");
	    	}
	    	catch(IOException e) {
	    		System.err.println(e);
	    	}
	    }
	    
	    System.out.println("Announcement sent");
	    
	    // Try to open input file and stream contents to server
	    try {
	    	BufferedReader br = new BufferedReader(new FileReader(inputFileName));
	    	String line;
	    	while((line = br.readLine()) != null) {
	    		System.out.println(line);
	    		if(line.equals(".bye"))
	    			break;
	    		streamOut.writeBytes(clientName + ":" + line);
	    	}
	    	System.out.println("Producer " + clientName + " sent all messages. Closing...");
	    	br.close();
	    }
	    catch(FileNotFoundException e) {
	    	System.err.println(e);
	    }
	    catch(IOException e) {
	    	System.err.println(e);
	    }
	    
	    // Close ressources
	    try {
	    	streamOut.close();
	    	streamIn.close();
	    	clientSocket.close();
	    }
	    catch(IOException e) {
	    	System.out.println(e);
	    }
	}
}
