/** Class: COSC 2327
 *  LAST EDITED: 9/24/26
 * @author Jesus Cardenas
 * @operating-system Endeavour OS (Linux)
 */

//Libraries
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSide
{
    //Objects
    DataOutputStream serverToClient;
    DataInputStream clientToServer;
    DataOutputStream serverLog;

    //Variables
    private int networkPort = 12345;
    protected int idxUserClient;
    protected int numUsers;
    protected ServerSocket srvrSocket;
    private Socket clientSocket;

    ///For Debug Purposes
    int numClientMessages;
    String userString;

    public ServerSide()
    {
        numClientMessages = 0;
        userString = "";

        try
        {
            srvrSocket = new ServerSocket(networkPort);
            System.out.println("Server started and listening on port " + networkPort);
        } catch (IOException e)
        {
            System.out.println("IO Exception caught");
        }
    }

    public void start()
    {
            try
            {
                //Connection on
                clientSocket = srvrSocket.accept();
                System.out.println("Connection Accepted");

                clientToServer = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                serverToClient = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                serverLog = new DataOutputStream(new BufferedOutputStream(System.out));

                while (numClientMessages < 20)
                {
                    userString = clientToServer.readUTF();

                    if (userString.equalsIgnoreCase("Quit"))
                    {
                        break;
                    }
                    serverLog.writeUTF(userString + "\n");
                    serverLog.flush();
                    serverToClient.writeUTF("Received the following message: " + userString);
                    serverToClient.flush();

                    numClientMessages++;
                }

                System.out.println("Thank you for the messages, closing connection");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Client Accepted");


            System.out.println("Goodbye from the server side!");
        }
}

