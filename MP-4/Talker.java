import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.* ;
import java.io.IOException;
import java.io.*;

/**
 * Created by Cemre on 10/31/2015.
 */

//Note some of this code was  gotten from: http://www.binarytides.com/udp-socket-programming-in-java/
public class Talker {
    public static void main(String args[])
    {

        int nRetry = 0;
        boolean retry = true;
        DatagramSocket sock = null;
        String MessageString;
        String zero = "0";

        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));

        // default port number (used if no argument)
        int port = 7777;
        
        //Tells us talker IP:
        InetAddress TalkerIP;
        try {
            TalkerIP = InetAddress.getLocalHost();
            System.out.println("Talker IP Address: " + TalkerIP.getHostAddress() + "\n");
        }
        catch   (UnknownHostException ex){
            ex.printStackTrace();
        }

        // use the argument port number, if available
        if( args.length >= 1 ) {
            port = Integer.parseInt(args[0]);
        }


        try
        {
            sock = new DatagramSocket();
            echo("The server is ready...");
            InetAddress host = InetAddress.getByName("localhost");

            while(true)
            {
                //take input and send the packet
                echo(""); //empty line for nice formatting
                echo("Enter message to send: ");
                MessageString = cin.readLine();

                //cut length of s to 50
                if(MessageString.length() > 50){
                    MessageString = MessageString.substring(0,50);
                    echo("Input more than 50 characters. Trimming to " + MessageString.length() + ".");
                }

                //We must split up our message so we are only sending 10 characters at a time
                //So basically we take some input, s, and then we split it every 10 characters which is why the {10} exists
                //Then in the for loop we simply send each individual thing as an independent string to the Listener and append the [1] [2] etc
                String splitArray[] = MessageString.split("(?<=\\G.{10})");
                //Checking to make sure this sorcery actually works (wow it does)
                //echo("splitArray[0]:" + splitArray[0] + " splitArray[1]:" + splitArray[1] + " splitArray[2]:" + splitArray[2]);


                //This is for sending the message 0 containing the number of 10 char strings.
                int splitArrayLength = splitArray.length; //number of entries in splitArray
                String splitArrayLengthString = Integer.toString(splitArrayLength); //convert to string to send
                echo("Number of 10 char words: " + splitArrayLengthString); //should be <=5


                for (int i = 0; i <= splitArray.length; i++) {

                    byte[] IndividualMessage;
                    //echo(splitArray[i]);

                    //Framing each message with a unique sequence number (adding i to the beginning of each string)



                    //sends the number of strings on the first iteration
                    if(i == 0) IndividualMessage = splitArrayLengthString.getBytes();
                    else {
                        splitArray[i-1] = Integer.toString(i) + splitArray[i-1];
                        echo("splitArray[" + (i-1) + "]=" + splitArray[i-1]);
                        IndividualMessage = splitArray[i-1].getBytes();
                    }
                    
                    //send IndividualMessage
                    DatagramPacket  dp = new DatagramPacket(IndividualMessage , IndividualMessage.length , host , port);
                    sock.send(dp);
                    sock.setSoTimeout(2000);

                    //now receive reply
                    //buffer to receive incoming data
                    do{
                        try{
                            byte[] buffer = new byte[65536];
                            DatagramPacket reply = new DatagramPacket(buffer, buffer.length, host, port);
                            sock.receive(reply);

                            byte[] data = reply.getData();
                            MessageString = new String(data, 0, reply.getLength());

                            //echo the details of incoming data - client ip : client port - client message
                            echo(reply.getAddress().getHostAddress() + ":" + reply.getPort() + " - " + MessageString);
                            retry = false;
                        }
                        catch (SocketTimeoutException e) {
                            echo("Connection has timed out. Resending message...");
                            sock.send(dp);
                            nRetry++;
                            if(nRetry == 5) {
                                sock.close();
                            }
                        }
                    }while(retry);
                }
            }
        }

        catch (SocketException c)	{
            echo("Socket closed " + c);
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
