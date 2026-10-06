package ServerHandling; /** Class: COSC 2327
 *  LAST EDITED: 9/24/26
 * @author Jesus Cardenas
 * @operating-system Endeavour OS (Linux)
 */

//Libraries
import lombok.Getter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSide
{
    //Objects
    protected DataOutputStream serverToClient;
    protected DataInputStream clientToServer;
    protected List<ServerThread> activeConn;

    //Variables
    private int networkPort = 12345;
    protected ServerSocket srvrSocket;
    private Socket clientSocket;

    ///For Debug Purposes
    int numClientMessages;
    String userString;

    public ServerSide()
    {
        try
        {
            activeConn = new ArrayList<>();
            srvrSocket = new ServerSocket(networkPort);

        } catch (IOException e)
        {
            System.out.println("IO Exception caught");
        }
    }

    public void start()
    {
            try
            {
                System.out.println("Server started and listening on port: " + networkPort);

                //Connection on
                clientSocket = srvrSocket.accept();
                System.out.println("Connection Accepted");


                ServerThread userThread = new ServerThread(clientSocket, activeConn);
                activeConn.add(userThread);
                userThread.run();

//                clientToServer = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
//                serverToClient = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
//
//                while (numClientMessages < 20)
//                {
//                    userString = clientToServer.readUTF();
//
//                    if (userString.equalsIgnoreCase("Quit"))
//                    {
//                        break;
//                    }
//                    serverToClient.writeUTF("Received the following message: " + userString);
//                    serverToClient.flush();
//
//                    numClientMessages++;
//                }
//
//                System.out.println("Thank you for the messages, closing connection");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Goodbye from the server side!");
        }

        public void broadcast(String msg)
        {
            for (ServerThread x : activeConn)
            {
            }
        }
}

