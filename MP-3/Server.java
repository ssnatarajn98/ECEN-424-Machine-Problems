package Group23;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.NullPointerException;

//Class that Creates Server
public class Server {
    //Initialize the information that will be given to the server:
    static int PortNumber;       //The port number on which clients would connect to the server
    static int MaxClients;    //The maximum number of clients that can connect simultaneously
    static String Input;   //The message to be passed as a string from the server to the clients
    
    //Initialize the Server itself and its socket for the client:
    static ServerSocket Server23;
    static Socket ClientSock;
    
    //Main:
    public static void main(String[] arg) {
        //The server will take as a console input the port number, the maximum number of clients, and the message to be passed to the client.
        //Try/Catches are set up for each integer input wanted:
        try {
            String PortIn = arg[0];                 //Get port from input
            PortNumber = Integer.parseInt(PortIn);
        }
        catch (Exception e) {
            System.out.println("The given port number is not a valid input. Exiting Server...");
            System.exit(1);
        }
        try {
            String MaxClientsIn = arg[1];           //Get maximum number of clients from input
            MaxClients = Integer.parseInt(MaxClientsIn);
        }
        catch (Exception e) {
            System.out.println("The given maximum number of clients is not a valid input. Exiting Server...");
            System.exit(1);
        }
        
        //The message input does not need a try/catch because it can be anything.
        try {                   //Get 50 character message to be sent to client connecting
            int length = 0;
            int temp1 = 0;
            Input = "";
            for(int k = 2; temp1 < 51; k++) {
                length = arg[k].length();
                if(length == 50) {
                    Input = arg[k];
                    break;
                }
                else if(temp1 < 51) {
                    Input = Input + arg[k] + " ";
                    temp1 = temp1 + arg[k].length() + 1;
                }
            }
        }
        catch (Exception e) {
            System.out.println("The given input message is not 50 characters long. Exiting Server...");
            System.exit(1);
        }
        
        //Output that the server is working:
        System.out.println("\nGroup23's Server has been created.");
        System.out.println("It can take a maximum of " + MaxClients + " client(s).");
        System.out.println("The port " + PortNumber + " will be used to connect to the server.");
        
        //Get Server IP address:
        InetAddress ServerIP;
        try {
            ServerIP = InetAddress.getLocalHost();
            System.out.println("The server's IP address is " + ServerIP.getHostAddress() + "\n");
        }
        catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
        
        //Only creates threads for allowed clients:
        for(int j = 0; j < MaxClients; j++) {
            //Creating the Server connection to the client:
            try {
                Server23 = new ServerSocket(PortNumber); //Creates server with given Port
                int ClientsConnected = 0;
                /**The minimum number of possible clients is 0. Therefore, a for loop is
                 created that will loop until there is a thread for each of the maximum amount of
                 clients allowed on the server. There will be multiple clients, but one socket
                 being used.**/
                while(ClientsConnected < MaxClients) {
                    ClientSock = Server23.accept();
                    Thread ClientThread = new Thread(new ServerThread());
                    ClientThread.start();
                    ClientsConnected++;
                    System.out.println("\nClient number " + ClientsConnected + " attempting to connect...");
                }
                Thread ClientThread = new Thread(new ServerThread());
                ClientThread.join();
            }
            catch (Exception e) {
            }
        }
        System.out.println("\nMaximum number of clients connected.\n");
    } //main
} //class

//Class that runs threads for server
class ServerThread implements Runnable {
    
    //Bring values from Server Class over to the Thread:
    Server ServerValues = new Server();
    
    @Override
    public void run() {
        
        //Find out what thread we are on:
        long checker = Thread.currentThread().getId();
        int i = (int) checker;
        i = i - 8;
        
        //Copy values from Server class:
        int PortNumber = ServerValues.PortNumber;
        int MaxClients = ServerValues.MaxClients;
        String Message = ServerValues.Input;
        Socket ClientSock = ServerValues.ClientSock;
        
        //Get the client's address:
        String ClientAddress = ClientSock.getRemoteSocketAddress().toString();
        
        try {
            //Connect client:
            System.out.println("Client number " + i + " connected.");
            System.out.println("Client Information");
            System.out.println("\tClient IP Address: " + ClientAddress);
            
            //Send message from Server to client:
            PrintWriter ServerMessage = new PrintWriter(ClientSock.getOutputStream());
            ServerMessage.write(Message + "\n");
            ServerMessage.flush();
            System.out.println("\tMessage Recieved from Server: True\n");
            
            //Read information coming in and going out:
            InputStreamReader Input = new InputStreamReader(ClientSock.getInputStream());
            BufferedReader ClientInput = new BufferedReader(Input);
            
            //Taking input from the client and disconnecting if needed:
            do {
                try
                {
                    String input = ClientInput.readLine();
                    System.out.println(ClientAddress + " says: " + input);
                    if (input.equals("\\disconnect"))
                    {
                        ClientSock.close();
                        System.out.println("Client number " + i + " disconnected.\n");
                        break;
                    }
                }
                catch (SocketException t) {
                    System.out.println("Client number " + i + " disconnected.\n");
                    break;
                }
                catch (NullPointerException n) {
                    ClientSock.close();
                    System.out.println("Client number " + i + " disconnected.\n");
                    break;
                }
            }while(true);
        }
        catch(IOException s) {
           s.printStackTrace();
        }
    } //run
} //Class
