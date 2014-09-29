/*
 * (c) University of Zurich 2014
 */

import java.net.*;
import java.io.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
  private static int port; 
  
  // the data structure to store incoming messages, you are also free to implement your own data structure.
  static LinkedBlockingQueue<String> messageStore =  new LinkedBlockingQueue<String>();

  // Listen for incoming client connections and handle them
  public static void main(String[] args) {
	//port number to listen to
    port = Integer.parseInt(args[0]);

    // the server listens to incoming connections
    // this is a blocking operation
    // which means the server listens to connections infinitely
    // when a new request is accepted, spawn a new thread to handle the request
    // keep listening to further requests
    // if you want, you can use the class HandleClient to process client requests
    // the first message for each new client connection is either "PRODUCER" or "LISTENER"

    /*
     * Your implementation goes here
     */
    ServerSocket server = null;
    Socket clientSocket = null;
    
    // Try to open a server socket
    try {
    	server = new ServerSocket(port);
    }
    catch(IOException e) {
    	System.out.println(e);
    }
    
    // Loop for accepting new connections, gives them to a new thread
    try {
    	while(true) {
    		clientSocket = server.accept();
    		new Thread(new HandleClient(clientSocket)).start();
    	}
    }
    catch(IOException e) {
    	System.out.println(e);
    }
  }  
}

// you can use this class to handle incoming client requests
// you are also free to implement your own class
class HandleClient implements Runnable {
	private Socket clientSocket;
	
	public HandleClient(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	public void run () {
		try {
			// Stream initialization
			BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
			
			// Reads first line to distinguish between listener and producer
			String firstLine = input.readLine();
			
			// Stores top String of messages to check against new entries
			String topString = Server.messageStore.peek();
			String checkString;
			
			if(firstLine == "LISTENER") {
				Object[] queue = Server.messageStore.toArray();
				for(int i = 0; i < queue.length; i++) {
					output.writeBytes(queue[i].toString());
				}
				while(true) {
					checkString = Server.messageStore.peek();
					if(topString != checkString) {
						output.writeBytes(checkString);
						topString = checkString;
					}
				}
			} else if(firstLine == "PRODUCER") {
				while(true) {
					Server.messageStore.put(input.readLine());
				}
			}
		}
		catch(IOException e) {
			System.out.println(e);
		}
		catch(InterruptedException e) {
			System.out.println(e);
		}
    }
}