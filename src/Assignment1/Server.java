/*
 * (c) University of Zurich 2014
 */

package Assignment1;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
  private static int port; 
  
  // the data structure to store incoming messages, you are also free to implement your own data structure.
  static LinkedBlockingQueue<String> messageStore =  new LinkedBlockingQueue<String>();
  static ArrayList<String> history = new ArrayList<String>();

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
    	System.out.println("Cannot open port " + port + ", " + e);
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
		System.out.println("Thread started");
	}
	
	public void run () {
		try {
			// Stream initialization
			System.out.println("Initializing streams");
			BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
			System.out.println("Streams initialized");
			boolean isFirstLine = true;
			String firstLine = null;
			String line;
			
			// Reads first line to distinguish between listener and producer
			while((line = input.readLine()) != null) {
				if(isFirstLine) {
					firstLine = line;
					System.out.println(firstLine);
				}
				
				if(firstLine.equals("LISTENER")) {
					// If this is the first iteration, send all past messages to listener
					if(isFirstLine) {
						for(int i = Server.history.size() - 1; i > 0; i--) {
							System.out.println("To Listener: " + Server.history.get(i));
							output.writeBytes(Server.history.get(i) + "\n");
							System.out.println("sent");
						}
					}
					
					// Check if there is a new message, if yes, send to listener
					String message;
					while(true) {
						System.out.println("Check for new message");
						message = Server.messageStore.take();
						output.writeBytes(message + "\n");
						output.flush();
						Server.history.add(line);
						System.out.println("New message sent");
					}
				} else if(firstLine.equals("PRODUCER")) {
					// Get messages from producer and store them
					if(!isFirstLine) {
						System.out.println(line);
						Server.messageStore.put(line);
					}
				}
				isFirstLine = false;
			}
			
			input.close();
			output.close();
			clientSocket.close();
		}
		catch(IOException e) {
			System.out.println(e);
		}
		catch(InterruptedException e) {
			System.out.println(e);
		}
    }
}