import java.net.* ;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Created by Cemre on 10/31/2015.
 */


//Note: A good place to look for code is: https://stackoverflow.com/questions/10556829/sending-and-receiving-udp-packets-using-java
// Although this code is sourced from: http://www.binarytides.com/udp-socket-programming-in-java/

public class Listener {
    public static void main(String args[])
    {
        //Change this to toggle unreliability!
        boolean UnreliableACK = true; //true = unreliable ACK, false = reliable ACK


        //This is our random number generator for packet loss
        Random ran = new Random();


        //Declarations
        int trial = 0;
        int NumMessages = 0;
        int port = 7777;
        int ReceivedMessageNumber = 0;
        DatagramSocket sock = null;
        String ACK = "";
        String FullWord = "";

        try
        {
            //Command line input address and port:
            port = Integer.parseInt(args[1]);
            String TalkerIP = args[0];
            
            //1. creating a server socket, parameter is local port number
            sock = new DatagramSocket(port);

            //buffer to receive incoming data
            byte[] buffer = new byte[65536];
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);

            //2. Wait for an incoming data
            echo("Server socket created. Waiting for incoming data...");

            //communication loop
            while(true)
            {
                sock.receive(incoming);
                byte[] data = incoming.getData();
                String IncomingString = new String(data, 0, incoming.getLength());


                //if message 0 is sent, extract int to know how many messages are coming
                if(trial == 0) {
                    NumMessages = Integer.parseInt(IncomingString);
                    //echo("NumMessages: " + NumMessages);
                }
                //if it is any other message besides 0
                else {
                    // try to extract an int from the first character (the frame number).
                    try {
                        ReceivedMessageNumber = Integer.parseInt(IncomingString.substring(0,1));
                        IncomingString = IncomingString.substring(1,IncomingString.length());
                        //echo("RecievedMessageNumber: " + ReceivedMessageNumber + ", IncomingString: " + IncomingString);
                    }
                    catch (NumberFormatException e) {
                        echo("Message does not start with a number. Error: " + e);
                    }
                }

                //Here we are simply outputting in console what was sent to the user
                //echo("IncomingString = " + IncomingString);


                //check to make sure the message sent is the correct number. Basically just makes sure it is new data.
                if(ReceivedMessageNumber == trial){
                    //prints the new data
                    echo(incoming.getAddress().getHostAddress() + ":" + incoming.getPort() + "(" + trial + ") - " + IncomingString);

                    // For all but the first trial, concatinate the incoming string to the FullWord.
                    if(trial!=0) FullWord = FullWord + IncomingString; //doesnt add message 0 (the size message)

                    // check to see if it is the final message.
                    if(trial == NumMessages){
                        ACK = "ACK: " + trial + " Received all messages.";
                        trial = 0;
                        echo(""); //empty line for formatting
                        echo("Full Message: " + FullWord);
                        echo(""); //empty line for formatting
                        FullWord = "";

                    }
                    //not the final message yet
                    else {
                        ACK = "ACK: " + trial + " Please send: " + (trial + 1);
                        trial = trial + 1;
                    }
                }
                // if message has already been received.
                else {
                    echo("Message " + trial + " has already been received. Discarding data. ");
                }


                //  Use this for UNreliable networks (UnreliableACK = true)
                if(UnreliableACK) {
                    //Random int for reliability
                    int AWGN = ran.nextInt(100);

                    //echo("THE AWGN IS : " + AWGN);
                    if (AWGN > 50) {

                        DatagramPacket dp = new DatagramPacket(ACK.getBytes(), ACK.getBytes().length, incoming.getAddress(), incoming.getPort());
                        sock.send(dp);
                    }
                    //Here we are simply outputting in console what was sent to the user
                    //echo(ACK);

                    else
                    {   //This is simply for testing once the ACK messages are cleared up it'll be clear from reading talker and no need for this
                        echo("SOMETHING HAPPENED AND AN ACK WAS NOT SENT FOR: " + trial);
                    }
                }

                //Use this for reliable networks (UnreliableACK = false);
                else {
                    DatagramPacket dp = new DatagramPacket(ACK.getBytes(), ACK.getBytes().length, incoming.getAddress(), incoming.getPort());
                    sock.send(dp);
                }

                //echo("Full Message: " + FullWord);


            }
        }

        catch(IOException e)
        {
            System.err.println("IOException " + e);
        }
    }

    //simple function to echo data to terminal
    public static void echo(String msg)
    {
        System.out.println(msg);
    }
}
