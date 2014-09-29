Assignment 1
------------------

In this assignment, you will use socket programming to simulate a chat room application. You have to implement:
- A TCP server: it has to store all messages sent from the producer client. It also has to send all stored messages to a new listener client, and keep the listener client updated if a new message is received. 
- A TCP Producer client: it first identifies itself as a producer client to the server and then sends messages. 
- A TCP Listener client: it first identifies itself as a listener client to the server and then continuously receives messages from the server. 

All implementations have to be in the Java programming language. Partially implemented java files for the server and clients are provided inside the "Assignment1" package. Your task is to complete the implementation. Apart from the java codebase, the following files are included in the project bundle
- messages.txt is an example file the Producer will have to read
- test_script.sh is an example evaluation script. You need a bash shell to execute this script. Before running the script, make it executable with the command 'chmod +x test_script.sh'.
- sample_output.txt is the example output the Listener will have to produce if the Producer used messages.txt file. “test_script.sh” will compare your output with it.


The final submission has to be a single jar file, which includes three java files (Server.java, Producer.java, and Server.java). 

The submitted jar will be executed as below (just an example):
(1) java -cp submitted_file.jar Assignment1.Server 8000
(2) java -cp submitted_file.jar Assignment1.Producer localhost 8000 Client1 input.text
(3) java -cp submitted_file.jar Assignment1.Listener localhost 8000 


The Server
---------------
You have to complete the server implementation in the file Server.java. The server takes one argument: the port number to listen at, as shown in (1) above. By default, the server will be running on “localhost” and infinitely listens for incoming connections on the specified port. When a new request arrives, it spawns a new thread and handles the request. The server will receive requests from two clients described above. 
First, it has to store messages received from the Producer. Second, when a Listener connects to it, it has to send the stored messages to the Listener. Furthermore, it always keeps the connections with Listeners and sends out all new messages it received from any producers. Up to 1-second’s delay is allowed when the server sends new messages to the Listener

The Clients
----------------
There are two types of clients you have to implement: Producer and Listener.

Producer
------------
The implementation of Producer is inside Producer.java. It takes four command line parameters: the name of the server, port number of the server, name of the producer, and input file (as shown in (2) above). 

The producer has to connect to the server at the specified name and port number (if your server and clients are running on the same computer, the server name should be “localhost”). 

After connected with Server, the Producer first sends the string “PRODUCER” to server, in order to differentiate itself from “LISTENER” clients. Then, it has to open the specified the input file and sends every line to the server until it encounters a line ".bye". It should not send any more messages and exits once this line is found. “Name of the producer” is used to differentiate messages from different producers. Each message has to be prepended with the name of the producer followed by a colon. For example, if a producer named "1" reads a message "Hello", it should be send as "1:Hello". You can assume that producer names will be unique and there will be less than 100 messages in total from all producers.


Listener
-----------
The implementation of Listener is inside Listener.java. It takes two command line parameters: the name and the port number of the server (as shown in (3) above). It first also sends the string “LISTENER” to server. It then continuously receives the messages sent from the server infinitely. It has to print the messages to stdout as they are received.

Evaluation:
---------------
Besides, sending and receiving messages, we will also test:
- The Server has to be able to handle multiple Producer/Listener clients. You can assume no more than 5 clients are connected to the server concurrently.
- The Server has to keep all Listener clients updated. 
- The Producer and the Listener clients need first send the string “PRODUCER” or “LISTENER” to identify them.

The evaluation of your submission will be done using a script similar to the one we have provided you inside "test_script.sh". You need a bash shell (usually on a UNIX like environment) to run this script. This script is run like this:

./test_script.sh submitted_file.jar 8080 1 messages.txt output.txt

If your implementation is correct, it will print the message "Test PASSED".

Please note that we'll test your submission with a different input and output files. We will test your server and clients against our own implementations to make sure that each component does the required task. For example, to test your server implementation, we'll run the Server from your jar file and the clients from our jar file.

You will receive 50% credit for correctly implementing the server, and 30% and 20% credit for implementing the Producer and Listener respectively. Assignment 1 also has a theoretical assignment that will be published later. The total contribution of the theoretical assignment is 20% and that of the above project is 80%

Submission:
----------------
- Please submit your jar file before the deadline. 
- The jar files will have to be named after your OLAT username. For example, if your username is bpaude, your jar file has to be named bpaude.jar. 
- To create the jar file, right click on the project in your IDE, click on Export -> Java -> JAR File -> (enter the name for the jar file, check "Export all output folders for checked projects" and "Export Java source files and resources") -> Finish.
- Your submission should not print anything on the stdout other than what is specified above. E.g., only the Listener can print the received messages to stdout.
- You should also not hardcode the server name, port number, input file or output file. If we cannot use the jar file you submitted with our test script like the one mentioned above, you will receive no credit for your submission.
- You have to write your program in Java version 1.7 or later
- You should not use any external libraries.

